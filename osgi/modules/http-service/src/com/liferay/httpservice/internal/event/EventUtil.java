/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.httpservice.internal.event;

import com.liferay.httpservice.internal.servlet.BundleServletContext;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.StringPool;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.osgi.service.event.EventConstants;
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

	public static void close() {
		_instance._close();

		_instance = null;
	}

	public static void sendEvent(
		Bundle bundle, String eventTopic, Exception exception,
		boolean collision) {

		_instance._sendEvent(bundle, eventTopic, exception, collision);
	}

	public static void start(BundleContext bundleContext) {
		if (_instance != null) {
			return;
		}

		_instance = new EventUtil();

		_instance._start(bundleContext);
	}

	public void _close() {
		_eventAdminTracker.close();

		_bundleContext = null;
		_eventAdminTracker = null;
		_webExtenderBundle = null;
	}

	public EventAdmin addingService(ServiceReference<EventAdmin> reference) {
		_eventAdmin = _bundleContext.getService(reference);

		return _eventAdmin;
	}

	public void modifiedService(
		ServiceReference<EventAdmin> reference, EventAdmin service) {
	}

	public void removedService(
		ServiceReference<EventAdmin> reference, EventAdmin service) {

		_eventAdmin = null;
	}

	private EventUtil() {
	}

	private void _sendEvent(
		Bundle bundle, String eventTopic, Exception exception,
		boolean collision) {

		String servletContextName = BundleServletContext.getServletContextName(
			bundle, true);
		String servletContextPath = StringPool.SLASH.concat(servletContextName);

		Map<String, Object> properties = new Hashtable<String, Object>();

		properties.put("bundle", bundle);
		properties.put("bundle.id", bundle.getBundleId());
		properties.put("bundle.symbolicName", bundle.getSymbolicName());
		properties.put("bundle.version", bundle.getVersion());

		if (collision) {
			properties.put("collision", servletContextPath);

			List<String> collidedIds = new ArrayList<String>();

			for (Bundle curBundle : bundle.getBundleContext().getBundles()) {
				if (curBundle.equals(bundle) ||
					(curBundle.getState() != Bundle.ACTIVE)) {

					continue;
				}

				String curContextName =
					BundleServletContext.getServletContextName(curBundle);

				if ((curContextName != null) &&
					curContextName.equals(servletContextName)) {

					collidedIds.add(String.valueOf(curBundle.getBundleId()));
				}
			}

			properties.put("collision.bundles", collidedIds);
		}

		properties.put("context.path", servletContextPath);
		properties.put("extender.bundle", _webExtenderBundle);
		properties.put("extender.bundle.id", _webExtenderBundle.getBundleId());
		properties.put(
			"extender.bundle.symbolicName",
			_webExtenderBundle.getSymbolicName());
		properties.put(
			"extender.bundle.version", _webExtenderBundle.getVersion());

		if (exception != null) {
			properties.put("exception", exception);
		}

		properties.put("servlet.context.name", servletContextName);
		properties.put("timestamp", System.currentTimeMillis());

		Event event = new Event(eventTopic, properties);

		if (_log.isInfoEnabled()) {
			_log.info(event);
		}

		if (_eventAdmin == null) {
			return;
		}

		_eventAdmin.sendEvent(event);
	}

	private void _start(BundleContext bundleContext) {
		_bundleContext = bundleContext;

		_webExtenderBundle = _bundleContext.getBundle();

		Hashtable<String, Object> properties = new Hashtable<String, Object>();

		properties.put(EventConstants.EVENT_TOPIC, _EVENT_TOPICS);

		_eventAdminTracker = new ServiceTracker<EventAdmin, EventAdmin>(
			_bundleContext, EventAdmin.class.getName(), this);

		_eventAdminTracker.open();
	}

	private static final String[] _EVENT_TOPICS = new String[] {
		DEPLOYED, DEPLOYING, FAILED, UNDEPLOYED, UNDEPLOYING
	};

	private static Log _log = LogFactoryUtil.getLog(EventUtil.class);

	private static EventUtil _instance;

	private BundleContext _bundleContext;
	private EventAdmin _eventAdmin;
	private ServiceTracker<EventAdmin, EventAdmin> _eventAdminTracker;
	private Bundle _webExtenderBundle;

}