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

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.model.dsl.DSLFunctionUtil;
import com.liferay.portal.kernel.dao.model.dsl.DSLStatementUtil;
import com.liferay.portal.kernel.dao.model.dsl.query.Query;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.Group;
import com.liferay.portal.kernel.model.Layout;
import com.liferay.portal.kernel.model.LayoutReference;
import com.liferay.portal.kernel.model.LayoutSoap;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.security.permission.InlineSQLHelperUtil;
import com.liferay.portal.kernel.service.persistence.LayoutFinder;
import com.liferay.portal.kernel.service.persistence.LayoutUtil;
import com.liferay.portal.kernel.util.PortalUtil;
import com.liferay.portal.model.impl.LayoutImpl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 */
public class LayoutFinderImpl
	extends LayoutFinderBaseImpl implements LayoutFinder {

	@Override
	public List<Layout> findByNullFriendlyURL() {
		Session session = null;

		try {
			session = openSession();

			SQLQuery q = DSLStatementUtil.createSynchronizedSQLQuery(
				session,
				DSLStatementUtil.select(
				).from(
					Layout.TABLE
				).where(
					Layout.TABLE.friendlyURL.eq(
						""
					).or(
						Layout.TABLE.friendlyURL.isNull()
					)
				));

			q.addEntity("Layout", LayoutImpl.class);

			return q.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
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

			Query query = DSLStatementUtil.select(
			).from(
				Layout.TABLE
			).innerJoinON(
				Group.TABLE,
				Group.TABLE.companyId.eq(
					Layout.TABLE.companyId
				).and(
					Group.TABLE.classNameId.eq(
						PortalUtil.getClassNameId(Layout.class))
				).and(
					Group.TABLE.classPK.eq(Layout.TABLE.plid)
				)
			).where(
				Layout.TABLE.groupId.eq(groupId)
			);

			String sql = InlineSQLHelperUtil.replacePermissionCheck(
				query.toString(), Layout.class.getName(), "Layout.plid",
				groupId);

			SQLQuery q = session.createSynchronizedSQLQuery(sql);

			q.addEntity("Layout", LayoutImpl.class);

			return q.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
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

			SQLQuery q = DSLStatementUtil.createSynchronizedSQLQuery(
				session,
				DSLStatementUtil.select(
				).from(
					Layout.TABLE
				).innerJoinON(
					Group.TABLE,
					Group.TABLE.companyId.eq(
						Layout.TABLE.companyId
					).and(
						Group.TABLE.classNameId.eq(
							PortalUtil.getClassNameId(Layout.class))
					).and(
						Group.TABLE.classPK.eq(Layout.TABLE.plid)
					)
				).where(
					Layout.TABLE.groupId.eq(
						groupId
					).and(
						Layout.TABLE.privateLayout.eq(privateLayout)
					)
				));

			q.addEntity("Layout", LayoutImpl.class);

			return q.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<LayoutReference> findByC_P_P(
		long companyId, String portletId, String preferencesKey,
		String preferencesValue) {

		Session session = null;

		try {
			session = openSession();

			Query query = DSLStatementUtil.selectDistinct(
				Layout.TABLE.plid.as("layoutPlid"),
				PortletPreferences.TABLE.portletId.as("preferencesPortletId")
			).from(
				Layout.TABLE
			).innerJoinON(
				PortletPreferences.TABLE,
				PortletPreferences.TABLE.plid.eq(Layout.TABLE.plid)
			).where(
				Layout.TABLE.companyId.eq(
					companyId
				).and(
					PortletPreferences.TABLE.portletId.eq(
						portletId
					).or(
						PortletPreferences.TABLE.portletId.like(
							portletId.concat("_INSTANCE_%"))
					).withParentheses()
				).and(
					DSLFunctionUtil.castClobText(
						PortletPreferences.TABLE.preferences
					).like(
						StringBundler.concat(
							"%<preference><name>", preferencesKey,
							"</name><value>", preferencesValue, "</value>%")
					)
				)
			);

			SQLQuery q = DSLStatementUtil.createSynchronizedSQLQuery(
				session, query);

			q.addScalar("layoutPlid", Type.LONG);
			q.addScalar("preferencesPortletId", Type.STRING);

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
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

}