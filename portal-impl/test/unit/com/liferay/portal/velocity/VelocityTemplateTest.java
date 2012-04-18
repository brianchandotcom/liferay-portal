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

	public void testGet() throws Exception {
		String testValue = "testValue";

		VelocityTemplate velocityTemplate = new VelocityTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, null, _velocityEngine,
			_templateContextHelper);

		velocityTemplate.put(_TEST_KEY, testValue);

		Object result = velocityTemplate.get(_TEST_KEY);

		assertNotNull(result);

		assertTrue(result instanceof String);

		String stringResult = (String)result;

		assertEquals(testValue, stringResult);
	}

	public void testPrepare() throws Exception {
		String testValue = "testValue";

		VelocityTemplate velocityTemplate = new VelocityTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, null, _velocityEngine,
			_templateContextHelper);

		velocityTemplate.put(_TEST_KEY, testValue);

		velocityTemplate.prepare(null);

		Object result = velocityTemplate.get(testValue);

		assertNotNull(result);

		assertTrue(result instanceof String);

		String stringResult = (String)result;

		assertEquals(testValue, stringResult);
	}

	public void testProcessTemplate1() throws Exception {
		String testValue = "testValue";

		VelocityTemplate velocityTemplate = new VelocityTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, null, _velocityEngine,
			_templateContextHelper);

		velocityTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		velocityTemplate.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(testValue, result);
	}

	public void testProcessTemplate2() throws Exception {
		String templateId = "wrongTemplateId";
		String testValue = "testValue";

		VelocityTemplate velocityTemplate = new VelocityTemplate(
			templateId, null, null, null, null, _velocityEngine,
			_templateContextHelper);

		velocityTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			velocityTemplate.processTemplate(unsyncStringWriter);

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

		VelocityTemplate velocityTemplate = new VelocityTemplate(
			templateId, _TEST_TEMPLATE_CONTENT, null, null, null,
			_velocityEngine, _templateContextHelper);

		velocityTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		velocityTemplate.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(testValue, result);
	}

	public void testProcessTemplate4() throws Exception {
		String errorTemplateId = "wrongTemplateId";
		String testValue = "testValue";

		VelocityTemplate velocityTemplate = new VelocityTemplate(
			_TEMPLATE_FILE_NAME, null, errorTemplateId, null, null,
			_velocityEngine, _templateContextHelper);

		velocityTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		velocityTemplate.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(testValue, result);
	}

	public void testProcessTemplate5() throws Exception {
		String templateId = "wrongTemplateId";
		String testValue = "testValue";

		VelocityTemplate velocityTemplate = new VelocityTemplate(
			templateId, null, _TEMPLATE_FILE_NAME, null, null, _velocityEngine,
			_templateContextHelper);

		velocityTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		velocityTemplate.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(testValue, result);
	}

	public void testProcessTemplate6() throws Exception {
		String templateId = "wrongTemplateId1";
		String errorTemplateId = "wrongTemplateId2";
		String testValue = "testValue";

		VelocityTemplate velocityTemplate = new VelocityTemplate(
			templateId, null, errorTemplateId, null, null, _velocityEngine,
			_templateContextHelper);

		velocityTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		try {
			velocityTemplate.processTemplate(unsyncStringWriter);

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

		VelocityTemplate velocityTemplate = new VelocityTemplate(
			templateId, null, errorTemplateId, _TEST_TEMPLATE_CONTENT, null,
			_velocityEngine, _templateContextHelper);

		velocityTemplate.put(_TEST_KEY, testValue);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		velocityTemplate.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(testValue, result);
	}

	public void testProcessTemplate8() throws Exception {
		String testValue = "testValue";

		VelocityContext context = new VelocityContext();

		context.put(_TEST_KEY, testValue);

		VelocityTemplate velocityTemplate = new VelocityTemplate(
			_TEMPLATE_FILE_NAME, null, null, null, context, _velocityEngine,
			_templateContextHelper);

		UnsyncStringWriter unsyncStringWriter = new UnsyncStringWriter();

		velocityTemplate.processTemplate(unsyncStringWriter);

		String result = unsyncStringWriter.toString();

		assertEquals(testValue, result);
	}

	public static class MockResourceLoader extends ResourceLoader {

		@Override
		public void init(ExtendedProperties ep) {
		}

		@Override
		public InputStream getResourceStream(String string)
			throws ResourceNotFoundException {

			try {
				if (_TEMPLATE_FILE_NAME.equals(string)) {
					return new ByteArrayInputStream(
						_TEST_TEMPLATE_CONTENT.getBytes());
				}

				throw new ResourceNotFoundException("Unable to find " + string);
			}
			catch (Exception e) {
				throw new ResourceNotFoundException(e);
			}
		}

		@Override
		public boolean isSourceModified(Resource rsrc) {
			return false;
		}

		@Override
		public long getLastModified(Resource rsrc) {
			return 0;
		}

	}

	private static final String _TEMPLATE_FILE_NAME = "test.vm";
	private static final String _TEST_KEY = "TEST_KEY";
	private static final String _TEST_TEMPLATE_CONTENT = "$"+ _TEST_KEY;

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
				TemplateContext templateContext, HttpServletRequest request)
			throws TemplateException {

			String testValue = (String)templateContext.get(_TEST_KEY);

			templateContext.put(testValue, testValue);
		}

	}

}