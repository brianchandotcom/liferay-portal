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

package com.liferay.dynamic.data.lists.internal.exporter;

import com.liferay.dynamic.data.lists.exporter.DDLExporter;
import com.liferay.dynamic.data.lists.exporter.DDLExporterFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceReferenceMapperFactory;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMap;
import com.liferay.osgi.service.tracker.collections.map.ServiceTrackerMapFactory;

import org.osgi.framework.BundleContext;
import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;

/**
 * Provides a factory to fetch implementations of the DDL Exporter service. By
 * default, implementations for XML and CSV formats are available, but others
 * can be added as OSGi modules.
 *
 * @author Marcellus Tavares
 * @see    DDLExporter
 */
@Component(service = DDLExporterFactory.class)
public class DDLExporterFactoryImpl implements DDLExporterFactory {

	/**
	 * Returns the DDL Exporter service instance for the format.
	 *
	 * @param  format the format that will be used to export
	 * @return the DDL Exporter instance
	 */
	@Override
	public DDLExporter getDDLExporter(String format) {
		DDLExporter ddlExporter = _serviceTrackerMap.getService(format);

		if (ddlExporter == null) {
			throw new IllegalArgumentException(
				"No DDL exporter exists for the format " + format);
		}

		return ddlExporter;
	}

	@Activate
	protected void activate(BundleContext bundleContext) {
		_serviceTrackerMap = ServiceTrackerMapFactory.openSingleValueMap(
			bundleContext, DDLExporter.class, null,
			ServiceReferenceMapperFactory.create(
				bundleContext,
				(ddlExporter, emitter) -> emitter.emit(
					ddlExporter.getFormat())));
	}

	@Deactivate
	protected void deactivate() {
		_serviceTrackerMap.close();
	}

	private ServiceTrackerMap<String, DDLExporter> _serviceTrackerMap;

}