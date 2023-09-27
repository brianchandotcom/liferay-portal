/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {getJsModule} from '../../../utilities/modules';
import Summary from '../../summary/Summary';
import CartItem from '../CartItem';
import CartItemsList from '../CartItemsList';
import CartItemsListActions from '../CartItemsListActions';
import Header from '../Header';
import Opener from '../Opener';
import OrderButton from '../OrderButton';
import RequestQuoteButton from '../RequestQuoteButton';
import Wrapper from '../Wrapper';
import {
	CART,
	HEADER,
	ITEM,
	ITEMS_LIST,
	ITEMS_LIST_ACTIONS,
	OPENER,
	ORDER_BUTTON,
	REQUEST_QUOTE_BUTTON,
	SUMMARY,
} from './constants';

export const DEFAULT_VIEWS = {
	[CART]: {component: Wrapper},
	[HEADER]: {component: Header},
	[ITEM]: {component: CartItem},
	[ITEMS_LIST]: {component: CartItemsList},
	[ITEMS_LIST_ACTIONS]: {component: CartItemsListActions},
	[OPENER]: {component: Opener},
	[ORDER_BUTTON]: {component: OrderButton},
	[REQUEST_QUOTE_BUTTON]: {component: RequestQuoteButton},
	[SUMMARY]: {component: Summary},
};

/**
 * decorateWithName - for test purposes only
 * @param componentFn: React [Function] component
 * @param keyValuePairs: object
 */
function decorateWith(componentFn, keyValuePairs) {
	const component = componentFn;

	component.component = {...keyValuePairs};

	return component;
}

function resolveView({component, contentRendererModuleUrl}) {
	if (component) {
		return Promise.resolve(
			decorateWith((props) => component(props), {name: component.name})
		);
	}

	return getJsModule(contentRendererModuleUrl).then((module) =>
		Promise.resolve(
			decorateWith(module, {
				moduleURL: contentRendererModuleUrl,
				name: module.name,
			})
		)
	);
}

export function resolveCartViews(customViews = {}) {
	const views = {...DEFAULT_VIEWS, ...customViews};
	const [...viewTypes] = Object.keys(DEFAULT_VIEWS).sort();

	return Promise.all(
		viewTypes.map((viewType) =>
			resolveView(views[viewType]).catch(() =>
				resolveView(DEFAULT_VIEWS[viewType])
			)
		)
	).then((resolvedViews) =>
		Promise.resolve(
			viewTypes.reduce(
				(views, type) => ({
					...views,
					[type]: resolvedViews.shift(),
				}),
				{}
			)
		)
	);
}
