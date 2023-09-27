/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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