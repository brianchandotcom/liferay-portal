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
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ResourceBlock;
import com.liferay.portal.security.permission.ResourceBlockIdsBag;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;

/**
 * @author Connor McKay
 */
public class ResourceBlockFinderImpl
	extends BasePersistenceImpl<ResourceBlock>
	implements ResourceBlockFinder {

	public static String FIND_BY_C_G_N_R =
		ResourceBlockFinder.class.getName() + ".findByC_G_N_R";

	public ResourceBlockIdsBag findByC_G_N_R(
			long companyId, long groupId, String name, long[] roleIds)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_G_N_R);

			sql = StringUtil.replace(
				sql, "[$ROLE_IDS$]", StringUtil.merge(roleIds));

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("resourceBlockId", Type.LONG);
			q.addScalar("actionIds", Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(name);

			ResourceBlockIdsBag resourceBlockIdsBag = new ResourceBlockIdsBag();

			Iterator<Object[]> itr = q.iterate();

			while (itr.hasNext()) {
				Object[] array = itr.next();

				Long resourceBlockId = (Long)array[0];
				Long actionIdsLong = (Long)array[1];

				resourceBlockIdsBag.addResourceBlockId(
					resourceBlockId, actionIdsLong);
			}

			return resourceBlockIdsBag;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}