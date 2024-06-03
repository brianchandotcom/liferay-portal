/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.object.definition.util;

import com.liferay.batch.engine.unit.BatchEngineUnitThreadLocal;
import com.liferay.portal.kernel.feature.flag.FeatureFlagManagerUtil;
import com.liferay.portal.kernel.util.HashMapBuilder;
import com.liferay.portal.kernel.util.PortalRunMode;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.tools.DBUpgrader;
import com.liferay.portal.util.PortalInstances;

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

		if (PortalRunMode.isTestMode() && StringUtil.startsWith(name, "Test")) {
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

	public static boolean isInvokerBundleAllowed() {
		if (DBUpgrader.isUpgradeClient() ||
			PortalInstances.isCurrentCompanyInDeletionProcess() ||
			PortalRunMode.isTestMode()) {

			return true;
		}

		String fileName = BatchEngineUnitThreadLocal.getFileName();

		for (String allowedInvokerBundleSymbolicName :
				_ALLOWED_INVOKER_BUNDLE_SYMBOLIC_NAMES) {

			if (fileName.matches(
					_getInvokerFileNameRegex(
						allowedInvokerBundleSymbolicName))) {

				return true;
			}
		}

		return false;
	}

	private static String _getInvokerFileNameRegex(
		String allowedInvokerBundleSymbolicName) {

		String invokerFileNameRegex = StringUtil.replace(
			allowedInvokerBundleSymbolicName, '.', "\\.");

		return invokerFileNameRegex + "_\\d+\\.\\d+\\.\\d+\\s+\\[\\d+\\]";
	}

	private static final String[] _ALLOWED_INVOKER_BUNDLE_SYMBOLIC_NAMES = {
		"com.liferay.commerce.service", "com.liferay.cookies.impl",
		"com.liferay.frontend.data.set.views.web",
		"com.liferay.headless.builder.impl", "com.liferay.list.type.service",
		"com.liferay.notification.service", "com.liferay.object.service"
	};

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
			"CommerceReturn", "/commerce-returns"
		).put(
			"CommerceReturnItem", "/commerce-return-items"
		).put(
			() -> {
				if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
					return "DSMAction";
				}

				return "FDSAction";
			},
			"/data-set-manager/actions"
		).put(
			() -> {
				if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
					return "DSMCardsSection";
				}

				return "FDSCardsSection";
			},
			"/data-set-manager/cards-sections"
		).put(
			() -> {
				if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
					return "DSMClientExtensionFilter";
				}

				return "FDSClientExtensionFilter";
			},
			"/data-set-manager/client-extension-filters"
		).put(
			() -> {
				if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
					return "DSMDateFilter";
				}

				return "FDSDateFilter";
			},
			"/data-set-manager/date-filters"
		).put(
			() -> {
				if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
					return "DSMSelectionFilter";
				}

				return "FDSDynamicFilter";
			},
			"/data-set-manager/selection-filters"
		).put(
			"FDSEntry", "/data-set-manager/entries"
		).put(
			() -> {
				if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
					return "DSMTableSection";
				}

				return "FDSField";
			},
			"/data-set-manager/table-sections"
		).put(
			() -> {
				if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
					return "DSMListSection";
				}

				return "FDSListSection";
			},
			"/data-set-manager/list-sections"
		).put(
			() -> {
				if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
					return "DSMSort";
				}

				return "FDSSort";
			},
			"/data-set-manager/sorts"
		).put(
			() -> {
				if (FeatureFlagManagerUtil.isEnabled("LPD-15729")) {
					return "DSMDataSet";
				}

				return "FDSView";
			},
			"/data-set-manager/data-sets"
		).put(
			"FunctionalCookieEntry", "/functional-cookies-entries"
		).put(
			"NecessaryCookieEntry", "/necessary-cookies-entries"
		).put(
			"PerformanceCookieEntry", "/performance-cookies-entries"
		).put(
			"PersonalizationCookieEntry", "/personalization-cookies-entries"
		).build();

	private static final Map<String, String>
		_allowedUnmodifiableSystemObjectDefinitionNames = HashMapBuilder.put(
			"AccountEntry", "L_ACCOUNT"
		).put(
			"Address", "L_POSTAL_ADDRESS"
		).put(
			"CommerceOrder", "L_COMMERCE_ORDER"
		).put(
			"CommerceOrderItem", "L_COMMERCE_ORDER_ITEM"
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