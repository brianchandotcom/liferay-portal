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

import {
	PagesVisitor,
	useConfig,
	useFormState,
} from 'data-engine-js-components-web';
import {useCallback, useEffect, useRef} from 'react';

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
export const useStateSync = () => {
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

	const settingsDDMFormRef = useRef(null);

	useEffect(() => {
		const getAsync = async () => {
			settingsDDMFormRef.current = await Liferay.componentReady(
				'settingsDDMForm'
			);
		};

		getAsync();
	}, [settingsDDMFormRef]);

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

		if (settingsDDMFormRef.current?.reactComponentRef.current) {
			document.querySelector(
				`#${portletNamespace}serializedSettingsContext`
			).value = JSON.stringify({
				pages: settingsDDMFormRef.current.reactComponentRef.current.get(
					'pages'
				),
			});
		}

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
};
