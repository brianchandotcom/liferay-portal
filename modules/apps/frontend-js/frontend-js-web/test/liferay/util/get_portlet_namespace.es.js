/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getPortletNamespace from '../../../src/main/resources/META-INF/resources/liferay/util/get_portlet_namespace.es';

describe('Liferay.Util.getPortletNamespace', () => {
	it('throws an error if portletId is not a string', () => {
		expect(() => getPortletNamespace({})).toThrow(
			'portletId must be a string'
		);
	});

	it('returns a portlet namespace surrounded by underscores', () => {
		expect(getPortletNamespace('fooBar')).toEqual('_fooBar_');
	});
});
