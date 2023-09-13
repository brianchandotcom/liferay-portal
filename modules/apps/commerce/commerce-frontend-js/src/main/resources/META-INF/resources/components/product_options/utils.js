/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {isObject} from 'frontend-js-web/index';

const getInitialProductOptionValue = (productOption) => {
	if (!productOption.productOptionValues) {
		return;
	}

	const selectedProductOptionValue = productOption.productOptionValues.find(
		(productOptionValue) => productOptionValue.preselected === true
	);

	if (selectedProductOptionValue) {
		return selectedProductOptionValue;
	}

	if (!productOption.skuContributor) {
		return;
	}

	return productOption.productOptionValues[0];
};

const getName = (
	key,
	name,
	selectedProductOptionValueKey,
	skuId,
	relativePriceFormatted
) => {
	if (isObject(name)) {
		name = name[Liferay.ThemeDisplay.getLanguageId()];

		if (!name) {
			name = name[Liferay.ThemeDisplay.getDefaultLanguageId()];
		}
	}

	if (
		selectedProductOptionValueKey === key ||
		!skuId ||
		!relativePriceFormatted
	) {
		return name;
	}

	return name + relativePriceFormatted;
};

const getProductOptionName = (name) => {
	if (isObject(name)) {
		name = name[Liferay.ThemeDisplay.getLanguageId()];

		if (!name) {
			name = name[Liferay.ThemeDisplay.getDefaultLanguageId()];
		}
	}

	return name;
};

const getSkuOptionsErrors = (hasErrors, productOption, skuOptionsAtomState) =>
	hasErrors
		? [
				...skuOptionsAtomState.errors,
				{
					hasErrors: true,
					id: productOption.id,
				},
		  ]
		: skuOptionsAtomState.errors.filter(
				(error) => error.id !== productOption.id
		  );

const initialSkuOptionsAtomState = {
	errors: [],
	namespace: '',
	skuOptions: [],
	updating: false,
};

const isRequired = (forceRequired, isAdmin, productOption) =>
	isAdmin
		? forceRequired
		: forceRequired ||
		  productOption.required ||
		  productOption.skuContributor;

export {
	getInitialProductOptionValue,
	getName,
	getProductOptionName,
	getSkuOptionsErrors,
	initialSkuOptionsAtomState,
	isRequired,
};
