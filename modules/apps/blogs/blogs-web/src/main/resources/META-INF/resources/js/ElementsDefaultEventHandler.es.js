import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';
import {Config} from 'metal-state';

class ElementsDefaultEventHandler extends DefaultEventHandler {
	delete(itemData) {
		let message = Liferay.Language.get('are-you-sure-you-want-to-delete-this');

		if (this.trashEnabled) {
			message = Liferay.Language.get('are-you-sure-you-want-to-move-this-to-the-recycle-bin');
		}

		if (confirm(message)) {
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
	trashEnabled: Config.bool()
};

export default ElementsDefaultEventHandler;