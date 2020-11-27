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

import {buildFragment} from 'frontend-js-web';

import AppDataAttributeHandler from '../../src/main/resources/META-INF/resources/app/AppDataAttributeHandler';
import globals from '../../src/main/resources/META-INF/resources/globals/globals';
import Screen from '../../src/main/resources/META-INF/resources/screen/Screen';

describe('AppDataAttributeHandler', () => {
	beforeAll(() => {
		globals.document.body.setAttribute('data-senna', '');
		globals.window.senna = {
			Screen,
		};
	});

	beforeEach(() => {
		jest.spyOn(console, 'log').mockImplementation(() => {});
	});

	afterAll(() => {
		globals.document.body.removeAttribute('data-senna');
		delete globals.window.senna;
	});

	it('throws error when base element not specified', () => {
		expect(() => {
			new AppDataAttributeHandler().handle();
		}).toThrow();
	});

	it('throws error when base element not valid', () => {
		expect(() => {
			var appDataAttributeHandler = new AppDataAttributeHandler();
			appDataAttributeHandler.setBaseElement({});
			appDataAttributeHandler.handle();
		}).toThrow();
	});

	it('throws error when already handled', () => {
		expect(() => {
			var appDataAttributeHandler = new AppDataAttributeHandler();
			appDataAttributeHandler.setBaseElement(globals.document.body);
			appDataAttributeHandler.handle();
			appDataAttributeHandler.handle();
		}).toThrow();
	});

	it('does not throw error when base element specified', () => {
		expect(() => {
			var appDataAttributeHandler = new AppDataAttributeHandler();
			appDataAttributeHandler.setBaseElement(globals.document.body);
			appDataAttributeHandler.handle();
			appDataAttributeHandler.dispose();
		}).not.toThrow();
	});

	it('disposes internal app when disposed', () => {
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		appDataAttributeHandler.handle();
		appDataAttributeHandler.dispose();
		expect(appDataAttributeHandler.getApp().isDisposed()).toBeTruthy();
	});

	it('disposes when not handled', () => {
		expect(() => {
			var appDataAttributeHandler = new AppDataAttributeHandler();
			appDataAttributeHandler.dispose();
		}).not.toThrow();
	});

	it('gets app', () => {
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		appDataAttributeHandler.handle();
		expect(appDataAttributeHandler.getApp()).toBeTruthy();
		appDataAttributeHandler.dispose();
	});

	it('gets base element', () => {
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		expect(appDataAttributeHandler.getBaseElement()).toBe(
			globals.document.body
		);
	});

	it('adds app surfaces from document', () => {
		enterDocumentSurfaceElement('surfaceId');
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		appDataAttributeHandler.handle();
		expect(
			'surfaceId' in appDataAttributeHandler.getApp().surfaces
		).toBeTruthy();
		appDataAttributeHandler.dispose();
		exitDocumentSurfaceElement('surfaceId');
	});

	it('adds random id to body without id when used as app surface', () => {
		globals.document.body.setAttribute('data-senna-surface', '');
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		appDataAttributeHandler.handle();
		expect(globals.document.body).toBeTruthy();
		appDataAttributeHandler.dispose();
		globals.document.body.removeAttribute('data-senna-surface');
	});

	it('throws error when adding app surfaces from document missing id', () => {
		enterDocumentSurfaceElementMissingId('surfaceId');
		expect(() => {
			var appDataAttributeHandler = new AppDataAttributeHandler();
			appDataAttributeHandler.setBaseElement(globals.document.body);
			appDataAttributeHandler.handle();
		}).toThrow();
		exitDocumentSurfaceElementMissingId('surfaceId');
	});

	it('adds default route if not found in document', () => {
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		appDataAttributeHandler.handle();
		expect(appDataAttributeHandler.getApp().hasRoutes()).toBeTruthy();
		appDataAttributeHandler.dispose();
	});

	it('adds routes from document', () => {
		enterDocumentRouteElement('/path1');
		enterDocumentRouteElement('/path2');
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		appDataAttributeHandler.handle();
		expect(appDataAttributeHandler.getApp().routes.length).toBe(2);
		appDataAttributeHandler.dispose();
		exitDocumentRouteElement('/path1');
		exitDocumentRouteElement('/path2');
	});

	it('adds routes from document with regex paths', () => {
		enterDocumentRouteElement('regex:[a-z]');
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		appDataAttributeHandler.handle();
		expect(
			appDataAttributeHandler.getApp().routes[0].getPath()
		).toBeInstanceOf(RegExp);
		appDataAttributeHandler.dispose();
		exitDocumentRouteElement('regex:[a-z]');
	});

	it('throws error when adding routes from document with missing screen type', () => {
		enterDocumentRouteElementMissingScreenType('/path');
		expect(() => {
			var appDataAttributeHandler = new AppDataAttributeHandler();
			appDataAttributeHandler.setBaseElement(globals.document.body);
			appDataAttributeHandler.handle();
		}).toThrow();
		exitDocumentRouteElement('/path');
	});

	it('throws error when adding routes from document with missing path', () => {
		enterDocumentRouteElementMissingPath();
		expect(() => {
			var appDataAttributeHandler = new AppDataAttributeHandler();
			appDataAttributeHandler.setBaseElement(globals.document.body);
			appDataAttributeHandler.handle();
		}).toThrow();
		exitDocumentRouteElementMissingPath();
	});

	it('sets base path from data attribute', () => {
		globals.document.body.setAttribute('data-senna-base-path', '/base');
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		appDataAttributeHandler.handle();
		expect(appDataAttributeHandler.getApp().getBasePath()).toBe('/base');
		appDataAttributeHandler.dispose();
		globals.document.body.removeAttribute('data-senna-base-path');
	});

	it('sets link selector from data attribute', () => {
		globals.document.body.setAttribute('data-senna-link-selector', 'a');
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		appDataAttributeHandler.handle();
		expect(appDataAttributeHandler.getApp().getLinkSelector()).toBe('a');
		appDataAttributeHandler.dispose();
		globals.document.body.removeAttribute('data-senna-link-selector');
	});

	it('sets loading css class from data attribute', () => {
		globals.document.body.setAttribute(
			'data-senna-loading-css-class',
			'loading'
		);
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		appDataAttributeHandler.handle();
		expect(appDataAttributeHandler.getApp().getLoadingCssClass()).toBe(
			'loading'
		);
		appDataAttributeHandler.dispose();
		globals.document.body.removeAttribute('data-senna-loading-css-class');
	});

	it('sets update scroll position to false from data attribute', () => {
		globals.document.body.setAttribute(
			'data-senna-update-scroll-position',
			'false'
		);
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		appDataAttributeHandler.handle();
		expect(appDataAttributeHandler.getApp().getUpdateScrollPosition()).toBe(
			false
		);
		appDataAttributeHandler.dispose();
		globals.document.body.removeAttribute(
			'data-senna-update-scroll-position'
		);
	});

	it('sets update scroll position to true from data attribute', () => {
		globals.document.body.setAttribute(
			'data-senna-update-scroll-position',
			'true'
		);
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		appDataAttributeHandler.handle();
		expect(appDataAttributeHandler.getApp().getUpdateScrollPosition()).toBe(
			true
		);
		appDataAttributeHandler.dispose();
		globals.document.body.removeAttribute(
			'data-senna-update-scroll-position'
		);
	});

	it.skip('dispatches app from data attribute', () => {
		globals.document.body.setAttribute('data-senna-dispatch', '');
		var appDataAttributeHandler = new AppDataAttributeHandler();
		appDataAttributeHandler.setBaseElement(globals.document.body);
		appDataAttributeHandler.handle();

		return appDataAttributeHandler.getApp().on('endNavigate', () => {
			appDataAttributeHandler.dispose();
			globals.document.body.removeAttribute('data-senna-dispatch');
		});
	});
});

