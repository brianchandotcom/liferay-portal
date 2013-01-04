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
public class CheckFunctions extends BaseFunctions {
	public CheckFunctions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void click(String target, String value) throws Exception {
		selenium.waitForVisible(target);

		if (!selenium.isChecked(target)) {
			selenium.click(target);
		}

		selenium.assertChecked(target);
	}

	public void clickAt(String target, String value) throws Exception {
		selenium.waitForVisible(target);

		if (!selenium.isChecked(target)) {
			selenium.clickAt(target, value);
		}

		selenium.assertChecked(target);
	}

	public void clickAtWCMouseOverArticle(String target, String value)
		throws Exception {
		CheckFunctions checkFunctions = new CheckFunctions(selenium);

		selenium.waitForVisible(
			"xpath=(//a[contains(@data-folder,'false')])[1]");
		selenium.mouseOver("xpath=(//a[contains(@data-folder,'false')])[1]");
		checkFunctions.clickAt(target, value);
	}
}