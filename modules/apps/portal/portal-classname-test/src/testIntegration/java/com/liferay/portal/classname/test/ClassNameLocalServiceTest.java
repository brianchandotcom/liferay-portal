/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.classname.test;

import com.liferay.arquillian.extension.junit.bridge.junit.Arquillian;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.kernel.dao.jdbc.DataAccess;
import com.liferay.portal.kernel.model.BaseModelListener;
import com.liferay.portal.kernel.model.ClassName;
import com.liferay.portal.kernel.model.CompanyConstants;
import com.liferay.portal.kernel.model.ModelListener;
import com.liferay.portal.kernel.security.auth.CompanyInheritableThreadLocalCallable;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.test.ReflectionTestUtil;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.TransactionConfig;
import com.liferay.portal.kernel.transaction.TransactionInvokerUtil;
import com.liferay.portal.kernel.util.PortalClassLoaderUtil;
import com.liferay.portal.kernel.util.PropsValues;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.test.log.LogCapture;
import com.liferay.portal.test.log.LogEntry;
import com.liferay.portal.test.log.LoggerTestUtil;
import com.liferay.portal.test.rule.Inject;
import com.liferay.portal.test.rule.LiferayIntegrationTestRule;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.FutureTask;

import org.junit.Assert;
import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * @author Shuyang Zhou
 */
@RunWith(Arquillian.class)
public class ClassNameLocalServiceTest {

	@ClassRule
	@Rule
	public static final LiferayIntegrationTestRule liferayIntegrationTestRule =
		new LiferayIntegrationTestRule();

	@Test
	public void testGetClassName() throws Throwable {
		_testGetClassName(true);
		_testGetClassName(false);
	}

	private void _deleteClassName(String value) {
		ClassName className = _classNameLocalService.fetchClassName(value);

		if (className.getClassNameId() > 0) {
			_classNameLocalService.deleteClassName(className);
		}
	}

	private boolean _hasClassName(long classNameId) throws Exception {
		try (Connection connection = DataAccess.getConnection();

			PreparedStatement preparedStatement = connection.prepareStatement(
				"select classNameId from ClassName_ where classNameId = ?")) {

			preparedStatement.setLong(1, classNameId);

			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next();
			}
		}
	}

	private void _pause(
		CountDownLatch pausedCountDownLatch,
		CountDownLatch resumeCountDownLatch) {

		pausedCountDownLatch.countDown();

		try {
			resumeCountDownLatch.await();
		}
		catch (InterruptedException interruptedException) {
			ReflectionUtil.throwException(interruptedException);
		}
	}

	private void _testGetClassName(boolean pauseBeforeInsert) throws Throwable {
		String value = ClassNameLocalServiceTest.class.getName();

		_deleteClassName(value);

		ClassLoader classLoader = PortalClassLoaderUtil.getClassLoader();

		Class<?> classNamePoolClass = classLoader.loadClass(
			"com.liferay.portal.service.impl.ClassNameLocalServiceImpl$" +
				"ClassNamePool");

		Map<Long, Map<String, Long>> classNameIdsMap =
			ReflectionTestUtil.getFieldValue(
				classNamePoolClass, "_classNameIdsMap");

		Long companyId = CompanyConstants.SYSTEM;

		if (PropsValues.DATABASE_PARTITION_ENABLED) {
			companyId = CompanyThreadLocal.getNonsystemCompanyId();
		}

		Map<String, Long> classNameIds = classNameIdsMap.get(companyId);

		FutureTask<ClassName> futureTask = new FutureTask<>(
			new CompanyInheritableThreadLocalCallable<>(
				() -> _classNameLocalService.getClassName(value)));

		Thread thread = new Thread(futureTask, "Class Name Local Service Test");

		CountDownLatch pausedCountDownLatch = new CountDownLatch(1);
		CountDownLatch resumeCountDownLatch = new CountDownLatch(1);

		Bundle bundle = FrameworkUtil.getBundle(
			ClassNameLocalServiceTest.class);

		BundleContext bundleContext = bundle.getBundleContext();

		ServiceRegistration<ModelListener<ClassName>> serviceRegistration =
			bundleContext.registerService(
				(Class<ModelListener<ClassName>>)(Class<?>)ModelListener.class,
				new BaseModelListener<ClassName>() {

					@Override
					public void onBeforeCreate(ClassName className) {
						if (pauseBeforeInsert &&
							value.equals(className.getValue()) &&
							(Thread.currentThread() == thread)) {

							_pause(pausedCountDownLatch, resumeCountDownLatch);
						}
					}

				},
				null);

		try (LogCapture logCapture = LoggerTestUtil.configureLog4JLogger(
				"org.hibernate.engine.jdbc.spi.SqlExceptionHelper",
				LoggerTestUtil.ERROR)) {

			Set<Long> pooledClassNameIds = ConcurrentHashMap.newKeySet();
			Set<Long> uncommittedClassNameIds = ConcurrentHashMap.newKeySet();

			classNameIdsMap.put(
				companyId,
				(Map<String, Long>)ProxyUtil.newDelegateProxyInstance(
					classLoader, Map.class,
					new Object() {

						public Object put(Object key, Object putValue)
							throws Exception {

							if (value.equals(key)) {
								if (!pauseBeforeInsert &&
									(Thread.currentThread() == thread)) {

									_pause(
										pausedCountDownLatch,
										resumeCountDownLatch);
								}

								long classNameId = (Long)putValue;

								pooledClassNameIds.add(classNameId);

								if (!_hasClassName(classNameId)) {
									uncommittedClassNameIds.add(classNameId);
								}
							}

							return classNameIds.put(
								(String)key, (Long)putValue);
						}

					},
					classNameIds));

			thread.start();

			pausedCountDownLatch.await();

			ClassName className = TransactionInvokerUtil.invoke(
				_transactionConfig,
				() -> _classNameLocalService.getClassName(value));

			resumeCountDownLatch.countDown();

			ClassName pausedClassName = futureTask.get();

			Assert.assertEquals(
				className.getClassNameId(), pausedClassName.getClassNameId());

			Assert.assertEquals(
				Collections.singleton(className.getClassNameId()),
				pooledClassNameIds);
			Assert.assertTrue(
				uncommittedClassNameIds.toString(),
				uncommittedClassNameIds.isEmpty());

			List<LogEntry> logEntries = logCapture.getLogEntries();

			Assert.assertEquals(
				logEntries.toString(), pauseBeforeInsert,
				!logEntries.isEmpty());
		}
		finally {
			resumeCountDownLatch.countDown();

			thread.join();

			classNameIdsMap.put(companyId, classNameIds);

			serviceRegistration.unregister();

			_deleteClassName(value);
		}
	}

	private static final TransactionConfig _transactionConfig =
		TransactionConfig.Factory.create(
			Propagation.REQUIRED, new Class<?>[] {Exception.class});

	@Inject
	private ClassNameLocalService _classNameLocalService;

}