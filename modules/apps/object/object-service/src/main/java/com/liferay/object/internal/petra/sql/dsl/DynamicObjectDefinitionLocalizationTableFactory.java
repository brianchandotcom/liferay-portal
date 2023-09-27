/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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