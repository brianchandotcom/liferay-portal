/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 */

import ClayForm, {ClayInput} from '@clayui/form';
import Autocomplete from 'commerce-frontend-js/components/autocomplete/Autocomplete';
import React from 'react';

export function LinkedToCatalogProductFormGroup({updateValue, value}) {
	return (
		<ClayForm.Group>
			<label htmlFor="linkedProductInput">
				{Liferay.Language.get('sku')}
			</label>

			<Autocomplete
				apiUrl="/o/headless-commerce-admin-catalog/v1.0/skus"
				infiniteScrollMode={true}
				initialLabel={value?.sku || ''}
				initialValue={value || ''}
				inputName="skuInput"
				itemsKey="id"
				itemsLabel="sku"
				onValueUpdated={(_sku, skuProduct) => {
					updateValue(skuProduct);
				}}
				pageSize={10}
			/>
		</ClayForm.Group>
	);
}

export function LinkedToDiagramFormGroup({updateValue, value}) {
	return (
		<ClayForm.Group>
			<label htmlFor="linkedProductInput">
				{Liferay.Language.get('diagram')}
			</label>

			<Autocomplete
				apiUrl="/o/headless-commerce-admin-catalog/v1.0/products?filter=(productType eq 'diagram')"
				infiniteScrollMode={true}
				initialLabel={
					value?.name
						? value.name[Liferay.ThemeDisplay.getLanguageId()]
						: ''
				}
				initialValue={value}
				inputName="productNameInput"
				itemsKey="productId"
				itemsLabel={['name', 'LANG']}
				onValueUpdated={(_productId, product) => {
					updateValue(product);
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
