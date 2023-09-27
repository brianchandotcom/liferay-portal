/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.rest.dto.v1_0.util;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.search.experiences.rest.dto.v1_0.Configuration;
import com.liferay.search.experiences.rest.dto.v1_0.ElementDefinition;
import com.liferay.search.experiences.rest.dto.v1_0.Field;
import com.liferay.search.experiences.rest.dto.v1_0.UiConfiguration;

/**
 * @author André de Oliveira
 */
public class ElementDefinitionUtil {

	public static ElementDefinition toElementDefinition(String json) {
		if (Validator.isNull(json)) {
			return null;
		}

		return unpack(ElementDefinition.unsafeToDTO(json));
	}

	public static ElementDefinition unpack(
		ElementDefinition elementDefinition) {

		if (elementDefinition == null) {
			return null;
		}

		Configuration configuration = elementDefinition.getConfiguration();

		if (configuration != null) {
			elementDefinition.setConfiguration(
				ConfigurationUtil.unpack(configuration));
		}

		_unpack(elementDefinition.getUiConfiguration());

		return elementDefinition;
	}

	private static void _unpack(Field field) {
		if (field == null) {
			return;
		}

		field.setDefaultValue(UnpackUtil.unpack(field.getDefaultValue()));
	}

	private static void _unpack(UiConfiguration uiConfiguration) {
		if (uiConfiguration == null) {
			return;
		}

		ArrayUtil.isNotEmptyForEach(
			uiConfiguration.getFieldSets(),
			fieldSet -> ArrayUtil.isNotEmptyForEach(
				fieldSet.getFields(), field -> _unpack(field)));
	}

}