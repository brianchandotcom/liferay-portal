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

import com.liferay.data.engine.service.DEDataDefinitionGetRequest;
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.dynamic.data.mapping.model.DDMStructure;
import com.liferay.dynamic.data.mapping.service.DDMStructureLocalService;

/**
 * @author Jeyvison Nascimento
 */
public class DEDataDefinitionGetRequestExecutor {

	public DEDataDefinitionGetRequestExecutor(
		DEDataEngineRequestExecutor deDataEngineRequestExecutor,
		DDMStructureLocalService ddmStructureLocalService) {

		_deDataEngineRequestExecutor = deDataEngineRequestExecutor;
		_ddmStructureLocalService = ddmStructureLocalService;
	}

	public DEDataDefinitionGetResponse execute(
			DEDataDefinitionGetRequest deDataDefinitionGetRequest)
		throws Exception {

		long deDataDefinitionId =
			deDataDefinitionGetRequest.getDEDataDefinitionId();

		DDMStructure ddmStructure = _ddmStructureLocalService.getStructure(
			deDataDefinitionId);

		return DEDataDefinitionGetResponse.Builder.of(
			_deDataEngineRequestExecutor.map(ddmStructure));
	}

	private final DDMStructureLocalService _ddmStructureLocalService;
	private final DEDataEngineRequestExecutor _deDataEngineRequestExecutor;

}