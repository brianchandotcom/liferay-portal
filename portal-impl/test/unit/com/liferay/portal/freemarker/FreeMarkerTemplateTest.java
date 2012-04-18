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

package com.liferay.portal.freemarker;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.templateparser.TemplateContext;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.template.TemplateContextHelper;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;

import freemarker.core.ParseException;

import freemarker.template.Configuration;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

/**
 * @author Tina Tian
 */
public class FreeMarkerTemplateTest extends TestCase {

	@Override
	public void setUp() throws Exception {
		_templateContextHelper = new MockTemplateContextHelper();
		_stringTemplateLoader = new StringTemplateLoader();
		_configuration = new Configuration();

		MultiTemplateLoader multiTemplateLoader =
			new MultiTemplateLoader(
				new TemplateLoader[] {
					new ClassTemplateLoader(
						FreeMarkerTemplateTest.class, StringPool.SLASH),
					_stringTemplateLoader, new MockTemplateLoader()
				});

		_configuration.setLocalizedLookup(false);
		_configuration.setTemplateLoader(multiTemplateLoader);
	}

	public void testGet() throws Exception {
		FreeMarkerTemplate freeMarkerTemplate = new FreeMarkerTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, null, _configuration,
			_templateContextHelper, _stringTemplateLoader);

		String testValue = "testValue";

		freeMarkerTemplate.put(_TEST_KEY, testValue);

		Object result = freeMarkerTemplate.get(_TEST_KEY);

		assertNotNull(result);

		assertTrue(result instanceof String);

		String stringResult = (String)result;

