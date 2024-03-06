/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	PagesVisitor,
	useConfig,
	useFormState,
} from 'data-engine-js-components-web';
import {useCallback} from 'react';

const getSerializedSettingsContextPages = (pages, defaultLanguageId) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields((field) => {
		if (field.type === 'options') {
			const {value} = field;
			const newValue = {};

			Object.keys(value).forEach((locale) => {
				newValue[locale] = value[locale].filter(
					({value}) => value !== ''
				);
			});

			if (!newValue[defaultLanguageId]) {
				newValue[defaultLanguageId] = [];
			}

			field = {
				...field,
				value: newValue,
			};
		}

		return field;
	});
};

const getSerializedFormBuilderContext = (state, defaultLanguageId) => {
	const pages = state.pages
		.map((page) => ({
			...page,
			description: page.localizedDescription,
			title: page.localizedTitle,
		}))
		.filter(({contentRenderer}) => contentRenderer !== 'success');

	const visitor = new PagesVisitor(pages);

	return JSON.stringify({
		...state,
		pages: visitor.mapFields(
			(field) => {
				return {
					...field,
					settingsContext: {
						...field.settingsContext,
						availableLanguageIds: state.availableLanguageIds,
						defaultLanguageId: state.defaultLanguageId,
						pages: getSerializedSettingsContextPages(
							field.settingsContext.pages,
							defaultLanguageId
						),
					},
				};
			},
			true,
			true
		),
	});
};

/**
 * This hook is just a way to save the state in the hidden input of the form
 * to make the submit, this is the same implementation of StateSyncronizer.
 */
export function useStateSync() {
	const {portletNamespace, sidebarPanels} = useConfig();
	const {
		availableLanguageIds,
		defaultLanguageId,
		localizedDescription,
		localizedName,
		pages,
		paginationMode,
		rules,
		successPageSettings,
	} = useFormState();

	return useCallback(() => {
		const state = {
			availableLanguageIds,
			defaultLanguageId,
			description: localizedDescription,
			name: localizedName,
			pages,
			paginationMode,
			rules,
			sidebarPanels,
			successPageSettings,
		};

		Object.keys(state.name).forEach((key) => {
			state.name[key] = Liferay.Util.unescape(state.name[key]);
		});

		Object.keys(state.description).forEach((key) => {
			state.description[key] = Liferay.Util.unescape(
				state.description[key]
			);
		});

		document.querySelector(
			`#${portletNamespace}name`
		).value = JSON.stringify(localizedName);
		document.querySelector(
			`#${portletNamespace}description`
		).value = JSON.stringify(localizedDescription);
		document.querySelector(
			`#${portletNamespace}serializedFormBuilderContext`
		).value = getSerializedFormBuilderContext(state, defaultLanguageId);
	}, [
		availableLanguageIds,
		defaultLanguageId,
		localizedDescription,
		localizedName,
		pages,
		paginationMode,
		portletNamespace,
		rules,
		sidebarPanels,
		successPageSettings,
	]);
}
