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

package com.liferay.portal.fabric.status;

import com.liferay.portal.fabric.status.JMXProxyUtil.GetAttributeProcessCallable;
import com.liferay.portal.fabric.status.JMXProxyUtil.JMXProxyInvocationHandler;
import com.liferay.portal.fabric.status.JMXProxyUtil.OperationProcessCallable;
import com.liferay.portal.fabric.status.JMXProxyUtil.Optional;
import com.liferay.portal.fabric.status.JMXProxyUtil.ProcessCallableExecutor;
import com.liferay.portal.fabric.status.JMXProxyUtil.SetAttributeProcessCallable;
import com.liferay.portal.kernel.concurrent.DefaultNoticeableFuture;
import com.liferay.portal.kernel.concurrent.NoticeableFuture;
import com.liferay.portal.kernel.process.ProcessCallable;
import com.liferay.portal.kernel.process.ProcessException;
import com.liferay.portal.kernel.test.rule.CodeCoverageAssertor;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.ReflectionUtil;

import java.io.Serializable;

import java.lang.management.CompilationMXBean;
import java.lang.management.LockInfo;
import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.MonitorInfo;
import java.lang.management.PlatformManagedObject;
import java.lang.management.ThreadInfo;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.util.Arrays;
import java.util.List;

import javax.management.AttributeNotFoundException;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.openmbean.CompositeData;
import javax.management.openmbean.CompositeDataSupport;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;

/**
 * @author Shuyang Zhou
 */
public class JMXProxyUtilTest {

	@ClassRule
	public static final CodeCoverageAssertor codeCoverageAssertor =
		CodeCoverageAssertor.INSTANCE;

	@Before
	public void setUp() throws Exception {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

		mBeanServer.registerMBean(_testClass, _testClassObjectName);
	}

	@After
	public void tearDown() throws Exception {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

		mBeanServer.unregisterMBean(_testClassObjectName);
	}

	@Test
	public void testConstructor() {
		new JMXProxyUtil();
	}

	@Test
	public void testDecodeArrayToList() {
		String[] stringArray = RandomTestUtil.randomStrings(10);

		Object stringList = JMXProxyUtil.decode(List.class, stringArray);

		Assert.assertEquals(Arrays.asList(stringArray), stringList);

		String string = RandomTestUtil.randomString();

		Assert.assertSame(string, JMXProxyUtil.decode(List.class, string));
	}

	@Test
	public void testDecodeCompositeData() throws Exception {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

		CompositeDataSupport compositeDataSupport =
			(CompositeDataSupport)mBeanServer.getAttribute(
				createObjectName(ManagementFactory.MEMORY_MXBEAN_NAME),
				"HeapMemoryUsage");

		MemoryUsage memoryUsage = (MemoryUsage)JMXProxyUtil.decode(
			MemoryUsage.class, compositeDataSupport);

		Assert.assertEquals(
			compositeDataSupport.get("init"), memoryUsage.getInit());
		Assert.assertEquals(
			compositeDataSupport.get("used"), memoryUsage.getUsed());
		Assert.assertEquals(
			compositeDataSupport.get("committed"), memoryUsage.getCommitted());
		Assert.assertEquals(
			compositeDataSupport.get("max"), memoryUsage.getMax());

		Thread currentThread = Thread.currentThread();

		CompositeData[] compositeDatas = (CompositeData[])mBeanServer.invoke(
			createObjectName(ManagementFactory.THREAD_MXBEAN_NAME),
			"getThreadInfo",
			new Object[] {new long[] {currentThread.getId()}, true, true},
			new String[] {
				long[].class.getName(), boolean.class.getName(),
				boolean.class.getName()
			});

		Assert.assertEquals(
			Arrays.toString(compositeDatas), 1, compositeDatas.length);

		compositeDataSupport = (CompositeDataSupport)compositeDatas[0];

		ThreadInfo threadInfo = (ThreadInfo)JMXProxyUtil.decode(
			ThreadInfo.class, compositeDataSupport);

		Assert.assertEquals(
			compositeDataSupport.get("threadId"), threadInfo.getThreadId());
		Assert.assertEquals(
			compositeDataSupport.get("threadName"), threadInfo.getThreadName());
		Assert.assertEquals(
			compositeDataSupport.get("suspended"), threadInfo.isSuspended());
		Assert.assertEquals(
			compositeDataSupport.get("inNative"), threadInfo.isInNative());
		Assert.assertEquals(
			compositeDataSupport.get("blockedCount"),
			threadInfo.getBlockedCount());
		Assert.assertEquals(
			compositeDataSupport.get("blockedTime"),
			threadInfo.getBlockedTime());
		Assert.assertEquals(
			compositeDataSupport.get("waitedCount"),
			threadInfo.getWaitedCount());
		Assert.assertEquals(
			compositeDataSupport.get("waitedTime"), threadInfo.getWaitedTime());

		assertEquals(
			createLockInfo((CompositeData)compositeDataSupport.get("lockInfo")),
			threadInfo.getLockInfo());

		Assert.assertEquals(
			compositeDataSupport.get("lockName"), threadInfo.getLockName());
		Assert.assertEquals(
			compositeDataSupport.get("lockOwnerId"),
			threadInfo.getLockOwnerId());
		Assert.assertEquals(
			compositeDataSupport.get("lockOwnerName"),
			threadInfo.getLockOwnerName());
		Assert.assertArrayEquals(
			createStackTraceElements(
				(CompositeData[])compositeDataSupport.get("stackTrace")),
			threadInfo.getStackTrace());

		assertEquals(
			createMonitorInfos(
				(CompositeData[])compositeDataSupport.get("lockedMonitors")),
			threadInfo.getLockedMonitors());
		assertEquals(
			createLockInfos(
				(CompositeData[])compositeDataSupport.get(
					"lockedSynchronizers")),
			threadInfo.getLockedSynchronizers());

		Assert.assertSame(
			compositeDataSupport,
			JMXProxyUtil.decode(Object.class, compositeDataSupport));
	}

