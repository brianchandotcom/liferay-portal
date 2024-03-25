/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Autocomplete} from 'commerce-frontend-js';

export function detailsAutocompleteBasePrice({
	apiUrl,
	initialLabel,
	initialValue,
	inputId,
	inputName,
	itemsKey,
	itemsLabel,
	namespace,
}) {
	Autocomplete('autocomplete', 'base-price-list-autocomplete-root', {
		apiUrl,
		initialLabel,
		initialValue,
		inputId,
		inputName,
		itemsKey,
		itemsLabel,
		onValueUpdated(value, priceListData) {
			if (value) {
				window.document.querySelector(
					`#${namespace}baseCommercePriceListId`
				).value = priceListData.id;
			}
			else {
				window.document.querySelector(
					`#${namespace}baseCommercePriceListId`
				).value = 0;
			}
		},
		required: true,
	});
}

export function detailsAutocompleteBasePromotion({
	apiUrl,
	initialLabel,
	initialValue,
	inputId,
	inputName,
	itemsKey,
	itemsLabel,
	namespace,
}) {
	Autocomplete('autocomplete', 'base-promotion-autocomplete-root', {
		apiUrl,
		initialLabel,
		initialValue,
		inputId,
		inputName,
		itemsKey,
		itemsLabel,
		onValueUpdated(value, priceListData) {
			if (value) {
				window.document.querySelector(
					`#${namespace}basePromotionCommercePriceListId`
				).value = priceListData.id;
			}
			else {
				window.document.querySelector(
					`#${namespace}basePromotionCommercePriceListId`
				).value = 0;
			}
		},
		required: true,
	});
}

export function detailsAutocompleteAccountLink({
	apiUrl,
	initialLabel,
	initialValue,
	inputId,
	inputName,
	itemsKey,
	itemsLabel,
}) {
	Autocomplete('autocomplete', 'link-account-entry-autocomplete-root', {
		apiUrl,
		initialLabel,
		initialValue,
		inputId,
		inputName,
		itemsKey,
		itemsLabel,
		required: false,
	});
}
