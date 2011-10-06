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

import java.util.List;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
public class DLContentLocalServiceTest extends BaseServiceTestCase {

	@Override
	public void setUp() throws Exception {
		super.setUp();

		_dlContentLocalService =
			(DLContentLocalService)PortalBeanLocatorUtil.locate(
				DLContentLocalService.class.getName());
	}

	public void testAddContentByByteArray() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String path = randomString();

		DLContent dlContent = _dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		DLContent newDLContent = _dlContentLocalService.getContent(
			companyId, repositoryId, path);

		assertEquals(dlContent, newDLContent);
	}

	public void testAddContentByInputStream() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String path = randomString();

		DLContent dlContent = _dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			new ByteArrayInputStream(_DATA_VERSION_1), 1024);

		DLContent newDLContent = _dlContentLocalService.getContent(
			companyId, repositoryId, path);

		assertEquals(dlContent, newDLContent);
	}

	public void testDeleteContent() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String path = randomString();

		_dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		boolean exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT);

		assertTrue(exist);

		_dlContentLocalService.deleteContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT);

		exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT);

		assertFalse(exist);
	}

	public void testDeleteContents() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String path = randomString();

		_dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);
		_dlContentLocalService.addContent(
			companyId, repositoryId, path, "1.1", _DATA_VERSION_2);

		boolean exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT);

		assertTrue(exist);

		exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path, "1.1");

		assertTrue(exist);

		_dlContentLocalService.deleteContents(companyId, repositoryId, path);

		exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT);

		assertFalse(exist);

		exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path, "1.1");

		assertFalse(exist);
	}

	public void testDeleteContentsByDirectory() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String directory = randomString();
		String subDirectory = directory + "/" + randomString();

		String path1 = directory + "/" + randomString();
		String path2 = subDirectory + "/" + randomString();

		_dlContentLocalService.addContent(
			companyId, repositoryId, path1, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		_dlContentLocalService.addContent(
			companyId, repositoryId, path2, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		boolean exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path1, Store.VERSION_DEFAULT);

		assertTrue(exist);

		exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path2, Store.VERSION_DEFAULT);

		assertTrue(exist);

		_dlContentLocalService.deleteContentsByDirectory(
			companyId, repositoryId, directory);

		exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path1, Store.VERSION_DEFAULT);

		assertFalse(exist);

		exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path2, Store.VERSION_DEFAULT);

		assertFalse(exist);
	}

	public void testGetContentLatest() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String path = randomString();

		DLContent dlContent1 = _dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		DLContent dlContent1Read = _dlContentLocalService.getContent(
			companyId, repositoryId, path);

		assertEquals(dlContent1, dlContent1Read);

		DLContent dlContent2 = _dlContentLocalService.addContent(
			companyId, repositoryId, path, "1.1", _DATA_VERSION_2);

		DLContent dlContent2Read = _dlContentLocalService.getContent(
			companyId, repositoryId, path);

		assertEquals(dlContent2, dlContent2Read);
	}

	public void testGetContentVersion() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String path = randomString();

		DLContent dlContent1 = _dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		DLContent dlContent1Read = _dlContentLocalService.getContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT);

		assertEquals(dlContent1, dlContent1Read);

		DLContent dlContent2 = _dlContentLocalService.addContent(
			companyId, repositoryId, path, "1.1", _DATA_VERSION_2);

		DLContent dlContent2Read = _dlContentLocalService.getContent(
			companyId, repositoryId, path, "1.1");

		assertEquals(dlContent2, dlContent2Read);
	}

	public void testGetContentsAll() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String path1 = randomString();
		String path2 = randomString();
		String path3 = randomString();

		DLContent dlContent1 = _dlContentLocalService.addContent(
			companyId, repositoryId, path1, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);
		DLContent dlContent2 = _dlContentLocalService.addContent(
			companyId, repositoryId, path2, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);
		DLContent dlContent3 = _dlContentLocalService.addContent(
			companyId, repositoryId, path3, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		List<DLContent> dlContents =
			_dlContentLocalService.getContents(companyId, repositoryId);

		assertEquals(3, dlContents.size());

		assertTrue(dlContents.contains(dlContent1));
		assertTrue(dlContents.contains(dlContent2));
		assertTrue(dlContents.contains(dlContent3));
	}

	public void testGetContentsVersions() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String path1 = randomString();
		String path2 = randomString();

		DLContent dlContent1 = _dlContentLocalService.addContent(
			companyId, repositoryId, path1, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);
		DLContent dlContent2 = _dlContentLocalService.addContent(
			companyId, repositoryId, path1, "1.1", _DATA_VERSION_2);

		DLContent dlContent3 = _dlContentLocalService.addContent(
			companyId, repositoryId, path2, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);
		DLContent dlContent4 = _dlContentLocalService.addContent(
			companyId, repositoryId, path2, "1.1", _DATA_VERSION_2);

		List<DLContent> dlContents1 =
			_dlContentLocalService.getContents(companyId, repositoryId, path1);

		assertEquals(2, dlContents1.size());

		assertTrue(dlContents1.contains(dlContent1));
		assertTrue(dlContents1.contains(dlContent2));

		List<DLContent> dlContents2 =
			_dlContentLocalService.getContents(companyId, repositoryId, path2);

		assertEquals(2, dlContents2.size());

		assertTrue(dlContents2.contains(dlContent3));
		assertTrue(dlContents2.contains(dlContent4));
	}

	public void testGetContentsByDirectory() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String directory = randomString();
		String subDirectory = randomString();

		String path1 = directory + "/" + randomString();
		String path2 = subDirectory + "/" + randomString();

		DLContent dlContent1 = _dlContentLocalService.addContent(
			companyId, repositoryId, path1, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);
		DLContent dlContent2 = _dlContentLocalService.addContent(
			companyId, repositoryId, path2, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		List<DLContent> dlContents =
			_dlContentLocalService.getContentsByDirectory(
				companyId, repositoryId, directory);

		assertEquals(1, dlContents.size());

		assertTrue(dlContents.contains(dlContent1));

		dlContents = _dlContentLocalService.getContentsByDirectory(
			companyId, repositoryId, subDirectory);

		assertEquals(1, dlContents.size());

		assertEquals(dlContent2, dlContents.get(0));
	}

	public void testHasContent() throws Exception {
		long companyId = nextLong();
		long repositoryId = nextLong();
		String path = randomString();

		// 1.0
		boolean exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT);

		assertFalse(exist);

		_dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT);

		assertTrue(exist);

		// 1.1
		exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path, "1.1");

		assertFalse(exist);

		_dlContentLocalService.addContent(
			companyId, repositoryId, path, "1.1", _DATA_VERSION_1);

		exist = _dlContentLocalService.hasContent(
			companyId, repositoryId, path, "1.1");

		assertTrue(exist);
	}

	public void testUpdateContent() throws Exception {
		long companyId = nextLong();
		long oldRepositoryId = nextLong();
		long newRepositoryId = nextLong();
		String oldPath = randomString();
		String newPath = randomString();

		DLContent dlContent = _dlContentLocalService.addContent(
			companyId, oldRepositoryId, oldPath, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		_dlContentLocalService.updateDLContent(
			companyId, oldRepositoryId, newRepositoryId, oldPath, newPath);

		DLContent newDLContent = _dlContentLocalService.getContent(
			companyId, newRepositoryId, newPath);

		dlContent.setRepositoryId(newRepositoryId);
		dlContent.setPath(newPath);

		assertEquals(dlContent, newDLContent);

		_dlContentLocalService.deleteDLContent(newDLContent);
	}

	protected void assertEquals(
			DLContent expectedDLContent, DLContent actualDLContent)
		throws Exception {

		assertEquals(
			expectedDLContent.getContentId(), actualDLContent.getContentId());
		assertEquals(
			expectedDLContent.getGroupId(), actualDLContent.getGroupId());
		assertEquals(
			expectedDLContent.getCompanyId(), actualDLContent.getCompanyId());
		assertEquals(
			expectedDLContent.getRepositoryId(),
			actualDLContent.getRepositoryId());
		assertEquals(expectedDLContent.getPath(), actualDLContent.getPath());
		assertEquals(
			expectedDLContent.getVersion(), actualDLContent.getVersion());
		assertEquals(expectedDLContent.getData(), actualDLContent.getData());
		assertEquals(expectedDLContent.getSize(), actualDLContent.getSize());
	}

	private static final int _DATA_SIZE = 1024;

	private static final byte[] _DATA_VERSION_1 = new byte[_DATA_SIZE];
	private static final byte[] _DATA_VERSION_2 = new byte[_DATA_SIZE];

	static {
		for (int i = 0; i < _DATA_SIZE; i++) {
			_DATA_VERSION_1[i] = (byte)i;
			_DATA_VERSION_2[i] = (byte)(i + 1);
		}
	}

	private DLContentLocalService _dlContentLocalService;

}