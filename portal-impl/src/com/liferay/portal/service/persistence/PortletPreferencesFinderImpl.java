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

package com.liferay.portal.service.persistence;

import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.PortletConstants;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.impl.PortletPreferencesImpl;
import com.liferay.portal.model.impl.PortletPreferencesModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.Iterator;
import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class PortletPreferencesFinderImpl
	extends BasePersistenceImpl<PortletPreferences>
	implements PortletPreferencesFinder {

	public static final String COUNT_BY_C_G_O_O_P_P_P =
		PortletPreferencesFinder.class.getName() + ".countByC_G_O_O_P_P_P";

	public static final String FIND_BY_PORTLET_ID =
		PortletPreferencesFinder.class.getName() + ".findByPortletId";

	public static final String FIND_BY_C_G_O_O_P_P =
		PortletPreferencesFinder.class.getName() + ".findByC_G_O_O_P_P";

	public static final FinderPath FINDER_PATH_FIND_BY_C_G_O_O_P_P =
		new FinderPath(
			PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED,
			PortletPreferencesImpl.class,
			PortletPreferencesPersistenceImpl.
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_G_O_O_P_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				String.class.getName(), Boolean.class.getName()
			}
		);

	@Override
	public long countByC_G_O_O_P_P_P(
			long companyId, long groupId, long ownerId, int ownerType,
			long plid, String portletId, boolean privateLayout)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(COUNT_BY_C_G_O_O_P_P_P);

			if (ownerId == -1) {
				sql = StringUtil.replace(sql, _OWNER_ID_SQL, StringPool.BLANK);
			}

			if (plid == -1) {
				sql = StringUtil.replace(sql, _PLID_SQL, StringPool.BLANK);
			}
			else {
				sql = StringUtil.replace(
					sql, _PRIVATE_LAYOUT_SQL, StringPool.BLANK);
			}

			SQLQuery q = session.createSQLQuery(sql);

			q.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(groupId);

			if (ownerId != -1) {
				qPos.add(ownerId);
			}

			qPos.add(ownerType);
			qPos.add(portletId);
			qPos.add(portletId.concat("%_INSTANCE_%"));

			if (plid != -1) {
				qPos.add(plid);
			}
			else {
				qPos.add(privateLayout);
			}

			int count = 0;

			Iterator<Long> itr = q.iterate();

			while (itr.hasNext()) {
				Long l = itr.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<PortletPreferences> findByPortletId(String portletId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_PORTLET_ID);

			SQLQuery q = session.createSQLQuery(sql);

			q.addEntity("PortletPreferences", PortletPreferencesImpl.class);

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(portletId);

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
	public List<PortletPreferences> findByC_G_O_O_P_P(
			long companyId, long groupId, long ownerId, int ownerType,
			String portletId, boolean privateLayout)
		throws SystemException {

		Object[] finderArgs = {
			companyId, groupId, ownerId, ownerType, portletId, privateLayout
		};

		List<PortletPreferences> list =
			(List<PortletPreferences>)FinderCacheUtil.getResult(
				FINDER_PATH_FIND_BY_C_G_O_O_P_P, finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (PortletPreferences portletPreferences : list) {
				if ((ownerId != portletPreferences.getOwnerId()) ||
					(ownerType != portletPreferences.getOwnerType()) ||
					!isInstanceOf(
						portletPreferences.getPortletId(), portletId)) {

					list = null;

					break;
				}
			}
		}

		if (list == null) {
			Session session = null;

			try {
				session = openSession();

				String sql = CustomSQLUtil.get(FIND_BY_C_G_O_O_P_P);

				SQLQuery q = session.createSQLQuery(sql);

				q.addEntity("PortletPreferences", PortletPreferencesImpl.class);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);
				qPos.add(groupId);
				qPos.add(ownerId);
				qPos.add(ownerType);
				qPos.add(portletId);
				qPos.add(portletId.concat("_INSTANCE_%"));
				qPos.add(privateLayout);

				list = q.list(true);

				PortletPreferencesUtil.cacheResult(list);

				FinderCacheUtil.putResult(
					FINDER_PATH_FIND_BY_C_G_O_O_P_P, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					FINDER_PATH_FIND_BY_C_G_O_O_P_P, finderArgs);

				throw new SystemException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	protected boolean isInstanceOf(
		String portletPreferencesPortletId, String portletId) {

		portletPreferencesPortletId = GetterUtil.getString(
			portletPreferencesPortletId);
		portletId = GetterUtil.getString(portletId);

		if (portletPreferencesPortletId.equals(portletId)) {
			return true;
		}

		if (portletPreferencesPortletId.startsWith(
				portletId.concat(PortletConstants.INSTANCE_SEPARATOR))) {

			return true;
		}

		return false;
	}

	private static final String _OWNER_ID_SQL =
		"(PortletPreferences.ownerId = ?) AND";

	private static final String _PLID_SQL = "(Layout.plid = ?) AND";

	private static final String _PRIVATE_LAYOUT_SQL =
		"AND (Layout.privateLayout = ?)";

}