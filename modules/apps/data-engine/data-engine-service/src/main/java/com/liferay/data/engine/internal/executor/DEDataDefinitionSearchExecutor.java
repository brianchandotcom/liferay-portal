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
import com.liferay.data.engine.service.DEDataDefinitionSearchRequest;
import com.liferay.data.engine.service.DEDataDefinitionSearchResponse;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataDefinitionSearchExecutor {

	public DEDataDefinitionSearchExecutor(
		DDMStructureService ddmStructureService,
		DEDataEngineRequestExecutor deDataEngineRequestExecutor,
		Portal portal) {

		this.ddmStructureService = ddmStructureService;
		this.deDataEngineRequestExecutor = deDataEngineRequestExecutor;
		this.portal = portal;
	}

	public DEDataDefinitionSearchResponse execute(
			DEDataDefinitionSearchRequest deDataDefinitionSearchRequest)
		throws Exception {

		List<DDMStructure> ddmStructures = ddmStructureService.search(
			deDataDefinitionSearchRequest.getCompanyId(),
			new long[] {deDataDefinitionSearchRequest.getGroupId()},
			portal.getClassNameId(DEDataDefinition.class),
			deDataDefinitionSearchRequest.getKeywords(),
			WorkflowConstants.STATUS_ANY,
			deDataDefinitionSearchRequest.getStart(),
			deDataDefinitionSearchRequest.getEnd(), null);

		List<DEDataDefinition> deDataDefinitions = new ArrayList<>();

		for (DDMStructure ddmStructure : ddmStructures) {
			deDataDefinitions.add(
				deDataEngineRequestExecutor.map(ddmStructure));
		}

		return DEDataDefinitionSearchResponse.Builder.of(deDataDefinitions);
	}

	protected DDMStructureService ddmStructureService;
	protected DEDataEngineRequestExecutor deDataEngineRequestExecutor;
	protected Portal portal;

}