/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.frontend.data.set.filter;

import java.util.Map;

/**
 * @author Marco Leo
 */
public interface FDSFilter {

	public default String getEntityFieldType() {
		return null;
	}

	public String getId();

	public String getLabel();

	public default Map<String, Object> getPreloadedData() {
		return null;
	}

	public String getType();

}