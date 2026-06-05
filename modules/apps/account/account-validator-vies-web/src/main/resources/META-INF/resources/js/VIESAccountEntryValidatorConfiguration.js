/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

export default function ({namespace}) {
	function saveValidatedCountries() {
		setTimeout(() => {
			const form = document[`${namespace}fm`];

			if (!form) {
				return;
			}

			const currentCountriesElement = Liferay.Util.getFormElement(
				form,
				'currentCountries'
			);

			if (currentCountriesElement) {
				Liferay.Util.setFormValues(form, {
					countries: Liferay.Util.getSelectedOptionValues(
						currentCountriesElement
					),
				});
			}
		});
	}

	Liferay.after(
		[
			'form:registered',
			'inputmoveboxes:moveItem',
			'inputmoveboxes:orderItem',
		],
		saveValidatedCountries
	);
}
