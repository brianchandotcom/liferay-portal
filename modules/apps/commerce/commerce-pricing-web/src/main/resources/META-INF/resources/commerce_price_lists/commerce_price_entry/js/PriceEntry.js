/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
