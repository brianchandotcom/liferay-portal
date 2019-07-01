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

package com.liferay.asset.info.display.contributor.field.util;

import com.liferay.info.display.contributor.InfoDisplayField;
import com.liferay.info.display.field.ExpandoInfoDisplayFieldProvider;

import java.util.List;
import java.util.Locale;
import java.util.Map;

import org.osgi.framework.Bundle;
import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Jürgen Kappler
 */
public class ExpandoInfoDisplayFieldProviderUtil {

	public static List<InfoDisplayField> getExpandoInfoDisplayFields(
		String className, Locale locale) {

		return getService().getContributorExpandoInfoDisplayFields(
			className, locale);
	}

	public static Map<String, Object> getExpandoInfoDisplayFieldsValues(
		String className, Object displayObject, Locale locale) {

		return getService().getContributorExpandoInfoDisplayFieldsValues(
			className, displayObject, locale);
	}

	public static ExpandoInfoDisplayFieldProvider getService() {
		return _serviceTracker.getService();
	}

	private static final ServiceTracker
		<ExpandoInfoDisplayFieldProvider, ExpandoInfoDisplayFieldProvider>
			_serviceTracker;

	static {
		Bundle bundle = FrameworkUtil.getBundle(
			ExpandoInfoDisplayFieldProviderUtil.class);

		ServiceTracker
			<ExpandoInfoDisplayFieldProvider, ExpandoInfoDisplayFieldProvider>
				serviceTracker = new ServiceTracker<>(
					bundle.getBundleContext(),
					ExpandoInfoDisplayFieldProvider.class, null);

		serviceTracker.open();

		_serviceTracker = serviceTracker;
	}

}