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

package com.liferay.style.book.service.persistence.impl;

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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.style.book.exception.NoSuchEntryException;
import com.liferay.style.book.model.StyleBookEntry;
import com.liferay.style.book.model.StyleBookEntryTable;
import com.liferay.style.book.model.impl.StyleBookEntryImpl;
import com.liferay.style.book.model.impl.StyleBookEntryModelImpl;
import com.liferay.style.book.service.persistence.StyleBookEntryPersistence;
import com.liferay.style.book.service.persistence.impl.constants.StyleBookPersistenceConstants;

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
 * The persistence implementation for the style book entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = {StyleBookEntryPersistence.class, BasePersistence.class})
public class StyleBookEntryPersistenceImpl
	extends BasePersistenceImpl<StyleBookEntry>
	implements StyleBookEntryPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>StyleBookEntryUtil</code> to access the style book entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		StyleBookEntryImpl.class.getName();

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
	 * Returns all the style book entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

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

		List<StyleBookEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if (!uuid.equals(styleBookEntry.getUuid())) {
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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

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
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByUuid_First(
			String uuid, OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByUuid_First(
		String uuid, OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByUuid_Last(
			String uuid, OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByUuid_Last(
		String uuid, OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByUuid_PrevAndNext(
			long styleBookEntryId, String uuid,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, styleBookEntry, uuid, orderByComparator, true);

			array[1] = styleBookEntry;

			array[2] = getByUuid_PrevAndNext(
				session, styleBookEntry, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected StyleBookEntry getByUuid_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, String uuid,
		OrderByComparator<StyleBookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (StyleBookEntry styleBookEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

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

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

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
		"styleBookEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(styleBookEntry.uuid IS NULL OR styleBookEntry.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_Head;
	private FinderPath _finderPathWithoutPaginationFindByUuid_Head;
	private FinderPath _finderPathCountByUuid_Head;

	/**
	 * Returns all the style book entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid_Head(String uuid, boolean head) {
		return findByUuid_Head(
			uuid, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end) {

		return findByUuid_Head(uuid, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByUuid_Head(uuid, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid_Head(
		String uuid, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_Head;
				finderArgs = new Object[] {uuid, head};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_Head;
			finderArgs = new Object[] {
				uuid, head, start, end, orderByComparator
			};
		}

		List<StyleBookEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if (!uuid.equals(styleBookEntry.getUuid()) ||
						(head != styleBookEntry.isHead())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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

				queryPos.add(head);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByUuid_Head_First(
			String uuid, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByUuid_Head_First(
			uuid, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByUuid_Head_First(
		String uuid, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByUuid_Head(
			uuid, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByUuid_Head_Last(
			String uuid, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByUuid_Head_Last(
			uuid, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByUuid_Head_Last(
		String uuid, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByUuid_Head(uuid, head);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByUuid_Head(
			uuid, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByUuid_Head_PrevAndNext(
			long styleBookEntryId, String uuid, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByUuid_Head_PrevAndNext(
				session, styleBookEntry, uuid, head, orderByComparator, true);

			array[1] = styleBookEntry;

			array[2] = getByUuid_Head_PrevAndNext(
				session, styleBookEntry, uuid, head, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected StyleBookEntry getByUuid_Head_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, String uuid,
		boolean head, OrderByComparator<StyleBookEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_HEAD_HEAD_2);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where uuid = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 */
	@Override
	public void removeByUuid_Head(String uuid, boolean head) {
		for (StyleBookEntry styleBookEntry :
				findByUuid_Head(
					uuid, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where uuid = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByUuid_Head(String uuid, boolean head) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_Head;

			finderArgs = new Object[] {uuid, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_UUID_HEAD_UUID_2 =
		"styleBookEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_HEAD_UUID_3 =
		"(styleBookEntry.uuid IS NULL OR styleBookEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_HEAD_HEAD_2 =
		"styleBookEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByUUID_G;
	private FinderPath _finderPathWithoutPaginationFindByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns all the style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUUID_G(String uuid, long groupId) {
		return findByUUID_G(
			uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUUID_G(
		String uuid, long groupId, int start, int end) {

		return findByUUID_G(uuid, groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByUUID_G(uuid, groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUUID_G(
		String uuid, long groupId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUUID_G;
				finderArgs = new Object[] {uuid, groupId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUUID_G;
			finderArgs = new Object[] {
				uuid, groupId, start, end, orderByComparator
			};
		}

		List<StyleBookEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if (!uuid.equals(styleBookEntry.getUuid()) ||
						(groupId != styleBookEntry.getGroupId())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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

				queryPos.add(groupId);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByUUID_G_First(
			String uuid, long groupId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByUUID_G_First(
			uuid, groupId, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByUUID_G_First(
		String uuid, long groupId,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByUUID_G(
			uuid, groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByUUID_G_Last(
			String uuid, long groupId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByUUID_G_Last(
			uuid, groupId, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByUUID_G_Last(
		String uuid, long groupId,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByUUID_G(uuid, groupId);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByUUID_G(
			uuid, groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63; and groupId = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByUUID_G_PrevAndNext(
			long styleBookEntryId, String uuid, long groupId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByUUID_G_PrevAndNext(
				session, styleBookEntry, uuid, groupId, orderByComparator,
				true);

			array[1] = styleBookEntry;

			array[2] = getByUUID_G_PrevAndNext(
				session, styleBookEntry, uuid, groupId, orderByComparator,
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

	protected StyleBookEntry getByUUID_G_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, String uuid,
		long groupId, OrderByComparator<StyleBookEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_G_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_G_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 */
	@Override
	public void removeByUUID_G(String uuid, long groupId) {
		for (StyleBookEntry styleBookEntry :
				findByUUID_G(
					uuid, groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

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

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

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
		"styleBookEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(styleBookEntry.uuid IS NULL OR styleBookEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"styleBookEntry.groupId = ?";

	private FinderPath _finderPathFetchByUUID_G_Head;
	private FinderPath _finderPathCountByUUID_G_Head;

	/**
	 * Returns the style book entry where uuid = &#63; and groupId = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByUUID_G_Head(uuid, groupId, head);

		if (styleBookEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("uuid=");
			sb.append(uuid);

			sb.append(", groupId=");
			sb.append(groupId);

			sb.append(", head=");
			sb.append(head);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return styleBookEntry;
	}

	/**
	 * Returns the style book entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head) {

		return fetchByUUID_G_Head(uuid, groupId, head, true);
	}

	/**
	 * Returns the style book entry where uuid = &#63; and groupId = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByUUID_G_Head(
		String uuid, long groupId, boolean head, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {uuid, groupId, head};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G_Head, finderArgs);
		}

		if (result instanceof StyleBookEntry) {
			StyleBookEntry styleBookEntry = (StyleBookEntry)result;

			if (!Objects.equals(uuid, styleBookEntry.getUuid()) ||
				(groupId != styleBookEntry.getGroupId()) ||
				(head != styleBookEntry.isHead())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_UUID_G_HEAD_HEAD_2);

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

				queryPos.add(head);

				List<StyleBookEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByUUID_G_Head, finderArgs, list);
					}
				}
				else {
					StyleBookEntry styleBookEntry = list.get(0);

					result = styleBookEntry;

					cacheResult(styleBookEntry);
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
			return (StyleBookEntry)result;
		}
	}

	/**
	 * Removes the style book entry where uuid = &#63; and groupId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the style book entry that was removed
	 */
	@Override
	public StyleBookEntry removeByUUID_G_Head(
			String uuid, long groupId, boolean head)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = findByUUID_G_Head(uuid, groupId, head);

		return remove(styleBookEntry);
	}

	/**
	 * Returns the number of style book entries where uuid = &#63; and groupId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByUUID_G_Head(String uuid, long groupId, boolean head) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUUID_G_Head;

			finderArgs = new Object[] {uuid, groupId, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_G_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_G_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_UUID_G_HEAD_HEAD_2);

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

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_UUID_G_HEAD_UUID_2 =
		"styleBookEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_HEAD_UUID_3 =
		"(styleBookEntry.uuid IS NULL OR styleBookEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_HEAD_GROUPID_2 =
		"styleBookEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_HEAD_HEAD_2 =
		"styleBookEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

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

		List<StyleBookEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if (!uuid.equals(styleBookEntry.getUuid()) ||
						(companyId != styleBookEntry.getCompanyId())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

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
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
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
	 * Returns the first style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
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
	 * Returns the last style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByUuid_C_PrevAndNext(
			long styleBookEntryId, String uuid, long companyId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, styleBookEntry, uuid, companyId, orderByComparator,
				true);

			array[1] = styleBookEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, styleBookEntry, uuid, companyId, orderByComparator,
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

	protected StyleBookEntry getByUuid_C_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, String uuid,
		long companyId, OrderByComparator<StyleBookEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (StyleBookEntry styleBookEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

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

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

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
		"styleBookEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(styleBookEntry.uuid IS NULL OR styleBookEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"styleBookEntry.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C_Head;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C_Head;
	private FinderPath _finderPathCountByUuid_C_Head;

	/**
	 * Returns all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head) {

		return findByUuid_C_Head(
			uuid, companyId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end) {

		return findByUuid_C_Head(uuid, companyId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByUuid_C_Head(
			uuid, companyId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByUuid_C_Head(
		String uuid, long companyId, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C_Head;
				finderArgs = new Object[] {uuid, companyId, head};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C_Head;
			finderArgs = new Object[] {
				uuid, companyId, head, start, end, orderByComparator
			};
		}

		List<StyleBookEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if (!uuid.equals(styleBookEntry.getUuid()) ||
						(companyId != styleBookEntry.getCompanyId()) ||
						(head != styleBookEntry.isHead())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2);

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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

				queryPos.add(head);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByUuid_C_Head_First(
			String uuid, long companyId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByUuid_C_Head_First(
			uuid, companyId, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByUuid_C_Head_First(
		String uuid, long companyId, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByUuid_C_Head(
			uuid, companyId, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByUuid_C_Head_Last(
			String uuid, long companyId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByUuid_C_Head_Last(
			uuid, companyId, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByUuid_C_Head_Last(
		String uuid, long companyId, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByUuid_C_Head(uuid, companyId, head);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByUuid_C_Head(
			uuid, companyId, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByUuid_C_Head_PrevAndNext(
			long styleBookEntryId, String uuid, long companyId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByUuid_C_Head_PrevAndNext(
				session, styleBookEntry, uuid, companyId, head,
				orderByComparator, true);

			array[1] = styleBookEntry;

			array[2] = getByUuid_C_Head_PrevAndNext(
				session, styleBookEntry, uuid, companyId, head,
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

	protected StyleBookEntry getByUuid_C_Head_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, String uuid,
		long companyId, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2);

		sb.append(_FINDER_COLUMN_UUID_C_HEAD_HEAD_2);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where uuid = &#63; and companyId = &#63; and head = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 */
	@Override
	public void removeByUuid_C_Head(String uuid, long companyId, boolean head) {
		for (StyleBookEntry styleBookEntry :
				findByUuid_C_Head(
					uuid, companyId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where uuid = &#63; and companyId = &#63; and head = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByUuid_C_Head(String uuid, long companyId, boolean head) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C_Head;

			finderArgs = new Object[] {uuid, companyId, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_HEAD_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2);

			sb.append(_FINDER_COLUMN_UUID_C_HEAD_HEAD_2);

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

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_UUID_C_HEAD_UUID_2 =
		"styleBookEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_HEAD_UUID_3 =
		"(styleBookEntry.uuid IS NULL OR styleBookEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_HEAD_COMPANYID_2 =
		"styleBookEntry.companyId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_HEAD_HEAD_2 =
		"styleBookEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the style book entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId(
		long groupId, int start, int end) {

		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

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

		List<StyleBookEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if (groupId != styleBookEntry.getGroupId()) {
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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByGroupId_First(
			long groupId, OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByGroupId_First(
			groupId, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByGroupId_First(
		long groupId, OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByGroupId_Last(
			long groupId, OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByGroupId_Last(
		long groupId, OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByGroupId_PrevAndNext(
			long styleBookEntryId, long groupId,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, styleBookEntry, groupId, orderByComparator, true);

			array[1] = styleBookEntry;

			array[2] = getByGroupId_PrevAndNext(
				session, styleBookEntry, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected StyleBookEntry getByGroupId_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		OrderByComparator<StyleBookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
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
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (StyleBookEntry styleBookEntry :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId;

			finderArgs = new Object[] {groupId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

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
		"styleBookEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId_Head;
	private FinderPath _finderPathWithoutPaginationFindByGroupId_Head;
	private FinderPath _finderPathCountByGroupId_Head;

	/**
	 * Returns all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId_Head(long groupId, boolean head) {
		return findByGroupId_Head(
			groupId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end) {

		return findByGroupId_Head(groupId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByGroupId_Head(
			groupId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByGroupId_Head(
		long groupId, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByGroupId_Head;
				finderArgs = new Object[] {groupId, head};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByGroupId_Head;
			finderArgs = new Object[] {
				groupId, head, start, end, orderByComparator
			};
		}

		List<StyleBookEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if ((groupId != styleBookEntry.getGroupId()) ||
						(head != styleBookEntry.isHead())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(head);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByGroupId_Head_First(
			long groupId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByGroupId_Head_First(
			groupId, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByGroupId_Head_First(
		long groupId, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByGroupId_Head(
			groupId, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByGroupId_Head_Last(
			long groupId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByGroupId_Head_Last(
			groupId, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByGroupId_Head_Last(
		long groupId, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByGroupId_Head(groupId, head);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByGroupId_Head(
			groupId, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByGroupId_Head_PrevAndNext(
			long styleBookEntryId, long groupId, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByGroupId_Head_PrevAndNext(
				session, styleBookEntry, groupId, head, orderByComparator,
				true);

			array[1] = styleBookEntry;

			array[2] = getByGroupId_Head_PrevAndNext(
				session, styleBookEntry, groupId, head, orderByComparator,
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

	protected StyleBookEntry getByGroupId_Head_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		boolean head, OrderByComparator<StyleBookEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

		sb.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_2);

		sb.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 */
	@Override
	public void removeByGroupId_Head(long groupId, boolean head) {
		for (StyleBookEntry styleBookEntry :
				findByGroupId_Head(
					groupId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByGroupId_Head(long groupId, boolean head) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByGroupId_Head;

			finderArgs = new Object[] {groupId, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_GROUPID_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_GROUPID_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_GROUPID_HEAD_GROUPID_2 =
		"styleBookEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_GROUPID_HEAD_HEAD_2 =
		"styleBookEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByG_D;
	private FinderPath _finderPathWithoutPaginationFindByG_D;
	private FinderPath _finderPathCountByG_D;

	/**
	 * Returns all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry) {

		return findByG_D(
			groupId, defaultStyleBookEntry, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end) {

		return findByG_D(groupId, defaultStyleBookEntry, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByG_D(
			groupId, defaultStyleBookEntry, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D(
		long groupId, boolean defaultStyleBookEntry, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_D;
				finderArgs = new Object[] {groupId, defaultStyleBookEntry};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_D;
			finderArgs = new Object[] {
				groupId, defaultStyleBookEntry, start, end, orderByComparator
			};
		}

		List<StyleBookEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if ((groupId != styleBookEntry.getGroupId()) ||
						(defaultStyleBookEntry !=
							styleBookEntry.isDefaultStyleBookEntry())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_D_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_D_DEFAULTSTYLEBOOKENTRY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(defaultStyleBookEntry);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_D_First(
			long groupId, boolean defaultStyleBookEntry,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_D_First(
			groupId, defaultStyleBookEntry, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", defaultStyleBookEntry=");
		sb.append(defaultStyleBookEntry);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_D_First(
		long groupId, boolean defaultStyleBookEntry,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByG_D(
			groupId, defaultStyleBookEntry, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_D_Last(
			long groupId, boolean defaultStyleBookEntry,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_D_Last(
			groupId, defaultStyleBookEntry, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", defaultStyleBookEntry=");
		sb.append(defaultStyleBookEntry);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_D_Last(
		long groupId, boolean defaultStyleBookEntry,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByG_D(groupId, defaultStyleBookEntry);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByG_D(
			groupId, defaultStyleBookEntry, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByG_D_PrevAndNext(
			long styleBookEntryId, long groupId, boolean defaultStyleBookEntry,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByG_D_PrevAndNext(
				session, styleBookEntry, groupId, defaultStyleBookEntry,
				orderByComparator, true);

			array[1] = styleBookEntry;

			array[2] = getByG_D_PrevAndNext(
				session, styleBookEntry, groupId, defaultStyleBookEntry,
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

	protected StyleBookEntry getByG_D_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		boolean defaultStyleBookEntry,
		OrderByComparator<StyleBookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_D_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_D_DEFAULTSTYLEBOOKENTRY_2);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(defaultStyleBookEntry);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 */
	@Override
	public void removeByG_D(long groupId, boolean defaultStyleBookEntry) {
		for (StyleBookEntry styleBookEntry :
				findByG_D(
					groupId, defaultStyleBookEntry, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and defaultStyleBookEntry = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByG_D(long groupId, boolean defaultStyleBookEntry) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_D;

			finderArgs = new Object[] {groupId, defaultStyleBookEntry};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_D_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_D_DEFAULTSTYLEBOOKENTRY_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(defaultStyleBookEntry);

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

	private static final String _FINDER_COLUMN_G_D_GROUPID_2 =
		"styleBookEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_D_DEFAULTSTYLEBOOKENTRY_2 =
		"styleBookEntry.defaultStyleBookEntry = ?";

	private FinderPath _finderPathWithPaginationFindByG_D_Head;
	private FinderPath _finderPathWithoutPaginationFindByG_D_Head;
	private FinderPath _finderPathCountByG_D_Head;

	/**
	 * Returns all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head) {

		return findByG_D_Head(
			groupId, defaultStyleBookEntry, head, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head, int start,
		int end) {

		return findByG_D_Head(
			groupId, defaultStyleBookEntry, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head, int start,
		int end, OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByG_D_Head(
			groupId, defaultStyleBookEntry, head, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head, int start,
		int end, OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_D_Head;
				finderArgs = new Object[] {
					groupId, defaultStyleBookEntry, head
				};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_D_Head;
			finderArgs = new Object[] {
				groupId, defaultStyleBookEntry, head, start, end,
				orderByComparator
			};
		}

		List<StyleBookEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if ((groupId != styleBookEntry.getGroupId()) ||
						(defaultStyleBookEntry !=
							styleBookEntry.isDefaultStyleBookEntry()) ||
						(head != styleBookEntry.isHead())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_D_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_D_HEAD_DEFAULTSTYLEBOOKENTRY_2);

			sb.append(_FINDER_COLUMN_G_D_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(defaultStyleBookEntry);

				queryPos.add(head);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_D_Head_First(
			long groupId, boolean defaultStyleBookEntry, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_D_Head_First(
			groupId, defaultStyleBookEntry, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", defaultStyleBookEntry=");
		sb.append(defaultStyleBookEntry);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_D_Head_First(
		long groupId, boolean defaultStyleBookEntry, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByG_D_Head(
			groupId, defaultStyleBookEntry, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_D_Head_Last(
			long groupId, boolean defaultStyleBookEntry, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_D_Head_Last(
			groupId, defaultStyleBookEntry, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", defaultStyleBookEntry=");
		sb.append(defaultStyleBookEntry);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_D_Head_Last(
		long groupId, boolean defaultStyleBookEntry, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByG_D_Head(groupId, defaultStyleBookEntry, head);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByG_D_Head(
			groupId, defaultStyleBookEntry, head, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByG_D_Head_PrevAndNext(
			long styleBookEntryId, long groupId, boolean defaultStyleBookEntry,
			boolean head, OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByG_D_Head_PrevAndNext(
				session, styleBookEntry, groupId, defaultStyleBookEntry, head,
				orderByComparator, true);

			array[1] = styleBookEntry;

			array[2] = getByG_D_Head_PrevAndNext(
				session, styleBookEntry, groupId, defaultStyleBookEntry, head,
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

	protected StyleBookEntry getByG_D_Head_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		boolean defaultStyleBookEntry, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_D_HEAD_GROUPID_2);

		sb.append(_FINDER_COLUMN_G_D_HEAD_DEFAULTSTYLEBOOKENTRY_2);

		sb.append(_FINDER_COLUMN_G_D_HEAD_HEAD_2);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		queryPos.add(defaultStyleBookEntry);

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 */
	@Override
	public void removeByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head) {

		for (StyleBookEntry styleBookEntry :
				findByG_D_Head(
					groupId, defaultStyleBookEntry, head, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and defaultStyleBookEntry = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param defaultStyleBookEntry the default style book entry
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByG_D_Head(
		long groupId, boolean defaultStyleBookEntry, boolean head) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_D_Head;

			finderArgs = new Object[] {groupId, defaultStyleBookEntry, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_D_HEAD_GROUPID_2);

			sb.append(_FINDER_COLUMN_G_D_HEAD_DEFAULTSTYLEBOOKENTRY_2);

			sb.append(_FINDER_COLUMN_G_D_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				queryPos.add(defaultStyleBookEntry);

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_G_D_HEAD_GROUPID_2 =
		"styleBookEntry.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_D_HEAD_DEFAULTSTYLEBOOKENTRY_2 =
			"styleBookEntry.defaultStyleBookEntry = ? AND ";

	private static final String _FINDER_COLUMN_G_D_HEAD_HEAD_2 =
		"styleBookEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByG_LikeN;
	private FinderPath _finderPathWithPaginationCountByG_LikeN;

	/**
	 * Returns all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN(long groupId, String name) {
		return findByG_LikeN(
			groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN(
		long groupId, String name, int start, int end) {

		return findByG_LikeN(groupId, name, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByG_LikeN(
			groupId, name, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN(
		long groupId, String name, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_LikeN;
		finderArgs = new Object[] {
			groupId, name, start, end, orderByComparator
		};

		List<StyleBookEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if ((groupId != styleBookEntry.getGroupId()) ||
						!StringUtil.wildcardMatches(
							styleBookEntry.getName(), name, '_', '%', '\\',
							true)) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_LikeN_First(
			long groupId, String name,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_LikeN_First(
			groupId, name, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_LikeN_First(
		long groupId, String name,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByG_LikeN(
			groupId, name, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_LikeN_Last(
			long groupId, String name,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_LikeN_Last(
			groupId, name, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_LikeN_Last(
		long groupId, String name,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByG_LikeN(groupId, name);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByG_LikeN(
			groupId, name, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByG_LikeN_PrevAndNext(
			long styleBookEntryId, long groupId, String name,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		name = Objects.toString(name, "");

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByG_LikeN_PrevAndNext(
				session, styleBookEntry, groupId, name, orderByComparator,
				true);

			array[1] = styleBookEntry;

			array[2] = getByG_LikeN_PrevAndNext(
				session, styleBookEntry, groupId, name, orderByComparator,
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

	protected StyleBookEntry getByG_LikeN_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		String name, OrderByComparator<StyleBookEntry> orderByComparator,
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

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindName) {
			queryPos.add(name);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and name LIKE &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 */
	@Override
	public void removeByG_LikeN(long groupId, String name) {
		for (StyleBookEntry styleBookEntry :
				findByG_LikeN(
					groupId, name, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and name LIKE &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByG_LikeN(long groupId, String name) {
		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_LikeN;

			finderArgs = new Object[] {groupId, name};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_NAME_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
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

	private static final String _FINDER_COLUMN_G_LIKEN_GROUPID_2 =
		"styleBookEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_NAME_2 =
		"styleBookEntry.name LIKE ?";

	private static final String _FINDER_COLUMN_G_LIKEN_NAME_3 =
		"(styleBookEntry.name IS NULL OR styleBookEntry.name LIKE '')";

	private FinderPath _finderPathWithPaginationFindByG_LikeN_Head;
	private FinderPath _finderPathWithPaginationCountByG_LikeN_Head;

	/**
	 * Returns all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head) {

		return findByG_LikeN_Head(
			groupId, name, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head, int start, int end) {

		return findByG_LikeN_Head(groupId, name, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByG_LikeN_Head(
			groupId, name, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_LikeN_Head(
		long groupId, String name, boolean head, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByG_LikeN_Head;
		finderArgs = new Object[] {
			groupId, name, head, start, end, orderByComparator
		};

		List<StyleBookEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if ((groupId != styleBookEntry.getGroupId()) ||
						!StringUtil.wildcardMatches(
							styleBookEntry.getName(), name, '_', '%', '\\',
							true) ||
						(head != styleBookEntry.isHead())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(head);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_LikeN_Head_First(
			long groupId, String name, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_LikeN_Head_First(
			groupId, name, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_LikeN_Head_First(
		long groupId, String name, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByG_LikeN_Head(
			groupId, name, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_LikeN_Head_Last(
			long groupId, String name, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_LikeN_Head_Last(
			groupId, name, head, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", nameLIKE");
		sb.append(name);

		sb.append(", head=");
		sb.append(head);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_LikeN_Head_Last(
		long groupId, String name, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByG_LikeN_Head(groupId, name, head);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByG_LikeN_Head(
			groupId, name, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByG_LikeN_Head_PrevAndNext(
			long styleBookEntryId, long groupId, String name, boolean head,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		name = Objects.toString(name, "");

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByG_LikeN_Head_PrevAndNext(
				session, styleBookEntry, groupId, name, head, orderByComparator,
				true);

			array[1] = styleBookEntry;

			array[2] = getByG_LikeN_Head_PrevAndNext(
				session, styleBookEntry, groupId, name, head, orderByComparator,
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

	protected StyleBookEntry getByG_LikeN_Head_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		String name, boolean head,
		OrderByComparator<StyleBookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_GROUPID_2);

		boolean bindName = false;

		if (name.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_NAME_3);
		}
		else {
			bindName = true;

			sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_NAME_2);
		}

		sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_HEAD_2);

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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindName) {
			queryPos.add(name);
		}

		queryPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and name LIKE &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 */
	@Override
	public void removeByG_LikeN_Head(long groupId, String name, boolean head) {
		for (StyleBookEntry styleBookEntry :
				findByG_LikeN_Head(
					groupId, name, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and name LIKE &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param name the name
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByG_LikeN_Head(long groupId, String name, boolean head) {
		name = Objects.toString(name, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathWithPaginationCountByG_LikeN_Head;

			finderArgs = new Object[] {groupId, name, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_GROUPID_2);

			boolean bindName = false;

			if (name.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_NAME_3);
			}
			else {
				bindName = true;

				sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_NAME_2);
			}

			sb.append(_FINDER_COLUMN_G_LIKEN_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindName) {
					queryPos.add(name);
				}

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_G_LIKEN_HEAD_GROUPID_2 =
		"styleBookEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_HEAD_NAME_2 =
		"styleBookEntry.name LIKE ? AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_HEAD_NAME_3 =
		"(styleBookEntry.name IS NULL OR styleBookEntry.name LIKE '') AND ";

	private static final String _FINDER_COLUMN_G_LIKEN_HEAD_HEAD_2 =
		"styleBookEntry.head = ?";

	private FinderPath _finderPathWithPaginationFindByG_SBEK;
	private FinderPath _finderPathWithoutPaginationFindByG_SBEK;
	private FinderPath _finderPathCountByG_SBEK;

	/**
	 * Returns all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @return the matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey) {

		return findByG_SBEK(
			groupId, styleBookEntryKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end) {

		return findByG_SBEK(groupId, styleBookEntryKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findByG_SBEK(
			groupId, styleBookEntryKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching style book entries
	 */
	@Override
	public List<StyleBookEntry> findByG_SBEK(
		long groupId, String styleBookEntryKey, int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByG_SBEK;
				finderArgs = new Object[] {groupId, styleBookEntryKey};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByG_SBEK;
			finderArgs = new Object[] {
				groupId, styleBookEntryKey, start, end, orderByComparator
			};
		}

		List<StyleBookEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (StyleBookEntry styleBookEntry : list) {
					if ((groupId != styleBookEntry.getGroupId()) ||
						!styleBookEntryKey.equals(
							styleBookEntry.getStyleBookEntryKey())) {

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

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_SBEK_GROUPID_2);

			boolean bindStyleBookEntryKey = false;

			if (styleBookEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_3);
			}
			else {
				bindStyleBookEntryKey = true;

				sb.append(_FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindStyleBookEntryKey) {
					queryPos.add(styleBookEntryKey);
				}

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Returns the first style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_SBEK_First(
			long groupId, String styleBookEntryKey,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_SBEK_First(
			groupId, styleBookEntryKey, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", styleBookEntryKey=");
		sb.append(styleBookEntryKey);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the first style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_SBEK_First(
		long groupId, String styleBookEntryKey,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		List<StyleBookEntry> list = findByG_SBEK(
			groupId, styleBookEntryKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_SBEK_Last(
			long groupId, String styleBookEntryKey,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_SBEK_Last(
			groupId, styleBookEntryKey, orderByComparator);

		if (styleBookEntry != null) {
			return styleBookEntry;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("groupId=");
		sb.append(groupId);

		sb.append(", styleBookEntryKey=");
		sb.append(styleBookEntryKey);

		sb.append("}");

		throw new NoSuchEntryException(sb.toString());
	}

	/**
	 * Returns the last style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_SBEK_Last(
		long groupId, String styleBookEntryKey,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		int count = countByG_SBEK(groupId, styleBookEntryKey);

		if (count == 0) {
			return null;
		}

		List<StyleBookEntry> list = findByG_SBEK(
			groupId, styleBookEntryKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the style book entries before and after the current style book entry in the ordered set where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param styleBookEntryId the primary key of the current style book entry
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry[] findByG_SBEK_PrevAndNext(
			long styleBookEntryId, long groupId, String styleBookEntryKey,
			OrderByComparator<StyleBookEntry> orderByComparator)
		throws NoSuchEntryException {

		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		StyleBookEntry styleBookEntry = findByPrimaryKey(styleBookEntryId);

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry[] array = new StyleBookEntryImpl[3];

			array[0] = getByG_SBEK_PrevAndNext(
				session, styleBookEntry, groupId, styleBookEntryKey,
				orderByComparator, true);

			array[1] = styleBookEntry;

			array[2] = getByG_SBEK_PrevAndNext(
				session, styleBookEntry, groupId, styleBookEntryKey,
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

	protected StyleBookEntry getByG_SBEK_PrevAndNext(
		Session session, StyleBookEntry styleBookEntry, long groupId,
		String styleBookEntryKey,
		OrderByComparator<StyleBookEntry> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

		sb.append(_FINDER_COLUMN_G_SBEK_GROUPID_2);

		boolean bindStyleBookEntryKey = false;

		if (styleBookEntryKey.isEmpty()) {
			sb.append(_FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_3);
		}
		else {
			bindStyleBookEntryKey = true;

			sb.append(_FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_2);
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
			sb.append(StyleBookEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(groupId);

		if (bindStyleBookEntryKey) {
			queryPos.add(styleBookEntryKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						styleBookEntry)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<StyleBookEntry> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the style book entries where groupId = &#63; and styleBookEntryKey = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 */
	@Override
	public void removeByG_SBEK(long groupId, String styleBookEntryKey) {
		for (StyleBookEntry styleBookEntry :
				findByG_SBEK(
					groupId, styleBookEntryKey, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and styleBookEntryKey = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByG_SBEK(long groupId, String styleBookEntryKey) {
		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_SBEK;

			finderArgs = new Object[] {groupId, styleBookEntryKey};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_SBEK_GROUPID_2);

			boolean bindStyleBookEntryKey = false;

			if (styleBookEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_3);
			}
			else {
				bindStyleBookEntryKey = true;

				sb.append(_FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindStyleBookEntryKey) {
					queryPos.add(styleBookEntryKey);
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

	private static final String _FINDER_COLUMN_G_SBEK_GROUPID_2 =
		"styleBookEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_2 =
		"styleBookEntry.styleBookEntryKey = ?";

	private static final String _FINDER_COLUMN_G_SBEK_STYLEBOOKENTRYKEY_3 =
		"(styleBookEntry.styleBookEntryKey IS NULL OR styleBookEntry.styleBookEntryKey = '')";

	private FinderPath _finderPathFetchByG_SBEK_Head;
	private FinderPath _finderPathCountByG_SBEK_Head;

	/**
	 * Returns the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByG_SBEK_Head(
			long groupId, String styleBookEntryKey, boolean head)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByG_SBEK_Head(
			groupId, styleBookEntryKey, head);

		if (styleBookEntry == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("groupId=");
			sb.append(groupId);

			sb.append(", styleBookEntryKey=");
			sb.append(styleBookEntryKey);

			sb.append(", head=");
			sb.append(head);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return styleBookEntry;
	}

	/**
	 * Returns the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_SBEK_Head(
		long groupId, String styleBookEntryKey, boolean head) {

		return fetchByG_SBEK_Head(groupId, styleBookEntryKey, head, true);
	}

	/**
	 * Returns the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByG_SBEK_Head(
		long groupId, String styleBookEntryKey, boolean head,
		boolean useFinderCache) {

		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {groupId, styleBookEntryKey, head};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByG_SBEK_Head, finderArgs);
		}

		if (result instanceof StyleBookEntry) {
			StyleBookEntry styleBookEntry = (StyleBookEntry)result;

			if ((groupId != styleBookEntry.getGroupId()) ||
				!Objects.equals(
					styleBookEntryKey, styleBookEntry.getStyleBookEntryKey()) ||
				(head != styleBookEntry.isHead())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_SBEK_HEAD_GROUPID_2);

			boolean bindStyleBookEntryKey = false;

			if (styleBookEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_SBEK_HEAD_STYLEBOOKENTRYKEY_3);
			}
			else {
				bindStyleBookEntryKey = true;

				sb.append(_FINDER_COLUMN_G_SBEK_HEAD_STYLEBOOKENTRYKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_SBEK_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindStyleBookEntryKey) {
					queryPos.add(styleBookEntryKey);
				}

				queryPos.add(head);

				List<StyleBookEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByG_SBEK_Head, finderArgs, list);
					}
				}
				else {
					StyleBookEntry styleBookEntry = list.get(0);

					result = styleBookEntry;

					cacheResult(styleBookEntry);
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
			return (StyleBookEntry)result;
		}
	}

	/**
	 * Removes the style book entry where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the style book entry that was removed
	 */
	@Override
	public StyleBookEntry removeByG_SBEK_Head(
			long groupId, String styleBookEntryKey, boolean head)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = findByG_SBEK_Head(
			groupId, styleBookEntryKey, head);

		return remove(styleBookEntry);
	}

	/**
	 * Returns the number of style book entries where groupId = &#63; and styleBookEntryKey = &#63; and head = &#63;.
	 *
	 * @param groupId the group ID
	 * @param styleBookEntryKey the style book entry key
	 * @param head the head
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByG_SBEK_Head(
		long groupId, String styleBookEntryKey, boolean head) {

		styleBookEntryKey = Objects.toString(styleBookEntryKey, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByG_SBEK_Head;

			finderArgs = new Object[] {groupId, styleBookEntryKey, head};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_G_SBEK_HEAD_GROUPID_2);

			boolean bindStyleBookEntryKey = false;

			if (styleBookEntryKey.isEmpty()) {
				sb.append(_FINDER_COLUMN_G_SBEK_HEAD_STYLEBOOKENTRYKEY_3);
			}
			else {
				bindStyleBookEntryKey = true;

				sb.append(_FINDER_COLUMN_G_SBEK_HEAD_STYLEBOOKENTRYKEY_2);
			}

			sb.append(_FINDER_COLUMN_G_SBEK_HEAD_HEAD_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(groupId);

				if (bindStyleBookEntryKey) {
					queryPos.add(styleBookEntryKey);
				}

				queryPos.add(head);

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

	private static final String _FINDER_COLUMN_G_SBEK_HEAD_GROUPID_2 =
		"styleBookEntry.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_SBEK_HEAD_STYLEBOOKENTRYKEY_2 =
		"styleBookEntry.styleBookEntryKey = ? AND ";

	private static final String _FINDER_COLUMN_G_SBEK_HEAD_STYLEBOOKENTRYKEY_3 =
		"(styleBookEntry.styleBookEntryKey IS NULL OR styleBookEntry.styleBookEntryKey = '') AND ";

	private static final String _FINDER_COLUMN_G_SBEK_HEAD_HEAD_2 =
		"styleBookEntry.head = ?";

	private FinderPath _finderPathFetchByHeadId;
	private FinderPath _finderPathCountByHeadId;

	/**
	 * Returns the style book entry where headId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param headId the head ID
	 * @return the matching style book entry
	 * @throws NoSuchEntryException if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry findByHeadId(long headId)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByHeadId(headId);

		if (styleBookEntry == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("headId=");
			sb.append(headId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchEntryException(sb.toString());
		}

		return styleBookEntry;
	}

	/**
	 * Returns the style book entry where headId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param headId the head ID
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByHeadId(long headId) {
		return fetchByHeadId(headId, true);
	}

	/**
	 * Returns the style book entry where headId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param headId the head ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching style book entry, or <code>null</code> if a matching style book entry could not be found
	 */
	@Override
	public StyleBookEntry fetchByHeadId(long headId, boolean useFinderCache) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {headId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByHeadId, finderArgs);
		}

		if (result instanceof StyleBookEntry) {
			StyleBookEntry styleBookEntry = (StyleBookEntry)result;

			if (headId != styleBookEntry.getHeadId()) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_SELECT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(headId);

				List<StyleBookEntry> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByHeadId, finderArgs, list);
					}
				}
				else {
					StyleBookEntry styleBookEntry = list.get(0);

					result = styleBookEntry;

					cacheResult(styleBookEntry);
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
			return (StyleBookEntry)result;
		}
	}

	/**
	 * Removes the style book entry where headId = &#63; from the database.
	 *
	 * @param headId the head ID
	 * @return the style book entry that was removed
	 */
	@Override
	public StyleBookEntry removeByHeadId(long headId)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = findByHeadId(headId);

		return remove(styleBookEntry);
	}

	/**
	 * Returns the number of style book entries where headId = &#63;.
	 *
	 * @param headId the head ID
	 * @return the number of matching style book entries
	 */
	@Override
	public int countByHeadId(long headId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByHeadId;

			finderArgs = new Object[] {headId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_STYLEBOOKENTRY_WHERE);

			sb.append(_FINDER_COLUMN_HEADID_HEADID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(headId);

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

	private static final String _FINDER_COLUMN_HEADID_HEADID_2 =
		"styleBookEntry.headId = ?";

	public StyleBookEntryPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(StyleBookEntry.class);

		setModelImplClass(StyleBookEntryImpl.class);
		setModelPKClass(long.class);

		setTable(StyleBookEntryTable.INSTANCE);
	}

	/**
	 * Caches the style book entry in the entity cache if it is enabled.
	 *
	 * @param styleBookEntry the style book entry
	 */
	@Override
	public void cacheResult(StyleBookEntry styleBookEntry) {
		if (styleBookEntry.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			StyleBookEntryImpl.class, styleBookEntry.getPrimaryKey(),
			styleBookEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G_Head,
			new Object[] {
				styleBookEntry.getUuid(), styleBookEntry.getGroupId(),
				styleBookEntry.isHead()
			},
			styleBookEntry);

		finderCache.putResult(
			_finderPathFetchByG_SBEK_Head,
			new Object[] {
				styleBookEntry.getGroupId(),
				styleBookEntry.getStyleBookEntryKey(), styleBookEntry.isHead()
			},
			styleBookEntry);

		finderCache.putResult(
			_finderPathFetchByHeadId, new Object[] {styleBookEntry.getHeadId()},
			styleBookEntry);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the style book entries in the entity cache if it is enabled.
	 *
	 * @param styleBookEntries the style book entries
	 */
	@Override
	public void cacheResult(List<StyleBookEntry> styleBookEntries) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (styleBookEntries.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (StyleBookEntry styleBookEntry : styleBookEntries) {
			if (styleBookEntry.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					StyleBookEntryImpl.class, styleBookEntry.getPrimaryKey()) ==
						null) {

				cacheResult(styleBookEntry);
			}
		}
	}

	/**
	 * Clears the cache for all style book entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(StyleBookEntryImpl.class);

		finderCache.clearCache(StyleBookEntryImpl.class);
	}

	/**
	 * Clears the cache for the style book entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(StyleBookEntry styleBookEntry) {
		entityCache.removeResult(StyleBookEntryImpl.class, styleBookEntry);
	}

	@Override
	public void clearCache(List<StyleBookEntry> styleBookEntries) {
		for (StyleBookEntry styleBookEntry : styleBookEntries) {
			entityCache.removeResult(StyleBookEntryImpl.class, styleBookEntry);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(StyleBookEntryImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(StyleBookEntryImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		StyleBookEntryModelImpl styleBookEntryModelImpl) {

		Object[] args = new Object[] {
			styleBookEntryModelImpl.getUuid(),
			styleBookEntryModelImpl.getGroupId(),
			styleBookEntryModelImpl.isHead()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G_Head, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByUUID_G_Head, args, styleBookEntryModelImpl);

		args = new Object[] {
			styleBookEntryModelImpl.getGroupId(),
			styleBookEntryModelImpl.getStyleBookEntryKey(),
			styleBookEntryModelImpl.isHead()
		};

		finderCache.putResult(
			_finderPathCountByG_SBEK_Head, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByG_SBEK_Head, args, styleBookEntryModelImpl);

		args = new Object[] {styleBookEntryModelImpl.getHeadId()};

		finderCache.putResult(_finderPathCountByHeadId, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByHeadId, args, styleBookEntryModelImpl);
	}

	/**
	 * Creates a new style book entry with the primary key. Does not add the style book entry to the database.
	 *
	 * @param styleBookEntryId the primary key for the new style book entry
	 * @return the new style book entry
	 */
	@Override
	public StyleBookEntry create(long styleBookEntryId) {
		StyleBookEntry styleBookEntry = new StyleBookEntryImpl();

		styleBookEntry.setNew(true);
		styleBookEntry.setPrimaryKey(styleBookEntryId);

		String uuid = PortalUUIDUtil.generate();

		styleBookEntry.setUuid(uuid);

		styleBookEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return styleBookEntry;
	}

	/**
	 * Removes the style book entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry that was removed
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry remove(long styleBookEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)styleBookEntryId);
	}

	/**
	 * Removes the style book entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the style book entry
	 * @return the style book entry that was removed
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			StyleBookEntry styleBookEntry = (StyleBookEntry)session.get(
				StyleBookEntryImpl.class, primaryKey);

			if (styleBookEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(styleBookEntry);
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
	protected StyleBookEntry removeImpl(StyleBookEntry styleBookEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(styleBookEntry)) {
				styleBookEntry = (StyleBookEntry)session.get(
					StyleBookEntryImpl.class,
					styleBookEntry.getPrimaryKeyObj());
			}

			if ((styleBookEntry != null) &&
				ctPersistenceHelper.isRemove(styleBookEntry)) {

				session.delete(styleBookEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (styleBookEntry != null) {
			clearCache(styleBookEntry);
		}

		return styleBookEntry;
	}

	@Override
	public StyleBookEntry updateImpl(StyleBookEntry styleBookEntry) {
		boolean isNew = styleBookEntry.isNew();

		if (!(styleBookEntry instanceof StyleBookEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(styleBookEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					styleBookEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in styleBookEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom StyleBookEntry implementation " +
					styleBookEntry.getClass());
		}

		StyleBookEntryModelImpl styleBookEntryModelImpl =
			(StyleBookEntryModelImpl)styleBookEntry;

		if (Validator.isNull(styleBookEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			styleBookEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (styleBookEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				styleBookEntry.setCreateDate(date);
			}
			else {
				styleBookEntry.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!styleBookEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				styleBookEntry.setModifiedDate(date);
			}
			else {
				styleBookEntry.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(styleBookEntry)) {
				if (!isNew) {
					session.evict(
						StyleBookEntryImpl.class,
						styleBookEntry.getPrimaryKeyObj());
				}

				session.save(styleBookEntry);
			}
			else {
				styleBookEntry = (StyleBookEntry)session.merge(styleBookEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (styleBookEntry.getCtCollectionId() != 0) {
			if (isNew) {
				styleBookEntry.setNew(false);
			}

			styleBookEntry.resetOriginalValues();

			return styleBookEntry;
		}

		entityCache.putResult(
			StyleBookEntryImpl.class, styleBookEntryModelImpl, false, true);

		cacheUniqueFindersCache(styleBookEntryModelImpl);

		if (isNew) {
			styleBookEntry.setNew(false);
		}

		styleBookEntry.resetOriginalValues();

		return styleBookEntry;
	}

	/**
	 * Returns the style book entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the style book entry
	 * @return the style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		StyleBookEntry styleBookEntry = fetchByPrimaryKey(primaryKey);

		if (styleBookEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return styleBookEntry;
	}

	/**
	 * Returns the style book entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry
	 * @throws NoSuchEntryException if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry findByPrimaryKey(long styleBookEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)styleBookEntryId);
	}

	/**
	 * Returns the style book entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the style book entry
	 * @return the style book entry, or <code>null</code> if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry fetchByPrimaryKey(Serializable primaryKey) {
		if (ctPersistenceHelper.isProductionMode(StyleBookEntry.class)) {
			return super.fetchByPrimaryKey(primaryKey);
		}

		StyleBookEntry styleBookEntry = null;

		Session session = null;

		try {
			session = openSession();

			styleBookEntry = (StyleBookEntry)session.get(
				StyleBookEntryImpl.class, primaryKey);

			if (styleBookEntry != null) {
				cacheResult(styleBookEntry);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return styleBookEntry;
	}

	/**
	 * Returns the style book entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param styleBookEntryId the primary key of the style book entry
	 * @return the style book entry, or <code>null</code> if a style book entry with the primary key could not be found
	 */
	@Override
	public StyleBookEntry fetchByPrimaryKey(long styleBookEntryId) {
		return fetchByPrimaryKey((Serializable)styleBookEntryId);
	}

	@Override
	public Map<Serializable, StyleBookEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(StyleBookEntry.class)) {
			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, StyleBookEntry> map =
			new HashMap<Serializable, StyleBookEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			StyleBookEntry styleBookEntry = fetchByPrimaryKey(primaryKey);

			if (styleBookEntry != null) {
				map.put(primaryKey, styleBookEntry);
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

			for (StyleBookEntry styleBookEntry :
					(List<StyleBookEntry>)query.list()) {

				map.put(styleBookEntry.getPrimaryKeyObj(), styleBookEntry);

				cacheResult(styleBookEntry);
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
	 * Returns all the style book entries.
	 *
	 * @return the style book entries
	 */
	@Override
	public List<StyleBookEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the style book entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @return the range of style book entries
	 */
	@Override
	public List<StyleBookEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the style book entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of style book entries
	 */
	@Override
	public List<StyleBookEntry> findAll(
		int start, int end,
		OrderByComparator<StyleBookEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the style book entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>StyleBookEntryModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of style book entries
	 * @param end the upper bound of the range of style book entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of style book entries
	 */
	@Override
	public List<StyleBookEntry> findAll(
		int start, int end, OrderByComparator<StyleBookEntry> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

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

		List<StyleBookEntry> list = null;

		if (useFinderCache && productionMode) {
			list = (List<StyleBookEntry>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_STYLEBOOKENTRY);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_STYLEBOOKENTRY;

				sql = sql.concat(StyleBookEntryModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<StyleBookEntry>)QueryUtil.list(
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
	 * Removes all the style book entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (StyleBookEntry styleBookEntry : findAll()) {
			remove(styleBookEntry);
		}
	}

	/**
	 * Returns the number of style book entries.
	 *
	 * @return the number of style book entries
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			StyleBookEntry.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_STYLEBOOKENTRY);

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
		return "styleBookEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_STYLEBOOKENTRY;
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
		return StyleBookEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "StyleBookEntry";
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
		ctStrictColumnNames.add("headId");
		ctStrictColumnNames.add("groupId");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("defaultStyleBookEntry");
		ctStrictColumnNames.add("frontendTokensValues");
		ctStrictColumnNames.add("name");
		ctStrictColumnNames.add("previewFileEntryId");
		ctStrictColumnNames.add("styleBookEntryKey");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("styleBookEntryId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(new String[] {"uuid_", "groupId", "head"});

		_uniqueIndexColumnNames.add(
			new String[] {"groupId", "styleBookEntryKey", "head"});

		_uniqueIndexColumnNames.add(new String[] {"headId"});
	}

	/**
	 * Initializes the style book entry persistence.
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

		_finderPathWithPaginationFindByUuid_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_Head",
			new String[] {
				String.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "head"}, true);

		_finderPathWithoutPaginationFindByUuid_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_Head",
			new String[] {String.class.getName(), Boolean.class.getName()},
			new String[] {"uuid_", "head"}, true);

		_finderPathCountByUuid_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_Head",
			new String[] {String.class.getName(), Boolean.class.getName()},
			new String[] {"uuid_", "head"}, false);

		_finderPathWithPaginationFindByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUUID_G",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathWithoutPaginationFindByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, true);

		_finderPathCountByUUID_G = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "groupId"}, false);

		_finderPathFetchByUUID_G_Head = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByUUID_G_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"uuid_", "groupId", "head"}, true);

		_finderPathCountByUUID_G_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"uuid_", "groupId", "head"}, false);

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

		_finderPathWithPaginationFindByUuid_C_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId", "head"}, true);

		_finderPathWithoutPaginationFindByUuid_C_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"uuid_", "companyId", "head"}, true);

		_finderPathCountByUuid_C_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C_Head",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"uuid_", "companyId", "head"}, false);

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId"}, true);

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			true);

		_finderPathCountByGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()}, new String[] {"groupId"},
			false);

		_finderPathWithPaginationFindByGroupId_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId_Head",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "head"}, true);

		_finderPathWithoutPaginationFindByGroupId_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId_Head",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "head"}, true);

		_finderPathCountByGroupId_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId_Head",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "head"}, false);

		_finderPathWithPaginationFindByG_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_D",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "defaultStyleBookEntry"}, true);

		_finderPathWithoutPaginationFindByG_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_D",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "defaultStyleBookEntry"}, true);

		_finderPathCountByG_D = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_D",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"groupId", "defaultStyleBookEntry"}, false);

		_finderPathWithPaginationFindByG_D_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_D_Head",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "defaultStyleBookEntry", "head"}, true);

		_finderPathWithoutPaginationFindByG_D_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_D_Head",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "defaultStyleBookEntry", "head"}, true);

		_finderPathCountByG_D_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_D_Head",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "defaultStyleBookEntry", "head"}, false);

		_finderPathWithPaginationFindByG_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LikeN",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "name"}, true);

		_finderPathWithPaginationCountByG_LikeN = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_LikeN",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "name"}, false);

		_finderPathWithPaginationFindByG_LikeN_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_LikeN_Head",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"groupId", "name", "head"}, true);

		_finderPathWithPaginationCountByG_LikeN_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByG_LikeN_Head",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "name", "head"}, false);

		_finderPathWithPaginationFindByG_SBEK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_SBEK",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"groupId", "styleBookEntryKey"}, true);

		_finderPathWithoutPaginationFindByG_SBEK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_SBEK",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "styleBookEntryKey"}, true);

		_finderPathCountByG_SBEK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_SBEK",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"groupId", "styleBookEntryKey"}, false);

		_finderPathFetchByG_SBEK_Head = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByG_SBEK_Head",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "styleBookEntryKey", "head"}, true);

		_finderPathCountByG_SBEK_Head = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_SBEK_Head",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"groupId", "styleBookEntryKey", "head"}, false);

		_finderPathFetchByHeadId = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByHeadId",
			new String[] {Long.class.getName()}, new String[] {"headId"}, true);

		_finderPathCountByHeadId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByHeadId",
			new String[] {Long.class.getName()}, new String[] {"headId"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(StyleBookEntryImpl.class.getName());
	}

	@Override
	@Reference(
		target = StyleBookPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = StyleBookPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = StyleBookPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_STYLEBOOKENTRY =
		"SELECT styleBookEntry FROM StyleBookEntry styleBookEntry";

	private static final String _SQL_SELECT_STYLEBOOKENTRY_WHERE =
		"SELECT styleBookEntry FROM StyleBookEntry styleBookEntry WHERE ";

	private static final String _SQL_COUNT_STYLEBOOKENTRY =
		"SELECT COUNT(styleBookEntry) FROM StyleBookEntry styleBookEntry";

	private static final String _SQL_COUNT_STYLEBOOKENTRY_WHERE =
		"SELECT COUNT(styleBookEntry) FROM StyleBookEntry styleBookEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "styleBookEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No StyleBookEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No StyleBookEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		StyleBookEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private StyleBookEntryModelArgumentsResolver
		_styleBookEntryModelArgumentsResolver;

}