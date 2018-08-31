import Component from 'metal-jsx';
import FormRenderer from '../../components/Form/index.es';
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

	_handleFieldAdd(event) {
		this.emit('fieldAdded', event);
	}

	/**
	 * Continues the propagation of data.
	 * @param {!Object} data
	 * @private
	 */

	_handleFieldEdited(data) {
		this.emit('fieldEdited', data);
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Object} data
	 * @private
	 */

	_handleFieldMoved(data) {
		this.emit('fieldMoved', data);
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
		const Sidebar = this.refs.sidebar;

		Sidebar._setMode(mode);
		Sidebar.show();
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
			fieldContext,
			fieldsList,
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
			fieldAdded: this._handleFieldAdd.bind(this),
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
					fieldContext={fieldContext}
					fieldLists={fieldsList}
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