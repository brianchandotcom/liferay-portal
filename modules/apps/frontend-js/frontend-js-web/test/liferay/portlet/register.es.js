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

import PortletInit from '../../../src/main/resources/META-INF/resources/liferay/portlet/PortletInit.es';
import register from '../../../src/main/resources/META-INF/resources/liferay/portlet/register.es';

describe('PortletHub', () => {
	describe('register', () => {
		it('throws error if called without portletId', () => {
			expect.assertions(1);

			const testFn = () => register();

			expect(testFn).toThrow();
		});

		it('returns an instance of PortletInit', () => {
			return register('PortletA').then((hub) => {
				expect(hub).toBeInstanceOf(PortletInit);
			});
		});
	});
});
