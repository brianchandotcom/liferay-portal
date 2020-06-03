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

package com.liferay.portal.tools.rest.builder.internal.yaml;

import com.liferay.portal.tools.rest.builder.internal.yaml.exception.OpenAPIValidatorException;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Info;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;

import org.yaml.snakeyaml.Yaml;

/**
 * @author Javier de Arcos
 */
public class OpenAPIValidator {

	public static void validate(
			String fileName, String openAPIYAMLString, Yaml openAPIYAML)
		throws OpenAPIValidatorException {

		OpenAPIYAML openAPI = openAPIYAML.loadAs(
			openAPIYAMLString, OpenAPIYAML.class);

		Info info = openAPI.getInfo();

		if (info.getVersion() == null) {
			throw new OpenAPIValidatorException(
				String.format(
					"File %s: Missing required field info: version", fileName));
		}
	}

}