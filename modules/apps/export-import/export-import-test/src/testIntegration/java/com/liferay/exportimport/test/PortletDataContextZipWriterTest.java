/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.exportimport.kernel.lar.PortletDataContext;
import com.liferay.exportimport.kernel.lar.PortletDataContextFactoryUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.test.util.TestPropsValues;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.MethodKey;
import com.liferay.portal.kernel.zip.ZipWriter;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author Máté Thurzó
 */
@RunWith(Arquillian.class)
public class PortletDataContextZipWriterTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new LiferayIntegrationTestRule();

	@Before
	public void setUp() throws Exception {
		_portletDataContext =
			PortletDataContextFactoryUtil.createExportPortletDataContext(
				TestPropsValues.getCompanyId(), TestPropsValues.getGroupId(),
				null, null, null, _recorderZipWriter);
	}

	@Test
	public void testMultipleByteArraysAddition() throws Exception {
		byte[] bytes = {Byte.MIN_VALUE, Byte.MAX_VALUE};

		_portletDataContext.addZipEntry(_PATH, bytes);

		List<MethodHandler> methodHandlers =
			_recorderZipWriter.getMethodHandlers();

		Assert.assertEquals(
			methodHandlers.toString(), 1, methodHandlers.size());

		MethodHandler methodHandler = methodHandlers.remove(0);

		MethodKey methodKey = methodHandler.getMethodKey();

		Assert.assertEquals(
			ReflectionTestUtil.getMethod(
				ZipWriter.class, "addEntry",
				new Class<?>[] {String.class, byte[].class}),
			methodKey.getMethod());

		Object[] arguments = methodHandler.getArguments();

		Assert.assertEquals(Arrays.toString(arguments), 2, arguments.length);
		Assert.assertSame(_PATH, arguments[0]);
		Assert.assertSame(bytes, arguments[1]);

		_portletDataContext.addZipEntry(_PATH, bytes);

		Assert.assertTrue(methodHandlers.toString(), methodHandlers.isEmpty());
	}

	@Test
	public void testMultipleInputStreamsAddition() throws Exception {
		byte[] bytes = {Byte.MIN_VALUE, Byte.MAX_VALUE};

		InputStream inputStream = new ByteArrayInputStream(bytes);

		_portletDataContext.addZipEntry(_PATH, inputStream);

		List<MethodHandler> methodHandlers =
			_recorderZipWriter.getMethodHandlers();

		Assert.assertEquals(
			methodHandlers.toString(), 1, methodHandlers.size());

		MethodHandler methodHandler = methodHandlers.remove(0);

		MethodKey methodKey = methodHandler.getMethodKey();

		Assert.assertEquals(
			ReflectionTestUtil.getMethod(
				ZipWriter.class, "addEntry",
				new Class<?>[] {String.class, InputStream.class}),
			methodKey.getMethod());

		Object[] arguments = methodHandler.getArguments();

		Assert.assertEquals(Arrays.toString(arguments), 2, arguments.length);
		Assert.assertSame(_PATH, arguments[0]);
		Assert.assertSame(inputStream, arguments[1]);

		_portletDataContext.addZipEntry(_PATH, inputStream);

		Assert.assertTrue(methodHandlers.toString(), methodHandlers.isEmpty());
	}

	@Test
	public void testMultipleStringsAddition() throws Exception {
		String string = RandomTestUtil.randomString();

		_portletDataContext.addZipEntry(_PATH, string);

		List<MethodHandler> methodHandlers =
			_recorderZipWriter.getMethodHandlers();

		Assert.assertEquals(
			methodHandlers.toString(), 1, methodHandlers.size());

		MethodHandler methodHandler = methodHandlers.remove(0);

		MethodKey methodKey = methodHandler.getMethodKey();

		Assert.assertEquals(
			ReflectionTestUtil.getMethod(
				ZipWriter.class, "addEntry",
				new Class<?>[] {String.class, String.class}),
			methodKey.getMethod());

		Object[] arguments = methodHandler.getArguments();

		Assert.assertEquals(Arrays.toString(arguments), 2, arguments.length);
		Assert.assertSame(_PATH, arguments[0]);
		Assert.assertSame(string, arguments[1]);

		_portletDataContext.addZipEntry(_PATH, string);

		Assert.assertTrue(methodHandlers.toString(), methodHandlers.isEmpty());
	}

	private static final String _PATH = "/test.xml";

	private PortletDataContext _portletDataContext;
	private final RecorderZipWriter _recorderZipWriter =
		new RecorderZipWriter();

	private static class RecorderZipWriter implements ZipWriter {

		@Override
		public void addEntry(String name, byte[] bytes) {
			_methodHandlers.add(
				new MethodHandler(
					ReflectionTestUtil.getMethod(
						ZipWriter.class, "addEntry",
						new Class<?>[] {String.class, byte[].class}),
					name, bytes));
		}

		@Override
		public void addEntry(String name, InputStream inputStream) {
			_methodHandlers.add(
				new MethodHandler(
					ReflectionTestUtil.getMethod(
						ZipWriter.class, "addEntry",
						new Class<?>[] {String.class, InputStream.class}),
					name, inputStream));
		}

		@Override
		public void addEntry(String name, String s) {
			_methodHandlers.add(
				new MethodHandler(
					ReflectionTestUtil.getMethod(
						ZipWriter.class, "addEntry",
						new Class<?>[] {String.class, String.class}),
					name, s));
		}

		@Override
		public void addEntry(String name, StringBuilder sb) {
			throw new UnsupportedOperationException();
		}

		@Override
		public byte[] finish() {
			throw new UnsupportedOperationException();
		}

		@Override
		public File getFile() {
			throw new UnsupportedOperationException();
		}

		public List<MethodHandler> getMethodHandlers() {
			return _methodHandlers;
		}

		@Override
		public String getPath() {
			throw new UnsupportedOperationException();
		}

		@Override
		public void umount() {
		}

		private final List<MethodHandler> _methodHandlers = new ArrayList<>();

	}

}