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

package com.liferay.portal.portlet.container.upload.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.portal.kernel.servlet.ServletInputStreamAdapter;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.upload.FileItem;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upload.LiferayInputStream;
import com.liferay.portal.upload.LiferayServletRequest;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.PrefsPropsUtil;
import com.liferay.portal.util.test.PortletContainerTestUtil;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Manuel de la Peña
 */
@RunWith(Enclosed.class)
public class UploadServletRequestTest {

	@RunWith(Arquillian.class)
	public static class WhenCleaningUp {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldNotRemoveMultipartParameters() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			uploadServletRequest.cleanUp();

			Map<String, FileItem[]> multipartParameterMap =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertNotNull(multipartParameterMap);
			Assert.assertEquals(
				multipartParameterMap.toString(), 1,
				multipartParameterMap.size());
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenCreatingFromMainConstructor {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldAddProgressTrackerToSession() throws Exception {
			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			HttpServletRequest mockHttpServletRequest =
				(HttpServletRequest)liferayServletRequest.getRequest();

			new UploadServletRequestImpl(mockHttpServletRequest);

			HttpSession mockHttpSession = mockHttpServletRequest.getSession();

			Assert.assertNotNull(
				mockHttpSession.getAttribute(ProgressTracker.PERCENT));
		}

		@Test
		public void shouldNotPopulateParameters() throws Exception {
			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			HttpServletRequest mockHttpServletRequest =
				(HttpServletRequest)liferayServletRequest.getRequest();

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(mockHttpServletRequest);

			Map<String, FileItem[]> multipartParameterMap =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertNotNull(multipartParameterMap);
			Assert.assertTrue(
				multipartParameterMap.toString(),
				multipartParameterMap.isEmpty());

			Map<String, List<String>> regularParameterMap =
				uploadServletRequest.getRegularParameterMap();

			Assert.assertNotNull(regularParameterMap);
			Assert.assertTrue(
				regularParameterMap.toString(), regularParameterMap.isEmpty());
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenCreatingFromParametrizedConstructor {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldNotPopulateParametersWithEmptyParameters()
			throws Exception {

			Map<String, FileItem[]> fileParameters = Collections.emptyMap();

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Map<String, FileItem[]> multipartParameterMap =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertNotNull(multipartParameterMap);
			Assert.assertTrue(
				multipartParameterMap.toString(),
				multipartParameterMap.isEmpty());

			Map<String, List<String>> regularParameterMap =
				uploadServletRequest.getRegularParameterMap();

			Assert.assertNotNull(regularParameterMap);
			Assert.assertTrue(
				regularParameterMap.toString(), regularParameterMap.isEmpty());
		}

		@Test
		public void shouldPopulateMultipartParametersWithFileParameters()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Map<String, FileItem[]> multipartParameterMap =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertNotNull(multipartParameterMap);
			Assert.assertEquals(
				multipartParameterMap.toString(), 1,
				multipartParameterMap.size());

			Map<String, List<String>> regularParameterMap =
				uploadServletRequest.getRegularParameterMap();

			Assert.assertNotNull(regularParameterMap);
			Assert.assertTrue(
				regularParameterMap.toString(), regularParameterMap.isEmpty());
		}

		@Test
		public void shouldPopulateRegularParametersWithRegularParameters()
			throws Exception {

			Map<String, List<String>> regularParameters =
				PortletContainerTestUtil.getRegularParameters(10);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(), regularParameters);

			Map<String, FileItem[]> multipartParameterMap =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertNotNull(multipartParameterMap);
			Assert.assertTrue(
				multipartParameterMap.toString(),
				multipartParameterMap.isEmpty());

			Map<String, List<String>> regularParameterMap =
				uploadServletRequest.getRegularParameterMap();

			Assert.assertNotNull(regularParameterMap);
			Assert.assertEquals(
				regularParameterMap.toString(), 10, regularParameterMap.size());
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingContentType {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldReturnFirstFileItemContentType() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Map<String, FileItem[]> map =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				FileItem[] fileItems = entry.getValue();

				FileItem firstFileItem = fileItems[0];

				Assert.assertEquals(
					firstFileItem.getContentType(),
					uploadServletRequest.getContentType(key));
			}
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>());

			Assert.assertNull(uploadServletRequest.getContentType("name"));
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingFile {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldReturnAFile() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Map<String, FileItem[]> map =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				File file = uploadServletRequest.getFile(key);

				Assert.assertNotNull(file);
				Assert.assertTrue(file.exists());

				file = uploadServletRequest.getFile(key, true);

				Assert.assertNotNull(file);
				Assert.assertTrue(file.exists());
			}
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>());

