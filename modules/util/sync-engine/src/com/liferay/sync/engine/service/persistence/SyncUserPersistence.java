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

import com.liferay.sync.engine.model.SyncUser;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Dennis Ju
 */
public class SyncUserPersistence extends BasePersistenceImpl<SyncUser, Long> {

	public SyncUserPersistence() throws SQLException {
		super(SyncUser.class);
	}

	public SyncUser fetchBySyncAccountId(long syncAccountId)
		throws SQLException {

		Map<String, Object> fieldValues = new HashMap<String, Object>();

		fieldValues.put("syncAccountId", syncAccountId);

		List<SyncUser> syncUsers = queryForFieldValues(fieldValues);

		if ((syncUsers == null) || syncUsers.isEmpty()) {
			return null;
		}

		return syncUsers.get(0);
	}

}