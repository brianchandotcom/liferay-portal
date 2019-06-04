'use strict';

import dom from 'metal-dom';
import getFormElement from '../../../../src/main/resources/META-INF/resources/liferay/util/form/get_form_element.es';
import setFormValues from '../../../../src/main/resources/META-INF/resources/liferay/util/form/set_form_values.es';

describe('Liferay.Util.setFormValues', () => {
	it('should set the given values of form elements', () => {
		let fragment = dom.buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
						<input name="_com_liferay_test_portlet_bar" type="text" value="123">
					</form>
				`);

		let form = fragment.firstElementChild;

		setFormValues(form, {
			foo: 'def',
			bar: '456'
		});

		const fooElement = getFormElement(form, 'foo');
		const barElement = getFormElement(form, 'bar');

		expect(fooElement.value).toEqual('def');
		expect(barElement.value).toEqual('456');
	});
});
