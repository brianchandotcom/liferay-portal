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

package com.liferay.data.engine.service.persistence.impl;

import com.liferay.data.engine.exception.NoSuchDataDefinitionFieldLinkException;
import com.liferay.data.engine.model.DEDataDefinitionFieldLink;
import com.liferay.data.engine.model.impl.DEDataDefinitionFieldLinkImpl;
import com.liferay.data.engine.model.impl.DEDataDefinitionFieldLinkModelImpl;
import com.liferay.data.engine.service.persistence.DEDataDefinitionFieldLinkPersistence;
import com.liferay.data.engine.service.persistence.impl.constants.DEPersistenceConstants;
import com.liferay.petra.string.StringBundler;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.HashMap;
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
 * The persistence implementation for the de data definition field link service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DEDataDefinitionFieldLinkPersistence.class)
public class DEDataDefinitionFieldLinkPersistenceImpl
	extends BasePersistenceImpl<DEDataDefinitionFieldLink>
	implements DEDataDefinitionFieldLinkPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DEDataDefinitionFieldLinkUtil</code> to access the de data definition field link persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DEDataDefinitionFieldLinkImpl.class.getName();

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
	 * Returns all the de data definition field links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de data definition field links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @return the range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<DEDataDefinitionFieldLink> list = null;

		if (useFinderCache) {
			list = (List<DEDataDefinitionFieldLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
						list) {

					if (!uuid.equals(deDataDefinitionFieldLink.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DEDATADEFINITIONFIELDLINK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DEDataDefinitionFieldLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				list = (List<DEDataDefinitionFieldLink>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first de data definition field link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByUuid_First(
			String uuid,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink = fetchByUuid_First(
			uuid, orderByComparator);

		if (deDataDefinitionFieldLink != null) {
			return deDataDefinitionFieldLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchDataDefinitionFieldLinkException(msg.toString());
	}

	/**
	 * Returns the first de data definition field link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByUuid_First(
		String uuid,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		List<DEDataDefinitionFieldLink> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last de data definition field link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByUuid_Last(
			String uuid,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink = fetchByUuid_Last(
			uuid, orderByComparator);

		if (deDataDefinitionFieldLink != null) {
			return deDataDefinitionFieldLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchDataDefinitionFieldLinkException(msg.toString());
	}

	/**
	 * Returns the last de data definition field link in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByUuid_Last(
		String uuid,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<DEDataDefinitionFieldLink> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the de data definition field links before and after the current de data definition field link in the ordered set where uuid = &#63;.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the current de data definition field link
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink[] findByUuid_PrevAndNext(
			long deDataDefinitionFieldLinkId, String uuid,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		uuid = Objects.toString(uuid, "");

		DEDataDefinitionFieldLink deDataDefinitionFieldLink = findByPrimaryKey(
			deDataDefinitionFieldLinkId);

		Session session = null;

		try {
			session = openSession();

			DEDataDefinitionFieldLink[] array =
				new DEDataDefinitionFieldLinkImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, deDataDefinitionFieldLink, uuid, orderByComparator,
				true);

			array[1] = deDataDefinitionFieldLink;

			array[2] = getByUuid_PrevAndNext(
				session, deDataDefinitionFieldLink, uuid, orderByComparator,
				false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DEDataDefinitionFieldLink getByUuid_PrevAndNext(
		Session session, DEDataDefinitionFieldLink deDataDefinitionFieldLink,
		String uuid,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DEDATADEFINITIONFIELDLINK_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DEDataDefinitionFieldLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						deDataDefinitionFieldLink)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DEDataDefinitionFieldLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the de data definition field links where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(deDataDefinitionFieldLink);
		}
	}

	/**
	 * Returns the number of de data definition field links where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching de data definition field links
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DEDATADEFINITIONFIELDLINK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"deDataDefinitionFieldLink.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(deDataDefinitionFieldLink.uuid IS NULL OR deDataDefinitionFieldLink.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the de data definition field link where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchDataDefinitionFieldLinkException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByUUID_G(String uuid, long groupId)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink = fetchByUUID_G(
			uuid, groupId);

		if (deDataDefinitionFieldLink == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDataDefinitionFieldLinkException(msg.toString());
		}

		return deDataDefinitionFieldLink;
	}

	/**
	 * Returns the de data definition field link where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the de data definition field link where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByUUID_G(
		String uuid, long groupId, boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {uuid, groupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByUUID_G, finderArgs, this);
		}

		if (result instanceof DEDataDefinitionFieldLink) {
			DEDataDefinitionFieldLink deDataDefinitionFieldLink =
				(DEDataDefinitionFieldLink)result;

			if (!Objects.equals(uuid, deDataDefinitionFieldLink.getUuid()) ||
				(groupId != deDataDefinitionFieldLink.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DEDATADEFINITIONFIELDLINK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				List<DEDataDefinitionFieldLink> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					DEDataDefinitionFieldLink deDataDefinitionFieldLink =
						list.get(0);

					result = deDataDefinitionFieldLink;

					cacheResult(deDataDefinitionFieldLink);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByUUID_G, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (DEDataDefinitionFieldLink)result;
		}
	}

	/**
	 * Removes the de data definition field link where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the de data definition field link that was removed
	 */
	@Override
	public DEDataDefinitionFieldLink removeByUUID_G(String uuid, long groupId)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink = findByUUID_G(
			uuid, groupId);

		return remove(deDataDefinitionFieldLink);
	}

	/**
	 * Returns the number of de data definition field links where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching de data definition field links
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DEDATADEFINITIONFIELDLINK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(groupId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_G_UUID_2 =
		"deDataDefinitionFieldLink.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(deDataDefinitionFieldLink.uuid IS NULL OR deDataDefinitionFieldLink.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"deDataDefinitionFieldLink.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the de data definition field links where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de data definition field links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @return the range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<DEDataDefinitionFieldLink> list = null;

		if (useFinderCache) {
			list = (List<DEDataDefinitionFieldLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
						list) {

					if (!uuid.equals(deDataDefinitionFieldLink.getUuid()) ||
						(companyId !=
							deDataDefinitionFieldLink.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DEDATADEFINITIONFIELDLINK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DEDataDefinitionFieldLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				list = (List<DEDataDefinitionFieldLink>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first de data definition field link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (deDataDefinitionFieldLink != null) {
			return deDataDefinitionFieldLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchDataDefinitionFieldLinkException(msg.toString());
	}

	/**
	 * Returns the first de data definition field link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		List<DEDataDefinitionFieldLink> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last de data definition field link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (deDataDefinitionFieldLink != null) {
			return deDataDefinitionFieldLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchDataDefinitionFieldLinkException(msg.toString());
	}

	/**
	 * Returns the last de data definition field link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<DEDataDefinitionFieldLink> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the de data definition field links before and after the current de data definition field link in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the current de data definition field link
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink[] findByUuid_C_PrevAndNext(
			long deDataDefinitionFieldLinkId, String uuid, long companyId,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		uuid = Objects.toString(uuid, "");

		DEDataDefinitionFieldLink deDataDefinitionFieldLink = findByPrimaryKey(
			deDataDefinitionFieldLinkId);

		Session session = null;

		try {
			session = openSession();

			DEDataDefinitionFieldLink[] array =
				new DEDataDefinitionFieldLinkImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, deDataDefinitionFieldLink, uuid, companyId,
				orderByComparator, true);

			array[1] = deDataDefinitionFieldLink;

			array[2] = getByUuid_C_PrevAndNext(
				session, deDataDefinitionFieldLink, uuid, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DEDataDefinitionFieldLink getByUuid_C_PrevAndNext(
		Session session, DEDataDefinitionFieldLink deDataDefinitionFieldLink,
		String uuid, long companyId,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DEDATADEFINITIONFIELDLINK_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DEDataDefinitionFieldLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						deDataDefinitionFieldLink)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DEDataDefinitionFieldLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the de data definition field links where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(deDataDefinitionFieldLink);
		}
	}

	/**
	 * Returns the number of de data definition field links where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching de data definition field links
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DEDATADEFINITIONFIELDLINK_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(companyId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"deDataDefinitionFieldLink.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(deDataDefinitionFieldLink.uuid IS NULL OR deDataDefinitionFieldLink.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"deDataDefinitionFieldLink.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByDDMStructureId;
	private FinderPath _finderPathWithoutPaginationFindByDDMStructureId;
	private FinderPath _finderPathCountByDDMStructureId;

	/**
	 * Returns all the de data definition field links where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @return the matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByDDMStructureId(
		long ddmStructureId) {

		return findByDDMStructureId(
			ddmStructureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de data definition field links where ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @return the range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByDDMStructureId(
		long ddmStructureId, int start, int end) {

		return findByDDMStructureId(ddmStructureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByDDMStructureId(
		long ddmStructureId, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return findByDDMStructureId(
			ddmStructureId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where ddmStructureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByDDMStructureId(
		long ddmStructureId, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByDDMStructureId;
				finderArgs = new Object[] {ddmStructureId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByDDMStructureId;
			finderArgs = new Object[] {
				ddmStructureId, start, end, orderByComparator
			};
		}

		List<DEDataDefinitionFieldLink> list = null;

		if (useFinderCache) {
			list = (List<DEDataDefinitionFieldLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
						list) {

					if (ddmStructureId !=
							deDataDefinitionFieldLink.getDdmStructureId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DEDATADEFINITIONFIELDLINK_WHERE);

			query.append(_FINDER_COLUMN_DDMSTRUCTUREID_DDMSTRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DEDataDefinitionFieldLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ddmStructureId);

				list = (List<DEDataDefinitionFieldLink>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first de data definition field link in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByDDMStructureId_First(
			long ddmStructureId,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink =
			fetchByDDMStructureId_First(ddmStructureId, orderByComparator);

		if (deDataDefinitionFieldLink != null) {
			return deDataDefinitionFieldLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ddmStructureId=");
		msg.append(ddmStructureId);

		msg.append("}");

		throw new NoSuchDataDefinitionFieldLinkException(msg.toString());
	}

	/**
	 * Returns the first de data definition field link in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByDDMStructureId_First(
		long ddmStructureId,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		List<DEDataDefinitionFieldLink> list = findByDDMStructureId(
			ddmStructureId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last de data definition field link in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByDDMStructureId_Last(
			long ddmStructureId,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink =
			fetchByDDMStructureId_Last(ddmStructureId, orderByComparator);

		if (deDataDefinitionFieldLink != null) {
			return deDataDefinitionFieldLink;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("ddmStructureId=");
		msg.append(ddmStructureId);

		msg.append("}");

		throw new NoSuchDataDefinitionFieldLinkException(msg.toString());
	}

	/**
	 * Returns the last de data definition field link in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByDDMStructureId_Last(
		long ddmStructureId,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		int count = countByDDMStructureId(ddmStructureId);

		if (count == 0) {
			return null;
		}

		List<DEDataDefinitionFieldLink> list = findByDDMStructureId(
			ddmStructureId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the de data definition field links before and after the current de data definition field link in the ordered set where ddmStructureId = &#63;.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the current de data definition field link
	 * @param ddmStructureId the ddm structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink[] findByDDMStructureId_PrevAndNext(
			long deDataDefinitionFieldLinkId, long ddmStructureId,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink = findByPrimaryKey(
			deDataDefinitionFieldLinkId);

		Session session = null;

		try {
			session = openSession();

			DEDataDefinitionFieldLink[] array =
				new DEDataDefinitionFieldLinkImpl[3];

			array[0] = getByDDMStructureId_PrevAndNext(
				session, deDataDefinitionFieldLink, ddmStructureId,
				orderByComparator, true);

			array[1] = deDataDefinitionFieldLink;

			array[2] = getByDDMStructureId_PrevAndNext(
				session, deDataDefinitionFieldLink, ddmStructureId,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DEDataDefinitionFieldLink getByDDMStructureId_PrevAndNext(
		Session session, DEDataDefinitionFieldLink deDataDefinitionFieldLink,
		long ddmStructureId,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DEDATADEFINITIONFIELDLINK_WHERE);

		query.append(_FINDER_COLUMN_DDMSTRUCTUREID_DDMSTRUCTUREID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DEDataDefinitionFieldLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(ddmStructureId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						deDataDefinitionFieldLink)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DEDataDefinitionFieldLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the de data definition field links where ddmStructureId = &#63; from the database.
	 *
	 * @param ddmStructureId the ddm structure ID
	 */
	@Override
	public void removeByDDMStructureId(long ddmStructureId) {
		for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
				findByDDMStructureId(
					ddmStructureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(deDataDefinitionFieldLink);
		}
	}

	/**
	 * Returns the number of de data definition field links where ddmStructureId = &#63;.
	 *
	 * @param ddmStructureId the ddm structure ID
	 * @return the number of matching de data definition field links
	 */
	@Override
	public int countByDDMStructureId(long ddmStructureId) {
		FinderPath finderPath = _finderPathCountByDDMStructureId;

		Object[] finderArgs = new Object[] {ddmStructureId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DEDATADEFINITIONFIELDLINK_WHERE);

			query.append(_FINDER_COLUMN_DDMSTRUCTUREID_DDMSTRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(ddmStructureId);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_DDMSTRUCTUREID_DDMSTRUCTUREID_2 =
		"deDataDefinitionFieldLink.ddmStructureId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the de data definition field links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByC_C(
		long classNameId, long classPK) {

		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de data definition field links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @return the range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<DEDataDefinitionFieldLink> list = null;

		if (useFinderCache) {
			list = (List<DEDataDefinitionFieldLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
						list) {

					if ((classNameId !=
							deDataDefinitionFieldLink.getClassNameId()) ||
						(classPK != deDataDefinitionFieldLink.getClassPK())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_DEDATADEFINITIONFIELDLINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DEDataDefinitionFieldLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				list = (List<DEDataDefinitionFieldLink>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first de data definition field link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (deDataDefinitionFieldLink != null) {
			return deDataDefinitionFieldLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchDataDefinitionFieldLinkException(msg.toString());
	}

	/**
	 * Returns the first de data definition field link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		List<DEDataDefinitionFieldLink> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last de data definition field link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (deDataDefinitionFieldLink != null) {
			return deDataDefinitionFieldLink;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchDataDefinitionFieldLinkException(msg.toString());
	}

	/**
	 * Returns the last de data definition field link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<DEDataDefinitionFieldLink> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the de data definition field links before and after the current de data definition field link in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the current de data definition field link
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink[] findByC_C_PrevAndNext(
			long deDataDefinitionFieldLinkId, long classNameId, long classPK,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink = findByPrimaryKey(
			deDataDefinitionFieldLinkId);

		Session session = null;

		try {
			session = openSession();

			DEDataDefinitionFieldLink[] array =
				new DEDataDefinitionFieldLinkImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, deDataDefinitionFieldLink, classNameId, classPK,
				orderByComparator, true);

			array[1] = deDataDefinitionFieldLink;

			array[2] = getByC_C_PrevAndNext(
				session, deDataDefinitionFieldLink, classNameId, classPK,
				orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DEDataDefinitionFieldLink getByC_C_PrevAndNext(
		Session session, DEDataDefinitionFieldLink deDataDefinitionFieldLink,
		long classNameId, long classPK,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_DEDATADEFINITIONFIELDLINK_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DEDataDefinitionFieldLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						deDataDefinitionFieldLink)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DEDataDefinitionFieldLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the de data definition field links where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(deDataDefinitionFieldLink);
		}
	}

	/**
	 * Returns the number of de data definition field links where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching de data definition field links
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DEDATADEFINITIONFIELDLINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"deDataDefinitionFieldLink.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"deDataDefinitionFieldLink.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByC_DDMSI_F;
	private FinderPath _finderPathWithoutPaginationFindByC_DDMSI_F;
	private FinderPath _finderPathCountByC_DDMSI_F;

	/**
	 * Returns all the de data definition field links where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @return the matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByC_DDMSI_F(
		long classNameId, long ddmStructureId, String fieldName) {

		return findByC_DDMSI_F(
			classNameId, ddmStructureId, fieldName, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de data definition field links where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @return the range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByC_DDMSI_F(
		long classNameId, long ddmStructureId, String fieldName, int start,
		int end) {

		return findByC_DDMSI_F(
			classNameId, ddmStructureId, fieldName, start, end, null);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByC_DDMSI_F(
		long classNameId, long ddmStructureId, String fieldName, int start,
		int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return findByC_DDMSI_F(
			classNameId, ddmStructureId, fieldName, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de data definition field links where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findByC_DDMSI_F(
		long classNameId, long ddmStructureId, String fieldName, int start,
		int end, OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean useFinderCache) {

		fieldName = Objects.toString(fieldName, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_DDMSI_F;
				finderArgs = new Object[] {
					classNameId, ddmStructureId, fieldName
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_DDMSI_F;
			finderArgs = new Object[] {
				classNameId, ddmStructureId, fieldName, start, end,
				orderByComparator
			};
		}

		List<DEDataDefinitionFieldLink> list = null;

		if (useFinderCache) {
			list = (List<DEDataDefinitionFieldLink>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
						list) {

					if ((classNameId !=
							deDataDefinitionFieldLink.getClassNameId()) ||
						(ddmStructureId !=
							deDataDefinitionFieldLink.getDdmStructureId()) ||
						!fieldName.equals(
							deDataDefinitionFieldLink.getFieldName())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_DEDATADEFINITIONFIELDLINK_WHERE);

			query.append(_FINDER_COLUMN_C_DDMSI_F_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_DDMSI_F_DDMSTRUCTUREID_2);

			boolean bindFieldName = false;

			if (fieldName.isEmpty()) {
				query.append(_FINDER_COLUMN_C_DDMSI_F_FIELDNAME_3);
			}
			else {
				bindFieldName = true;

				query.append(_FINDER_COLUMN_C_DDMSI_F_FIELDNAME_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DEDataDefinitionFieldLinkModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(ddmStructureId);

				if (bindFieldName) {
					qPos.add(fieldName);
				}

				list = (List<DEDataDefinitionFieldLink>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first de data definition field link in the ordered set where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByC_DDMSI_F_First(
			long classNameId, long ddmStructureId, String fieldName,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink =
			fetchByC_DDMSI_F_First(
				classNameId, ddmStructureId, fieldName, orderByComparator);

		if (deDataDefinitionFieldLink != null) {
			return deDataDefinitionFieldLink;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", ddmStructureId=");
		msg.append(ddmStructureId);

		msg.append(", fieldName=");
		msg.append(fieldName);

		msg.append("}");

		throw new NoSuchDataDefinitionFieldLinkException(msg.toString());
	}

	/**
	 * Returns the first de data definition field link in the ordered set where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByC_DDMSI_F_First(
		long classNameId, long ddmStructureId, String fieldName,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		List<DEDataDefinitionFieldLink> list = findByC_DDMSI_F(
			classNameId, ddmStructureId, fieldName, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last de data definition field link in the ordered set where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByC_DDMSI_F_Last(
			long classNameId, long ddmStructureId, String fieldName,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink =
			fetchByC_DDMSI_F_Last(
				classNameId, ddmStructureId, fieldName, orderByComparator);

		if (deDataDefinitionFieldLink != null) {
			return deDataDefinitionFieldLink;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", ddmStructureId=");
		msg.append(ddmStructureId);

		msg.append(", fieldName=");
		msg.append(fieldName);

		msg.append("}");

		throw new NoSuchDataDefinitionFieldLinkException(msg.toString());
	}

	/**
	 * Returns the last de data definition field link in the ordered set where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByC_DDMSI_F_Last(
		long classNameId, long ddmStructureId, String fieldName,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		int count = countByC_DDMSI_F(classNameId, ddmStructureId, fieldName);

		if (count == 0) {
			return null;
		}

		List<DEDataDefinitionFieldLink> list = findByC_DDMSI_F(
			classNameId, ddmStructureId, fieldName, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the de data definition field links before and after the current de data definition field link in the ordered set where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the current de data definition field link
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink[] findByC_DDMSI_F_PrevAndNext(
			long deDataDefinitionFieldLinkId, long classNameId,
			long ddmStructureId, String fieldName,
			OrderByComparator<DEDataDefinitionFieldLink> orderByComparator)
		throws NoSuchDataDefinitionFieldLinkException {

		fieldName = Objects.toString(fieldName, "");

		DEDataDefinitionFieldLink deDataDefinitionFieldLink = findByPrimaryKey(
			deDataDefinitionFieldLinkId);

		Session session = null;

		try {
			session = openSession();

			DEDataDefinitionFieldLink[] array =
				new DEDataDefinitionFieldLinkImpl[3];

			array[0] = getByC_DDMSI_F_PrevAndNext(
				session, deDataDefinitionFieldLink, classNameId, ddmStructureId,
				fieldName, orderByComparator, true);

			array[1] = deDataDefinitionFieldLink;

			array[2] = getByC_DDMSI_F_PrevAndNext(
				session, deDataDefinitionFieldLink, classNameId, ddmStructureId,
				fieldName, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DEDataDefinitionFieldLink getByC_DDMSI_F_PrevAndNext(
		Session session, DEDataDefinitionFieldLink deDataDefinitionFieldLink,
		long classNameId, long ddmStructureId, String fieldName,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_DEDATADEFINITIONFIELDLINK_WHERE);

		query.append(_FINDER_COLUMN_C_DDMSI_F_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_DDMSI_F_DDMSTRUCTUREID_2);

		boolean bindFieldName = false;

		if (fieldName.isEmpty()) {
			query.append(_FINDER_COLUMN_C_DDMSI_F_FIELDNAME_3);
		}
		else {
			bindFieldName = true;

			query.append(_FINDER_COLUMN_C_DDMSI_F_FIELDNAME_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				query.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						query.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(WHERE_GREATER_THAN);
					}
					else {
						query.append(WHERE_LESSER_THAN);
					}
				}
			}

			query.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				query.append(_ORDER_BY_ENTITY_ALIAS);
				query.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						query.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						query.append(ORDER_BY_ASC);
					}
					else {
						query.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			query.append(DEDataDefinitionFieldLinkModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(ddmStructureId);

		if (bindFieldName) {
			qPos.add(fieldName);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						deDataDefinitionFieldLink)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DEDataDefinitionFieldLink> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the de data definition field links where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 */
	@Override
	public void removeByC_DDMSI_F(
		long classNameId, long ddmStructureId, String fieldName) {

		for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
				findByC_DDMSI_F(
					classNameId, ddmStructureId, fieldName, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(deDataDefinitionFieldLink);
		}
	}

	/**
	 * Returns the number of de data definition field links where classNameId = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @return the number of matching de data definition field links
	 */
	@Override
	public int countByC_DDMSI_F(
		long classNameId, long ddmStructureId, String fieldName) {

		fieldName = Objects.toString(fieldName, "");

		FinderPath finderPath = _finderPathCountByC_DDMSI_F;

		Object[] finderArgs = new Object[] {
			classNameId, ddmStructureId, fieldName
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_DEDATADEFINITIONFIELDLINK_WHERE);

			query.append(_FINDER_COLUMN_C_DDMSI_F_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_DDMSI_F_DDMSTRUCTUREID_2);

			boolean bindFieldName = false;

			if (fieldName.isEmpty()) {
				query.append(_FINDER_COLUMN_C_DDMSI_F_FIELDNAME_3);
			}
			else {
				bindFieldName = true;

				query.append(_FINDER_COLUMN_C_DDMSI_F_FIELDNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(ddmStructureId);

				if (bindFieldName) {
					qPos.add(fieldName);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_DDMSI_F_CLASSNAMEID_2 =
		"deDataDefinitionFieldLink.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_DDMSI_F_DDMSTRUCTUREID_2 =
		"deDataDefinitionFieldLink.ddmStructureId = ? AND ";

	private static final String _FINDER_COLUMN_C_DDMSI_F_FIELDNAME_2 =
		"deDataDefinitionFieldLink.fieldName = ?";

	private static final String _FINDER_COLUMN_C_DDMSI_F_FIELDNAME_3 =
		"(deDataDefinitionFieldLink.fieldName IS NULL OR deDataDefinitionFieldLink.fieldName = '')";

	private FinderPath _finderPathFetchByC_C_DDMSI_F;
	private FinderPath _finderPathCountByC_C_DDMSI_F;

	/**
	 * Returns the de data definition field link where classNameId = &#63; and classPK = &#63; and ddmStructureId = &#63; and fieldName = &#63; or throws a <code>NoSuchDataDefinitionFieldLinkException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @return the matching de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByC_C_DDMSI_F(
			long classNameId, long classPK, long ddmStructureId,
			String fieldName)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink =
			fetchByC_C_DDMSI_F(classNameId, classPK, ddmStructureId, fieldName);

		if (deDataDefinitionFieldLink == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append(", ddmStructureId=");
			msg.append(ddmStructureId);

			msg.append(", fieldName=");
			msg.append(fieldName);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchDataDefinitionFieldLinkException(msg.toString());
		}

		return deDataDefinitionFieldLink;
	}

	/**
	 * Returns the de data definition field link where classNameId = &#63; and classPK = &#63; and ddmStructureId = &#63; and fieldName = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @return the matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByC_C_DDMSI_F(
		long classNameId, long classPK, long ddmStructureId, String fieldName) {

		return fetchByC_C_DDMSI_F(
			classNameId, classPK, ddmStructureId, fieldName, true);
	}

	/**
	 * Returns the de data definition field link where classNameId = &#63; and classPK = &#63; and ddmStructureId = &#63; and fieldName = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching de data definition field link, or <code>null</code> if a matching de data definition field link could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByC_C_DDMSI_F(
		long classNameId, long classPK, long ddmStructureId, String fieldName,
		boolean useFinderCache) {

		fieldName = Objects.toString(fieldName, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				classNameId, classPK, ddmStructureId, fieldName
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C_DDMSI_F, finderArgs, this);
		}

		if (result instanceof DEDataDefinitionFieldLink) {
			DEDataDefinitionFieldLink deDataDefinitionFieldLink =
				(DEDataDefinitionFieldLink)result;

			if ((classNameId != deDataDefinitionFieldLink.getClassNameId()) ||
				(classPK != deDataDefinitionFieldLink.getClassPK()) ||
				(ddmStructureId !=
					deDataDefinitionFieldLink.getDdmStructureId()) ||
				!Objects.equals(
					fieldName, deDataDefinitionFieldLink.getFieldName())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_DEDATADEFINITIONFIELDLINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_DDMSI_F_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_DDMSI_F_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_DDMSI_F_DDMSTRUCTUREID_2);

			boolean bindFieldName = false;

			if (fieldName.isEmpty()) {
				query.append(_FINDER_COLUMN_C_C_DDMSI_F_FIELDNAME_3);
			}
			else {
				bindFieldName = true;

				query.append(_FINDER_COLUMN_C_C_DDMSI_F_FIELDNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(ddmStructureId);

				if (bindFieldName) {
					qPos.add(fieldName);
				}

				List<DEDataDefinitionFieldLink> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_DDMSI_F, finderArgs, list);
					}
				}
				else {
					DEDataDefinitionFieldLink deDataDefinitionFieldLink =
						list.get(0);

					result = deDataDefinitionFieldLink;

					cacheResult(deDataDefinitionFieldLink);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByC_C_DDMSI_F, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (DEDataDefinitionFieldLink)result;
		}
	}

	/**
	 * Removes the de data definition field link where classNameId = &#63; and classPK = &#63; and ddmStructureId = &#63; and fieldName = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @return the de data definition field link that was removed
	 */
	@Override
	public DEDataDefinitionFieldLink removeByC_C_DDMSI_F(
			long classNameId, long classPK, long ddmStructureId,
			String fieldName)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink = findByC_C_DDMSI_F(
			classNameId, classPK, ddmStructureId, fieldName);

		return remove(deDataDefinitionFieldLink);
	}

	/**
	 * Returns the number of de data definition field links where classNameId = &#63; and classPK = &#63; and ddmStructureId = &#63; and fieldName = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param ddmStructureId the ddm structure ID
	 * @param fieldName the field name
	 * @return the number of matching de data definition field links
	 */
	@Override
	public int countByC_C_DDMSI_F(
		long classNameId, long classPK, long ddmStructureId, String fieldName) {

		fieldName = Objects.toString(fieldName, "");

		FinderPath finderPath = _finderPathCountByC_C_DDMSI_F;

		Object[] finderArgs = new Object[] {
			classNameId, classPK, ddmStructureId, fieldName
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_DEDATADEFINITIONFIELDLINK_WHERE);

			query.append(_FINDER_COLUMN_C_C_DDMSI_F_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_DDMSI_F_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_DDMSI_F_DDMSTRUCTUREID_2);

			boolean bindFieldName = false;

			if (fieldName.isEmpty()) {
				query.append(_FINDER_COLUMN_C_C_DDMSI_F_FIELDNAME_3);
			}
			else {
				bindFieldName = true;

				query.append(_FINDER_COLUMN_C_C_DDMSI_F_FIELDNAME_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(ddmStructureId);

				if (bindFieldName) {
					qPos.add(fieldName);
				}

				count = (Long)q.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_C_C_DDMSI_F_CLASSNAMEID_2 =
		"deDataDefinitionFieldLink.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_DDMSI_F_CLASSPK_2 =
		"deDataDefinitionFieldLink.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_DDMSI_F_DDMSTRUCTUREID_2 =
		"deDataDefinitionFieldLink.ddmStructureId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_DDMSI_F_FIELDNAME_2 =
		"deDataDefinitionFieldLink.fieldName = ?";

	private static final String _FINDER_COLUMN_C_C_DDMSI_F_FIELDNAME_3 =
		"(deDataDefinitionFieldLink.fieldName IS NULL OR deDataDefinitionFieldLink.fieldName = '')";

	public DEDataDefinitionFieldLinkPersistenceImpl() {
		setModelClass(DEDataDefinitionFieldLink.class);

		setModelImplClass(DEDataDefinitionFieldLinkImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the de data definition field link in the entity cache if it is enabled.
	 *
	 * @param deDataDefinitionFieldLink the de data definition field link
	 */
	@Override
	public void cacheResult(
		DEDataDefinitionFieldLink deDataDefinitionFieldLink) {

		entityCache.putResult(
			entityCacheEnabled, DEDataDefinitionFieldLinkImpl.class,
			deDataDefinitionFieldLink.getPrimaryKey(),
			deDataDefinitionFieldLink);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				deDataDefinitionFieldLink.getUuid(),
				deDataDefinitionFieldLink.getGroupId()
			},
			deDataDefinitionFieldLink);

		finderCache.putResult(
			_finderPathFetchByC_C_DDMSI_F,
			new Object[] {
				deDataDefinitionFieldLink.getClassNameId(),
				deDataDefinitionFieldLink.getClassPK(),
				deDataDefinitionFieldLink.getDdmStructureId(),
				deDataDefinitionFieldLink.getFieldName()
			},
			deDataDefinitionFieldLink);

		deDataDefinitionFieldLink.resetOriginalValues();
	}

	/**
	 * Caches the de data definition field links in the entity cache if it is enabled.
	 *
	 * @param deDataDefinitionFieldLinks the de data definition field links
	 */
	@Override
	public void cacheResult(
		List<DEDataDefinitionFieldLink> deDataDefinitionFieldLinks) {

		for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
				deDataDefinitionFieldLinks) {

			if (entityCache.getResult(
					entityCacheEnabled, DEDataDefinitionFieldLinkImpl.class,
					deDataDefinitionFieldLink.getPrimaryKey()) == null) {

				cacheResult(deDataDefinitionFieldLink);
			}
			else {
				deDataDefinitionFieldLink.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all de data definition field links.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DEDataDefinitionFieldLinkImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the de data definition field link.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		DEDataDefinitionFieldLink deDataDefinitionFieldLink) {

		entityCache.removeResult(
			entityCacheEnabled, DEDataDefinitionFieldLinkImpl.class,
			deDataDefinitionFieldLink.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DEDataDefinitionFieldLinkModelImpl)deDataDefinitionFieldLink,
			true);
	}

	@Override
	public void clearCache(
		List<DEDataDefinitionFieldLink> deDataDefinitionFieldLinks) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DEDataDefinitionFieldLink deDataDefinitionFieldLink :
				deDataDefinitionFieldLinks) {

			entityCache.removeResult(
				entityCacheEnabled, DEDataDefinitionFieldLinkImpl.class,
				deDataDefinitionFieldLink.getPrimaryKey());

			clearUniqueFindersCache(
				(DEDataDefinitionFieldLinkModelImpl)deDataDefinitionFieldLink,
				true);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				entityCacheEnabled, DEDataDefinitionFieldLinkImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		DEDataDefinitionFieldLinkModelImpl deDataDefinitionFieldLinkModelImpl) {

		Object[] args = new Object[] {
			deDataDefinitionFieldLinkModelImpl.getUuid(),
			deDataDefinitionFieldLinkModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, deDataDefinitionFieldLinkModelImpl,
			false);

		args = new Object[] {
			deDataDefinitionFieldLinkModelImpl.getClassNameId(),
			deDataDefinitionFieldLinkModelImpl.getClassPK(),
			deDataDefinitionFieldLinkModelImpl.getDdmStructureId(),
			deDataDefinitionFieldLinkModelImpl.getFieldName()
		};

		finderCache.putResult(
			_finderPathCountByC_C_DDMSI_F, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C_DDMSI_F, args,
			deDataDefinitionFieldLinkModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DEDataDefinitionFieldLinkModelImpl deDataDefinitionFieldLinkModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				deDataDefinitionFieldLinkModelImpl.getUuid(),
				deDataDefinitionFieldLinkModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((deDataDefinitionFieldLinkModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				deDataDefinitionFieldLinkModelImpl.getOriginalUuid(),
				deDataDefinitionFieldLinkModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				deDataDefinitionFieldLinkModelImpl.getClassNameId(),
				deDataDefinitionFieldLinkModelImpl.getClassPK(),
				deDataDefinitionFieldLinkModelImpl.getDdmStructureId(),
				deDataDefinitionFieldLinkModelImpl.getFieldName()
			};

			finderCache.removeResult(_finderPathCountByC_C_DDMSI_F, args);
			finderCache.removeResult(_finderPathFetchByC_C_DDMSI_F, args);
		}

		if ((deDataDefinitionFieldLinkModelImpl.getColumnBitmask() &
			 _finderPathFetchByC_C_DDMSI_F.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				deDataDefinitionFieldLinkModelImpl.getOriginalClassNameId(),
				deDataDefinitionFieldLinkModelImpl.getOriginalClassPK(),
				deDataDefinitionFieldLinkModelImpl.getOriginalDdmStructureId(),
				deDataDefinitionFieldLinkModelImpl.getOriginalFieldName()
			};

			finderCache.removeResult(_finderPathCountByC_C_DDMSI_F, args);
			finderCache.removeResult(_finderPathFetchByC_C_DDMSI_F, args);
		}
	}

	/**
	 * Creates a new de data definition field link with the primary key. Does not add the de data definition field link to the database.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key for the new de data definition field link
	 * @return the new de data definition field link
	 */
	@Override
	public DEDataDefinitionFieldLink create(long deDataDefinitionFieldLinkId) {
		DEDataDefinitionFieldLink deDataDefinitionFieldLink =
			new DEDataDefinitionFieldLinkImpl();

		deDataDefinitionFieldLink.setNew(true);
		deDataDefinitionFieldLink.setPrimaryKey(deDataDefinitionFieldLinkId);

		String uuid = PortalUUIDUtil.generate();

		deDataDefinitionFieldLink.setUuid(uuid);

		deDataDefinitionFieldLink.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return deDataDefinitionFieldLink;
	}

	/**
	 * Removes the de data definition field link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the de data definition field link
	 * @return the de data definition field link that was removed
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink remove(long deDataDefinitionFieldLinkId)
		throws NoSuchDataDefinitionFieldLinkException {

		return remove((Serializable)deDataDefinitionFieldLinkId);
	}

	/**
	 * Removes the de data definition field link with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the de data definition field link
	 * @return the de data definition field link that was removed
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink remove(Serializable primaryKey)
		throws NoSuchDataDefinitionFieldLinkException {

		Session session = null;

		try {
			session = openSession();

			DEDataDefinitionFieldLink deDataDefinitionFieldLink =
				(DEDataDefinitionFieldLink)session.get(
					DEDataDefinitionFieldLinkImpl.class, primaryKey);

			if (deDataDefinitionFieldLink == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDataDefinitionFieldLinkException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(deDataDefinitionFieldLink);
		}
		catch (NoSuchDataDefinitionFieldLinkException nsee) {
			throw nsee;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected DEDataDefinitionFieldLink removeImpl(
		DEDataDefinitionFieldLink deDataDefinitionFieldLink) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(deDataDefinitionFieldLink)) {
				deDataDefinitionFieldLink =
					(DEDataDefinitionFieldLink)session.get(
						DEDataDefinitionFieldLinkImpl.class,
						deDataDefinitionFieldLink.getPrimaryKeyObj());
			}

			if (deDataDefinitionFieldLink != null) {
				session.delete(deDataDefinitionFieldLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (deDataDefinitionFieldLink != null) {
			clearCache(deDataDefinitionFieldLink);
		}

		return deDataDefinitionFieldLink;
	}

	@Override
	public DEDataDefinitionFieldLink updateImpl(
		DEDataDefinitionFieldLink deDataDefinitionFieldLink) {

		boolean isNew = deDataDefinitionFieldLink.isNew();

		if (!(deDataDefinitionFieldLink instanceof
				DEDataDefinitionFieldLinkModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(deDataDefinitionFieldLink.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					deDataDefinitionFieldLink);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in deDataDefinitionFieldLink proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DEDataDefinitionFieldLink implementation " +
					deDataDefinitionFieldLink.getClass());
		}

		DEDataDefinitionFieldLinkModelImpl deDataDefinitionFieldLinkModelImpl =
			(DEDataDefinitionFieldLinkModelImpl)deDataDefinitionFieldLink;

		if (Validator.isNull(deDataDefinitionFieldLink.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			deDataDefinitionFieldLink.setUuid(uuid);
		}

		Session session = null;

		try {
			session = openSession();

			if (deDataDefinitionFieldLink.isNew()) {
				session.save(deDataDefinitionFieldLink);

				deDataDefinitionFieldLink.setNew(false);
			}
			else {
				deDataDefinitionFieldLink =
					(DEDataDefinitionFieldLink)session.merge(
						deDataDefinitionFieldLink);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!_columnBitmaskEnabled) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				deDataDefinitionFieldLinkModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				deDataDefinitionFieldLinkModelImpl.getUuid(),
				deDataDefinitionFieldLinkModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				deDataDefinitionFieldLinkModelImpl.getDdmStructureId()
			};

			finderCache.removeResult(_finderPathCountByDDMStructureId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByDDMStructureId, args);

			args = new Object[] {
				deDataDefinitionFieldLinkModelImpl.getClassNameId(),
				deDataDefinitionFieldLinkModelImpl.getClassPK()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			args = new Object[] {
				deDataDefinitionFieldLinkModelImpl.getClassNameId(),
				deDataDefinitionFieldLinkModelImpl.getDdmStructureId(),
				deDataDefinitionFieldLinkModelImpl.getFieldName()
			};

			finderCache.removeResult(_finderPathCountByC_DDMSI_F, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByC_DDMSI_F, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((deDataDefinitionFieldLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					deDataDefinitionFieldLinkModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {
					deDataDefinitionFieldLinkModelImpl.getUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((deDataDefinitionFieldLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					deDataDefinitionFieldLinkModelImpl.getOriginalUuid(),
					deDataDefinitionFieldLinkModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					deDataDefinitionFieldLinkModelImpl.getUuid(),
					deDataDefinitionFieldLinkModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((deDataDefinitionFieldLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByDDMStructureId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					deDataDefinitionFieldLinkModelImpl.
						getOriginalDdmStructureId()
				};

				finderCache.removeResult(
					_finderPathCountByDDMStructureId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDDMStructureId, args);

				args = new Object[] {
					deDataDefinitionFieldLinkModelImpl.getDdmStructureId()
				};

				finderCache.removeResult(
					_finderPathCountByDDMStructureId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDDMStructureId, args);
			}

			if ((deDataDefinitionFieldLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					deDataDefinitionFieldLinkModelImpl.getOriginalClassNameId(),
					deDataDefinitionFieldLinkModelImpl.getOriginalClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					deDataDefinitionFieldLinkModelImpl.getClassNameId(),
					deDataDefinitionFieldLinkModelImpl.getClassPK()
				};

				finderCache.removeResult(_finderPathCountByC_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}

			if ((deDataDefinitionFieldLinkModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_DDMSI_F.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					deDataDefinitionFieldLinkModelImpl.getOriginalClassNameId(),
					deDataDefinitionFieldLinkModelImpl.
						getOriginalDdmStructureId(),
					deDataDefinitionFieldLinkModelImpl.getOriginalFieldName()
				};

				finderCache.removeResult(_finderPathCountByC_DDMSI_F, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_DDMSI_F, args);

				args = new Object[] {
					deDataDefinitionFieldLinkModelImpl.getClassNameId(),
					deDataDefinitionFieldLinkModelImpl.getDdmStructureId(),
					deDataDefinitionFieldLinkModelImpl.getFieldName()
				};

				finderCache.removeResult(_finderPathCountByC_DDMSI_F, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByC_DDMSI_F, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, DEDataDefinitionFieldLinkImpl.class,
			deDataDefinitionFieldLink.getPrimaryKey(),
			deDataDefinitionFieldLink, false);

		clearUniqueFindersCache(deDataDefinitionFieldLinkModelImpl, false);
		cacheUniqueFindersCache(deDataDefinitionFieldLinkModelImpl);

		deDataDefinitionFieldLink.resetOriginalValues();

		return deDataDefinitionFieldLink;
	}

	/**
	 * Returns the de data definition field link with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the de data definition field link
	 * @return the de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDataDefinitionFieldLinkException {

		DEDataDefinitionFieldLink deDataDefinitionFieldLink = fetchByPrimaryKey(
			primaryKey);

		if (deDataDefinitionFieldLink == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDataDefinitionFieldLinkException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return deDataDefinitionFieldLink;
	}

	/**
	 * Returns the de data definition field link with the primary key or throws a <code>NoSuchDataDefinitionFieldLinkException</code> if it could not be found.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the de data definition field link
	 * @return the de data definition field link
	 * @throws NoSuchDataDefinitionFieldLinkException if a de data definition field link with the primary key could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink findByPrimaryKey(
			long deDataDefinitionFieldLinkId)
		throws NoSuchDataDefinitionFieldLinkException {

		return findByPrimaryKey((Serializable)deDataDefinitionFieldLinkId);
	}

	/**
	 * Returns the de data definition field link with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param deDataDefinitionFieldLinkId the primary key of the de data definition field link
	 * @return the de data definition field link, or <code>null</code> if a de data definition field link with the primary key could not be found
	 */
	@Override
	public DEDataDefinitionFieldLink fetchByPrimaryKey(
		long deDataDefinitionFieldLinkId) {

		return fetchByPrimaryKey((Serializable)deDataDefinitionFieldLinkId);
	}

	/**
	 * Returns all the de data definition field links.
	 *
	 * @return the de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the de data definition field links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @return the range of de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the de data definition field links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findAll(
		int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the de data definition field links.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DEDataDefinitionFieldLinkModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of de data definition field links
	 * @param end the upper bound of the range of de data definition field links (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of de data definition field links
	 */
	@Override
	public List<DEDataDefinitionFieldLink> findAll(
		int start, int end,
		OrderByComparator<DEDataDefinitionFieldLink> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<DEDataDefinitionFieldLink> list = null;

		if (useFinderCache) {
			list = (List<DEDataDefinitionFieldLink>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DEDATADEFINITIONFIELDLINK);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DEDATADEFINITIONFIELDLINK;

				sql = sql.concat(
					DEDataDefinitionFieldLinkModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<DEDataDefinitionFieldLink>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the de data definition field links from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DEDataDefinitionFieldLink deDataDefinitionFieldLink : findAll()) {
			remove(deDataDefinitionFieldLink);
		}
	}

	/**
	 * Returns the number of de data definition field links.
	 *
	 * @return the number of de data definition field links
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(
					_SQL_COUNT_DEDATADEFINITIONFIELDLINK);

				count = (Long)q.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
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
		return "deDataDefinitionFieldLinkId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DEDATADEFINITIONFIELDLINK;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DEDataDefinitionFieldLinkModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the de data definition field link persistence.
	 */
	@Activate
	public void activate() {
		DEDataDefinitionFieldLinkModelImpl.setEntityCacheEnabled(
			entityCacheEnabled);
		DEDataDefinitionFieldLinkModelImpl.setFinderCacheEnabled(
			finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			DEDataDefinitionFieldLinkModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			DEDataDefinitionFieldLinkModelImpl.UUID_COLUMN_BITMASK |
			DEDataDefinitionFieldLinkModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			DEDataDefinitionFieldLinkModelImpl.UUID_COLUMN_BITMASK |
			DEDataDefinitionFieldLinkModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByDDMStructureId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByDDMStructureId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByDDMStructureId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByDDMStructureId",
			new String[] {Long.class.getName()},
			DEDataDefinitionFieldLinkModelImpl.DDMSTRUCTUREID_COLUMN_BITMASK);

		_finderPathCountByDDMStructureId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDDMStructureId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			DEDataDefinitionFieldLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DEDataDefinitionFieldLinkModelImpl.CLASSPK_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_DDMSI_F = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_DDMSI_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_DDMSI_F = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_DDMSI_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			},
			DEDataDefinitionFieldLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DEDataDefinitionFieldLinkModelImpl.DDMSTRUCTUREID_COLUMN_BITMASK |
			DEDataDefinitionFieldLinkModelImpl.FIELDNAME_COLUMN_BITMASK);

		_finderPathCountByC_DDMSI_F = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_DDMSI_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				String.class.getName()
			});

		_finderPathFetchByC_C_DDMSI_F = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DEDataDefinitionFieldLinkImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByC_C_DDMSI_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			},
			DEDataDefinitionFieldLinkModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			DEDataDefinitionFieldLinkModelImpl.CLASSPK_COLUMN_BITMASK |
			DEDataDefinitionFieldLinkModelImpl.DDMSTRUCTUREID_COLUMN_BITMASK |
			DEDataDefinitionFieldLinkModelImpl.FIELDNAME_COLUMN_BITMASK);

		_finderPathCountByC_C_DDMSI_F = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_DDMSI_F",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), String.class.getName()
			});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DEDataDefinitionFieldLinkImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = DEPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.data.engine.model.DEDataDefinitionFieldLink"),
			true);
	}

	@Override
	@Reference(
		target = DEPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DEPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	private boolean _columnBitmaskEnabled;

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_DEDATADEFINITIONFIELDLINK =
		"SELECT deDataDefinitionFieldLink FROM DEDataDefinitionFieldLink deDataDefinitionFieldLink";

	private static final String _SQL_SELECT_DEDATADEFINITIONFIELDLINK_WHERE =
		"SELECT deDataDefinitionFieldLink FROM DEDataDefinitionFieldLink deDataDefinitionFieldLink WHERE ";

	private static final String _SQL_COUNT_DEDATADEFINITIONFIELDLINK =
		"SELECT COUNT(deDataDefinitionFieldLink) FROM DEDataDefinitionFieldLink deDataDefinitionFieldLink";

	private static final String _SQL_COUNT_DEDATADEFINITIONFIELDLINK_WHERE =
		"SELECT COUNT(deDataDefinitionFieldLink) FROM DEDataDefinitionFieldLink deDataDefinitionFieldLink WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"deDataDefinitionFieldLink.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DEDataDefinitionFieldLink exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DEDataDefinitionFieldLink exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DEDataDefinitionFieldLinkPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(DEPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}