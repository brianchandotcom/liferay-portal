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

package com.liferay.portlet.asset.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.asset.model.AssetVocabulary;
import com.liferay.portlet.asset.model.impl.AssetVocabularyImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Juan Fernández
 */
public class AssetVocabularyFinderImpl
	extends BasePersistenceImpl<AssetVocabulary>
	implements AssetVocabularyFinder {

	public static String COUNT_BY_G_N =
		AssetVocabularyFinder.class.getName() + ".countByG_N";

	public static String FIND_BY_G_N =
		AssetVocabularyFinder.class.getName() + ".findByG_N";

	public int countByG_N(long groupId, String name) throws SystemException {

		return doCountByG_N(groupId, name, false);
	}

	public int filterCountByG_N(long groupId, String name)
		throws SystemException {

		return doCountByG_N(groupId, name, true);
	}

	public List<AssetVocabulary> filterFindByG_N(long groupId, String name,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		return doFindByG_N(groupId, name, start, end, obc, true);
	}

	public List<AssetVocabulary> findByG_N(long groupId, String name, int start,
 			int end, OrderByComparator obc)
		throws SystemException {

		return doFindByG_N(groupId, name, start, end, obc, false);
	}

	protected int doCountByG_N(
			long groupId, String name, boolean inlineSQLHelper)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_N);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, AssetVocabulary.class.getName(),
					"AssetVocabulary.vocabularyId", groupId);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(name);
			qPos.add(name);

			Iterator<Long> itr = q.iterate();

			if (itr.hasNext()) {
				Long count = itr.next();

				if (count != null) {
					return count.intValue();
				}
			}

			return 0;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected List<AssetVocabulary> doFindByG_N(
			long groupId, String name, int start, int end,
			OrderByComparator obc, boolean inlineSQLHelper)
		throws SystemException {

		name = name.trim().toLowerCase();

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_N);

			sql = CustomSQLUtil.replaceOrderBy(sql, obc);

			if (inlineSQLHelper) {
				sql = InlineSQLHelperUtil.replacePermissionCheck(
					sql, AssetVocabulary.class.getName(),
					"AssetVocabulary.vocabularyId", groupId);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("AssetVocabulary", AssetVocabularyImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(name);
			qPos.add(name);

			return (List<AssetVocabulary>)QueryUtil.list(
				q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}