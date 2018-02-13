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

package com.liferay.dynamic.data.mapping.io.internal.exporter;

import com.liferay.dynamic.data.mapping.io.exporter.DDMExporterFactory;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormExporter;

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
 * @author Marcellus Tavares
 */
@Component(immediate = true, service = DDMExporterFactory.class)
public class DDMExporterFactoryImpl implements DDMExporterFactory {

	public Set<String> getAvailableFormats() {
		return Collections.unmodifiableSet(_ddmFormExporters.keySet());
	}

	public DDMFormExporter getDDMFormExporter(String format) {
		DDMFormExporter ddmExporter = _ddmFormExporters.get(format);

		if (ddmExporter == null) {
			throw new IllegalArgumentException(
				"No DDM Form exporter exists for the format " + format);
		}

		return ddmExporter;
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY,
		unbind = "removeDDMFormExporter"
	)
	protected void addDDMFormExporter(DDMFormExporter ddmExporter) {
		_ddmFormExporters.put(ddmExporter.getFormat(), ddmExporter);
	}

	protected void removeDDMFormExporter(DDMFormExporter ddmExporter) {
		_ddmFormExporters.remove(ddmExporter.getFormat());
	}

	private final Map<String, DDMFormExporter> _ddmFormExporters =
		new ConcurrentHashMap<>();

}