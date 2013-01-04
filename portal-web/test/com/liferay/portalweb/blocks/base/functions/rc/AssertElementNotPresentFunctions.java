/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.blocks.base.functions.rc;

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertElementNotPresentFunctions extends BaseFunctions {
	public AssertElementNotPresentFunctions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void assertElementNotPresent(String target, String value)
		throws Exception {
		if (selenium.isElementPresent(target)) {
			selenium.waitForNotVisible(target);
			selenium.assertNotVisible(target);
		}
		else {
			selenium.waitForElementNotPresent(target);
			selenium.assertElementNotPresent(target);
		}
	}
}