	@Test
	public void testDecodeCompositeDataArray() throws Exception {
		MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

		CompositeData[] compositeDatas = (CompositeData[])mBeanServer.invoke(
			createObjectName(ManagementFactory.THREAD_MXBEAN_NAME),
			"dumpAllThreads", new Object[] {true, true},
			new String[] {boolean.class.getName(), boolean.class.getName()});

		ThreadInfo[] threadInfos = (ThreadInfo[])JMXProxyUtil.decode(
			ThreadInfo[].class, compositeDatas);

		assertEquals(createThreadInfos(compositeDatas), threadInfos);

		Assert.assertSame(
			compositeDatas,
			JMXProxyUtil.decode(ThreadInfo.class, compositeDatas));
	}

	@Test
	public void testEquals() {
		final ObjectName objectName = ObjectName.WILDCARD;

		Assert.assertTrue(
			JMXProxyUtil.equals(
				objectName,
				new PlatformManagedObject() {

					@Override
					public ObjectName getObjectName() {
						return objectName;
					}

				}));
		Assert.assertTrue(
			JMXProxyUtil.equals(
				objectName,
				ProxyUtil.newProxyInstance(
					JMXProxyUtil.class.getClassLoader(),
					new Class<?>[] {Runnable.class},
					new JMXProxyInvocationHandler(objectName, null))));
		Assert.assertFalse(
			JMXProxyUtil.equals(
				objectName,
				ProxyUtil.newProxyInstance(
					JMXProxyUtil.class.getClassLoader(),
					new Class<?>[] {Runnable.class},
					new InvocationHandler() {

						@Override
						public Object invoke(
							Object proxy, Method method, Object[] args) {

							return null;
						}

					})));
		Assert.assertFalse(JMXProxyUtil.equals(objectName, new Object()));
	}

	@Test
	public void testGetAttributeProcessCallableFailureInstanceNotFound() {
		GetAttributeProcessCallable getAttributeProcessCallable =
			new GetAttributeProcessCallable(_unknowObjectName, "Name", true);

		try {
			getAttributeProcessCallable.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertSame(
				InstanceNotFoundException.class, throwable.getClass());
		}
	}

	@Test
	public void testGetAttributeProcessCallableFailureInstanceOptional()
		throws ProcessException {

		GetAttributeProcessCallable getAttributeProcessCallable =
			new GetAttributeProcessCallable(
				_testClassObjectName, "NameX", true);

		Assert.assertNull(getAttributeProcessCallable.call());
	}

	@Test
	public void testGetAttributeProcessCallableFailureInstanceRequired() {
		GetAttributeProcessCallable getAttributeProcessCallable =
			new GetAttributeProcessCallable(
				_testClassObjectName, "NameX", false);

		try {
			getAttributeProcessCallable.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertSame(
				AttributeNotFoundException.class, throwable.getClass());
		}
	}

	@Test
	public void testIsGetGetter() {
		Assert.assertTrue(JMXProxyUtil.isGetGetter("getXYZ"));
		Assert.assertFalse(JMXProxyUtil.isGetGetter("getXYZ", Object.class));
		Assert.assertFalse(JMXProxyUtil.isGetGetter("GetXYZ"));
	}

