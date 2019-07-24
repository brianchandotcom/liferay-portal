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

import com.liferay.data.engine.rest.client.dto.v1_0.DataLayout;
import com.liferay.data.engine.rest.client.http.HttpInvoker;
import com.liferay.data.engine.rest.client.resource.v1_0.DataLayoutResource;
import com.liferay.portal.kernel.test.util.RandomTestUtil;
import com.liferay.portal.kernel.util.LocaleUtil;

import java.util.HashMap;

/**
 * @author Marcela Cunha
 */
public class DataLayoutTestUtil {

	public static DataLayout createDataLayout(
		long dataDefinitionId, long groupId, String name) {

		DataLayout dataLayout = new DataLayout() {
			{
				dataLayoutKey = RandomTestUtil.randomString();
				paginationMode = "pagination";
				siteId = groupId;
			}
		};

		dataLayout.setDataDefinitionId(dataDefinitionId);

		dataLayout.setName(
			new HashMap<String, Object>() {
				{
					put("en_US", name);
				}
			});

		return dataLayout;
	}

	public static HttpInvoker.HttpResponse getDataLayoutHttpResponse(
			long dataLayoutId)
		throws Exception {

		DataLayoutResource.Builder layoutBuilder = DataLayoutResource.builder();

		DataLayoutResource dataLayoutResource = layoutBuilder.locale(
			LocaleUtil.getDefault()
		).build();

		return dataLayoutResource.getDataLayoutHttpResponse(dataLayoutId);
	}

	public static DataLayout postDataDefinitionDataLayout(
			long dataDefinitionId, DataLayout dataLayout)
		throws Exception {

		DataLayoutResource.Builder layoutBuilder = DataLayoutResource.builder();

		DataLayoutResource dataLayoutResource = layoutBuilder.locale(
			LocaleUtil.getDefault()
		).build();

		return dataLayoutResource.postDataDefinitionDataLayout(
			dataDefinitionId, dataLayout);
	}

}