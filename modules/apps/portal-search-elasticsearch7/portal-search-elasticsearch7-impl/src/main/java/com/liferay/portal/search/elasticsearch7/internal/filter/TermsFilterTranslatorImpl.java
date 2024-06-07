/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.filter;

import com.liferay.portal.kernel.search.filter.TermsFilter;
import com.liferay.portal.search.elasticsearch7.internal.util.TermsUtil;

import org.elasticsearch.index.query.QueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = TermsFilterTranslator.class)
public class TermsFilterTranslatorImpl implements TermsFilterTranslator {

	@Override
	public QueryBuilder translate(TermsFilter termsFilter) {
		return TermsUtil.translateTerms(
			termsFilter.getField(), termsFilter.getValues());
	}

}