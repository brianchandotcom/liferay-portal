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
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.PortletPreferences;
import com.liferay.portal.model.impl.PortletPreferencesImpl;
import com.liferay.portal.model.impl.PortletPreferencesModelImpl;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.util.List;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class PortletPreferencesFinderImpl
	extends BasePersistenceImpl<PortletPreferences>
	implements PortletPreferencesFinder {

	public static final String FIND_BY_PORTLETID =
		PortletPreferencesFinder.class.getName() + ".findByPortletId";

	public static final String FIND_BY_C_G_O_O_P_P =
		PortletPreferencesFinder.class.getName() + ".findByC_G_O_O_P_P";

	public static final FinderPath FINDER_PATH_FIND_PLIDS_BY_C_G_O_O_P_P =
		new FinderPath(
			PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
			PortletPreferencesModelImpl.FINDER_CACHE_ENABLED, List.class,
			PortletPreferencesPersistenceImpl
				.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findPlidsByC_G_O_O_P_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				String.class.getName(), Boolean.class.getName()
			}
		);

	public static final FinderPath
		FINDER_PATH_FIND_PORTLET_PREFERENCES_BY_C_G_O_O_P_P =
			new FinderPath(
				PortletPreferencesModelImpl.ENTITY_CACHE_ENABLED,
				PortletPreferencesModelImpl.FINDER_CACHE_ENABLED, List.class,
				PortletPreferencesPersistenceImpl
					.FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findPortletPreferencesByC_G_O_O_P_P",
				new String[] {
					Long.class.getName(), Long.class.getName(),
					Long.class.getName(), Integer.class.getName(),
					String.class.getName(), Boolean.class.getName()
				}
			);

	public List<PortletPreferences> findByPortletId(String portletId)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_PORTLETID);

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

	public List<PortletPreferences> findByC_G_O_O_P_P(
			long companyId, long groupId, long ownerId, int ownerType,
			String portletId, boolean privateLayout)
		throws SystemException {

		return doFindByC_G_O_O_P_P(
			companyId, groupId, ownerId, ownerType, portletId, privateLayout,
			false);
	}

	@SuppressWarnings("unchecked")
	public List<Long> findPlidsByC_G_O_O_P_P(
			long companyId, long groupId, long ownerId, int ownerType,
			String portletId, boolean privateLayout)
		throws SystemException {

		Object[] finderArgs = new Object[] {
			companyId, groupId, ownerId, ownerType, portletId, privateLayout};

		List<Long> list = (List<Long>)FinderCacheUtil.getResult(
			FINDER_PATH_FIND_PLIDS_BY_C_G_O_O_P_P, finderArgs, this);

		if (list != null) {
			return list;
		}

		try {
			list = doFindByC_G_O_O_P_P(
				companyId, groupId, ownerId, ownerType, portletId,
				privateLayout, true);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			if (list == null) {
				FinderCacheUtil.removeResult(
					FINDER_PATH_FIND_PLIDS_BY_C_G_O_O_P_P, finderArgs);
			}
			else {
				FinderCacheUtil.putResult(
					FINDER_PATH_FIND_PLIDS_BY_C_G_O_O_P_P, finderArgs, list);
			}
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public List<PortletPreferences> findPortletPreferencesByC_G_O_O_P_P(
			long companyId, long groupId, long ownerId, int ownerType,
			String portletId, boolean privateLayout)
		throws SystemException {

		Object[] finderArgs = new Object[] {
			companyId, groupId, ownerId, ownerType, portletId, privateLayout};

		List<PortletPreferences> list =
			(List<PortletPreferences>)FinderCacheUtil.getResult(
				FINDER_PATH_FIND_PORTLET_PREFERENCES_BY_C_G_O_O_P_P, finderArgs,
				this);

		if (list != null) {
			return list;
		}

		try {
			list = doFindByC_G_O_O_P_P(
				companyId, groupId, ownerId, ownerType, portletId,
				privateLayout, false);
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			if (list == null) {
				FinderCacheUtil.removeResult(
					FINDER_PATH_FIND_PORTLET_PREFERENCES_BY_C_G_O_O_P_P,
					finderArgs);
			}
			else {
				FinderCacheUtil.putResult(
					FINDER_PATH_FIND_PORTLET_PREFERENCES_BY_C_G_O_O_P_P,
					finderArgs, list);
			}
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	protected <E> List<E> doFindByC_G_O_O_P_P(
			long companyId, long groupId, long ownerId, int ownerType,
			String portletId, boolean privateLayout, boolean returnPlidsOnly)
		throws SystemException {

		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_C_G_O_O_P_P);

			String distinctValue = StringPool.BLANK;
			String projectionValue = "{PortletPreferences.*}";

			if (returnPlidsOnly) {
				distinctValue = "DISTINCT";
				projectionValue = "PortletPreferences.plid";
			}

			sql = StringUtil.replace(sql, "[$DISTINCT$]", distinctValue);
			sql = StringUtil.replace(sql, "[$PROJECTION$]", projectionValue);

			SQLQuery q = session.createSQLQuery(sql);

			if (returnPlidsOnly) {
				q.addScalar("plid", Type.LONG);
			}
			else {
				q.addEntity("PortletPreferences", PortletPreferencesImpl.class);
			}

			QueryPos qPos = QueryPos.getInstance(q);

			qPos.add(companyId);
			qPos.add(groupId);
			qPos.add(ownerId);
			qPos.add(ownerType);
			qPos.add(portletId);
			qPos.add(portletId.concat("_INSTANCE_%"));
			qPos.add(privateLayout);

			return (List<E>)q.list(true);
		}
		catch (Exception e) {
			throw new SystemException(e);
		}
		finally {
			closeSession(session);
		}
	}

}