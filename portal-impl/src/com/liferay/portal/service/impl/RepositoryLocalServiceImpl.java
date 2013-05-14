/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.impl;

import com.liferay.portal.InvalidRepositoryException;
import com.liferay.portal.NoSuchRepositoryException;
import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.lar.ExportImportThreadLocal;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.repository.BaseRepository;
import com.liferay.portal.kernel.repository.InvalidRepositoryIdException;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.RepositoryException;
import com.liferay.portal.kernel.repository.cmis.CMISRepositoryHandler;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.UnicodeProperties;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Group;
import com.liferay.portal.model.Repository;
import com.liferay.portal.model.RepositoryEntry;
import com.liferay.portal.model.User;
import com.liferay.portal.repository.cmis.CMISRepository;
import com.liferay.portal.repository.liferayrepository.LiferayLocalRepository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.proxy.BaseRepositoryProxyBean;
import com.liferay.portal.repository.util.RepositoryFactoryUtil;
import com.liferay.portal.service.ServiceContext;
import com.liferay.portal.service.base.RepositoryLocalServiceBaseImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.portlet.documentlibrary.NoSuchFolderException;
import com.liferay.portlet.documentlibrary.RepositoryNameException;
import com.liferay.portlet.documentlibrary.model.DLFolder;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author Alexander Chow
 */
public class RepositoryLocalServiceImpl extends RepositoryLocalServiceBaseImpl {

	@Override
	public Repository addRepository(
			long userId, long groupId, long classNameId, long parentFolderId,
			String name, String description, String portletId,
			UnicodeProperties typeSettingsProperties, boolean hidden,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		User user = userPersistence.findByPrimaryKey(userId);
		Date now = new Date();

		long repositoryId = counterLocalService.increment();

		Repository repository = repositoryPersistence.create(repositoryId);

		repository.setUuid(serviceContext.getUuid());
		repository.setGroupId(groupId);
		repository.setCompanyId(user.getCompanyId());
		repository.setUserId(user.getUserId());
		repository.setUserName(user.getFullName());
		repository.setCreateDate(now);
		repository.setModifiedDate(now);
		repository.setClassNameId(classNameId);
		repository.setName(name);
		repository.setDescription(description);
		repository.setPortletId(portletId);
		repository.setTypeSettingsProperties(typeSettingsProperties);
		repository.setDlFolderId(
			getDLFolderId(
				user, groupId, repositoryId, parentFolderId, name, description,
				hidden, serviceContext));

		repositoryPersistence.update(repository);

		if (classNameId != getDefaultClassNameId()) {
			try {
				createRepositoryImpl(repositoryId, classNameId);
			}
			catch (Exception e) {
				if (_log.isWarnEnabled()) {
					_log.warn(e, e);
				}

				throw new InvalidRepositoryException(e);
			}
		}

		return repository;
	}

	/**
	 * @deprecated As of 6.2.0, replaced by {@link #addRepository(long, long,
	 *             long, long, String, String, String, UnicodeProperties,
	 *             boolean, ServiceContext)}
	 */
	@Override
	public Repository addRepository(
			long userId, long groupId, long classNameId, long parentFolderId,
			String name, String description, String portletId,
			UnicodeProperties typeSettingsProperties,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		return addRepository(
			userId, groupId, classNameId, parentFolderId, name, description,
			portletId, typeSettingsProperties, false, serviceContext);
	}

	@Override
	public void checkRepository(long repositoryId) throws SystemException {
		Group group = groupPersistence.fetchByPrimaryKey(repositoryId);

		if (group != null) {
			return;
		}

		try {
			repositoryPersistence.findByPrimaryKey(repositoryId);
		}
		catch (NoSuchRepositoryException nsre) {
			throw new RepositoryException(nsre.getMessage());
		}
	}

	@Override
	public void deleteRepositories(long groupId)
		throws PortalException, SystemException {

		List<Repository> repositories = repositoryPersistence.findByGroupId(
			groupId);

		for (Repository repository : repositories) {
			deleteRepository(repository.getRepositoryId());
		}

		dlFolderLocalService.deleteAll(groupId);
	}

	@Override
	public Repository deleteRepository(long repositoryId)
		throws PortalException, SystemException {

		Repository repository = repositoryPersistence.fetchByPrimaryKey(
			repositoryId);

		if (repository != null) {
			expandoValueLocalService.deleteValues(
				Repository.class.getName(), repositoryId);

			try {
				dlFolderLocalService.deleteFolder(repository.getDlFolderId());
			}
			catch (NoSuchFolderException nsfe) {
			}

			repositoryPersistence.remove(repository);

			repositoryEntryPersistence.removeByRepositoryId(repositoryId);
		}

		return repository;
	}

