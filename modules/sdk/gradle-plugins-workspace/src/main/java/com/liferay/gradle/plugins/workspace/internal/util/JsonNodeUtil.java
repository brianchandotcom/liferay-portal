/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.gradle.plugins.workspace.internal.util;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.util.Iterator;

/**
 * @author Drew Brokke
 */
public class JsonNodeUtil {

	public static void overrideJsonNodeValues(
		JsonNode baseJsonNode, JsonNode overrideJsonNode) {

		if (overrideJsonNode.isEmpty()) {
			return;
		}

		Iterator<String> iterator = overrideJsonNode.fieldNames();

		while (iterator.hasNext()) {
			String fieldName = iterator.next();

			JsonNode fieldNameBaseJsonNode = baseJsonNode.path(fieldName);

			JsonNode fieldNameOverrideJsonNode = overrideJsonNode.path(
				fieldName);

			if (fieldNameOverrideJsonNode.isMissingNode()) {
				continue;
			}

			if (fieldNameBaseJsonNode.isObject()) {
				overrideJsonNodeValues(
					fieldNameBaseJsonNode, fieldNameOverrideJsonNode);

				continue;
			}

			ObjectNode baseObjectNode = (ObjectNode)baseJsonNode;

			if (fieldNameBaseJsonNode.isMissingNode()) {
				baseObjectNode.set(fieldName, fieldNameOverrideJsonNode);

				continue;
			}

			baseObjectNode.replace(fieldName, fieldNameOverrideJsonNode);
		}
	}

}