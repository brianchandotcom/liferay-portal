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

import com.liferay.portal.NoSuchLayoutBranchException;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutBranch;
import com.liferay.portal.model.impl.LayoutBranchImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Julio Camarero
 */
public class LayoutBranchFinderImpl
	extends BasePersistenceImpl<Layout> implements LayoutBranchFinder {

	public static String FIND_BY_MASTER =
		LayoutBranchFinder.class.getName() + ".findByMaster";

	public LayoutBranch findByMaster (long layoutSetBranchId, long plid)
		throws NoSuchLayoutBranchException, SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_MASTER);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("LayoutBranch", LayoutBranchImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(layoutSetBranchId);
			qPos.add(plid);
			qPos.add(true);

			List<LayoutBranch> list = q.list();

			if (!list.isEmpty()) {
				return list.get(0);
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}

		StringBundler sb = new StringBundler(5);

		sb.append("No LayoutBranch exists with the key {layoutSetBranchId=");
		sb.append(layoutSetBranchId);
		sb.append(", plid=");
		sb.append(plid);
		sb.append(", master=true}");

		throw new NoSuchLayoutBranchException(sb.toString());
	}

}