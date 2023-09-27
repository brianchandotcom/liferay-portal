/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.portal.kernel.search.Query;

import org.osgi.service.component.annotations.Component;

/**
 * @author Joao Victor Alves
 */
@Component(
	property = "search.engine.impl=Solr", service = LuceneQueryConverter.class
)
public class SolrLuceneQueryConverter
	extends BaseQueryVisitor implements LuceneQueryConverter {

	@Override
	public org.apache.lucene.search.Query convert(Query query) {
		return query.accept(this);
	}

}