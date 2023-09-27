/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.filter;

import com.liferay.portal.kernel.search.filter.PrefixFilter;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.PrefixQuery;
import org.apache.lucene.search.Query;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = PrefixFilterTranslator.class)
public class PrefixFilterTranslatorImpl implements PrefixFilterTranslator {

	@Override
	public Query translate(PrefixFilter prefixFilter) {
		Term term = new Term(prefixFilter.getField(), prefixFilter.getPrefix());

		return new PrefixQuery(term);
	}

}