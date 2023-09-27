/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.search.query.ExistsQuery;
import com.liferay.portal.search.query.QueryVisitor;

/**
 * @author Michael C. Han
 */
public class ExistsQueryImpl extends BaseQueryImpl implements ExistsQuery {

	public ExistsQueryImpl(String field) {
		_field = field;
	}

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	@Override
	public String getField() {
		return _field;
	}

	@Override
	public int getSortOrder() {
		return 1;
	}

	@Override
	public String toString() {
		return StringBundler.concat("{(", _field, "), ", super.toString(), "}");
	}

	private static final long serialVersionUID = 1L;

	private final String _field;

}