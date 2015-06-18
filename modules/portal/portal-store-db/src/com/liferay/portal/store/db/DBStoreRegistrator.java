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

package com.liferay.portal.store.db;

import com.liferay.portal.kernel.bean.PortalBeanLocatorUtil;
import com.liferay.portal.kernel.dao.db.DB;
import com.liferay.portal.kernel.dao.db.DBFactoryUtil;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.MethodInterceptorInvocationHandler;
import com.liferay.portlet.documentlibrary.store.Store;
import com.liferay.portlet.documentlibrary.store.TempFileMethodInterceptor;

import java.util.Arrays;
import java.util.Hashtable;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Carlos Sierra Andrés
 */
@Component(immediate = true)
public class DBStoreRegistrator {

	@Reference
	public void setDBStore(DBStore dbStore) {
		_dbStore = dbStore;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		Hashtable<String, String> properties = new Hashtable<>();

		properties.put("store.type", "com.liferay.portal.store.db.DBStore");

		_dbStore = _wrapDatabaseStore(_dbStore);

		_storeServiceRegistration = bundleContext.registerService(
			Store.class, _dbStore, properties);
	}

	@Deactivate
	protected void deactivate() {
		_storeServiceRegistration.unregister();
	}

	private Store _wrapDatabaseStore(Store store) {
		DB db = DBFactoryUtil.getDB();

		String dbType = db.getType();

		if (!dbType.equals(DB.TYPE_POSTGRESQL)) {
			return store;
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		MethodInterceptor transactionAdviceMethodInterceptor =
			(MethodInterceptor)PortalBeanLocatorUtil.locate(
				"transactionAdvice");

		MethodInterceptor tempFileMethodInterceptor =
			new TempFileMethodInterceptor();

		List<MethodInterceptor> methodInterceptors = Arrays.asList(
			transactionAdviceMethodInterceptor, tempFileMethodInterceptor);

		store = (Store)ProxyUtil.newProxyInstance(
			classLoader, new Class<?>[] {Store.class},
			new MethodInterceptorInvocationHandler(store, methodInterceptors));
	}

	private Store _dbStore;
	private ServiceRegistration<Store> _storeServiceRegistration;

}