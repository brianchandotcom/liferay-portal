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

package com.liferay.portal.nio.intraband.proxy;

import com.liferay.portal.asm.ASMUtil;
import com.liferay.portal.asm.MethodNodeGenerator;
import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.log.Jdk14LogImpl;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.log.LogWrapper;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.Intraband;
import com.liferay.portal.kernel.nio.intraband.RegistrationReference;
import com.liferay.portal.kernel.nio.intraband.SystemDataType;
import com.liferay.portal.kernel.nio.intraband.proxy.ExceptionHandler;
import com.liferay.portal.kernel.nio.intraband.proxy.IntrabandProxySkeleton;
import com.liferay.portal.kernel.nio.intraband.proxy.TargetLocator;
import com.liferay.portal.kernel.nio.intraband.proxy.annotation.Id;
import com.liferay.portal.kernel.nio.intraband.proxy.annotation.Proxy;
import com.liferay.portal.kernel.nio.intraband.rpc.RPCResponse;
import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.test.CaptureHandler;
import com.liferay.portal.kernel.test.JDKLoggerTestUtil;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.MethodHandler;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.SystemProperties;
import com.liferay.portal.kernel.util.TextFormatter;
import com.liferay.portal.nio.intraband.proxy.IntrabandProxyUtil.MethodComparator;
import com.liferay.portal.nio.intraband.proxy.IntrabandProxyUtil.MethodsBag;
import com.liferay.portal.nio.intraband.proxy.IntrabandProxyUtil.TemplateSkeleton;
import com.liferay.portal.nio.intraband.proxy.IntrabandProxyUtil.TemplateStub;
import com.liferay.portal.test.aspects.ReflectionUtilAdvice;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;
import com.liferay.portal.util.FileImpl;
import com.liferay.portal.util.PropsValues;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import java.net.URL;
import java.net.URLClassLoader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicReference;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.Opcodes;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.AbstractInsnNode;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldInsnNode;
import org.objectweb.asm.tree.InsnList;
import org.objectweb.asm.tree.IntInsnNode;
import org.objectweb.asm.tree.JumpInsnNode;
import org.objectweb.asm.tree.LabelNode;
import org.objectweb.asm.tree.LdcInsnNode;
import org.objectweb.asm.tree.MethodInsnNode;
import org.objectweb.asm.tree.MethodNode;
import org.objectweb.asm.tree.TypeInsnNode;
import org.objectweb.asm.tree.VarInsnNode;

/**
 * @author Shuyang Zhou
 */
