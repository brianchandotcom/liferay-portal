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

package com.liferay.data.engine.internal.executor;

import com.liferay.data.engine.exception.DEDataDefinitionFieldsDeserializerException;
import com.liferay.data.engine.internal.io.DEDataDefinitionFieldsDeserializerTracker;
import com.liferay.data.engine.io.DEDataDefinitionFieldsDeserializer;
import com.liferay.data.engine.io.DEDataDefinitionFieldsDeserializerApplyRequest;
import com.liferay.data.engine.io.DEDataDefinitionFieldsDeserializerApplyResponse;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataDefinitionField;
import com.liferay.data.engine.service.DEDataDefinitionGetRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = DEDataDefinitionGetRequestExecutor.class)
public class DEDataDefinitionGetRequestExecutor {

	public DEDataDefinitionGetResponse execute(
			DEDataDefinitionGetRequest deDataDefinitionGetRequest)
		throws Exception {

		long deDataDefinitionId =
			deDataDefinitionGetRequest.getDEDataDefinitionId();

		DDMStructure ddmStructure = ddmStructureLocalService.getStructure(
			deDataDefinitionId);

		return DEDataDefinitionGetResponse.Builder.of(map(ddmStructure));
	}

	protected List<DEDataDefinitionField> deserialize(String content)
		throws DEDataDefinitionFieldsDeserializerException {

		DEDataDefinitionFieldsDeserializer deDataDefinitionFieldsDeserializer =
			deDataDefinitionFieldsDeserializerTracker.
				getDEDataDefinitionFieldsDeserializer("json");

		DEDataDefinitionFieldsDeserializerApplyRequest
			deDataDefinitionFieldsDeserializerApplyRequest =
				DEDataDefinitionFieldsDeserializerApplyRequest.Builder.
					newBuilder(
						content
					).build();

		DEDataDefinitionFieldsDeserializerApplyResponse
			deDataDefinitionFieldsDeserializerApplyResponse =
				deDataDefinitionFieldsDeserializer.apply(
					deDataDefinitionFieldsDeserializerApplyRequest);

		return deDataDefinitionFieldsDeserializerApplyResponse.
			getDeDataDefinitionFields();
	}

	protected DEDataDefinition map(DDMStructure ddmStructure)
		throws DEDataDefinitionFieldsDeserializerException {

		List<DEDataDefinitionField> deDataDefinitionFields = deserialize(
			ddmStructure.getDefinition());

		DEDataDefinition deDataDefinition = new DEDataDefinition(
			deDataDefinitionFields);

		deDataDefinition.setDEDataDefinitionId(ddmStructure.getStructureId());
		deDataDefinition.addDescriptions(ddmStructure.getDescriptionMap());
		deDataDefinition.addNames(ddmStructure.getNameMap());
		deDataDefinition.setCreateDate(ddmStructure.getCreateDate());
		deDataDefinition.setModifiedDate(ddmStructure.getModifiedDate());
		deDataDefinition.setStorageType(ddmStructure.getStorageType());
		deDataDefinition.setUserId(ddmStructure.getUserId());

		return deDataDefinition;
	}

	@Reference
	protected DDMStructureLocalService ddmStructureLocalService;

	@Reference
	protected DEDataDefinitionFieldsDeserializerTracker
		deDataDefinitionFieldsDeserializerTracker;

}