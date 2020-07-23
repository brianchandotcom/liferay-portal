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
	addParams,
	navigate,
	openModal,
} from 'frontend-js-web';

class ContentDashboardManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	selectAuthor(itemData) {
		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: Liferay.Language.get('select'),
			eventName: this.ns('selectedAuthorItem'),
			title: itemData.dialogTitle,
			url: itemData.selectAuthorURL,
		});

		itemSelectorDialog.on('selectedItemChange', (event) => {
			const selectedItem = event.selectedItem;

			if (selectedItem) {
				var redirectURL = itemData.redirectURL;

				selectedItem.forEach((item) => {
					redirectURL = addParams(
						this.namespace + 'authorIds=' + item.id,
						redirectURL
					);
				});

				navigate(redirectURL);
			}
		});

		itemSelectorDialog.open();
	}

	selectCategory(itemData) {
		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: Liferay.Language.get('select'),
			eventName: this.ns('selectedCategory'),
			title: itemData.dialogTitle,
			url: itemData.selectCategoryURL,
		});

		itemSelectorDialog.on('selectedItemChange', (event) => {
			const selectedItem = event.selectedItem;

			if (selectedItem) {
				const categories = Object.keys(selectedItem).filter(
					(key) => !selectedItem[key].unchecked
				);

				var redirectURL = itemData.redirectURL;

				categories.forEach((category) => {
					redirectURL = addParams(
						this.namespace +
							'categoryId=' +
							selectedItem[category].categoryId,
						redirectURL
					);
				});

				navigate(redirectURL);
			}
		});

		itemSelectorDialog.open();
	}

	selectContentDashboardItemType(itemData) {
		const itemSelectorDialog = new ItemSelectorDialog({
			buttonAddLabel: Liferay.Language.get('select'),
			eventName: this.ns('selectedContentDashboardItemTypeItem'),
			title: itemData.dialogTitle,
			url: itemData.selectContentDashboardItemTypeURL,
		});

		itemSelectorDialog.on('selectedItemChange', (event) => {
			const selectedItem = event.selectedItem;

			if (selectedItem) {
				var redirectURL = itemData.redirectURL;

				selectedItem.forEach((item) => {
					redirectURL = addParams(
						this.namespace +
							'contentDashboardItemTypePayload=' +
							JSON.stringify(item),
						redirectURL
					);
				});

				navigate(redirectURL);
			}
		});

		itemSelectorDialog.open();
	}

	selectScope(itemData) {
		openModal({
			id: this.ns('selectedScopeIdItem'),
			onSelect: (selectedItem) => {
				navigate(
					addParams(
						this.namespace + 'scopeId=' + selectedItem.groupid,
						itemData.redirectURL
					)
				);
			},
			selectEventName: this.ns('selectedScopeIdItem'),
			title: itemData.dialogTitle,
			url: itemData.selectScopeURL,
		});
	}
}

export default ContentDashboardManagementToolbarDefaultEventHandler;
