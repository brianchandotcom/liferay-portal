/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {ItemFinder} from 'commerce-frontend-js';

export default function ({
	dataSetId,
	namespace,
	rootPortletId,
	typeSettingsInputId,
	workflowAction,
}) {
	function selectItem(product) {
		const typeSettingsInput = document.getElementById(
			`${namespace}${typeSettingsInputId}`
		);

		if (typeSettingsInput.value) {
			const filteredValue = typeSettingsInput.value
				.split(',')
				.filter((value) => value !== '' + product.productId);

			filteredValue.push(product.productId);

			typeSettingsInput.value = filteredValue.toString();
		}
		else {
			typeSettingsInput.value = product.productId;
		}

		document.getElementById(`${namespace}workflowAction`).value =
			workflowAction;

		return submitForm(document.getElementById(`${namespace}fm`));
	}

	ItemFinder('itemFinder', 'item-finder-root', {
		apiUrl: '/o/headless-commerce-admin-catalog/v1.0/products?nestedFields=catalog',
		getSelectedItems: () => Promise.resolve([]),
		inputPlaceholder: Liferay.Language.get('find-a-product'),
		itemCreation: false,
		itemSelectedMessage: Liferay.Language.get('product-selected'),
		itemsKey: 'id',
		linkedDataSetsId: [dataSetId],
		onItemSelected: selectItem,
		pageSize: 10,
		panelHeaderLabel: Liferay.Language.get('add-products'),
		portletId: rootPortletId,
		schema: [
			{
				fieldName: ['name', 'LANG'],
			},
			{
				fieldName: 'productId',
			},
			{
				fieldName: ['catalog', 'name'],
			},
		],
		titleLabel: Liferay.Language.get('add-existing-product'),
	});
}
