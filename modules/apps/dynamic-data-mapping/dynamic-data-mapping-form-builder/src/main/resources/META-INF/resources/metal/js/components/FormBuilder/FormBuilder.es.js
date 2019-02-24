import {Config} from 'metal-state';
import {debounce} from 'metal-debounce';
import {EventHandler} from 'metal-events';
import {focusedFieldStructure, pageStructure, ruleStructure} from '../../util/config.es';
import {generateFieldName} from '../LayoutProvider/util/fields.es';
import {normalizeSettingsContextPages} from '../../util/fieldSupport.es';
import {PagesVisitor} from '../../util/visitors.es';
import autobind from 'autobind-decorator';
import ClayModal from 'clay-modal';
import Component from 'metal-jsx';
import dom from 'metal-dom';
import FormRenderer from '../../components/Form/index.es';
import FormSupport from '../../components/Form/FormSupport.es';
import Sidebar from '../../components/Sidebar/index.es';

/**
 * Builder.
 * @extends Component
 */

class Builder extends Component {
	static STATE = {

		/**
		 * @default []
		 * @instance
		 * @memberof FormBuilder
		 * @type {?array<object>}
		 */

		indexes: Config.object()
	}

	static PROPS = {

		/**
		 * @default
		 * @instance
		 * @memberof FormBuilder
		 * @type {?number}
		 */

		activePage: Config.number().value(0),

		editingLanguageId: Config.string(),

		/**
		 * @default {}
		 * @instance
		 * @memberof FormBuilder
		 * @type {?object}
		 */

		focusedField: focusedFieldStructure.value({}),

		/**
		 * @default {}
		 * @instance
		 * @memberof FormBuilder
		 * @type {string}
		 */

		locale: Config.string().value('en_US'),

		/**
		 * @default []
		 * @instance
		 * @memberof FormBuilder
		 * @type {?array<object>}
		 */

		pages: Config.arrayOf(pageStructure).value([]),

		/**
		 * @instance
		 * @memberof FormBuilder
		 * @type {string}
		 */

		paginationMode: Config.string().required(),

		/**
		 * @instance
		 * @memberof FormBuilder
		 * @type {string}
		 */

		rules: Config.arrayOf(ruleStructure).required(),

		/**
		 * @instance
		 * @memberof FormBuilder
		 * @type {object}
		 */

		successPageSettings: Config.shapeOf(
			{
				body: Config.object(),
				enabled: Config.bool(),
				title: Config.object()
			}
		)
	};

	created() {
		this._eventHandler = new EventHandler();
		this._debouncedFieldEditedHandler = debounce(this._debouncedFieldEditedHandler.bind(this), 100);
	}

	attached() {
		const {activePage, pages} = this.props;
		const formBasicInfo = document.querySelector('.ddm-form-basic-info');
		const translationManager = document.querySelector('.ddm-translation-manager');

		if (formBasicInfo && translationManager) {
			formBasicInfo.classList.remove('hide');
			translationManager.classList.remove('hide');
		}

		if (!this._pageHasFields(pages, activePage)) {
			this.openSidebar();
		}
	}

	disposeInternal() {
		super.disposeInternal();

		this._eventHandler.removeAllListeners();
	}

	rendered() {
		const {sidebar} = this.refs;

		sidebar.refreshDragAndDrop();
	}

	willReceiveProps(changes) {
		let {activePage, pages} = this.props;
		let openSidebar = false;

		if (changes.activePage && changes.activePage.newVal !== -1) {
			activePage = changes.activePage.newVal;

			if (!this._pageHasFields(pages, activePage)) {
				openSidebar = true;
			}
		}

		if (
			changes.pages &&
			changes.pages.prevVal &&
			changes.pages.newVal.length !== changes.pages.prevVal.length
		) {
			pages = changes.pages.newVal;

			if (!this._pageHasFields(pages, activePage)) {
				openSidebar = true;
			}
		}

		if (openSidebar) {
			this.openSidebar();
		}
	}

	syncVisible(visible) {
		const addButton = document.querySelector('#addFieldButton');

		super.syncVisible(visible);

		if (visible) {
			addButton.classList.remove('hide');

			this._eventHandler.add(
				dom.on('#addFieldButton', 'click', this._handleAddFieldButtonClicked.bind(this))
			);
		}
		else {
			this._eventHandler.removeAllListeners();
		}
	}

	openSidebar() {
		const {sidebar} = this.refs;

		sidebar.open();
	}

	preparePagesForRender(pages) {
		const visitor = new PagesVisitor(pages);

		return visitor.mapFields(
			field => {
				if (field.type === 'select' && field.dataSourceType !== 'manual') {
					field = {
						...field,
						options: [
							{
								label: Liferay.Language.get('dynamically-loaded-data'),
								value: 'dynamic'
							}
						],
						value: 'dynamic'
					};
				}

				return field;
			}
		);
	}

	_debouncedFieldEditedHandler(fieldInstance, value) {
		const {locale} = this.props;
		const {fieldName} = fieldInstance;

		this.emit(
			'fieldEdited',
			{
				locale,
				propertyName: fieldName,
				propertyValue: value
			}
		);
	}

