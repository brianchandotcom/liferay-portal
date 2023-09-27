/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.security.auth;

import com.liferay.portal.kernel.security.auth.ScreenNameGenerator;
import com.liferay.portal.kernel.util.ServiceProxyFactory;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 * @author Peter Fellwock
 */
public class ScreenNameGeneratorFactory {

	public static ScreenNameGenerator getInstance() {
		return _screenNameGenerator;
	}

	private ScreenNameGeneratorFactory() {
	}

	private static volatile ScreenNameGenerator _screenNameGenerator =
		ServiceProxyFactory.newServiceTrackedInstance(
			ScreenNameGenerator.class, ScreenNameGeneratorFactory.class,
			"_screenNameGenerator", false, true);

}