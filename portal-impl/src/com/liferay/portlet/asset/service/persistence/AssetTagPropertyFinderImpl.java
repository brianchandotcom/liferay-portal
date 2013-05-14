/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
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
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portlet.asset.model.AssetTagProperty;
import com.liferay.portlet.asset.model.impl.AssetTagPropertyImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class AssetTagPropertyFinderImpl
	extends BasePersistenceImpl<AssetTagProperty>
	implements AssetTagPropertyFinder {

	public static final String COUNT_BY_G_K =
		AssetTagPropertyFinder.class.getName() + ".countByG_K";

	public static final String FIND_BY_G_K =
		AssetTagPropertyFinder.class.getName() + ".findByG_K";

	@Override
	public int countByG_K(long groupId, String key) throws SystemException {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_G_K);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(key);

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

	@Override
	public List<AssetTagProperty> findByG_K(long groupId, String key)
		throws SystemException {

		return findByG_K(groupId, key, QueryUtil.ALL_POS, QueryUtil.ALL_POS);
	}

	@Override
	public List<AssetTagProperty> findByG_K(
			long groupId, String key, int start, int end)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_G_K);

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar("tagPropertyValue", Type.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(key);

			List<AssetTagProperty> tagProperties =
				new ArrayList<AssetTagProperty>();

			Iterator<String> itr = (Iterator<String>)QueryUtil.iterate(
				q, getDialect(), start, end);

			while (itr.hasNext()) {
				String value = itr.next();

				AssetTagProperty tagProperty = new AssetTagPropertyImpl();

				tagProperty.setKey(key);
				tagProperty.setValue(value);

				tagProperties.add(tagProperty);
			}

			return tagProperties;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}