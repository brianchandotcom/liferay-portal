/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

// @ts-ignore

import addParams from '../../util/add_params';

// @ts-ignore

import navigate from '../../util/navigate.es';

// @ts-ignore

import {openSelectionModal} from '../Modal';

interface Category {
	categoryId: string;
	className: string;
	classNameId: string;
	classPK: string;
	nodePath: string;
	title: string;
	value: string;
	vocabularyId: number;
}

export default function openCategorySelectionModal({
	portletNamespace,
	redirectURL,
	selectCategoryURL,
	title,
}: {
	portletNamespace: string;
	redirectURL: string;
	selectCategoryURL: string;
	title: string;
}) {
	openSelectionModal({
		buttonAddLabel: Liferay.Language.get('select'),
		height: '70vh',
		iframeBodyCssClass: '',
		multiple: true,
		onSelect: (selectedItems: Record<string, Category>) => {
			if (!Object.keys(selectedItems).length) {
				return;
			}

			let url = redirectURL;

			const assetCategories = Object.keys(selectedItems);

			assetCategories.forEach((assetCategory) => {
				url = addParams(
					`${portletNamespace}assetCategoryId=${selectedItems[assetCategory].categoryId}`,
					url
				);
			});

			navigate(url);
		},
		selectEventName: `${portletNamespace}selectedAssetCategory`,
		size: 'md',
		title: title || Liferay.Language.get('filter-by-categories'),
		url: selectCategoryURL,
	});
}
