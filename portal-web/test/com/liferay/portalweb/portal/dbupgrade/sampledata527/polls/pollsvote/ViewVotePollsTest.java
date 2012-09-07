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

package com.liferay.portalweb.portal.dbupgrade.sampledata527.polls.pollsvote;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewVotePollsTest extends BaseTestCase {
	public void testViewVotePolls() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		assertTrue(selenium.isPartialText("//h2[@class='user-greeting']/span",
				"Welcome"));
		selenium.mouseOver("//h2[@class='user-greeting']/span");
		selenium.clickAt("//h2[@class='user-greeting']/span",
			RuntimeVariables.replace("Welcome"));
		selenium.waitForVisible("link=Control Panel");
		selenium.clickAt("link=Control Panel",
			RuntimeVariables.replace("Control Panel"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("link=Polls", RuntimeVariables.replace("Polls"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Poll Question"),
			selenium.getText("//td[1]/a"));
		selenium.clickAt("//td[1]/a",
			RuntimeVariables.replace("Test Poll Question"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("Test Poll Question"),
			selenium.getText("//b"));
		assertEquals(RuntimeVariables.replace(
				"This is a test poll description."),
			selenium.getText("//span[2]"));
		assertEquals(RuntimeVariables.replace("100%"),
			selenium.getText("//tr[2]/td[1]"));
		assertEquals(RuntimeVariables.replace("1"),
			selenium.getText("//tr[2]/td[2]"));
		assertEquals(RuntimeVariables.replace("a."),
			selenium.getText("//td[6]/b"));
		assertEquals(RuntimeVariables.replace("Test Choice A"),
			selenium.getText("//td[7]"));
	}
}