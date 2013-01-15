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

package com.liferay.portalweb.socialofficehome.activities.activitiesblockedsouser.sousviewactivitiesblockedsouser3;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs2_ViewActivitiesBlockedSOUser3Test extends BaseTestCase {
	public void testSOUs2_ViewActivitiesBlockedSOUser3()
		throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/user/socialoffice02/so/dashboard");
		assertEquals(RuntimeVariables.replace("Activities"),
			selenium.getText("xPath=(//h1[@class='portlet-title']/span)[2]"));
		assertEquals(RuntimeVariables.replace("Following"),
			selenium.getText("link=Following"));
		selenium.clickAt("link=Following", RuntimeVariables.replace("Following"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("There are no recent activities."),
			selenium.getText("//div[@class='portrait-social-activities']"));
		assertTrue(selenium.isElementNotPresent(
				"//div[@class='activity-title']"));
		assertTrue(selenium.isElementNotPresent("//a[@class='user']"));
		assertFalse(selenium.isTextPresent(
				"Social03 Office03 User03 wrote a new message board post, Forums Thread2 Message Subject, in Open Site Name."));
		assertFalse(selenium.isTextPresent("Microblogs Post2"));
		assertFalse(selenium.isTextPresent(
				"Social03 Office03 User03 wrote a new message board post, Forums Thread1 Message Subject, in Open Site Name."));
		assertFalse(selenium.isTextPresent("Microblogs Post1"));
	}
}