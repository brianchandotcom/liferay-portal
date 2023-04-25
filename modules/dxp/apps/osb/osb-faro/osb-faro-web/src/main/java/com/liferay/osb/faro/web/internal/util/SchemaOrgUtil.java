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

package com.liferay.osb.faro.web.internal.util;

import com.liferay.osb.faro.engine.client.constants.FieldMappingConstants;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;

/**
 * @author Matthew Kong
 */
public class SchemaOrgUtil {

	public static String getRawType(String type) {
		if (type.startsWith(_SCHEMA_ORG_URL)) {
			type = type.substring(_SCHEMA_ORG_URL.length());
		}

		if (StringUtil.equalsIgnoreCase(
				type, FieldMappingConstants.TYPE_BOOLEAN)) {

			return FieldMappingConstants.TYPE_BOOLEAN;
		}
		else if (StringUtil.equalsIgnoreCase(
					type, FieldMappingConstants.TYPE_DATE)) {

			return FieldMappingConstants.TYPE_DATE;
		}
		else if (StringUtil.equalsIgnoreCase(
					type, FieldMappingConstants.TYPE_NUMBER)) {

			return FieldMappingConstants.TYPE_NUMBER;
		}
		else if (StringUtil.equalsIgnoreCase(
					type, FieldMappingConstants.TYPE_TEXT)) {

			return FieldMappingConstants.TYPE_TEXT;
		}

		return _rawTypes.getOrDefault(type, FieldMappingConstants.TYPE_TEXT);
	}

	public static boolean isSubtype(String type, String rawType) {
		if (rawType.equals(getRawType(type))) {
			return true;
		}

		return false;
	}

	private static final String _SCHEMA_ORG_URL = "http://schema.org/";

	private static final Map<String, String> _rawTypes = HashMapBuilder.put(
		"birthDate", FieldMappingConstants.TYPE_DATE
	).build();

}