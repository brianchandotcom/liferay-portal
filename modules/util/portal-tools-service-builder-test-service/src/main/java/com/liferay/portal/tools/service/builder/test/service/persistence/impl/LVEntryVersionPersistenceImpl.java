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

package com.liferay.portal.tools.service.builder.test.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchLVEntryVersionException;
import com.liferay.portal.tools.service.builder.test.model.LVEntryVersion;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryVersionImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.LVEntryVersionModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.LVEntryVersionPersistence;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the lv entry version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see LVEntryVersionPersistence
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.LVEntryVersionUtil
 * @generated
 */
@ProviderType
public class LVEntryVersionPersistenceImpl extends BasePersistenceImpl<LVEntryVersion>
	implements LVEntryVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link LVEntryVersionUtil} to access the lv entry version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LVEntryVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByLvEntryId;
	private FinderPath _finderPathWithoutPaginationFindByLvEntryId;
	private FinderPath _finderPathCountByLvEntryId;

	/**
	 * Returns all the lv entry versions where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByLvEntryId(long lvEntryId) {
		return findByLvEntryId(lvEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the lv entry versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByLvEntryId(long lvEntryId, int start,
		int end) {
		return findByLvEntryId(lvEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByLvEntryId(long lvEntryId, int start,
		int end, OrderByComparator<LVEntryVersion> orderByComparator) {
		return findByLvEntryId(lvEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where lvEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param lvEntryId the lv entry ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByLvEntryId(long lvEntryId, int start,
		int end, OrderByComparator<LVEntryVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByLvEntryId;
			finderArgs = new Object[] { lvEntryId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByLvEntryId;
			finderArgs = new Object[] { lvEntryId, start, end, orderByComparator };
		}

		List<LVEntryVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryVersion lvEntryVersion : list) {
					if ((lvEntryId != lvEntryVersion.getLvEntryId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYID_LVENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryId);

				if (!pagination) {
					list = (List<LVEntryVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LVEntryVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByLvEntryId_First(long lvEntryId,
		OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {
		LVEntryVersion lvEntryVersion = fetchByLvEntryId_First(lvEntryId,
				orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lvEntryId=");
		msg.append(lvEntryId);

		msg.append("}");

		throw new NoSuchLVEntryVersionException(msg.toString());
	}

	/**
	 * Returns the first lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByLvEntryId_First(long lvEntryId,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		List<LVEntryVersion> list = findByLvEntryId(lvEntryId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByLvEntryId_Last(long lvEntryId,
		OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {
		LVEntryVersion lvEntryVersion = fetchByLvEntryId_Last(lvEntryId,
				orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("lvEntryId=");
		msg.append(lvEntryId);

		msg.append("}");

		throw new NoSuchLVEntryVersionException(msg.toString());
	}

	/**
	 * Returns the last lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByLvEntryId_Last(long lvEntryId,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		int count = countByLvEntryId(lvEntryId);

		if (count == 0) {
			return null;
		}

		List<LVEntryVersion> list = findByLvEntryId(lvEntryId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where lvEntryId = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param lvEntryId the lv entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion[] findByLvEntryId_PrevAndNext(long lvEntryVersionId,
		long lvEntryId, OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {
		LVEntryVersion lvEntryVersion = findByPrimaryKey(lvEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryVersion[] array = new LVEntryVersionImpl[3];

			array[0] = getByLvEntryId_PrevAndNext(session, lvEntryVersion,
					lvEntryId, orderByComparator, true);

			array[1] = lvEntryVersion;

			array[2] = getByLvEntryId_PrevAndNext(session, lvEntryVersion,
					lvEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LVEntryVersion getByLvEntryId_PrevAndNext(Session session,
		LVEntryVersion lvEntryVersion, long lvEntryId,
		OrderByComparator<LVEntryVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

		query.append(_FINDER_COLUMN_LVENTRYID_LVENTRYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(lvEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					lvEntryVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntryVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry versions where lvEntryId = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 */
	@Override
	public void removeByLvEntryId(long lvEntryId) {
		for (LVEntryVersion lvEntryVersion : findByLvEntryId(lvEntryId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions where lvEntryId = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByLvEntryId(long lvEntryId) {
		FinderPath finderPath = _finderPathCountByLvEntryId;

		Object[] finderArgs = new Object[] { lvEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYID_LVENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryId);

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

	private static final String _FINDER_COLUMN_LVENTRYID_LVENTRYID_2 = "lvEntryVersion.lvEntryId = ?";
	private FinderPath _finderPathFetchByLvEntryId_Version;
	private FinderPath _finderPathCountByLvEntryId_Version;

	/**
	 * Returns the lv entry version where lvEntryId = &#63; and version = &#63; or throws a {@link NoSuchLVEntryVersionException} if it could not be found.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByLvEntryId_Version(long lvEntryId, int version)
		throws NoSuchLVEntryVersionException {
		LVEntryVersion lvEntryVersion = fetchByLvEntryId_Version(lvEntryId,
				version);

		if (lvEntryVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("lvEntryId=");
			msg.append(lvEntryId);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLVEntryVersionException(msg.toString());
		}

		return lvEntryVersion;
	}

	/**
	 * Returns the lv entry version where lvEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByLvEntryId_Version(long lvEntryId, int version) {
		return fetchByLvEntryId_Version(lvEntryId, version, true);
	}

	/**
	 * Returns the lv entry version where lvEntryId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByLvEntryId_Version(long lvEntryId, int version,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { lvEntryId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(_finderPathFetchByLvEntryId_Version,
					finderArgs, this);
		}

		if (result instanceof LVEntryVersion) {
			LVEntryVersion lvEntryVersion = (LVEntryVersion)result;

			if ((lvEntryId != lvEntryVersion.getLvEntryId()) ||
					(version != lvEntryVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYID_VERSION_LVENTRYID_2);

			query.append(_FINDER_COLUMN_LVENTRYID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryId);

				qPos.add(version);

				List<LVEntryVersion> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(_finderPathFetchByLvEntryId_Version,
						finderArgs, list);
				}
				else {
					LVEntryVersion lvEntryVersion = list.get(0);

					result = lvEntryVersion;

					cacheResult(lvEntryVersion);
				}
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathFetchByLvEntryId_Version,
					finderArgs);

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
			return (LVEntryVersion)result;
		}
	}

	/**
	 * Removes the lv entry version where lvEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the lv entry version that was removed
	 */
	@Override
	public LVEntryVersion removeByLvEntryId_Version(long lvEntryId, int version)
		throws NoSuchLVEntryVersionException {
		LVEntryVersion lvEntryVersion = findByLvEntryId_Version(lvEntryId,
				version);

		return remove(lvEntryVersion);
	}

	/**
	 * Returns the number of lv entry versions where lvEntryId = &#63; and version = &#63;.
	 *
	 * @param lvEntryId the lv entry ID
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByLvEntryId_Version(long lvEntryId, int version) {
		FinderPath finderPath = _finderPathCountByLvEntryId_Version;

		Object[] finderArgs = new Object[] { lvEntryId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			query.append(_FINDER_COLUMN_LVENTRYID_VERSION_LVENTRYID_2);

			query.append(_FINDER_COLUMN_LVENTRYID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(lvEntryId);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_LVENTRYID_VERSION_LVENTRYID_2 = "lvEntryVersion.lvEntryId = ? AND ";
	private static final String _FINDER_COLUMN_LVENTRYID_VERSION_VERSION_2 = "lvEntryVersion.version = ?";
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the lv entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId(long groupId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId(long groupId, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByGroupId;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<LVEntryVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryVersion lvEntryVersion : list) {
					if ((groupId != lvEntryVersion.getGroupId())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<LVEntryVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LVEntryVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByGroupId_First(long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {
		LVEntryVersion lvEntryVersion = fetchByGroupId_First(groupId,
				orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLVEntryVersionException(msg.toString());
	}

	/**
	 * Returns the first lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByGroupId_First(long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		List<LVEntryVersion> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByGroupId_Last(long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {
		LVEntryVersion lvEntryVersion = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLVEntryVersionException(msg.toString());
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByGroupId_Last(long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LVEntryVersion> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where groupId = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion[] findByGroupId_PrevAndNext(long lvEntryVersionId,
		long groupId, OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {
		LVEntryVersion lvEntryVersion = findByPrimaryKey(lvEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryVersion[] array = new LVEntryVersionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, lvEntryVersion,
					groupId, orderByComparator, true);

			array[1] = lvEntryVersion;

			array[2] = getByGroupId_PrevAndNext(session, lvEntryVersion,
					groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LVEntryVersion getByGroupId_PrevAndNext(Session session,
		LVEntryVersion lvEntryVersion, long groupId,
		OrderByComparator<LVEntryVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					lvEntryVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntryVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LVEntryVersion lvEntryVersion : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "lvEntryVersion.groupId = ?";
	private FinderPath _finderPathWithPaginationFindByGroupId_Version;
	private FinderPath _finderPathWithoutPaginationFindByGroupId_Version;
	private FinderPath _finderPathCountByGroupId_Version;

	/**
	 * Returns all the lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId_Version(long groupId, int version) {
		return findByGroupId_Version(groupId, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId_Version(long groupId,
		int version, int start, int end) {
		return findByGroupId_Version(groupId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId_Version(long groupId,
		int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		return findByGroupId_Version(groupId, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findByGroupId_Version(long groupId,
		int version, int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByGroupId_Version;
			finderArgs = new Object[] { groupId, version };
		}
		else {
			finderPath = _finderPathWithPaginationFindByGroupId_Version;
			finderArgs = new Object[] {
					groupId, version,
					
					start, end, orderByComparator
				};
		}

		List<LVEntryVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LVEntryVersion lvEntryVersion : list) {
					if ((groupId != lvEntryVersion.getGroupId()) ||
							(version != lvEntryVersion.getVersion())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(4 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(4);
			}

			query.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(version);

				if (!pagination) {
					list = (List<LVEntryVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LVEntryVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByGroupId_Version_First(long groupId,
		int version, OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {
		LVEntryVersion lvEntryVersion = fetchByGroupId_Version_First(groupId,
				version, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLVEntryVersionException(msg.toString());
	}

	/**
	 * Returns the first lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByGroupId_Version_First(long groupId,
		int version, OrderByComparator<LVEntryVersion> orderByComparator) {
		List<LVEntryVersion> list = findByGroupId_Version(groupId, version, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version
	 * @throws NoSuchLVEntryVersionException if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion findByGroupId_Version_Last(long groupId, int version,
		OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {
		LVEntryVersion lvEntryVersion = fetchByGroupId_Version_Last(groupId,
				version, orderByComparator);

		if (lvEntryVersion != null) {
			return lvEntryVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLVEntryVersionException(msg.toString());
	}

	/**
	 * Returns the last lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching lv entry version, or <code>null</code> if a matching lv entry version could not be found
	 */
	@Override
	public LVEntryVersion fetchByGroupId_Version_Last(long groupId,
		int version, OrderByComparator<LVEntryVersion> orderByComparator) {
		int count = countByGroupId_Version(groupId, version);

		if (count == 0) {
			return null;
		}

		List<LVEntryVersion> list = findByGroupId_Version(groupId, version,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the lv entry versions before and after the current lv entry version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param lvEntryVersionId the primary key of the current lv entry version
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion[] findByGroupId_Version_PrevAndNext(
		long lvEntryVersionId, long groupId, int version,
		OrderByComparator<LVEntryVersion> orderByComparator)
		throws NoSuchLVEntryVersionException {
		LVEntryVersion lvEntryVersion = findByPrimaryKey(lvEntryVersionId);

		Session session = null;

		try {
			session = openSession();

			LVEntryVersion[] array = new LVEntryVersionImpl[3];

			array[0] = getByGroupId_Version_PrevAndNext(session,
					lvEntryVersion, groupId, version, orderByComparator, true);

			array[1] = lvEntryVersion;

			array[2] = getByGroupId_Version_PrevAndNext(session,
					lvEntryVersion, groupId, version, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LVEntryVersion getByGroupId_Version_PrevAndNext(Session session,
		LVEntryVersion lvEntryVersion, long groupId, int version,
		OrderByComparator<LVEntryVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LVENTRYVERSION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

		query.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields = orderByComparator.getOrderByConditionFields();

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
			query.append(LVEntryVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					lvEntryVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LVEntryVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the lv entry versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	@Override
	public void removeByGroupId_Version(long groupId, int version) {
		for (LVEntryVersion lvEntryVersion : findByGroupId_Version(groupId,
				version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching lv entry versions
	 */
	@Override
	public int countByGroupId_Version(long groupId, int version) {
		FinderPath finderPath = _finderPathCountByGroupId_Version;

		Object[] finderArgs = new Object[] { groupId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LVENTRYVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_GROUPID_VERSION_GROUPID_2 = "lvEntryVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_GROUPID_VERSION_VERSION_2 = "lvEntryVersion.version = ?";

	public LVEntryVersionPersistenceImpl() {
		setModelClass(LVEntryVersion.class);
	}

	/**
	 * Caches the lv entry version in the entity cache if it is enabled.
	 *
	 * @param lvEntryVersion the lv entry version
	 */
	@Override
	public void cacheResult(LVEntryVersion lvEntryVersion) {
		entityCache.putResult(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryVersionImpl.class, lvEntryVersion.getPrimaryKey(),
			lvEntryVersion);

		finderCache.putResult(_finderPathFetchByLvEntryId_Version,
			new Object[] {
				lvEntryVersion.getLvEntryId(), lvEntryVersion.getVersion()
			}, lvEntryVersion);

		lvEntryVersion.resetOriginalValues();
	}

	/**
	 * Caches the lv entry versions in the entity cache if it is enabled.
	 *
	 * @param lvEntryVersions the lv entry versions
	 */
	@Override
	public void cacheResult(List<LVEntryVersion> lvEntryVersions) {
		for (LVEntryVersion lvEntryVersion : lvEntryVersions) {
			if (entityCache.getResult(
						LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
						LVEntryVersionImpl.class, lvEntryVersion.getPrimaryKey()) == null) {
				cacheResult(lvEntryVersion);
			}
			else {
				lvEntryVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all lv entry versions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(LVEntryVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the lv entry version.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LVEntryVersion lvEntryVersion) {
		entityCache.removeResult(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryVersionImpl.class, lvEntryVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LVEntryVersionModelImpl)lvEntryVersion, true);
	}

	@Override
	public void clearCache(List<LVEntryVersion> lvEntryVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LVEntryVersion lvEntryVersion : lvEntryVersions) {
			entityCache.removeResult(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionImpl.class, lvEntryVersion.getPrimaryKey());

			clearUniqueFindersCache((LVEntryVersionModelImpl)lvEntryVersion,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		LVEntryVersionModelImpl lvEntryVersionModelImpl) {
		Object[] args = new Object[] {
				lvEntryVersionModelImpl.getLvEntryId(),
				lvEntryVersionModelImpl.getVersion()
			};

		finderCache.putResult(_finderPathCountByLvEntryId_Version, args,
			Long.valueOf(1), false);
		finderCache.putResult(_finderPathFetchByLvEntryId_Version, args,
			lvEntryVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LVEntryVersionModelImpl lvEntryVersionModelImpl, boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					lvEntryVersionModelImpl.getLvEntryId(),
					lvEntryVersionModelImpl.getVersion()
				};

			finderCache.removeResult(_finderPathCountByLvEntryId_Version, args);
			finderCache.removeResult(_finderPathFetchByLvEntryId_Version, args);
		}

		if ((lvEntryVersionModelImpl.getColumnBitmask() &
				_finderPathFetchByLvEntryId_Version.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					lvEntryVersionModelImpl.getOriginalLvEntryId(),
					lvEntryVersionModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(_finderPathCountByLvEntryId_Version, args);
			finderCache.removeResult(_finderPathFetchByLvEntryId_Version, args);
		}
	}

	/**
	 * Creates a new lv entry version with the primary key. Does not add the lv entry version to the database.
	 *
	 * @param lvEntryVersionId the primary key for the new lv entry version
	 * @return the new lv entry version
	 */
	@Override
	public LVEntryVersion create(long lvEntryVersionId) {
		LVEntryVersion lvEntryVersion = new LVEntryVersionImpl();

		lvEntryVersion.setNew(true);
		lvEntryVersion.setPrimaryKey(lvEntryVersionId);

		return lvEntryVersion;
	}

	/**
	 * Removes the lv entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param lvEntryVersionId the primary key of the lv entry version
	 * @return the lv entry version that was removed
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion remove(long lvEntryVersionId)
		throws NoSuchLVEntryVersionException {
		return remove((Serializable)lvEntryVersionId);
	}

	/**
	 * Removes the lv entry version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the lv entry version
	 * @return the lv entry version that was removed
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion remove(Serializable primaryKey)
		throws NoSuchLVEntryVersionException {
		Session session = null;

		try {
			session = openSession();

			LVEntryVersion lvEntryVersion = (LVEntryVersion)session.get(LVEntryVersionImpl.class,
					primaryKey);

			if (lvEntryVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLVEntryVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(lvEntryVersion);
		}
		catch (NoSuchLVEntryVersionException nsee) {
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
	protected LVEntryVersion removeImpl(LVEntryVersion lvEntryVersion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(lvEntryVersion)) {
				lvEntryVersion = (LVEntryVersion)session.get(LVEntryVersionImpl.class,
						lvEntryVersion.getPrimaryKeyObj());
			}

			if (lvEntryVersion != null) {
				session.delete(lvEntryVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (lvEntryVersion != null) {
			clearCache(lvEntryVersion);
		}

		return lvEntryVersion;
	}

	@Override
	public LVEntryVersion updateImpl(LVEntryVersion lvEntryVersion) {
		boolean isNew = lvEntryVersion.isNew();

		if (!(lvEntryVersion instanceof LVEntryVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(lvEntryVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(lvEntryVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in lvEntryVersion proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LVEntryVersion implementation " +
				lvEntryVersion.getClass());
		}

		LVEntryVersionModelImpl lvEntryVersionModelImpl = (LVEntryVersionModelImpl)lvEntryVersion;

		Session session = null;

		try {
			session = openSession();

			if (lvEntryVersion.isNew()) {
				session.save(lvEntryVersion);

				lvEntryVersion.setNew(false);
			}
			else {
				throw new IllegalArgumentException(
					"LVEntryVersion is read only, create a new version instead");
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LVEntryVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { lvEntryVersionModelImpl.getLvEntryId() };

			finderCache.removeResult(_finderPathCountByLvEntryId, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByLvEntryId,
				args);

			args = new Object[] { lvEntryVersionModelImpl.getGroupId() };

			finderCache.removeResult(_finderPathCountByGroupId, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId,
				args);

			args = new Object[] {
					lvEntryVersionModelImpl.getGroupId(),
					lvEntryVersionModelImpl.getVersion()
				};

			finderCache.removeResult(_finderPathCountByGroupId_Version, args);
			finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId_Version,
				args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((lvEntryVersionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByLvEntryId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						lvEntryVersionModelImpl.getOriginalLvEntryId()
					};

				finderCache.removeResult(_finderPathCountByLvEntryId, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByLvEntryId,
					args);

				args = new Object[] { lvEntryVersionModelImpl.getLvEntryId() };

				finderCache.removeResult(_finderPathCountByLvEntryId, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByLvEntryId,
					args);
			}

			if ((lvEntryVersionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByGroupId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						lvEntryVersionModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);

				args = new Object[] { lvEntryVersionModelImpl.getGroupId() };

				finderCache.removeResult(_finderPathCountByGroupId, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);
			}

			if ((lvEntryVersionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByGroupId_Version.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						lvEntryVersionModelImpl.getOriginalGroupId(),
						lvEntryVersionModelImpl.getOriginalVersion()
					};

				finderCache.removeResult(_finderPathCountByGroupId_Version, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId_Version,
					args);

				args = new Object[] {
						lvEntryVersionModelImpl.getGroupId(),
						lvEntryVersionModelImpl.getVersion()
					};

				finderCache.removeResult(_finderPathCountByGroupId_Version, args);
				finderCache.removeResult(_finderPathWithoutPaginationFindByGroupId_Version,
					args);
			}
		}

		entityCache.putResult(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
			LVEntryVersionImpl.class, lvEntryVersion.getPrimaryKey(),
			lvEntryVersion, false);

		clearUniqueFindersCache(lvEntryVersionModelImpl, false);
		cacheUniqueFindersCache(lvEntryVersionModelImpl);

		lvEntryVersion.resetOriginalValues();

		return lvEntryVersion;
	}

	/**
	 * Returns the lv entry version with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the lv entry version
	 * @return the lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLVEntryVersionException {
		LVEntryVersion lvEntryVersion = fetchByPrimaryKey(primaryKey);

		if (lvEntryVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLVEntryVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return lvEntryVersion;
	}

	/**
	 * Returns the lv entry version with the primary key or throws a {@link NoSuchLVEntryVersionException} if it could not be found.
	 *
	 * @param lvEntryVersionId the primary key of the lv entry version
	 * @return the lv entry version
	 * @throws NoSuchLVEntryVersionException if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion findByPrimaryKey(long lvEntryVersionId)
		throws NoSuchLVEntryVersionException {
		return findByPrimaryKey((Serializable)lvEntryVersionId);
	}

	/**
	 * Returns the lv entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the lv entry version
	 * @return the lv entry version, or <code>null</code> if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		LVEntryVersion lvEntryVersion = (LVEntryVersion)serializable;

		if (lvEntryVersion == null) {
			Session session = null;

			try {
				session = openSession();

				lvEntryVersion = (LVEntryVersion)session.get(LVEntryVersionImpl.class,
						primaryKey);

				if (lvEntryVersion != null) {
					cacheResult(lvEntryVersion);
				}
				else {
					entityCache.putResult(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
						LVEntryVersionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
					LVEntryVersionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return lvEntryVersion;
	}

	/**
	 * Returns the lv entry version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param lvEntryVersionId the primary key of the lv entry version
	 * @return the lv entry version, or <code>null</code> if a lv entry version with the primary key could not be found
	 */
	@Override
	public LVEntryVersion fetchByPrimaryKey(long lvEntryVersionId) {
		return fetchByPrimaryKey((Serializable)lvEntryVersionId);
	}

	@Override
	public Map<Serializable, LVEntryVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, LVEntryVersion> map = new HashMap<Serializable, LVEntryVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			LVEntryVersion lvEntryVersion = fetchByPrimaryKey(primaryKey);

			if (lvEntryVersion != null) {
				map.put(primaryKey, lvEntryVersion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
					LVEntryVersionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (LVEntryVersion)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_LVENTRYVERSION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append((long)primaryKey);

			query.append(",");
		}

		query.setIndex(query.index() - 1);

		query.append(")");

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (LVEntryVersion lvEntryVersion : (List<LVEntryVersion>)q.list()) {
				map.put(lvEntryVersion.getPrimaryKeyObj(), lvEntryVersion);

				cacheResult(lvEntryVersion);

				uncachedPrimaryKeys.remove(lvEntryVersion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
					LVEntryVersionImpl.class, primaryKey, nullModel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the lv entry versions.
	 *
	 * @return the lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the lv entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @return the range of lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the lv entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findAll(int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the lv entry versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link LVEntryVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of lv entry versions
	 * @param end the upper bound of the range of lv entry versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of lv entry versions
	 */
	@Override
	public List<LVEntryVersion> findAll(int start, int end,
		OrderByComparator<LVEntryVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindAll;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<LVEntryVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LVEntryVersion>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LVENTRYVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LVENTRYVERSION;

				if (pagination) {
					sql = sql.concat(LVEntryVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LVEntryVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LVEntryVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				finderCache.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the lv entry versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LVEntryVersion lvEntryVersion : findAll()) {
			remove(lvEntryVersion);
		}
	}

	/**
	 * Returns the number of lv entry versions.
	 *
	 * @return the number of lv entry versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LVENTRYVERSION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(_finderPathCountAll, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LVEntryVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the lv entry version persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED,
				LVEntryVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED,
				LVEntryVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
				new String[0]);

		_finderPathCountAll = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
				new String[0]);

		_finderPathWithPaginationFindByLvEntryId = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED,
				LVEntryVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLvEntryId",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByLvEntryId = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED,
				LVEntryVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLvEntryId",
				new String[] { Long.class.getName() },
				LVEntryVersionModelImpl.LVENTRYID_COLUMN_BITMASK |
				LVEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLvEntryId = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByLvEntryId",
				new String[] { Long.class.getName() });

		_finderPathFetchByLvEntryId_Version = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED,
				LVEntryVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
				"fetchByLvEntryId_Version",
				new String[] { Long.class.getName(), Integer.class.getName() },
				LVEntryVersionModelImpl.LVENTRYID_COLUMN_BITMASK |
				LVEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLvEntryId_Version = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByLvEntryId_Version",
				new String[] { Long.class.getName(), Integer.class.getName() });

		_finderPathWithPaginationFindByGroupId = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED,
				LVEntryVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED,
				LVEntryVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
				new String[] { Long.class.getName() },
				LVEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
				LVEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
				new String[] { Long.class.getName() });

		_finderPathWithPaginationFindByGroupId_Version = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED,
				LVEntryVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByGroupId_Version",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByGroupId_Version = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED,
				LVEntryVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByGroupId_Version",
				new String[] { Long.class.getName(), Integer.class.getName() },
				LVEntryVersionModelImpl.GROUPID_COLUMN_BITMASK |
				LVEntryVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByGroupId_Version = new FinderPath(LVEntryVersionModelImpl.ENTITY_CACHE_ENABLED,
				LVEntryVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByGroupId_Version",
				new String[] { Long.class.getName(), Integer.class.getName() });
	}

	public void destroy() {
		entityCache.removeCache(LVEntryVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_LVENTRYVERSION = "SELECT lvEntryVersion FROM LVEntryVersion lvEntryVersion";
	private static final String _SQL_SELECT_LVENTRYVERSION_WHERE_PKS_IN = "SELECT lvEntryVersion FROM LVEntryVersion lvEntryVersion WHERE lvEntryVersionId IN (";
	private static final String _SQL_SELECT_LVENTRYVERSION_WHERE = "SELECT lvEntryVersion FROM LVEntryVersion lvEntryVersion WHERE ";
	private static final String _SQL_COUNT_LVENTRYVERSION = "SELECT COUNT(lvEntryVersion) FROM LVEntryVersion lvEntryVersion";
	private static final String _SQL_COUNT_LVENTRYVERSION_WHERE = "SELECT COUNT(lvEntryVersion) FROM LVEntryVersion lvEntryVersion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "lvEntryVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LVEntryVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LVEntryVersion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LVEntryVersionPersistenceImpl.class);
}