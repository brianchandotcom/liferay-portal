import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class OrganizationDropdownDefaultEventHandler extends PortletBase {
	handleItemClicked(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteGroupOrganizations(itemData) {
		submitForm(document.hrefFm, itemData.deleteGroupUsersURL);
	}
}

OrganizationDropdownDefaultEventHandler.STATE = {
	namespace: Config.string()
};

export default OrganizationDropdownDefaultEventHandler;