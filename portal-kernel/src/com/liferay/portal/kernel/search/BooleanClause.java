/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search;

import com.liferay.petra.string.StringBundler;

import java.io.Serializable;

/**
 * @author Brian Wing Shun Chan
 * @author Michael C. Han
 */
public class BooleanClause<T> implements Serializable {

	public BooleanClause(T clause, BooleanClauseOccur booleanClauseOccur) {
		_clause = clause;
		_booleanClauseOccur = booleanClauseOccur;
	}

	public BooleanClauseOccur getBooleanClauseOccur() {
		return _booleanClauseOccur;
	}

	public T getClause() {
		return _clause;
	}

	@Override
	public String toString() {
		return StringBundler.concat(
			"{", _booleanClauseOccur, "(", _clause, ")}");
	}

	private final BooleanClauseOccur _booleanClauseOccur;
	private final T _clause;

}