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

package com.liferay.portal.search.internal.query;

import com.liferay.portal.kernel.search.Query;
import com.liferay.portal.kernel.search.query.FieldQueryFactory;
import com.liferay.portal.search.analysis.FieldQueryBuilderFactory;
import com.liferay.portal.search.analysis.QueryBuilder;
import com.liferay.portal.search.internal.analysis.TitleQueryBuilder;

import java.util.HashSet;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.osgi.service.component.annotations.ReferenceCardinality;
import org.osgi.service.component.annotations.ReferencePolicy;

/**
 * @author Michael C. Han
 */
@Component(immediate = true, service = FieldQueryFactory.class)
public class FieldQueryFactoryImpl implements FieldQueryFactory {

	@Override
	public Query createQuery(
		String fieldName, String keywords, boolean like,
		boolean splitKeywords) {

		QueryBuilder queryBuilder = getQueryBuilder(fieldName);

		return queryBuilder.build(fieldName, keywords);
	}

	@Reference(
		cardinality = ReferenceCardinality.MULTIPLE,
		policy = ReferencePolicy.DYNAMIC
	)
	protected void addFieldQueryBuilderFactory(
		FieldQueryBuilderFactory fieldQueryBuilderFactory) {

		_fieldQueryBuilderFactories.add(fieldQueryBuilderFactory);
	}

	protected QueryBuilder getDefaultQueryBuilder() {
		return titleQueryBuilder;
	}

	protected QueryBuilder getQueryBuilder(String fieldName) {
		for (FieldQueryBuilderFactory fieldQueryBuilderFactory :
				_fieldQueryBuilderFactories) {

			QueryBuilder queryBuilder =
				fieldQueryBuilderFactory.getQueryBuilder(fieldName);

			if (queryBuilder != null) {
				return queryBuilder;
			}
		}

		return getDefaultQueryBuilder();
	}

	protected void removeFieldQueryBuilderFactory(
		FieldQueryBuilderFactory fieldQueryBuilderFactory) {

		_fieldQueryBuilderFactories.remove(fieldQueryBuilderFactory);
	}

	@Reference
	protected TitleQueryBuilder titleQueryBuilder;

	private final HashSet<FieldQueryBuilderFactory>
		_fieldQueryBuilderFactories = new HashSet<>();

}