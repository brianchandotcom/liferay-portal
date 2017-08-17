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

package com.liferay.sharepoint.repository.internal.repository;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.document.library.repository.authorization.oauth2.TokenStore;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.sharepoint.repository.internal.configuration.SharepointRepositoryConfiguration;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Adolfo Pérez
 */
@Component(service = SharepointRepositoryFactoryProvider.class)
public class SharepointRepositoryFactoryProvider {

	public SharepointRepositoryFactory createForConfiguration(
		SharepointRepositoryConfiguration sharepointRepositoryConfiguration) {

		return new SharepointRepositoryFactory(
			sharepointRepositoryConfiguration);
	}

	@Reference
	private AssetEntryLocalService _assetEntryLocalService;

	@Reference
	private CompanyLocalService _companyLocalService;

	@Reference
	private DLAppHelperLocalService _dlAppHelperLocalService;

	@Reference
	private DLFolderLocalService _dlFolderLocalService;

	@Reference
	private RepositoryEntryLocalService _repositoryEntryLocalService;

	@Reference
	private RepositoryLocalService _repositoryLocalService;

	@Reference
	private TokenStore _tokenStore;

	@Reference
	private UserLocalService _userLocalService;

	private class SharepointRepositoryFactory implements RepositoryFactory {

		public SharepointRepositoryFactory(
			SharepointRepositoryConfiguration
				sharepointRepositoryConfiguration) {

			_sharepointRepositoryConfiguration =
				sharepointRepositoryConfiguration;
		}

		@Override
		public LocalRepository createLocalRepository(long repositoryId)
			throws PortalException {

			BaseRepository baseRepository = _createBaseRepository(repositoryId);

			return baseRepository.getLocalRepository();
		}

		@Override
		public Repository createRepository(long repositoryId)
			throws PortalException {

			return _createBaseRepository(repositoryId);
		}

		private BaseRepository _createBaseRepository(long repositoryId)
			throws PortalException {

			SharepointExtRepositoryAdapter repositoryAdapter =
				new SharepointExtRepositoryAdapter(
					new SharepointExtRepository(
						_tokenStore, _sharepointRepositoryConfiguration));

			repositoryAdapter.setAssetEntryLocalService(
				_assetEntryLocalService);
			repositoryAdapter.setCompanyLocalService(_companyLocalService);
			repositoryAdapter.setDLAppHelperLocalService(
				_dlAppHelperLocalService);
			repositoryAdapter.setDLFolderLocalService(_dlFolderLocalService);
			repositoryAdapter.setRepositoryEntryLocalService(
				_repositoryEntryLocalService);
			repositoryAdapter.setUserLocalService(_userLocalService);

			com.liferay.portal.kernel.model.Repository repository =
				_repositoryLocalService.getRepository(repositoryId);

			repositoryAdapter.setCompanyId(repository.getCompanyId());
			repositoryAdapter.setGroupId(repository.getGroupId());
			repositoryAdapter.setRepositoryId(repository.getRepositoryId());
			repositoryAdapter.setTypeSettingsProperties(
				repository.getTypeSettingsProperties());

			repositoryAdapter.initRepository();

			return repositoryAdapter;
		}

		private final SharepointRepositoryConfiguration
			_sharepointRepositoryConfiguration;

	}

}