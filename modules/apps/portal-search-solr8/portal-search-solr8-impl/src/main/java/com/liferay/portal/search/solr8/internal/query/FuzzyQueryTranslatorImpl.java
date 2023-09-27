/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.portal.kernel.search.generic.FuzzyQuery;
import com.liferay.portal.kernel.util.GetterUtil;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = FuzzyQueryTranslator.class)
public class FuzzyQueryTranslatorImpl implements FuzzyQueryTranslator {

	@Override
	public Query translate(FuzzyQuery fuzzyQuery) {
		Term term = new Term(fuzzyQuery.getField(), fuzzyQuery.getValue());

		int maxEdits = GetterUtil.getInteger(fuzzyQuery.getMaxEdits());
		int prefixLength = GetterUtil.getInteger(fuzzyQuery.getPrefixLength());
		int maxExpansions = GetterUtil.getInteger(
			fuzzyQuery.getMaxExpansions(), 50);

		if (!fuzzyQuery.isDefaultBoost()) {
			fuzzyQuery.setBoost(fuzzyQuery.getBoost());
		}

		return new org.apache.lucene.search.FuzzyQuery(
			term, maxEdits, prefixLength, maxExpansions, false);
	}

}