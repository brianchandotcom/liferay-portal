/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import '@testing-library/jest-dom';
import {render} from '@testing-library/react';
import * as FrontendJsWeb from 'frontend-js-web';
import React from 'react';

import {
	CART,
	HEADER,
	ITEM,
	ITEMS_LIST,
	ITEMS_LIST_ACTIONS,
	OPENER,
	ORDER_BUTTON,
	SUMMARY,
} from '../../../../src/main/resources/META-INF/resources/components/mini_cart/util/constants';
import {
	DEFAULT_VIEWS,
	resolveCartViews,
} from '../../../../src/main/resources/META-INF/resources/components/mini_cart/util/views';

jest.mock('frontend-js-web', () => ({
	...jest.requireActual('frontend-js-web'),
	loadModule: jest.fn(),
}));

describe('MiniCart tests_utilities -> Views', () => {
	const VIEW_TYPES = [
		CART,
		HEADER,
		ITEM,
		ITEMS_LIST,
		ITEMS_LIST_ACTIONS,
		OPENER,
		ORDER_BUTTON,
		SUMMARY,
	];

	const getComponentNames = (viewsMap) =>
		Object.entries(viewsMap).map(([, {component}]) => component.name);

	const DEFAULT_VIEWS_COMPONENT_NAMES = getComponentNames(DEFAULT_VIEWS);

	afterEach(() => {
		jest.resetAllMocks();
	});

	describe('resolveCartViews', () => {
		it('resolves the default MiniCart views', async () => {
			const resolvedViews = await resolveCartViews();

			expect(getComponentNames(resolvedViews)).toEqual(
				DEFAULT_VIEWS_COMPONENT_NAMES
			);
		});

		describe('selectively replaces default views with', () => {
			const CustomView = (props = {}) => (
				<div
					{...Object.keys(props).reduce(
						(attrs, propKey) => ({
							...attrs,
							[`data-${propKey}`]: props[propKey],
						}),
						{}
					)}
				>
					test
				</div>
			);

			it('resolved custom local component implementations', async () => {
				const customViews = {
					[OPENER]: {component: CustomView},
				};

				const resolvedViews = await resolveCartViews(customViews);
				const componentNames = getComponentNames(resolvedViews);

				expect(componentNames.length).toEqual(
					DEFAULT_VIEWS_COMPONENT_NAMES.length
				);
				expect(componentNames).not.toEqual(
					DEFAULT_VIEWS_COMPONENT_NAMES
				);

				VIEW_TYPES.forEach((viewType) => {
					if (viewType === OPENER) {
						expect(resolvedViews[viewType].component.name).toEqual(
							customViews[viewType].component.name
						);
					}
					else {
						expect(resolvedViews[viewType].component.name).toEqual(
							DEFAULT_VIEWS[viewType].component.name
						);
					}
				});

				const CustomOpener = resolvedViews[OPENER];
				const {getByText} = render(<CustomOpener testattr="test" />);

				const customViewElement = getByText('test');

				expect(customViewElement).toBeInTheDocument();
				expect(customViewElement.dataset.testattr).toEqual('test');
			});

			it('resolved custom Liferay module component implementations', async () => {
				jest.spyOn(FrontendJsWeb, 'loadModule').mockImplementation(() =>
					Promise.resolve(CustomView)
				);

				const customViews = {
					[OPENER]: {
						contentRendererModuleURL: `@module/${CustomView.name}`,
					},
				};

				const resolvedViews = await resolveCartViews(customViews);

				const componentNames = getComponentNames(resolvedViews);

				expect(componentNames.length).toEqual(
					DEFAULT_VIEWS_COMPONENT_NAMES.length
				);
				expect(componentNames).not.toEqual(
					DEFAULT_VIEWS_COMPONENT_NAMES
				);

				VIEW_TYPES.forEach((viewType) => {
					if (viewType === OPENER) {
						expect(resolvedViews[viewType].component.name).toEqual(
							CustomView.name
						);
						expect(
							resolvedViews[viewType].component.moduleURL
						).toEqual(customViews[OPENER].contentRendererModuleURL);
					}
					else {
						expect(resolvedViews[viewType].component.name).toEqual(
							DEFAULT_VIEWS[viewType].component.name
						);
					}
				});

				const CustomOpener = resolvedViews[OPENER];
				const {getByText} = render(<CustomOpener testattr="test" />);

				const customViewElement = getByText('test');

				expect(customViewElement).toBeInTheDocument();
				expect(customViewElement.dataset.testattr).toEqual('test');
			});

			it('fallback default MiniCart views if Liferay modules fail to resolve', async () => {
				jest.spyOn(FrontendJsWeb, 'loadModule').mockImplementation(() =>
					Promise.reject()
				);

				const customViews = {
					[OPENER]: {
						contentRendererModuleURL: `@failing-module/${CustomView.name}`,
					},
				};

				const resolvedViews = await resolveCartViews(customViews);

				const componentNames = getComponentNames(resolvedViews);

				expect(componentNames.length).toEqual(
					DEFAULT_VIEWS_COMPONENT_NAMES.length
				);
				expect(componentNames).toEqual(DEFAULT_VIEWS_COMPONENT_NAMES);

				VIEW_TYPES.forEach((viewType) => {
					expect(resolvedViews[viewType].component.name).toEqual(
						DEFAULT_VIEWS[viewType].component.name
					);
				});
			});
		});
	});
});
