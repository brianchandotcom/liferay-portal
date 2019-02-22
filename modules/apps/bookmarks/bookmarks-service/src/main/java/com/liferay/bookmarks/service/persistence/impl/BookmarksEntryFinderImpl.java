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
import com.liferay.portal.dao.orm.custom.sql.CustomSQL;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.Portal;

import java.util.List;

import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;

/**
 * @author     Brian Wing Shun Chan
 * @deprecated As of Judson (7.1.x), with no direct replacement
 */
@Component(service = BookmarksEntryFinder.class)
@Deprecated
public class BookmarksEntryFinderImpl
	extends BookmarksEntryFinderBaseImpl implements BookmarksEntryFinder {

	public static final String FIND_BY_NO_ASSETS =
		BookmarksEntryFinder.class.getName() + ".findByNoAssets";

	@Override
	public List<BookmarksEntry> findByNoAssets() {
		Session session = null;

		try {
			session = openSession();

			String sql = _customSQL.get(getClass(), FIND_BY_NO_ASSETS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("BookmarksEntry", BookmarksEntryImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(_portal.getClassNameId(BookmarksEntry.class));

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

	@Reference
	private Portal _portal;

}