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

function handleOverrideDiscount({namespace}) {
	const discountLevels = document.getElementById(
		`${namespace}discountLevels`
	);
	const overrideDiscountInput = document.getElementById(
		`${namespace}overrideDiscount`
	);

	if (discountLevels && overrideDiscountInput) {
		const inputs = [
			document.getElementById(`${namespace}discountLevel1`),
			document.getElementById(`${namespace}discountLevel2`),
			document.getElementById(`${namespace}discountLevel3`),
			document.getElementById(`${namespace}discountLevel4`),
		];

		overrideDiscountInput.addEventListener('change', (event) => {
			if (event.target.checked) {
				discountLevels.classList.remove('hide');
			}
			else {
				discountLevels.classList.add('hide');
			}

			inputs.forEach((input) => {
				if (input) {
					if (event.target.checked) {
						input.disabled = false;
						input.classList.remove('disabled');
					}
					else {
						input.disabled = true;
						input.classList.add('disabled');
					}
				}
			});
		});
	}
}

function handlePriceOnApplication({namespace}) {
	const priceOnApplicationInput = document.getElementById(
		`${namespace}priceOnApplication`
	);
	const priceSettingsElement = document.getElementById(
		`${namespace}price-entry-price-settings`
	);

	if (priceOnApplicationInput && priceSettingsElement) {
		priceOnApplicationInput.addEventListener('change', (event) => {
			let elements = [priceSettingsElement];

			elements = elements.concat(
				Array.from(priceSettingsElement.getElementsByTagName('button'))
			);
			elements = elements.concat(
				Array.from(priceSettingsElement.getElementsByTagName('input'))
			);
			elements = elements.concat(
				Array.from(priceSettingsElement.getElementsByTagName('label'))
			);

			elements.forEach((element) => {
				if (element && !element.classList.contains('base-price')) {
					if (event.target.checked) {
						element.disabled = true;
						element.classList.add('disabled');
					}
					else {
						element.disabled = false;
						element.classList.remove('disabled');
					}
				}
			});
		});
	}
}

export default function (context) {
	handleOverrideDiscount(context);

	handlePriceOnApplication(context);
}
