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
	ItemSelectorDialog,
	openSimpleInputModal
} from 'frontend-js-web';
import {Config} from 'metal-state';

class LayoutPageTemplateEntryDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteLayoutPageTemplateEntry(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteLayoutPageTemplateEntryURL);
		}
	}

	deleteLayoutPageTemplateEntryPreview(itemData) {
		this._send(itemData.deleteLayoutPageTemplateEntryPreviewURL);
	}

	permissionsLayoutPageTemplateEntry(itemData) {
		Liferay.Util.openWindow({
			dialog: {
				destroyOnHide: true,
				modal: true
			},
			dialogIframe: {
				bodyCssClass: 'dialog-with-footer'
			},
			title: Liferay.Language.get('permissions'),
			uri: itemData.permissionsLayoutPageTemplateEntryURL
		});
	}

	renameLayoutPageTemplateEntry(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-display-page-template'),
			formSubmitURL: itemData.updateLayoutPageTemplateEntryURL,
			idFieldName: itemData.idFieldName,
			idFieldValue: itemData.idFieldValue,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: itemData.layoutPageTemplateEntryName,
			namespace: this.namespace,
			spritemap: this.spritemap
		});
	}

	updateLayoutPageTemplateEntryPreview(itemData) {
		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: Liferay.Language.get('ok'),
			eventName: this.ns('changePreview'),
			title: Liferay.Language.get('page-template-thumbnail'),
			url: itemData.itemSelectorURL
		});

		itemSelectorDialog.on('selectedItemChange', event => {
			const selectedItem = event.selectedItem;

			if (selectedItem) {
				const itemValue = JSON.parse(selectedItem.value);

				this.one('#layoutPageTemplateEntryId').value =
					itemData.layoutPageTemplateEntryId;
				this.one('#fileEntryId').value = itemValue.fileEntryId;

				submitForm(this.one('#layoutPageTemplateEntryPreviewFm'));
			}
		});

		itemSelectorDialog.open();
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

LayoutPageTemplateEntryDropdownDefaultEventHandler.STATE = {
	spritemap: Config.string()
};

export default LayoutPageTemplateEntryDropdownDefaultEventHandler;
