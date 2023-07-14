/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import renderAutocomplete from 'commerce-frontend-js/components/autocomplete/entry';

function handleCPInstanceOptions({namespace}) {
	const form = document.getElementById(`${namespace}fm`);

	function saveInstance() {
		const cpInstanceOptionsInput = document.getElementById(
			`${namespace}cpInstanceOptions`
		);
		const optionsContainer = document.getElementById(
			`${namespace}optionsContainer`
		);

		if (!optionsContainer) {
			return submitForm(form);
		}

		const skuContributorInputs = optionsContainer.querySelectorAll(
			'[data-sku-contributor=true]'
		);

		if (skuContributorInputs) {
			cpInstanceOptionsInput.value = JSON.stringify(
				Array.from(skuContributorInputs).map((skuContributorInput) => {
					const name =
						skuContributorInput.name ||
						skuContributorInput.querySelector('input:checked').name;

					const value = skuContributorInput.value
						? skuContributorInput.value.split('[$SEPARATOR$]')[1]
						: skuContributorInput
								.querySelector('input:checked')
								.value.split('[$SEPARATOR$]')[1];

					return {key: name, value: [value]};
				})
			);
		}

		submitForm(form);
	}

	form.addEventListener('submit', saveInstance);
}

function handlePriceOnApplication({namespace}) {
	const priceOnApplicationInput = document.getElementById(
		`${namespace}priceOnApplication`
	);

	if (priceOnApplicationInput) {
		const inputs = [
			document.getElementById(`${namespace}price`),
			document.getElementById(`${namespace}cost`),
			document.getElementById(`${namespace}promoPrice`),
		];

		priceOnApplicationInput.addEventListener('change', (event) => {
			inputs.forEach((input) => {
				if (input) {
					if (event.target.checked) {
						input.disabled = true;
						input.classList.add('disabled');
					}
					else {
						input.disabled = false;
						input.classList.remove('disabled');
					}
				}
			});
		});
	}
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

export default function (context) {
	handleCPInstanceOptions(context);

	handlePriceOnApplication(context);

	handlePublish(context);

	handleReplacements(context);
}
