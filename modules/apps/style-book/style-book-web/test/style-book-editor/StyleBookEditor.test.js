/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import React from 'react';

import StyleBookEditor from '../../src/main/resources/META-INF/resources/js/style-book-editor/StyleBookEditor';
import {config} from '../../src/main/resources/META-INF/resources/js/style-book-editor/config';

jest.mock(
	'../../src/main/resources/META-INF/resources/js/style-book-editor/useCloseProductMenu',
	() => ({
		useCloseProductMenu: jest.fn(),
	})
);

jest.mock(
	'../../src/main/resources/META-INF/resources/js/style-book-editor/Toolbar',
	() => () => <div data-testid="Toolbar" />
);

jest.mock(
	'../../src/main/resources/META-INF/resources/js/style-book-editor/LayoutPreview',
	() => () => <div data-testid="LayoutPreview" />
);

jest.mock(
	'../../src/main/resources/META-INF/resources/js/style-book-editor/Sidebar',
	() => () => <div data-testid="Sidebar" />
);

global.Liferay = {
	Language: {
		get: jest.fn((key) => key),
	},
};

const frontendTokenDefinitions = [
	{
		frontendTokenCategories: [
			{
				frontendTokenSets: [
					{
						frontendTokens: [
							{
								defaultValue: '#000',
								label: 'Token 1',
								mappings: [
									{type: 'cssVariable', value: 'token-1'},
								],
								name: 'token1',
								type: 'color',
							},
						],
						label: 'Set 1',
						name: 'set1',
					},
				],
				label: 'Category 1',
				name: 'category1',
			},
		],
		id: 'theme',
		name: 'Theme Tokens',
	},
	{
		frontendTokenCategories: [
			{
				frontendTokenSets: [
					{
						frontendTokens: [
							{
								defaultValue: '#fff',
								label: 'Clay Token',
								mappings: [
									{type: 'cssVariable', value: 'clay-token'},
								],
								name: 'clayToken',
								type: 'color',
							},
						],
						label: 'Clay Set',
						name: 'claySet',
					},
				],
				label: 'Clay Category',
				name: 'clayCategory',
			},
		],
		id: 'clay',
		name: 'Clay Tokens',
	},
];

const previewOptions = [
	{data: {recentLayouts: []}, type: 'page'},
	{data: {recentLayouts: []}, type: 'master'},
	{data: {recentLayouts: []}, type: 'pageTemplate'},
	{data: {recentLayouts: []}, type: 'displayPageTemplate'},
	{data: {recentLayouts: []}, type: 'fragmentCollection'},
];

describe('StyleBookEditor', () => {
	it('initializes config with namespaced tokens and backward compatibility for theme tokens', () => {
		render(
			<StyleBookEditor
				frontendTokenDefinitions={frontendTokenDefinitions}
				previewOptions={previewOptions}
				themeFrontendTokenDefinitionId="theme"
			/>
		);

		// Namespaced keys should exist

		expect(config.frontendTokens['theme:token1']).toBeDefined();
		expect(config.frontendTokens['theme:token1'].value).toBe('#000');

		expect(config.frontendTokens['clay:clayToken']).toBeDefined();
		expect(config.frontendTokens['clay:clayToken'].value).toBe('#fff');

		// Backward compatibility: theme tokens should also exist without namespace

		expect(config.frontendTokens['token1']).toBeDefined();
		expect(config.frontendTokens['token1'].name).toBe('token1');
		expect(config.frontendTokens['token1'].value).toBe('#000');

		// Clay tokens should NOT exist without namespace (unless it was the theme)

		expect(config.frontendTokens['clayToken']).toBeUndefined();
	});
});
