/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import buildFragment from '../../../../src/main/resources/META-INF/resources/liferay/util/build_fragment';
import getFormElement from '../../../../src/main/resources/META-INF/resources/liferay/util/form/get_form_element.es';
import setFormValues from '../../../../src/main/resources/META-INF/resources/liferay/util/form/set_form_values.es';

describe('Liferay.Util.setFormValues', () => {
	it('sets the given values of form elements', () => {
		const fragment = buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
						<input name="_com_liferay_test_portlet_bar" type="text" value="123">
					</form>
				`);

		const form = fragment.firstElementChild;

		setFormValues(form, {
			bar: '456',
			foo: 'def',
		});

		const barElement = getFormElement(form, 'bar');
		const fooElement = getFormElement(form, 'foo');

		expect(barElement.value).toEqual('456');
		expect(fooElement.value).toEqual('def');
	});
});
