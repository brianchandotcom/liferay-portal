/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {Autocomplete} from 'commerce-frontend-js';

export default function main({initialLabel, initialValue, namespace, skuURL}) {
	const deleteButton = document.getElementById('remove-sku-button');
	const quantityInput = document.getElementById(`${namespace}quantity`);

	if (!quantityInput.value || quantityInput.value === '0') {
		quantityInput.value = 1;
	}

	Autocomplete('autocomplete', 'autocomplete-root', {
		apiUrl: '/o/headless-commerce-admin-catalog/v1.0/unit-of-measure-skus?filter=(hasChildCPDefinitions eq false)',
		initialLabel,
		initialValue,
		inputId: 'skuId',
		inputName: `${namespace}cpInstanceId`,
		itemsKey: 'unitOfMeasureSkuId',
		itemsLabel: 'sku',
		onValueUpdated(value) {
			if (value) {
				quantityInput.disabled = false;
				deleteButton.disabled = false;
			}
			else {
				quantityInput.disabled = true;
				deleteButton.disabled = true;
			}
		},
		secondaryItemsLabel: 'unitOfMeasureKey',
	});

	Liferay.provide(window, `${namespace}removeSku`, () => {
		const url = new URL(skuURL);

		if (Liferay.SPA) {
			Liferay.SPA.app.navigate(url.toString());
		}
		else {
			window.location.href = url.toString();
		}
	});
}
