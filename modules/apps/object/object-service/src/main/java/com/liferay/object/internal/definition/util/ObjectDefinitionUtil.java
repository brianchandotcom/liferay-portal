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

package com.liferay.object.internal.definition.util;

import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringUtil;

import java.util.Map;
import java.util.Objects;

/**
 * @author Alejandro Tardín
 */
public class ObjectDefinitionUtil {

	public static String getModifiableSystemObjectDefinitionRESTContextPath(
		String name) {

		if (PortalRunMode.isTestMode() && Objects.equals(name, "Test")) {
			return "/test";
		}

		return _allowedModifiableSystemObjectDefinitionNames.get(name);
	}

	public static boolean isAllowedModifiableSystemObjectDefinitionName(
		String name) {

		if (PortalRunMode.isTestMode() && Objects.equals(name, "Test")) {
			return true;
		}

		return _allowedModifiableSystemObjectDefinitionNames.containsKey(name);
	}

	public static boolean
		isAllowedUnmodifiableSystemObjectDefinitionExternalReferenceCode(
			String externalReferenceCode, String name) {

		if (PortalRunMode.isTestMode()) {
			return true;
		}

		return StringUtil.equals(
			_allowedUnmodifiableSystemObjectDefinitionNames.get(name),
			externalReferenceCode);
	}

	private static final Map<String, String>
		_allowedModifiableSystemObjectDefinitionNames = HashMapBuilder.put(
			"APIApplication", "/headless-builder/applications"
		).put(
			"APIEndpoint", "/headless-builder/endpoints"
		).put(
			"APIFilter", "/headless-builder/filters"
		).put(
			"APIProperty", "/headless-builder/properties"
		).put(
			"APISchema", "/headless-builder/schemas"
		).put(
			"APISort", "/headless-builder/sorts"
		).put(
			"Bookmark", "/bookmarks"
		).build();
	private static final Map<String, String>
		_allowedUnmodifiableSystemObjectDefinitionNames = HashMapBuilder.put(
			"AccountEntry", "L_ACCOUNT"
		).put(
			"Address", "L_POSTAL_ADDRESS"
		).put(
			"CommerceOrder", "L_COMMERCE_ORDER"
		).put(
			"CommercePricingClass", "L_COMMERCE_PRODUCT_GROUP"
		).put(
			"CPDefinition", "L_COMMERCE_PRODUCT_DEFINITION"
		).put(
			"Organization", "L_ORGANIZATION"
		).put(
			"User", "L_USER"
		).build();

}