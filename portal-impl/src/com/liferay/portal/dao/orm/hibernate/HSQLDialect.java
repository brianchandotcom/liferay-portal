/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
 */

package com.liferay.portal.dao.orm.hibernate;

import org.hibernate.dialect.pagination.AbstractLimitHandler;
import org.hibernate.dialect.pagination.LimitHandler;
import org.hibernate.dialect.pagination.LimitHelper;
import org.hibernate.engine.spi.RowSelection;

/**
 * @author Shuyang Zhou
 */
public class HSQLDialect extends org.hibernate.dialect.HSQLDialect {

	@Override
	public String getForUpdateString() {
		return " for update";
	}

	@Override
	public LimitHandler getLimitHandler() {
		return _hsqlLimitHandler;
	}

	@Override
	public String getLimitString(String sql, boolean hasOffset) {
		if (hasOffset) {
			return sql.concat(" limit ?, ?");
		}

		return sql.concat(" limit ?");
	}

	private final HSQLLimitHandler _hsqlLimitHandler = new HSQLLimitHandler();

	private final class HSQLLimitHandler extends AbstractLimitHandler {

		@Override
		public boolean bindLimitParametersFirst() {
			return false;
		}

		@Override
		public String processSql(String sql, RowSelection selection) {
			return getLimitString(sql, LimitHelper.hasFirstRow(selection));
		}

		@Override
		public boolean supportsLimit() {
			return true;
		}

	}

}