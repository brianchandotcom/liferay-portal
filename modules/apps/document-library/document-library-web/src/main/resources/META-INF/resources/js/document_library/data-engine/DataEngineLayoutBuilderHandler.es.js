/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
					defaultLanguageId.replaceAll('_', '-')
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
