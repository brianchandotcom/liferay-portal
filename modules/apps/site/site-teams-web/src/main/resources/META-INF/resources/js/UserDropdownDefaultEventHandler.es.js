import {DefaultEventHandler} from 'frontend-js-web';

class UserDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteTeamUsers(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(document.hrefFm, itemData.deleteTeamUsersURL);
		}
	}
}

export default UserDropdownDefaultEventHandler;
