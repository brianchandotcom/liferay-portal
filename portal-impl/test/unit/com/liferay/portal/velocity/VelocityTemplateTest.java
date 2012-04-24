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

package com.liferay.portal.velocity;

import com.liferay.portal.kernel.io.unsync.UnsyncStringWriter;
import com.liferay.portal.kernel.template.Template;
import com.liferay.portal.kernel.template.TemplateException;
import com.liferay.portal.kernel.templateparser.TemplateContext;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.template.TemplateContextHelper;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import java.util.Collections;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import junit.framework.TestCase;

import org.apache.commons.collections.ExtendedProperties;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.resource.Resource;
import org.apache.velocity.runtime.resource.loader.ResourceLoader;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;
import org.apache.velocity.runtime.resource.util.StringResourceRepositoryImpl;

/**
 * @author Tina Tian
 */
public class VelocityTemplateTest extends TestCase {

	public static class MockResourceLoader extends ResourceLoader {

		@Override
		public void init(ExtendedProperties extendedProperties) {
		}

		@Override
		public long getLastModified(Resource resource) {
			return 0;
		}

		@Override
		public InputStream getResourceStream(String source)
			throws ResourceNotFoundException {

			try {
				if (_TEMPLATE_FILE_NAME.equals(source)) {
					return new ByteArrayInputStream(
						_TEST_TEMPLATE_CONTENT.getBytes());
				}

				throw new ResourceNotFoundException("Unable to find " + source);
			}
			catch (Exception e) {
				throw new ResourceNotFoundException(e);
			}
		}

		@Override
		public boolean isSourceModified(Resource resource) {
			return false;
		}

	}

	@Override
	public void setUp() throws Exception {
		_templateContextHelper = new MockTemplateContextHelper();

		_velocityEngine = new VelocityEngine();

		ExtendedProperties extendedProperties = new FastExtendedProperties();

		extendedProperties.setProperty(
			VelocityEngine.RESOURCE_LOADER, "string,test");

		extendedProperties.setProperty(
			"string." + VelocityEngine.RESOURCE_LOADER + ".cache",
			String.valueOf(
				PropsValues.VELOCITY_ENGINE_RESOURCE_MANAGER_CACHE_ENABLED));

		extendedProperties.setProperty(
			"string." + VelocityEngine.RESOURCE_LOADER + ".class",
			StringResourceLoader.class.getName());

		extendedProperties.setProperty(
			"string." + VelocityEngine.RESOURCE_LOADER + ".repository.class",
			StringResourceRepositoryImpl.class.getName());

		extendedProperties.setProperty(
			"test." + VelocityEngine.RESOURCE_LOADER + ".cache", "false");

		extendedProperties.setProperty(
			"test." + VelocityEngine.RESOURCE_LOADER + ".class",
			MockResourceLoader.class.getName());

		extendedProperties.setProperty(
			VelocityEngine.RUNTIME_LOG_LOGSYSTEM_CLASS,
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER));

		extendedProperties.setProperty(
			VelocityEngine.RUNTIME_LOG_LOGSYSTEM + ".log4j.category",
			PropsUtil.get(PropsKeys.VELOCITY_ENGINE_LOGGER_CATEGORY));

		_velocityEngine.setExtendedProperties(extendedProperties);

		_velocityEngine.init();
	}

	@Override
	public void tearDown() throws Exception {
		StringResourceLoader.clearRepositories();
	}

	public void testGet() throws Exception {
		Template template = new VelocityTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, null, _velocityEngine,
			_templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		Object result = template.get(_TEST_KEY);

		assertNotNull(result);

		assertTrue(result instanceof String);

		String stringResult = (String)result;

		assertEquals(_TEST_VALUE, stringResult);
	}

	public void testPrepare() throws Exception {
		Template template = new VelocityTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, null, _velocityEngine,
			_templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		template.prepare(null);

		Object result = template.get(_TEST_VALUE);

		assertNotNull(result);

		assertTrue(result instanceof String);

		String stringResult = (String)result;

		assertEquals(_TEST_VALUE, stringResult);
	}

	public void testProcessTemplate1() throws Exception {
		Template template = new VelocityTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, null, _velocityEngine,
			_templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate2() throws Exception {
		Template template = new VelocityTemplate(
			_WRONG_TEMPLATE_ID, null, null, null, null, _velocityEngine,
			_templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			template.processTemplate(unsyncStringWriter);

			fail();
		}
		catch (Exception e) {
			if (e instanceof TemplateException) {
				String message = e.getMessage();

				assertTrue(message.contains(_WRONG_TEMPLATE_ID));

				return;
			}

			fail();
		}
	}

	public void testProcessTemplate3() throws Exception {
		Template template = new VelocityTemplate(
			_WRONG_TEMPLATE_ID, _TEST_TEMPLATE_CONTENT, null, null, null,
			_velocityEngine, _templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate4() throws Exception {
		Template template = new VelocityTemplate(
			_TEMPLATE_FILE_NAME, null, _WRONG_ERROR_TEMPLATE_ID, null, null,
			_velocityEngine, _templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate5() throws Exception {
		Template template = new VelocityTemplate(
			_WRONG_TEMPLATE_ID, null, _TEMPLATE_FILE_NAME, null, null,
			_velocityEngine, _templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate6() throws Exception {
		Template template = new VelocityTemplate(
			_WRONG_TEMPLATE_ID, null, _WRONG_ERROR_TEMPLATE_ID, null, null,
			_velocityEngine, _templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			template.processTemplate(unsyncStringWriter);

			fail();
		}
		catch (Exception e) {
			if (e instanceof TemplateException) {
				String message = e.getMessage();

				assertTrue(message.contains(_WRONG_ERROR_TEMPLATE_ID));

				return;
			}

			fail();
		}
	}

	public void testProcessTemplate7() throws Exception {
		Template template = new VelocityTemplate(
			_WRONG_TEMPLATE_ID, null, _WRONG_ERROR_TEMPLATE_ID,
			_TEST_TEMPLATE_CONTENT, null, _velocityEngine,
			_templateContextHelper);

		template.put(_TEST_KEY, _TEST_VALUE);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	public void testProcessTemplate8() throws Exception {
		VelocityContext velocityContext = new VelocityContext();

		velocityContext.put(_TEST_KEY, _TEST_VALUE);

		Template template = new VelocityTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, velocityContext,
			_velocityEngine, _templateContextHelper);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		template.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(_TEST_VALUE, result);
	}

	private static final String _TEMPLATE_FILE_NAME = "test.vm";

	private static final String _TEST_KEY = "TEST_KEY";

	private static final String _TEST_TEMPLATE_CONTENT = "$" + _TEST_KEY;

	private static final String _TEST_VALUE = "TEST_VALUE";

	private static final String _WRONG_ERROR_TEMPLATE_ID =
		"WRONG_ERROR_TEMPLATE_ID";

	private static final String _WRONG_TEMPLATE_ID = "WRONG_TEMPLATE_ID";

	private TemplateContextHelper _templateContextHelper;
	private VelocityEngine _velocityEngine;

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
			TemplateContext templateContext, HttpServletRequest request) {

			String testValue = (String)templateContext.get(_TEST_KEY);

			templateContext.put(testValue, testValue);
		}

	}

}