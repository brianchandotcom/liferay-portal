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
import com.liferay.data.engine.service.DEDataDefinitionCountRequest;
import com.liferay.data.engine.service.DEDataDefinitionCountResponse;
import com.liferay.dynamic.data.mapping.service.DDMStructureService;
import com.liferay.portal.kernel.util.Portal;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataDefinitionCountRequestExecutor {

	public DEDataDefinitionCountRequestExecutor(
		DDMStructureService ddmStructureService, Portal portal) {

		this.ddmStructureService = ddmStructureService;
		this.portal = portal;
	}

	public DEDataDefinitionCountResponse execute(
		DEDataDefinitionCountRequest deDataDefinitionCountRequest) {

		int structuresCount = ddmStructureService.getStructuresCount(
			deDataDefinitionCountRequest.getCompanyId(),
			new long[] {deDataDefinitionCountRequest.getGroupId()},
			portal.getClassNameId(DEDataDefinition.class));

		return DEDataDefinitionCountResponse.Builder.of(structuresCount);
	}

	protected DDMStructureService ddmStructureService;
	protected Portal portal;

}