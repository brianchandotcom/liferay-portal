import ClayModal from 'clay-modal';
import Component from 'metal-jsx';
import compose from '../../util/compose.es';
import dom from 'metal-dom';
import FormRenderer from '../../components/Form/FormRenderer.es';
import Sidebar from '../../components/Sidebar/Sidebar.es';
import withActionableFields from './withActionableFields.es';
import withEditablePageHeader from './withEditablePageHeader.es';
import withMoveableFields from './withMoveableFields.es';
import withMultiplePages from './withMultiplePages.es';
import withResizeableColumns from './withResizeableColumns.es';
import {Config} from 'metal-state';
import {EventHandler} from 'metal-events';
import {focusedFieldStructure, pageStructure, ruleStructure} from '../../util/config.es';
import {generateFieldName} from '../LayoutProvider/util/fields.es';
import {normalizeSettingsContextPages} from '../../util/fieldSupport.es';
import {PagesVisitor} from '../../util/visitors.es';

/**
 * Builder.
 * @extends Component
 */

class FormBuilder extends Component {
	static PROPS = {

		/**
		 * @default
		 * @instance
		 * @memberof FormBuilder
		 * @type {?number}
		 */

		activePage: Config.number().value(0),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormBuilder
		 * @type {?string}
		 */

		defaultLanguageId: Config.string(),

		/**
		 * @default undefined
		 * @instance
		 * @memberof FormBuilder
		 * @type {?string}
		 */

		editingLanguageId: Config.string(),

		/**
		 * @default []
		 * @instance
		 * @memberof Sidebar
		 * @type {?(array|undefined)}
		 */

		fieldTypes: Config.array().value([]),

		/**
		 * @default {}
		 * @instance
		 * @memberof FormBuilder
		 * @type {?object}
		 */

		focusedField: focusedFieldStructure.value({}),

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
		 * @default undefined
		 * @instance
		 * @memberof FormRenderer
		 * @type {!string}
		 */

		spritemap: Config.string().required()
	};

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

	created() {
		this._eventHandler = new EventHandler();
		this._handleCancelChangesModalButtonClicked = this._handleCancelChangesModalButtonClicked.bind(this);
	}

	disposeInternal() {
		super.disposeInternal();

		this._eventHandler.removeAllListeners();
	}

	getFormRendererEvents() {
		return {
			fieldClicked: this._handleFieldClicked.bind(this)
		};
	}

