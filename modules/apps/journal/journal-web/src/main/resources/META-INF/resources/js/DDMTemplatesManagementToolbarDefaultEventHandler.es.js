import {DefaultEventHandler} from 'frontend-js-web';

class DDMTemplatesManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteDDMTemplates() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

export default DDMTemplatesManagementToolbarDefaultEventHandler;
