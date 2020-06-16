/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {
	DefaultEventHandler,
	openModal,
	openSimpleInputModal,
} from 'frontend-js-web';
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
		openModal({
			title: Liferay.Language.get('permissions'),
			url: itemData.permissionsAssetEntryListURL,
		});
	}

	renameAssetListEntry(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-collection'),
			formSubmitURL: itemData.renameAssetListEntryURL,
			idFieldName: 'id',
			idFieldValue: itemData.assetListEntryId,
			mainFieldLabel: Liferay.Language.get('title'),
			mainFieldName: 'title',
			mainFieldPlaceholder: Liferay.Language.get('title'),
			mainFieldValue: itemData.assetListEntryTitle,
			namespace: this.namespace,
			spritemap: this.spritemap,
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

AssetEntryListDropdownDefaultEventHandler.STATE = {
	spritemap: Config.string(),
};

export default AssetEntryListDropdownDefaultEventHandler;
