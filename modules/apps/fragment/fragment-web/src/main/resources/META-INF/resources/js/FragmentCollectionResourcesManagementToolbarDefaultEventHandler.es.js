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

import {DefaultEventHandler} from 'frontend-js-web';

class FragmentCollectionResourcesManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	addFragmentCollectionResource(itemData) {
		AUI().use('liferay-item-selector-dialog', A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: this.ns('uploadFragmentCollectionResource'),
				on: {
					selectedItemChange: function(event) {
						const selectedItem = event.newVal;

						if (selectedItem) {
							const itemValue = JSON.parse(selectedItem.value);

							this.one('#fileEntryId').value =
								itemValue.fileEntryId;

							submitForm(
								this.one('#fragmentCollectionResourceFm')
							);
						}
					}.bind(this)
				},
				'strings.add': Liferay.Language.get('ok'),
				title: Liferay.Language.get(
					'upload-fragment-collection-resource'
				),
				url: itemData.itemSelectorURL
			});

			itemSelectorDialog.open();
		});
	}

	deleteSelectedFragmentCollectionResources(itemData) {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(
				this.one('#fm'),
				itemData.deleteFragmentCollectionResourcesURL
			);
		}
	}
}

export default FragmentCollectionResourcesManagementToolbarDefaultEventHandler;