public class IntrabandProxyUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			CodeCoverageAssertor.INSTANCE, AspectJNewEnvTestRule.INSTANCE);

	@Before
	public void setUp() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());
	}

	@Test
	public void testCheckField() {
		class TestClass {

			@SuppressWarnings("unused")
			private String _testField;
		}

		Field[] fields = TestClass.class.getDeclaredFields();

		IntrabandProxyUtil.checkField(
			fields, "noSuchField", String.class, true);

		IntrabandProxyUtil.checkField(
			fields, "_testField", String.class, false);

		try {
			IntrabandProxyUtil.checkField(
				fields, "_testField", Object.class, false);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Field " + fields[0] + " is expected to be of type " +
					Object.class + " and not static",
				iae.getMessage());
		}

		try {
			IntrabandProxyUtil.checkField(
				fields, "_testField", String.class, true);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Field " + fields[0] + " is expected to be of type " +
					String.class + " and static",
				iae.getMessage());
		}
	}

	@Test
	public void testConstructor() {
		new IntrabandProxyUtil();
	}

	@Test
	public void testCreateProxyMethodNode() {
		List<Method> methods = new ArrayList<Method>(
			Arrays.asList(
				TestProxyMethodsInterface.class.getDeclaredMethods()));

		methods.addAll(
			Arrays.asList(TestProxyMethodsClass.class.getDeclaredMethods()));

		for (int i = 0; i < methods.size(); i++) {
			_doTestCreateProxyMethodNode(
				methods.get(i), i, "skeletonId", "TestClassStub");
		}
	}

	@Test
	public void testDeserializerRead() {
		MethodNode methodNode = new MethodNode(
			Opcodes.ACC_PUBLIC, "name", "()V", null, null);

		MethodNodeGenerator methodNodeGenerator = new MethodNodeGenerator(
			methodNode);

		InsnList insnList = methodNode.instructions;

		for (Type type : _types) {
			IntrabandProxyUtil.deserializerRead(methodNodeGenerator, type);

			AbstractInsnNode abstractInsnNode = insnList.getLast();

			String methodName = "readObject";

			Type returnType = Type.getType(Serializable.class);

			if (type.getSort() <= Type.DOUBLE) {
				String name = TextFormatter.format(
					type.getClassName(), TextFormatter.G);

				methodName = "read".concat(name);
				returnType = type;
			}
			else if (type.equals(Type.getType(String.class))) {
				methodName = "readString";
				returnType = Type.getType(String.class);

				_assertMethodInsnNode(
					abstractInsnNode, Opcodes.INVOKEVIRTUAL,
					Type.getInternalName(Deserializer.class), "readString",
					Type.getType(String.class));
			}

			_assertMethodInsnNode(
				abstractInsnNode, Opcodes.INVOKEVIRTUAL,
				Type.getInternalName(Deserializer.class), methodName,
				returnType);
		}
	}

	@Test
	public void testExtractMethods() throws Exception {
		try {
			IntrabandProxyUtil.extractMethods(TestExtractMethodsClass1.class);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"The @Id annotated method " +
					TestExtractMethodsClass1.class.getMethod("getId") +
						" must not be static",
				iae.getMessage());
		}

		try {
			IntrabandProxyUtil.extractMethods(TestExtractMethodsClass2.class);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Method method = TestExtractMethodsClass2.class.getMethod(
				"getId", Object.class);

			Assert.assertEquals(
				"The @Id annotated method " + method +
					" must not have parameters",
				iae.getMessage());
		}

		try {
			IntrabandProxyUtil.extractMethods(TestExtractMethodsClass3.class);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"The @Id annotated method " +
					TestExtractMethodsClass3.class.getMethod("getId") +
						" must not return String",
				iae.getMessage());
		}

		try {
			IntrabandProxyUtil.extractMethods(TestExtractMethodsClass4.class);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				"Static proxy method violation for " +
					TestExtractMethodsClass4.class.getMethod("doStuff"),
				iae.getMessage());
		}

		MethodsBag methodsBag = IntrabandProxyUtil.extractMethods(
			TestExtractMethodsClass5.class);

		List<Method> idMethods = methodsBag.idMethods;

		Assert.assertEquals(2, idMethods.size());
		Assert.assertTrue(
			idMethods.contains(
				TestExtractMethodsClass5.class.getMethod("getId1")));
		Assert.assertTrue(
			idMethods.contains(
				TestExtractMethodsClass5.class.getMethod("getId2")));

		List<Method> proxyMethods = methodsBag.proxyMethods;

		Assert.assertEquals(2, proxyMethods.size());
		Assert.assertEquals(
			TestExtractMethodsClass5.class.getMethod("doStuff1"),
			proxyMethods.get(0));
		Assert.assertEquals(
			TestExtractMethodsClass5.class.getMethod("doStuff2"),
			proxyMethods.get(1));

		List<Method> emptyMethods = methodsBag.emptyMethods;

		Assert.assertEquals(1, emptyMethods.size());
		Assert.assertEquals(
			TestExtractMethodsClass5.class.getMethod("doStuff4"),
			emptyMethods.get(0));

		String[] proxyMethodSignatures = methodsBag.proxyMethodSignatures;

		Assert.assertEquals(2, proxyMethodSignatures.length);
		Assert.assertEquals("doStuff1-()V", proxyMethodSignatures[0]);
		Assert.assertEquals("doStuff2-()V", proxyMethodSignatures[1]);
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testGenerateSkeletonClassFunction() throws Exception {
		_doTestGenerateSkeletonClassFunction(TestGenerateInterface1.class);
		_doTestGenerateSkeletonClassFunction(TestGenerateInterface2.class);
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testGenerateSkeletonClassStructure() throws Exception {
		_doTestGenerateSkeletonClassStructure(TestGenerateInterface1.class);
		_doTestGenerateSkeletonClassStructure(TestGenerateInterface2.class);
		_doTestGenerateSkeletonClassStructure(TestGenerateClass1.class);
		_doTestGenerateSkeletonClassStructure(TestGenerateClass2.class);
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testGenerateStubClassFunction() throws Exception {

		// <clinit> and <init> appending

		String skeletonId = "skeletonId";

		Class<?> stubClass = IntrabandProxyUtil.generateStubClass(
			_classLoader, TestGenerateStubFunction1.class, skeletonId);

		Constructor<?> constructor = stubClass.getConstructor(
			String.class, RegistrationReference.class, ExceptionHandler.class);

		String testId = "testId";

		AutoReplyMockIntraband autoReplyMockIntraband =
			new AutoReplyMockIntraband(skeletonId, testId);

		RegistrationReference registrationReference =
			new MockRegistrationReference(autoReplyMockIntraband);

		Object stubObject = null;

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					stubClass.getName(), Level.INFO)) {

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			stubObject = constructor.newInstance(
				testId, registrationReference,
				WarnLogExceptionHandler.INSTANCE);

			Assert.assertEquals(2, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals(
				stubClass.getName() + " in <clinit>", logRecord.getMessage());

			logRecord = logRecords.get(1);

			Assert.assertEquals(
				stubClass.getName() + " in <init>", logRecord.getMessage());
		}

		Assert.assertSame(
			registrationReference,
			ReflectionTestUtil.getFieldValue(
				stubObject, "_registrationReference"));
		Assert.assertSame(
			WarnLogExceptionHandler.INSTANCE,
			ReflectionTestUtil.getFieldValue(stubObject, "_exceptionHandler"));
		Assert.assertSame(
			autoReplyMockIntraband,
			ReflectionTestUtil.getFieldValue(stubObject, "_intraband"));

		// Id methods

		stubClass = IntrabandProxyUtil.generateStubClass(
			_classLoader, TestGenerateStubFunction2.class, skeletonId);

		constructor = stubClass.getConstructor(
			String.class, RegistrationReference.class, ExceptionHandler.class);

		stubObject = constructor.newInstance(
			testId, registrationReference, WarnLogExceptionHandler.INSTANCE);

		for (Method idMethod : _getIdMethods(TestGenerateStubFunction2.class)) {
			Assert.assertEquals(
				testId,
				ReflectionTestUtil.invoke(
					stubObject, idMethod.getName(), new Class<?>[0]));
		}

		// Proxy methods

		List<Method> proxyMethods = _getProxyMethods(
			TestGenerateStubFunction2.class);

		for (int i = 0; i < proxyMethods.size(); i++) {
			Method proxyMethod = proxyMethods.get(i);

			Class<?>[] parameterTypes = proxyMethod.getParameterTypes();

			Object[] args = new Object[parameterTypes.length];

			for (int j = 0; j < args.length; j++) {
				args[j] = _sampleValueMap.get(parameterTypes[j]);
			}

			autoReplyMockIntraband.setInvocation(proxyMethod, i);

			Object object = ReflectionTestUtil.invoke(
				stubObject, proxyMethod.getName(),
				proxyMethod.getParameterTypes(), args);

			Assert.assertEquals(
				_sampleValueMap.get(proxyMethod.getReturnType()), object);
		}

		// Empty methods

		for (Method emptyMethod :
				_getEmptyMethods(TestGenerateStubFunction2.class)) {

			Assert.assertEquals(
				_defaultValueMap.get(emptyMethod.getReturnType()),
				ReflectionTestUtil.invoke(
					stubObject, emptyMethod.getName(), new Class<?>[0]));
		}

		// Copied methods

		List<Method> copiedMethods = _getCopiedMethods(
			TestGenerateStubFunction2.class);

		Collections.sort(
			copiedMethods,
			new Comparator<Method>() {

				@Override
				public int compare(Method method1, Method method2) {
					String name1 = method1.getName();
					String name2 = method2.getName();

					return name1.compareTo(name2);
				}

			});

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					stubClass.getName(), Level.INFO)) {

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			for (Method copiedMethod : copiedMethods) {
				ReflectionTestUtil.invoke(
					stubObject, copiedMethod.getName(), new Class<?>[0]);

				LogRecord logRecord = logRecords.get(logRecords.size() - 1);

				Assert.assertEquals(
					copiedMethod.getName(), logRecord.getMessage());
			}
		}
	}

	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testGenerateStubClassStructure() throws Exception {
		_doTestGenerateStubClassStructure(
			TestGenerateInterface1.class, "skeletonId");
		_doTestGenerateStubClassStructure(
			TestGenerateInterface2.class, "skeletonId");
		_doTestGenerateStubClassStructure(
			TestGenerateClass1.class, "skeletonId");
		_doTestGenerateStubClassStructure(
			TestGenerateClass2.class, "skeletonId");
	}

	@Test
	public void testGetClass() throws Exception {

		// getSkeletonClass()

		class TestClass {
		}

		ClassLoader classLoader = TestClass.class.getClassLoader();

		_doTestGetClass(
			classLoader,
			new MethodHandler(
				IntrabandProxyUtil.class.getDeclaredMethod(
					"getSkeletonClass", ClassLoader.class, Class.class),
				classLoader, TestClass.class));

		// getStubClass()

		_doTestGetClass(
			classLoader,
			new MethodHandler(
				IntrabandProxyUtil.class.getMethod(
					"getStubClass", Class.class, String.class),
				TestClass.class, "skeletonId"));
	}

	@Test
	public void testGetProxyMethodSignatures() {
		class TestClass {
		}

		Assert.assertNull(
			IntrabandProxyUtil.getProxyMethodSignatures(TestClass.class));
	}

	@AdviseWith(adviceClasses = {ReflectionUtilAdvice.class})
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testInitializationFailure() throws ClassNotFoundException {
		Throwable throwable = new Throwable();

		ReflectionUtilAdvice.setDeclaredMethodThrowable(throwable);

		try {
			new IntrabandProxyUtil();

			Assert.fail();
		}
		catch (ExceptionInInitializerError eiie) {
			Assert.assertSame(throwable, eiie.getCause());
		}
	}

	@Test
	public void testLoadClass() {
		Assert.assertNull(
			IntrabandProxyUtil.loadClass(_classLoader, Thread.class, "Proxy"));
		Assert.assertSame(
			ThreadLocal.class,
			IntrabandProxyUtil.loadClass(_classLoader, Thread.class, "Local"));
	}

	@Test
	public void testNewStubInstance() {
		Class<?> stubClass = IntrabandProxyUtil.getStubClass(
			TestGenerateInterface1.class, "skeletonId");

		IntrabandProxyUtil.newStubInstance(
			stubClass, "id", new MockRegistrationReference(null),
			WarnLogExceptionHandler.INSTANCE);

		try {
			IntrabandProxyUtil.newStubInstance(
				stubClass, "id", null, WarnLogExceptionHandler.INSTANCE);

			Assert.fail();
		}
		catch (RuntimeException re) {
			Throwable throwable = re.getCause();

			throwable = throwable.getCause();

			Assert.assertSame(NullPointerException.class, throwable.getClass());
			Assert.assertEquals(
				"Registration reference is null", throwable.getMessage());
		}

		stubClass = IntrabandProxyUtil.getStubClass(
			TestGenerateInterface2.class, "skeletonId");

		try {
			IntrabandProxyUtil.newStubInstance(
				stubClass, "id", null, WarnLogExceptionHandler.INSTANCE);

			Assert.fail();
		}
		catch (RuntimeException re) {
			Throwable throwable = re.getCause();

			throwable = throwable.getCause();

			Assert.assertSame(NullPointerException.class, throwable.getClass());
			Assert.assertEquals(
				"Registration reference is null", throwable.getMessage());
		}

		IntrabandProxyUtil.newStubInstance(
			stubClass, "id", new MockRegistrationReference(null),
			WarnLogExceptionHandler.INSTANCE);
	}

	@Test
	public void testRewriteGetProxyMethodSignaturesMethodNode() {
		class TestClass {

			@SuppressWarnings("unused")
			public final String[] PROXY_METHOD_SIGNATURES =
				_getProxyMethodSignatures();

			private String[] _getProxyMethodSignatures() {
				return new String[0];
			}

		}

		ClassNode classNode = _loadClass(TestClass.class);

		String[] proxyMethodSignatures =
			{"testSignature1", "testSignature2", "testSignature3"};

		IntrabandProxyUtil.rewriteGetProxyMethodSignaturesMethodNode(
			classNode, proxyMethodSignatures);

		MethodNode methodNode = ASMUtil.findMethodNode(
			classNode.methods, "_getProxyMethodSignatures",
			Type.getType(String[].class));

		InsnList insnList = methodNode.instructions;

		Iterator<AbstractInsnNode> iterator = insnList.iterator();

		_assertInsnNode(iterator.next(), Opcodes.ICONST_3);

		_assertTypeInsnNode(iterator.next(), Opcodes.ANEWARRAY, String.class);

		for (int i = 0; i < proxyMethodSignatures.length; i++) {
			_assertInsnNode(iterator.next(), Opcodes.DUP);
			_assertInsnNode(iterator.next(), Opcodes.ICONST_0 + i);
			_assertLdcInsnNode(
				iterator.next(), Opcodes.LDC, proxyMethodSignatures[i]);
			_assertInsnNode(iterator.next(), Opcodes.AASTORE);
		}

		_assertInsnNode(iterator.next(), Opcodes.ARETURN);

		Assert.assertFalse(iterator.hasNext());
	}

	@Test
	public void testSerializerWrite() {
		MethodNode methodNode = new MethodNode(
			Opcodes.ACC_PUBLIC, "name", "()V", null, null);

		MethodNodeGenerator methodNodeGenerator = new MethodNodeGenerator(
			methodNode);

		InsnList insnList = methodNode.instructions;

		for (Type type : _types) {
			IntrabandProxyUtil.serializerWrite(methodNodeGenerator, type);

			AbstractInsnNode abstractInsnNode = insnList.getLast();

			Assert.assertTrue(abstractInsnNode instanceof MethodInsnNode);

			MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;

			Assert.assertEquals(
				Opcodes.INVOKEVIRTUAL, abstractInsnNode.getOpcode());
			Assert.assertEquals(
				Type.getInternalName(Serializer.class), methodInsnNode.owner);

			if (type.getSort() <= Type.DOUBLE) {
				String name = TextFormatter.format(
					type.getClassName(), TextFormatter.G);

				Assert.assertEquals("write".concat(name), methodInsnNode.name);
				Assert.assertEquals(
					Type.getMethodDescriptor(Type.VOID_TYPE, type),
					methodInsnNode.desc);
			}
			else if (type.equals(Type.getType(String.class))) {
				Assert.assertEquals("writeString", methodInsnNode.name);
				Assert.assertEquals(
					Type.getMethodDescriptor(
						Type.VOID_TYPE, Type.getType(String.class)),
					methodInsnNode.desc);
			}
			else {
				Assert.assertEquals("writeObject", methodInsnNode.name);
				Assert.assertEquals(
					Type.getMethodDescriptor(
						Type.VOID_TYPE, Type.getType(Serializable.class)),
					methodInsnNode.desc);
			}
		}
	}

	@Test
	public void testTemplateSkeleton() throws ClassNotFoundException {
		class TestTemplateSkeleton extends TemplateSkeleton {

			TestTemplateSkeleton(TargetLocator targetLocator) {
				super(targetLocator);
			}

			@Override
			protected void doDispatch(
				RegistrationReference registrationReference, Datagram datagram,
				Deserializer deserializer) {

				int i = deserializer.readInt();

				if (i == 0) {
					ReflectionTestUtil.invoke(
						this, "_sendResponse",
						new Class<?>[] {
							RegistrationReference.class, Datagram.class,
							RPCResponse.class
						},
						registrationReference, datagram,
						new RPCResponse("syncCall"));
				}
				else if (i == 1) {
					ReflectionTestUtil.invoke(
						this, "_unknownMethodIndex", new Class<?>[] {int.class},
						1);
				}
			}

		}

		try {
			new TestTemplateSkeleton(null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Target locator is null", npe.getMessage());
		}

		TestGenerateTargetLocator testGenerateTargetLocator =
			new TestGenerateTargetLocator(TestGenerateInterface1.class);

		TestTemplateSkeleton testTemplateSkeleton = new TestTemplateSkeleton(
			testGenerateTargetLocator);

		Assert.assertSame(
			testGenerateTargetLocator,
			ReflectionTestUtil.getFieldValue(
				testTemplateSkeleton, "_targetLocator"));

		MockIntraband mockIntraband = new MockIntraband();

		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(mockIntraband);

		Serializer serializer = new Serializer();

		serializer.writeInt(0);

		testTemplateSkeleton.dispatch(
			mockRegistrationReference,
			Datagram.createRequestDatagram(
				SystemDataType.PROXY.getValue(), new byte[0]),
			new Deserializer(serializer.toByteBuffer()));

		Datagram datagram = mockIntraband.getDatagram();

		Deserializer deserializer = new Deserializer(
			datagram.getDataByteBuffer());

		RPCResponse rpcResponse = deserializer.readObject();

		Assert.assertEquals("syncCall", rpcResponse.getResult());

		serializer = new Serializer();

		serializer.writeInt(1);

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					TemplateSkeleton.class.getName(), Level.SEVERE)) {

			testTemplateSkeleton.dispatch(
				mockRegistrationReference,
				Datagram.createRequestDatagram(
					SystemDataType.PROXY.getValue(), new byte[0]),
				new Deserializer(serializer.toByteBuffer()));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Assert.assertEquals("Unable to dispatch", logRecord.getMessage());

			Throwable throwable = logRecord.getThrown();

			Assert.assertSame(
				IllegalArgumentException.class, throwable.getClass());
			Assert.assertEquals(
				"Unknow method index 1 for proxy methods mappings {}",
				throwable.getMessage());
		}

		Assert.assertEquals(
			"{0 -> a, 1 -> b, 2 -> c}",
			ReflectionTestUtil.invoke(
				testTemplateSkeleton, "_getProxyMethodsMapping",
				new Class<?>[] {String[].class},
				new Object[] {new String[] {"a", "b", "c"}}));
	}

	@Test
	public void testTemplateStub() {
		try {
			new TemplateStub(null, null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals("Id is null", npe.getMessage());
		}

		try {
			new TemplateStub("id", null, null);

			Assert.fail();
		}
		catch (NullPointerException npe) {
			Assert.assertEquals(
				"Registration reference is null", npe.getMessage());
		}

		final AtomicReference<RPCResponse> rpcResponseReference =
			new AtomicReference<>();

		MockIntraband mockIntraband = new MockIntraband() {

			@Override
			protected Datagram processDatagram(Datagram datagram) {
				Serializer serializer = new Serializer();

				serializer.writeObject(rpcResponseReference.get());

				return Datagram.createResponseDatagram(
					datagram, serializer.toByteBuffer());
			}

		};

		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(mockIntraband);

		TemplateStub templateStub = new TemplateStub(
			"id", mockRegistrationReference, null);

		Assert.assertEquals(
			"id", ReflectionTestUtil.getFieldValue(templateStub, "_id"));
		Assert.assertSame(
			mockRegistrationReference,
			ReflectionTestUtil.getFieldValue(
				templateStub, "_registrationReference"));
		Assert.assertNull(
			ReflectionTestUtil.getFieldValue(
				templateStub, "_exceptionHandler"));
		Assert.assertSame(
			mockIntraband,
			ReflectionTestUtil.getFieldValue(templateStub, "_intraband"));

		templateStub = new TemplateStub(
			"id", mockRegistrationReference, WarnLogExceptionHandler.INSTANCE);

		Assert.assertEquals(
			"id", ReflectionTestUtil.getFieldValue(templateStub, "_id"));
		Assert.assertSame(
			mockRegistrationReference,
			ReflectionTestUtil.getFieldValue(
				templateStub, "_registrationReference"));
		Assert.assertSame(
			WarnLogExceptionHandler.INSTANCE,
			ReflectionTestUtil.getFieldValue(
				templateStub, "_exceptionHandler"));
		Assert.assertSame(
			mockIntraband,
			ReflectionTestUtil.getFieldValue(templateStub, "_intraband"));

		ReflectionTestUtil.invoke(
			templateStub, "_send", new Class<?>[] {Serializer.class},
			new Serializer());

		Assert.assertSame(
			mockRegistrationReference,
			mockIntraband.getRegistrationReference());

		Datagram datagram = mockIntraband.getDatagram();

		Assert.assertEquals(
			SystemDataType.PROXY.getValue(), datagram.getType());

		rpcResponseReference.set(new RPCResponse("syncSend"));

		Assert.assertEquals(
			"syncSend",
			ReflectionTestUtil.invoke(
				templateStub, "_syncSend", new Class<?>[] {Serializer.class},
				new Serializer()));

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					WarnLogExceptionHandler.class.getName(), Level.WARNING)) {

			String message = "RPC failure";

			rpcResponseReference.set(new RPCResponse(new Exception(message)));

			Assert.assertNull(
				ReflectionTestUtil.invoke(
					templateStub, "_syncSend",
					new Class<?>[] {Serializer.class}, new Serializer()));

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			Assert.assertEquals(1, logRecords.size());

			LogRecord logRecord = logRecords.get(0);

			Throwable throwable = logRecord.getThrown();

			Assert.assertEquals(message, throwable.getMessage());

			logRecords.clear();

			rpcResponseReference.set(new RPCResponse((Serializable)null));

			Assert.assertNull(
				ReflectionTestUtil.invoke(
					templateStub, "_syncSend",
					new Class<?>[] {Serializer.class}, new Serializer()));

			Assert.assertTrue(logRecords.isEmpty());

			rpcResponseReference.set(null);

			ReflectionTestUtil.setFieldValue(
				templateStub, "_exceptionHandler", null);

			Assert.assertNull(
				ReflectionTestUtil.invoke(
					templateStub, "_syncSend",
					new Class<?>[] {Serializer.class}, new Serializer()));
			Assert.assertTrue(logRecords.isEmpty());
		}
	}

	@AdviseWith(adviceClasses = {DisableProxyClassesDump.class})
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testToClassProxyClassesDumpDisabled()
		throws FileNotFoundException {

		_doTestToClass(false, false);
	}

	@AdviseWith(adviceClasses = {EnableProxyClassesDump.class})
	@NewEnv(type = NewEnv.Type.CLASSLOADER)
	@Test
	public void testToClassProxyClassesDumpEnabled()
		throws FileNotFoundException {

		_doTestToClass(true, true);
		_doTestToClass(true, false);
	}

	@Test
	public void testValidate() throws Exception {
		_doTestValidate(true);
		_doTestValidate(false);
	}

	@Aspect
	public static class DisableProxyClassesDump {

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"INTRABAND_PROXY_DUMP_CLASSES_ENABLED)"
		)
		public Object disableClusterLink(
				ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.FALSE});
		}

	}

	@Aspect
	public static class EnableProxyClassesDump {

		@Around(
			"set(* com.liferay.portal.util.PropsValues." +
				"INTRABAND_PROXY_DUMP_CLASSES_ENABLED)"
		)
		public Object enableClusterLink(ProceedingJoinPoint proceedingJoinPoint)
			throws Throwable {

			return proceedingJoinPoint.proceed(new Object[] {Boolean.TRUE});
		}

	}

	private static Object _readFromDeserializer(
		Deserializer deserializer, Class<?> clazz) {

		if (clazz == boolean.class) {
			return deserializer.readBoolean();
		}
		else if (clazz == byte.class) {
			return deserializer.readByte();
		}
		else if (clazz == char.class) {
			return deserializer.readChar();
		}
		else if (clazz == double.class) {
			return deserializer.readDouble();
		}
		else if (clazz == float.class) {
			return deserializer.readFloat();
		}
		else if (clazz == int.class) {
			return deserializer.readInt();
		}
		else if (clazz == long.class) {
			return deserializer.readLong();
		}
		else if (clazz == short.class) {
			return deserializer.readShort();
		}
		else if (clazz == String.class) {
			return deserializer.readString();
		}
		else {
			try {
				return deserializer.readObject();
			}
			catch (ClassNotFoundException cnfe) {
				throw new RuntimeException(cnfe);
			}
		}
	}

	private static void _writeToSerializer(
		Serializer serializer, Class<?> clazz) {

		if (clazz == boolean.class) {
			serializer.writeBoolean(
				(Boolean)_sampleValueMap.get(boolean.class));
		}
		else if (clazz == byte.class) {
			serializer.writeByte((Byte)_sampleValueMap.get(byte.class));
		}
		else if (clazz == char.class) {
			serializer.writeChar((Character)_sampleValueMap.get(char.class));
		}
		else if (clazz == double.class) {
			serializer.writeDouble((Double)_sampleValueMap.get(double.class));
		}
		else if (clazz == float.class) {
			serializer.writeFloat((Float)_sampleValueMap.get(float.class));
		}
		else if (clazz == int.class) {
			serializer.writeInt((Integer)_sampleValueMap.get(int.class));
		}
		else if (clazz == long.class) {
			serializer.writeLong((Long)_sampleValueMap.get(long.class));
		}
		else if (clazz == short.class) {
			serializer.writeShort((Short)_sampleValueMap.get(short.class));
		}
		else if (clazz == String.class) {
			serializer.writeString((String)_sampleValueMap.get(String.class));
		}
		else {
			serializer.writeObject((Serializable)_sampleValueMap.get(clazz));
		}
	}

	private Field _assertDeclaredField(
			Class<?> clazz, String name, int modifiers, Class<?> type)
		throws Exception {

		Field field = clazz.getDeclaredField(name);

		field.setAccessible(true);

		Assert.assertEquals(modifiers, field.getModifiers());
		Assert.assertSame(type, field.getType());

		return field;
	}

	private void _assertDeclaredMethod(
			Class<?> clazz, String name, Class<?>[] parameterTypes,
			int modifiers, Class<?> returnType, Class<?>... exceptionTypes)
		throws Exception {

		Method method = clazz.getDeclaredMethod(name, parameterTypes);

		Assert.assertEquals(modifiers, method.getModifiers());
		Assert.assertSame(returnType, method.getReturnType());
		Assert.assertArrayEquals(exceptionTypes, method.getExceptionTypes());
	}

	private void _assertFieldInsnNode(
		AbstractInsnNode abstractInsnNode, int opcode, String owner,
		String name, Class<?> clazz) {

		Assert.assertEquals(opcode, abstractInsnNode.getOpcode());

		FieldInsnNode fieldInsnNode = (FieldInsnNode)abstractInsnNode;

		Assert.assertEquals(owner, fieldInsnNode.owner);
		Assert.assertEquals(name, fieldInsnNode.name);
		Assert.assertEquals(Type.getDescriptor(clazz), fieldInsnNode.desc);
	}

	private void _assertInsnNode(
		AbstractInsnNode abstractInsnNode, int opcode) {

		Assert.assertEquals(opcode, abstractInsnNode.getOpcode());
	}

	private void _assertIntInsnNode(
		AbstractInsnNode abstractInsnNode, int opcode, int operand) {

		Assert.assertEquals(opcode, abstractInsnNode.getOpcode());

		IntInsnNode intInsnNode = (IntInsnNode)abstractInsnNode;

		Assert.assertEquals(operand, intInsnNode.operand);
	}

	private LabelNode _assertJumpInsnNode(
		AbstractInsnNode abstractInsnNode, int opcode) {

		Assert.assertEquals(opcode, abstractInsnNode.getOpcode());

		JumpInsnNode jumpInsnNode = (JumpInsnNode)abstractInsnNode;

		return jumpInsnNode.label;
	}

	private void _assertLdcInsnNode(
		AbstractInsnNode abstractInsnNode, int opcode, Object obj) {

		Assert.assertEquals(opcode, abstractInsnNode.getOpcode());

		LdcInsnNode ldcInsnNode = (LdcInsnNode)abstractInsnNode;

		Assert.assertEquals(obj, ldcInsnNode.cst);
	}

	private void _assertMethodInsnNode(
		AbstractInsnNode abstractInsnNode, int opcode, String owner,
		String name, Type returnType, Type... argumentTypes) {

		Assert.assertEquals(opcode, abstractInsnNode.getOpcode());

		MethodInsnNode methodInsnNode = (MethodInsnNode)abstractInsnNode;

		Assert.assertEquals(owner, methodInsnNode.owner);
		Assert.assertEquals(name, methodInsnNode.name);
		Assert.assertEquals(
			Type.getMethodDescriptor(returnType, argumentTypes),
			methodInsnNode.desc);
	}

	private void _assertMethodNodeSignature(
		MethodNode methodNode, int access, String name, String desc,
		Class<?>... exceptionClasses) {

		Assert.assertEquals(access, methodNode.access);
		Assert.assertEquals(name, methodNode.name);
		Assert.assertEquals(desc, methodNode.desc);

		List<String> exceptions = new ArrayList<>(exceptionClasses.length);

		for (Class<?> exceptionClass : exceptionClasses) {
			exceptions.add(Type.getInternalName(exceptionClass));
		}

		Assert.assertEquals(exceptions, methodNode.exceptions);
	}

	private void _assertTypeInsnNode(
		AbstractInsnNode abstractInsnNode, int opcode, Class<?> clazz) {

		Assert.assertEquals(opcode, abstractInsnNode.getOpcode());

		TypeInsnNode typeInsnNode = (TypeInsnNode)abstractInsnNode;

		Assert.assertEquals(Type.getInternalName(clazz), typeInsnNode.desc);
	}

	private void _assertVarInsnNode(
		AbstractInsnNode abstractInsnNode, int opcode, int var) {

		Assert.assertEquals(opcode, abstractInsnNode.getOpcode());

		VarInsnNode varInsnNode = (VarInsnNode)abstractInsnNode;

		Assert.assertEquals(var, varInsnNode.var);
	}

	private String[] _buildProxyMethodSignatures(Class<?> clazz) {
		List<Method> proxyMethods = new ArrayList<>();

		for (Method method : ReflectionUtil.getVisibleMethods(clazz)) {
			if (method.getAnnotation(Proxy.class) != null) {
				proxyMethods.add(method);
			}
		}

		Collections.sort(proxyMethods, new MethodComparator());

		String[] proxyMethodSignatures = new String[proxyMethods.size()];

		for (int i = 0; i < proxyMethodSignatures.length; i++) {
			Method proxyMethod = proxyMethods.get(i);

			String name = proxyMethod.getName();

			proxyMethodSignatures[i] = name.concat(StringPool.DASH).concat(
				Type.getMethodDescriptor(proxyMethod));
		}

		return proxyMethodSignatures;
	}

	private void _doTestCreateProxyMethodNode(
		Method method, int index, String skeletonId, String stubInternalName) {

		MethodNode proxyMethodNode = IntrabandProxyUtil.createProxyMethodNode(
			method, index, skeletonId, Type.getType(stubInternalName));

		_assertMethodNodeSignature(
			proxyMethodNode, method.getModifiers() & ~Modifier.ABSTRACT,
			method.getName(), Type.getMethodDescriptor(method),
			method.getExceptionTypes());

		InsnList insnList = proxyMethodNode.instructions;

		Iterator<AbstractInsnNode> iterator = insnList.iterator();

		// NEW com/liferay/portal/kernel/io/Serializer

		_assertTypeInsnNode(iterator.next(), Opcodes.NEW, Serializer.class);

		// DUP

		_assertInsnNode(iterator.next(), Opcodes.DUP);

		// INVOKESPECIAL com/liferay/portal/kernel/io/Serializer <init> ()V

		_assertMethodInsnNode(
			iterator.next(), Opcodes.INVOKESPECIAL,
			Type.getInternalName(Serializer.class), "<init>", Type.VOID_TYPE);

		// ASTORE argumentsSize

		Type methodType = Type.getType(method);

		int argumentsAndReturnSizes = methodType.getArgumentsAndReturnSizes();

		int argumentsSize = argumentsAndReturnSizes >> 2;

		_assertVarInsnNode(iterator.next(), Opcodes.ASTORE, argumentsSize);

		// ALOAD argumentsSize

		_assertVarInsnNode(iterator.next(), Opcodes.ALOAD, argumentsSize);

		// LDC skeletonId

		_assertLdcInsnNode(iterator.next(), Opcodes.LDC, skeletonId);

		// INVOKEVIRTUAL com/liferay/portal/kernel/io/Serializer writeString
		// (Ljava/lang/String;)V

		_assertMethodInsnNode(
			iterator.next(), Opcodes.INVOKEVIRTUAL,
			Type.getInternalName(Serializer.class), "writeString",
			Type.VOID_TYPE, Type.getType(String.class));

		// ALOAD argumentsSize

		_assertVarInsnNode(iterator.next(), Opcodes.ALOAD, argumentsSize);

		// ALOAD 0

		_assertVarInsnNode(iterator.next(), Opcodes.ALOAD, 0);

		// GETFIELD stubInternalName _id Ljava/lang/String;

		_assertFieldInsnNode(
			iterator.next(), Opcodes.GETFIELD, stubInternalName, "_id",
			String.class);

		// INVOKEVIRTUAL com/liferay/portal/kernel/io/Serializer writeString
		// (Ljava/lang/String;)V

		_assertMethodInsnNode(
			iterator.next(), Opcodes.INVOKEVIRTUAL,
			Type.getInternalName(Serializer.class), "writeString",
			Type.VOID_TYPE, Type.getType(String.class));

		// ALOAD argumentsSize

		_assertVarInsnNode(iterator.next(), Opcodes.ALOAD, argumentsSize);

		if (index <= 5) {

			// ICONST_index

			_assertInsnNode(iterator.next(), Opcodes.ICONST_0 + index);
		}
		else {

			// BIPUSH index

			_assertIntInsnNode(iterator.next(), Opcodes.BIPUSH, index);
		}

		// INVOKEVIRTUAL com/liferay/portal/kernel/io/Serializer writeInt (I)V

		_assertMethodInsnNode(
			iterator.next(), Opcodes.INVOKEVIRTUAL,
			Type.getInternalName(Serializer.class), "writeInt", Type.VOID_TYPE,
			Type.INT_TYPE);

		Class<?>[] parameterTypes = method.getParameterTypes();

		int offset = 1;

		for (int i = 0; i < parameterTypes.length; i++) {
			Class<?> parameterClass = parameterTypes[i];

			// ALOAD argumentsSize

			_assertVarInsnNode(iterator.next(), Opcodes.ALOAD, argumentsSize);

			// xLOAD i

			Type parameterType = Type.getType(parameterClass);

			_assertVarInsnNode(
				iterator.next(), parameterType.getOpcode(Opcodes.ILOAD),
				offset);

			offset += parameterType.getSize();

			if (parameterClass.isPrimitive() ||
				(parameterClass == String.class)) {

				String name = TextFormatter.format(
					parameterClass.getSimpleName(), TextFormatter.G);

				_assertMethodInsnNode(
					iterator.next(), Opcodes.INVOKEVIRTUAL,
					Type.getInternalName(Serializer.class),
					"write".concat(name), Type.VOID_TYPE, parameterType);
			}
			else {
				_assertMethodInsnNode(
					iterator.next(), Opcodes.INVOKEVIRTUAL,
					Type.getInternalName(Serializer.class), "writeObject",
					Type.VOID_TYPE, Type.getType(Serializable.class));
			}
		}

		// ALOAD 0

		_assertVarInsnNode(iterator.next(), Opcodes.ALOAD, 0);

		// ALOAD argumentsSize

		_assertVarInsnNode(iterator.next(), Opcodes.ALOAD, argumentsSize);

		Class<?> returnClass = method.getReturnType();

		Type returnType = Type.getType(returnClass);

		if (returnClass == void.class) {

			// INVOKESPECIAL stubInternalName _send
			// (Lcom/liferay/portal/kernel/io/Serializer;)V

			_assertMethodInsnNode(
				iterator.next(), Opcodes.INVOKESPECIAL, stubInternalName,
				"_send", Type.VOID_TYPE, Type.getType(Serializer.class));

			_assertInsnNode(iterator.next(), Opcodes.RETURN);
		}
		else {

			// INVOKESPECIAL stubInternalName _syncSend
			// (Lcom/liferay/portal/kernel/io/Serializer;)Ljava/io/Serializable;

			_assertMethodInsnNode(
				iterator.next(), Opcodes.INVOKESPECIAL, stubInternalName,
				"_syncSend", Type.getType(Serializable.class),
				Type.getType(Serializer.class));

			if (returnClass.isPrimitive()) {

				// ASTORE argumentsSize + 1

				_assertVarInsnNode(
					iterator.next(), Opcodes.ASTORE, argumentsSize + 1);

				// ALOAD argumentsSize + 1

				_assertVarInsnNode(
					iterator.next(), Opcodes.ALOAD, argumentsSize + 1);

				// IFNULL nullCheckLabel

				LabelNode nullCheckLabelNode = _assertJumpInsnNode(
					iterator.next(), Opcodes.IFNULL);

				// ALOAD argumentsSize + 1

				_assertVarInsnNode(
					iterator.next(), Opcodes.ALOAD, argumentsSize + 1);

				// CHECKCAST returnType

				_assertTypeInsnNode(
					iterator.next(), Opcodes.CHECKCAST,
					_autoboxingMap.get(returnClass));

				if (returnClass == boolean.class) {

					// INVOKEVIRTUAL java/lang/Boolean booleanValue ()Z

					_assertMethodInsnNode(
						iterator.next(), Opcodes.INVOKEVIRTUAL,
						Type.getInternalName(Boolean.class), "booleanValue",
						Type.BOOLEAN_TYPE);
				}
				else if (returnClass == byte.class) {

					// INVOKEVIRTUAL java/lang/Number intValue ()I

					_assertMethodInsnNode(
						iterator.next(), Opcodes.INVOKEVIRTUAL,
						Type.getInternalName(Number.class), "intValue",
						Type.INT_TYPE);
				}
				else if (returnClass == char.class) {

					// INVOKEVIRTUAL java/lang/Character charValue ()C

					_assertMethodInsnNode(
						iterator.next(), Opcodes.INVOKEVIRTUAL,
						Type.getInternalName(Character.class), "charValue",
						Type.CHAR_TYPE);
				}
				else if (returnClass == double.class) {

					// INVOKEVIRTUAL java/lang/Number doubleValue ()D

					_assertMethodInsnNode(
						iterator.next(), Opcodes.INVOKEVIRTUAL,
						Type.getInternalName(Number.class), "doubleValue",
						Type.DOUBLE_TYPE);
				}
				else if (returnClass == float.class) {

					// INVOKEVIRTUAL java/lang/Number floatValue ()F

					_assertMethodInsnNode(
						iterator.next(), Opcodes.INVOKEVIRTUAL,
						Type.getInternalName(Number.class), "floatValue",
						Type.FLOAT_TYPE);
				}
				else if (returnClass == int.class) {

					// INVOKEVIRTUAL java/lang/Number intValue ()I

					_assertMethodInsnNode(
						iterator.next(), Opcodes.INVOKEVIRTUAL,
						Type.getInternalName(Number.class), "intValue",
						Type.INT_TYPE);
				}
				else if (returnClass == long.class) {

					// INVOKEVIRTUAL java/lang/Number longValue ()J

					_assertMethodInsnNode(
						iterator.next(), Opcodes.INVOKEVIRTUAL,
						Type.getInternalName(Number.class), "longValue",
						Type.LONG_TYPE);
				}
				else if (returnClass == short.class) {

					// INVOKEVIRTUAL java/lang/Number intValue ()I

					_assertMethodInsnNode(
						iterator.next(), Opcodes.INVOKEVIRTUAL,
						Type.getInternalName(Number.class), "intValue",
						Type.INT_TYPE);
				}

				// xRETURN

				_assertInsnNode(
					iterator.next(), returnType.getOpcode(Opcodes.IRETURN));

				// nullCheckLabel

				Assert.assertSame(nullCheckLabelNode, iterator.next());

				// xRETURN null/0

				if (!returnClass.isPrimitive()) {
					_assertInsnNode(iterator.next(), Opcodes.ACONST_NULL);
					_assertInsnNode(iterator.next(), Opcodes.ARETURN);
				}
				else if (returnClass == void.class) {
					_assertInsnNode(iterator.next(), Opcodes.RETURN);
				}
				else if (returnClass == float.class) {
					_assertInsnNode(iterator.next(), Opcodes.FCONST_0);
					_assertInsnNode(iterator.next(), Opcodes.FRETURN);
				}
				else if (returnClass == double.class) {
					_assertInsnNode(iterator.next(), Opcodes.DCONST_0);
					_assertInsnNode(iterator.next(), Opcodes.DRETURN);
				}
				else if (returnClass == long.class) {
					_assertInsnNode(iterator.next(), Opcodes.LCONST_0);
					_assertInsnNode(iterator.next(), Opcodes.LRETURN);
				}
				else {
					_assertInsnNode(iterator.next(), Opcodes.ICONST_0);
					_assertInsnNode(iterator.next(), Opcodes.IRETURN);
				}
			}
			else {
				if (returnClass != Object.class) {

					// CHECKCAST

					_assertTypeInsnNode(
						iterator.next(), Opcodes.CHECKCAST, returnClass);
				}

				// ARETURN

				_assertInsnNode(iterator.next(), Opcodes.ARETURN);
			}
		}

		Assert.assertFalse(iterator.hasNext());
	}

	private void _doTestGenerateSkeletonClassFunction(Class<?> clazz)
		throws Exception {

		Class<? extends IntrabandProxySkeleton> skeletonClass =
			IntrabandProxyUtil.generateSkeletonClass(_classLoader, clazz);

		Constructor<? extends IntrabandProxySkeleton> constructor =
			skeletonClass.getConstructor(TargetLocator.class);

		TestGenerateTargetLocator testGenerateTargetLocator =
			new TestGenerateTargetLocator(clazz);

		IntrabandProxySkeleton intrabandProxySkeleton = constructor.newInstance(
			testGenerateTargetLocator);

		Assert.assertSame(
			testGenerateTargetLocator,
			ReflectionTestUtil.getFieldValue(
				intrabandProxySkeleton, "_targetLocator"));

		MockIntraband mockIntraband = new MockIntraband();

		MockRegistrationReference mockRegistrationReference =
			new MockRegistrationReference(mockIntraband);

		Datagram datagram = Datagram.createRequestDatagram(
			SystemDataType.PROXY.getValue(), new byte[0]);

		String targetId = "targetId";

		List<Method> proxyMethods = _getProxyMethods(clazz);

		for (int i = 0; i < proxyMethods.size() + 1; i++) {
			Serializer serializer = new Serializer();

			serializer.writeString(targetId);
			serializer.writeInt(i);

			if (i == proxyMethods.size()) {
				try (CaptureHandler captureHandler =
						JDKLoggerTestUtil.configureJDKLogger(
							skeletonClass.getName(), Level.SEVERE)) {

					intrabandProxySkeleton.dispatch(
						mockRegistrationReference, datagram,
						new Deserializer(serializer.toByteBuffer()));

					List<LogRecord> logRecords = captureHandler.getLogRecords();

					Assert.assertEquals(1, logRecords.size());

					LogRecord logRecord = logRecords.get(0);

					Assert.assertEquals(
						"Unable to dispatch", logRecord.getMessage());

					Throwable throwable = logRecord.getThrown();

					Assert.assertSame(
						IllegalArgumentException.class, throwable.getClass());
					Assert.assertEquals(
						"Unknow method index " + i +
							" for proxy methods mappings " +
								ReflectionTestUtil.getFieldValue(
									skeletonClass, "_PROXY_METHODS_MAPPING"),
						throwable.getMessage());
				}

				break;
			}

			Method proxyMethod = proxyMethods.get(i);

			for (Class<?> parameterType : proxyMethod.getParameterTypes()) {
				_writeToSerializer(serializer, parameterType);
			}

			Deserializer deserializer = new Deserializer(
				serializer.toByteBuffer());

			intrabandProxySkeleton.dispatch(
				mockRegistrationReference, datagram, deserializer);

			Class<?> returnType = proxyMethod.getReturnType();

			if (returnType == void.class) {
				Assert.assertNull(mockIntraband.getDatagram());
				Assert.assertNull(mockIntraband.getRegistrationReference());
			}
			else {
				Datagram responseDatagram = mockIntraband.getDatagram();

				deserializer = new Deserializer(
					responseDatagram.getDataByteBuffer());

				RPCResponse rpcResponse = deserializer.readObject();

				Assert.assertEquals(
					_sampleValueMap.get(returnType), rpcResponse.getResult());
				Assert.assertSame(
					mockRegistrationReference,
					mockIntraband.getRegistrationReference());
			}
		}
	}

	private void _doTestGenerateSkeletonClassStructure(Class<?> clazz)
		throws Exception {

		Class<?> skeletonClass = IntrabandProxyUtil.generateSkeletonClass(
			_classLoader, clazz);

		// Class signature

		Assert.assertEquals(Modifier.PUBLIC, skeletonClass.getModifiers());
		Assert.assertArrayEquals(
			new Class<?>[] {IntrabandProxySkeleton.class},
			skeletonClass.getInterfaces());

		// Fields

		_assertDeclaredField(
			skeletonClass, "PROXY_METHOD_SIGNATURES",
			Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL, String[].class);

		String[] proxyMethodSignatures =
			IntrabandProxyUtil.getProxyMethodSignatures(skeletonClass);

		Assert.assertArrayEquals(
			_buildProxyMethodSignatures(clazz), proxyMethodSignatures);

		StringBundler sb = new StringBundler(
			proxyMethodSignatures.length * 4 + 1);

		sb.append(StringPool.OPEN_CURLY_BRACE);

		for (int i = 0; i < proxyMethodSignatures.length; i++) {
			sb.append(i);
			sb.append(" -> ");
			sb.append(proxyMethodSignatures[i]);
			sb.append(StringPool.COMMA_AND_SPACE);
		}

		if (proxyMethodSignatures.length > 0) {
			sb.setIndex(sb.index() - 1);
		}

		sb.append(StringPool.CLOSE_CURLY_BRACE);

		Field proxyMethodsMappingField = _assertDeclaredField(
			skeletonClass, "_PROXY_METHODS_MAPPING",
			Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL, String.class);

		Assert.assertEquals(sb.toString(), proxyMethodsMappingField.get(null));

		Field logField = _assertDeclaredField(
			skeletonClass, "_log",
			Modifier.FINAL | Modifier.PRIVATE | Modifier.STATIC, Log.class);

		LogWrapper logWrapper = (LogWrapper)logField.get(null);

		Jdk14LogImpl jdk14LogImpl = (Jdk14LogImpl)logWrapper.getWrappedLog();

		Logger logger = jdk14LogImpl.getWrappedLogger();

		Assert.assertEquals(skeletonClass.getName(), logger.getName());

		_assertDeclaredField(
			skeletonClass, "_targetLocator", Modifier.PRIVATE | Modifier.FINAL,
			TargetLocator.class);

		// Constructors

		Constructor<?>[] constructors = skeletonClass.getConstructors();

		Assert.assertEquals(1, constructors.length);

		Constructor<?> constructor = constructors[0];

		Assert.assertArrayEquals(
			new Class<?>[] {TargetLocator.class},
			constructor.getParameterTypes());

		// Methods

		_assertDeclaredMethod(
			skeletonClass, "dispatch",
			new Class<?>[] {
				RegistrationReference.class, Datagram.class, Deserializer.class
			},
			Modifier.PUBLIC, void.class);
		_assertDeclaredMethod(
			skeletonClass, "doDispatch",
			new Class<?>[] {
				RegistrationReference.class, Datagram.class, Deserializer.class
			},
			Modifier.PROTECTED, void.class, Exception.class);
		_assertDeclaredMethod(
			skeletonClass, "_getProxyMethodSignatures", new Class<?>[0],
			Modifier.STATIC | Modifier.PRIVATE, String[].class);
		_assertDeclaredMethod(
			skeletonClass, "_getProxyMethodsMapping",
			new Class<?>[] {String[].class}, Modifier.STATIC | Modifier.PRIVATE,
			String.class);
		_assertDeclaredMethod(
			skeletonClass, "_sendResponse",
			new Class<?>[] {
				RegistrationReference.class, Datagram.class, RPCResponse.class
			},
			Modifier.PRIVATE, void.class);
		_assertDeclaredMethod(
			skeletonClass, "_unknownMethodIndex", new Class<?>[] {int.class},
			Modifier.PRIVATE, void.class);

		Method[] declaredMethods = skeletonClass.getDeclaredMethods();

		Assert.assertEquals(6, declaredMethods.length);
	}

	private void _doTestGenerateStubClassStructure(
			Class<?> clazz, String skeletonId)
		throws Exception {

		Class<?> stubClass = IntrabandProxyUtil.generateStubClass(
			_classLoader, clazz, skeletonId);

		// Class signature

		Assert.assertEquals(Modifier.PUBLIC, stubClass.getModifiers());

		if (clazz.isInterface()) {
			Assert.assertArrayEquals(
				new Class<?>[] {clazz}, stubClass.getInterfaces());
		}
		else {
			Assert.assertArrayEquals(
				clazz.getInterfaces(), stubClass.getInterfaces());
		}

		// Fields

		_assertDeclaredField(
			stubClass, "PROXY_METHOD_SIGNATURES",
			Modifier.PUBLIC | Modifier.STATIC | Modifier.FINAL, String[].class);

		Assert.assertArrayEquals(
			_buildProxyMethodSignatures(clazz),
			IntrabandProxyUtil.getProxyMethodSignatures(stubClass));

		_assertDeclaredField(
			stubClass, "_id", Modifier.PRIVATE | Modifier.FINAL, String.class);

		Field proxyTypeField = _assertDeclaredField(
			stubClass, "_PROXY_TYPE",
			Modifier.PRIVATE | Modifier.STATIC | Modifier.FINAL, byte.class);

		Assert.assertEquals(
			SystemDataType.PROXY.getValue(), proxyTypeField.getByte(null));

		_assertDeclaredField(
			stubClass, "_intraband", Modifier.PRIVATE | Modifier.FINAL,
			Intraband.class);

		_assertDeclaredField(
			stubClass, "_registrationReference",
			Modifier.PRIVATE | Modifier.FINAL, RegistrationReference.class);

		_assertDeclaredField(
			stubClass, "_exceptionHandler", Modifier.PRIVATE | Modifier.FINAL,
			ExceptionHandler.class);

		Field[] fields = stubClass.getDeclaredFields();

		Assert.assertEquals(6, fields.length);

		// Constructors

		Constructor<?>[] constructors = stubClass.getConstructors();

		Assert.assertEquals(1, constructors.length);

		Constructor<?> constructor = constructors[0];

		Assert.assertArrayEquals(
			new Class<?>[] {
				String.class, RegistrationReference.class,
				ExceptionHandler.class
			},
			constructor.getParameterTypes());

		// Methods

		_assertDeclaredMethod(
			stubClass, "_getProxyMethodSignatures", new Class<?>[0],
			Modifier.STATIC | Modifier.PRIVATE, String[].class);
		_assertDeclaredMethod(
			stubClass, "_syncSend", new Class<?>[] {Serializer.class},
			Modifier.PRIVATE, Serializable.class);
		_assertDeclaredMethod(
			stubClass, "_send", new Class<?>[] {Serializer.class},
			Modifier.PRIVATE, void.class);

		List<Method> idMethods = _getIdMethods(clazz);

		for (Method idMethod : idMethods) {
			_assertDeclaredMethod(
				stubClass, idMethod.getName(), idMethod.getParameterTypes(),
				Modifier.PUBLIC, idMethod.getReturnType(),
				idMethod.getExceptionTypes());
		}

		List<Method> proxyMethods = _getProxyMethods(clazz);

		for (Method proxyMethod : proxyMethods) {
			_assertDeclaredMethod(
				stubClass, proxyMethod.getName(),
				proxyMethod.getParameterTypes(),
				(proxyMethod.getModifiers() & ~Modifier.ABSTRACT),
				proxyMethod.getReturnType(), proxyMethod.getExceptionTypes());
		}

		List<Method> emptyMethods = _getEmptyMethods(clazz);

		for (Method emptyMethod : emptyMethods) {
			_assertDeclaredMethod(
				stubClass, emptyMethod.getName(),
				emptyMethod.getParameterTypes(), Modifier.PUBLIC,
				emptyMethod.getReturnType(), emptyMethod.getExceptionTypes());
		}

		Method[] declaredMethods = stubClass.getDeclaredMethods();

		Assert.assertEquals(
			3 + idMethods.size() + proxyMethods.size() + emptyMethods.size(),
			declaredMethods.length);
	}

	private void _doTestGetClass(
			ClassLoader classLoader, final MethodHandler methodHandler)
		throws Exception {

		FutureTask<Class<?>> futureTask = new FutureTask<Class<?>>(
			new Callable<Class<?>>() {

				@Override
				public Class<?> call() throws Exception {
					return (Class<?>)methodHandler.invoke();
				}

			});

		Thread thread = new Thread(futureTask);

		Class<?> stubClass = null;

		synchronized (classLoader) {
			thread.start();

			while (thread.getState() != Thread.State.BLOCKED);

			stubClass = (Class<?>)methodHandler.invoke();
		}

		Assert.assertSame(stubClass, futureTask.get());

		// Load from ClassLoader cache

		Assert.assertSame(stubClass, methodHandler.invoke());
	}

	private void _doTestToClass(
			boolean proxyClassesDumpEnabled, boolean logEnabled)
		throws FileNotFoundException {

		class TestClass {
		}

		ClassNode classNode = _loadClass(TestClass.class);

		MethodNode methodNode = new MethodNode(
			Opcodes.ACC_PUBLIC, "<clinit>", "()V", null, null);

		methodNode.visitCode();
		methodNode.visitInsn(Opcodes.RETURN);
		methodNode.visitEnd();

		List<MethodNode> methodNodes = classNode.methods;

		methodNodes.add(methodNode);

		ClassLoader classLoader = new URLClassLoader(new URL[0], null);

		Level level = Level.WARNING;

		if (logEnabled) {
			level = Level.INFO;
		}

		try (CaptureHandler captureHandler =
				JDKLoggerTestUtil.configureJDKLogger(
					IntrabandProxyUtil.class.getName(), level)) {

			List<LogRecord> logRecords = captureHandler.getLogRecords();

			IntrabandProxyUtil.toClass(classNode, classLoader);

			if (proxyClassesDumpEnabled) {
				StringBundler sb = new StringBundler(6);

				sb.append(SystemProperties.get(SystemProperties.TMP_DIR));
				sb.append(StringPool.SLASH);
				sb.append(PropsValues.INTRABAND_PROXY_DUMP_CLASSES_DIR);
				sb.append(StringPool.SLASH);
				sb.append(classNode.name);
				sb.append(".class");

				String filePath = sb.toString();

				File classFile = new File(filePath);

				Assert.assertTrue(classFile.exists());

				ClassNode reloadedClassNode = _loadClass(
					new FileInputStream(classFile));

				MethodNode clinitMethodNode = ASMUtil.findMethodNode(
					reloadedClassNode.methods, "<clinit>", Type.VOID_TYPE);

				InsnList insnList = clinitMethodNode.instructions;

				Assert.assertEquals(1, insnList.size());

				_assertInsnNode(insnList.getFirst(), Opcodes.RETURN);

				if (logEnabled) {
					Assert.assertEquals(1, logRecords.size());

					LogRecord logRecord = logRecords.get(0);

					Assert.assertEquals(
						logRecord.getMessage(),
						"Dumpped class ".concat(filePath));
				}
			}

			if (!proxyClassesDumpEnabled || !logEnabled) {
				Assert.assertTrue(logRecords.isEmpty());
			}
		}

		try {
			IntrabandProxyUtil.toClass(classNode, classLoader);

			Assert.fail();
		}
		catch (RuntimeException re) {
			Throwable throwable = re.getCause();

			Assert.assertSame(
				InvocationTargetException.class, throwable.getClass());

			throwable = throwable.getCause();

			Assert.assertSame(LinkageError.class, throwable.getClass());

			String message = throwable.getMessage();

			Assert.assertTrue(
				message.contains(
					"duplicate class definition for name: \"" +
						Type.getInternalName(TestClass.class) + "\""));
		}
	}

	private void _doTestValidate(boolean skeletonOrStub) throws Exception {
		try {
			IntrabandProxyUtil.validate(_classLoader, Id.class, skeletonOrStub);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				Id.class + " is an annotation", iae.getMessage());
		}

		try {
			IntrabandProxyUtil.validate(
				_classLoader, int[].class, skeletonOrStub);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(int[].class + " is an array", iae.getMessage());
		}

		try {
			IntrabandProxyUtil.validate(
				_classLoader, SystemDataType.class, skeletonOrStub);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				SystemDataType.class + " is an enum", iae.getMessage());
		}

		try {
			IntrabandProxyUtil.validate(
				_classLoader, int.class, skeletonOrStub);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				int.class + " is a primitive", iae.getMessage());
		}

		ClassLoader classLoader = new URLClassLoader(new URL[0], null);

		try {
			IntrabandProxyUtil.validate(
				classLoader, IntrabandProxyUtilTest.class, skeletonOrStub);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Assert.assertEquals(
				IntrabandProxyUtilTest.class +
					" is not visible from class loader " + classLoader,
				iae.getMessage());
		}

		class TestValidateClass1 {

			@SuppressWarnings("unused")
			private String[] PROXY_METHOD_SIGNATURES;

		}

		try {
			IntrabandProxyUtil.validate(
				_classLoader, TestValidateClass1.class, skeletonOrStub);

			Assert.fail();
		}
		catch (IllegalArgumentException iae) {
			Field field = TestValidateClass1.class.getDeclaredField(
				"PROXY_METHOD_SIGNATURES");

			Assert.assertEquals(
				"Field " + field + " is expected to be of type " +
					String[].class + " and static",
				iae.getMessage());
		}

		if (skeletonOrStub) {
			class TestValidateClass2 {

				@SuppressWarnings("unused")
				private String _PROXY_METHODS_MAPPING;
			}

			try {
				IntrabandProxyUtil.validate(
					_classLoader, TestValidateClass2.class, true);

				Assert.fail();
			}
			catch (IllegalArgumentException iae) {
				Field field = TestValidateClass2.class.getDeclaredField(
					"_PROXY_METHODS_MAPPING");

				Assert.assertEquals(
					"Field " + field + " is expected to be of type " +
						String.class + " and static",
					iae.getMessage());
			}

			class TestValidateClass3 {

				@SuppressWarnings("unused")
				private String _log;
			}

			try {
				IntrabandProxyUtil.validate(
					_classLoader, TestValidateClass3.class, true);

				Assert.fail();
			}
			catch (IllegalArgumentException iae) {
				Field field = TestValidateClass3.class.getDeclaredField("_log");

				Assert.assertEquals(
					"Field " + field + " is expected to be of type " +
						Log.class + " and static",
					iae.getMessage());
			}

			class TestValidateClass4 {

				@SuppressWarnings("unused")
				private Object _targetLocator;
			}

			try {
				IntrabandProxyUtil.validate(
					_classLoader, TestValidateClass4.class, true);

				Assert.fail();
			}
			catch (IllegalArgumentException iae) {
				Field field = TestValidateClass4.class.getDeclaredField(
					"_targetLocator");

				Assert.assertEquals(
					"Field " + field + " is expected to be of type " +
						TargetLocator.class + " and not static",
					iae.getMessage());
			}
		}
		else {
			class TestValidateClass5 {

				@SuppressWarnings("unused")
				int _proxyType = 0;
			}

			try {
				IntrabandProxyUtil.validate(
					_classLoader, TestValidateClass5.class, false);

				Assert.fail();
			}
			catch (IllegalArgumentException iae) {
				Field field = TestValidateClass5.class.getDeclaredField(
					"_proxyType");

				Assert.assertEquals(
					"Field " + field + " is expected to be of type " +
						byte.class + " and static",
					iae.getMessage());
			}

			class TestValidateClass6 {

				@SuppressWarnings("unused")
				private Object _id;
			}

			try {
				IntrabandProxyUtil.validate(
					_classLoader, TestValidateClass6.class, false);

				Assert.fail();
			}
			catch (IllegalArgumentException iae) {
				Field field = TestValidateClass6.class.getDeclaredField("_id");

				Assert.assertEquals(
					"Field " + field + " is expected to be of type " +
						String.class + " and not static",
					iae.getMessage());
			}

			class TestValidateClass7 {

				@SuppressWarnings("unused")
				private Object _intraband;
			}

			try {
				IntrabandProxyUtil.validate(
					_classLoader, TestValidateClass7.class, false);

				Assert.fail();
			}
			catch (IllegalArgumentException iae) {
				Field field = TestValidateClass7.class.getDeclaredField(
					"_intraband");

				Assert.assertEquals(
					"Field " + field + " is expected to be of type " +
						Intraband.class + " and not static",
					iae.getMessage());
			}

			class TestValidateClass8 {

				@SuppressWarnings("unused")
				private Object _registrationReference;
			}

			try {
				IntrabandProxyUtil.validate(
					_classLoader, TestValidateClass8.class, false);

				Assert.fail();
			}
			catch (IllegalArgumentException iae) {
				Field field = TestValidateClass8.class.getDeclaredField(
					"_registrationReference");

				Assert.assertEquals(
					"Field " + field + " is expected to be of type " +
						RegistrationReference.class + " and not static",
					iae.getMessage());
			}

			class TestValidateClass9 {

				@SuppressWarnings("unused")
				private Object _exceptionHandler;
			}

			try {
				IntrabandProxyUtil.validate(
					_classLoader, TestValidateClass9.class, false);

				Assert.fail();
			}
			catch (IllegalArgumentException iae) {
				Field field = TestValidateClass9.class.getDeclaredField(
					"_exceptionHandler");

				Assert.assertEquals(
					"Field " + field + " is expected to be of type " +
						ExceptionHandler.class + " and not static",
					iae.getMessage());
			}
		}

		IntrabandProxyUtil.validate(
			_classLoader, TestValidateClass.class, skeletonOrStub);
	}

	private List<Method> _getCopiedMethods(Class<?> clazz) {
		List<Method> emptyMethods = new ArrayList<>();

		for (Method method : ReflectionUtil.getVisibleMethods(clazz)) {
			String name = method.getName();

			if (!Modifier.isAbstract(method.getModifiers()) &&
				name.startsWith("copy") &&
				(method.getAnnotation(Id.class) == null) &&
				(method.getAnnotation(Proxy.class) == null)) {

				emptyMethods.add(method);
			}
		}

		return emptyMethods;
	}

	private List<Method> _getEmptyMethods(Class<?> clazz) {
		List<Method> emptyMethods = new ArrayList<>();

		for (Method method : ReflectionUtil.getVisibleMethods(clazz)) {
			if (Modifier.isAbstract(method.getModifiers()) &&
				(method.getAnnotation(Id.class) == null) &&
				(method.getAnnotation(Proxy.class) == null)) {

				emptyMethods.add(method);
			}
		}

		return emptyMethods;
	}

	private List<Method> _getIdMethods(Class<?> clazz) {
		List<Method> idMethods = new ArrayList<>();

		for (Method method : ReflectionUtil.getVisibleMethods(clazz)) {
			if (method.getAnnotation(Id.class) != null) {
				idMethods.add(method);
			}
		}

		return idMethods;
	}

	private List<Method> _getProxyMethods(Class<?> clazz) {
		List<Method> proxyMethods = new ArrayList<>();

		for (Method method : ReflectionUtil.getVisibleMethods(clazz)) {
			if (method.getAnnotation(Proxy.class) != null) {
				proxyMethods.add(method);
			}
		}

		Collections.sort(proxyMethods, new MethodComparator());

		return proxyMethods;
	}

	private ClassNode _loadClass(Class<?> clazz) {
		ClassLoader classLoader = clazz.getClassLoader();

		String name = clazz.getName();

		name = name.replace(CharPool.PERIOD, CharPool.SLASH);

		return _loadClass(
			classLoader.getResourceAsStream(name.concat(".class")));
	}

	private ClassNode _loadClass(InputStream is) {
		ClassReader classReader = null;

		try {
			classReader = new ClassReader(is);
		}
		catch (IOException ioe) {
			throw new RuntimeException(ioe);
		}

		ClassNode classNode = new ClassNode();

		classReader.accept(classNode, 0);

		return classNode;
	}

	private static final Map<Class<?>, Class<?>> _autoboxingMap =
		new HashMap<>();
	private static final ClassLoader _classLoader =
		IntrabandProxyUtilTest.class.getClassLoader();
	private static final Map<Class<?>, Object> _defaultValueMap =
		new HashMap<>();
	private static final Map<Class<?>, Object> _sampleValueMap =
		new HashMap<>();
	private static final Type[] _types = {
		Type.BOOLEAN_TYPE, Type.BYTE_TYPE, Type.CHAR_TYPE, Type.DOUBLE_TYPE,
		Type.FLOAT_TYPE, Type.INT_TYPE, Type.LONG_TYPE, Type.SHORT_TYPE,
		Type.getType(String.class), Type.getType(Object.class)
	};

	static {
		_autoboxingMap.put(boolean.class, Boolean.class);
		_autoboxingMap.put(byte.class, Number.class);
		_autoboxingMap.put(char.class, Character.class);
		_autoboxingMap.put(double.class, Number.class);
		_autoboxingMap.put(float.class, Number.class);
		_autoboxingMap.put(int.class, Number.class);
		_autoboxingMap.put(long.class, Number.class);
		_autoboxingMap.put(short.class, Number.class);

		_defaultValueMap.put(boolean.class, Boolean.FALSE);
		_defaultValueMap.put(byte.class, (byte)0);
		_defaultValueMap.put(char.class, (char)0);
		_defaultValueMap.put(double.class, (double)0);
		_defaultValueMap.put(float.class, (float)0);
		_defaultValueMap.put(int.class, 0);
		_defaultValueMap.put(long.class, (long)0);
		_defaultValueMap.put(short.class, (short)0);
		_defaultValueMap.put(String.class, null);
		_defaultValueMap.put(Date.class, null);
		_defaultValueMap.put(Object.class, null);
		_defaultValueMap.put(void.class, null);

		_sampleValueMap.put(boolean.class, Boolean.TRUE);
		_sampleValueMap.put(byte.class, (byte)11);
		_sampleValueMap.put(char.class, 'X');
		_sampleValueMap.put(double.class, 12.345);
		_sampleValueMap.put(float.class, 5.325F);
		_sampleValueMap.put(int.class, 127);
		_sampleValueMap.put(long.class, (long)82465);
		_sampleValueMap.put(short.class, (short)-35);
		_sampleValueMap.put(String.class, "Hello");
		_sampleValueMap.put(Date.class, new Date());
		_sampleValueMap.put(Object.class, new Locale("en"));
		_sampleValueMap.put(void.class, null);
	}

	private static class AutoReplyMockIntraband extends MockIntraband {

		public AutoReplyMockIntraband(String skeletonId, String targetId) {
			_skeletonId = skeletonId;
			_targetId = targetId;
		}

		public void setInvocation(Method method, int index) {
			_method = method;
			_index = index;
		}

		@Override
		protected Datagram processDatagram(Datagram datagram) {
			Deserializer deserializer = new Deserializer(
				datagram.getDataByteBuffer());

			Assert.assertEquals(_skeletonId, deserializer.readString());
			Assert.assertEquals(_targetId, deserializer.readString());
			Assert.assertEquals(_index, deserializer.readInt());

			for (Class<?> parameterType : _method.getParameterTypes()) {
				Assert.assertEquals(
					_sampleValueMap.get(parameterType),
					_readFromDeserializer(deserializer, parameterType));
			}

			Class<?> returnType = _method.getReturnType();

			if (returnType == void.class) {
				return null;
			}

			Serializer serializer = new Serializer();

			serializer.writeObject(
				new RPCResponse((Serializable)_sampleValueMap.get(returnType)));

			return Datagram.createResponseDatagram(
				datagram, serializer.toByteBuffer());
		}

		private int _index;
		private Method _method;
		private final String _skeletonId;
		private final String _targetId;

	}

	private static class TestExtractMethodsClass1 {

		@Id
		public static String getId() {
			return null;
		}

	}

	private static class TestExtractMethodsClass2 {

		@Id
		public String getId(Object obj) {
			return null;
		}

	}

	private static class TestExtractMethodsClass3 {

		@Id
		public Object getId() {
			return null;
		}

	}

	private static class TestExtractMethodsClass4 {

		@Proxy
		public static void doStuff() {
		}

	}

	private static class TestGenerateStubFunction1 {

		@SuppressWarnings("unused")
		public TestGenerateStubFunction1() {
			if (_log.isInfoEnabled()) {
				_log.info(
					TestGenerateStubFunction1.class.getName() + " in <init>");
			}
		}

		private static final Log _log = LogFactoryUtil.getLog(
			TestGenerateStubFunction1.class);

		static {
			if (_log.isInfoEnabled()) {
				_log.info(
					TestGenerateStubFunction1.class.getName() + " in <clinit>");
			}
		}

	}

	private static class TestGenerateTargetLocator implements TargetLocator {

		public TestGenerateTargetLocator(Class<?> clazz) {
			_clazz = clazz;
		}

		@Override
		public boolean equals(Object obj) {
			TestGenerateTargetLocator testGenerateTargetLocator =
				(TestGenerateTargetLocator)obj;

			return _clazz.equals(testGenerateTargetLocator._clazz);
		}

		@SuppressWarnings("unused")
		public String getId() {
			return _id;
		}

		@Override
		public Object getTarget(String id) {
			_id = id;

			return ProxyUtil.newProxyInstance(
				_classLoader, new Class<?>[] {_clazz},
				new InvocationHandler() {

					@Override
					public Object invoke(
						Object proxy, Method method, Object[] args) {

						Class<?>[] parameterTypes = method.getParameterTypes();

						for (int i = 0; i < args.length; i++) {
							Assert.assertEquals(
								_sampleValueMap.get(parameterTypes[i]),
								args[i]);
						}

						return _sampleValueMap.get(method.getReturnType());
					}

				});
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		private final Class<?> _clazz;
		private String _id;

	}

	private static class TestValidateClass {

		@SuppressWarnings("unused")
		private static String _PROXY_METHODS_MAPPING;

		@SuppressWarnings("unused")
		private static String[] PROXY_METHOD_SIGNATURES;

		@SuppressWarnings("unused")
		private static Log _log;

		@SuppressWarnings("unused")
		private static byte _proxyType;

		@SuppressWarnings("unused")
		private ExceptionHandler _exceptionHandler;

		@SuppressWarnings("unused")
		private String _id;

		@SuppressWarnings("unused")
		private Intraband _intraband;

		@SuppressWarnings("unused")
		private RegistrationReference _registrationReference;

		@SuppressWarnings("unused")
		private TargetLocator _targetLocator;

	}

	private interface TestEmptyMethodsInterface {

		public boolean testBoolean();

		public byte testByte();

		public char testChar();

		public double testDouble();

		public float testFloat();

		public int testInt();

		public long testLong();

		public Object testObject();

		public short testShort();

		public void testVoid();

	}

	private abstract static class TestExtractMethodsClass5 {

		@Proxy
		public void doStuff1() {
		}

		@Proxy(name = "doStuffX")
		public void doStuff2() {
		}

		@SuppressWarnings("unused")
		public void doStuff3() {
		}

		public abstract void doStuff4();

		@Id
		public String getId1() {
			return null;
		}

		@Id
		public String getId2() {
			return null;
		}

	}

	private abstract static class TestGenerateClass1
		extends TestProxyMethodsClass implements TestGenerateInterface1 {
	}

	private abstract static class TestGenerateClass2
		extends TestProxyMethodsClass implements TestGenerateInterface2 {
	}

	private interface TestGenerateInterface1
		extends Comparable<String>, Callable<String>, Runnable,
				TestEmptyMethodsInterface, TestProxyMethodsInterface {
	}

	private interface TestGenerateInterface2
		extends TestIdMethodsInterface, TestGenerateInterface1 {
	}

	private abstract static class TestGenerateStubFunction2
		extends TestProxyMethodsClass
		implements TestEmptyMethodsInterface, TestIdMethodsInterface {

		@SuppressWarnings("unused")
		public void copyMethod1() {
			if (_log.isInfoEnabled()) {
				_log.info("copyMethod1");
			}
		}

		@SuppressWarnings("unused")
		protected void copyMethod2() {
			if (_log.isInfoEnabled()) {
				_log.info("copyMethod2");
			}
		}

		@SuppressWarnings("unused")
		void copyMethod3() {
			if (_log.isInfoEnabled()) {
				_log.info("copyMethod3");
			}
		}

		@SuppressWarnings("unused")
		private void copyMethod4() {
			if (_log.isInfoEnabled()) {
				_log.info("copyMethod4");
			}
		}

		private static final Log _log = LogFactoryUtil.getLog(
			TestGenerateStubFunction2.class);

	}

	private interface TestIdMethodsInterface {

		@Id
		public abstract String getId1();

		@Id
		public abstract String getId2();

	}

	private abstract static class TestProxyMethodsClass
		implements TestProxyMethodsInterface {

		@Proxy
		protected abstract short syncCallShort(
				boolean z, byte b, char c, double d, float f, int i, long j,
				short s, String string, Date date, Object obj)
			throws InterruptedException, IOException;

		@Proxy
		abstract Object syncCallObject(
				boolean z, byte b, char c, double d, float f, int i, long j,
				short s, String string, Date date, Object obj)
			throws InterruptedException, IOException;

		@Proxy
		abstract String syncCallString(
				boolean z, byte b, char c, double d, float f, int i, long j,
				short s, String string, Date date, Object obj)
			throws InterruptedException, IOException;

	}

	private interface TestProxyMethodsInterface {

		@Proxy
		public void asyncCall(
				boolean z, byte b, char c, double d, float f, int i, long j,
				short s, String string, Date date, Object obj)
			throws InterruptedException, IOException;

		@Proxy
		public boolean syncCallBoolean(
				boolean z, byte b, char c, double d, float f, int i, long j,
				short s, String string, Date date, Object obj)
			throws InterruptedException, IOException;

		@Proxy
		public byte syncCallByte(
				boolean z, byte b, char c, double d, float f, int i, long j,
				short s, String string, Date date, Object obj)
			throws InterruptedException, IOException;

		@Proxy
		public char syncCallChar(
				boolean z, byte b, char c, double d, float f, int i, long j,
				short s, String string, Date date, Object obj)
			throws InterruptedException, IOException;

		@Proxy
		public double syncCallDouble(
				boolean z, byte b, char c, double d, float f, int i, long j,
				short s, String string, Date date, Object obj)
			throws InterruptedException, IOException;

		@Proxy
		public float syncCallFloat(
				boolean z, byte b, char c, double d, float f, int i, long j,
				short s, String string, Date date, Object obj)
			throws InterruptedException, IOException;

		@Proxy
		public int syncCallInt(
				boolean z, byte b, char c, double d, float f, int i, long j,
				short s, String string, Date date, Object obj)
			throws InterruptedException, IOException;

		@Proxy
		public long syncCallLong(
				boolean z, byte b, char c, double d, float f, int i, long j,
				short s, String string, Date date, Object obj)
			throws InterruptedException, IOException;

	}

}