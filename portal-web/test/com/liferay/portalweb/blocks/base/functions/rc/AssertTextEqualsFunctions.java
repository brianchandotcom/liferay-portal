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
public class AssertTextEqualsFunctions extends BaseFunctions {
	public AssertTextEqualsFunctions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void assertPartialText(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForPartialText(target, value);
		selenium.assertPartialText(target, value);
	}

	public void assertPartialTextPause(String target, String value)
		throws Exception {
		AssertTextEqualsFunctions assertTextEqualsFunctions = new AssertTextEqualsFunctions(selenium);

		selenium.pause("25000");
		assertTextEqualsFunctions.assertPartialText(target, value);
	}

	public void assertText(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForText(target, value);
		selenium.assertText(target, value);
	}

	public void assertTextCPWebContenCKEditor(String target, String value)
		throws Exception {
		selenium.waitForElementPresent(
			"//textarea[@id='_15__15_structure_el_TextAreaField_content' and @style='display: none;']");
		selenium.waitForVisible(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		selenium.selectFrame(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		selenium.waitForText("//body", value);
		selenium.assertText("//body", value);
		selenium.selectFrame("relative=top");
	}

	public void assertValue(String target, String value)
		throws Exception {
		selenium.waitForVisible(target);
		selenium.waitForValue(target, value);
		selenium.assertValue(target, value);
	}
}