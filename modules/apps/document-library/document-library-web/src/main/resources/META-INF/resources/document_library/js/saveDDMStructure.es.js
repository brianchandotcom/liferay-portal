/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {postForm} from 'frontend-js-web';

export function getInputLocalizedValues(namespace, fieldName) {
	const inputLocalized = Liferay.component(`${namespace}${fieldName}`);
	const localizedValues = {};

	if (inputLocalized) {
		const translatedLanguages = inputLocalized
			.get('translatedLanguages')
			.values();

		translatedLanguages.forEach((languageId) => {
			localizedValues[languageId] = inputLocalized.getValue(languageId);
		});
	}

	return localizedValues;
}

export function getDataEngineStructure({dataLayoutBuilder, namespace}) {
	const {dataDefinition, dataLayout} = dataLayoutBuilder.current.state;

	const name = getInputLocalizedValues(namespace, 'name');
	const description = getInputLocalizedValues(namespace, 'description');

	return {
		dataDefinition: JSON.stringify({
			...dataDefinition.serialize(),
			description,
			name,
		}),
		dataLayout: JSON.stringify({
			...dataLayout.serialize(),
			description,
			name,
		}),
	};
}

export default function saveDDMStructure({namespace}) {
	const form = document[`${namespace}fm`];

	const saveDataEngineStructure = async (event) => {
		event.preventDefault();

		const dataLayoutBuilder = await Liferay.componentReady(
			`${namespace}dataLayoutBuilder`
		);

		postForm(form, {
			data: getDataEngineStructure({dataLayoutBuilder, namespace}),
		});
	};

	form.addEventListener('submit', saveDataEngineStructure, true);
}
