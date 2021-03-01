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

import {getUid} from 'dynamic-data-mapping-form-renderer';
import {INITIAL_STATE} from 'dynamic-data-mapping-form-renderer/js/core/config/index.es';

export const BUILDER_INITIAL_STATE = {
	...INITIAL_STATE,
	availableLanguageIds: [themeDisplay.getLanguageId()],
	editingLanguageId: themeDisplay.getDefaultLanguageId(),
	fieldSets: [],
	fieldTypes: [],
	focusedField: {},
	formInstanceId: 0,
	functionsMetadata: {},
	initialSuccessPageSettings: {
		body: {
			[themeDisplay.getDefaultLanguageId()]: Liferay.Language.get(
				'your-information-was-successfully-received-thank-you-for-filling-out-the-form'
			),
		},
		title: {
			[themeDisplay.getDefaultLanguageId()]: Liferay.Language.get(
				'thank-you'
			),
		},
	},
	paginationMode: 'multi_pages',
	rules: [],
};

const INITIAL_PAGES = [
	{
		description: '',
		localizedDescription: {
			[themeDisplay.getLanguageId()]: '',
		},
		localizedTitle: {
			[themeDisplay.getLanguageId()]: '',
		},
		rows: [
			{
				columns: [
					{
						fields: [],
						size: 12,
					},
				],
			},
		],
		title: '',
	},
];

export const initState = (
	{
		initialSuccessPageSettings,
		pages: initialPages,
		paginationMode: initialPaginationMode,
		successPageSettings: initialSuccessPage,
		...otherProps
	},
	{view}
) => {
	const pages = initialPages.length ? initialPages : INITIAL_PAGES;

	// The Forms application is also rendered for Element Set, so we have to configure some
	// components to behave differently. This can be removed when Element Set is deprecated.

	const paginationMode =
		view === 'fieldSets' ? 'single-page' : initialPaginationMode;

	const successPageSettings = {
		body:
			initialSuccessPage?.body === 'string'
				? {
						[themeDisplay.getDefaultLanguageId()]: initialSuccessPage.body,
				  }
				: initialSuccessPageSettings.body,
		enabled:
			view === 'fieldSets' ? false : initialSuccessPage?.enabled ?? true,
		title:
			initialSuccessPage?.title === 'string'
				? {
						[themeDisplay.getDefaultLanguageId()]: initialSuccessPage.title,
				  }
				: initialSuccessPageSettings.title,
	};

	return {
		pages: [

			// Adds new properties to pages for rendering and provides
			// a unique uid that will avoid `key` problems when rendering
			// pages.

			...pages.map((page, pageIndex) => ({
				...page,
				id: getUid(),

				// Deprecated property: The components that use this
				// information can consume the usePage which can keep
				// the index in context when iterating pages.

				pageIndex,

				// Deprecated property: Components that need this information can
				// directly consume `pages.length`.

				total: pages.length,
			})),

			// Adds the success page enabled by default.

			successPageSettings.enabled
				? {
						contentRenderer: 'success',
						id: getUid(),
						paginationItemRenderer: `${paginationMode}_success`,
						rows: [],
						successPageSettings,
				  }
				: false,
		].filter(Boolean),
		paginationMode,
		successPageSettings,
		...otherProps,
	};
};
