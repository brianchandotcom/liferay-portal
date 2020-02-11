/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.soap.repository;

import com.liferay.asset.kernel.service.AssetEntryLocalService;
import com.liferay.document.library.kernel.service.DLAppHelperLocalService;
import com.liferay.document.library.kernel.service.DLFolderLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.RepositoryEntryLocalService;
import com.liferay.portal.kernel.service.RepositoryLocalService;
import com.liferay.portal.kernel.service.UserLocalService;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Cristina González
 */
@Component(
	property = "repository.target.class.name=com.liferay.sharepoint.soap.repository.SharepointWSRepository",
	service = RepositoryFactory.class
)
public class SharepointWSRepositoryFactory implements RepositoryFactory {

	@Override
	public LocalRepository createLocalRepository(long repositoryId)
		throws PortalException {

		SharepointWSRepository sharepointWSRepository =
			(SharepointWSRepository)createRepository(repositoryId);

		return sharepointWSRepository.getLocalRepository();
	}

	@Override
	public Repository createRepository(long repositoryId)
		throws PortalException {

		SharepointWSRepository sharepointWSRepository =
			new SharepointWSRepository();

		setupRepository(
			_repositoryLocalService.getRepository(repositoryId),
			sharepointWSRepository);

		if (!ExportImportThreadLocal.isImportInProcess()) {
			try {
				sharepointWSRepository.initRepository();
			}
			catch (Exception exception) {
				throw new RepositoryException(exception);
			}
		}

		return sharepointWSRepository;
	}

	protected void setupRepository(
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
		baseRepository.setRepositoryId(repository.getRepositoryId());
		baseRepository.setTypeSettingsProperties(
			repository.getTypeSettingsProperties());
		baseRepository.setUserLocalService(_userLocalService);
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
	private UserLocalService _userLocalService;

}