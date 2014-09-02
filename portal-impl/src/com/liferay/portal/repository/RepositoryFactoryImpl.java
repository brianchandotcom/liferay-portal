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
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.UndeployedExternalRepositoryException;
import com.liferay.portal.kernel.repository.capabilities.Capability;
import com.liferay.portal.kernel.repository.cmis.CMISRepositoryHandler;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.model.ClassName;
import com.liferay.portal.repository.capabilities.CapabilityLocalRepository;
import com.liferay.portal.repository.capabilities.CapabilityRepository;
import com.liferay.portal.repository.liferayrepository.LiferayRepository;
import com.liferay.portal.repository.proxy.BaseRepositoryProxyBean;
import com.liferay.portal.repository.registry.RepositoryClassDefinition;
import com.liferay.portal.repository.registry.RepositoryClassDefinitionCatalog;
import com.liferay.portal.repository.registry.RepositoryInstanceDefinition;
import com.liferay.portal.service.ClassNameLocalService;
import com.liferay.portal.service.RepositoryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileEntryService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionLocalService;
import com.liferay.portlet.documentlibrary.service.DLFileVersionService;
import com.liferay.portlet.documentlibrary.service.DLFolderLocalService;
import com.liferay.portlet.documentlibrary.service.DLFolderService;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author Adolfo Pérez
 */
public class RepositoryFactoryImpl implements RepositoryFactory {

	@Override
	public LocalRepository createLocalRepository(long repositoryId)
		throws PortalException {

		long classNameId = getRepositoryClassNameId(repositoryId);

		RepositoryClassDefinition repositoryClassDefinition =
			getRepositoryClassDefinition(classNameId);

		RepositoryFactory repositoryFactory =
			repositoryClassDefinition.getRepositoryFactory();

		LocalRepository localRepository =
			repositoryFactory.createLocalRepository(repositoryId);

		RepositoryInstanceDefinition repositoryInstanceDefinition =
			repositoryClassDefinition.createRepositoryInstanceDefinition(
				localRepository);

		return new CapabilityLocalRepository(
			localRepository,
			repositoryInstanceDefinition.getSupportedCapabilities(),
			repositoryInstanceDefinition.getExportedCapabilities(),
			repositoryClassDefinition.getRepositoryEventTrigger());
	}

	@Override
	public Repository createRepository(long repositoryId)
		throws PortalException {

		long classNameId = getRepositoryClassNameId(repositoryId);

		RepositoryClassDefinition repositoryClassDefinition =
			getRepositoryClassDefinition(classNameId);

		RepositoryFactory repositoryFactory =
			repositoryClassDefinition.getRepositoryFactory();

		Repository repository = repositoryFactory.createRepository(
			repositoryId);

		RepositoryInstanceDefinition repositoryInstanceDefinition =
			repositoryClassDefinition.createRepositoryInstanceDefinition(
				repository);

		Map<Class<? extends Capability>, Capability>
			externalSupportedCapabilities =
				repositoryInstanceDefinition.getSupportedCapabilities();
		Set<Class<? extends Capability>> externalExportedCapabilityClasses =
			repositoryInstanceDefinition.getExportedCapabilities();

		CMISRepositoryHandler cmisRepositoryHandler = getCMISRepositoryHandler(
			repository);

		if (cmisRepositoryHandler != null) {
			externalSupportedCapabilities =
				new HashMap<Class<? extends Capability>, Capability>(
					externalSupportedCapabilities);

			externalSupportedCapabilities.put(
				CMISRepositoryHandler.class, cmisRepositoryHandler);

			externalExportedCapabilityClasses =
				new HashSet<Class<? extends Capability>>(
					externalExportedCapabilityClasses);

			externalExportedCapabilityClasses.add(CMISRepositoryHandler.class);
		}

		return new CapabilityRepository(
			repository, externalSupportedCapabilities,
			externalExportedCapabilityClasses,
			repositoryClassDefinition.getRepositoryEventTrigger());
	}

	protected CMISRepositoryHandler getCMISRepositoryHandler(
		Repository repository) {

		if (repository instanceof BaseRepositoryProxyBean) {
			BaseRepositoryProxyBean baseRepositoryProxyBean =
				(BaseRepositoryProxyBean)repository;

			ClassLoaderBeanHandler classLoaderBeanHandler =
				(ClassLoaderBeanHandler)ProxyUtil.getInvocationHandler(
					baseRepositoryProxyBean.getProxyBean());

			Object bean = classLoaderBeanHandler.getBean();

			if (bean instanceof CMISRepositoryHandler) {
				return (CMISRepositoryHandler)bean;
			}
		}

		return null;
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

	protected com.liferay.portal.model.Repository getRepository(
		long repositoryId) {

		RepositoryLocalService repositoryLocalService =
			getRepositoryLocalService();

		return repositoryLocalService.fetchRepository(repositoryId);
	}

	protected RepositoryClassDefinition getRepositoryClassDefinition(
			long classNameId)
		throws PortalException {

		ClassName className = _classNameLocalService.getClassName(classNameId);

		RepositoryClassDefinition repositoryDefinition =
			_repositoryClassDefinitionCatalog.getRepositoryClassDefinition(
				className.getClassName());

		if (repositoryDefinition == null) {
			throw new UndeployedExternalRepositoryException(className);
		}

		return repositoryDefinition;
	}

	protected long getRepositoryClassNameId(long repositoryId) {
		com.liferay.portal.model.Repository repository =
			_repositoryLocalService.fetchRepository(repositoryId);

		if (repository != null) {
			return repository.getClassNameId();
		}

		return _classNameLocalService.getClassNameId(
			LiferayRepository.class.getName());
	}

	protected RepositoryLocalService getRepositoryLocalService() {
		return _repositoryLocalService;
	}

	@BeanReference(type = ClassNameLocalService.class)
	private ClassNameLocalService _classNameLocalService;

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

	@BeanReference(type = RepositoryClassDefinitionCatalog.class)
	private RepositoryClassDefinitionCatalog _repositoryClassDefinitionCatalog;

	@BeanReference(type = RepositoryLocalService.class)
	private RepositoryLocalService _repositoryLocalService;

}