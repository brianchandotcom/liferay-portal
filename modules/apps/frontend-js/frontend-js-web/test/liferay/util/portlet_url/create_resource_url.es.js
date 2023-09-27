/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import createResourceURL from '../../../../src/main/resources/META-INF/resources/liferay/util/portlet_url/create_resource_url.es';

describe('Liferay.Util.PortletURL.createResourceURL', () => {
	it('returns a url with the p_p_lifecycle parameter set to 2', () => {
		Liferay = {
			ThemeDisplay: {
				getPortalURL: jest.fn(() => 'http://localhost:8080'),
			},
		};

		const portletURL = createResourceURL(
			'http://localhost:8080/group/control_panel/manage?p_p_id=foo'
		);

		expect(portletURL.href).toEqual(
			'http://localhost:8080/group/control_panel/manage?p_p_id=foo&p_p_lifecycle=2'
		);
	});
});
