import {DefaultEventHandler, openSimpleInputModal} from 'frontend-js-web';
import {Config} from 'metal-state';

class ManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addSiteNavigationMenu(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('add-menu'),
			formSubmitURL: itemData.addSiteNavigationMenuURL,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			namespace: this.namespace,
			spritemap: this.spritemap
		});
	}

	deleteSelectedSiteNavigationMenus() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}
}

ManagementToolbarDefaultEventHandler.STATE = {
	spritemap: Config.string()
};

export default ManagementToolbarDefaultEventHandler;
