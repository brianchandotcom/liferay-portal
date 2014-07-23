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
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.capabilities.TrashCapability;
import com.liferay.portal.kernel.repository.cmis.CMISRepositoryHandler;
import com.liferay.portal.kernel.repository.registry.RepositoryCreator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.repository.capabilities.LiferayTrashCapability;
import com.liferay.portal.repository.cmis.CMISRepository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.liferayrepository.LiferayRepositoryCreator;
import com.liferay.portal.repository.proxy.BaseRepositoryProxyBean;
import com.liferay.portal.repository.util.ExternalRepositoryFactoryUtil;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.service.CompanyLocalService;
import com.liferay.portal.service.RepositoryEntryLocalService;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portal.service.UserLocalService;
import com.liferay.portal.util.RepositoryUtil;
import com.liferay.portlet.asset.service.AssetEntryLocalService;
import com.liferay.portlet.documentlibrary.NoSuchFileEntryException;
import com.liferay.portlet.documentlibrary.NoSuchFileVersionException;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.service.DLAppHelperLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseRepositoryFactory<T> {

	public T create(long repositoryId) throws PortalException {
		long classNameId = getRepositoryClassNameId(repositoryId);

		if (classNameId == getDefaultClassNameId()) {
			return createInternalRepository(repositoryId);
		}
		else {
			return createExternalRepository(repositoryId, classNameId);
		}
	}

	public T create(long folderId, long fileEntryId, long fileVersionId)
		throws PortalException {

		T liferayRepository = createInternalRepository(
			folderId, fileEntryId, fileVersionId);

		if (liferayRepository != null) {
			return liferayRepository;
		}

		return createExternalRepository(folderId, fileEntryId, fileVersionId);
	}

	protected abstract T createExternalRepository(
			long repositoryId, long classNameId)
		throws PortalException;

	protected abstract T createExternalRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException;

	protected BaseRepository createExternalRepositoryImpl(
			long repositoryId, long classNameId)
		throws PortalException {

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

	protected abstract T createInternalRepository(long repositoryId)
		throws PortalException;

	protected T createInternalRepository(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException {

		try {
			long repositoryId = 0;

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
					"Missing a valid ID for folder, file entry, or file " +
						"version");
			}

			return createInternalRepository(repositoryId);
		}
		catch (NoSuchFileEntryException nsfee) {
			return null;
		}
		catch (NoSuchFileVersionException nsfve) {
			return null;
		}
		catch (NoSuchFolderException nsfe) {
			return null;
		}
	}

	protected long getDefaultClassNameId() {
		if (_defaultClassNameId == 0) {
			_defaultClassNameId = _classNameLocalService.getClassNameId(
				LiferayRepository.class.getName());
		}

		return _defaultClassNameId;
	}

	protected Map<Class<? extends Capability>, Capability>
		getExternalSupportedCapabilities() {

		return _externalSupportedCapabilities;
	}

	protected Set<Class<? extends Capability>>
		getExternalExportedCapabilityClasses() {

		return _externalExportedCapabilityClasses;
	}

	protected Set<Class<? extends Capability>>
		getInternalExportedCapabilityClasses() {

		return _internalExportedCapabilityClasses;
	}

	protected Map<Class<? extends Capability>, Capability>
		getInternalSupportedCapabilities() {

		return _internalSupportedCapabilities;
	}

	protected DLFileEntryLocalService getDlFileEntryLocalService() {
		return _dlFileEntryLocalService;
	}

	protected DLFileEntryService getDlFileEntryService() {
		return _dlFileEntryService;
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
		throws PortalException;

	protected abstract long getFileVersionRepositoryId(long fileVersionId)
		throws PortalException;

	protected abstract long getFolderRepositoryId(long folderId)
		throws PortalException;

	protected RepositoryCreator getLiferayRepositoryCreator() {
		return _liferayRepositoryCreator;
	}

	protected abstract Repository getRepository(long repositoryId);

	protected long getRepositoryClassNameId(long repositoryId) {
		Repository repository = _repositoryLocalService.fetchRepository(
			repositoryId);

		if (repository != null) {
			return repository.getClassNameId();
		}

		return _classNameLocalService.getClassNameId(
			LiferayRepository.class.getName());
	}

	protected long getRepositoryId(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException {

		long repositoryEntryId = RepositoryUtil.getRepositoryEntryId(
			folderId, fileEntryId, fileVersionId);

		RepositoryEntry repositoryEntry =
			_repositoryEntryLocalService.getRepositoryEntry(repositoryEntryId);

		return repositoryEntry.getRepositoryId();
	}

	protected RepositoryLocalService getRepositoryLocalService() {
		return _repositoryLocalService;
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

	private static Set<Class<? extends Capability>>
		_externalExportedCapabilityClasses = Collections.emptySet();
	private static Map<Class<? extends Capability>, Capability>
		_externalSupportedCapabilities = Collections.emptyMap();
	private static Set<Class<? extends Capability>>
		_internalExportedCapabilityClasses =
			Collections.<Class<? extends Capability>>singleton(
				TrashCapability.class);
	private static Map<Class<? extends Capability>, Capability>
		_internalSupportedCapabilities =
			new HashMap<Class<? extends Capability>, Capability>();

	static {
		_internalSupportedCapabilities.put(
			TrashCapability.class, new LiferayTrashCapability());
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

	@BeanReference(type = DLFileVersionLocalService.class)
	private DLFileVersionLocalService _dlFileVersionLocalService;

	@BeanReference(type = DLFileVersionService.class)
	private DLFileVersionService _dlFileVersionService;

	@BeanReference(type = DLFolderLocalService.class)
	private DLFolderLocalService _dlFolderLocalService;

	@BeanReference(type = DLFolderService.class)
	private DLFolderService _dlFolderService;

	@BeanReference(type = LiferayRepositoryCreator.class)
	private RepositoryCreator _liferayRepositoryCreator;

	@BeanReference(type = RepositoryEntryLocalService.class)
	private RepositoryEntryLocalService _repositoryEntryLocalService;

	@BeanReference(type = RepositoryLocalService.class)
	private RepositoryLocalService _repositoryLocalService;

	@BeanReference(type = UserLocalService.class)
	private UserLocalService _userLocalService;

}