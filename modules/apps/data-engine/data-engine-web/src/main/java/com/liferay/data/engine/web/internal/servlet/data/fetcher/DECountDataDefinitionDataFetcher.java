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

package com.liferay.data.engine.web.internal.servlet.data.fetcher;

import com.liferay.data.engine.service.DEDataDefinitionCountRequest;
import com.liferay.data.engine.service.DEDataDefinitionCountResponse;
import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.web.internal.graphql.model.CountDataDefinitionType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = DECountDataDefinitionDataFetcher.class)
public class DECountDataDefinitionDataFetcher
	extends DEBaseDataDefinitionDataFetcher
	implements DataFetcher<CountDataDefinitionType> {

	@Override
	public CountDataDefinitionType get(DataFetchingEnvironment environment) {
		CountDataDefinitionType countDataDefinitionType =
			new CountDataDefinitionType();

		long groupId = GetterUtil.getLong(environment.getArgument("groupId"));

		DEDataDefinitionCountRequest deDataDefinitionCountRequest =
			DEDataDefinitionRequestBuilder.countBuilder(
			).inGroup(
				groupId
			).build();

		DEDataDefinitionCountResponse deDataDefinitionCountResponse =
			deDataDefinitionService.execute(deDataDefinitionCountRequest);

		countDataDefinitionType.setTotal(
			deDataDefinitionCountResponse.getTotal());

		return countDataDefinitionType;
	}

	@Override
	protected Portal getPortal() {
		return portal;
	}

	@Reference
	protected DEDataDefinitionService deDataDefinitionService;

	@Reference
	protected Portal portal;

}