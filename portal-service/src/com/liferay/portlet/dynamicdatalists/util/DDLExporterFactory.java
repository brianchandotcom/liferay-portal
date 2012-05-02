/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.dynamicdatalists.util;

import com.liferay.portal.kernel.exception.PortalException;
import com.liferay.portal.kernel.security.pacl.permission.PortalRuntimePermission;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Marcellus Tavares
 */
public class DDLExporterFactory {

	public static DDLExporter getDDLExporter(DDLExportFormat exportFormat)
		throws PortalException {

		DDLExporter exporter = _exporters.get(exportFormat);

		if (exporter == null) {
			throw new PortalException("Invalid format type " + exportFormat);
		}

		return exporter;
	}

	public void setDDLExporters(Map<String, DDLExporter> exporters) {
		PortalRuntimePermission.checkSetBeanProperty(getClass());

		_exporters = new HashMap<DDLExportFormat, DDLExporter>();

		for (Map.Entry<String, DDLExporter> entry : exporters.entrySet()) {
			DDLExportFormat exportFormat = DDLExportFormat.parse(
				entry.getKey());

			_exporters.put(exportFormat, entry.getValue());
		}
	}

	private static Map<DDLExportFormat, DDLExporter> _exporters;

}