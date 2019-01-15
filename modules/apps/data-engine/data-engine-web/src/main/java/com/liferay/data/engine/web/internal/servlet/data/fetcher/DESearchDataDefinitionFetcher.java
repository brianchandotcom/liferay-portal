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

import com.liferay.data.engine.exception.DEDataDefinitionException;
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionSearchRequest;
import com.liferay.data.engine.service.DEDataDefinitionSearchResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinition;
import com.liferay.data.engine.web.internal.graphql.model.SearchDataDefinitionType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.ArrayList;
import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Jeyvison Nascimento
 */
@Component(immediate = true, service = DESearchDataDefinitionFetcher.class)
public class DESearchDataDefinitionFetcher
	extends DEBaseDataDefinitionDataFetcher
	implements DataFetcher<SearchDataDefinitionType> {

	@Override
	public SearchDataDefinitionType get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		SearchDataDefinitionType searchDataDefinitionType =
			new SearchDataDefinitionType();

		String errorMessage = null;

		try {
			DEDataDefinitionSearchRequest deDataDefinitionSearchRequest =
				DEDataDefinitionRequestBuilder.searchBuilder(
				).endingAt(
					dataFetchingEnvironment.getArgument("end")
				).havingKeywords(
					dataFetchingEnvironment.getArgument("keywords")
				).inCompany(
					GetterUtil.getLong(
						dataFetchingEnvironment.getArgument("companyId"))
				).inGroup(
					GetterUtil.getLong(
						dataFetchingEnvironment.getArgument("groupId"))
				).startingAt(
					dataFetchingEnvironment.getArgument("start")
				).build();

			DEDataDefinitionSearchResponse deDataDefinitionSearchResponse =
				_deDataDefinitionService.execute(deDataDefinitionSearchRequest);

			List<DataDefinition> dataDefinitions = new ArrayList<>();

			for (DEDataDefinition deDataDefinition :
					deDataDefinitionSearchResponse.getDEDataDefinitions()) {

				dataDefinitions.add(
					createDataDefinition(
						deDataDefinition.getDEDataDefinitionId(),
						deDataDefinition));
			}

			searchDataDefinitionType.setDataDefinitions(dataDefinitions);
		}
		catch (DEDataDefinitionException dedde) {
			errorMessage = getMessage(
				dataFetchingEnvironment.getArgument("languageId"),
				"unable-to-search-data-definitions");
		}

		if (errorMessage != null) {
			handleErrorMessage(errorMessage);
		}

		return searchDataDefinitionType;
	}

	@Override
	protected Portal getPortal() {
		return _portal;
	}

	@Reference
	private DEDataDefinitionService _deDataDefinitionService;

	@Reference
	private Portal _portal;

}