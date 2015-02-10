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

package com.liferay.portal.kernel.servlet;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.Props;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.modules.junit4.PowerMockRunner;

import org.springframework.mock.web.MockHttpServletResponse;

/**
 * @author Tomas Polesovsky
 */
@RunWith(PowerMockRunner.class)
public class ServletResponseUtilRangeTest extends PowerMockito {

	@Before
	public void setUp() throws IOException {
		MockitoAnnotations.initMocks(this);

		setupFileUtil();
		setupPropsUtil();
	}

	@Test
	public void testGetMultipleRanges() throws IOException {
		long length = 1000;
		String rangeHeader = "bytes=1-3,3-8,9-11,12-12,30-";

		setupRange(request, rangeHeader);

		List<Range> ranges = ServletResponseUtil.getRanges(
			request, response, length);

		Assert.assertEquals(ranges.size(), 5);
		assertRange(ranges.get(0), 1, 3, 3);
		assertRange(ranges.get(1), 3, 8, 6);
		assertRange(ranges.get(2), 9, 11, 3);
		assertRange(ranges.get(3), 12, 12, 1);
		assertRange(ranges.get(4), 30, 999, 970);
	}

	@Test
	public void testGetRangesPerSpec() throws IOException {

		// https://tools.ietf.org/html/rfc7233#section-2.1
		// Additional examples, assuming a representation of length 10000:

		long length = 10000;

		// The final 500 bytes (byte offsets 9500-9999, inclusive):
		// 		bytes=-500
		// Or:
		// 		bytes=9500-

		String rangeHeader = "bytes=-500";
		setupRange(request, rangeHeader);

		List<Range> ranges = ServletResponseUtil.getRanges(
			request, response, length);

		Assert.assertEquals(ranges.size(), 1);
		assertRange(ranges.get(0), 9500, 9999, 500);

		rangeHeader = "bytes=9500-";
		setupRange(request, rangeHeader);

		ranges = ServletResponseUtil.getRanges(request, response, length);

		Assert.assertEquals(ranges.size(), 1);
		assertRange(ranges.get(0), 9500, 9999, 500);

		// The first and last bytes only (bytes 0 and 9999):
		// 		bytes=0-0,-1

		rangeHeader = "bytes=0-0,-1";
		setupRange(request, rangeHeader);

		ranges = ServletResponseUtil.getRanges(request, response, length);

		Assert.assertEquals(ranges.size(), 2);
		assertRange(ranges.get(0), 0, 0, 1);
		assertRange(ranges.get(1), 9999, 9999, 1);

		// Other valid (but not canonical) specifications of the second 500
		// bytes (byte offsets 500-999, inclusive):
		// 		bytes=500-600,601-999
		// 		bytes=500-700,601-999

		rangeHeader = "bytes=500-600,601-999";
		setupRange(request, rangeHeader);

		ranges = ServletResponseUtil.getRanges(request, response, length);

		Assert.assertEquals(ranges.size(), 2);
		assertRange(ranges.get(0), 500, 600, 101);
		assertRange(ranges.get(1), 601, 999, 399);

		rangeHeader = "bytes=500-700,601-999";
		setupRange(request, rangeHeader);

		ranges = ServletResponseUtil.getRanges(request, response, length);

		Assert.assertEquals(ranges.size(), 2);
		assertRange(ranges.get(0), 500, 700, 201);
		assertRange(ranges.get(1), 601, 999, 399);
	}

	@Test
	public void testGetRangesSimple() throws IOException {
		long length = 1000;
		String rangeHeader = "bytes=0-999";

		setupRange(request, rangeHeader);

		List<Range> ranges = ServletResponseUtil.getRanges(
			request, response, length);

		Assert.assertEquals(ranges.size(), 1);
		assertRange(ranges.get(0), 0, 999, 1000);
	}

	@Test
	public void testWriteWithRanges() throws IOException {
		byte[] content = new byte[1000];
		Arrays.fill(content, (byte) 48);

		// ByteArrayInputStream

		testWriteWith(new ByteArrayInputStream(content), content);

		// FileInputStream

		File tempFile = FileUtil.createTempFile();

		try {
			try(FileOutputStream fos = new FileOutputStream(tempFile)) {
				fos.write(content);
			}

			testWriteWith(new FileInputStream(tempFile), content);
		}
		finally {
			tempFile.delete();
		}

		// Other InputStream

		testWriteWith(
			new BufferedInputStream(new ByteArrayInputStream(content)),
			content);
	}

