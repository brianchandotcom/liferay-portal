import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class FragmentEntryUsageManagementToolbarDefaultEventHandler extends PortletBase {
	callAction(event) {
		var itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	propagate() {
		submitForm(this.one('#fm'));
	}

	handleActionItemClicked(event) {
		this.callAction(event);
	}
}

FragmentEntryUsageManagementToolbarDefaultEventHandler.STATE = {
	namespace: Config.string()
};

export default FragmentEntryUsageManagementToolbarDefaultEventHandler;