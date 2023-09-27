/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.importer.validator;

import com.liferay.portal.json.validator.JSONValidator;
import com.liferay.portal.json.validator.JSONValidatorException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Bárbara Cabrera
 */
public class UtilityPageTemplateValidator {

	public static void validateUtilityPageTemplate(
			String utilityPageTemplateJSON)
		throws JSONValidatorException {

		if (Validator.isNull(utilityPageTemplateJSON)) {
			return;
		}

		_jsonValidator.validate(utilityPageTemplateJSON);
	}

	private static final JSONValidator _jsonValidator = new JSONValidator(
		UtilityPageTemplateValidator.class.getResource(
			"dependencies/utility_page_template_json_schema.json"));

}