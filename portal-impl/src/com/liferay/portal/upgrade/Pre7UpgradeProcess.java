/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.upgrade;

import com.liferay.counter.kernel.model.Counter;
import com.liferay.counter.kernel.service.CounterLocalService;
import com.liferay.counter.kernel.service.persistence.CounterFinder;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.ResourcePermission;
import com.liferay.portal.kernel.upgrade.UpgradeException;
import com.liferay.portal.kernel.upgrade.UpgradeProcess;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.AopInvocationHandler;

import java.io.Closeable;
import java.io.IOException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public abstract class Pre7UpgradeProcess extends UpgradeProcess {

	@Override
	public void upgrade() throws UpgradeException {
		try (Closeable closeable = _injectFieldProxy(
				PortalBeanLocatorUtil.locate(
					CounterLocalService.class.getName()),
				"counterFinder", Pre7CounterFinderImpl.class)) {

			super.upgrade();
		}
		catch (UpgradeException upgradeException) {
			throw upgradeException;
		}
		catch (Exception exception) {
			throw new UpgradeException(exception);
		}
	}

	private Closeable _injectFieldProxy(
			Object springServiceProxy, String fieldName, Class<?> wrapperClass)
		throws Exception {

		AopInvocationHandler aopInvocationHandler =
			ProxyUtil.fetchInvocationHandler(
				springServiceProxy, AopInvocationHandler.class);

		Object target = aopInvocationHandler.getTarget();

		Class<?> clazz = target.getClass();

		Field field = null;

		while (clazz != null) {
			try {
				field = ReflectionUtil.getDeclaredField(clazz, fieldName);

				break;
			}
			catch (NoSuchFieldException noSuchFieldException) {
				if (_log.isDebugEnabled()) {
					_log.debug(noSuchFieldException);
				}

				clazz = clazz.getSuperclass();
			}
		}

		if (field == null) {
			throw new IllegalArgumentException(
				StringBundler.concat(
					"Unable to locate field ", fieldName, " in ", target));
		}

		final Field finalField = field;

		Object previousValue = finalField.get(target);

		Constructor<?> constructor = wrapperClass.getDeclaredConstructor(
			finalField.getType());

		constructor.setAccessible(true);

		finalField.set(target, constructor.newInstance(previousValue));

		return new Closeable() {

			@Override
			public void close() throws IOException {
				try {
					finalField.set(target, previousValue);
				}
				catch (ReflectiveOperationException
							reflectiveOperationException) {

					throw new IOException(reflectiveOperationException);
				}
			}

		};
	}

	private static final Log _log = LogFactoryUtil.getLog(
		Pre7UpgradeProcess.class);

	private static class Pre7CounterFinderImpl implements CounterFinder {

		@Override
		public List<String> getNames() {
			return _counterFinder.getNames();
		}

		@Override
		public String getRegistryName() {
			return _counterFinder.getRegistryName();
		}

		@Override
		public long increment() {
			return _counterFinder.increment(
				"com.liferay.counter.model.Counter");
		}

		@Override
		public long increment(String name) {
			if (name.equals(Counter.class.getName())) {
				name = "com.liferay.counter.model.Counter";
			}
			else if (name.equals(ResourcePermission.class.getName())) {
				name = "com.liferay.portal.model.ResourcePermission";
			}

			return _counterFinder.increment(name);
		}

		@Override
		public long increment(String name, int size) {
			if (name.equals(Counter.class.getName())) {
				name = "com.liferay.counter.model.Counter";
			}
			else if (name.equals(ResourcePermission.class.getName())) {
				name = "com.liferay.portal.model.ResourcePermission";
			}

			return _counterFinder.increment(name, size);
		}

		@Override
		public void invalidate() {
			_counterFinder.invalidate();
		}

		@Override
		public void rename(String oldName, String newName) {
			_counterFinder.rename(oldName, newName);
		}

		@Override
		public void reset(String name) {
			_counterFinder.reset(name);
		}

		@Override
		public void reset(String name, long size) {
			_counterFinder.reset(name, size);
		}

		private Pre7CounterFinderImpl(CounterFinder counterFinder) {
			_counterFinder = counterFinder;
		}

		private final CounterFinder _counterFinder;

	}

}