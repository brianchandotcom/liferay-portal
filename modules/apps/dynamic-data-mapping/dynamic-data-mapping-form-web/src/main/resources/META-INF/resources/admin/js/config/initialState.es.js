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

import {PagesVisitor, getUid} from 'data-engine-js-components-web';
import {
	INITIAL_PAGES,
	INITIAL_STATE,
} from 'data-engine-js-components-web/js/core/config/index.es';
import {RulesSupport} from 'dynamic-data-mapping-form-builder';

export const BUILDER_INITIAL_STATE = {
	...INITIAL_STATE,

	// Flag that indicates the index of the rule being edited.

	currentRuleLoc: null,
	editingLanguageId: themeDisplay.getDefaultLanguageId(),
	fieldSets: [],
	fieldTypes: [],
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
	paginationMode: 'multi-pages',
	rules: [],
};

/**
 * NormalizePages deals with manipulations of the Field to change behaviors or
 * fixes something specific to the structure that affects the Field.
 *
 * Called only at application startup, but adds an initial computing load
 * to traverse through all fields on the form as well as nested fields.
 */
const normalizePages = (pages) => {
	const visitor = new PagesVisitor(pages);

	return visitor.mapFields(
		({settingsContext, ...otherProps}) => {
			const visitor = new PagesVisitor(settingsContext.pages);

			// Inferences the edited property to `true` of the options of a Field with
			// the type `options`. This is an implementation that came from the old
			// implementation of the LayoutProvider, to remove this it is necessary
			// to refactor the Options field to better deal with states and location.

			return {
				settingsContext: {
					...settingsContext,
					pages: visitor.mapFields((field) => {
						if (field.type === 'options') {
							const languageIds = Object.keys(field.value);

							return {
								...field,
								value: languageIds.reduce(
									(previousValue, currentLanguageId) => ({
										...previousValue,
										[currentLanguageId]: field.value[
											currentLanguageId
										].map((option) => ({
											...option,
											edited: true,
										})),
									}),
									{}
								),
							};
						}

						return field;
					}),
				},
				...otherProps,
			};
		},
		true,
		true
	);
};

export const initState = (
	{
		initialSuccessPageSettings,
		localizedName,
		pages: initialPages,
		paginationMode: initialPaginationMode,
		rules: initialRules,
		successPageSettings: initialSuccessPage,
		...otherProps
	},
	{view}
) => {
	const pages = initialPages.length
		? normalizePages(initialPages)
		: INITIAL_PAGES;

	// Before starting the application formats the rules there may be a broken rule with
	// an invalid field and it is necessary to remove it from the rule.

	const rules = initialRules.length
		? RulesSupport.formatRules(pages, initialRules)
		: initialRules;

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
		availableLanguageIds: Object.keys(localizedName),
		localizedName,
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
		rules,
		successPageSettings,
		...otherProps,
	};
};
