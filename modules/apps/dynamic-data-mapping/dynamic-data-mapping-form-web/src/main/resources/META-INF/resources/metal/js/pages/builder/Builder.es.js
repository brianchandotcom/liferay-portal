import {Config} from 'metal-state';
import {focusedFieldStructure} from '../../util/config.es';
import FormSupport from '../../components/Form/FormSupport.es';
import {PagesVisitor} from '../../util/visitors.es';
import Component from 'metal-jsx';
import FormRenderer from '../../components/Form/index.es';
import Sidebar from '../../components/Sidebar/index.es';

/**
 * Builder.
 * @extends Component
 */

class Builder extends Component {

	static PROPS = {

		/**
		 * @default
		 * @instance
		 * @memberof FormRenderer
		 * @type {?number}
		 */

		activePage: Config.number().value(0),

		/**
		 * @default {}
		 * @instance
		 * @memberof Sidebar
		 * @type {?object}
		 */

		focusedField: focusedFieldStructure.value({})
	};

	/**
	 * Continues the propagation of event.
	 * @param {!Object} indexAllocateField
	 * @private
	 */

	_handleFieldClicked(indexAllocateField) {
		this.emit('fieldClicked', indexAllocateField);
	}

	_handlePageAdded(pages) {
		const {sidebar} = this.refs;

		this.emit('pageAdded', pages);

		sidebar.open();
	}

	rendered() {
		const {sidebar} = this.refs;

		sidebar.refreshDragAndDrop();
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */

	_handleFieldAdded(event) {
		const {sidebar} = this.refs;
		const settingsContext = event.fieldType.settingsContext;
		const visitor = new PagesVisitor(settingsContext.pages);

		const newFieldName = FormSupport.generateFieldName(event.fieldType.name);

		this.emit(
			'fieldAdded',
			{
				...event,
				fieldType: {
					...event.fieldType,
					fieldName: newFieldName,
					settingsContext: {
						...settingsContext,
						pages: visitor.mapFields(
							field => {
								const {fieldName} = field;
								if (fieldName === 'name') {
									field = {
										...field,
										value: newFieldName,
										visible: true
									};
								}
								else if (fieldName === 'label') {
									field = {
										...field,
										type: 'text'
									};
								}
								else if (fieldName === 'type') {
									field = {
										...field,
										value: event.fieldType.name
									};
								}
								return field;
							}
						)
					},
					type: event.fieldType.name
				}
			}
		);

		sidebar.open();
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */

	_handleFieldBlurred() {
		this.emit('fieldBlurred');
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldEdited({fieldInstance, property, value}) {
		const {focusedField, namespace} = this.props;
		const {settingsContext} = focusedField;
		const {columnIndex, pageIndex, rowIndex} = focusedField;
		const properties = {columnIndex, pageIndex, rowIndex};

		properties[property || fieldInstance.fieldName] = value;

		const visitor = new PagesVisitor(settingsContext.pages);

		const translationManager = Liferay.component(`${namespace}translationManager`);

		properties.settingsContext = {
			...settingsContext,
			pages: visitor.mapFields(
				field => {
					if (field.fieldName === fieldInstance.fieldName) {
						field = {
							...field,
							value
						};
						if (field.localizable) {
							field.localizedValue = {
								...field.localizedValue,
								[translationManager.get('editingLocale')]: value
							};
						}
					}
					return field;
				}
			)
		};

		this.emit('fieldEdited', properties);
	}

	_handleActivePageUpdated(activePage) {
		this.emit('activePageUpdated', activePage);
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
	 * @param {!Object} indexes
	 * @private
	 */

	_handleDeleteButtonClicked(indexes) {
		this.emit('deleteField', indexes);
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Object}
	 * @private
	 */

	_handleDuplicateButtonClicked(indexes) {
		this.emit('duplicateField', indexes);
	}

	/**
	 * Continues the propagation of event.
	 * @param {Array} pages
	 * @private
	 */

	_handlePagesUpdated(pages) {
		this.emit('pagesUpdated', pages);
	}

	attached() {
		const translationManager = document.querySelector('.ddm-translation-manager');

		const formBasicInfo = document.querySelector('.ddm-form-basic-info');

		if (translationManager && formBasicInfo) {
			formBasicInfo.classList.remove('hide');
			translationManager.classList.remove('hide');
		}
	}

	/**
	 * @inheritDoc
	 */

	render() {
		const {
			activePage,
			fieldTypes,
			focusedField,
			pages,
			spritemap
		} = this.props;

		const FormRendererEvents = {
			activePageUpdated: this._handleActivePageUpdated.bind(this),
			deleteButtonClicked: this._handleDeleteButtonClicked.bind(this),
			duplicateButtonClicked: this._handleDuplicateButtonClicked.bind(this),
			fieldClicked: this._handleFieldClicked.bind(this),
			fieldMoved: this._handleFieldMoved.bind(this),
			pageAdded: this._handlePageAdded.bind(this),
			pagesUpdated: this._handlePagesUpdated.bind(this)
		};

		const sidebarEvents = {
			fieldAdded: this._handleFieldAdded.bind(this),
			fieldBlurred: this._handleFieldBlurred.bind(this),
			fieldEdited: this._handleFieldEdited.bind(this)
		};

		return (
			<div>
				<div class="container">
					<div class="sheet">
						<FormRenderer
							activePage={activePage}
							editable={true}
							events={FormRendererEvents}
							pages={pages}
							ref="FormRenderer"
							spritemap={spritemap}
						/>
					</div>
				</div>
				<Sidebar
					events={sidebarEvents}
					fieldTypes={fieldTypes}
					focusedField={focusedField}
					ref="sidebar"
					spritemap={spritemap}
				/>
			</div>
		);
	}
}

export default Builder;
export {Builder};