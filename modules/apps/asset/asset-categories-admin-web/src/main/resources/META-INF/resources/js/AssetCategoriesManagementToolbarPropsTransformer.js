/**
 * SPDX-FileCopyrightText: (c) 2023 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {addParams, navigate, openSelectionModal} from 'frontend-js-web';

import openDeleteCategoryModal from './openDeleteCategoryModal';

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
		openDeleteCategoryModal({
			multiple: true,
			onDelete: () => {
				const form = document.getElementById(`${portletNamespace}fm`);

				if (form) {
					submitForm(form);
				}
			},
		});
	};

	const selectCategory = (itemData) => {
		openSelectionModal({
			height: '70vh',
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
			size: 'md',
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
		},
		onFilterDropdownItemClick(event, {item}) {
			if (item?.data?.action === 'selectCategory') {
				selectCategory(item?.data);
			}
		},
	};
}
