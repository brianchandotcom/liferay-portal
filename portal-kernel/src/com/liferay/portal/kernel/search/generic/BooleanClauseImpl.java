/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.search.generic;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.search.BooleanClause;
import com.liferay.portal.kernel.search.BooleanClauseOccur;

/**
 * @author Michael C. Han
 */
public class BooleanClauseImpl<T> implements BooleanClause<T> {

	public BooleanClauseImpl(T t, BooleanClauseOccur booleanClauseOccur) {
		_t = t;
		_booleanClauseOccur = booleanClauseOccur;
	}

	@Override
	public BooleanClauseOccur getBooleanClauseOccur() {
		return _booleanClauseOccur;
	}

	@Override
	public T getClause() {
		return _t;
	}

	@Override
	public String toString() {
		return StringBundler.concat("{", _booleanClauseOccur, "(", _t, ")}");
	}

	private final BooleanClauseOccur _booleanClauseOccur;
	private final T _t;

}