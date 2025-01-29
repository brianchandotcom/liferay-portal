/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.vulcan.internal.crud;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.vulcan.crud.VulcanCRUDItemDelegate;
import com.liferay.portal.vulcan.crud.VulcanCRUDItemDelegateRegistry;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
 * @author Marco Leo
 */
@Component(service = VulcanCRUDItemDelegateRegistry.class)
public class VulcanCRUDItemDelegateRegistryImpl
	implements VulcanCRUDItemDelegateRegistry {

	@Override
	public VulcanCRUDItemDelegate<?> getVulcanCRUDItemDelegate(
		long companyId, String entityClassName) {

		VulcanCRUDItemDelegate<?> vulcanCRUDItemDelegate =
			_vulcanCRUDItemDelegates.get(entityClassName);

		if (vulcanCRUDItemDelegate != null) {
			return vulcanCRUDItemDelegate;
		}

		Map<String, VulcanCRUDItemDelegate<?>> companyVulcanCRUDItemDelegates =
			_companyScopedCRUDItemDelegatesMap.get(companyId);

		if (companyVulcanCRUDItemDelegates != null) {
			return companyVulcanCRUDItemDelegates.get(entityClassName);
		}

		return null;
	}

	@Activate
	protected void activate(BundleContext bundleContext)
		throws InvalidSyntaxException {

		Filter filter = bundleContext.createFilter("(crud.item.delegate=true)");

		_serviceTracker = new ServiceTracker<>(
			bundleContext, filter,
			new VulcanCRUDItemDelegateServiceTrackerCustomizer(bundleContext));

		_serviceTracker.open();
	}

	@Deactivate
	protected void deactivate() {
		_serviceTracker.close();
	}

	private final Map<Long, Map<String, VulcanCRUDItemDelegate<?>>>
		_companyScopedCRUDItemDelegatesMap = new HashMap<>();
	private ServiceTracker<?, ?> _serviceTracker;
	private final Map<String, VulcanCRUDItemDelegate<?>>
		_vulcanCRUDItemDelegates = new HashMap<>();

	private class VulcanCRUDItemDelegateServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<VulcanCRUDItemDelegate<?>, VulcanCRUDItemDelegate<?>> {

		@Override
		public VulcanCRUDItemDelegate<?> addingService(
			ServiceReference<VulcanCRUDItemDelegate<?>> serviceReference) {

			List<String> companyIdStrings = _getCompanyIdStrings(
				serviceReference);
			String entityClassName = (String)serviceReference.getProperty(
				"entity.class.name");
			VulcanCRUDItemDelegate<?> vulcanCRUDItemDelegate =
				_bundleContext.getService(serviceReference);

			if (companyIdStrings == null) {
				_vulcanCRUDItemDelegates.put(
					entityClassName, vulcanCRUDItemDelegate);
			}
			else {
				for (String companyIdString : companyIdStrings) {
					long companyId = GetterUtil.getLong(companyIdString);

					_companyScopedCRUDItemDelegatesMap.compute(
						companyId,
						(key, VulcanCRUDItemDelegateMap) -> {
							if (VulcanCRUDItemDelegateMap == null) {
								VulcanCRUDItemDelegateMap = new HashMap<>();
							}

							VulcanCRUDItemDelegateMap.put(
								entityClassName, vulcanCRUDItemDelegate);

							return VulcanCRUDItemDelegateMap;
						});
				}
			}

			return vulcanCRUDItemDelegate;
		}

		@Override
		public void modifiedService(
			ServiceReference<VulcanCRUDItemDelegate<?>> serviceReference,
			VulcanCRUDItemDelegate<?> vulcanCRUDItemDelegate) {

			List<String> companyIdStrings = _getCompanyIdStrings(
				serviceReference);

			if (companyIdStrings == null) {
				return;
			}

			String entityClassName = (String)serviceReference.getProperty(
				"entity.class.name");

			for (Map.Entry<Long, Map<String, VulcanCRUDItemDelegate<?>>> entry :
					_companyScopedCRUDItemDelegatesMap.entrySet()) {

				if (companyIdStrings.contains(String.valueOf(entry.getKey()))) {
					continue;
				}

				Map<String, VulcanCRUDItemDelegate<?>>
					companyVulcanCRUDItemDelegates = entry.getValue();

				companyVulcanCRUDItemDelegates.remove(entityClassName);
			}

			addingService(serviceReference);
		}

		@Override
		public void removedService(
			ServiceReference<VulcanCRUDItemDelegate<?>> serviceReference,
			VulcanCRUDItemDelegate<?> vulcanCRUDItemDelegate) {

			List<String> companyIdStrings = _getCompanyIdStrings(
				serviceReference);

			String entityClassName = (String)serviceReference.getProperty(
				"entity.class.name");

			if (companyIdStrings == null) {
				_vulcanCRUDItemDelegates.remove(entityClassName);
			}
			else {
				for (String companyIdString : companyIdStrings) {
					long companyId = GetterUtil.getLong(companyIdString);

					Map<String, VulcanCRUDItemDelegate<?>>
						companyVulcanCRUDItemDelegates =
							_companyScopedCRUDItemDelegatesMap.get(companyId);

					companyVulcanCRUDItemDelegates.remove(entityClassName);
				}
			}
		}

		private VulcanCRUDItemDelegateServiceTrackerCustomizer(
			BundleContext bundleContext) {

			_bundleContext = bundleContext;
		}

		private List<String> _getCompanyIdStrings(
			ServiceReference<?> serviceReference) {

			Object companyIdObject = serviceReference.getProperty("companyId");

			if (companyIdObject == null) {
				return null;
			}
			else if (companyIdObject instanceof List) {
				return (List<String>)companyIdObject;
			}

			return Collections.singletonList(String.valueOf(companyIdObject));
		}

		private final BundleContext _bundleContext;

	}

}