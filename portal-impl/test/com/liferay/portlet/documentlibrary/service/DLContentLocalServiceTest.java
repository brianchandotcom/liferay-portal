/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.store.Store;
import java.io.ByteArrayInputStream;

/**
 * @author Tina Tian
 */
public class DLContentLocalServiceTest extends BaseServiceTestCase {

	public void setUp() throws Exception {
		super.setUp();

		_dlContentLocalService = 
			(DLContentLocalService)PortalBeanLocatorUtil.locate(
				DLContentLocalService.class.getName());
	}

	public void testAddDLContentByByteArray() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String path = randomString();

		DLContent dlContent = _dlContentLocalService.addContent(
			companyId, randomString(), nextLong(), repositoryId, path,
			Store.DEFAULT_VERSION, new byte[1024]);

		DLContent newDlContent = _dlContentLocalService.getContent(
			companyId, repositoryId, path);

		assertEquals(dlContent, newDlContent);
	}

	public void testAddDLContentByInputStream() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String path = randomString();

		DLContent dlContent = _dlContentLocalService.addContent(
			companyId, randomString(), nextLong(), repositoryId, path,
			Store.DEFAULT_VERSION, new ByteArrayInputStream(new byte[1024]), 
			1024);

		DLContent newDlContent = _dlContentLocalService.getContent(
			companyId, repositoryId, path);
		
		assertEquals(dlContent, newDlContent);
	}

	public void testUpdateDLContent() throws Exception {
		long companyId = nextLong();
		long oldRepositoryId = nextLong();
		long newRepositoryId = nextLong();
		String oldPath = randomString();
		String newPath = randomString();

		DLContent dlContent = _dlContentLocalService.addContent(
			companyId, randomString(), nextLong(), oldRepositoryId, oldPath,
			Store.DEFAULT_VERSION, new byte[1024]);

		_dlContentLocalService.updateDLContent(
			companyId, oldRepositoryId, newRepositoryId, oldPath, newPath);

		DLContent newDlContent = _dlContentLocalService.getContent(
			companyId, newRepositoryId, newPath);

		_dlContentLocalService.deleteDLContent(newDlContent);

		assertEquals(dlContent.getContentId(), newDlContent.getContentId());
		assertEquals(dlContent.getData(), newDlContent.getData());
		assertEquals(dlContent.getGroupId(), newDlContent.getGroupId());
		assertEquals(dlContent.getPortletId(), newDlContent.getPortletId());
		assertEquals(dlContent.getPrimaryKey(), newDlContent.getPrimaryKey());
		assertEquals(dlContent.getSize(), newDlContent.getSize());
		assertEquals(dlContent.getVersion(), newDlContent.getVersion());
	}

	private DLContentLocalService _dlContentLocalService;

}