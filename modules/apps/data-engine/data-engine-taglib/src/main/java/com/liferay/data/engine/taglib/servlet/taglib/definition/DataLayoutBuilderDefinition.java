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
			"allowGuestUsers", "fieldNamespace", "indexType", "readOnly",
			"requireConfirmation", "validation", "visibilityExpression"
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