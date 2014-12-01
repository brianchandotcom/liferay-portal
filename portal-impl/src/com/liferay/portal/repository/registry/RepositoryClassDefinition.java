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
import com.liferay.portal.kernel.repository.LocalRepository;
import com.liferay.portal.kernel.repository.Repository;
import com.liferay.portal.kernel.repository.RepositoryFactory;
import com.liferay.portal.kernel.repository.capabilities.ConfigurationCapability;
import com.liferay.portal.kernel.repository.capabilities.RepositoryEventTriggerCapability;
import com.liferay.portal.kernel.repository.cmis.CMISRepositoryHandler;
import com.liferay.portal.kernel.repository.event.RepositoryEventListener;
import com.liferay.portal.kernel.repository.event.RepositoryEventTrigger;
import com.liferay.portal.kernel.repository.event.RepositoryEventType;
import com.liferay.portal.kernel.repository.registry.RepositoryDefiner;
import com.liferay.portal.kernel.repository.registry.RepositoryEventRegistry;
import com.liferay.portal.kernel.repository.registry.RepositoryFactoryRegistry;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.Tuple;
import com.liferay.portal.repository.capabilities.BaseCapabilityRepository;
import com.liferay.portal.repository.capabilities.CapabilityLocalRepository;
import com.liferay.portal.repository.capabilities.CapabilityRepository;
import com.liferay.portal.repository.capabilities.ConfigurationCapabilityImpl;
import com.liferay.portal.repository.capabilities.LiferayRepositoryEventTriggerCapability;
import com.liferay.portal.repository.proxy.BaseRepositoryProxyBean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Adolfo Pérez
 */
public class RepositoryClassDefinition
	implements RepositoryEventRegistry, RepositoryEventTrigger,
		RepositoryFactory, RepositoryFactoryRegistry {

	public RepositoryClassDefinition(RepositoryDefiner repositoryDefiner) {
		_repositoryDefiner = repositoryDefiner;
	}

	@Override
	public LocalRepository createLocalRepository(long repositoryId)
		throws PortalException {

		LocalRepository localRepository =
			_repositoryFactory.createLocalRepository(repositoryId);

		CapabilityLocalRepository capabilityLocalRepository =
			new CapabilityLocalRepository(localRepository, this);

		_repositoryDefiner.registerCapabilities(capabilityLocalRepository);

		setupCommonCapabilities(capabilityLocalRepository);

		return capabilityLocalRepository;
	}

	@Override
	public Repository createRepository(long repositoryId)
		throws PortalException {

		Repository repository = _repositoryFactory.createRepository(
			repositoryId);

		CapabilityRepository capabilityRepository = new CapabilityRepository(
			repository, this);

		_repositoryDefiner.registerCapabilities(capabilityRepository);

		setupCommonCapabilities(capabilityRepository);

		setupCapabilityRepositoryCapabilities(capabilityRepository);

		return capabilityRepository;
	}

	@Override
	public <S extends RepositoryEventType, T>
		void registerRepositoryEventListener(
			Class<S> repositoryEventTypeClass, Class<T> modelClass,
			RepositoryEventListener<S, T> repositoryEventListener) {

		if (repositoryEventListener == null) {
			throw new NullPointerException("Repository event listener is null");
		}

		Tuple key = new Tuple(repositoryEventTypeClass, modelClass);

		Collection<RepositoryEventListener<?, ?>> repositoryEventListeners =
			_repositoryEventListeners.get(key);

		if (repositoryEventListeners == null) {
			repositoryEventListeners = new ArrayList<>();

			_repositoryEventListeners.put(key, repositoryEventListeners);
		}

		repositoryEventListeners.add(repositoryEventListener);
	}

	@Override
	public void setRepositoryFactory(RepositoryFactory repositoryFactory) {
		if (_repositoryFactory != null) {
			throw new IllegalStateException(
				"Repository factory already exists");
		}

		_repositoryFactory = repositoryFactory;
	}

	@Override
	public <S extends RepositoryEventType, T> void trigger(
			Class<S> repositoryEventTypeClass, Class<T> modelClass, T model)
		throws PortalException {

		Tuple key = new Tuple(repositoryEventTypeClass, modelClass);

		@SuppressWarnings("rawtypes")
		Collection<RepositoryEventListener<S, T>> repositoryEventListeners =
			(Collection)_repositoryEventListeners.get(key);

		if (repositoryEventListeners != null) {
			for (RepositoryEventListener<S, T> repositoryEventListener :
					repositoryEventListeners) {

				repositoryEventListener.execute(model);
			}
		}
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

	protected void setupCapabilityRepositoryCapabilities(
		CapabilityRepository capabilityRepository) {

		Repository repository = capabilityRepository.getRepository();

		CMISRepositoryHandler cmisRepositoryHandler = getCMISRepositoryHandler(
			repository);

		if (cmisRepositoryHandler != null) {
			capabilityRepository.addExportedCapability(
				CMISRepositoryHandler.class, cmisRepositoryHandler);
		}
	}

	protected void setupCommonCapabilities(
		BaseCapabilityRepository<?> baseCapabilityRepository) {

		if (!baseCapabilityRepository.isCapabilityProvided(
				ConfigurationCapability.class)) {

			baseCapabilityRepository.addExportedCapability(
				ConfigurationCapability.class,
				new ConfigurationCapabilityImpl(baseCapabilityRepository));
		}

		if (!baseCapabilityRepository.isCapabilityProvided(
				RepositoryEventTriggerCapability.class)) {

			baseCapabilityRepository.addExportedCapability(
				RepositoryEventTriggerCapability.class,
				new LiferayRepositoryEventTriggerCapability(this));
		}
	}

	private final RepositoryDefiner _repositoryDefiner;
	private final Map<Tuple, Collection<RepositoryEventListener<?, ?>>>
		_repositoryEventListeners =
			new HashMap<Tuple, Collection<RepositoryEventListener<?, ?>>>();
	private RepositoryFactory _repositoryFactory;

}