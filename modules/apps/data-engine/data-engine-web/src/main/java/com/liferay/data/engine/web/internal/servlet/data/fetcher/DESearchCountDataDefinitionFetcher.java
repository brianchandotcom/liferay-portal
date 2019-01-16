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

import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionSearchCountRequest;
import com.liferay.data.engine.service.DEDataDefinitionSearchCountResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.web.internal.graphql.model.SearchCountDataDefinitionType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Gabriel Albuquerque
 */
@Component(immediate = true, service = DESearchCountDataDefinitionFetcher.class)
public class DESearchCountDataDefinitionFetcher
	extends DEBaseDataDefinitionDataFetcher
	implements DataFetcher<SearchCountDataDefinitionType> {

	@Override
	public SearchCountDataDefinitionType get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		SearchCountDataDefinitionType searchCountDataDefinitionType =
			new SearchCountDataDefinitionType();

		DEDataDefinitionSearchCountRequest deDataDefinitionSearchCountRequest =
			DEDataDefinitionRequestBuilder.searchCountBuilder(
			).havingKeywords(
				GetterUtil.getString(
					dataFetchingEnvironment.getArgument("keywords"))
			).inCompany(
				GetterUtil.getLong(
					dataFetchingEnvironment.getArgument("companyId"))
			).inGroup(
				GetterUtil.getLong(
					dataFetchingEnvironment.getArgument("groupId"))
			).build();

		DEDataDefinitionSearchCountResponse
			deDataDefinitionSearchCountResponse =
				deDataDefinitionService.execute(
					deDataDefinitionSearchCountRequest);

		searchCountDataDefinitionType.setTotal(
			deDataDefinitionSearchCountResponse.getTotal());

		return searchCountDataDefinitionType;
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