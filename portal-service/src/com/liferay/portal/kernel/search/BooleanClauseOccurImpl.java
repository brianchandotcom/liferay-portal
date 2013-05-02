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

package com.liferay.portal.kernel.search;

/**
 * @author Brian Wing Shun Chan
 */
public class BooleanClauseOccurImpl implements BooleanClauseOccur {

	public BooleanClauseOccurImpl(String name) {
		_name = name;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}

		if (!(obj instanceof BooleanClauseOccur)) {
			return false;
		}

		BooleanClauseOccur booleanClauseOccur = (BooleanClauseOccur)obj;

		String name = booleanClauseOccur.getName();

		if (_name.equals(name)) {
			return true;
		}
		else {
			return false;
		}
	}

	public String getName() {
		return _name;
	}

	private String _name;

}