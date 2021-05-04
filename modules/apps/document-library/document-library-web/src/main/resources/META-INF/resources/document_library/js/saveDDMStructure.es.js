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

export const getInputLocalizedValues = (namespace, fieldName) => {
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
};

export const getDataEngineStructure = ({dataLayoutBuilder, namespace}) => {
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
};

export default function saveDDMStructure({namespace}) {
	const form = document[`${namespace}fm`];

	const saveDataEngineStructure = async (event) => {
		event.preventDefault();

		const dataLayoutBuilder = await Liferay.componentReady(
			`${namespace}dataLayoutBuilder`
		);

		Liferay.Util.postForm(form, {
			data: getDataEngineStructure({dataLayoutBuilder, namespace}),
		});
	};

	form.addEventListener('submit', saveDataEngineStructure, true);
}
