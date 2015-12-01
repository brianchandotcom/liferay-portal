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

package com.liferay.dynamic.data.lists.exporter;

import com.liferay.osgi.util.ServiceTrackerFactory;

import java.util.Set;

import org.osgi.framework.FrameworkUtil;
import org.osgi.util.tracker.ServiceTracker;

/**
 * @author Marcellus Tavares
 */
public class DDLExporterFactoryUtil {

	public static DDLExporter getDDLExporter(String format) {
		return getDDLExporterFactory().getDDLExporter(format);
	}

	public static DDLExporterFactory getDDLExporterFactory() {
		return _serviceTracker.getService();
	}

	public Set<String> getAvailableFormats() {
		return getDDLExporterFactory().getAvailableFormats();
	}

	private static final ServiceTracker<DDLExporterFactory, DDLExporterFactory>
		_serviceTracker = ServiceTrackerFactory.open(
			FrameworkUtil.getBundle(DDLExporterFactoryUtil.class),
			DDLExporterFactory.class);

}