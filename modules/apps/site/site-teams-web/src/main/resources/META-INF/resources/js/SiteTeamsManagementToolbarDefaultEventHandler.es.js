import {DefaultEventHandler} from 'frontend-js-web';

class SiteTeamsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedTeams() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

export default SiteTeamsManagementToolbarDefaultEventHandler;
