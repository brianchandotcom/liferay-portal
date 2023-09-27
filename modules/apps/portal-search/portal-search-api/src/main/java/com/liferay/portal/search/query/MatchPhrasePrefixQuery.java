/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public interface MatchPhrasePrefixQuery extends Query {

	public String getAnalyzer();

	public String getField();

	public Integer getMaxExpansions();

	public Integer getSlop();

	public Object getValue();

	public void setAnalyzer(String analyzer);

	public void setMaxExpansions(Integer maxExpansions);

	public void setSlop(Integer slop);

}