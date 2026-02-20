/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.exportimport.internal.data.handler;

import com.liferay.batch.engine.BatchEngineExportTaskExecutor;
import com.liferay.batch.engine.BatchEngineImportTaskExecutor;
import com.liferay.batch.engine.service.BatchEngineExportTaskLocalService;
import com.liferay.batch.engine.service.BatchEngineImportTaskService;
import com.liferay.changeset.service.ChangesetEntryLocalService;
import com.liferay.exportimport.kernel.lar.ExportImportHelper;
import com.liferay.exportimport.kernel.lar.PortletDataHandler;
import com.liferay.exportimport.vulcan.batch.engine.ExportImportVulcanBatchEngineTaskItemDelegate;
import com.liferay.osgi.service.tracker.collections.EagerServiceTrackerCustomizer;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerList;
import com.liferay.osgi.service.tracker.collections.list.ServiceTrackerListFactory;
import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.portal.instance.lifecycle.BasePortalInstanceLifecycleListener;
import com.liferay.portal.instance.lifecycle.PortalInstanceLifecycleListener;
import com.liferay.portal.kernel.model.Company;
import com.liferay.portal.kernel.service.ClassNameLocalService;
import com.liferay.portal.kernel.service.GroupLocalService;
import com.liferay.portal.kernel.service.LayoutLocalService;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.HashMapDictionaryBuilder;
import com.liferay.portal.vulcan.batch.engine.VulcanBatchEngineTaskItemDelegate;
import com.liferay.staging.StagingGroupHelper;

