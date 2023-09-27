/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.filter;

import com.liferay.petra.string.CharPool;
import com.liferay.petra.string.StringPool;
import com.liferay.portal.kernel.search.filter.TermFilter;
import com.liferay.portal.kernel.util.StringUtil;

import org.apache.lucene.index.Term;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.TermQuery;
import org.apache.solr.client.solrj.util.ClientUtils;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = TermFilterTranslator.class)
public class TermFilterTranslatorImpl implements TermFilterTranslator {

	@Override
	public Query translate(TermFilter termFilter) {
		String value = termFilter.getValue();

		if (value.isEmpty()) {
			value = StringPool.DOUBLE_APOSTROPHE;
		}

		Term term = new Term(
			_escape(termFilter.getField()),
			ClientUtils.escapeQueryChars(value));

		return new TermQuery(term);
	}

	private String _escape(String value) {
		return StringUtil.replace(
			value, CharPool.SPACE, StringPool.BACK_SLASH + StringPool.SPACE);
	}

}