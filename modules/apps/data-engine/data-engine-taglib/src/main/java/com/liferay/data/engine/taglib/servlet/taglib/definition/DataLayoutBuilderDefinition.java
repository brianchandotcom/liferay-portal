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

/**
 * @author Eudaldo Alonso
 */
public interface DataLayoutBuilderDefinition {

	public default boolean allowFieldSets() {
		return false;
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

	public default String getPaginationMode() {
		return DDMFormLayout.WIZARD_MODE;
	}

	public default String[] getUnimplementedProperties() {
		return new String[] {
			"fieldNamespace", "indexType", "localizable", "readOnly", "type",
			"validation", "visibilityExpression"
		};
	}

	/**
	 * @deprecated As of Athanasius (7.3.x), with no direct replacement
	 */
	@Deprecated
	public default boolean isMultiPage() {
		return true;
	}

}