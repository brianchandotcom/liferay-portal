/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getFormElement, setFormValues} from 'frontend-js-web';

export default function updateRanks(defaultLanguageId, namespace) {
	const updateRanksLanguageTemps = (lang) => {
		if (lang !== defaultLanguageId) {
			let value = null;

			const defaultRanksInput = document.getElementById(
				`${namespace}ranks_${defaultLanguageId}`
			);

			if (defaultRanksInput) {
				value = defaultRanksInput.value;
			}

			const ranksInput = document.getElementById(
				`${namespace}ranks_${lang}`
			);

			if (ranksInput) {
				value = ranksInput.value;
			}

			setFormValues(form, {
				ranks_temp: value,
			});
		}
	};

	const form = document.getElementById(`${namespace}fm`);

	const ranksChanged = window[`${namespace}ranksChanged`];
	const ranksLastLanguageId = window[`${namespace}ranksLastLanguageId`];

	if (form) {
		const ranksTempTextarea = getFormElement(form, 'ranks_temp');

		if (ranksTempTextarea) {
			if (ranksChanged && ranksLastLanguageId !== defaultLanguageId) {
				setFormValues(form, {
					ranksLastLanguageId: ranksTempTextarea.value,
				});

				submitForm(form);

				ranksChanged.value = false;
			}

			const selLanguageInput = getFormElement(form, 'ranksLanguageId');

			if (selLanguageInput) {
				const selLanguageId = selLanguageInput.value;

				if (selLanguageId) {
					updateRanksLanguageTemps(selLanguageId);

					ranksTempTextarea.classList.remove('hide');

					ranksLastLanguageId.value = selLanguageId;
				}
				else {
					ranksTempTextarea.classList.add('hide');
				}
			}
		}
	}
}
