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