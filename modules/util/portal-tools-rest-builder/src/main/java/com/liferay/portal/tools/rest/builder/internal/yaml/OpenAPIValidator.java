/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.tools.rest.builder.internal.yaml;

import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.rest.builder.internal.yaml.exception.OpenAPIValidatorException;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Components;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Info;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.OpenAPIYAML;
import com.liferay.portal.tools.rest.builder.internal.yaml.openapi.Schema;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.yaml.snakeyaml.Yaml;

/**
 * @author Javier de Arcos
 */
public class OpenAPIValidator {

	public static void validate(
			String fileName, String openAPIYAMLString, Yaml yaml)
		throws OpenAPIValidatorException {

		List<String> validationErrorMessages = new ArrayList<>();

		OpenAPIYAML openAPIYAML = yaml.loadAs(
			openAPIYAMLString, OpenAPIYAML.class);

		Info info = openAPIYAML.getInfo();

		if (info.getVersion() == null) {
			validationErrorMessages.add(
				String.format(
					"File %s: Missing required field info: version", fileName));
		}

		Components components = openAPIYAML.getComponents();

		Map<String, Schema> schemas = components.getSchemas();

		for (Map.Entry<String, Schema> entry : schemas.entrySet()) {
			Schema schema = entry.getValue();

			if ((schema.getPropertySchemas() != null) &&
				!Objects.equals(schema.getType(), "object")) {

				validationErrorMessages.add(
					String.format(
						"File %s: Schema %s is missing required property '%s'",
						fileName, entry.getKey(), "type: object"));
			}
		}

		if (!validationErrorMessages.isEmpty()) {
			throw new OpenAPIValidatorException(
				StringUtil.merge(validationErrorMessages, StringPool.NEW_LINE));
		}
	}

}