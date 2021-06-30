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

import { GlobalStore } from 'redux-micro-frontend';
import * as Redux from 'redux';

declare global {
	interface Window {
		StateManager: any;
		__REDUX_DEVTOOLS_EXTENSION__: any;
	}
}

declare var Liferay: any;

window.StateManager = window.StateManager || { 'GlobalStore': GlobalStore, 'Redux': Redux };

let layoutRelativeURL: string = Liferay.ThemeDisplay.getLayoutRelativeURL();

const initialState = {
	routerBasePage: layoutRelativeURL,
	routerBaseSite: layoutRelativeURL.substring(0, layoutRelativeURL.lastIndexOf('/')),
	routerBaseComponents: []
};

function navigationReducer(state: any = initialState, action: any): any {
	switch (action.type) {
		case 'navigation/add':
			return {...state, routerBaseComponents: state.routerBaseComponents.filter((x:any):boolean => x.instance !== action.payload.instance).concat([action.payload])};
		case 'navigation/remove':
			return {...state, routerBaseComponents: state.routerBaseComponents.filter((x:any):boolean => x.instance !== action.payload.instance)};
		default:
			return state;
	}
}

let globalStore:GlobalStore = window.StateManager.GlobalStore.Get();

let navStore:Redux.Store = window.StateManager.Redux.createStore(
	navigationReducer,
	window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);

globalStore.RegisterStore(
	'com.liferay.portlet.navigation',
	navStore
);

globalStore.RegisterGlobalActions(
	'com.liferay.portlet.navigation',
	['navigation/add', 'navigation/remove', 'navigation/ping']
);

function updateNavigation(state:any):void {
	let urlsElement:HTMLElement|null = document.getElementById('spa-additional-portlets-urls');
	if (urlsElement != null) {
		urlsElement.textContent = '';

		let el:HTMLElement = document.createElement('a');

		if (state.routerBaseSite.length > 0) {
			el.setAttribute('href', state.routerBaseSite);
			el.textContent = state.routerBaseSite;
			urlsElement.appendChild(el);
			el = document.createElement('a');
		}

		el.setAttribute('href', state.routerBasePage);
		el.textContent = state.routerBasePage;
		urlsElement.appendChild(el);

		state.routerBaseComponents.flatMap(
			(x:any):string[] => x.navigations
		).forEach(
			(x:string):void => {
				el = document.createElement('a');
				el.setAttribute('href', x);
				el.textContent = x;
				urlsElement!.appendChild(el);
			}
		);
	}
}

function waitForElm(selector:string) {
	return new Promise(resolve => {
		if (document.querySelector(selector)) {
			return resolve(document.querySelector(selector));
		}

		const observer:MutationObserver = new MutationObserver(mutations => {
			if (document.querySelector(selector)) {
				resolve(document.querySelector(selector));
				observer.disconnect();
			}
		});

		observer.observe(document, {childList: true, subtree: true});
	});
}

waitForElm('#spa-additional-portlets-urls').then(elm =>
	globalStore.Subscribe('com.liferay.portlet.navigation', updateNavigation)
);

function populateMenu(e:any):void {
	globalStore.DispatchAction('com.liferay.portlet.navigation', <any> {type: 'navigation/ping'});
}

Liferay.on(
	{
		'endNavigate': populateMenu,
		'allPortletsReady': populateMenu,
		'addPortlet': populateMenu,
		'closePortlet': populateMenu
	}
	, Liferay
);
