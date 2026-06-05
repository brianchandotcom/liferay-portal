/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.portal.kernel.search.Query;

/**
 * @author André de Oliveira
 * @author Miguel Angelo Caldas Gallindo
 */
public class SolrQueryVisitor extends BaseQueryVisitor {

	public static final SolrQueryVisitor INSTANCE = new SolrQueryVisitor();

	public String translate(Query query) {
		org.apache.lucene.search.Query luceneQuery = query.accept(this);

		if (luceneQuery != null) {
			return luceneQuery.toString();
		}

		return null;
	}

	private SolrQueryVisitor() {
	}

}