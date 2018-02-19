import {actionItemsValidator, ClayManagementToolbar} from 'clay-management-toolbar';
import {Config} from 'metal-state';
import {EventHandler} from 'metal-events';

/**
 * Metal ManagementToolbar component.
 */
class ManagementToolbar extends ClayManagementToolbar {
	/**
	 * @inheritDoc
	 */
	created() {
		super.created(...arguments);
		
		Liferay.componentReady('myClayManagementToolbar').then(this.handleComponentReady_.bind(this));
	}

	/**
	 * @inheritDoc
	 */
	disposed() {
		super.disposed(...arguments);

		this.detachSearchContainerRegisterHandle_();
		this.eventHandler_.removeAllListeners();
	}

	/**
	 * Attaches the searchContainer events.
	 * @private
	 */
	attachEvents_() {
		this.eventHandler_ = new EventHandler();

		this.eventHandler_.add(
			this.searchContainer_.on('rowToggled', this.handleSearchContainerRowToggled_, this)
		);
	}

	/**
	 * Detaches the searchContainerRegistered event.
	 * @private
	 */
	detachSearchContainerRegisterHandle_() {
		if (this.searchContainerRegisterHandle_) {
			this.searchContainerRegisterHandle_.detach();
			this.searchContainerRegisterHandle_ = null;
		}
	}

	/**
	 * Attaches the searchContainer registered event.
	 * @private
	 */
	handleComponentReady_(component) {
		this.searchContainerRegisterHandle_ = Liferay.on('search-container:registered', this.handleSearchContainerRegistered_, this);
	}

	/**
	 * @inheritDoc
	 */
	handleDeselectAllClicked_(event) {
		super.handleDeselectAllClicked_(...arguments);
		
		if (this.searchContainer_) {
			this.searchContainer_.select.toggleAllRows(false);
		}
	}

	/**
	 * @inheritDoc
	 */
	handleSelectAllClicked_(event) {
		super.handleSelectAllClicked_(...arguments);
		
		if (this.searchContainer_) {
			this.searchContainer_.select.toggleAllRows(true);
		}
	}

	/**
	 * @inheritDoc
	 */
	handleSelectPageCheckboxChanged_(event) {
		super.handleSelectPageCheckboxChanged_(...arguments);

		if (this.searchContainer_) {
			let checkboxStatus = event.target.checked;

			if (checkboxStatus) {
				this.searchContainer_.select.toggleAllRows(true);
			} else {
				this.searchContainer_.select.toggleAllRows(false);
			}
		}
	}

	/**
	 * This method is called when specified searchContainer is registered.
	 * @private
	 */
	handleSearchContainerRegistered_(event) {
		var searchContainer = event.searchContainer;

		if (searchContainer.get('id') === this.searchContainerId) {
			this.searchContainer_ = searchContainer;

			this.detachSearchContainerRegisterHandle_();

			this.attachEvents_();
		}
	}

	/**
	 * Updates management toolbar selectedItems count on searchContainer element
	 * toggled.
	 * @private
	 */
	handleSearchContainerRowToggled_(event) {
		var elements = event.elements;

		this.selectedItems = elements.allSelectedElements.filter(':enabled').size();
	}
}

/**
 * State definition.
 * @static
 * @type {!Object}
 */
ManagementToolbar.STATE = {
	/**
	 * Id to get a instance of the searchContainer.
	 * @instance
	 * @memberof ManagementToolbar
	 * @type {?string|undefined}
	 * @default undefined
	 */
	searchContainerId: Config.string(),
};

export {ManagementToolbar};
export default ManagementToolbar;
