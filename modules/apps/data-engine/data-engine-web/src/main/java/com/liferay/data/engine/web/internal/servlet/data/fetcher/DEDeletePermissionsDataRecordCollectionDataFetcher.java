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

import com.liferay.data.engine.exception.DEDataRecordCollectionException;
import com.liferay.data.engine.service.DEDataRecordCollectionDeletePermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionDeletePermissionsResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionRequestBuilder;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.data.engine.web.internal.graphql.model.DeletePermissionsDataRecordCollectionType;
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
 * @author Leonardo Barros
 */
@Component(
	immediate = true,
	service = DEDeletePermissionsDataRecordCollectionDataFetcher.class
)
public class DEDeletePermissionsDataRecordCollectionDataFetcher
	extends DEBaseDataRecordCollectionDataFetcher
	implements DataFetcher<DeletePermissionsDataRecordCollectionType> {

	@Override
	public DeletePermissionsDataRecordCollectionType get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		DeletePermissionsDataRecordCollectionType
			deletePermissionsDataRecordCollectionType =
				new DeletePermissionsDataRecordCollectionType();

		String errorMessage = null;
		String languageId = dataFetchingEnvironment.getArgument("languageId");

		try {
			Map<String, Object> properties =
				dataFetchingEnvironment.getArgument(
					"deleteDataRecordCollectionPermissionsInput");

			List<String> roleNames = (List<String>)properties.get("roleNames");

			DEDataRecordCollectionDeletePermissionsRequest.Builder builder =
				DEDataRecordCollectionRequestBuilder.deletePermissionsBuilder(
					MapUtil.getLong(properties, "companyId"),
					MapUtil.getLong(properties, "scopedGroupId"),
					ArrayUtil.toStringArray(roleNames));

			DEDataRecordCollectionDeletePermissionsResponse
				deDataRecordCollectionDeletePermissionsResponse =
					deDataRecordCollectionService.execute(builder.build());

			deletePermissionsDataRecordCollectionType.setRoleNames(
				deDataRecordCollectionDeletePermissionsResponse.getRoleNames());
		}
		catch (DEDataRecordCollectionException.MustHavePermission mhp) {
			errorMessage = getMessage(
				languageId, "the-user-must-have-permission",
				getActionMessage(languageId, mhp.getActionId()));
		}
		catch (DEDataRecordCollectionException.NoSuchRoles nsr) {
			errorMessage = getMessage(
				languageId, "no-roles-exists-with-names-x",
				getRolesMessage(languageId, nsr.getRoleNames()));
		}
		catch (Exception e) {
			errorMessage = getMessage(
				languageId,
				"unable-to-delete-permissions-for-data-record-collection");
		}

		if (errorMessage != null) {
			handleErrorMessage(errorMessage);
		}

		return deletePermissionsDataRecordCollectionType;
	}

	@Override
	protected Portal getPortal() {
		return portal;
	}

	@Reference
	protected DEDataRecordCollectionService deDataRecordCollectionService;

	@Reference
	protected Portal portal;

}