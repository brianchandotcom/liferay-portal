/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskItemDelegateRegistry;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskService;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.osgi.service.tracker.collections.EagerServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.function.transform.TransformUtil;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;

import java.util.Dictionary;
import java.util.HashSet;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alejandro Tardín
 */
@Component(service = {})
public class BatchEnginePortletDataHandlerRegistrar {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceRegistration = bundleContext.registerService(
			FeatureFlagListener.class,
			(companyId, featureFlagKey, enabled) -> {
				if (enabled) {
					_enabledCompanyIds.add(companyId);

					if (_serviceRegistrations == null) {
						_serviceRegistrations = ServiceTrackerListFactory.open(
							bundleContext, null,
							"(batch.engine.task.item.delegate=true)",
							new VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer(
								bundleContext));

						return;
					}
				}
				else {
					_enabledCompanyIds.remove(companyId);
				}

				if (_enabledCompanyIds.isEmpty()) {
					if (_serviceRegistrations != null) {
						_serviceRegistrations.close();

						_serviceRegistrations = null;
					}

					return;
				}

				for (ServiceRegistration<PortletDataHandler>
						serviceRegistration : _serviceRegistrations) {

					Dictionary<String, Object> properties = _toProperties(
						serviceRegistration.getReference());

					serviceRegistration.setProperties(
						_setEnabledCompanyIds(properties));
				}
			},
			MapUtil.singletonDictionary("feature.flag.key", "LPD-35914"));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();

		if (_serviceRegistrations != null) {
			_serviceRegistrations.close();
		}
	}

	private Dictionary<String, Object> _setEnabledCompanyIds(
		Dictionary<String, Object> properties) {

		return HashMapDictionaryBuilder.<String, Object>putAll(
			properties
		).put(
			"companyId",
			TransformUtil.transform(_enabledCompanyIds, String::valueOf)
		).build();
	}

	private Dictionary<String, Object> _toProperties(
		ServiceReference<?> serviceReference) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		for (String key : serviceReference.getPropertyKeys()) {
			Object value = serviceReference.getProperty(key);

			properties.put(key, value);
		}

		return properties;
	}

	@Reference
	private BatchEngineExportTaskExecutor _batchEngineExportTaskExecutor;

	@Reference
	private BatchEngineExportTaskLocalService
		_batchEngineExportTaskLocalService;

	@Reference
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	@Reference
	private BatchEngineImportTaskService _batchEngineImportTaskService;

	@Reference
	private BatchEngineTaskItemDelegateRegistry
		_batchEngineTaskItemDelegateRegistry;

	@Reference
	private CompanyLocalService _companyLocalService;

	private final Set<Long> _enabledCompanyIds = new HashSet<>();
	private volatile ServiceRegistration<FeatureFlagListener>
		_serviceRegistration;
	private ServiceTrackerList<ServiceRegistration<PortletDataHandler>>
		_serviceRegistrations;

	@Reference
	private UserLocalService _userLocalService;

	private class VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer
		implements EagerServiceTrackerCustomizer
			<VulcanBatchEngineTaskItemDelegate,
			 ServiceRegistration<PortletDataHandler>> {

		public VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public ServiceRegistration<PortletDataHandler> addingService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate>
				serviceReference) {

			VulcanBatchEngineTaskItemDelegate<?>
				vulcanBatchEngineTaskItemDelegate = _bundleContext.getService(
					serviceReference);

			if (!(vulcanBatchEngineTaskItemDelegate instanceof
					ExportImportVulcanBatchEngineTaskItemDelegate<?>
						exportImportVulcanBatchEngineTaskItemDelegate)) {

				return null;
			}

			ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
				exportImportDescriptor =
					exportImportVulcanBatchEngineTaskItemDelegate.
						getExportImportDescriptor();

			String portletId = exportImportDescriptor.getPortletId();

			if (Validator.isNull(portletId)) {
				return null;
			}

			BatchEnginePortletDataHandler batchEnginePortletDataHandler =
				new BatchEnginePortletDataHandler(
					_batchEngineExportTaskExecutor,
					_batchEngineExportTaskLocalService,
					_batchEngineImportTaskExecutor,
					_batchEngineImportTaskService,
					_batchEngineTaskItemDelegateRegistry,
					GetterUtil.getObject(
						(String)serviceReference.getProperty(
							"batch.engine.task.item.delegate.class.name"),
						() -> (String)serviceReference.getProperty(
							"batch.engine.entity.class.name")),
					_companyLocalService,
					exportImportVulcanBatchEngineTaskItemDelegate,
					exportImportDescriptor.getItemClassName(), portletId,
					(String)serviceReference.getProperty(
						"batch.engine.task.item.delegate.name"),
					_userLocalService);

			return _bundleContext.registerService(
				PortletDataHandler.class, batchEnginePortletDataHandler,
				_setEnabledCompanyIds(
					HashMapDictionaryBuilder.<String, Object>put(
						"batch.engine.task.item.delegate.item.class.name",
						exportImportDescriptor.getItemClassName()
					).put(
						"jakarta.portlet.name", portletId
					).put(
						"service.ranking", Integer.MAX_VALUE
					).build()));
		}

		@Override
		public void modifiedService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate>
				serviceReference,
			ServiceRegistration<PortletDataHandler> serviceRegistration) {
		}

		@Override
		public void removedService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate>
				serviceReference,
			ServiceRegistration<PortletDataHandler> serviceRegistration) {

			serviceRegistration.unregister();
		}

		private final BundleContext _bundleContext;

	}

}