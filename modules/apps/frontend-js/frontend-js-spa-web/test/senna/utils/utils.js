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

import Uri from 'metal-uri';

import globals from '../../../src/main/resources/META-INF/resources/senna/globals/globals';
import utils from '../../../src/main/resources/META-INF/resources/senna/utils/utils';

describe('utils', () => {
	before(() => {
		globals.window = {
			location: {
				hostname: 'hostname',
				pathname: '/path',
				search: '?a=1',
				hash: '#hash',
			},
			history: {
				pushState: 1,
			},
		};
	});

	after(() => {
		globals.window = window;
	});

	it('should copy attributes from source node to target node', () => {
		var nodeA = document.createElement('div');
		nodeA.setAttribute('a', 'valueA');
		nodeA.setAttribute('b', 'valueB');

		var nodeB = document.createElement('div');
		utils.copyNodeAttributes(nodeA, nodeB);

		assert.strictEqual(nodeA.attributes.length, nodeB.attributes.length);
		assert.strictEqual(nodeA.getAttribute('a'), nodeB.getAttribute('a'));
		assert.strictEqual(nodeA.getAttribute('b'), nodeB.getAttribute('b'));
		assert.strictEqual(nodeB.getAttribute('a'), 'valueA');
		assert.strictEqual(nodeB.getAttribute('b'), 'valueB');
	});

	it('should clear attributes from a given node', () => {
		var node = document.createElement('div');
		node.setAttribute('a', 'valueA');
		node.setAttribute('b', 'valueB');

		utils.clearNodeAttributes(node);

		assert.strictEqual(node.getAttribute('a'), null);
		assert.strictEqual(node.getAttribute('b'), null);
		assert.strictEqual(node.attributes.length, 0);
	});

	it('should get path from url', () => {
		assert.strictEqual(
			'/path?a=1#hash',
			utils.getUrlPath('http://hostname/path?a=1#hash')
		);
	});

	it('should get path from url excluding hashbang', () => {
		assert.strictEqual(
			'/path?a=1',
			utils.getUrlPathWithoutHash('http://hostname/path?a=1#hash')
		);
	});

	it('should get path from url excluding hashbang and search', () => {
		assert.strictEqual(
			'/path',
			utils.getUrlPathWithoutHashAndSearch(
				'http://hostname/path?a=1#hash'
			)
		);
	});

	it('should test if path is current browser path', () => {
		assert.ok(utils.isCurrentBrowserPath('http://hostname/path?a=1'));
		assert.ok(utils.isCurrentBrowserPath('http://hostname/path?a=1#hash'));
		assert.ok(!utils.isCurrentBrowserPath('http://hostname/path1?a=1'));
		assert.ok(
			!utils.isCurrentBrowserPath('http://hostname/path1?a=1#hash')
		);
		assert.ok(!utils.isCurrentBrowserPath());
	});

	it('should get current browser path', () => {
		assert.strictEqual('/path?a=1#hash', utils.getCurrentBrowserPath());
	});

	it('should get current browser path excluding hashbang', () => {
		assert.strictEqual(
			'/path?a=1',
			utils.getCurrentBrowserPathWithoutHash()
		);
	});

	it('should test if Html5 history is supported', () => {
		assert.ok(utils.isHtml5HistorySupported());
		globals.window.history = null;
		assert.ok(!utils.isHtml5HistorySupported());
	});

	it('should test if a given url is a valid web (http/https) uri', () => {
		assert.ok(
			!utils.isWebUri('tel:+999999999'),
			'tel:+999999999 is not a valid url'
		);
		assert.instanceOf(utils.isWebUri('http://localhost:12345'), Uri);
	});
});
