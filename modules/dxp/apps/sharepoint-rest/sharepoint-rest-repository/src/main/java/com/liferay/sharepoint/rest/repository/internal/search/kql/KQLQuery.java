/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.sharepoint.rest.repository.internal.search.kql;

/**
 * @author Adolfo Pérez
 */
public interface KQLQuery {

	public static KQLQuery and(KQLQuery... kqlQueries) {
		KQLQuery finalKQLQuery = NullKQLQuery.INSTANCE;

		for (KQLQuery kqlQuery : kqlQueries) {
			finalKQLQuery = finalKQLQuery.and(kqlQuery);
		}

		return finalKQLQuery;
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