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

package com.liferay.portalweb.blocks.base.functions;

import com.liferay.portalweb.portal.util.liferayselenium.LiferaySelenium;

/**
 * @author Brian Wing Shun Chan
 */
public class AssertTextEqualsFunctions extends BaseFunctions {
	public AssertTextEqualsFunctions(LiferaySelenium liferaySelenium) {
		super(liferaySelenium);
	}

	public void assertPartialText(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForPartialText(param1, param2);
		selenium.assertPartialText(param1, param2);
	}

	public void assertPartialTextPause(String param1, String param2)
		throws Exception {
		AssertTextEqualsFunctions assertTextEqualsFunctions = new AssertTextEqualsFunctions(selenium);

		selenium.pause("25000");
		assertTextEqualsFunctions.assertPartialText(param1, param2);
	}

	public void assertText(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForText(param1, param2);
		selenium.assertText(param1, param2);
	}

	public void assertTextCPWebContenCKEditor(String param1, String param2)
		throws Exception {
		selenium.waitForElementPresent(
			"//textarea[@id='_15__15_structure_el_TextAreaField_content' and @style='display: none;']");
		selenium.waitForVisible(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		selenium.selectFrame(
			"//td[@id='cke_contents__15__15_structure_el_TextAreaField_content']/iframe");
		selenium.waitForText("//body", param2);
		selenium.assertText("//body", param2);
		selenium.selectFrame("relative=top");
	}

	public void assertValue(String param1, String param2)
		throws Exception {
		selenium.waitForVisible(param1);
		selenium.waitForValue(param1, param2);
		selenium.assertValue(param1, param2);
	}
}