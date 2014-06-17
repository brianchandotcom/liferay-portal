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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.persistence.LockFinder;
import com.liferay.portal.service.persistence.LockUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class LockFinderImpl
	extends BasePersistenceImpl<Lock> implements LockFinder {

	public static final String FIND_BY_C_K =
		LockFinder.class.getName() + ".findByC_K";

	@Override
	public Lock fetchByC_K(String className, String key, LockMode lockMode) {
		if (lockMode == null) {
			return LockUtil.fetchByC_K(className, key);
		}

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_K);

			Query q = session.createQuery(sql);

			q.setLockMode("lock", lockMode);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(className);
			qPos.add(key);

			List<Lock> locks = q.list();

			if (!locks.isEmpty()) {
				return locks.get(0);
			}

			return null;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

}