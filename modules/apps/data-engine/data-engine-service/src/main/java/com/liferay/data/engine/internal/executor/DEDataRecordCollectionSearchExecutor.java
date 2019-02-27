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

import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataRecordCollectionSearchRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSearchResponse;
import com.liferay.dynamic.data.lists.model.DDLRecordSet;
import com.liferay.dynamic.data.lists.model.DDLRecordSetConstants;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Marcelo Mello
 */
public class DEDataRecordCollectionSearchExecutor {

	public DEDataRecordCollectionSearchExecutor(
		DDLRecordSetLocalService ddlRecordSetLocalService,
		DEDataEngineRequestExecutor deDataEngineRequestExecutor) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
		_deDataEngineRequestExecutor = deDataEngineRequestExecutor;
	}

	public DEDataRecordCollectionSearchResponse execute(
		DEDataRecordCollectionSearchRequest
			deDataRecordCollectionSearchRequest) throws Exception {

		List<DEDataRecordCollection> deDataRecordCollections =
			new ArrayList<>();

		List<DDLRecordSet> ddlRecordSets = _ddlRecordSetLocalService.search(
			deDataRecordCollectionSearchRequest.getCompanyId(),
			deDataRecordCollectionSearchRequest.getGroupId(),
			deDataRecordCollectionSearchRequest.getKeywords(),
			DDLRecordSetConstants.SCOPE_ANY,
			deDataRecordCollectionSearchRequest.getStart(),
			deDataRecordCollectionSearchRequest.getEnd(), null);

		for (DDLRecordSet ddlRecordSet : ddlRecordSets) {
			deDataRecordCollections.add(
				_deDataEngineRequestExecutor.map(ddlRecordSet));
		}

		return DEDataRecordCollectionSearchResponse.Builder.of(
			deDataRecordCollections);
	}

	private final DDLRecordSetLocalService _ddlRecordSetLocalService;
	private final DEDataEngineRequestExecutor _deDataEngineRequestExecutor;

}