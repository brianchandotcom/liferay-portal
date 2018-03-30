import {ClayManagementToolbar} from 'clay-management-toolbar';
import {Config} from 'metal-state';
import {EventHandler} from 'metal-events';

/**
 * Metal ManagementToolbar component.
 * @review
 */

class ManagementToolbar extends ClayManagementToolbar {

	/**
	 * @inheritDoc
	 * @review
	 */

	attached() {
		super.attached();

		Liferay.componentReady(this.searchContainerId).then(
			searchContainer => {
				this._eventHandler = [
					searchContainer.on('rowToggled', this._handleSearchContainerRowToggled, this)
				];

				this._searchContainer = searchContainer;
			}
		);
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	disposed() {
		super.disposed();

		if (this._eventHandler) {
			this._eventHandler.forEach(
				eventHandler => {
					eventHandler.detach();
				}
			);
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	_handleDeselectAllClicked(event) {
		super._handleDeselectAllClicked(event);

		if (this._searchContainer) {
			this._searchContainer.select.toggleAllRows(false);
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	_handleSelectAllClicked(event) {
		super._handleSelectAllClicked(event);

		if (this._searchContainer) {
			this._searchContainer.select.toggleAllRows(true);
		}
	}

	/**
	 * @inheritDoc
	 * @review
	 */

	_handleSelectPageCheckboxChanged(event) {
		super._handleSelectPageCheckboxChanged(event);

		if (this._searchContainer) {
			let checkboxStatus = event.target.checked;

			if (checkboxStatus) {
				this._searchContainer.select.toggleAllRows(true);
			}
			else {
				this._searchContainer.select.toggleAllRows(false);
			}
		}
	}

	/**
	 * Updates management toolbar selectedItems count on searchContainer element
	 * toggled.
	 * @param {object} event The row toggle event from the SearchContainer instance
	 * @private
	 * @review
	 */

	_handleSearchContainerRowToggled(event) {
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
	 * @default undefined
	 * @instance
	 * @memberof ManagementToolbar
	 * @review
	 * @type {?string|undefined}
	 */

	searchContainerId: Config.string()
};

export {ManagementToolbar};
export default ManagementToolbar;