/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.search.kql;

/**
 * @author Adolfo Pérez
 */
public class RangeKQLQuery implements KQLQuery {

	public RangeKQLQuery(String lowerTerm, String upperTerm) {
		_lowerTerm = lowerTerm;
		_upperTerm = upperTerm;
	}

	@Override
	public String toString() {
		return String.format("(%s .. %s)", _lowerTerm, _upperTerm);
	}

	private final String _lowerTerm;
	private final String _upperTerm;

}