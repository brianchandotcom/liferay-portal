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

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;

import freemarker.ext.beans.InvalidPropertyException;

import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;

import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.hamcrest.CoreMatchers;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Tina Tian
 */
public class RestrictedLiferayObjectWrapperTest
	extends BaseObjectWrapperTestCase {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Test
	public void testCheckClassIsRestricted() {
		_testCheckClassIsRestricted(
			new RestrictedLiferayObjectWrapper(null, null, null),
			TestLiferayObject.class, null);

		_testCheckClassIsRestricted(
			new RestrictedLiferayObjectWrapper(
				new String[] {TestLiferayObject.class.getName()},
				new String[] {TestLiferayObject.class.getName()}, null),
			TestLiferayObject.class, null);

		_testCheckClassIsRestricted(
			new RestrictedLiferayObjectWrapper(
				null, new String[] {"java.lang.String"}, null),
			TestLiferayObject.class, null);

		_testCheckClassIsRestricted(
			new RestrictedLiferayObjectWrapper(
				null, new String[] {"com.liferay.portal.cache"}, null),
			TestLiferayObject.class, null);

		_testCheckClassIsRestricted(
			new RestrictedLiferayObjectWrapper(
				null, new String[] {TestLiferayObject.class.getName()}, null),
			TestLiferayObject.class,
			StringBundler.concat(
				"Denied resolving class ", TestLiferayObject.class.getName(),
				" by ", TestLiferayObject.class.getName()));

		_testCheckClassIsRestricted(
			new RestrictedLiferayObjectWrapper(
				null, new String[] {"com.liferay.portal.template.freemarker"},
				null),
			TestLiferayObject.class,
			StringBundler.concat(
				"Denied resolving class ", TestLiferayObject.class.getName(),
				" by com.liferay.portal.template.freemarker"));

		_testCheckClassIsRestricted(
			new RestrictedLiferayObjectWrapper(
				null, new String[] {"com.liferay.portal.template.freemarker"},
				null),
			byte.class, null);
	}

	@Test
	public void testCheckClassIsRestrictedWithNoContextClassloader() {
		Thread thread = Thread.currentThread();

		ClassLoader contextClassLoader = thread.getContextClassLoader();

		thread.setContextClassLoader(null);

		try {
			_testCheckClassIsRestricted(
				new RestrictedLiferayObjectWrapper(
					new String[] {TestLiferayObject.class.getName()},
					new String[] {TestLiferayObject.class.getName()}, null),
				TestLiferayObject.class, null);
		}
		finally {
			thread.setContextClassLoader(contextClassLoader);
		}
	}

	@Test
	public void testConstructor() {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RestrictedLiferayObjectWrapper.class.getName(),
					Level.INFO)) {

			Assert.assertEquals(
				Collections.singletonList("com.liferay.package.name"),
				ReflectionTestUtil.getFieldValue(
					new RestrictedLiferayObjectWrapper(
						null, new String[] {"com.liferay.package.name"}, null),
					"_restrictedPackageNames"));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				"Unable to find restricted class com.liferay.package.name. " +
					"Registering as a package.",
				logRecord.getMessage());
		}

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RestrictedLiferayObjectWrapper.class.getName(),
					Level.OFF)) {

			Assert.assertEquals(
				Collections.singletonList("com.liferay.package.name"),
				ReflectionTestUtil.getFieldValue(
					new RestrictedLiferayObjectWrapper(
						null, new String[] {"com.liferay.package.name"}, null),
					"_restrictedPackageNames"));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 0, logRecords.size());
		}
	}

	@Test
	public void testRestrictedMethodNames() throws Exception {
		RestrictedLiferayObjectWrapper restrictedLiferayObjectWrapper =
			new RestrictedLiferayObjectWrapper(
				null, null,
				new String[] {
					TestLiferayMethodObject.class.getName() + "#getName"
				});

		TemplateModel templateModel = restrictedLiferayObjectWrapper.wrap(
			new TestLiferayMethodObject("name"));

		Assert.assertThat(
			templateModel,
			CoreMatchers.instanceOf(LiferayFreeMarkerBeanModel.class));

		LiferayFreeMarkerBeanModel liferayFreeMarkerBeanModel =
			(LiferayFreeMarkerBeanModel)templateModel;

		_testRestrictedMethodNames(liferayFreeMarkerBeanModel, "name");
		_testRestrictedMethodNames(liferayFreeMarkerBeanModel, "Name");
		_testRestrictedMethodNames(liferayFreeMarkerBeanModel, "getName");
		_testRestrictedMethodNames(liferayFreeMarkerBeanModel, "getname");
	}

	@Test
	public void testRestrictedMethodNamesIncorrectSyntax() throws Exception {
		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					RestrictedLiferayObjectWrapper.class.getName(),
					Level.INFO)) {

			String methodName =
				TestLiferayMethodObject.class.getName() + ".getName";

			new RestrictedLiferayObjectWrapper(
				null, null, new String[] {methodName});

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				StringBundler.concat(
					"\"", methodName, "\" does not match format ",
					"\"className#methodName\""),
				logRecord.getMessage());
		}
	}

	@Test
	public void testWrap() throws Exception {
		testWrap(new RestrictedLiferayObjectWrapper(null, null, null));
		testWrap(
			new RestrictedLiferayObjectWrapper(
				new String[] {StringPool.STAR}, null, null));
		testWrap(
			new RestrictedLiferayObjectWrapper(
				new String[] {StringPool.STAR},
				new String[] {LiferayObjectWrapper.class.getName()}, null));
		testWrap(
			new RestrictedLiferayObjectWrapper(
				new String[] {StringPool.BLANK}, null, null));
		testWrap(
			new RestrictedLiferayObjectWrapper(
				null, new String[] {StringPool.BLANK}, null));
		testWrap(
			new RestrictedLiferayObjectWrapper(
				new String[] {StringPool.BLANK},
				new String[] {StringPool.BLANK}, null));

		testWrap(
			new RestrictedLiferayObjectWrapper(
				new String[] {StringPool.BLANK},
				new String[] {StringPool.BLANK},
				new String[] {StringPool.BLANK}));

		testWrap(
			new RestrictedLiferayObjectWrapper(
				new String[] {StringPool.BLANK},
				new String[] {StringPool.BLANK},
				new String[] {
					TestLiferayMethodObject.class.getName() + "#getName"
				}));
	}

	private void _testCheckClassIsRestricted(
		RestrictedLiferayObjectWrapper restrictedLiferayObjectWrapper,
		Class<?> targetClass, String exceptionMessage) {

		try {
			ReflectionTestUtil.invoke(
				restrictedLiferayObjectWrapper, "_checkClassIsRestricted",
				new Class<?>[] {Class.class}, targetClass);

			Assert.assertNull(
				"Should throw TemplateModelException", exceptionMessage);
		}
		catch (Exception e) {
			Assert.assertSame(TemplateModelException.class, e.getClass());

			TemplateModelException templateModelException =
				(TemplateModelException)e;

			Assert.assertEquals(
				exceptionMessage, templateModelException.getMessage());
		}
	}

	private void _testRestrictedMethodNames(
		LiferayFreeMarkerBeanModel liferayFreeMarkerBeanModel, String key) {

		try {
			liferayFreeMarkerBeanModel.get(key);

			Assert.assertNull("Should throw TemplateModelException for " + key);
		}
		catch (TemplateModelException tme) {
			Assert.assertSame(InvalidPropertyException.class, tme.getClass());

			Assert.assertEquals(
				StringBundler.concat(
					"Denied access to method or field ", key, " of ",
					TestLiferayMethodObject.class.toString()),
				tme.getMessage());
		}
	}

	private class TestLiferayMethodObject {

		public String getName() {
			return _name;
		}

		public void setName(String name) {
			_name = name;
		}

		@Override
		public String toString() {
			return _name;
		}

		private TestLiferayMethodObject(String name) {
			_name = name;
		}

		private String _name;

	}

}