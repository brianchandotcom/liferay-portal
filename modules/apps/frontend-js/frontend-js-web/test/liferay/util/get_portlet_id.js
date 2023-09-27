/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

import getPortletId from '../../../src/main/resources/META-INF/resources/liferay/util/get_portlet_id';

describe('Liferay.Util.getPortletId', () => {
	it('returns the portlet id', () => {
		expect(getPortletId('p_p_id_fooBar_')).toEqual('fooBar');
	});

	it('returns the portlet id without a leading p_p_id', () => {
		expect(getPortletId('_fooBar_')).toEqual('fooBar');
	});

	it('returns the input as-is when no portlet ID can be extracted', () => {
		expect(getPortletId('not-a-well-formed-id')).toBe(
			'not-a-well-formed-id'
		);
	});
});
