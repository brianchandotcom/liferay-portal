/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query.field;

import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.analysis.KeywordTokenizer;
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
	property = {
		"exact.match.boost=2.0", "proximity.slop=50",
		"query.builder.type=description"
	},
	service = FieldQueryBuilder.class
)
public class DescriptionFieldQueryBuilder implements FieldQueryBuilder {

	@Override
	public Query build(String field, String keywords) {
		FullTextQueryBuilder fullTextQueryBuilder = new FullTextQueryBuilder(
			keywordTokenizer, queries);

		fullTextQueryBuilder.setExactMatchBoost(_exactMatchBoost);
		fullTextQueryBuilder.setProximitySlop(_proximitySlop);

		return fullTextQueryBuilder.build(field, keywords);
	}

	@Activate
	@Modified
	protected void activate(Map<String, Object> properties) {
		_exactMatchBoost = GetterUtil.getFloat(
			properties.get("exact.match.boost"), _exactMatchBoost);

		_proximitySlop = GetterUtil.getInteger(
			properties.get("proximity.slop"), _proximitySlop);
	}

	@Reference
	protected KeywordTokenizer keywordTokenizer;

	@Reference
	protected Queries queries;

	private volatile float _exactMatchBoost = 2.0F;
	private volatile int _proximitySlop = 50;

}