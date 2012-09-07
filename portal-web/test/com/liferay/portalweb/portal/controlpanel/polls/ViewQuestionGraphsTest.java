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

package com.liferay.portalweb.portal.controlpanel.polls;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewQuestionGraphsTest extends BaseTestCase {
	public void testViewQuestionGraphs() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertEquals(RuntimeVariables.replace("Go to"),
			selenium.getText("//li[@id='_145_mySites']/a/span"));
		selenium.mouseOver("//li[@id='_145_mySites']/a/span");
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Polls", RuntimeVariables.replace("Polls"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Test Poll Question",
			RuntimeVariables.replace("Test Poll Question"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Area", RuntimeVariables.replace("Area"));
		selenium.waitForPopUp("viewChart", RuntimeVariables.replace("30000"));
		selenium.selectWindow("viewChart");
		selenium.close();
		selenium.selectWindow("null");
		selenium.waitForVisible("link=Horizontal Bar");
		selenium.clickAt("link=Horizontal Bar",
			RuntimeVariables.replace("Horizontal Bar"));
		selenium.waitForPopUp("viewChart", RuntimeVariables.replace("30000"));
		selenium.selectWindow("viewChart");
		selenium.close();
		selenium.selectWindow("null");
		selenium.waitForVisible("link=Line");
		selenium.clickAt("link=Line", RuntimeVariables.replace("Line"));
		selenium.waitForPopUp("viewChart", RuntimeVariables.replace("30000"));
		selenium.selectWindow("viewChart");
		selenium.close();
		selenium.selectWindow("null");
		selenium.waitForVisible("link=Pie");
		selenium.clickAt("link=Pie", RuntimeVariables.replace("Pie"));
		selenium.waitForPopUp("viewChart", RuntimeVariables.replace("30000"));
		selenium.selectWindow("viewChart");
		selenium.close();
		selenium.selectWindow("null");
		selenium.waitForVisible("link=Vertical Bar");
		selenium.clickAt("link=Vertical Bar",
			RuntimeVariables.replace("Vertical Bar"));
		selenium.waitForPopUp("viewChart", RuntimeVariables.replace("30000"));
		selenium.selectWindow("viewChart");
		selenium.close();
		selenium.selectWindow("null");
	}
}