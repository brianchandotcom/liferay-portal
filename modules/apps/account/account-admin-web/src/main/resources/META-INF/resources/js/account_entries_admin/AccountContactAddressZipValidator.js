/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({countryId, namespace}) {
	const country = document.getElementById(`${namespace}countryId`);

	if (country) {
		country.addEventListener('change', handleSelectChange);

		if (countryId > 0) {
			checkCountry(countryId);
		}
	}

	function checkCountry(countryId) {
		Liferay.Service(
			'/country/get-country',
			{
				countryId,
			},
			(response, error) => {
				if (error) {
					console.error(error);
				}
				else {
					updateZipRequired(response.zipRequired);
				}
			}
		);
	}

	function handleSelectChange(event) {
		const value = Number(event.currentTarget.value);

		if (value > 0) {
			checkCountry(value);
		}
		else {
			updateZipRequired(false);
		}
	}

	function updateZipRequired(required) {
		const zipRequiredWrapper = document.getElementById(
			`${namespace}zipRequiredWrapper`
		);
		const formValidator = Liferay.Form.get(`${namespace}fm`).formValidator;

		const rules = formValidator._getAttr('rules');

		if (required) {
			zipRequiredWrapper.removeAttribute('hidden');
		}
		else {
			zipRequiredWrapper.setAttribute('hidden', true);
		}

		rules.addressZip = {required};
	}
}
