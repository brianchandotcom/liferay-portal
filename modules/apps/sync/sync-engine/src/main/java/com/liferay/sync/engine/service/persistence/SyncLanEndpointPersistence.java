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
import com.j256.ormlite.stmt.DeleteBuilder;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.stmt.Where;

import com.liferay.sync.engine.model.SyncLanEndpoint;

import java.sql.SQLException;

import java.util.List;

/**
 * @author Dennis Ju
 */
public class SyncLanEndpointPersistence
	extends BasePersistenceImpl<SyncLanEndpoint, Long> {

	public SyncLanEndpointPersistence() throws SQLException {
		super(SyncLanEndpoint.class);
	}

	public List<SyncLanEndpoint> findBySyncLanClientUuid(
			String syncLanClientUuid)
		throws SQLException {

		return queryForEq("syncLanClientUuid", syncLanClientUuid);
	}

	public List<String> findByL_R(String lanServerUuid, long repositoryId)
		throws SQLException {

		QueryBuilder<SyncLanEndpoint, Long> queryBuilder = queryBuilder();

		queryBuilder.selectColumns("syncLanClientUuid");

		Where<SyncLanEndpoint, Long> where = queryBuilder.where();

		where.eq("lanServerUuid", lanServerUuid);
		where.eq("repositoryId", repositoryId);

		where.and(2);

		GenericRawResults<String> genericRawResults = queryRaw(
			queryBuilder.prepareStatementString(),
			new RawRowMapper<String>() {

				@Override
				public String mapRow(
					String[] columnNames, String[] resultColumns) {

					return String.valueOf(resultColumns[0]);
				}

			});

		return genericRawResults.getResults();
	}

	public void deleteBySyncLanClientUuid(String syncLanClientUuid)
		throws SQLException {

		DeleteBuilder<SyncLanEndpoint, Long> deleteBuilder = deleteBuilder();

		Where<SyncLanEndpoint, Long> where = deleteBuilder.where();

		where.eq("syncLanClientUuid", syncLanClientUuid);

		deleteBuilder.delete();
	}

}