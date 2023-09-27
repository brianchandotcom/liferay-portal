/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.data.engine.taglib.servlet.taglib.definition;

import com.liferay.dynamic.data.mapping.model.DDMFormLayout;
import com.liferay.portal.kernel.util.HashMapBuilder;

import java.util.Map;

/**
 * @author Eudaldo Alonso
 */
public interface DataLayoutBuilderDefinition {

	public default boolean allowFieldSets() {
		return false;
	}

	public default boolean allowMultiplePages() {
		return false;
	}

	public default boolean allowNestedFields() {
		return true;
	}

	public default boolean allowRules() {
		return false;
	}

	public default boolean allowSuccessPage() {
		return false;
	}

	public default String[] getDisabledProperties() {
		return new String[0];
	}

	public default String[] getDisabledTabs() {
		return new String[0];
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public default String getPaginationMode() {
		return DDMFormLayout.WIZARD_MODE;
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public default Map<String, Object> getSuccessPageSettings() {
		return HashMapBuilder.<String, Object>put(
			"enabled", true
		).build();
	}

	public default String[] getUnimplementedProperties() {
		return new String[] {
			"allowGuestUsers", "fieldNamespace", "hideField", "indexType",
			"inputMask", "readOnly", "requireConfirmation", "validation",
			"visibilityExpression"
		};
	}

	public default String[] getVisibleProperties() {
		return new String[0];
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public default boolean isMultiPage() {
		return true;
	}

}