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

package com.liferay.portal.service.persistence.impl;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.model.Layout;
import com.liferay.portal.model.LayoutReference;
import com.liferay.portal.model.LayoutSoap;
import com.liferay.portal.model.ResourceConstants;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.portal.service.persistence.LayoutFinder;
import com.liferay.portal.service.persistence.LayoutUtil;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutFinderImpl
	extends BasePersistenceImpl<Layout> implements LayoutFinder {

	public static final String FIND_BY_NO_PERMISSIONS =
		LayoutFinder.class.getName() + ".findByNoPermissions";

	public static final String FIND_BY_NULL_FRIENDLY_URL =
		LayoutFinder.class.getName() + ".findByNullFriendlyURL";

	public static final String FIND_BY_SCOPE_GROUP =
		LayoutFinder.class.getName() + ".findByScopeGroup";

	public static final String FIND_BY_C_P_P =
		LayoutFinder.class.getName() + ".findByC_P_P";

	@Override
	public List<Layout> findByNoPermissions(long roleId) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NO_PERMISSIONS);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Layout", LayoutImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(ResourceConstants.SCOPE_INDIVIDUAL);
			qPos.add(roleId);

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
	public List<Layout> findByNullFriendlyURL() {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_NULL_FRIENDLY_URL);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Layout", LayoutImpl.class);

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
	public List<Layout> findByScopeGroup(long groupId, boolean privateLayout) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_SCOPE_GROUP);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Layout", LayoutImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);
			qPos.add(privateLayout);

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
	public List<LayoutReference> findByC_P_P(
		long companyId, String portletId, String preferencesKey,
		String preferencesValue) {

		String preferences =
			"%<preference><name>" + preferencesKey + "</name><value>" +
				preferencesValue + "</value>%";

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_P_P);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addScalar("layoutPlid", Type.LONG);
			q.addScalar("preferencesPortletId", Type.STRING);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(portletId);
			qPos.add(portletId.concat("_INSTANCE_%"));
			qPos.add(preferences);

			List<LayoutReference> layoutReferences =
				new ArrayList<LayoutReference>();

			Iterator<Object[]> itr = q.iterate();

			while (itr.hasNext()) {
				Object[] array = itr.next();

				Long layoutPlid = (Long)array[0];
				String preferencesPortletId = (String)array[1];

				Layout layout = LayoutUtil.findByPrimaryKey(
					layoutPlid.longValue());

				layoutReferences.add(
					new LayoutReference(
						LayoutSoap.toSoapModel(layout), preferencesPortletId));
			}

			return layoutReferences;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}