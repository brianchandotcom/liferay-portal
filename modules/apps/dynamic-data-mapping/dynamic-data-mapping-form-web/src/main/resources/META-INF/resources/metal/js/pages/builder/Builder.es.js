import Component from 'metal-jsx';
import FormRenderer, {FormSupport} from '../../components/Form/index.es';
import Sidebar from '../../components/Sidebar/index.es';

/**
 * Builder.
 * @extends Component
 */

class Builder extends Component {

	/**
	 * Continues the propagation of event.
	 * @param {!Object} indexAllocateField
	 * @private
	 */

	_handleFieldClicked(indexAllocateField) {
		this._handleSidebarOpened(
			{
				mode: 'edit'
			}
		);
		this.emit('fieldClicked', indexAllocateField);
	}

	_handlePageAdded(pages) {
		this._handleSidebarOpened(
			{
				mode: 'add'
			}
		);
		this.emit('pagesUpdated', pages);
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */

	_handleFieldAdded(event) {
		this.emit('fieldAdded', event);
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Object} event
	 * @private
	 */

	_handleFieldEdited({fieldInstance, property, value}) {
		const {focusedField, pages} = this.props;
		const {columnIndex, pageIndex, rowIndex} = focusedField;
		const column = FormSupport.getColumn(
			pages,
			pageIndex,
			rowIndex,
			columnIndex
		);

		const settingsContext = column.fields[0].settingsContext;

		const properties = {};

		properties.settingsContext = {
			...settingsContext,
			pages: settingsContext.pages.map(
				page => (
					{
						...page,
						rows: page.rows.map(
							row => (
								{
									...row,
									columns: row.columns.map(
										column => (
											{
												...column,
												fields: column.fields.map(
													field => {
														if (field.fieldName === fieldInstance.fieldName) {
															field = {
																...field,
																value
															};
														}
														if (field.fieldName === 'name' && fieldInstance.fieldName === 'label') {
															properties.fieldName = value;
															field = {
																...field,
																value
															};
														}
														if (field.fieldName === 'type') {
															field = {
																...field,
																value: focusedField.type
															};
														}
														delete field.settingsContext;

														properties[field.fieldName] = field.value;

														return field;
													}
												)
											}
										)
									)
								}
							)
						)
					}
				)
			)
		};

		properties[fieldInstance.fieldName] = value;

		this.emit('fieldEdited', properties);
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
		this.emit(
			'duplicateField',
			indexes
		);
	}

	/**
	 * Continues the propagation of event.
	 * @param {String} mode
	 * @private
	 */

	_handleSidebarOpened({mode}) {
		const sidebar = this.refs.sidebar;

		sidebar.props.focusedField = {};

		sidebar._setMode(mode);
		sidebar.show();
	}

	_handleActivePageUpdated({mode}) {
		const Sidebar = this.refs.sidebar;

		Sidebar._dragAndDrop.disposeInternal();
		Sidebar._startDrag();

		if (mode) {
			this._handleSidebarOpened({mode});
		}
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
			fieldTypes,
			focusedField,
			mode,
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
			pagesUpdated: this._handlePagesUpdated.bind(this),
			sidebarOpened: this._handleSidebarOpened.bind(this)
		};

		const sidebarEvents = {
			fieldAdded: this._handleFieldAdded.bind(this),
			fieldEdited: this._handleFieldEdited.bind(this)
		};

		return (
			<div>
				<div class="container">
					<div class="sheet">
						<FormRenderer
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
					mode={mode}
					pages={pages}
					ref="sidebar"
					spritemap={spritemap}
				/>
			</div>
		);
	}
}

export default Builder;
export {Builder};