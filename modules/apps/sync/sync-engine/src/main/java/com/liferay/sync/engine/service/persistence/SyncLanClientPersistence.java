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

import com.liferay.sync.engine.model.SyncLanClient;

import java.sql.SQLException;

import java.util.List;

/**
 * @author Dennis Ju
 */
public class SyncLanClientPersistence
	extends BasePersistenceImpl<SyncLanClient, String> {

	public SyncLanClientPersistence() throws SQLException {
		super(SyncLanClient.class);
	}

	public List<SyncLanClient> findByModifiedTime(long modifiedTime)
		throws SQLException {

		QueryBuilder<SyncLanClient, String> queryBuilder = queryBuilder();

		Where<SyncLanClient, String> where = queryBuilder.where();

		where.lt("modifiedTime", modifiedTime);

		return queryBuilder.query();
	}

}