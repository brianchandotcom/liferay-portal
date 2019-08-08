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

import createURL from '../../../../src/main/resources/META-INF/resources/liferay/util/portlet_url/create_url.es';

describe('Liferay.Util.PortletURL.createURL', () => {
	afterEach(() => {
		Liferay.ThemeDisplay.getPortalURL.mockRestore();
	});

	beforeEach(() => {
		Liferay = {
			ThemeDisplay: {
				getPortalURL: jest.fn(() => 'http://localhost:8080')
			}
		};
	});

	it('throws an error if basePortletURL is not a string', () => {
		expect(() => createURL({portlet: 'url'}, {foo: 'bar'})).toThrow(
			'basePortletURL parameter must be a string'
		);
	});

	it('throws an error if parameters is not an object', () => {
		expect(() =>
			createURL(
				'http://localhost:8080/group/control_panel/manage',
				'foo:bar'
			)
		).toThrow('parameters argument must be an object');
	});

	it('throws an error if parameters are provided but portlet ID is null', () => {
		expect(() =>
			createURL('http://localhost:8080/group/control_panel/manage', {
				foo: 'bar'
			})
		).toThrow('Portlet ID must not be null if parameters are provided');
	});

	it('returns a URL object with a href parameter containing base url and parameters, where reserved parameters are not namespaced', () => {
		const portletURL = createURL(
			'http://localhost:8080/group/control_panel/manage?p_p_id=com_liferay_roles_admin_web_portlet_RolesAdminPortlet',
			{
				doAsGroupId: 'fooBar',
				foo: 'bar'
			}
		);

		expect(portletURL.href).toEqual(
			'http://localhost:8080/group/control_panel/manage?p_p_id=com_liferay_roles_admin_web_portlet_RolesAdminPortlet&doAsGroupId=fooBar&_com_liferay_roles_admin_web_portlet_RolesAdminPortlet_foo=bar'
		);
	});

	it('overwrites a parameter in base url if the same parameter is set in the parameters argument', () => {
		const portletURL = createURL(
			'http://localhost:8080/group/control_panel/manage?p_p_id=com_liferay_roles_admin_web_portlet_RolesAdminPortlet&doAsGroupId=fooBar',
			{
				doAsGroupId: 'barBaz',
				foo: 'bar'
			}
		);
		expect(portletURL.href).toEqual(
			'http://localhost:8080/group/control_panel/manage?p_p_id=com_liferay_roles_admin_web_portlet_RolesAdminPortlet&doAsGroupId=barBaz&_com_liferay_roles_admin_web_portlet_RolesAdminPortlet_foo=bar'
		);
	});

	it('prepends portal url if the provided base url does not have a proper beginning', () => {
		const portletURL = createURL('/group/control_panel/manage');

		expect(portletURL.href).toEqual(
			'http://localhost:8080/group/control_panel/manage'
		);
	});
});
