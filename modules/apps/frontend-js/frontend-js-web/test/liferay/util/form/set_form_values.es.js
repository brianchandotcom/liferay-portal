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

import dom from 'metal-dom';

import getFormElement from '../../../../src/main/resources/META-INF/resources/liferay/util/form/get_form_element.es';
import setFormValues from '../../../../src/main/resources/META-INF/resources/liferay/util/form/set_form_values.es';

describe('Liferay.Util.setFormValues', () => {
	it('sets the given values of form elements', () => {
		const fragment = dom.buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
						<input name="_com_liferay_test_portlet_bar" type="text" value="123">
					</form>
				`);

		const form = fragment.firstElementChild;

		setFormValues(form, {
			bar: '456',
			foo: 'def'
		});

		const barElement = getFormElement(form, 'bar');
		const fooElement = getFormElement(form, 'foo');

		expect(barElement.value).toEqual('456');
		expect(fooElement.value).toEqual('def');
	});
});
