/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
			String fileName, String openAPIYAMLString, Yaml yaml)
		throws OpenAPIValidatorException {

		OpenAPIYAML openAPIYAML = yaml.loadAs(
			openAPIYAMLString, OpenAPIYAML.class);

		Info info = openAPIYAML.getInfo();

		if (info.getVersion() == null) {
			throw new OpenAPIValidatorException(
				String.format(
					"File %s: Missing required field info: version", fileName));
		}
	}

}