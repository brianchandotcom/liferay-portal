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

package com.liferay.portlet.documentlibrary.store;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ClassUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.spring.aop.MethodInterceptorInvocationHandler;
import com.liferay.portal.util.ClassLoaderUtil;
import com.liferay.portal.util.PropsUtil;
import com.liferay.portal.util.PropsValues;

import java.util.Arrays;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;

/**
 * @author Brian Wing Shun Chan
 * @author Shuyang Zhou
 */
public class StoreFactory {

	public static void checkProperties() {
		if (_warned) {
			return;
		}

		String dlHookImpl = PropsUtil.get("dl.hook.impl");

		if (Validator.isNull(dlHookImpl)) {
			_warned = true;

			return;
		}

		boolean found = false;

		for (String[] dlHookStoreParts : _DL_HOOK_STORES) {
			if (dlHookImpl.equals(dlHookStoreParts[0])) {
				PropsValues.DL_STORE_IMPL = dlHookStoreParts[1];

				found = true;

				break;
			}
		}

		if (!found) {
			PropsValues.DL_STORE_IMPL = dlHookImpl;
		}

		if (_log.isWarnEnabled()) {
			StringBundler sb = new StringBundler(13);

			sb.append("Liferay is configured with the legacy ");
			sb.append("property \"dl.hook.impl=");
			sb.append(dlHookImpl);
			sb.append("\" ");
			sb.append("in portal-ext.properties. Please reconfigure ");
			sb.append("to use the new property \"");
			sb.append(PropsKeys.DL_STORE_IMPL);
			sb.append("\". Liferay will ");
			sb.append("attempt to temporarily set \"");
			sb.append(PropsKeys.DL_STORE_IMPL);
			sb.append("=");
			sb.append(PropsValues.DL_STORE_IMPL);
			sb.append("\".");

			_log.warn(sb.toString());
		}

		_warned = true;
	}

	public static Store getInstance() {
		if (_store == null) {
			checkProperties();

			if (_log.isDebugEnabled()) {
				_log.debug("Instantiate " + PropsValues.DL_STORE_IMPL);
			}

			try {
				_store = _getInstance();
			}
			catch (Exception e) {
				_log.error(e, e);
			}
		}

		if ((_store != null) && _log.isDebugEnabled()) {
			Class<?> clazz = _store.getClass();

			_log.debug("Return " + clazz.getName());
		}

		return _store;
	}

	public static void setInstance(Store store) {
		if (_log.isDebugEnabled()) {
			_log.debug("Set " + ClassUtil.getClassName(store));
		}

		_store = store;
	}

	private static Store _getInstance() throws Exception {
		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		Store store = (Store)InstanceFactory.newInstance(
			classLoader, PropsValues.DL_STORE_IMPL);

		if (!(store instanceof DBStore)) {
			return store;
		}

		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (dbType.equals(DB.TYPE_POSTGRESQL)) {
			MethodInterceptor transactionAdviceMethodInterceptor =
				(MethodInterceptor)PortalBeanLocatorUtil.locate(
					"transactionAdvice");

			MethodInterceptor tempFileMethodInterceptor =
				new TempFileMethodInterceptor();

			List<MethodInterceptor> methodInterceptors = Arrays.asList(
				transactionAdviceMethodInterceptor, tempFileMethodInterceptor);

			store = (Store)ProxyUtil.newProxyInstance(
				classLoader, new Class<?>[] {Store.class},
				new MethodInterceptorInvocationHandler(
					store, methodInterceptors));
		}

		return store;
	}

	private static final String[][] _DL_HOOK_STORES = new String[][] {
		new String[] {
			"com.liferay.documentlibrary.util.AdvancedFileSystemHook",
			AdvancedFileSystemStore.class.getName()
		},
		new String[] {
			"com.liferay.documentlibrary.util.CMISHook",
			CMISStore.class.getName()
		},
		new String[] {
			"com.liferay.documentlibrary.util.FileSystemHook",
			FileSystemStore.class.getName()
		},
		new String[] {
			"com.liferay.documentlibrary.util.JCRHook", JCRStore.class.getName()
		},
		new String[] {
			"com.liferay.documentlibrary.util.S3Hook", S3Store.class.getName()
		}
	};

	private static final Log _log = LogFactoryUtil.getLog(StoreFactory.class);

	private static Store _store;
	private static boolean _warned;

}