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
import com.liferay.portal.kernel.util.FileUtil;
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
import org.junit.rules.TemporaryFolder;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Manuel de la Peña
 */
@RunWith(Arquillian.class)
public class UploadServletRequestTest {

	@ClassRule
	@Rule
	public static final TestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_fileNameParameter = RandomTestUtil.randomString();

		InputStream inputStream = getClass().getResourceAsStream(
			_TXT_DEPENDENCY);

		_bytes = FileUtil.getBytes(inputStream);
	}

	@Test
	public void testBuildWithEmptyParametersShouldNotPopulateParameters()
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
		Assert.assertEquals(0, multipartParameterMap.size());

		Map<String, List<String>> regularParameterMap =
			uploadServletRequest.getRegularParameterMap();

		Assert.assertNotNull(regularParameterMap);
		Assert.assertEquals(0, regularParameterMap.size());
	}

	@Test
	public void testBuildWithFileParametersShouldPopulateMultipartParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

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
		Assert.assertEquals(1, multipartParameterMap.size());

		Map<String, List<String>> regularParameterMap =
			uploadServletRequest.getRegularParameterMap();

		Assert.assertNotNull(regularParameterMap);
		Assert.assertEquals(0, regularParameterMap.size());
	}

	@Test
	public void testBuildWithRegularParametersShouldPopulateRegularParameters()
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
		Assert.assertEquals(0, multipartParameterMap.size());

		Map<String, List<String>> regularParameterMap =
			uploadServletRequest.getRegularParameterMap();

		Assert.assertNotNull(regularParameterMap);
		Assert.assertEquals(10, regularParameterMap.size());
	}

	@Test
	public void testCleanUpShouldNotFailFromMainConstructor() throws Exception {
		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest());

		uploadServletRequest.cleanUp();

		Map<String, FileItem[]> multipartParameterMap =
			uploadServletRequest.getMultipartParameterMap();

		Assert.assertNotNull(multipartParameterMap);
		Assert.assertEquals(0, multipartParameterMap.size());
	}

	@Test
	public void testCleanUpShouldNotRemoveMultipartParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				fileParameters, new HashMap<String, List<String>>());

		try {
			uploadServletRequest.cleanUp();
		}
		catch (NullPointerException npe) {

			// the _liferayServletRequest is null!

		}

		Map<String, FileItem[]> multipartParameterMap =
			uploadServletRequest.getMultipartParameterMap();

		Assert.assertNotNull(multipartParameterMap);
		Assert.assertEquals(1, multipartParameterMap.size());
	}

	@Test(expected = NullPointerException.class)
	public void
			testCleanUpShouldThrowNullPointerExceptionIfUsedAfterConstructor()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>());

		uploadServletRequest.cleanUp();
	}

	@Test
	public void testGetContentTypeShouldReturnFirstFileItemContentType()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

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
	public void testGetContentTypeShouldReturnNullIfFileParametersAreEmpty()
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

	@Test
	public void testGetFileAsStreamShouldReturnAStream() throws Exception {
		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

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

			InputStream inputStream = uploadServletRequest.getFileAsStream(key);

			Assert.assertNotNull(inputStream);

			inputStream = uploadServletRequest.getFileAsStream(key, true);

			Assert.assertNotNull(inputStream);
		}
	}

	@Test
	public void testGetFileAsStreamShouldReturnNullIfFileParametersAreEmpty()
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
			uploadServletRequest.getFileAsStream("never-mind-name"));
		Assert.assertNull(
			uploadServletRequest.getFileAsStream("never-mind-name", true));
	}

	@Test
	public void testGetFileAsStreamShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				fileParameters, new HashMap<String, List<String>>());

		Assert.assertNull(
			uploadServletRequest.getFileAsStream("not-existing-file"));
		Assert.assertNull(
			uploadServletRequest.getFileAsStream("not-existing-file", true));
	}

	@Test
	public void testGetFileNameShouldReturnFirstFileNameFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

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
	public void testGetFileNameShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>());

		Assert.assertNull(uploadServletRequest.getFileName("never-mind-name"));
	}

	@Test
	public void testGetFileNameShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				fileParameters, new HashMap<String, List<String>>());

		Assert.assertNull(
			uploadServletRequest.getFileName("not-existing-file"));
	}

	@Test
	public void
			testGetFileNamesShouldReturnAnArrayWithFileNamesFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				10, getClass(), _TXT_DEPENDENCY);

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
				Assert.assertEquals(fileItems[i].getFileName(), fileNames[i]);
			}
		}
	}

	@Test
	public void testGetFileNamesShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>());

		Assert.assertNull(uploadServletRequest.getFileNames("never-mind-name"));
	}

	@Test
	public void testGetFileNamesShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				fileParameters, new HashMap<String, List<String>>());

		Assert.assertNull(
			uploadServletRequest.getFileNames("not-existing-file"));
	}

	@Test
	public void
			testGetFilesAsStreamShouldReturnArrayWithStreamsFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				10, getClass(), _TXT_DEPENDENCY);

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

			InputStream[] inputStreams = uploadServletRequest.getFilesAsStream(
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
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>());

		Assert.assertNull(
			uploadServletRequest.getFilesAsStream("never-mind-name"));
	}

	@Test
	public void testGetFilesAsStreamShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				fileParameters, new HashMap<String, List<String>>());

		Assert.assertNull(
			uploadServletRequest.getFilesAsStream("not-existing-file"));
	}

	@Test
	public void testGetFileShouldReturnAFile() throws Exception {
		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

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
	public void testGetFileShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>());

		Assert.assertNull(uploadServletRequest.getFile("never-mind-name"));
		Assert.assertNull(
			uploadServletRequest.getFile("never-mind-name", true));
	}

	@Test
	public void testGetFileShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				fileParameters, new HashMap<String, List<String>>());

		Assert.assertNull(uploadServletRequest.getFile("not-existing-file"));
		Assert.assertNull(
			uploadServletRequest.getFile("not-existing-file", true));
	}

	@Test
	public void
			testGetFilesShouldReturnArrayWithStoreLocationsFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				10, getClass(), _TXT_DEPENDENCY);

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
	public void testGetFilesShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>());

		Assert.assertNull(uploadServletRequest.getFiles("never-mind-name"));
	}

	@Test
	public void testGetFilesShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				fileParameters, new HashMap<String, List<String>>());

		Assert.assertNull(uploadServletRequest.getFiles("not-existing-file"));
	}

	@Test
	public void testGetFullFileNameShouldReturnFirstFileNameFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

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

			Assert.assertEquals(firstFileItem.getFullFileName(), fullFileName);
		}
	}

	@Test
	public void testGetFullFileNameShouldReturnNullIfFileParametersAreEmpty()
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
			uploadServletRequest.getFullFileName("never-mind-name"));
	}

	@Test
	public void testGetFullFileNameShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				fileParameters, new HashMap<String, List<String>>());

		Assert.assertNull(
			uploadServletRequest.getFullFileName("not-existing-file"));
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
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest());

		ServletInputStream inputStream = uploadServletRequest.getInputStream();

		Assert.assertFalse(inputStream instanceof LiferayInputStream);

		uploadServletRequest = new UploadServletRequestImpl(
			(HttpServletRequest)liferayServletRequest.getRequest(),
			new HashMap<String, FileItem[]>(),
			new HashMap<String, List<String>>());

		inputStream = uploadServletRequest.getInputStream();

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
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest());

		ServletInputStream inputStream = uploadServletRequest.getInputStream();

		Assert.assertTrue(inputStream instanceof ServletInputStreamAdapter);
	}

	@Test
	public void
			testGetParameterNamesShouldMergeRegular_File_And_RequestParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				10, getClass(), _TXT_DEPENDENCY);

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

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				10, getClass(), _TXT_DEPENDENCY);

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

		// regular parameters

		for (Map.Entry<String, List<String>> entry :
				regularParameters.entrySet()) {

			String key = entry.getKey();

			String[] parameterValues = uploadServletRequest.getParameterValues(
				key);

			List<String> parameterValuesList = ListUtil.fromArray(
				parameterValues);

			Assert.assertTrue(
				parameterValuesList.containsAll(entry.getValue()));
		}

		// request parameters

		String[] requestParameterValues =
			uploadServletRequest.getParameterValues(parameter);

		ArrayUtil.contains(requestParameterValues, parameter);

		// file parameters

		for (Map.Entry<String, FileItem[]> entry : fileParameters.entrySet()) {
			String key = entry.getKey();

			String[] parameterValues = uploadServletRequest.getParameterValues(
				key);

			List<String> parameterValuesList = ListUtil.fromArray(
				parameterValues);

			Assert.assertFalse(parameterValuesList.contains(key));
		}
	}

	@Test
	public void testGetSizeShouldReturnFirstSizeFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

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
	public void testGetSizeShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>());

		Assert.assertNull(uploadServletRequest.getSize("never-mind-name"));
	}

	@Test
	public void testGetSizeShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				fileParameters, new HashMap<String, List<String>>());

		Assert.assertNull(uploadServletRequest.getSize("not-existing-file"));
	}

	@Test
	public void testGetTempDirShouldNotReturnPreferencesValueWhenModified()
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
	public void testGetTempDirShouldReturnPreferencesValue() {
		File tempDir = UploadServletRequestImpl.getTempDir();

		File expectedTempDir = new File(
			PrefsPropsUtil.getString(
				PropsKeys.UPLOAD_SERVLET_REQUEST_IMPL_TEMP_DIR,
				SystemProperties.get(SystemProperties.TMP_DIR)));

		Assert.assertEquals(expectedTempDir, tempDir);
	}

	@Test
	public void testIsFormFieldShouldReturnFirstSizeFromFileParameters()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

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
	public void testIsFormFieldShouldReturnNullIfFileParametersAreEmpty()
		throws Exception {

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				new HashMap<String, FileItem[]>(),
				new HashMap<String, List<String>>());

		Assert.assertNull(uploadServletRequest.isFormField("never-mind-name"));
	}

	@Test
	public void testIsFormFieldShouldReturnNullIfNameIsNotAFileParameter()
		throws Exception {

		Map<String, FileItem[]> fileParameters =
			PortletContainerTestUtil.getFileParameters(
				1, getClass(), _TXT_DEPENDENCY);

		LiferayServletRequest liferayServletRequest =
			PortletContainerTestUtil.getMultipartRequest(
				_fileNameParameter, _bytes);

		UploadServletRequestImpl uploadServletRequest =
			new UploadServletRequestImpl(
				(HttpServletRequest)liferayServletRequest.getRequest(),
				fileParameters, new HashMap<String, List<String>>());

		Assert.assertNull(
			uploadServletRequest.isFormField("not-existing-file"));
	}

	@Test
	public void testMainConstructorShouldAddProgressTrackerToSession()
		throws Exception {

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
	public void testMainConstructorShouldNotPopulateParameters()
		throws Exception {

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
		Assert.assertEquals(0, multipartParameterMap.size());

		Map<String, List<String>> regularParameterMap =
			uploadServletRequest.getRegularParameterMap();

		Assert.assertNotNull(regularParameterMap);
		Assert.assertEquals(0, regularParameterMap.size());
	}

	private static final String _TXT_DEPENDENCY =
		"/com/liferay/portal/portlet/container/upload/test/dependencies/" +
			"file_upload.txt";

	private byte[] _bytes;
	private String _fileNameParameter;

}