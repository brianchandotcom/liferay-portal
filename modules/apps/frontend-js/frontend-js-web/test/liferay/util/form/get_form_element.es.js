/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import buildFragment from '../../../../src/main/resources/META-INF/resources/liferay/util/build_fragment';
import getFormElement from '../../../../src/main/resources/META-INF/resources/liferay/util/form/get_form_element.es';

describe('Liferay.Util.getFormElement', () => {
	it('returns null if the form parameter is not a form node', () => {
		const fragment = buildFragment('<div />');

		const form = fragment.firstElementChild;

		expect(getFormElement(undefined, 'foo')).toEqual(null);
		expect(getFormElement(form, 'foo')).toEqual(null);
	});

	it('returns null if the elementName parameter is not a string', () => {
		const fragment = buildFragment('<form />');

		const form = fragment.firstElementChild;

		expect(getFormElement(form, undefined)).toEqual(null);
		expect(getFormElement(form, {})).toEqual(null);
	});

	it('returns null if the element does not exist withing the form', () => {
		const fragment = buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
					</form>
				`);

		const form = fragment.firstElementChild;

		expect(getFormElement(form, 'bar')).toEqual(null);
	});

	it('returns element value if the element does exist withing the form', () => {
		const fragment = buildFragment(`
					<form data-fm-namespace="_com_liferay_test_portlet_" id="fm">
						<input name="_com_liferay_test_portlet_foo" type="text" value="abc">
					</form>
				`);

		const form = fragment.firstElementChild;

		const formElement = getFormElement(form, 'foo');

		expect(formElement.value).toEqual('abc');
	});
});
