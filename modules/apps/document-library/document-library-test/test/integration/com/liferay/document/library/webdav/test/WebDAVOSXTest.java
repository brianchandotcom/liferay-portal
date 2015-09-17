/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
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

package com.liferay.document.library.webdav.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.repository.model.FileEntry;
import com.liferay.portal.kernel.repository.model.Folder;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.Sync;
import com.liferay.portal.kernel.test.rule.SynchronousDestinationTestRule;
import com.liferay.portal.kernel.test.util.ServiceContextTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.ContentTypes;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.kernel.webdav.methods.Method;
import com.liferay.portal.model.Group;
import com.liferay.portal.service.GroupLocalServiceUtil;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.model.DLFolderConstants;
import com.liferay.portlet.documentlibrary.service.DLAppLocalServiceUtil;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <p>
 * Based on Microsoft Office 2008 for OS X.
 * </p>
 *
 * @author Alexander Chow
 */
@RunWith(Arquillian.class)
@Sync
public class WebDAVOSXTest extends BaseWebDAVTestCase {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			new LiferayIntegrationTestRule(),
			SynchronousDestinationTestRule.INSTANCE,
			WebDAVEnvironmentConfigTestRule.INSTANCE);

	@Before
	public void setUp() throws Exception {
		Class<?> clazz = getClass();

		_testFileBytes = FileUtil.getBytes(clazz, _OFFICE_TEST_DOCX);
		_testMetaBytes = FileUtil.getBytes(clazz, _OFFICE_TEST_META_DOCX);
		_testDeltaBytes = FileUtil.getBytes(clazz, _OFFICE_TEST_DELTA_DOCX);

		servicePut(_TEST_FILE_NAME, _testFileBytes, getLock(_TEST_FILE_NAME));
	}

	@Test
	public void testGetFileWithEscapedCharactersInFileName() throws Exception {
		FileEntry fileEntry = null;

		try {
			Group group = GroupLocalServiceUtil.getFriendlyURLGroup(
				PortalUtil.getDefaultCompanyId(), getGroupFriendlyURL());

			Folder folder = DLAppLocalServiceUtil.getFolder(
				group.getGroupId(), DLFolderConstants.DEFAULT_PARENT_FOLDER_ID,
				getFolderName());

			fileEntry = DLAppLocalServiceUtil.addFileEntry(
				TestPropsValues.getUserId(), group.getGroupId(),
				folder.getFolderId(), _TEST_FILE_NAME_ILLEGAL_CHARACTERS,
				ContentTypes.APPLICATION_MSWORD,
				_TEST_FILE_NAME_ILLEGAL_CHARACTERS, StringPool.BLANK,
				StringPool.BLANK, _testFileBytes,
				ServiceContextTestUtil.getServiceContext(group.getGroupId()));

			assertCode(
				HttpServletResponse.SC_OK,
				serviceGet(_TEST_FILE_NAME_ILLEGAL_CHARACTERS_ESCAPED));
		}
		finally {
			if (fileEntry != null) {
				DLAppLocalServiceUtil.deleteFileEntry(
					fileEntry.getFileEntryId());
			}
		}
	}

	@Test
	public void testMSOffice1Create() throws Exception {
		Tuple tuple = null;

		for (int i = 0; i < 3; i++) {
			lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);
			unlock(_TEST_FILE_NAME);
		}

		lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);

		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(
				_TEST_FILE_NAME, _testFileBytes, getLock(_TEST_FILE_NAME)));

		unlock(_TEST_FILE_NAME);

		for (int i = 0; i < 3; i++) {
			lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);

			tuple = serviceGet(_TEST_FILE_NAME);

			assertCode(HttpServletResponse.SC_OK, tuple);
			Assert.assertArrayEquals(_testFileBytes, getResponseBody(tuple));

			unlock(_TEST_FILE_NAME);
		}

		for (int i = 0; i < 2; i++) {
			assertCode(
				HttpServletResponse.SC_NOT_FOUND,
				servicePropFind(_TEST_META_NAME));
			assertCode(
				HttpServletResponse.SC_CREATED,
				servicePut(_TEST_META_NAME, _testMetaBytes));

			lock(HttpServletResponse.SC_OK, _TEST_META_NAME);

			assertCode(
				HttpServletResponse.SC_CREATED,
				servicePut(
					_TEST_META_NAME, _testMetaBytes, getLock(_TEST_META_NAME)));
			assertCode(
				WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_TEST_META_NAME));

			tuple = serviceGet(_TEST_META_NAME);

			assertCode(HttpServletResponse.SC_OK, tuple);
			Assert.assertArrayEquals(_testMetaBytes, getResponseBody(tuple));

			unlock(_TEST_META_NAME);

			assertCode(
				HttpServletResponse.SC_NO_CONTENT,
				serviceDelete(_TEST_META_NAME));
		}

		for (int i = 0; i < 3; i++) {
			if (i == 1) {
				lock(HttpServletResponse.SC_OK, _TEST_META_NAME);

				tuple = serviceGet(_TEST_META_NAME);

				assertCode(HttpServletResponse.SC_OK, tuple);
				Assert.assertArrayEquals(
					_testMetaBytes, getResponseBody(tuple));
			}
			else {
				lock(HttpServletResponse.SC_CREATED, _TEST_META_NAME);

				tuple = serviceGet(_TEST_META_NAME);

				assertCode(HttpServletResponse.SC_OK, tuple);
				Assert.assertTrue(getResponseBody(tuple).length == 0);
			}

			unlock(_TEST_META_NAME);

			if (i == 0) {
				assertCode(
					HttpServletResponse.SC_CREATED,
					servicePut(
						_TEST_META_NAME, _testMetaBytes,
						getLock(_TEST_META_NAME)));

				tuple = serviceGet(_TEST_META_NAME);

				assertCode(HttpServletResponse.SC_OK, tuple);
				Assert.assertArrayEquals(
					_testMetaBytes, getResponseBody(tuple));
				assertCode(
					WebDAVUtil.SC_MULTI_STATUS,
					servicePropFind(_TEST_META_NAME));
			}
		}
	}

	@Test
	public void testMSOffice2Open() throws Exception {
		Tuple tuple = null;

		assertCode(
			WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_TEST_FILE_NAME));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind("MCF-Test.docx"));

		lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);

		tuple = serviceGet(_TEST_FILE_NAME);

		assertCode(HttpServletResponse.SC_OK, tuple);
		Assert.assertArrayEquals(_testFileBytes, getResponseBody(tuple));

		unlock(_TEST_FILE_NAME);
		lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);

		tuple = serviceGet(_TEST_FILE_NAME);

		assertCode(HttpServletResponse.SC_OK, tuple);
		Assert.assertArrayEquals(_testFileBytes, getResponseBody(tuple));
	}

	@Test
	public void testMSOffice3Modify() throws Exception {
		Tuple tuple = null;

		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_FILE_NAME_1));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind("MCF-Word Work File D_1.tmp"));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_FILE_NAME_1));
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(_TEMP_FILE_NAME_1, _testDeltaBytes));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_META_NAME_1));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_META_NAME_1));
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(_TEMP_META_NAME_1, _testMetaBytes));

		lock(HttpServletResponse.SC_OK, _TEMP_META_NAME_1);

		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(
				_TEMP_META_NAME_1, _testMetaBytes, getLock(_TEMP_META_NAME_1)));
		assertCode(
			WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_TEMP_FILE_NAME_1));

		unlock(_TEMP_META_NAME_1);
		lock(HttpServletResponse.SC_OK, _TEMP_FILE_NAME_1);
		unlock(_TEMP_FILE_NAME_1);
		lock(HttpServletResponse.SC_OK, _TEMP_FILE_NAME_1);

		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(
				_TEMP_FILE_NAME_1, _testDeltaBytes,
				getLock(_TEMP_FILE_NAME_1)));
		assertCode(
			WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_TEMP_FILE_NAME_1));

		unlock(_TEST_FILE_NAME);
		lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);

		tuple = serviceGet(_TEST_FILE_NAME);

		assertCode(HttpServletResponse.SC_OK, tuple);
		Assert.assertArrayEquals(_testFileBytes, getResponseBody(tuple));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind("Backup of Test.docx"));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_FILE_NAME_2));

		unlock(_TEST_FILE_NAME);

		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_FILE_NAME_2));
		assertCode(
			HttpServletResponse.SC_CREATED,
			serviceCopyOrMove(Method.MOVE, _TEST_FILE_NAME, _TEMP_FILE_NAME_2));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind(_TEST_META_NAME));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_META_NAME_2));

		for (int i = 0; i < 2; i++) {
			lock(HttpServletResponse.SC_OK, _TEMP_FILE_NAME_2);

			tuple = serviceGet(_TEMP_FILE_NAME_2);

			assertCode(HttpServletResponse.SC_OK, tuple);
			Assert.assertArrayEquals(_testFileBytes, getResponseBody(tuple));

			unlock(_TEMP_FILE_NAME_2);
		}

		for (int i = 0; i < 2; i++) {
			String orig = _TEMP_FILE_NAME_1;
			String dest = _TEST_FILE_NAME;

			if (i == 1) {
				orig = _TEMP_META_NAME_1;
				dest = _TEST_META_NAME;
			}

			assertCode(HttpServletResponse.SC_NOT_FOUND, servicePropFind(dest));
			assertCode(HttpServletResponse.SC_NOT_FOUND, servicePropFind(dest));

			if (i == 0) {
				assertCode(
					HttpServletResponse.SC_CREATED,
					serviceCopyOrMove(Method.MOVE, orig, dest, getLock(orig)));

				moveLock(orig, dest);
			}
		}

		for (int i = 0; i < 2; i++) {
			lock(HttpServletResponse.SC_OK, _TEST_FILE_NAME);

			tuple = serviceGet(_TEST_FILE_NAME);

			assertCode(HttpServletResponse.SC_OK, tuple);
			Assert.assertArrayEquals(_testDeltaBytes, getResponseBody(tuple));

			unlock(_TEST_FILE_NAME);
		}

		lock(HttpServletResponse.SC_CREATED, _TEST_META_NAME);

		tuple = serviceGet(_TEST_META_NAME);

		assertCode(HttpServletResponse.SC_OK, tuple);
		Assert.assertTrue(getResponseBody(tuple).length == 0);
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(
				_TEST_META_NAME, _testMetaBytes, getLock(_TEST_META_NAME)));
		assertCode(
			WebDAVUtil.SC_MULTI_STATUS, servicePropFind(_TEST_META_NAME));

		unlock(_TEST_META_NAME);
		unlock(_TEMP_FILE_NAME_2);

		assertCode(
			HttpServletResponse.SC_NO_CONTENT,
			serviceDelete(_TEMP_FILE_NAME_2));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_META_NAME_2));
		assertCode(
			HttpServletResponse.SC_NOT_FOUND,
			servicePropFind(_TEMP_FILE_NAME_2));
	}

	protected String getLock(String fileName) {
		return _lockMap.get(fileName);
	}

	@Override
	protected String getUserAgent() {
		return _USER_AGENT;
	}

	protected void lock(int statusCode, String fileName) {
		Tuple tuple = serviceLock(fileName, null, 0);

		assertCode(statusCode, tuple);

		_lockMap.put(fileName, getLock(tuple));
	}

	protected void moveLock(String src, String dest) {
		String lock = _lockMap.remove(src);

		if (lock != null) {
			_lockMap.put(dest, lock);
		}
	}

	protected void unlock(String fileName) {
		String lock = _lockMap.remove(fileName);

		assertCode(
			HttpServletResponse.SC_NO_CONTENT, serviceUnlock(fileName, lock));
	}

	private static final String _OFFICE_TEST_DELTA_DOCX =
		"/com/liferay/document/library/dependencies/OSX_Test_Delta.docx";

	private static final String _OFFICE_TEST_DOCX =
		"/com/liferay/document/library/dependencies/OSX_Test.docx";

	private static final String _OFFICE_TEST_META_DOCX =
		"/com/liferay/document/library/dependencies/OSX_Test_Meta.docx";

	private static final String _TEMP_FILE_NAME_1 = "Word Work File D_1.tmp";

	private static final String _TEMP_FILE_NAME_2 = "Word Work File L_2.tmp";

	private static final String _TEMP_META_NAME_1 = "._Word Work File D_1.tmp";

	private static final String _TEMP_META_NAME_2 = "._Word Work File L_2.tmp";

	private static final String _TEST_FILE_NAME = "Test.docx";

	private static final String _TEST_FILE_NAME_ILLEGAL_CHARACTERS =
		"Test/0.docx";

	private static final String _TEST_FILE_NAME_ILLEGAL_CHARACTERS_ESCAPED =
		"Test" + PropsValues.DL_WEBDAV_SUBSTITUTION_CHAR + "0.docx";

	private static final String _TEST_META_NAME = "._Test.docx";

	private static final String _USER_AGENT =
		"WebDAVFS/1.8 (01808000) Darwin/10.3.0 (i386)";

	private static byte[] _testDeltaBytes;
	private static byte[] _testFileBytes;
	private static byte[] _testMetaBytes;

	private final Map<String, String> _lockMap = new HashMap<>();

}