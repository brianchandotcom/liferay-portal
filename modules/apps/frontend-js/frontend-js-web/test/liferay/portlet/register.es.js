/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
