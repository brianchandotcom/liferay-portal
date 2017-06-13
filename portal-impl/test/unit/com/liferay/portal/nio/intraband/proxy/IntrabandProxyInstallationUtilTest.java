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

import com.liferay.portal.kernel.io.Deserializer;
import com.liferay.portal.kernel.io.Serializer;
import com.liferay.portal.kernel.nio.intraband.Datagram;
import com.liferay.portal.kernel.nio.intraband.PortalExecutorManagerUtilAdvice;
import com.liferay.portal.kernel.nio.intraband.proxy.AsyncIntrabandProxySkeleton;
import com.liferay.portal.kernel.nio.intraband.proxy.IntrabandProxySkeleton;
import com.liferay.portal.kernel.nio.intraband.proxy.IntrabandProxySkeletonRegistryUtil;
import com.liferay.portal.kernel.nio.intraband.proxy.TargetLocator;
import com.liferay.portal.kernel.nio.intraband.proxy.annotation.Id;
import com.liferay.portal.kernel.nio.intraband.proxy.annotation.Proxy;
import com.liferay.portal.kernel.nio.intraband.rpc.RPCResponse;
import com.liferay.portal.kernel.nio.intraband.test.MockIntraband;
import com.liferay.portal.kernel.nio.intraband.test.MockRegistrationReference;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.test.rule.AggregateTestRule;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.rule.NewEnv;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.test.rule.AdviseWith;
import com.liferay.portal.test.rule.AspectJNewEnvTestRule;
import com.liferay.portal.util.FileImpl;
import com.liferay.registry.BasicRegistryImpl;
import com.liferay.registry.RegistryUtil;

import java.io.Serializable;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
@NewEnv(type = NewEnv.Type.CLASSLOADER)
public class IntrabandProxyInstallationUtilTest {

	@ClassRule
	@Rule
	public static final AggregateTestRule aggregateTestRule =
		new AggregateTestRule(
			AspectJNewEnvTestRule.INSTANCE, CodeCoverageAssertor.INSTANCE);

	@Before
	public void setUp() {
		FileUtil fileUtil = new FileUtil();

		fileUtil.setFile(new FileImpl());

		IntrabandProxySkeletonRegistryUtil.unregister(
			TestClass.class.getName());

		MockIntraband mockIntraband = new MockIntraband() {

			@Override
			protected Datagram processDatagram(Datagram datagram) {
				Deserializer deserializer = new Deserializer(
					datagram.getDataByteBuffer());

				Serializer serializer = new Serializer();

				try {
					ProcessCallable<Serializable> processCallable =
						deserializer.readObject();

					serializer.writeObject(
						new RPCResponse(processCallable.call()));
				}
				catch (Exception e) {
					serializer.writeObject(new RPCResponse(e));
				}

				return Datagram.createResponseDatagram(
					datagram, serializer.toByteBuffer());
			}

		};

		_mockRegistrationReference = new MockRegistrationReference(
			mockIntraband);

		_stubProxyMethodSignatures =
			IntrabandProxyUtil.getProxyMethodSignatures(
				IntrabandProxyUtil.getStubClass(TestClass.class, "skeletonId"));

		RegistryUtil.setRegistry(new BasicRegistryImpl());
	}

