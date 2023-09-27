/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.elasticsearch7.internal.query;

import com.liferay.portal.search.query.WildcardQuery;

import org.elasticsearch.index.query.QueryBuilder;

/**
 * @author Miguel Angelo Caldas Gallindo
 */
public interface WildcardQueryTranslator {

	public QueryBuilder translate(WildcardQuery wildcardQuery);

}