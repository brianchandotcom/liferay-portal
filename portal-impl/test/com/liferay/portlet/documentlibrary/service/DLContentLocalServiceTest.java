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
import com.liferay.portal.kernel.dao.jdbc.OutputBlob;
import com.liferay.portal.service.BaseServiceTestCase;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.store.Store;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.sql.Blob;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
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

		dlContent.setData(
			new OutputBlob(new ByteArrayInputStream(new byte[1024]), 1024));

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

		dlContent.setPath(newPath);
		dlContent.setRepositoryId(newRepositoryId);

		dlContent.setData(
			new OutputBlob(new ByteArrayInputStream(new byte[1024]), 1024));

		assertEquals(dlContent, newDlContent);

		_dlContentLocalService.deleteDLContent(newDlContent);
	}

	private void assertEquals(DLContent expect, DLContent actual)
		throws Exception {
		assertEquals(expect.getCompanyId(), actual.getCompanyId());
		assertEquals(expect.getContentId(), actual.getContentId());
		assertEquals(expect.getData(), actual.getData());
		assertEquals(expect.getGroupId(), actual.getGroupId());
		assertEquals(expect.getPath(), actual.getPath());
		assertEquals(expect.getPortletId(), actual.getPortletId());
		assertEquals(expect.getPrimaryKey(), actual.getPrimaryKey());
		assertEquals(expect.getRepositoryId(), actual.getRepositoryId());
		assertEquals(expect.getSize(), actual.getSize());
		assertEquals(expect.getVersion(), actual.getVersion());
	}

	private void assertEquals(Blob expect, Blob actual) throws Exception {
		InputStream expectInputStream = expect.getBinaryStream();
		InputStream actualInputStream = actual.getBinaryStream();

		while (true) {
			int expectValue = expectInputStream.read();
			int actualValue = actualInputStream.read();

			assertEquals(expectValue, actualValue);

			if (expectValue == -1) {
				break;
			}
		}

		expectInputStream.close();
		actualInputStream.close();
	}

	private DLContentLocalService _dlContentLocalService;

}