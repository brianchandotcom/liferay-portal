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

package com.liferay.layout.seo.service.persistence.impl;

import com.liferay.layout.seo.exception.NoSuchEntryException;
import com.liferay.layout.seo.model.LayoutSEOEntry;
import com.liferay.layout.seo.model.LayoutSEOEntryTable;
import com.liferay.layout.seo.model.impl.LayoutSEOEntryImpl;
import com.liferay.layout.seo.model.impl.LayoutSEOEntryModelImpl;
import com.liferay.layout.seo.service.persistence.LayoutSEOEntryPersistence;
import com.liferay.layout.seo.service.persistence.impl.constants.LayoutSEOPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

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
 * The persistence implementation for the layout seo entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {LayoutSEOEntryPersistence.class, BasePersistence.class})
public class LayoutSEOEntryPersistenceImpl
	extends BasePersistenceImpl<LayoutSEOEntry>
	implements LayoutSEOEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutSEOEntryUtil</code> to access the layout seo entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutSEOEntryImpl.class.getName();

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
	 * Returns all the layout seo entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout seo entries
	 */
	@Override
	public List<LayoutSEOEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout seo entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @return the range of matching layout seo entries
	 */
	@Override
	public List<LayoutSEOEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo entries
	 */
	@Override
	public List<LayoutSEOEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout seo entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo entries
	 */
	@Override
	public List<LayoutSEOEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutSEOEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOEntry.class);

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

		List<LayoutSEOEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutSEOEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSEOEntry layoutSEOEntry : list) {
					if (!uuid.equals(layoutSEOEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_LAYOUTSEOENTRY_WHERE);

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
				sb.append(LayoutSEOEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<LayoutSEOEntry>)QueryUtil.list(
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
	 * Returns the first layout seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo entry
	 * @throws NoSuchEntryException if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry findByUuid_First(
			String uuid, OrderByComparator<LayoutSEOEntry> orderByComparator)
		throws NoSuchEntryException {

		LayoutSEOEntry layoutSEOEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (layoutSEOEntry != null) {
			return layoutSEOEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first layout seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry fetchByUuid_First(
		String uuid, OrderByComparator<LayoutSEOEntry> orderByComparator) {

		List<LayoutSEOEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo entry
	 * @throws NoSuchEntryException if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry findByUuid_Last(
			String uuid, OrderByComparator<LayoutSEOEntry> orderByComparator)
		throws NoSuchEntryException {

		LayoutSEOEntry layoutSEOEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (layoutSEOEntry != null) {
			return layoutSEOEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last layout seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry fetchByUuid_Last(
		String uuid, OrderByComparator<LayoutSEOEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LayoutSEOEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout seo entries before and after the current layout seo entry in the ordered set where uuid = &#63;.
	 *
	 * @param layoutSEOEntryId the primary key of the current layout seo entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo entry
	 * @throws NoSuchEntryException if a layout seo entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntry[] findByUuid_PrevAndNext(
			long layoutSEOEntryId, String uuid,
			OrderByComparator<LayoutSEOEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		LayoutSEOEntry layoutSEOEntry = findByPrimaryKey(layoutSEOEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutSEOEntry[] array = new LayoutSEOEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, layoutSEOEntry, uuid, orderByComparator, true);

			array[1] = layoutSEOEntry;

			array[2] = getByUuid_PrevAndNext(
				session, layoutSEOEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSEOEntry getByUuid_PrevAndNext(
		Session session, LayoutSEOEntry layoutSEOEntry, String uuid,
		OrderByComparator<LayoutSEOEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_LAYOUTSEOENTRY_WHERE);

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
			sb.append(LayoutSEOEntryModelImpl.ORDER_BY_JPQL);
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
						layoutSEOEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutSEOEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout seo entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LayoutSEOEntry layoutSEOEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutSEOEntry);
		}
	}

	/**
	 * Returns the number of layout seo entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout seo entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_LAYOUTSEOENTRY_WHERE);

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
		"layoutSEOEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(layoutSEOEntry.uuid IS NULL OR layoutSEOEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the layout seo entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo entry
	 * @throws NoSuchEntryException if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		LayoutSEOEntry layoutSEOEntry = fetchByUUID_G(uuid, groupId);

		if (layoutSEOEntry == null) {
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

			throw new NoSuchEntryException(sb.toString());
		}

		return layoutSEOEntry;
	}

	/**
	 * Returns the layout seo entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the layout seo entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs);
		}

		if (result instanceof LayoutSEOEntry) {
			LayoutSEOEntry layoutSEOEntry = (LayoutSEOEntry)result;

			if (!Objects.equals(uuid, layoutSEOEntry.getUuid()) ||
				(groupId != layoutSEOEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_LAYOUTSEOENTRY_WHERE);

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

				List<LayoutSEOEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					LayoutSEOEntry layoutSEOEntry = list.get(0);

					result = layoutSEOEntry;

					cacheResult(layoutSEOEntry);
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
			return (LayoutSEOEntry)result;
		}
	}

	/**
	 * Removes the layout seo entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the layout seo entry that was removed
	 */
	@Override
	public LayoutSEOEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		LayoutSEOEntry layoutSEOEntry = findByUUID_G(uuid, groupId);

		return remove(layoutSEOEntry);
	}

	/**
	 * Returns the number of layout seo entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching layout seo entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G;

			finderArgs = new Object[] {uuid, groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAYOUTSEOENTRY_WHERE);

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
		"layoutSEOEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(layoutSEOEntry.uuid IS NULL OR layoutSEOEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"layoutSEOEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the layout seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout seo entries
	 */
	@Override
	public List<LayoutSEOEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @return the range of matching layout seo entries
	 */
	@Override
	public List<LayoutSEOEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout seo entries
	 */
	@Override
	public List<LayoutSEOEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout seo entries
	 */
	@Override
	public List<LayoutSEOEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutSEOEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOEntry.class);

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

		List<LayoutSEOEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutSEOEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSEOEntry layoutSEOEntry : list) {
					if (!uuid.equals(layoutSEOEntry.getUuid()) ||
						(companyId != layoutSEOEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_LAYOUTSEOENTRY_WHERE);

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
				sb.append(LayoutSEOEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<LayoutSEOEntry>)QueryUtil.list(
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
	 * Returns the first layout seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo entry
	 * @throws NoSuchEntryException if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOEntry> orderByComparator)
		throws NoSuchEntryException {

		LayoutSEOEntry layoutSEOEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (layoutSEOEntry != null) {
			return layoutSEOEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first layout seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOEntry> orderByComparator) {

		List<LayoutSEOEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo entry
	 * @throws NoSuchEntryException if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutSEOEntry> orderByComparator)
		throws NoSuchEntryException {

		LayoutSEOEntry layoutSEOEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (layoutSEOEntry != null) {
			return layoutSEOEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last layout seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutSEOEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutSEOEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout seo entries before and after the current layout seo entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutSEOEntryId the primary key of the current layout seo entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout seo entry
	 * @throws NoSuchEntryException if a layout seo entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntry[] findByUuid_C_PrevAndNext(
			long layoutSEOEntryId, String uuid, long companyId,
			OrderByComparator<LayoutSEOEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		LayoutSEOEntry layoutSEOEntry = findByPrimaryKey(layoutSEOEntryId);

		Session session = null;

		try {
			session = openSession();

			LayoutSEOEntry[] array = new LayoutSEOEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, layoutSEOEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = layoutSEOEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, layoutSEOEntry, uuid, companyId, orderByComparator,
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

	protected LayoutSEOEntry getByUuid_C_PrevAndNext(
		Session session, LayoutSEOEntry layoutSEOEntry, String uuid,
		long companyId, OrderByComparator<LayoutSEOEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_LAYOUTSEOENTRY_WHERE);

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
			sb.append(LayoutSEOEntryModelImpl.ORDER_BY_JPQL);
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
						layoutSEOEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<LayoutSEOEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout seo entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LayoutSEOEntry layoutSEOEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutSEOEntry);
		}
	}

	/**
	 * Returns the number of layout seo entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout seo entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_LAYOUTSEOENTRY_WHERE);

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
		"layoutSEOEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(layoutSEOEntry.uuid IS NULL OR layoutSEOEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"layoutSEOEntry.companyId = ?";

	private FinderPath _finderPathFetchByG_P_L;
	private FinderPath _finderPathCountByG_P_L;

	/**
	 * Returns the layout seo entry where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout seo entry
	 * @throws NoSuchEntryException if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry findByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchEntryException {

		LayoutSEOEntry layoutSEOEntry = fetchByG_P_L(
			groupId, privateLayout, layoutId);

		if (layoutSEOEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", privateLayout=");
			sb.append(privateLayout);

			sb.append(", layoutId=");
			sb.append(layoutId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return layoutSEOEntry;
	}

	/**
	 * Returns the layout seo entry where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		return fetchByG_P_L(groupId, privateLayout, layoutId, true);
	}

	/**
	 * Returns the layout seo entry where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout seo entry, or <code>null</code> if a matching layout seo entry could not be found
	 */
	@Override
	public LayoutSEOEntry fetchByG_P_L(
		long groupId, boolean privateLayout, long layoutId,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, privateLayout, layoutId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(_finderPathFetchByG_P_L, finderArgs);
		}

		if (result instanceof LayoutSEOEntry) {
			LayoutSEOEntry layoutSEOEntry = (LayoutSEOEntry)result;

			if ((groupId != layoutSEOEntry.getGroupId()) ||
				(privateLayout != layoutSEOEntry.isPrivateLayout()) ||
				(layoutId != layoutSEOEntry.getLayoutId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_LAYOUTSEOENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

			sb.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(privateLayout);

				queryPos.add(layoutId);

				List<LayoutSEOEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_P_L, finderArgs, list);
					}
				}
				else {
					LayoutSEOEntry layoutSEOEntry = list.get(0);

					result = layoutSEOEntry;

					cacheResult(layoutSEOEntry);
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
			return (LayoutSEOEntry)result;
		}
	}

	/**
	 * Removes the layout seo entry where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the layout seo entry that was removed
	 */
	@Override
	public LayoutSEOEntry removeByG_P_L(
			long groupId, boolean privateLayout, long layoutId)
		throws NoSuchEntryException {

		LayoutSEOEntry layoutSEOEntry = findByG_P_L(
			groupId, privateLayout, layoutId);

		return remove(layoutSEOEntry);
	}

	/**
	 * Returns the number of layout seo entries where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching layout seo entries
	 */
	@Override
	public int countByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_P_L;

			finderArgs = new Object[] {groupId, privateLayout, layoutId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_LAYOUTSEOENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

			sb.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(privateLayout);

				queryPos.add(layoutId);

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

	private static final String _FINDER_COLUMN_G_P_L_GROUPID_2 =
		"layoutSEOEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2 =
		"layoutSEOEntry.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_LAYOUTID_2 =
		"layoutSEOEntry.layoutId = ?";

	public LayoutSEOEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(LayoutSEOEntry.class);

		setModelImplClass(LayoutSEOEntryImpl.class);
		setModelPKClass(long.class);

		setTable(LayoutSEOEntryTable.INSTANCE);
	}

	/**
	 * Caches the layout seo entry in the entity cache if it is enabled.
	 *
	 * @param layoutSEOEntry the layout seo entry
	 */
	@Override
	public void cacheResult(LayoutSEOEntry layoutSEOEntry) {
		if (layoutSEOEntry.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			LayoutSEOEntryImpl.class, layoutSEOEntry.getPrimaryKey(),
			layoutSEOEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				layoutSEOEntry.getUuid(), layoutSEOEntry.getGroupId()
			},
			layoutSEOEntry);

		finderCache.putResult(
			_finderPathFetchByG_P_L,
			new Object[] {
				layoutSEOEntry.getGroupId(), layoutSEOEntry.isPrivateLayout(),
				layoutSEOEntry.getLayoutId()
			},
			layoutSEOEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the layout seo entries in the entity cache if it is enabled.
	 *
	 * @param layoutSEOEntries the layout seo entries
	 */
	@Override
	public void cacheResult(List<LayoutSEOEntry> layoutSEOEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (layoutSEOEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (LayoutSEOEntry layoutSEOEntry : layoutSEOEntries) {
			if (layoutSEOEntry.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					LayoutSEOEntryImpl.class, layoutSEOEntry.getPrimaryKey()) ==
						null) {

				cacheResult(layoutSEOEntry);
			}
		}
	}

	/**
	 * Clears the cache for all layout seo entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LayoutSEOEntryImpl.class);

		finderCache.clearCache(LayoutSEOEntryImpl.class);
	}

	/**
	 * Clears the cache for the layout seo entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutSEOEntry layoutSEOEntry) {
		entityCache.removeResult(LayoutSEOEntryImpl.class, layoutSEOEntry);
	}

	@Override
	public void clearCache(List<LayoutSEOEntry> layoutSEOEntries) {
		for (LayoutSEOEntry layoutSEOEntry : layoutSEOEntries) {
			entityCache.removeResult(LayoutSEOEntryImpl.class, layoutSEOEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(LayoutSEOEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(LayoutSEOEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutSEOEntryModelImpl layoutSEOEntryModelImpl) {

		Object[] args = new Object[] {
			layoutSEOEntryModelImpl.getUuid(),
			layoutSEOEntryModelImpl.getGroupId()
		};

		finderCache.putResult(_finderPathCountByUUID_G, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, layoutSEOEntryModelImpl);

		args = new Object[] {
			layoutSEOEntryModelImpl.getGroupId(),
			layoutSEOEntryModelImpl.isPrivateLayout(),
			layoutSEOEntryModelImpl.getLayoutId()
		};

		finderCache.putResult(_finderPathCountByG_P_L, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_P_L, args, layoutSEOEntryModelImpl);
	}

	/**
	 * Creates a new layout seo entry with the primary key. Does not add the layout seo entry to the database.
	 *
	 * @param layoutSEOEntryId the primary key for the new layout seo entry
	 * @return the new layout seo entry
	 */
	@Override
	public LayoutSEOEntry create(long layoutSEOEntryId) {
		LayoutSEOEntry layoutSEOEntry = new LayoutSEOEntryImpl();

		layoutSEOEntry.setNew(true);
		layoutSEOEntry.setPrimaryKey(layoutSEOEntryId);

		String uuid = PortalUUIDUtil.generate();

		layoutSEOEntry.setUuid(uuid);

		layoutSEOEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return layoutSEOEntry;
	}

	/**
	 * Removes the layout seo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSEOEntryId the primary key of the layout seo entry
	 * @return the layout seo entry that was removed
	 * @throws NoSuchEntryException if a layout seo entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntry remove(long layoutSEOEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)layoutSEOEntryId);
	}

	/**
	 * Removes the layout seo entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout seo entry
	 * @return the layout seo entry that was removed
	 * @throws NoSuchEntryException if a layout seo entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			LayoutSEOEntry layoutSEOEntry = (LayoutSEOEntry)session.get(
				LayoutSEOEntryImpl.class, primaryKey);

			if (layoutSEOEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutSEOEntry);
		}
		catch (NoSuchEntryException noSuchEntityException) {
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
	protected LayoutSEOEntry removeImpl(LayoutSEOEntry layoutSEOEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutSEOEntry)) {
				layoutSEOEntry = (LayoutSEOEntry)session.get(
					LayoutSEOEntryImpl.class,
					layoutSEOEntry.getPrimaryKeyObj());
			}

			if ((layoutSEOEntry != null) &&
				ctPersistenceHelper.isRemove(layoutSEOEntry)) {

				session.delete(layoutSEOEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (layoutSEOEntry != null) {
			clearCache(layoutSEOEntry);
		}

		return layoutSEOEntry;
	}

	@Override
	public LayoutSEOEntry updateImpl(LayoutSEOEntry layoutSEOEntry) {
		boolean isNew = layoutSEOEntry.isNew();

		if (!(layoutSEOEntry instanceof LayoutSEOEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutSEOEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutSEOEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutSEOEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutSEOEntry implementation " +
					layoutSEOEntry.getClass());
		}

		LayoutSEOEntryModelImpl layoutSEOEntryModelImpl =
			(LayoutSEOEntryModelImpl)layoutSEOEntry;

		if (Validator.isNull(layoutSEOEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			layoutSEOEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (layoutSEOEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutSEOEntry.setCreateDate(date);
			}
			else {
				layoutSEOEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!layoutSEOEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutSEOEntry.setModifiedDate(date);
			}
			else {
				layoutSEOEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(layoutSEOEntry)) {
				if (!isNew) {
					session.evict(
						LayoutSEOEntryImpl.class,
						layoutSEOEntry.getPrimaryKeyObj());
				}

				session.save(layoutSEOEntry);
			}
			else {
				layoutSEOEntry = (LayoutSEOEntry)session.merge(layoutSEOEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (layoutSEOEntry.getCtCollectionId() != 0) {
			if (isNew) {
				layoutSEOEntry.setNew(false);
			}

			layoutSEOEntry.resetOriginalValues();

			return layoutSEOEntry;
		}

		entityCache.putResult(
			LayoutSEOEntryImpl.class, layoutSEOEntryModelImpl, false, true);

		cacheUniqueFindersCache(layoutSEOEntryModelImpl);

		if (isNew) {
			layoutSEOEntry.setNew(false);
		}

		layoutSEOEntry.resetOriginalValues();

		return layoutSEOEntry;
	}

	/**
	 * Returns the layout seo entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout seo entry
	 * @return the layout seo entry
	 * @throws NoSuchEntryException if a layout seo entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		LayoutSEOEntry layoutSEOEntry = fetchByPrimaryKey(primaryKey);

		if (layoutSEOEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutSEOEntry;
	}

	/**
	 * Returns the layout seo entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param layoutSEOEntryId the primary key of the layout seo entry
	 * @return the layout seo entry
	 * @throws NoSuchEntryException if a layout seo entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntry findByPrimaryKey(long layoutSEOEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)layoutSEOEntryId);
	}

	/**
	 * Returns the layout seo entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout seo entry
	 * @return the layout seo entry, or <code>null</code> if a layout seo entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(LayoutSEOEntry.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		LayoutSEOEntry layoutSEOEntry = null;

		Session session = null;

		try {
			session = openSession();

			layoutSEOEntry = (LayoutSEOEntry)session.get(
				LayoutSEOEntryImpl.class, primaryKey);

			if (layoutSEOEntry != null) {
				cacheResult(layoutSEOEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return layoutSEOEntry;
	}

	/**
	 * Returns the layout seo entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSEOEntryId the primary key of the layout seo entry
	 * @return the layout seo entry, or <code>null</code> if a layout seo entry with the primary key could not be found
	 */
	@Override
	public LayoutSEOEntry fetchByPrimaryKey(long layoutSEOEntryId) {
		return fetchByPrimaryKey((Serializable)layoutSEOEntryId);
	}

	@Override
	public Map<Serializable, LayoutSEOEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(LayoutSEOEntry.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LayoutSEOEntry> map =
			new HashMap<Serializable, LayoutSEOEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LayoutSEOEntry layoutSEOEntry = fetchByPrimaryKey(primaryKey);

			if (layoutSEOEntry != null) {
				map.put(primaryKey, layoutSEOEntry);
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

			for (LayoutSEOEntry layoutSEOEntry :
					(List<LayoutSEOEntry>)query.list()) {

				map.put(layoutSEOEntry.getPrimaryKeyObj(), layoutSEOEntry);

				cacheResult(layoutSEOEntry);
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
	 * Returns all the layout seo entries.
	 *
	 * @return the layout seo entries
	 */
	@Override
	public List<LayoutSEOEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @return the range of layout seo entries
	 */
	@Override
	public List<LayoutSEOEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout seo entries
	 */
	@Override
	public List<LayoutSEOEntry> findAll(
		int start, int end,
		OrderByComparator<LayoutSEOEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout seo entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutSEOEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout seo entries
	 * @param end the upper bound of the range of layout seo entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout seo entries
	 */
	@Override
	public List<LayoutSEOEntry> findAll(
		int start, int end, OrderByComparator<LayoutSEOEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOEntry.class);

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

		List<LayoutSEOEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<LayoutSEOEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_LAYOUTSEOENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTSEOENTRY;

				sql = sql.concat(LayoutSEOEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<LayoutSEOEntry>)QueryUtil.list(
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
	 * Removes all the layout seo entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutSEOEntry layoutSEOEntry : findAll()) {
			remove(layoutSEOEntry);
		}
	}

	/**
	 * Returns the number of layout seo entries.
	 *
	 * @return the number of layout seo entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			LayoutSEOEntry.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_LAYOUTSEOENTRY);

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
		return "layoutSEOEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTSEOENTRY;
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
		return LayoutSEOEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "LayoutSEOEntry";
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
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("privateLayout");
		ctStrictColumnNames.add("layoutId");
		ctStrictColumnNames.add("canonicalURL");
		ctStrictColumnNames.add("canonicalURLEnabled");
		ctStrictColumnNames.add("DDMStorageId");
		ctStrictColumnNames.add("openGraphDescription");
		ctStrictColumnNames.add("openGraphDescriptionEnabled");
		ctStrictColumnNames.add("openGraphImageAlt");
		ctStrictColumnNames.add("openGraphImageFileEntryId");
		ctStrictColumnNames.add("openGraphTitle");
		ctStrictColumnNames.add("openGraphTitleEnabled");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("layoutSEOEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "privateLayout", "layoutId"});
	}

	/**
	 * Initializes the layout seo entry persistence.
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

		_finderPathFetchByG_P_L = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			new String[] {"groupId", "privateLayout", "layoutId"}, true);

		_finderPathCountByG_P_L = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			new String[] {"groupId", "privateLayout", "layoutId"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(LayoutSEOEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = LayoutSEOPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = LayoutSEOPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = LayoutSEOPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_LAYOUTSEOENTRY =
		"SELECT layoutSEOEntry FROM LayoutSEOEntry layoutSEOEntry";

	private static final String _SQL_SELECT_LAYOUTSEOENTRY_WHERE =
		"SELECT layoutSEOEntry FROM LayoutSEOEntry layoutSEOEntry WHERE ";

	private static final String _SQL_COUNT_LAYOUTSEOENTRY =
		"SELECT COUNT(layoutSEOEntry) FROM LayoutSEOEntry layoutSEOEntry";

	private static final String _SQL_COUNT_LAYOUTSEOENTRY_WHERE =
		"SELECT COUNT(layoutSEOEntry) FROM LayoutSEOEntry layoutSEOEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutSEOEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutSEOEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutSEOEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutSEOEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private LayoutSEOEntryModelArgumentsResolver
		_layoutSEOEntryModelArgumentsResolver;

}