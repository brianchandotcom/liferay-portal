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

package com.liferay.layout.service.persistence.impl;

import com.liferay.layout.exception.NoSuchLayoutLocalizationException;
import com.liferay.layout.model.LayoutLocalization;
import com.liferay.layout.model.LayoutLocalizationTable;
import com.liferay.layout.model.impl.LayoutLocalizationImpl;
import com.liferay.layout.model.impl.LayoutLocalizationModelImpl;
import com.liferay.layout.service.persistence.LayoutLocalizationPersistence;
import com.liferay.layout.service.persistence.LayoutLocalizationUtil;
import com.liferay.layout.service.persistence.impl.constants.LayoutPersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUID;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the layout localization service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = LayoutLocalizationPersistence.class)
public class LayoutLocalizationPersistenceImpl
	extends BasePersistenceImpl<LayoutLocalization>
	implements LayoutLocalizationPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutLocalizationUtil</code> to access the layout localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutLocalizationImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the layout localizations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout localizations
	 */
	@Override
	public List<LayoutLocalization> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout localizations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @return the range of matching layout localizations
	 */
	@Override
	public List<LayoutLocalization> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout localizations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout localizations
	 */
	@Override
	public List<LayoutLocalization> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout localizations where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout localizations
	 */
	@Override
	public List<LayoutLocalization> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<LayoutLocalization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutLocalization>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutLocalization layoutLocalization : list) {
					if (!uuid.equals(layoutLocalization.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_LAYOUTLOCALIZATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<LayoutLocalization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization findByUuid_First(
			String uuid,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = fetchByUuid_First(
			uuid, orderByComparator);

		if (layoutLocalization != null) {
			return layoutLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchLayoutLocalizationException(sb.toString());
	}

	/**
	 * Returns the first layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization fetchByUuid_First(
		String uuid, OrderByComparator<LayoutLocalization> orderByComparator) {

		List<LayoutLocalization> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization findByUuid_Last(
			String uuid,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = fetchByUuid_Last(
			uuid, orderByComparator);

		if (layoutLocalization != null) {
			return layoutLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchLayoutLocalizationException(sb.toString());
	}

	/**
	 * Returns the last layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization fetchByUuid_Last(
		String uuid, OrderByComparator<LayoutLocalization> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LayoutLocalization> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout localizations before and after the current layout localization in the ordered set where uuid = &#63;.
	 *
	 * @param layoutLocalizationId the primary key of the current layout localization
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout localization
	 * @throws NoSuchLayoutLocalizationException if a layout localization with the primary key could not be found
	 */
	@Override
	public LayoutLocalization[] findByUuid_PrevAndNext(
			long layoutLocalizationId, String uuid,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws NoSuchLayoutLocalizationException {

		uuid = Objects.toString(uuid, "");

		LayoutLocalization layoutLocalization = findByPrimaryKey(
			layoutLocalizationId);

		Session session = null;

		try {
			session = openSession();

			LayoutLocalization[] array = new LayoutLocalizationImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, layoutLocalization, uuid, orderByComparator, true);

			array[1] = layoutLocalization;

			array[2] = getByUuid_PrevAndNext(
				session, layoutLocalization, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutLocalization getByUuid_PrevAndNext(
		Session session, LayoutLocalization layoutLocalization, String uuid,
		OrderByComparator<LayoutLocalization> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LAYOUTLOCALIZATION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(LayoutLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutLocalization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutLocalization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout localizations where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LayoutLocalization layoutLocalization :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutLocalization);
		}
	}

	/**
	 * Returns the number of layout localizations where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout localizations
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LAYOUTLOCALIZATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"layoutLocalization.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(layoutLocalization.uuid IS NULL OR layoutLocalization.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the layout localization where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchLayoutLocalizationException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization findByUUID_G(String uuid, long groupId)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = fetchByUUID_G(uuid, groupId);

		if (layoutLocalization == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLayoutLocalizationException(sb.toString());
		}

		return layoutLocalization;
	}

	/**
	 * Returns the layout localization where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the layout localization where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof LayoutLocalization) {
			LayoutLocalization layoutLocalization = (LayoutLocalization)result;

			if (!Objects.equals(uuid, layoutLocalization.getUuid()) ||
				(groupId != layoutLocalization.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LAYOUTLOCALIZATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				List<LayoutLocalization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					LayoutLocalization layoutLocalization = list.get(0);

					result = layoutLocalization;

					cacheResult(layoutLocalization);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (LayoutLocalization)result;
		}
	}

	/**
	 * Removes the layout localization where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout localization that was removed
	 */
	@Override
	public LayoutLocalization removeByUUID_G(String uuid, long groupId)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = findByUUID_G(uuid, groupId);

		return remove(layoutLocalization);
	}

	/**
	 * Returns the number of layout localizations where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout localizations
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAYOUTLOCALIZATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(groupId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"layoutLocalization.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(layoutLocalization.uuid IS NULL OR layoutLocalization.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"layoutLocalization.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout localizations
	 */
	@Override
	public List<LayoutLocalization> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @return the range of matching layout localizations
	 */
	@Override
	public List<LayoutLocalization> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout localizations
	 */
	@Override
	public List<LayoutLocalization> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout localizations
	 */
	@Override
	public List<LayoutLocalization> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<LayoutLocalization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutLocalization>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutLocalization layoutLocalization : list) {
					if (!uuid.equals(layoutLocalization.getUuid()) ||
						(companyId != layoutLocalization.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_LAYOUTLOCALIZATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<LayoutLocalization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (layoutLocalization != null) {
			return layoutLocalization;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchLayoutLocalizationException(sb.toString());
	}

	/**
	 * Returns the first layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutLocalization> orderByComparator) {

		List<LayoutLocalization> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (layoutLocalization != null) {
			return layoutLocalization;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchLayoutLocalizationException(sb.toString());
	}

	/**
	 * Returns the last layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutLocalization> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutLocalization> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout localizations before and after the current layout localization in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutLocalizationId the primary key of the current layout localization
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout localization
	 * @throws NoSuchLayoutLocalizationException if a layout localization with the primary key could not be found
	 */
	@Override
	public LayoutLocalization[] findByUuid_C_PrevAndNext(
			long layoutLocalizationId, String uuid, long companyId,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws NoSuchLayoutLocalizationException {

		uuid = Objects.toString(uuid, "");

		LayoutLocalization layoutLocalization = findByPrimaryKey(
			layoutLocalizationId);

		Session session = null;

		try {
			session = openSession();

			LayoutLocalization[] array = new LayoutLocalizationImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, layoutLocalization, uuid, companyId, orderByComparator,
				true);

			array[1] = layoutLocalization;

			array[2] = getByUuid_C_PrevAndNext(
				session, layoutLocalization, uuid, companyId, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutLocalization getByUuid_C_PrevAndNext(
		Session session, LayoutLocalization layoutLocalization, String uuid,
		long companyId, OrderByComparator<LayoutLocalization> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_LAYOUTLOCALIZATION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(LayoutLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutLocalization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutLocalization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout localizations where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LayoutLocalization layoutLocalization :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutLocalization);
		}
	}

	/**
	 * Returns the number of layout localizations where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout localizations
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAYOUTLOCALIZATION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"layoutLocalization.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(layoutLocalization.uuid IS NULL OR layoutLocalization.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"layoutLocalization.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByPlid;
	private FinderPath _finderPathWithoutPaginationFindByPlid;
	private FinderPath _finderPathCountByPlid;

	/**
	 * Returns all the layout localizations where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching layout localizations
	 */
	@Override
	public List<LayoutLocalization> findByPlid(long plid) {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout localizations where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @return the range of matching layout localizations
	 */
	@Override
	public List<LayoutLocalization> findByPlid(long plid, int start, int end) {
		return findByPlid(plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout localizations where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout localizations
	 */
	@Override
	public List<LayoutLocalization> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator) {

		return findByPlid(plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout localizations where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout localizations
	 */
	@Override
	public List<LayoutLocalization> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByPlid;
				finderArgs = new Object[] {plid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByPlid;
			finderArgs = new Object[] {plid, start, end, orderByComparator};
		}

		List<LayoutLocalization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutLocalization>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutLocalization layoutLocalization : list) {
					if (plid != layoutLocalization.getPlid()) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_LAYOUTLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(LayoutLocalizationModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(plid);

				list = (List<LayoutLocalization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first layout localization in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization findByPlid_First(
			long plid, OrderByComparator<LayoutLocalization> orderByComparator)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = fetchByPlid_First(
			plid, orderByComparator);

		if (layoutLocalization != null) {
			return layoutLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchLayoutLocalizationException(sb.toString());
	}

	/**
	 * Returns the first layout localization in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization fetchByPlid_First(
		long plid, OrderByComparator<LayoutLocalization> orderByComparator) {

		List<LayoutLocalization> list = findByPlid(
			plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout localization in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization findByPlid_Last(
			long plid, OrderByComparator<LayoutLocalization> orderByComparator)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = fetchByPlid_Last(
			plid, orderByComparator);

		if (layoutLocalization != null) {
			return layoutLocalization;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchLayoutLocalizationException(sb.toString());
	}

	/**
	 * Returns the last layout localization in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization fetchByPlid_Last(
		long plid, OrderByComparator<LayoutLocalization> orderByComparator) {

		int count = countByPlid(plid);

		if (count == 0) {
			return null;
		}

		List<LayoutLocalization> list = findByPlid(
			plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout localizations before and after the current layout localization in the ordered set where plid = &#63;.
	 *
	 * @param layoutLocalizationId the primary key of the current layout localization
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout localization
	 * @throws NoSuchLayoutLocalizationException if a layout localization with the primary key could not be found
	 */
	@Override
	public LayoutLocalization[] findByPlid_PrevAndNext(
			long layoutLocalizationId, long plid,
			OrderByComparator<LayoutLocalization> orderByComparator)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = findByPrimaryKey(
			layoutLocalizationId);

		Session session = null;

		try {
			session = openSession();

			LayoutLocalization[] array = new LayoutLocalizationImpl[3];

			array[0] = getByPlid_PrevAndNext(
				session, layoutLocalization, plid, orderByComparator, true);

			array[1] = layoutLocalization;

			array[2] = getByPlid_PrevAndNext(
				session, layoutLocalization, plid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutLocalization getByPlid_PrevAndNext(
		Session session, LayoutLocalization layoutLocalization, long plid,
		OrderByComparator<LayoutLocalization> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LAYOUTLOCALIZATION_WHERE);

		sb.append(_FINDER_COLUMN_PLID_PLID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(LayoutLocalizationModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutLocalization)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutLocalization> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout localizations where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	@Override
	public void removeByPlid(long plid) {
		for (LayoutLocalization layoutLocalization :
				findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutLocalization);
		}
	}

	/**
	 * Returns the number of layout localizations where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout localizations
	 */
	@Override
	public int countByPlid(long plid) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByPlid;

			finderArgs = new Object[] {plid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LAYOUTLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_PLID_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(plid);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_PLID_PLID_2 =
		"layoutLocalization.plid = ?";

	private FinderPath _finderPathFetchByL_P;
	private FinderPath _finderPathCountByL_P;

	/**
	 * Returns the layout localization where languageId = &#63; and plid = &#63; or throws a <code>NoSuchLayoutLocalizationException</code> if it could not be found.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization findByL_P(String languageId, long plid)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = fetchByL_P(languageId, plid);

		if (layoutLocalization == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("languageId=");
			sb.append(languageId);

			sb.append(", plid=");
			sb.append(plid);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLayoutLocalizationException(sb.toString());
		}

		return layoutLocalization;
	}

	/**
	 * Returns the layout localization where languageId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization fetchByL_P(String languageId, long plid) {
		return fetchByL_P(languageId, plid, true);
	}

	/**
	 * Returns the layout localization where languageId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization fetchByL_P(
		String languageId, long plid, boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {languageId, plid};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByL_P, finderArgs, this);
		}

		if (result instanceof LayoutLocalization) {
			LayoutLocalization layoutLocalization = (LayoutLocalization)result;

			if (!Objects.equals(
					languageId, layoutLocalization.getLanguageId()) ||
				(plid != layoutLocalization.getPlid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LAYOUTLOCALIZATION_WHERE);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_L_P_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_L_P_LANGUAGEID_2);
			}

			sb.append(_FINDER_COLUMN_L_P_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				queryPos.add(plid);

				List<LayoutLocalization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByL_P, finderArgs, list);
					}
				}
				else {
					LayoutLocalization layoutLocalization = list.get(0);

					result = layoutLocalization;

					cacheResult(layoutLocalization);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (LayoutLocalization)result;
		}
	}

	/**
	 * Removes the layout localization where languageId = &#63; and plid = &#63; from the database.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the layout localization that was removed
	 */
	@Override
	public LayoutLocalization removeByL_P(String languageId, long plid)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = findByL_P(languageId, plid);

		return remove(layoutLocalization);
	}

	/**
	 * Returns the number of layout localizations where languageId = &#63; and plid = &#63;.
	 *
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the number of matching layout localizations
	 */
	@Override
	public int countByL_P(String languageId, long plid) {
		languageId = Objects.toString(languageId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByL_P;

			finderArgs = new Object[] {languageId, plid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAYOUTLOCALIZATION_WHERE);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_L_P_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_L_P_LANGUAGEID_2);
			}

			sb.append(_FINDER_COLUMN_L_P_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				queryPos.add(plid);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_L_P_LANGUAGEID_2 =
		"layoutLocalization.languageId = ? AND ";

	private static final String _FINDER_COLUMN_L_P_LANGUAGEID_3 =
		"(layoutLocalization.languageId IS NULL OR layoutLocalization.languageId = '') AND ";

	private static final String _FINDER_COLUMN_L_P_PLID_2 =
		"layoutLocalization.plid = ?";

	private FinderPath _finderPathFetchByG_L_P;
	private FinderPath _finderPathCountByG_L_P;

	/**
	 * Returns the layout localization where groupId = &#63; and languageId = &#63; and plid = &#63; or throws a <code>NoSuchLayoutLocalizationException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the matching layout localization
	 * @throws NoSuchLayoutLocalizationException if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization findByG_L_P(
			long groupId, String languageId, long plid)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = fetchByG_L_P(
			groupId, languageId, plid);

		if (layoutLocalization == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", languageId=");
			sb.append(languageId);

			sb.append(", plid=");
			sb.append(plid);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchLayoutLocalizationException(sb.toString());
		}

		return layoutLocalization;
	}

	/**
	 * Returns the layout localization where groupId = &#63; and languageId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization fetchByG_L_P(
		long groupId, String languageId, long plid) {

		return fetchByG_L_P(groupId, languageId, plid, true);
	}

	/**
	 * Returns the layout localization where groupId = &#63; and languageId = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout localization, or <code>null</code> if a matching layout localization could not be found
	 */
	@Override
	public LayoutLocalization fetchByG_L_P(
		long groupId, String languageId, long plid, boolean useFinderCache) {

		languageId = Objects.toString(languageId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, languageId, plid};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_L_P, finderArgs, this);
		}

		if (result instanceof LayoutLocalization) {
			LayoutLocalization layoutLocalization = (LayoutLocalization)result;

			if ((groupId != layoutLocalization.getGroupId()) ||
				!Objects.equals(
					languageId, layoutLocalization.getLanguageId()) ||
				(plid != layoutLocalization.getPlid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_LAYOUTLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_G_L_P_GROUPID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_L_P_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_G_L_P_LANGUAGEID_2);
			}

			sb.append(_FINDER_COLUMN_G_L_P_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				queryPos.add(plid);

				List<LayoutLocalization> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_L_P, finderArgs, list);
					}
				}
				else {
					LayoutLocalization layoutLocalization = list.get(0);

					result = layoutLocalization;

					cacheResult(layoutLocalization);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (LayoutLocalization)result;
		}
	}

	/**
	 * Removes the layout localization where groupId = &#63; and languageId = &#63; and plid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the layout localization that was removed
	 */
	@Override
	public LayoutLocalization removeByG_L_P(
			long groupId, String languageId, long plid)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = findByG_L_P(
			groupId, languageId, plid);

		return remove(layoutLocalization);
	}

	/**
	 * Returns the number of layout localizations where groupId = &#63; and languageId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param languageId the language ID
	 * @param plid the plid
	 * @return the number of matching layout localizations
	 */
	@Override
	public int countByG_L_P(long groupId, String languageId, long plid) {
		languageId = Objects.toString(languageId, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_L_P;

			finderArgs = new Object[] {groupId, languageId, plid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_LAYOUTLOCALIZATION_WHERE);

			sb.append(_FINDER_COLUMN_G_L_P_GROUPID_2);

			boolean bindLanguageId = false;

			if (languageId.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_L_P_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				sb.append(_FINDER_COLUMN_G_L_P_LANGUAGEID_2);
			}

			sb.append(_FINDER_COLUMN_G_L_P_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindLanguageId) {
					queryPos.add(languageId);
				}

				queryPos.add(plid);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_G_L_P_GROUPID_2 =
		"layoutLocalization.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_L_P_LANGUAGEID_2 =
		"layoutLocalization.languageId = ? AND ";

	private static final String _FINDER_COLUMN_G_L_P_LANGUAGEID_3 =
		"(layoutLocalization.languageId IS NULL OR layoutLocalization.languageId = '') AND ";

	private static final String _FINDER_COLUMN_G_L_P_PLID_2 =
		"layoutLocalization.plid = ?";

	public LayoutLocalizationPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(LayoutLocalization.class);

		setModelImplClass(LayoutLocalizationImpl.class);
		setModelPKClass(long.class);

		setTable(LayoutLocalizationTable.INSTANCE);
	}

	/**
	 * Caches the layout localization in the entity cache if it is enabled.
	 *
	 * @param layoutLocalization the layout localization
	 */
	@Override
	public void cacheResult(LayoutLocalization layoutLocalization) {
		if (layoutLocalization.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			LayoutLocalizationImpl.class, layoutLocalization.getPrimaryKey(),
			layoutLocalization);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				layoutLocalization.getUuid(), layoutLocalization.getGroupId()
			},
			layoutLocalization);

		finderCache.putResult(
			_finderPathFetchByL_P,
			new Object[] {
				layoutLocalization.getLanguageId(), layoutLocalization.getPlid()
			},
			layoutLocalization);

		finderCache.putResult(
			_finderPathFetchByG_L_P,
			new Object[] {
				layoutLocalization.getGroupId(),
				layoutLocalization.getLanguageId(), layoutLocalization.getPlid()
			},
			layoutLocalization);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the layout localizations in the entity cache if it is enabled.
	 *
	 * @param layoutLocalizations the layout localizations
	 */
	@Override
	public void cacheResult(List<LayoutLocalization> layoutLocalizations) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (layoutLocalizations.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LayoutLocalization layoutLocalization : layoutLocalizations) {
			if (layoutLocalization.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					LayoutLocalizationImpl.class,
					layoutLocalization.getPrimaryKey()) == null) {

				cacheResult(layoutLocalization);
			}
		}
	}

	/**
	 * Clears the cache for all layout localizations.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutLocalizationImpl.class);

		finderCache.clearCache(LayoutLocalizationImpl.class);
	}

	/**
	 * Clears the cache for the layout localization.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutLocalization layoutLocalization) {
		entityCache.removeResult(
			LayoutLocalizationImpl.class, layoutLocalization);
	}

	@Override
	public void clearCache(List<LayoutLocalization> layoutLocalizations) {
		for (LayoutLocalization layoutLocalization : layoutLocalizations) {
			entityCache.removeResult(
				LayoutLocalizationImpl.class, layoutLocalization);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(LayoutLocalizationImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(LayoutLocalizationImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutLocalizationModelImpl layoutLocalizationModelImpl) {

		Object[] args = new Object[] {
			layoutLocalizationModelImpl.getUuid(),
			layoutLocalizationModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, layoutLocalizationModelImpl);

		args = new Object[] {
			layoutLocalizationModelImpl.getLanguageId(),
			layoutLocalizationModelImpl.getPlid()
		};

		finderCache.putResult(_finderPathCountByL_P, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByL_P, args, layoutLocalizationModelImpl);

		args = new Object[] {
			layoutLocalizationModelImpl.getGroupId(),
			layoutLocalizationModelImpl.getLanguageId(),
			layoutLocalizationModelImpl.getPlid()
		};

		finderCache.putResult(_finderPathCountByG_L_P, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_L_P, args, layoutLocalizationModelImpl);
	}

	/**
	 * Creates a new layout localization with the primary key. Does not add the layout localization to the database.
	 *
	 * @param layoutLocalizationId the primary key for the new layout localization
	 * @return the new layout localization
	 */
	@Override
	public LayoutLocalization create(long layoutLocalizationId) {
		LayoutLocalization layoutLocalization = new LayoutLocalizationImpl();

		layoutLocalization.setNew(true);
		layoutLocalization.setPrimaryKey(layoutLocalizationId);

		String uuid = _portalUUID.generate();

		layoutLocalization.setUuid(uuid);

		layoutLocalization.setCompanyId(CompanyThreadLocal.getCompanyId());

		return layoutLocalization;
	}

	/**
	 * Removes the layout localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutLocalizationId the primary key of the layout localization
	 * @return the layout localization that was removed
	 * @throws NoSuchLayoutLocalizationException if a layout localization with the primary key could not be found
	 */
	@Override
	public LayoutLocalization remove(long layoutLocalizationId)
		throws NoSuchLayoutLocalizationException {

		return remove((Serializable)layoutLocalizationId);
	}

	/**
	 * Removes the layout localization with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout localization
	 * @return the layout localization that was removed
	 * @throws NoSuchLayoutLocalizationException if a layout localization with the primary key could not be found
	 */
	@Override
	public LayoutLocalization remove(Serializable primaryKey)
		throws NoSuchLayoutLocalizationException {

		Session session = null;

		try {
			session = openSession();

			LayoutLocalization layoutLocalization =
				(LayoutLocalization)session.get(
					LayoutLocalizationImpl.class, primaryKey);

			if (layoutLocalization == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutLocalizationException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutLocalization);
		}
		catch (NoSuchLayoutLocalizationException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected LayoutLocalization removeImpl(
		LayoutLocalization layoutLocalization) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutLocalization)) {
				layoutLocalization = (LayoutLocalization)session.get(
					LayoutLocalizationImpl.class,
					layoutLocalization.getPrimaryKeyObj());
			}

			if ((layoutLocalization != null) &&
				ctPersistenceHelper.isRemove(layoutLocalization)) {

				session.delete(layoutLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (layoutLocalization != null) {
			clearCache(layoutLocalization);
		}

		return layoutLocalization;
	}

	@Override
	public LayoutLocalization updateImpl(
		LayoutLocalization layoutLocalization) {

		boolean isNew = layoutLocalization.isNew();

		if (!(layoutLocalization instanceof LayoutLocalizationModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutLocalization.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutLocalization);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutLocalization proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutLocalization implementation " +
					layoutLocalization.getClass());
		}

		LayoutLocalizationModelImpl layoutLocalizationModelImpl =
			(LayoutLocalizationModelImpl)layoutLocalization;

		if (Validator.isNull(layoutLocalization.getUuid())) {
			String uuid = _portalUUID.generate();

			layoutLocalization.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (layoutLocalization.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutLocalization.setCreateDate(date);
			}
			else {
				layoutLocalization.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!layoutLocalizationModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutLocalization.setModifiedDate(date);
			}
			else {
				layoutLocalization.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(layoutLocalization)) {
				if (!isNew) {
					session.evict(
						LayoutLocalizationImpl.class,
						layoutLocalization.getPrimaryKeyObj());
				}

				session.save(layoutLocalization);
			}
			else {
				layoutLocalization = (LayoutLocalization)session.merge(
					layoutLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (layoutLocalization.getCtCollectionId() != 0) {
			if (isNew) {
				layoutLocalization.setNew(false);
			}

			layoutLocalization.resetOriginalValues();

			return layoutLocalization;
		}

		entityCache.putResult(
			LayoutLocalizationImpl.class, layoutLocalizationModelImpl, false,
			true);

		cacheUniqueFindersCache(layoutLocalizationModelImpl);

		if (isNew) {
			layoutLocalization.setNew(false);
		}

		layoutLocalization.resetOriginalValues();

		return layoutLocalization;
	}

	/**
	 * Returns the layout localization with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout localization
	 * @return the layout localization
	 * @throws NoSuchLayoutLocalizationException if a layout localization with the primary key could not be found
	 */
	@Override
	public LayoutLocalization findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLayoutLocalizationException {

		LayoutLocalization layoutLocalization = fetchByPrimaryKey(primaryKey);

		if (layoutLocalization == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLayoutLocalizationException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutLocalization;
	}

	/**
	 * Returns the layout localization with the primary key or throws a <code>NoSuchLayoutLocalizationException</code> if it could not be found.
	 *
	 * @param layoutLocalizationId the primary key of the layout localization
	 * @return the layout localization
	 * @throws NoSuchLayoutLocalizationException if a layout localization with the primary key could not be found
	 */
	@Override
	public LayoutLocalization findByPrimaryKey(long layoutLocalizationId)
		throws NoSuchLayoutLocalizationException {

		return findByPrimaryKey((Serializable)layoutLocalizationId);
	}

	/**
	 * Returns the layout localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout localization
	 * @return the layout localization, or <code>null</code> if a layout localization with the primary key could not be found
	 */
	@Override
	public LayoutLocalization fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(
				LayoutLocalization.class, primaryKey)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		LayoutLocalization layoutLocalization = null;

		Session session = null;

		try {
			session = openSession();

			layoutLocalization = (LayoutLocalization)session.get(
				LayoutLocalizationImpl.class, primaryKey);

			if (layoutLocalization != null) {
				cacheResult(layoutLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return layoutLocalization;
	}

	/**
	 * Returns the layout localization with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutLocalizationId the primary key of the layout localization
	 * @return the layout localization, or <code>null</code> if a layout localization with the primary key could not be found
	 */
	@Override
	public LayoutLocalization fetchByPrimaryKey(long layoutLocalizationId) {
		return fetchByPrimaryKey((Serializable)layoutLocalizationId);
	}

	@Override
	public Map<Serializable, LayoutLocalization> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(LayoutLocalization.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LayoutLocalization> map =
			new HashMap<Serializable, LayoutLocalization>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LayoutLocalization layoutLocalization = fetchByPrimaryKey(
				primaryKey);

			if (layoutLocalization != null) {
				map.put(primaryKey, layoutLocalization);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (LayoutLocalization layoutLocalization :
					(List<LayoutLocalization>)query.list()) {

				map.put(
					layoutLocalization.getPrimaryKeyObj(), layoutLocalization);

				cacheResult(layoutLocalization);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the layout localizations.
	 *
	 * @return the layout localizations
	 */
	@Override
	public List<LayoutLocalization> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @return the range of layout localizations
	 */
	@Override
	public List<LayoutLocalization> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout localizations
	 */
	@Override
	public List<LayoutLocalization> findAll(
		int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout localizations.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutLocalizationModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout localizations
	 * @param end the upper bound of the range of layout localizations (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout localizations
	 */
	@Override
	public List<LayoutLocalization> findAll(
		int start, int end,
		OrderByComparator<LayoutLocalization> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<LayoutLocalization> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutLocalization>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LAYOUTLOCALIZATION);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTLOCALIZATION;

				sql = sql.concat(LayoutLocalizationModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LayoutLocalization>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the layout localizations from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutLocalization layoutLocalization : findAll()) {
			remove(layoutLocalization);
		}
	}

	/**
	 * Returns the number of layout localizations.
	 *
	 * @return the number of layout localizations
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutLocalization.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_LAYOUTLOCALIZATION);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "layoutLocalizationId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTLOCALIZATION;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return LayoutLocalizationModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "LayoutLocalization";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctMergeColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctMergeColumnNames.add("content");
		ctStrictColumnNames.add("languageId");
		ctStrictColumnNames.add("plid");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("layoutLocalizationId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(new String[] {"languageId", "plid"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "languageId", "plid"});
	}

	/**
	 * Initializes the layout localization persistence.
	 */
	@Activate
	public void activate() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathFetchByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByPlid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByPlid",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"plid"}, true);

		_finderPathWithoutPaginationFindByPlid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByPlid",
			new String[] {Long.class.getName()}, new String[] {"plid"}, true);

		_finderPathCountByPlid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] {Long.class.getName()}, new String[] {"plid"}, false);

		_finderPathFetchByL_P = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByL_P",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"languageId", "plid"}, true);

		_finderPathCountByL_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"languageId", "plid"}, false);

		_finderPathFetchByG_L_P = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_L_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			new String[] {"groupId", "languageId", "plid"}, true);

		_finderPathCountByG_L_P = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_L_P",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Long.class.getName()
			},
			new String[] {"groupId", "languageId", "plid"}, false);

		LayoutLocalizationUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		LayoutLocalizationUtil.setPersistence(null);

		entityCache.removeCache(LayoutLocalizationImpl.class.getName());
	}

	@Override
	@Reference(
		target = LayoutPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = LayoutPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = LayoutPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected CTPersistenceHelper ctPersistenceHelper;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_LAYOUTLOCALIZATION =
		"SELECT layoutLocalization FROM LayoutLocalization layoutLocalization";

	private static final String _SQL_SELECT_LAYOUTLOCALIZATION_WHERE =
		"SELECT layoutLocalization FROM LayoutLocalization layoutLocalization WHERE ";

	private static final String _SQL_COUNT_LAYOUTLOCALIZATION =
		"SELECT COUNT(layoutLocalization) FROM LayoutLocalization layoutLocalization";

	private static final String _SQL_COUNT_LAYOUTLOCALIZATION_WHERE =
		"SELECT COUNT(layoutLocalization) FROM LayoutLocalization layoutLocalization WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutLocalization.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutLocalization exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutLocalization exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutLocalizationPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private PortalUUID _portalUUID;

}