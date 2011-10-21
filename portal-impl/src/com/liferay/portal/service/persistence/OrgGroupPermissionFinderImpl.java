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
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.OrgGroupPermission;
import com.liferay.portal.model.impl.OrgGroupPermissionImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;

/**
 * @author Brian Wing Shun Chan
 */
public class OrgGroupPermissionFinderImpl
	extends BasePersistenceImpl<OrgGroupPermission>
	implements OrgGroupPermissionFinder {

	public static String FIND_BY_O_G_R =
		OrgGroupPermissionFinder.class.getName() + ".findByO_G_R";

	public void removeByO_G_R(
			long organizationId, long groupId, long resourceId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_O_G_R);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("OrgGroupPermission", OrgGroupPermissionImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(resourceId);
			qPos.add(organizationId);
			qPos.add(groupId);

			Iterator<OrgGroupPermission> itr = q.iterate();

			while (itr.hasNext()) {
				OrgGroupPermission orgGroupPermission = itr.next();

				OrgGroupPermissionUtil.remove(
					orgGroupPermission.getPrimaryKey());
			}
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}