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

package com.liferay.frontend.data.set.view;

import com.liferay.frontend.data.set.view.table.FDSTableSchema;

import java.util.Locale;

/**
 * @author Marco Leo
 * @author Marko Cikos
 */
public interface FDSView {

	public String getContentRenderer();

	public default String getContentRendererModuleURL() {
		return null;
	}

	public default FDSTableSchema getFDSTableSchema(Locale locale) {
		return null;
	}

	public String getLabel();

	public String getName();

	public String getThumbnail();

}