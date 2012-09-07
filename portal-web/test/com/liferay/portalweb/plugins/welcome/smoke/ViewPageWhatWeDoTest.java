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

package com.liferay.portalweb.plugins.welcome.smoke;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewPageWhatWeDoTest extends BaseTestCase {
	public void testViewPageWhatWeDo() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/guest/what-we-do/");
		selenium.clickAt("link=What We Do",
			RuntimeVariables.replace("What We Do"));
		selenium.waitForPageToLoad("30000");
		selenium.clickAt("//nav[@id='navigation']",
			RuntimeVariables.replace("Navigation"));
		selenium.waitForElementPresent(
			"//script[contains(@src,'/aui/sortable/sortable-min.js')]");
		assertTrue(selenium.isVisible("//a[@title='Go to Liferay']"));
		assertEquals(RuntimeVariables.replace("Welcome to Liferay Portal"),
			selenium.getText("//h2[@class='page-title']/span"));
		assertEquals(RuntimeVariables.replace("What We Do"),
			selenium.getText(
				"xPath=(//li[contains(@class,'lfr-nav-deletable')]/a/span)[1]"));
		assertEquals(RuntimeVariables.replace("Who Is Using Liferay"),
			selenium.getText(
				"xPath=(//li[contains(@class,'lfr-nav-deletable')]/a/span)[2]"));
		assertEquals(RuntimeVariables.replace("Liferay Benefits"),
			selenium.getText(
				"xPath=(//li[contains(@class,'lfr-nav-deletable')]/a/span)[3]"));
		assertEquals(RuntimeVariables.replace(
				"Liferay helps you build feature-rich, easy-to-use web applications quickly."),
			selenium.getText(
				"//header[@class='content-head content-head-liferay-portal']/hgroup/h1"));
		assertEquals(RuntimeVariables.replace("A Foundation for Apps"),
			selenium.getText("//ul[@id='contentNav']/li[1]/a"));
		assertEquals(RuntimeVariables.replace("Dynamic Websites"),
			selenium.getText("//ul[@id='contentNav']/li[2]/a"));
		assertEquals(RuntimeVariables.replace("Centralized Document Management"),
			selenium.getText("//ul[@id='contentNav']/li[3]/a"));
		assertEquals(RuntimeVariables.replace("Intuitive Team Collaboration"),
			selenium.getText("//ul[@id='contentNav']/li[4]/a"));
		assertEquals(RuntimeVariables.replace("Tools for Business"),
			selenium.getText("//ul[@id='contentNav']/li[5]/a"));
		assertEquals(RuntimeVariables.replace("Desktop & Mobile Access"),
			selenium.getText("//ul[@id='contentNav']/li[6]/a"));
		assertEquals(RuntimeVariables.replace("Liferay Marketplace"),
			selenium.getText("//ul[@id='contentNav']/li[7]/a"));
		assertTrue(selenium.isVisible("//a[@id='marketplace']"));
		assertTrue(selenium.isVisible(
				"//div[@id='getting-started']/div/span[@class='footer-logo']"));
		assertEquals(RuntimeVariables.replace("Want Easy Social Collaboration?"),
			selenium.getText("//div[@id='getting-started']/div/div/h3"));
		assertEquals(RuntimeVariables.replace("Get Social Office \u25ba"),
			selenium.getText("//div[@id='getting-started']/div/div/a"));
		assertTrue(selenium.isVisible(
				"//div[@id='ide']/div/span[@class='footer-logo']"));
		assertTrue(selenium.isVisible("//div[@id='ide']/div/div/h3"));
		assertTrue(selenium.isVisible("//div[@id='ide']/div/div/a"));
		assertTrue(selenium.isVisible(
				"//div[@id='social-office']/div/span[@class='footer-logo']"));
		assertTrue(selenium.isVisible("//div[@id='social-office']/div/div/h3"));
		assertTrue(selenium.isVisible("//div[@id='social-office']/div/div/a"));
	}
}