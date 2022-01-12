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

import renderAutocomplete from 'commerce-frontend-js/components/autocomplete/entry';

function handleReplacements({initialLabel, initialValue, namespace}) {
	const discontinuedInput = document.getElementById(
		`${namespace}discontinued`
	);

	const discontinuedDateInput = document.getElementById(
		`${namespace}discontinuedDate`
	);

	const replacementAutocompleteWrapper = document.getElementById(
		`${namespace}replacementAutocompleteWrapper`
	);

	discontinuedInput.addEventListener('change', (event) => {
		if (event.target.checked) {
			discontinuedDateInput.disabled = false;
			discontinuedDateInput.classList.remove('disabled');
			replacementAutocompleteWrapper.classList.remove('d-none');
		}
		else {
			discontinuedDateInput.disabled = true;
			discontinuedDateInput.classList.add('disabled');
			replacementAutocompleteWrapper.classList.add('d-none');
		}
	});

	renderAutocomplete('autocomplete', 'autocomplete-root', {
		apiUrl: '/o/headless-commerce-admin-catalog/v1.0/skus',
		initialLabel,
		initialValue,
		inputId: 'replacementId',
		inputName: `${namespace}replacementCPInstanceId`,
		itemsKey: 'id',
		itemsLabel: 'sku',
		showDeleteButton: true,
	});
}

function handlePublish({WORKFLOW_ACTION_PUBLISH, namespace}) {
	const publishButton = document.getElementById(`${namespace}publishButton`);

	publishButton.addEventListener('click', () => {
		const workflowActionInput = document.getElementById(
			`${namespace}workflowAction`
		);

		if (workflowActionInput) {
			workflowActionInput.value = WORKFLOW_ACTION_PUBLISH;
		}
	});
}

function handleDDMForm({cpDefinitionId, namespace}) {
	const form = document.getElementById(`${namespace}fm`);
	let fieldValues = [];

	Liferay.componentReady(`ProductOptions${cpDefinitionId}`).then(
		(ddmForm) => {
			ddmForm.unstable_onEvent((event) => {
				if (event.type === 'field_change') {
					const key = event.payload.fieldInstance.fieldName;

					const updatedItem = {
						key,
						value: event.payload.value,
					};

					const itemFound = fieldValues.some(
						(item) => item.key === key
					);

					if (itemFound) {
						fieldValues = fieldValues.reduce(
							(acc, item) =>
								acc.concat(
									item.key === key ? updatedItem : item
								),
							[]
						);
					}
					else {
						fieldValues.push(updatedItem);
					}

					const ddmFormValuesInput = document.getElementById(
						`${namespace}ddmFormValues`
					);

					ddmFormValuesInput.value = JSON.stringify(fieldValues);
				}
			});
		}
	);

	function saveInstance() {
		const ddmForm = Liferay.component(
			`ProductOptions${cpDefinitionId}DDMForm`
		);
		const ddmFormValuesInput = document.getElementById(
			`${namespace}ddmFormValues`
		);

		if (ddmForm) {
			const fields = ddmForm.getImmediateFields();

			const fieldValues = fields.map((field) => {
				const fieldValue = {
					key: field.get('fieldName'),
				};

				const value = field.getValue();

				fieldValue.value = Array.isArray(value) ? value : [value];

				return fieldValue;
			});

			ddmFormValuesInput.value = JSON.stringify(fieldValues);
		}

		submitForm(form);
	}

	form.addEventListener('submit', saveInstance);
}

export default function (context) {
	handleReplacements(context);

	handlePublish(context);

	handleDDMForm(context);
}
