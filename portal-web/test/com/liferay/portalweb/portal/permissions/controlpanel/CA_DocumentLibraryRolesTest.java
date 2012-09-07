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
public class CA_DocumentLibraryRolesTest extends BaseTestCase {
	public void testCA_DocumentLibraryRoles() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.clickAt("link=Define Permissions", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		selenium.select("_128_add-permissions",
			RuntimeVariables.replace("label=Document Library"));
		selenium.waitForPageToLoad("30000");
		selenium.check("_128_rowIds");
		selenium.check(
			"//input[@value='com.liferay.portlet.documentlibraryADD_FOLDER']");
		selenium.check(
			"//input[@value='com.liferay.portlet.documentlibraryADD_SHORTCUT']");
		selenium.check(
			"//input[@value='com.liferay.portlet.documentlibraryPERMISSIONS']");
		selenium.check(
			"//input[@value='com.liferay.portlet.documentlibraryVIEW']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileEntryADD_DISCUSSION']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileEntryDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileEntryDELETE_DISCUSSION']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileEntryPERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileEntryUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileEntryUPDATE_DISCUSSION']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileEntryVIEW']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFolderADD_DOCUMENT']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFolderADD_SHORTCUT']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFolderADD_SUBFOLDER']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFolderDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFolderPERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFolderUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFolderVIEW']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileShortcutADD_DISCUSSION']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileShortcutDELETE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileShortcutDELETE_DISCUSSION']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileShortcutPERMISSIONS']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileShortcutUPDATE']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileShortcutUPDATE_DISCUSSION']");
		selenium.check(
			"//input[@name='_128_rowIds' and @value='com.liferay.portlet.documentlibrary.model.DLFileShortcutVIEW']");
		selenium.clickAt("//input[@value='Save']", RuntimeVariables.replace(""));
		selenium.waitForPageToLoad("30000");
		assertTrue(selenium.isTextPresent("The role permissions were updated."));
	}
}