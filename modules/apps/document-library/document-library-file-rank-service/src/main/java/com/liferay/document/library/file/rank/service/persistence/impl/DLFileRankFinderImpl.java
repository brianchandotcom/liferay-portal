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

package com.liferay.document.library.file.rank.service.persistence.impl;

import com.liferay.document.library.file.rank.model.DLFileRank;
import com.liferay.document.library.file.rank.model.impl.DLFileRankImpl;
import com.liferay.document.library.file.rank.service.persistence.DLFileRankFinder;
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author Alexander Chow
 */
@Component(service = DLFileRankFinder.class)
public class DLFileRankFinderImpl
	extends DLFileRankFinderBaseImpl implements DLFileRankFinder {

	public static final String FIND_BY_STALE_RANKS =
		DLFileRankFinder.class.getName() + ".findByStaleRanks";

	public static final String FIND_BY_FOLDER_ID =
		DLFileRankFinder.class.getName() + ".findByFolderId";

	@Override
	public List<Object[]> findByStaleRanks(int count) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_STALE_RANKS);

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
	public List<DLFileRank> findByFolderId(long folderId) {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_FOLDER_ID);

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

	@Reference
	private CustomSQL _customSQL;

}