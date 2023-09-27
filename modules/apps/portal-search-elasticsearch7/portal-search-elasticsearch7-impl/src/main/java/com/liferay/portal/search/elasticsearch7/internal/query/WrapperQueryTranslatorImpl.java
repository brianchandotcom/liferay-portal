/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.query.WrapperQuery;

import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;

import org.osgi.service.component.annotations.Component;

/**
 * @author Adam Brandizzi
 */
@Component(service = WrapperQueryTranslator.class)
public class WrapperQueryTranslatorImpl implements WrapperQueryTranslator {

	@Override
	public QueryBuilder translate(WrapperQuery wrapperQuery) {
		return QueryBuilders.wrapperQuery(wrapperQuery.getSource());
	}

}