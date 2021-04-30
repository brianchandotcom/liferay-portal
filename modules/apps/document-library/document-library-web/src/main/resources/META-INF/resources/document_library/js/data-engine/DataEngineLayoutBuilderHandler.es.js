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

export default function ({defaultLanguageId, namespace}) {
	const form = document.getElementById(`${namespace}fm`);

	const getDataLayoutBuilder = () => {
		return Liferay.componentReady(`${namespace}dataLayoutBuilder`);
	};

	const getInputLocalizedValues = (fieldName) => {
		const inputLocalized = Liferay.component(`${namespace}${fieldName}`);
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

	const saveDataEngineStructure = async () => {
		const dataLayoutBuilder = await getDataLayoutBuilder();
		const nameInput = document.getElementById(`${namespace}name`);
		const name = getInputLocalizedValues('name');
		const description = getInputLocalizedValues('description');

		if (!nameInput.value || !name[defaultLanguageId]) {
			Liferay.Util.openToast({
				message: Liferay.Util.sub(
					Liferay.Language.get(
						'please-enter-a-valid-title-for-the-default-language-x'
					),
					defaultLanguageId.replace('_', '-')
				),
				title: Liferay.Language.get('error'),
				type: 'danger',
			});

			nameInput.focus();

			return;
		}

		const {dataDefinition, dataLayout} = dataLayoutBuilder.current.state;

		Liferay.Util.postForm(form, {
			data: {
				dataDefinition: JSON.stringify({
					...dataDefinition.serialize(),
					defaultLanguageId,
					description,
					name
				}),
				dataLayout: JSON.stringify({
					...dataLayout.serialize(),
					description,
					name
				}),
			},
		});
	};

	form.addEventListener('submit', saveDataEngineStructure);

	const detectClickOutside = async (event) => {
		if (
			!event.target.closest(
				'.ddm-form-builder-wrapper, .multi-panel-sidebar, .dropdown-menu'
			)
		) {
			const dataLayoutBuilder = await getDataLayoutBuilder();
			dataLayoutBuilder.formBuilderWithLayoutProvider.refs.layoutProvider?.dispatch?.(
				'sidebarFieldBlurred'
			);
		}
	};

	window.addEventListener('click', detectClickOutside, true);

	return {
		dispose() {
			form.removeEventListener('submit', saveDataEngineStructure);
			window.removeEventListener('click', detectClickOutside, true);
		},
	};
}
