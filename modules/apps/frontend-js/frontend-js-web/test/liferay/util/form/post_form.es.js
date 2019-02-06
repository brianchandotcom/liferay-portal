'use strict';

import dom from 'metal-dom';
import postForm from '../../../../src/main/resources/META-INF/resources/liferay/util/form/post_form.es';
import getFormElement from '../../../../src/main/resources/META-INF/resources/liferay/util/form/get_form_element.es';

describe(
	'Liferay.Util.postForm',
	() => {
		let sampleUrl = 'http://sampleurl.com';

		afterEach(
			() => {
				global.submitForm.mockRestore();
			}
		);

		beforeEach(
			() => {
				global.submitForm = jest.fn();
			}
		);

		it(
			'should do nothing if the form parameter is not a form node',
			() => {
				let fragment = dom.buildFragment('<div />');

				postForm(undefined, sampleUrl);
				postForm(fragment.firstElementChild, sampleUrl);

				expect(global.submitForm.mock.calls.length).toBe(0);
			}
		);

		it(
			'should do nothing if the url parameter is not a string',
			() => {
				let fragment = dom.buildFragment('<form />');

				let form = fragment.firstElementChild;

				postForm(form, undefined);
				postForm(form, {});

				expect(global.submitForm.mock.calls.length).toBe(0);
			}
		);

		it(
			'should submit form if the form and url parameters are set, even if the data parameter is not set',
			() => {
				let fragment = dom.buildFragment('<form />');

				let form = fragment.firstElementChild;

				postForm(form, sampleUrl);

				expect(global.submitForm.mock.calls.length).toBe(1);
			}
		);

		it(
			'should set given element values in data parameter, and submit form',
			() => {
				let fragment = dom.buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
						<input name="_com_liferay_test_portlet_bar" type="text" value="123">
					</form>
				`);

				let form = fragment.firstElementChild;

				postForm(
					form,
					sampleUrl,
					{
						foo: 'def',
						bar: '456'
					}
				);

				const fooElement = getFormElement(form, 'foo');
				const barElement = getFormElement(form, 'bar');

				expect(fooElement.value).toEqual('def');
				expect(barElement.value).toEqual('456');

				expect(global.submitForm.mock.calls.length).toBe(1);
			}
		);
	}
);