	@Override
	public Repository fetchRepository(long groupId, String portletId)
		throws SystemException {

		return fetchRepository(groupId, portletId, portletId);
	}

	@Override
	public Repository fetchRepository(
			long groupId, String name, String portletId)
		throws SystemException {

		return repositoryPersistence.fetchByG_N_P(groupId, name, portletId);
	}

	@Override
	public LocalRepository getLocalRepositoryImpl(long repositoryId)
		throws PortalException, SystemException {

		LocalRepository localRepositoryImpl =
			_localRepositoriesByRepositoryId.get(repositoryId);

		if (localRepositoryImpl != null) {
			return localRepositoryImpl;
		}

		long classNameId = getRepositoryClassNameId(repositoryId);

		if (classNameId == getDefaultClassNameId()) {
			localRepositoryImpl = new LiferayLocalRepository(
				repositoryLocalService, repositoryService,
				dlAppHelperLocalService, dlFileEntryLocalService,
				dlFileEntryService, dlFileEntryTypeLocalService,
				dlFileVersionLocalService, dlFileVersionService,
				dlFolderLocalService, dlFolderService, resourceLocalService,
				repositoryId);
		}
		else {
			BaseRepository baseRepository = createRepositoryImpl(
				repositoryId, classNameId);

			localRepositoryImpl = baseRepository.getLocalRepository();
		}

		checkRepository(repositoryId);

		_localRepositoriesByRepositoryId.put(repositoryId, localRepositoryImpl);

		return localRepositoryImpl;
	}

	@Override
	public LocalRepository getLocalRepositoryImpl(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		long repositoryEntryId = getRepositoryEntryId(
			folderId, fileEntryId, fileVersionId);

		LocalRepository localRepositoryImpl =
			_localRepositoriesByRepositoryEntryId.get(repositoryEntryId);

		if (localRepositoryImpl != null) {
			return localRepositoryImpl;
		}

		localRepositoryImpl = new LiferayLocalRepository(
			repositoryLocalService, repositoryService, dlAppHelperLocalService,
			dlFileEntryLocalService, dlFileEntryService,
			dlFileEntryTypeLocalService, dlFileVersionLocalService,
			dlFileVersionService, dlFolderLocalService, dlFolderService,
			resourceLocalService, folderId, fileEntryId, fileVersionId);

		if (localRepositoryImpl.getRepositoryId() == 0) {
			localRepositoryImpl = null;
		}

		if (localRepositoryImpl == null) {
			BaseRepository baseRepository = createRepositoryImpl(
				repositoryEntryId);

			localRepositoryImpl = baseRepository.getLocalRepository();
		}
		else {
			checkRepository(localRepositoryImpl.getRepositoryId());
		}

		_localRepositoriesByRepositoryEntryId.put(
			repositoryEntryId, localRepositoryImpl);

		return localRepositoryImpl;
	}

	@Override
	public Repository getRepository(long groupId, String portletId)
		throws PortalException, SystemException {

		return getRepository(groupId, portletId, portletId);
	}

	@Override
	public Repository getRepository(long groupId, String name, String portletId)
		throws PortalException, SystemException {

		return repositoryPersistence.findByG_N_P(groupId, name, portletId);
	}

