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

package com.liferay.portalweb.demo.devcon6100.media.dmdocumenttypemusic;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class AddDMDocumentTypeMusicTest extends BaseTestCase {
	public void testAddDMDocumentTypeMusic() throws Exception {
		selenium.open("/web/guest/home/");
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
							"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Document Types"),
			selenium.getText(
				"//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a"));
		selenium.click("//div[@class='lfr-component lfr-menu-list']/ul/li[1]/a");

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
		selenium.type("//input[@id='_20_name']",
			RuntimeVariables.replace("Music"));
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
				if (selenium.isVisible("//tr[2]/td/div")) {
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
			RuntimeVariables.replace("Album"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("Album")
										.equals(selenium.getText(
								"//tr[2]/td[2]/div"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Album"),
			selenium.getText("//tr[2]/td[2]/div"));
		assertEquals(RuntimeVariables.replace("Album"),
			selenium.getText(
				"//div[contains(@id,'fields_field_aui')]/div/label"));
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
			RuntimeVariables.replace("album"));
		assertEquals(RuntimeVariables.replace("Save"),
			selenium.getText("//button"));
		selenium.clickAt("//button", RuntimeVariables.replace("Save"));

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (RuntimeVariables.replace("album")
										.equals(selenium.getText(
								"//tr[5]/td[2]/div"))) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("album"),
			selenium.getText("//tr[5]/td[2]/div"));
		assertEquals(RuntimeVariables.replace("Select Metadata Set"),
			selenium.getText(
				"//span[contains(@class,'select-metadata')]/a/span"));
		selenium.clickAt("//span[contains(@class,'select-metadata')]/a/span",
			RuntimeVariables.replace("Select Metadata Set"));
		selenium.selectFrame("relative=top");
		Thread.sleep(5000);

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"//div[contains(@class,'aui-dialog-iframe-bd')]/iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame(
			"//div[contains(@class,'aui-dialog-iframe-bd')]/iframe");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible("//a[contains(.,'Song Information')]")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		assertEquals(RuntimeVariables.replace("Song Information"),
			selenium.getText("//a[contains(.,'Song Information')]"));
		selenium.click("//a[contains(.,'Song Information')]");
		selenium.selectFrame("relative=top");

		for (int second = 0;; second++) {
			if (second >= 90) {
				fail("timeout");
			}

			try {
				if (selenium.isVisible(
							"xPath=(//div[contains(@class,'aui-dialog-iframe-bd')])[2]/iframe")) {
					break;
				}
			}
			catch (Exception e) {
			}

			Thread.sleep(1000);
		}

		selenium.selectFrame(
			"xPath=(//div[contains(@class,'aui-dialog-iframe-bd')])[2]/iframe");
		assertEquals(RuntimeVariables.replace("Song Information"),
			selenium.getText("//table/tr/td"));
		selenium.clickAt("//input[@value='Save']",
			RuntimeVariables.replace("Save"));
		selenium.waitForPageToLoad("30000");
		loadRequiredJavaScriptModules();
		assertEquals(RuntimeVariables.replace(
				"Your request completed successfully."),
			selenium.getText("//div[@class='portlet-msg-success']"));
		assertTrue(selenium.isTextPresent("Music"));
		selenium.selectFrame("relative=top");
	}
}