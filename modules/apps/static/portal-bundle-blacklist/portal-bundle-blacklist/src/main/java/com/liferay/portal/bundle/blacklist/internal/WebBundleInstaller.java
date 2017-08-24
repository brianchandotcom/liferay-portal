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

package com.liferay.portal.bundle.blacklist.internal;

import com.liferay.portal.kernel.util.ReflectionUtil;
import com.liferay.portal.lpkg.deployer.util.BundleStartLevelUtil;

import java.net.URL;
import java.net.URLConnection;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.service.url.URLConstants;
import org.osgi.service.url.URLStreamHandlerService;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Shuyang Zhou
 */
public class WebBundleInstaller
	extends ServiceTracker<URLStreamHandlerService, Void> {

	public WebBundleInstaller(
			BundleContext bundleContext, String location, int startLevel)
		throws InvalidSyntaxException {

		super(bundleContext, bundleContext.createFilter(_FILTER_STRING), null);

		_location = location;
		_startLevel = startLevel;
	}

	@Override
	public Void addingService(
		ServiceReference<URLStreamHandlerService> serviceReference) {

		URLStreamHandlerService urlStreamHandlerService = context.getService(
			serviceReference);

		try {
			URLConnection urlConnection =
				urlStreamHandlerService.openConnection(new URL(_location));

			Bundle bundle = context.installBundle(
				_location, urlConnection.getInputStream());

			BundleStartLevelUtil.setStartLevelAndStart(
				bundle, _startLevel, context);
		}
		catch (Exception be) {
			ReflectionUtil.throwException(be);
		}
		finally {
			context.ungetService(serviceReference);
		}

		close();

		return null;
	}

	private static final String _FILTER_STRING =
		"(&(" + URLConstants.URL_HANDLER_PROTOCOL + "=webbundle)(objectClass=" +
			URLStreamHandlerService.class.getName() + "))";

	private final String _location;
	private final int _startLevel;

}