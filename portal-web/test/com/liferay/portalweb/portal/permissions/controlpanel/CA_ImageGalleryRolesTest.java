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
public class CA_ImageGalleryRolesTest extends BaseTestCase {
	public void testCA_ImageGalleryRoles() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.clickAt("link=Define Permissions", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_128_add-permissions",
			RuntimeVariables.replace("label=Image Gallery"));
		selenium.waitForPageToLoad("30000");
		selenium.check("_128_rowIds");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegalleryADD_IMAGE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegalleryPERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegalleryVIEW']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGFolderACCESS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGFolderADD_IMAGE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGFolderADD_SUBFOLDER']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGFolderDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGFolderPERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGFolderUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGFolderVIEW']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGImageDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGImagePERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGImageUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.imagegallery.model.IGImageVIEW']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
	}
}