	@Override
	public com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
			long repositoryId)
		throws PortalException, SystemException {

		com.liferay.portal.kernel.repository.Repository repositoryImpl =
			_repositoriesByRepositoryId.get(repositoryId);

		if (repositoryImpl != null) {
			return repositoryImpl;
		}

		long classNameId = getRepositoryClassNameId(repositoryId);

		if (classNameId ==
				PortalUtil.getClassNameId(LiferayRepository.class.getName())) {

			repositoryImpl = new LiferayRepository(
				repositoryLocalService, repositoryService,
				dlAppHelperLocalService, dlFileEntryLocalService,
				dlFileEntryService, dlFileEntryTypeLocalService,
				dlFileVersionLocalService, dlFileVersionService,
				dlFolderLocalService, dlFolderService, resourceLocalService,
				repositoryId);
		}
		else {
			repositoryImpl = createRepositoryImpl(repositoryId, classNameId);
		}

		checkRepository(repositoryId);

		_repositoriesByRepositoryId.put(repositoryId, repositoryImpl);

		return repositoryImpl;
	}

	@Override
	public com.liferay.portal.kernel.repository.Repository getRepositoryImpl(
			long folderId, long fileEntryId, long fileVersionId)
		throws PortalException, SystemException {

		long repositoryEntryId = getRepositoryEntryId(
			folderId, fileEntryId, fileVersionId);

		com.liferay.portal.kernel.repository.Repository repositoryImpl =
			_repositoriesByEntryId.get(repositoryEntryId);

		if (repositoryImpl != null) {
			return repositoryImpl;
		}

		repositoryImpl = new LiferayRepository(
			repositoryLocalService, repositoryService, dlAppHelperLocalService,
			dlFileEntryLocalService, dlFileEntryService,
			dlFileEntryTypeLocalService, dlFileVersionLocalService,
			dlFileVersionService, dlFolderLocalService, dlFolderService,
			resourceLocalService, folderId, fileEntryId, fileVersionId);

		if (repositoryImpl.getRepositoryId() == 0) {
			repositoryImpl = null;
		}

		if (repositoryImpl == null) {
			repositoryImpl = createRepositoryImpl(repositoryEntryId);
		}
		else {
			checkRepository(repositoryImpl.getRepositoryId());
		}

		_repositoriesByEntryId.put(repositoryEntryId, repositoryImpl);

		return repositoryImpl;
	}

	@Override
	public UnicodeProperties getTypeSettingsProperties(long repositoryId)
		throws PortalException, SystemException {

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		return repository.getTypeSettingsProperties();
	}

	@Override
	public void updateRepository(
			long repositoryId, String name, String description)
		throws PortalException, SystemException {

		Date now = new Date();

		Repository repository = repositoryPersistence.findByPrimaryKey(
			repositoryId);

		repository.setModifiedDate(now);
		repository.setName(name);
		repository.setDescription(description);

		repositoryPersistence.update(repository);

		DLFolder dlFolder = dlFolderPersistence.findByPrimaryKey(
			repository.getDlFolderId());

		dlFolder.setModifiedDate(now);
		dlFolder.setName(name);
		dlFolder.setDescription(description);

		dlFolderPersistence.update(dlFolder);
	}

	protected BaseRepository createRepositoryImpl(long repositoryEntryId)
		throws PortalException, SystemException {

		RepositoryEntry repositoryEntry =
			repositoryEntryPersistence.findByPrimaryKey(repositoryEntryId);

		long repositoryId = repositoryEntry.getRepositoryId();

		return (BaseRepository)getRepositoryImpl(repositoryId);
	}

	protected BaseRepository createRepositoryImpl(
			long repositoryId, long classNameId)
		throws PortalException, SystemException {

		BaseRepository baseRepository = null;

		Repository repository = null;

		try {
			repository = getRepository(repositoryId);

			String repositoryImplClassName = PortalUtil.getClassName(
				classNameId);

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
			_defaultClassNameId = PortalUtil.getClassNameId(
				LiferayRepository.class.getName());
		}

		return _defaultClassNameId;
	}

	protected long getDLFolderId(
			User user, long groupId, long repositoryId, long parentFolderId,
			String name, String description, boolean hidden,
			ServiceContext serviceContext)
		throws PortalException, SystemException {

		if (Validator.isNull(name)) {
			throw new RepositoryNameException();
		}

		DLFolder dlFolder = dlFolderLocalService.addFolder(
			user.getUserId(), groupId, repositoryId, true, parentFolderId, name,
			description, hidden, serviceContext);

		return dlFolder.getFolderId();
	}

	protected long getRepositoryClassNameId(long repositoryId)
		throws SystemException {

		Repository repository = repositoryPersistence.fetchByPrimaryKey(
			repositoryId);

		if (repository != null) {
			return repository.getClassNameId();
		}

		return PortalUtil.getClassNameId(LiferayRepository.class.getName());
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
		baseRepository.setCounterLocalService(counterLocalService);
		baseRepository.setDLAppHelperLocalService(dlAppHelperLocalService);
		baseRepository.setGroupId(repository.getGroupId());
		baseRepository.setRepositoryId(repositoryId);
		baseRepository.setTypeSettingsProperties(
			repository.getTypeSettingsProperties());
		baseRepository.setUserLocalService(userLocalService);
	}

	private static Log _log = LogFactoryUtil.getLog(
		RepositoryLocalServiceImpl.class);

	private long _defaultClassNameId;
	private Map<Long, LocalRepository> _localRepositoriesByRepositoryEntryId =
		new ConcurrentHashMap<Long, LocalRepository>();
	private Map<Long, LocalRepository> _localRepositoriesByRepositoryId =
		new ConcurrentHashMap<Long, LocalRepository>();
	private Map<Long, com.liferay.portal.kernel.repository.Repository>
		_repositoriesByEntryId = new ConcurrentHashMap
			<Long, com.liferay.portal.kernel.repository.Repository>();
	private Map<Long, com.liferay.portal.kernel.repository.Repository>
		_repositoriesByRepositoryId = new ConcurrentHashMap
			<Long, com.liferay.portal.kernel.repository.Repository>();

}