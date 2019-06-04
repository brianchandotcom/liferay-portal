'use strict';

import dom from 'metal-dom';
import getFormElement from '../../../../src/main/resources/META-INF/resources/liferay/util/form/get_form_element.es';

describe('Liferay.Util.getFormElement', () => {
	it('should return null if the form parameter is not a form node', () => {
		let fragment = dom.buildFragment('<div />');

		let form = fragment.firstElementChild;

		expect(getFormElement(undefined, 'foo')).toEqual(null);
		expect(getFormElement(form, 'foo')).toEqual(null);
	});

	it('should return null if the elementName parameter is not a string', () => {
		let fragment = dom.buildFragment('<form />');

		let form = fragment.firstElementChild;

		expect(getFormElement(form, undefined)).toEqual(null);
		expect(getFormElement(form, {})).toEqual(null);
	});

	it('should return null if the element does not exist withing the form', () => {
		let fragment = dom.buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
					</form>
				`);

		let form = fragment.firstElementChild;

		expect(getFormElement(form, 'bar')).toEqual(null);
	});

	it('should return element value if the element does exist withing the form', () => {
		let fragment = dom.buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
					</form>
				`);

		let form = fragment.firstElementChild;

		const formElement = getFormElement(form, 'foo');

		expect(formElement.value).toEqual('abc');
	});
});