	protected void assertRange(Range range, long start, long end, long length) {
		Assert.assertEquals(range.getStart(), start);
		Assert.assertEquals(range.getEnd(), end);
		Assert.assertEquals(range.getLength(), length);
	}

	protected void setupFileUtil() throws IOException {
		new FileUtil().setFile(file);

		when(
			file.createTempFile()
		).thenAnswer(
			new Answer<File>() {
				@Override
				public File answer(InvocationOnMock invocation)
					throws Throwable {

					String name = String.valueOf(System.currentTimeMillis());

					return File.createTempFile(name, null);
				}
			}
		);

		when(
			file.delete(Matchers.any(File.class))
		).thenAnswer(
			new Answer<Boolean>() {
				@Override
				public Boolean answer(InvocationOnMock invocation)
					throws Throwable {

					Object[] args = invocation.getArguments();
					File file = (File)args[0];

					return file.delete();
				}
			}
		);
	}

	protected void setupPropsUtil() {
		PropsUtil.setProps(props);

		when(
			props.get(PropsKeys.WEB_SERVER_SERVLET_MAX_RANGE_FIELDS)
		).thenReturn(
			"10"
		);
	}

	protected void setupRange(HttpServletRequest request, String rangeHeader) {
		when(
			request.getHeader(HttpHeaders.RANGE)
		).thenReturn(
			rangeHeader
		);
	}

	protected void testWriteWith(InputStream inputStream, byte[] content)
		throws IOException {

		String rangeHeader = "bytes=0-9,980-989,980-999,990-999";

		setupRange(request, rangeHeader);

		List<Range> ranges = ServletResponseUtil.getRanges(
			request, response, content.length);

		MockHttpServletResponse mockHttpServletResponse =
			new MockHttpServletResponse();

		mockHttpServletResponse.setCharacterEncoding(StringPool.UTF8);

		ServletResponseUtil.write(
			request, mockHttpServletResponse, "filename.txt", ranges,
			inputStream, content.length, "text/plain");

		String contentType = mockHttpServletResponse.getContentType();

		Assert.assertTrue(contentType.startsWith(_BOUNDARY_PREFACE));

		String boundary = contentType.substring(_BOUNDARY_PREFACE.length());

		String responseBody = mockHttpServletResponse.getContentAsString();

		Assert.assertTrue(
			responseBody.startsWith("\r\n--" + boundary + "\r\n"));
		Assert.assertTrue(responseBody.endsWith("--" + boundary + "--\r\n"));

		String[] responseBodies = StringUtil.split(responseBody, boundary);

		Assert.assertEquals(ranges.size() + 2, responseBodies.length);
		Assert.assertEquals(StringPool.DOUBLE_DASH, responseBodies[0]);
		Assert.assertEquals(
			StringPool.DOUBLE_DASH, responseBodies[ranges.size() + 1]);

		for (int i = 0; i < ranges.size(); i++) {
			Range range = ranges.get(i);

			String[] lines = StringUtil.split(
				responseBodies[i + 1], StringPool.RETURN_NEW_LINE);

			Assert.assertEquals("Content-Type: text/plain", lines[0]);
			Assert.assertEquals(
				"Content-Range: " + range.getContentRange(), lines[1]);
			Assert.assertTrue(Validator.isNull(lines[2]));

			int start = (int)range.getStart();
			int end = (int)range.getEnd();

			byte[] bytes = ArrayUtil.subset(content, start, end + 1);

			Assert.assertArrayEquals(bytes, lines[3].getBytes("UTF-8"));
			Assert.assertEquals(StringPool.DOUBLE_DASH, lines[4]);
		}
	}

	@Mock
	protected com.liferay.portal.kernel.util.File file;

	@Mock
	protected Props props;

	@Mock
	protected HttpServletRequest request;

	@Mock
	protected HttpServletResponse response;

	private static final String _BOUNDARY_PREFACE =
		"multipart/byteranges; boundary=";

}