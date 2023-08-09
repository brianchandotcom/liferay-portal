/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {buildFragment} from 'frontend-js-web';

import HtmlScreen from '../../src/main/resources/META-INF/resources/screen/HtmlScreen';
import Surface from '../../src/main/resources/META-INF/resources/surface/Surface';

describe('HtmlScreen', () => {
	beforeAll(() => {
		window.Liferay.DOMTaskRunner = {
			runTasks: jest.fn(),
		};
		window.Liferay.CSP = {
			nonce: '',
		};
	});

	beforeEach(() => {
		Liferay.SPA = {};
	});

	it('gets title selector', () => {
		const screen = new HtmlScreen();
		expect(screen.getTitleSelector()).toBe('title');
		screen.setTitleSelector('div.title');
		expect(screen.getTitleSelector()).toBe('div.title');
	});

	it('returns loaded content', (done) => {
		fetch.mockResponse('content');

		const screen = new HtmlScreen();
		screen.load('/url').then((content) => {
			expect(content).toBe('content');
			done();
		});
	});

	it('sets title from response content', (done) => {
		fetch.mockResponse('<title>new</title>');

		const screen = new HtmlScreen();
		screen.load('/url').then(() => {
			expect(screen.getTitle()).toBe('new');
			done();
		});
	});

	it('does not set title from response content if not present', (done) => {
		fetch.mockResponse('content');

		const screen = new HtmlScreen();
		screen.load('/url').then(() => {
			expect(screen.getTitle()).toBeNull();
			done();
		});
	});

	it.skip('cancels load request to an url', (done) => {
		fetch.mockResponse('');

		const screen = new HtmlScreen();
		screen
			.load('/url')
			.catch((reason) => {
				expect(reason).toBeInstanceOf(Error);
				done();
			})
			.cancel();
	});

	it('copies surface root node attributes from response content', (done) => {
		const screen = new HtmlScreen();
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
		const screen = new HtmlScreen();
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
		const screen = new HtmlScreen();
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
		const screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent('');
		expect(screen.virtualDocument).toBeTruthy();
		screen.activate();
		expect(screen.virtualDocument).toBeFalsy();
	});

	it('sets body id in virtual document to page body id', () => {
		const screen = new HtmlScreen();
		document.body.id = 'bodyAsSurface';
		screen.allocateVirtualDocumentForContent('<body>body</body>');
		screen.assertSameBodyIdInVirtualDocument();
		expect(screen.virtualDocument.querySelector('body').id).toBe(
			'bodyAsSurface'
		);
	});

	it('sets body id in virtual document to page body id even when it was already set', () => {
		const screen = new HtmlScreen();
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
		const screen = new HtmlScreen();
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
		const surface = new Surface('surfaceId');
		const screen = new HtmlScreen();
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
		const surface = new Surface('surfaceId');
		const screen = new HtmlScreen();
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
		const screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<link rel="Shortcut Icon" href="/for/favicon.ico" />'
		);
		screen.evaluateFavicon_().then(() => {
			const element = document.querySelector('link[rel="Shortcut Icon"]');
			const uri = new URL(element.href);
			expect(uri.pathname).toBe('/for/favicon.ico');
			exitDocumentElement('surfaceId');
			done();
		});
	});

	it('always evaluates tracked favicon', (done) => {
		const screen = new HtmlScreen();
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
				const element = document.querySelector(
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
		const screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent(
			'<link rel="Shortcut Icon" href="/for/favicon.ico" />'
		);
		screen.evaluateFavicon_().then(() => {
			const element = document.querySelector('link[rel="Shortcut Icon"]');
			const uri = new URL(element.href);
			expect(uri.pathname).toBe('/for/favicon.ico');
			expect(uri.searchParams.has('q')).toBe(true);
			exitDocumentElement('surfaceId');
			done();
		});
	});

	it('always evaluates tracked temporary scripts', (done) => {
		const screen = new HtmlScreen();
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
		const screen = new HtmlScreen();
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
		const screen = new HtmlScreen();
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
		const screen = new HtmlScreen();
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
		const screen = new HtmlScreen();
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
		const screen = new HtmlScreen();
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
		const screen = new HtmlScreen();
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
		const screen = new HtmlScreen();
		screen.allocateVirtualDocumentForContent('<title>left</title>');
		screen.resolveTitleFromVirtualDocument();
		screen.flip([]).then(() => {
			expect(screen.getTitle()).toBe('left');
			done();
		});
	});

	it('has correct title when the title contains html entities', (done) => {
		const screen = new HtmlScreen();
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
