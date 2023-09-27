/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import ClayForm, {ClayInput} from '@clayui/form';
import Autocomplete from 'commerce-frontend-js/components/autocomplete/Autocomplete';
import React from 'react';

export function LinkedToCatalogProductFormGroup({updateValue, value}) {
	const initialValue = value ? {...value} : null;

	if (initialValue && initialValue.skuId) {
		initialValue.productId = initialValue.skuId;
	}

	return (
		<ClayForm.Group>
			<label htmlFor="linkedProductInput">
				{Liferay.Language.get('sku')}
			</label>

			<Autocomplete
				apiUrl="/o/headless-commerce-admin-catalog/v1.0/skus"
				infiniteScrollMode={true}
				initialLabel={initialValue?.sku || ''}
				initialValue={initialValue?.productId || ''}
				inputName="skuInput"
				itemsKey="productId"
				itemsLabel="sku"
				onValueUpdated={(productId, skuProduct) => {
					if (
						(!productId && initialValue) ||
						(!initialValue && productId) ||
						(initialValue && productId !== initialValue.productId)
					) {
						updateValue(skuProduct);
					}
				}}
				pageSize={10}
			/>
		</ClayForm.Group>
	);
}

export function LinkedToDiagramFormGroup({updateValue, value}) {
	const initialValue = value ? {...value} : null;

	if (initialValue && !initialValue.name) {
		initialValue.name = initialValue.productName;
	}

	return (
		<ClayForm.Group>
			<label htmlFor="linkedProductInput">
				{Liferay.Language.get('diagram')}
			</label>

			<Autocomplete
				apiUrl="/o/headless-commerce-admin-catalog/v1.0/products?filter=(productType eq 'diagram')"
				infiniteScrollMode={true}
				initialLabel={
					initialValue
						? initialValue.name[
								Liferay.ThemeDisplay.getLanguageId()
						  ]
						: ''
				}
				initialValue={initialValue?.productId || ''}
				inputName="productNameInput"
				itemsKey="productId"
				itemsLabel={['name', 'LANG']}
				onValueUpdated={(productId, product) => {
					if (
						(!productId && initialValue) ||
						(!initialValue && productId) ||
						(initialValue && productId !== initialValue.productId)
					) {
						updateValue(product);
					}
				}}
				pageSize={10}
			/>
		</ClayForm.Group>
	);
}

export function LinkedToExternalProductFormGroup({updateValue, value}) {
	return (
		<ClayForm.Group>
			<label htmlFor="linkedProductInput">
				{Liferay.Language.get('label')}
			</label>

			<ClayInput
				id="linkedProductInput"
				onChange={(event) => updateValue({sku: event.target.value})}
				value={value?.sku || ''}
			/>
		</ClayForm.Group>
	);
}
