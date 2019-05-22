import OpenSimpleInputModal from 'frontend-js-web/liferay/modal/commands/OpenSimpleInputModal.es';
import DefaultEventHandler from 'frontend-js-web/liferay/DefaultEventHandler.es';
import {Config} from 'metal-state';

class AssetEntryListDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteAssetListEntry(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteAssetListEntryURL);
		}
	}

	permissionsAssetEntryList(itemData) {
		this._openWindow(
			Liferay.Language.get('permissions'),
			itemData.permissionsAssetEntryListURL
		);
	}

	renameAssetListEntry(itemData) {
		OpenSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-content-set'),
			formSubmitURL: itemData.renameAssetListEntryURL,
			idFieldName: 'id',
			idFieldValue: itemData.assetListEntryId,
			mainFieldLabel: Liferay.Language.get('title'),
			mainFieldName: 'title',
			mainFieldPlaceholder: Liferay.Language.get('title'),
			mainFieldValue: itemData.assetListEntryTitle,
			namespace: this.namespace,
			spritemap: this.spritemap
		});
	}

	_openWindow(label, url) {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				modal: true
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer'
			},
			title: Liferay.Language.get(label),
			uri: url
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

AssetEntryListDropdownDefaultEventHandler.STATE = {
	spritemap: Config.string()
};

export default AssetEntryListDropdownDefaultEventHandler;
