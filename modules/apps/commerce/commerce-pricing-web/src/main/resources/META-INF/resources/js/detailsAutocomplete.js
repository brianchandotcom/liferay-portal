/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Autocomplete} from 'commerce-frontend-js';

export default function ({apiUrl, initialLabel, initialValue, namespace}) {
	Autocomplete('autocomplete', 'autocomplete-root', {
		apiUrl,
		initialLabel,
		initialValue,
		inputId: 'parentCommercePriceListId',
		inputName: `${namespace}parentCommercePriceListId`,
		itemsKey: 'id',
		itemsLabel: 'name',
		onValueUpdated(value, priceListData) {
			if (value) {
				window.document.querySelector(
					`#${namespace}parentCommercePriceListId`
				).value = priceListData.id;
			}
			else {
				window.document.querySelector(
					`#${namespace}parentCommercePriceListId`
				).value = 0;
			}
		},
	});
}
