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
	openSelectionModal,
	openSimpleInputModal,
} from 'frontend-js-web';
import {Config} from 'metal-state';

class StyleBookEntryDropdownDefaultEventHandler extends DefaultEventHandler {
	copyStyleBookEntry(itemData) {
		this._send(itemData.copyStyleBookEntryURL);
	}

	deleteStyleBookEntry(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteStyleBookEntryURL);
		}
	}

	deleteStyleBookEntryPreview(itemData) {
		this._send(itemData.deleteStyleBookEntryPreviewURL);
	}

	discardDraftStyleBookEntry(itemData) {
		this._send(itemData.discardDraftStyleBookEntryURL);
	}

	markAsDefaultStyleBookEntry(itemData) {
		if (itemData.message !== '') {
			if (confirm(Liferay.Language.get(itemData.message))) {
				this._send(itemData.markAsDefaultStyleBookEntryURL);
			}
		}
		else {
			this._send(itemData.markAsDefaultStyleBookEntryURL);
		}
	}

	renameStyleBookEntry(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-style-book'),
			formSubmitURL: itemData.updateStyleBookEntryURL,
			idFieldName: 'id',
			idFieldValue: itemData.styleBookEntryId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: itemData.styleBookEntryName,
			namespace: this.namespace,
			spritemap: this.spritemap,
		});
	}

	unmarkAsDefaultStyleBookEntry(itemData) {
		if (confirm(Liferay.Language.get('unmark-default-confirmation'))) {
			this._send(itemData.unmarkAsDefaultStyleBookEntryURL);
		}
	}

	updateStyleBookEntryPreview(itemData) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					this.one('#styleBookEntryId').value =
						itemData.styleBookEntryId;
					this.one('#fileEntryId').value = itemValue.fileEntryId;

					submitForm(this.one('#styleBookEntryPreviewFm'));
				}
			},
			selectEventName: this.ns('changePreview'),
			title: Liferay.Language.get('style-book-thumbnail'),
			url: itemData.itemSelectorURL,
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

StyleBookEntryDropdownDefaultEventHandler.STATE = {
	spritemap: Config.string(),
};

export default StyleBookEntryDropdownDefaultEventHandler;
