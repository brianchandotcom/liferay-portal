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