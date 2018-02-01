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

package com.liferay.dynamic.data.mapping.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.dynamic.data.mapping.exception.NoSuchFormInstanceVersionException;
import com.liferay.dynamic.data.mapping.model.DDMFormInstanceVersion;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionImpl;
import com.liferay.dynamic.data.mapping.model.impl.DDMFormInstanceVersionModelImpl;
import com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceVersionPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the ddm form instance version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @see DDMFormInstanceVersionPersistence
 * @see com.liferay.dynamic.data.mapping.service.persistence.DDMFormInstanceVersionUtil
 * @generated
 */
@ProviderType
public class DDMFormInstanceVersionPersistenceImpl extends BasePersistenceImpl<DDMFormInstanceVersion>
	implements DDMFormInstanceVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link DDMFormInstanceVersionUtil} to access the ddm form instance version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = DDMFormInstanceVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_FORMINSTANCEID =
		new FinderPath(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByFormInstanceId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMINSTANCEID =
		new FinderPath(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByFormInstanceId",
			new String[] { Long.class.getName() },
			DDMFormInstanceVersionModelImpl.FORMINSTANCEID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_FORMINSTANCEID = new FinderPath(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByFormInstanceId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the ddm form instance versions where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId) {
		return findByFormInstanceId(formInstanceId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance versions where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @return the range of matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId, int start, int end) {
		return findByFormInstanceId(formInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {
		return findByFormInstanceId(formInstanceId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByFormInstanceId(
		long formInstanceId, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMINSTANCEID;
			finderArgs = new Object[] { formInstanceId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_FORMINSTANCEID;
			finderArgs = new Object[] {
					formInstanceId,
					
					start, end, orderByComparator
				};
		}

		List<DDMFormInstanceVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDMFormInstanceVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceVersion ddmFormInstanceVersion : list) {
					if ((formInstanceId != ddmFormInstanceVersion.getFormInstanceId())) {
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

			query.append(_SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE);

			query.append(_FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMFormInstanceVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(formInstanceId);

				if (!pagination) {
					list = (List<DDMFormInstanceVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMFormInstanceVersion>)QueryUtil.list(q,
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
	 * Returns the first ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByFormInstanceId_First(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException {
		DDMFormInstanceVersion ddmFormInstanceVersion = fetchByFormInstanceId_First(formInstanceId,
				orderByComparator);

		if (ddmFormInstanceVersion != null) {
			return ddmFormInstanceVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("formInstanceId=");
		msg.append(formInstanceId);

		msg.append("}");

		throw new NoSuchFormInstanceVersionException(msg.toString());
	}

	/**
	 * Returns the first ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByFormInstanceId_First(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {
		List<DDMFormInstanceVersion> list = findByFormInstanceId(formInstanceId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByFormInstanceId_Last(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException {
		DDMFormInstanceVersion ddmFormInstanceVersion = fetchByFormInstanceId_Last(formInstanceId,
				orderByComparator);

		if (ddmFormInstanceVersion != null) {
			return ddmFormInstanceVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("formInstanceId=");
		msg.append(formInstanceId);

		msg.append("}");

		throw new NoSuchFormInstanceVersionException(msg.toString());
	}

	/**
	 * Returns the last ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByFormInstanceId_Last(
		long formInstanceId,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {
		int count = countByFormInstanceId(formInstanceId);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceVersion> list = findByFormInstanceId(formInstanceId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance versions before and after the current ddm form instance version in the ordered set where formInstanceId = &#63;.
	 *
	 * @param formInstanceVersionId the primary key of the current ddm form instance version
	 * @param formInstanceId the form instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion[] findByFormInstanceId_PrevAndNext(
		long formInstanceVersionId, long formInstanceId,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException {
		DDMFormInstanceVersion ddmFormInstanceVersion = findByPrimaryKey(formInstanceVersionId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceVersion[] array = new DDMFormInstanceVersionImpl[3];

			array[0] = getByFormInstanceId_PrevAndNext(session,
					ddmFormInstanceVersion, formInstanceId, orderByComparator,
					true);

			array[1] = ddmFormInstanceVersion;

			array[2] = getByFormInstanceId_PrevAndNext(session,
					ddmFormInstanceVersion, formInstanceId, orderByComparator,
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

	protected DDMFormInstanceVersion getByFormInstanceId_PrevAndNext(
		Session session, DDMFormInstanceVersion ddmFormInstanceVersion,
		long formInstanceId,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator,
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

		query.append(_SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE);

		query.append(_FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2);

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
			query.append(DDMFormInstanceVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(formInstanceId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmFormInstanceVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMFormInstanceVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance versions where formInstanceId = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 */
	@Override
	public void removeByFormInstanceId(long formInstanceId) {
		for (DDMFormInstanceVersion ddmFormInstanceVersion : findByFormInstanceId(
				formInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(ddmFormInstanceVersion);
		}
	}

	/**
	 * Returns the number of ddm form instance versions where formInstanceId = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @return the number of matching ddm form instance versions
	 */
	@Override
	public int countByFormInstanceId(long formInstanceId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_FORMINSTANCEID;

		Object[] finderArgs = new Object[] { formInstanceId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DDMFORMINSTANCEVERSION_WHERE);

			query.append(_FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(formInstanceId);

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

	private static final String _FINDER_COLUMN_FORMINSTANCEID_FORMINSTANCEID_2 = "ddmFormInstanceVersion.formInstanceId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_F_V = new FinderPath(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByF_V",
			new String[] { Long.class.getName(), String.class.getName() },
			DDMFormInstanceVersionModelImpl.FORMINSTANCEID_COLUMN_BITMASK |
			DDMFormInstanceVersionModelImpl.VERSION_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_F_V = new FinderPath(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_V",
			new String[] { Long.class.getName(), String.class.getName() });

	/**
	 * Returns the ddm form instance version where formInstanceId = &#63; and version = &#63; or throws a {@link NoSuchFormInstanceVersionException} if it could not be found.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @return the matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByF_V(long formInstanceId, String version)
		throws NoSuchFormInstanceVersionException {
		DDMFormInstanceVersion ddmFormInstanceVersion = fetchByF_V(formInstanceId,
				version);

		if (ddmFormInstanceVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("formInstanceId=");
			msg.append(formInstanceId);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchFormInstanceVersionException(msg.toString());
		}

		return ddmFormInstanceVersion;
	}

	/**
	 * Returns the ddm form instance version where formInstanceId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @return the matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByF_V(long formInstanceId, String version) {
		return fetchByF_V(formInstanceId, version, true);
	}

	/**
	 * Returns the ddm form instance version where formInstanceId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByF_V(long formInstanceId,
		String version, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { formInstanceId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_F_V,
					finderArgs, this);
		}

		if (result instanceof DDMFormInstanceVersion) {
			DDMFormInstanceVersion ddmFormInstanceVersion = (DDMFormInstanceVersion)result;

			if ((formInstanceId != ddmFormInstanceVersion.getFormInstanceId()) ||
					!Objects.equals(version, ddmFormInstanceVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_V_FORMINSTANCEID_2);

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_F_V_VERSION_1);
			}
			else if (version.equals("")) {
				query.append(_FINDER_COLUMN_F_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_F_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(formInstanceId);

				if (bindVersion) {
					qPos.add(version);
				}

				List<DDMFormInstanceVersion> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_F_V, finderArgs,
						list);
				}
				else {
					DDMFormInstanceVersion ddmFormInstanceVersion = list.get(0);

					result = ddmFormInstanceVersion;

					cacheResult(ddmFormInstanceVersion);

					if ((ddmFormInstanceVersion.getFormInstanceId() != formInstanceId) ||
							(ddmFormInstanceVersion.getVersion() == null) ||
							!ddmFormInstanceVersion.getVersion().equals(version)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_F_V,
							finderArgs, ddmFormInstanceVersion);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_F_V, finderArgs);

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
			return (DDMFormInstanceVersion)result;
		}
	}

	/**
	 * Removes the ddm form instance version where formInstanceId = &#63; and version = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @return the ddm form instance version that was removed
	 */
	@Override
	public DDMFormInstanceVersion removeByF_V(long formInstanceId,
		String version) throws NoSuchFormInstanceVersionException {
		DDMFormInstanceVersion ddmFormInstanceVersion = findByF_V(formInstanceId,
				version);

		return remove(ddmFormInstanceVersion);
	}

	/**
	 * Returns the number of ddm form instance versions where formInstanceId = &#63; and version = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param version the version
	 * @return the number of matching ddm form instance versions
	 */
	@Override
	public int countByF_V(long formInstanceId, String version) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_F_V;

		Object[] finderArgs = new Object[] { formInstanceId, version };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMFORMINSTANCEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_V_FORMINSTANCEID_2);

			boolean bindVersion = false;

			if (version == null) {
				query.append(_FINDER_COLUMN_F_V_VERSION_1);
			}
			else if (version.equals("")) {
				query.append(_FINDER_COLUMN_F_V_VERSION_3);
			}
			else {
				bindVersion = true;

				query.append(_FINDER_COLUMN_F_V_VERSION_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(formInstanceId);

				if (bindVersion) {
					qPos.add(version);
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

	private static final String _FINDER_COLUMN_F_V_FORMINSTANCEID_2 = "ddmFormInstanceVersion.formInstanceId = ? AND ";
	private static final String _FINDER_COLUMN_F_V_VERSION_1 = "ddmFormInstanceVersion.version IS NULL";
	private static final String _FINDER_COLUMN_F_V_VERSION_2 = "ddmFormInstanceVersion.version = ?";
	private static final String _FINDER_COLUMN_F_V_VERSION_3 = "(ddmFormInstanceVersion.version IS NULL OR ddmFormInstanceVersion.version = '')";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_F_S = new FinderPath(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByF_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S = new FinderPath(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionModelImpl.FINDER_CACHE_ENABLED,
			DDMFormInstanceVersionImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByF_S",
			new String[] { Long.class.getName(), Integer.class.getName() },
			DDMFormInstanceVersionModelImpl.FORMINSTANCEID_COLUMN_BITMASK |
			DDMFormInstanceVersionModelImpl.STATUS_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_F_S = new FinderPath(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByF_S",
			new String[] { Long.class.getName(), Integer.class.getName() });

	/**
	 * Returns all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @return the matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByF_S(long formInstanceId,
		int status) {
		return findByF_S(formInstanceId, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @return the range of matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByF_S(long formInstanceId,
		int status, int start, int end) {
		return findByF_S(formInstanceId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByF_S(long formInstanceId,
		int status, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {
		return findByF_S(formInstanceId, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findByF_S(long formInstanceId,
		int status, int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S;
			finderArgs = new Object[] { formInstanceId, status };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_F_S;
			finderArgs = new Object[] {
					formInstanceId, status,
					
					start, end, orderByComparator
				};
		}

		List<DDMFormInstanceVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDMFormInstanceVersion>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DDMFormInstanceVersion ddmFormInstanceVersion : list) {
					if ((formInstanceId != ddmFormInstanceVersion.getFormInstanceId()) ||
							(status != ddmFormInstanceVersion.getStatus())) {
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

			query.append(_SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_S_FORMINSTANCEID_2);

			query.append(_FINDER_COLUMN_F_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(DDMFormInstanceVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(formInstanceId);

				qPos.add(status);

				if (!pagination) {
					list = (List<DDMFormInstanceVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMFormInstanceVersion>)QueryUtil.list(q,
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
	 * Returns the first ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByF_S_First(long formInstanceId,
		int status, OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException {
		DDMFormInstanceVersion ddmFormInstanceVersion = fetchByF_S_First(formInstanceId,
				status, orderByComparator);

		if (ddmFormInstanceVersion != null) {
			return ddmFormInstanceVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("formInstanceId=");
		msg.append(formInstanceId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFormInstanceVersionException(msg.toString());
	}

	/**
	 * Returns the first ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByF_S_First(long formInstanceId,
		int status, OrderByComparator<DDMFormInstanceVersion> orderByComparator) {
		List<DDMFormInstanceVersion> list = findByF_S(formInstanceId, status,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByF_S_Last(long formInstanceId,
		int status, OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException {
		DDMFormInstanceVersion ddmFormInstanceVersion = fetchByF_S_Last(formInstanceId,
				status, orderByComparator);

		if (ddmFormInstanceVersion != null) {
			return ddmFormInstanceVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("formInstanceId=");
		msg.append(formInstanceId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchFormInstanceVersionException(msg.toString());
	}

	/**
	 * Returns the last ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching ddm form instance version, or <code>null</code> if a matching ddm form instance version could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByF_S_Last(long formInstanceId,
		int status, OrderByComparator<DDMFormInstanceVersion> orderByComparator) {
		int count = countByF_S(formInstanceId, status);

		if (count == 0) {
			return null;
		}

		List<DDMFormInstanceVersion> list = findByF_S(formInstanceId, status,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the ddm form instance versions before and after the current ddm form instance version in the ordered set where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceVersionId the primary key of the current ddm form instance version
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion[] findByF_S_PrevAndNext(
		long formInstanceVersionId, long formInstanceId, int status,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator)
		throws NoSuchFormInstanceVersionException {
		DDMFormInstanceVersion ddmFormInstanceVersion = findByPrimaryKey(formInstanceVersionId);

		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceVersion[] array = new DDMFormInstanceVersionImpl[3];

			array[0] = getByF_S_PrevAndNext(session, ddmFormInstanceVersion,
					formInstanceId, status, orderByComparator, true);

			array[1] = ddmFormInstanceVersion;

			array[2] = getByF_S_PrevAndNext(session, ddmFormInstanceVersion,
					formInstanceId, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected DDMFormInstanceVersion getByF_S_PrevAndNext(Session session,
		DDMFormInstanceVersion ddmFormInstanceVersion, long formInstanceId,
		int status,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator,
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

		query.append(_SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE);

		query.append(_FINDER_COLUMN_F_S_FORMINSTANCEID_2);

		query.append(_FINDER_COLUMN_F_S_STATUS_2);

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
			query.append(DDMFormInstanceVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(formInstanceId);

		qPos.add(status);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(ddmFormInstanceVersion);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<DDMFormInstanceVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the ddm form instance versions where formInstanceId = &#63; and status = &#63; from the database.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 */
	@Override
	public void removeByF_S(long formInstanceId, int status) {
		for (DDMFormInstanceVersion ddmFormInstanceVersion : findByF_S(
				formInstanceId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(ddmFormInstanceVersion);
		}
	}

	/**
	 * Returns the number of ddm form instance versions where formInstanceId = &#63; and status = &#63;.
	 *
	 * @param formInstanceId the form instance ID
	 * @param status the status
	 * @return the number of matching ddm form instance versions
	 */
	@Override
	public int countByF_S(long formInstanceId, int status) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_F_S;

		Object[] finderArgs = new Object[] { formInstanceId, status };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DDMFORMINSTANCEVERSION_WHERE);

			query.append(_FINDER_COLUMN_F_S_FORMINSTANCEID_2);

			query.append(_FINDER_COLUMN_F_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(formInstanceId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_F_S_FORMINSTANCEID_2 = "ddmFormInstanceVersion.formInstanceId = ? AND ";
	private static final String _FINDER_COLUMN_F_S_STATUS_2 = "ddmFormInstanceVersion.status = ?";

	public DDMFormInstanceVersionPersistenceImpl() {
		setModelClass(DDMFormInstanceVersion.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("settings", "settings_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the ddm form instance version in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceVersion the ddm form instance version
	 */
	@Override
	public void cacheResult(DDMFormInstanceVersion ddmFormInstanceVersion) {
		entityCache.putResult(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionImpl.class,
			ddmFormInstanceVersion.getPrimaryKey(), ddmFormInstanceVersion);

		finderCache.putResult(FINDER_PATH_FETCH_BY_F_V,
			new Object[] {
				ddmFormInstanceVersion.getFormInstanceId(),
				ddmFormInstanceVersion.getVersion()
			}, ddmFormInstanceVersion);

		ddmFormInstanceVersion.resetOriginalValues();
	}

	/**
	 * Caches the ddm form instance versions in the entity cache if it is enabled.
	 *
	 * @param ddmFormInstanceVersions the ddm form instance versions
	 */
	@Override
	public void cacheResult(
		List<DDMFormInstanceVersion> ddmFormInstanceVersions) {
		for (DDMFormInstanceVersion ddmFormInstanceVersion : ddmFormInstanceVersions) {
			if (entityCache.getResult(
						DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
						DDMFormInstanceVersionImpl.class,
						ddmFormInstanceVersion.getPrimaryKey()) == null) {
				cacheResult(ddmFormInstanceVersion);
			}
			else {
				ddmFormInstanceVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all ddm form instance versions.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DDMFormInstanceVersionImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the ddm form instance version.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DDMFormInstanceVersion ddmFormInstanceVersion) {
		entityCache.removeResult(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionImpl.class,
			ddmFormInstanceVersion.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((DDMFormInstanceVersionModelImpl)ddmFormInstanceVersion,
			true);
	}

	@Override
	public void clearCache(List<DDMFormInstanceVersion> ddmFormInstanceVersions) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DDMFormInstanceVersion ddmFormInstanceVersion : ddmFormInstanceVersions) {
			entityCache.removeResult(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMFormInstanceVersionImpl.class,
				ddmFormInstanceVersion.getPrimaryKey());

			clearUniqueFindersCache((DDMFormInstanceVersionModelImpl)ddmFormInstanceVersion,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		DDMFormInstanceVersionModelImpl ddmFormInstanceVersionModelImpl) {
		Object[] args = new Object[] {
				ddmFormInstanceVersionModelImpl.getFormInstanceId(),
				ddmFormInstanceVersionModelImpl.getVersion()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_F_V, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_F_V, args,
			ddmFormInstanceVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DDMFormInstanceVersionModelImpl ddmFormInstanceVersionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					ddmFormInstanceVersionModelImpl.getFormInstanceId(),
					ddmFormInstanceVersionModelImpl.getVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_F_V, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_F_V, args);
		}

		if ((ddmFormInstanceVersionModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_F_V.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					ddmFormInstanceVersionModelImpl.getOriginalFormInstanceId(),
					ddmFormInstanceVersionModelImpl.getOriginalVersion()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_F_V, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_F_V, args);
		}
	}

	/**
	 * Creates a new ddm form instance version with the primary key. Does not add the ddm form instance version to the database.
	 *
	 * @param formInstanceVersionId the primary key for the new ddm form instance version
	 * @return the new ddm form instance version
	 */
	@Override
	public DDMFormInstanceVersion create(long formInstanceVersionId) {
		DDMFormInstanceVersion ddmFormInstanceVersion = new DDMFormInstanceVersionImpl();

		ddmFormInstanceVersion.setNew(true);
		ddmFormInstanceVersion.setPrimaryKey(formInstanceVersionId);

		ddmFormInstanceVersion.setCompanyId(companyProvider.getCompanyId());

		return ddmFormInstanceVersion;
	}

	/**
	 * Removes the ddm form instance version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param formInstanceVersionId the primary key of the ddm form instance version
	 * @return the ddm form instance version that was removed
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion remove(long formInstanceVersionId)
		throws NoSuchFormInstanceVersionException {
		return remove((Serializable)formInstanceVersionId);
	}

	/**
	 * Removes the ddm form instance version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the ddm form instance version
	 * @return the ddm form instance version that was removed
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion remove(Serializable primaryKey)
		throws NoSuchFormInstanceVersionException {
		Session session = null;

		try {
			session = openSession();

			DDMFormInstanceVersion ddmFormInstanceVersion = (DDMFormInstanceVersion)session.get(DDMFormInstanceVersionImpl.class,
					primaryKey);

			if (ddmFormInstanceVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchFormInstanceVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(ddmFormInstanceVersion);
		}
		catch (NoSuchFormInstanceVersionException nsee) {
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
	protected DDMFormInstanceVersion removeImpl(
		DDMFormInstanceVersion ddmFormInstanceVersion) {
		ddmFormInstanceVersion = toUnwrappedModel(ddmFormInstanceVersion);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(ddmFormInstanceVersion)) {
				ddmFormInstanceVersion = (DDMFormInstanceVersion)session.get(DDMFormInstanceVersionImpl.class,
						ddmFormInstanceVersion.getPrimaryKeyObj());
			}

			if (ddmFormInstanceVersion != null) {
				session.delete(ddmFormInstanceVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (ddmFormInstanceVersion != null) {
			clearCache(ddmFormInstanceVersion);
		}

		return ddmFormInstanceVersion;
	}

	@Override
	public DDMFormInstanceVersion updateImpl(
		DDMFormInstanceVersion ddmFormInstanceVersion) {
		ddmFormInstanceVersion = toUnwrappedModel(ddmFormInstanceVersion);

		boolean isNew = ddmFormInstanceVersion.isNew();

		DDMFormInstanceVersionModelImpl ddmFormInstanceVersionModelImpl = (DDMFormInstanceVersionModelImpl)ddmFormInstanceVersion;

		Session session = null;

		try {
			session = openSession();

			if (ddmFormInstanceVersion.isNew()) {
				session.save(ddmFormInstanceVersion);

				ddmFormInstanceVersion.setNew(false);
			}
			else {
				ddmFormInstanceVersion = (DDMFormInstanceVersion)session.merge(ddmFormInstanceVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!DDMFormInstanceVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					ddmFormInstanceVersionModelImpl.getFormInstanceId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_FORMINSTANCEID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMINSTANCEID,
				args);

			args = new Object[] {
					ddmFormInstanceVersionModelImpl.getFormInstanceId(),
					ddmFormInstanceVersionModelImpl.getStatus()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_F_S, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((ddmFormInstanceVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMINSTANCEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmFormInstanceVersionModelImpl.getOriginalFormInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FORMINSTANCEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMINSTANCEID,
					args);

				args = new Object[] {
						ddmFormInstanceVersionModelImpl.getFormInstanceId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_FORMINSTANCEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_FORMINSTANCEID,
					args);
			}

			if ((ddmFormInstanceVersionModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						ddmFormInstanceVersionModelImpl.getOriginalFormInstanceId(),
						ddmFormInstanceVersionModelImpl.getOriginalStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_F_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S,
					args);

				args = new Object[] {
						ddmFormInstanceVersionModelImpl.getFormInstanceId(),
						ddmFormInstanceVersionModelImpl.getStatus()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_F_S, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_F_S,
					args);
			}
		}

		entityCache.putResult(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
			DDMFormInstanceVersionImpl.class,
			ddmFormInstanceVersion.getPrimaryKey(), ddmFormInstanceVersion,
			false);

		clearUniqueFindersCache(ddmFormInstanceVersionModelImpl, false);
		cacheUniqueFindersCache(ddmFormInstanceVersionModelImpl);

		ddmFormInstanceVersion.resetOriginalValues();

		return ddmFormInstanceVersion;
	}

	protected DDMFormInstanceVersion toUnwrappedModel(
		DDMFormInstanceVersion ddmFormInstanceVersion) {
		if (ddmFormInstanceVersion instanceof DDMFormInstanceVersionImpl) {
			return ddmFormInstanceVersion;
		}

		DDMFormInstanceVersionImpl ddmFormInstanceVersionImpl = new DDMFormInstanceVersionImpl();

		ddmFormInstanceVersionImpl.setNew(ddmFormInstanceVersion.isNew());
		ddmFormInstanceVersionImpl.setPrimaryKey(ddmFormInstanceVersion.getPrimaryKey());

		ddmFormInstanceVersionImpl.setFormInstanceVersionId(ddmFormInstanceVersion.getFormInstanceVersionId());
		ddmFormInstanceVersionImpl.setGroupId(ddmFormInstanceVersion.getGroupId());
		ddmFormInstanceVersionImpl.setCompanyId(ddmFormInstanceVersion.getCompanyId());
		ddmFormInstanceVersionImpl.setUserId(ddmFormInstanceVersion.getUserId());
		ddmFormInstanceVersionImpl.setUserName(ddmFormInstanceVersion.getUserName());
		ddmFormInstanceVersionImpl.setCreateDate(ddmFormInstanceVersion.getCreateDate());
		ddmFormInstanceVersionImpl.setFormInstanceId(ddmFormInstanceVersion.getFormInstanceId());
		ddmFormInstanceVersionImpl.setStructureVersionId(ddmFormInstanceVersion.getStructureVersionId());
		ddmFormInstanceVersionImpl.setName(ddmFormInstanceVersion.getName());
		ddmFormInstanceVersionImpl.setDescription(ddmFormInstanceVersion.getDescription());
		ddmFormInstanceVersionImpl.setSettings(ddmFormInstanceVersion.getSettings());
		ddmFormInstanceVersionImpl.setVersion(ddmFormInstanceVersion.getVersion());
		ddmFormInstanceVersionImpl.setStatus(ddmFormInstanceVersion.getStatus());
		ddmFormInstanceVersionImpl.setStatusByUserId(ddmFormInstanceVersion.getStatusByUserId());
		ddmFormInstanceVersionImpl.setStatusByUserName(ddmFormInstanceVersion.getStatusByUserName());
		ddmFormInstanceVersionImpl.setStatusDate(ddmFormInstanceVersion.getStatusDate());

		return ddmFormInstanceVersionImpl;
	}

	/**
	 * Returns the ddm form instance version with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm form instance version
	 * @return the ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchFormInstanceVersionException {
		DDMFormInstanceVersion ddmFormInstanceVersion = fetchByPrimaryKey(primaryKey);

		if (ddmFormInstanceVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchFormInstanceVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return ddmFormInstanceVersion;
	}

	/**
	 * Returns the ddm form instance version with the primary key or throws a {@link NoSuchFormInstanceVersionException} if it could not be found.
	 *
	 * @param formInstanceVersionId the primary key of the ddm form instance version
	 * @return the ddm form instance version
	 * @throws NoSuchFormInstanceVersionException if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion findByPrimaryKey(long formInstanceVersionId)
		throws NoSuchFormInstanceVersionException {
		return findByPrimaryKey((Serializable)formInstanceVersionId);
	}

	/**
	 * Returns the ddm form instance version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the ddm form instance version
	 * @return the ddm form instance version, or <code>null</code> if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
				DDMFormInstanceVersionImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		DDMFormInstanceVersion ddmFormInstanceVersion = (DDMFormInstanceVersion)serializable;

		if (ddmFormInstanceVersion == null) {
			Session session = null;

			try {
				session = openSession();

				ddmFormInstanceVersion = (DDMFormInstanceVersion)session.get(DDMFormInstanceVersionImpl.class,
						primaryKey);

				if (ddmFormInstanceVersion != null) {
					cacheResult(ddmFormInstanceVersion);
				}
				else {
					entityCache.putResult(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
						DDMFormInstanceVersionImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
					DDMFormInstanceVersionImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return ddmFormInstanceVersion;
	}

	/**
	 * Returns the ddm form instance version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param formInstanceVersionId the primary key of the ddm form instance version
	 * @return the ddm form instance version, or <code>null</code> if a ddm form instance version with the primary key could not be found
	 */
	@Override
	public DDMFormInstanceVersion fetchByPrimaryKey(long formInstanceVersionId) {
		return fetchByPrimaryKey((Serializable)formInstanceVersionId);
	}

	@Override
	public Map<Serializable, DDMFormInstanceVersion> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, DDMFormInstanceVersion> map = new HashMap<Serializable, DDMFormInstanceVersion>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			DDMFormInstanceVersion ddmFormInstanceVersion = fetchByPrimaryKey(primaryKey);

			if (ddmFormInstanceVersion != null) {
				map.put(primaryKey, ddmFormInstanceVersion);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
					DDMFormInstanceVersionImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (DDMFormInstanceVersion)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE_PKS_IN);

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

			for (DDMFormInstanceVersion ddmFormInstanceVersion : (List<DDMFormInstanceVersion>)q.list()) {
				map.put(ddmFormInstanceVersion.getPrimaryKeyObj(),
					ddmFormInstanceVersion);

				cacheResult(ddmFormInstanceVersion);

				uncachedPrimaryKeys.remove(ddmFormInstanceVersion.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(DDMFormInstanceVersionModelImpl.ENTITY_CACHE_ENABLED,
					DDMFormInstanceVersionImpl.class, primaryKey, nullModel);
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
	 * Returns all the ddm form instance versions.
	 *
	 * @return the ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the ddm form instance versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @return the range of ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findAll(int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the ddm form instance versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link DDMFormInstanceVersionModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of ddm form instance versions
	 * @param end the upper bound of the range of ddm form instance versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of ddm form instance versions
	 */
	@Override
	public List<DDMFormInstanceVersion> findAll(int start, int end,
		OrderByComparator<DDMFormInstanceVersion> orderByComparator,
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

		List<DDMFormInstanceVersion> list = null;

		if (retrieveFromCache) {
			list = (List<DDMFormInstanceVersion>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DDMFORMINSTANCEVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DDMFORMINSTANCEVERSION;

				if (pagination) {
					sql = sql.concat(DDMFormInstanceVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<DDMFormInstanceVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<DDMFormInstanceVersion>)QueryUtil.list(q,
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
	 * Removes all the ddm form instance versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DDMFormInstanceVersion ddmFormInstanceVersion : findAll()) {
			remove(ddmFormInstanceVersion);
		}
	}

	/**
	 * Returns the number of ddm form instance versions.
	 *
	 * @return the number of ddm form instance versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DDMFORMINSTANCEVERSION);

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
	public Set<String> getBadColumnNames() {
		return _badColumnNames;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DDMFormInstanceVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the ddm form instance version persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(DDMFormInstanceVersionImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;
	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;
	private static final String _SQL_SELECT_DDMFORMINSTANCEVERSION = "SELECT ddmFormInstanceVersion FROM DDMFormInstanceVersion ddmFormInstanceVersion";
	private static final String _SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE_PKS_IN = "SELECT ddmFormInstanceVersion FROM DDMFormInstanceVersion ddmFormInstanceVersion WHERE formInstanceVersionId IN (";
	private static final String _SQL_SELECT_DDMFORMINSTANCEVERSION_WHERE = "SELECT ddmFormInstanceVersion FROM DDMFormInstanceVersion ddmFormInstanceVersion WHERE ";
	private static final String _SQL_COUNT_DDMFORMINSTANCEVERSION = "SELECT COUNT(ddmFormInstanceVersion) FROM DDMFormInstanceVersion ddmFormInstanceVersion";
	private static final String _SQL_COUNT_DDMFORMINSTANCEVERSION_WHERE = "SELECT COUNT(ddmFormInstanceVersion) FROM DDMFormInstanceVersion ddmFormInstanceVersion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "ddmFormInstanceVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DDMFormInstanceVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DDMFormInstanceVersion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(DDMFormInstanceVersionPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"settings"
			});
}