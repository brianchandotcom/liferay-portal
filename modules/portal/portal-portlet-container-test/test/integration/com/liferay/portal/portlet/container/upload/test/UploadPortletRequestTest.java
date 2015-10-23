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
import com.liferay.portal.kernel.upload.UploadPortletRequest;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.ProgressTracker;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;
import com.liferay.portal.upload.LiferayInputStream;
import com.liferay.portal.upload.LiferayServletRequest;
import com.liferay.portal.upload.UploadPortletRequestImpl;
import com.liferay.portal.upload.UploadServletRequestImpl;
import com.liferay.portal.util.test.PortletContainerTestUtil;

import java.io.File;
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
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Manuel de la Peña
 */
@RunWith(Enclosed.class)
public class UploadPortletRequestTest {

	@RunWith(Arquillian.class)
	public static final class WhenBuildingFromMainConstructor {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUpScenario();
		}

		@Test
		public void shouldAddProgressTrackerToSession() throws Exception {
			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			HttpServletRequest mockHttpServletRequest =
				(HttpServletRequest)liferayServletRequest.getRequest();

			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(mockHttpServletRequest), null,
				_portletNamespace);

			HttpSession httpSession = mockHttpServletRequest.getSession();

			Assert.assertNotNull(
				httpSession.getAttribute(ProgressTracker.PERCENT));
		}

		@Test
		public void shouldNotPopulateParameters() throws Exception {
			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			HttpServletRequest mockHttpServletRequest =
				(HttpServletRequest)liferayServletRequest.getRequest();

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(mockHttpServletRequest), null,
					_portletNamespace);

			Map<String, FileItem[]> multipartParameterMap =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertNotNull(multipartParameterMap);
			Assert.assertEquals(0, multipartParameterMap.size());

			Map<String, List<String>> regularParameterMap =
				uploadPortletRequest.getRegularParameterMap();

			Assert.assertNotNull(regularParameterMap);
			Assert.assertEquals(0, regularParameterMap.size());
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenBuildingFromParametrizedConstructor {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUpScenario();
		}

		@Test
		public void shouldNotPopulateParametersWithEmptyParameters()
			throws Exception {

			Map<String, FileItem[]> fileParameters = Collections.emptyMap();

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Map<String, FileItem[]> multipartParameterMap =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertNotNull(multipartParameterMap);
			Assert.assertEquals(0, multipartParameterMap.size());

			Map<String, List<String>> regularParameterMap =
				uploadPortletRequest.getRegularParameterMap();

			Assert.assertNotNull(regularParameterMap);
			Assert.assertEquals(0, regularParameterMap.size());
		}

		@Test
		public void shouldPopulateMultipartParametersWithFileParameters()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Map<String, FileItem[]> multipartParameterMap =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertNotNull(multipartParameterMap);
			Assert.assertEquals(1, multipartParameterMap.size());

			Map<String, List<String>> regularParameterMap =
				uploadPortletRequest.getRegularParameterMap();

			Assert.assertNotNull(regularParameterMap);
			Assert.assertEquals(0, regularParameterMap.size());
		}

		@Test
		public void shouldPopulateRegularParametersWithRegularParameters()
			throws Exception {

			Map<String, List<String>> regularParameters =
				PortletContainerTestUtil.getRegularParameters(10);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						new HashMap<String, FileItem[]>(), regularParameters),
					null, _portletNamespace);

			Map<String, FileItem[]> multipartParameterMap =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertNotNull(multipartParameterMap);
			Assert.assertEquals(0, multipartParameterMap.size());

			Map<String, List<String>> regularParameterMap =
				uploadPortletRequest.getRegularParameterMap();

			Assert.assertNotNull(regularParameterMap);
			Assert.assertEquals(10, regularParameterMap.size());
		}

	}

	@RunWith(Arquillian.class)
	public static final class WhenCleaningUp {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUpScenario();
		}

		@Test
		public void shouldNotRemoveMultipartParameters() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			uploadPortletRequest.cleanUp();

			Map<String, FileItem[]> multipartParameterMap =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertNotNull(multipartParameterMap);
			Assert.assertEquals(1, multipartParameterMap.size());
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
			_setUpScenario();
		}

		@Test
		public void shouldReturnFirstFileItemContentType() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Map<String, FileItem[]> multipartParameterMap =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, multipartParameterMap.size());

			for (Map.Entry<String, FileItem[]> entry :
					multipartParameterMap.entrySet()) {

				String key = entry.getKey();

				FileItem[] fileItems = entry.getValue();

				FileItem firstFileItem = fileItems[0];

				Assert.assertEquals(
					firstFileItem.getContentType(),
					uploadPortletRequest.getContentType(key));
			}
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						new HashMap<String, FileItem[]>(),
						new HashMap<String, List<String>>()), null,
					_portletNamespace);

			Assert.assertNull(uploadPortletRequest.getContentType("name"));
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
			_setUpScenario();
		}

		@Test
		public void shouldReturnAFile() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Map<String, FileItem[]> map =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				File file = uploadPortletRequest.getFile(key);

				Assert.assertNotNull(file);
				Assert.assertTrue(file.exists());

				file = uploadPortletRequest.getFile(key, true);

				Assert.assertNotNull(file);
				Assert.assertTrue(file.exists());
			}
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						new HashMap<String, FileItem[]>(),
						new HashMap<String, List<String>>()), null,
					_portletNamespace);

			Assert.assertNull(uploadPortletRequest.getFile("nevermind-name"));
			Assert.assertNull(
				uploadPortletRequest.getFile("nevermind-name", true));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Assert.assertNull(
				uploadPortletRequest.getFile("not-existing-file"));
			Assert.assertNull(
				uploadPortletRequest.getFile("not-existing-file", true));
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
			_setUpScenario();
		}

		@Test
		public void shouldReturnAStream() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Map<String, FileItem[]> multipartParametersMap =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, multipartParametersMap.size());

			for (Map.Entry<String, FileItem[]> entry :
					multipartParametersMap.entrySet()) {

				String key = entry.getKey();

				InputStream inputStream = uploadPortletRequest.getFileAsStream(
					key);

				Assert.assertNotNull(inputStream);

				inputStream = uploadPortletRequest.getFileAsStream(key, true);

				Assert.assertNotNull(inputStream);
			}
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						new HashMap<String, FileItem[]>(),
						new HashMap<String, List<String>>()), null,
					_portletNamespace);

			Assert.assertNull(
				uploadPortletRequest.getFileAsStream("nevermind-name"));
			Assert.assertNull(
				uploadPortletRequest.getFileAsStream("nevermind-name", true));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Assert.assertNull(
				uploadPortletRequest.getFileAsStream("not-existing-file"));
			Assert.assertNull(
				uploadPortletRequest.getFileAsStream(
					"not-existing-file", true));
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
			_setUpScenario();
		}

		@Test
		public void shouldReturnFirstFileNameFromFileParameters()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Map<String, FileItem[]> map =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				String fileName = uploadPortletRequest.getFileName(key);

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
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						new HashMap<String, FileItem[]>(),
						new HashMap<String, List<String>>()), null,
					_portletNamespace);

			Assert.assertNull(
				uploadPortletRequest.getFileName("nevermind-name"));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Assert.assertNull(
				uploadPortletRequest.getFileName("not-existing-file"));
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
			_setUpScenario();
		}

		@Test
		public void shouldReturnFileNamesFromFileParameters() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					10, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Map<String, FileItem[]> multipartParameterMap =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertEquals(10, multipartParameterMap.size());

			for (Map.Entry<String, FileItem[]> entry :
					multipartParameterMap.entrySet()) {

				String key = entry.getKey();

				String[] fileNames = uploadPortletRequest.getFileNames(key);

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
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						new HashMap<String, FileItem[]>(),
						new HashMap<String, List<String>>()), null,
					_portletNamespace);

			Assert.assertNull(
				uploadPortletRequest.getFileNames("nevermind-name"));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Assert.assertNull(
				uploadPortletRequest.getFileNames("not-existing-file"));
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
			_setUpScenario();
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						new HashMap<String, FileItem[]>(),
						new HashMap<String, List<String>>()), null,
					_portletNamespace);

			Assert.assertNull(uploadPortletRequest.getFiles("nevermind-name"));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Assert.assertNull(
				uploadPortletRequest.getFiles("not-existing-file"));
		}

		@Test
		public void shouldReturnStoreLocationsFromFileParameters()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					10, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Map<String, FileItem[]> multipartParameterMap =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertEquals(10, multipartParameterMap.size());

			for (Map.Entry<String, FileItem[]> entry :
					multipartParameterMap.entrySet()) {

				String key = entry.getKey();

				File[] files = uploadPortletRequest.getFiles(key);

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

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingFilesAsStream {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUpScenario();
		}

		@Test
		public void shouldReturnNullIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						new HashMap<String, FileItem[]>(),
						new HashMap<String, List<String>>()), null,
					_portletNamespace);

			Assert.assertNull(
				uploadPortletRequest.getFilesAsStream("nevermind-name"));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Assert.assertNull(
				uploadPortletRequest.getFilesAsStream("not-existing-file"));
		}

		@Test
		public void shouldReturnStreamsFromFileParameters() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					10, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Map<String, FileItem[]> map =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertEquals(10, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				InputStream[] inputStreams =
					uploadPortletRequest.getFilesAsStream(key);

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

	}

	@RunWith(Arquillian.class)
	public static final class WhenGettingFullFileName {

		@ClassRule
		@Rule
		public static final TestRule aggregateTestRule =
			new LiferayIntegrationTestRule();

		@Before
		public void setUp() throws Exception {
			_setUpScenario();
		}

		@Test
		public void shouldReturnFirstFileNameFromFileParameters()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Map<String, FileItem[]> multipartParameterMap =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, multipartParameterMap.size());

			for (Map.Entry<String, FileItem[]> entry :
					multipartParameterMap.entrySet()) {

				String key = entry.getKey();

				String fullFileName = uploadPortletRequest.getFullFileName(key);

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
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						new HashMap<String, FileItem[]>(),
						new HashMap<String, List<String>>()), null,
					_portletNamespace);

			Assert.assertNull(
				uploadPortletRequest.getFullFileName("nevermind-name"));
		}

		@Test
		public void shouldReturnNullIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Assert.assertNull(
				uploadPortletRequest.getFullFileName("not-existing-file"));
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
			_setUpScenario();
		}

		/**
		 * Using parametrized constructor will cause the _liferayServletRequest
		 * to be null, so the getInputStream() method will invoke super class
		 * method, instead of delegating the invocation to Liferay's class.
		 *
		 * @see LiferayServletRequest
		 */
		@Test
		public void shouldNotReturnLiferayInputStream() throws Exception {
			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest()),
					null, _portletNamespace);

			ServletInputStream inputStream =
				uploadPortletRequest.getInputStream();

			Assert.assertFalse(inputStream instanceof LiferayInputStream);

			uploadPortletRequest = new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>()),
				null, _portletNamespace);

			inputStream = uploadPortletRequest.getInputStream();

			Assert.assertFalse(inputStream instanceof LiferayInputStream);
		}

		/**
		 * Using main constructor will cause invoking
		 * LiferayServletRequest.setFinishedReadingOriginalStream(true) method,
		 * which is the only way to set _finishedReadingOriginalStream to TRUE,
		 * so it will Return the cached input stream the second time the user
		 * requests the input stream, otherwise, it will return an empty input
		 * stream because it has already been parsed.
		 *
		 * @see LiferayServletRequest
		 */
		@Test
		public void shouldReturnServletInputStreamAdapter() throws Exception {
			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest()),
					null, _portletNamespace);

			ServletInputStream inputStream =
				uploadPortletRequest.getInputStream();

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
			_setUpScenario();
		}

		@Test
		public void shouldMergeFileAndRequestParameters() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					10, _portletNamespace, _bytes);

			Map<String, List<String>> regularParameters =
				PortletContainerTestUtil.getRegularParameters(10);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			String parameter = RandomTestUtil.randomString();

			MockHttpServletRequest mockHttpServletRequest =
				(MockHttpServletRequest)liferayServletRequest.getRequest();

			mockHttpServletRequest.addParameter(parameter, parameter);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, regularParameters), null,
					_portletNamespace);

			Enumeration<String> parameterNames =
				uploadPortletRequest.getParameterNames();

			List<String> parameterNamesList = Collections.list(parameterNames);

			for (Map.Entry<String, List<String>> entry :
					regularParameters.entrySet()) {

				Assert.assertTrue(parameterNamesList.contains(entry.getKey()));
			}

			for (Map.Entry<String, FileItem[]> entry :
					fileParameters.entrySet()) {

				Assert.assertFalse(parameterNamesList.contains(entry.getKey()));

				String fileParameter = entry.getKey();

				fileParameter = fileParameter.substring(
					_portletNamespace.length(), fileParameter.length());

				Assert.assertTrue(parameterNamesList.contains(fileParameter));
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
			_setUpScenario();
		}

		@Test
		public void shouldMergeRegularAndRequestParameters() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					10, _portletNamespace, _bytes);

			Map<String, List<String>> regularParameters =
				PortletContainerTestUtil.getRegularParameters(10);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			String parameter = RandomTestUtil.randomString();

			MockHttpServletRequest mockHttpServletRequest =
				(MockHttpServletRequest)liferayServletRequest.getRequest();

			mockHttpServletRequest.addParameter(parameter, parameter);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, regularParameters), null,
					_portletNamespace);

			for (Map.Entry<String, List<String>> entry :
					regularParameters.entrySet()) {

				String key = entry.getKey();

				String[] parameterValues =
					uploadPortletRequest.getParameterValues(key);

				List<String> parameterValuesList = ListUtil.fromArray(
					parameterValues);

				Assert.assertTrue(
					parameterValuesList.containsAll(entry.getValue()));
			}

			String[] requestParameterValues =
				uploadPortletRequest.getParameterValues(parameter);

			Assert.assertTrue(
				ArrayUtil.contains(requestParameterValues, parameter));

			for (Map.Entry<String, FileItem[]> entry :
					fileParameters.entrySet()) {

				String key = entry.getKey();

				String[] parameterValues =
					uploadPortletRequest.getParameterValues(key);

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
			_setUpScenario();
		}

		@Test
		public void shouldReturnFirstSizeFromFileParameters() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Map<String, FileItem[]> map =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				Long size = uploadPortletRequest.getSize(key);

				FileItem[] fileItems = entry.getValue();

				FileItem firstFileItem = fileItems[0];

				Assert.assertEquals(firstFileItem.getSize(), size.longValue());
			}
		}

		@Test
		public void shouldReturnZeroIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						new HashMap<String, FileItem[]>(),
						new HashMap<String, List<String>>()), null,
					_portletNamespace);

			Long size = uploadPortletRequest.getSize("nevermind-name");

			Assert.assertEquals(0, size.longValue());
		}

		@Test
		public void shouldReturnZeroIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Long size = uploadPortletRequest.getSize("not-existing-file");

			Assert.assertEquals(0, size.longValue());
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
			_setUpScenario();
		}

		@Test
		public void shouldReturnFirstSizeFromFileParameters() throws Exception {
			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Map<String, FileItem[]> map =
				uploadPortletRequest.getMultipartParameterMap();

			Assert.assertEquals(1, map.size());

			for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
				String key = entry.getKey();

				FileItem[] fileItems = entry.getValue();

				FileItem firstFileItem = fileItems[0];

				Assert.assertEquals(
					firstFileItem.isFormField(),
					uploadPortletRequest.isFormField(key));
			}
		}

		@Test
		public void shouldReturnTrueIfFileParametersAreEmpty()
			throws Exception {

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						new HashMap<String, FileItem[]>(),
						new HashMap<String, List<String>>()), null,
					_portletNamespace);

			Assert.assertTrue(
				uploadPortletRequest.isFormField("nevermind-name"));
		}

		@Test
		public void shouldReturnTrueIfNameIsNotAFileParameter()
			throws Exception {

			Map<String, FileItem[]> fileParameters =
				PortletContainerTestUtil.getFileParameters(
					1, _portletNamespace, _bytes);

			LiferayServletRequest liferayServletRequest =
				PortletContainerTestUtil.getMultipartRequest(
					_portletNamespace, _bytes);

			UploadPortletRequest uploadPortletRequest =
				new UploadPortletRequestImpl(
					new UploadServletRequestImpl(
						(HttpServletRequest)liferayServletRequest.getRequest(),
						fileParameters, new HashMap<String, List<String>>()),
					null, _portletNamespace);

			Assert.assertTrue(
				uploadPortletRequest.isFormField("not-existing-file"));
		}

	}

	private static void _setUpScenario() throws Exception {
		_portletNamespace = RandomTestUtil.randomString();
	}

	private static final byte[] _bytes =
		"Enterprise. Open Source. For Life.".getBytes();
	private static String _portletNamespace;

}