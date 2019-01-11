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

import com.liferay.data.engine.model.DEDataDefinition;
import com.liferay.data.engine.model.DEDataRecordCollection;
import com.liferay.data.engine.web.internal.graphql.model.DataDefinition;
import com.liferay.data.engine.web.internal.graphql.model.DataRecordCollection;
import com.liferay.data.engine.web.internal.graphql.model.DataRecordCollectionType;

/**
 * @author Leonardo Barros
 */
public abstract class DEBaseDataRecordCollectionDataFetcher
	extends DEBaseDataFetcher {

	protected DataRecordCollection createDataRecordCollection(
		long deDataRecordCollectionId,
		DEDataRecordCollection deDataRecordCollection,
		DESaveDataDefinitionDataFetcher deSaveDataDefinitionDataFetcher) {

		DataRecordCollection dataRecordCollection =
			new DataRecordCollectionType();

		DEDataDefinition deDataDefinition =
			deDataRecordCollection.getDEDataDefinition();

		DataDefinition dataDefinition =
			deSaveDataDefinitionDataFetcher.createDataDefinition(
				deDataDefinition.getDEDataDefinitionId(), deDataDefinition);

		dataRecordCollection.setDataDefinition(dataDefinition);

		dataRecordCollection.setDataRecordCollectionId(
			String.valueOf(deDataRecordCollectionId));
		dataRecordCollection.setDescriptions(
			getLocalizedValuesType(deDataRecordCollection.getDescription()));
		dataRecordCollection.setNames(
			getLocalizedValuesType(deDataRecordCollection.getName()));

		return dataRecordCollection;
	}

}