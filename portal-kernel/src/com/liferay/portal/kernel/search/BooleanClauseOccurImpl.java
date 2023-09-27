/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}

		if (!(object instanceof BooleanClauseOccur)) {
			return false;
		}

		BooleanClauseOccur booleanClauseOccur = (BooleanClauseOccur)object;

		if (_name.equals(booleanClauseOccur.getName())) {
			return true;
		}

		return false;
	}

	@Override
	public String getName() {
		return _name;
	}

	@Override
	public int hashCode() {
		return _name.hashCode();
	}

	@Override
	public String toString() {
		return _name;
	}

	private final String _name;

}