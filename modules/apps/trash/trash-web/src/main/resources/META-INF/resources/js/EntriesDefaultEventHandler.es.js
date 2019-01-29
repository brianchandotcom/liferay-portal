import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class EntriesDefaultEventHandler extends PortletBase {
	handleItemClicked(event) {
		var itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	restoreEntry(itemData) {
		if (itemData.move) {
			Liferay.Util.navigate(itemData.restoreURL);
		}
		else {
			submitForm(this.one('#fm'), itemData.restoreURL);
		}
	}

	deleteEntry(itemData) {
		submitForm(this.one('#fm'), itemData.deleteURL);
	}

}

EntriesDefaultEventHandler.STATE = {
	namespace: Config.string()
};

export default EntriesDefaultEventHandler;