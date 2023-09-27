/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.search.kql;

/**
 * @author Adolfo Pérez
 */
public interface KQLQuery {

	public static KQLQuery and(KQLQuery... kqlQueries) {
		KQLQuery kqlQuery = NullKQLQuery.INSTANCE;

		for (KQLQuery curKQLQuery : kqlQueries) {
			kqlQuery = kqlQuery.and(curKQLQuery);
		}

		return kqlQuery;
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