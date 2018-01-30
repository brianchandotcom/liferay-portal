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

package com.liferay.sharepoint.rest.repository.internal.search.kql;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * @author Adolfo Pérez
 */
public interface KQLQuery {

	public static KQLQuery and(KQLQuery... kqlQueries) {
		Stream<KQLQuery> queryStream = Arrays.stream(kqlQueries);

		return queryStream.reduce(
			NullKQLQuery.INSTANCE,
			(kqlQuery1, kqlQuery2) -> kqlQuery1.and(kqlQuery2));
	}

	public static KQLQuery eq(String field, String value) {
		return new StringKQLQuery(field, value);
	}

	public static KQLQuery range(String lower, String upper) {
		return new RangeKQLQuery(lower, upper);
	}

	public default KQLQuery and(KQLQuery kqlQuery) {
		if (kqlQuery == NullKQLQuery.INSTANCE) {
			return this;
		}

		return new AndKQLQuery(this, kqlQuery);
	}

	public default KQLQuery not() {
		return new NotKQLQuery(this);
	}

	public default KQLQuery or(KQLQuery kqlQuery) {
		if (kqlQuery == NullKQLQuery.INSTANCE) {
			return this;
		}

		return new OrKQLQuery(this, kqlQuery);
	}

	public String toString();

}