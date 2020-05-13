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
	openSimpleInputModal,
} from 'frontend-js-web';

class FragmentCompositionDropdownDefaultEventHandler extends DefaultEventHandler {
	deleteFragmentComposition(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			this._send(itemData.deleteFragmentCompositionURL);
		}
	}

	deleteFragmentCompositionPreview(itemData) {
		this._send(itemData.deleteFragmentCompositionPreviewURL);
	}

	moveFragmentComposition(itemData) {
		this._selectFragmentCollection(
			itemData.fragmentCompositionId,
			itemData.selectFragmentCollectionURL,
			itemData.moveFragmentCompositionURL
		);
	}

	renameFragmentComposition(itemData) {
		openSimpleInputModal({
			dialogTitle: Liferay.Language.get('rename-fragment'),
			formSubmitURL: itemData.renameFragmentCompositionURL,
			idFieldName: 'id',
			idFieldValue: itemData.fragmentCompositionId,
			mainFieldLabel: Liferay.Language.get('name'),
			mainFieldName: 'name',
			mainFieldPlaceholder: Liferay.Language.get('name'),
			mainFieldValue: itemData.fragmentCompositionName,
			namespace: this.namespace,
			spritemap: this.spritemap,
		});
	}

	updateFragmentCompositionPreview(itemData) {
		const itemSelectorDialog = new ItemSelectorDialog({
			eventName: this.ns('changePreview'),
			singleSelect: true,
			title: Liferay.Language.get('fragment-thumbnail'),
			url: itemData.itemSelectorURL,
		});

		itemSelectorDialog.open();

		itemSelectorDialog.on('selectedItemChange', (event) => {
			const selectedItem = event.selectedItem;

			if (selectedItem) {
				const itemValue = JSON.parse(selectedItem.value);

				this.one('#fragmentCompositionId').value =
					itemData.fragmentCompositionId;
				this.one('#fragmentCompositionFileEntryId').value =
					itemValue.fileEntryId;

				submitForm(this.one('#fragmentCompositionPreviewFm'));
			}
		});
	}

	_selectFragmentCollection(
		fragmentCompositionId,
		selectFragmentCollectionURL,
		targetFragmentCompositionURL
	) {
		Liferay.Util.openModal({
			id: this.ns('selectFragmentCollection'),
			onSelect: (selectedItem) => {
				if (selectedItem) {
					const form = this.one('#fragmentEntryFm');

					form.querySelector(
						`#${this.namespace}fragmentCollectionId`
					).value = selectedItem.id;

					form.querySelector(
						`#${this.namespace}fragmentCompositionId`
					).value = fragmentCompositionId;

					submitForm(form, targetFragmentCompositionURL);
				}
			},
			selectEventName: this.ns('selectFragmentCollection'),
			title: Liferay.Language.get('select-collection'),
			url: selectFragmentCollectionURL,
		});
	}

	_send(url) {
		submitForm(document.hrefFm, url);
	}
}

export default FragmentCompositionDropdownDefaultEventHandler;
