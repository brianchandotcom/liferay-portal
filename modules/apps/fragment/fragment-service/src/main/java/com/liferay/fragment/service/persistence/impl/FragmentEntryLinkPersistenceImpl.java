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

package com.liferay.fragment.service.persistence.impl;

import com.liferay.fragment.exception.NoSuchEntryLinkException;
import com.liferay.fragment.model.FragmentEntryLink;
import com.liferay.fragment.model.FragmentEntryLinkTable;
import com.liferay.fragment.model.impl.FragmentEntryLinkImpl;
import com.liferay.fragment.model.impl.FragmentEntryLinkModelImpl;
import com.liferay.fragment.service.persistence.FragmentEntryLinkPersistence;
import com.liferay.fragment.service.persistence.impl.constants.FragmentPersistenceConstants;
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
import com.liferay.portal.kernel.util.OrderByComparator;
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
 * The persistence implementation for the fragment entry link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = FragmentEntryLinkPersistence.class)
public class FragmentEntryLinkPersistenceImpl
	extends BasePersistenceImpl<FragmentEntryLink>
	implements FragmentEntryLinkPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>FragmentEntryLinkUtil</code> to access the fragment entry link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		FragmentEntryLinkImpl.class.getName();

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
	 * Returns all the fragment entry links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

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

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryLink fragmentEntryLink : list) {
					if (!uuid.equals(fragmentEntryLink.getUuid())) {
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

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

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
				sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
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

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByUuid_First(
			String uuid, OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByUuid_First(
			uuid, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByUuid_First(
		String uuid, OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByUuid_Last(
			String uuid, OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByUuid_Last(
			uuid, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the last fragment entry link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByUuid_Last(
		String uuid, OrderByComparator<FragmentEntryLink> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryLink> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry links before and after the current fragment entry link in the ordered set where uuid = &#63;.
	 *
	 * @param fragmentEntryLinkId the primary key of the current fragment entry link
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink[] findByUuid_PrevAndNext(
			long fragmentEntryLinkId, String uuid,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		uuid = Objects.toString(uuid, "");

		FragmentEntryLink fragmentEntryLink = findByPrimaryKey(
			fragmentEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink[] array = new FragmentEntryLinkImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, fragmentEntryLink, uuid, orderByComparator, true);

			array[1] = fragmentEntryLink;

			array[2] = getByUuid_PrevAndNext(
				session, fragmentEntryLink, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryLink getByUuid_PrevAndNext(
		Session session, FragmentEntryLink fragmentEntryLink, String uuid,
		OrderByComparator<FragmentEntryLink> orderByComparator,
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

		sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

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
			sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
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
						fragmentEntryLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry links where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (FragmentEntryLink fragmentEntryLink :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

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

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

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
		"fragmentEntryLink.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(fragmentEntryLink.uuid IS NULL OR fragmentEntryLink.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the fragment entry link where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryLinkException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByUUID_G(uuid, groupId);

		if (fragmentEntryLink == null) {
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

			throw new NoSuchEntryLinkException(sb.toString());
		}

		return fragmentEntryLink;
	}

	/**
	 * Returns the fragment entry link where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the fragment entry link where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof FragmentEntryLink) {
			FragmentEntryLink fragmentEntryLink = (FragmentEntryLink)result;

			if (!Objects.equals(uuid, fragmentEntryLink.getUuid()) ||
				(groupId != fragmentEntryLink.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

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

				List<FragmentEntryLink> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					FragmentEntryLink fragmentEntryLink = list.get(0);

					result = fragmentEntryLink;

					cacheResult(fragmentEntryLink);
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
			return (FragmentEntryLink)result;
		}
	}

	/**
	 * Removes the fragment entry link where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the fragment entry link that was removed
	 */
	@Override
	public FragmentEntryLink removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = findByUUID_G(uuid, groupId);

		return remove(fragmentEntryLink);
	}

	/**
	 * Returns the number of fragment entry links where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

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

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

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
		"fragmentEntryLink.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(fragmentEntryLink.uuid IS NULL OR fragmentEntryLink.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"fragmentEntryLink.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the fragment entry links where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

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

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryLink fragmentEntryLink : list) {
					if (!uuid.equals(fragmentEntryLink.getUuid()) ||
						(companyId != fragmentEntryLink.getCompanyId())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

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
				sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
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

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the last fragment entry link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryLink> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry links before and after the current fragment entry link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param fragmentEntryLinkId the primary key of the current fragment entry link
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink[] findByUuid_C_PrevAndNext(
			long fragmentEntryLinkId, String uuid, long companyId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		uuid = Objects.toString(uuid, "");

		FragmentEntryLink fragmentEntryLink = findByPrimaryKey(
			fragmentEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink[] array = new FragmentEntryLinkImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, fragmentEntryLink, uuid, companyId, orderByComparator,
				true);

			array[1] = fragmentEntryLink;

			array[2] = getByUuid_C_PrevAndNext(
				session, fragmentEntryLink, uuid, companyId, orderByComparator,
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

	protected FragmentEntryLink getByUuid_C_PrevAndNext(
		Session session, FragmentEntryLink fragmentEntryLink, String uuid,
		long companyId, OrderByComparator<FragmentEntryLink> orderByComparator,
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

		sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

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
			sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
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
						fragmentEntryLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry links where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (FragmentEntryLink fragmentEntryLink :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

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

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

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
		"fragmentEntryLink.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(fragmentEntryLink.uuid IS NULL OR fragmentEntryLink.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"fragmentEntryLink.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the fragment entry links where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByGroupId;
				finderArgs = new Object[] {groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryLink fragmentEntryLink : list) {
					if (groupId != fragmentEntryLink.getGroupId()) {
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

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByGroupId_First(
			long groupId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByGroupId_First(
			groupId, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByGroupId_First(
		long groupId, OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByGroupId_Last(
			long groupId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByGroupId_Last(
		long groupId, OrderByComparator<FragmentEntryLink> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryLink> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry links before and after the current fragment entry link in the ordered set where groupId = &#63;.
	 *
	 * @param fragmentEntryLinkId the primary key of the current fragment entry link
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink[] findByGroupId_PrevAndNext(
			long fragmentEntryLinkId, long groupId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = findByPrimaryKey(
			fragmentEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink[] array = new FragmentEntryLinkImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, fragmentEntryLink, groupId, orderByComparator, true);

			array[1] = fragmentEntryLink;

			array[2] = getByGroupId_PrevAndNext(
				session, fragmentEntryLink, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryLink getByGroupId_PrevAndNext(
		Session session, FragmentEntryLink fragmentEntryLink, long groupId,
		OrderByComparator<FragmentEntryLink> orderByComparator,
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

		sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (FragmentEntryLink fragmentEntryLink :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"fragmentEntryLink.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByFragmentEntryId;
	private FinderPath _finderPathWithoutPaginationFindByFragmentEntryId;
	private FinderPath _finderPathCountByFragmentEntryId;

	/**
	 * Returns all the fragment entry links where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByFragmentEntryId(long fragmentEntryId) {
		return findByFragmentEntryId(
			fragmentEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByFragmentEntryId(
		long fragmentEntryId, int start, int end) {

		return findByFragmentEntryId(fragmentEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByFragmentEntryId(
		long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByFragmentEntryId(
			fragmentEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByFragmentEntryId(
		long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByFragmentEntryId;
				finderArgs = new Object[] {fragmentEntryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByFragmentEntryId;
			finderArgs = new Object[] {
				fragmentEntryId, start, end, orderByComparator
			};
		}

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryLink fragmentEntryLink : list) {
					if (fragmentEntryId !=
							fragmentEntryLink.getFragmentEntryId()) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_FRAGMENTENTRYID_FRAGMENTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentEntryId);

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByFragmentEntryId_First(
			long fragmentEntryId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByFragmentEntryId_First(
			fragmentEntryId, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fragmentEntryId=");
		sb.append(fragmentEntryId);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByFragmentEntryId_First(
		long fragmentEntryId,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByFragmentEntryId(
			fragmentEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry link in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByFragmentEntryId_Last(
			long fragmentEntryId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByFragmentEntryId_Last(
			fragmentEntryId, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("fragmentEntryId=");
		sb.append(fragmentEntryId);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the last fragment entry link in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByFragmentEntryId_Last(
		long fragmentEntryId,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		int count = countByFragmentEntryId(fragmentEntryId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryLink> list = findByFragmentEntryId(
			fragmentEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry links before and after the current fragment entry link in the ordered set where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryLinkId the primary key of the current fragment entry link
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink[] findByFragmentEntryId_PrevAndNext(
			long fragmentEntryLinkId, long fragmentEntryId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = findByPrimaryKey(
			fragmentEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink[] array = new FragmentEntryLinkImpl[3];

			array[0] = getByFragmentEntryId_PrevAndNext(
				session, fragmentEntryLink, fragmentEntryId, orderByComparator,
				true);

			array[1] = fragmentEntryLink;

			array[2] = getByFragmentEntryId_PrevAndNext(
				session, fragmentEntryLink, fragmentEntryId, orderByComparator,
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

	protected FragmentEntryLink getByFragmentEntryId_PrevAndNext(
		Session session, FragmentEntryLink fragmentEntryLink,
		long fragmentEntryId,
		OrderByComparator<FragmentEntryLink> orderByComparator,
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

		sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

		sb.append(_FINDER_COLUMN_FRAGMENTENTRYID_FRAGMENTENTRYID_2);

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
			sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(fragmentEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry links where fragmentEntryId = &#63; from the database.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 */
	@Override
	public void removeByFragmentEntryId(long fragmentEntryId) {
		for (FragmentEntryLink fragmentEntryLink :
				findByFragmentEntryId(
					fragmentEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryId the fragment entry ID
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByFragmentEntryId(long fragmentEntryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByFragmentEntryId;

			finderArgs = new Object[] {fragmentEntryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_FRAGMENTENTRYID_FRAGMENTENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(fragmentEntryId);

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

	private static final String
		_FINDER_COLUMN_FRAGMENTENTRYID_FRAGMENTENTRYID_2 =
			"fragmentEntryLink.fragmentEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByRendererKey;
	private FinderPath _finderPathWithoutPaginationFindByRendererKey;
	private FinderPath _finderPathCountByRendererKey;

	/**
	 * Returns all the fragment entry links where rendererKey = &#63;.
	 *
	 * @param rendererKey the renderer key
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByRendererKey(String rendererKey) {
		return findByRendererKey(
			rendererKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where rendererKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param rendererKey the renderer key
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByRendererKey(
		String rendererKey, int start, int end) {

		return findByRendererKey(rendererKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where rendererKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param rendererKey the renderer key
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByRendererKey(
		String rendererKey, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByRendererKey(
			rendererKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where rendererKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param rendererKey the renderer key
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByRendererKey(
		String rendererKey, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		rendererKey = Objects.toString(rendererKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByRendererKey;
				finderArgs = new Object[] {rendererKey};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByRendererKey;
			finderArgs = new Object[] {
				rendererKey, start, end, orderByComparator
			};
		}

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryLink fragmentEntryLink : list) {
					if (!rendererKey.equals(
							fragmentEntryLink.getRendererKey())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

			boolean bindRendererKey = false;

			if (rendererKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_RENDERERKEY_RENDERERKEY_3);
			}
			else {
				bindRendererKey = true;

				sb.append(_FINDER_COLUMN_RENDERERKEY_RENDERERKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindRendererKey) {
					queryPos.add(rendererKey);
				}

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where rendererKey = &#63;.
	 *
	 * @param rendererKey the renderer key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByRendererKey_First(
			String rendererKey,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByRendererKey_First(
			rendererKey, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("rendererKey=");
		sb.append(rendererKey);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where rendererKey = &#63;.
	 *
	 * @param rendererKey the renderer key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByRendererKey_First(
		String rendererKey,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByRendererKey(
			rendererKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry link in the ordered set where rendererKey = &#63;.
	 *
	 * @param rendererKey the renderer key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByRendererKey_Last(
			String rendererKey,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByRendererKey_Last(
			rendererKey, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("rendererKey=");
		sb.append(rendererKey);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the last fragment entry link in the ordered set where rendererKey = &#63;.
	 *
	 * @param rendererKey the renderer key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByRendererKey_Last(
		String rendererKey,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		int count = countByRendererKey(rendererKey);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryLink> list = findByRendererKey(
			rendererKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry links before and after the current fragment entry link in the ordered set where rendererKey = &#63;.
	 *
	 * @param fragmentEntryLinkId the primary key of the current fragment entry link
	 * @param rendererKey the renderer key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink[] findByRendererKey_PrevAndNext(
			long fragmentEntryLinkId, String rendererKey,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		rendererKey = Objects.toString(rendererKey, "");

		FragmentEntryLink fragmentEntryLink = findByPrimaryKey(
			fragmentEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink[] array = new FragmentEntryLinkImpl[3];

			array[0] = getByRendererKey_PrevAndNext(
				session, fragmentEntryLink, rendererKey, orderByComparator,
				true);

			array[1] = fragmentEntryLink;

			array[2] = getByRendererKey_PrevAndNext(
				session, fragmentEntryLink, rendererKey, orderByComparator,
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

	protected FragmentEntryLink getByRendererKey_PrevAndNext(
		Session session, FragmentEntryLink fragmentEntryLink,
		String rendererKey,
		OrderByComparator<FragmentEntryLink> orderByComparator,
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

		sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

		boolean bindRendererKey = false;

		if (rendererKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_RENDERERKEY_RENDERERKEY_3);
		}
		else {
			bindRendererKey = true;

			sb.append(_FINDER_COLUMN_RENDERERKEY_RENDERERKEY_2);
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
			sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindRendererKey) {
			queryPos.add(rendererKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry links where rendererKey = &#63; from the database.
	 *
	 * @param rendererKey the renderer key
	 */
	@Override
	public void removeByRendererKey(String rendererKey) {
		for (FragmentEntryLink fragmentEntryLink :
				findByRendererKey(
					rendererKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where rendererKey = &#63;.
	 *
	 * @param rendererKey the renderer key
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByRendererKey(String rendererKey) {
		rendererKey = Objects.toString(rendererKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByRendererKey;

			finderArgs = new Object[] {rendererKey};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

			boolean bindRendererKey = false;

			if (rendererKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_RENDERERKEY_RENDERERKEY_3);
			}
			else {
				bindRendererKey = true;

				sb.append(_FINDER_COLUMN_RENDERERKEY_RENDERERKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindRendererKey) {
					queryPos.add(rendererKey);
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

	private static final String _FINDER_COLUMN_RENDERERKEY_RENDERERKEY_2 =
		"fragmentEntryLink.rendererKey = ?";

	private static final String _FINDER_COLUMN_RENDERERKEY_RENDERERKEY_3 =
		"(fragmentEntryLink.rendererKey IS NULL OR fragmentEntryLink.rendererKey = '')";

	private FinderPath _finderPathWithPaginationFindByG_F;
	private FinderPath _finderPathWithoutPaginationFindByG_F;
	private FinderPath _finderPathCountByG_F;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F(
		long groupId, long fragmentEntryId) {

		return findByG_F(
			groupId, fragmentEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F(
		long groupId, long fragmentEntryId, int start, int end) {

		return findByG_F(groupId, fragmentEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F(
		long groupId, long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_F(
			groupId, fragmentEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F(
		long groupId, long fragmentEntryId, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_F;
				finderArgs = new Object[] {groupId, fragmentEntryId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_F;
			finderArgs = new Object[] {
				groupId, fragmentEntryId, start, end, orderByComparator
			};
		}

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryLink fragmentEntryLink : list) {
					if ((groupId != fragmentEntryLink.getGroupId()) ||
						(fragmentEntryId !=
							fragmentEntryLink.getFragmentEntryId())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_F_FRAGMENTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentEntryId);

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_F_First(
			long groupId, long fragmentEntryId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_F_First(
			groupId, fragmentEntryId, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentEntryId=");
		sb.append(fragmentEntryId);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_F_First(
		long groupId, long fragmentEntryId,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByG_F(
			groupId, fragmentEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_F_Last(
			long groupId, long fragmentEntryId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_F_Last(
			groupId, fragmentEntryId, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentEntryId=");
		sb.append(fragmentEntryId);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_F_Last(
		long groupId, long fragmentEntryId,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		int count = countByG_F(groupId, fragmentEntryId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryLink> list = findByG_F(
			groupId, fragmentEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry links before and after the current fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param fragmentEntryLinkId the primary key of the current fragment entry link
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink[] findByG_F_PrevAndNext(
			long fragmentEntryLinkId, long groupId, long fragmentEntryId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = findByPrimaryKey(
			fragmentEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink[] array = new FragmentEntryLinkImpl[3];

			array[0] = getByG_F_PrevAndNext(
				session, fragmentEntryLink, groupId, fragmentEntryId,
				orderByComparator, true);

			array[1] = fragmentEntryLink;

			array[2] = getByG_F_PrevAndNext(
				session, fragmentEntryLink, groupId, fragmentEntryId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryLink getByG_F_PrevAndNext(
		Session session, FragmentEntryLink fragmentEntryLink, long groupId,
		long fragmentEntryId,
		OrderByComparator<FragmentEntryLink> orderByComparator,
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

		sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

		sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_FRAGMENTENTRYID_2);

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
			sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 */
	@Override
	public void removeByG_F(long groupId, long fragmentEntryId) {
		for (FragmentEntryLink fragmentEntryLink :
				findByG_F(
					groupId, fragmentEntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and fragmentEntryId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_F(long groupId, long fragmentEntryId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_F;

			finderArgs = new Object[] {groupId, fragmentEntryId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_F_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_F_FRAGMENTENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentEntryId);

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

	private static final String _FINDER_COLUMN_G_F_GROUPID_2 =
		"fragmentEntryLink.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_FRAGMENTENTRYID_2 =
		"fragmentEntryLink.fragmentEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByG_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P;
	private FinderPath _finderPathCountByG_P;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_P(long groupId, long plid) {
		return findByG_P(
			groupId, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_P(
		long groupId, long plid, int start, int end) {

		return findByG_P(groupId, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_P(
		long groupId, long plid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_P(groupId, plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_P(
		long groupId, long plid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_P;
				finderArgs = new Object[] {groupId, plid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_P;
			finderArgs = new Object[] {
				groupId, plid, start, end, orderByComparator
			};
		}

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryLink fragmentEntryLink : list) {
					if ((groupId != fragmentEntryLink.getGroupId()) ||
						(plid != fragmentEntryLink.getPlid())) {

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

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_P_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(plid);

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_P_First(
			long groupId, long plid,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_P_First(
			groupId, plid, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_P_First(
		long groupId, long plid,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByG_P(
			groupId, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_P_Last(
			long groupId, long plid,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_P_Last(
			groupId, plid, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_P_Last(
		long groupId, long plid,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		int count = countByG_P(groupId, plid);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryLink> list = findByG_P(
			groupId, plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry links before and after the current fragment entry link in the ordered set where groupId = &#63; and plid = &#63;.
	 *
	 * @param fragmentEntryLinkId the primary key of the current fragment entry link
	 * @param groupId the group ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink[] findByG_P_PrevAndNext(
			long fragmentEntryLinkId, long groupId, long plid,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = findByPrimaryKey(
			fragmentEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink[] array = new FragmentEntryLinkImpl[3];

			array[0] = getByG_P_PrevAndNext(
				session, fragmentEntryLink, groupId, plid, orderByComparator,
				true);

			array[1] = fragmentEntryLink;

			array[2] = getByG_P_PrevAndNext(
				session, fragmentEntryLink, groupId, plid, orderByComparator,
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

	protected FragmentEntryLink getByG_P_PrevAndNext(
		Session session, FragmentEntryLink fragmentEntryLink, long groupId,
		long plid, OrderByComparator<FragmentEntryLink> orderByComparator,
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

		sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

		sb.append(_FINDER_COLUMN_G_P_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_P_PLID_2);

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
			sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and plid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 */
	@Override
	public void removeByG_P(long groupId, long plid) {
		for (FragmentEntryLink fragmentEntryLink :
				findByG_P(
					groupId, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param plid the plid
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_P(long groupId, long plid) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_P;

			finderArgs = new Object[] {groupId, plid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_P_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_P_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

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

	private static final String _FINDER_COLUMN_G_P_GROUPID_2 =
		"fragmentEntryLink.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_PLID_2 =
		"fragmentEntryLink.plid = ?";

	private FinderPath _finderPathWithPaginationFindByG_F_C;
	private FinderPath _finderPathWithoutPaginationFindByG_F_C;
	private FinderPath _finderPathCountByG_F_C;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F_C(
		long groupId, long fragmentEntryId, long classNameId) {

		return findByG_F_C(
			groupId, fragmentEntryId, classNameId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F_C(
		long groupId, long fragmentEntryId, long classNameId, int start,
		int end) {

		return findByG_F_C(
			groupId, fragmentEntryId, classNameId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F_C(
		long groupId, long fragmentEntryId, long classNameId, int start,
		int end, OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_F_C(
			groupId, fragmentEntryId, classNameId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F_C(
		long groupId, long fragmentEntryId, long classNameId, int start,
		int end, OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_F_C;
				finderArgs = new Object[] {
					groupId, fragmentEntryId, classNameId
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_F_C;
			finderArgs = new Object[] {
				groupId, fragmentEntryId, classNameId, start, end,
				orderByComparator
			};
		}

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryLink fragmentEntryLink : list) {
					if ((groupId != fragmentEntryLink.getGroupId()) ||
						(fragmentEntryId !=
							fragmentEntryLink.getFragmentEntryId()) ||
						(classNameId != fragmentEntryLink.getClassNameId())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_F_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_F_C_FRAGMENTENTRYID_2);

			sb.append(_FINDER_COLUMN_G_F_C_CLASSNAMEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentEntryId);

				queryPos.add(classNameId);

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_F_C_First(
			long groupId, long fragmentEntryId, long classNameId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_F_C_First(
			groupId, fragmentEntryId, classNameId, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentEntryId=");
		sb.append(fragmentEntryId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_F_C_First(
		long groupId, long fragmentEntryId, long classNameId,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByG_F_C(
			groupId, fragmentEntryId, classNameId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_F_C_Last(
			long groupId, long fragmentEntryId, long classNameId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_F_C_Last(
			groupId, fragmentEntryId, classNameId, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentEntryId=");
		sb.append(fragmentEntryId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_F_C_Last(
		long groupId, long fragmentEntryId, long classNameId,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		int count = countByG_F_C(groupId, fragmentEntryId, classNameId);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryLink> list = findByG_F_C(
			groupId, fragmentEntryId, classNameId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry links before and after the current fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param fragmentEntryLinkId the primary key of the current fragment entry link
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink[] findByG_F_C_PrevAndNext(
			long fragmentEntryLinkId, long groupId, long fragmentEntryId,
			long classNameId,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = findByPrimaryKey(
			fragmentEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink[] array = new FragmentEntryLinkImpl[3];

			array[0] = getByG_F_C_PrevAndNext(
				session, fragmentEntryLink, groupId, fragmentEntryId,
				classNameId, orderByComparator, true);

			array[1] = fragmentEntryLink;

			array[2] = getByG_F_C_PrevAndNext(
				session, fragmentEntryLink, groupId, fragmentEntryId,
				classNameId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryLink getByG_F_C_PrevAndNext(
		Session session, FragmentEntryLink fragmentEntryLink, long groupId,
		long fragmentEntryId, long classNameId,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

		sb.append(_FINDER_COLUMN_G_F_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_C_FRAGMENTENTRYID_2);

		sb.append(_FINDER_COLUMN_G_F_C_CLASSNAMEID_2);

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
			sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentEntryId);

		queryPos.add(classNameId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 */
	@Override
	public void removeByG_F_C(
		long groupId, long fragmentEntryId, long classNameId) {

		for (FragmentEntryLink fragmentEntryLink :
				findByG_F_C(
					groupId, fragmentEntryId, classNameId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_F_C(
		long groupId, long fragmentEntryId, long classNameId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_F_C;

			finderArgs = new Object[] {groupId, fragmentEntryId, classNameId};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_F_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_F_C_FRAGMENTENTRYID_2);

			sb.append(_FINDER_COLUMN_G_F_C_CLASSNAMEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentEntryId);

				queryPos.add(classNameId);

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

	private static final String _FINDER_COLUMN_G_F_C_GROUPID_2 =
		"fragmentEntryLink.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_C_FRAGMENTENTRYID_2 =
		"fragmentEntryLink.fragmentEntryId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_C_CLASSNAMEID_2 =
		"fragmentEntryLink.classNameId = ?";

	private FinderPath _finderPathWithPaginationFindByG_F_P;
	private FinderPath _finderPathWithoutPaginationFindByG_F_P;
	private FinderPath _finderPathCountByG_F_P;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param plid the plid
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F_P(
		long groupId, long fragmentEntryId, long plid) {

		return findByG_F_P(
			groupId, fragmentEntryId, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F_P(
		long groupId, long fragmentEntryId, long plid, int start, int end) {

		return findByG_F_P(groupId, fragmentEntryId, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F_P(
		long groupId, long fragmentEntryId, long plid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_F_P(
			groupId, fragmentEntryId, plid, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F_P(
		long groupId, long fragmentEntryId, long plid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_F_P;
				finderArgs = new Object[] {groupId, fragmentEntryId, plid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_F_P;
			finderArgs = new Object[] {
				groupId, fragmentEntryId, plid, start, end, orderByComparator
			};
		}

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryLink fragmentEntryLink : list) {
					if ((groupId != fragmentEntryLink.getGroupId()) ||
						(fragmentEntryId !=
							fragmentEntryLink.getFragmentEntryId()) ||
						(plid != fragmentEntryLink.getPlid())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_F_P_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_F_P_FRAGMENTENTRYID_2);

			sb.append(_FINDER_COLUMN_G_F_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentEntryId);

				queryPos.add(plid);

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_F_P_First(
			long groupId, long fragmentEntryId, long plid,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_F_P_First(
			groupId, fragmentEntryId, plid, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentEntryId=");
		sb.append(fragmentEntryId);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_F_P_First(
		long groupId, long fragmentEntryId, long plid,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByG_F_P(
			groupId, fragmentEntryId, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_F_P_Last(
			long groupId, long fragmentEntryId, long plid,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_F_P_Last(
			groupId, fragmentEntryId, plid, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentEntryId=");
		sb.append(fragmentEntryId);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_F_P_Last(
		long groupId, long fragmentEntryId, long plid,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		int count = countByG_F_P(groupId, fragmentEntryId, plid);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryLink> list = findByG_F_P(
			groupId, fragmentEntryId, plid, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry links before and after the current fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and plid = &#63;.
	 *
	 * @param fragmentEntryLinkId the primary key of the current fragment entry link
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink[] findByG_F_P_PrevAndNext(
			long fragmentEntryLinkId, long groupId, long fragmentEntryId,
			long plid, OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = findByPrimaryKey(
			fragmentEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink[] array = new FragmentEntryLinkImpl[3];

			array[0] = getByG_F_P_PrevAndNext(
				session, fragmentEntryLink, groupId, fragmentEntryId, plid,
				orderByComparator, true);

			array[1] = fragmentEntryLink;

			array[2] = getByG_F_P_PrevAndNext(
				session, fragmentEntryLink, groupId, fragmentEntryId, plid,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryLink getByG_F_P_PrevAndNext(
		Session session, FragmentEntryLink fragmentEntryLink, long groupId,
		long fragmentEntryId, long plid,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

		sb.append(_FINDER_COLUMN_G_F_P_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_P_FRAGMENTENTRYID_2);

		sb.append(_FINDER_COLUMN_G_F_P_PLID_2);

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
			sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentEntryId);

		queryPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and plid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param plid the plid
	 */
	@Override
	public void removeByG_F_P(long groupId, long fragmentEntryId, long plid) {
		for (FragmentEntryLink fragmentEntryLink :
				findByG_F_P(
					groupId, fragmentEntryId, plid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param plid the plid
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_F_P(long groupId, long fragmentEntryId, long plid) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_F_P;

			finderArgs = new Object[] {groupId, fragmentEntryId, plid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_F_P_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_F_P_FRAGMENTENTRYID_2);

			sb.append(_FINDER_COLUMN_G_F_P_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentEntryId);

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

	private static final String _FINDER_COLUMN_G_F_P_GROUPID_2 =
		"fragmentEntryLink.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_P_FRAGMENTENTRYID_2 =
		"fragmentEntryLink.fragmentEntryId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_P_PLID_2 =
		"fragmentEntryLink.plid = ?";

	private FinderPath _finderPathWithPaginationFindByG_S_P;
	private FinderPath _finderPathWithoutPaginationFindByG_S_P;
	private FinderPath _finderPathCountByG_S_P;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P(
		long groupId, long segmentsExperienceId, long plid) {

		return findByG_S_P(
			groupId, segmentsExperienceId, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P(
		long groupId, long segmentsExperienceId, long plid, int start,
		int end) {

		return findByG_S_P(
			groupId, segmentsExperienceId, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P(
		long groupId, long segmentsExperienceId, long plid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_S_P(
			groupId, segmentsExperienceId, plid, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_P(
		long groupId, long segmentsExperienceId, long plid, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_S_P;
				finderArgs = new Object[] {groupId, segmentsExperienceId, plid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_S_P;
			finderArgs = new Object[] {
				groupId, segmentsExperienceId, plid, start, end,
				orderByComparator
			};
		}

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryLink fragmentEntryLink : list) {
					if ((groupId != fragmentEntryLink.getGroupId()) ||
						(segmentsExperienceId !=
							fragmentEntryLink.getSegmentsExperienceId()) ||
						(plid != fragmentEntryLink.getPlid())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_S_P_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_P_SEGMENTSEXPERIENCEID_2);

			sb.append(_FINDER_COLUMN_G_S_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(segmentsExperienceId);

				queryPos.add(plid);

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_S_P_First(
			long groupId, long segmentsExperienceId, long plid,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_S_P_First(
			groupId, segmentsExperienceId, plid, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", segmentsExperienceId=");
		sb.append(segmentsExperienceId);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_S_P_First(
		long groupId, long segmentsExperienceId, long plid,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByG_S_P(
			groupId, segmentsExperienceId, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_S_P_Last(
			long groupId, long segmentsExperienceId, long plid,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_S_P_Last(
			groupId, segmentsExperienceId, plid, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", segmentsExperienceId=");
		sb.append(segmentsExperienceId);

		sb.append(", plid=");
		sb.append(plid);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_S_P_Last(
		long groupId, long segmentsExperienceId, long plid,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		int count = countByG_S_P(groupId, segmentsExperienceId, plid);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryLink> list = findByG_S_P(
			groupId, segmentsExperienceId, plid, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry links before and after the current fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * @param fragmentEntryLinkId the primary key of the current fragment entry link
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink[] findByG_S_P_PrevAndNext(
			long fragmentEntryLinkId, long groupId, long segmentsExperienceId,
			long plid, OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = findByPrimaryKey(
			fragmentEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink[] array = new FragmentEntryLinkImpl[3];

			array[0] = getByG_S_P_PrevAndNext(
				session, fragmentEntryLink, groupId, segmentsExperienceId, plid,
				orderByComparator, true);

			array[1] = fragmentEntryLink;

			array[2] = getByG_S_P_PrevAndNext(
				session, fragmentEntryLink, groupId, segmentsExperienceId, plid,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryLink getByG_S_P_PrevAndNext(
		Session session, FragmentEntryLink fragmentEntryLink, long groupId,
		long segmentsExperienceId, long plid,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

		sb.append(_FINDER_COLUMN_G_S_P_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_P_SEGMENTSEXPERIENCEID_2);

		sb.append(_FINDER_COLUMN_G_S_P_PLID_2);

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
			sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(segmentsExperienceId);

		queryPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 */
	@Override
	public void removeByG_S_P(
		long groupId, long segmentsExperienceId, long plid) {

		for (FragmentEntryLink fragmentEntryLink :
				findByG_S_P(
					groupId, segmentsExperienceId, plid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and plid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param plid the plid
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_S_P(
		long groupId, long segmentsExperienceId, long plid) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_S_P;

			finderArgs = new Object[] {groupId, segmentsExperienceId, plid};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_S_P_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_P_SEGMENTSEXPERIENCEID_2);

			sb.append(_FINDER_COLUMN_G_S_P_PLID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(segmentsExperienceId);

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

	private static final String _FINDER_COLUMN_G_S_P_GROUPID_2 =
		"fragmentEntryLink.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_P_SEGMENTSEXPERIENCEID_2 =
		"fragmentEntryLink.segmentsExperienceId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_P_PLID_2 =
		"fragmentEntryLink.plid = ?";

	private FinderPath _finderPathWithPaginationFindByG_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_C_C;
	private FinderPath _finderPathCountByG_C_C;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_C_C(
		long groupId, long classNameId, long classPK) {

		return findByG_C_C(
			groupId, classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end) {

		return findByG_C_C(groupId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_C_C(
			groupId, classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_C_C(
		long groupId, long classNameId, long classPK, int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_C_C;
				finderArgs = new Object[] {groupId, classNameId, classPK};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_C_C;
			finderArgs = new Object[] {
				groupId, classNameId, classPK, start, end, orderByComparator
			};
		}

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryLink fragmentEntryLink : list) {
					if ((groupId != fragmentEntryLink.getGroupId()) ||
						(classNameId != fragmentEntryLink.getClassNameId()) ||
						(classPK != fragmentEntryLink.getClassPK())) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_C_C_First(
			long groupId, long classNameId, long classPK,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_C_C_First(
			groupId, classNameId, classPK, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_C_C_First(
		long groupId, long classNameId, long classPK,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByG_C_C(
			groupId, classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_C_C_Last(
			long groupId, long classNameId, long classPK,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_C_C_Last(
			groupId, classNameId, classPK, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_C_C_Last(
		long groupId, long classNameId, long classPK,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		int count = countByG_C_C(groupId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryLink> list = findByG_C_C(
			groupId, classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry links before and after the current fragment entry link in the ordered set where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param fragmentEntryLinkId the primary key of the current fragment entry link
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink[] findByG_C_C_PrevAndNext(
			long fragmentEntryLinkId, long groupId, long classNameId,
			long classPK,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = findByPrimaryKey(
			fragmentEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink[] array = new FragmentEntryLinkImpl[3];

			array[0] = getByG_C_C_PrevAndNext(
				session, fragmentEntryLink, groupId, classNameId, classPK,
				orderByComparator, true);

			array[1] = fragmentEntryLink;

			array[2] = getByG_C_C_PrevAndNext(
				session, fragmentEntryLink, groupId, classNameId, classPK,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryLink getByG_C_C_PrevAndNext(
		Session session, FragmentEntryLink fragmentEntryLink, long groupId,
		long classNameId, long classPK,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

		sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

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
			sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_C_C(long groupId, long classNameId, long classPK) {
		for (FragmentEntryLink fragmentEntryLink :
				findByG_C_C(
					groupId, classNameId, classPK, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_C_C;

			finderArgs = new Object[] {groupId, classNameId, classPK};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 =
		"fragmentEntryLink.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 =
		"fragmentEntryLink.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 =
		"fragmentEntryLink.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByG_F_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_F_C_C;
	private FinderPath _finderPathCountByG_F_C_C;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F_C_C(
		long groupId, long fragmentEntryId, long classNameId, long classPK) {

		return findByG_F_C_C(
			groupId, fragmentEntryId, classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F_C_C(
		long groupId, long fragmentEntryId, long classNameId, long classPK,
		int start, int end) {

		return findByG_F_C_C(
			groupId, fragmentEntryId, classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F_C_C(
		long groupId, long fragmentEntryId, long classNameId, long classPK,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_F_C_C(
			groupId, fragmentEntryId, classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_F_C_C(
		long groupId, long fragmentEntryId, long classNameId, long classPK,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_F_C_C;
				finderArgs = new Object[] {
					groupId, fragmentEntryId, classNameId, classPK
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_F_C_C;
			finderArgs = new Object[] {
				groupId, fragmentEntryId, classNameId, classPK, start, end,
				orderByComparator
			};
		}

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryLink fragmentEntryLink : list) {
					if ((groupId != fragmentEntryLink.getGroupId()) ||
						(fragmentEntryId !=
							fragmentEntryLink.getFragmentEntryId()) ||
						(classNameId != fragmentEntryLink.getClassNameId()) ||
						(classPK != fragmentEntryLink.getClassPK())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_F_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_F_C_C_FRAGMENTENTRYID_2);

			sb.append(_FINDER_COLUMN_G_F_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_F_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentEntryId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_F_C_C_First(
			long groupId, long fragmentEntryId, long classNameId, long classPK,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_F_C_C_First(
			groupId, fragmentEntryId, classNameId, classPK, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentEntryId=");
		sb.append(fragmentEntryId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_F_C_C_First(
		long groupId, long fragmentEntryId, long classNameId, long classPK,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByG_F_C_C(
			groupId, fragmentEntryId, classNameId, classPK, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_F_C_C_Last(
			long groupId, long fragmentEntryId, long classNameId, long classPK,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_F_C_C_Last(
			groupId, fragmentEntryId, classNameId, classPK, orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", fragmentEntryId=");
		sb.append(fragmentEntryId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_F_C_C_Last(
		long groupId, long fragmentEntryId, long classNameId, long classPK,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		int count = countByG_F_C_C(
			groupId, fragmentEntryId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryLink> list = findByG_F_C_C(
			groupId, fragmentEntryId, classNameId, classPK, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry links before and after the current fragment entry link in the ordered set where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param fragmentEntryLinkId the primary key of the current fragment entry link
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink[] findByG_F_C_C_PrevAndNext(
			long fragmentEntryLinkId, long groupId, long fragmentEntryId,
			long classNameId, long classPK,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = findByPrimaryKey(
			fragmentEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink[] array = new FragmentEntryLinkImpl[3];

			array[0] = getByG_F_C_C_PrevAndNext(
				session, fragmentEntryLink, groupId, fragmentEntryId,
				classNameId, classPK, orderByComparator, true);

			array[1] = fragmentEntryLink;

			array[2] = getByG_F_C_C_PrevAndNext(
				session, fragmentEntryLink, groupId, fragmentEntryId,
				classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryLink getByG_F_C_C_PrevAndNext(
		Session session, FragmentEntryLink fragmentEntryLink, long groupId,
		long fragmentEntryId, long classNameId, long classPK,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

		sb.append(_FINDER_COLUMN_G_F_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_F_C_C_FRAGMENTENTRYID_2);

		sb.append(_FINDER_COLUMN_G_F_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_F_C_C_CLASSPK_2);

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
			sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(fragmentEntryId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_F_C_C(
		long groupId, long fragmentEntryId, long classNameId, long classPK) {

		for (FragmentEntryLink fragmentEntryLink :
				findByG_F_C_C(
					groupId, fragmentEntryId, classNameId, classPK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and fragmentEntryId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param fragmentEntryId the fragment entry ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_F_C_C(
		long groupId, long fragmentEntryId, long classNameId, long classPK) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_F_C_C;

			finderArgs = new Object[] {
				groupId, fragmentEntryId, classNameId, classPK
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_F_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_F_C_C_FRAGMENTENTRYID_2);

			sb.append(_FINDER_COLUMN_G_F_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_F_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(fragmentEntryId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_F_C_C_GROUPID_2 =
		"fragmentEntryLink.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_C_C_FRAGMENTENTRYID_2 =
		"fragmentEntryLink.fragmentEntryId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_C_C_CLASSNAMEID_2 =
		"fragmentEntryLink.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_F_C_C_CLASSPK_2 =
		"fragmentEntryLink.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByG_S_C_C;
	private FinderPath _finderPathWithoutPaginationFindByG_S_C_C;
	private FinderPath _finderPathCountByG_S_C_C;

	/**
	 * Returns all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_C_C(
		long groupId, long segmentsExperienceId, long classNameId,
		long classPK) {

		return findByG_S_C_C(
			groupId, segmentsExperienceId, classNameId, classPK,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_C_C(
		long groupId, long segmentsExperienceId, long classNameId, long classPK,
		int start, int end) {

		return findByG_S_C_C(
			groupId, segmentsExperienceId, classNameId, classPK, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_C_C(
		long groupId, long segmentsExperienceId, long classNameId, long classPK,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findByG_S_C_C(
			groupId, segmentsExperienceId, classNameId, classPK, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findByG_S_C_C(
		long groupId, long segmentsExperienceId, long classNameId, long classPK,
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_S_C_C;
				finderArgs = new Object[] {
					groupId, segmentsExperienceId, classNameId, classPK
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_S_C_C;
			finderArgs = new Object[] {
				groupId, segmentsExperienceId, classNameId, classPK, start, end,
				orderByComparator
			};
		}

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (FragmentEntryLink fragmentEntryLink : list) {
					if ((groupId != fragmentEntryLink.getGroupId()) ||
						(segmentsExperienceId !=
							fragmentEntryLink.getSegmentsExperienceId()) ||
						(classNameId != fragmentEntryLink.getClassNameId()) ||
						(classPK != fragmentEntryLink.getClassPK())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(6);
			}

			sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_S_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_C_C_SEGMENTSEXPERIENCEID_2);

			sb.append(_FINDER_COLUMN_G_S_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_S_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(segmentsExperienceId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_S_C_C_First(
			long groupId, long segmentsExperienceId, long classNameId,
			long classPK,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_S_C_C_First(
			groupId, segmentsExperienceId, classNameId, classPK,
			orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", segmentsExperienceId=");
		sb.append(segmentsExperienceId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the first fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_S_C_C_First(
		long groupId, long segmentsExperienceId, long classNameId, long classPK,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		List<FragmentEntryLink> list = findByG_S_C_C(
			groupId, segmentsExperienceId, classNameId, classPK, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link
	 * @throws NoSuchEntryLinkException if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink findByG_S_C_C_Last(
			long groupId, long segmentsExperienceId, long classNameId,
			long classPK,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByG_S_C_C_Last(
			groupId, segmentsExperienceId, classNameId, classPK,
			orderByComparator);

		if (fragmentEntryLink != null) {
			return fragmentEntryLink;
		}

		StringBundler sb = new StringBundler(10);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", segmentsExperienceId=");
		sb.append(segmentsExperienceId);

		sb.append(", classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchEntryLinkException(sb.toString());
	}

	/**
	 * Returns the last fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching fragment entry link, or <code>null</code> if a matching fragment entry link could not be found
	 */
	@Override
	public FragmentEntryLink fetchByG_S_C_C_Last(
		long groupId, long segmentsExperienceId, long classNameId, long classPK,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		int count = countByG_S_C_C(
			groupId, segmentsExperienceId, classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<FragmentEntryLink> list = findByG_S_C_C(
			groupId, segmentsExperienceId, classNameId, classPK, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the fragment entry links before and after the current fragment entry link in the ordered set where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param fragmentEntryLinkId the primary key of the current fragment entry link
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink[] findByG_S_C_C_PrevAndNext(
			long fragmentEntryLinkId, long groupId, long segmentsExperienceId,
			long classNameId, long classPK,
			OrderByComparator<FragmentEntryLink> orderByComparator)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = findByPrimaryKey(
			fragmentEntryLinkId);

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink[] array = new FragmentEntryLinkImpl[3];

			array[0] = getByG_S_C_C_PrevAndNext(
				session, fragmentEntryLink, groupId, segmentsExperienceId,
				classNameId, classPK, orderByComparator, true);

			array[1] = fragmentEntryLink;

			array[2] = getByG_S_C_C_PrevAndNext(
				session, fragmentEntryLink, groupId, segmentsExperienceId,
				classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected FragmentEntryLink getByG_S_C_C_PrevAndNext(
		Session session, FragmentEntryLink fragmentEntryLink, long groupId,
		long segmentsExperienceId, long classNameId, long classPK,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(6);
		}

		sb.append(_SQL_SELECT_FRAGMENTENTRYLINK_WHERE);

		sb.append(_FINDER_COLUMN_G_S_C_C_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_S_C_C_SEGMENTSEXPERIENCEID_2);

		sb.append(_FINDER_COLUMN_G_S_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_G_S_C_C_CLASSPK_2);

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
			sb.append(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(segmentsExperienceId);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						fragmentEntryLink)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<FragmentEntryLink> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByG_S_C_C(
		long groupId, long segmentsExperienceId, long classNameId,
		long classPK) {

		for (FragmentEntryLink fragmentEntryLink :
				findByG_S_C_C(
					groupId, segmentsExperienceId, classNameId, classPK,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links where groupId = &#63; and segmentsExperienceId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param segmentsExperienceId the segments experience ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching fragment entry links
	 */
	@Override
	public int countByG_S_C_C(
		long groupId, long segmentsExperienceId, long classNameId,
		long classPK) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_S_C_C;

			finderArgs = new Object[] {
				groupId, segmentsExperienceId, classNameId, classPK
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs, this);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_COUNT_FRAGMENTENTRYLINK_WHERE);

			sb.append(_FINDER_COLUMN_G_S_C_C_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_S_C_C_SEGMENTSEXPERIENCEID_2);

			sb.append(_FINDER_COLUMN_G_S_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_G_S_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(segmentsExperienceId);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_S_C_C_GROUPID_2 =
		"fragmentEntryLink.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_C_C_SEGMENTSEXPERIENCEID_2 =
		"fragmentEntryLink.segmentsExperienceId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_C_C_CLASSNAMEID_2 =
		"fragmentEntryLink.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_G_S_C_C_CLASSPK_2 =
		"fragmentEntryLink.classPK = ?";

	public FragmentEntryLinkPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(FragmentEntryLink.class);

		setModelImplClass(FragmentEntryLinkImpl.class);
		setModelPKClass(long.class);

		setTable(FragmentEntryLinkTable.INSTANCE);
	}

	/**
	 * Caches the fragment entry link in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryLink the fragment entry link
	 */
	@Override
	public void cacheResult(FragmentEntryLink fragmentEntryLink) {
		if (fragmentEntryLink.getCtCollectionId() != 0) {
			fragmentEntryLink.resetOriginalValues();

			return;
		}

		entityCache.putResult(
			FragmentEntryLinkImpl.class, fragmentEntryLink.getPrimaryKey(),
			fragmentEntryLink);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				fragmentEntryLink.getUuid(), fragmentEntryLink.getGroupId()
			},
			fragmentEntryLink);

		fragmentEntryLink.resetOriginalValues();
	}

	/**
	 * Caches the fragment entry links in the entity cache if it is enabled.
	 *
	 * @param fragmentEntryLinks the fragment entry links
	 */
	@Override
	public void cacheResult(List<FragmentEntryLink> fragmentEntryLinks) {
		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			if (fragmentEntryLink.getCtCollectionId() != 0) {
				fragmentEntryLink.resetOriginalValues();

				continue;
			}

			if (entityCache.getResult(
					FragmentEntryLinkImpl.class,
					fragmentEntryLink.getPrimaryKey()) == null) {

				cacheResult(fragmentEntryLink);
			}
			else {
				fragmentEntryLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all fragment entry links.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(FragmentEntryLinkImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the fragment entry link.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(FragmentEntryLink fragmentEntryLink) {
		entityCache.removeResult(
			FragmentEntryLinkImpl.class, fragmentEntryLink.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(FragmentEntryLinkModelImpl)fragmentEntryLink, true);
	}

	@Override
	public void clearCache(List<FragmentEntryLink> fragmentEntryLinks) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (FragmentEntryLink fragmentEntryLink : fragmentEntryLinks) {
			entityCache.removeResult(
				FragmentEntryLinkImpl.class, fragmentEntryLink.getPrimaryKey());

			clearUniqueFindersCache(
				(FragmentEntryLinkModelImpl)fragmentEntryLink, true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(FragmentEntryLinkImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		FragmentEntryLinkModelImpl fragmentEntryLinkModelImpl) {

		Object[] args = new Object[] {
			fragmentEntryLinkModelImpl.getUuid(),
			fragmentEntryLinkModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, fragmentEntryLinkModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		FragmentEntryLinkModelImpl fragmentEntryLinkModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				fragmentEntryLinkModelImpl.getUuid(),
				fragmentEntryLinkModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				fragmentEntryLinkModelImpl.getOriginalUuid(),
				fragmentEntryLinkModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}
	}

	/**
	 * Creates a new fragment entry link with the primary key. Does not add the fragment entry link to the database.
	 *
	 * @param fragmentEntryLinkId the primary key for the new fragment entry link
	 * @return the new fragment entry link
	 */
	@Override
	public FragmentEntryLink create(long fragmentEntryLinkId) {
		FragmentEntryLink fragmentEntryLink = new FragmentEntryLinkImpl();

		fragmentEntryLink.setNew(true);
		fragmentEntryLink.setPrimaryKey(fragmentEntryLinkId);

		String uuid = PortalUUIDUtil.generate();

		fragmentEntryLink.setUuid(uuid);

		fragmentEntryLink.setCompanyId(CompanyThreadLocal.getCompanyId());

		return fragmentEntryLink;
	}

	/**
	 * Removes the fragment entry link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param fragmentEntryLinkId the primary key of the fragment entry link
	 * @return the fragment entry link that was removed
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink remove(long fragmentEntryLinkId)
		throws NoSuchEntryLinkException {

		return remove((Serializable)fragmentEntryLinkId);
	}

	/**
	 * Removes the fragment entry link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the fragment entry link
	 * @return the fragment entry link that was removed
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink remove(Serializable primaryKey)
		throws NoSuchEntryLinkException {

		Session session = null;

		try {
			session = openSession();

			FragmentEntryLink fragmentEntryLink =
				(FragmentEntryLink)session.get(
					FragmentEntryLinkImpl.class, primaryKey);

			if (fragmentEntryLink == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryLinkException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(fragmentEntryLink);
		}
		catch (NoSuchEntryLinkException noSuchEntityException) {
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
	protected FragmentEntryLink removeImpl(
		FragmentEntryLink fragmentEntryLink) {

		if (!ctPersistenceHelper.isRemove(fragmentEntryLink)) {
			return fragmentEntryLink;
		}

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(fragmentEntryLink)) {
				fragmentEntryLink = (FragmentEntryLink)session.get(
					FragmentEntryLinkImpl.class,
					fragmentEntryLink.getPrimaryKeyObj());
			}

			if (fragmentEntryLink != null) {
				session.delete(fragmentEntryLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fragmentEntryLink != null) {
			clearCache(fragmentEntryLink);
		}

		return fragmentEntryLink;
	}

	@Override
	public FragmentEntryLink updateImpl(FragmentEntryLink fragmentEntryLink) {
		boolean isNew = fragmentEntryLink.isNew();

		if (!(fragmentEntryLink instanceof FragmentEntryLinkModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(fragmentEntryLink.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					fragmentEntryLink);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in fragmentEntryLink proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom FragmentEntryLink implementation " +
					fragmentEntryLink.getClass());
		}

		FragmentEntryLinkModelImpl fragmentEntryLinkModelImpl =
			(FragmentEntryLinkModelImpl)fragmentEntryLink;

		if (Validator.isNull(fragmentEntryLink.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			fragmentEntryLink.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (fragmentEntryLink.getCreateDate() == null)) {
			if (serviceContext == null) {
				fragmentEntryLink.setCreateDate(now);
			}
			else {
				fragmentEntryLink.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!fragmentEntryLinkModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				fragmentEntryLink.setModifiedDate(now);
			}
			else {
				fragmentEntryLink.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(fragmentEntryLink)) {
				if (!isNew) {
					session.evict(
						FragmentEntryLinkImpl.class,
						fragmentEntryLink.getPrimaryKeyObj());
				}

				session.save(fragmentEntryLink);

				fragmentEntryLink.setNew(false);
			}
			else {
				fragmentEntryLink = (FragmentEntryLink)session.merge(
					fragmentEntryLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (fragmentEntryLink.getCtCollectionId() != 0) {
			fragmentEntryLink.resetOriginalValues();

			return fragmentEntryLink;
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew) {
			Object[] args = new Object[] {fragmentEntryLinkModelImpl.getUuid()};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				fragmentEntryLinkModelImpl.getUuid(),
				fragmentEntryLinkModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {fragmentEntryLinkModelImpl.getGroupId()};

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				fragmentEntryLinkModelImpl.getFragmentEntryId()
			};

			finderCache.removeResult(_finderPathCountByFragmentEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByFragmentEntryId, args);

			args = new Object[] {fragmentEntryLinkModelImpl.getRendererKey()};

			finderCache.removeResult(_finderPathCountByRendererKey, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByRendererKey, args);

			args = new Object[] {
				fragmentEntryLinkModelImpl.getGroupId(),
				fragmentEntryLinkModelImpl.getFragmentEntryId()
			};

			finderCache.removeResult(_finderPathCountByG_F, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_F, args);

			args = new Object[] {
				fragmentEntryLinkModelImpl.getGroupId(),
				fragmentEntryLinkModelImpl.getPlid()
			};

			finderCache.removeResult(_finderPathCountByG_P, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_P, args);

			args = new Object[] {
				fragmentEntryLinkModelImpl.getGroupId(),
				fragmentEntryLinkModelImpl.getFragmentEntryId(),
				fragmentEntryLinkModelImpl.getClassNameId()
			};

			finderCache.removeResult(_finderPathCountByG_F_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_F_C, args);

			args = new Object[] {
				fragmentEntryLinkModelImpl.getGroupId(),
				fragmentEntryLinkModelImpl.getFragmentEntryId(),
				fragmentEntryLinkModelImpl.getPlid()
			};

			finderCache.removeResult(_finderPathCountByG_F_P, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_F_P, args);

			args = new Object[] {
				fragmentEntryLinkModelImpl.getGroupId(),
				fragmentEntryLinkModelImpl.getSegmentsExperienceId(),
				fragmentEntryLinkModelImpl.getPlid()
			};

			finderCache.removeResult(_finderPathCountByG_S_P, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_S_P, args);

			args = new Object[] {
				fragmentEntryLinkModelImpl.getGroupId(),
				fragmentEntryLinkModelImpl.getClassNameId(),
				fragmentEntryLinkModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByG_C_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_C_C, args);

			args = new Object[] {
				fragmentEntryLinkModelImpl.getGroupId(),
				fragmentEntryLinkModelImpl.getFragmentEntryId(),
				fragmentEntryLinkModelImpl.getClassNameId(),
				fragmentEntryLinkModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByG_F_C_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_F_C_C, args);

			args = new Object[] {
				fragmentEntryLinkModelImpl.getGroupId(),
				fragmentEntryLinkModelImpl.getSegmentsExperienceId(),
				fragmentEntryLinkModelImpl.getClassNameId(),
				fragmentEntryLinkModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByG_S_C_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByG_S_C_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryLinkModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {fragmentEntryLinkModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryLinkModelImpl.getOriginalUuid(),
					fragmentEntryLinkModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					fragmentEntryLinkModelImpl.getUuid(),
					fragmentEntryLinkModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryLinkModelImpl.getOriginalGroupId()
				};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {fragmentEntryLinkModelImpl.getGroupId()};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByFragmentEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryLinkModelImpl.getOriginalFragmentEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByFragmentEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFragmentEntryId, args);

				args = new Object[] {
					fragmentEntryLinkModelImpl.getFragmentEntryId()
				};

				finderCache.removeResult(
					_finderPathCountByFragmentEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByFragmentEntryId, args);
			}

			if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByRendererKey.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryLinkModelImpl.getOriginalRendererKey()
				};

				finderCache.removeResult(_finderPathCountByRendererKey, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByRendererKey, args);

				args = new Object[] {
					fragmentEntryLinkModelImpl.getRendererKey()
				};

				finderCache.removeResult(_finderPathCountByRendererKey, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByRendererKey, args);
			}

			if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_F.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryLinkModelImpl.getOriginalGroupId(),
					fragmentEntryLinkModelImpl.getOriginalFragmentEntryId()
				};

				finderCache.removeResult(_finderPathCountByG_F, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_F, args);

				args = new Object[] {
					fragmentEntryLinkModelImpl.getGroupId(),
					fragmentEntryLinkModelImpl.getFragmentEntryId()
				};

				finderCache.removeResult(_finderPathCountByG_F, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_F, args);
			}

			if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryLinkModelImpl.getOriginalGroupId(),
					fragmentEntryLinkModelImpl.getOriginalPlid()
				};

				finderCache.removeResult(_finderPathCountByG_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_P, args);

				args = new Object[] {
					fragmentEntryLinkModelImpl.getGroupId(),
					fragmentEntryLinkModelImpl.getPlid()
				};

				finderCache.removeResult(_finderPathCountByG_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_P, args);
			}

			if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_F_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryLinkModelImpl.getOriginalGroupId(),
					fragmentEntryLinkModelImpl.getOriginalFragmentEntryId(),
					fragmentEntryLinkModelImpl.getOriginalClassNameId()
				};

				finderCache.removeResult(_finderPathCountByG_F_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_F_C, args);

				args = new Object[] {
					fragmentEntryLinkModelImpl.getGroupId(),
					fragmentEntryLinkModelImpl.getFragmentEntryId(),
					fragmentEntryLinkModelImpl.getClassNameId()
				};

				finderCache.removeResult(_finderPathCountByG_F_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_F_C, args);
			}

			if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_F_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryLinkModelImpl.getOriginalGroupId(),
					fragmentEntryLinkModelImpl.getOriginalFragmentEntryId(),
					fragmentEntryLinkModelImpl.getOriginalPlid()
				};

				finderCache.removeResult(_finderPathCountByG_F_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_F_P, args);

				args = new Object[] {
					fragmentEntryLinkModelImpl.getGroupId(),
					fragmentEntryLinkModelImpl.getFragmentEntryId(),
					fragmentEntryLinkModelImpl.getPlid()
				};

				finderCache.removeResult(_finderPathCountByG_F_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_F_P, args);
			}

			if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_S_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryLinkModelImpl.getOriginalGroupId(),
					fragmentEntryLinkModelImpl.
						getOriginalSegmentsExperienceId(),
					fragmentEntryLinkModelImpl.getOriginalPlid()
				};

				finderCache.removeResult(_finderPathCountByG_S_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_S_P, args);

				args = new Object[] {
					fragmentEntryLinkModelImpl.getGroupId(),
					fragmentEntryLinkModelImpl.getSegmentsExperienceId(),
					fragmentEntryLinkModelImpl.getPlid()
				};

				finderCache.removeResult(_finderPathCountByG_S_P, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_S_P, args);
			}

			if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_C_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					fragmentEntryLinkModelImpl.getOriginalGroupId(),
					fragmentEntryLinkModelImpl.getOriginalClassNameId(),
					fragmentEntryLinkModelImpl.getOriginalClassPK()
				};

				finderCache.removeResult(_finderPathCountByG_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C, args);

				args = new Object[] {
					fragmentEntryLinkModelImpl.getGroupId(),
					fragmentEntryLinkModelImpl.getClassNameId(),
					fragmentEntryLinkModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByG_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_C_C, args);
			}

			if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_F_C_C.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryLinkModelImpl.getOriginalGroupId(),
					fragmentEntryLinkModelImpl.getOriginalFragmentEntryId(),
					fragmentEntryLinkModelImpl.getOriginalClassNameId(),
					fragmentEntryLinkModelImpl.getOriginalClassPK()
				};

				finderCache.removeResult(_finderPathCountByG_F_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_F_C_C, args);

				args = new Object[] {
					fragmentEntryLinkModelImpl.getGroupId(),
					fragmentEntryLinkModelImpl.getFragmentEntryId(),
					fragmentEntryLinkModelImpl.getClassNameId(),
					fragmentEntryLinkModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByG_F_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_F_C_C, args);
			}

			if ((fragmentEntryLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_S_C_C.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					fragmentEntryLinkModelImpl.getOriginalGroupId(),
					fragmentEntryLinkModelImpl.
						getOriginalSegmentsExperienceId(),
					fragmentEntryLinkModelImpl.getOriginalClassNameId(),
					fragmentEntryLinkModelImpl.getOriginalClassPK()
				};

				finderCache.removeResult(_finderPathCountByG_S_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_S_C_C, args);

				args = new Object[] {
					fragmentEntryLinkModelImpl.getGroupId(),
					fragmentEntryLinkModelImpl.getSegmentsExperienceId(),
					fragmentEntryLinkModelImpl.getClassNameId(),
					fragmentEntryLinkModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByG_S_C_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByG_S_C_C, args);
			}
		}

		entityCache.putResult(
			FragmentEntryLinkImpl.class, fragmentEntryLink.getPrimaryKey(),
			fragmentEntryLink, false);

		clearUniqueFindersCache(fragmentEntryLinkModelImpl, false);
		cacheUniqueFindersCache(fragmentEntryLinkModelImpl);

		fragmentEntryLink.resetOriginalValues();

		return fragmentEntryLink;
	}

	/**
	 * Returns the fragment entry link with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment entry link
	 * @return the fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryLinkException {

		FragmentEntryLink fragmentEntryLink = fetchByPrimaryKey(primaryKey);

		if (fragmentEntryLink == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryLinkException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return fragmentEntryLink;
	}

	/**
	 * Returns the fragment entry link with the primary key or throws a <code>NoSuchEntryLinkException</code> if it could not be found.
	 *
	 * @param fragmentEntryLinkId the primary key of the fragment entry link
	 * @return the fragment entry link
	 * @throws NoSuchEntryLinkException if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink findByPrimaryKey(long fragmentEntryLinkId)
		throws NoSuchEntryLinkException {

		return findByPrimaryKey((Serializable)fragmentEntryLinkId);
	}

	/**
	 * Returns the fragment entry link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the fragment entry link
	 * @return the fragment entry link, or <code>null</code> if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(FragmentEntryLink.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		FragmentEntryLink fragmentEntryLink = null;

		Session session = null;

		try {
			session = openSession();

			fragmentEntryLink = (FragmentEntryLink)session.get(
				FragmentEntryLinkImpl.class, primaryKey);

			if (fragmentEntryLink != null) {
				cacheResult(fragmentEntryLink);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return fragmentEntryLink;
	}

	/**
	 * Returns the fragment entry link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param fragmentEntryLinkId the primary key of the fragment entry link
	 * @return the fragment entry link, or <code>null</code> if a fragment entry link with the primary key could not be found
	 */
	@Override
	public FragmentEntryLink fetchByPrimaryKey(long fragmentEntryLinkId) {
		return fetchByPrimaryKey((Serializable)fragmentEntryLinkId);
	}

	@Override
	public Map<Serializable, FragmentEntryLink> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(FragmentEntryLink.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, FragmentEntryLink> map =
			new HashMap<Serializable, FragmentEntryLink>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			FragmentEntryLink fragmentEntryLink = fetchByPrimaryKey(primaryKey);

			if (fragmentEntryLink != null) {
				map.put(primaryKey, fragmentEntryLink);
			}

			return map;
		}

		StringBundler sb = new StringBundler(primaryKeys.size() * 2 + 1);

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

			for (FragmentEntryLink fragmentEntryLink :
					(List<FragmentEntryLink>)query.list()) {

				map.put(
					fragmentEntryLink.getPrimaryKeyObj(), fragmentEntryLink);

				cacheResult(fragmentEntryLink);
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
	 * Returns all the fragment entry links.
	 *
	 * @return the fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the fragment entry links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @return the range of fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the fragment entry links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findAll(
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the fragment entry links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>FragmentEntryLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of fragment entry links
	 * @param end the upper bound of the range of fragment entry links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of fragment entry links
	 */
	@Override
	public List<FragmentEntryLink> findAll(
		int start, int end,
		OrderByComparator<FragmentEntryLink> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

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

		List<FragmentEntryLink> list = null;

		if (useFinderCache && productionMode) {
			list = (List<FragmentEntryLink>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_FRAGMENTENTRYLINK);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_FRAGMENTENTRYLINK;

				sql = sql.concat(FragmentEntryLinkModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<FragmentEntryLink>)QueryUtil.list(
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
	 * Removes all the fragment entry links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (FragmentEntryLink fragmentEntryLink : findAll()) {
			remove(fragmentEntryLink);
		}
	}

	/**
	 * Returns the number of fragment entry links.
	 *
	 * @return the number of fragment entry links
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			FragmentEntryLink.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY, this);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_FRAGMENTENTRYLINK);

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
		return "fragmentEntryLinkId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_FRAGMENTENTRYLINK;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.get(ctColumnResolutionType);
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return FragmentEntryLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "FragmentEntryLink";
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
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("originalFragmentEntryLinkId");
		ctStrictColumnNames.add("fragmentEntryId");
		ctStrictColumnNames.add("segmentsExperienceId");
		ctStrictColumnNames.add("classNameId");
		ctStrictColumnNames.add("classPK");
		ctStrictColumnNames.add("plid");
		ctStrictColumnNames.add("css");
		ctStrictColumnNames.add("html");
		ctStrictColumnNames.add("js");
		ctStrictColumnNames.add("configuration");
		ctStrictColumnNames.add("editableValues");
		ctStrictColumnNames.add("namespace");
		ctStrictColumnNames.add("position");
		ctStrictColumnNames.add("rendererKey");
		ctStrictColumnNames.add("lastPropagationDate");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(CTColumnResolutionType.MERGE, ctMergeColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("fragmentEntryLinkId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId"});
	}

	/**
	 * Initializes the fragment entry link persistence.
	 */
	@Activate
	public void activate() {
		_finderPathWithPaginationFindAll = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			FragmentEntryLinkModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid", new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			FragmentEntryLinkModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			FragmentEntryLinkModelImpl.UUID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.COMPANYID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()},
			FragmentEntryLinkModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByFragmentEntryId = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByFragmentEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByFragmentEntryId = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFragmentEntryId",
			new String[] {Long.class.getName()},
			FragmentEntryLinkModelImpl.FRAGMENTENTRYID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByFragmentEntryId = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByFragmentEntryId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByRendererKey = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByRendererKey",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByRendererKey = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByRendererKey",
			new String[] {String.class.getName()},
			FragmentEntryLinkModelImpl.RENDERERKEY_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByRendererKey = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByRendererKey", new String[] {String.class.getName()});

		_finderPathWithPaginationFindByG_F = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_F = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F",
			new String[] {Long.class.getName(), Long.class.getName()},
			FragmentEntryLinkModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.FRAGMENTENTRYID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByG_F = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_F",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_P = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			FragmentEntryLinkModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.PLID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByG_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByG_F_C = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_F_C = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			FragmentEntryLinkModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.FRAGMENTENTRYID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByG_F_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_F_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByG_F_P = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_F_P = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			FragmentEntryLinkModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.FRAGMENTENTRYID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.PLID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByG_F_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_F_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByG_S_P = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_S_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_S_P = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			FragmentEntryLinkModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.SEGMENTSEXPERIENCEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.PLID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByG_S_P = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_S_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByG_C_C = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_C_C = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			FragmentEntryLinkModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByG_C_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByG_F_C_C = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_F_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_F_C_C = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_F_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			},
			FragmentEntryLinkModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.FRAGMENTENTRYID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByG_F_C_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_F_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByG_S_C_C = new FinderPath(
			FragmentEntryLinkImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_S_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_S_C_C = new FinderPath(
			FragmentEntryLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_S_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			},
			FragmentEntryLinkModelImpl.GROUPID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.SEGMENTSEXPERIENCEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			FragmentEntryLinkModelImpl.POSITION_COLUMN_BITMASK);

		_finderPathCountByG_S_C_C = new FinderPath(
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_S_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Long.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(FragmentEntryLinkImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = FragmentPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_FRAGMENTENTRYLINK =
		"SELECT fragmentEntryLink FROM FragmentEntryLink fragmentEntryLink";

	private static final String _SQL_SELECT_FRAGMENTENTRYLINK_WHERE =
		"SELECT fragmentEntryLink FROM FragmentEntryLink fragmentEntryLink WHERE ";

	private static final String _SQL_COUNT_FRAGMENTENTRYLINK =
		"SELECT COUNT(fragmentEntryLink) FROM FragmentEntryLink fragmentEntryLink";

	private static final String _SQL_COUNT_FRAGMENTENTRYLINK_WHERE =
		"SELECT COUNT(fragmentEntryLink) FROM FragmentEntryLink fragmentEntryLink WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "fragmentEntryLink.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No FragmentEntryLink exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No FragmentEntryLink exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		FragmentEntryLinkPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(FragmentPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException classNotFoundException) {
			throw new ExceptionInInitializerError(classNotFoundException);
		}
	}

}