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

class AssetCategoriesManagementToolbarDefaultEventHandler extends DefaultEventHandler {
	deleteSelectedCategories() {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			submitForm(this.one('#fm'));
		}
	}

	selectCategory(itemData) {
		const namespace = this.namespace;

		AUI().use('liferay-item-selector-dialog', A => {
			const itemSelectorDialog = new A.LiferayItemSelectorDialog({
				eventName: this.ns('selectCategory'),
				on: {
					selectedItemChange(event) {
						const selectedItem = event.newVal;

						const category = selectedItem
							? selectedItem[Object.keys(selectedItem)[0]]
							: null;

						if (category) {
							location.href = Liferay.Util.addParams(
								namespace + 'categoryId=' + category.categoryId,
								itemData.viewCategoriesURL
							);
						}
					}
				},
				strings: {
					add: Liferay.Language.get('select'),
					cancel: Liferay.Language.get('cancel')
				},
				title: Liferay.Language.get('select-category'),
				url: itemData.categoriesSelectorURL
			});

			itemSelectorDialog.open();
		});
	}
}

export default AssetCategoriesManagementToolbarDefaultEventHandler;
