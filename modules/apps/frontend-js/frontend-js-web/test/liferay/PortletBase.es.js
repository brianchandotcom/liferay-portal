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

import PortletBase from '../../src/main/resources/META-INF/resources/liferay/PortletBase.es';

describe('PortletBase', () => {
	const namespace = '_com_liferay_test_portlet_';
	let portletBase;

	afterEach(() => portletBase.dispose());

	beforeAll(() => {
		document.body.innerHTML = `
					<div class="foo" id="p_p_id_com_liferay_test_portlet_">
						<div class="foo" id="_com_liferay_test_portlet_child_container">
							<div class="foo" id="_com_liferay_test_portlet_grand_child_container"></div>
						</div>
					</div>

					<div class="foo" id="p_p_id_com_liferay_another_portlet_"></div>
				`;
	});

	beforeEach(() => {
		Liferay = {
			Util: {
				ns: jest.fn()
			}
		};

		portletBase = new PortletBase({
			namespace
		});
	});

	describe('PortletBase.all', () => {
		it('returns an empty list if no elements are found', () => {
			const elements = portletBase.all('.bar');

			expect(elements).not.toBeNull();
			expect(elements.length).toEqual(0);
		});

		it('gets all matching nodes within the root node tree', () => {
			expect(portletBase.all('.foo').length).toEqual(2);
			expect(
				portletBase.all(
					'.foo',
					'#_com_liferay_test_portlet_child_container'
				).length
			).toEqual(1);
		});

		it('uses the document as root node if one has not been specified or the default has not been found', () => {
			portletBase = new PortletBase({
				namespace: '_com_liferay_unknown_portlet'
			});

			expect(portletBase.all('.foo').length).toEqual(4);
		});
	});

	describe('PortletBase.fetch', () => {
		let globalFetch;
		let sampleBody;
		const sampleUrl = 'http://sampleurl.com';

		beforeEach(() => {
			globalFetch = global.fetch;
			portletBase.ns = obj => obj;
		});

		afterEach(() => {
			global.fetch = globalFetch;
		});

		it('makes the request to the given url', done => {
			global.fetch = jest.fn(url => {
				expect(url).toBe(sampleUrl);
				done();
			});

			portletBase.fetch(sampleUrl, sampleBody);
		});

		it('adds credentials option to the request', done => {
			global.fetch = jest.fn((url, options) => {
				expect(options.credentials).toBe('include');
				done();
			});

			portletBase.fetch(sampleUrl, sampleBody);
		});

		it('adds the POST method option to the request', done => {
			global.fetch = jest.fn((url, options) => {
				expect(options.method).toBe('POST');
				done();
			});

			portletBase.fetch(sampleUrl, sampleBody);
		});

		it('adds the given body to the request', done => {
			global.fetch = jest.fn((url, options) => {
				expect(options.body).toBe(sampleBody);
				done();
			});

			sampleBody = 'sample body';
			portletBase.fetch(sampleUrl, sampleBody);
		});

		it('transforms the given body using getRequestBody_', done => {
			portletBase.getRequestBody_ = jest.fn();

			global.fetch = jest.fn(() => {
				expect(portletBase.getRequestBody_.mock.calls).toEqual([
					[sampleBody]
				]);
				done();
			});

			expect(portletBase.getRequestBody_.mock.calls.length).toBe(0);

			portletBase.fetch(sampleUrl, sampleBody);
		});
	});

	describe('PortletBase.getRequestBody_', () => {
		it('keeps body intact if it is already a FormData instance', () => {
			const sampleFormData = new FormData();

			expect(portletBase.getRequestBody_(sampleFormData)).toBe(
				sampleFormData
			);
		});

		it('creates a FormData instance from a given HTMLFormElement', () => {
			const sampleFormElement = document.createElement('form');

			sampleFormElement.innerHTML = `
							<input name="field1" value="value1" />
							<input name="field2" value="value2" />
						`;

			const sampleFormData = new FormData(sampleFormElement);

			const resultFormData = portletBase.getRequestBody_(
				sampleFormElement
			);

			expect(resultFormData.get('field1')).toBe(
				sampleFormData.get('field1')
			);
			expect(resultFormData.get('field2')).toBe(
				sampleFormData.get('field2')
			);
		});

		it('appends all object keys inside a new FormData element', () => {
			portletBase.ns = obj => obj;

			const sampleBody = {
				fieldA: 'valueA',
				fieldB: 'valueB'
			};

			const resultFormData = portletBase.getRequestBody_(sampleBody);

			expect(resultFormData.get('fieldA')).toBe(sampleBody.fieldA);
			expect(resultFormData.get('fieldB')).toBe(sampleBody.fieldB);
		});
	});

	describe('PortletBase.ns', () => {
		it('namespaces objects with the portlet namespace using the provided Liferay.Util.ns helper', () => {
			portletBase.ns('test');

			expect(Liferay.Util.ns.mock.calls.length).toBe(1);
		});
	});

	describe('PortletBase.one', () => {
		it('returns null if no element is found', () => {
			expect(portletBase.one('.bar')).toBeNull();
		});

		it('gets the first matching node within the root node tree', () => {
			expect(portletBase.one('.foo')).toEqual(
				document.getElementById(
					'_com_liferay_test_portlet_child_container'
				)
			);

			expect(
				portletBase.one(
					'.foo',
					'#_com_liferay_test_portlet_child_container'
				)
			).toEqual(
				document.getElementById(
					'_com_liferay_test_portlet_grand_child_container'
				)
			);
		});

		it('uses the document as root node if one has not been specified or the default has not been found', () => {
			portletBase = new PortletBase({
				namespace: '_com_liferay_unknown_portlet'
			});

			expect(portletBase.one('.foo')).toEqual(
				document.getElementById('p_p_id_com_liferay_test_portlet_')
			);
		});
	});

	describe('PortletBase.rootNode', () => {
		it('sets the root node by default', () => {
			expect(portletBase.rootNode).toEqual(
				document.getElementById('p_p_id' + namespace)
			);
		});

		it('uses a specified root node', () => {
			portletBase.rootNode = '#' + namespace + 'child_container';

			expect(portletBase.rootNode).toEqual(
				document.getElementById(namespace + 'child_container')
			);
		});

		it('overrides the default root node if specified', () => {
			portletBase = new PortletBase({
				namespace,
				rootNode: '#' + namespace + 'child_container'
			});

			expect(portletBase.rootNode).toEqual(
				document.getElementById(namespace + 'child_container')
			);
		});
	});
});
