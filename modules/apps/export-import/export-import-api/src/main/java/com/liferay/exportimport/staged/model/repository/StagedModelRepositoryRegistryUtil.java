/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.staged.model.repository;

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
public class StagedModelRepositoryRegistryUtil {

	public static List<StagedModelRepository<?>> getStagedModelRepositories() {
		return _stagedModelRepositoryRegistryUtil._getStagedModelRepositories();
	}

	public static StagedModelRepository<?> getStagedModelRepository(
		String className) {

		return _stagedModelRepositoryRegistryUtil._getStagedModelRepository(
			className);
	}

	private StagedModelRepositoryRegistryUtil() {
		Bundle bundle = FrameworkUtil.getBundle(
			StagedModelRepositoryRegistryUtil.class);

		_bundleContext = bundle.getBundleContext();

		_serviceTracker = ServiceTrackerFactory.open(
			_bundleContext,
			(Class<StagedModelRepository<?>>)
				(Class<?>)StagedModelRepository.class,
			new StagedModelRepositoryServiceTrackerCustomizer());
	}

	private List<StagedModelRepository<?>> _getStagedModelRepositories() {
		return ListUtil.fromCollection(_stagedModelRepositories.values());
	}

	private StagedModelRepository<?> _getStagedModelRepository(
		String className) {

		return _stagedModelRepositories.get(className);
	}

	private static final StagedModelRepositoryRegistryUtil
		_stagedModelRepositoryRegistryUtil =
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