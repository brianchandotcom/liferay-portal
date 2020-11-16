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
	openSelectionModal,
	openSimpleInputModal,
} from 'frontend-js-web';
import {Config} from 'metal-state';

class DisplayPageDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteDisplayPage(itemData) {
		if (confirm(itemData.deleteDisplayPageMessage)) {
			this._send(itemData.deleteDisplayPageURL);
		}
	}

	deleteLayoutPageTemplateEntryPreview(itemData) {
		this._send(itemData.deleteLayoutPageTemplateEntryPreviewURL);
	}

	discardDraft(itemData) {
		if (
			confirm(
				Liferay.Language.get(
					'are-you-sure-you-want-to-discard-current-draft-and-apply-latest-published-changes'
				)
			)
		) {
			this._send(itemData.discardDraftURL);
		}
	}

	markAsDefaultDisplayPage(itemData) {
		if (itemData.message !== '') {
			if (confirm(Liferay.Language.get(itemData.message))) {
				this._send(itemData.markAsDefaultDisplayPageURL);
			}
		}
		else {
			this._send(itemData.markAsDefaultDisplayPageURL);
		}
	}

	permissionsDisplayPage(itemData) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: itemData.permissionsDisplayPageURL,
		});
	}

	renameDisplayPage(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-display-page-template'),
			formSubmitURL: itemData.updateDisplayPageURL,
			idFieldName: 'layoutPageTemplateEntryId',
			idFieldValue: itemData.layoutPageTemplateEntryId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: itemData.layoutPageTemplateEntryName,
			namespace: this.namespace,
			spritemap: this.spritemap,
		});
	}

	unmarkAsDefaultDisplayPage(itemData) {
		if (confirm(Liferay.Language.get('unmark-default-confirmation'))) {
			this._send(itemData.unmarkAsDefaultDisplayPageURL);
		}
	}

	updateLayoutPageTemplateEntryPreview(itemData) {
		openSelectionModal({
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const itemValue = JSON.parse(selectedItem.value);

					this.one('#layoutPageTemplateEntryId').value =
						itemData.layoutPageTemplateEntryId;
					this.one('#fileEntryId').value = itemValue.fileEntryId;

					submitForm(this.one('#layoutPageTemplateEntryPreviewFm'));
				}
			},
			selectEventName: this.ns('changePreview'),
			title: Liferay.Language.get('page-template-thumbnail'),
			url: itemData.itemSelectorURL,
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

DisplayPageDropdownDefaultEventHandler.STATE = {
	spritemap: Config.string(),
};

export default DisplayPageDropdownDefaultEventHandler;
