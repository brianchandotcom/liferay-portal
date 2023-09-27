/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	openModal,
	openSelectionModal,
	openToast,
	setFormValues,
	sub,
} from 'frontend-js-web';

import openDeleteCategoryModal from './openDeleteCategoryModal';

const ACTIONS = {
	deleteCategory({deleteCategoryURL}) {
		openDeleteCategoryModal({
			message: Liferay.Language.get(
				'this-category-might-be-being-used-in-some-contents'
			),
			onDelete: () => {
				submitForm(document.hrefFm, deleteCategoryURL);
			},
		});
	},

	moveCategory(
		{categoryId, categoryTitle, selectParentCategoryURL},
		portletNamespace
	) {
		openSelectionModal({
			height: '70vh',
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
				const vocabularyId = item.vocabularyId;

				if (
					categoryId === parentCategoryId ||
					item.ancestorIds?.includes(categoryId)
				) {
					openToast({
						message: sub(
							Liferay.Language.get(
								'unable-to-move-the-category-x-into-itself-or-one-of-its-children'
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
						setFormValues(form, {
							categoryId,
							parentCategoryId,
							vocabularyId,
						});

						submitForm(form);
					}
				}
			},
			selectEventName: `${portletNamespace}selectCategory`,
			size: 'md',
			title: sub(Liferay.Language.get('move-x'), categoryTitle),
			url: selectParentCategoryURL,
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
