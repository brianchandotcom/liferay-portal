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

package com.liferay.frontend.taglib.clay.data.set;

import com.liferay.frontend.taglib.clay.data.set.view.table.ClayTableSchema;
import com.liferay.frontend.taglib.clay.internal.data.set.view.table.ClayTableSchemaBuilderImpl;

import java.util.Locale;

/**
 * @author Marco Leo
 */
public interface ClayDataSetDisplayView {

	/**
	 * @deprecated As of Cavanaugh (7.4.x), replaced by {@link #getClayTableSchema(Locale)}
	 */
	@Deprecated
	public default ClayTableSchema getClayTableSchema() {
		ClayTableSchemaBuilderImpl clayTableSchemaBuilderImpl =
			new ClayTableSchemaBuilderImpl();

		return clayTableSchemaBuilderImpl.build();
	}

	public default ClayTableSchema getClayTableSchema(Locale locale) {
		return getClayTableSchema();
	}

	public String getContentRenderer();

	public default String getContentRendererModuleURL() {
		return null;
	}

	public String getLabel();

	public String getName();

	public String getThumbnail();

}