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