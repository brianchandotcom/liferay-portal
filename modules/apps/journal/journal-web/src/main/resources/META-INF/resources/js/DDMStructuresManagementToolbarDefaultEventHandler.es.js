import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class DDMStructuresManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteDDMStructures() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

export default DDMStructuresManagementToolbarDefaultEventHandler;
