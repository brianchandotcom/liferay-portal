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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;
import com.liferay.portal.tools.service.builder.test.exception.NoSuchVersionedEntryContentVersionException;
import com.liferay.portal.tools.service.builder.test.model.VersionedEntryContentVersion;
import com.liferay.portal.tools.service.builder.test.model.impl.VersionedEntryContentVersionImpl;
import com.liferay.portal.tools.service.builder.test.model.impl.VersionedEntryContentVersionModelImpl;
import com.liferay.portal.tools.service.builder.test.service.persistence.VersionedEntryContentVersionPersistence;

import java.io.Serializable;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the versioned entry content version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see VersionedEntryContentVersionPersistence
 * @see com.liferay.portal.tools.service.builder.test.service.persistence.VersionedEntryContentVersionUtil
 * @generated
 */
@ProviderType
public class VersionedEntryContentVersionPersistenceImpl
	extends BasePersistenceImpl<VersionedEntryContentVersion>
	implements VersionedEntryContentVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link VersionedEntryContentVersionUtil} to access the versioned entry content version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = VersionedEntryContentVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_VERSIONEDENTRYCONTENTID =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByVersionedEntryContentId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYCONTENTID =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByVersionedEntryContentId",
			new String[] { Long.class.getName() },
			VersionedEntryContentVersionModelImpl.VERSIONEDENTRYCONTENTID_COLUMN_BITMASK |
			VersionedEntryContentVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_VERSIONEDENTRYCONTENTID = new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByVersionedEntryContentId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the versioned entry content versions where versionedEntryContentId = &#63;.
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @return the matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryContentId(
		long versionedEntryContentId) {
		return findByVersionedEntryContentId(versionedEntryContentId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the versioned entry content versions where versionedEntryContentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @return the range of matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryContentId(
		long versionedEntryContentId, int start, int end) {
		return findByVersionedEntryContentId(versionedEntryContentId, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the versioned entry content versions where versionedEntryContentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryContentId(
		long versionedEntryContentId, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return findByVersionedEntryContentId(versionedEntryContentId, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the versioned entry content versions where versionedEntryContentId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryContentId(
		long versionedEntryContentId, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYCONTENTID;
			finderArgs = new Object[] { versionedEntryContentId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_VERSIONEDENTRYCONTENTID;
			finderArgs = new Object[] {
					versionedEntryContentId,
					
					start, end, orderByComparator
				};
		}

		List<VersionedEntryContentVersion> list = null;

		if (retrieveFromCache) {
			list = (List<VersionedEntryContentVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (VersionedEntryContentVersion versionedEntryContentVersion : list) {
					if ((versionedEntryContentId != versionedEntryContentVersion.getVersionedEntryContentId())) {
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

			query.append(_SQL_SELECT_VERSIONEDENTRYCONTENTVERSION_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYCONTENTID_VERSIONEDENTRYCONTENTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(VersionedEntryContentVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryContentId);

				if (!pagination) {
					list = (List<VersionedEntryContentVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<VersionedEntryContentVersion>)QueryUtil.list(q,
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
	 * Returns the first versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion findByVersionedEntryContentId_First(
		long versionedEntryContentId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = fetchByVersionedEntryContentId_First(versionedEntryContentId,
				orderByComparator);

		if (versionedEntryContentVersion != null) {
			return versionedEntryContentVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("versionedEntryContentId=");
		msg.append(versionedEntryContentId);

		msg.append("}");

		throw new NoSuchVersionedEntryContentVersionException(msg.toString());
	}

	/**
	 * Returns the first versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByVersionedEntryContentId_First(
		long versionedEntryContentId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		List<VersionedEntryContentVersion> list = findByVersionedEntryContentId(versionedEntryContentId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion findByVersionedEntryContentId_Last(
		long versionedEntryContentId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = fetchByVersionedEntryContentId_Last(versionedEntryContentId,
				orderByComparator);

		if (versionedEntryContentVersion != null) {
			return versionedEntryContentVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("versionedEntryContentId=");
		msg.append(versionedEntryContentId);

		msg.append("}");

		throw new NoSuchVersionedEntryContentVersionException(msg.toString());
	}

	/**
	 * Returns the last versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByVersionedEntryContentId_Last(
		long versionedEntryContentId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		int count = countByVersionedEntryContentId(versionedEntryContentId);

		if (count == 0) {
			return null;
		}

		List<VersionedEntryContentVersion> list = findByVersionedEntryContentId(versionedEntryContentId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the versioned entry content versions before and after the current versioned entry content version in the ordered set where versionedEntryContentId = &#63;.
	 *
	 * @param versionedEntryContentVersionId the primary key of the current versioned entry content version
	 * @param versionedEntryContentId the versioned entry content ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	 */
	@Override
	public VersionedEntryContentVersion[] findByVersionedEntryContentId_PrevAndNext(
		long versionedEntryContentVersionId, long versionedEntryContentId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = findByPrimaryKey(versionedEntryContentVersionId);

		Session session = null;

		try {
			session = openSession();

			VersionedEntryContentVersion[] array = new VersionedEntryContentVersionImpl[3];

			array[0] = getByVersionedEntryContentId_PrevAndNext(session,
					versionedEntryContentVersion, versionedEntryContentId,
					orderByComparator, true);

			array[1] = versionedEntryContentVersion;

			array[2] = getByVersionedEntryContentId_PrevAndNext(session,
					versionedEntryContentVersion, versionedEntryContentId,
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

	protected VersionedEntryContentVersion getByVersionedEntryContentId_PrevAndNext(
		Session session,
		VersionedEntryContentVersion versionedEntryContentVersion,
		long versionedEntryContentId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_VERSIONEDENTRYCONTENTVERSION_WHERE);

		query.append(_FINDER_COLUMN_VERSIONEDENTRYCONTENTID_VERSIONEDENTRYCONTENTID_2);

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
			query.append(VersionedEntryContentVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(versionedEntryContentId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(versionedEntryContentVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<VersionedEntryContentVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the versioned entry content versions where versionedEntryContentId = &#63; from the database.
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 */
	@Override
	public void removeByVersionedEntryContentId(long versionedEntryContentId) {
		for (VersionedEntryContentVersion versionedEntryContentVersion : findByVersionedEntryContentId(
				versionedEntryContentId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(versionedEntryContentVersion);
		}
	}

	/**
	 * Returns the number of versioned entry content versions where versionedEntryContentId = &#63;.
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @return the number of matching versioned entry content versions
	 */
	@Override
	public int countByVersionedEntryContentId(long versionedEntryContentId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_VERSIONEDENTRYCONTENTID;

		Object[] finderArgs = new Object[] { versionedEntryContentId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_VERSIONEDENTRYCONTENTVERSION_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYCONTENTID_VERSIONEDENTRYCONTENTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryContentId);

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

	private static final String _FINDER_COLUMN_VERSIONEDENTRYCONTENTID_VERSIONEDENTRYCONTENTID_2 =
		"versionedEntryContentVersion.versionedEntryContentId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_VERSIONEDENTRYCONTENTID_VERSION =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByVersionedEntryContentId_Version",
			new String[] { Long.class.getName(), Integer.class.getName() },
			VersionedEntryContentVersionModelImpl.VERSIONEDENTRYCONTENTID_COLUMN_BITMASK |
			VersionedEntryContentVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_VERSIONEDENTRYCONTENTID_VERSION =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByVersionedEntryContentId_Version",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns the versioned entry content version where versionedEntryContentId = &#63; and version = &#63; or throws a {@link NoSuchVersionedEntryContentVersionException} if it could not be found.
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @param version the version
	 * @return the matching versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion findByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = fetchByVersionedEntryContentId_Version(versionedEntryContentId,
				version);

		if (versionedEntryContentVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("versionedEntryContentId=");
			msg.append(versionedEntryContentId);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchVersionedEntryContentVersionException(msg.toString());
		}

		return versionedEntryContentVersion;
	}

	/**
	 * Returns the versioned entry content version where versionedEntryContentId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @param version the version
	 * @return the matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version) {
		return fetchByVersionedEntryContentId_Version(versionedEntryContentId,
			version, true);
	}

	/**
	 * Returns the versioned entry content version where versionedEntryContentId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { versionedEntryContentId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYCONTENTID_VERSION,
					finderArgs, this);
		}

		if (result instanceof VersionedEntryContentVersion) {
			VersionedEntryContentVersion versionedEntryContentVersion = (VersionedEntryContentVersion)result;

			if ((versionedEntryContentId != versionedEntryContentVersion.getVersionedEntryContentId()) ||
					(version != versionedEntryContentVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_VERSIONEDENTRYCONTENTVERSION_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYCONTENTID_VERSION_VERSIONEDENTRYCONTENTID_2);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYCONTENTID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryContentId);

				qPos.add(version);

				List<VersionedEntryContentVersion> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYCONTENTID_VERSION,
						finderArgs, list);
				}
				else {
					VersionedEntryContentVersion versionedEntryContentVersion = list.get(0);

					result = versionedEntryContentVersion;

					cacheResult(versionedEntryContentVersion);

					if ((versionedEntryContentVersion.getVersionedEntryContentId() != versionedEntryContentId) ||
							(versionedEntryContentVersion.getVersion() != version)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYCONTENTID_VERSION,
							finderArgs, versionedEntryContentVersion);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYCONTENTID_VERSION,
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
			return (VersionedEntryContentVersion)result;
		}
	}

	/**
	 * Removes the versioned entry content version where versionedEntryContentId = &#63; and version = &#63; from the database.
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @param version the version
	 * @return the versioned entry content version that was removed
	 */
	@Override
	public VersionedEntryContentVersion removeByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = findByVersionedEntryContentId_Version(versionedEntryContentId,
				version);

		return remove(versionedEntryContentVersion);
	}

	/**
	 * Returns the number of versioned entry content versions where versionedEntryContentId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryContentId the versioned entry content ID
	 * @param version the version
	 * @return the number of matching versioned entry content versions
	 */
	@Override
	public int countByVersionedEntryContentId_Version(
		long versionedEntryContentId, int version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_VERSIONEDENTRYCONTENTID_VERSION;

		Object[] finderArgs = new Object[] { versionedEntryContentId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_VERSIONEDENTRYCONTENTVERSION_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYCONTENTID_VERSION_VERSIONEDENTRYCONTENTID_2);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYCONTENTID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryContentId);

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

	private static final String _FINDER_COLUMN_VERSIONEDENTRYCONTENTID_VERSION_VERSIONEDENTRYCONTENTID_2 =
		"versionedEntryContentVersion.versionedEntryContentId = ? AND ";
	private static final String _FINDER_COLUMN_VERSIONEDENTRYCONTENTID_VERSION_VERSION_2 =
		"versionedEntryContentVersion.version = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_VERSIONEDENTRYID =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByVersionedEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByVersionedEntryId", new String[] { Long.class.getName() },
			VersionedEntryContentVersionModelImpl.VERSIONEDENTRYID_COLUMN_BITMASK |
			VersionedEntryContentVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_VERSIONEDENTRYID = new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByVersionedEntryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the versioned entry content versions where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @return the matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryId(
		long versionedEntryId) {
		return findByVersionedEntryId(versionedEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the versioned entry content versions where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @return the range of matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end) {
		return findByVersionedEntryId(versionedEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return findByVersionedEntryId(versionedEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryId(
		long versionedEntryId, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID;
			finderArgs = new Object[] { versionedEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_VERSIONEDENTRYID;
			finderArgs = new Object[] {
					versionedEntryId,
					
					start, end, orderByComparator
				};
		}

		List<VersionedEntryContentVersion> list = null;

		if (retrieveFromCache) {
			list = (List<VersionedEntryContentVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (VersionedEntryContentVersion versionedEntryContentVersion : list) {
					if ((versionedEntryId != versionedEntryContentVersion.getVersionedEntryId())) {
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

			query.append(_SQL_SELECT_VERSIONEDENTRYCONTENTVERSION_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_VERSIONEDENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(VersionedEntryContentVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryId);

				if (!pagination) {
					list = (List<VersionedEntryContentVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<VersionedEntryContentVersion>)QueryUtil.list(q,
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
	 * Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion findByVersionedEntryId_First(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = fetchByVersionedEntryId_First(versionedEntryId,
				orderByComparator);

		if (versionedEntryContentVersion != null) {
			return versionedEntryContentVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("versionedEntryId=");
		msg.append(versionedEntryId);

		msg.append("}");

		throw new NoSuchVersionedEntryContentVersionException(msg.toString());
	}

	/**
	 * Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByVersionedEntryId_First(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		List<VersionedEntryContentVersion> list = findByVersionedEntryId(versionedEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion findByVersionedEntryId_Last(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = fetchByVersionedEntryId_Last(versionedEntryId,
				orderByComparator);

		if (versionedEntryContentVersion != null) {
			return versionedEntryContentVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("versionedEntryId=");
		msg.append(versionedEntryId);

		msg.append("}");

		throw new NoSuchVersionedEntryContentVersionException(msg.toString());
	}

	/**
	 * Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByVersionedEntryId_Last(
		long versionedEntryId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		int count = countByVersionedEntryId(versionedEntryId);

		if (count == 0) {
			return null;
		}

		List<VersionedEntryContentVersion> list = findByVersionedEntryId(versionedEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the versioned entry content versions before and after the current versioned entry content version in the ordered set where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryContentVersionId the primary key of the current versioned entry content version
	 * @param versionedEntryId the versioned entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	 */
	@Override
	public VersionedEntryContentVersion[] findByVersionedEntryId_PrevAndNext(
		long versionedEntryContentVersionId, long versionedEntryId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = findByPrimaryKey(versionedEntryContentVersionId);

		Session session = null;

		try {
			session = openSession();

			VersionedEntryContentVersion[] array = new VersionedEntryContentVersionImpl[3];

			array[0] = getByVersionedEntryId_PrevAndNext(session,
					versionedEntryContentVersion, versionedEntryId,
					orderByComparator, true);

			array[1] = versionedEntryContentVersion;

			array[2] = getByVersionedEntryId_PrevAndNext(session,
					versionedEntryContentVersion, versionedEntryId,
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

	protected VersionedEntryContentVersion getByVersionedEntryId_PrevAndNext(
		Session session,
		VersionedEntryContentVersion versionedEntryContentVersion,
		long versionedEntryId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_VERSIONEDENTRYCONTENTVERSION_WHERE);

		query.append(_FINDER_COLUMN_VERSIONEDENTRYID_VERSIONEDENTRYID_2);

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
			query.append(VersionedEntryContentVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(versionedEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(versionedEntryContentVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<VersionedEntryContentVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the versioned entry content versions where versionedEntryId = &#63; from the database.
	 *
	 * @param versionedEntryId the versioned entry ID
	 */
	@Override
	public void removeByVersionedEntryId(long versionedEntryId) {
		for (VersionedEntryContentVersion versionedEntryContentVersion : findByVersionedEntryId(
				versionedEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(versionedEntryContentVersion);
		}
	}

	/**
	 * Returns the number of versioned entry content versions where versionedEntryId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @return the number of matching versioned entry content versions
	 */
	@Override
	public int countByVersionedEntryId(long versionedEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_VERSIONEDENTRYID;

		Object[] finderArgs = new Object[] { versionedEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_VERSIONEDENTRYCONTENTVERSION_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_VERSIONEDENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryId);

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

	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_VERSIONEDENTRYID_2 =
		"versionedEntryContentVersion.versionedEntryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_VERSIONEDENTRYID_VERSION =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByVersionedEntryId_Version",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID_VERSION =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByVersionedEntryId_Version",
			new String[] { Long.class.getName(), Integer.class.getName() },
			VersionedEntryContentVersionModelImpl.VERSIONEDENTRYID_COLUMN_BITMASK |
			VersionedEntryContentVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_VERSION =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByVersionedEntryId_Version",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryId_Version(
		long versionedEntryId, int version) {
		return findByVersionedEntryId_Version(versionedEntryId, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @return the range of matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryId_Version(
		long versionedEntryId, int version, int start, int end) {
		return findByVersionedEntryId_Version(versionedEntryId, version, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryId_Version(
		long versionedEntryId, int version, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return findByVersionedEntryId_Version(versionedEntryId, version, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryId_Version(
		long versionedEntryId, int version, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID_VERSION;
			finderArgs = new Object[] { versionedEntryId, version };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_VERSIONEDENTRYID_VERSION;
			finderArgs = new Object[] {
					versionedEntryId, version,
					
					start, end, orderByComparator
				};
		}

		List<VersionedEntryContentVersion> list = null;

		if (retrieveFromCache) {
			list = (List<VersionedEntryContentVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (VersionedEntryContentVersion versionedEntryContentVersion : list) {
					if ((versionedEntryId != versionedEntryContentVersion.getVersionedEntryId()) ||
							(version != versionedEntryContentVersion.getVersion())) {
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

			query.append(_SQL_SELECT_VERSIONEDENTRYCONTENTVERSION_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_VERSION_VERSIONEDENTRYID_2);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(VersionedEntryContentVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryId);

				qPos.add(version);

				if (!pagination) {
					list = (List<VersionedEntryContentVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<VersionedEntryContentVersion>)QueryUtil.list(q,
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
	 * Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion findByVersionedEntryId_Version_First(
		long versionedEntryId, int version,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = fetchByVersionedEntryId_Version_First(versionedEntryId,
				version, orderByComparator);

		if (versionedEntryContentVersion != null) {
			return versionedEntryContentVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("versionedEntryId=");
		msg.append(versionedEntryId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchVersionedEntryContentVersionException(msg.toString());
	}

	/**
	 * Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByVersionedEntryId_Version_First(
		long versionedEntryId, int version,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		List<VersionedEntryContentVersion> list = findByVersionedEntryId_Version(versionedEntryId,
				version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion findByVersionedEntryId_Version_Last(
		long versionedEntryId, int version,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = fetchByVersionedEntryId_Version_Last(versionedEntryId,
				version, orderByComparator);

		if (versionedEntryContentVersion != null) {
			return versionedEntryContentVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("versionedEntryId=");
		msg.append(versionedEntryId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchVersionedEntryContentVersionException(msg.toString());
	}

	/**
	 * Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByVersionedEntryId_Version_Last(
		long versionedEntryId, int version,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		int count = countByVersionedEntryId_Version(versionedEntryId, version);

		if (count == 0) {
			return null;
		}

		List<VersionedEntryContentVersion> list = findByVersionedEntryId_Version(versionedEntryId,
				version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the versioned entry content versions before and after the current versioned entry content version in the ordered set where versionedEntryId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryContentVersionId the primary key of the current versioned entry content version
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	 */
	@Override
	public VersionedEntryContentVersion[] findByVersionedEntryId_Version_PrevAndNext(
		long versionedEntryContentVersionId, long versionedEntryId,
		int version,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = findByPrimaryKey(versionedEntryContentVersionId);

		Session session = null;

		try {
			session = openSession();

			VersionedEntryContentVersion[] array = new VersionedEntryContentVersionImpl[3];

			array[0] = getByVersionedEntryId_Version_PrevAndNext(session,
					versionedEntryContentVersion, versionedEntryId, version,
					orderByComparator, true);

			array[1] = versionedEntryContentVersion;

			array[2] = getByVersionedEntryId_Version_PrevAndNext(session,
					versionedEntryContentVersion, versionedEntryId, version,
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

	protected VersionedEntryContentVersion getByVersionedEntryId_Version_PrevAndNext(
		Session session,
		VersionedEntryContentVersion versionedEntryContentVersion,
		long versionedEntryId, int version,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_VERSIONEDENTRYCONTENTVERSION_WHERE);

		query.append(_FINDER_COLUMN_VERSIONEDENTRYID_VERSION_VERSIONEDENTRYID_2);

		query.append(_FINDER_COLUMN_VERSIONEDENTRYID_VERSION_VERSION_2);

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
			query.append(VersionedEntryContentVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(versionedEntryId);

		qPos.add(version);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(versionedEntryContentVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<VersionedEntryContentVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the versioned entry content versions where versionedEntryId = &#63; and version = &#63; from the database.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 */
	@Override
	public void removeByVersionedEntryId_Version(long versionedEntryId,
		int version) {
		for (VersionedEntryContentVersion versionedEntryContentVersion : findByVersionedEntryId_Version(
				versionedEntryId, version, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(versionedEntryContentVersion);
		}
	}

	/**
	 * Returns the number of versioned entry content versions where versionedEntryId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param version the version
	 * @return the number of matching versioned entry content versions
	 */
	@Override
	public int countByVersionedEntryId_Version(long versionedEntryId,
		int version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_VERSION;

		Object[] finderArgs = new Object[] { versionedEntryId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_VERSIONEDENTRYCONTENTVERSION_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_VERSION_VERSIONEDENTRYID_2);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryId);

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

	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_VERSION_VERSIONEDENTRYID_2 =
		"versionedEntryContentVersion.versionedEntryId = ? AND ";
	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_VERSION_VERSION_2 =
		"versionedEntryContentVersion.version = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_VERSIONEDENTRYID_LANGUAGEID =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByVersionedEntryId_LanguageId",
			new String[] {
				Long.class.getName(), String.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID_LANGUAGEID =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByVersionedEntryId_LanguageId",
			new String[] { Long.class.getName(), String.class.getName() },
			VersionedEntryContentVersionModelImpl.VERSIONEDENTRYID_COLUMN_BITMASK |
			VersionedEntryContentVersionModelImpl.LANGUAGEID_COLUMN_BITMASK |
			VersionedEntryContentVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByVersionedEntryId_LanguageId",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @return the matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryId_LanguageId(
		long versionedEntryId, String languageId) {
		return findByVersionedEntryId_LanguageId(versionedEntryId, languageId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @return the range of matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryId_LanguageId(
		long versionedEntryId, String languageId, int start, int end) {
		return findByVersionedEntryId_LanguageId(versionedEntryId, languageId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryId_LanguageId(
		long versionedEntryId, String languageId, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return findByVersionedEntryId_LanguageId(versionedEntryId, languageId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findByVersionedEntryId_LanguageId(
		long versionedEntryId, String languageId, int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID_LANGUAGEID;
			finderArgs = new Object[] { versionedEntryId, languageId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_VERSIONEDENTRYID_LANGUAGEID;
			finderArgs = new Object[] {
					versionedEntryId, languageId,
					
					start, end, orderByComparator
				};
		}

		List<VersionedEntryContentVersion> list = null;

		if (retrieveFromCache) {
			list = (List<VersionedEntryContentVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (VersionedEntryContentVersion versionedEntryContentVersion : list) {
					if ((versionedEntryId != versionedEntryContentVersion.getVersionedEntryId()) ||
							!Objects.equals(languageId,
								versionedEntryContentVersion.getLanguageId())) {
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

			query.append(_SQL_SELECT_VERSIONEDENTRYCONTENTVERSION_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSIONEDENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(VersionedEntryContentVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				if (!pagination) {
					list = (List<VersionedEntryContentVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<VersionedEntryContentVersion>)QueryUtil.list(q,
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
	 * Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion findByVersionedEntryId_LanguageId_First(
		long versionedEntryId, String languageId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = fetchByVersionedEntryId_LanguageId_First(versionedEntryId,
				languageId, orderByComparator);

		if (versionedEntryContentVersion != null) {
			return versionedEntryContentVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("versionedEntryId=");
		msg.append(versionedEntryId);

		msg.append(", languageId=");
		msg.append(languageId);

		msg.append("}");

		throw new NoSuchVersionedEntryContentVersionException(msg.toString());
	}

	/**
	 * Returns the first versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByVersionedEntryId_LanguageId_First(
		long versionedEntryId, String languageId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		List<VersionedEntryContentVersion> list = findByVersionedEntryId_LanguageId(versionedEntryId,
				languageId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion findByVersionedEntryId_LanguageId_Last(
		long versionedEntryId, String languageId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = fetchByVersionedEntryId_LanguageId_Last(versionedEntryId,
				languageId, orderByComparator);

		if (versionedEntryContentVersion != null) {
			return versionedEntryContentVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("versionedEntryId=");
		msg.append(versionedEntryId);

		msg.append(", languageId=");
		msg.append(languageId);

		msg.append("}");

		throw new NoSuchVersionedEntryContentVersionException(msg.toString());
	}

	/**
	 * Returns the last versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByVersionedEntryId_LanguageId_Last(
		long versionedEntryId, String languageId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		int count = countByVersionedEntryId_LanguageId(versionedEntryId,
				languageId);

		if (count == 0) {
			return null;
		}

		List<VersionedEntryContentVersion> list = findByVersionedEntryId_LanguageId(versionedEntryId,
				languageId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the versioned entry content versions before and after the current versioned entry content version in the ordered set where versionedEntryId = &#63; and languageId = &#63;.
	 *
	 * @param versionedEntryContentVersionId the primary key of the current versioned entry content version
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	 */
	@Override
	public VersionedEntryContentVersion[] findByVersionedEntryId_LanguageId_PrevAndNext(
		long versionedEntryContentVersionId, long versionedEntryId,
		String languageId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = findByPrimaryKey(versionedEntryContentVersionId);

		Session session = null;

		try {
			session = openSession();

			VersionedEntryContentVersion[] array = new VersionedEntryContentVersionImpl[3];

			array[0] = getByVersionedEntryId_LanguageId_PrevAndNext(session,
					versionedEntryContentVersion, versionedEntryId, languageId,
					orderByComparator, true);

			array[1] = versionedEntryContentVersion;

			array[2] = getByVersionedEntryId_LanguageId_PrevAndNext(session,
					versionedEntryContentVersion, versionedEntryId, languageId,
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

	protected VersionedEntryContentVersion getByVersionedEntryId_LanguageId_PrevAndNext(
		Session session,
		VersionedEntryContentVersion versionedEntryContentVersion,
		long versionedEntryId, String languageId,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_VERSIONEDENTRYCONTENTVERSION_WHERE);

		query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSIONEDENTRYID_2);

		boolean bindLanguageId = false;

		if (languageId == null) {
			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_1);
		}
		else if (languageId.equals("")) {
			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_3);
		}
		else {
			bindLanguageId = true;

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_2);
		}

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
			query.append(VersionedEntryContentVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(versionedEntryId);

		if (bindLanguageId) {
			qPos.add(languageId);
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(versionedEntryContentVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<VersionedEntryContentVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the versioned entry content versions where versionedEntryId = &#63; and languageId = &#63; from the database.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 */
	@Override
	public void removeByVersionedEntryId_LanguageId(long versionedEntryId,
		String languageId) {
		for (VersionedEntryContentVersion versionedEntryContentVersion : findByVersionedEntryId_LanguageId(
				versionedEntryId, languageId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(versionedEntryContentVersion);
		}
	}

	/**
	 * Returns the number of versioned entry content versions where versionedEntryId = &#63; and languageId = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @return the number of matching versioned entry content versions
	 */
	@Override
	public int countByVersionedEntryId_LanguageId(long versionedEntryId,
		String languageId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID;

		Object[] finderArgs = new Object[] { versionedEntryId, languageId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_VERSIONEDENTRYCONTENTVERSION_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSIONEDENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
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

	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSIONEDENTRYID_2 =
		"versionedEntryContentVersion.versionedEntryId = ? AND ";
	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_1 =
		"versionedEntryContentVersion.languageId IS NULL";
	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_2 =
		"versionedEntryContentVersion.languageId = ?";
	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_LANGUAGEID_3 =
		"(versionedEntryContentVersion.languageId IS NULL OR versionedEntryContentVersion.languageId = '')";
	public static final FinderPath FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByVersionedEntryId_LanguageId_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			VersionedEntryContentVersionModelImpl.VERSIONEDENTRYID_COLUMN_BITMASK |
			VersionedEntryContentVersionModelImpl.LANGUAGEID_COLUMN_BITMASK |
			VersionedEntryContentVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION =
		new FinderPath(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByVersionedEntryId_LanguageId_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

	/**
	 * Returns the versioned entry content version where versionedEntryId = &#63; and languageId = &#63; and version = &#63; or throws a {@link NoSuchVersionedEntryContentVersionException} if it could not be found.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the matching versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion findByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, String languageId, int version)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = fetchByVersionedEntryId_LanguageId_Version(versionedEntryId,
				languageId, version);

		if (versionedEntryContentVersion == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("versionedEntryId=");
			msg.append(versionedEntryId);

			msg.append(", languageId=");
			msg.append(languageId);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchVersionedEntryContentVersionException(msg.toString());
		}

		return versionedEntryContentVersion;
	}

	/**
	 * Returns the versioned entry content version where versionedEntryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, String languageId, int version) {
		return fetchByVersionedEntryId_LanguageId_Version(versionedEntryId,
			languageId, version, true);
	}

	/**
	 * Returns the versioned entry content version where versionedEntryId = &#63; and languageId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching versioned entry content version, or <code>null</code> if a matching versioned entry content version could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, String languageId, int version,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { versionedEntryId, languageId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION,
					finderArgs, this);
		}

		if (result instanceof VersionedEntryContentVersion) {
			VersionedEntryContentVersion versionedEntryContentVersion = (VersionedEntryContentVersion)result;

			if ((versionedEntryId != versionedEntryContentVersion.getVersionedEntryId()) ||
					!Objects.equals(languageId,
						versionedEntryContentVersion.getLanguageId()) ||
					(version != versionedEntryContentVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_VERSIONEDENTRYCONTENTVERSION_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_VERSIONEDENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_LANGUAGEID_2);
			}

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

				qPos.add(version);

				List<VersionedEntryContentVersion> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION,
						finderArgs, list);
				}
				else {
					VersionedEntryContentVersion versionedEntryContentVersion = list.get(0);

					result = versionedEntryContentVersion;

					cacheResult(versionedEntryContentVersion);

					if ((versionedEntryContentVersion.getVersionedEntryId() != versionedEntryId) ||
							(versionedEntryContentVersion.getLanguageId() == null) ||
							!versionedEntryContentVersion.getLanguageId()
															 .equals(languageId) ||
							(versionedEntryContentVersion.getVersion() != version)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION,
							finderArgs, versionedEntryContentVersion);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION,
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
			return (VersionedEntryContentVersion)result;
		}
	}

	/**
	 * Removes the versioned entry content version where versionedEntryId = &#63; and languageId = &#63; and version = &#63; from the database.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the versioned entry content version that was removed
	 */
	@Override
	public VersionedEntryContentVersion removeByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, String languageId, int version)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = findByVersionedEntryId_LanguageId_Version(versionedEntryId,
				languageId, version);

		return remove(versionedEntryContentVersion);
	}

	/**
	 * Returns the number of versioned entry content versions where versionedEntryId = &#63; and languageId = &#63; and version = &#63;.
	 *
	 * @param versionedEntryId the versioned entry ID
	 * @param languageId the language ID
	 * @param version the version
	 * @return the number of matching versioned entry content versions
	 */
	@Override
	public int countByVersionedEntryId_LanguageId_Version(
		long versionedEntryId, String languageId, int version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION;

		Object[] finderArgs = new Object[] { versionedEntryId, languageId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_VERSIONEDENTRYCONTENTVERSION_WHERE);

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_VERSIONEDENTRYID_2);

			boolean bindLanguageId = false;

			if (languageId == null) {
				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_LANGUAGEID_1);
			}
			else if (languageId.equals("")) {
				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_LANGUAGEID_3);
			}
			else {
				bindLanguageId = true;

				query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_LANGUAGEID_2);
			}

			query.append(_FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(versionedEntryId);

				if (bindLanguageId) {
					qPos.add(languageId);
				}

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

	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_VERSIONEDENTRYID_2 =
		"versionedEntryContentVersion.versionedEntryId = ? AND ";
	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_LANGUAGEID_1 =
		"versionedEntryContentVersion.languageId IS NULL AND ";
	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_LANGUAGEID_2 =
		"versionedEntryContentVersion.languageId = ? AND ";
	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_LANGUAGEID_3 =
		"(versionedEntryContentVersion.languageId IS NULL OR versionedEntryContentVersion.languageId = '') AND ";
	private static final String _FINDER_COLUMN_VERSIONEDENTRYID_LANGUAGEID_VERSION_VERSION_2 =
		"versionedEntryContentVersion.version = ?";

	public VersionedEntryContentVersionPersistenceImpl() {
		setModelClass(VersionedEntryContentVersion.class);
	}

	/**
	 * Caches the versioned entry content version in the entity cache if it is enabled.
	 *
	 * @param versionedEntryContentVersion the versioned entry content version
	 */
	@Override
	public void cacheResult(
		VersionedEntryContentVersion versionedEntryContentVersion) {
		entityCache.putResult(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class,
			versionedEntryContentVersion.getPrimaryKey(),
			versionedEntryContentVersion);

		finderCache.putResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYCONTENTID_VERSION,
			new Object[] {
				versionedEntryContentVersion.getVersionedEntryContentId(),
				versionedEntryContentVersion.getVersion()
			}, versionedEntryContentVersion);

		finderCache.putResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION,
			new Object[] {
				versionedEntryContentVersion.getVersionedEntryId(),
				versionedEntryContentVersion.getLanguageId(),
				versionedEntryContentVersion.getVersion()
			}, versionedEntryContentVersion);

		versionedEntryContentVersion.resetOriginalValues();
	}

	/**
	 * Caches the versioned entry content versions in the entity cache if it is enabled.
	 *
	 * @param versionedEntryContentVersions the versioned entry content versions
	 */
	@Override
	public void cacheResult(
		List<VersionedEntryContentVersion> versionedEntryContentVersions) {
		for (VersionedEntryContentVersion versionedEntryContentVersion : versionedEntryContentVersions) {
			if (entityCache.getResult(
						VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
						VersionedEntryContentVersionImpl.class,
						versionedEntryContentVersion.getPrimaryKey()) == null) {
				cacheResult(versionedEntryContentVersion);
			}
			else {
				versionedEntryContentVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all versioned entry content versions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(VersionedEntryContentVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the versioned entry content version.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		VersionedEntryContentVersion versionedEntryContentVersion) {
		entityCache.removeResult(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class,
			versionedEntryContentVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((VersionedEntryContentVersionModelImpl)versionedEntryContentVersion,
			true);
	}

	@Override
	public void clearCache(
		List<VersionedEntryContentVersion> versionedEntryContentVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (VersionedEntryContentVersion versionedEntryContentVersion : versionedEntryContentVersions) {
			entityCache.removeResult(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
				VersionedEntryContentVersionImpl.class,
				versionedEntryContentVersion.getPrimaryKey());

			clearUniqueFindersCache((VersionedEntryContentVersionModelImpl)versionedEntryContentVersion,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		VersionedEntryContentVersionModelImpl versionedEntryContentVersionModelImpl) {
		Object[] args = new Object[] {
				versionedEntryContentVersionModelImpl.getVersionedEntryContentId(),
				versionedEntryContentVersionModelImpl.getVersion()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYCONTENTID_VERSION,
			args, Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYCONTENTID_VERSION,
			args, versionedEntryContentVersionModelImpl, false);

		args = new Object[] {
				versionedEntryContentVersionModelImpl.getVersionedEntryId(),
				versionedEntryContentVersionModelImpl.getLanguageId(),
				versionedEntryContentVersionModelImpl.getVersion()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION,
			args, Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION,
			args, versionedEntryContentVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		VersionedEntryContentVersionModelImpl versionedEntryContentVersionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					versionedEntryContentVersionModelImpl.getVersionedEntryContentId(),
					versionedEntryContentVersionModelImpl.getVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYCONTENTID_VERSION,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYCONTENTID_VERSION,
				args);
		}

		if ((versionedEntryContentVersionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_VERSIONEDENTRYCONTENTID_VERSION.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					versionedEntryContentVersionModelImpl.getOriginalVersionedEntryContentId(),
					versionedEntryContentVersionModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYCONTENTID_VERSION,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYCONTENTID_VERSION,
				args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					versionedEntryContentVersionModelImpl.getVersionedEntryId(),
					versionedEntryContentVersionModelImpl.getLanguageId(),
					versionedEntryContentVersionModelImpl.getVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION,
				args);
		}

		if ((versionedEntryContentVersionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					versionedEntryContentVersionModelImpl.getOriginalVersionedEntryId(),
					versionedEntryContentVersionModelImpl.getOriginalLanguageId(),
					versionedEntryContentVersionModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION,
				args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_VERSIONEDENTRYID_LANGUAGEID_VERSION,
				args);
		}
	}

	/**
	 * Creates a new versioned entry content version with the primary key. Does not add the versioned entry content version to the database.
	 *
	 * @param versionedEntryContentVersionId the primary key for the new versioned entry content version
	 * @return the new versioned entry content version
	 */
	@Override
	public VersionedEntryContentVersion create(
		long versionedEntryContentVersionId) {
		VersionedEntryContentVersion versionedEntryContentVersion = new VersionedEntryContentVersionImpl();

		versionedEntryContentVersion.setNew(true);
		versionedEntryContentVersion.setPrimaryKey(versionedEntryContentVersionId);

		return versionedEntryContentVersion;
	}

	/**
	 * Removes the versioned entry content version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param versionedEntryContentVersionId the primary key of the versioned entry content version
	 * @return the versioned entry content version that was removed
	 * @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	 */
	@Override
	public VersionedEntryContentVersion remove(
		long versionedEntryContentVersionId)
		throws NoSuchVersionedEntryContentVersionException {
		return remove((Serializable)versionedEntryContentVersionId);
	}

	/**
	 * Removes the versioned entry content version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the versioned entry content version
	 * @return the versioned entry content version that was removed
	 * @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	 */
	@Override
	public VersionedEntryContentVersion remove(Serializable primaryKey)
		throws NoSuchVersionedEntryContentVersionException {
		Session session = null;

		try {
			session = openSession();

			VersionedEntryContentVersion versionedEntryContentVersion = (VersionedEntryContentVersion)session.get(VersionedEntryContentVersionImpl.class,
					primaryKey);

			if (versionedEntryContentVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVersionedEntryContentVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(versionedEntryContentVersion);
		}
		catch (NoSuchVersionedEntryContentVersionException nsee) {
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
	protected VersionedEntryContentVersion removeImpl(
		VersionedEntryContentVersion versionedEntryContentVersion) {
		versionedEntryContentVersion = toUnwrappedModel(versionedEntryContentVersion);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(versionedEntryContentVersion)) {
				versionedEntryContentVersion = (VersionedEntryContentVersion)session.get(VersionedEntryContentVersionImpl.class,
						versionedEntryContentVersion.getPrimaryKeyObj());
			}

			if (versionedEntryContentVersion != null) {
				session.delete(versionedEntryContentVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (versionedEntryContentVersion != null) {
			clearCache(versionedEntryContentVersion);
		}

		return versionedEntryContentVersion;
	}

	@Override
	public VersionedEntryContentVersion updateImpl(
		VersionedEntryContentVersion versionedEntryContentVersion) {
		versionedEntryContentVersion = toUnwrappedModel(versionedEntryContentVersion);

		boolean isNew = versionedEntryContentVersion.isNew();

		VersionedEntryContentVersionModelImpl versionedEntryContentVersionModelImpl =
			(VersionedEntryContentVersionModelImpl)versionedEntryContentVersion;

		Session session = null;

		try {
			session = openSession();

			if (versionedEntryContentVersion.isNew()) {
				session.save(versionedEntryContentVersion);

				versionedEntryContentVersion.setNew(false);
			}
			else {
				throw new IllegalArgumentException(
					"VersionedEntryContentVersion is read only, create a new version instead");
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!VersionedEntryContentVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					versionedEntryContentVersionModelImpl.getVersionedEntryContentId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYCONTENTID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYCONTENTID,
				args);

			args = new Object[] {
					versionedEntryContentVersionModelImpl.getVersionedEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID,
				args);

			args = new Object[] {
					versionedEntryContentVersionModelImpl.getVersionedEntryId(),
					versionedEntryContentVersionModelImpl.getVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_VERSION,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID_VERSION,
				args);

			args = new Object[] {
					versionedEntryContentVersionModelImpl.getVersionedEntryId(),
					versionedEntryContentVersionModelImpl.getLanguageId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID_LANGUAGEID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((versionedEntryContentVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYCONTENTID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						versionedEntryContentVersionModelImpl.getOriginalVersionedEntryContentId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYCONTENTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYCONTENTID,
					args);

				args = new Object[] {
						versionedEntryContentVersionModelImpl.getVersionedEntryContentId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYCONTENTID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYCONTENTID,
					args);
			}

			if ((versionedEntryContentVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						versionedEntryContentVersionModelImpl.getOriginalVersionedEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID,
					args);

				args = new Object[] {
						versionedEntryContentVersionModelImpl.getVersionedEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID,
					args);
			}

			if ((versionedEntryContentVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID_VERSION.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						versionedEntryContentVersionModelImpl.getOriginalVersionedEntryId(),
						versionedEntryContentVersionModelImpl.getOriginalVersion()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_VERSION,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID_VERSION,
					args);

				args = new Object[] {
						versionedEntryContentVersionModelImpl.getVersionedEntryId(),
						versionedEntryContentVersionModelImpl.getVersion()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_VERSION,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID_VERSION,
					args);
			}

			if ((versionedEntryContentVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID_LANGUAGEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						versionedEntryContentVersionModelImpl.getOriginalVersionedEntryId(),
						versionedEntryContentVersionModelImpl.getOriginalLanguageId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID_LANGUAGEID,
					args);

				args = new Object[] {
						versionedEntryContentVersionModelImpl.getVersionedEntryId(),
						versionedEntryContentVersionModelImpl.getLanguageId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_VERSIONEDENTRYID_LANGUAGEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_VERSIONEDENTRYID_LANGUAGEID,
					args);
			}
		}

		entityCache.putResult(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
			VersionedEntryContentVersionImpl.class,
			versionedEntryContentVersion.getPrimaryKey(),
			versionedEntryContentVersion, false);

		clearUniqueFindersCache(versionedEntryContentVersionModelImpl, false);
		cacheUniqueFindersCache(versionedEntryContentVersionModelImpl);

		versionedEntryContentVersion.resetOriginalValues();

		return versionedEntryContentVersion;
	}

	protected VersionedEntryContentVersion toUnwrappedModel(
		VersionedEntryContentVersion versionedEntryContentVersion) {
		if (versionedEntryContentVersion instanceof VersionedEntryContentVersionImpl) {
			return versionedEntryContentVersion;
		}

		VersionedEntryContentVersionImpl versionedEntryContentVersionImpl = new VersionedEntryContentVersionImpl();

		versionedEntryContentVersionImpl.setNew(versionedEntryContentVersion.isNew());
		versionedEntryContentVersionImpl.setPrimaryKey(versionedEntryContentVersion.getPrimaryKey());

		versionedEntryContentVersionImpl.setVersionedEntryContentVersionId(versionedEntryContentVersion.getVersionedEntryContentVersionId());
		versionedEntryContentVersionImpl.setVersion(versionedEntryContentVersion.getVersion());
		versionedEntryContentVersionImpl.setVersionedEntryContentId(versionedEntryContentVersion.getVersionedEntryContentId());
		versionedEntryContentVersionImpl.setVersionedEntryId(versionedEntryContentVersion.getVersionedEntryId());
		versionedEntryContentVersionImpl.setLanguageId(versionedEntryContentVersion.getLanguageId());
		versionedEntryContentVersionImpl.setContent(versionedEntryContentVersion.getContent());

		return versionedEntryContentVersionImpl;
	}

	/**
	 * Returns the versioned entry content version with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the versioned entry content version
	 * @return the versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	 */
	@Override
	public VersionedEntryContentVersion findByPrimaryKey(
		Serializable primaryKey)
		throws NoSuchVersionedEntryContentVersionException {
		VersionedEntryContentVersion versionedEntryContentVersion = fetchByPrimaryKey(primaryKey);

		if (versionedEntryContentVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVersionedEntryContentVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return versionedEntryContentVersion;
	}

	/**
	 * Returns the versioned entry content version with the primary key or throws a {@link NoSuchVersionedEntryContentVersionException} if it could not be found.
	 *
	 * @param versionedEntryContentVersionId the primary key of the versioned entry content version
	 * @return the versioned entry content version
	 * @throws NoSuchVersionedEntryContentVersionException if a versioned entry content version with the primary key could not be found
	 */
	@Override
	public VersionedEntryContentVersion findByPrimaryKey(
		long versionedEntryContentVersionId)
		throws NoSuchVersionedEntryContentVersionException {
		return findByPrimaryKey((Serializable)versionedEntryContentVersionId);
	}

	/**
	 * Returns the versioned entry content version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the versioned entry content version
	 * @return the versioned entry content version, or <code>null</code> if a versioned entry content version with the primary key could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
				VersionedEntryContentVersionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		VersionedEntryContentVersion versionedEntryContentVersion = (VersionedEntryContentVersion)serializable;

		if (versionedEntryContentVersion == null) {
			Session session = null;

			try {
				session = openSession();

				versionedEntryContentVersion = (VersionedEntryContentVersion)session.get(VersionedEntryContentVersionImpl.class,
						primaryKey);

				if (versionedEntryContentVersion != null) {
					cacheResult(versionedEntryContentVersion);
				}
				else {
					entityCache.putResult(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
						VersionedEntryContentVersionImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
					VersionedEntryContentVersionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return versionedEntryContentVersion;
	}

	/**
	 * Returns the versioned entry content version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param versionedEntryContentVersionId the primary key of the versioned entry content version
	 * @return the versioned entry content version, or <code>null</code> if a versioned entry content version with the primary key could not be found
	 */
	@Override
	public VersionedEntryContentVersion fetchByPrimaryKey(
		long versionedEntryContentVersionId) {
		return fetchByPrimaryKey((Serializable)versionedEntryContentVersionId);
	}

	@Override
	public Map<Serializable, VersionedEntryContentVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, VersionedEntryContentVersion> map = new HashMap<Serializable, VersionedEntryContentVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			VersionedEntryContentVersion versionedEntryContentVersion = fetchByPrimaryKey(primaryKey);

			if (versionedEntryContentVersion != null) {
				map.put(primaryKey, versionedEntryContentVersion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
					VersionedEntryContentVersionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(VersionedEntryContentVersion)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_VERSIONEDENTRYCONTENTVERSION_WHERE_PKS_IN);

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

			for (VersionedEntryContentVersion versionedEntryContentVersion : (List<VersionedEntryContentVersion>)q.list()) {
				map.put(versionedEntryContentVersion.getPrimaryKeyObj(),
					versionedEntryContentVersion);

				cacheResult(versionedEntryContentVersion);

				uncachedPrimaryKeys.remove(versionedEntryContentVersion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(VersionedEntryContentVersionModelImpl.ENTITY_CACHE_ENABLED,
					VersionedEntryContentVersionImpl.class, primaryKey,
					nullModel);
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
	 * Returns all the versioned entry content versions.
	 *
	 * @return the versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the versioned entry content versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @return the range of versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the versioned entry content versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findAll(int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the versioned entry content versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link VersionedEntryContentVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of versioned entry content versions
	 * @param end the upper bound of the range of versioned entry content versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of versioned entry content versions
	 */
	@Override
	public List<VersionedEntryContentVersion> findAll(int start, int end,
		OrderByComparator<VersionedEntryContentVersion> orderByComparator,
		boolean retrieveFromCache) {
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

		List<VersionedEntryContentVersion> list = null;

		if (retrieveFromCache) {
			list = (List<VersionedEntryContentVersion>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_VERSIONEDENTRYCONTENTVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_VERSIONEDENTRYCONTENTVERSION;

				if (pagination) {
					sql = sql.concat(VersionedEntryContentVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<VersionedEntryContentVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<VersionedEntryContentVersion>)QueryUtil.list(q,
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
	 * Removes all the versioned entry content versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (VersionedEntryContentVersion versionedEntryContentVersion : findAll()) {
			remove(versionedEntryContentVersion);
		}
	}

	/**
	 * Returns the number of versioned entry content versions.
	 *
	 * @return the number of versioned entry content versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_VERSIONEDENTRYCONTENTVERSION);

				count = (Long)q.uniqueResult();

				finderCache.putResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY,
					count);
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_COUNT_ALL,
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
	protected Map<String, Integer> getTableColumnsMap() {
		return VersionedEntryContentVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the versioned entry content version persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(VersionedEntryContentVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_VERSIONEDENTRYCONTENTVERSION = "SELECT versionedEntryContentVersion FROM VersionedEntryContentVersion versionedEntryContentVersion";
	private static final String _SQL_SELECT_VERSIONEDENTRYCONTENTVERSION_WHERE_PKS_IN =
		"SELECT versionedEntryContentVersion FROM VersionedEntryContentVersion versionedEntryContentVersion WHERE versionedEntryContentVersionId IN (";
	private static final String _SQL_SELECT_VERSIONEDENTRYCONTENTVERSION_WHERE = "SELECT versionedEntryContentVersion FROM VersionedEntryContentVersion versionedEntryContentVersion WHERE ";
	private static final String _SQL_COUNT_VERSIONEDENTRYCONTENTVERSION = "SELECT COUNT(versionedEntryContentVersion) FROM VersionedEntryContentVersion versionedEntryContentVersion";
	private static final String _SQL_COUNT_VERSIONEDENTRYCONTENTVERSION_WHERE = "SELECT COUNT(versionedEntryContentVersion) FROM VersionedEntryContentVersion versionedEntryContentVersion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "versionedEntryContentVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No VersionedEntryContentVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No VersionedEntryContentVersion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(VersionedEntryContentVersionPersistenceImpl.class);
}