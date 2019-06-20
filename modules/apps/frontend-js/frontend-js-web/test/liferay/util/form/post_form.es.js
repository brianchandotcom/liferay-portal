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

/* eslint no-undef: "warn" */

'use strict';

import dom from 'metal-dom';
import postForm from '../../../../src/main/resources/META-INF/resources/liferay/util/form/post_form.es';
import getFormElement from '../../../../src/main/resources/META-INF/resources/liferay/util/form/get_form_element.es';

describe('Liferay.Util.postForm', () => {
	afterEach(() => {
		global.submitForm.mockRestore();
	});

	beforeEach(() => {
		global.submitForm = jest.fn();
	});

	it('should do nothing if the form parameter is not a form node', () => {
		const fragment = dom.buildFragment('<div />');

		postForm(undefined);
		postForm(fragment.firstElementChild);

		expect(global.submitForm.mock.calls.length).toBe(0);
	});

	it('should submit form even if options parameter is not set', () => {
		const fragment = dom.buildFragment('<form />');

		const form = fragment.firstElementChild;

		postForm(form);

		expect(global.submitForm.mock.calls.length).toBe(1);
	});

	it('should do nothing if the url optional parameter is not a string', () => {
		const fragment = dom.buildFragment('<form />');

		const form = fragment.firstElementChild;

		postForm(form, {url: undefined});
		postForm(form, {url: {}});

		expect(global.submitForm.mock.calls.length).toBe(0);
	});

	it('should do nothing if the data optional parameter is not an object', () => {
		const fragment = dom.buildFragment('<form />');

		const form = fragment.firstElementChild;

		postForm(form, {data: undefined});
		postForm(form, {data: 'abc'});

		expect(global.submitForm.mock.calls.length).toBe(0);
	});

	it('should set given element values in data parameter, and submit form to a given url', () => {
		const fragment = dom.buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
						<input name="_com_liferay_test_portlet_bar" type="text" value="123">
					</form>
				`);

		const form = fragment.firstElementChild;

		postForm(form, {
			data: {
				foo: 'def',
				bar: '456'
			},
			url: 'http://sampleurl.com'
		});

		const fooElement = getFormElement(form, 'foo');
		const barElement = getFormElement(form, 'bar');

		expect(fooElement.value).toEqual('def');
		expect(barElement.value).toEqual('456');

		expect(global.submitForm.mock.calls.length).toBe(1);
	});
});