	@NewEnv(type = NewEnv.Type.NONE)
	@Test
	public void testConstructor() {
		new IntrabandProxyInstallationUtil();
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testInstallSkeletonLocally() {
		IntrabandProxyInstallationUtil.checkProxyMethodSignatures(
			IntrabandProxyInstallationUtil.installSkeleton(
				TestClass.class, _targetLocator),
			_stubProxyMethodSignatures);

		IntrabandProxySkeleton intrabandProxySkeleton =
			IntrabandProxySkeletonRegistryUtil.get(TestClass.class.getName());

		Assert.assertSame(
			AsyncIntrabandProxySkeleton.class,
			intrabandProxySkeleton.getClass());

		intrabandProxySkeleton = ReflectionTestUtil.getFieldValue(
			intrabandProxySkeleton, "_intrabandProxySkeleton");

		Assert.assertEquals(
			_targetLocator,
			ReflectionTestUtil.getFieldValue(
				intrabandProxySkeleton, "_targetLocator"));

		try {
			IntrabandProxyInstallationUtil.installSkeleton(
				_classLoader, TestClass.class, null);

			Assert.fail();
		}
		catch (RuntimeException re) {
			Throwable throwable = re.getCause();

			throwable = throwable.getCause();

			Assert.assertSame(NullPointerException.class, throwable.getClass());
			Assert.assertEquals(
				"Target locator is null", throwable.getMessage());
		}

		try {
			IntrabandProxyInstallationUtil.checkProxyMethodSignatures(
				IntrabandProxyInstallationUtil.installSkeleton(
					_classLoader, TestClass.class, _targetLocator),
				new String[] {"doStuffX-()Ljava/lang/Object;"});

			Assert.fail();
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals(
				"Skeleton and stub proxy method signatures do not match. " +
					"Skeleton is [doStuff-()Ljava/lang/Object;]. Stub is " +
						"[doStuffX-()Ljava/lang/Object;].",
				ise.getMessage());
		}
	}

	@AdviseWith(adviceClasses = {PortalExecutorManagerUtilAdvice.class})
	@Test
	public void testInstallSkeletonRemotely() throws Exception {
		Future<String[]> skeletonProxyMethodSignaturesFuture =
			IntrabandProxyInstallationUtil.installSkeleton(
				_mockRegistrationReference, TestClass.class, _targetLocator);

		String[] skeletonProxyMethodSignatures =
			skeletonProxyMethodSignaturesFuture.get();

		IntrabandProxyInstallationUtil.checkProxyMethodSignatures(
			skeletonProxyMethodSignatures, _stubProxyMethodSignatures);

		IntrabandProxySkeleton intrabandProxySkeleton =
			IntrabandProxySkeletonRegistryUtil.get(TestClass.class.getName());

		Assert.assertSame(
			AsyncIntrabandProxySkeleton.class,
			intrabandProxySkeleton.getClass());

		intrabandProxySkeleton = ReflectionTestUtil.getFieldValue(
			intrabandProxySkeleton, "_intrabandProxySkeleton");

		Assert.assertEquals(
			_targetLocator,
			ReflectionTestUtil.getFieldValue(
				intrabandProxySkeleton, "_targetLocator"));

		skeletonProxyMethodSignaturesFuture =
			IntrabandProxyInstallationUtil.installSkeleton(
				_mockRegistrationReference, _classLoader, TestClass.class,
				null);

		try {
			skeletonProxyMethodSignaturesFuture.get();

			Assert.fail();
		}
		catch (ExecutionException ee) {
			Throwable throwable = ee.getCause();

			throwable = throwable.getCause();
			throwable = throwable.getCause();
			throwable = throwable.getCause();

			Assert.assertSame(NullPointerException.class, throwable.getClass());
			Assert.assertEquals(
				"Target locator is null", throwable.getMessage());
		}

		skeletonProxyMethodSignaturesFuture =
			IntrabandProxyInstallationUtil.installSkeleton(
				_mockRegistrationReference, _classLoader, TestClass.class,
				_targetLocator);

		skeletonProxyMethodSignatures =
			skeletonProxyMethodSignaturesFuture.get();

		try {
			IntrabandProxyInstallationUtil.checkProxyMethodSignatures(
				skeletonProxyMethodSignatures,
				new String[] {"doStuffX-()Ljava/lang/Object;"});

			Assert.fail();
		}
		catch (IllegalStateException ise) {
			Assert.assertEquals(
				"Skeleton and stub proxy method signatures do not match. " +
					"Skeleton is [doStuff-()Ljava/lang/Object;]. Stub is " +
						"[doStuffX-()Ljava/lang/Object;].",
				ise.getMessage());
		}
	}

	private static final ClassLoader _classLoader =
		IntrabandProxyInstallationUtilTest.class.getClassLoader();

	private MockRegistrationReference _mockRegistrationReference;
	private String[] _stubProxyMethodSignatures;
	private final TargetLocator _targetLocator = new TestGenerateTargetLocator(
		TestClass.class);

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

		@Override
		public Object getTarget(String id) {
			return null;
		}

		@Override
		public int hashCode() {
			return super.hashCode();
		}

		private final Class<?> _clazz;

	}

	private abstract class TestClass {

		@SuppressWarnings("unused")
		public void copyMethod() {
		}

		@Proxy
		public abstract Object doStuff();

		@Id
		public abstract String getId();

	}

}