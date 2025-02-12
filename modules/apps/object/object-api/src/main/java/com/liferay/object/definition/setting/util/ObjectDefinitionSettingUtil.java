/**
 * SPDX-FileCopyrightText: (c) 2025 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.definition.setting.util;

import com.liferay.object.model.ObjectDefinitionSetting;

import java.util.List;
import java.util.Objects;

/**
 * @author Pedro Tavares
 */
public class ObjectDefinitionSettingUtil {

	public static String getValue(
		String name, List<ObjectDefinitionSetting> objectDefinitionSettings) {

		for (ObjectDefinitionSetting objectDefinitionSetting :
				objectDefinitionSettings) {

			if (Objects.equals(objectDefinitionSetting.getName(), name)) {
				return objectDefinitionSetting.getValue();
			}
		}

		return null;
	}

}