import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';

class AssetPublisherDropdownDefaultEventHandler extends DefaultEventHandler {
	assetEntryAction(itemData) {
		this._openWindow(itemData.title, itemData.assetEntryActionURL);

	}

	editAssetEntry(itemData) {
		this._openWindow(itemData.title, itemData.editAssetEntryURL);
	}

	_openWindow(title, url) {
		Liferay.Util.openWindow(
			{
				dialog: {
					destroyOnHide: true,
					modal: true
				},
				dialogIframe: {
					bodyCssClass: 'dialog-with-footer'
				},
				title: title,
				uri: url
			}
		);
	}
}

export default AssetPublisherDropdownDefaultEventHandler;