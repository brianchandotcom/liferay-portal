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

package com.liferay.portal.osgi.web.wab.extender.internal.event;

import com.liferay.osgi.util.ServiceTrackerFactory;
import com.liferay.petra.string.CharPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.osgi.web.wab.extender.internal.WabUtil;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.List;

import org.apache.felix.utils.log.Logger;

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

		// See LPS-82529 for more information on the property
		// "wab.event.enabled"

		_enabled = GetterUtil.getBoolean(
			bundleContext.getProperty("wab.event.enabled"));

		if (_enabled) {
			_bundleContext = bundleContext;

			_logger = new Logger(bundleContext);

			_webExtenderBundle = _bundleContext.getBundle();

			_eventAdminServiceTracker = ServiceTrackerFactory.open(
				_bundleContext, EventAdmin.class, this);
		}
		else {
			_bundleContext = null;
			_eventAdminServiceTracker = null;
			_logger = null;
			_webExtenderBundle = null;
		}
	}

	@Override
	public EventAdmin addingService(
		ServiceReference<EventAdmin> serviceReference) {

		_eventAdmin = _bundleContext.getService(serviceReference);

		return _eventAdmin;
	}

	public void close() {
		if (!_enabled) {
			return;
		}

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

		if (!_enabled) {
			return;
		}

		Dictionary<String, Object> properties = new HashMapDictionary<>();

		properties.put("bundle", bundle);
		properties.put("bundle.id", bundle.getBundleId());
		properties.put("bundle.symbolicName", bundle.getSymbolicName());
		properties.put("bundle.version", bundle.getVersion());

		String contextPath = GetterUtil.getString(
			WabUtil.getWebContextPath(bundle));

		if (collision) {
			List<Long> collidedBundleIds = new ArrayList<>();
			List<String> collidedBundleNames = new ArrayList<>();

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

					collidedBundleNames.add(curBundle.getSymbolicName());
				}
			}

			if (!collidedBundleIds.isEmpty()) {
				properties.put("collision", contextPath);

				properties.put("collision.bundles", collidedBundleIds);

				StringBuilder sb = new StringBuilder(7);

				sb.append("Newly added bundle: \"");
				sb.append(bundle.getSymbolicName());
				sb.append("\" has the same Web-ContextPath as the following ");
				sb.append("bundles: ");
				sb.append(collidedBundleNames);
				sb.append(
					". This can lead to unexpected behavior when multiple ");
				sb.append("bundles provide the same context path. See ");
				sb.append("https://osgi.org/specification/osgi.cmpn");
				sb.append("/7.0.0/service.http.whiteboard.html");
				sb.append("#service.http.whiteboard.servletcontext");

				_logger.log(Logger.LOG_WARNING, sb.toString());
			}
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
			"servlet.context.name", _sanitizeSymbolicName(symbolicName));

		properties.put("timestamp", System.currentTimeMillis());

		if (_eventAdmin == null) {
			return;
		}

		Event event = new Event(eventTopic, properties);

		_eventAdmin.sendEvent(event);
	}

	private static String _sanitizeSymbolicName(String symbolicName) {
		StringBuilder sb = new StringBuilder(symbolicName.length());

		for (int i = 0; i < symbolicName.length(); i++) {
			char c = symbolicName.charAt(i);

			if ((c < 128) && _VALID_CHARS[c]) {
				sb.append(c);
			}
		}

		if (sb.length() == symbolicName.length()) {
			return symbolicName;
		}

		return sb.toString();
	}

	private static final boolean[] _VALID_CHARS = new boolean[128];

	static {
		for (int i = CharPool.NUMBER_0; i <= CharPool.NUMBER_9; i++) {
			_VALID_CHARS[i] = true;
		}

		for (int i = CharPool.UPPER_CASE_A; i <= CharPool.UPPER_CASE_Z; i++) {
			_VALID_CHARS[i] = true;
		}

		for (int i = CharPool.LOWER_CASE_A; i <= CharPool.LOWER_CASE_Z; i++) {
			_VALID_CHARS[i] = true;
		}
	}

	private final BundleContext _bundleContext;
	private final boolean _enabled;
	private EventAdmin _eventAdmin;
	private final ServiceTracker<EventAdmin, EventAdmin>
		_eventAdminServiceTracker;
	private final Logger _logger;
	private final Bundle _webExtenderBundle;

}