/**
 * SPDX-FileCopyrightText: (c) 2026 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.kernel.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.model.BaseModel;

/**
 * @author Shuyang Zhou
 */
public abstract class BasePersistenceFinder<T extends BaseModel<T>> {

	public String buildNoSuchKeyMessage(String prefix, Object[] values) {
		StringBundler sb = new StringBundler((finderColumns.length * 3) + 1);

		sb.append(prefix);

		for (int i = 0; i < finderColumns.length; i++) {
			if (i > 0) {
				sb.append(", ");
			}

			sb.append(finderColumns[i].getKeyFragment());
			sb.append(values[i]);
		}

		sb.append("}");

		return sb.toString();
	}

	@SafeVarargs
	protected BasePersistenceFinder(
		BasePersistenceImpl<T> basePersistenceImpl, String sqlSelectWhere,
		FinderColumn<T>... finderColumns) {

		this.basePersistenceImpl = basePersistenceImpl;
		this.sqlSelectWhere = sqlSelectWhere;
		this.finderColumns = finderColumns;
	}

	protected void bindQueryParams(QueryPos queryPos, Object[] values) {
		for (int i = 0; i < finderColumns.length; i++) {
			if (finderColumns[i].shouldBind(values[i])) {
				queryPos.add(values[i]);
			}
		}
	}

	protected Object[] buildFinderArgs(Object[] values) {
		Object[] finderArgs = new Object[finderColumns.length];

		for (int i = 0; i < finderColumns.length; i++) {
			finderArgs[i] = finderColumns[i].toFinderArg(values[i]);
		}

		return finderArgs;
	}

	protected String buildSQLWhere(String sqlWhere, Object[] values) {
		StringBundler sb = new StringBundler(finderColumns.length + 1);

		sb.append(sqlWhere);

		for (int i = 0; i < finderColumns.length; i++) {
			sb.append(finderColumns[i].getSqlFragment(values[i]));
		}

		return sb.toString();
	}

	protected boolean matchesAll(T entity, Object[] values) {
		for (int i = 0; i < finderColumns.length; i++) {
			if (!finderColumns[i].matches(entity, values[i])) {
				return false;
			}
		}

		return true;
	}

	protected void normalizeValues(Object[] values) {
		for (int i = 0; i < finderColumns.length; i++) {
			values[i] = finderColumns[i].normalizeValue(values[i]);
		}
	}

	protected final BasePersistenceImpl<T> basePersistenceImpl;
	protected final FinderColumn<T>[] finderColumns;
	protected final String sqlSelectWhere;

}