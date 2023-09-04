/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
		).put(
			"FDSAction", "/data-set-manager/actions"
		).put(
			"FDSDateFilter", "/data-set-manager/date-filters"
		).put(
			"FDSDynamicFilter", "/data-set-manager/dynamic-filters"
		).put(
			"FDSEntry", "/data-set-manager/entries"
		).put(
			"FDSField", "/data-set-manager/fields"
		).put(
			"FDSSort", "/data-set-manager/sorts"
		).put(
			"FDSView", "/data-set-manager/views"
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