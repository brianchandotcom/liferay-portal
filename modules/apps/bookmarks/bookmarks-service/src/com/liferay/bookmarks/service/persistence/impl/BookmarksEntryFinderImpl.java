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

package com.liferay.bookmarks.service.persistence.impl;

import com.liferay.bookmarks.model.BookmarksEntry;
import com.liferay.bookmarks.model.impl.BookmarksEntryImpl;
import com.liferay.bookmarks.service.persistence.BookmarksEntryFinder;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.util.PortalUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class BookmarksEntryFinderImpl
	extends BasePersistenceImpl<BookmarksEntry>
	implements BookmarksEntryFinder {

	public static final String FIND_BY_NO_ASSETS =
		BookmarksEntryFinder.class.getName() + ".findByNoAssets";

	@Override
	public List<BookmarksEntry> findByNoAssets() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("BookmarksEntry", BookmarksEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(PortalUtil.getClassNameId(BookmarksEntry.class));

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