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

import {DefaultEventHandler, openSimpleInputModal} from 'frontend-js-web';
import {Config} from 'metal-state';

class MasterPageDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteMasterPage(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteMasterPageURL);
		}
	}

	deleteMasterPagePreview(itemData) {
		this._send(itemData.deleteMasterPagePreviewURL);
	}

	renameMasterPage(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-master-page'),
			formSubmitURL: itemData.updateMasterPageURL,
			idFieldName: 'layoutPageTemplateEntryId',
			idFieldValue: itemData.layoutPageTemplateEntryId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: itemData.layoutPageTemplateEntryName,
			namespace: this.namespace,
			spritemap: this.spritemap
		});
	}

	updateMasterPagePreview(itemData) {
		AUI().use('liferay-item-selector-dialog', A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: this.ns('changePreview'),
				on: {
					selectedItemChange: function(event) {
						const selectedItem = event.newVal;

						if (selectedItem) {
							const itemValue = JSON.parse(selectedItem.value);

							this.one('#layoutPageTemplateEntryId').value =
								itemData.layoutPageTemplateEntryId;
							this.one('#fileEntryId').value =
								itemValue.fileEntryId;

							submitForm(this.one('#masterPagePreviewFm'));
						}
					}.bind(this)
				},
				'strings.add': Liferay.Language.get('ok'),
				title: Liferay.Language.get('master-page-thumbnail'),
				url: itemData.itemSelectorURL
			});

			itemSelectorDialog.open();
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

MasterPageDropdownDefaultEventHandler.STATE = {
	spritemap: Config.string()
};

export default MasterPageDropdownDefaultEventHandler;
