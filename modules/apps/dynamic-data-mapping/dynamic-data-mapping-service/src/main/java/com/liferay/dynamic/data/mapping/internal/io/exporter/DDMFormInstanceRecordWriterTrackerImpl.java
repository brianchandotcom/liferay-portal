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

package com.liferay.dynamic.data.mapping.internal.io.exporter;

import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriter;
import com.liferay.dynamic.data.mapping.io.exporter.DDMFormInstanceRecordWriterTracker;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;

import java.util.Collections;
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
@Component(immediate = true, service = DDMFormInstanceRecordWriterTracker.class)
public class DDMFormInstanceRecordWriterTrackerImpl
	implements DDMFormInstanceRecordWriterTracker {

	@Override
	public DDMFormInstanceRecordWriter getDDMFormInstanceRecordWriter(
		String type) {

		return _ddmFormInstanceRecordWriters.get(type);
	}

	@Override
	public Map<String, String> getDDMFormInstanceRecordWriterExtensions() {
		return Collections.unmodifiableMap(
			_ddmFormInstanceRecordWriterExtensions);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC,
		policyOption = ReferencePolicyOption.GREEDY
	)
	protected void addDDMFormInstanceRecordWriter(
		DDMFormInstanceRecordWriter ddmFormInstanceRecordWriter,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "ddm.form.instance.record.writer.type");

		String extension = MapUtil.getString(
			properties, "ddm.form.instance.record.writer.extension");

		if (Validator.isNull(extension)) {
			extension = StringUtil.toUpperCase(type);
		}

		_ddmFormInstanceRecordWriterExtensions.put(type, extension);

		_ddmFormInstanceRecordWriters.put(type, ddmFormInstanceRecordWriter);
	}

	@Deactivate
	protected void deactivate() {
		_ddmFormInstanceRecordWriterExtensions.clear();
		_ddmFormInstanceRecordWriters.clear();
	}

	protected void removeDDMFormInstanceRecordWriter(
		DDMFormInstanceRecordWriter ddmFormInstanceRecordWriter,
		Map<String, Object> properties) {

		String type = MapUtil.getString(
			properties, "ddm.form.instance.record.writer.type");

		_ddmFormInstanceRecordWriterExtensions.remove(type);
		_ddmFormInstanceRecordWriters.remove(type);
	}

	private final Map<String, String> _ddmFormInstanceRecordWriterExtensions =
		new TreeMap<>();
	private final Map<String, DDMFormInstanceRecordWriter>
		_ddmFormInstanceRecordWriters = new TreeMap<>();

}