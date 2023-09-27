/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.sharepoint.rest.repository.internal.search.kql;

import com.liferay.petra.string.StringPool;

/**
 * @author Adolfo Pérez
 */
public class NullKQLQuery implements KQLQuery {

	public static final KQLQuery INSTANCE = new NullKQLQuery();

	@Override
	public KQLQuery and(KQLQuery kqlQuery) {
		return kqlQuery;
	}

	@Override
	public KQLQuery not() {
		return this;
	}

	@Override
	public KQLQuery or(KQLQuery kqlQuery) {
		return kqlQuery;
	}

	@Override
	public String toString() {
		return StringPool.BLANK;
	}

}