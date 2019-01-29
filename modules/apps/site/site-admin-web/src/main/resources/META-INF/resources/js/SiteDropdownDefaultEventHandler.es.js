import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class SiteDropdownDefaultEventHandler extends PortletBase {
	handleItemClicked(event) {
		const itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	activateSite(itemData) {
		this._send(itemData.activateSiteURL);
	}

	deactivateSite(itemData) {
		this._send(itemData.deactivateSiteURL);
	}

	deleteSite(itemData) {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			this._send(itemData.deleteSiteURL);
		}
	}

	leaveSite(itemData) {
		this._send(itemData.leaveSiteURL);
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

SiteDropdownDefaultEventHandler.STATE = {
	namespace: Config.string()
};

export default SiteDropdownDefaultEventHandler;