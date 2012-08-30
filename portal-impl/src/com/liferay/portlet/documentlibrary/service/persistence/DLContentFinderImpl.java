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

package com.liferay.portlet.documentlibrary.service.persistence;

import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.documentlibrary.model.DLContent;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Preston Crary
 */
public class DLContentFinderImpl
	extends BasePersistenceImpl<DLContent> implements DLContentFinder {

	public static final String FIND_P_BY_C_R_LIKE_P =
		DLContentFinder.class.getName() + ".findP_ByC_R_LikeP";

	public static final String FIND_R_BY_COMPANY_ID =
		DLContentFinder.class.getName() + ".findR_ByCompanyId";

	public static final String FIND_S_BY_C_R_P =
		DLContentFinder.class.getName() + ".findS_ByC_R_P";

	public static final String FIND_V_BY_C_R_P =
		DLContentFinder.class.getName() + ".findV_ByC_R_P";

	public List<String> findPByC_R_LikeP(
			long companyId, long repostioryId, String path)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_P_BY_C_R_LIKE_P);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("path_", Type.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(repostioryId);
			qPos.add(path);

			return q.list();
		}
		catch (Exception e) {
			throw new SystemException();
		}
		finally {
			closeSession(session);
		}
	}

	public List<Long> findRByCompanyId(long companyId) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_R_BY_COMPANY_ID);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("repositoryId", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException();
		}
		finally {
			closeSession(session);
		}
	}

	public long findSByC_R_P(long companyId, long repositoryId, String path)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_S_BY_C_R_P);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("size_", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(repositoryId);
			qPos.add(path);

			Iterator<Long> itr = q.iterate();

			long size = 0;

			while (itr.hasNext()) {
				size += itr.next();
			}

			return size;
		}
		catch (Exception e) {
			throw new SystemException();
		}
		finally {
			closeSession(session);
		}
	}

	public List<String> findVByC_R_P(
			long companyId, long repositoryId, String path)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_V_BY_C_R_P);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("version", Type.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(repositoryId);
			qPos.add(path);

			return q.list(true);
		}
		catch (Exception e) {
			throw new SystemException();
		}
		finally {
			closeSession(session);
		}
	}

}