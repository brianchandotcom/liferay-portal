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

package com.liferay.portlet.documentlibrary.service;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.test.AssertUtils;
import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.service.ServiceTestUtil;
import com.liferay.portal.test.EnvironmentExecutionTestListener;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.test.TransactionalExecutionTestListener;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portlet.documentlibrary.store.Store;

import java.io.ByteArrayInputStream;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Tina Tian
 * @author Shuyang Zhou
 */
@ExecutionTestListeners(
	listeners = {
		EnvironmentExecutionTestListener.class,
		TransactionalExecutionTestListener.class
	})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class DLContentLocalServiceTest {

	@Before
	public void setUp() throws Exception {
		_dlContentLocalService =
			(DLContentLocalService)PortalBeanLocatorUtil.locate(
				DLContentLocalService.class.getName());
	}

	@Test
	public void testAddContentByByteArray() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String path = ServiceTestUtil.randomString();

		DLContent addDLContent = _dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		DLContent getDLContent = _dlContentLocalService.getContent(
			companyId, repositoryId, path);

		Assert.assertEquals(addDLContent, getDLContent);
	}

	@Test
	public void testAddContentByInputStream() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String path = ServiceTestUtil.randomString();

		DLContent addDLContent = _dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			new ByteArrayInputStream(_DATA_VERSION_1), 1024);

		DLContent getDLContent = _dlContentLocalService.getContent(
			companyId, repositoryId, path);

		Assert.assertEquals(addDLContent, getDLContent);
	}

	@Test
	public void testDeleteContent() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String path = ServiceTestUtil.randomString();

		_dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		Assert.assertTrue(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path, Store.VERSION_DEFAULT));

		_dlContentLocalService.deleteContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT);

		Assert.assertFalse(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path, Store.VERSION_DEFAULT));
	}

	@Test
	public void testDeleteContents() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String path = ServiceTestUtil.randomString();

		_dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);
		_dlContentLocalService.addContent(
			companyId, repositoryId, path, "1.1", _DATA_VERSION_2);

		Assert.assertTrue(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path, Store.VERSION_DEFAULT));
		Assert.assertTrue(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path, "1.1"));

		_dlContentLocalService.deleteContents(companyId, repositoryId, path);

		Assert.assertFalse(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path, "1.1"));
	}

	@Test
	public void testDeleteContentsByDirectory() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();

		String directory = ServiceTestUtil.randomString();

		String path1 = directory + "/" + ServiceTestUtil.randomString();
		String path2 =
			directory + "/" + ServiceTestUtil.randomString() + "/" +
				ServiceTestUtil.randomString();

		_dlContentLocalService.addContent(
			companyId, repositoryId, path1, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		_dlContentLocalService.addContent(
			companyId, repositoryId, path2, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		Assert.assertTrue(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path1, Store.VERSION_DEFAULT));
		Assert.assertTrue(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path2, Store.VERSION_DEFAULT));

		_dlContentLocalService.deleteContentsByDirectory(
			companyId, repositoryId, directory);

		Assert.assertFalse(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path1, Store.VERSION_DEFAULT));
		Assert.assertFalse(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path2, Store.VERSION_DEFAULT));
	}

	@Test
	public void testGetContentLatest() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String path = ServiceTestUtil.randomString();

		DLContent addDLContent1 = _dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		DLContent getDLContent1 = _dlContentLocalService.getContent(
			companyId, repositoryId, path);

		Assert.assertEquals(addDLContent1, getDLContent1);

		DLContent addDLContent2 = _dlContentLocalService.addContent(
			companyId, repositoryId, path, "1.1", _DATA_VERSION_2);

		DLContent getDLContent2 = _dlContentLocalService.getContent(
			companyId, repositoryId, path);

		Assert.assertEquals(addDLContent2, getDLContent2);
	}

	@Test
	public void testGetContentsAll() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();

		DLContent dlContent1 = _dlContentLocalService.addContent(
			companyId, repositoryId, ServiceTestUtil.randomString(),
			Store.VERSION_DEFAULT, _DATA_VERSION_1);
		DLContent dlContent2 = _dlContentLocalService.addContent(
			companyId, repositoryId, ServiceTestUtil.randomString(),
			Store.VERSION_DEFAULT, _DATA_VERSION_1);
		DLContent dlContent3 = _dlContentLocalService.addContent(
			companyId, repositoryId, ServiceTestUtil.randomString(),
			Store.VERSION_DEFAULT, _DATA_VERSION_1);

		List<DLContent> dlContents = _dlContentLocalService.getContents(
			companyId, repositoryId);

		Assert.assertEquals(3, dlContents.size());
		Assert.assertTrue(dlContents.contains(dlContent1));
		Assert.assertTrue(dlContents.contains(dlContent2));
		Assert.assertTrue(dlContents.contains(dlContent3));
	}

	@Test
	public void testGetContentsByDirectory() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String path1 = ServiceTestUtil.randomString();
		String path2 = ServiceTestUtil.randomString();

		DLContent dlContent1 = _dlContentLocalService.addContent(
			companyId, repositoryId,
			path1 + "/" + ServiceTestUtil.randomString(), Store.VERSION_DEFAULT,
			_DATA_VERSION_1);
		DLContent dlContent2 = _dlContentLocalService.addContent(
			companyId, repositoryId,
			path2 + "/" + ServiceTestUtil.randomString(), Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		List<DLContent> dlContents =
			_dlContentLocalService.getContentsByDirectory(
				companyId, repositoryId, path1);

		Assert.assertEquals(1, dlContents.size());
		Assert.assertTrue(dlContents.contains(dlContent1));

		dlContents = _dlContentLocalService.getContentsByDirectory(
			companyId, repositoryId, path2);

		Assert.assertEquals(1, dlContents.size());
		Assert.assertEquals(dlContent2, dlContents.get(0));
	}

	@Test
	public void testGetContentsVersions() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String path1 = ServiceTestUtil.randomString();
		String path2 = ServiceTestUtil.randomString();

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

		List<DLContent> dlContents1 = _dlContentLocalService.getContents(
			companyId, repositoryId, path1);

		Assert.assertEquals(2, dlContents1.size());
		Assert.assertTrue(dlContents1.contains(dlContent1));
		Assert.assertTrue(dlContents1.contains(dlContent2));

		List<DLContent> dlContents2 = _dlContentLocalService.getContents(
			companyId, repositoryId, path2);

		Assert.assertEquals(2, dlContents2.size());
		Assert.assertTrue(dlContents2.contains(dlContent3));
		Assert.assertTrue(dlContents2.contains(dlContent4));
	}

	@Test
	@Transactional
	public void testGetContentVersion() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String path = ServiceTestUtil.randomString();

		DLContent addDLContent1 = _dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		DLContent getDLContent1 = _dlContentLocalService.getContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT);

		assertEquals(addDLContent1, getDLContent1);

		DLContent addDLContent2 = _dlContentLocalService.addContent(
			companyId, repositoryId, path, "1.1", _DATA_VERSION_2);

		DLContent getDLContent2 = _dlContentLocalService.getContent(
			companyId, repositoryId, path, "1.1");

		assertEquals(addDLContent2, getDLContent2);
	}

	@Test
	public void testHasContent() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long repositoryId = ServiceTestUtil.nextLong();
		String path = ServiceTestUtil.randomString();

		// 1.0

		Assert.assertFalse(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path, Store.VERSION_DEFAULT));

		_dlContentLocalService.addContent(
			companyId, repositoryId, path, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		Assert.assertTrue(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path, Store.VERSION_DEFAULT));

		// 1.1

		Assert.assertFalse(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path, "1.1"));

		_dlContentLocalService.addContent(
			companyId, repositoryId, path, "1.1", _DATA_VERSION_1);

		Assert.assertTrue(
			_dlContentLocalService.hasContent(
				companyId, repositoryId, path, "1.1"));
	}

	@Test
	@Transactional
	public void testUpdateContent() throws Exception {
		long companyId = ServiceTestUtil.nextLong();
		long oldRepositoryId = ServiceTestUtil.nextLong();
		long newRepositoryId = ServiceTestUtil.nextLong();
		String oldPath = ServiceTestUtil.randomString();
		String newPath = ServiceTestUtil.randomString();

		DLContent addDLContent = _dlContentLocalService.addContent(
			companyId, oldRepositoryId, oldPath, Store.VERSION_DEFAULT,
			_DATA_VERSION_1);

		_dlContentLocalService.updateDLContent(
			companyId, oldRepositoryId, newRepositoryId, oldPath, newPath);

		DLContent getDLContent = _dlContentLocalService.getContent(
			companyId, newRepositoryId, newPath);

		addDLContent.setRepositoryId(newRepositoryId);
		addDLContent.setPath(newPath);

		assertEquals(addDLContent, getDLContent);

		_dlContentLocalService.deleteDLContent(getDLContent);
	}

	protected void assertEquals(
			DLContent expectedDLContent, DLContent actualDLContent)
		throws Exception {

		Assert.assertEquals(
			expectedDLContent.getContentId(), actualDLContent.getContentId());
		Assert.assertEquals(
			expectedDLContent.getGroupId(), actualDLContent.getGroupId());
		Assert.assertEquals(
			expectedDLContent.getCompanyId(), actualDLContent.getCompanyId());
		Assert.assertEquals(
			expectedDLContent.getRepositoryId(),
			actualDLContent.getRepositoryId());
		Assert.assertEquals(
			expectedDLContent.getPath(), actualDLContent.getPath());
		Assert.assertEquals(
			expectedDLContent.getVersion(), actualDLContent.getVersion());
		AssertUtils.assertEquals(
			expectedDLContent.getData(), actualDLContent.getData());
		Assert.assertEquals(
			expectedDLContent.getSize(), actualDLContent.getSize());
	}

	private static final int _DATA_SIZE = 1024;

	private static final byte[] _DATA_VERSION_1 = new byte[_DATA_SIZE];

	private static final byte[] _DATA_VERSION_2 = new byte[_DATA_SIZE];

	private DLContentLocalService _dlContentLocalService;

	static {
		for (int i = 0; i < _DATA_SIZE; i++) {
			_DATA_VERSION_1[i] = (byte)i;
			_DATA_VERSION_2[i] = (byte)(i + 1);
		}
	}

}