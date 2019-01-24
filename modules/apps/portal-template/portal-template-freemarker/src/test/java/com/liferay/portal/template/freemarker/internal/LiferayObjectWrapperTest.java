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

import com.liferay.petra.function.UnsafeFunction;
import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.aspects.ReflectionUtilAdvice;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;

import freemarker.ext.beans.EnumerationModel;
import freemarker.ext.beans.MapModel;
import freemarker.ext.beans.ResourceBundleModel;
import freemarker.ext.beans.StringModel;
import freemarker.ext.dom.NodeModel;
import freemarker.ext.util.ModelFactory;

import freemarker.template.SimpleSequence;
import freemarker.template.TemplateModel;
import freemarker.template.TemplateModelException;
import freemarker.template.Version;

import java.lang.reflect.Field;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.LogRecord;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * @author Xiangyue Cai
 */
public class LiferayObjectWrapperTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@Test
	public void testCheckClassIsRestricted() {
		_testCheckClassIsRestricted(
			new LiferayObjectWrapper(null, null), TestLiferayObject.class,
			null);

		_testCheckClassIsRestricted(
			new LiferayObjectWrapper(
				new String[] {TestLiferayObject.class.getName()},
				new String[] {TestLiferayObject.class.getName()}),
			TestLiferayObject.class, null);

		_testCheckClassIsRestricted(
			new LiferayObjectWrapper(null, new String[] {"java.lang.String"}),
			TestLiferayObject.class, null);

		_testCheckClassIsRestricted(
			new LiferayObjectWrapper(
				null, new String[] {"com.liferay.portal.cache"}),
			TestLiferayObject.class, null);

		_testCheckClassIsRestricted(
			new LiferayObjectWrapper(
				null, new String[] {TestLiferayObject.class.getName()}),
			TestLiferayObject.class,
			StringBundler.concat(
				"Denied resolving class ", TestLiferayObject.class.getName(),
				" by ", TestLiferayObject.class.getName()));

		_testCheckClassIsRestricted(
			new LiferayObjectWrapper(
				null, new String[] {"com.liferay.portal.template.freemarker"}),
			TestLiferayObject.class,
			StringBundler.concat(
				"Denied resolving class ", TestLiferayObject.class.getName(),
				" by com.liferay.portal.template.freemarker"));

		_testCheckClassIsRestricted(
			new LiferayObjectWrapper(
				null, new String[] {"com.liferay.portal.template.freemarker"}),
			byte.class, null);
	}

	@Test
	public void testCheckClassIsRestrictedWithNoContextClassloader() {
		Thread thread = Thread.currentThread();

		ClassLoader contextClassLoader = thread.getContextClassLoader();

		thread.setContextClassLoader(null);

		try {
			_testCheckClassIsRestricted(
				new LiferayObjectWrapper(
					new String[] {TestLiferayObject.class.getName()},
					new String[] {TestLiferayObject.class.getName()}),
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
					LiferayObjectWrapper.class.getName(), Level.INFO)) {

			Assert.assertEquals(
				Collections.singletonList("com.liferay.package.name"),
				ReflectionTestUtil.getFieldValue(
					new LiferayObjectWrapper(
						null, new String[] {"com.liferay.package.name"}),
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
					LiferayObjectWrapper.class.getName(), Level.OFF)) {

			Assert.assertEquals(
				Collections.singletonList("com.liferay.package.name"),
				ReflectionTestUtil.getFieldValue(
					new LiferayObjectWrapper(
						null, new String[] {"com.liferay.package.name"}),
					"_restrictedPackageNames"));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(logRecords.toString(), 0, logRecords.size());
		}

		Field cacheClassNamesField = ReflectionTestUtil.getAndSetFieldValue(
			LiferayObjectWrapper.class, "_cacheClassNamesField", null);

		try {
			new LiferayObjectWrapper(null, null);

			Assert.fail("NullPointerException was not thrown");
		}
		catch (Exception e) {
			Assert.assertSame(NullPointerException.class, e.getClass());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				LiferayObjectWrapper.class, "_cacheClassNamesField",
				cacheClassNamesField);
		}
	}

	@Test
	public void testHandleUnknownType() throws Exception {
		LiferayObjectWrapper liferayObjectWrapper = new LiferayObjectWrapper(
			null, null);

		// 1. Handle Enumeration

		Enumeration<String> enumeration = Collections.enumeration(
			Collections.singletonList("testElement"));

		_assertTemplateModel(
			"testElement", enumerationModel -> enumerationModel.next(),
			EnumerationModel.class.cast(
				liferayObjectWrapper.handleUnknownType(enumeration)));

		_assertModelFactoryCache(
			"_ENUMERATION_MODEL_FACTORY", enumeration.getClass());

		// 2. Handle Node

		Node node = (Node)ProxyUtil.newProxyInstance(
			LiferayObjectWrapper.class.getClassLoader(),
			new Class<?>[] {Node.class, Element.class},
			(proxy, method, args) -> {
				String methodName = method.getName();

				if (methodName.equals("getNodeType")) {
					return Node.ELEMENT_NODE;
				}

				return null;
			});

		TemplateModel templateModel = liferayObjectWrapper.handleUnknownType(
			node);

		Assert.assertTrue(
			"org.w3c.dom.Node should be handled as NodeModel",
			templateModel instanceof NodeModel);

		NodeModel nodeModel = (NodeModel)templateModel;

		Assert.assertSame(node, nodeModel.getNode());
		Assert.assertEquals("element", nodeModel.getNodeType());

		_assertModelFactoryCache("_NODE_MODEL_FACTORY", node.getClass());

		// 3. Handle ResourceBundle

		ResourceBundle resourceBundle = new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return null;
			}

			@Override
			protected Object handleGetObject(String key) {
				return key;
			}

		};

		_assertTemplateModel(
			resourceBundle.toString(),
			resourceBundleModel -> resourceBundleModel.getBundle(),
			ResourceBundleModel.class.cast(
				liferayObjectWrapper.handleUnknownType(resourceBundle)));

		_assertModelFactoryCache(
			"_RESOURCE_BUNDLE_MODEL_FACTORY", resourceBundle.getClass());

		// 4. Handle Version

		_assertTemplateModel(
			"1.0", stringModel -> stringModel.getAsString(),
			StringModel.class.cast(
				liferayObjectWrapper.handleUnknownType(
					liferayObjectWrapper.handleUnknownType(
						new Version("1.0")))));

		_assertModelFactoryCache("_STRING_MODEL_FACTORY", Version.class);
	}

	@AdviseWith(adviceClasses = ReflectionUtilAdvice.class)
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testInitializationFailure() throws Exception {
		Exception exception = new Exception();

		ReflectionUtilAdvice.setDeclaredFieldThrowable(exception);

		try {
			Class.forName(
				"com.liferay.portal.template.freemarker.internal." +
					"LiferayObjectWrapper");

			Assert.fail("ExceptionInInitializerError was not thrown");
		}
		catch (ExceptionInInitializerError eiie) {
			Assert.assertSame(exception, eiie.getCause());
		}
	}

	@Test
	public void testWrap() throws Exception {
		_testWrap(new LiferayObjectWrapper(null, null));
		_testWrap(
			new LiferayObjectWrapper(new String[] {StringPool.STAR}, null));
		_testWrap(
			new LiferayObjectWrapper(
				new String[] {StringPool.STAR},
				new String[] {LiferayObjectWrapper.class.getName()}));
		_testWrap(
			new LiferayObjectWrapper(new String[] {StringPool.BLANK}, null));
		_testWrap(
			new LiferayObjectWrapper(null, new String[] {StringPool.BLANK}));
		_testWrap(
			new LiferayObjectWrapper(
				new String[] {StringPool.BLANK},
				new String[] {StringPool.BLANK}));
	}

	private void _assertModelFactoryCache(
		String modelFactoryFieldName, Class<?> clazz) {

		Map<Class<?>, ModelFactory> modelFactories =
			ReflectionTestUtil.getFieldValue(
				LiferayObjectWrapper.class, "_modelFactories");

		Assert.assertSame(
			ReflectionTestUtil.getFieldValue(
				LiferayObjectWrapper.class, modelFactoryFieldName),
			modelFactories.get(clazz));
	}

	private <T, R> void _assertTemplateModel(
			String expectResult, UnsafeFunction<T, R, Exception> unsafeFunction,
			T templateModel)
		throws Exception {

		R result = unsafeFunction.apply(templateModel);

		Assert.assertEquals(expectResult, result.toString());
	}

	private void _testCheckClassIsRestricted(
		LiferayObjectWrapper liferayObjectWrapper, Class<?> targetClass,
		String exceptionMessage) {

		try {
			ReflectionTestUtil.invoke(
				liferayObjectWrapper, "_checkClassIsRestricted",
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

	private void _testWrap(LiferayObjectWrapper liferayObjectWrapper)
		throws Exception {

		// 1. Wrap null

		Assert.assertNull(liferayObjectWrapper.wrap(null));

		// 2. Wrap TemplateModel

		TemplateModel dummyTemplateModel = new TemplateModel() {
		};

		Assert.assertSame(
			dummyTemplateModel, liferayObjectWrapper.wrap(dummyTemplateModel));

		// 3. Wrap TemplateNode

		_assertTemplateModel(
			"testName",
			liferayTemplateModel -> liferayTemplateModel.get("name"),
			LiferayTemplateModel.class.cast(
				liferayObjectWrapper.wrap(
					new TemplateNode(null, "testName", "", "", null))));

		// 4. Wrap Liferay collection

		_assertTemplateModel(
			"testElement", simpleSequence -> simpleSequence.get(0),
			SimpleSequence.class.cast(
				liferayObjectWrapper.wrap(
					new TestLiferayCollection("testElement"))));

		// 5. Wrap Liferay map

		_assertTemplateModel(
			"testValue", mapModel -> mapModel.get("testKey"),
			MapModel.class.cast(
				liferayObjectWrapper.wrap(
					new TestLiferayMap("testKey", "testValue"))));

		// 6. Wrap Liferay object

		_assertTemplateModel(
			"TestLiferayObject", stringModel -> stringModel.getAsString(),
			StringModel.class.cast(
				liferayObjectWrapper.wrap(
					new TestLiferayObject("TestLiferayObject"))));

		// 7. Wrap non-Liferay object when module factory is avaiable

		Map<Class<?>, ModelFactory> modelFactories =
			ReflectionTestUtil.getFieldValue(
				LiferayObjectWrapper.class, "_modelFactories");

		modelFactories.put(
			String.class, (object, wrapper) -> dummyTemplateModel);

		Assert.assertSame(
			dummyTemplateModel, liferayObjectWrapper.wrap(StringPool.BLANK));

		modelFactories.clear();
	}

	private class TestLiferayCollection extends ArrayList<String> {

		private TestLiferayCollection(String element) {
			add(element);
		}

		private static final long serialVersionUID = 1L;

	}

	private class TestLiferayMap extends HashMap<String, String> {

		private TestLiferayMap(String key, String value) {
			put(key, value);
		}

		private static final long serialVersionUID = 1L;

	}

	private class TestLiferayObject {

		@Override
		public String toString() {
			return _name;
		}

		private TestLiferayObject(String name) {
			_name = name;
		}

		private final String _name;

	}

}