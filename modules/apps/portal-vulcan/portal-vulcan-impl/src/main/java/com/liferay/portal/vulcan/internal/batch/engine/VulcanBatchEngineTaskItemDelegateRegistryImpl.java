/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

		entityClassNames.addAll(_vulcanBatchEngineTaskItemDelegates.keySet());

		Map<String, VulcanBatchEngineTaskItemDelegate<?>>
			companyVulcanBatchEngineTaskItemDelegates =
				_companyScopedVulcanBatchEngineTaskItemDelegatesMap.get(
					companyId);

		if (companyVulcanBatchEngineTaskItemDelegates != null) {
			entityClassNames.addAll(
				companyVulcanBatchEngineTaskItemDelegates.keySet());
		}

		return entityClassNames;
	}

	@Override
	public VulcanBatchEngineTaskItemDelegate<?>
		getVulcanBatchEngineTaskItemDelegate(
			long companyId, String entityClassName) {

		VulcanBatchEngineTaskItemDelegate<?> vulcanBatchEngineTaskItemDelegate =
			_vulcanBatchEngineTaskItemDelegates.get(entityClassName);

		if (vulcanBatchEngineTaskItemDelegate != null) {
			return vulcanBatchEngineTaskItemDelegate;
		}

		Map<String, VulcanBatchEngineTaskItemDelegate<?>>
			companyVulcanBatchEngineTaskItemDelegates =
				_companyScopedVulcanBatchEngineTaskItemDelegatesMap.get(
					companyId);

		if (companyVulcanBatchEngineTaskItemDelegates != null) {
			return companyVulcanBatchEngineTaskItemDelegates.get(
				entityClassName);
		}

		return null;
	}

	@Override
	public boolean isBatchPlannerExportEnabled(
		long companyId, String entityClassName) {

		Boolean batchPlannerExportEnabled = _batchPlannerExportEnableds.get(
			entityClassName);

		if (batchPlannerExportEnabled != null) {
			return batchPlannerExportEnabled;
		}

		Map<String, Boolean> companyBatchPlannerExportEnableds =
			_companyScopedBatchPlannerExportEnabledsMap.get(companyId);

		return companyBatchPlannerExportEnableds.get(entityClassName);
	}

	@Override
	public boolean isBatchPlannerImportEnabled(
		long companyId, String entityClassName) {

		Boolean batchPlannerImportEnabled = _batchPlannerImportEnableds.get(
			entityClassName);

		if (batchPlannerImportEnabled != null) {
			return batchPlannerImportEnabled;
		}

		Map<String, Boolean> companyBatchPlannerImportEnableds =
			_companyScopedBatchPlannerImportEnabledsMap.get(companyId);

		return companyBatchPlannerImportEnableds.get(entityClassName);
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

	private final Map<String, Boolean> _batchPlannerExportEnableds =
		new HashMap<>();
	private final Map<String, Boolean> _batchPlannerImportEnableds =
		new HashMap<>();
	private final Map<Long, Map<String, Boolean>>
		_companyScopedBatchPlannerExportEnabledsMap = new HashMap<>();
	private final Map<Long, Map<String, Boolean>>
		_companyScopedBatchPlannerImportEnabledsMap = new HashMap<>();
	private final Map<Long, Map<String, VulcanBatchEngineTaskItemDelegate<?>>>
		_companyScopedVulcanBatchEngineTaskItemDelegatesMap = new HashMap<>();
	private ServiceTracker<?, ?> _serviceTracker;
	private final Map<String, VulcanBatchEngineTaskItemDelegate<?>>
		_vulcanBatchEngineTaskItemDelegates = new HashMap<>();

	private class VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<VulcanBatchEngineTaskItemDelegate<?>,
			 VulcanBatchEngineTaskItemDelegate<?>> {

		@Override
		public VulcanBatchEngineTaskItemDelegate<?> addingService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate<?>>
				serviceReference) {

			boolean batchPlannerExportEnabled = GetterUtil.getBoolean(
				serviceReference.getProperty("batch.planner.export.enabled"));
			boolean batchPlannerImportEnabled = GetterUtil.getBoolean(
				serviceReference.getProperty("batch.planner.import.enabled"));
			List<String> companyIdStrings = (List)serviceReference.getProperty(
				"companyId");
			String entityClassName = (String)serviceReference.getProperty(
				"entity.class.name");
			VulcanBatchEngineTaskItemDelegate<?>
				vulcanBatchEngineTaskItemDelegate = _bundleContext.getService(
					serviceReference);

			if (companyIdStrings == null) {
				_batchPlannerExportEnableds.put(
					entityClassName, batchPlannerExportEnabled);
				_batchPlannerImportEnableds.put(
					entityClassName, batchPlannerImportEnabled);
				_vulcanBatchEngineTaskItemDelegates.put(
					entityClassName, vulcanBatchEngineTaskItemDelegate);
			}
			else {
				for (String companyIdString : companyIdStrings) {
					long companyId = GetterUtil.getLong(companyIdString);

					_companyScopedBatchPlannerExportEnabledsMap.compute(
						companyId,
						(key, batchPlannerExportEnabledMap) -> {
							if (batchPlannerExportEnabledMap == null) {
								batchPlannerExportEnabledMap = new HashMap<>();
							}

							batchPlannerExportEnabledMap.put(
								entityClassName, batchPlannerExportEnabled);

							return batchPlannerExportEnabledMap;
						});
					_companyScopedBatchPlannerImportEnabledsMap.compute(
						companyId,
						(key, batchPlannerImportEnabledMap) -> {
							if (batchPlannerImportEnabledMap == null) {
								batchPlannerImportEnabledMap = new HashMap<>();
							}

							batchPlannerImportEnabledMap.put(
								entityClassName, batchPlannerImportEnabled);

							return batchPlannerImportEnabledMap;
						});
					_companyScopedVulcanBatchEngineTaskItemDelegatesMap.compute(
						companyId,
						(key, vulcanBatchEngineTaskItemDelegateMap) -> {
							if (vulcanBatchEngineTaskItemDelegateMap == null) {
								vulcanBatchEngineTaskItemDelegateMap =
									new HashMap<>();
							}

							vulcanBatchEngineTaskItemDelegateMap.put(
								entityClassName,
								vulcanBatchEngineTaskItemDelegate);

							return vulcanBatchEngineTaskItemDelegateMap;
						});
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

			List<String> companyIdStrings = (List)serviceReference.getProperty(
				"companyId");

			if (companyIdStrings == null) {
				return;
			}

			String entityClassName = (String)serviceReference.getProperty(
				"entity.class.name");

			for (Map.Entry<Long, Map<String, Boolean>> entry :
					_companyScopedBatchPlannerExportEnabledsMap.entrySet()) {

				if (companyIdStrings.contains(String.valueOf(entry.getKey()))) {
					continue;
				}

				Map<String, Boolean> companyBatchPlannerExportEnableds =
					entry.getValue();

				companyBatchPlannerExportEnableds.remove(entityClassName);
			}

			for (Map.Entry<Long, Map<String, Boolean>> entry :
					_companyScopedBatchPlannerImportEnabledsMap.entrySet()) {

				if (companyIdStrings.contains(String.valueOf(entry.getKey()))) {
					continue;
				}

				Map<String, Boolean> companyBatchPlannerImportEnableds =
					entry.getValue();

				companyBatchPlannerImportEnableds.remove(entityClassName);
			}

			for (Map.Entry
					<Long, Map<String, VulcanBatchEngineTaskItemDelegate<?>>>
						entry :
							_companyScopedVulcanBatchEngineTaskItemDelegatesMap.
								entrySet()) {

				if (companyIdStrings.contains(String.valueOf(entry.getKey()))) {
					continue;
				}

				Map<String, VulcanBatchEngineTaskItemDelegate<?>>
					companyVulcanBatchEngineTaskItemDelegates =
						entry.getValue();

				companyVulcanBatchEngineTaskItemDelegates.remove(
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

			List<String> companyIdStrings = (List)serviceReference.getProperty(
				"companyId");
			String entityClassName = (String)serviceReference.getProperty(
				"entity.class.name");

			if (companyIdStrings == null) {
				_batchPlannerExportEnableds.remove(entityClassName);

				_batchPlannerImportEnableds.remove(entityClassName);

				_vulcanBatchEngineTaskItemDelegates.remove(entityClassName);
			}
			else {
				for (String companyIdString : companyIdStrings) {
					long companyId = GetterUtil.getLong(companyIdString);

					Map<String, Boolean> companyBatchPlannerExportEnableds =
						_companyScopedBatchPlannerExportEnabledsMap.get(
							companyId);

					companyBatchPlannerExportEnableds.remove(entityClassName);

					Map<String, Boolean> companyBatchPlannerImportEnableds =
						_companyScopedBatchPlannerImportEnabledsMap.get(
							companyId);

					companyBatchPlannerImportEnableds.remove(entityClassName);

					Map<String, VulcanBatchEngineTaskItemDelegate<?>>
						companyVulcanBatchEngineTaskItemDelegates =
							_companyScopedVulcanBatchEngineTaskItemDelegatesMap.
								get(companyId);

					companyVulcanBatchEngineTaskItemDelegates.remove(
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