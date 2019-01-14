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
import com.liferay.data.engine.service.DEDataDefinitionRequestBuilder;
import com.liferay.data.engine.service.DEDataDefinitionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataDefinitionSaveModelPermissionsResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.web.internal.graphql.model.SaveModelPermissionsDataDefinitionType;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	service = DESaveModelPermissionsDataDefinitionDataFetcher.class
)
public class DESaveModelPermissionsDataDefinitionDataFetcher
	extends DEBaseDataDefinitionDataFetcher
	implements DataFetcher<SaveModelPermissionsDataDefinitionType> {

	@Override
	public SaveModelPermissionsDataDefinitionType get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		SaveModelPermissionsDataDefinitionType
			saveModelPermissionsDataDefinitionType =
				new SaveModelPermissionsDataDefinitionType();

		String errorMessage = null;
		String languageId = dataFetchingEnvironment.getArgument("languageId");

		try {
			Map<String, Object> properties =
				dataFetchingEnvironment.getArgument(
					"saveDataDefinitionModelPermissionsInput");

			DEDataDefinitionSaveModelPermissionsRequest.Builder builder =
				DEDataDefinitionRequestBuilder.saveModelPermissionsBuilder(
					MapUtil.getLong(properties, "companyId"),
					MapUtil.getLong(properties, "scopedGroupId"),
					MapUtil.getLong(properties, "dataDefinitionId")
				).grantTo(
					MapUtil.getLong(properties, "userId")
				).inGroup(
					MapUtil.getLong(properties, "groupId")
				);

			if (MapUtil.getBoolean(properties, "delete")) {
				builder = builder.allowDelete();
			}

			if (MapUtil.getBoolean(properties, "update")) {
				builder = builder.allowUpdate();
			}

			if (MapUtil.getBoolean(properties, "view")) {
				builder = builder.allowView();
			}

			DEDataDefinitionSaveModelPermissionsResponse
				deDataDefinitionSaveModelPermissionsResponse =
					deDataDefinitionService.execute(builder.build());

			saveModelPermissionsDataDefinitionType.setDataDefinitionId(
				String.valueOf(
					deDataDefinitionSaveModelPermissionsResponse.
						getDEDataDefinitionId()));
		}
		catch (DEDataDefinitionException.MustHavePermission mhp) {
			errorMessage = getMessage(
				languageId, "the-user-must-have-permission",
				getActionMessage(languageId, mhp.getActionId()));
		}
		catch (Exception e) {
			errorMessage = getMessage(
				languageId, "unable-to-save-permissions-for-data-definition");
		}

		if (errorMessage != null) {
			handleErrorMessage(errorMessage);
		}

		return saveModelPermissionsDataDefinitionType;
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