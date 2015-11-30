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

package com.liferay.exportimport.staged.model.repository;

import aQute.bnd.annotation.ProviderType;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ListUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Daniel Kocsis
 */
@ProviderType
public class StagedModelRepositoryRegistryUtil {

	public static List<StagedModelRepository<?>> getStagedModelRepositories() {
		return _instance._getStagedModelRepositories();
	}

	public static StagedModelRepository<?> getStagedModelRepository(
		String className) {

		return _instance._getStagedModelRepository(className);
	}

	private StagedModelRepositoryRegistryUtil() {
		Bundle bundle = FrameworkUtil.getBundle(
			StagedModelRepositoryRegistryUtil.class);

		_bundleContext = bundle.getBundleContext();

		Class<StagedModelRepository<?>> clazz =
			(Class)StagedModelRepository.class;

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext, clazz,
			new StagedModelRepositoryServiceTrackerCustomizer());
	}

	private List<StagedModelRepository<?>> _getStagedModelRepositories() {
		return ListUtil.fromCollection(_stagedModelRepositories.values());
	}

	private StagedModelRepository<?> _getStagedModelRepository(
		String className) {

		return _stagedModelRepositories.get(className);
	}

	private static final StagedModelRepositoryRegistryUtil _instance =
		new StagedModelRepositoryRegistryUtil();

	private final BundleContext _bundleContext;
	private final ServiceTracker
		<StagedModelRepository<?>, StagedModelRepository<?>> _serviceTracker;
	private final Map<String, StagedModelRepository<?>>
		_stagedModelRepositories = new ConcurrentHashMap<>();

	private class StagedModelRepositoryServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<StagedModelRepository<?>, StagedModelRepository<?>> {

		@Override
		public StagedModelRepository<?> addingService(
			ServiceReference<StagedModelRepository<?>> serviceReference) {

			StagedModelRepository<?> stagedModelRepository =
				_bundleContext.getService(serviceReference);

			String modelClassName = GetterUtil.getString(
				serviceReference.getProperty("model.class.name"));

			_stagedModelRepositories.put(modelClassName, stagedModelRepository);

			return stagedModelRepository;
		}

		@Override
		public void modifiedService(
			ServiceReference<StagedModelRepository<?>> serviceReference,
			StagedModelRepository<?> xStreamConfigurator) {

			removedService(serviceReference, xStreamConfigurator);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<StagedModelRepository<?>> serviceReference,
			StagedModelRepository<?> xStreamConfigurator) {

			_bundleContext.ungetService(serviceReference);

			String modelClassName = GetterUtil.getString(
				serviceReference.getProperty("model.class.name"));

			_stagedModelRepositories.remove(modelClassName);
		}

	}

}