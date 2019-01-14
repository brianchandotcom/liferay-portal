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
import com.liferay.data.engine.service.DEDataRecordCollectionRequestBuilder;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveModelPermissionsRequest;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveModelPermissionsResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.data.engine.web.internal.graphql.model.SaveModelPermissionsDataRecordCollectionType;
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
	service = DESaveModelPermissionsDataRecordCollectionDataFetcher.class
)
public class DESaveModelPermissionsDataRecordCollectionDataFetcher
	extends DEBaseDataRecordCollectionDataFetcher
	implements DataFetcher<SaveModelPermissionsDataRecordCollectionType> {

	@Override
	public SaveModelPermissionsDataRecordCollectionType get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		SaveModelPermissionsDataRecordCollectionType
			saveModelPermissionsDataRecordCollectionType =
				new SaveModelPermissionsDataRecordCollectionType();

		String errorMessage = null;
		String languageId = dataFetchingEnvironment.getArgument("languageId");

		try {
			Map<String, Object> properties =
				dataFetchingEnvironment.getArgument(
					"saveDataRecordCollectionModelPermissionsInput");

			DEDataRecordCollectionSaveModelPermissionsRequest.Builder builder =
				DEDataRecordCollectionRequestBuilder.
					saveModelPermissionsBuilder(
						MapUtil.getLong(properties, "companyId"),
						MapUtil.getLong(properties, "scopedGroupId"),
						MapUtil.getLong(properties, "dataRecordCollectionId"));

			builder = builder.grantTo(
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

			DEDataRecordCollectionSaveModelPermissionsResponse
				deDataRecordCollectionSaveModelPermissionsResponse =
					deDataRecordCollectionService.execute(builder.build());

			saveModelPermissionsDataRecordCollectionType.
				setDataRecordCollectionId(
					String.valueOf(
						deDataRecordCollectionSaveModelPermissionsResponse.
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
				"unable-to-save-permissions-for-data-record-collection");
		}

		if (errorMessage != null) {
			handleErrorMessage(errorMessage);
		}

		return saveModelPermissionsDataRecordCollectionType;
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