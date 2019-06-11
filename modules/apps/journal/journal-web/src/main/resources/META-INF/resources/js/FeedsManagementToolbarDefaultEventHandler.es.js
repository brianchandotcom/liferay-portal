import {DefaultEventHandler} from 'frontend-js-web';

class FeedsManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteFeeds() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

export default FeedsManagementToolbarDefaultEventHandler;
