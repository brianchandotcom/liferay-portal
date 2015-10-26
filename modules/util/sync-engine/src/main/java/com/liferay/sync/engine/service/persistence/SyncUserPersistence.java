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

package com.liferay.sync.engine.service.persistence;

import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import com.liferay.sync.engine.model.SyncUser;

import java.sql.SQLException;

/**
 * @author Dennis Ju
 */
public class SyncUserPersistence extends BasePersistenceImpl<SyncUser, Long> {

	public SyncUserPersistence() throws SQLException {
		super(SyncUser.class);
	}

	public SyncUser fetchBySyncAccountId(long syncAccountId)
		throws SQLException {

		QueryBuilder<SyncUser, Long> queryBuilder = queryBuilder();

		queryBuilder.limit(1L);

		Where<SyncUser, Long> where = queryBuilder.where();

		where.eq("syncAccountId", syncAccountId);

		return where.queryForFirst();
	}

}