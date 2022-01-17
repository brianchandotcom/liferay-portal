/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
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