import {DefaultEventHandler} from 'frontend-js-web';

class LayoutPrototypeManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedLayoutPrototypes() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

export default LayoutPrototypeManagementToolbarDefaultEventHandler;
