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

import Screen from '../../src/main/resources/META-INF/resources/screen/Screen';
import Surface from '../../src/main/resources/META-INF/resources/surface/Surface';

describe('Screen', () => {
	beforeAll(() => {
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
		var surfaces = {
			surface1: new Surface('surface1'),
			surface2: new Surface('surface2'),
		};
		var stub1 = jest.fn();
		var stub2 = jest.fn();

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
		var screen = new Screen();
		expect(screen.getId()).toBeTruthy();
		screen.setId('otherId');
		expect(screen.getId()).toBe('otherId');
	});

	it('gets screen title', () => {
		var screen = new Screen();
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
		var surface = new Surface('surfaceId');
		var screen = new Screen();
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
		var surface = new Surface('surfaceId');
		var screen = new Screen();
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
