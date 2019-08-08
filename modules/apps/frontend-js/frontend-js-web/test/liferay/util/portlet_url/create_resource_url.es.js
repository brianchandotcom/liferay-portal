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

import createResourceURL from '../../../../src/main/resources/META-INF/resources/liferay/util/portlet_url/create_resource_url.es';

describe('Liferay.Util.PortletURL.createResourceURL', () => {
	it('returns a url with the p_p_lifecycle parameter set to 2', () => {
		Liferay = {
			ThemeDisplay: {
				getPortalURL: jest.fn(() => 'http://localhost:8080')
			}
		};

		const portletURL = createResourceURL(
			'http://localhost:8080/group/control_panel/manage?p_p_id=foo'
		);

		expect(portletURL.href).toEqual(
			'http://localhost:8080/group/control_panel/manage?p_p_id=foo&p_p_lifecycle=2'
		);
	});
});
