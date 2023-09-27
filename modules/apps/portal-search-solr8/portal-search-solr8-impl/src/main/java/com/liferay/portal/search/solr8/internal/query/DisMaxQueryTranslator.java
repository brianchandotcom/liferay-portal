/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.portal.kernel.search.generic.DisMaxQuery;
import com.liferay.portal.kernel.search.query.QueryVisitor;

import org.apache.lucene.search.Query;

/**
 * @author Michael C. Han
 */
public interface DisMaxQueryTranslator {

	public Query translate(
		DisMaxQuery disMaxQuery, QueryVisitor<Query> queryVisitor);

}