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

import com.liferay.portal.search.query.IdsQuery;

import java.util.Set;

import org.elasticsearch.index.query.IdsQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = IdsQueryTranslator.class)
public class IdsQueryTranslatorImpl implements IdsQueryTranslator {

	@Override
	public QueryBuilder translate(IdsQuery idsQuery) {
		IdsQueryBuilder idsQueryBuilder = QueryBuilders.idsQuery();

		if (idsQuery.getBoost() != null) {
			idsQueryBuilder.boost(idsQuery.getBoost());
		}

		Set<String> ids = idsQuery.getIds();

		idsQueryBuilder.addIds(ids.toArray(new String[0]));

		idsQueryBuilder.queryName(idsQuery.getQueryName());

		Set<String> types = idsQuery.getTypes();

		idsQueryBuilder.types(types.toArray(new String[0]));

		return idsQueryBuilder;
	}

}