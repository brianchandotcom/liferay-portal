/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portalweb.socialofficeprofile.profile.viewprofilesocialnetwork;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewProfileSocialNetworkTest extends BaseTestCase {
	public void testViewProfileSocialNetwork() throws Exception {
		selenium.open("/web/socialofficefriendsn/profile");
		assertEquals(RuntimeVariables.replace("Profile"),
			selenium.getText("//nav/ul/li/a/span"));
		assertEquals(RuntimeVariables.replace("Profile"),
			selenium.getText("//div[2]/div/div/div/section/header/h1/span[2]"));
		assertEquals(RuntimeVariables.replace(
				"socialofficefriendfn socialofficefriendmn socialofficefriendln"),
			selenium.getText("//div[1]/h1/span"));
		assertEquals(RuntimeVariables.replace(
				"socialofficefriendea@liferay.com"),
			selenium.getText("//div[2]/div/div[1]/div/div[1]/div/a"));
		assertEquals(RuntimeVariables.replace("Information"),
			selenium.getText("//div[1]/div[1]/div/span"));
		assertTrue(selenium.isVisible("//div[1]/h3"));
		assertEquals(RuntimeVariables.replace("Social Network"),
			selenium.getText("//div[1]/h3"));
		assertEquals(RuntimeVariables.replace("Facebook"),
			selenium.getText("//dt[1]"));
		assertEquals(RuntimeVariables.replace("socialofficefriendfn"),
			selenium.getText("//dd[1]"));
		assertEquals(RuntimeVariables.replace("MySpace"),
			selenium.getText("//dt[2]"));
		assertEquals(RuntimeVariables.replace("socialofficefriendfn"),
			selenium.getText("//dd[2]"));
		assertEquals(RuntimeVariables.replace("Twitter"),
			selenium.getText("//dt[3]"));
		assertEquals(RuntimeVariables.replace("socialofficefriendfn"),
			selenium.getText("//dd[3]"));
	}
}