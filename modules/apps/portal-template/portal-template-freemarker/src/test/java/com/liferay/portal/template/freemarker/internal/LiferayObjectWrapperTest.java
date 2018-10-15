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
import com.liferay.portal.kernel.templateparser.TemplateNode;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.ProxyFactory;
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
import java.lang.reflect.Method;

import java.util.ArrayList;
import java.util.Arrays;
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
	public void testCheckClassIsRestricted() throws Exception {
		_testWrapLiferayObject(new LiferayObjectWrapper(null, null));

		_testWrapLiferayObject(
			new LiferayObjectWrapper(
				new String[] {StringPool.STAR},
				new String[] {TestLiferayObject.class.getName()}));

		_testWrapLiferayObject(
			new LiferayObjectWrapper(
				new String[] {TestLiferayObject.class.getName()},
				new String[] {TestLiferayObject.class.getName()}));

		_testWrapLiferayObject(
			new LiferayObjectWrapper(null, new String[] {"java.lang.String"}));

		_testWrapLiferayObject(
			new LiferayObjectWrapper(
				null, new String[] {"com.liferay.portal.cache"}));

		try {
			_testWrapLiferayObject(
				new LiferayObjectWrapper(
					null, new String[] {TestLiferayObject.class.getName()}));

			Assert.fail("TemplateModelException was not thrown");
		}
		catch (TemplateModelException tme) {
			Assert.assertEquals(
				StringBundler.concat(
					"Denied resolving class ",
					TestLiferayObject.class.getName(), " by ",
					TestLiferayObject.class.getName()),
				tme.getMessage());
		}

		try {
			_testWrapLiferayObject(
				new LiferayObjectWrapper(
					null,
					new String[] {"com.liferay.portal.template.freemarker"}));

			Assert.fail("TemplateModelException was not thrown");
		}
		catch (TemplateModelException tme) {
			Assert.assertEquals(
				StringBundler.concat(
					"Denied resolving class ",
					TestLiferayObject.class.getName(), " by ",
					"com.liferay.portal.template.freemarker"),
				tme.getMessage());
		}

		// Coverage: a class without package is considered allowed

		Method checkClassIsRestricted = ReflectionTestUtil.getMethod(
			LiferayObjectWrapper.class, "_checkClassIsRestricted", Class.class);

		checkClassIsRestricted.invoke(
			new LiferayObjectWrapper(
				null, new String[] {"com.liferay.portal.template.freemarker"}),
			byte.class);
	}

	@Test
	public void testConstructor() {
		_assertLiferayObjectWrapper(
			new LiferayObjectWrapper(null, null), Collections.emptyList(),
			Collections.emptyList(), Collections.emptyList(), false);

		_assertLiferayObjectWrapper(
			new LiferayObjectWrapper(new String[] {StringPool.STAR}, null),
			Collections.singletonList(StringPool.STAR), Collections.emptyList(),
			Collections.emptyList(), true);

		_assertLiferayObjectWrapper(
			new LiferayObjectWrapper(
				new String[] {StringPool.BLANK},
				new String[] {StringPool.BLANK}),
			Collections.emptyList(), Collections.emptyList(),
			Collections.emptyList(), false);

		_assertLiferayObjectWrapper(
			new LiferayObjectWrapper(
				new String[] {"com.liferay.allowed.Class"}, null),
			Collections.singletonList("com.liferay.allowed.Class"),
			Collections.emptyList(), Collections.emptyList(), false);

		_assertLiferayObjectWrapper(
			new LiferayObjectWrapper(
				null, new String[] {LiferayObjectWrapper.class.getName()}),
			Collections.emptyList(),
			Collections.singletonList(LiferayObjectWrapper.class),
			Collections.emptyList(), false);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					LiferayObjectWrapper.class.getName(), Level.INFO)) {

			_assertLiferayObjectWrapper(
				new LiferayObjectWrapper(
					null, new String[] {"com.liferay.package.name"}),
				Collections.emptyList(), Collections.emptyList(),
				Collections.singletonList("com.liferay.package.name"), false);

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

			_assertLiferayObjectWrapper(
				new LiferayObjectWrapper(
					null, new String[] {"com.liferay.package.name"}),
				Collections.emptyList(), Collections.emptyList(),
				Collections.singletonList("com.liferay.package.name"), false);

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
			Assert.assertEquals(NullPointerException.class, e.getClass());
		}
		finally {
			ReflectionTestUtil.setFieldValue(
				LiferayObjectWrapper.class, "_cacheClassNamesField",
				cacheClassNamesField);
		}
	}

	@Test
	public void testHandleUnknownTypeEnumeration()throws Exception {

		// 1. handle Enumeration

		LiferayObjectWrapper liferayObjectWrapper1 = new LiferayObjectWrapper(
			null, null);

		Enumeration<String> enumeration = Collections.enumeration(
			Arrays.asList("testElement"));

		TemplateModel templateModel1 = liferayObjectWrapper1.handleUnknownType(
			enumeration);

		Assert.assertTrue(
			"Enumeration should be handled as EnumerationModel",
			templateModel1 instanceof EnumerationModel);

		_assertModelFactoryCache(
			"_ENUMERATION_MODEL_FACTORY", enumeration.getClass());

		EnumerationModel enumerationModel = (EnumerationModel)templateModel1;

		TemplateModel nextTemplateModel = enumerationModel.next();

		Assert.assertEquals("testElement", nextTemplateModel.toString());

		// 2. handle Node

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

		LiferayObjectWrapper liferayObjectWrapper2 = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel2 = liferayObjectWrapper2.handleUnknownType(
			node);

		Assert.assertTrue(
			"org.w3c.dom.Node should be handled as NodeModel",
			templateModel2 instanceof NodeModel);

		_assertModelFactoryCache("_NODE_MODEL_FACTORY", node.getClass());

		NodeModel nodeModel = (NodeModel)templateModel2;

		Assert.assertSame(node, nodeModel.getNode());
		Assert.assertEquals("element", nodeModel.getNodeType());

		// 3. handle ResourceBundle

		ResourceBundle resourceBundle = new ResourceBundle() {

			@Override
			public Enumeration<String> getKeys() {
				return null;
			}

			@Override
			protected Object handleGetObject(String key) {
				return null;
			}

		};

		LiferayObjectWrapper liferayObjectWrapper3 = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel3 = liferayObjectWrapper3.handleUnknownType(
			resourceBundle);

		Assert.assertTrue(
			"ResourceBundle should be handled as ResourceBundleModel",
			templateModel3 instanceof ResourceBundleModel);

		_assertModelFactoryCache(
			"_RESOURCE_BUNDLE_MODEL_FACTORY", resourceBundle.getClass());

		ResourceBundleModel resourceBundleModel =
			(ResourceBundleModel)templateModel3;

		ResourceBundle handledResourceBundle = resourceBundleModel.getBundle();

		Assert.assertNull(handledResourceBundle.getKeys());

		// 4. handle Version

		LiferayObjectWrapper liferayObjectWrapper4 = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel4 = liferayObjectWrapper4.handleUnknownType(
			new Version("1.0"));

		Assert.assertTrue(
			"Unknown type (freemarker.template.Version) should be handled as " +
				"StringModel",
			templateModel4 instanceof StringModel);

		_assertModelFactoryCache("_STRING_MODEL_FACTORY", Version.class);

		StringModel stringModel = (StringModel)templateModel4;

		Assert.assertEquals("1.0", stringModel.getAsString());
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

		// 1. wrap Null

		LiferayObjectWrapper liferayObjectWrapper1 = new LiferayObjectWrapper(
			null, null);

		Assert.assertNull(liferayObjectWrapper1.wrap(null));

		// 2. wrap TemplateModel

		LiferayObjectWrapper liferayObjectWrapper2 = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel1 = ProxyFactory.newDummyInstance(
			TemplateModel.class);

		Assert.assertSame(
			templateModel1, liferayObjectWrapper2.wrap(templateModel1));

		// 3. wrap TemplateNode

		LiferayObjectWrapper liferayObjectWrapper3 = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel2 = liferayObjectWrapper3.wrap(
			new TemplateNode(null, "testName", "", "", null));

		Assert.assertTrue(
			"TemplateNode should be wrapped as LiferayTemplateModel",
			templateModel2 instanceof LiferayTemplateModel);

		LiferayTemplateModel liferayTemplateModel =
			(LiferayTemplateModel)templateModel2;

		TemplateModel nameTemplateModel = liferayTemplateModel.get("name");

		Assert.assertEquals("testName", nameTemplateModel.toString());

		// 4. wrap TestLiferayCollection

		LiferayObjectWrapper liferayObjectWrapper4 = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel3 = liferayObjectWrapper4.wrap(
			new TestLiferayCollection("testElement"));

		Assert.assertTrue(
			"Liferay collection implementation should be wrapped as" +
				"SimpleSequence",
			templateModel3 instanceof SimpleSequence);

		SimpleSequence simpleSequence = (SimpleSequence)templateModel3;

		TemplateModel elementTemplateModel = simpleSequence.get(0);

		Assert.assertEquals("testElement", elementTemplateModel.toString());

		// 5. wrap TestLiferayMap

		LiferayObjectWrapper liferayObjectWrapper5 = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel4 = liferayObjectWrapper5.wrap(
			new TestLiferayMap("testKey", "testValue"));

		Assert.assertTrue(
			"Liferay map implementation should be wrapped as MapModel",
			templateModel4 instanceof MapModel);

		MapModel mapModel = (MapModel)templateModel4;

		TemplateModel testValueModel = mapModel.get("testKey");

		Assert.assertEquals("testValue", testValueModel.toString());

		// 6. wrap UnknownType

		LiferayObjectWrapper liferayObjectWrapper6 = new LiferayObjectWrapper(
			null, null);

		Assert.assertTrue(
			"Unknown type (freemarker.template.Version) should be wrapped as " +
				"StringModel",
			liferayObjectWrapper6.wrap(new Version("1.0")) instanceof
				StringModel);

		_assertModelFactoryCache("_STRING_MODEL_FACTORY", Version.class);

		Map<Class<?>, ModelFactory> modelFactories =
			ReflectionTestUtil.getFieldValue(
				LiferayObjectWrapper.class, "_modelFactories");

		modelFactories.put(Version.class, (object, wrapper) -> null);

		Assert.assertNull(liferayObjectWrapper6.wrap(new Version("2.0")));
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

	private void _assertLiferayObjectWrapper(
		LiferayObjectWrapper liferayObjectWrapper,
		List<String> expectedAllowedClassNames,
		List<Class<?>> expectedRestrictedClasses,
		List<String> expectedRestrictedPackageNames,
		boolean expectedAllowAllClasses) {

		Assert.assertEquals(
			expectedAllowedClassNames,
			ReflectionTestUtil.getFieldValue(
				liferayObjectWrapper, "_allowedClassNames"));
		Assert.assertEquals(
			expectedRestrictedClasses,
			ReflectionTestUtil.getFieldValue(
				liferayObjectWrapper, "_restrictedClasses"));
		Assert.assertEquals(
			expectedRestrictedPackageNames,
			ReflectionTestUtil.getFieldValue(
				liferayObjectWrapper, "_restrictedPackageNames"));

		if (expectedAllowAllClasses) {
			Assert.assertTrue(
				"_allowAllClasses should be true since \"*\" is in " +
					"_allowedClassNames",
				ReflectionTestUtil.getFieldValue(
					liferayObjectWrapper, "_allowAllClasses"));
		}
		else {
			Assert.assertFalse(
				"_allowAllClasses should be false since \"*\" is not in " +
					"_allowedClassNames",
				ReflectionTestUtil.getFieldValue(
					liferayObjectWrapper, "_allowAllClasses"));
		}
	}

	private void _testWrapLiferayObject(
			LiferayObjectWrapper liferayObjectWrapper)
		throws Exception {

		TestLiferayObject testLiferayObject = new TestLiferayObject();

		TemplateModel templateModel = liferayObjectWrapper.wrap(
			testLiferayObject);

		Assert.assertTrue(
			"Liferay classes should be wrapped as StringModel",
			templateModel instanceof StringModel);

		StringModel stringModel = (StringModel)templateModel;

		Assert.assertEquals(
			testLiferayObject.toString(), stringModel.getAsString());
	}

	private class TestLiferayCollection extends ArrayList<String> {

		private TestLiferayCollection(String element) {
			add(element);
		}

	}

	private class TestLiferayMap extends HashMap<String, String> {

		private TestLiferayMap(String key, String value) {
			put(key, value);
		}

	}

	private class TestLiferayObject {
	}

}