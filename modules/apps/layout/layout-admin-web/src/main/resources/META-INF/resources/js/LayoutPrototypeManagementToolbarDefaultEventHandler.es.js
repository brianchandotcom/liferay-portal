import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class LayoutPrototypeManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedLayoutPrototypes() {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			submitForm(this.one('#fm'));
		}
	}
}

export default LayoutPrototypeManagementToolbarDefaultEventHandler;