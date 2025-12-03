/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.headless.admin.site.resource.v1_0.test.util;

import com.liferay.headless.admin.site.client.dto.v1_0.FragmentViewport;
import com.liferay.portal.kernel.test.util.RandomTestUtil;

/**
 * @author Mikel Lorza
 */
public class FragmentViewportTestUtil {

	public static FragmentViewport[] getFragmentViewports() {
		return new FragmentViewport[] {
			new FragmentViewport() {
				{
					setCustomCSS(RandomTestUtil.randomString());
					setFragmentViewportStyle(
						FragmentViewportStyleTestUtil.
							getFragmentViewportStyle());
					setId(Id.LANDSCAPE_MOBILE);
				}
			},
			new FragmentViewport() {
				{
					setCustomCSS(RandomTestUtil.randomString());
					setFragmentViewportStyle(
						FragmentViewportStyleTestUtil.
							getFragmentViewportStyle());
					setId(Id.TABLET);
				}
			}
		};
	}

}