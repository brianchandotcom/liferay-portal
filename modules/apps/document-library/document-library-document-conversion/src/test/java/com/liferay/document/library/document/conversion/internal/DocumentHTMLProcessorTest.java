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

package com.liferay.document.library.document.conversion.internal;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.util.FastDateFormatFactoryUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.LiferayUnitTestRule;
import com.liferay.portal.util.FastDateFormatFactoryImpl;
import com.liferay.portal.util.FileImpl;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * @author Sergio González
 */
public class DocumentHTMLProcessorTest {

	@ClassRule
	@Rule
	public static final LiferayUnitTestRule liferayUnitTestRule =
		LiferayUnitTestRule.INSTANCE;

	@Before
	public void setUp() {
		FastDateFormatFactoryUtil fastDateFormatFactoryUtil =
			new FastDateFormatFactoryUtil();

		fastDateFormatFactoryUtil.setFastDateFormatFactory(
			new FastDateFormatFactoryImpl());

		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		Mockito.when(
			ImageRequestTokenUtil.createToken(Mockito.anyLong())
		).thenReturn(
			"authtoken"
		);
	}

	@After
	public void tearDown() {
		_imageRequestTokenUtilMockedStatic.close();
	}

	@Test
	public void testProcessDocumentURLWithThumbnailParameter()
		throws Exception {

		DocumentHTMLProcessor documentHTMLProcessor =
			new DocumentHTMLProcessor();

		String originalHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body><img src=\"",
			"/documents/29543/100903188/how-long/4e69-b2cc-e6ef21c10?t=1513212",
			"&imageThumbnail=1\"/></body></html>");

		InputStream originalInputStream = new ByteArrayInputStream(
			originalHTML.getBytes());

		InputStream processedInputStream = documentHTMLProcessor.process(
			originalInputStream);

		String processedHTML = IOUtils.toString(processedInputStream, "UTF-8");

