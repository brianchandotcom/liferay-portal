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

import com.liferay.osgi.util.bundle.BundleStartLevelUtil;
import com.liferay.petra.reflect.ReflectionUtil;
import com.liferay.portal.osgi.web.wab.generator.WabGenerator;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.InvalidSyntaxException;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Shuyang Zhou
 */
public class WebBundleInstaller extends ServiceTracker<WabGenerator, Void> {

	public WebBundleInstaller(
			BundleContext bundleContext, String location, int startLevel)
		throws InvalidSyntaxException {

		super(bundleContext, WabGenerator.class, null);

		_location = location;
		_startLevel = startLevel;
	}

	@Override
	public Void addingService(ServiceReference<WabGenerator> serviceReference) {

		// Service must be explicitly gotten from the bundle context to ensure
		// DS component's lazy activation is completed

		WabGenerator wabGenerator = context.getService(serviceReference);

		if (wabGenerator == null) {
			throw new IllegalStateException("Missing WAB generator");
		}

		try {
			Bundle bundle = context.installBundle(_location);

			BundleStartLevelUtil.setStartLevelAndStart(
				bundle, _startLevel, context);
		}
		catch (Exception e) {
			ReflectionUtil.throwException(e);
		}
		finally {
			context.ungetService(serviceReference);
		}

		close();

		return null;
	}

	private final String _location;
	private final int _startLevel;

}