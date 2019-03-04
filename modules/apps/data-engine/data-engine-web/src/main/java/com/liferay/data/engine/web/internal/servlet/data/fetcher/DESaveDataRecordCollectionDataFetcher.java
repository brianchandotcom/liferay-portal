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
import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.service.DEDataRecordCollectionRequestBuilder;
import com.liferay.data.engine.service.DEDataRecordCollectionSaveResponse;
import com.liferay.data.engine.service.DEDataRecordCollectionService;
import com.liferay.data.engine.web.internal.graphql.model.DataRecordCollection;
import com.liferay.data.engine.web.internal.graphql.model.SaveDataRecordCollectionType;
import com.liferay.portal.kernel.util.GetterUtil;
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
	immediate = true, service = DESaveDataRecordCollectionDataFetcher.class
)
public class DESaveDataRecordCollectionDataFetcher
	extends DEBaseDataRecordCollectionDataFetcher
	implements DataFetcher<SaveDataRecordCollectionType> {

	@Override
	public SaveDataRecordCollectionType get(
		DataFetchingEnvironment dataFetchingEnvironment) {

		SaveDataRecordCollectionType saveDataRecordCollectionType =
			new SaveDataRecordCollectionType();

		String errorMessage = null;
		String languageId = dataFetchingEnvironment.getArgument("languageId");

		try {
			Map<String, Object> dataRecordCollectionAttributes =
				dataFetchingEnvironment.getArgument("dataRecordCollection");

			Map<String, Object> dataDefinitionAttributes =
				(Map<String, Object>)dataRecordCollectionAttributes.get(
					"dataDefinition");

			DEDataDefinition deDataDefinition = new DEDataDefinition();

			deDataDefinition.setDEDataDefinitionId(
				MapUtil.getLong(dataDefinitionAttributes, "dataDefinitionId"));

			DEDataRecordCollection deDataRecordCollection =
				new DEDataRecordCollection();

			deDataRecordCollection.setDescription(
				getLocalizedValues(
					(List<Map<String, Object>>)
						dataRecordCollectionAttributes.get("descriptions")));
			deDataRecordCollection.setName(
				getLocalizedValues(
					(List<Map<String, Object>>)
						dataRecordCollectionAttributes.get("names")));
			deDataRecordCollection.setDEDataDefinition(deDataDefinition);

			DEDataRecordCollectionSaveResponse
				deDataRecordCollectionSaveResponse =
					deDataRecordCollectionService.execute(
						DEDataRecordCollectionRequestBuilder.saveBuilder(
							deDataRecordCollection
						).inGroup(
							GetterUtil.getLong(
								dataFetchingEnvironment.getArgument("groupId"))
						).onBehalfOf(
							GetterUtil.getLong(
								dataFetchingEnvironment.getArgument("userId"))
						).build());

			DataRecordCollection dataRecordCollection =
				createDataRecordCollection(
					deDataRecordCollectionSaveResponse.
						getDEDataRecordCollection(),
					deSaveDataDefinitionDataFetcher);

			saveDataRecordCollectionType.setDataRecordCollection(
				dataRecordCollection);
		}
		catch (DEDataRecordCollectionException.MustHavePermission mhp) {
			errorMessage = getMessage(
				languageId, "the-user-must-have-data-permission",
				getActionMessage(languageId, mhp.getActionId()));
		}
		catch (DEDataRecordCollectionException.NoSuchDataRecordCollection
					nsdrc) {

			errorMessage = getMessage(
				languageId, "no-data-record-collection-exists-with-id-x",
				nsdrc.getDEDataRecordCollectionId());
		}
		catch (Exception e) {
			errorMessage = getMessage(
				languageId, "unable-to-save-data-record-collection");
		}

		if (errorMessage != null) {
			handleErrorMessage(errorMessage);
		}

		return saveDataRecordCollectionType;
	}

	@Override
	protected Portal getPortal() {
		return portal;
	}

	@Reference
	protected DEDataRecordCollectionService deDataRecordCollectionService;

	@Reference
	protected DESaveDataDefinitionDataFetcher deSaveDataDefinitionDataFetcher;

	@Reference
	protected Portal portal;

}