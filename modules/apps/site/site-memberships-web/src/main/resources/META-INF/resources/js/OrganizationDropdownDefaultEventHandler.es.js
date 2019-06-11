import {DefaultEventHandler} from 'frontend-js-web';

class OrganizationDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteGroupOrganizations(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, itemData.deleteGroupOrganizationsURL);
		}
	}
}

export default OrganizationDropdownDefaultEventHandler;