	@Test
	public void testIsIsGetter() {
		Assert.assertTrue(JMXProxyUtil.isIsGetter("isXYZ"));
		Assert.assertFalse(JMXProxyUtil.isIsGetter("isXYZ", Object.class));
		Assert.assertFalse(JMXProxyUtil.isIsGetter("IsXYZ"));
	}

	@Test
	public void testIsObjectEquals() throws NoSuchMethodException {
		Assert.assertTrue(
			JMXProxyUtil.isObjectEquals(
				Object.class.getMethod("equals", Object.class)));
		Assert.assertFalse(
			JMXProxyUtil.isObjectEquals(Object.class.getMethod("wait")));

		class OverrideClass {

			@Override
			public boolean equals(Object object) {
				return super.equals(object);
			}

			@Override
			public int hashCode() {
				return super.hashCode();
			}

		}

		Assert.assertFalse(
			JMXProxyUtil.isObjectEquals(
				OverrideClass.class.getMethod("equals", Object.class)));
	}

	@Test
	public void testIsObjectHashCode() throws NoSuchMethodException {
		Assert.assertTrue(
			JMXProxyUtil.isObjectHashCode(Object.class.getMethod("hashCode")));
		Assert.assertFalse(
			JMXProxyUtil.isObjectHashCode(Object.class.getMethod("wait")));

		class OverrideClass {

			@Override
			public boolean equals(Object object) {
				return super.equals(object);
			}

			@Override
			public int hashCode() {
				return super.hashCode();
			}

		}

		Assert.assertFalse(
			JMXProxyUtil.isObjectHashCode(
				OverrideClass.class.getMethod("hashCode")));
	}

	@Test
	public void testIsObjectToString() throws NoSuchMethodException {
		Assert.assertTrue(
			JMXProxyUtil.isObjectToString(Object.class.getMethod("toString")));
		Assert.assertFalse(
			JMXProxyUtil.isObjectToString(Object.class.getMethod("wait")));

		class OverrideClass {

			@Override
			public String toString() {
				return super.toString();
			}

		}

		Assert.assertFalse(
			JMXProxyUtil.isObjectToString(
				OverrideClass.class.getMethod("toString")));
	}

	@Test
	public void testIsOptional() throws NoSuchMethodException {
		class TestOptional {

			@Optional
			public void method1() {
			}

			@SuppressWarnings("unused")
			public void method2() {
			}

		}

		Assert.assertTrue(
			JMXProxyUtil.isOptional(TestOptional.class.getMethod("method1")));
		Assert.assertFalse(
			JMXProxyUtil.isOptional(TestOptional.class.getMethod("method2")));
	}

	@Test
	public void testIsSetter() {
		Assert.assertTrue(JMXProxyUtil.isSetter("setXYZ", Object.class));
		Assert.assertFalse(JMXProxyUtil.isSetter("setXYZ"));
		Assert.assertFalse(JMXProxyUtil.isSetter("SetXYZ", Object.class));
	}

	@Test
	public void testJMXProxyInvocationHandlerEquals() throws Throwable {
		JMXProxyInvocationHandler jmxProxyInvocationHandler =
			new JMXProxyInvocationHandler(
				_testClassObjectName, _processCallableExecutor);

		Assert.assertTrue(
			(boolean)jmxProxyInvocationHandler.invoke(
				null, Object.class.getMethod("equals", Object.class),
				new Object[] {
					new PlatformManagedObject() {

						@Override
						public ObjectName getObjectName() {
							return _testClassObjectName;
						}

					}
				}));
	}

	@Test
	public void testJMXProxyInvocationHandlerHashCode() throws Throwable {
		JMXProxyInvocationHandler jmxProxyInvocationHandler =
			new JMXProxyInvocationHandler(
				_testClassObjectName, _processCallableExecutor);

		Assert.assertEquals(
			_testClassObjectName.hashCode(),
			(int)jmxProxyInvocationHandler.invoke(
				null, Object.class.getMethod("hashCode"), new Object[0]));
	}

	@Test
	public void testJMXProxyInvocationHandlerToString() throws Throwable {
		JMXProxyInvocationHandler jmxProxyInvocationHandler =
			new JMXProxyInvocationHandler(
				_testClassObjectName, _processCallableExecutor);

		Assert.assertEquals(
			_testClassObjectName.toString(),
			jmxProxyInvocationHandler.invoke(
				null, Object.class.getMethod("toString"), new Object[0]));
	}

