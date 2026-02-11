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
public abstract class BooleanQuery extends Query {

	public abstract BooleanQuery addFilterQueryClauses(Query... clauses);

	public abstract BooleanQuery addMustNotQueryClauses(Query... clauses);

	public abstract BooleanQuery addMustQueryClauses(Query... clauses);

	public abstract BooleanQuery addShouldQueryClauses(Query... clauses);

	public abstract Boolean getAdjustPureNegative();

	public abstract List<Query> getFilterQueryClauses();

	public abstract Integer getMinimumShouldMatch();

	public abstract List<Query> getMustNotQueryClauses();

	public abstract List<Query> getMustQueryClauses();

	public abstract List<Query> getShouldQueryClauses();

	public abstract boolean hasClauses();

	public abstract void setAdjustPureNegative(Boolean adjustPureNegative);

	public abstract void setMinimumShouldMatch(Integer minimumShouldMatch);

}