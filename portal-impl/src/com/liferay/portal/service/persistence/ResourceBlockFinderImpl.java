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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.model.impl.ResourceBlockImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Connor McKay
 */
public class ResourceBlockFinderImpl
	extends BasePersistenceImpl<ResourceBlock>
	implements ResourceBlockFinder {

	public static String FIND_BY_C_G_N_R_A =
		ResourceBlockFinder.class.getName() + ".findByC_G_N_R_A";

	public List<ResourceBlock> findByC_G_N_R_A(
			long companyId, long groupId, String name, long[] roleIds,
			long actionId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_G_N_R_A);

			// The action ID must be string replaced into the query so that
			// SQL transformer can duplicated it in the query

			sql = StringUtil.replace(
				sql,
				new String[] {
					"[$ROLE_IDS$]",
					"[$ACTION_ID$]"
				},
				new String[] {
					StringUtil.merge(roleIds),
					String.valueOf(actionId)
				});

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("ResourceBlock", ResourceBlockImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(name);

			return (List<ResourceBlock>)QueryUtil.list(
				q, getDialect(), QueryUtil.ALL_POS, QueryUtil.ALL_POS);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}