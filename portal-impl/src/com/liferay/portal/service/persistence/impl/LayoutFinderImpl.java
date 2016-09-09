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
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutReference;
import com.liferay.portal.kernel.model.LayoutSoap;
import com.liferay.portal.kernel.model.ResourceConstants;
import com.liferay.portal.kernel.model.Role;
import com.liferay.portal.kernel.service.persistence.LayoutFinder;
import com.liferay.portal.kernel.service.persistence.LayoutUtil;
import com.liferay.portal.kernel.service.persistence.RoleUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.LayoutImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutFinderImpl
	extends LayoutFinderBaseImpl implements LayoutFinder {

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
		Role role = RoleUtil.fetchByPrimaryKey(roleId);

		if (role == null) {
			return Collections.emptyList();
		}

		Session session = null;

		try {
			session = openSession();

			String sql = "SELECT plid FROM Layout WHERE companyId = ?";

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(role.getCompanyId());

			Set<BigInteger> plids = new HashSet<>(q.list(true));

			StringBundler sb = new StringBundler(3);

			sb.append("SELECT primKey from ResourcePermission WHERE ");
			sb.append("(companyId = ?) AND (name = ?) AND (scope = ?) AND ");
			sb.append("(roleId = ?)");

			sql = sb.toString();

			q = session.createSynchronizedSQLQuery(sql);

			qPos = QueryPos.getInstance(q);

			qPos.add(role.getCompanyId());
			qPos.add(Layout.class.getName());
			qPos.add(ResourceConstants.SCOPE_INDIVIDUAL);
			qPos.add(roleId);

			Iterator<String> it = q.iterate(false);

			while (it.hasNext()) {
				String primKey = it.next();

				plids.remove(GetterUtil.getLong(primKey));
			}

			Class<?>[] layoutClassArray = new Class<?>[] {Layout.class};
			ClassLoader layoutClassLoader = Layout.class.getClassLoader();

			List<Layout> layouts = new ArrayList<>(plids.size());

			for (BigInteger plid : plids) {
				Layout layout = (Layout)ProxyUtil.newProxyInstance(
					layoutClassLoader, layoutClassArray,
					new LayoutPlidOnlyInvocationHandler(plid.longValue()));

				layouts.add(layout);
			}

			return layouts;
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
	public List<Layout> findByScopeGroup(long groupId) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_SCOPE_GROUP);

			sql = StringUtil.replace(
				sql, "AND (Layout.privateLayout = ?)", StringPool.BLANK);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Layout", LayoutImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(groupId);

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

			List<LayoutReference> layoutReferences = new ArrayList<>();

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

	private class LayoutPlidOnlyInvocationHandler implements InvocationHandler {

		public LayoutPlidOnlyInvocationHandler(long plid) {
			_plid = plid;
		}

		public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

			String methodName = method.getName();

			if (methodName.equals("getPlid")) {
				return _plid;
			}
			else {
				throw new UnsupportedOperationException(methodName);
			}
		}

		private final long _plid;

	}

}