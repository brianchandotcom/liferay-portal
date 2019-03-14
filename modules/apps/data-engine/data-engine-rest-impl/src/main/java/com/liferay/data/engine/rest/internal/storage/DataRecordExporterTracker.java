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

package com.liferay.data.engine.rest.internal.storage;

import com.liferay.data.engine.storage.DataRecordExporter;
import com.liferay.portal.kernel.util.MapUtil;

import java.util.Map;
import java.util.TreeMap;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;
import org.osgi.service.component.annotations.ReferencePolicyOption;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DataRecordExporterTracker.class)
public class DataRecordExporterTracker {

	public DataRecordExporter getDataRecordExporter(String format) {
		return _dataRecordExporters.get(format);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDataRecordExporter(
		DataRecordExporter dataRecordExporter, Map<String, Object> properties) {

		String format = MapUtil.getString(
			properties, "data.record.exporter.format");

		_dataRecordExporters.put(format, dataRecordExporter);
	}

	@Deactivate
	protected void deactivate() {
		_dataRecordExporters.clear();
	}

	protected void removeDataRecordExporter(
		DataRecordExporter dataRecordExporter, Map<String, Object> properties) {

		String format = MapUtil.getString(
			properties, "data.record.exporter.format");

		_dataRecordExporters.remove(format);
	}

	private final Map<String, DataRecordExporter> _dataRecordExporters =
		new TreeMap<>();

}