			Assert.assertNull(uploadServletRequest.getFile("irrelevantName"));
			Assert.assertNull(
				uploadServletRequest.getFile("irrelevantName", true));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Assert.assertNull(uploadServletRequest.getFile("nonexistentFile"));
			Assert.assertNull(
				uploadServletRequest.getFile("nonexistentFile", true));
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingFileAsStream {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldReturnAStream() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Map<String, FileItem[]> map =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				InputStream inputStream = uploadServletRequest.getFileAsStream(
					key);

				Assert.assertNotNull(inputStream);

				inputStream = uploadServletRequest.getFileAsStream(key, true);

				Assert.assertNotNull(inputStream);
			}
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>());

			Assert.assertNull(
				uploadServletRequest.getFileAsStream("irrelevantName"));
			Assert.assertNull(
				uploadServletRequest.getFileAsStream("irrelevantName", true));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Assert.assertNull(
				uploadServletRequest.getFileAsStream("nonexistentFile"));
			Assert.assertNull(
				uploadServletRequest.getFileAsStream("nonexistentFile", true));
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingFileName {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldReturnFirstFileNameFromFileParameters()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Map<String, FileItem[]> map =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				String fileName = uploadServletRequest.getFileName(key);

				FileItem[] fileItems = entry.getValue();

				FileItem firstFileItem = fileItems[0];

				Assert.assertEquals(firstFileItem.getFileName(), fileName);
			}
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>());

			Assert.assertNull(
				uploadServletRequest.getFileName("irrelevantName"));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Assert.assertNull(
				uploadServletRequest.getFileName("nonexistentFile"));
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingFileNames {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldReturnAnArrayWithFileNamesFromFileParameters()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(10, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Map<String, FileItem[]> map =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertEquals(10, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				String[] fileNames = uploadServletRequest.getFileNames(key);

				FileItem[] fileItems = entry.getValue();

				Assert.assertEquals(fileItems.length, fileNames.length);
				Assert.assertEquals(2, fileNames.length);

				for (int i = 0; i < fileNames.length; i++) {
					Assert.assertEquals(
						fileItems[i].getFileName(), fileNames[i]);
				}
			}
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>());

			Assert.assertNull(
				uploadServletRequest.getFileNames("irrelevantName"));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Assert.assertNull(
				uploadServletRequest.getFileNames("nonexistentFile"));
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingFiles {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldReturnArrayWithStoreLocationsFromFileParameters()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(10, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Map<String, FileItem[]> map =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertEquals(10, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				File[] files = uploadServletRequest.getFiles(key);

				FileItem[] fileItems = entry.getValue();

				Assert.assertEquals(fileItems.length, files.length);
				Assert.assertEquals(2, files.length);

				for (int i = 0; i < files.length; i++) {
					File storeLocation = fileItems[i].getStoreLocation();

					Assert.assertEquals(
						storeLocation.getAbsolutePath(),
						files[i].getAbsolutePath());
				}
			}
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>());

			Assert.assertNull(uploadServletRequest.getFiles("irrelevantName"));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Assert.assertNull(uploadServletRequest.getFiles("nonexistentFile"));
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingFilesAsStream {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldReturnArrayWithStreamsFromFileParameters()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(10, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Map<String, FileItem[]> map =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertEquals(10, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				InputStream[] inputStreams =
					uploadServletRequest.getFilesAsStream(key);

				FileItem[] fileItems = entry.getValue();

				Assert.assertEquals(fileItems.length, inputStreams.length);
				Assert.assertEquals(2, inputStreams.length);

				for (int i = 0; i < inputStreams.length; i++) {
					Assert.assertTrue(
						IOUtils.contentEquals(
							fileItems[i].getInputStream(), inputStreams[i]));
				}
			}
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>());

			Assert.assertNull(
				uploadServletRequest.getFilesAsStream("irrelevantName"));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Assert.assertNull(
				uploadServletRequest.getFilesAsStream("nonexistentFile"));
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingFullFileName {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldReturnFirstFileNameFromFileParameters()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Map<String, FileItem[]> map =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				String fullFileName = uploadServletRequest.getFullFileName(key);

				FileItem[] fileItems = entry.getValue();

				FileItem firstFileItem = fileItems[0];

				Assert.assertEquals(
					firstFileItem.getFullFileName(), fullFileName);
			}
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>());

			Assert.assertNull(
				uploadServletRequest.getFullFileName("irrelevantName"));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Assert.assertNull(
				uploadServletRequest.getFullFileName("nonexistentFile"));
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingInputStream {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldNotReturnLiferayInputStream() throws Exception {
			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest());

			ServletInputStream inputStream =
				uploadServletRequest.getInputStream();

			Assert.assertFalse(inputStream instanceof LiferayInputStream);

			uploadServletRequest = new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>());

			inputStream = uploadServletRequest.getInputStream();

			Assert.assertFalse(inputStream instanceof LiferayInputStream);
		}

		@Test
		public void shouldReturnServletInputStreamAdapter() throws Exception {
			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest());

			ServletInputStream inputStream =
				uploadServletRequest.getInputStream();

			Assert.assertTrue(inputStream instanceof ServletInputStreamAdapter);
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingParameterNames {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldMergeRegularFileAndRequestParameters()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(10, _bytes);

			Map<String, List<String>> regularParameters =
				PortletContainerTestUtil.getRegularParameters(10);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			String parameter = RandomTestUtil.randomString();

			MockHttpServletRequest mockHttpServletRequest =
				(MockHttpServletRequest)liferayServletRequest.getRequest();

			mockHttpServletRequest.addParameter(parameter, parameter);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, regularParameters);

			Enumeration<String> parameterNames =
				uploadServletRequest.getParameterNames();

			List<String> parameterNamesList = Collections.list(parameterNames);

			for (Map.Entry<String, List<String>> entry :
					regularParameters.entrySet()) {

				Assert.assertTrue(parameterNamesList.contains(entry.getKey()));
			}

			for (Map.Entry<String, FileItem[]> entry :
					fileParameters.entrySet()) {

				Assert.assertTrue(parameterNamesList.contains(entry.getKey()));
			}

			Assert.assertTrue(parameterNamesList.contains(parameter));
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingParameterValues {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldMergeRegularAndRequestParameters() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(10, _bytes);

			Map<String, List<String>> regularParameters =
				PortletContainerTestUtil.getRegularParameters(10);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			String parameter = RandomTestUtil.randomString();

			MockHttpServletRequest mockHttpServletRequest =
				(MockHttpServletRequest)liferayServletRequest.getRequest();

			mockHttpServletRequest.addParameter(parameter, parameter);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, regularParameters);

			for (Map.Entry<String, List<String>> entry :
					regularParameters.entrySet()) {

				String key = entry.getKey();

				String[] parameterValues =
					uploadServletRequest.getParameterValues(key);

				List<String> parameterValuesList = ListUtil.fromArray(
					parameterValues);

				Assert.assertTrue(
					parameterValuesList.containsAll(entry.getValue()));
			}

			String[] requestParameterValues =
				uploadServletRequest.getParameterValues(parameter);

			ArrayUtil.contains(requestParameterValues, parameter);

			for (Map.Entry<String, FileItem[]> entry :
					fileParameters.entrySet()) {

				String key = entry.getKey();

				String[] parameterValues =
					uploadServletRequest.getParameterValues(key);

				List<String> parameterValuesList = ListUtil.fromArray(
					parameterValues);

				Assert.assertFalse(parameterValuesList.contains(key));
			}
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingSize {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldReturnFirstSizeFromFileParameters() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Map<String, FileItem[]> map =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				Long size = uploadServletRequest.getSize(key);

				FileItem[] fileItems = entry.getValue();

				FileItem firstFileItem = fileItems[0];

				Assert.assertEquals(firstFileItem.getSize(), size.longValue());
			}
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>());

			Assert.assertNull(uploadServletRequest.getSize("irrelevantName"));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Assert.assertNull(uploadServletRequest.getSize("nonexistentFile"));
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingTempDir {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldNotReturnPreferencesValueWhenModified()
			throws IOException {

			File tempDir = UploadServletRequestImpl.getTempDir();

			try {
				TemporaryFolder temporaryFolder = new TemporaryFolder();

				temporaryFolder.create();

				File newTempDir = temporaryFolder.getRoot();

				UploadServletRequestImpl.setTempDir(newTempDir);

				File currentTempDir = UploadServletRequestImpl.getTempDir();

				Assert.assertEquals(temporaryFolder.getRoot(), currentTempDir);
			}
			finally {
				UploadServletRequestImpl.setTempDir(tempDir);
			}
		}

		@Test
		public void shouldReturnPreferencesValue() {
			File tempDir = UploadServletRequestImpl.getTempDir();

			File expectedTempDir = new File(
				PrefsPropsUtil.getString(
					PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR,
					SystemProperties.get(SystemProperties.TMP_DIR)));

			Assert.assertEquals(expectedTempDir, tempDir);
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenIsFormField {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUp();
		}

		@Test
		public void shouldReturnFirstSizeFromFileParameters() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Map<String, FileItem[]> map =
				uploadServletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				FileItem[] fileItems = entry.getValue();

				FileItem firstFileItem = fileItems[0];

				Assert.assertEquals(
					firstFileItem.isFormField(),
					uploadServletRequest.isFormField(key));
			}
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>());

			Assert.assertNull(
				uploadServletRequest.isFormField("irrelevantName"));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(1, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_fileNameParameter, _bytes);

			UploadServletRequestImpl uploadServletRequest =
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>());

			Assert.assertNull(
				uploadServletRequest.isFormField("nonexistentFile"));
		}

	}

	private static void _setUp() throws Exception {
		_fileNameParameter = RandomTestUtil.randomString();
	}

	private static final byte[] _bytes =
		"Enterprise. Open Source. For Life.".getBytes();
	private static String _fileNameParameter;

}