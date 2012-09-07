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

package com.liferay.portalweb.portal.permissions.controlpanel;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class CA_MessageBoardsRolesTest extends BaseTestCase {
	public void testCA_MessageBoardsRoles() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.clickAt("link=Define Permissions", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_128_add-permissions",
			RuntimeVariables.replace("label=Message Boards"));
		selenium.waitForPageToLoad("30000");
		selenium.check("_128_rowIds");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboardsBAN_USER']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryADD_FILE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryADD_MESSAGE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryADD_SUBCATEGORY']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryMOVE_THREAD']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryPERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryREPLY_TO_MESSAGE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategorySUBSCRIBE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryUPDATE_THREAD_PRIORITY']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBCategoryVIEW']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBMessageDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBMessagePERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBMessageSUBSCRIBE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBMessageUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.messageboards.model.MBMessageVIEW']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
	}
}