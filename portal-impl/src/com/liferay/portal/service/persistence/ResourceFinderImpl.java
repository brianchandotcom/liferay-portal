/**
 * Copyright (c) 2000-2012 Liferay, Inc. All rights reserved.
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

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Resource;
import com.liferay.portal.model.impl.ResourceImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Alexander Chow
 */
public class ResourceFinderImpl
	extends BasePersistenceImpl<Resource> implements ResourceFinder {

	public static final String FIND_BY_NAME =
		ResourceFinder.class.getName() + ".findByName";

	public static final String FIND_BY_C_P =
		ResourceFinder.class.getName() + ".findByC_P";

	public static final String FIND_BY_N_S =
		ResourceFinder.class.getName() + ".findByN_S";

	public List<Resource> findByName(String name) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NAME);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Resource_", ResourceImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(name);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Resource> findByC_P(long companyId, String primKey)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_P);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Resource_", ResourceImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(primKey);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	public List<Resource> findByN_S(String name, int scope)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_N_S);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("Resource_", ResourceImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(name);
			qPos.add(scope);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}