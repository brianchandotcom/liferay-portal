/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portlet.internal;

import com.liferay.petra.concurrent.DCLSingleton;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.model.Portlet;
import com.liferay.portal.kernel.module.util.SystemBundleUtil;
import com.liferay.portal.kernel.portlet.FriendlyURLMapper;
import com.liferay.portal.kernel.portlet.FriendlyURLMapperTracker;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 */
public class FriendlyURLMapperTrackerImpl implements FriendlyURLMapperTracker {

	public FriendlyURLMapperTrackerImpl(Portlet portlet) throws Exception {
		_portlet = portlet;
	}

	@Override
	public void close() {
		for (Map.Entry<FriendlyURLMapper, ServiceRegistration<?>> entry :
				_serviceRegistrations.entrySet()) {

			ServiceRegistration<?> serviceRegistration = entry.getValue();

			serviceRegistration.unregister();
		}

		_serviceTrackerDCLSingleton.destroy(ServiceTracker::close);
	}

	@Override
	public FriendlyURLMapper getFriendlyURLMapper() {
		ServiceTracker<FriendlyURLMapper, FriendlyURLMapper> serviceTracker =
			_serviceTrackerDCLSingleton.getSingleton(this::_openServiceTracker);

		return serviceTracker.getService();
	}

	@Override
	public void register(FriendlyURLMapper friendlyURLMapper) {
		ServiceRegistration<?> serviceRegistration =
			_bundleContext.registerService(
				FriendlyURLMapper.class, friendlyURLMapper,
				MapUtil.singletonDictionary(
					"jakarta.portlet.name", _portlet.getPortletId()));

		_serviceRegistrations.put(friendlyURLMapper, serviceRegistration);
	}

	@Override
	public void unregister(FriendlyURLMapper friendlyURLMapper) {
		ServiceRegistration<?> serviceRegistration =
			_serviceRegistrations.remove(friendlyURLMapper);

		if (serviceRegistration != null) {
			serviceRegistration.unregister();
		}
	}

	private ServiceTracker<FriendlyURLMapper, FriendlyURLMapper>
		_openServiceTracker() {

		String filterString = null;

		String portletId = _portlet.getPortletId();

		if (portletId.equals(_portlet.getPortletName())) {
			filterString = StringBundler.concat(
				"(&(jakarta.portlet.name=", portletId, ")(objectClass=",
				FriendlyURLMapper.class.getName(), "))");
		}
		else {
			filterString = StringBundler.concat(
				"(&(|(jakarta.portlet.name=", portletId,
				")(jakarta.portlet.name=", _portlet.getPortletName(),
				"))(objectClass=", FriendlyURLMapper.class.getName(), "))");
		}

		ServiceTracker<FriendlyURLMapper, FriendlyURLMapper> serviceTracker =
			new ServiceTracker<>(
				_bundleContext, SystemBundleUtil.createFilter(filterString),
				new FriendlyURLMapperServiceTrackerCustomizer());

		serviceTracker.open();

		return serviceTracker;
	}

	private final BundleContext _bundleContext =
		SystemBundleUtil.getBundleContext();
	private final Portlet _portlet;
	private final Map<FriendlyURLMapper, ServiceRegistration<?>>
		_serviceRegistrations = new ConcurrentHashMap<>();
	private final DCLSingleton
		<ServiceTracker<FriendlyURLMapper, FriendlyURLMapper>>
			_serviceTrackerDCLSingleton = new DCLSingleton<>();

	private class FriendlyURLMapperServiceTrackerCustomizer
		implements ServiceTrackerCustomizer
			<FriendlyURLMapper, FriendlyURLMapper> {

		@Override
		public FriendlyURLMapper addingService(
			ServiceReference<FriendlyURLMapper> serviceReference) {

			FriendlyURLMapper friendlyURLMapper = _bundleContext.getService(
				serviceReference);

			friendlyURLMapper.setFriendlyURLRoutes(
				(String)serviceReference.getProperty(
					"com.liferay.portlet.friendly-url-routes"));

			friendlyURLMapper.init(_portlet);

			return friendlyURLMapper;
		}

		@Override
		public void modifiedService(
			ServiceReference<FriendlyURLMapper> serviceReference,
			FriendlyURLMapper friendlyURLMapper) {
		}

		@Override
		public void removedService(
			ServiceReference<FriendlyURLMapper> serviceReference,
			FriendlyURLMapper friendlyURLMapper) {

			_bundleContext.ungetService(serviceReference);
		}

	}

}