/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.test.rule;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.util.SystemProperties;

import org.junit.runner.Description;

/**
 * @author Rafael Praxedes
 */
public class PortalRunModeClassTestRule extends ClassTestRule<String> {

	public static final PortalRunModeClassTestRule INSTANCE =
		new PortalRunModeClassTestRule();

	@Override
	protected void afterClass(Description description, String liferayMode) {
		if (liferayMode == null) {
			SystemProperties.clear("liferay.mode");
		}
		else {
			SystemProperties.set("liferay.mode", liferayMode);
		}
	}

	@Override
	protected String beforeClass(Description description)
		throws PortalException {

		String liferayMode = SystemProperties.get("liferay.mode");

		SystemProperties.set("liferay.mode", "test");

		return liferayMode;
	}

	private PortalRunModeClassTestRule() {
	}

}