function enterDocumentRouteElement(path) {
	document.body.appendChild(
		buildFragment(
			`<link href="${path}" rel="senna-route" type="senna.Screen"></link>`
		)
	);

	return document.querySelector('link[href="' + path + '"]');
}

function enterDocumentRouteElementMissingPath() {
	document.body.appendChild(
		buildFragment(
			`<link id="routeElementMissingPath" rel="senna-route" type="senna.Screen"></link>`
		)
	);

	return document.getElementById('routeElementMissingPath');
}

function enterDocumentRouteElementMissingScreenType(path) {
	document.body.appendChild(
		buildFragment(`<link href="${path}" rel="senna-route"></link>`)
	);

	return document.querySelector('link[href="' + path + '"]');
}

function enterDocumentSurfaceElement(surfaceId) {
	document.body.appendChild(
		buildFragment(`<div id="${surfaceId}" data-senna-surface></div>`)
	);

	return document.getElementById(surfaceId);
}

function enterDocumentSurfaceElementMissingId(surfaceId) {
	document.body.appendChild(
		buildFragment(`<div data-id="${surfaceId}" data-senna-surface></div>`)
	);

	return document.getElementById(surfaceId);
}

function exitDocumentRouteElement(path) {
	return document.querySelector('link[href="' + path + '"]').remove();
}

function exitDocumentRouteElementMissingPath() {
	return document.getElementById('routeElementMissingPath').remove();
}

function exitDocumentSurfaceElement(surfaceId) {
	return document.getElementById(surfaceId).remove();
}

function exitDocumentSurfaceElementMissingId(surfaceId) {
	return document.querySelector('[data-id="' + surfaceId + '"]').remove();
}
