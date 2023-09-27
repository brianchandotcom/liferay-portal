/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import java.util.List;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 * @author Hugo Huijser
 */
@ProviderType
public interface BooleanQuery extends Query {

	public BooleanQuery addFilterQueryClauses(Query... clauses);

	public BooleanQuery addMustNotQueryClauses(Query... clauses);

	public BooleanQuery addMustQueryClauses(Query... clauses);

	public BooleanQuery addShouldQueryClauses(Query... clauses);

	public Boolean getAdjustPureNegative();

	public List<Query> getFilterQueryClauses();

	public Integer getMinimumShouldMatch();

	public List<Query> getMustNotQueryClauses();

	public List<Query> getMustQueryClauses();

	public List<Query> getShouldQueryClauses();

	public boolean hasClauses();

	public void setAdjustPureNegative(Boolean adjustPureNegative);

	public void setMinimumShouldMatch(Integer minimumShouldMatch);

	@Override
	public String toString();

}