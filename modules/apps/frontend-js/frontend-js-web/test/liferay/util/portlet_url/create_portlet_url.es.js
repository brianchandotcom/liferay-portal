/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import createPortletURL from '../../../../src/main/resources/META-INF/resources/liferay/util/portlet_url/create_portlet_url.es';

describe('Liferay.Util.PortletURL.createPortletURL', () => {
	afterEach(() => {
		Liferay.ThemeDisplay.getPortalURL.mockRestore();
	});

	beforeEach(() => {
		Liferay = {
			ThemeDisplay: {
				getPortalURL: jest.fn(() => 'http://localhost:8080'),
			},
		};
	});

	it('throws an error if basePortletURL is not a string', () => {
		expect(() => createPortletURL({portlet: 'url'}, {foo: 'bar'})).toThrow(
			'basePortletURL parameter must be a string'
		);
	});

	it('throws an error if parameters is not an object', () => {
		expect(() =>
			createPortletURL(
				'http://localhost:8080/group/control_panel/manage',
				'foo:bar'
			)
		).toThrow('parameters argument must be an object');
	});

	it('throws an error if parameters are provided but portlet ID is null', () => {
		expect(() =>
			createPortletURL(
				'http://localhost:8080/group/control_panel/manage',
				{
					foo: 'bar',
				}
			)
		).toThrow('Portlet ID must not be null if parameters are provided');
	});

	it('returns a URL object with a href parameter containing base url and parameters, where reserved parameters are not namespaced', () => {
		const portletURL = createPortletURL(
			'http://localhost:8080/group/control_panel/manage?p_p_id=com_liferay_roles_admin_web_portlet_RolesAdminPortlet',
			{
				doAsGroupId: 'fooBar',
				foo: 'bar',
			}
		);

		expect(portletURL.href).toEqual(
			'http://localhost:8080/group/control_panel/manage?p_p_id=com_liferay_roles_admin_web_portlet_RolesAdminPortlet&doAsGroupId=fooBar&_com_liferay_roles_admin_web_portlet_RolesAdminPortlet_foo=bar'
		);
	});

	it('returns a URL object when the base url is a mailto url', () => {
		const portletURL = createPortletURL('mailto:test@liferay.com');

		expect(portletURL.href).toEqual('mailto:test@liferay.com');
	});

	it('returns a URL object consisting of a portal url followd by the base url when the base url  starts with a backslash', () => {
		const portletURL = createPortletURL('/foobar');

		expect(portletURL.href).toEqual('http://localhost:8080/foobar');
	});

	it('returns a URL object consisting of a portal url plus a slash followed by the base url when the base url is a basic string', () => {
		const portletURL = createPortletURL('foobar');

		expect(portletURL.href).toEqual('http://localhost:8080/foobar');
	});

	it('overwrites a parameter in base url if the same parameter is set in the parameters argument', () => {
		const portletURL = createPortletURL(
			'http://localhost:8080/group/control_panel/manage?p_p_id=com_liferay_roles_admin_web_portlet_RolesAdminPortlet&doAsGroupId=fooBar',
			{
				doAsGroupId: 'barBaz',
				foo: 'bar',
			}
		);
		expect(portletURL.href).toEqual(
			'http://localhost:8080/group/control_panel/manage?p_p_id=com_liferay_roles_admin_web_portlet_RolesAdminPortlet&doAsGroupId=barBaz&_com_liferay_roles_admin_web_portlet_RolesAdminPortlet_foo=bar'
		);
	});

	it('prepends portal url if the provided base url does not have a proper beginning', () => {
		const portletURL = createPortletURL('/group/control_panel/manage');

		expect(portletURL.href).toEqual(
			'http://localhost:8080/group/control_panel/manage'
		);
	});
});
