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

package com.liferay.portal.osgi.web.wab.extender.internal;

import com.liferay.portal.osgi.web.wab.extender.internal.event.EventUtil;

import java.util.Dictionary;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.felix.utils.log.Logger;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.wiring.BundleRevision;

/**
 * @author Raymond Augé
 * @author Miguel Pastor
 */
public class WebBundleDeployer {

	public WebBundleDeployer(
			BundleContext bundleContext, Dictionary<String, Object> properties,
			EventUtil eventUtil, Logger logger)
		throws Exception {

		_bundleContext = bundleContext;
		_properties = properties;
		_eventUtil = eventUtil;
		_logger = logger;
	}

	public void close() {
		for (Bundle bundle : _wabBundleProcessors.keySet()) {
			doStop(bundle);
		}
	}

	public void doStart(Bundle bundle) {
		_eventUtil.sendEvent(bundle, EventUtil.DEPLOYING, null, false);

		String contextPath = WabUtil.getWebContextPath(bundle);

		if (contextPath == null) {
			return;
		}

		BundleContext bundleContext = bundle.getBundleContext();

		if (bundleContext == null) {
			_eventUtil.sendEvent(bundle, EventUtil.FAILED, null, false);

			return;
		}

		try {
			WabBundleProcessor newWabBundleProcessor = new WabBundleProcessor(
				bundle, _logger);

			WabBundleProcessor oldWabBundleProcessor =
				_wabBundleProcessors.putIfAbsent(bundle, newWabBundleProcessor);

			if (oldWabBundleProcessor != null) {
				_eventUtil.sendEvent(bundle, EventUtil.FAILED, null, false);

				return;
			}

			newWabBundleProcessor.init(_properties);
		}
		catch (Exception e) {
			_eventUtil.sendEvent(bundle, EventUtil.FAILED, e, false);
		}
	}

	public void doStop(Bundle bundle) {
		WabBundleProcessor wabBundleProcessor = _wabBundleProcessors.remove(
			bundle);

		if (wabBundleProcessor == null) {
			return;
		}

		_eventUtil.sendEvent(bundle, EventUtil.UNDEPLOYING, null, false);

		try {
			wabBundleProcessor.destroy();

			_eventUtil.sendEvent(bundle, EventUtil.UNDEPLOYED, null, false);

			handleCollidedWABs(bundle);
		}
		catch (Exception e) {
			_eventUtil.sendEvent(bundle, EventUtil.FAILED, e, false);
		}
	}

	public boolean isFragmentBundle(Bundle bundle) {
		BundleRevision bundleRevision = bundle.adapt(BundleRevision.class);

		if ((bundleRevision.getTypes() & BundleRevision.TYPE_FRAGMENT) ==
				BundleRevision.TYPE_FRAGMENT) {

			return false;
		}

		return true;
	}

	protected void handleCollidedWABs(Bundle bundle) {
		String contextPath = WabUtil.getWebContextPath(bundle);

		for (Bundle curBundle : _bundleContext.getBundles()) {
			if (bundle.equals(curBundle) || isFragmentBundle(curBundle) ||
				_wabBundleProcessors.containsKey(curBundle)) {

				continue;
			}

			String curContextPath = WabUtil.getWebContextPath(curBundle);

			if (contextPath.equals(curContextPath)) {
				doStart(curBundle);

				break;
			}
		}
	}

	private final BundleContext _bundleContext;
	private final EventUtil _eventUtil;
	private final Logger _logger;
	private final Dictionary<String, Object> _properties;
	private final ConcurrentMap<Bundle, WabBundleProcessor>
		_wabBundleProcessors = new ConcurrentHashMap<>();

}