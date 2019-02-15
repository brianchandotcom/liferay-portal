import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class DDMTemplatesManagementToolbarDefaultEventHandler extends PortletBase {
	callAction(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action]();
		}
	}

	deleteDDMTemplates() {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			submitForm(this.one('#fm'));
		}
	}

	handleActionItemClicked(event) {
		this.callAction(event);
	}
}

DDMTemplatesManagementToolbarDefaultEventHandler.STATE = {
	namespace: Config.string()
};

export default DDMTemplatesManagementToolbarDefaultEventHandler;