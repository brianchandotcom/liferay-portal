/**
 * Copyright (c) 2000-2011 Liferay, Inc. All rights reserved.
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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.LockMode;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.Lock;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.List;

/**
 * @author Shuyang Zhou
 */
public class LockFinderImpl
	extends BasePersistenceImpl<Lock> implements LockFinder {

	public Lock fetchByC_K(String className, String key, LockMode lockMode)
		throws SystemException {

		if (lockMode == null) {
			return lockPersistence.fetchByC_K(className, key);
		}

		if (Validator.isNull(className)) {
			throw new IllegalArgumentException("null className");
		}

		if ((Validator.isNull(key))) {
			throw new IllegalArgumentException("null key");
		}

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(_SQL_SELECT_BY_CK);

			q.setLockMode("lock", lockMode);

			QueryPos qPos = QueryPos.getInstance(q);

			if (className != null) {
				qPos.add(className);
			}

			if (key != null) {
				qPos.add(key);
			}

			List<Lock> list = q.list();

			Lock lock = null;

			if (!list.isEmpty()) {
				lock = list.get(0);
			}

			return lock;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	private static final String _SQL_SELECT_BY_CK =
		"SELECT lock FROM Lock lock WHERE lock.className = ? AND lock.key = ?";

	@BeanReference(type = LockPersistence.class)
	protected LockPersistence lockPersistence;

}