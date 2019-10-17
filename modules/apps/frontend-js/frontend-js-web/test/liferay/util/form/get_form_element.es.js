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

describe('Liferay.Util.getFormElement', () => {
	it('returns null if the form parameter is not a form node', () => {
		const fragment = dom.buildFragment('<div />');

		const form = fragment.firstElementChild;

		expect(getFormElement(undefined, 'foo')).toEqual(null);
		expect(getFormElement(form, 'foo')).toEqual(null);
	});

	it('returns null if the elementName parameter is not a string', () => {
		const fragment = dom.buildFragment('<form />');

		const form = fragment.firstElementChild;

		expect(getFormElement(form, undefined)).toEqual(null);
		expect(getFormElement(form, {})).toEqual(null);
	});

	it('returns null if the element does not exist withing the form', () => {
		const fragment = dom.buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
					</form>
				`);

		const form = fragment.firstElementChild;

		expect(getFormElement(form, 'bar')).toEqual(null);
	});

	it('returns element value if the element does exist withing the form', () => {
		const fragment = dom.buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
					</form>
				`);

		const form = fragment.firstElementChild;

		const formElement = getFormElement(form, 'foo');

		expect(formElement.value).toEqual('abc');
	});
});
