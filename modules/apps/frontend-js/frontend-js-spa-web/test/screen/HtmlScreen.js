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

import HtmlScreen from '../../src/main/resources/META-INF/resources/screen/HtmlScreen';
import Surface from '../../src/main/resources/META-INF/resources/surface/Surface';

describe('HtmlScreen', () => {
	beforeAll(() => {
		window.Liferay.DOMTaskRunner = {
			runTasks: jest.fn(),
		};
	});

	beforeEach(() => {
		jest.spyOn(console, 'log').mockImplementation(() => {});
	});

	it('gets title selector', () => {
		var screen = new HtmlScreen();
		expect(screen.getTitleSelector()).toBe('title');
		screen.setTitleSelector('div.title');
		expect(screen.getTitleSelector()).toBe('div.title');
	});

	it('returns loaded content', (done) => {
		fetch.mockResponse('content');

		var screen = new HtmlScreen();
		screen.load('/url').then((content) => {
			expect(content).toBe('content');
			done();
		});
	});

	it('sets title from response content', (done) => {
		fetch.mockResponse('<title>new</title>');

		var screen = new HtmlScreen();
		screen.load('/url').then(() => {
			expect(screen.getTitle()).toBe('new');
			done();
		});
	});

	it('does not set title from response content if not present', (done) => {
		fetch.mockResponse('content');

		var screen = new HtmlScreen();
		screen.load('/url').then(() => {
			expect(screen.getTitle()).toBeNull();
			done();
		});
	});

	it.skip('cancels load request to an url', (done) => {
		fetch.mockResponse('');

		var screen = new HtmlScreen();
		screen
			.load('/url')
			.catch((reason) => {
				expect(reason).toBeInstanceOf(Error);
				done();
			})
			.cancel();
	});

	it('copies surface root node attributes from response content', (done) => {
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<html attributeA="valueA"><div id="surfaceId">surface</div></html>'
		);
		screen.flip([]).then(() => {
			expect(document.documentElement.getAttribute('attributeA')).toBe(
				'valueA'
			);
			done();
		});
	});

	it('extracts surface content from response content', () => {
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<div id="surfaceId">surface</div>'
		);
		expect(screen.getSurfaceContent('surfaceId')).toBe('surface');
		screen.allocateVirtualDocumentForContent(
			'<div id="surfaceId">surface</div>'
		);
		expect(screen.getSurfaceContent('surfaceIdInvalid')).toBe(undefined);
	});

	it('extracts surface content from response content default child if present', () => {
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<div id="surfaceId">static<div id="surfaceId-default">surface</div></div>'
		);
		expect(screen.getSurfaceContent('surfaceId')).toBe('surface');
		screen.allocateVirtualDocumentForContent(
			'<div id="surfaceId">static<div id="surfaceId-default">surface</div></div>'
		);
		expect(screen.getSurfaceContent('surfaceIdInvalid')).toBe(undefined);
	});

	it('releases virtual document after activate', () => {
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent('');
		expect(screen.virtualDocument).toBeTruthy();
		screen.activate();
		expect(screen.virtualDocument).toBeFalsy();
	});

	it('sets body id in virtual document to page body id', () => {
		var screen = new HtmlScreen();
		document.body.id = 'bodyAsSurface';
		screen.allocateVirtualDocumentForContent('<body>body</body>');
		screen.assertSameBodyIdInVirtualDocument();
		expect(screen.virtualDocument.querySelector('body').id).toBe(
			'bodyAsSurface'
		);
	});

	it('sets body id in virtual document to page body id even when it was already set', () => {
		var screen = new HtmlScreen();
		document.body.id = 'bodyAsSurface';
		screen.allocateVirtualDocumentForContent(
			'<body id="serverId">body</body>'
		);
		screen.assertSameBodyIdInVirtualDocument();
		expect(screen.virtualDocument.querySelector('body').id).toBe(
			'bodyAsSurface'
		);
	});

	it('sets body id in document and use the same in virtual document', () => {
		var screen = new HtmlScreen();
		document.body.id = '';
		screen.allocateVirtualDocumentForContent('<body>body</body>');
		screen.assertSameBodyIdInVirtualDocument();
		expect(document.body.id).toBeTruthy();
		expect(screen.virtualDocument.querySelector('body').id).toBe(
			document.body.id
		);
	});

	it('evaluates surface scripts', (done) => {
		enterDocumentSurfaceElement(
			'surfaceId',
			'<script>window.sentinel=true;</script>'
		);
		var surface = new Surface('surfaceId');
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent('');
		expect(window.sentinel).toBeFalsy();
		screen
			.evaluateScripts({
				surfaceId: surface,
			})
			.then(() => {
				expect(window.sentinel).toBe(true);
				delete window.sentinel;
				exitDocumentElement('surfaceId');
				done();
			});
	});

	it('evaluates surface styles', (done) => {
		enterDocumentSurfaceElement(
			'surfaceId',
			'<style id="temporaryStyle">body{background-color:rgb(0, 255, 0);}</style>'
		);
		var surface = new Surface('surfaceId');
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent('');
		screen
			.evaluateStyles({
				surfaceId: surface,
			})
			.then(() => {
				assertComputedStyle('backgroundColor', 'rgb(0, 255, 0)');
				exitDocumentElement('surfaceId');
				done();
			});
	});

	it('evaluates favicon', (done) => {
		enterDocumentSurfaceElement('surfaceId', '');
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<link rel="Shortcut Icon" href="/for/favicon.ico" />'
		);
		screen.evaluateFavicon_().then(() => {
			var element = document.querySelector('link[rel="Shortcut Icon"]');
			var uri = new URL(element.href);
			expect(uri.pathname).toBe('/for/favicon.ico');
			exitDocumentElement('surfaceId');
			done();
		});
	});

	it('always evaluates tracked favicon', (done) => {
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<link id="favicon" rel="Shortcut Icon" href="/for/favicon.ico" />'
		);
		screen.evaluateFavicon_().then(() => {
			expect(
				document.querySelector('link[rel="Shortcut Icon"]')
			).toBeTruthy();
			screen.allocateVirtualDocumentForContent(
				'<link id="favicon" rel="Shortcut Icon" href="/bar/favicon.ico" />'
			);
			screen.evaluateFavicon_({}).then(() => {
				var element = document.querySelector(
					'link[rel="Shortcut Icon"]'
				);
				expect(element).toBeTruthy();
				exitDocumentElement('favicon');
				done();
			});
		});
	});

	it('forces favicon to change whenever change the href when the browser is not IE', (done) => {
		enterDocumentSurfaceElement(
			'surfaceId',
			'<link rel="Shortcut Icon" href="/bar/favicon.ico" />'
		);
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<link rel="Shortcut Icon" href="/for/favicon.ico" />'
		);
		screen.evaluateFavicon_().then(() => {
			var element = document.querySelector('link[rel="Shortcut Icon"]');
			var uri = new URL(element.href);
			expect(uri.pathname).toBe('/for/favicon.ico');
			expect(uri.searchParams.has('q')).toBe(true);
			exitDocumentElement('surfaceId');
			done();
		});
	});

	it('always evaluates tracked temporary scripts', (done) => {
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<script data-senna-track="temporary">window.sentinel=true;</script>'
		);
		expect(window.sentinel).toBeFalsy();
		screen.evaluateScripts({}).then(() => {
			expect(window.sentinel).toBe(true);
			delete window.sentinel;
			screen.allocateVirtualDocumentForContent(
				'<script data-senna-track="temporary">window.sentinel=true;</script>'
			);
			screen.evaluateScripts({}).then(() => {
				expect(window.sentinel).toBe(true);
				delete window.sentinel;
				done();
			});
		});
	});

	it.skip('always evaluates tracked temporary styles', (done) => {
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<style id="temporaryStyle" data-senna-track="temporary">body{background-color:rgb(0, 255, 0);}</style>'
		);
		screen.evaluateStyles({}).then(() => {
			assertComputedStyle('backgroundColor', 'rgb(0, 255, 0)');
			screen.allocateVirtualDocumentForContent(
				'<style id="temporaryStyle" data-senna-track="temporary">body{background-color:rgb(255, 0, 0);}</style>'
			);
			screen.evaluateStyles({}).then(() => {
				assertComputedStyle('backgroundColor', 'rgb(255, 0, 0)');
				exitDocumentElement('temporaryStyle');
				done();
			});
		});
	});

	it('appends existing teporary styles with id in the same place as the reference', (done) => {
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<style id="temporaryStyle" data-senna-track="temporary">body{background-color:rgb(0, 255, 0);}</style>'
		);
		screen.evaluateStyles({}).then(() => {
			document.head.appendChild(
				buildFragment(
					'<style id="mainStyle">body{background-color:rgb(255, 255, 255);}</style>'
				)
			);
			assertComputedStyle('backgroundColor', 'rgb(255, 255, 255)');
			screen.allocateVirtualDocumentForContent(
				'<style id="temporaryStyle" data-senna-track="temporary">body{background-color:rgb(255, 0, 0);}</style>'
			);
			screen.evaluateStyles({}).then(() => {
				assertComputedStyle('backgroundColor', 'rgb(255, 255, 255)');
				exitDocumentElement('mainStyle');
				exitDocumentElement('temporaryStyle');
				done();
			});
		});
	});

	it('evaluates tracked permanent scripts only once', (done) => {
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<script id="permanentScriptKey" data-senna-track="permanent">window.sentinel=true;</script>'
		);
		expect(window.sentinel).toBeFalsy();
		screen.evaluateScripts({}).then(() => {
			expect(window.sentinel).toBe(true);
			delete window.sentinel;
			screen.allocateVirtualDocumentForContent(
				'<script id="permanentScriptKey" data-senna-track="permanent">window.sentinel=true;</script>'
			);
			screen.evaluateScripts({}).then(() => {
				expect(window.sentinel).toBeFalsy();
				done();
			});
		});
	});

	it('evaluates tracked permanent styles only once', (done) => {
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<style id="permanentStyle" data-senna-track="permanent">body{background-color:rgb(0, 255, 0);}</style>'
		);
		screen.evaluateStyles({}).then(() => {
			assertComputedStyle('backgroundColor', 'rgb(0, 255, 0)');
			screen.allocateVirtualDocumentForContent(
				'<style id="permanentStyle" data-senna-track="permanent">body{background-color:rgb(255, 0, 0);}</style>'
			);
			screen.evaluateStyles({}).then(() => {
				assertComputedStyle('backgroundColor', 'rgb(0, 255, 0)');
				exitDocumentElement('permanentStyle');
				done();
			});
		});
	});

	it('removes from document tracked pending styles on screen dispose', (done) => {
		var screen = new HtmlScreen();
		document.head.appendChild(
			buildFragment(
				'<style id="mainStyle">body{background-color:rgb(255, 255, 255);}</style>'
			)
		);
		screen.allocateVirtualDocumentForContent(
			'<style id="temporaryStyle" data-senna-track="temporary">body{background-color:rgb(0, 255, 0);}</style>'
		);
		screen.evaluateStyles({}).then(() => {
			assertComputedStyle('backgroundColor', 'rgb(255, 255, 255)');
			exitDocumentElement('mainStyle');
			done();
		});
		screen.dispose();
	});

	it('clears pendingStyles after screen activates', (done) => {
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<style id="temporaryStyle" data-senna-track="temporary"></style>'
		);
		screen.evaluateStyles({}).then(() => {
			expect(screen.pendingStyles).toBeFalsy();
			exitDocumentElement('temporaryStyle');
			done();
		});
		expect(screen.pendingStyles).toBeTruthy();
		screen.activate();
	});

	it('has correct title', (done) => {
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent('<title>left</title>');
		screen.resolveTitleFromVirtualDocument();
		screen.flip([]).then(() => {
			expect(screen.getTitle()).toBe('left');
			done();
		});
	});

	it('has correct title when the title contains html entities', (done) => {
		var screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<title>left &amp; right</title>'
		);
		screen.resolveTitleFromVirtualDocument();
		screen.flip([]).then(() => {
			expect(screen.getTitle()).toBe('left & right');
			done();
		});
	});
});

function enterDocumentSurfaceElement(surfaceId, opt_content) {
	document.body.appendChild(
		buildFragment(
			'<div id="' +
				surfaceId +
				'">' +
				(opt_content ? opt_content : '') +
				'</div>'
		)
	);

	return document.getElementById(surfaceId);
}

function exitDocumentElement(surfaceId) {
	return document.getElementById(surfaceId).remove();
}

function assertComputedStyle(property, value) {
	expect(window.getComputedStyle(document.body, null)[property]).toBe(value);
}
