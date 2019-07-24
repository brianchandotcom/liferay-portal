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

package com.liferay.data.engine.rest.resource.v1_0.test.util;

import com.liferay.data.engine.rest.client.dto.v1_0.DataRecord;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.resource.v1_0.DataRecordResource;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;

/**
 * @author Marcela Cunha
 */
public class DataRecordTestUtil {

	public static DataRecord createDataRecord(
		long dataRecordCollectionId, String fieldName) {

		DataRecord dataRecord = new DataRecord() {
			{
				dataRecordValues = new HashMap<String, Object>() {
					{
						put(fieldName, RandomTestUtil.randomString());
					}
				};
			}
		};

		dataRecord.setDataRecordCollectionId(dataRecordCollectionId);

		return dataRecord;
	}

	public static HttpInvoker.HttpResponse getDataRecordHttpResponse(
			long dataRecordId)
		throws Exception {

		DataRecordResource.Builder recordBuilder = DataRecordResource.builder();

		DataRecordResource dataRecordResource = recordBuilder.locale(
			LocaleUtil.getDefault()
		).build();

		return dataRecordResource.getDataRecordHttpResponse(dataRecordId);
	}

	public static DataRecord postDataRecordCollectionDataRecord(
			long dataRecordColectionId, DataRecord dataRecord)
		throws Exception {

		DataRecordResource.Builder recordBuilder = DataRecordResource.builder();

		DataRecordResource dataRecordResource = recordBuilder.locale(
			LocaleUtil.getDefault()
		).build();

		return dataRecordResource.postDataRecordCollectionDataRecord(
			dataRecordColectionId, dataRecord);
	}

}