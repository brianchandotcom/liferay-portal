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

package com.liferay.layout.page.template.admin.web.internal.validator;

import com.liferay.layout.page.template.admin.web.internal.exception.PageDefinitionValidatorException;
import com.liferay.petra.json.validator.JSONValidator;
import com.liferay.petra.json.validator.JSONValidatorException;
import com.liferay.portal.kernel.util.Validator;

import java.io.InputStream;

/**
 * @author Rubén Pulido
 */
public class PageDefinitionValidator {

	public static void validatePageDefinition(String pageDefinitionJSON)
		throws PageDefinitionValidatorException {

		if (Validator.isNull(pageDefinitionJSON)) {
			return;
		}

		InputStream pageDefinitionJSONSchemaInputStream =
			PageDefinitionValidator.class.getResourceAsStream(
				"dependencies/page_definition_json_schema.json");

		try {
			JSONValidator.validate(
				pageDefinitionJSON, pageDefinitionJSONSchemaInputStream);
		}
		catch (JSONValidatorException jsonValidatorException) {
			throw new PageDefinitionValidatorException(
				jsonValidatorException.getMessage(), jsonValidatorException);
		}
	}

}