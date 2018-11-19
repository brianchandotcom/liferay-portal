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

package com.liferay.portal.upload;

import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.util.File;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.servlet.filters.uploadservletrequest.UploadServletRequestFilter;

import java.io.InputStream;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import org.springframework.mock.web.MockHttpServletRequest;

/**
 * @author Preston Crary
 */
public class LiferayInputStreamTest {

	@Before
	public void setUp() throws Exception {
		Field field = ReflectionUtil.getDeclaredField(FileUtil.class, "_file");

		field.set(
			null,
			ProxyUtil.newProxyInstance(
				ClassLoader.getSystemClassLoader(), new Class<?>[] {File.class},
				new InvocationHandler() {

					@Override
					public Object invoke(
							Object proxy, Method method, Object[] args)
						throws Throwable {

						if (method.equals(
								File.class.getMethod("createTempFile"))) {

							_file = java.io.File.createTempFile(
								"temp", String.valueOf(System.nanoTime()));

							return _file;
						}

						return null;
					}

				}));
	}

	@After
	public void tearDown() {
		_liferayInputStream.cleanUp();
	}

	@Test
	public void testGetCachedInputStreamFromFile() throws Exception {
		testGetCachedInputStream(true, _UNCACHEABLE_BYTES);
	}

	@Test
	public void testGetCachedInputStreamFromMemory() throws Exception {
		testGetCachedInputStream(true, _CACHEABLE_BYTES);
	}

	@Test
	public void testGetCachedInputStreamWithoutCopying() throws Exception {
		testGetCachedInputStream(false, _UNCACHEABLE_BYTES);
	}

	@Test
	public void testInitialReadWithCacheableBytes() throws Exception {
		testInitialRead(_CACHEABLE_BYTES);
	}

	@Test
	public void testInitialReadWithUncacheableBytes() throws Exception {
		testInitialRead(_UNCACHEABLE_BYTES);
	}

	@Test
	public void testInitialReadWithUncacheableBytesWithCopying()
		throws Exception {

		_mockHttpServletRequest.setAttribute(
			UploadServletRequestFilter.COPY_MULTIPART_STREAM_TO_FILE,
			Boolean.FALSE);

		testInitialRead(_UNCACHEABLE_BYTES);
	}

	@Test
	public void testTempFileCreation() throws Exception {
		testTempFile(_UNCACHEABLE_BYTES);

		Assert.assertTrue(_file.exists());
	}

	@Test
	public void testTempFileDeletion() throws Exception {
		testTempFile(_UNCACHEABLE_BYTES);

		_liferayInputStream.cleanUp();

		Assert.assertFalse(_file.exists());
	}

	@Test
	public void testTempFileNoCreation() throws Exception {
		testTempFile(_CACHEABLE_BYTES);

		Assert.assertNull(_file);
	}

	protected void assertCachedInputStreamReadability(
			boolean readable, byte[] expectedBytes)
		throws Exception {

		InputStream inputStream = _liferayInputStream.getCachedInputStream();

		if (readable) {
			byte[] bytes = new byte[expectedBytes.length];

			Assert.assertEquals(expectedBytes.length, inputStream.read(bytes));

			Assert.assertArrayEquals(expectedBytes, bytes);
		}
		else {
			Assert.assertEquals(
				-1, inputStream.read(new byte[expectedBytes.length]));
		}
	}

	protected void testGetCachedInputStream(boolean readable, byte[] content)
		throws Exception {

		_mockHttpServletRequest.setContent(content);

		_mockHttpServletRequest.setAttribute(
			UploadServletRequestFilter.COPY_MULTIPART_STREAM_TO_FILE, readable);

		_liferayInputStream = new LiferayInputStream(_mockHttpServletRequest);

		_liferayInputStream.read(new byte[content.length], 0, content.length);

		assertCachedInputStreamReadability(readable, content);
	}

	protected void testInitialRead(byte[] content) throws Exception {
		_mockHttpServletRequest.setContent(content);

		_liferayInputStream = new LiferayInputStream(_mockHttpServletRequest);

		byte[] bytes = new byte[content.length];

		Assert.assertEquals(
			content.length, _liferayInputStream.read(bytes, 0, bytes.length));

		Assert.assertArrayEquals(content, bytes);
	}

	protected void testTempFile(byte[] content) throws Exception {
		_mockHttpServletRequest.setContent(content);

		_liferayInputStream = new LiferayInputStream(_mockHttpServletRequest);

		_liferayInputStream.read(new byte[content.length], 0, content.length);
	}

	private static final byte[] _CACHEABLE_BYTES =
		new byte[(int)LiferayInputStream.THRESHOLD_SIZE - 1];

	private static final byte[] _UNCACHEABLE_BYTES =
		new byte[(int)LiferayInputStream.THRESHOLD_SIZE];

	static {
		for (int i = 0; i < _CACHEABLE_BYTES.length; i++) {
			_CACHEABLE_BYTES[i] = (byte)i;
		}

		for (int i = 0; i < _UNCACHEABLE_BYTES.length; i++) {
			_UNCACHEABLE_BYTES[i] = (byte)i;
		}
	}

	private java.io.File _file;
	private LiferayInputStream _liferayInputStream;
	private final MockHttpServletRequest _mockHttpServletRequest =
		new MockHttpServletRequest();

}