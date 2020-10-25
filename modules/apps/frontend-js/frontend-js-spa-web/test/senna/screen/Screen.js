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

'use strict';

import dom from 'metal-dom';
import CancellablePromise from 'metal-promise';

import Screen from '../../../src/main/resources/META-INF/resources/senna/screen/Screen';
import Surface from '../../../src/main/resources/META-INF/resources/senna/surface/Surface';

describe('Screen', () => {
	before(() => {

		// Prevent log messages from showing up in test output.

		sinon.stub(console, 'log');
	});

	after(() => {
		console.log.restore();
	});

	it('should expose lifecycle activate', () => {
		assert.doesNotThrow(() => {
			new Screen().activate();
		});
	});

	it('should expose lifecycle deactivate', () => {
		assert.doesNotThrow(() => {
			new Screen().deactivate();
		});
	});

	it('should expose lifecycle beforeActivate', () => {
		assert.doesNotThrow(() => {
			new Screen().beforeActivate();
		});
	});

	it('should expose lifecycle beforeDeactivate', () => {
		assert.doesNotThrow(() => {
			new Screen().beforeDeactivate();
		});
	});

	it('should expose lifecycle load', () => {
		assert.doesNotThrow(() => {
			new Screen().load();
		});
	});

	it('should expose lifecycle getSurfaceContent', () => {
		assert.doesNotThrow(() => {
			new Screen().getSurfaceContent();
		});
	});

	it('should expose lifecycle dispose', () => {
		assert.doesNotThrow(() => {
			new Screen().dispose();
		});
	});

	it('should expose lifecycle flip', () => {
		assert.doesNotThrow(() => {
			new Screen().flip({});
		});
	});

	it('should wait to flip all surfaces', (done) => {
		var surfaces = {
			surface1: new Surface('surface1'),
			surface2: new Surface('surface2'),
		};
		var stub1 = sinon.stub();
		var stub2 = sinon.stub();
		surfaces.surface1.show = () => {
			stub1();

			return CancellablePromise.resolve();
		};
		surfaces.surface2.show = () => {
			stub2();

			return CancellablePromise.resolve();
		};
		new Screen().flip(surfaces).then(() => {
			assert.strictEqual(1, stub1.callCount);
			assert.strictEqual(1, stub2.callCount);
			done();
		});
	});

	it('should get screen id', () => {
		var screen = new Screen();
		assert.ok(screen.getId());
		screen.setId('otherId');
		assert.strictEqual('otherId', screen.getId());
	});

	it('should get screen title', () => {
		var screen = new Screen();
		assert.strictEqual(null, screen.getTitle());
		screen.setTitle('other');
		assert.strictEqual('other', screen.getTitle());
	});

	it('should check if object implements a screen', () => {
		assert.ok(Screen.isImplementedBy(new Screen()));
	});

	it('should evaluate surface scripts', (done) => {
		enterDocumentSurfaceElement(
			'surfaceId',
			'<script>window.sentinel=true;</script>'
		);
		var surface = new Surface('surfaceId');
		var screen = new Screen();
		assert.ok(!window.sentinel);
		screen
			.evaluateScripts({
				surfaceId: surface,
			})
			.then(() => {
				assert.ok(window.sentinel);
				delete window.sentinel;
				exitDocumentSurfaceElement('surfaceId');
				done();
			});
	});

	it('should evaluate surface styles', (done) => {
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
	dom.enterDocument(
		'<div id="' +
			surfaceId +
			'">' +
			(opt_content ? opt_content : '') +
			'</div>'
	);

	return document.getElementById(surfaceId);
}

function exitDocumentSurfaceElement(surfaceId) {
	return dom.exitDocument(document.getElementById(surfaceId));
}

function assertComputedStyle(property, value) {
	assert.strictEqual(
		value,
		window.getComputedStyle(document.body, null)[property]
	);
}
