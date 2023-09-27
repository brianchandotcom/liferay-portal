/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.search.kql;

/**
 * @author Adolfo Pérez
 */
public class OrKQLQuery implements KQLQuery {

	public OrKQLQuery(KQLQuery kqlQuery1, KQLQuery kqlQuery2) {
		_kqlQuery1 = kqlQuery1;
		_kqlQuery2 = kqlQuery2;
	}

	@Override
	public String toString() {
		return String.format(
			"(%s OR %s)", _kqlQuery1.toString(), _kqlQuery2.toString());
	}

	private final KQLQuery _kqlQuery1;
	private final KQLQuery _kqlQuery2;

}