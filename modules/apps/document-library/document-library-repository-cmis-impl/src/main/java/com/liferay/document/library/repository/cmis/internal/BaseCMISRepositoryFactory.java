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

package com.liferay.document.library.repository.cmis.internal;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.repository.cmis.CMISRepositoryHandler;
import com.liferay.document.library.repository.cmis.configuration.CMISRepositoryConfiguration;
import com.liferay.document.library.repository.cmis.search.BaseCmisSearchQueryBuilder;
import com.liferay.document.library.repository.cmis.search.CMISSearchQueryBuilder;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lock.LockManager;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.ProxyUtil;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCMISRepositoryFactory<T extends CMISRepositoryHandler>
	implements RepositoryFactory {

	@Override
	public LocalRepository createLocalRepository(long repositoryId)
		throws PortalException {

		Thread currentThread = Thread.currentThread();

		ClassLoader contextClassLoader = currentThread.getContextClassLoader();

		currentThread.setContextClassLoader(
			BaseCMISRepositoryFactory.class.getClassLoader());

		try {
			BaseRepository baseRepository = createBaseRepository(repositoryId);

			return baseRepository.getLocalRepository();
		}
		finally {
			currentThread.setContextClassLoader(contextClassLoader);
		}
	}

	@Override
	public Repository createRepository(long repositoryId)
		throws PortalException {

		return (Repository)ProxyUtil.newProxyInstance(
			Repository.class.getClassLoader(),
			new Class<?>[] {Repository.class},
			new ClassLoaderBeanHandler(
				new RepositoryProxyBean(
					createBaseRepository(repositoryId),
					BaseCMISRepositoryFactory.class.getClassLoader()),
				BaseCMISRepositoryFactory.class.getClassLoader()));
	}

	protected abstract T createBaseRepository();

	protected BaseRepository createBaseRepository(long repositoryId)
		throws PortalException {

		T baseRepository = createBaseRepository();

		RepositoryLocalService repositoryLocalService =
			getRepositoryLocalService();

		com.liferay.portal.kernel.model.Repository repository =
			repositoryLocalService.getRepository(repositoryId);

		CMISRepository cmisRepository = new CMISRepository(
			_cmisRepositoryConfiguration, baseRepository,
			_cmisSearchQueryBuilder, getCMISSessionCache(), getLockManager());

		baseRepository.setCmisRepository(cmisRepository);

		_setupRepository(repositoryId, repository, cmisRepository);

		_setupRepository(repositoryId, repository, baseRepository);

		if (!ExportImportThreadLocal.isImportInProcess()) {
			baseRepository.initRepository();
		}

		return baseRepository;
	}

	protected abstract AssetEntryLocalService getAssetEntryLocalService();

	protected abstract CMISSessionCache getCMISSessionCache();

	protected abstract CompanyLocalService getCompanyLocalService();

	protected abstract DLAppHelperLocalService getDLAppHelperLocalService();

	protected abstract DLFolderLocalService getDLFolderLocalService();

	protected abstract LockManager getLockManager();

	protected abstract RepositoryEntryLocalService
		getRepositoryEntryLocalService();

	protected abstract RepositoryLocalService getRepositoryLocalService();

	protected abstract UserLocalService getUserLocalService();

	protected void setCMISRepositoryConfiguration(
		CMISRepositoryConfiguration cmisRepositoryConfiguration) {

		_cmisRepositoryConfiguration = cmisRepositoryConfiguration;
	}

	private void _setupRepository(
		long repositoryId,
		com.liferay.portal.kernel.model.Repository repository,
		BaseRepository baseRepository) {

		baseRepository.setAssetEntryLocalService(getAssetEntryLocalService());
		baseRepository.setCompanyId(repository.getCompanyId());
		baseRepository.setCompanyLocalService(getCompanyLocalService());
		baseRepository.setDLAppHelperLocalService(getDLAppHelperLocalService());
		baseRepository.setDLFolderLocalService(getDLFolderLocalService());
		baseRepository.setGroupId(repository.getGroupId());
		baseRepository.setRepositoryEntryLocalService(
			getRepositoryEntryLocalService());
		baseRepository.setRepositoryId(repositoryId);
		baseRepository.setTypeSettingsProperties(
			repository.getTypeSettingsProperties());
		baseRepository.setUserLocalService(getUserLocalService());
	}

	private CMISRepositoryConfiguration _cmisRepositoryConfiguration;
	private final CMISSearchQueryBuilder _cmisSearchQueryBuilder =
		new BaseCmisSearchQueryBuilder();

}