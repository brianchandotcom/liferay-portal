/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {buildFragment} from 'frontend-js-web';

import Screen from '../../src/main/resources/META-INF/resources/screen/Screen';
import Surface from '../../src/main/resources/META-INF/resources/surface/Surface';

describe('Screen', () => {
	beforeAll(() => {
		window.Liferay.CSP = {
			nonce: '',
		};
		window.Liferay.DOMTaskRunner = {
			runTasks: jest.fn(),
		};
	});

	it('exposes lifecycle activate', () => {
		expect(() => {
			new Screen().activate();
		}).not.toThrow();
	});

	it('exposes lifecycle deactivate', () => {
		expect(() => {
			new Screen().deactivate();
		}).not.toThrow();
	});

	it('exposes lifecycle beforeActivate', () => {
		expect(() => {
			new Screen().beforeActivate();
		}).not.toThrow();
	});

	it('exposes lifecycle beforeDeactivate', () => {
		expect(() => {
			new Screen().beforeDeactivate();
		}).not.toThrow();
	});

	it('exposes lifecycle load', () => {
		expect(() => {
			new Screen().load();
		}).not.toThrow();
	});

	it('exposes lifecycle getSurfaceContent', () => {
		expect(() => {
			new Screen().getSurfaceContent();
		}).not.toThrow();
	});

	it('exposes lifecycle dispose', () => {
		expect(() => {
			new Screen().dispose();
		}).not.toThrow();
	});

	it('exposes lifecycle flip', () => {
		expect(() => {
			new Screen().flip({});
		}).not.toThrow();
	});

	it('waits to flip all surfaces', (done) => {
		const surfaces = {
			surface1: new Surface('surface1'),
			surface2: new Surface('surface2'),
		};
		const stub1 = jest.fn();
		const stub2 = jest.fn();

		surfaces.surface1.show = () => {
			stub1();

			return Promise.resolve();
		};
		surfaces.surface2.show = () => {
			stub2();

			return Promise.resolve();
		};

		new Screen().flip(surfaces).then(() => {
			expect(stub1).toHaveBeenCalledTimes(1);
			expect(stub2).toHaveBeenCalledTimes(1);
			done();
		});
	});

	it('gets screen id', () => {
		const screen = new Screen();
		expect(screen.getId()).toBeTruthy();
		screen.setId('otherId');
		expect(screen.getId()).toBe('otherId');
	});

	it('gets screen title', () => {
		const screen = new Screen();
		expect(screen.getTitle()).toBeNull();
		screen.setTitle('other');
		expect(screen.getTitle()).toBe('other');
	});

	it('checks if object implements a screen', () => {
		expect(Screen.isImplementedBy(new Screen())).toBe(true);
	});

	it('evaluates surface scripts', (done) => {
		enterDocumentSurfaceElement(
			'surfaceId',
			'<script>window.sentinel=true;</script>'
		);
		const surface = new Surface('surfaceId');
		const screen = new Screen();
		expect(window.sentinel).toBeFalsy();
		screen
			.evaluateScripts({
				surfaceId: surface,
			})
			.then(() => {
				expect(window.sentinel).toBe(true);
				delete window.sentinel;
				exitDocumentSurfaceElement('surfaceId');
				done();
			});
	});

	it('evaluates surface styles', (done) => {
		enterDocumentSurfaceElement(
			'surfaceId',
			'<style>body{background-color:rgb(0, 255, 0);}</style>'
		);
		const surface = new Surface('surfaceId');
		const screen = new Screen();
		screen
			.evaluateStyles({
				surfaceId: surface,
			})
			.then(() => {
				assertComputedStyle('backgroundColor', 'rgb(0, 255, 0)');
				exitDocumentSurfaceElement('surfaceId');
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

function exitDocumentSurfaceElement(surfaceId) {
	return document.getElementById(surfaceId).remove();
}

function assertComputedStyle(property, value) {
	expect(window.getComputedStyle(document.body, null)[property]).toBe(value);
}
