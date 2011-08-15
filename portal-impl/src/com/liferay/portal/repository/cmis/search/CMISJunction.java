/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.repository.cmis.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mika Koivisto
 */
public abstract class CMISJunction implements CMISCriterion {

	public CMISJunction() {
		_criterions = new ArrayList<CMISCriterion>();
	}

	public void add(CMISCriterion criterion) {
		_criterions.add(criterion);
	}

	public List<CMISCriterion> getCriterions() {
		return _criterions;
	}

	public boolean isEmpty() {
		return _criterions.isEmpty();
	}

	public abstract String toQueryFragment();

	private List<CMISCriterion> _criterions;

}