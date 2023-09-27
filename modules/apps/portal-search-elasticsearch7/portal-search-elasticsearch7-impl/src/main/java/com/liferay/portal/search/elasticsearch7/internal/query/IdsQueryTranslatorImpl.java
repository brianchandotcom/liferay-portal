/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

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