/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getFormElement, setFormValues} from 'frontend-js-web';

export default function updatePriorities(defaultLanguageId, namespace) {
	const updatePrioritiesLanguageTemps = (lang) => {
		if (lang !== defaultLanguageId) {
			for (let i = 0; i < 10; i++) {
				const defaultImageInput = getFormElement(
					form,
					'priorityImage' + i + '_' + defaultLanguageId
				);
				const priorityImageInput = getFormElement(
					form,
					'priorityImage' + i + '_' + lang
				);

				let image = '';

				if (defaultImageInput && priorityImageInput) {
					const defaultImage = defaultImageInput.value;
					const priorityImage = priorityImageInput.value;

					image = priorityImage || defaultImage;
				}

				const defaultNameInput = getFormElement(
					form,
					'priorityName' + i + defaultLanguageId
				);
				const priorityNameInput = getFormElement(
					form,
					'priorityName' + i + '_' + lang
				);

				let name = '';

				if (defaultNameInput && priorityNameInput) {
					const defaultName = defaultNameInput.value;
					const priorityName = priorityNameInput.value;

					name = priorityName || defaultName;
				}

				const defaultValueInput = getFormElement(
					form,
					'priorityValue' + i + defaultLanguageId
				);
				const priorityValueInput = getFormElement(
					form,
					'priorityValue' + i + '_' + lang
				);

				let value = '';

				if (defaultValueInput && priorityValueInput) {
					const defaultValue = defaultValueInput;
					const priorityValue = priorityValueInput;

					value = priorityValue || defaultValue;
				}

				const data = {};

				if (name && image && value) {
					data['priorityName' + i + '_temp'] = name;
					data['priorityImage' + i + '_temp'] = image;
					data['priorityValue' + i + '_temp'] = value;
				}

				setFormValues(form, data);
			}
		}
	};

	const form = document.getElementById(`${namespace}fm`);
	const prioritiesChanged = window[`${namespace}prioritiesChanged`];
	const prioritiesLastLanguageId =
		window[`${namespace}prioritiesLastLanguageId`];

	if (form) {
		if (
			prioritiesChanged &&
			prioritiesLastLanguageId !== defaultLanguageId
		) {
			for (let i = 0; i < 10; i++) {
				const priorityImage = getFormElement(
					form,
					'priorityImage' + i + '_temp'
				).value;

				const priorityName = getFormElement(
					form,
					'priorityName' + i + '_temp'
				).value;

				const priorityValue = getFormElement(
					form,
					'priorityValue' + i + '_temp'
				).value;

				setFormValues(form, {
					['priorityImage' +
					i +
					'_' +
					prioritiesLastLanguageId]: priorityImage,
					['priorityName' +
					i +
					'_' +
					prioritiesLastLanguageId]: priorityName,
					['priorityValue' +
					i +
					'_' +
					prioritiesLastLanguageId]: priorityValue,
				});
			}

			prioritiesChanged.value = false;
		}

		const selLanguageInput = getFormElement(form, 'prioritiesLanguageId');

		if (selLanguageInput) {
			const selLanguageId = selLanguageInput.value;

			if (selLanguageId) {
				updatePrioritiesLanguageTemps(selLanguageId);
			}

			const localizedPrioritiesTable = document.getElementById(
				`${namespace}localized-priorities-table`
			);

			if (localizedPrioritiesTable) {
				if (selLanguageId) {
					localizedPrioritiesTable.classList.remove('hide');
				}
				else {
					localizedPrioritiesTable.classList.add('hide');
				}
			}

			prioritiesLastLanguageId.value = selLanguageId;
		}
	}
}
