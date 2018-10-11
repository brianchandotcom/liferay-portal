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
		Assert.assertEquals(
			Collections.emptyList(),
			ReflectionTestUtil.getFieldValue(
				new LiferayObjectWrapper(null, null), "_allowedClassNames"));
		Assert.assertEquals(
			Collections.emptyList(),
			ReflectionTestUtil.getFieldValue(
				new LiferayObjectWrapper(null, null), "_restrictedClasses"));
		Assert.assertEquals(
			Collections.emptyList(),
			ReflectionTestUtil.getFieldValue(
				new LiferayObjectWrapper(null, null),
				"_restrictedPackageNames"));
		Assert.assertFalse(
			"_allowAllClasses should be false if \"*\" is not in " +
				"_allowedClassNames",
			ReflectionTestUtil.getFieldValue(
				new LiferayObjectWrapper(null, null), "_allowAllClasses"));

		Assert.assertEquals(
			Collections.singletonList(StringPool.STAR),
			ReflectionTestUtil.getFieldValue(
				new LiferayObjectWrapper(new String[] {StringPool.STAR}, null),
				"_allowedClassNames"));
		Assert.assertTrue(
			"_allowAllClasses should be true if \"*\" is in _allowedClassNames",
			ReflectionTestUtil.getFieldValue(
				new LiferayObjectWrapper(new String[] {StringPool.STAR}, null),
				"_allowAllClasses"));

		Assert.assertEquals(
			Collections.emptyList(),
			ReflectionTestUtil.getFieldValue(
				new LiferayObjectWrapper(new String[] {StringPool.BLANK}, null),
				"_allowedClassNames"));

		Assert.assertEquals(
			Collections.singletonList("com.liferay.allowed.Class"),
			ReflectionTestUtil.getFieldValue(
				new LiferayObjectWrapper(
					new String[] {"com.liferay.allowed.Class"}, null),
				"_allowedClassNames"));

		Assert.assertEquals(
			Collections.emptyList(),
			ReflectionTestUtil.getFieldValue(
				new LiferayObjectWrapper(null, new String[] {StringPool.BLANK}),
				"_restrictedClasses"));

		Assert.assertEquals(
			Collections.singletonList(LiferayObjectWrapper.class),
			ReflectionTestUtil.getFieldValue(
				new LiferayObjectWrapper(
					null, new String[] {LiferayObjectWrapper.class.getName()}),
				"_restrictedClasses"));

		_testContstructorRestrictedPackageNames(
			Collections.emptyList(),
			Collections.singletonList("com.liferay.package.name"), 0,
			new String[] {"com.liferay.package.name"}, Level.OFF);

		_testContstructorRestrictedPackageNames(
			Collections.emptyList(),
			Collections.singletonList("com.liferay.package.name"), 1,
			new String[] {"com.liferay.package.name"}, Level.INFO);
	}

	@Test
	public void testConstructorException() {
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
		LiferayObjectWrapper liferayObjectWrapper = new LiferayObjectWrapper(
			null, null);

		Enumeration<String> enumeration = Collections.enumeration(
			Arrays.asList("testElement"));

		TemplateModel templateModel = liferayObjectWrapper.handleUnknownType(
			enumeration);

		Assert.assertTrue(
			"Enumeration should be handled as EnumerationModel",
			templateModel instanceof EnumerationModel);

		_assertModelFactoryCache(
			"_ENUMERATION_MODEL_FACTORY", enumeration.getClass());

		EnumerationModel enumerationModel = (EnumerationModel)templateModel;

		TemplateModel nextTemplateModel = enumerationModel.next();

		Assert.assertEquals("testElement", nextTemplateModel.toString());
	}

	@Test
	public void testHandleUnknownTypeNode() throws Exception {
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

		LiferayObjectWrapper liferayObjectWrapper = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel = liferayObjectWrapper.handleUnknownType(
			node);

		Assert.assertTrue(
			"org.w3c.dom.Node should be handled as NodeModel",
			templateModel instanceof NodeModel);

		_assertModelFactoryCache("_NODE_MODEL_FACTORY", node.getClass());

		NodeModel nodeModel = (NodeModel)templateModel;

		Assert.assertSame(node, nodeModel.getNode());
		Assert.assertEquals("element", nodeModel.getNodeType());
	}

	@Test
	public void testHandleUnknownTypeResourceBundle() {
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

		LiferayObjectWrapper liferayObjectWrapper = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel = liferayObjectWrapper.handleUnknownType(
			resourceBundle);

		Assert.assertTrue(
			"ResourceBundle should be handled as ResourceBundleModel",
			templateModel instanceof ResourceBundleModel);

		_assertModelFactoryCache(
			"_RESOURCE_BUNDLE_MODEL_FACTORY", resourceBundle.getClass());

		ResourceBundleModel resourceBundleModel =
			(ResourceBundleModel)templateModel;

		ResourceBundle handledResourceBundle = resourceBundleModel.getBundle();

		Assert.assertNull(handledResourceBundle.getKeys());
	}

	@Test
	public void testHandleUnknowTypeVersion() {
		LiferayObjectWrapper liferayObjectWrapper = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel = liferayObjectWrapper.handleUnknownType(
			new Version("1.0"));

		Assert.assertTrue(
			"Unknown type (freemarker.template.Version) should be handled as " +
				"StringModel",
			templateModel instanceof StringModel);

		_assertModelFactoryCache("_STRING_MODEL_FACTORY", Version.class);

		StringModel stringModel = (StringModel)templateModel;

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
	public void testWrapLiferayCollection() throws Exception {
		LiferayObjectWrapper liferayObjectWrapper = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel = liferayObjectWrapper.wrap(
			new TestLiferayCollection("testElement"));

		Assert.assertTrue(
			"Liferay collection implementation should be wrapped as" +
				"SimpleSequence",
			templateModel instanceof SimpleSequence);

		SimpleSequence simpleSequence = (SimpleSequence)templateModel;

		TemplateModel elementTemplateModel = simpleSequence.get(0);

		Assert.assertEquals("testElement", elementTemplateModel.toString());
	}

	@Test
	public void testWrapLiferayMap() throws Exception {
		LiferayObjectWrapper liferayObjectWrapper = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel = liferayObjectWrapper.wrap(
			new TestLiferayMap("testKey", "testValue"));

		Assert.assertTrue(
			"Liferay map implementation should be wrapped as MapModel",
			templateModel instanceof MapModel);

		MapModel mapModel = (MapModel)templateModel;

		TemplateModel testValueModel = mapModel.get("testKey");

		Assert.assertEquals("testValue", testValueModel.toString());
	}

	@Test
	public void testWrapLiferayTemplateNode() throws Exception {
		LiferayObjectWrapper liferayObjectWrapper = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel = liferayObjectWrapper.wrap(
			new TemplateNode(null, "testName", "", "", null));

		Assert.assertTrue(
			"TemplateNode should be wrapped as LiferayTemplateModel",
			templateModel instanceof LiferayTemplateModel);

		LiferayTemplateModel liferayTemplateModel =
			(LiferayTemplateModel)templateModel;

		TemplateModel nameTemplateModel = liferayTemplateModel.get("name");

		Assert.assertEquals("testName", nameTemplateModel.toString());
	}

	@Test
	public void testWrapNull() throws Exception {
		LiferayObjectWrapper liferayObjectWrapper = new LiferayObjectWrapper(
			null, null);

		Assert.assertNull(liferayObjectWrapper.wrap(null));
	}

	@Test
	public void testWrapTemplateModel() throws Exception {
		LiferayObjectWrapper liferayObjectWrapper = new LiferayObjectWrapper(
			null, null);

		TemplateModel templateModel = ProxyFactory.newDummyInstance(
			TemplateModel.class);

		Assert.assertSame(
			templateModel, liferayObjectWrapper.wrap(templateModel));
	}

	@Test
	public void testWrapUnknownType() throws Exception {
		LiferayObjectWrapper liferayObjectWrapper = new LiferayObjectWrapper(
			null, null);

		Assert.assertTrue(
			"Unknown type (freemarker.template.Version) should be wrapped as " +
				"StringModel",
			liferayObjectWrapper.wrap(new Version("1.0")) instanceof
				StringModel);

		_assertModelFactoryCache("_STRING_MODEL_FACTORY", Version.class);

		Map<Class<?>, ModelFactory> modelFactories =
			ReflectionTestUtil.getFieldValue(
				LiferayObjectWrapper.class, "_modelFactories");

		modelFactories.put(Version.class, (object, wrapper) -> null);

		Assert.assertNull(liferayObjectWrapper.wrap(new Version("2.0")));
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

	private void _testContstructorRestrictedPackageNames(
		List<Class<?>> expectedRestrictedClasses,
		List<String> expectedRestrictedPackageNames, int expectedLogSize,
		String[] restrictedClassNames, Level level) {

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					LiferayObjectWrapper.class.getName(), level)) {

			LiferayObjectWrapper liferayObjectWrapper =
				new LiferayObjectWrapper(null, restrictedClassNames);

			Assert.assertEquals(
				expectedRestrictedClasses,
				ReflectionTestUtil.getFieldValue(
					liferayObjectWrapper, "_restrictedClasses"));

			Assert.assertEquals(
				expectedRestrictedPackageNames,
				ReflectionTestUtil.getFieldValue(
					liferayObjectWrapper, "_restrictedPackageNames"));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(
				logRecords.toString(), expectedLogSize, logRecords.size());

			if (Level.INFO.equals(level)) {
				LogRecord logRecord = logRecords.get(0);

				Assert.assertEquals(
					StringBundler.concat(
						"Unable to find restricted class ",
						restrictedClassNames[0], ". Registering as a package."),
					logRecord.getMessage());
			}
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