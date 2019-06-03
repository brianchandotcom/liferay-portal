import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

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
