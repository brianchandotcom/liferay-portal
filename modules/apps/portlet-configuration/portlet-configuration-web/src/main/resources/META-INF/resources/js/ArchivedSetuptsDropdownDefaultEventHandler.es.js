import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class ArchivedSetuptsDropdownDefaultEventHandler extends PortletBase {
	handleItemClicked(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	deleteArchivedSetups(itemData) {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			this._send(itemData.deleteArchivedSetupsURL);
		}
	}

	restoreArchivedSetup(itemData) {
		this._send(itemData.restoreArchivedSetupURL);
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

ArchivedSetuptsDropdownDefaultEventHandler.STATE = {
	namespace: Config.string()
};

export default ArchivedSetuptsDropdownDefaultEventHandler;