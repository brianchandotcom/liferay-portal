/**
 * SPDX-FileCopyrightText: (c) 2000 Liferay, Inc. https://liferay.com
 * SPDX-License-Identifier: LGPL-2.1-or-later OR LicenseRef-Liferay-DXP-EULA-2.0.0-2023-06
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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				DLFileEntryMetadataImpl.TABLE_NAME,
				DLFileEntryMetadataImpl.class);

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
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

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				DLFileEntryMetadataImpl.TABLE_NAME,
				DLFileEntryMetadataImpl.class);

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

}