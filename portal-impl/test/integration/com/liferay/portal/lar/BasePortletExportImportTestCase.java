/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.lar;

import com.liferay.portal.RequiredGroupException;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Layout;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.service.LayoutLocalServiceUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.util.GroupTestUtil;
import com.liferay.portal.util.LayoutTestUtil;

import java.io.File;

import org.junit.After;
import org.junit.Before;

import org.powermock.api.mockito.PowerMockito;

/**
 * @author Juan Fernández
 */
public class BasePortletExportImportTestCase extends PowerMockito {

	@Before
	public void setUp() throws Exception {
		group = GroupTestUtil.addGroup();

		layout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());

		// Delete and readd to ensure a different layout ID (not ID or UUID).
		// See LPS-32132.

		LayoutLocalServiceUtil.deleteLayout(layout, true, new ServiceContext());

		layout = LayoutTestUtil.addLayout(
			group.getGroupId(), ServiceTestUtil.randomString());
	}

	@After
	public void tearDown() throws Exception {
		try {
			GroupLocalServiceUtil.deleteGroup(group);
			GroupLocalServiceUtil.deleteGroup(importedGroup);
		}
		catch (RequiredGroupException rge) {
		}

		LayoutLocalServiceUtil.deleteLayout(layout);
		LayoutLocalServiceUtil.deleteLayout(importedLayout);

		if ((larFile != null) && larFile.exists()) {
			FileUtil.delete(larFile);
		}
	}

	protected Group group;
	protected Group importedGroup;
	protected Layout importedLayout;
	protected File larFile;
	protected Layout layout;

}