		String expectedHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body><img src=\"",
			"/documents/29543/100903188/how-long/4e69-b2cc-e6ef21c10?t=1513212",
			"&imageThumbnail=1&auth_token=authtoken\"/></body></html>");

		Assert.assertEquals(expectedHTML, processedHTML);
	}

	@Test
	public void testProcessImageURLWithThumbnailParameter() throws Exception {
		DocumentHTMLProcessor documentHTMLProcessor =
			new DocumentHTMLProcessor();

		String originalHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body><img src=\"",
			"/image/image_gallery?uuid=f17b2a6b-70ee-4121-ae6e-61c22ff47",
			"&groupId=807138&t=12798459506&imageThumbnail=1\"/></body></html>");

		InputStream originalInputStream = new ByteArrayInputStream(
			originalHTML.getBytes());

		InputStream processedInputStream = documentHTMLProcessor.process(
			originalInputStream);

		String processedHTML = IOUtils.toString(processedInputStream, "UTF-8");

		String expectedHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body><img src=\"",
			"/image/image_gallery?uuid=f17b2a6b-70ee-4121-ae6e-61c22ff47",
			"&groupId=807138&t=12798459506&imageThumbnail=1",
			"&auth_token=authtoken\"/></body></html>");

		Assert.assertEquals(expectedHTML, processedHTML);
	}

	@Test
	public void testProcessImgTagWithAttributesAndSimpleDocumentURL()
		throws Exception {

		DocumentHTMLProcessor documentHTMLProcessor =
			new DocumentHTMLProcessor();

		String originalHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body>",
			"<img class=\"test\" src=\"/documents/29543/100903188/how-long",
			"/4e69-b2cc-e6ef21c10?t=1513212\"/></body></html>");

		InputStream originalInputStream = new ByteArrayInputStream(
			originalHTML.getBytes());

		InputStream processedInputStream = documentHTMLProcessor.process(
			originalInputStream);

		String processedHTML = IOUtils.toString(processedInputStream, "UTF-8");

		String expectedHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body>",
			"<img class=\"test\" src=\"/documents/29543/100903188/how-long",
			"/4e69-b2cc-e6ef21c10?t=1513212&auth_token=authtoken\"/></body>",
			"</html>");

		Assert.assertEquals(expectedHTML, processedHTML);
	}

	@Test
	public void testProcessImgTagWithAttributesAndSimpleImageURL()
		throws Exception {

		DocumentHTMLProcessor documentHTMLProcessor =
			new DocumentHTMLProcessor();

		String originalHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body>",
			"<img class=\"test\" src=\"/image",
			"/image_gallery?uuid=f17b2a6b-70ee-4121-ae6e-61c22ff47",
			"&groupId=807138&t=12798459506\"/></body></html>");

		InputStream originalInputStream = new ByteArrayInputStream(
			originalHTML.getBytes());

		InputStream processedInputStream = documentHTMLProcessor.process(
			originalInputStream);

		String processedHTML = IOUtils.toString(processedInputStream, "UTF-8");

		String expectedHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body>",
			"<img class=\"test\" src=\"/image",
			"/image_gallery?uuid=f17b2a6b-70ee-4121-ae6e-61c22ff47",
			"&groupId=807138&t=12798459506&auth_token=authtoken\"/>",
			"</body></html>");

		Assert.assertEquals(expectedHTML, processedHTML);
	}

	@Test
	public void testProcessImgTagWithAttributesAndSimplePortletFileEntryURL()
		throws Exception {

		DocumentHTMLProcessor documentHTMLProcessor =
			new DocumentHTMLProcessor();

		String originalHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body>",
			"<img class=\"test\" src=\"/documents/portlet_file_entry/10766",
			"/test-title/f17b2a6b-ae6e-61cf\"/></body></html>");

		InputStream originalInputStream = new ByteArrayInputStream(
			originalHTML.getBytes());

		InputStream processedInputStream = documentHTMLProcessor.process(
			originalInputStream);

		String processedHTML = IOUtils.toString(processedInputStream, "UTF-8");

		String expectedHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body>",
			"<img class=\"test\" src=\"/documents/portlet_file_entry/10766",
			"/test-title/f17b2a6b-ae6e-61cf?auth_token=authtoken\"/></body>",
			"</html>");

		Assert.assertEquals(expectedHTML, processedHTML);
	}

	@Test
	public void testProcessSimpleDocumentURL() throws Exception {
		DocumentHTMLProcessor documentHTMLProcessor =
			new DocumentHTMLProcessor();

		String originalHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body><img src=\"",
			"/documents/29543/100903188/how-long/4e69-b2cc-e6ef21c10?t=1513212",
			"\"/></body></html>");

		InputStream originalInputStream = new ByteArrayInputStream(
			originalHTML.getBytes());

		InputStream processedInputStream = documentHTMLProcessor.process(
			originalInputStream);

		String processedHTML = IOUtils.toString(processedInputStream, "UTF-8");

		String expectedHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body><img src=\"",
			"/documents/29543/100903188/how-long/4e69-b2cc-e6ef21c10?t=1513212",
			"&auth_token=authtoken\"/></body></html>");

		Assert.assertEquals(expectedHTML, processedHTML);
	}

	@Test
	public void testProcessSimpleImageURL() throws Exception {
		DocumentHTMLProcessor documentHTMLProcessor =
			new DocumentHTMLProcessor();

		String originalHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body><img src=\"",
			"/image/image_gallery?uuid=f17b2a6b-70ee-4121-ae6e-61c22ff47",
			"&groupId=807138&t=12798459506\"/></body></html>");

		InputStream originalInputStream = new ByteArrayInputStream(
			originalHTML.getBytes());

		InputStream processedInputStream = documentHTMLProcessor.process(
			originalInputStream);

		String processedHTML = IOUtils.toString(processedInputStream, "UTF-8");

		String expectedHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body><img src=\"",
			"/image/image_gallery?uuid=f17b2a6b-70ee-4121-ae6e-61c22ff47",
			"&groupId=807138&t=12798459506&auth_token=authtoken\"/>",
			"</body></html>");

		Assert.assertEquals(expectedHTML, processedHTML);
	}

	@Test
	public void testProcessSimplePortletFileEntryURL() throws Exception {
		DocumentHTMLProcessor documentHTMLProcessor =
			new DocumentHTMLProcessor();

		String originalHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body><img src=\"",
			"/documents/portlet_file_entry/10766/test-title/f17b2a6b-ae6e-61cf",
			"\"/></body></html>");

		InputStream originalInputStream = new ByteArrayInputStream(
			originalHTML.getBytes());

		InputStream processedInputStream = documentHTMLProcessor.process(
			originalInputStream);

		String processedHTML = IOUtils.toString(processedInputStream, "UTF-8");

		String expectedHTML = StringBundler.concat(
			"<html><head><title>test-title</title></head><body><img src=\"",
			"/documents/portlet_file_entry/10766/test-title/f17b2a6b-ae6e-61cf",
			"?auth_token=authtoken\"/></body></html>");

		Assert.assertEquals(expectedHTML, processedHTML);
	}

	private final MockedStatic<ImageRequestTokenUtil>
		_imageRequestTokenUtilMockedStatic = Mockito.mockStatic(
			ImageRequestTokenUtil.class);

}