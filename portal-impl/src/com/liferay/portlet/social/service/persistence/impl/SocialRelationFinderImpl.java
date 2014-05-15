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

package com.liferay.portlet.social.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.User;
import com.liferay.portal.model.impl.UserImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.social.model.SocialRelation;
import com.liferay.portlet.social.service.persistence.SocialRelationFinder;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Sherry Yang
 */
public class SocialRelationFinderImpl
	extends BasePersistenceImpl<SocialRelation>
	implements SocialRelationFinder {

	public static final String COUNT_SOCIAL_USERS =
		SocialRelationFinder.class.getName() + ".countSocialUsers";

	public static final String FIND_SOCIAL_USERS =
		SocialRelationFinder.class.getName() + ".findSocialUsers";

	public int countSocialUsers(
			long companyId, long userId, int type, boolean equal, int status)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_SOCIAL_USERS);

			sql = buildTypeSQL(sql, equal);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(type);
			qPos.add(userId);
			qPos.add(companyId);
			qPos.add(Boolean.FALSE);
			qPos.add(status);

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

	public List<User> findSocialUsers(
			long companyId, long userId, int type, boolean equal, int status,
			int start, int end, OrderByComparator obc)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_SOCIAL_USERS);

			sql = buildTypeSQL(sql, equal);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("User_", UserImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(type);
			qPos.add(userId);
			qPos.add(companyId);
			qPos.add(Boolean.FALSE);
			qPos.add(status);

			return (List<User>)QueryUtil.list(q, getDialect(), start, end);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected String buildTypeSQL(String sql, boolean equal) {
		if (equal) {
			return StringUtil.replace(
				sql, "[$TYPE]", "SocialRelation.type_ = ?");
		}

		return StringUtil.replace(sql, "[$TYPE]", "SocialRelation.type_ <> ?");
	}

}