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

const isElementInnerSelector = (element, ...selectors) =>
	!selectors.some((selector) => element.closest(selector));

export default function DataEngineLayoutBuilderHandler({namespace}) {
	const form = document.getElementById(`${namespace}fm`);

	// Clean the input if the language is not considered translated when
	// submitting the form

	const clearNameInputIfNeeded = (defaultLanguageId) => {
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
		const {dataDefinition, dataLayout} = dataLayoutBuilder.current.state;
		const {defaultLanguageId} = dataDefinition;

		const nameInput = document.getElementById(`${namespace}name`);
		const name = getInputLocalizedValues('name');
		const description = getInputLocalizedValues('description');

		if (!nameInput.value && !name[defaultLanguageId]) {
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

		clearNameInputIfNeeded(defaultLanguageId);

		Liferay.Util.postForm(form, {
			data: {
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
			},
		});
	};

	form.addEventListener('submit', saveDataEngineStructure);

	// Deselect field when clicking outside the form builder

	const detectClickOutside = async ({target}) => {
		if (
			isElementInnerSelector(
				target,
				'.ddm-form-builder-wrapper',
				'.multi-panel-sidebar',
				'.lfr-icon-menu-open',
				'.input-localized-content'
			)
		) {
			const dataLayoutBuilder = await getDataLayoutBuilder();

			dataLayoutBuilder.current.dispatch({
				type: 'sidebar_field_blur',
			});
		}
	};

	window.addEventListener('click', detectClickOutside, true);

	// Update editing language id in the data engine side

	const updateEditingLanguageId = async (event) => {
		const editingLanguageId = event.item.getAttribute('data-value');
		const dataLayoutBuilder = await getDataLayoutBuilder();

		dataLayoutBuilder.current.dispatch({
			payload: {editingLanguageId},
			type: 'language_change',
		});
	};

	Liferay.after('inputLocalized:localeChanged', updateEditingLanguageId);

	return {
		dispose() {
			form.removeEventListener('submit', saveDataEngineStructure);
			window.removeEventListener('click', detectClickOutside, true);
		},
	};
}
