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

import {getJsModule} from '../../../utilities/modules';
import Summary from '../../summary/Summary';
import CartItem from '../CartItem';
import CartItemsList from '../CartItemsList';
import CartItemsListActions from '../CartItemsListActions';
import Header from '../Header';
import Opener from '../Opener';
import OrderButton from '../OrderButton';
import Wrapper from '../Wrapper';
import {
	CART,
	HEADER,
	ITEM,
	ITEMS_LIST,
	ITEMS_LIST_ACTIONS,
	OPENER,
	ORDER_BUTTON,
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
