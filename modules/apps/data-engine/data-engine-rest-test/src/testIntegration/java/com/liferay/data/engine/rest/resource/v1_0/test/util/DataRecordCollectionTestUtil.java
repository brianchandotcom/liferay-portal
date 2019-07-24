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

import com.liferay.data.engine.rest.client.dto.v1_0.DataRecordCollection;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.resource.v1_0.DataRecordCollectionResource;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;

/**
 * @author Marcela Cunha
 */
public class DataRecordCollectionTestUtil {

	public static DataRecordCollection createDataRecordCollection(
		long dataDefinitionId, String description, String name, long siteId) {

		DataRecordCollection dataRecordCollection = new DataRecordCollection() {
			{
				dataRecordCollectionKey = RandomTestUtil.randomString();
			}
		};

		dataRecordCollection.setDataDefinitionId(dataDefinitionId);

		dataRecordCollection.setDescription(
			new HashMap<String, Object>() {
				{
					put("en_US", description);
				}
			});

		dataRecordCollection.setName(
			new HashMap<String, Object>() {
				{
					put("en_US", name);
				}
			});

		dataRecordCollection.setSiteId(siteId);

		return dataRecordCollection;
	}

	public static HttpInvoker.HttpResponse getDataRecordCollectionHttpResponse(
			long dataRecordCollectionId)
		throws Exception {

		DataRecordCollectionResource.Builder recordCollectionBuilder =
			DataRecordCollectionResource.builder();

		DataRecordCollectionResource dataRecordCollectionResource =
			recordCollectionBuilder.locale(
				LocaleUtil.getDefault()
			).build();

		return dataRecordCollectionResource.getDataRecordCollectionHttpResponse(
			dataRecordCollectionId);
	}

	public static DataRecordCollection getSiteDataRecordCollection(
			Long siteId, String dataRecordCollectionKey)
		throws Exception {

		DataRecordCollectionResource.Builder recordCollectionBuilder =
			DataRecordCollectionResource.builder();

		DataRecordCollectionResource dataRecordCollectionResource =
			recordCollectionBuilder.locale(
				LocaleUtil.getDefault()
			).build();

		return dataRecordCollectionResource.getSiteDataRecordCollection(
			siteId, dataRecordCollectionKey);
	}

	public static DataRecordCollection postDataDefinitionDataRecordCollection(
			long dataDefinitionId, DataRecordCollection dataRecordCollection)
		throws Exception {

		DataRecordCollectionResource.Builder recordCollectionBuilder =
			DataRecordCollectionResource.builder();

		DataRecordCollectionResource dataRecordCollectionResource =
			recordCollectionBuilder.locale(
				LocaleUtil.getDefault()
			).build();

		return dataRecordCollectionResource.
			postDataDefinitionDataRecordCollection(
				dataDefinitionId, dataRecordCollection);
	}

}