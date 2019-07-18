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

package com.liferay.change.tracking.rest.internal.dto.v1_0.util;

import com.liferay.change.tracking.constants.CTConstants;
import com.liferay.change.tracking.engine.CTEngineManager;
import com.liferay.change.tracking.model.CTCollection;
import com.liferay.change.tracking.rest.dto.v1_0.Collection;

import java.util.Map;

/**
 * @author Máté Thurzó
 */
public class CollectionUtil {

	public static Collection toCollection(
		CTCollection ctCollection, CTEngineManager ctEngineManager) {

		Map<Integer, Long> ctCollectionChangeTypeCounts =
			ctEngineManager.getCTCollectionChangeTypeCounts(
				ctCollection.getCtCollectionId());

		return new Collection() {
			{
				additionCount = ctCollectionChangeTypeCounts.getOrDefault(
					CTConstants.CT_CHANGE_TYPE_ADDITION, 0L);
				companyId = ctCollection.getCompanyId();
				dateStatus = ctCollection.getStatusDate();
				deletionCount = ctCollectionChangeTypeCounts.getOrDefault(
					CTConstants.CT_CHANGE_TYPE_DELETION, 0L);
				description = ctCollection.getDescription();
				id = ctCollection.getCtCollectionId();
				modificationCount = ctCollectionChangeTypeCounts.getOrDefault(
					CTConstants.CT_CHANGE_TYPE_MODIFICATION, 0L);
				name = ctCollection.getName();
				statusByUserName = ctCollection.getStatusByUserName();
			}
		};
	}

}