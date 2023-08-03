/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {openSelectionModal} from 'frontend-js-web';

export default function propsTransformer({
	additionalProps,
	portletNamespace,
	...props
}) {
	return {
		...props,
		onClick() {
			const {selectAssetCategoryPropsTransformerURL} = additionalProps;

			openSelectionModal({
				onSelect: (selectedItems) => {
					const [selectedItem] = Object.values(selectedItems);

					const assetCategoryIds = document.getElementById(
						`${portletNamespace}assetCategoryIds`
					);

					const assetCategory = document.getElementById(
						`${portletNamespace}assetCategory`
					);

					assetCategoryIds.value = selectedItem.classPK;
					assetCategory.value = selectedItem.title;

					const removeAssetCategoryButton = document.getElementById(
						`${portletNamespace}removeAssetCategoryButton`
					);

					removeAssetCategoryButton.disabled = false;
				},
				selectEventName: `${portletNamespace}selectAssetCategory`,
				title: Liferay.Language.get('select-category'),
				url: `${selectAssetCategoryPropsTransformerURL}`,
			});
		},
	};
}
