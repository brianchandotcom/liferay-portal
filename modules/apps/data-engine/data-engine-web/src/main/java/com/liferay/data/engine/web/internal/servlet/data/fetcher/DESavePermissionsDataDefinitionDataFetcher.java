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
import com.liferay.data.engine.service.DEDataDefinitionSavePermissionsRequest;
import com.liferay.data.engine.service.DEDataDefinitionSavePermissionsResponse;
import com.liferay.data.engine.service.DEDataDefinitionService;
import com.liferay.data.engine.web.internal.graphql.model.SavePermissionsDataDefinitionType;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.MapUtil;
import com.liferay.portal.kernel.util.Portal;

import graphql.schema.DataFetcher;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Marcela Cunha
 */
@Component(
	immediate = true, service = DESavePermissionsDataDefinitionDataFetcher.class
)
public class DESavePermissionsDataDefinitionDataFetcher
	extends DEBaseDataDefinitionDataFetcher
	implements DataFetcher<SavePermissionsDataDefinitionType> {

	@Override
	public SavePermissionsDataDefinitionType get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		SavePermissionsDataDefinitionType savePermissionsDataDefinitionType =
			new SavePermissionsDataDefinitionType();

		String errorMessage = null;
		String languageId = dataFetchingEnvironment.getArgument("languageId");

		try {
			Map<String, Object> properties =
				dataFetchingEnvironment.getArgument(
					"saveDataDefinitionPermissionsInput");

			DEDataDefinitionSavePermissionsRequest.Builder builder =
				DEDataDefinitionRequestBuilder.savePermissionsBuilder(
					MapUtil.getLong(properties, "companyId"),
					MapUtil.getLong(properties, "scopedGroupId"),
					ArrayUtil.toStringArray(
						(List<String>)properties.get("roleNames")));

			if (MapUtil.getBoolean(properties, "addDataDefinition")) {
				builder = builder.allowAddDataDefinition();
			}

			if (MapUtil.getBoolean(properties, "definePermissions")) {
				builder = builder.allowDefinePermissions();
			}

			DEDataDefinitionSavePermissionsResponse
				deDataDefinitionSavePermissionsResponse =
					deDataDefinitionService.execute(builder.build());

			savePermissionsDataDefinitionType.setRoleNames(
				deDataDefinitionSavePermissionsResponse.getRoleNames());
		}
		catch (DEDataDefinitionException.MustHavePermission mhp) {
			errorMessage = getMessage(
				languageId, "the-user-must-have-permission",
				getActionMessage(languageId, mhp.getActionId()));
		}
		catch (DEDataDefinitionException.PrincipalException deddepe) {
			errorMessage = getMessage(
				languageId,
				"this-role-does-not-support-this-type-of-permission");
		}
		catch (DEDataDefinitionException.NoSuchRoles nsr) {
			errorMessage = getMessage(
				languageId, "no-roles-exists-with-names-x",
				getRolesMessage(languageId, nsr.getRoleNames()));
		}
		catch (Exception e) {
			errorMessage = getMessage(
				languageId, "unable-to-save-permissions-for-data-definition");
		}

		if (errorMessage != null) {
			handleErrorMessage(errorMessage);
		}

		return savePermissionsDataDefinitionType;
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