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

import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 *  Provides a factory to fetch implementations of DDL Exporter service. By default
 *  two implemententations are provided and available as XML or CSV formats
 *  but other could be added as OSGi modules.
 *
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDLExporterFactory.class)
public class DDLExporterFactory {

	/**
	 * Returns the available formats that could be used to export record set records.
	 *
	 * @return the available formats registered in the system
	 */
	public Set<String> getAvailableFormats() {
		return Collections.unmodifiableSet(_ddlExporters.keySet());
	}

	/**
	 * Returns the DDL Export service instace for the formats specified.
	 * @param  format the format that will be used to export
	 * @return the DDLExporter instance
	 */
	public DDLExporter getDDLExporter(String format) {
		DDLExporter ddlExporter = _ddlExporters.get(format);

		if (ddlExporter == null) {
			throw new IllegalArgumentException(
				"No DDL exporter exists for the format " + format);
		}

		return ddlExporter;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeDDLExporter"
	)
	protected void addDDLExporter(DDLExporter ddlExporter) {
		_ddlExporters.put(ddlExporter.getFormat(), ddlExporter);
	}

	protected void removeDDLExporter(DDLExporter ddlExporter) {
		_ddlExporters.remove(ddlExporter.getFormat());
	}

	private final Map<String, DDLExporter> _ddlExporters =
		new ConcurrentHashMap<>();

}