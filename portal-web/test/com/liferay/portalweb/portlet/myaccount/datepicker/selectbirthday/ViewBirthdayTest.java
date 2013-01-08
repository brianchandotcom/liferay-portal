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

package com.liferay.portalweb.portlet.myaccount.datepicker.selectbirthday;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewBirthdayTest extends BaseTestCase {
	public void testViewBirthday() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/home/");
		selenium.clickAt("link=Joe Bloggs",
			RuntimeVariables.replace("Joe Bloggs"));
		selenium.waitForPageToLoad("30000");
		assertEquals(RuntimeVariables.replace("My Account"),
			selenium.getText("//span[@class='portlet-title-text']"));
		assertTrue(selenium.isPartialText(
				"//div[@id='show-portlet-description-2']/div",
				"My Account organizes all of your information in one, easy to use location. Users can edit their profile and view site memberships and the organizations and user groups to which they belong."));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//h1[@class='header-title']"));
		assertEquals(RuntimeVariables.replace("Details"),
			selenium.getText("//div[@id='_2_details']/h3"));
		assertEquals("joebloggs",
			selenium.getValue("//input[@id='_2_screenName']"));
		assertEquals("test@liferay.com",
			selenium.getValue("//input[@id='_2_emailAddress']"));
		assertEquals("",
			selenium.getSelectedLabel("//select[@id='_2_prefixId']"));
		assertEquals("Joe", selenium.getValue("//input[@id='_2_firstName']"));
		assertEquals("", selenium.getValue("//input[@id='_2_middleName']"));
		assertEquals("Bloggs", selenium.getValue("//input[@id='_2_lastName']"));
		assertEquals("",
			selenium.getSelectedLabel("//select[@id='_2_suffixId']"));
		assertTrue(selenium.isVisible("//img[@class='avatar']"));
		assertEquals(RuntimeVariables.replace("Change"),
			selenium.getText("//span[@class='edit-logo-link']/a/span"));
		assertEquals("May",
			selenium.getSelectedLabel("//select[@id='_2_birthdayMonth']"));
		assertEquals("31",
			selenium.getSelectedLabel("//select[@id='_2_birthdayDay']"));
		assertEquals("1986",
			selenium.getSelectedLabel("//select[@id='_2_birthdayYear']"));
		assertTrue(selenium.isVisible("//button[@id='buttonTest']"));
		assertEquals("Male",
			selenium.getSelectedLabel("//select[@id='_2_male']"));
		assertEquals("", selenium.getValue("//input[@id='_2_jobTitle']"));
		assertTrue(selenium.isVisible("//img[@class='user-logo']"));
		assertEquals(RuntimeVariables.replace("Joe Bloggs"),
			selenium.getText("//span[@class='user-name']"));
		assertEquals(RuntimeVariables.replace("User Information"),
			selenium.getText("xpath=(//div[@class='menu-group']/h3)[1]"));
		assertTrue(selenium.isPartialText("//a[@id='_2_detailsLink']", "Details"));
		assertTrue(selenium.isPartialText("//a[@id='_2_passwordLink']",
				"Password"));
		assertTrue(selenium.isPartialText("//a[@id='_2_organizationsLink']",
				"Organizations"));
		assertTrue(selenium.isPartialText("//a[@id='_2_sitesLink']", "Sites"));
		assertTrue(selenium.isPartialText("//a[@id='_2_userGroupsLink']",
				"User Groups"));
		assertTrue(selenium.isPartialText("//a[@id='_2_rolesLink']", "Roles"));
		assertTrue(selenium.isPartialText("//a[@id='_2_categorizationLink']",
				"Categorization"));
		assertEquals(RuntimeVariables.replace("Identification"),
			selenium.getText("xpath=(//div[@class='menu-group']/h3)[2]"));
		assertTrue(selenium.isPartialText("//a[@id='_2_addressesLink']",
				"Addresses"));
		assertTrue(selenium.isPartialText("//a[@id='_2_phoneNumbersLink']",
				"Phone Numbers"));
		assertTrue(selenium.isPartialText(
				"//a[@id='_2_additionalEmailAddressesLink']",
				"Additional Email Addresses"));
		assertTrue(selenium.isPartialText("//a[@id='_2_websitesLink']",
				"Websites"));
		assertTrue(selenium.isPartialText(
				"//a[@id='_2_instantMessengerLink']", "Instant Messenger"));
		assertTrue(selenium.isPartialText("//a[@id='_2_socialNetworkLink']",
				"Social Network"));
		assertTrue(selenium.isPartialText("//a[@id='_2_smsLink']", "SMS"));
		assertTrue(selenium.isPartialText("//a[@id='_2_openIdLink']", "OpenID"));
		assertEquals(RuntimeVariables.replace("Miscellaneous"),
			selenium.getText("xpath=(//div[@class='menu-group']/h3)[3]"));
		assertTrue(selenium.isPartialText("//a[@id='_2_announcementsLink']",
				"Announcements"));
		assertTrue(selenium.isPartialText("//a[@id='_2_displaySettingsLink']",
				"Display Settings"));
		assertTrue(selenium.isPartialText("//a[@id='_2_commentsLink']",
				"Comments"));
		assertTrue(selenium.isPartialText("//a[@id='_2_customFieldsLink']",
				"Custom Fields"));
		assertTrue(selenium.isVisible("//input[@value='Save']"));
		assertTrue(selenium.isVisible("//input[@value='Cancel']"));
	}
}