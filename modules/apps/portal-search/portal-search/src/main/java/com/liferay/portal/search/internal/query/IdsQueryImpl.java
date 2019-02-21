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

	public void addIds(String... ids) {
		if (ArrayUtil.isEmpty(ids)) {
			return;
		}

		Collections.addAll(_ids, ids);
	}

	public void addTypes(String... types) {
		if (ArrayUtil.isEmpty(types)) {
			return;
		}

		Collections.addAll(_types, types);
	}

	public Set<String> getIds() {
		return Collections.unmodifiableSet(_ids);
	}

	public Set<String> getTypes() {
		return Collections.unmodifiableSet(_types);
	}

	private static final long serialVersionUID = 1L;

	private final Set<String> _ids = new HashSet<>();
	private final Set<String> _types = new HashSet<>();

}