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

class FragmentEntryDropdownDefaultEventHandler extends DefaultEventHandler {
	copyFragmentEntry(itemData) {
		this.one('#fragmentCollectionId').value = itemData.fragmentCollectionId;
		this.one('#fragmentEntryIds').value = itemData.fragmentEntryId;

		submitForm(this.one('#fragmentEntryFm'), itemData.copyFragmentEntryURL);
	}

	copyToContributedFragmentEntry(itemData) {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					destroyOnHide: true,
					modal: true
				},
				eventName: this.ns('selectFragmentCollection'),
				id: this.ns('selectFragmentCollection'),
				title: Liferay.Language.get('select-collection'),
				uri: itemData.selectFragmentCollectionURL
			},
			selectedItem => {
				if (selectedItem) {
					this.one('#fragmentCollectionId').value = selectedItem.id;
					this.one('#fragmentEntryKeys').value =
						itemData.fragmentEntryKey;

					submitForm(
						this.one('#fragmentEntryFm'),
						itemData.copyContributedFragmentEntryURL
					);
				}
			}
		);
	}

	copyToFragmentEntry(itemData) {
		this._selectFragmentCollection(
			itemData.fragmentEntryId,
			itemData.selectFragmentCollectionURL,
			itemData.copyFragmentEntryURL
		);
	}

	deleteFragmentEntry(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteFragmentEntryURL);
		}
	}

	deleteFragmentEntryPreview(itemData) {
		this._send(itemData.deleteFragmentEntryPreviewURL);
	}

	moveFragmentEntry(itemData) {
		this._selectFragmentCollection(
			itemData.fragmentEntryId,
			itemData.selectFragmentCollectionURL,
			itemData.moveFragmentEntryURL
		);
	}

	renameFragmentEntry(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-fragment'),
			formSubmitURL: itemData.updateFragmentEntryURL,
			idFieldName: 'id',
			idFieldValue: itemData.fragmentEntryId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: itemData.fragmentEntryName,
			namespace: this.namespace,
			spritemap: this.spritemap
		});
	}

	updateFragmentEntryPreview(itemData) {
		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: Liferay.Language.get('ok'),
			eventName: this.ns('changePreview'),
			title: Liferay.Language.get('fragment-thumbnail'),
			url: itemData.itemSelectorURL
		});

		itemSelectorDialog.open();

		itemSelectorDialog.on('selectedItemChange', event => {
			const selectedItem = event.selectedItem;

			if (selectedItem) {
				const itemValue = JSON.parse(selectedItem.value);

				this.one('#fragmentEntryId').value = itemData.fragmentEntryId;
				this.one('#fileEntryId').value = itemValue.fileEntryId;

				submitForm(this.one('#fragmentEntryPreviewFm'));
			}
		});
	}

	_selectFragmentCollection(
		fragmentEntryId,
		selectFragmentCollectionURL,
		targetFragmentEntryURL
	) {
		Liferay.Util.selectEntity(
			{
				dialog: {
					constrain: true,
					destroyOnHide: true,
					modal: true
				},
				eventName: this.ns('selectFragmentCollection'),
				id: this.ns('selectFragmentCollection'),
				title: Liferay.Language.get('select-collection'),
				uri: selectFragmentCollectionURL
			},
			selectedItem => {
				if (selectedItem) {
					this.one('#fragmentCollectionId').value = selectedItem.id;
					this.one('#fragmentEntryIds').value = fragmentEntryId;

					submitForm(
						this.one('#fragmentEntryFm'),
						targetFragmentEntryURL
					);
				}
			}
		);
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

FragmentEntryDropdownDefaultEventHandler.STATE = {
	copyFragmentEntryURL: Config.string(),
	fragmentCollectionId: Config.string(),
	moveFragmentEntryURL: Config.string(),
	spritemap: Config.string()
};

export default FragmentEntryDropdownDefaultEventHandler;
