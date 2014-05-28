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

package com.liferay.portal.repository;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.cmis.CMISRepositoryHandler;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.repository.cmis.CMISRepository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.proxy.BaseRepositoryProxyBean;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryUtil;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.RepositoryEntryLocalService;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portal.service.RepositoryService;
import com.liferay.portal.service.ResourceLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryTypeLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseRepositoryFactory<T> {

	protected BaseRepository createExternalRepositoryImpl(
			long repositoryId, long classNameId)
		throws PortalException, SystemException {

		BaseRepository baseRepository = null;

		Repository repository = null;

		try {
			repository = _repositoryLocalService.getRepository(repositoryId);

			ClassName className = _classNameLocalService.getClassName(
				classNameId);

			String repositoryImplClassName = className.getValue();

			baseRepository = ExternalRepositoryFactoryUtil.getInstance(
				repositoryImplClassName);
		}
		catch (Exception e) {
			throw new RepositoryException(
				"There is no valid repository class with class name id " +
					classNameId,
				e);
		}

		CMISRepositoryHandler cmisRepositoryHandler = null;

		if (baseRepository instanceof CMISRepositoryHandler) {
			cmisRepositoryHandler = (CMISRepositoryHandler)baseRepository;
		}
		else if (baseRepository instanceof BaseRepositoryProxyBean) {
			BaseRepositoryProxyBean baseRepositoryProxyBean =
				(BaseRepositoryProxyBean)baseRepository;

			ClassLoaderBeanHandler classLoaderBeanHandler =
				(ClassLoaderBeanHandler)ProxyUtil.getInvocationHandler(
					baseRepositoryProxyBean.getProxyBean());

			Object bean = classLoaderBeanHandler.getBean();

			if (bean instanceof CMISRepositoryHandler) {
				cmisRepositoryHandler = (CMISRepositoryHandler)bean;
			}
		}

		if (cmisRepositoryHandler != null) {
			CMISRepository cmisRepository = new CMISRepository(
				cmisRepositoryHandler);

			cmisRepositoryHandler.setCmisRepository(cmisRepository);

			setupRepository(repositoryId, repository, cmisRepository);
		}

		setupRepository(repositoryId, repository, baseRepository);

		if (!ExportImportThreadLocal.isImportInProcess()) {
			baseRepository.initRepository();
		}

		return baseRepository;
	}

	protected T createLiferayRepository(long repositoryId)
		throws PortalException, SystemException {

		Repository repository = getRepository(repositoryId);

		long groupId;
		long actualRepositoryId;
		long dlFolderId;

		if (repository == null) {
			groupId = repositoryId;
			actualRepositoryId = repositoryId;
			dlFolderId = 0;
		}
		else {
			groupId = repository.getGroupId();
			actualRepositoryId = repository.getRepositoryId();
			dlFolderId = repository.getDlFolderId();
		}

		return createLiferayRepositoryInstance(
			groupId, actualRepositoryId, dlFolderId);
	}

	protected T createLiferayRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		try {
			long repositoryId;

			if (folderId != 0) {
				repositoryId = getFolderRepositoryId(folderId);
			}
			else if (fileEntryId != 0) {
				repositoryId = getFileEntryRepositoryId(fileEntryId);
			}
			else if (fileVersionId != 0) {
				repositoryId = getFileVersionRepositoryId(fileVersionId);
			}
			else {
				throw new InvalidRepositoryIdException(
					"Missing a valid ID for folder, file entry or file " +
						"version");
			}

			return createLiferayRepository(repositoryId);
		}
		catch (NoSuchFolderException nsfe) {
			return null;
		}
		catch (NoSuchFileEntryException nsfee) {
			return null;
		}
		catch (NoSuchFileVersionException nsfve) {
			return null;
		}
	}

	protected abstract T createLiferayRepositoryInstance(
		long groupId, long repositoryId, long dlFolderId);

	protected AssetEntryLocalService getAssetEntryLocalService() {
		return _assetEntryLocalService;
	}

	protected ClassNameLocalService getClassNameLocalService() {
		return _classNameLocalService;
	}

	protected CompanyLocalService getCompanyLocalService() {
		return _companyLocalService;
	}

	protected long getDefaultClassNameId() {
		if (_defaultClassNameId == 0) {
			_defaultClassNameId = _classNameLocalService.getClassNameId(
				LiferayRepository.class.getName());
		}

		return _defaultClassNameId;
	}

	protected DLAppHelperLocalService getDlAppHelperLocalService() {
		return _dlAppHelperLocalService;
	}

	protected DLFileEntryLocalService getDlFileEntryLocalService() {
		return _dlFileEntryLocalService;
	}

	protected DLFileEntryService getDlFileEntryService() {
		return _dlFileEntryService;
	}

	protected DLFileEntryTypeLocalService getDlFileEntryTypeLocalService() {
		return _dlFileEntryTypeLocalService;
	}

	protected DLFileVersionLocalService getDlFileVersionLocalService() {
		return _dlFileVersionLocalService;
	}

	protected DLFileVersionService getDlFileVersionService() {
		return _dlFileVersionService;
	}

	protected DLFolderLocalService getDlFolderLocalService() {
		return _dlFolderLocalService;
	}

	protected DLFolderService getDlFolderService() {
		return _dlFolderService;
	}

	protected abstract long getFileEntryRepositoryId(long fileEntryId)
		throws PortalException, SystemException;

	protected abstract long getFileVersionRepositoryId(long fileVersionId)
		throws PortalException, SystemException;

	protected abstract long getFolderRepositoryId(long folderId)
		throws PortalException, SystemException;

	protected abstract com.liferay.portal.model.Repository getRepository(
			long repositoryId)
		throws PortalException, SystemException;

	protected long getRepositoryClassNameId(long repositoryId)
		throws SystemException {

		Repository repository = _repositoryLocalService.fetchRepository(
			repositoryId);

		if (repository != null) {
			return repository.getClassNameId();
		}

		return _classNameLocalService.getClassNameId(
			LiferayRepository.class.getName());
	}

	protected long getRepositoryEntryId(
			long folderId, long fileEntryId, long fileVersionId)
		throws RepositoryException {

		long repositoryEntryId = 0;

		if (folderId != 0) {
			repositoryEntryId = folderId;
		}
		else if (fileEntryId != 0) {
			repositoryEntryId = fileEntryId;
		}
		else if (fileVersionId != 0) {
			repositoryEntryId = fileVersionId;
		}

		if (repositoryEntryId == 0) {
			throw new InvalidRepositoryIdException(
				"Missing a valid ID for folder, file entry, or file version");
		}

		return repositoryEntryId;
	}

	protected RepositoryEntryLocalService getRepositoryEntryLocalService() {
		return _repositoryEntryLocalService;
	}

	protected long getRepositoryId(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		long repositoryEntryId = getRepositoryEntryId(
			folderId, fileEntryId, fileVersionId);

		RepositoryEntry repositoryEntry =
			_repositoryEntryLocalService.getRepositoryEntry(repositoryEntryId);

		return repositoryEntry.getRepositoryId();
	}

	protected RepositoryLocalService getRepositoryLocalService() {
		return _repositoryLocalService;
	}

	protected RepositoryService getRepositoryService() {
		return _repositoryService;
	}

	protected ResourceLocalService getResourceLocalService() {
		return _resourceLocalService;
	}

	protected UserLocalService getUserLocalService() {
		return _userLocalService;
	}

	protected void setAssetEntryLocalService(
		AssetEntryLocalService assetEntryLocalService) {

		_assetEntryLocalService = assetEntryLocalService;
	}

	protected void setClassNameLocalService(
		ClassNameLocalService classNameLocalService) {

		_classNameLocalService = classNameLocalService;
	}

	protected void setCompanyLocalService(
		CompanyLocalService companyLocalService) {

		_companyLocalService = companyLocalService;
	}

	protected void setDlAppHelperLocalService(
		DLAppHelperLocalService dlAppHelperLocalService) {

		_dlAppHelperLocalService = dlAppHelperLocalService;
	}

	protected void setDlFileEntryLocalService(
		DLFileEntryLocalService dlFileEntryLocalService) {

		_dlFileEntryLocalService = dlFileEntryLocalService;
	}

	protected void setDlFileEntryService(
		DLFileEntryService dlFileEntryService) {

		_dlFileEntryService = dlFileEntryService;
	}

	protected void setDlFileEntryTypeLocalService(
		DLFileEntryTypeLocalService dlFileEntryTypeLocalService) {

		_dlFileEntryTypeLocalService = dlFileEntryTypeLocalService;
	}

	protected void setDlFileVersionLocalService(
		DLFileVersionLocalService dlFileVersionLocalService) {

		_dlFileVersionLocalService = dlFileVersionLocalService;
	}

	protected void setDlFileVersionService(
		DLFileVersionService dlFileVersionService) {

		_dlFileVersionService = dlFileVersionService;
	}

	protected void setDlFolderLocalService(
		DLFolderLocalService dlFolderLocalService) {

		_dlFolderLocalService = dlFolderLocalService;
	}

	protected void setDlFolderService(DLFolderService dlFolderService) {
		_dlFolderService = dlFolderService;
	}

	protected void setRepositoryEntryLocalService(
		RepositoryEntryLocalService repositoryEntryLocalService) {

		_repositoryEntryLocalService = repositoryEntryLocalService;
	}

	protected void setRepositoryLocalService(
		RepositoryLocalService repositoryLocalService) {

		_repositoryLocalService = repositoryLocalService;
	}

	protected void setRepositoryService(RepositoryService repositoryService) {
		_repositoryService = repositoryService;
	}

	protected void setResourceLocalService(
		ResourceLocalService resourceLocalService) {

		_resourceLocalService = resourceLocalService;
	}

	protected void setupRepository(
		long repositoryId, Repository repository,
		BaseRepository baseRepository) {

		baseRepository.setAssetEntryLocalService(_assetEntryLocalService);
		baseRepository.setCompanyId(repository.getCompanyId());
		baseRepository.setCompanyLocalService(_companyLocalService);
		baseRepository.setDLAppHelperLocalService(_dlAppHelperLocalService);
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

	@BeanReference(type = AssetEntryLocalService.class)
	private AssetEntryLocalService _assetEntryLocalService;

	@BeanReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

	@BeanReference(type = CompanyLocalService.class)
	private CompanyLocalService _companyLocalService;

	private long _defaultClassNameId;

	@BeanReference(type = DLAppHelperLocalService.class)
	private DLAppHelperLocalService _dlAppHelperLocalService;

	@BeanReference(type = DLFileEntryLocalService.class)
	private DLFileEntryLocalService _dlFileEntryLocalService;

	@BeanReference(type = DLFileEntryService.class)
	private DLFileEntryService _dlFileEntryService;

	@BeanReference(type = DLFileEntryTypeLocalService.class)
	private DLFileEntryTypeLocalService _dlFileEntryTypeLocalService;

	@BeanReference(type = DLFileVersionLocalService.class)
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@BeanReference(type = DLFileVersionService.class)
	private DLFileVersionService _dlFileVersionService;

	@BeanReference(type = DLFolderLocalService.class)
	private DLFolderLocalService _dlFolderLocalService;

	@BeanReference(type = DLFolderService.class)
	private DLFolderService _dlFolderService;

	@BeanReference(type = RepositoryEntryLocalService.class)
	private RepositoryEntryLocalService _repositoryEntryLocalService;

	@BeanReference(type = RepositoryLocalService.class)
	private RepositoryLocalService _repositoryLocalService;

	@BeanReference(type = RepositoryService.class)
	private RepositoryService _repositoryService;

	@BeanReference(type = ResourceLocalService.class)
	private ResourceLocalService _resourceLocalService;

	@BeanReference(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}