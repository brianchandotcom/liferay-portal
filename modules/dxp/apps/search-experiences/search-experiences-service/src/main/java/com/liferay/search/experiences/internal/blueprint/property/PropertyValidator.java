/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.search.experiences.internal.blueprint.property;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.search.experiences.blueprint.exception.UnresolvedTemplateVariableException;

import org.apache.commons.lang.StringUtils;

/**
 * @author André de Oliveira
 */
public class PropertyValidator {

	public static <T> T validate(T object) {
		String[] templateVariables = StringUtils.substringsBetween(
			object.toString(), StringPool.DOLLAR_AND_OPEN_CURLY_BRACE,
			StringPool.CLOSE_CURLY_BRACE);

		if (ArrayUtil.isNotEmpty(templateVariables)) {
			throw UnresolvedTemplateVariableException.with(templateVariables);
		}

		return object;
	}

}