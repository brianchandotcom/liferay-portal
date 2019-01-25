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
import com.liferay.data.engine.service.DEDataRecordCollectionGetRecordRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionGetRecordResponse;
import com.liferay.dynamic.data.lists.model.DDLRecord;
import com.liferay.dynamic.data.lists.service.DDLRecordLocalService;

/**
 * @author Leonardo Barros
 */
public class DEDataRecordCollectionGetRecordRequestExecutor {

	public DEDataRecordCollectionGetRecordRequestExecutor(
		DEDataEngineRequestExecutor deDataEngineRequestExecutor,
		DDLRecordLocalService ddlRecordLocalService) {

		_deDataEngineRequestExecutor = deDataEngineRequestExecutor;
		_ddlRecordLocalService = ddlRecordLocalService;
	}

	public DEDataRecordCollectionGetRecordResponse execute(
			DEDataRecordCollectionGetRecordRequest
				deDataRecordCollectionGetRecordRequest)
		throws Exception {

		DDLRecord ddlRecord = _ddlRecordLocalService.getRecord(
			deDataRecordCollectionGetRecordRequest.getDEDataRecordId());

		DEDataRecord deDataRecord = _deDataEngineRequestExecutor.map(ddlRecord);

		return DEDataRecordCollectionGetRecordResponse.Builder.of(deDataRecord);
	}

	private final DDLRecordLocalService _ddlRecordLocalService;
	private final DEDataEngineRequestExecutor _deDataEngineRequestExecutor;

}