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

import com.liferay.data.engine.service.DEDataRecordCollectionDeleteRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteResponse;
import com.liferay.dynamic.data.lists.service.DDLRecordSetLocalService;

/**
 * @author Leonardo Barros
 */
public class DEDataRecordCollectionDeleteRequestExecutor {

	public DEDataRecordCollectionDeleteRequestExecutor(
		DDLRecordSetLocalService ddlRecordSetLocalService) {

		_ddlRecordSetLocalService = ddlRecordSetLocalService;
	}

	public DEDataRecordCollectionDeleteResponse execute(
			DEDataRecordCollectionDeleteRequest
				deDataRecordCollectionDeleteRequest)
		throws Exception {

		long deDataRecordCollectionId =
			deDataRecordCollectionDeleteRequest.getDEDataRecordCollectionId();

		_ddlRecordSetLocalService.deleteRecordSet(deDataRecordCollectionId);

		return DEDataRecordCollectionDeleteResponse.Builder.of(
			deDataRecordCollectionId);
	}

	private final DDLRecordSetLocalService _ddlRecordSetLocalService;

}