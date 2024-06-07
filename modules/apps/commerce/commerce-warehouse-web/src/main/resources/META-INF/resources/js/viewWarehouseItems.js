/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({ADD, CMD, UPDATE, namespace}) {
	const form = document.getElementById(`${namespace}fm`);

	function handleSaveBtnClick(event) {
		const {
			commerceInventoryWarehouseId,
			commerceInventoryWarehouseItemId,
			commerceInventoryWarehouseItemUom,
			index,
			mvccVersion,
		} = event.target.closest('tr').dataset;

		form.querySelector(`#${namespace}${CMD}`).value =
			commerceInventoryWarehouseItemId > 0 ? UPDATE : ADD;

		form.querySelector(`#${namespace}commerceInventoryWarehouseId`).value =
			commerceInventoryWarehouseId;
		form.querySelector(
			`#${namespace}commerceInventoryWarehouseItemId`
		).value = commerceInventoryWarehouseItemId;
		form.querySelector(`#${namespace}unitOfMeasureKey`).value =
			commerceInventoryWarehouseItemUom;

		const quantityInput = document.querySelector(
			`#${namespace}commerceInventoryWarehouseItemQuantity${index}`
		);

		form.querySelector(`#${namespace}quantity`).value = quantityInput.value;
		form.querySelector(`#${namespace}mvccVersion`).value = mvccVersion;

		submitForm(form);
	}

	document.querySelectorAll('.warehouse-save-btn').forEach((saveBtn) => {
		saveBtn.addEventListener('click', handleSaveBtnClick);
	});

	const quantityPrefix = `${namespace}commerceInventoryWarehouseItemQuantity`;
	const enterKeyCode = 13;
	const quantityInputElements = document.querySelectorAll(
		`input[id^=${quantityPrefix}]`
	);

	quantityInputElements.forEach((quantityInputElement) => {
		quantityInputElement.addEventListener('keypress', (event) => {
			if (event.keyCode === enterKeyCode) {
				event.preventDefault();

				quantityInputElement
					.closest('tr')
					.querySelector('.warehouse-save-btn')
					.click();
			}
		});
	});
}
