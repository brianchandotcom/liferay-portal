/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.search.kql;

/**
 * @author Adolfo Pérez
 */
public class NotKQLQuery implements KQLQuery {

	public NotKQLQuery(KQLQuery kqlQuery) {
		_kqlQuery = kqlQuery;
	}

	@Override
	public String toString() {
		return String.format("(NOT %s)", _kqlQuery.toString());
	}

	private final KQLQuery _kqlQuery;

}