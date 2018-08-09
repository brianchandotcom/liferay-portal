import Component from 'metal-jsx';
import LayoutRenderer from '../../components/Layout/index.es';
import Sidebar from '../../components/Sidebar/index.es';
import {ClayManagementToolbar} from 'clay-management-toolbar';

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
		const Sidebar = this.refs.sidebar;

		Sidebar.show();
		this.emit('fieldClicked', indexAllocateField);
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
		this.emit('fieldDeleted', indexes);
	}

	/**
	 * Continues the propagation of event.
	 * @param {!Event} event
	 * @private
	 */

	_handleCreationButtonClicked() {
		const Sidebar = this.refs.sidebar;

		Sidebar.props.mode = 'add';
		Sidebar.show();
	}

	/**
	 * @inheritDoc
	 */

	render() {
		const {
			context,
			fieldsList,
			focusedField,
			mode,
			spritemap
		} = this.props;

		const layoutRendererEvents = {
			deleteButtonClicked: this._handleDeleteButtonClicked.bind(this),
			fieldClicked: this._handleFieldClicked.bind(this),
			fieldMoved: this._handleFieldMoved.bind(this)
		};

		const sidebarEvents = {
			fieldAdded: this._handleFieldAdd.bind(this),
			fieldEdited: this._handleFieldEdited.bind(this)
		};

		const clayManagementToolbarEvents = {
			creationButtonClicked: this._handleCreationButtonClicked.bind(this)
		};

		return (
			<div>
				<ClayManagementToolbar
					events={clayManagementToolbarEvents}
					ref="managementToolbar"
					showSearch={false}
					spritemap={spritemap}
				/>
				<div class="container">
					<div class="sheet">
						<LayoutRenderer
							editable={true}
							events={layoutRendererEvents}
							pages={context}
							ref="layoutRenderer"
							spritemap={spritemap}
						/>
					</div>
				</div>
				<Sidebar
					context={context}
					events={sidebarEvents}
					fieldLists={fieldsList}
					focusedField={focusedField}
					mode={mode}
					ref="sidebar"
					spritemap={spritemap}
				/>
			</div>
		);
	}
}

export default Builder;
export {Builder};