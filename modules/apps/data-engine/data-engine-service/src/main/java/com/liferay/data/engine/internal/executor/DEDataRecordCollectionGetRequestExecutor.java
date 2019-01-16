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

import com.liferay.data.engine.service.DEDataRecordCollectionGetRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionGetResponse;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;

/**
 * @author Leonardo Barros
 */
public class DEDataRecordCollectionGetRequestExecutor {

	public DEDataRecordCollectionGetRequestExecutor(
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DEDataEngineRequestExecutor deDataEngineRequestExecutor) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_deDataEngineRequestExecutor = deDataEngineRequestExecutor;
	}

	public DEDataRecordCollectionGetResponse execute(
			DEDataRecordCollectionGetRequest deDataRecordCollectionGetRequest)
		throws Exception {

		long deDataRecordCollectionId =
			deDataRecordCollectionGetRequest.getDEDataRecordCollectionId();

		DDLRecordSet ddlRecordSet = _ddlRecordSetLocalService.getRecordSet(
			deDataRecordCollectionId);

		return DEDataRecordCollectionGetResponse.Builder.of(
			_deDataEngineRequestExecutor.map(ddlRecordSet));
	}

	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final DEDataEngineRequestExecutor _deDataEngineRequestExecutor;

}