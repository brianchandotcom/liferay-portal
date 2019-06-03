import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class SiteDropdownDefaultEventHandler extends DefaultEventHandler {
	activateSite(itemData) {
		this._send(itemData.activateSiteURL);
	}

	deactivateSite(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-deactivate-this')
			)
		) {
			this._send(itemData.deactivateSiteURL);
		}
	}

	deleteSite(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
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

export default SiteDropdownDefaultEventHandler;
