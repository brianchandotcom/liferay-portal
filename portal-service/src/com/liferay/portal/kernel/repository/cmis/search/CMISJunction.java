/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.kernel.repository.cmis.search;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Mika Koivisto
 */
public abstract class CMISJunction implements CMISCriterion {

	public CMISJunction() {
		_cmisCriterions = new ArrayList<CMISCriterion>();
	}

	public void add(CMISCriterion cmisCriterion) {
		_cmisCriterions.add(cmisCriterion);
	}

	public boolean isEmpty() {
		return _cmisCriterions.isEmpty();
	}

	public List<CMISCriterion> list() {
		return _cmisCriterions;
	}

	@Override
	public abstract String toQueryFragment();

	private List<CMISCriterion> _cmisCriterions;

}