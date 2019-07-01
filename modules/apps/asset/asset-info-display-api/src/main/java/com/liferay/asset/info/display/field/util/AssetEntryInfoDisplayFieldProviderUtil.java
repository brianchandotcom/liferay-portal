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

package com.liferay.asset.info.display.field.util;

import com.liferay.asset.info.display.contributor.util.AssetInfoDisplayContributorFieldUtil;
import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.field.InfoDisplayFieldProvider;
import com.liferay.portal.kernel.exception.PortalException;

import java.util.Locale;
import java.util.Map;
import java.util.Set;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Jürgen Kappler
 */
public class AssetEntryInfoDisplayFieldProviderUtil {

	public static Set<InfoDisplayField> getInfoDisplayFields(
		Locale locale, String... className) {

		return getService().getContributorInfoDisplayFields(locale, className);
	}

	public static Map<String, Object> getInfoDisplayFields(
			String className, Object displayObject, Locale locale)
		throws PortalException {

		return getService().getContributorInfoDisplayFieldsValues(
			className, displayObject, locale);
	}

	public static InfoDisplayFieldProvider getService() {
		return _serviceTracker.getService();
	}

	private static final ServiceTracker
		<InfoDisplayFieldProvider, InfoDisplayFieldProvider> _serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			AssetInfoDisplayContributorFieldUtil.class);

		ServiceTracker<InfoDisplayFieldProvider, InfoDisplayFieldProvider>
			serviceTracker = new ServiceTracker<>(
				bundle.getBundleContext(), InfoDisplayFieldProvider.class,
				null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}