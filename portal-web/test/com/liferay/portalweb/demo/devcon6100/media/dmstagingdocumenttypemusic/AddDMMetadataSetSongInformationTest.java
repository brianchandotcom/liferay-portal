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

package com.liferay.portalweb.demo.devcon6100.media.dmstagingdocumenttypemusic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMMetadataSetSongInformationTest extends BaseTestCase {
	public void testAddDMMetadataSetSongInformation() throws Exception {
		selenium.open("/web/site-name/");
		loadRequiredJavaScriptModules();

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Documents and Media Test Page")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Documents and Media Test Page",
			RuntimeVariables.replace("Documents and Media Test Page"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		Thread.sleep(5000);
		assertEquals(RuntimeVariables.replace("Manage"),
			selenium.getText("//span[@title='Manage']/ul/li/strong/a/span"));
		selenium.clickAt("//span[@title='Manage']/ul/li/strong/a/span",
			RuntimeVariables.replace("Manage"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Metadata Sets"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a"));
		selenium.click("//div[@class='lfr-component lfr-menu-list']/ul/li[2]/a");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame("//iframe");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("link=Add")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("link=Add", RuntimeVariables.replace("Add"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		selenium.type("//input[@id='_166_name_en_US']",
			RuntimeVariables.replace("Song Information"));
		selenium.dragAndDropToObject("//ul[contains(@class,'aui-diagram-builder-fields-container')]/li/div[.='Text']",
			"//div[@class='aui-diagram-builder-drop-container']");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[contains(@id,'fields_field_aui')]/div/label")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText(
				"//div[contains(@id,'fields_field_aui')]/div/label"));
		selenium.doubleClickAt("//div[contains(@id,'fields_field_aui')]/div/label",
			RuntimeVariables.replace("Text"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//tr[2]/td[1]/div")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Field Label"),
			selenium.getText("//tr[2]/td[1]/div"));
		assertEquals(RuntimeVariables.replace("Text"),
			selenium.getText("//tr[2]/td[2]/div"));
		selenium.doubleClickAt("//tr[2]/td[1]/div",
			RuntimeVariables.replace("Field Label"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@name='value']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@name='value']",
			RuntimeVariables.replace("Artist"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Artist")
										.equals(selenium.getText(
								"//tr[2]/td[2]/div"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Artist"),
			selenium.getText("//tr[2]/td[2]/div"));
		assertEquals(RuntimeVariables.replace("Artist"),
			selenium.getText(
				"//div[contains(@id,'fields_field_aui')]/div/label"));
		assertEquals(RuntimeVariables.replace("Required"),
			selenium.getText("//tr[4]/td[1]/div"));
		assertEquals(RuntimeVariables.replace("No"),
			selenium.getText("//tr[4]/td[2]/div"));
		selenium.doubleClickAt("//tr[4]/td[1]/div",
			RuntimeVariables.replace("Required"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//input[@type='radio' and @value='true']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.clickAt("//input[@type='radio' and @value='true']",
			RuntimeVariables.replace("Yes"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Yes")
										.equals(selenium.getText(
								"//tr[4]/td[2]/div"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Yes"),
			selenium.getText("//tr[4]/td[2]/div"));
		assertEquals(RuntimeVariables.replace("*"),
			selenium.getText("//span[@class='aui-form-builder-required']"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText("//tr[5]/td[1]/div"));
		assertTrue(selenium.isPartialText("//tr[5]/td[2]/div", "text"));
		selenium.doubleClickAt("//tr[5]/td[1]/div",
			RuntimeVariables.replace("Name"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@name='value']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@name='value']",
			RuntimeVariables.replace("artist"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("artist")
										.equals(selenium.getText(
								"//tr[5]/td[2]/div"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("artist"),
			selenium.getText("//tr[5]/td[2]/div"));
		selenium.clickAt("link=Fields", RuntimeVariables.replace("Fields"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//ul[contains(@class,'aui-diagram-builder-fields-container')]/li/div[.='Number']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.dragAndDropToObject("//ul[contains(@class,'aui-diagram-builder-fields-container')]/li/div[.='Number']",
			"//div[@class='aui-diagram-builder-drop-container']");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"xPath=(//div[contains(@id,'fields_field_aui')]/div/label)[2]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText(
				"xPath=(//div[contains(@id,'fields_field_aui')]/div/label)[2]"));
		selenium.doubleClickAt("xPath=(//div[contains(@id,'fields_field_aui')]/div/label)[2]",
			RuntimeVariables.replace("Number"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//tr[2]/td[1]/div")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Field Label"),
			selenium.getText("//tr[2]/td[1]/div"));
		assertEquals(RuntimeVariables.replace("Number"),
			selenium.getText("//tr[2]/td[2]/div"));
		selenium.doubleClickAt("//tr[2]/td[1]/div",
			RuntimeVariables.replace("Field Label"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@name='value']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@name='value']",
			RuntimeVariables.replace("Track Number"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Track Number")
										.equals(selenium.getText(
								"//tr[2]/td[2]/div"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Track Number"),
			selenium.getText("//tr[2]/td[2]/div"));
		assertEquals(RuntimeVariables.replace("Track Number"),
			selenium.getText(
				"xPath=(//div[contains(@id,'fields_field_aui')]/div/label)[2]"));
		assertEquals(RuntimeVariables.replace("Name"),
			selenium.getText("//tr[5]/td[1]/div"));
		assertTrue(selenium.isPartialText("//tr[5]/td[2]/div", "ddm-number"));
		selenium.doubleClickAt("//tr[5]/td[1]/div",
			RuntimeVariables.replace("Field Label"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//input[@name='value']")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.type("//input[@name='value']",
			RuntimeVariables.replace("track"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("track")
										.equals(selenium.getText(
								"//tr[5]/td[2]/div"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("track"),
			selenium.getText("//tr[5]/td[2]/div"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isTextPresent("Song Information"));
		selenium.selectFrame("relative=top");
	}
}