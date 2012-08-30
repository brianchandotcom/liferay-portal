/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.convert;

import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.CharPool;
import com.liferay.portal.kernel.util.FileUtil;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.CompanyConstants;
import com.liferay.portal.security.pacl.PACLClassLoaderUtil;
import com.liferay.portal.util.MaintenanceUtil;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portal.util.PropsValues;
import com.liferay.portlet.documentlibrary.store.AdvancedFileSystemStore;
import com.liferay.portlet.documentlibrary.store.CMISStore;
import com.liferay.portlet.documentlibrary.store.DBStore;
import com.liferay.portlet.documentlibrary.store.FileSystemStore;
import com.liferay.portlet.documentlibrary.store.JCRStore;
import com.liferay.portlet.documentlibrary.store.S3Store;
import com.liferay.portlet.documentlibrary.store.Store;
import com.liferay.portlet.documentlibrary.store.StoreFactory;

import java.io.InputStream;

import java.util.List;

/**
 * @author Minhchau Dang
 * @author Alexander Chow
 * @author Preston Crary
 */
public class ConvertDocumentLibrary extends ConvertProcess {

	@Override
	public String getDescription() {
		return "migrate-documents-from-one-repository-to-another";
	}

	@Override
	public String getParameterDescription() {
		return "please-select-a-new-repository-hook";
	}

	@Override
	public String[] getParameterNames() {
		StringBundler sb = new StringBundler(_HOOKS.length * 2 + 2);

		sb.append(PropsKeys.DL_STORE_IMPL);
		sb.append(StringPool.EQUAL);

		for (String hook : _HOOKS) {
			if (!hook.equals(PropsValues.DL_STORE_IMPL)) {
				sb.append(hook);
				sb.append(StringPool.SEMICOLON);
			}
		}

		return new String[] {sb.toString()};
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	protected void doConvert() throws Exception {
		_sourceStore = StoreFactory.getInstance();

		String[] values = getParameterValues();

		String targetStoreClassName = values[0];

		ClassLoader classLoader = PACLClassLoaderUtil.getPortalClassLoader();

		_targetStore = (Store)classLoader.loadClass(
			targetStoreClassName).newInstance();

		migrateStore();

		StoreFactory.setInstance(_targetStore);

		MaintenanceUtil.appendStatus(
			"Please set " + PropsKeys.DL_STORE_IMPL +
				" in your portal-ext.properties to use " +
					targetStoreClassName);

		PropsValues.DL_STORE_IMPL = targetStoreClassName;
	}

	protected void migrateDirectories(
		long companyId, long repositoryId, String[] dirNames) throws Exception {

		for (String dirName : dirNames) {
			String[] subDirNames = _sourceStore.getFileNames(
				companyId, repositoryId, dirName);

			if (subDirNames.length == 0) {
				String path = dirName;

				if (_sourceStore instanceof AdvancedFileSystemStore) {
					path = unbuildPath(companyId, repositoryId, dirName);
				}

				migrateFile(companyId, repositoryId, dirName, path);
			}
			else {
				migrateDirectories(companyId, repositoryId, subDirNames);
			}
		}
	}

	protected void migrateFile(
		long companyId, long repositoryId, String oldPath, String newPath) {

		try {
			String[] fileVersions = _sourceStore.getFileVersions(
				companyId, repositoryId, oldPath);

			for (String fileVersion : fileVersions) {
				InputStream is = _sourceStore.getFileAsStream(
					companyId, repositoryId, oldPath, fileVersion);

				if (fileVersion.equals(Store.VERSION_DEFAULT)) {
					_targetStore.addFile(companyId, repositoryId, newPath, is);
				}
				else {
					_targetStore.updateFile(
						companyId, repositoryId, newPath, fileVersion, is);
				}
			}
		}
		catch (Exception e) {
			_log.error("Migration failed for " + oldPath, e);
		}
	}

	protected void migrateStore() throws Exception {
		long[] companyIds = PortalUtil.getCompanyIds();

		companyIds = ArrayUtil.append(companyIds, CompanyConstants.SYSTEM);

		for (long companyId : companyIds) {
			List<Long> repositoryIds = _sourceStore.getRepositoryIds(companyId);

			for (long repositoryId : repositoryIds) {
				String[] dirNames = _sourceStore.getFileNames(
					companyId, repositoryId);

				migrateDirectories(companyId, repositoryId, dirNames);
			}
		}
	}

	protected String unbuildPath(
		long companyId, long repositoryId, String path) {

		if (companyId != CompanyConstants.SYSTEM) {
			if (repositoryId == CompanyConstants.SYSTEM) {
				return path;
			}

			path = FileUtil.stripExtension(path);
		}

		int pos = path.lastIndexOf(CharPool.SLASH);

		if (pos != -1) {
			path = path.substring(pos);
		}

		return path;
	}

	private static final String[] _HOOKS = new String[] {
		AdvancedFileSystemStore.class.getName(), CMISStore.class.getName(),
		DBStore.class.getName(), FileSystemStore.class.getName(),
		JCRStore.class.getName(), S3Store.class.getName()
	};

	private static Log _log = LogFactoryUtil.getLog(
		ConvertDocumentLibrary.class);

	private Store _sourceStore;
	private Store _targetStore;

}