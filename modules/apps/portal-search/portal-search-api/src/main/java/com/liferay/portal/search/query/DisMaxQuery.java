/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.query;

import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * @author Michael C. Han
 */
@ProviderType
public abstract class DisMaxQuery extends Query {

	public abstract void addQuery(Query query);

	public abstract Set<Query> getQueries();

	public abstract Float getTieBreaker();

	public abstract void setTieBreaker(Float tieBreaker);

}