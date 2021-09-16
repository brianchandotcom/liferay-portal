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

import {addParams, navigate, openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({portletNamespace, ...otherProps}) {
	const setCategoryDisplayPageTemplate = (
		setCategoryDisplayPageTemplateURL
	) => {
		const nodes = document.querySelectorAll(
			`input[name="${portletNamespace}rowIds"]`
		);

		nodes.forEach((node) => {
			if (node.checked) {
				setCategoryDisplayPageTemplateURL = addParams(
					`${portletNamespace}categoryIds=${node.value}`,
					setCategoryDisplayPageTemplateURL
				);
			}
		});

		navigate(setCategoryDisplayPageTemplateURL);
	};

	const deleteSelectedCategories = () => {
		if (
			confirm(
				Liferay.Language.get('are-you-sure-you-want-to-delete-this')
			)
		) {
			const form = document.getElementById(`${portletNamespace}fm`);

			if (form) {
				submitForm(form);
			}
		}
	};

	const selectCategory = (itemData) => {
		openSelectionModal({
			iframeBodyCssClass: '',
			onSelect(selectedItem) {
				const category = selectedItem
					? selectedItem[Object.keys(selectedItem)[0]]
					: null;

				if (category) {
					location.href = addParams(
						`${portletNamespace}categoryId=${category.categoryId}`,
						itemData?.viewCategoriesURL
					);
				}
			},
			selectEventName: `${portletNamespace}selectCategory`,
			title: Liferay.Language.get('select-category'),
			url: itemData?.categoriesSelectorURL,
		});
	};

	return {
		...otherProps,
		onActionButtonClick(event, {item}) {
			const data = item?.data;

			const action = data?.action;

			if (action === 'setCategoryDisplayPageTemplate') {
				setCategoryDisplayPageTemplate(
					data.setCategoryDisplayPageTemplateURL
				);
			}
			if (action === 'deleteSelectedCategories') {
				deleteSelectedCategories();
			}
			else if (action === 'selectCategory') {
				selectCategory(data);
			}
		},
	};
}
