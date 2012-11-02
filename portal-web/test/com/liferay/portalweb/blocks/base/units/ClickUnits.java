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

package com.liferay.portalweb.blocks.base.units;

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class ClickUnits extends BaseActionsUnits {

	public ClickUnits(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void click(String param1, String param2)throws Exception {
		selenium.waitForVisible(param1);
		selenium.click(param1);
	}

	public void clickAndWait(String param1, String param2)throws Exception {
		selenium.waitForVisible(param1);
		selenium.clickAndWait(param1);
	}

	public void clickAt(String param1, String param2)throws Exception {
		selenium.waitForVisible(param1);
		selenium.clickAt(param1, "");
	}

	public void clickAtAndWait(String param1, String param2)throws Exception {
		selenium.waitForVisible(param1);
		selenium.clickAtAndWait(param1, "");
	}

	public void partialTextClick(String param1, String param2)
		throws Exception {

		selenium.waitForVisible(param1);
		selenium.waitForPartialText(param1, param2);
		selenium.assertPartialText(param1, param2);
		selenium.click(param1);
	}

	public void partialTextClickAndWait(String param1, String param2)
		throws Exception {

		selenium.waitForVisible(param1);
		selenium.waitForPartialText(param1, param2);
		selenium.assertPartialText(param1, param2);
		selenium.clickAndWait(param1);
	}

	public void partialTextClickAt(String param1, String param2)
		throws Exception {

		selenium.waitForVisible(param1);
		selenium.waitForPartialText(param1, param2);
		selenium.assertPartialText(param1, param2);
		selenium.clickAt(param1, "");
	}

	public void partialTextClickAtAndWait(String param1, String param2)
		throws Exception {

		selenium.waitForVisible(param1);
		selenium.waitForPartialText(param1, param2);
		selenium.assertPartialText(param1, param2);
		selenium.clickAtAndWait(param1, "");
	}

	public void textClick(String param1, String param2) throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForText(param1, param2);
		selenium.assertText(param1, param2);
		selenium.click(param1);
	}

	public void textClickAndWait(String param1, String param2)
		throws Exception {

		selenium.waitForVisible(param1);
		selenium.waitForText(param1, param2);
		selenium.assertText(param1, param2);
		selenium.clickAndWait(param1);
	}

	public void textClickAt(String param1, String param2) throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForText(param1, param2);
		selenium.assertText(param1, param2);
		selenium.clickAt(param1, "");
	}

	public void textClickAtAndWait(String param1, String param2)
		throws Exception {

		selenium.waitForVisible(param1);
		selenium.waitForText(param1, param2);
		selenium.assertText(param1, param2);
		selenium.clickAtAndWait(param1, "");
	}

	public void valueClick(String param1, String param2) throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForValue(param1, param2);
		selenium.assertValue(param1, param2);
		selenium.click(param1);
	}

	public void valueClickAndWait(String param1, String param2)
		throws Exception {

		selenium.waitForVisible(param1);
		selenium.waitForValue(param1, param2);
		selenium.assertValue(param1, param2);
		selenium.clickAndWait(param1);
	}

	public void valueClickAt(String param1, String param2) throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForValue(param1, param2);
		selenium.assertValue(param1, param2);
		selenium.clickAt(param1, "");
	}

	public void valueClickAtAndWait(String param1, String param2)
		throws Exception {

		selenium.waitForVisible(param1);
		selenium.waitForValue(param1, param2);
		selenium.assertValue(param1, param2);
		selenium.clickAtAndWait(param1, "");
	}

}