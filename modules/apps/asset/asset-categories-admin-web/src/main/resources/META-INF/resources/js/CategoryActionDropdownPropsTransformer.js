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

import {openModal, openSelectionModal, openToast} from 'frontend-js-web';

const ACTIONS = {
	deleteCategory({deleteCategoryURL}) {
		if (
			confirm(
				Liferay.Language.get(
					'this-category-might-be-being-used-in-some-contents'
				)
			)
		) {
			submitForm(document.hrefFm, deleteCategoryURL);
		}
	},

	moveCategory(
		{categoryId, categoryTitle, moveCategoryURL},
		portletNamespace
	) {
		openSelectionModal({
			iframeBodyCssClass: '',
			multiple: true,
			onSelect: (selectedItems) => {
				if (!selectedItems) {
					return;
				}

				const item = Object.values(selectedItems).find(
					(item) => !item.unchecked
				);

				const parentCategoryId = item.categoryId || 0;
				const vocabularyId = item.vocabularyId || 0;

				if (categoryId === parentCategoryId) {
					openToast({
						message: Liferay.Util.sub(
							Liferay.Language.get(
								'unable-to-move-the-category-x-into-itself'
							),
							categoryTitle
						),
						type: 'danger',
					});

					return;
				}

				if (vocabularyId > 0 || parentCategoryId > 0) {
					const form = document.getElementById(
						`${portletNamespace}moveCategoryFm`
					);

					if (form) {
						Liferay.Util.setFormValues(form, {
							categoryId,
							parentCategoryId,
							vocabularyId,
						});

						submitForm(form);
					}
				}
			},
			selectEventName: `${portletNamespace}selectCategory`,
			title: Liferay.Util.sub(
				Liferay.Language.get('move-x'),
				categoryTitle
			),
			url: moveCategoryURL,
		});
	},

	permissionsCategory({permissionsCategoryURL}) {
		openModal({
			title: Liferay.Language.get('permissions'),
			url: permissionsCategoryURL,
		});
	},
};

export default function propsTransformer({items, portletNamespace, ...props}) {
	return {
		...props,
		items: items.map((item) => {
			return {
				...item,
				items: item.items?.map((child) => ({
					...child,
					onClick(event) {
						const action = child.data?.action;

						if (action) {
							event.preventDefault();

							ACTIONS[action](child.data, portletNamespace);
						}
					},
				})),
			};
		}),
	};
}