		assertEquals(testValue, stringResult);
	}

	public void testPrepare() throws Exception {
		String testValue = "testValue";

		FreeMarkerTemplate freeMarkerTemplate = new FreeMarkerTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, null, _configuration,
			_templateContextHelper, _stringTemplateLoader);

		freeMarkerTemplate.put(_TEST_KEY, testValue);

		freeMarkerTemplate.prepare(null);

		Object result = freeMarkerTemplate.get(testValue);

		assertNotNull(result);

		assertTrue(result instanceof String);

		String stringResult = (String)result;

		assertEquals(testValue, stringResult);
	}

	public void testProcessTemplate1() throws Exception {
		String testValue = "testValue";

		FreeMarkerTemplate freeMarkerTemplate = new FreeMarkerTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, null, _configuration,
			_templateContextHelper, _stringTemplateLoader);

		freeMarkerTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		freeMarkerTemplate.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(testValue, result);
	}

	public void testProcessTemplate2() throws Exception {
		String templateId = "wrongTemplateId";
		String testValue = "testValue";

		FreeMarkerTemplate freeMarkerTemplate = new FreeMarkerTemplate(
			templateId, null, null, null, null, _configuration,
			_templateContextHelper, _stringTemplateLoader);

		freeMarkerTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			freeMarkerTemplate.processTemplate(unsyncStringWriter);

			fail();
		}
		catch (Exception e) {
			if (e instanceof TemplateException) {
				String message = e.getMessage();

				assertTrue(message.contains(templateId));

				return;
			}

			fail();
		}
	}

	public void testProcessTemplate3() throws Exception {
		String templateId = "wrongTemplateId";
		String testValue = "testValue";

		FreeMarkerTemplate freeMarkerTemplate = new FreeMarkerTemplate(
			templateId, _TEST_TEMPLATE_CONTENT, null, null, null,
			_configuration, _templateContextHelper, _stringTemplateLoader);

		freeMarkerTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		freeMarkerTemplate.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(testValue, result);
	}

	public void testProcessTemplate4() throws Exception {
		String errorTemplateId = "wrongTemplateId";
		String testValue = "testValue";

		FreeMarkerTemplate freeMarkerTemplate = new FreeMarkerTemplate(
			_TEMPLATE_FILE_NAME, null, errorTemplateId, null, null,
			_configuration, _templateContextHelper, _stringTemplateLoader);

		freeMarkerTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		freeMarkerTemplate.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(testValue, result);
	}

	public void testProcessTemplate5() throws Exception {
		String templateId = "wrongTemplateId";
		String testValue = "testValue";

		FreeMarkerTemplate freeMarkerTemplate = new FreeMarkerTemplate(
			templateId, null, _TEMPLATE_FILE_NAME, null, null, _configuration,
			_templateContextHelper, _stringTemplateLoader);

		freeMarkerTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		freeMarkerTemplate.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(testValue, result);
	}

	public void testProcessTemplate6() throws Exception {
		String templateId = "wrongTemplateId1";
		String errorTemplateId = "wrongTemplateId2";
		String testValue = "testValue";

		FreeMarkerTemplate freeMarkerTemplate = new FreeMarkerTemplate(
			templateId, null, errorTemplateId, null, null, _configuration,
			_templateContextHelper, _stringTemplateLoader);

		freeMarkerTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			freeMarkerTemplate.processTemplate(unsyncStringWriter);

			fail();
		}
		catch (Exception e) {
			if (e instanceof TemplateException) {
				String message = e.getMessage();

				assertTrue(message.contains(errorTemplateId));

				return;
			}

			fail();
		}
	}

	public void testProcessTemplate7() throws Exception {
		String templateId = "wrongTemplateId1";
		String errorTemplateId = "wrongTemplateId2";
		String testValue = "testValue";

		FreeMarkerTemplate freeMarkerTemplate = new FreeMarkerTemplate(
			templateId, null, errorTemplateId, _TEST_TEMPLATE_CONTENT, null,
			_configuration, _templateContextHelper, _stringTemplateLoader);

		freeMarkerTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		freeMarkerTemplate.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(testValue, result);
	}

	public void testProcessTemplate8() throws Exception {
		String testValue = "testValue";

		Map<String, Object> context = new HashMap<String, Object>();

		context.put(_TEST_KEY, testValue);

		FreeMarkerTemplate freeMarkerTemplate = new FreeMarkerTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, context, _configuration,
			_templateContextHelper, _stringTemplateLoader);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		freeMarkerTemplate.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(testValue, result);
	}

	private static final String _TEMPLATE_FILE_NAME = "test.ftl";
	private static final String _TEST_KEY = "TEST_KEY";
	private static final String _TEST_TEMPLATE_CONTENT = "${"+ _TEST_KEY +"}";

	private Configuration _configuration;
	private StringTemplateLoader _stringTemplateLoader;
	private TemplateContextHelper _templateContextHelper;

	private class MockTemplateContextHelper extends TemplateContextHelper {

		@Override
		public Map<String, Object> getHelperUtilities() {
			return Collections.emptyMap();
		}

		@Override
		public Map<String, Object> getRestrictedHelperUtilities() {
			return Collections.emptyMap();
		}

		@Override
		public Set<String> getRestrictedVariables() {
			return Collections.emptySet();
		}

		@Override
		public void prepare(
				TemplateContext templateContext, HttpServletRequest request)
			throws TemplateException {

			String testValue = (String)templateContext.get(_TEST_KEY);

			templateContext.put(testValue, testValue);
		}

	}

	private class MockTemplateLoader implements TemplateLoader {

		public Object findTemplateSource(String string) throws IOException {
			if (_TEMPLATE_FILE_NAME.equals(string)) {
				return _TEMPLATE_FILE_NAME;
			}

			throw new ParseException("Unable to find template" + string, 0, 0);
		}

		public long getLastModified(Object o) {
			return 0;
		}

		public Reader getReader(Object o, String string) throws IOException {
			if (o == _TEMPLATE_FILE_NAME) {
				return new StringReader(_TEST_TEMPLATE_CONTENT);
			}

			throw new ParseException("Unable to find template" + string, 0, 0);
		}

		public void closeTemplateSource(Object o) throws IOException {
		}

	}

}