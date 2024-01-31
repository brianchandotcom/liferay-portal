/**
 * SPDX-FileCopyrightText: (c) 2024 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.opensearch2.internal.query;

import org.opensearch.client.opensearch._types.FieldValue;
import org.opensearch.client.opensearch._types.query_dsl.Query;
import org.opensearch.client.opensearch._types.query_dsl.QueryBuilders;

/**
 * @author André de Oliveira
 */
public class QueryFactories {

	public static final QueryFactory MATCH = new QueryFactory() {

		@Override
		public Query create(String field, String text) {
			return new Query(
				QueryBuilders.match(
				).field(
					field
				).query(
					FieldValue.of(text)
				).build());
		}

	};

	public static final QueryFactory MATCH_PHRASE_PREFIX = new QueryFactory() {

		@Override
		public Query create(String field, String text) {
			return new Query(
				QueryBuilders.matchPhrasePrefix(
				).field(
					field
				).query(
					text
				).build());
		}

	};

}