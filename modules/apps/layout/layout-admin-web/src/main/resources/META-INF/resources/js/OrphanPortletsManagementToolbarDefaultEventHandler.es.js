import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class OrphanPortletsManagementToolbarDefaultEventHandler extends PortletBase {
	callAction(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteOrphanPortlets() {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			submitForm(this.one('#fm'));
		}
	}

	handleActionItemClicked(event) {
		this.callAction(event);
	}
}

OrphanPortletsManagementToolbarDefaultEventHandler.STATE = {
	namespace: Config.string()
};

export default OrphanPortletsManagementToolbarDefaultEventHandler;