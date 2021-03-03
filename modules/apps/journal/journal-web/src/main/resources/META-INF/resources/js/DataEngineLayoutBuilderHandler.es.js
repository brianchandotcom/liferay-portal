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

export default function ({defaultLanguageId, namespace}) {
	const form = document.getElementById(`${namespace}fm`);

	// Clean the input if the language is not considered translated when
	// submitting the form

	const clearNameInputIfNeeded = () => {
		const inputComponent = Liferay.component(`${namespace}name`);

		if (inputComponent) {
			const selectedLanguageId = document.getElementById(
				`${namespace}languageId`
			).value;
			const translatedLanguages = inputComponent.get(
				'translatedLanguages'
			);

			if (
				!translatedLanguages.has(selectedLanguageId) &&
				selectedLanguageId !== defaultLanguageId
			) {
				inputComponent.updateInput('');

				const form = Liferay.Form.get(`${namespace}fm`);

				if (form) {
					form.removeRule(`${namespace}name`, 'required');
				}
			}
		}
	};

	const getDataLayoutBuilder = () => {
		return Liferay.componentReady(`${namespace}dataLayoutBuilder`);
	};

	const getInputLocalizedValues = (fieldName) => {
		const inputLocalized = Liferay.component(`${namespace}${fieldName}`);
		const localizedValues = {};

		if (inputLocalized) {
			var translatedLanguages = inputLocalized
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

	const saveDDMStructure = () => {
		getDataLayoutBuilder().then((dataLayoutBuilder) => {
			const nameInput = document.getElementById(`${namespace}name`);

			const name = getInputLocalizedValues('name');

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

			const description = getInputLocalizedValues('description');

			const formData = dataLayoutBuilder.getFormData();

			const dataDefinition = formData.definition;

			dataDefinition.description = description;
			dataDefinition.name = name;

			const dataLayout = formData.layout;

			dataLayout.description = description;
			dataLayout.name = name;

			clearNameInputIfNeeded();

			Liferay.Util.postForm(form, {
				data: {
					dataDefinition: JSON.stringify(dataDefinition),
					dataLayout: JSON.stringify(dataLayout),
				},
			});
		});
	};

	form.addEventListener('submit', saveDDMStructure);

	// Deselect field when clicking outside the form builder

	const detectClickOutside = (event) => {
		if (
			!event.target.closest('.ddm-form-builder-wrapper') &&
			!event.target.closest('.multi-panel-sidebar')
		) {
			getDataLayoutBuilder().then((dataLayoutBuilder) => {
				dataLayoutBuilder.dispatch('sidebarFieldBlurred');
			});
		}
	};

	window.addEventListener('click', detectClickOutside, true);

	return {
		dispose() {
			form.removeEventListener('submit', saveDDMStructure);
			window.removeEventListener('click', detectClickOutside, true);
		},
	};
}
