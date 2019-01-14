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
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionDeleteModelPermissionsResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionRequestBuilder;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.data.engine.web.internal.graphql.model.DeleteModelPermissionsDataRecordCollectionType;
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
	service = DEDeleteModelPermissionsDataRecordCollectionDataFetcher.class
)
public class DEDeleteModelPermissionsDataRecordCollectionDataFetcher
	extends DEBaseDataRecordCollectionDataFetcher
	implements DataFetcher<DeleteModelPermissionsDataRecordCollectionType> {

	@Override
	public DeleteModelPermissionsDataRecordCollectionType get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		DeleteModelPermissionsDataRecordCollectionType
			deleteModelPermissionsDataRecordCollectionType =
				new DeleteModelPermissionsDataRecordCollectionType();

		String errorMessage = null;
		String languageId = dataFetchingEnvironment.getArgument("languageId");

		try {
			Map<String, Object> properties =
				dataFetchingEnvironment.getArgument(
					"deleteDataRecordCollectionModelPermissionsInput");

			DEDataRecordCollectionDeleteModelPermissionsRequest.Builder
				builder =
					DEDataRecordCollectionRequestBuilder.
						deleteModelPermissionsBuilder(
							MapUtil.getLong(properties, "companyId"),
							MapUtil.getLong(properties, "scopedGroupId"),
							MapUtil.getLong(
								properties, "dataRecordCollectionId"));

			DEDataRecordCollectionDeleteModelPermissionsResponse
				deDataRecordCollectionDeleteModelPermissionsResponse =
					deDataRecordCollectionService.execute(builder.build());

			deleteModelPermissionsDataRecordCollectionType.
				setDataRecordCollectionId(
					String.valueOf(
						deDataRecordCollectionDeleteModelPermissionsResponse.
							getDEDataRecordCollectionId()));
		}
		catch (DEDataRecordCollectionException.MustHavePermission mhp) {
			errorMessage = getMessage(
				languageId, "the-user-must-have-permission",
				getActionMessage(languageId, mhp.getActionId()));
		}
		catch (Exception e) {
			errorMessage = getMessage(
				languageId,
				"unable-to-delete-permissions-for-data-record-collection");
		}

		if (errorMessage != null) {
			handleErrorMessage(errorMessage);
		}

		return deleteModelPermissionsDataRecordCollectionType;
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