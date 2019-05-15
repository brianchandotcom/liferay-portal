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

package com.liferay.portal.lpkg.deployer.internal.wrapper.bundle;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;

import java.net.URL;

import java.util.Dictionary;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Shuyang Zhou
 */
public class WARBundleWrapperBundleActivator implements BundleActivator {

	@Override
	public void start(final BundleContext bundleContext) throws Exception {
		Bundle bundle = bundleContext.getBundle();

		Dictionary<String, String> headers = bundle.getHeaders(
			StringPool.BLANK);

		String contextName = headers.get("Liferay-WAB-Context-Name");

		if (contextName == null) {
			throw new IllegalArgumentException(
				"The header \"Liferay-WAB-Context-Name\" is null");
		}

		String lpkgURLString = headers.get("Liferay-WAB-LPKG-URL");

		if (lpkgURLString == null) {
			throw new IllegalArgumentException(
				"The header \"Liferay-WAB-LPKG-URL\" is null");
		}

		String startLevelString = headers.get("Liferay-WAB-Start-Level");

		if (startLevelString == null) {
			throw new IllegalArgumentException(
				"The header \"Liferay-WAB-Start-Level\" is null");
		}

		int startLevel = GetterUtil.getInteger(startLevelString);

		// Defer WAR bundle installation until WAB protocol handler is ready

		_serviceTracker = new ServiceTracker<>(
			bundleContext,
			bundleContext.createFilter(
				StringBundler.concat(
					"(&(", URLConstants.URL_HANDLER_PROTOCOL,
					"=webbundle)(objectClass=",
					URLStreamHandlerService.class.getName(), "))")),
			new URLStreamHandlerServiceServiceTrackerCustomizer(
				bundleContext, contextName, new URL(lpkgURLString),
				startLevel));

		_serviceTracker.open();
	}

	@Override
	public void stop(BundleContext bundleContext) {
		_serviceTracker.close();
	}

	private ServiceTracker<URLStreamHandlerService, Bundle> _serviceTracker;

}