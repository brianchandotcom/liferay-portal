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

import com.liferay.document.library.repository.cmis.CMISRepositoryHandler;
import com.liferay.document.library.repository.cmis.configuration.CMISRepositoryConfiguration;
import com.liferay.document.library.repository.cmis.search.BaseCmisSearchQueryBuilder;
import com.liferay.document.library.repository.cmis.search.CMISSearchQueryBuilder;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.lock.LockHelper;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.RepositoryEntryLocalService;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseCMISRepositoryFactory<T extends CMISRepositoryHandler>
	implements RepositoryFactory {

	@Override
	public LocalRepository createLocalRepository(long repositoryId)
		throws PortalException {

		BaseRepository baseRepository = createBaseRepository(repositoryId);

		return baseRepository.getLocalRepository();
	}

	@Override
	public Repository createRepository(long repositoryId)
		throws PortalException {

		return createBaseRepository(repositoryId);
	}

	protected abstract T createBaseRepository();

	protected BaseRepository createBaseRepository(long repositoryId)
		throws PortalException {

		T baseRepository = createBaseRepository();

		com.liferay.portal.model.Repository repository =
			_repositoryLocalService.getRepository(repositoryId);

		CMISRepository cmisRepository = new CMISRepository(
			_cmisRepositoryConfiguration, baseRepository,
			_cmisSearchQueryBuilder, _lockHelper);

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

	protected void setLockHelper(LockHelper lockHelper) {
		_lockHelper = lockHelper;
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
		long repositoryId, com.liferay.portal.model.Repository repository,
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
	private CompanyLocalService _companyLocalService;
	private DLAppHelperLocalService _dlAppHelperLocalService;
	private DLFolderLocalService _dlFolderLocalService;
	private LockHelper _lockHelper;
	private RepositoryEntryLocalService _repositoryEntryLocalService;
	private RepositoryLocalService _repositoryLocalService;
	private UserLocalService _userLocalService;

}