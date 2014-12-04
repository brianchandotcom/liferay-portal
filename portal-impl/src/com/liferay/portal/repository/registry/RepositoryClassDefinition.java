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

package com.liferay.portal.repository.registry;

import com.liferay.portal.kernel.bean.ClassLoaderBeanHandler;
import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.repository.DocumentRepository;
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.ConfigurationCapability;
import com.liferay.portal.kernel.repository.capabilities.RepositoryEventTriggerCapability;
import com.liferay.portal.kernel.repository.cmis.CMISRepositoryHandler;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.kernel.repository.registry.CapabilityRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.repository.capabilities.CapabilityLocalRepository;
import com.liferay.portal.repository.capabilities.CapabilityRepository;
import com.liferay.portal.repository.capabilities.ConfigurationCapabilityImpl;
import com.liferay.portal.repository.capabilities.LiferayRepositoryEventTriggerCapability;
import com.liferay.portal.repository.proxy.BaseRepositoryProxyBean;

/**
 * @author Adolfo Pérez
 */
public class RepositoryClassDefinition
	implements RepositoryFactory, RepositoryFactoryRegistry {

	public static final RepositoryClassDefinition fromRepositoryDefiner(
		RepositoryDefiner repositoryDefiner) {

		DefaultRepositoryEventRegistry defaultRepositoryEventRegistry =
			new DefaultRepositoryEventRegistry(null);

		RepositoryClassDefinition repositoryClassDefinition =
			new RepositoryClassDefinition(
				repositoryDefiner, defaultRepositoryEventRegistry);

		repositoryDefiner.registerRepositoryFactory(repositoryClassDefinition);
		repositoryDefiner.registerRepositoryEventListeners(
			defaultRepositoryEventRegistry);

		return repositoryClassDefinition;
	}

	@Override
	public LocalRepository createLocalRepository(long repositoryId)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryFactory.createLocalRepository(repositoryId);

		DefaultCapabilityRegistry defaultCapabilityRegistry =
			new DefaultCapabilityRegistry(localRepository);

		_repositoryDefiner.registerCapabilities(defaultCapabilityRegistry);

		DefaultRepositoryEventRegistry defaultRepositoryEventRegistry =
			new DefaultRepositoryEventRegistry(_rootRepositoryEventTrigger);

		setUpCommonCapabilities(
			localRepository, defaultCapabilityRegistry,
			defaultRepositoryEventRegistry);

		defaultCapabilityRegistry.registerCapabilityRepositoryEvents(
			defaultRepositoryEventRegistry);

		LocalRepository wrappedLocalRepository =
			defaultCapabilityRegistry.invokeCapabilityWrappers(localRepository);

		CapabilityLocalRepository capabilityLocalRepository =
			new CapabilityLocalRepository(
				wrappedLocalRepository, defaultCapabilityRegistry,
				defaultRepositoryEventRegistry);

		return capabilityLocalRepository;
	}

	@Override
	public Repository createRepository(long repositoryId)
		throws PortalException {

		Repository repository = _repositoryFactory.createRepository(
			repositoryId);

		DefaultCapabilityRegistry defaultCapabilityRegistry =
			new DefaultCapabilityRegistry(repository);

		_repositoryDefiner.registerCapabilities(defaultCapabilityRegistry);

		DefaultRepositoryEventRegistry defaultRepositoryEventRegistry =
			new DefaultRepositoryEventRegistry(_rootRepositoryEventTrigger);

		setUpCommonCapabilities(
			repository, defaultCapabilityRegistry,
			defaultRepositoryEventRegistry);

		setUpCapabilityRepositoryCapabilities(
			repository, defaultCapabilityRegistry);

		defaultCapabilityRegistry.registerCapabilityRepositoryEvents(
			defaultRepositoryEventRegistry);

		Repository wrappedRepository =
			defaultCapabilityRegistry.invokeCapabilityWrappers(repository);

		CapabilityRepository capabilityRepository = new CapabilityRepository(
			wrappedRepository, defaultCapabilityRegistry,
			defaultRepositoryEventRegistry);

		return capabilityRepository;
	}

	@Override
	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		if (_repositoryFactory != null) {
			throw new IllegalStateException(
				"Repository factory already exists");
		}

		_repositoryFactory = repositoryFactory;
	}

	protected RepositoryClassDefinition(
		RepositoryDefiner repositoryDefiner,
		RepositoryEventTrigger rootRepositoryEventTrigger) {

		_repositoryDefiner = repositoryDefiner;
		_rootRepositoryEventTrigger = rootRepositoryEventTrigger;
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

	protected void setUpCapabilityRepositoryCapabilities(
		Repository repository, CapabilityRegistry capabilityRegistry) {

		CMISRepositoryHandler cmisRepositoryHandler = getCMISRepositoryHandler(
			repository);

		if (cmisRepositoryHandler != null) {
			capabilityRegistry.addExportedCapability(
				CMISRepositoryHandler.class, cmisRepositoryHandler);
		}
	}

	protected void setUpCommonCapabilities(
		DocumentRepository documentRepository,
		DefaultCapabilityRegistry capabilityRegistry,
		RepositoryEventTrigger repositoryEventTrigger) {

		if (!capabilityRegistry.isCapabilityProvided(
				ConfigurationCapability.class)) {

			capabilityRegistry.addExportedCapability(
				ConfigurationCapability.class,
				new ConfigurationCapabilityImpl(documentRepository));
		}

		if (!capabilityRegistry.isCapabilityProvided(
				RepositoryEventTriggerCapability.class)) {

			capabilityRegistry.addExportedCapability(
				RepositoryEventTriggerCapability.class,
				new LiferayRepositoryEventTriggerCapability(
					repositoryEventTrigger));
		}
	}

	private final RepositoryDefiner _repositoryDefiner;
	private RepositoryFactory _repositoryFactory;
	private final RepositoryEventTrigger _rootRepositoryEventTrigger;

}