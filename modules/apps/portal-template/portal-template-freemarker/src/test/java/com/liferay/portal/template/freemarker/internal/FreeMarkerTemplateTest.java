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

package com.liferay.portal.template.freemarker.internal;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.StringTemplateResource;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.template.TemplateResource;
import com.liferay.portal.kernel.template.TemplateResourceCache;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.template.TemplateContextHelper;

import freemarker.cache.TemplateCache;

import freemarker.core.ParseException;

import freemarker.template.Configuration;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.Reader;
import java.io.StringReader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class FreeMarkerTemplateTest {

	@BeforeClass
	public static void setUpClass() throws Exception {
		_templateResourceCache = new FreeMarkerTemplateResourceCache() {

			@Override
			public boolean isEnabled() {
				return false;
			}

		};

		_freeMarkerTemplateResourceLoader =
			new FreeMarkerTemplateResourceLoader();

		ReflectionTestUtil.setFieldValue(
			_freeMarkerTemplateResourceLoader,
			"_freeMarkerTemplateResourceCache", _templateResourceCache);

		_freeMarkerTemplateResourceLoader.activate(Collections.emptyMap());
	}

	@Before
	public void setUp() throws Exception {
		_configuration = new Configuration();

		TemplateCache templateCache = new LiferayTemplateCache(
			_configuration, _freeMarkerTemplateResourceLoader, null);

		ReflectionTestUtil.setFieldValue(
			_configuration, "cache", templateCache);

		_configuration.setLocalizedLookup(false);

		_templateContextHelper = new MockTemplateContextHelper();
	}

	@Test
	public void testGet() throws Exception {
		Template template = new FreeMarkerTemplate(
			new MockTemplateResource(_TEMPLATE_FILE_NAME), null, _configuration,
			_templateContextHelper, _templateResourceCache, false,
			_configuration.getObjectWrapper());

		template.put(_TEST_KEY, _TEST_VALUE);

		Object result = template.get(_TEST_KEY);

		Assert.assertNotNull(result);

		Assert.assertTrue(result instanceof String);

		String stringResult = (String)result;

		Assert.assertEquals(_TEST_VALUE, stringResult);
	}

	@Test
	public void testPrepare() throws Exception {
		Template template = new FreeMarkerTemplate(
			new MockTemplateResource(_TEMPLATE_FILE_NAME), null, _configuration,
			_templateContextHelper, _templateResourceCache, false,
			_configuration.getObjectWrapper());

		template.put(_TEST_KEY, _TEST_VALUE);

		template.prepare(null);

		Object result = template.get(_TEST_VALUE);

		Assert.assertNotNull(result);

		Assert.assertTrue(result instanceof String);

		String stringResult = (String)result;

		Assert.assertEquals(_TEST_VALUE, stringResult);
	}

	@Test
	public void testProcessTemplate1() throws Exception {
		Template template = new FreeMarkerTemplate(
			new MockTemplateResource(_TEMPLATE_FILE_NAME), null, _configuration,
			_templateContextHelper, _templateResourceCache, false,
			_configuration.getObjectWrapper());

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals(_TEST_VALUE, result);
	}

	@Test
	public void testProcessTemplate2() throws Exception {
		Template template = new FreeMarkerTemplate(
			new MockTemplateResource(_WRONG_TEMPLATE_ID), null, _configuration,
			_templateContextHelper, _templateResourceCache, false,
			_configuration.getObjectWrapper());

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			template.processTemplate(unsyncStringWriter);

			Assert.fail();
		}
		catch (TemplateException te) {
			String message = te.getMessage();

			Assert.assertTrue(message, message.contains(_WRONG_TEMPLATE_ID));
		}
	}

	@Test
	public void testProcessTemplate3() throws Exception {
		Template template = new FreeMarkerTemplate(
			new StringTemplateResource(
				_WRONG_TEMPLATE_ID, _TEST_TEMPLATE_CONTENT),
			null, _configuration, _templateContextHelper,
			_templateResourceCache, false, _configuration.getObjectWrapper());

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals(_TEST_VALUE, result);
	}

	@Test
	public void testProcessTemplate4() throws Exception {
		Template template = new FreeMarkerTemplate(
			new MockTemplateResource(_TEMPLATE_FILE_NAME), null, _configuration,
			_templateContextHelper, _templateResourceCache, false,
			_configuration.getObjectWrapper());

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(
			unsyncStringWriter,
			() -> new MockTemplateResource(_WRONG_ERROR_TEMPLATE_ID));

		String result = unsyncStringWriter.toString();

		Assert.assertEquals(_TEST_VALUE, result);
	}

	@Test
	public void testProcessTemplate5() throws Exception {
		Template template = new FreeMarkerTemplate(
			new MockTemplateResource(_WRONG_TEMPLATE_ID), null, _configuration,
			_templateContextHelper, _templateResourceCache, false,
			_configuration.getObjectWrapper());

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(
			unsyncStringWriter,
			() -> new MockTemplateResource(_TEMPLATE_FILE_NAME));

		String result = unsyncStringWriter.toString();

		Assert.assertEquals(_TEST_VALUE, result);
	}

	@Test
	public void testProcessTemplate6() throws Exception {
		Template template = new FreeMarkerTemplate(
			new MockTemplateResource(_WRONG_TEMPLATE_ID), null, _configuration,
			_templateContextHelper, _templateResourceCache, false,
			_configuration.getObjectWrapper());

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			template.processTemplate(
				unsyncStringWriter,
				() -> new MockTemplateResource(_WRONG_ERROR_TEMPLATE_ID));

			Assert.fail();
		}
		catch (TemplateException te) {
			String message = te.getMessage();

			Assert.assertTrue(
				message, message.contains(_WRONG_ERROR_TEMPLATE_ID));
		}
	}

	@Test
	public void testProcessTemplate7() throws Exception {
		Template template = new FreeMarkerTemplate(
			new MockTemplateResource(_WRONG_TEMPLATE_ID), null, _configuration,
			_templateContextHelper, _templateResourceCache, false,
			_configuration.getObjectWrapper());

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(
			unsyncStringWriter,
			() -> new StringTemplateResource(
				_WRONG_ERROR_TEMPLATE_ID, _TEST_TEMPLATE_CONTENT));

		String result = unsyncStringWriter.toString();

		Assert.assertEquals(_TEST_VALUE, result);
	}

	@Test
	public void testProcessTemplate8() throws Exception {
		Map<String, Object> context = new HashMap<>();

		context.put(_TEST_KEY, _TEST_VALUE);

		Template template = new FreeMarkerTemplate(
			new MockTemplateResource(_TEMPLATE_FILE_NAME), context,
			_configuration, _templateContextHelper, _templateResourceCache,
			false, _configuration.getObjectWrapper());

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		Assert.assertEquals(_TEST_VALUE, result);
	}

	private static final String _TEMPLATE_FILE_NAME = "test.ftl";

	private static final String _TEST_KEY = "TEST_KEY";

	private static final String _TEST_TEMPLATE_CONTENT = "${" + _TEST_KEY + "}";

	private static final String _TEST_VALUE = "TEST_VALUE";

	private static final String _WRONG_ERROR_TEMPLATE_ID =
		"WRONG_ERROR_TEMPLATE_ID";

	private static final String _WRONG_TEMPLATE_ID = "WRONG_TEMPLATE_ID";

	private static FreeMarkerTemplateResourceLoader
		_freeMarkerTemplateResourceLoader;
	private static TemplateResourceCache _templateResourceCache;

	private Configuration _configuration;
	private TemplateContextHelper _templateContextHelper;

	private static class MockTemplateContextHelper
		extends TemplateContextHelper {

		@Override
		public Map<String, Object> getHelperUtilities(
			ClassLoader classLoader, boolean restricted) {

			return Collections.emptyMap();
		}

		@Override
		public Set<String> getRestrictedVariables() {
			return Collections.emptySet();
		}

		@Override
		public void prepare(
			Map<String, Object> contextObjects,
			HttpServletRequest httpServletRequest) {

			String testValue = (String)contextObjects.get(_TEST_KEY);

			contextObjects.put(testValue, testValue);
		}

	}

	private static class MockTemplateResource implements TemplateResource {

		/**
		 * The empty constructor is required by {@link java.io.Externalizable}.
		 * Do not use this for any other purpose.
		 */
		@SuppressWarnings("unused")
		public MockTemplateResource() {
		}

		public MockTemplateResource(String templateId) {
			_templateId = templateId;
		}

		@Override
		public long getLastModified() {
			return _lastModified;
		}

		@Override
		public Reader getReader() throws IOException {
			if (_templateId.equals(_TEMPLATE_FILE_NAME)) {
				return new StringReader(_TEST_TEMPLATE_CONTENT);
			}

			throw new ParseException(
				"Unable to get reader for template source " + _templateId, 0,
				0);
		}

		@Override
		public String getTemplateId() {
			return _templateId;
		}

		@Override
		public void readExternal(ObjectInput objectInput) throws IOException {
			_lastModified = objectInput.readLong();
			_templateId = objectInput.readUTF();
		}

		@Override
		public void writeExternal(ObjectOutput objectOutput)
			throws IOException {

			objectOutput.writeLong(_lastModified);
			objectOutput.writeUTF(_templateId);
		}

		private long _lastModified = System.currentTimeMillis();
		private String _templateId;

	}

}