/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.layout.internal.importer.validator;

import com.liferay.portal.json.validator.JSONValidator;
import com.liferay.portal.json.validator.JSONValidatorException;
import com.liferay.portal.kernel.util.Validator;

/**
 * @author Rubén Pulido
 */
public class DisplayPageTemplateValidator {

	public static void validateDisplayPageTemplate(
			String displayPageTemplateJSON)
		throws JSONValidatorException {

		if (Validator.isNull(displayPageTemplateJSON)) {
			return;
		}

		_jsonValidator.validate(displayPageTemplateJSON);
	}

	private static final JSONValidator _jsonValidator = new JSONValidator(
		DisplayPageTemplateValidator.class.getResource(
			"dependencies/display_page_template_json_schema.json"));

}