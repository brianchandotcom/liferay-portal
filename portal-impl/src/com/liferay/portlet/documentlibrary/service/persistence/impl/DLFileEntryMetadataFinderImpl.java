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

package com.liferay.portlet.documentlibrary.service.persistence.impl;

import com.liferay.document.library.kernel.model.DLFileEntryMetadata;
import com.liferay.document.library.kernel.service.persistence.DLFileEntryMetadataFinder;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portlet.documentlibrary.model.impl.DLFileEntryMetadataImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Michael C. Han
 */
public class DLFileEntryMetadataFinderImpl
	extends DLFileEntryMetadataFinderBaseImpl
	implements DLFileEntryMetadataFinder {

	public static final String FIND_BY_MISMATCHED_COMPANY_ID =
		DLFileEntryMetadataFinder.class.getName() +
			".findByMismatchedCompanyId";

	public static final String FIND_BY_NO_STRUCTURES =
		DLFileEntryMetadataFinder.class.getName() + ".findByNoStructures";

	@Override
	public List<DLFileEntryMetadata> findByMismatchedCompanyId() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_MISMATCHED_COMPANY_ID);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				DLFileEntryMetadataImpl.TABLE_NAME,
				DLFileEntryMetadataImpl.class);

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
	public List<DLFileEntryMetadata> findByNoStructures() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_STRUCTURES);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity(
				DLFileEntryMetadataImpl.TABLE_NAME,
				DLFileEntryMetadataImpl.class);

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