	@Test
	public void testNewProxySystemClass() {
		CompilationMXBean compilationMXBean =
			ManagementFactory.getCompilationMXBean();

		CompilationMXBean compilationMXBeanProxy = JMXProxyUtil.newProxy(
			compilationMXBean.getObjectName(), CompilationMXBean.class,
			_processCallableExecutor);

		Assert.assertEquals(
			compilationMXBean.getName(), compilationMXBeanProxy.getName());
		Assert.assertEquals(
			compilationMXBean.getObjectName(),
			compilationMXBeanProxy.getObjectName());
		Assert.assertEquals(
			compilationMXBean.isCompilationTimeMonitoringSupported(),
			compilationMXBeanProxy.isCompilationTimeMonitoringSupported());
	}

	@Test
	public void testNewProxyUserClass() {
		TestClassMXBean testClassMXBean = JMXProxyUtil.newProxy(
			_testClassObjectName, TestClassMXBean.class,
			_processCallableExecutor);

		Assert.assertEquals(_testClass.getName(), testClassMXBean.getName());

		String newName = "newName";

		testClassMXBean.setName(newName);

		Assert.assertEquals(newName, testClassMXBean.getName());

		Assert.assertEquals(
			"doSomething", testClassMXBean.doSomething("doSomething"));
	}

	@Test
	public void testOperationProcessCallableFail() {
		OperationProcessCallable operationProcessCallable =
			new OperationProcessCallable(
				_unknowObjectName, "doSomething", new Object[] {"doSomething"},
				new String[] {String.class.getName()});

		try {
			operationProcessCallable.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertSame(
				InstanceNotFoundException.class, throwable.getClass());
		}
	}

	@Test
	public void testOperationProcessCallableSuccess() throws ProcessException {
		OperationProcessCallable operationProcessCallable =
			new OperationProcessCallable(
				_testClassObjectName, "doSomething",
				new Object[] {"doSomething"},
				new String[] {String.class.getName()});

		Assert.assertEquals("doSomething", operationProcessCallable.call());
	}

	@Test
	public void testSetAttributeProcessCallableFailureInstanceNotFound() {
		SetAttributeProcessCallable setAttributeProcessCallable =
			new SetAttributeProcessCallable(
				_unknowObjectName, "Name", "newName", true);

		try {
			setAttributeProcessCallable.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertSame(
				InstanceNotFoundException.class, throwable.getClass());
		}
	}

	@Test
	public void testSetAttributeProcessCallableFailureOptional()
		throws ProcessException {

		String oldName = _testClass.getName();

		SetAttributeProcessCallable setAttributeProcessCallable =
			new SetAttributeProcessCallable(
				_testClassObjectName, "NameX", "newName", true);

		Assert.assertNull(setAttributeProcessCallable.call());

		Assert.assertEquals(oldName, _testClass.getName());
	}

	@Test
	public void testSetAttributeProcessCallableFailureRequired() {
		SetAttributeProcessCallable setAttributeProcessCallable =
			new SetAttributeProcessCallable(
				_testClassObjectName, "NameX", "newName", false);

		try {
			setAttributeProcessCallable.call();

			Assert.fail();
		}
		catch (ProcessException pe) {
			Throwable throwable = pe.getCause();

			Assert.assertSame(
				AttributeNotFoundException.class, throwable.getClass());
		}
	}

	@Test
	public void testSetAttributeProcessCallableSuccess()
		throws ProcessException {

		String newName = "newName";

		SetAttributeProcessCallable setAttributeProcessCallable =
			new SetAttributeProcessCallable(
				_testClassObjectName, "Name", newName, true);

		Assert.assertNull(setAttributeProcessCallable.call());

		Assert.assertEquals(newName, _testClass.getName());
	}

	public interface TestClassMXBean {

		public String doSomething(String s);

		public String getName();

		public boolean isValid();

		public void setName(String name);

	}

	protected static void assertEquals(LockInfo lockInfo1, LockInfo lockInfo2) {
		if (lockInfo1 == lockInfo2) {
			return;
		}

		Assert.assertEquals(lockInfo1.getClassName(), lockInfo2.getClassName());
		Assert.assertEquals(
			lockInfo1.getIdentityHashCode(), lockInfo2.getIdentityHashCode());
	}

	protected static void assertEquals(
		LockInfo[] lockInfos1, LockInfo[] lockInfos2) {

		Assert.assertEquals(
			Arrays.toString(lockInfos2), lockInfos1.length, lockInfos2.length);

		for (int i = 0; i < lockInfos1.length; i++) {
			assertEquals(lockInfos1[i], lockInfos2[i]);
		}
	}

