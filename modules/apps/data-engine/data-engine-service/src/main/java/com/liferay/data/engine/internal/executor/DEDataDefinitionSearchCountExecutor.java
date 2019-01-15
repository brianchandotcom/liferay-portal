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
import com.liferay.data.engine.service.DEDataDefinitionSearchCountRequest;
import com.liferay.data.engine.service.DEDataDefinitionSearchCountResponse;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.portal.kernel.util.Portal;
import com.liferay.portal.kernel.workflow.WorkflowConstants;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataDefinitionSearchCountExecutor {

	public DEDataDefinitionSearchCountExecutor(
		DDMStructureService ddmStructureService,
		DEDataEngineRequestExecutor deDataEngineRequestExecutor,
		Portal portal) {

		this.ddmStructureService = ddmStructureService;
		this.deDataEngineRequestExecutor = deDataEngineRequestExecutor;
		this.portal = portal;
	}

	public DEDataDefinitionSearchCountResponse execute(
		DEDataDefinitionSearchCountRequest deDataDefinitionSearchCountRequest) {

		int count = ddmStructureService.searchCount(
			deDataDefinitionSearchCountRequest.getCompanyId(),
			new long[] {deDataDefinitionSearchCountRequest.getGroupId()},
			portal.getClassNameId(DEDataDefinition.class),
			deDataDefinitionSearchCountRequest.getKeywords(),
			WorkflowConstants.STATUS_ANY);

		return DEDataDefinitionSearchCountResponse.Builder.of(count);
	}

	protected DDMStructureService ddmStructureService;
	protected DEDataEngineRequestExecutor deDataEngineRequestExecutor;
	protected Portal portal;

}