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

import com.liferay.data.engine.model.DEDataRecord;
import com.liferay.data.engine.service.DEDataRecordCollectionListRecordRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionListRecordResponse;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcela Cunha
 */
public class DEDataRecordCollectionListRecordRequestExecutor {

	public DEDataRecordCollectionListRecordRequestExecutor(
		DDLRecordLocalService ddlRecordLocalService,
		DEDataEngineRequestExecutor deDataEngineRequestExecutor) {

		_ddlRecordLocalService = ddlRecordLocalService;
		_deDataEngineRequestExecutor = deDataEngineRequestExecutor;
	}

	public DEDataRecordCollectionListRecordResponse execute(
			DEDataRecordCollectionListRecordRequest
				deDataRecordCollectionGetRecordRequest)
		throws Exception {

		List<DEDataRecord> deDataRecords = new ArrayList<>();

		List<DDLRecord> ddlRecords = _ddlRecordLocalService.getRecords(
			deDataRecordCollectionGetRecordRequest.getDERecordCollectionId(),
			deDataRecordCollectionGetRecordRequest.getStart(),
			deDataRecordCollectionGetRecordRequest.getEnd(), null);

		for (DDLRecord ddlRecord : ddlRecords) {
			deDataRecords.add(_deDataEngineRequestExecutor.map(ddlRecord));
		}

		return DEDataRecordCollectionListRecordResponse.Builder.of(
			deDataRecords);
	}

	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DEDataEngineRequestExecutor _deDataEngineRequestExecutor;

}