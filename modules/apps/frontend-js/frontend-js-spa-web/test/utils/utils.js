/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import {
	clearNodeAttributes,
	copyNodeAttributes,
	getCurrentBrowserPath,
	getCurrentBrowserPathWithoutHash,
	getUrlPath,
	getUrlPathWithoutHash,
	getUrlPathWithoutHashAndSearch,
	isCurrentBrowserPath,
} from '../../src/main/resources/META-INF/resources/util/utils';

describe('utils', () => {
	const oldWindowLocation = window.location;

	beforeAll(() => {
		delete window.location;

		window.location = Object.defineProperties(
			{},
			{
				...Object.getOwnPropertyDescriptors(oldWindowLocation),

				hash: {
					configurable: true,
					value: '#hash',
				},

				hostname: {
					configurable: true,
					value: 'hostname',
				},

				origin: {
					configurable: true,
					value: 'http://localhost',
				},

				pathname: {
					configurable: true,
					value: '/path',
				},

				search: {
					configurable: true,
					value: '?a=1',
				},
			}
		);
	});

	afterAll(() => {
		window.location = oldWindowLocation;
	});

	it('copies attributes from source node to target node', () => {
		const nodeA = document.createElement('div');
		nodeA.setAttribute('a', 'valueA');
		nodeA.setAttribute('b', 'valueB');

		const nodeB = document.createElement('div');
		copyNodeAttributes(nodeA, nodeB);

		expect(nodeA.attributes.length).toBe(nodeB.attributes.length);
		expect(nodeA.getAttribute('a')).toBe(nodeB.getAttribute('a'));
		expect(nodeA.getAttribute('b')).toBe(nodeB.getAttribute('b'));
		expect(nodeB.getAttribute('a')).toBe('valueA');
		expect(nodeB.getAttribute('b')).toBe('valueB');
	});

	it('clears attributes from a given node', () => {
		const node = document.createElement('div');
		node.setAttribute('a', 'valueA');
		node.setAttribute('b', 'valueB');

		clearNodeAttributes(node);

		expect(node.getAttribute('a')).toBeNull();
		expect(node.getAttribute('b')).toBeNull();
		expect(node.attributes.length).toBe(0);
	});

	it('gets path from url', () => {
		expect(getUrlPath('http://hostname/path?a=1#hash')).toBe(
			'/path?a=1#hash'
		);
	});

	it('gets path from url excluding hashbang', () => {
		expect(getUrlPathWithoutHash('http://hostname/path?a=1#hash')).toBe(
			'/path?a=1'
		);
	});

	it('gets path from url excluding hashbang and search', () => {
		expect(
			getUrlPathWithoutHashAndSearch('http://hostname/path?a=1#hash')
		).toBe('/path');
	});

	it('tests if path is current browser path', () => {
		expect(isCurrentBrowserPath('http://hostname/path?a=1')).toBeTruthy();
		expect(
			isCurrentBrowserPath('http://hostname/path?a=1#hash')
		).toBeTruthy();
		expect(!isCurrentBrowserPath('http://hostname/path1?a=1')).toBeTruthy();
		expect(
			!isCurrentBrowserPath('http://hostname/path1?a=1#hash')
		).toBeTruthy();
		expect(!isCurrentBrowserPath()).toBeTruthy();
	});

	it('gets current browser path', () => {
		expect(getCurrentBrowserPath()).toBe('/path?a=1#hash');
	});

	it('gets current browser path excluding hashbang', () => {
		expect(getCurrentBrowserPathWithoutHash()).toBe('/path?a=1');
	});
});
