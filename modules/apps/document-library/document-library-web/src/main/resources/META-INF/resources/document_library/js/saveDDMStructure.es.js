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

import {DataConverter} from 'data-engine-taglib';

export default function ({namespace}) {
	const formElement = document[`${namespace}fm`];

	const getInputLocalizedValues = (field) => {
		const inputLocalized = Liferay.component(`${namespace}${field}`);
		const localizedValues = {};

		if (inputLocalized) {
			const translatedLanguages = inputLocalized
				.get('translatedLanguages')
				.values();

			translatedLanguages.forEach((languageId) => {
				localizedValues[languageId] = inputLocalized.getValue(
					languageId
				);
			});
		}

		return localizedValues;
	};

	const onSubmit = async (event) => {
		event.preventDefault();

		//deprecated

		const dataLayoutBuilder = await Liferay.componentReady(
			`${namespace}dataLayoutBuilder`
		);
		const name = getInputLocalizedValues('name');

		const {
			availableLanguageIds,
			defaultLanguageId,
		} = dataLayoutBuilder.props;
		const {
			availableLanguageIds: availableLanguageIdsState,
		} = dataLayoutBuilder.state;

		const layoutProvider =
			dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider;

		const description = getInputLocalizedValues('description');

		const formData = DataConverter.getFormData({
			availableLanguageIds,
			availableLanguageIdsState,
			defaultLanguageId,
			layoutProvider,
		});

		const dataDefinition = formData.definition;

		dataDefinition.description = description;
		dataDefinition.name = name;

		const dataLayout = formData.layout;

		dataLayout.description = description;
		dataLayout.name = name;

		Liferay.Util.postForm(formElement, {
			data: {
				dataDefinition: JSON.stringify(dataDefinition),
				dataLayout: JSON.stringify(dataLayout),
			},
		});
	};

	formElement.addEventListener('submit', onSubmit, true);
}
