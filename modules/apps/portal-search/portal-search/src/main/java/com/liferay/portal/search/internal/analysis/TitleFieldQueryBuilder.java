/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.analysis;

import com.liferay.portal.configuration.metatype.bnd.util.ConfigurableUtil;
import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.analysis.KeywordTokenizer;
import com.liferay.portal.search.configuration.TitleFieldQueryBuilderConfiguration;

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
			keywordTokenizer);

		fullTextQueryBuilder.setAutocomplete(true);
		fullTextQueryBuilder.setExactMatchBoost(_exactMatchBoost);
		fullTextQueryBuilder.setMaxExpansions(_maxExpansions);

		return fullTextQueryBuilder.build(field, keywords);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		TitleFieldQueryBuilderConfiguration
			titleFieldQueryBuilderConfiguration =
				ConfigurableUtil.createConfigurable(
					TitleFieldQueryBuilderConfiguration.class, properties);

		_exactMatchBoost =
			titleFieldQueryBuilderConfiguration.exactMatchBoost();
		_maxExpansions = titleFieldQueryBuilderConfiguration.maxExpansions();
	}

	@Reference
	protected KeywordTokenizer keywordTokenizer;

	private volatile float _exactMatchBoost = 2.0F;
	private volatile int _maxExpansions = 300;

}