import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicBoolean;

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
		_portalInstanceLifecycleListenerServiceRegistration =
			bundleContext.registerService(
				PortalInstanceLifecycleListener.class,
				new BasePortalInstanceLifecycleListener() {

					@Override
					public void portalInstanceRegistered(Company company) {
						_registerCompany(
							bundleContext, company.getCompanyId(), true);
					}

					@Override
					public void portalInstanceUnregistered(Company company) {
						_registerCompany(
							bundleContext, company.getCompanyId(), false);
					}

				},
				null);
	}

	@Deactivate
	protected void deactivate() {
		_portalInstanceLifecycleListenerServiceRegistration.unregister();

		_serviceTrackerListDCLSingleton.destroy(ServiceTrackerList::close);
	}

	private void _registerCompany(
		BundleContext bundleContext, long companyId, boolean enabled) {

		if (enabled) {
			if (_enabledCompanyIds.contains(companyId)) {
				return;
			}

			_enabledCompanyIds.add(companyId);
		}
		else {
			_enabledCompanyIds.remove(companyId);
		}

		if (_enabledCompanyIds.isEmpty()) {
			_serviceTrackerListDCLSingleton.destroy(ServiceTrackerList::close);

			return;
		}

		AtomicBoolean newOpen = new AtomicBoolean();

		_serviceTrackerListDCLSingleton.getSingleton(
			() -> {
				newOpen.set(true);

				return ServiceTrackerListFactory.open(
					bundleContext, null,
					"(export.import.vulcan.batch.engine.task.item." +
						"delegate=true)",
					new VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer(
						bundleContext, companyId));
			});

		if (newOpen.get()) {
			return;
		}

		for (ServiceRegistration<PortletDataHandler> serviceRegistration :
				_serviceRegistrations.values()) {

			Dictionary<String, Object> properties = _toProperties(
				serviceRegistration.getReference());

			serviceRegistration.setProperties(
				_setEnabledCompanyIds(properties));
		}
	}

	private Dictionary<String, Object> _setEnabledCompanyIds(
		Dictionary<String, Object> properties) {

		return HashMapDictionaryBuilder.<String, Object>putAll(
			properties
		).put(
			"companyId", ArrayUtil.toStringArray(_enabledCompanyIds)
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
	private ChangesetEntryLocalService _changesetEntryLocalService;

	@Reference
	private ClassNameLocalService _classNameLocalService;

	private final Set<Long> _enabledCompanyIds = new CopyOnWriteArraySet<>();

	@Reference
	private ExportImportHelper _exportImportHelper;

	@Reference
	private GroupLocalService _groupLocalService;

	@Reference
	private LayoutLocalService _layoutLocalService;

	private ServiceRegistration<PortalInstanceLifecycleListener>
		_portalInstanceLifecycleListenerServiceRegistration;
	private final Map<String, ServiceRegistration<PortletDataHandler>>
		_serviceRegistrations = new HashMap<>();
	private final DCLSingleton
		<ServiceTrackerList<ServiceRegistration<PortletDataHandler>>>
			_serviceTrackerListDCLSingleton = new DCLSingleton<>();

	@Reference
	private StagingGroupHelper _stagingGroupHelper;

	private class VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer
		implements EagerServiceTrackerCustomizer
			<VulcanBatchEngineTaskItemDelegate,
			 ServiceRegistration<PortletDataHandler>> {

		public VulcanBatchEngineTaskItemDelegateServiceTrackerCustomizer(
			BundleContext bundleContext, long companyId) {

			_bundleContext = bundleContext;
			_companyId = companyId;
		}

		@Override
		public ServiceRegistration<PortletDataHandler> addingService(
			ServiceReference<VulcanBatchEngineTaskItemDelegate>
				serviceReference) {

			ExportImportVulcanBatchEngineTaskItemDelegate<?>
				exportImportVulcanBatchEngineTaskItemDelegate =
					(ExportImportVulcanBatchEngineTaskItemDelegate<?>)
						_bundleContext.getService(serviceReference);

			ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
				exportImportDescriptor =
					exportImportVulcanBatchEngineTaskItemDelegate.
						getExportImportDescriptor();

			String portletId = exportImportDescriptor.getPortletId();

			BatchEnginePortletDataHandler
				previousBatchEnginePortletDataHandler =
					BatchEnginePortletDataHandlerRegistryUtil.getByPortletId(
						_companyId, portletId);

			BatchEnginePortletDataHandler batchEnginePortletDataHandler =
				previousBatchEnginePortletDataHandler;

			if (previousBatchEnginePortletDataHandler == null) {
				batchEnginePortletDataHandler =
					new BatchEnginePortletDataHandler(
						_batchEngineExportTaskExecutor,
						_batchEngineExportTaskLocalService,
						_batchEngineImportTaskExecutor,
						_batchEngineImportTaskService,
						_changesetEntryLocalService, _classNameLocalService,
						_exportImportHelper, _groupLocalService,
						_layoutLocalService, _stagingGroupHelper);

				batchEnginePortletDataHandler.setPortletId(
					exportImportDescriptor.getPortletId());
			}

			batchEnginePortletDataHandler.
				registerExportImportVulcanBatchEngineTaskItemDelegate(
					GetterUtil.getObject(
						(String)serviceReference.getProperty(
							"batch.engine.task.item.delegate.class.name"),
						() -> (String)serviceReference.getProperty(
							"batch.engine.entity.class.name")),
					exportImportDescriptor,
					(String)serviceReference.getProperty(
						"batch.engine.task.item.delegate.name"));

			BatchEnginePortletDataHandlerRegistryUtil.put(
				batchEnginePortletDataHandler, _companyId,
				exportImportDescriptor.getKey(), portletId);

			if (previousBatchEnginePortletDataHandler != null) {
				return _serviceRegistrations.get(portletId);
			}

			ServiceRegistration<PortletDataHandler> serviceRegistration =
				_bundleContext.registerService(
					PortletDataHandler.class, batchEnginePortletDataHandler,
					_setEnabledCompanyIds(
						HashMapDictionaryBuilder.<String, Object>put(
							"jakarta.portlet.name", portletId
						).put(
							"service.ranking", Integer.MAX_VALUE
						).build()));

			_serviceRegistrations.put(portletId, serviceRegistration);

			return serviceRegistration;
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

			ExportImportVulcanBatchEngineTaskItemDelegate<?>
				exportImportVulcanBatchEngineTaskItemDelegate =
					(ExportImportVulcanBatchEngineTaskItemDelegate<?>)
						_bundleContext.getService(serviceReference);

			ExportImportVulcanBatchEngineTaskItemDelegate.ExportImportDescriptor
				exportImportDescriptor =
					exportImportVulcanBatchEngineTaskItemDelegate.
						getExportImportDescriptor();

			String portletId = exportImportDescriptor.getPortletId();

			BatchEnginePortletDataHandler batchEnginePortletDataHandler =
				BatchEnginePortletDataHandlerRegistryUtil.getByPortletId(
					_companyId, portletId);

			if (batchEnginePortletDataHandler == null) {
				return;
			}

			batchEnginePortletDataHandler.
				unregisterExportImportVulcanBatchEngineTaskItemDelegate(
					GetterUtil.getObject(
						(String)serviceReference.getProperty(
							"batch.engine.task.item.delegate.class.name"),
						() -> (String)serviceReference.getProperty(
							"batch.engine.entity.class.name")),
					(String)serviceReference.getProperty(
						"batch.engine.task.item.delegate.name"));

			String[] classNames = batchEnginePortletDataHandler.getClassNames();

			if (classNames.length == 0) {
				serviceRegistration.unregister();

				BatchEnginePortletDataHandlerRegistryUtil.remove(
					_companyId, exportImportDescriptor.getKey(), portletId);

				_serviceRegistrations.remove(portletId);
			}
		}

		private final BundleContext _bundleContext;
		private final long _companyId;

	}

}