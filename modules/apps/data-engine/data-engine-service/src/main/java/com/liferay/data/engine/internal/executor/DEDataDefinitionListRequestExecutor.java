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

import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.service.DEDataDefinitionListRequest;
import com.liferay.data.engine.service.DEDataDefinitionListResponse;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.portal.kernel.util.Portal;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataDefinitionListRequestExecutor {

	public DEDataDefinitionListRequestExecutor(
		DEDataEngineRequestExecutor deDataEngineRequestExecutor,
		DDMStructureService ddmStructureService, Portal portal) {

		_deDataEngineRequestExecutor = deDataEngineRequestExecutor;
		_ddmStructureService = ddmStructureService;
		_portal = portal;
	}

	public DEDataDefinitionListResponse execute(
			DEDataDefinitionListRequest deDataDefinitionListRequest)
		throws Exception {

		List<DEDataDefinition> deDataDefinitions = new ArrayList<>();

		List<DDMStructure> ddmStructures = _ddmStructureService.getStructures(
			deDataDefinitionListRequest.getCompanyId(),
			new long[] {deDataDefinitionListRequest.getGroupId()},
			_portal.getClassNameId(DEDataDefinition.class),
			deDataDefinitionListRequest.getStart(),
			deDataDefinitionListRequest.getEnd(), null);

		for (DDMStructure ddmStructure : ddmStructures) {
			deDataDefinitions.add(
				_deDataEngineRequestExecutor.map(ddmStructure));
		}

		return DEDataDefinitionListResponse.Builder.of(deDataDefinitions);
	}

	private final DDMStructureService _ddmStructureService;
	private final DEDataEngineRequestExecutor _deDataEngineRequestExecutor;
	private final Portal _portal;

}