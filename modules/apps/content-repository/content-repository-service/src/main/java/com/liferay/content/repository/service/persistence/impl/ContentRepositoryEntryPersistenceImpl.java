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

package com.liferay.content.repository.service.persistence.impl;

import com.liferay.content.repository.exception.NoSuchEntryException;
import com.liferay.content.repository.model.ContentRepositoryEntry;
import com.liferay.content.repository.model.impl.ContentRepositoryEntryImpl;
import com.liferay.content.repository.model.impl.ContentRepositoryEntryModelImpl;
import com.liferay.content.repository.service.persistence.ContentRepositoryEntryPersistence;
import com.liferay.content.repository.service.persistence.impl.constants.ContentRepositoryPersistenceConstants;
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
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
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
 * The persistence implementation for the content repository entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = ContentRepositoryEntryPersistence.class)
public class ContentRepositoryEntryPersistenceImpl
	extends BasePersistenceImpl<ContentRepositoryEntry>
	implements ContentRepositoryEntryPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>ContentRepositoryEntryUtil</code> to access the content repository entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		ContentRepositoryEntryImpl.class.getName();

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
	 * Returns all the content repository entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching content repository entries
	 */
	@Override
	public List<ContentRepositoryEntry> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the content repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @return the range of matching content repository entries
	 */
	@Override
	public List<ContentRepositoryEntry> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the content repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching content repository entries
	 */
	@Override
	public List<ContentRepositoryEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the content repository entries where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching content repository entries
	 */
	@Override
	public List<ContentRepositoryEntry> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<ContentRepositoryEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<ContentRepositoryEntry> list = null;

		if (useFinderCache) {
			list = (List<ContentRepositoryEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ContentRepositoryEntry contentRepositoryEntry : list) {
					if (!uuid.equals(contentRepositoryEntry.getUuid())) {
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

			query.append(_SQL_SELECT_CONTENTREPOSITORYENTRY_WHERE);

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
			else if (pagination) {
				query.append(ContentRepositoryEntryModelImpl.ORDER_BY_JPQL);
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

				if (!pagination) {
					list = (List<ContentRepositoryEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ContentRepositoryEntry>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Returns the first content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	@Override
	public ContentRepositoryEntry findByUuid_First(
			String uuid,
			OrderByComparator<ContentRepositoryEntry> orderByComparator)
		throws NoSuchEntryException {

		ContentRepositoryEntry contentRepositoryEntry = fetchByUuid_First(
			uuid, orderByComparator);

		if (contentRepositoryEntry != null) {
			return contentRepositoryEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	@Override
	public ContentRepositoryEntry fetchByUuid_First(
		String uuid,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		List<ContentRepositoryEntry> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	@Override
	public ContentRepositoryEntry findByUuid_Last(
			String uuid,
			OrderByComparator<ContentRepositoryEntry> orderByComparator)
		throws NoSuchEntryException {

		ContentRepositoryEntry contentRepositoryEntry = fetchByUuid_Last(
			uuid, orderByComparator);

		if (contentRepositoryEntry != null) {
			return contentRepositoryEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	@Override
	public ContentRepositoryEntry fetchByUuid_Last(
		String uuid,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<ContentRepositoryEntry> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the content repository entries before and after the current content repository entry in the ordered set where uuid = &#63;.
	 *
	 * @param contentRepositoryEntryId the primary key of the current content repository entry
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next content repository entry
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	@Override
	public ContentRepositoryEntry[] findByUuid_PrevAndNext(
			long contentRepositoryEntryId, String uuid,
			OrderByComparator<ContentRepositoryEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		ContentRepositoryEntry contentRepositoryEntry = findByPrimaryKey(
			contentRepositoryEntryId);

		Session session = null;

		try {
			session = openSession();

			ContentRepositoryEntry[] array = new ContentRepositoryEntryImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, contentRepositoryEntry, uuid, orderByComparator, true);

			array[1] = contentRepositoryEntry;

			array[2] = getByUuid_PrevAndNext(
				session, contentRepositoryEntry, uuid, orderByComparator,
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

	protected ContentRepositoryEntry getByUuid_PrevAndNext(
		Session session, ContentRepositoryEntry contentRepositoryEntry,
		String uuid,
		OrderByComparator<ContentRepositoryEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CONTENTREPOSITORYENTRY_WHERE);

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
			query.append(ContentRepositoryEntryModelImpl.ORDER_BY_JPQL);
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
						contentRepositoryEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ContentRepositoryEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the content repository entries where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (ContentRepositoryEntry contentRepositoryEntry :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(contentRepositoryEntry);
		}
	}

	/**
	 * Returns the number of content repository entries where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching content repository entries
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_CONTENTREPOSITORYENTRY_WHERE);

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
		"contentRepositoryEntry.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(contentRepositoryEntry.uuid IS NULL OR contentRepositoryEntry.uuid = '')";

	private FinderPath _finderPathFetchByUUID_G;
	private FinderPath _finderPathCountByUUID_G;

	/**
	 * Returns the content repository entry where uuid = &#63; and groupId = &#63; or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	@Override
	public ContentRepositoryEntry findByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		ContentRepositoryEntry contentRepositoryEntry = fetchByUUID_G(
			uuid, groupId);

		if (contentRepositoryEntry == null) {
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

			throw new NoSuchEntryException(msg.toString());
		}

		return contentRepositoryEntry;
	}

	/**
	 * Returns the content repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	@Override
	public ContentRepositoryEntry fetchByUUID_G(String uuid, long groupId) {
		return fetchByUUID_G(uuid, groupId, true);
	}

	/**
	 * Returns the content repository entry where uuid = &#63; and groupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	@Override
	public ContentRepositoryEntry fetchByUUID_G(
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

		if (result instanceof ContentRepositoryEntry) {
			ContentRepositoryEntry contentRepositoryEntry =
				(ContentRepositoryEntry)result;

			if (!Objects.equals(uuid, contentRepositoryEntry.getUuid()) ||
				(groupId != contentRepositoryEntry.getGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_CONTENTREPOSITORYENTRY_WHERE);

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

				List<ContentRepositoryEntry> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByUUID_G, finderArgs, list);
					}
				}
				else {
					ContentRepositoryEntry contentRepositoryEntry = list.get(0);

					result = contentRepositoryEntry;

					cacheResult(contentRepositoryEntry);
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
			return (ContentRepositoryEntry)result;
		}
	}

	/**
	 * Removes the content repository entry where uuid = &#63; and groupId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the content repository entry that was removed
	 */
	@Override
	public ContentRepositoryEntry removeByUUID_G(String uuid, long groupId)
		throws NoSuchEntryException {

		ContentRepositoryEntry contentRepositoryEntry = findByUUID_G(
			uuid, groupId);

		return remove(contentRepositoryEntry);
	}

	/**
	 * Returns the number of content repository entries where uuid = &#63; and groupId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @return the number of matching content repository entries
	 */
	@Override
	public int countByUUID_G(String uuid, long groupId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G;

		Object[] finderArgs = new Object[] {uuid, groupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CONTENTREPOSITORYENTRY_WHERE);

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
		"contentRepositoryEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_UUID_3 =
		"(contentRepositoryEntry.uuid IS NULL OR contentRepositoryEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_GROUPID_2 =
		"contentRepositoryEntry.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching content repository entries
	 */
	@Override
	public List<ContentRepositoryEntry> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @return the range of matching content repository entries
	 */
	@Override
	public List<ContentRepositoryEntry> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching content repository entries
	 */
	@Override
	public List<ContentRepositoryEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching content repository entries
	 */
	@Override
	public List<ContentRepositoryEntry> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<ContentRepositoryEntry> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

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

		List<ContentRepositoryEntry> list = null;

		if (useFinderCache) {
			list = (List<ContentRepositoryEntry>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (ContentRepositoryEntry contentRepositoryEntry : list) {
					if (!uuid.equals(contentRepositoryEntry.getUuid()) ||
						(companyId != contentRepositoryEntry.getCompanyId())) {

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

			query.append(_SQL_SELECT_CONTENTREPOSITORYENTRY_WHERE);

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
			else if (pagination) {
				query.append(ContentRepositoryEntryModelImpl.ORDER_BY_JPQL);
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

				if (!pagination) {
					list = (List<ContentRepositoryEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ContentRepositoryEntry>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Returns the first content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	@Override
	public ContentRepositoryEntry findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<ContentRepositoryEntry> orderByComparator)
		throws NoSuchEntryException {

		ContentRepositoryEntry contentRepositoryEntry = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (contentRepositoryEntry != null) {
			return contentRepositoryEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the first content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	@Override
	public ContentRepositoryEntry fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		List<ContentRepositoryEntry> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching content repository entry
	 * @throws NoSuchEntryException if a matching content repository entry could not be found
	 */
	@Override
	public ContentRepositoryEntry findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<ContentRepositoryEntry> orderByComparator)
		throws NoSuchEntryException {

		ContentRepositoryEntry contentRepositoryEntry = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (contentRepositoryEntry != null) {
			return contentRepositoryEntry;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchEntryException(msg.toString());
	}

	/**
	 * Returns the last content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching content repository entry, or <code>null</code> if a matching content repository entry could not be found
	 */
	@Override
	public ContentRepositoryEntry fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<ContentRepositoryEntry> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the content repository entries before and after the current content repository entry in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param contentRepositoryEntryId the primary key of the current content repository entry
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next content repository entry
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	@Override
	public ContentRepositoryEntry[] findByUuid_C_PrevAndNext(
			long contentRepositoryEntryId, String uuid, long companyId,
			OrderByComparator<ContentRepositoryEntry> orderByComparator)
		throws NoSuchEntryException {

		uuid = Objects.toString(uuid, "");

		ContentRepositoryEntry contentRepositoryEntry = findByPrimaryKey(
			contentRepositoryEntryId);

		Session session = null;

		try {
			session = openSession();

			ContentRepositoryEntry[] array = new ContentRepositoryEntryImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, contentRepositoryEntry, uuid, companyId,
				orderByComparator, true);

			array[1] = contentRepositoryEntry;

			array[2] = getByUuid_C_PrevAndNext(
				session, contentRepositoryEntry, uuid, companyId,
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

	protected ContentRepositoryEntry getByUuid_C_PrevAndNext(
		Session session, ContentRepositoryEntry contentRepositoryEntry,
		String uuid, long companyId,
		OrderByComparator<ContentRepositoryEntry> orderByComparator,
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

		query.append(_SQL_SELECT_CONTENTREPOSITORYENTRY_WHERE);

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
			query.append(ContentRepositoryEntryModelImpl.ORDER_BY_JPQL);
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
						contentRepositoryEntry)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<ContentRepositoryEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the content repository entries where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (ContentRepositoryEntry contentRepositoryEntry :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(contentRepositoryEntry);
		}
	}

	/**
	 * Returns the number of content repository entries where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching content repository entries
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_CONTENTREPOSITORYENTRY_WHERE);

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
		"contentRepositoryEntry.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(contentRepositoryEntry.uuid IS NULL OR contentRepositoryEntry.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"contentRepositoryEntry.companyId = ?";

	public ContentRepositoryEntryPersistenceImpl() {
		setModelClass(ContentRepositoryEntry.class);

		setModelImplClass(ContentRepositoryEntryImpl.class);
		setModelPKClass(long.class);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the content repository entry in the entity cache if it is enabled.
	 *
	 * @param contentRepositoryEntry the content repository entry
	 */
	@Override
	public void cacheResult(ContentRepositoryEntry contentRepositoryEntry) {
		entityCache.putResult(
			entityCacheEnabled, ContentRepositoryEntryImpl.class,
			contentRepositoryEntry.getPrimaryKey(), contentRepositoryEntry);

		finderCache.putResult(
			_finderPathFetchByUUID_G,
			new Object[] {
				contentRepositoryEntry.getUuid(),
				contentRepositoryEntry.getGroupId()
			},
			contentRepositoryEntry);

		contentRepositoryEntry.resetOriginalValues();
	}

	/**
	 * Caches the content repository entries in the entity cache if it is enabled.
	 *
	 * @param contentRepositoryEntries the content repository entries
	 */
	@Override
	public void cacheResult(
		List<ContentRepositoryEntry> contentRepositoryEntries) {

		for (ContentRepositoryEntry contentRepositoryEntry :
				contentRepositoryEntries) {

			if (entityCache.getResult(
					entityCacheEnabled, ContentRepositoryEntryImpl.class,
					contentRepositoryEntry.getPrimaryKey()) == null) {

				cacheResult(contentRepositoryEntry);
			}
			else {
				contentRepositoryEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all content repository entries.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(ContentRepositoryEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the content repository entry.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(ContentRepositoryEntry contentRepositoryEntry) {
		entityCache.removeResult(
			entityCacheEnabled, ContentRepositoryEntryImpl.class,
			contentRepositoryEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(ContentRepositoryEntryModelImpl)contentRepositoryEntry, true);
	}

	@Override
	public void clearCache(
		List<ContentRepositoryEntry> contentRepositoryEntries) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (ContentRepositoryEntry contentRepositoryEntry :
				contentRepositoryEntries) {

			entityCache.removeResult(
				entityCacheEnabled, ContentRepositoryEntryImpl.class,
				contentRepositoryEntry.getPrimaryKey());

			clearUniqueFindersCache(
				(ContentRepositoryEntryModelImpl)contentRepositoryEntry, true);
		}
	}

	protected void cacheUniqueFindersCache(
		ContentRepositoryEntryModelImpl contentRepositoryEntryModelImpl) {

		Object[] args = new Object[] {
			contentRepositoryEntryModelImpl.getUuid(),
			contentRepositoryEntryModelImpl.getGroupId()
		};

		finderCache.putResult(
			_finderPathCountByUUID_G, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByUUID_G, args, contentRepositoryEntryModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		ContentRepositoryEntryModelImpl contentRepositoryEntryModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				contentRepositoryEntryModelImpl.getUuid(),
				contentRepositoryEntryModelImpl.getGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}

		if ((contentRepositoryEntryModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				contentRepositoryEntryModelImpl.getOriginalUuid(),
				contentRepositoryEntryModelImpl.getOriginalGroupId()
			};

			finderCache.removeResult(_finderPathCountByUUID_G, args);
			finderCache.removeResult(_finderPathFetchByUUID_G, args);
		}
	}

	/**
	 * Creates a new content repository entry with the primary key. Does not add the content repository entry to the database.
	 *
	 * @param contentRepositoryEntryId the primary key for the new content repository entry
	 * @return the new content repository entry
	 */
	@Override
	public ContentRepositoryEntry create(long contentRepositoryEntryId) {
		ContentRepositoryEntry contentRepositoryEntry =
			new ContentRepositoryEntryImpl();

		contentRepositoryEntry.setNew(true);
		contentRepositoryEntry.setPrimaryKey(contentRepositoryEntryId);

		String uuid = PortalUUIDUtil.generate();

		contentRepositoryEntry.setUuid(uuid);

		contentRepositoryEntry.setCompanyId(CompanyThreadLocal.getCompanyId());

		return contentRepositoryEntry;
	}

	/**
	 * Removes the content repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param contentRepositoryEntryId the primary key of the content repository entry
	 * @return the content repository entry that was removed
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	@Override
	public ContentRepositoryEntry remove(long contentRepositoryEntryId)
		throws NoSuchEntryException {

		return remove((Serializable)contentRepositoryEntryId);
	}

	/**
	 * Removes the content repository entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the content repository entry
	 * @return the content repository entry that was removed
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	@Override
	public ContentRepositoryEntry remove(Serializable primaryKey)
		throws NoSuchEntryException {

		Session session = null;

		try {
			session = openSession();

			ContentRepositoryEntry contentRepositoryEntry =
				(ContentRepositoryEntry)session.get(
					ContentRepositoryEntryImpl.class, primaryKey);

			if (contentRepositoryEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(contentRepositoryEntry);
		}
		catch (NoSuchEntryException nsee) {
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
	protected ContentRepositoryEntry removeImpl(
		ContentRepositoryEntry contentRepositoryEntry) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(contentRepositoryEntry)) {
				contentRepositoryEntry = (ContentRepositoryEntry)session.get(
					ContentRepositoryEntryImpl.class,
					contentRepositoryEntry.getPrimaryKeyObj());
			}

			if (contentRepositoryEntry != null) {
				session.delete(contentRepositoryEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (contentRepositoryEntry != null) {
			clearCache(contentRepositoryEntry);
		}

		return contentRepositoryEntry;
	}

	@Override
	public ContentRepositoryEntry updateImpl(
		ContentRepositoryEntry contentRepositoryEntry) {

		boolean isNew = contentRepositoryEntry.isNew();

		if (!(contentRepositoryEntry instanceof
				ContentRepositoryEntryModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(contentRepositoryEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					contentRepositoryEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in contentRepositoryEntry proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom ContentRepositoryEntry implementation " +
					contentRepositoryEntry.getClass());
		}

		ContentRepositoryEntryModelImpl contentRepositoryEntryModelImpl =
			(ContentRepositoryEntryModelImpl)contentRepositoryEntry;

		if (Validator.isNull(contentRepositoryEntry.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			contentRepositoryEntry.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (contentRepositoryEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				contentRepositoryEntry.setCreateDate(now);
			}
			else {
				contentRepositoryEntry.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!contentRepositoryEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				contentRepositoryEntry.setModifiedDate(now);
			}
			else {
				contentRepositoryEntry.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (contentRepositoryEntry.isNew()) {
				session.save(contentRepositoryEntry);

				contentRepositoryEntry.setNew(false);
			}
			else {
				contentRepositoryEntry = (ContentRepositoryEntry)session.merge(
					contentRepositoryEntry);
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
				contentRepositoryEntryModelImpl.getUuid()
			};

			finderCache.removeResult(_finderPathCountByUuid, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				contentRepositoryEntryModelImpl.getUuid(),
				contentRepositoryEntryModelImpl.getCompanyId()
			};

			finderCache.removeResult(_finderPathCountByUuid_C, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((contentRepositoryEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					contentRepositoryEntryModelImpl.getOriginalUuid()
				};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {contentRepositoryEntryModelImpl.getUuid()};

				finderCache.removeResult(_finderPathCountByUuid, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((contentRepositoryEntryModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					contentRepositoryEntryModelImpl.getOriginalUuid(),
					contentRepositoryEntryModelImpl.getOriginalCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					contentRepositoryEntryModelImpl.getUuid(),
					contentRepositoryEntryModelImpl.getCompanyId()
				};

				finderCache.removeResult(_finderPathCountByUuid_C, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, ContentRepositoryEntryImpl.class,
			contentRepositoryEntry.getPrimaryKey(), contentRepositoryEntry,
			false);

		clearUniqueFindersCache(contentRepositoryEntryModelImpl, false);
		cacheUniqueFindersCache(contentRepositoryEntryModelImpl);

		contentRepositoryEntry.resetOriginalValues();

		return contentRepositoryEntry;
	}

	/**
	 * Returns the content repository entry with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the content repository entry
	 * @return the content repository entry
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	@Override
	public ContentRepositoryEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryException {

		ContentRepositoryEntry contentRepositoryEntry = fetchByPrimaryKey(
			primaryKey);

		if (contentRepositoryEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return contentRepositoryEntry;
	}

	/**
	 * Returns the content repository entry with the primary key or throws a <code>NoSuchEntryException</code> if it could not be found.
	 *
	 * @param contentRepositoryEntryId the primary key of the content repository entry
	 * @return the content repository entry
	 * @throws NoSuchEntryException if a content repository entry with the primary key could not be found
	 */
	@Override
	public ContentRepositoryEntry findByPrimaryKey(
			long contentRepositoryEntryId)
		throws NoSuchEntryException {

		return findByPrimaryKey((Serializable)contentRepositoryEntryId);
	}

	/**
	 * Returns the content repository entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param contentRepositoryEntryId the primary key of the content repository entry
	 * @return the content repository entry, or <code>null</code> if a content repository entry with the primary key could not be found
	 */
	@Override
	public ContentRepositoryEntry fetchByPrimaryKey(
		long contentRepositoryEntryId) {

		return fetchByPrimaryKey((Serializable)contentRepositoryEntryId);
	}

	/**
	 * Returns all the content repository entries.
	 *
	 * @return the content repository entries
	 */
	@Override
	public List<ContentRepositoryEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the content repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @return the range of content repository entries
	 */
	@Override
	public List<ContentRepositoryEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the content repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of content repository entries
	 */
	@Override
	public List<ContentRepositoryEntry> findAll(
		int start, int end,
		OrderByComparator<ContentRepositoryEntry> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the content repository entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>ContentRepositoryEntryModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of content repository entries
	 * @param end the upper bound of the range of content repository entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of content repository entries
	 */
	@Override
	public List<ContentRepositoryEntry> findAll(
		int start, int end,
		OrderByComparator<ContentRepositoryEntry> orderByComparator,
		boolean useFinderCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<ContentRepositoryEntry> list = null;

		if (useFinderCache) {
			list = (List<ContentRepositoryEntry>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_CONTENTREPOSITORYENTRY);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_CONTENTREPOSITORYENTRY;

				if (pagination) {
					sql = sql.concat(
						ContentRepositoryEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<ContentRepositoryEntry>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<ContentRepositoryEntry>)QueryUtil.list(
						q, getDialect(), start, end);
				}

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
	 * Removes all the content repository entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (ContentRepositoryEntry contentRepositoryEntry : findAll()) {
			remove(contentRepositoryEntry);
		}
	}

	/**
	 * Returns the number of content repository entries.
	 *
	 * @return the number of content repository entries
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
					_SQL_COUNT_CONTENTREPOSITORYENTRY);

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
		return "contentRepositoryEntryId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CONTENTREPOSITORYENTRY;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return ContentRepositoryEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the content repository entry persistence.
	 */
	@Activate
	public void activate() {
		ContentRepositoryEntryModelImpl.setEntityCacheEnabled(
			entityCacheEnabled);
		ContentRepositoryEntryModelImpl.setFinderCacheEnabled(
			finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			ContentRepositoryEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			ContentRepositoryEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			ContentRepositoryEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			ContentRepositoryEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()},
			ContentRepositoryEntryModelImpl.UUID_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathFetchByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			ContentRepositoryEntryImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()},
			ContentRepositoryEntryModelImpl.UUID_COLUMN_BITMASK |
			ContentRepositoryEntryModelImpl.GROUPID_COLUMN_BITMASK);

		_finderPathCountByUUID_G = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			ContentRepositoryEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			ContentRepositoryEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			ContentRepositoryEntryModelImpl.UUID_COLUMN_BITMASK |
			ContentRepositoryEntryModelImpl.COMPANYID_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(ContentRepositoryEntryImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = ContentRepositoryPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.content.repository.model.ContentRepositoryEntry"),
			true);
	}

	@Override
	@Reference(
		target = ContentRepositoryPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = ContentRepositoryPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_CONTENTREPOSITORYENTRY =
		"SELECT contentRepositoryEntry FROM ContentRepositoryEntry contentRepositoryEntry";

	private static final String _SQL_SELECT_CONTENTREPOSITORYENTRY_WHERE =
		"SELECT contentRepositoryEntry FROM ContentRepositoryEntry contentRepositoryEntry WHERE ";

	private static final String _SQL_COUNT_CONTENTREPOSITORYENTRY =
		"SELECT COUNT(contentRepositoryEntry) FROM ContentRepositoryEntry contentRepositoryEntry";

	private static final String _SQL_COUNT_CONTENTREPOSITORYENTRY_WHERE =
		"SELECT COUNT(contentRepositoryEntry) FROM ContentRepositoryEntry contentRepositoryEntry WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"contentRepositoryEntry.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No ContentRepositoryEntry exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No ContentRepositoryEntry exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		ContentRepositoryEntryPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	static {
		try {
			Class.forName(
				ContentRepositoryPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}