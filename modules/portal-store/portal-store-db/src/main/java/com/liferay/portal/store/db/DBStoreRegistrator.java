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
import com.liferay.portal.kernel.dao.db.DBManagerUtil;
import com.liferay.portal.kernel.dao.db.DBType;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.module.framework.ModuleServiceLifecycle;
import com.liferay.portal.kernel.transaction.Propagation;
import com.liferay.portal.kernel.transaction.Transactional;
import com.liferay.portal.kernel.util.ClassLoaderUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.aop.MethodInterceptorInvocationHandler;
import com.liferay.portlet.documentlibrary.store.BaseStore;
import com.liferay.portlet.documentlibrary.store.Store;
import com.liferay.portlet.documentlibrary.store.TempFileMethodInterceptor;

import java.io.File;
import java.io.InputStream;

import java.util.Arrays;
import java.util.Dictionary;
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

	@Reference(unbind = "-")
	public void setDBStore(DBStore dbStore) {
		_dbStore = dbStore;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		Dictionary<String, String> properties = new Hashtable<>();

		properties.put("store.type", "com.liferay.portal.store.db.DBStore");

		_dbStore = prepare(_dbStore);

		_serviceRegistration = bundleContext.registerService(
			Store.class, _dbStore, properties);
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
	}

	protected Store prepare(Store store) {
		DB db = DBManagerUtil.getDB();

		if (db.getDBType() != DBType.POSTGRESQL) {
			return store;
		}

		ClassLoader classLoader = ClassLoaderUtil.getPortalClassLoader();

		MethodInterceptor transactionAdviceMethodInterceptor =
			(MethodInterceptor)PortalBeanLocatorUtil.locate(
				"transactionAdvice");

		List<MethodInterceptor> methodInterceptors = Arrays.asList(
			transactionAdviceMethodInterceptor,
			new TempFileMethodInterceptor());

		store = (Store)ProxyUtil.newProxyInstance(
			classLoader, new Class<?>[] {Store.class},
			new MethodInterceptorInvocationHandler(store, methodInterceptors));

		return new DelegatorDBStore(store);
	}

	@Reference(target = ModuleServiceLifecycle.PORTAL_INITIALIZED, unbind = "-")
	protected void setModuleServiceLifecycle(
		ModuleServiceLifecycle moduleServiceLifecycle) {
	}

	private Store _dbStore;
	private ServiceRegistration<Store> _serviceRegistration;

	/**
	 * This class must only delegate the methods that DBStore overrides.
	 */
	private static class DelegatorDBStore extends BaseStore {

		public DelegatorDBStore(Store store) {
			_store = store;
		}

		@Override
		public void addDirectory(
			long companyId, long repositoryId, String dirName) {

			_store.addDirectory(companyId, repositoryId, dirName);
		}

		@Override
		public void addFile(
				long companyId, long repositoryId, String fileName,
				byte[] bytes)
			throws PortalException {

			_store.addFile(companyId, repositoryId, fileName, bytes);
		}

		@Override
		public void addFile(
				long companyId, long repositoryId, String fileName, File file)
			throws PortalException {

			_store.addFile(companyId, repositoryId, fileName, file);
		}

		@Override
		public void addFile(
				long companyId, long repositoryId, String fileName,
				InputStream inputStream)
			throws PortalException {

			_store.addFile(companyId, repositoryId, fileName, inputStream);
		}

		@Override
		public void checkRoot(long companyId) {
			_store.checkRoot(companyId);
		}

		@Override
		public void deleteDirectory(
			long companyId, long repositoryId, String dirName) {

			_store.deleteDirectory(companyId, repositoryId, dirName);
		}

		@Override
		public void deleteFile(
			long companyId, long repositoryId, String fileName) {

			_store.deleteFile(companyId, repositoryId, fileName);
		}

		@Override
		public void deleteFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel) {

			_store.deleteFile(companyId, repositoryId, fileName, versionLabel);
		}

		@Override
		@Transactional(propagation = Propagation.REQUIRED)
		public InputStream getFileAsStream(
				long companyId, long repositoryId, String fileName)
			throws PortalException {

			return _store.getFileAsStream(companyId, repositoryId, fileName);
		}

		@Override
		@Transactional(propagation = Propagation.REQUIRED)
		public InputStream getFileAsStream(
				long companyId, long repositoryId, String fileName,
				String versionLabel)
			throws PortalException {

			return _store.getFileAsStream(
				companyId, repositoryId, fileName, versionLabel);
		}

		@Override
		public String[] getFileNames(long companyId, long repositoryId) {
			return _store.getFileNames(companyId, repositoryId);
		}

		@Override
		public String[] getFileNames(
			long companyId, long repositoryId, String dirName) {

			return _store.getFileNames(companyId, repositoryId, dirName);
		}

		@Override
		public long getFileSize(
				long companyId, long repositoryId, String fileName)
			throws PortalException {

			return _store.getFileSize(companyId, repositoryId, fileName);
		}

		@Override
		public boolean hasDirectory(
			long companyId, long repositoryId, String dirName) {

			return _store.hasDirectory(companyId, repositoryId, dirName);
		}

		@Override
		public boolean hasFile(
			long companyId, long repositoryId, String fileName,
			String versionLabel) {

			return _store.hasFile(
				companyId, repositoryId, fileName, versionLabel);
		}

		@Override
		public void updateFile(
				long companyId, long repositoryId, long newRepositoryId,
				String fileName)
			throws PortalException {

			_store.updateFile(
				companyId, repositoryId, newRepositoryId, fileName);
		}

		@Override
		public void updateFile(
				long companyId, long repositoryId, String fileName,
				String newFileName)
			throws PortalException {

			_store.updateFile(companyId, repositoryId, fileName, newFileName);
		}

		@Override
		public void updateFile(
				long companyId, long repositoryId, String fileName,
				String versionLabel, byte[] bytes)
			throws PortalException {

			_store.updateFile(
				companyId, repositoryId, fileName, versionLabel, bytes);
		}

		@Override
		public void updateFile(
				long companyId, long repositoryId, String fileName,
				String versionLabel, File file)
			throws PortalException {

			_store.updateFile(
				companyId, repositoryId, fileName, versionLabel, file);
		}

		@Override
		public void updateFile(
				long companyId, long repositoryId, String fileName,
				String versionLabel, InputStream inputStream)
			throws PortalException {

			_store.updateFile(
				companyId, repositoryId, fileName, versionLabel, inputStream);
		}

		private final Store _store;

	}

}