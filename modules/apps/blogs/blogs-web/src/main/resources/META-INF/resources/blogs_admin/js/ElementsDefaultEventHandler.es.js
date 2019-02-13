import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class ElementsDefaultEventHandler extends PortletBase {
	handleItemClicked(event) {
		var itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	delete(itemData) {
		let message = 'are-you-sure-you-want-to-delete-this';

		if (this.trashEnabled) {
			message = 'are-you-sure-you-want-to-move-this-to-the-recycle-bin';
		}

		if (confirm(Liferay.Language.get(message))) {
			this._send(itemData.deleteURL);
		}
	}

	permissions(itemData) {
		Liferay.Util.openWindow(
			{
				dialog: {
					destroyOnHide: true,
					modal: true
				},
				dialogIframe: {
					bodyCssClass: 'dialog-with-footer'
				},
				title: Liferay.Language.get('permissions'),
				uri: itemData.permissionsURL
			}
		);
	}

	publishToLive(itemData) {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-publish-the-selected-blogs-entry'))) {
			this._send(itemData.publishEntryURL);
		}
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

ElementsDefaultEventHandler.STATE = {
	namespace: Config.string(),
	trashEnabled: Config.bool()
};

export default ElementsDefaultEventHandler;