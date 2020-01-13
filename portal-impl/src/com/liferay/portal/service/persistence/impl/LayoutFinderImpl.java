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

import com.liferay.petra.sql.dsl.DSLFunctionUtil;
import com.liferay.petra.sql.dsl.DSLSelectUtil;
import com.liferay.petra.sql.dsl.expressions.Predicate;
import com.liferay.petra.sql.dsl.query.Query;
import com.liferay.petra.string.StringBundler;
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

			SQLQuery q = session.createSynchronizedSQLQuery(
				DSLSelectUtil.select(
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
		return _findByScopeGroup(
			Layout.TABLE.groupId.eq(groupId), groupId, true);
	}

	@Override
	public List<Layout> findByScopeGroup(long groupId, boolean privateLayout) {
		return _findByScopeGroup(
			Layout.TABLE.groupId.eq(
				groupId
			).and(
				Layout.TABLE.privateLayout.eq(privateLayout)
			),
			groupId, false);
	}

	@Override
	public List<LayoutReference> findByC_P_P(
		long companyId, String portletId, String preferencesKey,
		String preferencesValue) {

		Session session = null;

		try {
			session = openSession();

			Query query = DSLSelectUtil.selectDistinct(
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

			SQLQuery q = session.createSynchronizedSQLQuery(query);

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

	private List<Layout> _findByScopeGroup(
		Predicate wherePredicate, long groupId, boolean inlineSQLHelper) {

		Session session = null;

		try {
			session = openSession();

			Query query = DSLSelectUtil.select(
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
				wherePredicate
			);

			if (inlineSQLHelper) {
				query = InlineSQLHelperUtil.replacePermissionCheck(
					query, Layout.class, Layout.TABLE.plid, groupId);
			}

			SQLQuery q = session.createSynchronizedSQLQuery(query);

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

}