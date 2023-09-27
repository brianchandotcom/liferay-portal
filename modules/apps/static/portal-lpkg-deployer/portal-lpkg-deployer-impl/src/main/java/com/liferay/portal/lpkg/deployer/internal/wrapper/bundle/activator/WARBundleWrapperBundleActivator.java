/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.lpkg.deployer.internal.wrapper.bundle.activator;

import com.liferay.petra.string.StringBundler;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.lpkg.deployer.internal.wrapper.bundle.URLStreamHandlerServiceServiceTrackerCustomizer;

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
	public void start(BundleContext bundleContext) throws Exception {
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