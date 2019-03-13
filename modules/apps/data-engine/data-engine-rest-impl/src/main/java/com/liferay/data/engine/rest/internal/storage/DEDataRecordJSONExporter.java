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

import com.liferay.data.engine.io.DEDataRecordValuesRequestBuilder;
import com.liferay.data.engine.io.DEDataRecordValuesSerializer;
import com.liferay.data.engine.io.DEDataRecordValuesSerializerApplyRequest;
import com.liferay.data.engine.io.DEDataRecordValuesSerializerApplyResponse;
import com.liferay.data.engine.model.DEDataRecord;
import com.liferay.data.engine.storage.DEDataRecordExporter;
import com.liferay.data.engine.storage.DEDataRecordExporterApplyRequest;
import com.liferay.data.engine.storage.DEDataRecordExporterApplyResponse;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import java.util.List;
import java.util.stream.Stream;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "de.data.record.exporter.format=json",
	service = DEDataRecordExporter.class
)
public class DEDataRecordJSONExporter implements DEDataRecordExporter {

	@Override
	public DEDataRecordExporterApplyResponse apply(
		DEDataRecordExporterApplyRequest deDataRecordExporterApplyRequest) {

		List<DEDataRecord> deDataRecords =
			deDataRecordExporterApplyRequest.getDEDataRecords();

		Stream<DEDataRecord> stream = deDataRecords.parallelStream();

		JSONArray jsonArray = jsonFactory.createJSONArray();

		stream.map(
			this::map
		).forEach(
			jsonArray::put
		);

		return DEDataRecordExporterApplyResponse.Builder.of(
			jsonArray.toJSONString());
	}

	protected JSONObject map(DEDataRecord deDataRecord) {
		DEDataRecordValuesSerializerApplyRequest
			deDataRecordValuesSerializerApplyRequest =
				DEDataRecordValuesRequestBuilder.serializeBuilder(
					deDataRecord.getValues(), deDataRecord.getDEDataDefinition()
				).build();

		DEDataRecordValuesSerializerApplyResponse
			deDataRecordValuesSerializerApplyResponse =
				deDataRecordValuesSerializer.apply(
					deDataRecordValuesSerializerApplyRequest);

		try {
			return jsonFactory.createJSONObject(
				deDataRecordValuesSerializerApplyResponse.getContent());
		}
		catch (JSONException jsone) {
			return jsonFactory.createJSONObject();
		}
	}

	@Reference(target = "(de.data.record.values.serializer.type=json)")
	protected DEDataRecordValuesSerializer deDataRecordValuesSerializer;

	@Reference
	protected JSONFactory jsonFactory;

}