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

package com.liferay.portlet.dynamicdatamapping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.model.CacheModel;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import com.liferay.portlet.dynamicdatamapping.NoSuchStructureVersionException;
import com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureVersionImpl;
import com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureVersionModelImpl;
import com.liferay.portlet.dynamicdatamapping.service.persistence.DDMStructureVersionPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the d d m structure version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMStructureVersionPersistence
 * @see DDMStructureVersionUtil
 * @generated
 */
@ProviderType
public class DDMStructureVersionPersistenceImpl extends BasePersistenceImpl<DDMStructureVersion>
	implements DDMStructureVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMStructureVersionUtil} to access the d d m structure version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMStructureVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByStructureId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID =
		new FinderPath(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByStructureId",
			new String[] { Long.class.getName() },
			DDMStructureVersionModelImpl.STRUCTUREID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_STRUCTUREID = new FinderPath(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStructureId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the d d m structure versions where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the matching d d m structure versions
	 */
	@Override
	public List<DDMStructureVersion> findByStructureId(long structureId) {
		return findByStructureId(structureId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structure versions where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m structure versions
	 * @param end the upper bound of the range of d d m structure versions (not inclusive)
	 * @return the range of matching d d m structure versions
	 */
	@Override
	public List<DDMStructureVersion> findByStructureId(long structureId,
		int start, int end) {
		return findByStructureId(structureId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structure versions where structureId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param structureId the structure ID
	 * @param start the lower bound of the range of d d m structure versions
	 * @param end the upper bound of the range of d d m structure versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching d d m structure versions
	 */
	@Override
	public List<DDMStructureVersion> findByStructureId(long structureId,
		int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_STRUCTUREID;
			finderArgs = new Object[] { structureId, start, end, orderByComparator };
		}

		List<DDMStructureVersion> list = (List<DDMStructureVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if ((list != null) && !list.isEmpty()) {
			for (DDMStructureVersion ddmStructureVersion : list) {
				if ((structureId != ddmStructureVersion.getStructureId())) {
					list = null;

					break;
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(3 +
						(orderByComparator.getOrderByFields().length * 3));
			}
			else {
				query = new StringBundler(3);
			}

			query.append(_SQL_SELECT_DDMSTRUCTUREVERSION_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMStructureVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				if (!pagination) {
					list = (List<DDMStructureVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructureVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first d d m structure version in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure version
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureVersionException if a matching d d m structure version could not be found
	 */
	@Override
	public DDMStructureVersion findByStructureId_First(long structureId,
		OrderByComparator<DDMStructureVersion> orderByComparator)
		throws NoSuchStructureVersionException {
		DDMStructureVersion ddmStructureVersion = fetchByStructureId_First(structureId,
				orderByComparator);

		if (ddmStructureVersion != null) {
			return ddmStructureVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureVersionException(msg.toString());
	}

	/**
	 * Returns the first d d m structure version in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching d d m structure version, or <code>null</code> if a matching d d m structure version could not be found
	 */
	@Override
	public DDMStructureVersion fetchByStructureId_First(long structureId,
		OrderByComparator<DDMStructureVersion> orderByComparator) {
		List<DDMStructureVersion> list = findByStructureId(structureId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last d d m structure version in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure version
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureVersionException if a matching d d m structure version could not be found
	 */
	@Override
	public DDMStructureVersion findByStructureId_Last(long structureId,
		OrderByComparator<DDMStructureVersion> orderByComparator)
		throws NoSuchStructureVersionException {
		DDMStructureVersion ddmStructureVersion = fetchByStructureId_Last(structureId,
				orderByComparator);

		if (ddmStructureVersion != null) {
			return ddmStructureVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("structureId=");
		msg.append(structureId);

		msg.append(StringPool.CLOSE_CURLY_BRACE);

		throw new NoSuchStructureVersionException(msg.toString());
	}

	/**
	 * Returns the last d d m structure version in the ordered set where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching d d m structure version, or <code>null</code> if a matching d d m structure version could not be found
	 */
	@Override
	public DDMStructureVersion fetchByStructureId_Last(long structureId,
		OrderByComparator<DDMStructureVersion> orderByComparator) {
		int count = countByStructureId(structureId);

		if (count == 0) {
			return null;
		}

		List<DDMStructureVersion> list = findByStructureId(structureId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the d d m structure versions before and after the current d d m structure version in the ordered set where structureId = &#63;.
	 *
	 * @param structureVersionId the primary key of the current d d m structure version
	 * @param structureId the structure ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next d d m structure version
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureVersionException if a d d m structure version with the primary key could not be found
	 */
	@Override
	public DDMStructureVersion[] findByStructureId_PrevAndNext(
		long structureVersionId, long structureId,
		OrderByComparator<DDMStructureVersion> orderByComparator)
		throws NoSuchStructureVersionException {
		DDMStructureVersion ddmStructureVersion = findByPrimaryKey(structureVersionId);

		Session session = null;

		try {
			session = openSession();

			DDMStructureVersion[] array = new DDMStructureVersionImpl[3];

			array[0] = getByStructureId_PrevAndNext(session,
					ddmStructureVersion, structureId, orderByComparator, true);

			array[1] = ddmStructureVersion;

			array[2] = getByStructureId_PrevAndNext(session,
					ddmStructureVersion, structureId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMStructureVersion getByStructureId_PrevAndNext(
		Session session, DDMStructureVersion ddmStructureVersion,
		long structureId,
		OrderByComparator<DDMStructureVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByFields().length * 6));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_DDMSTRUCTUREVERSION_WHERE);

		query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

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
			query.append(DDMStructureVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(structureId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmStructureVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMStructureVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the d d m structure versions where structureId = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 */
	@Override
	public void removeByStructureId(long structureId) {
		for (DDMStructureVersion ddmStructureVersion : findByStructureId(
				structureId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmStructureVersion);
		}
	}

	/**
	 * Returns the number of d d m structure versions where structureId = &#63;.
	 *
	 * @param structureId the structure ID
	 * @return the number of matching d d m structure versions
	 */
	@Override
	public int countByStructureId(long structureId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_STRUCTUREID;

		Object[] finderArgs = new Object[] { structureId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMSTRUCTUREVERSION_WHERE);

			query.append(_FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_STRUCTUREID_STRUCTUREID_2 = "ddmStructureVersion.structureId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_S_V = new FinderPath(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMStructureVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByS_V",
			new String[] { Long.class.getName(), String.class.getName() },
			DDMStructureVersionModelImpl.STRUCTUREID_COLUMN_BITMASK |
			DDMStructureVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_S_V = new FinderPath(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByS_V",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the d d m structure version where structureId = &#63; and version = &#63; or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureVersionException} if it could not be found.
	 *
	 * @param structureId the structure ID
	 * @param version the version
	 * @return the matching d d m structure version
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureVersionException if a matching d d m structure version could not be found
	 */
	@Override
	public DDMStructureVersion findByS_V(long structureId, String version)
		throws NoSuchStructureVersionException {
		DDMStructureVersion ddmStructureVersion = fetchByS_V(structureId,
				version);

		if (ddmStructureVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("structureId=");
			msg.append(structureId);

			msg.append(", version=");
			msg.append(version);

			msg.append(StringPool.CLOSE_CURLY_BRACE);

			if (_log.isWarnEnabled()) {
				_log.warn(msg.toString());
			}

			throw new NoSuchStructureVersionException(msg.toString());
		}

		return ddmStructureVersion;
	}

	/**
	 * Returns the d d m structure version where structureId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param structureId the structure ID
	 * @param version the version
	 * @return the matching d d m structure version, or <code>null</code> if a matching d d m structure version could not be found
	 */
	@Override
	public DDMStructureVersion fetchByS_V(long structureId, String version) {
		return fetchByS_V(structureId, version, true);
	}

	/**
	 * Returns the d d m structure version where structureId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param structureId the structure ID
	 * @param version the version
	 * @param retrieveFromCache whether to use the finder cache
	 * @return the matching d d m structure version, or <code>null</code> if a matching d d m structure version could not be found
	 */
	@Override
	public DDMStructureVersion fetchByS_V(long structureId, String version,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { structureId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(FINDER_PATH_FETCH_BY_S_V,
					finderArgs, this);
		}

		if (result instanceof DDMStructureVersion) {
			DDMStructureVersion ddmStructureVersion = (DDMStructureVersion)result;

			if ((structureId != ddmStructureVersion.getStructureId()) ||
					!Validator.equals(version, ddmStructureVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDMSTRUCTUREVERSION_WHERE);

			query.append(_FINDER_COLUMN_S_V_STRUCTUREID_2);

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_S_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_S_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_S_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				if (bindVersion) {
					qPos.add(version);
				}

				List<DDMStructureVersion> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_S_V,
						finderArgs, list);
				}
				else {
					DDMStructureVersion ddmStructureVersion = list.get(0);

					result = ddmStructureVersion;

					cacheResult(ddmStructureVersion);

					if ((ddmStructureVersion.getStructureId() != structureId) ||
							(ddmStructureVersion.getVersion() == null) ||
							!ddmStructureVersion.getVersion().equals(version)) {
						FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_S_V,
							finderArgs, ddmStructureVersion);
					}
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_S_V,
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
			return (DDMStructureVersion)result;
		}
	}

	/**
	 * Removes the d d m structure version where structureId = &#63; and version = &#63; from the database.
	 *
	 * @param structureId the structure ID
	 * @param version the version
	 * @return the d d m structure version that was removed
	 */
	@Override
	public DDMStructureVersion removeByS_V(long structureId, String version)
		throws NoSuchStructureVersionException {
		DDMStructureVersion ddmStructureVersion = findByS_V(structureId, version);

		return remove(ddmStructureVersion);
	}

	/**
	 * Returns the number of d d m structure versions where structureId = &#63; and version = &#63;.
	 *
	 * @param structureId the structure ID
	 * @param version the version
	 * @return the number of matching d d m structure versions
	 */
	@Override
	public int countByS_V(long structureId, String version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_S_V;

		Object[] finderArgs = new Object[] { structureId, version };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMSTRUCTUREVERSION_WHERE);

			query.append(_FINDER_COLUMN_S_V_STRUCTUREID_2);

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_S_V_VERSION_1);
			}
			else if (version.equals(StringPool.BLANK)) {
				query.append(_FINDER_COLUMN_S_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_S_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(structureId);

				if (bindVersion) {
					qPos.add(version);
				}

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(finderPath, finderArgs, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_S_V_STRUCTUREID_2 = "ddmStructureVersion.structureId = ? AND ";
	private static final String _FINDER_COLUMN_S_V_VERSION_1 = "ddmStructureVersion.version IS NULL";
	private static final String _FINDER_COLUMN_S_V_VERSION_2 = "ddmStructureVersion.version = ?";
	private static final String _FINDER_COLUMN_S_V_VERSION_3 = "(ddmStructureVersion.version IS NULL OR ddmStructureVersion.version = '')";

	public DDMStructureVersionPersistenceImpl() {
		setModelClass(DDMStructureVersion.class);
	}

	/**
	 * Caches the d d m structure version in the entity cache if it is enabled.
	 *
	 * @param ddmStructureVersion the d d m structure version
	 */
	@Override
	public void cacheResult(DDMStructureVersion ddmStructureVersion) {
		EntityCacheUtil.putResult(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureVersionImpl.class, ddmStructureVersion.getPrimaryKey(),
			ddmStructureVersion);

		FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_S_V,
			new Object[] {
				ddmStructureVersion.getStructureId(),
				ddmStructureVersion.getVersion()
			}, ddmStructureVersion);

		ddmStructureVersion.resetOriginalValues();
	}

	/**
	 * Caches the d d m structure versions in the entity cache if it is enabled.
	 *
	 * @param ddmStructureVersions the d d m structure versions
	 */
	@Override
	public void cacheResult(List<DDMStructureVersion> ddmStructureVersions) {
		for (DDMStructureVersion ddmStructureVersion : ddmStructureVersions) {
			if (EntityCacheUtil.getResult(
						DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureVersionImpl.class,
						ddmStructureVersion.getPrimaryKey()) == null) {
				cacheResult(ddmStructureVersion);
			}
			else {
				ddmStructureVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all d d m structure versions.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		if (_HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE) {
			CacheRegistryUtil.clear(DDMStructureVersionImpl.class.getName());
		}

		EntityCacheUtil.clearCache(DDMStructureVersionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the d d m structure version.
	 *
	 * <p>
	 * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMStructureVersion ddmStructureVersion) {
		EntityCacheUtil.removeResult(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureVersionImpl.class, ddmStructureVersion.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(ddmStructureVersion);
	}

	@Override
	public void clearCache(List<DDMStructureVersion> ddmStructureVersions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMStructureVersion ddmStructureVersion : ddmStructureVersions) {
			EntityCacheUtil.removeResult(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMStructureVersionImpl.class,
				ddmStructureVersion.getPrimaryKey());

			clearUniqueFindersCache(ddmStructureVersion);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMStructureVersion ddmStructureVersion) {
		if (ddmStructureVersion.isNew()) {
			Object[] args = new Object[] {
					ddmStructureVersion.getStructureId(),
					ddmStructureVersion.getVersion()
				};

			FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_S_V, args,
				Long.valueOf(1));
			FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_S_V, args,
				ddmStructureVersion);
		}
		else {
			DDMStructureVersionModelImpl ddmStructureVersionModelImpl = (DDMStructureVersionModelImpl)ddmStructureVersion;

			if ((ddmStructureVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_FETCH_BY_S_V.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureVersion.getStructureId(),
						ddmStructureVersion.getVersion()
					};

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_S_V, args,
					Long.valueOf(1));
				FinderCacheUtil.putResult(FINDER_PATH_FETCH_BY_S_V, args,
					ddmStructureVersion);
			}
		}
	}

	protected void clearUniqueFindersCache(
		DDMStructureVersion ddmStructureVersion) {
		DDMStructureVersionModelImpl ddmStructureVersionModelImpl = (DDMStructureVersionModelImpl)ddmStructureVersion;

		Object[] args = new Object[] {
				ddmStructureVersion.getStructureId(),
				ddmStructureVersion.getVersion()
			};

		FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_S_V, args);
		FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_S_V, args);

		if ((ddmStructureVersionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_S_V.getColumnBitmask()) != 0) {
			args = new Object[] {
					ddmStructureVersionModelImpl.getOriginalStructureId(),
					ddmStructureVersionModelImpl.getOriginalVersion()
				};

			FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_S_V, args);
			FinderCacheUtil.removeResult(FINDER_PATH_FETCH_BY_S_V, args);
		}
	}

	/**
	 * Creates a new d d m structure version with the primary key. Does not add the d d m structure version to the database.
	 *
	 * @param structureVersionId the primary key for the new d d m structure version
	 * @return the new d d m structure version
	 */
	@Override
	public DDMStructureVersion create(long structureVersionId) {
		DDMStructureVersion ddmStructureVersion = new DDMStructureVersionImpl();

		ddmStructureVersion.setNew(true);
		ddmStructureVersion.setPrimaryKey(structureVersionId);

		return ddmStructureVersion;
	}

	/**
	 * Removes the d d m structure version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param structureVersionId the primary key of the d d m structure version
	 * @return the d d m structure version that was removed
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureVersionException if a d d m structure version with the primary key could not be found
	 */
	@Override
	public DDMStructureVersion remove(long structureVersionId)
		throws NoSuchStructureVersionException {
		return remove((Serializable)structureVersionId);
	}

	/**
	 * Removes the d d m structure version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the d d m structure version
	 * @return the d d m structure version that was removed
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureVersionException if a d d m structure version with the primary key could not be found
	 */
	@Override
	public DDMStructureVersion remove(Serializable primaryKey)
		throws NoSuchStructureVersionException {
		Session session = null;

		try {
			session = openSession();

			DDMStructureVersion ddmStructureVersion = (DDMStructureVersion)session.get(DDMStructureVersionImpl.class,
					primaryKey);

			if (ddmStructureVersion == null) {
				if (_log.isWarnEnabled()) {
					_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchStructureVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmStructureVersion);
		}
		catch (NoSuchStructureVersionException nsee) {
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
	protected DDMStructureVersion removeImpl(
		DDMStructureVersion ddmStructureVersion) {
		ddmStructureVersion = toUnwrappedModel(ddmStructureVersion);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmStructureVersion)) {
				ddmStructureVersion = (DDMStructureVersion)session.get(DDMStructureVersionImpl.class,
						ddmStructureVersion.getPrimaryKeyObj());
			}

			if (ddmStructureVersion != null) {
				session.delete(ddmStructureVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmStructureVersion != null) {
			clearCache(ddmStructureVersion);
		}

		return ddmStructureVersion;
	}

	@Override
	public DDMStructureVersion updateImpl(
		com.liferay.portlet.dynamicdatamapping.model.DDMStructureVersion ddmStructureVersion) {
		ddmStructureVersion = toUnwrappedModel(ddmStructureVersion);

		boolean isNew = ddmStructureVersion.isNew();

		DDMStructureVersionModelImpl ddmStructureVersionModelImpl = (DDMStructureVersionModelImpl)ddmStructureVersion;

		Session session = null;

		try {
			session = openSession();

			if (ddmStructureVersion.isNew()) {
				session.save(ddmStructureVersion);

				ddmStructureVersion.setNew(false);
			}
			else {
				session.merge(ddmStructureVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (isNew || !DDMStructureVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}

		else {
			if ((ddmStructureVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmStructureVersionModelImpl.getOriginalStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);

				args = new Object[] {
						ddmStructureVersionModelImpl.getStructureId()
					};

				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_BY_STRUCTUREID,
					args);
				FinderCacheUtil.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_STRUCTUREID,
					args);
			}
		}

		EntityCacheUtil.putResult(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMStructureVersionImpl.class, ddmStructureVersion.getPrimaryKey(),
			ddmStructureVersion, false);

		clearUniqueFindersCache(ddmStructureVersion);
		cacheUniqueFindersCache(ddmStructureVersion);

		ddmStructureVersion.resetOriginalValues();

		return ddmStructureVersion;
	}

	protected DDMStructureVersion toUnwrappedModel(
		DDMStructureVersion ddmStructureVersion) {
		if (ddmStructureVersion instanceof DDMStructureVersionImpl) {
			return ddmStructureVersion;
		}

		DDMStructureVersionImpl ddmStructureVersionImpl = new DDMStructureVersionImpl();

		ddmStructureVersionImpl.setNew(ddmStructureVersion.isNew());
		ddmStructureVersionImpl.setPrimaryKey(ddmStructureVersion.getPrimaryKey());

		ddmStructureVersionImpl.setStructureVersionId(ddmStructureVersion.getStructureVersionId());
		ddmStructureVersionImpl.setGroupId(ddmStructureVersion.getGroupId());
		ddmStructureVersionImpl.setCompanyId(ddmStructureVersion.getCompanyId());
		ddmStructureVersionImpl.setUserId(ddmStructureVersion.getUserId());
		ddmStructureVersionImpl.setUserName(ddmStructureVersion.getUserName());
		ddmStructureVersionImpl.setCreateDate(ddmStructureVersion.getCreateDate());
		ddmStructureVersionImpl.setStructureId(ddmStructureVersion.getStructureId());
		ddmStructureVersionImpl.setVersion(ddmStructureVersion.getVersion());
		ddmStructureVersionImpl.setName(ddmStructureVersion.getName());
		ddmStructureVersionImpl.setDescription(ddmStructureVersion.getDescription());
		ddmStructureVersionImpl.setDefinition(ddmStructureVersion.getDefinition());
		ddmStructureVersionImpl.setStorageType(ddmStructureVersion.getStorageType());
		ddmStructureVersionImpl.setType(ddmStructureVersion.getType());

		return ddmStructureVersionImpl;
	}

	/**
	 * Returns the d d m structure version with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure version
	 * @return the d d m structure version
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureVersionException if a d d m structure version with the primary key could not be found
	 */
	@Override
	public DDMStructureVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchStructureVersionException {
		DDMStructureVersion ddmStructureVersion = fetchByPrimaryKey(primaryKey);

		if (ddmStructureVersion == null) {
			if (_log.isWarnEnabled()) {
				_log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchStructureVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddmStructureVersion;
	}

	/**
	 * Returns the d d m structure version with the primary key or throws a {@link com.liferay.portlet.dynamicdatamapping.NoSuchStructureVersionException} if it could not be found.
	 *
	 * @param structureVersionId the primary key of the d d m structure version
	 * @return the d d m structure version
	 * @throws com.liferay.portlet.dynamicdatamapping.NoSuchStructureVersionException if a d d m structure version with the primary key could not be found
	 */
	@Override
	public DDMStructureVersion findByPrimaryKey(long structureVersionId)
		throws NoSuchStructureVersionException {
		return findByPrimaryKey((Serializable)structureVersionId);
	}

	/**
	 * Returns the d d m structure version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the d d m structure version
	 * @return the d d m structure version, or <code>null</code> if a d d m structure version with the primary key could not be found
	 */
	@Override
	public DDMStructureVersion fetchByPrimaryKey(Serializable primaryKey) {
		DDMStructureVersion ddmStructureVersion = (DDMStructureVersion)EntityCacheUtil.getResult(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMStructureVersionImpl.class, primaryKey);

		if (ddmStructureVersion == _nullDDMStructureVersion) {
			return null;
		}

		if (ddmStructureVersion == null) {
			Session session = null;

			try {
				session = openSession();

				ddmStructureVersion = (DDMStructureVersion)session.get(DDMStructureVersionImpl.class,
						primaryKey);

				if (ddmStructureVersion != null) {
					cacheResult(ddmStructureVersion);
				}
				else {
					EntityCacheUtil.putResult(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
						DDMStructureVersionImpl.class, primaryKey,
						_nullDDMStructureVersion);
				}
			}
			catch (Exception e) {
				EntityCacheUtil.removeResult(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
					DDMStructureVersionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddmStructureVersion;
	}

	/**
	 * Returns the d d m structure version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param structureVersionId the primary key of the d d m structure version
	 * @return the d d m structure version, or <code>null</code> if a d d m structure version with the primary key could not be found
	 */
	@Override
	public DDMStructureVersion fetchByPrimaryKey(long structureVersionId) {
		return fetchByPrimaryKey((Serializable)structureVersionId);
	}

	@Override
	public Map<Serializable, DDMStructureVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMStructureVersion> map = new HashMap<Serializable, DDMStructureVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMStructureVersion ddmStructureVersion = fetchByPrimaryKey(primaryKey);

			if (ddmStructureVersion != null) {
				map.put(primaryKey, ddmStructureVersion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			DDMStructureVersion ddmStructureVersion = (DDMStructureVersion)EntityCacheUtil.getResult(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
					DDMStructureVersionImpl.class, primaryKey);

			if (ddmStructureVersion == null) {
				if (uncachedPrimaryKeys == null) {
					uncachedPrimaryKeys = new HashSet<Serializable>();
				}

				uncachedPrimaryKeys.add(primaryKey);
			}
			else {
				map.put(primaryKey, ddmStructureVersion);
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDMSTRUCTUREVERSION_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
			query.append(String.valueOf(primaryKey));

			query.append(StringPool.COMMA);
		}

		query.setIndex(query.index() - 1);

		query.append(StringPool.CLOSE_PARENTHESIS);

		String sql = query.toString();

		Session session = null;

		try {
			session = openSession();

			Query q = session.createQuery(sql);

			for (DDMStructureVersion ddmStructureVersion : (List<DDMStructureVersion>)q.list()) {
				map.put(ddmStructureVersion.getPrimaryKeyObj(),
					ddmStructureVersion);

				cacheResult(ddmStructureVersion);

				uncachedPrimaryKeys.remove(ddmStructureVersion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				EntityCacheUtil.putResult(DDMStructureVersionModelImpl.ENTITY_CACHE_ENABLED,
					DDMStructureVersionImpl.class, primaryKey,
					_nullDDMStructureVersion);
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
	 * Returns all the d d m structure versions.
	 *
	 * @return the d d m structure versions
	 */
	@Override
	public List<DDMStructureVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the d d m structure versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure versions
	 * @param end the upper bound of the range of d d m structure versions (not inclusive)
	 * @return the range of d d m structure versions
	 */
	@Override
	public List<DDMStructureVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the d d m structure versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link com.liferay.portlet.dynamicdatamapping.model.impl.DDMStructureVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of d d m structure versions
	 * @param end the upper bound of the range of d d m structure versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of d d m structure versions
	 */
	@Override
	public List<DDMStructureVersion> findAll(int start, int end,
		OrderByComparator<DDMStructureVersion> orderByComparator) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL;
			finderArgs = FINDER_ARGS_EMPTY;
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_ALL;
			finderArgs = new Object[] { start, end, orderByComparator };
		}

		List<DDMStructureVersion> list = (List<DDMStructureVersion>)FinderCacheUtil.getResult(finderPath,
				finderArgs, this);

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 3));

				query.append(_SQL_SELECT_DDMSTRUCTUREVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMSTRUCTUREVERSION;

				if (pagination) {
					sql = sql.concat(DDMStructureVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMStructureVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMStructureVersion>)QueryUtil.list(q,
							getDialect(), start, end);
				}

				cacheResult(list);

				FinderCacheUtil.putResult(finderPath, finderArgs, list);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(finderPath, finderArgs);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the d d m structure versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMStructureVersion ddmStructureVersion : findAll()) {
			remove(ddmStructureVersion);
		}
	}

	/**
	 * Returns the number of d d m structure versions.
	 *
	 * @return the number of d d m structure versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMSTRUCTUREVERSION);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(FINDER_PATH_COUNT_ALL,
					FINDER_ARGS_EMPTY);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	@Override
	protected Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	/**
	 * Initializes the d d m structure version persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		EntityCacheUtil.removeCache(DDMStructureVersionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_DDMSTRUCTUREVERSION = "SELECT ddmStructureVersion FROM DDMStructureVersion ddmStructureVersion";
	private static final String _SQL_SELECT_DDMSTRUCTUREVERSION_WHERE_PKS_IN = "SELECT ddmStructureVersion FROM DDMStructureVersion ddmStructureVersion WHERE structureVersionId IN (";
	private static final String _SQL_SELECT_DDMSTRUCTUREVERSION_WHERE = "SELECT ddmStructureVersion FROM DDMStructureVersion ddmStructureVersion WHERE ";
	private static final String _SQL_COUNT_DDMSTRUCTUREVERSION = "SELECT COUNT(ddmStructureVersion) FROM DDMStructureVersion ddmStructureVersion";
	private static final String _SQL_COUNT_DDMSTRUCTUREVERSION_WHERE = "SELECT COUNT(ddmStructureVersion) FROM DDMStructureVersion ddmStructureVersion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmStructureVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMStructureVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMStructureVersion exists with the key {";
	private static final boolean _HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE = com.liferay.portal.util.PropsValues.HIBERNATE_CACHE_USE_SECOND_LEVEL_CACHE;
	private static final Log _log = LogFactoryUtil.getLog(DDMStructureVersionPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"type"
			});
	private static final DDMStructureVersion _nullDDMStructureVersion = new DDMStructureVersionImpl() {
			@Override
			public Object clone() {
				return this;
			}

			@Override
			public CacheModel<DDMStructureVersion> toCacheModel() {
				return _nullDDMStructureVersionCacheModel;
			}
		};

	private static final CacheModel<DDMStructureVersion> _nullDDMStructureVersionCacheModel =
		new CacheModel<DDMStructureVersion>() {
			@Override
			public DDMStructureVersion toEntityModel() {
				return _nullDDMStructureVersion;
			}
		};
}