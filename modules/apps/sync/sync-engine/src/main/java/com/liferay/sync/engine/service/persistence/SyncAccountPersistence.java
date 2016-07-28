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

import com.j256.ormlite.dao.GenericRawResults;
import com.j256.ormlite.dao.RawRowMapper;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.SelectArg;
import com.j256.ormlite.stmt.Where;

import com.liferay.sync.engine.model.SyncAccount;

import java.sql.SQLException;

import java.util.List;

/**
 * @author Shinn Lok
 */
public class SyncAccountPersistence
	extends BasePersistenceImpl<SyncAccount, Long> {

	public SyncAccountPersistence() throws SQLException {
		super(SyncAccount.class);
	}

	public List<Long> findByActive(boolean active) throws SQLException {
		QueryBuilder<SyncAccount, Long> queryBuilder = queryBuilder();

		queryBuilder.selectColumns("syncAccountId");

		Where<SyncAccount, Long> where = queryBuilder.where();

		where.eq("active", active);

		GenericRawResults<Long> genericRawResults = queryRaw(
			queryBuilder.prepareStatementString(),
			new RawRowMapper<Long>() {

				@Override
				public Long mapRow(
					String[] columnNames, String[] resultColumns) {

					return Long.valueOf(resultColumns[0]);
				}

			});

		return genericRawResults.getResults();
	}

	public SyncAccount fetchByUuid(String uuid) throws SQLException {
		QueryBuilder<SyncAccount, Long> queryBuilder = queryBuilder();

		queryBuilder.limit(1L);

		Where<SyncAccount, Long> where = queryBuilder.where();

		where.eq("uuid", uuid);

		where.and(1);

		return where.queryForFirst();
	}

	public SyncAccount fetchByFilePathName(String filePathName)
		throws SQLException {

		QueryBuilder<SyncAccount, Long> queryBuilder = queryBuilder();

		queryBuilder.limit(1L);

		Where<SyncAccount, Long> where = queryBuilder.where();

		where.eq("filePathName", new SelectArg(filePathName));

		where.and(1);

		return where.queryForFirst();
	}

}