/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import buildFragment from '../../../../src/main/resources/META-INF/resources/liferay/util/build_fragment';
import getFormElement from '../../../../src/main/resources/META-INF/resources/liferay/util/form/get_form_element.es';
import postForm from '../../../../src/main/resources/META-INF/resources/liferay/util/form/post_form.es';

describe('Liferay.Util.postForm', () => {
	afterEach(() => {
		global.submitForm.mockRestore();
	});

	beforeEach(() => {
		global.submitForm = jest.fn();
	});

	it('does nothing if the form parameter is not a form node', () => {
		const fragment = buildFragment('<div />');

		postForm(undefined);
		postForm(fragment.firstElementChild);

		expect(global.submitForm.mock.calls.length).toBe(0);
	});

	it('submits form even if options parameter is not set', () => {
		const fragment = buildFragment('<form />');

		const form = fragment.firstElementChild;

		postForm(form);

		expect(global.submitForm.mock.calls.length).toBe(1);
	});

	it('does nothing if the url optional parameter is not a string', () => {
		const fragment = buildFragment('<form />');

		const form = fragment.firstElementChild;

		postForm(form, {url: undefined});
		postForm(form, {url: {}});

		expect(global.submitForm.mock.calls.length).toBe(0);
	});

	it('does nothing if the data optional parameter is not an object', () => {
		const fragment = buildFragment('<form />');

		const form = fragment.firstElementChild;

		postForm(form, {data: undefined});
		postForm(form, {data: 'abc'});

		expect(global.submitForm.mock.calls.length).toBe(0);
	});

	it('sets given element values in data parameter, and submit form to a given url', () => {
		const fragment = buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
						<input name="_com_liferay_test_portlet_bar" type="text" value="123">
					</form>
				`);

		const form = fragment.firstElementChild;

		postForm(form, {
			data: {
				bar: '456',
				foo: 'def',
			},
			url: 'http://sampleurl.com',
		});

		const barElement = getFormElement(form, 'bar');
		const fooElement = getFormElement(form, 'foo');

		expect(fooElement.value).toEqual('def');
		expect(barElement.value).toEqual('456');

		expect(global.submitForm.mock.calls.length).toBe(1);
	});
});
