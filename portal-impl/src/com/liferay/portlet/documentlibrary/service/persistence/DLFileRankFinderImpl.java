/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.documentlibrary.model.DLFileRank;
import com.liferay.portlet.documentlibrary.model.impl.DLFileRankImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Alexander Chow
 */
public class DLFileRankFinderImpl
	extends BasePersistenceImpl<DLFileRank> implements DLFileRankFinder {

	public static final String FIND_BY_STALE_RANKS =
		DLFileRankFinder.class.getName() + ".findByStaleRanks";

	public static final String FIND_BY_FOLDER_ID =
		DLFileRankFinder.class.getName() + ".findByFolderId";

	@Override
	public List<Object[]> findByStaleRanks(int count) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_STALE_RANKS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("groupId", Type.LONG);
			q.addScalar("userId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(count);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<DLFileRank> findByFolderId(long folderId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_FOLDER_ID);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("DLFileRank", DLFileRankImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(folderId);

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