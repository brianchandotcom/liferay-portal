/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.BatchEngineTaskItemDelegateRegistry;
import com.liferay.batch.engine.service.BatchEngineExportTaskService;
import com.liferay.batch.engine.service.BatchEngineImportTaskService;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.service.CompanyLocalService;
import com.liferay.portal.kernel.service.UserLocalService;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Alejandro Tardín
 */
@Component(service = {})
public class BatchEnginePortletDataHandlerRegistry {

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTracker = ServiceTrackerFactory.create(
			bundleContext, "(batch.engine.task.item.delegate=true)",
			new VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer(
				bundleContext));

		_serviceRegistration = bundleContext.registerService(
			FeatureFlagListener.class,
			(companyId, featureFlagKey, enabled) -> {
				if (enabled) {
					_serviceTracker.open();
				}
				else {
					_serviceTracker.close();
				}
			},
			MapUtil.singletonDictionary("feature.flag.key", "LPD-35914"));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
		_serviceTracker.close();
	}

	@Reference
	private BatchEngineExportTaskExecutor _batchEngineExportTaskExecutor;

	@Reference
	private BatchEngineExportTaskService _batchEngineExportTaskService;

	@Reference
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	@Reference
	private BatchEngineImportTaskService _batchEngineImportTaskService;

	@Reference
	private BatchEngineTaskItemDelegateRegistry
		_batchEngineTaskItemDelegateRegistry;

	@Reference
	private CompanyLocalService _companyLocalService;

	private ServiceRegistration<FeatureFlagListener> _serviceRegistration;
	private ServiceTracker
		<VulcanBatchEngineTaskItemDelegate,
		 ServiceRegistration<PortletDataHandler>> _serviceTracker;

	@Reference
	private UserLocalService _userLocalService;

	private class VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
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

			String portletId =
				exportImportVulcanBatchEngineTaskItemDelegate.getPortletId();

			if (Validator.isNull(portletId)) {
				return null;
			}

			BatchEnginePortletDataHandler batchEnginePortletDataHandler =
				new BatchEnginePortletDataHandler(
					_batchEngineExportTaskExecutor,
					_batchEngineExportTaskService,
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
					(String)serviceReference.getProperty(
						"batch.engine.task.item.delegate.item.class.name"),
					(String)serviceReference.getProperty(
						"batch.engine.task.item.delegate.name"),
					_userLocalService);

			return _bundleContext.registerService(
				PortletDataHandler.class, batchEnginePortletDataHandler,
				HashMapDictionaryBuilder.<String, Object>put(
					"batch.engine.task.item.delegate.item.class.name",
					(String)serviceReference.getProperty(
						"batch.engine.task.item.delegate.item.class.name")
				).put(
					"jakarta.portlet.name", portletId
				).put(
					"service.ranking", Integer.MAX_VALUE
				).build());
		}

		@Override
		public void modifiedService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate>
				serviceReference,
			ServiceRegistration<PortletDataHandler> serviceRegistration) {

			removedService(serviceReference, serviceRegistration);

			addingService(serviceReference);
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