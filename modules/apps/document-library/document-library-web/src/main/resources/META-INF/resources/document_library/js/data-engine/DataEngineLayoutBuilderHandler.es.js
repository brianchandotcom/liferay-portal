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

import {openToast, postForm, sub} from 'frontend-js-web';

import {
	getDataEngineStructure,
	getInputLocalizedValues,
} from '../saveDDMStructure.es';

const isElementInnerSelector = (element, ...selectors) =>
	!selectors.some((selector) => element.closest(selector));

export default function DataEngineLayoutBuilderHandler({namespace}) {
	const form = document.getElementById(`${namespace}fm`);

	const getDataLayoutBuilder = () => {
		return Liferay.componentReady(`${namespace}dataLayoutBuilder`);
	};

	// Deselect field when clicking outside the form builder

	const detectClickOutside = async ({target}) => {
		if (
			isElementInnerSelector(
				target,
				'.cke_dialog',
				'.clay-color-dropdown-menu',
				'.date-picker-dropdown-menu',
				'.ddm-form-builder-wrapper',
				'.ddm-select-dropdown',
				'.input-localized-content',
				'.lfr-icon-menu-open',
				'.multi-panel-sidebar'
			)
		) {
			const dataLayoutBuilder = await getDataLayoutBuilder();

			dataLayoutBuilder.current.dispatch({
				submitButtonId: `${namespace}submitButton`,
				type: 'sidebar_field_blur',
			});
		}
	};

	window.addEventListener('mousedown', detectClickOutside, true);

	const saveDataEngineStructure = async () => {
		const dataLayoutBuilder = await getDataLayoutBuilder();
		const nameInput = document.getElementById(`${namespace}name`);
		const name = getInputLocalizedValues(namespace, 'name');

		const {
			defaultLanguageId,
		} = dataLayoutBuilder.current.state.dataDefinition;

		if (!nameInput.value || !name[defaultLanguageId]) {
			openToast({
				message: sub(
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

		postForm(form, {
			data: getDataEngineStructure({dataLayoutBuilder, namespace}),
		});
	};

	form.addEventListener('submit', saveDataEngineStructure);

	// Update editing language id in the data engine side

	const updateEditingLanguageId = async (event) => {
		const editingLanguageId = event.item.getAttribute('data-value');
		const dataLayoutBuilder = await getDataLayoutBuilder();

		dataLayoutBuilder.current.dispatch({
			payload: {languageId: editingLanguageId},
			type: 'language_add',
		});

		dataLayoutBuilder.current.dispatch({
			payload: {editingLanguageId},
			type: 'language_change',
		});
	};

	Liferay.after('inputLocalized:localeChanged', updateEditingLanguageId);

	return {
		dispose() {
			form.removeEventListener('submit', saveDataEngineStructure);
			window.removeEventListener('mousedown', detectClickOutside, true);
		},
	};
}
