import PortletBase from 'frontend-js-web/liferay/PortletBase.es';
import {Config} from 'metal-state';

class ElementsDefaultEventHandler extends PortletBase {
	handleItemClicked(event) {
		var itemData = event.data.item.data;

		if (itemData && itemData.action && this[itemData.action]) {
			this[itemData.action](itemData);
		}
	}

	copyArticle(itemData) {
		this._send(itemData.copyArticleURL);
	}

	delete(itemData) {
		if (this.trashEnabled || confirm(Liferay.Language.get('are-you-sure-you-want-to-delete-this'))) {
			this._send(itemData.deleteFolderURL);
		}
	}

	expireArticles(itemData) {
		this._send(itemData.expireURL);
	}

	permissions(itemData) {
		Liferay.Util.openWindow(
			{
				dialog: {
					destroyOnHide: true,
					modal: true
				},
				title: Liferay.Language.get('permissions'),
				uri: itemData.permissionsURL
			}
		);
	}

	preview(itemData) {
		Liferay.fire(
			'previewArticle',
			{
				title: itemData.title,
				uri: itemData.previewURL
			}
		);
	}

	publishToLive(itemData) {
		if (confirm(Liferay.Language.get('are-you-sure-you-want-to-publish-the-selected-web-content'))) {
			this._send(itemData.publishArticleURL);
		}
	}

	subscribeArticle(itemData) {
		this._send(itemData.subscribeArticleURL);
	}

	unsubscribeArticle(itemData) {
		this._send(itemData.unsubscribeArticleURL);
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