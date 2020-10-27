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

import com.liferay.petra.sql.dsl.DSLQueryFactoryUtil;
import com.liferay.petra.sql.dsl.expression.Predicate;
import com.liferay.petra.sql.dsl.query.JoinStep;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.Type;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.model.PortletPreferenceValueTable;
import com.liferay.portal.kernel.model.PortletPreferences;
import com.liferay.portal.kernel.model.PortletPreferencesTable;
import com.liferay.portal.kernel.portlet.PortletIdCodec;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesFinder;
import com.liferay.portal.kernel.service.persistence.PortletPreferencesUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.model.impl.PortletPreferencesImpl;
import com.liferay.util.dao.orm.CustomSQLUtil;

import java.io.Serializable;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * @author Brian Wing Shun Chan
 * @author Hugo Huijser
 */
public class PortletPreferencesFinderImpl
	extends PortletPreferencesFinderBaseImpl
	implements PortletPreferencesFinder {

	public static final String FIND_BY_PORTLET_ID =
		PortletPreferencesFinder.class.getName() + ".findByPortletId";

	public static final String FIND_BY_C_G_O_O_P_P =
		PortletPreferencesFinder.class.getName() + ".findByC_G_O_O_P_P";

	public static final FinderPath FINDER_PATH_FIND_BY_C_G_O_O_P_P =
		new FinderPath(
			PortletPreferencesPersistenceImpl.
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_G_O_O_P_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				String.class.getName(), Boolean.class.getName()
			},
			new String[] {"ownerId", "ownerType", "portletId"}, true);

	@Override
	public long countByO_O_P(
		long ownerId, int ownerType, String portletId,
		boolean excludeDefaultPreferences) {

		return countByO_O_P_P_P(
			ownerId, ownerType, -1, portletId, excludeDefaultPreferences);
	}

	@Override
	public long countByO_O_P_P_P(
		long ownerId, int ownerType, long plid, String portletId,
		boolean excludeDefaultPreferences) {

		Session session = null;

		try {
			session = openSession();

			JoinStep joinStep = DSLQueryFactoryUtil.count(
			).from(
				PortletPreferencesTable.INSTANCE
			);

			if (excludeDefaultPreferences) {
				joinStep = joinStep.innerJoinON(
					PortletPreferenceValueTable.INSTANCE,
					PortletPreferenceValueTable.INSTANCE.portletPreferencesId.
						eq(
							PortletPreferencesTable.INSTANCE.
								portletPreferencesId));
			}

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(
				joinStep.where(
					() -> {
						Predicate predicate =
							PortletPreferencesTable.INSTANCE.ownerType.eq(
								ownerType);

						if (ownerId != -1) {
							predicate = predicate.and(
								PortletPreferencesTable.INSTANCE.ownerId.eq(
									ownerId));
						}

						if (plid == -1) {
							predicate = predicate.and(
								PortletPreferencesTable.INSTANCE.portletId.eq(
									portletId
								).or(
									PortletPreferencesTable.INSTANCE.portletId.
										like(portletId.concat("%_INSTANCE_%"))
								).withParentheses());
						}
						else {
							predicate = predicate.and(
								PortletPreferencesTable.INSTANCE.portletId.eq(
									portletId
								).and(
									PortletPreferencesTable.INSTANCE.plid.eq(
										plid)
								));
						}

						return predicate;
					}));

			sqlQuery.addScalar(COUNT_COLUMN_NAME, Type.LONG);

			int count = 0;

			Iterator<Long> iterator = sqlQuery.iterate();

			while (iterator.hasNext()) {
				Long l = iterator.next();

				if (l != null) {
					count += l.intValue();
				}
			}

			return count;
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	/**
	 * @deprecated As of Cavanaugh (7.4.x), with no direct replacement
	 */
	@Deprecated
	@Override
	public Map<Serializable, PortletPreferences> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		return PortletPreferencesUtil.fetchByPrimaryKeys(primaryKeys);
	}

	@Override
	public List<PortletPreferences> findByPortletId(String portletId) {
		Session session = null;

		try {
			session = openSession();

			String sql = CustomSQLUtil.get(FIND_BY_PORTLET_ID);

			SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

			sqlQuery.addEntity(
				"PortletPreferences", PortletPreferencesImpl.class);

			QueryPos queryPos = QueryPos.getInstance(sqlQuery);

			queryPos.add(portletId);

			return sqlQuery.list(true);
		}
		catch (Exception exception) {
			throw new SystemException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	public List<PortletPreferences> findByC_G_O_O_P_P(
		long companyId, long groupId, long ownerId, int ownerType,
		String portletId, boolean privateLayout) {

		Object[] finderArgs = {
			companyId, groupId, ownerId, ownerType, portletId, privateLayout
		};

		List<PortletPreferences> list =
			(List<PortletPreferences>)FinderCacheUtil.getResult(
				FINDER_PATH_FIND_BY_C_G_O_O_P_P, finderArgs);

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

				SQLQuery sqlQuery = session.createSynchronizedSQLQuery(sql);

				sqlQuery.addEntity(
					"PortletPreferences", PortletPreferencesImpl.class);

				QueryPos queryPos = QueryPos.getInstance(sqlQuery);

				queryPos.add(companyId);
				queryPos.add(groupId);
				queryPos.add(ownerId);
				queryPos.add(ownerType);
				queryPos.add(portletId);
				queryPos.add(portletId.concat("_INSTANCE_%"));
				queryPos.add(privateLayout);

				list = sqlQuery.list(true);

				PortletPreferencesUtil.cacheResult(list);

				FinderCacheUtil.putResult(
					FINDER_PATH_FIND_BY_C_G_O_O_P_P, finderArgs, list);
			}
			catch (Exception exception) {
				throw new SystemException(exception);
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

		String portletName = PortletIdCodec.decodePortletName(
			portletPreferencesPortletId);

		return Objects.equals(portletName, portletId);
	}

}