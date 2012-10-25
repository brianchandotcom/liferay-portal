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

package com.liferay.portlet.documentlibrary.webdav;

import com.liferay.portal.kernel.test.ExecutionTestListeners;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.kernel.webdav.WebDAVUtil;
import com.liferay.portal.test.LiferayIntegrationJUnitTestRunner;
import com.liferay.portal.webdav.methods.Method;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * <p>
 * Based on Microsoft Office 2008 for OS X.
 * </p>
 *
 * @author Alexander Chow
 */
@ExecutionTestListeners(listeners = {WebDAVEnviornmentConfigTestListener.class})
@RunWith(LiferayIntegrationJUnitTestRunner.class)
public class WebDAVOSXTest extends BaseWebDAVTestCase {

	@Test
	public void testMSOffice0Setup() throws Exception {
		Class<?> clazz = getClass();

		_testFileBytes = FileUtil.getBytes(
			clazz.getResourceAsStream(_OFFICE_TEST_DOCX));
		_testMetaBytes = FileUtil.getBytes(
			clazz.getResourceAsStream(_OFFICE_TEST_META_DOCX));
		_testDeltaBytes = FileUtil.getBytes(
			clazz.getResourceAsStream(_OFFICE_TEST_DELTA_DOCX));
	}

	@Test
	public void testMSOffice1Create() throws Exception {
		Tuple tuple = null;

		assertCode(
			HttpServletResponse.SC_NOT_FOUND, servicePropFind(_TEST_FILE_NAME));
		assertCode(
			HttpServletResponse.SC_CREATED,
			servicePut(
				_TEST_FILE_NAME, _testFileBytes, getLock(_TEST_FILE_NAME)));

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
		"dependencies/OSX_Test_Delta.docx";

	private static final String _OFFICE_TEST_DOCX =
		"dependencies/OSX_Test.docx";

	private static final String _OFFICE_TEST_META_DOCX =
		"dependencies/OSX_Test_Meta.docx";

	private static final String _TEMP_FILE_NAME_1 = "Word Work File D_1.tmp";

	private static final String _TEMP_FILE_NAME_2 = "Word Work File L_2.tmp";

	private static final String _TEMP_META_NAME_1 = "._Word Work File D_1.tmp";

	private static final String _TEMP_META_NAME_2 = "._Word Work File L_2.tmp";

	private static final String _TEST_FILE_NAME = "Test.docx";

	private static final String _TEST_META_NAME = "._Test.docx";

	private static final String _USER_AGENT =
		"WebDAVFS/1.8 (01808000) Darwin/10.3.0 (i386)";

	private static byte[] _testDeltaBytes;
	private static byte[] _testFileBytes;
	private static byte[] _testMetaBytes;

	private Map<String, String> _lockMap = new HashMap<String, String>();

}