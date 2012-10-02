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

package com.liferay.portalweb.portal.dbupgrade.sampledata6110.documentlibrary.folder;

import com.liferay.portalweb.portal.BaseTestCase;
import com.liferay.portalweb.portal.util.RuntimeVariables;

/**
 * @author Brian Wing Shun Chan
 */
public class ViewFolderTest extends BaseTestCase {
	public void testViewFolder() throws Exception {
		selenium.selectWindow("null");
		selenium.selectFrame("relative=top");
		selenium.open("/web/document-library-folder-community/");
		selenium.clickAt("link=Document Library Page",
			RuntimeVariables.replace("Document Library Page"));
		selenium.waitForPageToLoad("30000");
		selenium.waitForVisible("//div/a/span[contains(.,'Test1 Folder1')]");
		assertEquals(RuntimeVariables.replace("Test1 Folder1"),
			selenium.getText("//div/a/span[contains(.,'Test1 Folder1')]"));
	}
}