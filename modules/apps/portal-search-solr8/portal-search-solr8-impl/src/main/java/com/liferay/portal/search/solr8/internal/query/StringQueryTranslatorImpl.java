/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.solr8.internal.query;

import com.liferay.portal.kernel.search.generic.StringQuery;

import java.util.Objects;

import org.apache.lucene.search.Query;

import org.osgi.service.component.annotations.Component;

/**
 * @author Michael C. Han
 */
@Component(service = StringQueryTranslator.class)
public class StringQueryTranslatorImpl implements StringQueryTranslator {

	@Override
	public Query translate(StringQuery stringQuery) {
		return new Query() {

			@Override
			public boolean equals(Object object) {
				String query = stringQuery.getQuery();

				return query.equals(object);
			}

			@Override
			public int hashCode() {
				return Objects.hashCode(stringQuery.getQuery());
			}

			@Override
			public String toString(String field) {
				return stringQuery.getQuery();
			}

		};
	}

}