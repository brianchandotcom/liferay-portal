import OpenSimpleInputModal from 'frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es';
import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';
import {Config} from 'metal-state';

class ManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addSiteNavigationMenu(itemData) {
		OpenSimpleInputModal({
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
