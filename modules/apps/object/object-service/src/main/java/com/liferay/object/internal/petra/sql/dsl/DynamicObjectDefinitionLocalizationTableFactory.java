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

package com.liferay.object.internal.petra.sql.dsl;

import com.liferay.object.model.ObjectDefinition;
import com.liferay.object.petra.sql.dsl.DynamicObjectDefinitionLocalizationTable;
import com.liferay.object.service.ObjectFieldLocalService;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;

/**
 * @author Feliphe Marinho
 */
public class DynamicObjectDefinitionLocalizationTableFactory {

	public static DynamicObjectDefinitionLocalizationTable create(
		ObjectDefinition objectDefinition,
		ObjectFieldLocalService objectFieldLocalService) {

		if (!FeatureFlagManagerUtil.isEnabled("LPS-172017") ||
			!objectDefinition.isEnableLocalization()) {

			return null;
		}

		return new DynamicObjectDefinitionLocalizationTable(
			objectDefinition,
			objectFieldLocalService.getLocalizedObjectFields(
				objectDefinition.getObjectDefinitionId()));
	}

}