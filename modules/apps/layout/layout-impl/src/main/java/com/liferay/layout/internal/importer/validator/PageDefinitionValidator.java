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
public class PageDefinitionValidator {

	public static void validatePageDefinition(String pageDefinitionJSON)
		throws JSONValidatorException {

		if (Validator.isNull(pageDefinitionJSON)) {
			return;
		}

		_jsonValidator.validate(pageDefinitionJSON);
	}

	private static final JSONValidator _jsonValidator = new JSONValidator(
		PageDefinitionValidator.class.getResource(
			"dependencies/page_definition_json_schema.json"));

}