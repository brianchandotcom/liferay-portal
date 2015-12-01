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

package com.liferay.portal.wab.extender.internal.event;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.wab.extender.internal.WabUtil;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.util.tracker.ServiceTracker;
import org.osgi.util.tracker.ServiceTrackerCustomizer;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class EventUtil
	implements ServiceTrackerCustomizer<EventAdmin, EventAdmin> {

	public static final String DEPLOYED = "org/osgi/service/web/DEPLOYED";

	public static final String DEPLOYING = "org/osgi/service/web/DEPLOYING";

	public static final String FAILED = "org/osgi/service/web/FAILED";

	public static final String UNDEPLOYED = "org/osgi/service/web/UNDEPLOYED";

	public static final String UNDEPLOYING = "org/osgi/service/web/UNDEPLOYING";

	public EventUtil(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_webExtenderBundle = _bundleContext.getBundle();

		_eventAdminServiceTracker = ServiceTrackerFactory.open(
			_bundleContext, EventAdmin.class, this);
	}

	@Override
	public EventAdmin addingService(
		ServiceReference<EventAdmin> serviceReference) {

		_eventAdmin = _bundleContext.getService(serviceReference);

		return _eventAdmin;
	}

	public void close() {
		_eventAdminServiceTracker.close();
	}

	@Override
	public void modifiedService(
		ServiceReference<EventAdmin> serviceReference, EventAdmin eventAdmin) {
	}

	@Override
	public void removedService(
		ServiceReference<EventAdmin> serviceReference, EventAdmin eventAdmin) {

		_bundleContext.ungetService(serviceReference);

		_eventAdmin = null;
	}

	public void sendEvent(
		Bundle bundle, String eventTopic, Exception exception,
		boolean collision) {

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("bundle", bundle);
		properties.put("bundle.id", bundle.getBundleId());
		properties.put("bundle.symbolicName", bundle.getSymbolicName());
		properties.put("bundle.version", bundle.getVersion());

		String contextPath = GetterUtil.getString(
			WabUtil.getWebContextPath(bundle));

		if (collision) {
			properties.put("collision", contextPath);

			List<Long> collidedBundleIds = new ArrayList<>();

			BundleContext bundleContext = bundle.getBundleContext();

			for (Bundle curBundle : bundleContext.getBundles()) {
				if (curBundle.equals(bundle) ||
					(curBundle.getState() != Bundle.ACTIVE)) {

					continue;
				}

				String curContextPath = WabUtil.getWebContextPath(curBundle);

				if ((curContextPath != null) &&
					curContextPath.equals(contextPath)) {

					collidedBundleIds.add(curBundle.getBundleId());
				}
			}

			properties.put("collision.bundles", collidedBundleIds);
		}

		properties.put("context.path", contextPath);

		if (exception != null) {
			properties.put("exception", exception);
		}

		properties.put("extender.bundle", _webExtenderBundle);
		properties.put("extender.bundle.id", _webExtenderBundle.getBundleId());
		properties.put(
			"extender.bundle.symbolicName",
			_webExtenderBundle.getSymbolicName());
		properties.put(
			"extender.bundle.version", _webExtenderBundle.getVersion());

		String symbolicName = bundle.getSymbolicName();

		properties.put(
			"servlet.context.name",
			symbolicName.replaceAll("[^a-zA-Z0-9]", ""));

		properties.put("timestamp", System.currentTimeMillis());

		Event event = new Event(eventTopic, properties);

		if (_eventAdmin == null) {
			return;
		}

		_eventAdmin.sendEvent(event);
	}

	private final BundleContext _bundleContext;
	private EventAdmin _eventAdmin;
	private final ServiceTracker<EventAdmin, EventAdmin>
		_eventAdminServiceTracker;
	private final Bundle _webExtenderBundle;

}