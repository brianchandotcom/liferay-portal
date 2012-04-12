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

package com.liferay.portalweb.socialofficehome.microblogs.microblogsentry.sousdeletereplymicroblogscontentprofile;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class SOUs_ViewDeleteReplyMicroblogsContentTest extends BaseTestCase {
	public void testSOUs_ViewDeleteReplyMicroblogsContent()
		throws Exception {
		selenium.open("/user/socialoffice01/so/dashboard");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace("There are no recent activities."),
			selenium.getText("xPath=(//div[@class='portlet-msg-info'])[2]"));
		assertFalse(selenium.isTextPresent("Microblogs Post Comment"));
	}
}