	getSidebarEvents() {
		return {
			fieldAdded: this._handleFieldAdded.bind(this),
			fieldBlurred: this._handleSidebarFieldBlurred.bind(this),
			fieldChangesCanceled: this._handleCancelFieldChangesModal.bind(this),
			fieldDeleted: this._handleFieldDeleted.bind(this),
			fieldDuplicated: this._handleFieldDuplicated.bind(this),
			focusedFieldUpdated: this._handleFocusedFieldUpdated.bind(this),
			settingsFieldBlurred: this._handleSettingsFieldBlurred.bind(this),
			settingsFieldEdited: this._handleSettingsFieldEdited.bind(this)
		};
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

	render() {
		const {props} = this;
		const {
			activePage,
			defaultLanguageId,
			editingLanguageId,
			fieldTypes,
			focusedField,
			namespace,
			pages,
			paginationMode,
			rules,
			spritemap,
			visible
		} = props;

		return (
			<div>
				<div class="container ddm-form-builder">
					<div class="sheet">
						<FormRenderer
							activePage={activePage}
							editable={true}
							editingLanguageId={editingLanguageId}
							events={this.getFormRendererEvents()}
							pages={this.preparePagesForRender(pages)}
							paginationMode={paginationMode}
							ref="FormRenderer"
							spritemap={spritemap}
						/>

						<ClayModal
							body={Liferay.Language.get('are-you-sure-you-want-to-cancel')}
							events={{
								clickButton: this._handleCancelChangesModalButtonClicked
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
					defaultLanguageId={defaultLanguageId}
					editingLanguageId={editingLanguageId}
					events={this.getSidebarEvents()}
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

	rendered() {
		const {sidebar} = this.refs;

		sidebar.refreshDragAndDrop();
	}

	syncEditingLanguageId(editingLanguageId) {
		const {defaultLanguageId} = this.props;
		const addButton = document.querySelector('#addFieldButton');

		if (defaultLanguageId === editingLanguageId) {
			addButton.classList.remove('invisible');
		}
		else {
			addButton.classList.add('invisible');
		}
	}

	syncVisible(visible) {
		const addButton = document.querySelector('#addFieldButton');
		const translationManager = document.querySelector('.ddm-translation-manager');

		super.syncVisible(visible);

		if (visible) {
			addButton.classList.remove('hide');
			translationManager.classList.remove('hide');

			this._eventHandler.add(
				dom.on('#addFieldButton', 'click', this._handleAddFieldButtonClicked.bind(this))
			);
		}
		else {
			this._eventHandler.removeAllListeners();
		}
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

	_handleAddFieldButtonClicked() {
		this.openSidebar();
	}

	_handleCancelChangesModalButtonClicked(event) {
		const {store} = this.context;

		event.stopPropagation();

		const {target} = event;
		const {cancelChangesModal, sidebar} = this.refs;

		if (this._isOutsideModal(target)) {
			sidebar.close();
		}

		cancelChangesModal.emit('hide');

		if (!event.target.classList.contains('close-modal')) {
			store.emit('fieldChangesCanceled', {});
		}
	}

	_handleCancelFieldChangesModal() {
		const {cancelChangesModal} = this.refs;

		cancelChangesModal.show();
	}

	_handleFieldAdded(event) {
		const {dispatch} = this.context;
		const {fieldType} = event;
		const {editingLanguageId} = this.props;
		const {settingsContext} = fieldType;
		const {pages} = settingsContext;
		const newFieldName = generateFieldName(this.props.pages, fieldType.label);

		const focusedField = {
			...fieldType,
			fieldName: newFieldName,
			settingsContext: {
				...settingsContext,
				pages: normalizeSettingsContextPages(pages, editingLanguageId, fieldType, newFieldName),
				type: fieldType.name
			}
		};

		const addedToPlaceholder = !event.data.target.parentElement.parentElement.classList.contains('position-relative');

		dispatch(
			'fieldAdded',
			{
				...event,
				addedToPlaceholder,
				focusedField
			}
		);

		this.openSidebar();
	}

	_handleFieldClicked(event) {
		const {dispatch} = this.context;

		this.openSidebar();

		dispatch('fieldClicked', event);
	}

	_handleFieldDeleted(event) {
		this.emit('fieldDeleted', event);
	}

	_handleFieldDuplicated(event) {
		this.emit('fieldDuplicated', event);
	}

	_handleFocusedFieldUpdated(focusedField) {
		const {store} = this.context;
		const settingsContext = focusedField.settingsContext;

		store.emit(
			'focusedFieldUpdated',
			{
				...focusedField,
				settingsContext
			}
		);
	}

	_handleSettingsFieldBlurred({fieldInstance, value}) {
		const {store} = this.context;
		const {editingLanguageId} = this.props;
		const {fieldName} = fieldInstance;

		store.emit(
			'fieldBlurred',
			{
				editingLanguageId,
				propertyName: fieldName,
				propertyValue: value
			}
		);
	}

	_handleSettingsFieldEdited({fieldInstance, value}) {
		if (fieldInstance && !fieldInstance.isDisposed()) {
			const {editingLanguageId} = this.props;
			const {fieldName} = fieldInstance;
			const {store} = this.context;

			store.emit(
				'fieldEdited',
				{
					editingLanguageId,
					propertyName: fieldName,
					propertyValue: value
				}
			);
		}
	}

	_handleSidebarFieldBlurred() {
		const {store} = this.context;

		store.emit('sidebarFieldBlurred');
	}

	_isOutsideModal(node) {
		return !dom.closest(node, '.close-modal');
	}

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
}

export default compose(
	withActionableFields,
	withEditablePageHeader,
	withMoveableFields,
	withMultiplePages,
	withResizeableColumns
)(FormBuilder);