	protected static void assertEquals(
		MonitorInfo[] monitorInfos1, MonitorInfo[] monitorInfos2) {

		Assert.assertEquals(
			Arrays.toString(monitorInfos2), monitorInfos1.length,
			monitorInfos2.length);

		for (int i = 0; i < monitorInfos1.length; i++) {
			Assert.assertEquals(
				monitorInfos1[i].getClassName(),
				monitorInfos2[i].getClassName());
			Assert.assertEquals(
				monitorInfos1[i].getIdentityHashCode(),
				monitorInfos2[i].getIdentityHashCode());
			Assert.assertEquals(
				monitorInfos1[i].getLockedStackDepth(),
				monitorInfos2[i].getLockedStackDepth());
			Assert.assertEquals(
				monitorInfos1[i].getLockedStackFrame(),
				monitorInfos2[i].getLockedStackFrame());
		}
	}

	protected static void assertEquals(
		ThreadInfo[] threadInfos1, ThreadInfo[] threadInfos2) {

		Assert.assertEquals(
			Arrays.toString(threadInfos2), threadInfos1.length,
			threadInfos2.length);

		for (int i = 0; i < threadInfos1.length; i++) {
			Assert.assertEquals(
				threadInfos1[i].toString(), threadInfos2[i].toString());
		}
	}

	protected static LockInfo createLockInfo(CompositeData compositeData) {
		if (compositeData == null) {
			return null;
		}

		return new LockInfo(
			(String)compositeData.get("className"),
			(int)compositeData.get("identityHashCode"));
	}

	protected static LockInfo[] createLockInfos(
		CompositeData[] compositeDatas) {

		LockInfo[] lockInfos = new LockInfo[compositeDatas.length];

		for (int i = 0; i < compositeDatas.length; i++) {
			lockInfos[i] = createLockInfo(compositeDatas[i]);
		}

		return lockInfos;
	}

	protected static MonitorInfo[] createMonitorInfos(
		CompositeData[] compositeDatas) {

		MonitorInfo[] monitorInfos = new MonitorInfo[compositeDatas.length];

		for (int i = 0; i < compositeDatas.length; i++) {
			monitorInfos[i] = MonitorInfo.from(compositeDatas[i]);
		}

		return monitorInfos;
	}

	protected static ObjectName createObjectName(String name) {
		try {
			return new ObjectName(name);
		}
		catch (MalformedObjectNameException mone) {
			return ReflectionUtil.throwException(mone);
		}
	}

	protected static StackTraceElement[] createStackTraceElements(
		CompositeData[] compositeDatas) {

		StackTraceElement[] stackTraceElements =
			new StackTraceElement[compositeDatas.length];

		for (int i = 0; i < compositeDatas.length; i++) {
			stackTraceElements[i] = new StackTraceElement(
				(String)compositeDatas[i].get("className"),
				(String)compositeDatas[i].get("methodName"),
				(String)compositeDatas[i].get("fileName"),
				(int)compositeDatas[i].get("lineNumber"));
		}

		return stackTraceElements;
	}

	protected static ThreadInfo[] createThreadInfos(
		CompositeData[] compositeDatas) {

		ThreadInfo[] threadInfos = new ThreadInfo[compositeDatas.length];

		for (int i = 0; i < compositeDatas.length; i++) {
			threadInfos[i] = ThreadInfo.from(compositeDatas[i]);
		}

		return threadInfos;
	}

	private final ProcessCallableExecutor _processCallableExecutor =
		new ProcessCallableExecutor() {

			@Override
			public <V extends Serializable> NoticeableFuture<V> execute(
				ProcessCallable<V> processCallable) {

				DefaultNoticeableFuture<V> defaultNoticeableFuture =
					new DefaultNoticeableFuture<>();

				try {
					defaultNoticeableFuture.set(processCallable.call());
				}
				catch (ProcessException pe) {
					defaultNoticeableFuture.setException(pe);
				}

				return defaultNoticeableFuture;
			}

		};

	private final TestClass _testClass = new TestClass(
		TestClass.class.getName());
	private final ObjectName _testClassObjectName = createObjectName(
		"com.liferay:type=TestClass");
	private final ObjectName _unknowObjectName = createObjectName(
		"com.liferay:type=Unknown");

	private static class TestClass implements TestClassMXBean {

		public TestClass(String name) {
			_name = name;
		}

		@Override
		public String doSomething(String s) {
			return s;
		}

		@Override
		public String getName() {
			return _name;
		}

		@Override
		public boolean isValid() {
			return true;
		}

		@Override
		public void setName(String name) {
			_name = name;
		}

		private String _name;

	}

}