/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.portal;

import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.util.InitUtil;
import com.liferay.portalweb.portal.util.SeleniumUtil;
import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

import junit.framework.TestSuite;

/**
 * @author Brian Wing Shun Chan
 */
public class NamedTestSuite extends TestSuite {

	public NamedTestSuite() {
		InitUtil.initWithSpring();

		LiferaySelenium liferaySelenium = SeleniumUtil.getSelenium();

		if (Validator.isNotNull(liferaySelenium.getPrimaryTestSuiteName())) {
			return;
		}

		Thread currentThread = Thread.currentThread();

		StackTraceElement stackTraceElement = currentThread.getStackTrace()[2];

		liferaySelenium.setPrimaryTestSuiteName(
			stackTraceElement.getClassName());
	}

}