	_handleActivePageUpdated(activePage) {
		this.emit('activePageUpdated', activePage);
	}

	/**
	 * @param {!Object} event
	 * @private
	 */
	@autobind
	_handleSettingsFieldBlurred({fieldInstance, value}) {
		const {locale} = this.props;
		const {fieldName} = fieldInstance;

		this.emit(
			'fieldBlurred',
			{
				locale,
				propertyName: fieldName,
				propertyValue: value
			}
		);
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */
	@autobind
	_handleSidebarFieldBlurred() {
		this.emit('sidebarFieldBlurred');
	}

	/**
	 * Handles click on plus button. Button shows Sidebar when clicked.
	 * @param {!Object} index
	 * @private
	 */

	_handleAddFieldButtonClicked() {
		this.openSidebar();
	}

	@autobind
	_handleCancelChangesModalButtonClicked(event) {
		event.stopPropagation();

		const {target} = event;
		const {cancelChangesModal, sidebar} = this.refs;

		if (this._isOutsideModal(target)) {
			sidebar.close();
		}

		cancelChangesModal.emit('hide');

		if (!event.target.classList.contains('close-modal')) {
			this.emit('fieldChangesCanceled', {});
		}
	}

	/**
	 * Handle the cancel field changes modal
	 * for checking if the user is sure about reseting his focused field
	 * @param {!Event} event
	 * @private
	 */

	_handleCancelFieldChangesModal() {
		const {cancelChangesModal} = this.refs;

		cancelChangesModal.show();
	}

	_handleDeleteFieldClicked(indexes) {
		this.setState(
			{
				indexes
			}
		);

		this._handleDeleteModal();
	}

	/**
	 * @param {!Event} event
	 * @private
	 */

	_handleDeleteModal() {
		const {deleteModal} = this.refs;

		deleteModal.show();
	}

	@autobind
	_handleDeleteModalButtonClicked(event) {
		event.stopPropagation();

		const {target} = event;
		const {deleteModal, sidebar} = this.refs;
		const {indexes} = this.state;

		if (this._isOutsideModal(target)) {
			sidebar.close();
		}

		deleteModal.emit('hide');

		if (!event.target.classList.contains('close-modal')) {
			this.emit(
				'fieldDeleted',
				{...indexes}
			);
		}
	}

	/**
	 * Event handler for when the user adds a new field to the Form Builder.
	 * This method creates a new field name based on the label of the FieldType
	 * added and emits an event with the new field configurations.
	 * @param {!Event} event
	 * @private
	 */

	_handleFieldAdded(event) {
		const {fieldType} = event;
		const {namespace} = this.props;
		const {settingsContext} = fieldType;
		const {pages} = settingsContext;
		const newFieldName = generateFieldName(this.props.pages, fieldType.label);

		const focusedField = {
			...fieldType,
			fieldName: newFieldName,
			settingsContext: {
				...settingsContext,
				pages: normalizeSettingsContextPages(pages, namespace, fieldType, newFieldName),
				type: fieldType.name
			}
		};

		this.emit(
			'fieldAdded',
			{
				...event,
				focusedField
			}
		);

		this.openSidebar();
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Object} index
	 * @private
	 */

	_handleFieldClicked({pageIndex, rowIndex, columnIndex}) {
		const {pages} = this.props;
		const fieldProperties = FormSupport.getField(pages, pageIndex, rowIndex, columnIndex);
		const {spritemap} = this.props;
		const {settingsContext} = fieldProperties;
		const visitor = new PagesVisitor(settingsContext.pages);

		const focusedField = {
			...fieldProperties,
			columnIndex,
			pageIndex,
			rowIndex,
			settingsContext: {
				...settingsContext,
				pages: visitor.mapFields(
					field => {
						const {fieldName} = field;

						if (fieldName === 'name') {
							field.visible = true;
						}
						else if (fieldName === 'label') {
							field.type = 'text';
						}
						else if (fieldName === 'validation') {
							field = {
								...field,
								validation: {
									...field.validation,
									fieldName: fieldProperties.fieldName
								}
							};
						}

						return field;
					}
				)
			},
			spritemap
		};

		this.openSidebar();

		this.emit(
			'fieldClicked',
			{
				...focusedField,
				originalContext: focusedField
			}
		);
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Object}
	 * @private
	 */

	@autobind
	_handleFieldDuplicated(indexes) {
		this.emit('fieldDuplicated', indexes);
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Object} event
	 * @private
	 */
	@autobind
	_handleSettingsFieldEdited({fieldInstance, value}) {
		this._debouncedFieldEditedHandler(fieldInstance, value);
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldMoved(event) {
		this.emit('fieldMoved', event);
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Object}
	 * @private
	 */
	@autobind
	_handleFocusedFieldUpdated(focusedField) {
		const settingsContext = focusedField.settingsContext;

		this.emit(
			'focusedFieldUpdated',
			{
				...focusedField,
				settingsContext
			}
		);
	}

	_handlePageAdded() {
		this.emit('pageAdded');
	}

	_handlePageDeleted(pageIndex) {
		this.emit('pageDeleted', pageIndex);
	}

	_handlePageReset() {
		this.openSidebar();

		this.emit('pageReset');
	}

	/**
	 * Continues the propagation of event.
	 * @private
	 */

	_handlePaginationModeUpdated() {
		this.emit('paginationModeUpdated');
	}

	/**
	 * Continues the propagation of event.
	 * @param {Array} pages
	 * @private
	 */

	_handlePagesUpdated(pages) {
		this.emit('pagesUpdated', pages);
	}

	/**
	 * Continues the propagation of event.
	 * @param {Object} successPageSettings
	 * @private
	 */
	_handleSuccessPageChanged(successPageSettings) {
		this.emit('successPageChanged', successPageSettings);
	}

	_isOutsideModal(node) {
		return !dom.closest(node, '.close-modal');
	}

	/**
	 * Continues the propagation of event.
	 * @param {Array} pages
	 * @param {Number} pageIndex
	 * @private
	 */
	_pageHasFields(pages, pageIndex) {
		const visitor = new PagesVisitor([pages[pageIndex]]);

		let hasFields = false;

		visitor.mapFields(
			() => {
				hasFields = true;
			}
		);

		return hasFields;
	}

	/**
	 * @inheritDoc
	 */

	render() {
		const {
			_handleCancelChangesModalButtonClicked,
			_handleDeleteModalButtonClicked,
			props
		} = this;
		const {
			activePage,
			editingLanguageId,
			fieldTypes,
			focusedField,
			namespace,
			pages,
			paginationMode,
			rules,
			spritemap,
			successPageSettings,
			visible
		} = props;

		const FormRendererEvents = {
			activePageUpdated: this._handleActivePageUpdated.bind(this),
			fieldClicked: this._handleFieldClicked.bind(this),
			fieldDeleted: this._handleDeleteFieldClicked.bind(this),
			fieldDuplicated: this._handleFieldDuplicated,
			fieldMoved: this._handleFieldMoved.bind(this),
			pageAdded: this._handlePageAdded.bind(this),
			pageDeleted: this._handlePageDeleted.bind(this),
			pageReset: this._handlePageReset.bind(this),
			pagesUpdated: this._handlePagesUpdated.bind(this),
			paginationModeUpdated: this._handlePaginationModeUpdated.bind(this),
			successPageChanged: this._handleSuccessPageChanged.bind(this)
		};

		const sidebarEvents = {
			fieldAdded: this._handleFieldAdded.bind(this),
			fieldBlurred: this._handleSidebarFieldBlurred,
			fieldChangesCanceled: this._handleCancelFieldChangesModal.bind(this),
			fieldDeleted: this._handleDeleteFieldClicked.bind(this),
			fieldDuplicated: this._handleFieldDuplicated,
			focusedFieldUpdated: this._handleFocusedFieldUpdated,
			settingsFieldBlurred: this._handleSettingsFieldBlurred,
			settingsFieldEdited: this._handleSettingsFieldEdited
		};

		return (
			<div>
				<div class="container">
					<div class="sheet">
						<FormRenderer
							activePage={activePage}
							editable={true}
							editingLanguageId={editingLanguageId}
							events={FormRendererEvents}
							pages={this.preparePagesForRender(pages)}
							paginationMode={paginationMode}
							ref="FormRenderer"
							spritemap={spritemap}
							successPageSettings={successPageSettings}
						/>
						<ClayModal
							body={Liferay.Language.get('are-you-sure-you-want-to-delete-this-field')}
							events={{
								clickButton: _handleDeleteModalButtonClicked
							}}
							footerButtons={[
								{
									alignment: 'right',
									label: Liferay.Language.get('dismiss'),
									style: 'primary',
									type: 'close'
								},
								{
									alignment: 'right',
									label: Liferay.Language.get('delete'),
									style: 'primary',
									type: 'button'
								}
							]}
							ref="deleteModal"
							size="sm"
							spritemap={spritemap}
							title={Liferay.Language.get('delete-field-dialog-title')}
						/>
						<ClayModal
							body={Liferay.Language.get('are-you-sure-you-want-to-cancel')}
							events={{
								clickButton: _handleCancelChangesModalButtonClicked
							}}
							footerButtons={[
								{
									alignment: 'right',
									label: Liferay.Language.get('dismiss'),
									style: 'primary',
									type: 'close'
								},
								{
									alignment: 'right',
									label: Liferay.Language.get('yes-cancel'),
									style: 'primary',
									type: 'button'
								}
							]}
							ref="cancelChangesModal"
							size="sm"
							spritemap={spritemap}
							title={Liferay.Language.get('cancel-field-changes-question')}
						/>
					</div>
				</div>
				<Sidebar
					editingLanguageId={editingLanguageId}
					events={sidebarEvents}
					fieldTypes={fieldTypes}
					focusedField={focusedField}
					namespace={namespace}
					ref="sidebar"
					rules={rules}
					spritemap={spritemap}
					visible={visible}
				/>
			</div>
		);
	}
}

export default Builder;
export {Builder};