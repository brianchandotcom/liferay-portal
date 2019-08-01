/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.search.elasticsearch6.internal.query;

import com.liferay.portal.kernel.util.ListUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.search.elasticsearch6.internal.script.ScriptTranslator;
import com.liferay.portal.search.query.TermsSetQuery;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.TermsSetQueryBuilder;
import org.elasticsearch.script.Script;

import org.osgi.service.component.annotations.Component;

/**
 * @author Marco Leo
 */
@Component(service = TermsSetQueryTranslator.class)
public class TermsSetQueryTranslatorImpl implements TermsSetQueryTranslator {

	@Override
	public QueryBuilder translate(TermsSetQuery termsSetQuery) {
		TermsSetQueryBuilder termsSetQueryBuilder = new TermsSetQueryBuilder(
			termsSetQuery.getFieldName(),
			ListUtil.toList(termsSetQuery.getValues()));

		if (!Validator.isBlank(termsSetQuery.getMinimumShouldMatchField())) {
			termsSetQueryBuilder.setMinimumShouldMatchField(
				termsSetQuery.getMinimumShouldMatchField());
		}

		if (termsSetQuery.getMinimumShouldMatchScript() != null) {
			Script script = _scriptTranslator.translate(
				termsSetQuery.getMinimumShouldMatchScript());

			termsSetQueryBuilder.setMinimumShouldMatchScript(script);
		}

		return termsSetQueryBuilder;
	}

	private final ScriptTranslator _scriptTranslator = new ScriptTranslator();

}