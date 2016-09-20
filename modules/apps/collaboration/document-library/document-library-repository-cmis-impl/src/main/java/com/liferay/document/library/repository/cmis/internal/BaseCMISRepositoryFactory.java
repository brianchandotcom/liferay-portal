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

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCMISRepositoryFactory<T extends CMISRepositoryHandler>
	implements RepositoryFactory {

	@Override
	public LocalRepository createLocalRepository(long repositoryId)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(
					BaseCMISRepositoryFactory.class.getClassLoader())) {

			BaseRepository baseRepository = createBaseRepository(repositoryId);

			return baseRepository.getLocalRepository();
		}
	}

	@Override
	public Repository createRepository(long repositoryId)
		throws PortalException {

		try (ContextClassLoaderSetter contextClassLoaderSetter =
				new ContextClassLoaderSetter(
					BaseCMISRepositoryFactory.class.getClassLoader())) {

			return new RepositoryProxyBean(
				createBaseRepository(repositoryId),
				BaseCMISRepositoryFactory.class.getClassLoader());
		}
	}

	protected abstract T createBaseRepository();

	protected BaseRepository createBaseRepository(long repositoryId)
		throws PortalException {

		T baseRepository = createBaseRepository();

		com.liferay.portal.kernel.model.Repository repository =
			_repositoryLocalService.getRepository(repositoryId);

		CMISRepository cmisRepository = new CMISRepository(
			_cmisRepositoryConfiguration, baseRepository,
			_cmisSearchQueryBuilder, _cmisSessionCache, _lockManager);

		baseRepository.setCmisRepository(cmisRepository);

		setupRepository(repositoryId, repository, cmisRepository);

		setupRepository(repositoryId, repository, baseRepository);

		if (!ExportImportThreadLocal.isImportInProcess()) {
			baseRepository.initRepository();
		}

		return baseRepository;
	}

	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	protected void setCMISRepositoryConfiguration(
		CMISRepositoryConfiguration cmisRepositoryConfiguration) {

		_cmisRepositoryConfiguration = cmisRepositoryConfiguration;
	}

	protected void setCMISSessionCache(CMISSessionCache cmisSessionCache) {
		_cmisSessionCache = cmisSessionCache;
	}

	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	protected void setDLAppHelperLocalService(
		DLAppHelperLocalService dlAppHelperLocalService) {

		_dlAppHelperLocalService = dlAppHelperLocalService;
	}

	protected void setDLFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
	}

	protected void setLockManager(LockManager lockManager) {
		_lockManager = lockManager;
	}

	protected void setRepositoryEntryLocalService(
		RepositoryEntryLocalService repositoryEntryLocalService) {

		_repositoryEntryLocalService = repositoryEntryLocalService;
	}

	protected void setRepositoryLocalService(
		RepositoryLocalService repositoryLocalService) {

		_repositoryLocalService = repositoryLocalService;
	}

	protected void setupRepository(
		long repositoryId,
		com.liferay.portal.kernel.model.Repository repository,
		BaseRepository baseRepository) {

		baseRepository.setAssetEntryLocalService(_assetEntryLocalService);
		baseRepository.setCompanyId(repository.getCompanyId());
		baseRepository.setCompanyLocalService(_companyLocalService);
		baseRepository.setDLAppHelperLocalService(_dlAppHelperLocalService);
		baseRepository.setDLFolderLocalService(_dlFolderLocalService);
		baseRepository.setGroupId(repository.getGroupId());
		baseRepository.setRepositoryEntryLocalService(
			_repositoryEntryLocalService);
		baseRepository.setRepositoryId(repositoryId);
		baseRepository.setTypeSettingsProperties(
			repository.getTypeSettingsProperties());
		baseRepository.setUserLocalService(_userLocalService);
	}

	protected void setUserLocalService(UserLocalService userLocalService) {
		_userLocalService = userLocalService;
	}

	private AssetEntryLocalService _assetEntryLocalService;
	private CMISRepositoryConfiguration _cmisRepositoryConfiguration;
	private final CMISSearchQueryBuilder _cmisSearchQueryBuilder =
		new BaseCmisSearchQueryBuilder();
	private CMISSessionCache _cmisSessionCache;
	private CompanyLocalService _companyLocalService;
	private DLAppHelperLocalService _dlAppHelperLocalService;
	private DLFolderLocalService _dlFolderLocalService;
	private LockManager _lockManager;
	private RepositoryEntryLocalService _repositoryEntryLocalService;
	private RepositoryLocalService _repositoryLocalService;
	private UserLocalService _userLocalService;

}