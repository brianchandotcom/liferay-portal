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

package com.liferay.talend.common.oas;

/**
 * @author Igor Beslic
 */
public class OASExtensions {

	public String getI18nFieldName(String name) {
		if (!name.endsWith("_i18n")) {
			throw new OASException(
				String.format(
					"Name %s is not valid i18n extension field name", name));
		}

		for (int idx = name.indexOf("_i18n") - 1; idx >= 0; idx--) {
			if (name.charAt(idx) == '_') {
				return name.substring(idx + 1);
			}
		}

		return name;
	}

	public boolean isI18nFieldName(String name) {
		int idx = name.indexOf("_i18n");

		if (idx < 0) {
			return false;
		}

		if ((idx > 0) && (name.lastIndexOf("_") == idx)) {
			return true;
		}

		throw new OASException(
			"Unsupported usage of _i18n in OpenAPI schema property name");
	}

	public boolean isI18nFieldNameNested(String name) {
		if (name.indexOf("_") < name.indexOf("_i18n")) {
			return true;
		}

		return false;
	}

}