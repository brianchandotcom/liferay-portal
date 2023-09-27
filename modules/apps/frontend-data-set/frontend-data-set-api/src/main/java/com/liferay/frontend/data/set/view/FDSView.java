/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

	public default boolean isDefault() {
		return false;
	}

}