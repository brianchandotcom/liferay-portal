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

package com.liferay.documentum.repository.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mika Koivisto
 */
public abstract class DQLJunction implements DQLCriterion {

	public DQLJunction() {
		_dqlCriterions = new ArrayList<>();
	}

	public void add(DQLCriterion dqlCriterion) {
		_dqlCriterions.add(dqlCriterion);
	}

	public boolean isEmpty() {
		return _dqlCriterions.isEmpty();
	}

	public List<DQLCriterion> list() {
		return _dqlCriterions;
	}

	@Override
	public abstract String toQueryFragment();

	private final List<DQLCriterion> _dqlCriterions;

}