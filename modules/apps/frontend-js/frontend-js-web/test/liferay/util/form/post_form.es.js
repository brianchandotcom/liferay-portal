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
		let fragment = dom.buildFragment('<div />');

		postForm(undefined);
		postForm(fragment.firstElementChild);

		expect(global.submitForm.mock.calls.length).toBe(0);
	});

	it('should submit form even if options parameter is not set', () => {
		let fragment = dom.buildFragment('<form />');

		let form = fragment.firstElementChild;

		postForm(form);

		expect(global.submitForm.mock.calls.length).toBe(1);
	});

	it('should do nothing if the url optional parameter is not a string', () => {
		let fragment = dom.buildFragment('<form />');

		let form = fragment.firstElementChild;

		postForm(form, {url: undefined});
		postForm(form, {url: {}});

		expect(global.submitForm.mock.calls.length).toBe(0);
	});

	it('should do nothing if the data optional parameter is not an object', () => {
		let fragment = dom.buildFragment('<form />');

		let form = fragment.firstElementChild;

		postForm(form, {data: undefined});
		postForm(form, {data: 'abc'});

		expect(global.submitForm.mock.calls.length).toBe(0);
	});

	it('should set given element values in data parameter, and submit form to a given url', () => {
		let fragment = dom.buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
						<input name="_com_liferay_test_portlet_bar" type="text" value="123">
					</form>
				`);

		let form = fragment.firstElementChild;

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
