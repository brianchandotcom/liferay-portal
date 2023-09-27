/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.search.internal.query;

import com.liferay.portal.kernel.util.ArrayUtil;
import com.liferay.portal.search.query.IdsQuery;
import com.liferay.portal.search.query.QueryVisitor;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Michael C. Han
 */
public class IdsQueryImpl extends BaseQueryImpl implements IdsQuery {

	@Override
	public <T> T accept(QueryVisitor<T> queryVisitor) {
		return queryVisitor.visit(this);
	}

	@Override
	public void addIds(String... ids) {
		if (ArrayUtil.isEmpty(ids)) {
			return;
		}

		Collections.addAll(_ids, ids);
	}

	@Override
	public void addTypes(String... types) {
		if (ArrayUtil.isEmpty(types)) {
			return;
		}

		Collections.addAll(_types, types);
	}

	@Override
	public Set<String> getIds() {
		return Collections.unmodifiableSet(_ids);
	}

	@Override
	public Set<String> getTypes() {
		return Collections.unmodifiableSet(_types);
	}

	private static final long serialVersionUID = 1L;

	private final Set<String> _ids = new HashSet<>();
	private final Set<String> _types = new HashSet<>();

}