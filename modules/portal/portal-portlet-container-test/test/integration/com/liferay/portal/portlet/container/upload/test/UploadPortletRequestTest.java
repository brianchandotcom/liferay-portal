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
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class UploadPortletRequestTest {

	@ClassRule
	@Rule
	public static final TestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() {
		_portletNamespace = RandomTestUtil.randomString();
	}

	@Test
	public void testBuildWithEmptyParametersShouldNotPopulateParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
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

	@Test
	public void testBuildWithFileParametersShouldPopulateMultipartParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

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
	public void testBuildWithRegularParametersShouldPopulateRegularParameters()
		throws Exception {

		Map<String, List<String>> regularParameters = new HashMap<>();

		for (int i = 0; i < 10; i++) {
			PortletContainerTestUtil.putRegularParameter(regularParameters);
		}

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(), regularParameters), null,
				_portletNamespace);

		Map<String, FileItem[]> multipartParameterMap =
			uploadPortletRequest.getMultipartParameterMap();

		Assert.assertNotNull(multipartParameterMap);
		Assert.assertEquals(0, multipartParameterMap.size());

		Map<String, List<String>> regularParameterMap =
			uploadPortletRequest.getRegularParameterMap();

		Assert.assertNotNull(regularParameterMap);
		Assert.assertEquals(10, regularParameterMap.size());
	}

	@Test
	public void testCleanUpShouldNotFailFromMainConstructor() throws Exception {
		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest()),
				null, _portletNamespace);

		uploadPortletRequest.cleanUp();

		Map<String, FileItem[]> multipartParameterMap =
			uploadPortletRequest.getMultipartParameterMap();

		Assert.assertNotNull(multipartParameterMap);
		Assert.assertEquals(0, multipartParameterMap.size());
	}

	@Test
	public void testCleanUpShouldNotRemoveMultipartParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		try {
			uploadPortletRequest.cleanUp();
		}
		catch (NullPointerException npe) {

			// the _liferayServletRequest is null!

		}

		Map<String, FileItem[]> multipartParameterMap =
			uploadPortletRequest.getMultipartParameterMap();

		Assert.assertNotNull(multipartParameterMap);
		Assert.assertEquals(1, multipartParameterMap.size());
	}

	@Test(expected = NullPointerException.class)
	public void
	testCleanUpShouldThrowNullPointerExceptionIfUsedAfterConstructor()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>()), null,
				_portletNamespace);

		uploadPortletRequest.cleanUp();
	}

	@Test
	public void testGetContentTypeShouldReturnFirstFileItemContentType()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Map<String, FileItem[]> map =
			uploadPortletRequest.getMultipartParameterMap();

		Assert.assertEquals(1, map.size());

		for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
			String key = entry.getKey();

			FileItem[] fileItems = entry.getValue();

			FileItem firstFileItem = fileItems[0];

			Assert.assertEquals(
				firstFileItem.getContentType(),
				uploadPortletRequest.getContentType(key));
		}
	}

	@Test
	public void testGetContentTypeShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(uploadPortletRequest.getContentType("name"));
	}

	@Test
	public void testGetFileAsStreamShouldReturnAStream() throws Exception {
		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Map<String, FileItem[]> map =
			uploadPortletRequest.getMultipartParameterMap();

		Assert.assertEquals(1, map.size());

		for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
			String key = entry.getKey();

			InputStream inputStream = uploadPortletRequest.getFileAsStream(key);

			Assert.assertNotNull(inputStream);

			inputStream = uploadPortletRequest.getFileAsStream(key, true);

			Assert.assertNotNull(inputStream);
		}
	}

	@Test
	public void testGetFileAsStreamShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(
			uploadPortletRequest.getFileAsStream("never-mind-name"));
		Assert.assertNull(
			uploadPortletRequest.getFileAsStream("never-mind-name", true));
	}

	@Test
	public void testGetFileAsStreamShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(
			uploadPortletRequest.getFileAsStream("not-existing-file"));
		Assert.assertNull(
			uploadPortletRequest.getFileAsStream("not-existing-file", true));
	}

	@Test
	public void testGetFileNameShouldReturnFirstFileNameFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

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
	public void testGetFileNameShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(uploadPortletRequest.getFileName("never-mind-name"));
	}

	@Test
	public void testGetFileNameShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(
			uploadPortletRequest.getFileName("not-existing-file"));
	}

	@Test
	public void
	testGetFileNamesShouldReturnAnArrayWithFileNamesFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		for (int i = 0; i < 10; i++) {
			PortletContainerTestUtil.putFileParameter(
				getClass(), _TXT_DEPENDENCY, fileParameters);
		}

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Map<String, FileItem[]> map =
			uploadPortletRequest.getMultipartParameterMap();

		Assert.assertEquals(10, map.size());

		for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
			String key = entry.getKey();

			String[] fileNames = uploadPortletRequest.getFileNames(key);

			FileItem[] fileItems = entry.getValue();

			Assert.assertEquals(fileItems.length, fileNames.length);
			Assert.assertEquals(2, fileNames.length);

			for (int i = 0; i < fileNames.length; i++) {
				Assert.assertEquals(fileItems[i].getFileName(), fileNames[i]);
			}
		}
	}

	@Test
	public void testGetFileNamesShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(uploadPortletRequest.getFileNames("never-mind-name"));
	}

	@Test
	public void testGetFileNamesShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(
			uploadPortletRequest.getFileNames("not-existing-file"));
	}

	@Test
	public void
	testGetFilesAsStreamShouldReturnArrayWithStreamsFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		for (int i = 0; i < 10; i++) {
			PortletContainerTestUtil.putFileParameter(
				getClass(), _TXT_DEPENDENCY, fileParameters);
		}

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Map<String, FileItem[]> map =
			uploadPortletRequest.getMultipartParameterMap();

		Assert.assertEquals(10, map.size());

		for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
			String key = entry.getKey();

			InputStream[] inputStreams = uploadPortletRequest.getFilesAsStream(
				key);

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
	public void testGetFilesAsStreamShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(
			uploadPortletRequest.getFilesAsStream("never-mind-name"));
	}

	@Test
	public void testGetFilesAsStreamShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(
			uploadPortletRequest.getFilesAsStream("not-existing-file"));
	}

	@Test
	public void testGetFileShouldReturnAFile() throws Exception {
		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

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
	public void testGetFileShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(uploadPortletRequest.getFile("never-mind-name"));
		Assert.assertNull(
			uploadPortletRequest.getFile("never-mind-name", true));
	}

	@Test
	public void testGetFileShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(uploadPortletRequest.getFile("not-existing-file"));
		Assert.assertNull(
			uploadPortletRequest.getFile("not-existing-file", true));
	}

	@Test
	public void
	testGetFilesShouldReturnArrayWithStoreLocationsFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		for (int i = 0; i < 10; i++) {
			PortletContainerTestUtil.putFileParameter(
				getClass(), _TXT_DEPENDENCY, fileParameters);
		}

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Map<String, FileItem[]> map =
			uploadPortletRequest.getMultipartParameterMap();

		Assert.assertEquals(10, map.size());

		for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
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

	@Test
	public void testGetFilesShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(uploadPortletRequest.getFiles("never-mind-name"));
	}

	@Test
	public void testGetFilesShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(uploadPortletRequest.getFiles("not-existing-file"));
	}

	@Test
	public void testGetFullFileNameShouldReturnFirstFileNameFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Map<String, FileItem[]> map =
			uploadPortletRequest.getMultipartParameterMap();

		Assert.assertEquals(1, map.size());

		for (Map.Entry<String, FileItem[]> entry : map.entrySet()) {
			String key = entry.getKey();

			String fullFileName = uploadPortletRequest.getFullFileName(key);

			FileItem[] fileItems = entry.getValue();

			FileItem firstFileItem = fileItems[0];

			Assert.assertEquals(firstFileItem.getFullFileName(), fullFileName);
		}
	}

	@Test
	public void testGetFullFileNameShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(
			uploadPortletRequest.getFullFileName("never-mind-name"));
	}

	@Test
	public void testGetFullFileNameShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertNull(
			uploadPortletRequest.getFullFileName("not-existing-file"));
	}

	/**
	 * Using parametrized constructor will cause the _liferayServletRequest to
	 * be null, so the getInputStream() method will invoke super class method,
	 * instead of delegating the invocation to Liferay's class.
	 *
	 * @see LiferayServletRequest
	 */
	@Test
	public void testGetInputStreamCannotReturnLiferayInputStream()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest()),
				null, _portletNamespace);

		ServletInputStream inputStream = uploadPortletRequest.getInputStream();

		Assert.assertFalse(inputStream instanceof LiferayInputStream);

		uploadPortletRequest = new UploadPortletRequestImpl(
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>()), null, _portletNamespace);

		inputStream = uploadPortletRequest.getInputStream();

		Assert.assertFalse(inputStream instanceof LiferayInputStream);
	}

	/**
	 * Using main constructor will cause invoking
	 * LiferayServletRequest.setFinishedReadingOriginalStream(true) method,
	 * which is the only way to set _finishedReadingOriginalStream to TRUE, so
	 * it will Return the cached input stream the second time the user requests
	 * the input stream, otherwise, it will return an empty input stream because
	 * it has already been parsed.
	 *
	 * @see LiferayServletRequest
	 */
	@Test
	public void testGetInputStreamShouldReturnServletInputStreamAdapter()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest()),
				null, _portletNamespace);

		ServletInputStream inputStream = uploadPortletRequest.getInputStream();

		Assert.assertTrue(inputStream instanceof ServletInputStreamAdapter);
	}

	@Test
	public void
	testGetParameterNamesShouldMergeRegular_File_And_RequestParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();
		Map<String, List<String>> regularParameters = new HashMap<>();

		for (int i = 0; i < 10; i++) {
			PortletContainerTestUtil.putFileParameter(
				getClass(), _TXT_DEPENDENCY, fileParameters);
			PortletContainerTestUtil.putRegularParameter(regularParameters);
		}

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

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

		// regular parameters

		for (Map.Entry<String, List<String>> entry :
			regularParameters.entrySet()) {

			Assert.assertTrue(parameterNamesList.contains(entry.getKey()));
		}

		// file parameters

		for (Map.Entry<String, FileItem[]> entry : fileParameters.entrySet()) {
			Assert.assertTrue(parameterNamesList.contains(entry.getKey()));
		}

		// request parameters

		parameterNamesList.contains(parameter);
	}

	@Test
	public void testGetParameterValuesShouldMergeRegular_And_RequestParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();
		Map<String, List<String>> regularParameters = new HashMap<>();

		for (int i = 0; i < 10; i++) {
			PortletContainerTestUtil.putFileParameter(
				getClass(), _TXT_DEPENDENCY, fileParameters);
			PortletContainerTestUtil.putRegularParameter(regularParameters);
		}

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

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

		// regular parameters

		for (Map.Entry<String, List<String>> entry :
			regularParameters.entrySet()) {

			String key = entry.getKey();

			String[] parameterValues = uploadPortletRequest.getParameterValues(
				key);

			List<String> parameterValuesList = ListUtil.fromArray(
				parameterValues);

			Assert.assertTrue(
				parameterValuesList.containsAll(entry.getValue()));
		}

		// request parameters

		String[] requestParameterValues =
			uploadPortletRequest.getParameterValues(parameter);

		ArrayUtil.contains(requestParameterValues, parameter);

		// file parameters

		for (Map.Entry<String, FileItem[]> entry : fileParameters.entrySet()) {
			String key = entry.getKey();

			String[] parameterValues = uploadPortletRequest.getParameterValues(
				key);

			List<String> parameterValuesList = ListUtil.fromArray(
				parameterValues);

			Assert.assertFalse(parameterValuesList.contains(key));
		}
	}

	@Test
	public void testGetSizeShouldReturnFirstSizeFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

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
	public void testGetSizeShouldReturnZeroIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Long size = uploadPortletRequest.getSize("never-mind-name");

		Assert.assertEquals(0, size.longValue());
	}

	@Test
	public void testGetSizeShouldReturnZeroIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Long size = uploadPortletRequest.getSize("not-existing-file");

		Assert.assertEquals(0, size.longValue());
	}

	@Test
	public void testIsFormFieldShouldReturnFirstSizeFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

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
	public void testIsFormFieldShouldReturnTrueIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					new HashMap<String, FileItem[]>(),
					new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertTrue(uploadPortletRequest.isFormField("never-mind-name"));
	}

	@Test
	public void testIsFormFieldShouldReturnTrueIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters = new HashMap<>();

		PortletContainerTestUtil.putFileParameter(
			getClass(), _TXT_DEPENDENCY, fileParameters);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		UploadPortletRequest uploadPortletRequest =
			new UploadPortletRequestImpl(
				new UploadServletRequestImpl(
					(HttpServletRequest)liferayServletRequest.getRequest(),
					fileParameters, new HashMap<String, List<String>>()), null,
				_portletNamespace);

		Assert.assertTrue(
			uploadPortletRequest.isFormField("not-existing-file"));
	}

	@Test
	public void testMainConstructorShouldAddProgressTrackerToSession()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

		HttpServletRequest mockHttpServletRequest =
			(HttpServletRequest)liferayServletRequest.getRequest();

		new UploadPortletRequestImpl(
			new UploadServletRequestImpl(mockHttpServletRequest), null,
			_portletNamespace);

		HttpSession mockHttpSession = mockHttpServletRequest.getSession();

		Assert.assertNotNull(
			mockHttpSession.getAttribute(ProgressTracker.PERCENT));
	}

	@Test
	public void testMainConstructorShouldNotPopulateParameters()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.mockLiferayServletRequest(
				getClass(), _TXT_DEPENDENCY);

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

	private static final String _TXT_DEPENDENCY =
		"/com/liferay/portal/portlet/container/upload/test/dependencies/" +
			"file_upload.txt";

	private String _portletNamespace;

}