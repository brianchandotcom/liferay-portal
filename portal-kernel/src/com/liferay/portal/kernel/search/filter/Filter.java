/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.filter;

/**
 * @author Michael C. Han
 */
public interface Filter {

	public <T> T accept(FilterVisitor<T> filterVisitor);

	public String getExecutionOption();

	public int getSortOrder();

	public Boolean isCached();

	public void setCached(Boolean cached);

	public void setExecutionOption(String executionOption);

}