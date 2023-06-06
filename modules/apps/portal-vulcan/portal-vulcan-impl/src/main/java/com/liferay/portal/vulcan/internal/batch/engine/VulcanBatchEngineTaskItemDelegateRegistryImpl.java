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

package com.liferay.portal.vulcan.internal.batch.engine;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegateRegistry;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.Filter;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Javier de Arcos
 */
@Component(service = VulcanBatchEngineTaskItemDelegateRegistry.class)
public class VulcanBatchEngineTaskItemDelegateRegistryImpl
	implements VulcanBatchEngineTaskItemDelegateRegistry {

	@Override
	public Set<String> getEntityClassNames(long companyId) {
		Set<String> entityClassNames = new HashSet<>();

		entityClassNames.addAll(_vulcanBatchEngineTaskItemDelegateMap.keySet());

		Map<String, VulcanBatchEngineTaskItemDelegate<?>>
			companyVulcanBatchEngineTaskItemDelegateMap =
				_companyScopedVulcanBatchEngineTaskItemDelegateMap.get(
					companyId);

		if (companyVulcanBatchEngineTaskItemDelegateMap != null) {
			entityClassNames.addAll(
				companyVulcanBatchEngineTaskItemDelegateMap.keySet());
		}

		return entityClassNames;
	}

	@Override
	public VulcanBatchEngineTaskItemDelegate<?>
		getVulcanBatchEngineTaskItemDelegate(
			long companyId, String entityClassName) {

		VulcanBatchEngineTaskItemDelegate<?> vulcanBatchEngineTaskItemDelegate =
			_vulcanBatchEngineTaskItemDelegateMap.get(entityClassName);

		if (vulcanBatchEngineTaskItemDelegate != null) {
			return vulcanBatchEngineTaskItemDelegate;
		}

		Map<String, VulcanBatchEngineTaskItemDelegate<?>>
			companyVulcanBatchEngineTaskItemDelegateMap =
				_companyScopedVulcanBatchEngineTaskItemDelegateMap.get(
					companyId);

		if (companyVulcanBatchEngineTaskItemDelegateMap != null) {
			return companyVulcanBatchEngineTaskItemDelegateMap.get(
				entityClassName);
		}

		return null;
	}

	@Override
	public boolean isBatchPlannerExportEnabled(
		long companyId, String entityClassName) {

		Boolean batchPlannerExportEnabled = _batchPlannerExportEnabledMap.get(
			entityClassName);

		if (batchPlannerExportEnabled != null) {
			return batchPlannerExportEnabled;
		}

		Map<String, Boolean> companyBatchPlannerExportEnabledMap =
			_companyScopedBatchPlannerExportEnabledMap.get(companyId);

		return companyBatchPlannerExportEnabledMap.get(entityClassName);
	}

	@Override
	public boolean isBatchPlannerImportEnabled(
		long companyId, String entityClassName) {

		Boolean batchPlannerImportEnabled = _batchPlannerImportEnabledMap.get(
			entityClassName);

		if (batchPlannerImportEnabled != null) {
			return batchPlannerImportEnabled;
		}

		Map<String, Boolean> companyBatchPlannerImportEnabledMap =
			_companyScopedBatchPlannerImportEnabledMap.get(companyId);

		return companyBatchPlannerImportEnabledMap.get(entityClassName);
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		Filter filter = bundleContext.createFilter(
			"(batch.engine.task.item.delegate=true)");

		_serviceTracker = new ServiceTracker<>(
			bundleContext, filter,
			new VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer(
				bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private final Map<String, Boolean> _batchPlannerExportEnabledMap =
		new HashMap<>();
	private final Map<String, Boolean> _batchPlannerImportEnabledMap =
		new HashMap<>();
	private final Map<Long, Map<String, Boolean>>
		_companyScopedBatchPlannerExportEnabledMap = new HashMap<>();
	private final Map<Long, Map<String, Boolean>>
		_companyScopedBatchPlannerImportEnabledMap = new HashMap<>();
	private final Map<Long, Map<String, VulcanBatchEngineTaskItemDelegate<?>>>
		_companyScopedVulcanBatchEngineTaskItemDelegateMap = new HashMap<>();
	private ServiceTracker<?, ?> _serviceTracker;
	private final Map<String, VulcanBatchEngineTaskItemDelegate<?>>
		_vulcanBatchEngineTaskItemDelegateMap = new HashMap<>();

	private class VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<VulcanBatchEngineTaskItemDelegate<?>,
			 VulcanBatchEngineTaskItemDelegate<?>> {

		@Override
		public VulcanBatchEngineTaskItemDelegate<?> addingService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate<?>>
				serviceReference) {

			VulcanBatchEngineTaskItemDelegate<?>
				vulcanBatchEngineTaskItemDelegate = _bundleContext.getService(
					serviceReference);

			String entityClassName = (String)serviceReference.getProperty(
				"entity.class.name");

			List<String> companyIds = (List)serviceReference.getProperty(
				"companyId");

			if (companyIds == null) {
				_batchPlannerExportEnabledMap.put(
					entityClassName,
					GetterUtil.getBoolean(
						serviceReference.getProperty(
							"batch.planner.export.enabled")));

				_batchPlannerImportEnabledMap.put(
					entityClassName,
					GetterUtil.getBoolean(
						serviceReference.getProperty(
							"batch.planner.import.enabled")));

				_vulcanBatchEngineTaskItemDelegateMap.put(
					entityClassName, vulcanBatchEngineTaskItemDelegate);
			}
			else {
				for (String companyIdString : companyIds) {
					long companyId = GetterUtil.getLong(companyIdString);

					Map<String, Boolean> companyBatchPlannerExportEnabledMap =
						_companyScopedBatchPlannerExportEnabledMap.get(
							companyId);

					if (companyBatchPlannerExportEnabledMap == null) {
						companyBatchPlannerExportEnabledMap = new HashMap<>();

						_companyScopedBatchPlannerExportEnabledMap.put(
							companyId, companyBatchPlannerExportEnabledMap);
					}

					companyBatchPlannerExportEnabledMap.put(
						entityClassName,
						GetterUtil.getBoolean(
							serviceReference.getProperty(
								"batch.planner.export.enabled")));

					Map<String, Boolean> companyBatchPlannerImportEnabledMap =
						_companyScopedBatchPlannerImportEnabledMap.get(
							companyId);

					if (companyBatchPlannerImportEnabledMap == null) {
						companyBatchPlannerImportEnabledMap = new HashMap<>();

						_companyScopedBatchPlannerImportEnabledMap.put(
							companyId, companyBatchPlannerImportEnabledMap);
					}

					companyBatchPlannerImportEnabledMap.put(
						entityClassName,
						GetterUtil.getBoolean(
							serviceReference.getProperty(
								"batch.planner.import.enabled")));

					Map<String, VulcanBatchEngineTaskItemDelegate<?>>
						companyVulcanBatchEngineTaskItemDelegateMap =
							_companyScopedVulcanBatchEngineTaskItemDelegateMap.
								get(companyId);

					if (companyVulcanBatchEngineTaskItemDelegateMap == null) {
						companyVulcanBatchEngineTaskItemDelegateMap =
							new HashMap<>();

						_companyScopedVulcanBatchEngineTaskItemDelegateMap.put(
							companyId,
							companyVulcanBatchEngineTaskItemDelegateMap);
					}

					companyVulcanBatchEngineTaskItemDelegateMap.put(
						entityClassName, vulcanBatchEngineTaskItemDelegate);
				}
			}

			return vulcanBatchEngineTaskItemDelegate;
		}

		@Override
		public void modifiedService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate<?>>
				serviceReference,
			VulcanBatchEngineTaskItemDelegate<?>
				vulcanBatchEngineTaskItemDelegate) {

			List<String> companyIds = (List)serviceReference.getProperty(
				"companyId");

			if (companyIds == null) {
				return;
			}

			String entityClassName = (String)serviceReference.getProperty(
				"entity.class.name");

			for (Map.Entry<Long, Map<String, Boolean>> entry :
					_companyScopedBatchPlannerExportEnabledMap.entrySet()) {

				if (companyIds.contains(String.valueOf(entry.getKey()))) {
					continue;
				}

				Map<String, Boolean> companyBatchPlannerExportEnabledMap =
					entry.getValue();

				companyBatchPlannerExportEnabledMap.remove(entityClassName);
			}

			for (Map.Entry<Long, Map<String, Boolean>> entry :
					_companyScopedBatchPlannerImportEnabledMap.entrySet()) {

				if (companyIds.contains(String.valueOf(entry.getKey()))) {
					continue;
				}

				Map<String, Boolean> companyBatchPlannerImportEnabledMap =
					entry.getValue();

				companyBatchPlannerImportEnabledMap.remove(entityClassName);
			}

			for (Map.Entry
					<Long, Map<String, VulcanBatchEngineTaskItemDelegate<?>>>
						entry :
							_companyScopedVulcanBatchEngineTaskItemDelegateMap.
								entrySet()) {

				if (companyIds.contains(String.valueOf(entry.getKey()))) {
					continue;
				}

				Map<String, VulcanBatchEngineTaskItemDelegate<?>>
					companyVulcanBatchEngineTaskItemDelegateMap =
						entry.getValue();

				companyVulcanBatchEngineTaskItemDelegateMap.remove(
					entityClassName);
			}

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate<?>>
				serviceReference,
			VulcanBatchEngineTaskItemDelegate<?>
				vulcanBatchEngineTaskItemDelegate) {

			String entityClassName = (String)serviceReference.getProperty(
				"entity.class.name");

			List<String> companyIds = (List)serviceReference.getProperty(
				"companyId");

			if (companyIds == null) {
				_batchPlannerExportEnabledMap.remove(entityClassName);

				_batchPlannerImportEnabledMap.remove(entityClassName);

				_vulcanBatchEngineTaskItemDelegateMap.remove(entityClassName);
			}
			else {
				for (String companyIdString : companyIds) {
					long companyId = GetterUtil.getLong(companyIdString);

					Map<String, Boolean> companyBatchPlannerExportEnabledMap =
						_companyScopedBatchPlannerExportEnabledMap.get(
							companyId);

					companyBatchPlannerExportEnabledMap.remove(entityClassName);

					Map<String, Boolean> companyBatchPlannerImportEnabledMap =
						_companyScopedBatchPlannerImportEnabledMap.get(
							companyId);

					companyBatchPlannerImportEnabledMap.remove(entityClassName);

					Map<String, VulcanBatchEngineTaskItemDelegate<?>>
						companyVulcanBatchEngineTaskItemDelegateMap =
							_companyScopedVulcanBatchEngineTaskItemDelegateMap.
								get(companyId);

					companyVulcanBatchEngineTaskItemDelegateMap.remove(
						entityClassName);
				}
			}
		}

		private VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private final BundleContext _bundleContext;

	}

}