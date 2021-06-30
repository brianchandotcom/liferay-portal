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

import * as Redux from 'redux';
import {GlobalStore} from 'redux-micro-frontend';

declare global {
	interface Window {
		StateManager: any;
		__REDUX_DEVTOOLS_EXTENSION__: any;
	}
}

declare var Liferay: any;

window.StateManager = window.StateManager || {GlobalStore, Redux};

const layoutRelativeURL: string = Liferay.ThemeDisplay.getLayoutRelativeURL();

const initialState = {
	routerBaseComponents: [],
	routerBasePage: layoutRelativeURL,
	routerBaseSite: layoutRelativeURL.substring(
		0,
		layoutRelativeURL.lastIndexOf('/')
	),
};

function navigationReducer(state: any = initialState, action: any): any {
	switch (action.type) {
		case 'navigation/add':
			return {
				...state,
				routerBaseComponents: state.routerBaseComponents
					.filter(
						(x: any): boolean =>
							x.instance !== action.payload.instance
					)
					.concat([action.payload]),
			};
		case 'navigation/remove':
			return {
				...state,
				routerBaseComponents: state.routerBaseComponents.filter(
					(x: any): boolean => x.instance !== action.payload.instance
				),
			};
		default:
			return state;
	}
}

const globalStore: GlobalStore = window.StateManager.GlobalStore.Get();

const navStore = window.StateManager.Redux.createStore(
	navigationReducer,
	window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);

globalStore.RegisterStore('com.liferay.portlet.navigation', navStore);

globalStore.RegisterGlobalActions('com.liferay.portlet.navigation', [
	'navigation/add',
	'navigation/remove',
	'navigation/ping',
]);

function updateNavigation(state: any): void {
	const urlsElement: HTMLElement | null = document.getElementById(
		'spa-additional-portlets-urls'
	);
	if (urlsElement != null) {
		urlsElement.textContent = '';

		let element: HTMLElement = document.createElement('a');

		if (state.routerBaseSite.length > 0) {
			element.setAttribute('href', state.routerBaseSite);
			element.textContent = state.routerBaseSite;
			urlsElement.appendChild(element);
			element = document.createElement('a');
		}

		element.setAttribute('href', state.routerBasePage);
		element.textContent = state.routerBasePage;
		urlsElement.appendChild(element);

		state.routerBaseComponents
			.flatMap((x: any): string[] => x.navigations)
			.forEach((x: string): void => {
				element = document.createElement('a');
				element.setAttribute('href', x);
				element.textContent = x;
				urlsElement!.appendChild(element);
			});
	}
}

function waitForElm(selector: string) {
	return new Promise((resolve) => {
		if (document.querySelector(selector)) {
			return resolve(document.querySelector(selector));
		}

		const observer: MutationObserver = new MutationObserver(
			(_mutations) => {
				if (document.querySelector(selector)) {
					resolve(document.querySelector(selector));
					observer.disconnect();
				}
			}
		);

		observer.observe(document, {childList: true, subtree: true});
	});
}

waitForElm('#spa-additional-portlets-urls').then((_elm) =>
	globalStore.Subscribe('com.liferay.portlet.navigation', updateNavigation)
);

function populateMenu(): void {
	globalStore.DispatchAction('com.liferay.portlet.navigation', <any>{
		type: 'navigation/ping',
	});
}

Liferay.on(
	{
		addPortlet: populateMenu,
		allPortletsReady: populateMenu,
		closePortlet: populateMenu,
		endNavigate: populateMenu,
	},
	Liferay
);
