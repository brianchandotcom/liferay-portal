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
import com.liferay.portal.repository.cmis.CMISRepository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.proxy.BaseRepositoryProxyBean;
import com.liferay.portal.repository.util.RepositoryFactoryUtil;

/**
 * @author Adolfo Pérez
 */
public abstract class BaseRepositoryFactory {

	protected BaseRepository createRepositoryImpl(
			long repositoryId, long classNameId)
		throws PortalException, SystemException {

		BaseRepository baseRepository = null;

		Repository repository = null;

		try {
			repository = getRepository(repositoryId);

			ClassName className = classNameLocalService.getClassName(
				classNameId);

			String repositoryImplClassName = className.getValue();

			baseRepository = RepositoryFactoryUtil.getInstance(
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

	protected long getDefaultClassNameId() {
		if (_defaultClassNameId == 0) {
			_defaultClassNameId = classNameLocalService.getClassNameId(
				LiferayRepository.class.getName());
		}

		return _defaultClassNameId;
	}

	protected long getRepositoryClassNameId(long repositoryId)
		throws SystemException {

		Repository repository = repositoryPersistence.fetchByPrimaryKey(
			repositoryId);

		if (repository != null) {
			return repository.getClassNameId();
		}

		return classNameLocalService.getClassNameId(
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
				"Missing a valid ID for folder, file entry or file version");
		}

		return repositoryEntryId;
	}

	protected void setupRepository(
		long repositoryId, Repository repository,
		BaseRepository baseRepository) {

		baseRepository.setAssetEntryLocalService(assetEntryLocalService);
		baseRepository.setCompanyId(repository.getCompanyId());
		baseRepository.setCompanyLocalService(companyLocalService);
		baseRepository.setDLAppHelperLocalService(dlAppHelperLocalService);
		baseRepository.setGroupId(repository.getGroupId());
		baseRepository.setRepositoryEntryLocalService(
			repositoryEntryLocalService);
		baseRepository.setRepositoryId(repositoryId);
		baseRepository.setTypeSettingsProperties(
			repository.getTypeSettingsProperties());
		baseRepository.setUserLocalService(userLocalService);
	}

	private long _defaultClassNameId;

}