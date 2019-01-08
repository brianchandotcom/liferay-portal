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
import com.liferay.data.engine.service.DEDataDefinitionListRequest;
import com.liferay.data.engine.service.DEDataDefinitionListResponse;
import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinition;
import com.liferay.data.engine.web.internal.graphql.model.ListDataDefinitionType;
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
@Component(immediate = true, service = DEListDataDefinitionDataFetcher.class)
public class DEListDataDefinitionDataFetcher
	extends DEBaseDataDefinitionDataFetcher
	implements DataFetcher<ListDataDefinitionType> {

	public ListDataDefinitionType get(DataFetchingEnvironment environment) {
		ListDataDefinitionType listDataDefinitionType =
			new ListDataDefinitionType();

		String errorMessage = null;

		long groupId = GetterUtil.getLong(environment.getArgument("groupId"));
		long companyId = GetterUtil.getLong(
			environment.getArgument("companyId"));
		int start = environment.getArgument("start");
		int end = environment.getArgument("end");

		DEDataDefinitionListRequest deDataDefinitionListRequest =
			DEDataDefinitionRequestBuilder.listBuilder(
			).inCompany(
				companyId
			).inGroup(
				groupId
			).startingAt(
				start
			).endingAt(
				end
			).build();

		try {
			DEDataDefinitionListResponse deDataDefinitionListResponse =
				deDataDefinitionService.execute(deDataDefinitionListRequest);

			List<DataDefinition> dataDefinitions = new ArrayList<>();

			for (DEDataDefinition deDataDefinition :
					deDataDefinitionListResponse.getDEDataDefinitions()) {

				DataDefinition dataDefinition = createDataDefinition(
					deDataDefinition.getDEDataDefinitionId(), deDataDefinition);

				dataDefinitions.add(dataDefinition);
			}

			listDataDefinitionType.setDataDefinitions(dataDefinitions);
		}
		catch (DEDataDefinitionException dedde) {
			errorMessage = getMessage(
				environment.getArgument("languageId"),
				"unable-to-retrive-data-definitions");
		}

		if (errorMessage != null) {
			handleErrorMessage(errorMessage);
		}

		return listDataDefinitionType;
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