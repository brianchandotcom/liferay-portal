/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

import {sub} from 'frontend-js-web';

function fillField(fieldElement) {
	if (fieldElement.jsonData && fieldElement instanceof HTMLElement) {
		const textElement = fieldElement.querySelector('span:nth-child(2)');

		if (textElement) {
			textElement.innerText = Liferay.Util.escape(fieldElement.jsonData);

			fieldElement.classList.remove('hide');
		}
	}
}

export function updateProductFields(data) {
	const cpInstance = data.cpInstance;

	const availabilityEstimateContainer =
		document.querySelector(
			'[data-text-cp-instance-availability-estimate]'
		) || {};
	const gtinContainer =
		document.querySelector('[data-text-cp-instance-gtin]') || {};
	const mpnContainer =
		document.querySelector(
			'[data-text-cp-instance-manufacturer-part-number]'
		) || {};
	const skuContainer =
		document.querySelector('[data-text-cp-instance-sku]') || {};
	const stockQuantityContainer =
		document.querySelector('[data-text-cp-instance-stock-quantity]') || {};

	availabilityEstimateContainer.jsonData = cpInstance.availabilityEstimate;
	gtinContainer.jsonData = cpInstance.gtin;
	mpnContainer.jsonData = cpInstance.manufacturerPartNumber;
	skuContainer.jsonData = cpInstance.sku;

	[
		availabilityEstimateContainer,
		gtinContainer,
		mpnContainer,
		skuContainer,
	].forEach(fillField);

	if (stockQuantityContainer) {
		stockQuantityContainer.innerHTML = cpInstance.availability.stockQuantity
			? sub(
					Liferay.Language.get('x-in-stock'),
					cpInstance.availability.stockQuantity
			  )
			: '';
	}

	const subscriptionInfoContainer = document.querySelector(
		'[data-text-cp-instance-subscription-info]'
	);

	if (subscriptionInfoContainer) {
		subscriptionInfoContainer.innerHTML = cpInstance.subscriptionInfo || '';
	}

	const deliverySubscriptionInfoContainer = document.querySelector(
		'[data-text-cp-instance-delivery-subscription-info]'
	);

	if (deliverySubscriptionInfoContainer) {
		deliverySubscriptionInfoContainer.innerHTML =
			cpInstance.deliverySubscriptionInfo || '';
	}

	const incomingQuantityLabelContainer = document.querySelector(
		'[data-text-cp-instance-incoming-quantity-label]'
	);

	if (incomingQuantityLabelContainer) {
		incomingQuantityLabelContainer.innerHTML =
			cpInstance.incomingQuantityLabel || '';
	}
}
