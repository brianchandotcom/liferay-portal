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

package com.liferay.portal.search.internal.query.field;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.search.analysis.KeywordTokenizer;
import com.liferay.portal.search.configuration.TitleFieldQueryBuilderConfiguration;
import com.liferay.portal.search.query.Queries;
import com.liferay.portal.search.query.Query;
import com.liferay.portal.search.query.field.FieldQueryBuilder;

import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.component.annotations.Reference;

/**
 * @author André de Oliveira
 * @author Rodrigo Paulino
 */
@Component(
	configurationPid = "com.liferay.portal.search.configuration.TitleFieldQueryBuilderConfiguration",
	service = TitleFieldQueryBuilder.class
)
public class TitleFieldQueryBuilder implements FieldQueryBuilder {

	@Override
	public Query build(String field, String keywords) {
		FullTextQueryBuilder fullTextQueryBuilder = new FullTextQueryBuilder(
			keywordTokenizer, queries);

		fullTextQueryBuilder.setAutocomplete(true);
		fullTextQueryBuilder.setExactMatchBoost(_exactMatchBoost);
		fullTextQueryBuilder.setMaxExpansions(_maxExpansions);

		return fullTextQueryBuilder.build(field, keywords);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		TitleFieldQueryBuilderConfiguration titleFieldQueryConfiguration =
			ConfigurableUtil.createConfigurable(
				TitleFieldQueryBuilderConfiguration.class, properties);

		_exactMatchBoost = titleFieldQueryConfiguration.exactMatchBoost();

		_maxExpansions = titleFieldQueryConfiguration.maxExpansions();
	}

	@Reference
	protected KeywordTokenizer keywordTokenizer;

	@Reference
	protected Queries queries;

	private volatile float _exactMatchBoost = 2.0F;
	private volatile int _maxExpansions = 300;

}