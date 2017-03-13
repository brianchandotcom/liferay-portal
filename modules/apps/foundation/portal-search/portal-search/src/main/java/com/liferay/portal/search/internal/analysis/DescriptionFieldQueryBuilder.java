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

package com.liferay.portal.search.internal.analysis;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.search.analysis.FieldQueryBuilder;
import com.liferay.portal.search.analysis.KeywordTokenizer;

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
	immediate = true, property = {"exact.match.boost=2.0", "proximity.slop=50"},
	service = DescriptionFieldQueryBuilder.class
)
public class DescriptionFieldQueryBuilder implements FieldQueryBuilder {

	@Override
	public Query build(String field, String keywords) {
		FullTextQueryBuilder fullTextQueryBuilder = new FullTextQueryBuilder(
			keywordTokenizer);

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

	private volatile float _exactMatchBoost = 2.0F;
	private volatile int _proximitySlop = 50;

}