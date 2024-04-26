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

interface Tag {
	qaId: string;
	selectable: boolean;
	value: string;
}

export default function openTagSelectionModal({
	portletNamespace,
	redirectURL,
	selectTagURL,
	title,
}: {
	portletNamespace: string;
	redirectURL: string;
	selectTagURL: string;
	title: string;
}) {
	openSelectionModal({
		buttonAddLabel: Liferay.Language.get('select'),
		height: '70vh',
		iframeBodyCssClass: '',
		multiple: true,
		onSelect: (selectedItems: Tag[]) => {
			if (!selectedItems.length) {
				return;
			}

			const url = new URL(redirectURL);

			const resetCurParam = `_${url.searchParams.get('p_p_id')}_resetCur`;

			url.searchParams.set(resetCurParam, 'true');

			const assetTags = selectedItems.map((tag) => tag.value);

			let finalURL = url.href;

			assetTags.forEach((assetTag) => {
				const selectedValue = JSON.parse(assetTag);

				finalURL = addParams(
					`${portletNamespace}assetTagId=${selectedValue.tagName}`,
					finalURL
				);
			});

			navigate(finalURL);
		},
		selectEventName: `${portletNamespace}selectedAssetTag`,
		size: 'lg',
		title: title || Liferay.Language.get('filter-by-tags'),
		url: selectTagURL,
	});
}
