import {DefaultEventHandler} from 'frontend-js-web';

class OrphanPortletsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteOrphanPortlets() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

export default OrphanPortletsManagementToolbarDefaultEventHandler;
