/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.exportimport.data.handler;

import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.service.BatchEngineExportTaskService;
import com.liferay.batch.engine.service.BatchEngineImportTaskService;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.feature.flag.FeatureFlagListener;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
public class VulcanBatchEnginePortletDataHandlerRegistry {

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
			MapUtil.singletonDictionary("featureFlagKey", "LPD-35914"));
	}

	@Deactivate
	protected void deactivate() {
		_serviceRegistration.unregister();
		_serviceTracker.close();

		for (ServiceRegistration<PortletDataHandler> serviceRegistration :
				_serviceRegistrations.values()) {

			try {
				serviceRegistration.unregister();
			}
			catch (IllegalStateException illegalStateException) {
				_log.error(illegalStateException);
			}
		}

		_serviceRegistrations.clear();
	}

	private static final Log _log = LogFactoryUtil.getLog(
		VulcanBatchEnginePortletDataHandlerRegistry.class);

	@Reference
	private BatchEngineExportTaskExecutor _batchEngineExportTaskExecutor;

	@Reference
	private BatchEngineExportTaskService _batchEngineExportTaskService;

	@Reference
	private BatchEngineImportTaskExecutor _batchEngineImportTaskExecutor;

	@Reference
	private BatchEngineImportTaskService _batchEngineImportTaskService;

	private ServiceRegistration<FeatureFlagListener> _serviceRegistration;
	private final Map
		<ServiceReference<VulcanBatchEngineTaskItemDelegate>,
		 ServiceRegistration<PortletDataHandler>> _serviceRegistrations =
			new ConcurrentHashMap<>();
	private ServiceTracker
		<VulcanBatchEngineTaskItemDelegate, PortletDataHandler> _serviceTracker;

	private class VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<VulcanBatchEngineTaskItemDelegate, PortletDataHandler> {

		public VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		@Override
		public PortletDataHandler addingService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate>
				serviceReference) {

			String portletId = (String)serviceReference.getProperty(
				"batch.engine.task.item.delegate.portlet.id");

			if (Validator.isNull(portletId)) {
				return null;
			}

			VulcanBatchEnginePortletDataHandler
				vulcanBatchEnginePortletDataHandler =
					new VulcanBatchEnginePortletDataHandler(
						_batchEngineExportTaskExecutor,
						_batchEngineExportTaskService,
						_batchEngineImportTaskExecutor,
						_batchEngineImportTaskService,
						(String)serviceReference.getProperty(
							"batch.engine.task.item.delegate.class.name"),
						(String)serviceReference.getProperty(
							"batch.engine.task.item.delegate.name"));

			_serviceRegistrations.put(
				serviceReference,
				_bundleContext.registerService(
					PortletDataHandler.class,
					vulcanBatchEnginePortletDataHandler,
					HashMapDictionaryBuilder.<String, Object>put(
						"javax.portlet.name", portletId
					).build()));

			return vulcanBatchEnginePortletDataHandler;
		}

		@Override
		public void modifiedService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate>
				serviceReference,
			PortletDataHandler portletDataHandler) {

			removedService(serviceReference, portletDataHandler);

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate>
				serviceReference,
			PortletDataHandler portletDataHandler) {

			ServiceRegistration<PortletDataHandler> serviceRegistration =
				_serviceRegistrations.remove(serviceReference);

			serviceRegistration.unregister();
		}

		private final BundleContext _bundleContext;

	}

}