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
