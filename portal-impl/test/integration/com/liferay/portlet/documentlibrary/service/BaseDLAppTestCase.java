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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.util.DLAppTestUtil;

import org.junit.After;
import org.junit.Before;

/**
 * @author Alexander Chow
 */
public abstract class BaseDLAppTestCase {

	@Before
	public void setUp() throws Exception {
		group = ServiceTestUtil.addGroup();

		parentFolder = DLAppTestUtil.addFolder(
			group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
			"Test Folder", true);
	}

	@After
	public void tearDown() throws Exception {
		if (parentFolder != null) {
			DLAppServiceUtil.deleteFolder(parentFolder.getFolderId());
		}
	}

	protected static final String CONTENT =
		"Content: Enterprise. Open Source. For Life.";

	protected Group group;
	protected Folder parentFolder;

}