/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.filter;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.filter.TermsSetFilter;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermsSetQueryBuilder;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(service = TermsSetFilterTranslator.class)
public class TermsSetFilterTranslatorImpl implements TermsSetFilterTranslator {

	@Override
	public QueryBuilder translate(TermsSetFilter termsSetFilter) {
		TermsSetQueryBuilder termsSetQueryBuilder = new TermsSetQueryBuilder(
			termsSetFilter.getFieldName(),
			ListUtil.toList(termsSetFilter.getValues()));

		if (!Validator.isBlank(termsSetFilter.getMinimumShouldMatchField())) {
			termsSetQueryBuilder.setMinimumShouldMatchField(
				termsSetFilter.getMinimumShouldMatchField());
		}

		return termsSetQueryBuilder;
	}

}