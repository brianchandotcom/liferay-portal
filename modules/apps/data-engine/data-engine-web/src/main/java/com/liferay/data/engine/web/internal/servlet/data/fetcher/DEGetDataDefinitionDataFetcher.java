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
import com.liferay.data.engine.service.DEDataDefinitionGetResponse;
import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinition;
import com.liferay.data.engine.web.internal.graphql.model.GetDataDefinitionType;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.Portal;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(immediate = true, service = DEGetDataDefinitionDataFetcher.class)
public class DEGetDataDefinitionDataFetcher
	extends DEBaseDataDefinitionDataFetcher
	implements DataFetcher<GetDataDefinitionType> {

	@Override
	public GetDataDefinitionType get(DataFetchingEnvironment environment) {
		GetDataDefinitionType getDataDefinitionType =
			new GetDataDefinitionType();

		String errorMessage = null;
		String languageId = environment.getArgument("languageId");

		try {
			long dataDefinitionId = GetterUtil.getLong(
				environment.getArgument("dataDefinitionId"));

			DEDataDefinitionGetResponse deDataDefinitionGetResponse =
				deDataDefinitionService.execute(
					DEDataDefinitionRequestBuilder.getBuilder(
					).byId(
						dataDefinitionId
					).build());

			DEDataDefinition deDataDefinition =
				deDataDefinitionGetResponse.getDeDataDefinition();

			DataDefinition dataDefinition = createDataDefinition(
				dataDefinitionId, deDataDefinition);

			getDataDefinitionType.setDataDefinition(dataDefinition);
		}
		catch (DEDataDefinitionException.MustHavePermission mhp) {
			errorMessage = getMessage(
				languageId, "the-user-must-have-data-definition-permission",
				getActionMessage(languageId, mhp.getActionId()));
		}
		catch (DEDataDefinitionException.NoSuchDataDefinition nsdd) {
			errorMessage = getMessage(
				languageId, "no-data-definition-exists-with-id-x",
				nsdd.getDEDataDefinitionId());
		}
		catch (Exception e) {
			errorMessage = getMessage(
				languageId, "unable-to-retrive-data-definition");
		}

		if (errorMessage != null) {
			handleErrorMessage(errorMessage);
		}

		return getDataDefinitionType;
	}

	@Override
	public Portal getPortal() {
		return portal;
	}

	@Reference
	protected DEDataDefinitionService deDataDefinitionService;

	@Reference
	protected Portal portal;

}