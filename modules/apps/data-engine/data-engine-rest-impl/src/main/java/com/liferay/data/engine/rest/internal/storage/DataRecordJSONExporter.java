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

import com.liferay.data.engine.rest.dto.v1_0.DataDefinition;
import com.liferay.data.engine.rest.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.internal.dto.v1_0.util.DataRecordValueUtil;
import com.liferay.data.engine.rest.resource.v1_0.DataDefinitionResource;
import com.liferay.data.engine.rest.resource.v1_0.DataRecordCollectionResource;
import com.liferay.data.engine.storage.DataRecordExporter;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONException;
import com.liferay.portal.kernel.json.JSONFactory;
import com.liferay.portal.kernel.json.JSONObject;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true, property = "data.record.exporter.format=json",
	service = DataRecordExporter.class
)
public class DataRecordJSONExporter implements DataRecordExporter {

	@Override
	public String apply(DataRecord[] dataRecords) throws Exception {
		JSONArray jsonArray = jsonFactory.createJSONArray();

		if (dataRecords.length > 0) {
			for (DataRecord dataRecord : dataRecords) {
				jsonArray.put(map(dataRecord));
			}
		}

		return jsonArray.toJSONString();
	}

	protected JSONObject map(DataRecord dataRecord) throws Exception {
		DDLRecordSet ddlRecordSet = ddlRecordSetLocalService.getDDLRecordSet(
			dataRecord.getDataRecordCollectionId());

		DDMStructure ddmStructure = ddlRecordSet.getDDMStructure();

		DataDefinition dataDefinition =
			dataDefinitionResource.getDataDefinition(
				ddmStructure.getStructureId());

		try {
			return jsonFactory.createJSONObject(
				DataRecordValueUtil.toJSONString(
					dataDefinition, dataRecord.getDataRecordValues()));
		}
		catch (JSONException jsone) {
			return jsonFactory.createJSONObject();
		}
	}

	@Reference
	protected DataDefinitionResource dataDefinitionResource;

	@Reference
	protected DataRecordCollectionResource dataRecordCollectionResource;

	@Reference
	protected DDLRecordSetLocalService ddlRecordSetLocalService;

	@Reference
	protected JSONFactory jsonFactory;

}