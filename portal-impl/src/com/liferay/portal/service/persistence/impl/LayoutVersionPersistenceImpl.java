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

package com.liferay.portal.service.persistence.impl;

import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchLayoutVersionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutVersion;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.LayoutVersionPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.impl.LayoutVersionImpl;
import com.liferay.portal.model.impl.LayoutVersionModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import org.osgi.annotation.versioning.ProviderType;

/**
 * The persistence implementation for the layout version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutVersionPersistenceImpl
	extends BasePersistenceImpl<LayoutVersion>
	implements LayoutVersionPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutVersionUtil</code> to access the layout version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutVersionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByPlid;
	private FinderPath _finderPathWithoutPaginationFindByPlid;
	private FinderPath _finderPathCountByPlid;

	/**
	 * Returns all the layout versions where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByPlid(long plid) {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByPlid(long plid, int start, int end) {
		return findByPlid(plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByPlid(plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByPlid;
			finderArgs = new Object[] {plid};
		}
		else {
			finderPath = _finderPathWithPaginationFindByPlid;
			finderArgs = new Object[] {plid, start, end, orderByComparator};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((plid != layoutVersion.getPlid())) {
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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByPlid_First(
			long plid, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByPlid_First(
			plid, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByPlid_First(
		long plid, OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByPlid(plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByPlid_Last(
			long plid, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByPlid_Last(plid, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByPlid_Last(
		long plid, OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByPlid(plid);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByPlid(
			plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where plid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByPlid_PrevAndNext(
			long layoutVersionId, long plid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByPlid_PrevAndNext(
				session, layoutVersion, plid, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByPlid_PrevAndNext(
				session, layoutVersion, plid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutVersion getByPlid_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long plid,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_PLID_PLID_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	@Override
	public void removeByPlid(long plid) {
		for (LayoutVersion layoutVersion :
				findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByPlid(long plid) {
		FinderPath finderPath = _finderPathCountByPlid;

		Object[] finderArgs = new Object[] {plid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

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

	private static final String _FINDER_COLUMN_PLID_PLID_2 =
		"layoutVersion.plid = ?";

	private FinderPath _finderPathFetchByPlid_Version;
	private FinderPath _finderPathCountByPlid_Version;

	/**
	 * Returns the layout version where plid = &#63; and version = &#63; or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @return the matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByPlid_Version(long plid, int version)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByPlid_Version(plid, version);

		if (layoutVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("plid=");
			msg.append(plid);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLayoutVersionException(msg.toString());
		}

		return layoutVersion;
	}

	/**
	 * Returns the layout version where plid = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByPlid_Version(long plid, int version) {
		return fetchByPlid_Version(plid, version, true);
	}

	/**
	 * Returns the layout version where plid = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByPlid_Version(
		long plid, int version, boolean retrieveFromCache) {

		Object[] finderArgs = new Object[] {plid, version};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByPlid_Version, finderArgs, this);
		}

		if (result instanceof LayoutVersion) {
			LayoutVersion layoutVersion = (LayoutVersion)result;

			if ((plid != layoutVersion.getPlid()) ||
				(version != layoutVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_PLID_VERSION_PLID_2);

			query.append(_FINDER_COLUMN_PLID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				qPos.add(version);

				List<LayoutVersion> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(
						_finderPathFetchByPlid_Version, finderArgs, list);
				}
				else {
					LayoutVersion layoutVersion = list.get(0);

					result = layoutVersion;

					cacheResult(layoutVersion);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathFetchByPlid_Version, finderArgs);

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
			return (LayoutVersion)result;
		}
	}

	/**
	 * Removes the layout version where plid = &#63; and version = &#63; from the database.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @return the layout version that was removed
	 */
	@Override
	public LayoutVersion removeByPlid_Version(long plid, int version)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPlid_Version(plid, version);

		return remove(layoutVersion);
	}

	/**
	 * Returns the number of layout versions where plid = &#63; and version = &#63;.
	 *
	 * @param plid the plid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByPlid_Version(long plid, int version) {
		FinderPath finderPath = _finderPathCountByPlid_Version;

		Object[] finderArgs = new Object[] {plid, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_PLID_VERSION_PLID_2);

			query.append(_FINDER_COLUMN_PLID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_PLID_VERSION_PLID_2 =
		"layoutVersion.plid = ? AND ";

	private static final String _FINDER_COLUMN_PLID_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the layout versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid(String uuid, int start, int end) {
		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid;
			finderArgs = new Object[] {uuid};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if (!uuid.equals(layoutVersion.getUuid())) {
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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

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
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByUuid_First(
			String uuid, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByUuid_First(
			uuid, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByUuid_First(
		String uuid, OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByUuid(uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByUuid_Last(
			String uuid, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByUuid_Last(uuid, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByUuid_Last(
		String uuid, OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByUuid_PrevAndNext(
			long layoutVersionId, String uuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		uuid = Objects.toString(uuid, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, layoutVersion, uuid, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByUuid_PrevAndNext(
				session, layoutVersion, uuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutVersion getByUuid_PrevAndNext(
		Session session, LayoutVersion layoutVersion, String uuid,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
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
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (LayoutVersion layoutVersion :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"layoutVersion.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(layoutVersion.uuid IS NULL OR layoutVersion.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_Version;
	private FinderPath _finderPathWithoutPaginationFindByUuid_Version;
	private FinderPath _finderPathCountByUuid_Version;

	/**
	 * Returns all the layout versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid_Version(String uuid, int version) {
		return findByUuid_Version(
			uuid, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid_Version(
		String uuid, int version, int start, int end) {

		return findByUuid_Version(uuid, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByUuid_Version(
			uuid, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid_Version(
		String uuid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid_Version;
			finderArgs = new Object[] {uuid, version};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid_Version;
			finderArgs = new Object[] {
				uuid, version, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if (!uuid.equals(layoutVersion.getUuid()) ||
						(version != layoutVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_VERSION_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
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

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByUuid_Version_First(
			String uuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByUuid_Version_First(
			uuid, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByUuid_Version_First(
		String uuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByUuid_Version(
			uuid, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByUuid_Version_Last(
			String uuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByUuid_Version_Last(
			uuid, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByUuid_Version_Last(
		String uuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByUuid_Version(uuid, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByUuid_Version(
			uuid, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByUuid_Version_PrevAndNext(
			long layoutVersionId, String uuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		uuid = Objects.toString(uuid, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByUuid_Version_PrevAndNext(
				session, layoutVersion, uuid, version, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByUuid_Version_PrevAndNext(
				session, layoutVersion, uuid, version, orderByComparator,
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

	protected LayoutVersion getByUuid_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, String uuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_VERSION_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_VERSION_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where uuid = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 */
	@Override
	public void removeByUuid_Version(String uuid, int version) {
		for (LayoutVersion layoutVersion :
				findByUuid_Version(
					uuid, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where uuid = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByUuid_Version(String uuid, int version) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_Version;

		Object[] finderArgs = new Object[] {uuid, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_VERSION_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindUuid) {
					qPos.add(uuid);
				}

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_UUID_VERSION_UUID_2 =
		"layoutVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_VERSION_UUID_3 =
		"(layoutVersion.uuid IS NULL OR layoutVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByUUID_G_P;
	private FinderPath _finderPathWithoutPaginationFindByUUID_G_P;
	private FinderPath _finderPathCountByUUID_G_P;

	/**
	 * Returns all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUUID_G_P(
		String uuid, long groupId, boolean privateLayout) {

		return findByUUID_G_P(
			uuid, groupId, privateLayout, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUUID_G_P(
		String uuid, long groupId, boolean privateLayout, int start, int end) {

		return findByUUID_G_P(uuid, groupId, privateLayout, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUUID_G_P(
		String uuid, long groupId, boolean privateLayout, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByUUID_G_P(
			uuid, groupId, privateLayout, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUUID_G_P(
		String uuid, long groupId, boolean privateLayout, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUUID_G_P;
			finderArgs = new Object[] {uuid, groupId, privateLayout};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUUID_G_P;
			finderArgs = new Object[] {
				uuid, groupId, privateLayout, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if (!uuid.equals(layoutVersion.getUuid()) ||
						(groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_P_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_P_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_UUID_G_P_PRIVATELAYOUT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
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

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByUUID_G_P_First(
			String uuid, long groupId, boolean privateLayout,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByUUID_G_P_First(
			uuid, groupId, privateLayout, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByUUID_G_P_First(
		String uuid, long groupId, boolean privateLayout,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByUUID_G_P(
			uuid, groupId, privateLayout, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByUUID_G_P_Last(
			String uuid, long groupId, boolean privateLayout,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByUUID_G_P_Last(
			uuid, groupId, privateLayout, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByUUID_G_P_Last(
		String uuid, long groupId, boolean privateLayout,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByUUID_G_P(uuid, groupId, privateLayout);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByUUID_G_P(
			uuid, groupId, privateLayout, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByUUID_G_P_PrevAndNext(
			long layoutVersionId, String uuid, long groupId,
			boolean privateLayout,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		uuid = Objects.toString(uuid, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByUUID_G_P_PrevAndNext(
				session, layoutVersion, uuid, groupId, privateLayout,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByUUID_G_P_PrevAndNext(
				session, layoutVersion, uuid, groupId, privateLayout,
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

	protected LayoutVersion getByUUID_G_P_PrevAndNext(
		Session session, LayoutVersion layoutVersion, String uuid, long groupId,
		boolean privateLayout,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_G_P_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_G_P_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_UUID_G_P_PRIVATELAYOUT_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindUuid) {
			qPos.add(uuid);
		}

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 */
	@Override
	public void removeByUUID_G_P(
		String uuid, long groupId, boolean privateLayout) {

		for (LayoutVersion layoutVersion :
				findByUUID_G_P(
					uuid, groupId, privateLayout, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByUUID_G_P(
		String uuid, long groupId, boolean privateLayout) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G_P;

		Object[] finderArgs = new Object[] {uuid, groupId, privateLayout};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_P_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_P_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_UUID_G_P_PRIVATELAYOUT_2);

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

				qPos.add(privateLayout);

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

	private static final String _FINDER_COLUMN_UUID_G_P_UUID_2 =
		"layoutVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_P_UUID_3 =
		"(layoutVersion.uuid IS NULL OR layoutVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_P_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_P_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ?";

	private FinderPath _finderPathFetchByUUID_G_P_Version;
	private FinderPath _finderPathCountByUUID_G_P_Version;

	/**
	 * Returns the layout version where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63; or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByUUID_G_P_Version(
			String uuid, long groupId, boolean privateLayout, int version)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByUUID_G_P_Version(
			uuid, groupId, privateLayout, version);

		if (layoutVersion == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("uuid=");
			msg.append(uuid);

			msg.append(", groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLayoutVersionException(msg.toString());
		}

		return layoutVersion;
	}

	/**
	 * Returns the layout version where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByUUID_G_P_Version(
		String uuid, long groupId, boolean privateLayout, int version) {

		return fetchByUUID_G_P_Version(
			uuid, groupId, privateLayout, version, true);
	}

	/**
	 * Returns the layout version where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByUUID_G_P_Version(
		String uuid, long groupId, boolean privateLayout, int version,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		Object[] finderArgs = new Object[] {
			uuid, groupId, privateLayout, version
		};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByUUID_G_P_Version, finderArgs, this);
		}

		if (result instanceof LayoutVersion) {
			LayoutVersion layoutVersion = (LayoutVersion)result;

			if (!Objects.equals(uuid, layoutVersion.getUuid()) ||
				(groupId != layoutVersion.getGroupId()) ||
				(privateLayout != layoutVersion.isPrivateLayout()) ||
				(version != layoutVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_P_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_P_VERSION_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_P_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_UUID_G_P_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_UUID_G_P_VERSION_VERSION_2);

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

				qPos.add(privateLayout);

				qPos.add(version);

				List<LayoutVersion> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(
						_finderPathFetchByUUID_G_P_Version, finderArgs, list);
				}
				else {
					LayoutVersion layoutVersion = list.get(0);

					result = layoutVersion;

					cacheResult(layoutVersion);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathFetchByUUID_G_P_Version, finderArgs);

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
			return (LayoutVersion)result;
		}
	}

	/**
	 * Removes the layout version where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the layout version that was removed
	 */
	@Override
	public LayoutVersion removeByUUID_G_P_Version(
			String uuid, long groupId, boolean privateLayout, int version)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByUUID_G_P_Version(
			uuid, groupId, privateLayout, version);

		return remove(layoutVersion);
	}

	/**
	 * Returns the number of layout versions where uuid = &#63; and groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByUUID_G_P_Version(
		String uuid, long groupId, boolean privateLayout, int version) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUUID_G_P_Version;

		Object[] finderArgs = new Object[] {
			uuid, groupId, privateLayout, version
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_G_P_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_G_P_VERSION_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_G_P_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_UUID_G_P_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_UUID_G_P_VERSION_VERSION_2);

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

				qPos.add(privateLayout);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_UUID_G_P_VERSION_UUID_2 =
		"layoutVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_P_VERSION_UUID_3 =
		"(layoutVersion.uuid IS NULL OR layoutVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_G_P_VERSION_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_UUID_G_P_VERSION_PRIVATELAYOUT_2 =
			"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_UUID_G_P_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid_C(String uuid, long companyId) {
		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid_C;
			finderArgs = new Object[] {uuid, companyId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if (!uuid.equals(layoutVersion.getUuid()) ||
						(companyId != layoutVersion.getCompanyId())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

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
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByUuid_C_First(
			uuid, companyId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByUuid_C_Last(
			uuid, companyId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByUuid_C_PrevAndNext(
			long layoutVersionId, String uuid, long companyId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		uuid = Objects.toString(uuid, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, layoutVersion, uuid, companyId, orderByComparator,
				true);

			array[1] = layoutVersion;

			array[2] = getByUuid_C_PrevAndNext(
				session, layoutVersion, uuid, companyId, orderByComparator,
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

	protected LayoutVersion getByUuid_C_PrevAndNext(
		Session session, LayoutVersion layoutVersion, String uuid,
		long companyId, OrderByComparator<LayoutVersion> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
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
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (LayoutVersion layoutVersion :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"layoutVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(layoutVersion.uuid IS NULL OR layoutVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"layoutVersion.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByUuid_C_Version;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C_Version;
	private FinderPath _finderPathCountByUuid_C_Version;

	/**
	 * Returns all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid_C_Version(
		String uuid, long companyId, int version) {

		return findByUuid_C_Version(
			uuid, companyId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end) {

		return findByUuid_C_Version(uuid, companyId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByUuid_C_Version(
			uuid, companyId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByUuid_C_Version(
		String uuid, long companyId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		uuid = Objects.toString(uuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByUuid_C_Version;
			finderArgs = new Object[] {uuid, companyId, version};
		}
		else {
			finderPath = _finderPathWithPaginationFindByUuid_C_Version;
			finderArgs = new Object[] {
				uuid, companyId, version, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if (!uuid.equals(layoutVersion.getUuid()) ||
						(companyId != layoutVersion.getCompanyId()) ||
						(version != layoutVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_VERSION_COMPANYID_2);

			query.append(_FINDER_COLUMN_UUID_C_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
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

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByUuid_C_Version_First(
			String uuid, long companyId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByUuid_C_Version_First(
			uuid, companyId, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByUuid_C_Version_First(
		String uuid, long companyId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByUuid_C_Version(
			uuid, companyId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByUuid_C_Version_Last(
			String uuid, long companyId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByUuid_C_Version_Last(
			uuid, companyId, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("uuid=");
		msg.append(uuid);

		msg.append(", companyId=");
		msg.append(companyId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByUuid_C_Version_Last(
		String uuid, long companyId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByUuid_C_Version(uuid, companyId, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByUuid_C_Version(
			uuid, companyId, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByUuid_C_Version_PrevAndNext(
			long layoutVersionId, String uuid, long companyId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		uuid = Objects.toString(uuid, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByUuid_C_Version_PrevAndNext(
				session, layoutVersion, uuid, companyId, version,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByUuid_C_Version_PrevAndNext(
				session, layoutVersion, uuid, companyId, version,
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

	protected LayoutVersion getByUuid_C_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, String uuid,
		long companyId, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			query.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_3);
		}
		else {
			bindUuid = true;

			query.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_2);
		}

		query.append(_FINDER_COLUMN_UUID_C_VERSION_COMPANYID_2);

		query.append(_FINDER_COLUMN_UUID_C_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
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

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where uuid = &#63; and companyId = &#63; and version = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 */
	@Override
	public void removeByUuid_C_Version(
		String uuid, long companyId, int version) {

		for (LayoutVersion layoutVersion :
				findByUuid_C_Version(
					uuid, companyId, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where uuid = &#63; and companyId = &#63; and version = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByUuid_C_Version(String uuid, long companyId, int version) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C_Version;

		Object[] finderArgs = new Object[] {uuid, companyId, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				query.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_3);
			}
			else {
				bindUuid = true;

				query.append(_FINDER_COLUMN_UUID_C_VERSION_UUID_2);
			}

			query.append(_FINDER_COLUMN_UUID_C_VERSION_COMPANYID_2);

			query.append(_FINDER_COLUMN_UUID_C_VERSION_VERSION_2);

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

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_UUID_C_VERSION_UUID_2 =
		"layoutVersion.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_VERSION_UUID_3 =
		"(layoutVersion.uuid IS NULL OR layoutVersion.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_VERSION_COMPANYID_2 =
		"layoutVersion.companyId = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the layout versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByGroupId(long groupId) {
		return findByGroupId(
			groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByGroupId(
		long groupId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByGroupId;
			finderArgs = new Object[] {groupId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByGroupId;
			finderArgs = new Object[] {groupId, start, end, orderByComparator};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId())) {
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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByGroupId_First(
			long groupId, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByGroupId_First(
			groupId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByGroupId_First(
		long groupId, OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByGroupId(
			groupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByGroupId_Last(
			long groupId, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByGroupId_Last(
			groupId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByGroupId_Last(
		long groupId, OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByGroupId(
			groupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByGroupId_PrevAndNext(
			long layoutVersionId, long groupId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByGroupId_PrevAndNext(
				session, layoutVersion, groupId, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByGroupId_PrevAndNext(
				session, layoutVersion, groupId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutVersion getByGroupId_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LayoutVersion layoutVersion :
				findByGroupId(
					groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] {groupId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 =
		"layoutVersion.groupId = ?";

	private FinderPath _finderPathWithPaginationFindByGroupId_Version;
	private FinderPath _finderPathWithoutPaginationFindByGroupId_Version;
	private FinderPath _finderPathCountByGroupId_Version;

	/**
	 * Returns all the layout versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByGroupId_Version(
		long groupId, int version) {

		return findByGroupId_Version(
			groupId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByGroupId_Version(
		long groupId, int version, int start, int end) {

		return findByGroupId_Version(groupId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByGroupId_Version(
			groupId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByGroupId_Version(
		long groupId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByGroupId_Version;
			finderArgs = new Object[] {groupId, version};
		}
		else {
			finderPath = _finderPathWithPaginationFindByGroupId_Version;
			finderArgs = new Object[] {
				groupId, version, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(version != layoutVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByGroupId_Version_First(
			long groupId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByGroupId_Version_First(
			groupId, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByGroupId_Version_First(
		long groupId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByGroupId_Version(
			groupId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByGroupId_Version_Last(
			long groupId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByGroupId_Version_Last(
			groupId, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByGroupId_Version_Last(
		long groupId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByGroupId_Version(groupId, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByGroupId_Version(
			groupId, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByGroupId_Version_PrevAndNext(
			long layoutVersionId, long groupId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByGroupId_Version_PrevAndNext(
				session, layoutVersion, groupId, version, orderByComparator,
				true);

			array[1] = layoutVersion;

			array[2] = getByGroupId_Version_PrevAndNext(
				session, layoutVersion, groupId, version, orderByComparator,
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

	protected LayoutVersion getByGroupId_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

		query.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	@Override
	public void removeByGroupId_Version(long groupId, int version) {
		for (LayoutVersion layoutVersion :
				findByGroupId_Version(
					groupId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByGroupId_Version(long groupId, int version) {
		FinderPath finderPath = _finderPathCountByGroupId_Version;

		Object[] finderArgs = new Object[] {groupId, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_VERSION_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_GROUPID_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the layout versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByCompanyId;
			finderArgs = new Object[] {companyId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((companyId != layoutVersion.getCompanyId())) {
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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByCompanyId_First(
			long companyId, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByCompanyId_First(
		long companyId, OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByCompanyId_Last(
			long companyId, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByCompanyId_Last(
		long companyId, OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where companyId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByCompanyId_PrevAndNext(
			long layoutVersionId, long companyId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, layoutVersion, companyId, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByCompanyId_PrevAndNext(
				session, layoutVersion, companyId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutVersion getByCompanyId_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long companyId,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (LayoutVersion layoutVersion :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"layoutVersion.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCompanyId_Version;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId_Version;
	private FinderPath _finderPathCountByCompanyId_Version;

	/**
	 * Returns all the layout versions where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByCompanyId_Version(
		long companyId, int version) {

		return findByCompanyId_Version(
			companyId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByCompanyId_Version(
		long companyId, int version, int start, int end) {

		return findByCompanyId_Version(companyId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByCompanyId_Version(
		long companyId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByCompanyId_Version(
			companyId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByCompanyId_Version(
		long companyId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByCompanyId_Version;
			finderArgs = new Object[] {companyId, version};
		}
		else {
			finderPath = _finderPathWithPaginationFindByCompanyId_Version;
			finderArgs = new Object[] {
				companyId, version, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((companyId != layoutVersion.getCompanyId()) ||
						(version != layoutVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_VERSION_COMPANYID_2);

			query.append(_FINDER_COLUMN_COMPANYID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByCompanyId_Version_First(
			long companyId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByCompanyId_Version_First(
			companyId, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByCompanyId_Version_First(
		long companyId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByCompanyId_Version(
			companyId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByCompanyId_Version_Last(
			long companyId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByCompanyId_Version_Last(
			companyId, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByCompanyId_Version_Last(
		long companyId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByCompanyId_Version(companyId, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByCompanyId_Version(
			companyId, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where companyId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param companyId the company ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByCompanyId_Version_PrevAndNext(
			long layoutVersionId, long companyId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByCompanyId_Version_PrevAndNext(
				session, layoutVersion, companyId, version, orderByComparator,
				true);

			array[1] = layoutVersion;

			array[2] = getByCompanyId_Version_PrevAndNext(
				session, layoutVersion, companyId, version, orderByComparator,
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

	protected LayoutVersion getByCompanyId_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long companyId,
		int version, OrderByComparator<LayoutVersion> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_COMPANYID_VERSION_COMPANYID_2);

		query.append(_FINDER_COLUMN_COMPANYID_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where companyId = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 */
	@Override
	public void removeByCompanyId_Version(long companyId, int version) {
		for (LayoutVersion layoutVersion :
				findByCompanyId_Version(
					companyId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where companyId = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByCompanyId_Version(long companyId, int version) {
		FinderPath finderPath = _finderPathCountByCompanyId_Version;

		Object[] finderArgs = new Object[] {companyId, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_COMPANYID_VERSION_COMPANYID_2);

			query.append(_FINDER_COLUMN_COMPANYID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_COMPANYID_VERSION_COMPANYID_2 =
		"layoutVersion.companyId = ? AND ";

	private static final String _FINDER_COLUMN_COMPANYID_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByParentPlid;
	private FinderPath _finderPathWithoutPaginationFindByParentPlid;
	private FinderPath _finderPathCountByParentPlid;

	/**
	 * Returns all the layout versions where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByParentPlid(long parentPlid) {
		return findByParentPlid(
			parentPlid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where parentPlid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByParentPlid(
		long parentPlid, int start, int end) {

		return findByParentPlid(parentPlid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where parentPlid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByParentPlid(
		long parentPlid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByParentPlid(
			parentPlid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where parentPlid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByParentPlid(
		long parentPlid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByParentPlid;
			finderArgs = new Object[] {parentPlid};
		}
		else {
			finderPath = _finderPathWithPaginationFindByParentPlid;
			finderArgs = new Object[] {
				parentPlid, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((parentPlid != layoutVersion.getParentPlid())) {
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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_PARENTPLID_PARENTPLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentPlid);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByParentPlid_First(
			long parentPlid, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByParentPlid_First(
			parentPlid, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentPlid=");
		msg.append(parentPlid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByParentPlid_First(
		long parentPlid, OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByParentPlid(
			parentPlid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByParentPlid_Last(
			long parentPlid, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByParentPlid_Last(
			parentPlid, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentPlid=");
		msg.append(parentPlid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByParentPlid_Last(
		long parentPlid, OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByParentPlid(parentPlid);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByParentPlid(
			parentPlid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where parentPlid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param parentPlid the parent plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByParentPlid_PrevAndNext(
			long layoutVersionId, long parentPlid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByParentPlid_PrevAndNext(
				session, layoutVersion, parentPlid, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByParentPlid_PrevAndNext(
				session, layoutVersion, parentPlid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutVersion getByParentPlid_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long parentPlid,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_PARENTPLID_PARENTPLID_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(parentPlid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where parentPlid = &#63; from the database.
	 *
	 * @param parentPlid the parent plid
	 */
	@Override
	public void removeByParentPlid(long parentPlid) {
		for (LayoutVersion layoutVersion :
				findByParentPlid(
					parentPlid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where parentPlid = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByParentPlid(long parentPlid) {
		FinderPath finderPath = _finderPathCountByParentPlid;

		Object[] finderArgs = new Object[] {parentPlid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_PARENTPLID_PARENTPLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentPlid);

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

	private static final String _FINDER_COLUMN_PARENTPLID_PARENTPLID_2 =
		"layoutVersion.parentPlid = ?";

	private FinderPath _finderPathWithPaginationFindByParentPlid_Version;
	private FinderPath _finderPathWithoutPaginationFindByParentPlid_Version;
	private FinderPath _finderPathCountByParentPlid_Version;

	/**
	 * Returns all the layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByParentPlid_Version(
		long parentPlid, int version) {

		return findByParentPlid_Version(
			parentPlid, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByParentPlid_Version(
		long parentPlid, int version, int start, int end) {

		return findByParentPlid_Version(parentPlid, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByParentPlid_Version(
		long parentPlid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByParentPlid_Version(
			parentPlid, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByParentPlid_Version(
		long parentPlid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByParentPlid_Version;
			finderArgs = new Object[] {parentPlid, version};
		}
		else {
			finderPath = _finderPathWithPaginationFindByParentPlid_Version;
			finderArgs = new Object[] {
				parentPlid, version, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((parentPlid != layoutVersion.getParentPlid()) ||
						(version != layoutVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_PARENTPLID_VERSION_PARENTPLID_2);

			query.append(_FINDER_COLUMN_PARENTPLID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentPlid);

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByParentPlid_Version_First(
			long parentPlid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByParentPlid_Version_First(
			parentPlid, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentPlid=");
		msg.append(parentPlid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByParentPlid_Version_First(
		long parentPlid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByParentPlid_Version(
			parentPlid, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByParentPlid_Version_Last(
			long parentPlid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByParentPlid_Version_Last(
			parentPlid, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("parentPlid=");
		msg.append(parentPlid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByParentPlid_Version_Last(
		long parentPlid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByParentPlid_Version(parentPlid, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByParentPlid_Version(
			parentPlid, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where parentPlid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByParentPlid_Version_PrevAndNext(
			long layoutVersionId, long parentPlid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByParentPlid_Version_PrevAndNext(
				session, layoutVersion, parentPlid, version, orderByComparator,
				true);

			array[1] = layoutVersion;

			array[2] = getByParentPlid_Version_PrevAndNext(
				session, layoutVersion, parentPlid, version, orderByComparator,
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

	protected LayoutVersion getByParentPlid_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long parentPlid,
		int version, OrderByComparator<LayoutVersion> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_PARENTPLID_VERSION_PARENTPLID_2);

		query.append(_FINDER_COLUMN_PARENTPLID_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(parentPlid);

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where parentPlid = &#63; and version = &#63; from the database.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 */
	@Override
	public void removeByParentPlid_Version(long parentPlid, int version) {
		for (LayoutVersion layoutVersion :
				findByParentPlid_Version(
					parentPlid, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where parentPlid = &#63; and version = &#63;.
	 *
	 * @param parentPlid the parent plid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByParentPlid_Version(long parentPlid, int version) {
		FinderPath finderPath = _finderPathCountByParentPlid_Version;

		Object[] finderArgs = new Object[] {parentPlid, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_PARENTPLID_VERSION_PARENTPLID_2);

			query.append(_FINDER_COLUMN_PARENTPLID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(parentPlid);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_PARENTPLID_VERSION_PARENTPLID_2 =
		"layoutVersion.parentPlid = ? AND ";

	private static final String _FINDER_COLUMN_PARENTPLID_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByIconImageId;
	private FinderPath _finderPathWithoutPaginationFindByIconImageId;
	private FinderPath _finderPathCountByIconImageId;

	/**
	 * Returns all the layout versions where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByIconImageId(long iconImageId) {
		return findByIconImageId(
			iconImageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByIconImageId(
		long iconImageId, int start, int end) {

		return findByIconImageId(iconImageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByIconImageId(
		long iconImageId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByIconImageId(
			iconImageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByIconImageId(
		long iconImageId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByIconImageId;
			finderArgs = new Object[] {iconImageId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByIconImageId;
			finderArgs = new Object[] {
				iconImageId, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((iconImageId != layoutVersion.getIconImageId())) {
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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_ICONIMAGEID_ICONIMAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(iconImageId);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByIconImageId_First(
			long iconImageId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByIconImageId_First(
			iconImageId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("iconImageId=");
		msg.append(iconImageId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByIconImageId_First(
		long iconImageId, OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByIconImageId(
			iconImageId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByIconImageId_Last(
			long iconImageId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByIconImageId_Last(
			iconImageId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("iconImageId=");
		msg.append(iconImageId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByIconImageId_Last(
		long iconImageId, OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByIconImageId(iconImageId);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByIconImageId(
			iconImageId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where iconImageId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByIconImageId_PrevAndNext(
			long layoutVersionId, long iconImageId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByIconImageId_PrevAndNext(
				session, layoutVersion, iconImageId, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByIconImageId_PrevAndNext(
				session, layoutVersion, iconImageId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutVersion getByIconImageId_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long iconImageId,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_ICONIMAGEID_ICONIMAGEID_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(iconImageId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where iconImageId = &#63; from the database.
	 *
	 * @param iconImageId the icon image ID
	 */
	@Override
	public void removeByIconImageId(long iconImageId) {
		for (LayoutVersion layoutVersion :
				findByIconImageId(
					iconImageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where iconImageId = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByIconImageId(long iconImageId) {
		FinderPath finderPath = _finderPathCountByIconImageId;

		Object[] finderArgs = new Object[] {iconImageId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_ICONIMAGEID_ICONIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(iconImageId);

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

	private static final String _FINDER_COLUMN_ICONIMAGEID_ICONIMAGEID_2 =
		"layoutVersion.iconImageId = ?";

	private FinderPath _finderPathWithPaginationFindByIconImageId_Version;
	private FinderPath _finderPathWithoutPaginationFindByIconImageId_Version;
	private FinderPath _finderPathCountByIconImageId_Version;

	/**
	 * Returns all the layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByIconImageId_Version(
		long iconImageId, int version) {

		return findByIconImageId_Version(
			iconImageId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByIconImageId_Version(
		long iconImageId, int version, int start, int end) {

		return findByIconImageId_Version(
			iconImageId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByIconImageId_Version(
		long iconImageId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByIconImageId_Version(
			iconImageId, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByIconImageId_Version(
		long iconImageId, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByIconImageId_Version;
			finderArgs = new Object[] {iconImageId, version};
		}
		else {
			finderPath = _finderPathWithPaginationFindByIconImageId_Version;
			finderArgs = new Object[] {
				iconImageId, version, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((iconImageId != layoutVersion.getIconImageId()) ||
						(version != layoutVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_ICONIMAGEID_VERSION_ICONIMAGEID_2);

			query.append(_FINDER_COLUMN_ICONIMAGEID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(iconImageId);

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByIconImageId_Version_First(
			long iconImageId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByIconImageId_Version_First(
			iconImageId, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("iconImageId=");
		msg.append(iconImageId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByIconImageId_Version_First(
		long iconImageId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByIconImageId_Version(
			iconImageId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByIconImageId_Version_Last(
			long iconImageId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByIconImageId_Version_Last(
			iconImageId, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("iconImageId=");
		msg.append(iconImageId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByIconImageId_Version_Last(
		long iconImageId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByIconImageId_Version(iconImageId, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByIconImageId_Version(
			iconImageId, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where iconImageId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByIconImageId_Version_PrevAndNext(
			long layoutVersionId, long iconImageId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByIconImageId_Version_PrevAndNext(
				session, layoutVersion, iconImageId, version, orderByComparator,
				true);

			array[1] = layoutVersion;

			array[2] = getByIconImageId_Version_PrevAndNext(
				session, layoutVersion, iconImageId, version, orderByComparator,
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

	protected LayoutVersion getByIconImageId_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long iconImageId,
		int version, OrderByComparator<LayoutVersion> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_ICONIMAGEID_VERSION_ICONIMAGEID_2);

		query.append(_FINDER_COLUMN_ICONIMAGEID_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(iconImageId);

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where iconImageId = &#63; and version = &#63; from the database.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 */
	@Override
	public void removeByIconImageId_Version(long iconImageId, int version) {
		for (LayoutVersion layoutVersion :
				findByIconImageId_Version(
					iconImageId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where iconImageId = &#63; and version = &#63;.
	 *
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByIconImageId_Version(long iconImageId, int version) {
		FinderPath finderPath = _finderPathCountByIconImageId_Version;

		Object[] finderArgs = new Object[] {iconImageId, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_ICONIMAGEID_VERSION_ICONIMAGEID_2);

			query.append(_FINDER_COLUMN_ICONIMAGEID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(iconImageId);

				qPos.add(version);

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

	private static final String
		_FINDER_COLUMN_ICONIMAGEID_VERSION_ICONIMAGEID_2 =
			"layoutVersion.iconImageId = ? AND ";

	private static final String _FINDER_COLUMN_ICONIMAGEID_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByLayoutPrototypeUuid;
	private FinderPath _finderPathWithoutPaginationFindByLayoutPrototypeUuid;
	private FinderPath _finderPathCountByLayoutPrototypeUuid;

	/**
	 * Returns all the layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByLayoutPrototypeUuid(
		String layoutPrototypeUuid) {

		return findByLayoutPrototypeUuid(
			layoutPrototypeUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByLayoutPrototypeUuid(
		String layoutPrototypeUuid, int start, int end) {

		return findByLayoutPrototypeUuid(layoutPrototypeUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByLayoutPrototypeUuid(
		String layoutPrototypeUuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByLayoutPrototypeUuid(
			layoutPrototypeUuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByLayoutPrototypeUuid(
		String layoutPrototypeUuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByLayoutPrototypeUuid;
			finderArgs = new Object[] {layoutPrototypeUuid};
		}
		else {
			finderPath = _finderPathWithPaginationFindByLayoutPrototypeUuid;
			finderArgs = new Object[] {
				layoutPrototypeUuid, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if (!layoutPrototypeUuid.equals(
							layoutVersion.getLayoutPrototypeUuid())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid.isEmpty()) {
				query.append(
					_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_3);
			}
			else {
				bindLayoutPrototypeUuid = true;

				query.append(
					_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutPrototypeUuid) {
					qPos.add(layoutPrototypeUuid);
				}

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByLayoutPrototypeUuid_First(
			String layoutPrototypeUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByLayoutPrototypeUuid_First(
			layoutPrototypeUuid, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByLayoutPrototypeUuid_First(
		String layoutPrototypeUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByLayoutPrototypeUuid(
			layoutPrototypeUuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByLayoutPrototypeUuid_Last(
			String layoutPrototypeUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByLayoutPrototypeUuid_Last(
			layoutPrototypeUuid, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByLayoutPrototypeUuid_Last(
		String layoutPrototypeUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByLayoutPrototypeUuid(layoutPrototypeUuid);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByLayoutPrototypeUuid(
			layoutPrototypeUuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByLayoutPrototypeUuid_PrevAndNext(
			long layoutVersionId, String layoutPrototypeUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByLayoutPrototypeUuid_PrevAndNext(
				session, layoutVersion, layoutPrototypeUuid, orderByComparator,
				true);

			array[1] = layoutVersion;

			array[2] = getByLayoutPrototypeUuid_PrevAndNext(
				session, layoutVersion, layoutPrototypeUuid, orderByComparator,
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

	protected LayoutVersion getByLayoutPrototypeUuid_PrevAndNext(
		Session session, LayoutVersion layoutVersion,
		String layoutPrototypeUuid,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		boolean bindLayoutPrototypeUuid = false;

		if (layoutPrototypeUuid.isEmpty()) {
			query.append(
				_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_3);
		}
		else {
			bindLayoutPrototypeUuid = true;

			query.append(
				_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_2);
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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindLayoutPrototypeUuid) {
			qPos.add(layoutPrototypeUuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where layoutPrototypeUuid = &#63; from the database.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 */
	@Override
	public void removeByLayoutPrototypeUuid(String layoutPrototypeUuid) {
		for (LayoutVersion layoutVersion :
				findByLayoutPrototypeUuid(
					layoutPrototypeUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByLayoutPrototypeUuid(String layoutPrototypeUuid) {
		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		FinderPath finderPath = _finderPathCountByLayoutPrototypeUuid;

		Object[] finderArgs = new Object[] {layoutPrototypeUuid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid.isEmpty()) {
				query.append(
					_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_3);
			}
			else {
				bindLayoutPrototypeUuid = true;

				query.append(
					_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutPrototypeUuid) {
					qPos.add(layoutPrototypeUuid);
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

	private static final String
		_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_2 =
			"layoutVersion.layoutPrototypeUuid = ?";

	private static final String
		_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_LAYOUTPROTOTYPEUUID_3 =
			"(layoutVersion.layoutPrototypeUuid IS NULL OR layoutVersion.layoutPrototypeUuid = '')";

	private FinderPath
		_finderPathWithPaginationFindByLayoutPrototypeUuid_Version;
	private FinderPath
		_finderPathWithoutPaginationFindByLayoutPrototypeUuid_Version;
	private FinderPath _finderPathCountByLayoutPrototypeUuid_Version;

	/**
	 * Returns all the layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version) {

		return findByLayoutPrototypeUuid_Version(
			layoutPrototypeUuid, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version, int start, int end) {

		return findByLayoutPrototypeUuid_Version(
			layoutPrototypeUuid, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByLayoutPrototypeUuid_Version(
			layoutPrototypeUuid, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath =
				_finderPathWithoutPaginationFindByLayoutPrototypeUuid_Version;
			finderArgs = new Object[] {layoutPrototypeUuid, version};
		}
		else {
			finderPath =
				_finderPathWithPaginationFindByLayoutPrototypeUuid_Version;
			finderArgs = new Object[] {
				layoutPrototypeUuid, version, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if (!layoutPrototypeUuid.equals(
							layoutVersion.getLayoutPrototypeUuid()) ||
						(version != layoutVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid.isEmpty()) {
				query.append(
					_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_VERSION_LAYOUTPROTOTYPEUUID_3);
			}
			else {
				bindLayoutPrototypeUuid = true;

				query.append(
					_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_VERSION_LAYOUTPROTOTYPEUUID_2);
			}

			query.append(_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutPrototypeUuid) {
					qPos.add(layoutPrototypeUuid);
				}

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByLayoutPrototypeUuid_Version_First(
			String layoutPrototypeUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByLayoutPrototypeUuid_Version_First(
			layoutPrototypeUuid, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByLayoutPrototypeUuid_Version_First(
		String layoutPrototypeUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByLayoutPrototypeUuid_Version(
			layoutPrototypeUuid, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByLayoutPrototypeUuid_Version_Last(
			String layoutPrototypeUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByLayoutPrototypeUuid_Version_Last(
			layoutPrototypeUuid, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByLayoutPrototypeUuid_Version_Last(
		String layoutPrototypeUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByLayoutPrototypeUuid_Version(
			layoutPrototypeUuid, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByLayoutPrototypeUuid_Version(
			layoutPrototypeUuid, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByLayoutPrototypeUuid_Version_PrevAndNext(
			long layoutVersionId, String layoutPrototypeUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByLayoutPrototypeUuid_Version_PrevAndNext(
				session, layoutVersion, layoutPrototypeUuid, version,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByLayoutPrototypeUuid_Version_PrevAndNext(
				session, layoutVersion, layoutPrototypeUuid, version,
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

	protected LayoutVersion getByLayoutPrototypeUuid_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion,
		String layoutPrototypeUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		boolean bindLayoutPrototypeUuid = false;

		if (layoutPrototypeUuid.isEmpty()) {
			query.append(
				_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_VERSION_LAYOUTPROTOTYPEUUID_3);
		}
		else {
			bindLayoutPrototypeUuid = true;

			query.append(
				_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_VERSION_LAYOUTPROTOTYPEUUID_2);
		}

		query.append(_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindLayoutPrototypeUuid) {
			qPos.add(layoutPrototypeUuid);
		}

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where layoutPrototypeUuid = &#63; and version = &#63; from the database.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 */
	@Override
	public void removeByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version) {

		for (LayoutVersion layoutVersion :
				findByLayoutPrototypeUuid_Version(
					layoutPrototypeUuid, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByLayoutPrototypeUuid_Version(
		String layoutPrototypeUuid, int version) {

		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		FinderPath finderPath = _finderPathCountByLayoutPrototypeUuid_Version;

		Object[] finderArgs = new Object[] {layoutPrototypeUuid, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid.isEmpty()) {
				query.append(
					_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_VERSION_LAYOUTPROTOTYPEUUID_3);
			}
			else {
				bindLayoutPrototypeUuid = true;

				query.append(
					_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_VERSION_LAYOUTPROTOTYPEUUID_2);
			}

			query.append(_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutPrototypeUuid) {
					qPos.add(layoutPrototypeUuid);
				}

				qPos.add(version);

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

	private static final String
		_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_VERSION_LAYOUTPROTOTYPEUUID_2 =
			"layoutVersion.layoutPrototypeUuid = ? AND ";

	private static final String
		_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_VERSION_LAYOUTPROTOTYPEUUID_3 =
			"(layoutVersion.layoutPrototypeUuid IS NULL OR layoutVersion.layoutPrototypeUuid = '') AND ";

	private static final String
		_FINDER_COLUMN_LAYOUTPROTOTYPEUUID_VERSION_VERSION_2 =
			"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindBySourcePrototypeLayoutUuid;
	private FinderPath
		_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid;
	private FinderPath _finderPathCountBySourcePrototypeLayoutUuid;

	/**
	 * Returns all the layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid) {

		return findBySourcePrototypeLayoutUuid(
			sourcePrototypeLayoutUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid, int start, int end) {

		return findBySourcePrototypeLayoutUuid(
			sourcePrototypeLayoutUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findBySourcePrototypeLayoutUuid(
			sourcePrototypeLayoutUuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		sourcePrototypeLayoutUuid = Objects.toString(
			sourcePrototypeLayoutUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath =
				_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid;
			finderArgs = new Object[] {sourcePrototypeLayoutUuid};
		}
		else {
			finderPath =
				_finderPathWithPaginationFindBySourcePrototypeLayoutUuid;
			finderArgs = new Object[] {
				sourcePrototypeLayoutUuid, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if (!sourcePrototypeLayoutUuid.equals(
							layoutVersion.getSourcePrototypeLayoutUuid())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid.isEmpty()) {
				query.append(
					_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_3);
			}
			else {
				bindSourcePrototypeLayoutUuid = true;

				query.append(
					_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSourcePrototypeLayoutUuid) {
					qPos.add(sourcePrototypeLayoutUuid);
				}

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findBySourcePrototypeLayoutUuid_First(
			String sourcePrototypeLayoutUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchBySourcePrototypeLayoutUuid_First(
			sourcePrototypeLayoutUuid, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("sourcePrototypeLayoutUuid=");
		msg.append(sourcePrototypeLayoutUuid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchBySourcePrototypeLayoutUuid_First(
		String sourcePrototypeLayoutUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findBySourcePrototypeLayoutUuid(
			sourcePrototypeLayoutUuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findBySourcePrototypeLayoutUuid_Last(
			String sourcePrototypeLayoutUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchBySourcePrototypeLayoutUuid_Last(
			sourcePrototypeLayoutUuid, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("sourcePrototypeLayoutUuid=");
		msg.append(sourcePrototypeLayoutUuid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchBySourcePrototypeLayoutUuid_Last(
		String sourcePrototypeLayoutUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countBySourcePrototypeLayoutUuid(sourcePrototypeLayoutUuid);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findBySourcePrototypeLayoutUuid(
			sourcePrototypeLayoutUuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findBySourcePrototypeLayoutUuid_PrevAndNext(
			long layoutVersionId, String sourcePrototypeLayoutUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		sourcePrototypeLayoutUuid = Objects.toString(
			sourcePrototypeLayoutUuid, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getBySourcePrototypeLayoutUuid_PrevAndNext(
				session, layoutVersion, sourcePrototypeLayoutUuid,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getBySourcePrototypeLayoutUuid_PrevAndNext(
				session, layoutVersion, sourcePrototypeLayoutUuid,
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

	protected LayoutVersion getBySourcePrototypeLayoutUuid_PrevAndNext(
		Session session, LayoutVersion layoutVersion,
		String sourcePrototypeLayoutUuid,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		boolean bindSourcePrototypeLayoutUuid = false;

		if (sourcePrototypeLayoutUuid.isEmpty()) {
			query.append(
				_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_3);
		}
		else {
			bindSourcePrototypeLayoutUuid = true;

			query.append(
				_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_2);
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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindSourcePrototypeLayoutUuid) {
			qPos.add(sourcePrototypeLayoutUuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where sourcePrototypeLayoutUuid = &#63; from the database.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 */
	@Override
	public void removeBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid) {

		for (LayoutVersion layoutVersion :
				findBySourcePrototypeLayoutUuid(
					sourcePrototypeLayoutUuid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the number of matching layout versions
	 */
	@Override
	public int countBySourcePrototypeLayoutUuid(
		String sourcePrototypeLayoutUuid) {

		sourcePrototypeLayoutUuid = Objects.toString(
			sourcePrototypeLayoutUuid, "");

		FinderPath finderPath = _finderPathCountBySourcePrototypeLayoutUuid;

		Object[] finderArgs = new Object[] {sourcePrototypeLayoutUuid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid.isEmpty()) {
				query.append(
					_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_3);
			}
			else {
				bindSourcePrototypeLayoutUuid = true;

				query.append(
					_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSourcePrototypeLayoutUuid) {
					qPos.add(sourcePrototypeLayoutUuid);
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

	private static final String
		_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_2 =
			"layoutVersion.sourcePrototypeLayoutUuid = ?";

	private static final String
		_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_SOURCEPROTOTYPELAYOUTUUID_3 =
			"(layoutVersion.sourcePrototypeLayoutUuid IS NULL OR layoutVersion.sourcePrototypeLayoutUuid = '')";

	private FinderPath
		_finderPathWithPaginationFindBySourcePrototypeLayoutUuid_Version;
	private FinderPath
		_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid_Version;
	private FinderPath _finderPathCountBySourcePrototypeLayoutUuid_Version;

	/**
	 * Returns all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version) {

		return findBySourcePrototypeLayoutUuid_Version(
			sourcePrototypeLayoutUuid, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version, int start, int end) {

		return findBySourcePrototypeLayoutUuid_Version(
			sourcePrototypeLayoutUuid, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findBySourcePrototypeLayoutUuid_Version(
			sourcePrototypeLayoutUuid, version, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		sourcePrototypeLayoutUuid = Objects.toString(
			sourcePrototypeLayoutUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath =
				_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid_Version;
			finderArgs = new Object[] {sourcePrototypeLayoutUuid, version};
		}
		else {
			finderPath =
				_finderPathWithPaginationFindBySourcePrototypeLayoutUuid_Version;
			finderArgs = new Object[] {
				sourcePrototypeLayoutUuid, version, start, end,
				orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if (!sourcePrototypeLayoutUuid.equals(
							layoutVersion.getSourcePrototypeLayoutUuid()) ||
						(version != layoutVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid.isEmpty()) {
				query.append(
					_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_VERSION_SOURCEPROTOTYPELAYOUTUUID_3);
			}
			else {
				bindSourcePrototypeLayoutUuid = true;

				query.append(
					_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_VERSION_SOURCEPROTOTYPELAYOUTUUID_2);
			}

			query.append(
				_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSourcePrototypeLayoutUuid) {
					qPos.add(sourcePrototypeLayoutUuid);
				}

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findBySourcePrototypeLayoutUuid_Version_First(
			String sourcePrototypeLayoutUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion =
			fetchBySourcePrototypeLayoutUuid_Version_First(
				sourcePrototypeLayoutUuid, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("sourcePrototypeLayoutUuid=");
		msg.append(sourcePrototypeLayoutUuid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchBySourcePrototypeLayoutUuid_Version_First(
		String sourcePrototypeLayoutUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findBySourcePrototypeLayoutUuid_Version(
			sourcePrototypeLayoutUuid, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findBySourcePrototypeLayoutUuid_Version_Last(
			String sourcePrototypeLayoutUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion =
			fetchBySourcePrototypeLayoutUuid_Version_Last(
				sourcePrototypeLayoutUuid, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("sourcePrototypeLayoutUuid=");
		msg.append(sourcePrototypeLayoutUuid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchBySourcePrototypeLayoutUuid_Version_Last(
		String sourcePrototypeLayoutUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countBySourcePrototypeLayoutUuid_Version(
			sourcePrototypeLayoutUuid, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findBySourcePrototypeLayoutUuid_Version(
			sourcePrototypeLayoutUuid, version, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findBySourcePrototypeLayoutUuid_Version_PrevAndNext(
			long layoutVersionId, String sourcePrototypeLayoutUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		sourcePrototypeLayoutUuid = Objects.toString(
			sourcePrototypeLayoutUuid, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getBySourcePrototypeLayoutUuid_Version_PrevAndNext(
				session, layoutVersion, sourcePrototypeLayoutUuid, version,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getBySourcePrototypeLayoutUuid_Version_PrevAndNext(
				session, layoutVersion, sourcePrototypeLayoutUuid, version,
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

	protected LayoutVersion getBySourcePrototypeLayoutUuid_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion,
		String sourcePrototypeLayoutUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		boolean bindSourcePrototypeLayoutUuid = false;

		if (sourcePrototypeLayoutUuid.isEmpty()) {
			query.append(
				_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_VERSION_SOURCEPROTOTYPELAYOUTUUID_3);
		}
		else {
			bindSourcePrototypeLayoutUuid = true;

			query.append(
				_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_VERSION_SOURCEPROTOTYPELAYOUTUUID_2);
		}

		query.append(
			_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindSourcePrototypeLayoutUuid) {
			qPos.add(sourcePrototypeLayoutUuid);
		}

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63; from the database.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 */
	@Override
	public void removeBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version) {

		for (LayoutVersion layoutVersion :
				findBySourcePrototypeLayoutUuid_Version(
					sourcePrototypeLayoutUuid, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countBySourcePrototypeLayoutUuid_Version(
		String sourcePrototypeLayoutUuid, int version) {

		sourcePrototypeLayoutUuid = Objects.toString(
			sourcePrototypeLayoutUuid, "");

		FinderPath finderPath =
			_finderPathCountBySourcePrototypeLayoutUuid_Version;

		Object[] finderArgs = new Object[] {sourcePrototypeLayoutUuid, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid.isEmpty()) {
				query.append(
					_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_VERSION_SOURCEPROTOTYPELAYOUTUUID_3);
			}
			else {
				bindSourcePrototypeLayoutUuid = true;

				query.append(
					_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_VERSION_SOURCEPROTOTYPELAYOUTUUID_2);
			}

			query.append(
				_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSourcePrototypeLayoutUuid) {
					qPos.add(sourcePrototypeLayoutUuid);
				}

				qPos.add(version);

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

	private static final String
		_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_VERSION_SOURCEPROTOTYPELAYOUTUUID_2 =
			"layoutVersion.sourcePrototypeLayoutUuid = ? AND ";

	private static final String
		_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_VERSION_SOURCEPROTOTYPELAYOUTUUID_3 =
			"(layoutVersion.sourcePrototypeLayoutUuid IS NULL OR layoutVersion.sourcePrototypeLayoutUuid = '') AND ";

	private static final String
		_FINDER_COLUMN_SOURCEPROTOTYPELAYOUTUUID_VERSION_VERSION_2 =
			"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P;
	private FinderPath _finderPathCountByG_P;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P(long groupId, boolean privateLayout) {
		return findByG_P(
			groupId, privateLayout, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P(
		long groupId, boolean privateLayout, int start, int end) {

		return findByG_P(groupId, privateLayout, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P(
			groupId, privateLayout, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P(
		long groupId, boolean privateLayout, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P;
			finderArgs = new Object[] {groupId, privateLayout};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P;
			finderArgs = new Object[] {
				groupId, privateLayout, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_First(
			long groupId, boolean privateLayout,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_First(
			groupId, privateLayout, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_First(
		long groupId, boolean privateLayout,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P(
			groupId, privateLayout, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_Last(
			long groupId, boolean privateLayout,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_Last(
			groupId, privateLayout, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_Last(
		long groupId, boolean privateLayout,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P(groupId, privateLayout);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P(
			groupId, privateLayout, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_PrevAndNext(
				session, layoutVersion, groupId, privateLayout,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_PrevAndNext(
				session, layoutVersion, groupId, privateLayout,
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

	protected LayoutVersion getByG_P_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 */
	@Override
	public void removeByG_P(long groupId, boolean privateLayout) {
		for (LayoutVersion layoutVersion :
				findByG_P(
					groupId, privateLayout, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P(long groupId, boolean privateLayout) {
		FinderPath finderPath = _finderPathCountByG_P;

		Object[] finderArgs = new Object[] {groupId, privateLayout};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

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

	private static final String _FINDER_COLUMN_G_P_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_P_Version;
	private FinderPath _finderPathCountByG_P_Version;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_Version(
		long groupId, boolean privateLayout, int version) {

		return findByG_P_Version(
			groupId, privateLayout, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_Version(
		long groupId, boolean privateLayout, int version, int start, int end) {

		return findByG_P_Version(
			groupId, privateLayout, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_Version(
		long groupId, boolean privateLayout, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P_Version(
			groupId, privateLayout, version, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_Version(
		long groupId, boolean privateLayout, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_Version;
			finderArgs = new Object[] {groupId, privateLayout, version};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_Version;
			finderArgs = new Object[] {
				groupId, privateLayout, version, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout()) ||
						(version != layoutVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_Version_First(
			long groupId, boolean privateLayout, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_Version_First(
			groupId, privateLayout, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_Version_First(
		long groupId, boolean privateLayout, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P_Version(
			groupId, privateLayout, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_Version_Last(
			long groupId, boolean privateLayout, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_Version_Last(
			groupId, privateLayout, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_Version_Last(
		long groupId, boolean privateLayout, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P_Version(groupId, privateLayout, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P_Version(
			groupId, privateLayout, version, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			int version, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_Version_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, version,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_Version_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, version,
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

	protected LayoutVersion getByG_P_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_VERSION_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_VERSION_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 */
	@Override
	public void removeByG_P_Version(
		long groupId, boolean privateLayout, int version) {

		for (LayoutVersion layoutVersion :
				findByG_P_Version(
					groupId, privateLayout, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_Version(
		long groupId, boolean privateLayout, int version) {

		FinderPath finderPath = _finderPathCountByG_P_Version;

		Object[] finderArgs = new Object[] {groupId, privateLayout, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_G_P_VERSION_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_VERSION_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_T;
	private FinderPath _finderPathWithoutPaginationFindByG_T;
	private FinderPath _finderPathCountByG_T;

	/**
	 * Returns all the layout versions where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_T(long groupId, String type) {
		return findByG_T(
			groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_T(
		long groupId, String type, int start, int end) {

		return findByG_T(groupId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_T(
		long groupId, String type, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_T(groupId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_T(
		long groupId, String type, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		type = Objects.toString(type, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_T;
			finderArgs = new Object[] {groupId, type};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_T;
			finderArgs = new Object[] {
				groupId, type, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						!type.equals(layoutVersion.getType())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_G_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindType) {
					qPos.add(type);
				}

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_T_First(
			long groupId, String type,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_T_First(
			groupId, type, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_T_First(
		long groupId, String type,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_T(
			groupId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_T_Last(
			long groupId, String type,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_T_Last(
			groupId, type, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_T_Last(
		long groupId, String type,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_T(groupId, type);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_T(
			groupId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and type = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_T_PrevAndNext(
			long layoutVersionId, long groupId, String type,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		type = Objects.toString(type, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_T_PrevAndNext(
				session, layoutVersion, groupId, type, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_T_PrevAndNext(
				session, layoutVersion, groupId, type, orderByComparator,
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

	protected LayoutVersion getByG_T_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId, String type,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_T_GROUPID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_G_T_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_TYPE_2);
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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 */
	@Override
	public void removeByG_T(long groupId, String type) {
		for (LayoutVersion layoutVersion :
				findByG_T(
					groupId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_T(long groupId, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByG_T;

		Object[] finderArgs = new Object[] {groupId, type};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_T_GROUPID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_G_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindType) {
					qPos.add(type);
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

	private static final String _FINDER_COLUMN_G_T_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_TYPE_2 =
		"layoutVersion.type = ?";

	private static final String _FINDER_COLUMN_G_T_TYPE_3 =
		"(layoutVersion.type IS NULL OR layoutVersion.type = '')";

	private FinderPath _finderPathWithPaginationFindByG_T_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_T_Version;
	private FinderPath _finderPathCountByG_T_Version;

	/**
	 * Returns all the layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_T_Version(
		long groupId, String type, int version) {

		return findByG_T_Version(
			groupId, type, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_T_Version(
		long groupId, String type, int version, int start, int end) {

		return findByG_T_Version(groupId, type, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_T_Version(
		long groupId, String type, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_T_Version(
			groupId, type, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_T_Version(
		long groupId, String type, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		type = Objects.toString(type, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_T_Version;
			finderArgs = new Object[] {groupId, type, version};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_T_Version;
			finderArgs = new Object[] {
				groupId, type, version, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						!type.equals(layoutVersion.getType()) ||
						(version != layoutVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_T_VERSION_GROUPID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_G_T_VERSION_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_T_VERSION_TYPE_2);
			}

			query.append(_FINDER_COLUMN_G_T_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindType) {
					qPos.add(type);
				}

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_T_Version_First(
			long groupId, String type, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_T_Version_First(
			groupId, type, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_T_Version_First(
		long groupId, String type, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_T_Version(
			groupId, type, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_T_Version_Last(
			long groupId, String type, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_T_Version_Last(
			groupId, type, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", type=");
		msg.append(type);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_T_Version_Last(
		long groupId, String type, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_T_Version(groupId, type, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_T_Version(
			groupId, type, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_T_Version_PrevAndNext(
			long layoutVersionId, long groupId, String type, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		type = Objects.toString(type, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_T_Version_PrevAndNext(
				session, layoutVersion, groupId, type, version,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_T_Version_PrevAndNext(
				session, layoutVersion, groupId, type, version,
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

	protected LayoutVersion getByG_T_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId, String type,
		int version, OrderByComparator<LayoutVersion> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_T_VERSION_GROUPID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_G_T_VERSION_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_T_VERSION_TYPE_2);
		}

		query.append(_FINDER_COLUMN_G_T_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (bindType) {
			qPos.add(type);
		}

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and type = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 */
	@Override
	public void removeByG_T_Version(long groupId, String type, int version) {
		for (LayoutVersion layoutVersion :
				findByG_T_Version(
					groupId, type, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param type the type
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_T_Version(long groupId, String type, int version) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByG_T_Version;

		Object[] finderArgs = new Object[] {groupId, type, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_T_VERSION_GROUPID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_G_T_VERSION_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_T_VERSION_TYPE_2);
			}

			query.append(_FINDER_COLUMN_G_T_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (bindType) {
					qPos.add(type);
				}

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_G_T_VERSION_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_T_VERSION_TYPE_2 =
		"layoutVersion.type = ? AND ";

	private static final String _FINDER_COLUMN_G_T_VERSION_TYPE_3 =
		"(layoutVersion.type IS NULL OR layoutVersion.type = '') AND ";

	private static final String _FINDER_COLUMN_G_T_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByC_L;
	private FinderPath _finderPathWithoutPaginationFindByC_L;
	private FinderPath _finderPathCountByC_L;

	/**
	 * Returns all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_L(
		long companyId, String layoutPrototypeUuid) {

		return findByC_L(
			companyId, layoutPrototypeUuid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_L(
		long companyId, String layoutPrototypeUuid, int start, int end) {

		return findByC_L(companyId, layoutPrototypeUuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_L(
		long companyId, String layoutPrototypeUuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByC_L(
			companyId, layoutPrototypeUuid, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_L(
		long companyId, String layoutPrototypeUuid, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_L;
			finderArgs = new Object[] {companyId, layoutPrototypeUuid};
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_L;
			finderArgs = new Object[] {
				companyId, layoutPrototypeUuid, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((companyId != layoutVersion.getCompanyId()) ||
						!layoutPrototypeUuid.equals(
							layoutVersion.getLayoutPrototypeUuid())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_L_COMPANYID_2);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_3);
			}
			else {
				bindLayoutPrototypeUuid = true;

				query.append(_FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindLayoutPrototypeUuid) {
					qPos.add(layoutPrototypeUuid);
				}

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByC_L_First(
			long companyId, String layoutPrototypeUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByC_L_First(
			companyId, layoutPrototypeUuid, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByC_L_First(
		long companyId, String layoutPrototypeUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByC_L(
			companyId, layoutPrototypeUuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByC_L_Last(
			long companyId, String layoutPrototypeUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByC_L_Last(
			companyId, layoutPrototypeUuid, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByC_L_Last(
		long companyId, String layoutPrototypeUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByC_L(companyId, layoutPrototypeUuid);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByC_L(
			companyId, layoutPrototypeUuid, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByC_L_PrevAndNext(
			long layoutVersionId, long companyId, String layoutPrototypeUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByC_L_PrevAndNext(
				session, layoutVersion, companyId, layoutPrototypeUuid,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByC_L_PrevAndNext(
				session, layoutVersion, companyId, layoutPrototypeUuid,
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

	protected LayoutVersion getByC_L_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long companyId,
		String layoutPrototypeUuid,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_C_L_COMPANYID_2);

		boolean bindLayoutPrototypeUuid = false;

		if (layoutPrototypeUuid.isEmpty()) {
			query.append(_FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_3);
		}
		else {
			bindLayoutPrototypeUuid = true;

			query.append(_FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_2);
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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindLayoutPrototypeUuid) {
			qPos.add(layoutPrototypeUuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 */
	@Override
	public void removeByC_L(long companyId, String layoutPrototypeUuid) {
		for (LayoutVersion layoutVersion :
				findByC_L(
					companyId, layoutPrototypeUuid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where companyId = &#63; and layoutPrototypeUuid = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByC_L(long companyId, String layoutPrototypeUuid) {
		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		FinderPath finderPath = _finderPathCountByC_L;

		Object[] finderArgs = new Object[] {companyId, layoutPrototypeUuid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_L_COMPANYID_2);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_3);
			}
			else {
				bindLayoutPrototypeUuid = true;

				query.append(_FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindLayoutPrototypeUuid) {
					qPos.add(layoutPrototypeUuid);
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

	private static final String _FINDER_COLUMN_C_L_COMPANYID_2 =
		"layoutVersion.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_2 =
		"layoutVersion.layoutPrototypeUuid = ?";

	private static final String _FINDER_COLUMN_C_L_LAYOUTPROTOTYPEUUID_3 =
		"(layoutVersion.layoutPrototypeUuid IS NULL OR layoutVersion.layoutPrototypeUuid = '')";

	private FinderPath _finderPathWithPaginationFindByC_L_Version;
	private FinderPath _finderPathWithoutPaginationFindByC_L_Version;
	private FinderPath _finderPathCountByC_L_Version;

	/**
	 * Returns all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version) {

		return findByC_L_Version(
			companyId, layoutPrototypeUuid, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version, int start,
		int end) {

		return findByC_L_Version(
			companyId, layoutPrototypeUuid, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator) {

		return findByC_L_Version(
			companyId, layoutPrototypeUuid, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_L_Version;
			finderArgs = new Object[] {companyId, layoutPrototypeUuid, version};
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_L_Version;
			finderArgs = new Object[] {
				companyId, layoutPrototypeUuid, version, start, end,
				orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((companyId != layoutVersion.getCompanyId()) ||
						!layoutPrototypeUuid.equals(
							layoutVersion.getLayoutPrototypeUuid()) ||
						(version != layoutVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_L_VERSION_COMPANYID_2);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_C_L_VERSION_LAYOUTPROTOTYPEUUID_3);
			}
			else {
				bindLayoutPrototypeUuid = true;

				query.append(_FINDER_COLUMN_C_L_VERSION_LAYOUTPROTOTYPEUUID_2);
			}

			query.append(_FINDER_COLUMN_C_L_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindLayoutPrototypeUuid) {
					qPos.add(layoutPrototypeUuid);
				}

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByC_L_Version_First(
			long companyId, String layoutPrototypeUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByC_L_Version_First(
			companyId, layoutPrototypeUuid, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByC_L_Version_First(
		long companyId, String layoutPrototypeUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByC_L_Version(
			companyId, layoutPrototypeUuid, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByC_L_Version_Last(
			long companyId, String layoutPrototypeUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByC_L_Version_Last(
			companyId, layoutPrototypeUuid, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("companyId=");
		msg.append(companyId);

		msg.append(", layoutPrototypeUuid=");
		msg.append(layoutPrototypeUuid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByC_L_Version_Last(
		long companyId, String layoutPrototypeUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByC_L_Version(companyId, layoutPrototypeUuid, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByC_L_Version(
			companyId, layoutPrototypeUuid, version, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByC_L_Version_PrevAndNext(
			long layoutVersionId, long companyId, String layoutPrototypeUuid,
			int version, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByC_L_Version_PrevAndNext(
				session, layoutVersion, companyId, layoutPrototypeUuid, version,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByC_L_Version_PrevAndNext(
				session, layoutVersion, companyId, layoutPrototypeUuid, version,
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

	protected LayoutVersion getByC_L_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long companyId,
		String layoutPrototypeUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_C_L_VERSION_COMPANYID_2);

		boolean bindLayoutPrototypeUuid = false;

		if (layoutPrototypeUuid.isEmpty()) {
			query.append(_FINDER_COLUMN_C_L_VERSION_LAYOUTPROTOTYPEUUID_3);
		}
		else {
			bindLayoutPrototypeUuid = true;

			query.append(_FINDER_COLUMN_C_L_VERSION_LAYOUTPROTOTYPEUUID_2);
		}

		query.append(_FINDER_COLUMN_C_L_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(companyId);

		if (bindLayoutPrototypeUuid) {
			qPos.add(layoutPrototypeUuid);
		}

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 */
	@Override
	public void removeByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version) {

		for (LayoutVersion layoutVersion :
				findByC_L_Version(
					companyId, layoutPrototypeUuid, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where companyId = &#63; and layoutPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param companyId the company ID
	 * @param layoutPrototypeUuid the layout prototype uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByC_L_Version(
		long companyId, String layoutPrototypeUuid, int version) {

		layoutPrototypeUuid = Objects.toString(layoutPrototypeUuid, "");

		FinderPath finderPath = _finderPathCountByC_L_Version;

		Object[] finderArgs = new Object[] {
			companyId, layoutPrototypeUuid, version
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_L_VERSION_COMPANYID_2);

			boolean bindLayoutPrototypeUuid = false;

			if (layoutPrototypeUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_C_L_VERSION_LAYOUTPROTOTYPEUUID_3);
			}
			else {
				bindLayoutPrototypeUuid = true;

				query.append(_FINDER_COLUMN_C_L_VERSION_LAYOUTPROTOTYPEUUID_2);
			}

			query.append(_FINDER_COLUMN_C_L_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(companyId);

				if (bindLayoutPrototypeUuid) {
					qPos.add(layoutPrototypeUuid);
				}

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_C_L_VERSION_COMPANYID_2 =
		"layoutVersion.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_L_VERSION_LAYOUTPROTOTYPEUUID_2 =
			"layoutVersion.layoutPrototypeUuid = ? AND ";

	private static final String
		_FINDER_COLUMN_C_L_VERSION_LAYOUTPROTOTYPEUUID_3 =
			"(layoutVersion.layoutPrototypeUuid IS NULL OR layoutVersion.layoutPrototypeUuid = '') AND ";

	private static final String _FINDER_COLUMN_C_L_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByP_I;
	private FinderPath _finderPathWithoutPaginationFindByP_I;
	private FinderPath _finderPathCountByP_I;

	/**
	 * Returns all the layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByP_I(
		boolean privateLayout, long iconImageId) {

		return findByP_I(
			privateLayout, iconImageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByP_I(
		boolean privateLayout, long iconImageId, int start, int end) {

		return findByP_I(privateLayout, iconImageId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByP_I(
		boolean privateLayout, long iconImageId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByP_I(
			privateLayout, iconImageId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByP_I(
		boolean privateLayout, long iconImageId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByP_I;
			finderArgs = new Object[] {privateLayout, iconImageId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByP_I;
			finderArgs = new Object[] {
				privateLayout, iconImageId, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((privateLayout != layoutVersion.isPrivateLayout()) ||
						(iconImageId != layoutVersion.getIconImageId())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_P_I_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_P_I_ICONIMAGEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(privateLayout);

				qPos.add(iconImageId);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByP_I_First(
			boolean privateLayout, long iconImageId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByP_I_First(
			privateLayout, iconImageId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("privateLayout=");
		msg.append(privateLayout);

		msg.append(", iconImageId=");
		msg.append(iconImageId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByP_I_First(
		boolean privateLayout, long iconImageId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByP_I(
			privateLayout, iconImageId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByP_I_Last(
			boolean privateLayout, long iconImageId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByP_I_Last(
			privateLayout, iconImageId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("privateLayout=");
		msg.append(privateLayout);

		msg.append(", iconImageId=");
		msg.append(iconImageId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByP_I_Last(
		boolean privateLayout, long iconImageId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByP_I(privateLayout, iconImageId);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByP_I(
			privateLayout, iconImageId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByP_I_PrevAndNext(
			long layoutVersionId, boolean privateLayout, long iconImageId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByP_I_PrevAndNext(
				session, layoutVersion, privateLayout, iconImageId,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByP_I_PrevAndNext(
				session, layoutVersion, privateLayout, iconImageId,
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

	protected LayoutVersion getByP_I_PrevAndNext(
		Session session, LayoutVersion layoutVersion, boolean privateLayout,
		long iconImageId, OrderByComparator<LayoutVersion> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_P_I_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_P_I_ICONIMAGEID_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(privateLayout);

		qPos.add(iconImageId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where privateLayout = &#63; and iconImageId = &#63; from the database.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 */
	@Override
	public void removeByP_I(boolean privateLayout, long iconImageId) {
		for (LayoutVersion layoutVersion :
				findByP_I(
					privateLayout, iconImageId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where privateLayout = &#63; and iconImageId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByP_I(boolean privateLayout, long iconImageId) {
		FinderPath finderPath = _finderPathCountByP_I;

		Object[] finderArgs = new Object[] {privateLayout, iconImageId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_P_I_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_P_I_ICONIMAGEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(privateLayout);

				qPos.add(iconImageId);

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

	private static final String _FINDER_COLUMN_P_I_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_P_I_ICONIMAGEID_2 =
		"layoutVersion.iconImageId = ?";

	private FinderPath _finderPathWithPaginationFindByP_I_Version;
	private FinderPath _finderPathWithoutPaginationFindByP_I_Version;
	private FinderPath _finderPathCountByP_I_Version;

	/**
	 * Returns all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByP_I_Version(
		boolean privateLayout, long iconImageId, int version) {

		return findByP_I_Version(
			privateLayout, iconImageId, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByP_I_Version(
		boolean privateLayout, long iconImageId, int version, int start,
		int end) {

		return findByP_I_Version(
			privateLayout, iconImageId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByP_I_Version(
		boolean privateLayout, long iconImageId, int version, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator) {

		return findByP_I_Version(
			privateLayout, iconImageId, version, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByP_I_Version(
		boolean privateLayout, long iconImageId, int version, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByP_I_Version;
			finderArgs = new Object[] {privateLayout, iconImageId, version};
		}
		else {
			finderPath = _finderPathWithPaginationFindByP_I_Version;
			finderArgs = new Object[] {
				privateLayout, iconImageId, version, start, end,
				orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((privateLayout != layoutVersion.isPrivateLayout()) ||
						(iconImageId != layoutVersion.getIconImageId()) ||
						(version != layoutVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_P_I_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_P_I_VERSION_ICONIMAGEID_2);

			query.append(_FINDER_COLUMN_P_I_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(privateLayout);

				qPos.add(iconImageId);

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByP_I_Version_First(
			boolean privateLayout, long iconImageId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByP_I_Version_First(
			privateLayout, iconImageId, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("privateLayout=");
		msg.append(privateLayout);

		msg.append(", iconImageId=");
		msg.append(iconImageId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByP_I_Version_First(
		boolean privateLayout, long iconImageId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByP_I_Version(
			privateLayout, iconImageId, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByP_I_Version_Last(
			boolean privateLayout, long iconImageId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByP_I_Version_Last(
			privateLayout, iconImageId, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("privateLayout=");
		msg.append(privateLayout);

		msg.append(", iconImageId=");
		msg.append(iconImageId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByP_I_Version_Last(
		boolean privateLayout, long iconImageId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByP_I_Version(privateLayout, iconImageId, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByP_I_Version(
			privateLayout, iconImageId, version, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByP_I_Version_PrevAndNext(
			long layoutVersionId, boolean privateLayout, long iconImageId,
			int version, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByP_I_Version_PrevAndNext(
				session, layoutVersion, privateLayout, iconImageId, version,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByP_I_Version_PrevAndNext(
				session, layoutVersion, privateLayout, iconImageId, version,
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

	protected LayoutVersion getByP_I_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, boolean privateLayout,
		long iconImageId, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_P_I_VERSION_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_P_I_VERSION_ICONIMAGEID_2);

		query.append(_FINDER_COLUMN_P_I_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(privateLayout);

		qPos.add(iconImageId);

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63; from the database.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 */
	@Override
	public void removeByP_I_Version(
		boolean privateLayout, long iconImageId, int version) {

		for (LayoutVersion layoutVersion :
				findByP_I_Version(
					privateLayout, iconImageId, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where privateLayout = &#63; and iconImageId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param iconImageId the icon image ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByP_I_Version(
		boolean privateLayout, long iconImageId, int version) {

		FinderPath finderPath = _finderPathCountByP_I_Version;

		Object[] finderArgs = new Object[] {
			privateLayout, iconImageId, version
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_P_I_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_P_I_VERSION_ICONIMAGEID_2);

			query.append(_FINDER_COLUMN_P_I_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(privateLayout);

				qPos.add(iconImageId);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_P_I_VERSION_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_P_I_VERSION_ICONIMAGEID_2 =
		"layoutVersion.iconImageId = ? AND ";

	private static final String _FINDER_COLUMN_P_I_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_C(long classNameId, long classPK) {
		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_C;
			finderArgs = new Object[] {classNameId, classPK};
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((classNameId != layoutVersion.getClassNameId()) ||
						(classPK != layoutVersion.getClassPK())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByC_C_PrevAndNext(
			long layoutVersionId, long classNameId, long classPK,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, layoutVersion, classNameId, classPK, orderByComparator,
				true);

			array[1] = layoutVersion;

			array[2] = getByC_C_PrevAndNext(
				session, layoutVersion, classNameId, classPK, orderByComparator,
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

	protected LayoutVersion getByC_C_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long classNameId,
		long classPK, OrderByComparator<LayoutVersion> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
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
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (LayoutVersion layoutVersion :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"layoutVersion.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"layoutVersion.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByC_C_Version;
	private FinderPath _finderPathWithoutPaginationFindByC_C_Version;
	private FinderPath _finderPathCountByC_C_Version;

	/**
	 * Returns all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_C_Version(
		long classNameId, long classPK, int version) {

		return findByC_C_Version(
			classNameId, classPK, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_C_Version(
		long classNameId, long classPK, int version, int start, int end) {

		return findByC_C_Version(
			classNameId, classPK, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_C_Version(
		long classNameId, long classPK, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByC_C_Version(
			classNameId, classPK, version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByC_C_Version(
		long classNameId, long classPK, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByC_C_Version;
			finderArgs = new Object[] {classNameId, classPK, version};
		}
		else {
			finderPath = _finderPathWithPaginationFindByC_C_Version;
			finderArgs = new Object[] {
				classNameId, classPK, version, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((classNameId != layoutVersion.getClassNameId()) ||
						(classPK != layoutVersion.getClassPK()) ||
						(version != layoutVersion.getVersion())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_C_VERSION_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_VERSION_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByC_C_Version_First(
			long classNameId, long classPK, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByC_C_Version_First(
			classNameId, classPK, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByC_C_Version_First(
		long classNameId, long classPK, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByC_C_Version(
			classNameId, classPK, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByC_C_Version_Last(
			long classNameId, long classPK, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByC_C_Version_Last(
			classNameId, classPK, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByC_C_Version_Last(
		long classNameId, long classPK, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByC_C_Version(classNameId, classPK, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByC_C_Version(
			classNameId, classPK, version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByC_C_Version_PrevAndNext(
			long layoutVersionId, long classNameId, long classPK, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByC_C_Version_PrevAndNext(
				session, layoutVersion, classNameId, classPK, version,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByC_C_Version_PrevAndNext(
				session, layoutVersion, classNameId, classPK, version,
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

	protected LayoutVersion getByC_C_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long classNameId,
		long classPK, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_C_C_VERSION_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_VERSION_CLASSPK_2);

		query.append(_FINDER_COLUMN_C_C_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where classNameId = &#63; and classPK = &#63; and version = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 */
	@Override
	public void removeByC_C_Version(
		long classNameId, long classPK, int version) {

		for (LayoutVersion layoutVersion :
				findByC_C_Version(
					classNameId, classPK, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where classNameId = &#63; and classPK = &#63; and version = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByC_C_Version(long classNameId, long classPK, int version) {
		FinderPath finderPath = _finderPathCountByC_C_Version;

		Object[] finderArgs = new Object[] {classNameId, classPK, version};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_C_C_VERSION_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_VERSION_CLASSPK_2);

			query.append(_FINDER_COLUMN_C_C_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(classNameId);

				qPos.add(classPK);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_C_C_VERSION_CLASSNAMEID_2 =
		"layoutVersion.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_VERSION_CLASSPK_2 =
		"layoutVersion.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_L;
	private FinderPath _finderPathWithoutPaginationFindByG_P_L;
	private FinderPath _finderPathCountByG_P_L;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		return findByG_P_L(
			groupId, privateLayout, layoutId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start,
		int end) {

		return findByG_P_L(groupId, privateLayout, layoutId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P_L(
			groupId, privateLayout, layoutId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_L(
		long groupId, boolean privateLayout, long layoutId, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_L;
			finderArgs = new Object[] {groupId, privateLayout, layoutId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_L;
			finderArgs = new Object[] {
				groupId, privateLayout, layoutId, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout()) ||
						(layoutId != layoutVersion.getLayoutId())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_L_First(
			long groupId, boolean privateLayout, long layoutId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_L_First(
			groupId, privateLayout, layoutId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", layoutId=");
		msg.append(layoutId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_L_First(
		long groupId, boolean privateLayout, long layoutId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P_L(
			groupId, privateLayout, layoutId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_L_Last(
			long groupId, boolean privateLayout, long layoutId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_L_Last(
			groupId, privateLayout, layoutId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", layoutId=");
		msg.append(layoutId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_L_Last(
		long groupId, boolean privateLayout, long layoutId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P_L(groupId, privateLayout, layoutId);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P_L(
			groupId, privateLayout, layoutId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_L_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long layoutId, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_L_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, layoutId,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_L_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, layoutId,
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

	protected LayoutVersion getByG_P_L_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout, long layoutId,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(layoutId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 */
	@Override
	public void removeByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		for (LayoutVersion layoutVersion :
				findByG_P_L(
					groupId, privateLayout, layoutId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_L(
		long groupId, boolean privateLayout, long layoutId) {

		FinderPath finderPath = _finderPathCountByG_P_L;

		Object[] finderArgs = new Object[] {groupId, privateLayout, layoutId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_L_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_L_LAYOUTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

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

	private static final String _FINDER_COLUMN_G_P_L_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_LAYOUTID_2 =
		"layoutVersion.layoutId = ?";

	private FinderPath _finderPathFetchByG_P_L_Version;
	private FinderPath _finderPathCountByG_P_L_Version;

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63; or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @return the matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_L_Version(
			long groupId, boolean privateLayout, long layoutId, int version)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_L_Version(
			groupId, privateLayout, layoutId, version);

		if (layoutVersion == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", layoutId=");
			msg.append(layoutId);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLayoutVersionException(msg.toString());
		}

		return layoutVersion;
	}

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_L_Version(
		long groupId, boolean privateLayout, long layoutId, int version) {

		return fetchByG_P_L_Version(
			groupId, privateLayout, layoutId, version, true);
	}

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_L_Version(
		long groupId, boolean privateLayout, long layoutId, int version,
		boolean retrieveFromCache) {

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, layoutId, version
		};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByG_P_L_Version, finderArgs, this);
		}

		if (result instanceof LayoutVersion) {
			LayoutVersion layoutVersion = (LayoutVersion)result;

			if ((groupId != layoutVersion.getGroupId()) ||
				(privateLayout != layoutVersion.isPrivateLayout()) ||
				(layoutId != layoutVersion.getLayoutId()) ||
				(version != layoutVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_L_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_L_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_L_VERSION_LAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_L_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				qPos.add(version);

				List<LayoutVersion> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(
						_finderPathFetchByG_P_L_Version, finderArgs, list);
				}
				else {
					LayoutVersion layoutVersion = list.get(0);

					result = layoutVersion;

					cacheResult(layoutVersion);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathFetchByG_P_L_Version, finderArgs);

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
			return (LayoutVersion)result;
		}
	}

	/**
	 * Removes the layout version where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @return the layout version that was removed
	 */
	@Override
	public LayoutVersion removeByG_P_L_Version(
			long groupId, boolean privateLayout, long layoutId, int version)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByG_P_L_Version(
			groupId, privateLayout, layoutId, version);

		return remove(layoutVersion);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and layoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param layoutId the layout ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_L_Version(
		long groupId, boolean privateLayout, long layoutId, int version) {

		FinderPath finderPath = _finderPathCountByG_P_L_Version;

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, layoutId, version
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_L_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_L_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_L_VERSION_LAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_L_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(layoutId);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_G_P_L_VERSION_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_VERSION_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_VERSION_LAYOUTID_2 =
		"layoutVersion.layoutId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_L_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P_P;
	private FinderPath _finderPathCountByG_P_P;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId) {

		return findByG_P_P(
			groupId, privateLayout, parentLayoutId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end) {

		return findByG_P_P(
			groupId, privateLayout, parentLayoutId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P_P(
			groupId, privateLayout, parentLayoutId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_P;
			finderArgs = new Object[] {groupId, privateLayout, parentLayoutId};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_P;
			finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId, start, end,
				orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout()) ||
						(parentLayoutId != layoutVersion.getParentLayoutId())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_P_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_P_First(
			groupId, privateLayout, parentLayoutId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_P_First(
		long groupId, boolean privateLayout, long parentLayoutId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P_P(
			groupId, privateLayout, parentLayoutId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_P_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_P_Last(
			groupId, privateLayout, parentLayoutId, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_P_Last(
		long groupId, boolean privateLayout, long parentLayoutId,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P_P(groupId, privateLayout, parentLayoutId);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P_P(
			groupId, privateLayout, parentLayoutId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_P_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_P_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, parentLayoutId,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_P_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, parentLayoutId,
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

	protected LayoutVersion getByG_P_P_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout, long parentLayoutId,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(parentLayoutId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 */
	@Override
	public void removeByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId) {

		for (LayoutVersion layoutVersion :
				findByG_P_P(
					groupId, privateLayout, parentLayoutId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_P(
		long groupId, boolean privateLayout, long parentLayoutId) {

		FinderPath finderPath = _finderPathCountByG_P_P;

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, parentLayoutId
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

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

	private static final String _FINDER_COLUMN_G_P_P_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_PARENTLAYOUTID_2 =
		"layoutVersion.parentLayoutId = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_P_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_P_P_Version;
	private FinderPath _finderPathCountByG_P_P_Version;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version) {

		return findByG_P_P_Version(
			groupId, privateLayout, parentLayoutId, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		int start, int end) {

		return findByG_P_P_Version(
			groupId, privateLayout, parentLayoutId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P_P_Version(
			groupId, privateLayout, parentLayoutId, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		int start, int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_P_Version;
			finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId, version
			};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_P_Version;
			finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId, version, start, end,
				orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout()) ||
						(parentLayoutId != layoutVersion.getParentLayoutId()) ||
						(version != layoutVersion.getVersion())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_VERSION_PARENTLAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_P_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_P_Version_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			int version, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_P_Version_First(
			groupId, privateLayout, parentLayoutId, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_P_Version_First(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P_P_Version(
			groupId, privateLayout, parentLayoutId, version, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_P_Version_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			int version, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_P_Version_Last(
			groupId, privateLayout, parentLayoutId, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_P_Version_Last(
		long groupId, boolean privateLayout, long parentLayoutId, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P_P_Version(
			groupId, privateLayout, parentLayoutId, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P_P_Version(
			groupId, privateLayout, parentLayoutId, version, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_P_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_P_Version_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, parentLayoutId,
				version, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_P_Version_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, parentLayoutId,
				version, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutVersion getByG_P_P_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout, long parentLayoutId, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_P_VERSION_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_VERSION_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_VERSION_PARENTLAYOUTID_2);

		query.append(_FINDER_COLUMN_G_P_P_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(parentLayoutId);

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 */
	@Override
	public void removeByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version) {

		for (LayoutVersion layoutVersion :
				findByG_P_P_Version(
					groupId, privateLayout, parentLayoutId, version,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_P_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int version) {

		FinderPath finderPath = _finderPathCountByG_P_P_Version;

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, parentLayoutId, version
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_VERSION_PARENTLAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_P_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_G_P_P_VERSION_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_VERSION_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_VERSION_PARENTLAYOUTID_2 =
		"layoutVersion.parentLayoutId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_T;
	private FinderPath _finderPathWithoutPaginationFindByG_P_T;
	private FinderPath _finderPathCountByG_P_T;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_T(
		long groupId, boolean privateLayout, String type) {

		return findByG_P_T(
			groupId, privateLayout, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_T(
		long groupId, boolean privateLayout, String type, int start, int end) {

		return findByG_P_T(groupId, privateLayout, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_T(
		long groupId, boolean privateLayout, String type, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P_T(
			groupId, privateLayout, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_T(
		long groupId, boolean privateLayout, String type, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		type = Objects.toString(type, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_T;
			finderArgs = new Object[] {groupId, privateLayout, type};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_T;
			finderArgs = new Object[] {
				groupId, privateLayout, type, start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout()) ||
						!type.equals(layoutVersion.getType())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_P_T_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindType) {
					qPos.add(type);
				}

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_T_First(
			long groupId, boolean privateLayout, String type,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_T_First(
			groupId, privateLayout, type, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_T_First(
		long groupId, boolean privateLayout, String type,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P_T(
			groupId, privateLayout, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_T_Last(
			long groupId, boolean privateLayout, String type,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_T_Last(
			groupId, privateLayout, type, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", type=");
		msg.append(type);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_T_Last(
		long groupId, boolean privateLayout, String type,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P_T(groupId, privateLayout, type);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P_T(
			groupId, privateLayout, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_T_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String type, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		type = Objects.toString(type, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_T_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, type,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_T_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, type,
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

	protected LayoutVersion getByG_P_T_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout, String type,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_G_P_T_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_P_T_TYPE_2);
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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (bindType) {
			qPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 */
	@Override
	public void removeByG_P_T(
		long groupId, boolean privateLayout, String type) {

		for (LayoutVersion layoutVersion :
				findByG_P_T(
					groupId, privateLayout, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_T(long groupId, boolean privateLayout, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByG_P_T;

		Object[] finderArgs = new Object[] {groupId, privateLayout, type};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_T_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_T_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_P_T_TYPE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindType) {
					qPos.add(type);
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

	private static final String _FINDER_COLUMN_G_P_T_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_T_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_T_TYPE_2 =
		"layoutVersion.type = ?";

	private static final String _FINDER_COLUMN_G_P_T_TYPE_3 =
		"(layoutVersion.type IS NULL OR layoutVersion.type = '')";

	private FinderPath _finderPathWithPaginationFindByG_P_T_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_P_T_Version;
	private FinderPath _finderPathCountByG_P_T_Version;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version) {

		return findByG_P_T_Version(
			groupId, privateLayout, type, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version,
		int start, int end) {

		return findByG_P_T_Version(
			groupId, privateLayout, type, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version,
		int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P_T_Version(
			groupId, privateLayout, type, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version,
		int start, int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		type = Objects.toString(type, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_T_Version;
			finderArgs = new Object[] {groupId, privateLayout, type, version};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_T_Version;
			finderArgs = new Object[] {
				groupId, privateLayout, type, version, start, end,
				orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout()) ||
						!type.equals(layoutVersion.getType()) ||
						(version != layoutVersion.getVersion())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_T_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_T_VERSION_PRIVATELAYOUT_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_T_VERSION_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_P_T_VERSION_TYPE_2);
			}

			query.append(_FINDER_COLUMN_G_P_T_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindType) {
					qPos.add(type);
				}

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_T_Version_First(
			long groupId, boolean privateLayout, String type, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_T_Version_First(
			groupId, privateLayout, type, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", type=");
		msg.append(type);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_T_Version_First(
		long groupId, boolean privateLayout, String type, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P_T_Version(
			groupId, privateLayout, type, version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_T_Version_Last(
			long groupId, boolean privateLayout, String type, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_T_Version_Last(
			groupId, privateLayout, type, version, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", type=");
		msg.append(type);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_T_Version_Last(
		long groupId, boolean privateLayout, String type, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P_T_Version(groupId, privateLayout, type, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P_T_Version(
			groupId, privateLayout, type, version, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_T_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String type, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		type = Objects.toString(type, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_T_Version_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, type, version,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_T_Version_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, type, version,
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

	protected LayoutVersion getByG_P_T_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout, String type, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_T_VERSION_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_T_VERSION_PRIVATELAYOUT_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			query.append(_FINDER_COLUMN_G_P_T_VERSION_TYPE_3);
		}
		else {
			bindType = true;

			query.append(_FINDER_COLUMN_G_P_T_VERSION_TYPE_2);
		}

		query.append(_FINDER_COLUMN_G_P_T_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (bindType) {
			qPos.add(type);
		}

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 */
	@Override
	public void removeByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version) {

		for (LayoutVersion layoutVersion :
				findByG_P_T_Version(
					groupId, privateLayout, type, version, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and type = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param type the type
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_T_Version(
		long groupId, boolean privateLayout, String type, int version) {

		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathCountByG_P_T_Version;

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, type, version
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_T_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_T_VERSION_PRIVATELAYOUT_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_T_VERSION_TYPE_3);
			}
			else {
				bindType = true;

				query.append(_FINDER_COLUMN_G_P_T_VERSION_TYPE_2);
			}

			query.append(_FINDER_COLUMN_G_P_T_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindType) {
					qPos.add(type);
				}

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_G_P_T_VERSION_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_T_VERSION_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_T_VERSION_TYPE_2 =
		"layoutVersion.type = ? AND ";

	private static final String _FINDER_COLUMN_G_P_T_VERSION_TYPE_3 =
		"(layoutVersion.type IS NULL OR layoutVersion.type = '') AND ";

	private static final String _FINDER_COLUMN_G_P_T_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_F;
	private FinderPath _finderPathWithoutPaginationFindByG_P_F;
	private FinderPath _finderPathCountByG_P_F;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL) {

		return findByG_P_F(
			groupId, privateLayout, friendlyURL, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL, int start,
		int end) {

		return findByG_P_F(
			groupId, privateLayout, friendlyURL, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P_F(
			groupId, privateLayout, friendlyURL, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL, int start,
		int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		friendlyURL = Objects.toString(friendlyURL, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_F;
			finderArgs = new Object[] {groupId, privateLayout, friendlyURL};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_F;
			finderArgs = new Object[] {
				groupId, privateLayout, friendlyURL, start, end,
				orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout()) ||
						!friendlyURL.equals(layoutVersion.getFriendlyURL())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_F_PRIVATELAYOUT_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
				}

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_F_First(
			long groupId, boolean privateLayout, String friendlyURL,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_F_First(
			groupId, privateLayout, friendlyURL, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", friendlyURL=");
		msg.append(friendlyURL);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_F_First(
		long groupId, boolean privateLayout, String friendlyURL,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P_F(
			groupId, privateLayout, friendlyURL, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_F_Last(
			long groupId, boolean privateLayout, String friendlyURL,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_F_Last(
			groupId, privateLayout, friendlyURL, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", friendlyURL=");
		msg.append(friendlyURL);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_F_Last(
		long groupId, boolean privateLayout, String friendlyURL,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P_F(groupId, privateLayout, friendlyURL);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P_F(
			groupId, privateLayout, friendlyURL, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_F_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String friendlyURL,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		friendlyURL = Objects.toString(friendlyURL, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_F_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, friendlyURL,
				orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_F_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, friendlyURL,
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

	protected LayoutVersion getByG_P_F_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout, String friendlyURL,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_F_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_F_PRIVATELAYOUT_2);

		boolean bindFriendlyURL = false;

		if (friendlyURL.isEmpty()) {
			query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_3);
		}
		else {
			bindFriendlyURL = true;

			query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_2);
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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (bindFriendlyURL) {
			qPos.add(friendlyURL);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 */
	@Override
	public void removeByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL) {

		for (LayoutVersion layoutVersion :
				findByG_P_F(
					groupId, privateLayout, friendlyURL, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_F(
		long groupId, boolean privateLayout, String friendlyURL) {

		friendlyURL = Objects.toString(friendlyURL, "");

		FinderPath finderPath = _finderPathCountByG_P_F;

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, friendlyURL
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_F_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_F_PRIVATELAYOUT_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_G_P_F_FRIENDLYURL_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
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

	private static final String _FINDER_COLUMN_G_P_F_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_F_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_F_FRIENDLYURL_2 =
		"layoutVersion.friendlyURL = ?";

	private static final String _FINDER_COLUMN_G_P_F_FRIENDLYURL_3 =
		"(layoutVersion.friendlyURL IS NULL OR layoutVersion.friendlyURL = '')";

	private FinderPath _finderPathFetchByG_P_F_Version;
	private FinderPath _finderPathCountByG_P_F_Version;

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63; or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @return the matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_F_Version(
			long groupId, boolean privateLayout, String friendlyURL,
			int version)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_F_Version(
			groupId, privateLayout, friendlyURL, version);

		if (layoutVersion == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", friendlyURL=");
			msg.append(friendlyURL);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLayoutVersionException(msg.toString());
		}

		return layoutVersion;
	}

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_F_Version(
		long groupId, boolean privateLayout, String friendlyURL, int version) {

		return fetchByG_P_F_Version(
			groupId, privateLayout, friendlyURL, version, true);
	}

	/**
	 * Returns the layout version where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_F_Version(
		long groupId, boolean privateLayout, String friendlyURL, int version,
		boolean retrieveFromCache) {

		friendlyURL = Objects.toString(friendlyURL, "");

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, friendlyURL, version
		};

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByG_P_F_Version, finderArgs, this);
		}

		if (result instanceof LayoutVersion) {
			LayoutVersion layoutVersion = (LayoutVersion)result;

			if ((groupId != layoutVersion.getGroupId()) ||
				(privateLayout != layoutVersion.isPrivateLayout()) ||
				!Objects.equals(friendlyURL, layoutVersion.getFriendlyURL()) ||
				(version != layoutVersion.getVersion())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_F_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_F_VERSION_PRIVATELAYOUT_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_F_VERSION_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_G_P_F_VERSION_FRIENDLYURL_2);
			}

			query.append(_FINDER_COLUMN_G_P_F_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
				}

				qPos.add(version);

				List<LayoutVersion> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(
						_finderPathFetchByG_P_F_Version, finderArgs, list);
				}
				else {
					LayoutVersion layoutVersion = list.get(0);

					result = layoutVersion;

					cacheResult(layoutVersion);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
					_finderPathFetchByG_P_F_Version, finderArgs);

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
			return (LayoutVersion)result;
		}
	}

	/**
	 * Removes the layout version where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @return the layout version that was removed
	 */
	@Override
	public LayoutVersion removeByG_P_F_Version(
			long groupId, boolean privateLayout, String friendlyURL,
			int version)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByG_P_F_Version(
			groupId, privateLayout, friendlyURL, version);

		return remove(layoutVersion);
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and friendlyURL = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param friendlyURL the friendly url
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_F_Version(
		long groupId, boolean privateLayout, String friendlyURL, int version) {

		friendlyURL = Objects.toString(friendlyURL, "");

		FinderPath finderPath = _finderPathCountByG_P_F_Version;

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, friendlyURL, version
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_F_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_F_VERSION_PRIVATELAYOUT_2);

			boolean bindFriendlyURL = false;

			if (friendlyURL.isEmpty()) {
				query.append(_FINDER_COLUMN_G_P_F_VERSION_FRIENDLYURL_3);
			}
			else {
				bindFriendlyURL = true;

				query.append(_FINDER_COLUMN_G_P_F_VERSION_FRIENDLYURL_2);
			}

			query.append(_FINDER_COLUMN_G_P_F_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindFriendlyURL) {
					qPos.add(friendlyURL);
				}

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_G_P_F_VERSION_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_F_VERSION_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_F_VERSION_FRIENDLYURL_2 =
		"layoutVersion.friendlyURL = ? AND ";

	private static final String _FINDER_COLUMN_G_P_F_VERSION_FRIENDLYURL_3 =
		"(layoutVersion.friendlyURL IS NULL OR layoutVersion.friendlyURL = '') AND ";

	private static final String _FINDER_COLUMN_G_P_F_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_SPLU;
	private FinderPath _finderPathWithoutPaginationFindByG_P_SPLU;
	private FinderPath _finderPathCountByG_P_SPLU;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid) {

		return findByG_P_SPLU(
			groupId, privateLayout, sourcePrototypeLayoutUuid,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int start, int end) {

		return findByG_P_SPLU(
			groupId, privateLayout, sourcePrototypeLayoutUuid, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P_SPLU(
			groupId, privateLayout, sourcePrototypeLayoutUuid, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int start, int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		sourcePrototypeLayoutUuid = Objects.toString(
			sourcePrototypeLayoutUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_SPLU;
			finderArgs = new Object[] {
				groupId, privateLayout, sourcePrototypeLayoutUuid
			};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_SPLU;
			finderArgs = new Object[] {
				groupId, privateLayout, sourcePrototypeLayoutUuid, start, end,
				orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout()) ||
						!sourcePrototypeLayoutUuid.equals(
							layoutVersion.getSourcePrototypeLayoutUuid())) {

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

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_SPLU_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_SPLU_PRIVATELAYOUT_2);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid.isEmpty()) {
				query.append(
					_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_3);
			}
			else {
				bindSourcePrototypeLayoutUuid = true;

				query.append(
					_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindSourcePrototypeLayoutUuid) {
					qPos.add(sourcePrototypeLayoutUuid);
				}

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_SPLU_First(
			long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_SPLU_First(
			groupId, privateLayout, sourcePrototypeLayoutUuid,
			orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", sourcePrototypeLayoutUuid=");
		msg.append(sourcePrototypeLayoutUuid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_SPLU_First(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P_SPLU(
			groupId, privateLayout, sourcePrototypeLayoutUuid, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_SPLU_Last(
			long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_SPLU_Last(
			groupId, privateLayout, sourcePrototypeLayoutUuid,
			orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", sourcePrototypeLayoutUuid=");
		msg.append(sourcePrototypeLayoutUuid);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_SPLU_Last(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P_SPLU(
			groupId, privateLayout, sourcePrototypeLayoutUuid);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P_SPLU(
			groupId, privateLayout, sourcePrototypeLayoutUuid, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_SPLU_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		sourcePrototypeLayoutUuid = Objects.toString(
			sourcePrototypeLayoutUuid, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_SPLU_PrevAndNext(
				session, layoutVersion, groupId, privateLayout,
				sourcePrototypeLayoutUuid, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_SPLU_PrevAndNext(
				session, layoutVersion, groupId, privateLayout,
				sourcePrototypeLayoutUuid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutVersion getByG_P_SPLU_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout, String sourcePrototypeLayoutUuid,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_SPLU_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_SPLU_PRIVATELAYOUT_2);

		boolean bindSourcePrototypeLayoutUuid = false;

		if (sourcePrototypeLayoutUuid.isEmpty()) {
			query.append(_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_3);
		}
		else {
			bindSourcePrototypeLayoutUuid = true;

			query.append(_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_2);
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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (bindSourcePrototypeLayoutUuid) {
			qPos.add(sourcePrototypeLayoutUuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 */
	@Override
	public void removeByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid) {

		for (LayoutVersion layoutVersion :
				findByG_P_SPLU(
					groupId, privateLayout, sourcePrototypeLayoutUuid,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_SPLU(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid) {

		sourcePrototypeLayoutUuid = Objects.toString(
			sourcePrototypeLayoutUuid, "");

		FinderPath finderPath = _finderPathCountByG_P_SPLU;

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, sourcePrototypeLayoutUuid
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_SPLU_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_SPLU_PRIVATELAYOUT_2);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid.isEmpty()) {
				query.append(
					_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_3);
			}
			else {
				bindSourcePrototypeLayoutUuid = true;

				query.append(
					_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindSourcePrototypeLayoutUuid) {
					qPos.add(sourcePrototypeLayoutUuid);
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

	private static final String _FINDER_COLUMN_G_P_SPLU_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_SPLU_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String
		_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_2 =
			"layoutVersion.sourcePrototypeLayoutUuid = ?";

	private static final String
		_FINDER_COLUMN_G_P_SPLU_SOURCEPROTOTYPELAYOUTUUID_3 =
			"(layoutVersion.sourcePrototypeLayoutUuid IS NULL OR layoutVersion.sourcePrototypeLayoutUuid = '')";

	private FinderPath _finderPathWithPaginationFindByG_P_SPLU_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_P_SPLU_Version;
	private FinderPath _finderPathCountByG_P_SPLU_Version;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version) {

		return findByG_P_SPLU_Version(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version, int start, int end) {

		return findByG_P_SPLU_Version(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P_SPLU_Version(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		sourcePrototypeLayoutUuid = Objects.toString(
			sourcePrototypeLayoutUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_SPLU_Version;
			finderArgs = new Object[] {
				groupId, privateLayout, sourcePrototypeLayoutUuid, version
			};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_SPLU_Version;
			finderArgs = new Object[] {
				groupId, privateLayout, sourcePrototypeLayoutUuid, version,
				start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout()) ||
						!sourcePrototypeLayoutUuid.equals(
							layoutVersion.getSourcePrototypeLayoutUuid()) ||
						(version != layoutVersion.getVersion())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_SPLU_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_SPLU_VERSION_PRIVATELAYOUT_2);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid.isEmpty()) {
				query.append(
					_FINDER_COLUMN_G_P_SPLU_VERSION_SOURCEPROTOTYPELAYOUTUUID_3);
			}
			else {
				bindSourcePrototypeLayoutUuid = true;

				query.append(
					_FINDER_COLUMN_G_P_SPLU_VERSION_SOURCEPROTOTYPELAYOUTUUID_2);
			}

			query.append(_FINDER_COLUMN_G_P_SPLU_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindSourcePrototypeLayoutUuid) {
					qPos.add(sourcePrototypeLayoutUuid);
				}

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_SPLU_Version_First(
			long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_SPLU_Version_First(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version,
			orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", sourcePrototypeLayoutUuid=");
		msg.append(sourcePrototypeLayoutUuid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_SPLU_Version_First(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version, OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P_SPLU_Version(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_SPLU_Version_Last(
			long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_SPLU_Version_Last(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version,
			orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", sourcePrototypeLayoutUuid=");
		msg.append(sourcePrototypeLayoutUuid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_SPLU_Version_Last(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version, OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P_SPLU_Version(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P_SPLU_Version(
			groupId, privateLayout, sourcePrototypeLayoutUuid, version,
			count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_SPLU_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			String sourcePrototypeLayoutUuid, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		sourcePrototypeLayoutUuid = Objects.toString(
			sourcePrototypeLayoutUuid, "");

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_SPLU_Version_PrevAndNext(
				session, layoutVersion, groupId, privateLayout,
				sourcePrototypeLayoutUuid, version, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_SPLU_Version_PrevAndNext(
				session, layoutVersion, groupId, privateLayout,
				sourcePrototypeLayoutUuid, version, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutVersion getByG_P_SPLU_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout, String sourcePrototypeLayoutUuid, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_SPLU_VERSION_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_SPLU_VERSION_PRIVATELAYOUT_2);

		boolean bindSourcePrototypeLayoutUuid = false;

		if (sourcePrototypeLayoutUuid.isEmpty()) {
			query.append(
				_FINDER_COLUMN_G_P_SPLU_VERSION_SOURCEPROTOTYPELAYOUTUUID_3);
		}
		else {
			bindSourcePrototypeLayoutUuid = true;

			query.append(
				_FINDER_COLUMN_G_P_SPLU_VERSION_SOURCEPROTOTYPELAYOUTUUID_2);
		}

		query.append(_FINDER_COLUMN_G_P_SPLU_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (bindSourcePrototypeLayoutUuid) {
			qPos.add(sourcePrototypeLayoutUuid);
		}

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 */
	@Override
	public void removeByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version) {

		for (LayoutVersion layoutVersion :
				findByG_P_SPLU_Version(
					groupId, privateLayout, sourcePrototypeLayoutUuid, version,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and sourcePrototypeLayoutUuid = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param sourcePrototypeLayoutUuid the source prototype layout uuid
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_SPLU_Version(
		long groupId, boolean privateLayout, String sourcePrototypeLayoutUuid,
		int version) {

		sourcePrototypeLayoutUuid = Objects.toString(
			sourcePrototypeLayoutUuid, "");

		FinderPath finderPath = _finderPathCountByG_P_SPLU_Version;

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, sourcePrototypeLayoutUuid, version
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_SPLU_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_SPLU_VERSION_PRIVATELAYOUT_2);

			boolean bindSourcePrototypeLayoutUuid = false;

			if (sourcePrototypeLayoutUuid.isEmpty()) {
				query.append(
					_FINDER_COLUMN_G_P_SPLU_VERSION_SOURCEPROTOTYPELAYOUTUUID_3);
			}
			else {
				bindSourcePrototypeLayoutUuid = true;

				query.append(
					_FINDER_COLUMN_G_P_SPLU_VERSION_SOURCEPROTOTYPELAYOUTUUID_2);
			}

			query.append(_FINDER_COLUMN_G_P_SPLU_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				if (bindSourcePrototypeLayoutUuid) {
					qPos.add(sourcePrototypeLayoutUuid);
				}

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_G_P_SPLU_VERSION_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_P_SPLU_VERSION_PRIVATELAYOUT_2 =
			"layoutVersion.privateLayout = ? AND ";

	private static final String
		_FINDER_COLUMN_G_P_SPLU_VERSION_SOURCEPROTOTYPELAYOUTUUID_2 =
			"layoutVersion.sourcePrototypeLayoutUuid = ? AND ";

	private static final String
		_FINDER_COLUMN_G_P_SPLU_VERSION_SOURCEPROTOTYPELAYOUTUUID_3 =
			"(layoutVersion.sourcePrototypeLayoutUuid IS NULL OR layoutVersion.sourcePrototypeLayoutUuid = '') AND ";

	private static final String _FINDER_COLUMN_G_P_SPLU_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_P_H;
	private FinderPath _finderPathWithoutPaginationFindByG_P_P_H;
	private FinderPath _finderPathCountByG_P_P_H;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden) {

		return findByG_P_P_H(
			groupId, privateLayout, parentLayoutId, hidden, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int start, int end) {

		return findByG_P_P_H(
			groupId, privateLayout, parentLayoutId, hidden, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P_P_H(
			groupId, privateLayout, parentLayoutId, hidden, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_P_H;
			finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId, hidden
			};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_P_H;
			finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId, hidden, start, end,
				orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout()) ||
						(parentLayoutId != layoutVersion.getParentLayoutId()) ||
						(hidden != layoutVersion.isHidden())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_H_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_H_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_H_PARENTLAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_P_H_HIDDEN_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				qPos.add(hidden);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_P_H_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean hidden, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_P_H_First(
			groupId, privateLayout, parentLayoutId, hidden, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_P_H_First(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P_P_H(
			groupId, privateLayout, parentLayoutId, hidden, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_P_H_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean hidden, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_P_H_Last(
			groupId, privateLayout, parentLayoutId, hidden, orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_P_H_Last(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P_P_H(
			groupId, privateLayout, parentLayoutId, hidden);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P_P_H(
			groupId, privateLayout, parentLayoutId, hidden, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_P_H_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, boolean hidden,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_P_H_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, parentLayoutId,
				hidden, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_P_H_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, parentLayoutId,
				hidden, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutVersion getByG_P_P_H_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout, long parentLayoutId, boolean hidden,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_P_H_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_H_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_H_PARENTLAYOUTID_2);

		query.append(_FINDER_COLUMN_G_P_P_H_HIDDEN_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(parentLayoutId);

		qPos.add(hidden);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 */
	@Override
	public void removeByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden) {

		for (LayoutVersion layoutVersion :
				findByG_P_P_H(
					groupId, privateLayout, parentLayoutId, hidden,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_P_H(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden) {

		FinderPath finderPath = _finderPathCountByG_P_P_H;

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, parentLayoutId, hidden
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_H_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_H_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_H_PARENTLAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_P_H_HIDDEN_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				qPos.add(hidden);

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

	private static final String _FINDER_COLUMN_G_P_P_H_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_H_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_H_PARENTLAYOUTID_2 =
		"layoutVersion.parentLayoutId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_H_HIDDEN_2 =
		"layoutVersion.hidden = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_P_H_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_P_P_H_Version;
	private FinderPath _finderPathCountByG_P_P_H_Version;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version) {

		return findByG_P_P_H_Version(
			groupId, privateLayout, parentLayoutId, hidden, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version, int start, int end) {

		return findByG_P_P_H_Version(
			groupId, privateLayout, parentLayoutId, hidden, version, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P_P_H_Version(
			groupId, privateLayout, parentLayoutId, hidden, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_P_H_Version;
			finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId, hidden, version
			};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_P_H_Version;
			finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId, hidden, version, start,
				end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout()) ||
						(parentLayoutId != layoutVersion.getParentLayoutId()) ||
						(hidden != layoutVersion.isHidden()) ||
						(version != layoutVersion.getVersion())) {

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
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_H_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_H_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_H_VERSION_PARENTLAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_P_H_VERSION_HIDDEN_2);

			query.append(_FINDER_COLUMN_G_P_P_H_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				qPos.add(hidden);

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_P_H_Version_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean hidden, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_P_H_Version_First(
			groupId, privateLayout, parentLayoutId, hidden, version,
			orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_P_H_Version_First(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P_P_H_Version(
			groupId, privateLayout, parentLayoutId, hidden, version, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_P_H_Version_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			boolean hidden, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_P_H_Version_Last(
			groupId, privateLayout, parentLayoutId, hidden, version,
			orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(", hidden=");
		msg.append(hidden);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_P_H_Version_Last(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P_P_H_Version(
			groupId, privateLayout, parentLayoutId, hidden, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P_P_H_Version(
			groupId, privateLayout, parentLayoutId, hidden, version, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_P_H_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, boolean hidden, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_P_H_Version_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, parentLayoutId,
				hidden, version, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_P_H_Version_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, parentLayoutId,
				hidden, version, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutVersion getByG_P_P_H_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout, long parentLayoutId, boolean hidden, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_P_H_VERSION_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_H_VERSION_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_H_VERSION_PARENTLAYOUTID_2);

		query.append(_FINDER_COLUMN_G_P_P_H_VERSION_HIDDEN_2);

		query.append(_FINDER_COLUMN_G_P_P_H_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(parentLayoutId);

		qPos.add(hidden);

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 */
	@Override
	public void removeByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version) {

		for (LayoutVersion layoutVersion :
				findByG_P_P_H_Version(
					groupId, privateLayout, parentLayoutId, hidden, version,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and hidden = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param hidden the hidden
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_P_H_Version(
		long groupId, boolean privateLayout, long parentLayoutId,
		boolean hidden, int version) {

		FinderPath finderPath = _finderPathCountByG_P_P_H_Version;

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, parentLayoutId, hidden, version
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_H_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_H_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_H_VERSION_PARENTLAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_P_H_VERSION_HIDDEN_2);

			query.append(_FINDER_COLUMN_G_P_P_H_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				qPos.add(hidden);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_G_P_P_H_VERSION_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_H_VERSION_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String
		_FINDER_COLUMN_G_P_P_H_VERSION_PARENTLAYOUTID_2 =
			"layoutVersion.parentLayoutId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_H_VERSION_HIDDEN_2 =
		"layoutVersion.hidden = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_H_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_P_LtP;
	private FinderPath _finderPathWithoutPaginationFindByG_P_P_LtP;
	private FinderPath _finderPathCountByG_P_P_LtP;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId,
		int priority) {

		return findByG_P_P_LtP(
			groupId, privateLayout, parentLayoutId, priority, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int start, int end) {

		return findByG_P_P_LtP(
			groupId, privateLayout, parentLayoutId, priority, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P_P_LtP(
			groupId, privateLayout, parentLayoutId, priority, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int start, int end, OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_P_LtP;
			finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId, priority
			};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_P_LtP;
			finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId, priority, start, end,
				orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout()) ||
						(parentLayoutId != layoutVersion.getParentLayoutId()) ||
						(priority != layoutVersion.getPriority())) {

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
					6 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(6);
			}

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_LTP_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_PARENTLAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_PRIORITY_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				qPos.add(priority);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_P_LtP_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			int priority, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_P_LtP_First(
			groupId, privateLayout, parentLayoutId, priority,
			orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(", priority=");
		msg.append(priority);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_P_LtP_First(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P_P_LtP(
			groupId, privateLayout, parentLayoutId, priority, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_P_LtP_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			int priority, OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_P_LtP_Last(
			groupId, privateLayout, parentLayoutId, priority,
			orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(10);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(", priority=");
		msg.append(priority);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_P_LtP_Last(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P_P_LtP(
			groupId, privateLayout, parentLayoutId, priority);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P_P_LtP(
			groupId, privateLayout, parentLayoutId, priority, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_P_LtP_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, int priority,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_P_LtP_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, parentLayoutId,
				priority, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_P_LtP_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, parentLayoutId,
				priority, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutVersion getByG_P_P_LtP_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout, long parentLayoutId, int priority,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				7 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(6);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_P_LTP_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PARENTLAYOUTID_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_PRIORITY_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(parentLayoutId);

		qPos.add(priority);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 */
	@Override
	public void removeByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId,
		int priority) {

		for (LayoutVersion layoutVersion :
				findByG_P_P_LtP(
					groupId, privateLayout, parentLayoutId, priority,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_P_LtP(
		long groupId, boolean privateLayout, long parentLayoutId,
		int priority) {

		FinderPath finderPath = _finderPathCountByG_P_P_LtP;

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, parentLayoutId, priority
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_LTP_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_PARENTLAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_PRIORITY_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				qPos.add(priority);

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

	private static final String _FINDER_COLUMN_G_P_P_LTP_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_LTP_PRIVATELAYOUT_2 =
		"layoutVersion.privateLayout = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_LTP_PARENTLAYOUTID_2 =
		"layoutVersion.parentLayoutId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_LTP_PRIORITY_2 =
		"layoutVersion.priority = ?";

	private FinderPath _finderPathWithPaginationFindByG_P_P_LtP_Version;
	private FinderPath _finderPathWithoutPaginationFindByG_P_P_LtP_Version;
	private FinderPath _finderPathCountByG_P_P_LtP_Version;

	/**
	 * Returns all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @return the matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version) {

		return findByG_P_P_LtP_Version(
			groupId, privateLayout, parentLayoutId, priority, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version, int start, int end) {

		return findByG_P_P_LtP_Version(
			groupId, privateLayout, parentLayoutId, priority, version, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findByG_P_P_LtP_Version(
			groupId, privateLayout, parentLayoutId, priority, version, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout versions
	 */
	@Override
	public List<LayoutVersion> findByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version, int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator,
		boolean retrieveFromCache) {

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P_P_LtP_Version;
			finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId, priority, version
			};
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P_P_LtP_Version;
			finderArgs = new Object[] {
				groupId, privateLayout, parentLayoutId, priority, version,
				start, end, orderByComparator
			};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutVersion layoutVersion : list) {
					if ((groupId != layoutVersion.getGroupId()) ||
						(privateLayout != layoutVersion.isPrivateLayout()) ||
						(parentLayoutId != layoutVersion.getParentLayoutId()) ||
						(priority != layoutVersion.getPriority()) ||
						(version != layoutVersion.getVersion())) {

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
					7 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(7);
			}

			query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_PARENTLAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_PRIORITY_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else if (pagination) {
				query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				qPos.add(priority);

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_P_LtP_Version_First(
			long groupId, boolean privateLayout, long parentLayoutId,
			int priority, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_P_LtP_Version_First(
			groupId, privateLayout, parentLayoutId, priority, version,
			orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(", priority=");
		msg.append(priority);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the first layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_P_LtP_Version_First(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version, OrderByComparator<LayoutVersion> orderByComparator) {

		List<LayoutVersion> list = findByG_P_P_LtP_Version(
			groupId, privateLayout, parentLayoutId, priority, version, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version
	 * @throws NoSuchLayoutVersionException if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion findByG_P_P_LtP_Version_Last(
			long groupId, boolean privateLayout, long parentLayoutId,
			int priority, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByG_P_P_LtP_Version_Last(
			groupId, privateLayout, parentLayoutId, priority, version,
			orderByComparator);

		if (layoutVersion != null) {
			return layoutVersion;
		}

		StringBundler msg = new StringBundler(12);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append(", parentLayoutId=");
		msg.append(parentLayoutId);

		msg.append(", priority=");
		msg.append(priority);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutVersionException(msg.toString());
	}

	/**
	 * Returns the last layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout version, or <code>null</code> if a matching layout version could not be found
	 */
	@Override
	public LayoutVersion fetchByG_P_P_LtP_Version_Last(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version, OrderByComparator<LayoutVersion> orderByComparator) {

		int count = countByG_P_P_LtP_Version(
			groupId, privateLayout, parentLayoutId, priority, version);

		if (count == 0) {
			return null;
		}

		List<LayoutVersion> list = findByG_P_P_LtP_Version(
			groupId, privateLayout, parentLayoutId, priority, version,
			count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout versions before and after the current layout version in the ordered set where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param layoutVersionId the primary key of the current layout version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion[] findByG_P_P_LtP_Version_PrevAndNext(
			long layoutVersionId, long groupId, boolean privateLayout,
			long parentLayoutId, int priority, int version,
			OrderByComparator<LayoutVersion> orderByComparator)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = findByPrimaryKey(layoutVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutVersion[] array = new LayoutVersionImpl[3];

			array[0] = getByG_P_P_LtP_Version_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, parentLayoutId,
				priority, version, orderByComparator, true);

			array[1] = layoutVersion;

			array[2] = getByG_P_P_LtP_Version_PrevAndNext(
				session, layoutVersion, groupId, privateLayout, parentLayoutId,
				priority, version, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutVersion getByG_P_P_LtP_Version_PrevAndNext(
		Session session, LayoutVersion layoutVersion, long groupId,
		boolean privateLayout, long parentLayoutId, int priority, int version,
		OrderByComparator<LayoutVersion> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				8 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(7);
		}

		query.append(_SQL_SELECT_LAYOUTVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_PARENTLAYOUTID_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_PRIORITY_2);

		query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_VERSION_2);

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
			query.append(LayoutVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		qPos.add(parentLayoutId);

		qPos.add(priority);

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutVersion)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 */
	@Override
	public void removeByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version) {

		for (LayoutVersion layoutVersion :
				findByG_P_P_LtP_Version(
					groupId, privateLayout, parentLayoutId, priority, version,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions where groupId = &#63; and privateLayout = &#63; and parentLayoutId = &#63; and priority = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param parentLayoutId the parent layout ID
	 * @param priority the priority
	 * @param version the version
	 * @return the number of matching layout versions
	 */
	@Override
	public int countByG_P_P_LtP_Version(
		long groupId, boolean privateLayout, long parentLayoutId, int priority,
		int version) {

		FinderPath finderPath = _finderPathCountByG_P_P_LtP_Version;

		Object[] finderArgs = new Object[] {
			groupId, privateLayout, parentLayoutId, priority, version
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_COUNT_LAYOUTVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_PARENTLAYOUTID_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_PRIORITY_2);

			query.append(_FINDER_COLUMN_G_P_P_LTP_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(privateLayout);

				qPos.add(parentLayoutId);

				qPos.add(priority);

				qPos.add(version);

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

	private static final String _FINDER_COLUMN_G_P_P_LTP_VERSION_GROUPID_2 =
		"layoutVersion.groupId = ? AND ";

	private static final String
		_FINDER_COLUMN_G_P_P_LTP_VERSION_PRIVATELAYOUT_2 =
			"layoutVersion.privateLayout = ? AND ";

	private static final String
		_FINDER_COLUMN_G_P_P_LTP_VERSION_PARENTLAYOUTID_2 =
			"layoutVersion.parentLayoutId = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_LTP_VERSION_PRIORITY_2 =
		"layoutVersion.priority = ? AND ";

	private static final String _FINDER_COLUMN_G_P_P_LTP_VERSION_VERSION_2 =
		"layoutVersion.version = ?";

	public LayoutVersionPersistenceImpl() {
		setModelClass(LayoutVersion.class);

		setModelImplClass(LayoutVersionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(LayoutVersionModelImpl.ENTITY_CACHE_ENABLED);

		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("type", "type_");
		dbColumnNames.put("hidden", "hidden_");
		dbColumnNames.put("system", "system_");

		setDBColumnNames(dbColumnNames);
	}

	/**
	 * Caches the layout version in the entity cache if it is enabled.
	 *
	 * @param layoutVersion the layout version
	 */
	@Override
	public void cacheResult(LayoutVersion layoutVersion) {
		EntityCacheUtil.putResult(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionImpl.class, layoutVersion.getPrimaryKey(),
			layoutVersion);

		FinderCacheUtil.putResult(
			_finderPathFetchByPlid_Version,
			new Object[] {layoutVersion.getPlid(), layoutVersion.getVersion()},
			layoutVersion);

		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G_P_Version,
			new Object[] {
				layoutVersion.getUuid(), layoutVersion.getGroupId(),
				layoutVersion.isPrivateLayout(), layoutVersion.getVersion()
			},
			layoutVersion);

		FinderCacheUtil.putResult(
			_finderPathFetchByG_P_L_Version,
			new Object[] {
				layoutVersion.getGroupId(), layoutVersion.isPrivateLayout(),
				layoutVersion.getLayoutId(), layoutVersion.getVersion()
			},
			layoutVersion);

		FinderCacheUtil.putResult(
			_finderPathFetchByG_P_F_Version,
			new Object[] {
				layoutVersion.getGroupId(), layoutVersion.isPrivateLayout(),
				layoutVersion.getFriendlyURL(), layoutVersion.getVersion()
			},
			layoutVersion);

		layoutVersion.resetOriginalValues();
	}

	/**
	 * Caches the layout versions in the entity cache if it is enabled.
	 *
	 * @param layoutVersions the layout versions
	 */
	@Override
	public void cacheResult(List<LayoutVersion> layoutVersions) {
		for (LayoutVersion layoutVersion : layoutVersions) {
			if (EntityCacheUtil.getResult(
					LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
					LayoutVersionImpl.class, layoutVersion.getPrimaryKey()) ==
						null) {

				cacheResult(layoutVersion);
			}
			else {
				layoutVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(LayoutVersionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutVersion layoutVersion) {
		EntityCacheUtil.removeResult(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionImpl.class, layoutVersion.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LayoutVersionModelImpl)layoutVersion, true);
	}

	@Override
	public void clearCache(List<LayoutVersion> layoutVersions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutVersion layoutVersion : layoutVersions) {
			EntityCacheUtil.removeResult(
				LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutVersionImpl.class, layoutVersion.getPrimaryKey());

			clearUniqueFindersCache(
				(LayoutVersionModelImpl)layoutVersion, true);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutVersionModelImpl layoutVersionModelImpl) {

		Object[] args = new Object[] {
			layoutVersionModelImpl.getPlid(),
			layoutVersionModelImpl.getVersion()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByPlid_Version, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByPlid_Version, args, layoutVersionModelImpl,
			false);

		args = new Object[] {
			layoutVersionModelImpl.getUuid(),
			layoutVersionModelImpl.getGroupId(),
			layoutVersionModelImpl.isPrivateLayout(),
			layoutVersionModelImpl.getVersion()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByUUID_G_P_Version, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByUUID_G_P_Version, args, layoutVersionModelImpl,
			false);

		args = new Object[] {
			layoutVersionModelImpl.getGroupId(),
			layoutVersionModelImpl.isPrivateLayout(),
			layoutVersionModelImpl.getLayoutId(),
			layoutVersionModelImpl.getVersion()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByG_P_L_Version, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByG_P_L_Version, args, layoutVersionModelImpl,
			false);

		args = new Object[] {
			layoutVersionModelImpl.getGroupId(),
			layoutVersionModelImpl.isPrivateLayout(),
			layoutVersionModelImpl.getFriendlyURL(),
			layoutVersionModelImpl.getVersion()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByG_P_F_Version, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByG_P_F_Version, args, layoutVersionModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		LayoutVersionModelImpl layoutVersionModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutVersionModelImpl.getPlid(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByPlid_Version, args);
			FinderCacheUtil.removeResult(_finderPathFetchByPlid_Version, args);
		}

		if ((layoutVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByPlid_Version.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutVersionModelImpl.getOriginalPlid(),
				layoutVersionModelImpl.getOriginalVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByPlid_Version, args);
			FinderCacheUtil.removeResult(_finderPathFetchByPlid_Version, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutVersionModelImpl.getUuid(),
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByUUID_G_P_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathFetchByUUID_G_P_Version, args);
		}

		if ((layoutVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByUUID_G_P_Version.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutVersionModelImpl.getOriginalUuid(),
				layoutVersionModelImpl.getOriginalGroupId(),
				layoutVersionModelImpl.getOriginalPrivateLayout(),
				layoutVersionModelImpl.getOriginalVersion()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByUUID_G_P_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathFetchByUUID_G_P_Version, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getLayoutId(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_L_Version, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_L_Version, args);
		}

		if ((layoutVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_P_L_Version.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutVersionModelImpl.getOriginalGroupId(),
				layoutVersionModelImpl.getOriginalPrivateLayout(),
				layoutVersionModelImpl.getOriginalLayoutId(),
				layoutVersionModelImpl.getOriginalVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_L_Version, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_L_Version, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getFriendlyURL(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_F_Version, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_F_Version, args);
		}

		if ((layoutVersionModelImpl.getColumnBitmask() &
			 _finderPathFetchByG_P_F_Version.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutVersionModelImpl.getOriginalGroupId(),
				layoutVersionModelImpl.getOriginalPrivateLayout(),
				layoutVersionModelImpl.getOriginalFriendlyURL(),
				layoutVersionModelImpl.getOriginalVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_F_Version, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_F_Version, args);
		}
	}

	/**
	 * Creates a new layout version with the primary key. Does not add the layout version to the database.
	 *
	 * @param layoutVersionId the primary key for the new layout version
	 * @return the new layout version
	 */
	@Override
	public LayoutVersion create(long layoutVersionId) {
		LayoutVersion layoutVersion = new LayoutVersionImpl();

		layoutVersion.setNew(true);
		layoutVersion.setPrimaryKey(layoutVersionId);

		layoutVersion.setCompanyId(CompanyThreadLocal.getCompanyId());

		return layoutVersion;
	}

	/**
	 * Removes the layout version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutVersionId the primary key of the layout version
	 * @return the layout version that was removed
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion remove(long layoutVersionId)
		throws NoSuchLayoutVersionException {

		return remove((Serializable)layoutVersionId);
	}

	/**
	 * Removes the layout version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout version
	 * @return the layout version that was removed
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion remove(Serializable primaryKey)
		throws NoSuchLayoutVersionException {

		Session session = null;

		try {
			session = openSession();

			LayoutVersion layoutVersion = (LayoutVersion)session.get(
				LayoutVersionImpl.class, primaryKey);

			if (layoutVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutVersionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutVersion);
		}
		catch (NoSuchLayoutVersionException nsee) {
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
	protected LayoutVersion removeImpl(LayoutVersion layoutVersion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutVersion)) {
				layoutVersion = (LayoutVersion)session.get(
					LayoutVersionImpl.class, layoutVersion.getPrimaryKeyObj());
			}

			if (layoutVersion != null) {
				session.delete(layoutVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutVersion != null) {
			clearCache(layoutVersion);
		}

		return layoutVersion;
	}

	@Override
	public LayoutVersion updateImpl(LayoutVersion layoutVersion) {
		boolean isNew = layoutVersion.isNew();

		if (!(layoutVersion instanceof LayoutVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutVersion proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutVersion implementation " +
					layoutVersion.getClass());
		}

		LayoutVersionModelImpl layoutVersionModelImpl =
			(LayoutVersionModelImpl)layoutVersion;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutVersion.setCreateDate(now);
			}
			else {
				layoutVersion.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!layoutVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutVersion.setModifiedDate(now);
			}
			else {
				layoutVersion.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutVersion.isNew()) {
				session.save(layoutVersion);

				layoutVersion.setNew(false);
			}
			else {
				throw new IllegalArgumentException(
					"LayoutVersion is read only, create a new version instead");
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {layoutVersionModelImpl.getPlid()};

			FinderCacheUtil.removeResult(_finderPathCountByPlid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByPlid, args);

			args = new Object[] {layoutVersionModelImpl.getUuid()};

			FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid, args);

			args = new Object[] {
				layoutVersionModelImpl.getUuid(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUuid_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid_Version, args);

			args = new Object[] {
				layoutVersionModelImpl.getUuid(),
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUUID_G_P, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUUID_G_P, args);

			args = new Object[] {
				layoutVersionModelImpl.getUuid(),
				layoutVersionModelImpl.getCompanyId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid_C, args);

			args = new Object[] {
				layoutVersionModelImpl.getUuid(),
				layoutVersionModelImpl.getCompanyId(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByUuid_C_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByUuid_C_Version, args);

			args = new Object[] {layoutVersionModelImpl.getGroupId()};

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGroupId, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByGroupId_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByGroupId_Version, args);

			args = new Object[] {layoutVersionModelImpl.getCompanyId()};

			FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByCompanyId, args);

			args = new Object[] {
				layoutVersionModelImpl.getCompanyId(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByCompanyId_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByCompanyId_Version, args);

			args = new Object[] {layoutVersionModelImpl.getParentPlid()};

			FinderCacheUtil.removeResult(_finderPathCountByParentPlid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByParentPlid, args);

			args = new Object[] {
				layoutVersionModelImpl.getParentPlid(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByParentPlid_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByParentPlid_Version, args);

			args = new Object[] {layoutVersionModelImpl.getIconImageId()};

			FinderCacheUtil.removeResult(_finderPathCountByIconImageId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByIconImageId, args);

			args = new Object[] {
				layoutVersionModelImpl.getIconImageId(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByIconImageId_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByIconImageId_Version, args);

			args = new Object[] {
				layoutVersionModelImpl.getLayoutPrototypeUuid()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByLayoutPrototypeUuid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByLayoutPrototypeUuid, args);

			args = new Object[] {
				layoutVersionModelImpl.getLayoutPrototypeUuid(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByLayoutPrototypeUuid_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByLayoutPrototypeUuid_Version,
				args);

			args = new Object[] {
				layoutVersionModelImpl.getSourcePrototypeLayoutUuid()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountBySourcePrototypeLayoutUuid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid,
				args);

			args = new Object[] {
				layoutVersionModelImpl.getSourcePrototypeLayoutUuid(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountBySourcePrototypeLayoutUuid_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid_Version,
				args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_Version, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.getType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_T, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_T, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.getType(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_T_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_T_Version, args);

			args = new Object[] {
				layoutVersionModelImpl.getCompanyId(),
				layoutVersionModelImpl.getLayoutPrototypeUuid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_L, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_L, args);

			args = new Object[] {
				layoutVersionModelImpl.getCompanyId(),
				layoutVersionModelImpl.getLayoutPrototypeUuid(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_L_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_L_Version, args);

			args = new Object[] {
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getIconImageId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByP_I, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByP_I, args);

			args = new Object[] {
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getIconImageId(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByP_I_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByP_I_Version, args);

			args = new Object[] {
				layoutVersionModelImpl.getClassNameId(),
				layoutVersionModelImpl.getClassPK()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_C, args);

			args = new Object[] {
				layoutVersionModelImpl.getClassNameId(),
				layoutVersionModelImpl.getClassPK(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByC_C_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByC_C_Version, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getLayoutId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_L, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_L, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getParentLayoutId()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_P, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_P, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getParentLayoutId(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_P_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_P_Version, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getType()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_T, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_T, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getType(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_T_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_T_Version, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getFriendlyURL()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_F, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_F, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getSourcePrototypeLayoutUuid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_SPLU, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_SPLU, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getSourcePrototypeLayoutUuid(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByG_P_SPLU_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_SPLU_Version, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getParentLayoutId(),
				layoutVersionModelImpl.isHidden()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_P_H, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_P_H, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getParentLayoutId(),
				layoutVersionModelImpl.isHidden(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByG_P_P_H_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_P_H_Version, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getParentLayoutId(),
				layoutVersionModelImpl.getPriority()
			};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_P_LtP, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_P_LtP, args);

			args = new Object[] {
				layoutVersionModelImpl.getGroupId(),
				layoutVersionModelImpl.isPrivateLayout(),
				layoutVersionModelImpl.getParentLayoutId(),
				layoutVersionModelImpl.getPriority(),
				layoutVersionModelImpl.getVersion()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByG_P_P_LtP_Version, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByG_P_P_LtP_Version, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByPlid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalPlid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByPlid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByPlid, args);

				args = new Object[] {layoutVersionModelImpl.getPlid()};

				FinderCacheUtil.removeResult(_finderPathCountByPlid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByPlid, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);

				args = new Object[] {layoutVersionModelImpl.getUuid()};

				FinderCacheUtil.removeResult(_finderPathCountByUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalUuid(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByUuid_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getUuid(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByUuid_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUUID_G_P.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalUuid(),
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUUID_G_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUUID_G_P, args);

				args = new Object[] {
					layoutVersionModelImpl.getUuid(),
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUUID_G_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUUID_G_P, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalUuid(),
					layoutVersionModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);

				args = new Object[] {
					layoutVersionModelImpl.getUuid(),
					layoutVersionModelImpl.getCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByUuid_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByUuid_C_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalUuid(),
					layoutVersionModelImpl.getOriginalCompanyId(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByUuid_C_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getUuid(),
					layoutVersionModelImpl.getCompanyId(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByUuid_C_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByUuid_C_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);

				args = new Object[] {layoutVersionModelImpl.getGroupId()};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByGroupId_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByGroupId_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByGroupId_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByGroupId_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalCompanyId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);

				args = new Object[] {layoutVersionModelImpl.getCompanyId()};

				FinderCacheUtil.removeResult(_finderPathCountByCompanyId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCompanyId_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalCompanyId(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByCompanyId_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getCompanyId(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByCompanyId_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByCompanyId_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByParentPlid.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalParentPlid()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByParentPlid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByParentPlid, args);

				args = new Object[] {layoutVersionModelImpl.getParentPlid()};

				FinderCacheUtil.removeResult(
					_finderPathCountByParentPlid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByParentPlid, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByParentPlid_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalParentPlid(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByParentPlid_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByParentPlid_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getParentPlid(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByParentPlid_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByParentPlid_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByIconImageId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalIconImageId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByIconImageId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByIconImageId, args);

				args = new Object[] {layoutVersionModelImpl.getIconImageId()};

				FinderCacheUtil.removeResult(
					_finderPathCountByIconImageId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByIconImageId, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByIconImageId_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalIconImageId(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByIconImageId_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByIconImageId_Version,
					args);

				args = new Object[] {
					layoutVersionModelImpl.getIconImageId(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByIconImageId_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByIconImageId_Version,
					args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByLayoutPrototypeUuid.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalLayoutPrototypeUuid()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByLayoutPrototypeUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByLayoutPrototypeUuid,
					args);

				args = new Object[] {
					layoutVersionModelImpl.getLayoutPrototypeUuid()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByLayoutPrototypeUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByLayoutPrototypeUuid,
					args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByLayoutPrototypeUuid_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalLayoutPrototypeUuid(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByLayoutPrototypeUuid_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByLayoutPrototypeUuid_Version,
					args);

				args = new Object[] {
					layoutVersionModelImpl.getLayoutPrototypeUuid(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByLayoutPrototypeUuid_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByLayoutPrototypeUuid_Version,
					args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.
						getOriginalSourcePrototypeLayoutUuid()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountBySourcePrototypeLayoutUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid,
					args);

				args = new Object[] {
					layoutVersionModelImpl.getSourcePrototypeLayoutUuid()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountBySourcePrototypeLayoutUuid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid,
					args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.
						getOriginalSourcePrototypeLayoutUuid(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountBySourcePrototypeLayoutUuid_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid_Version,
					args);

				args = new Object[] {
					layoutVersionModelImpl.getSourcePrototypeLayoutUuid(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountBySourcePrototypeLayoutUuid_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid_Version,
					args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_P_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_P_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_T, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.getType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_T, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_T_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalType(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_T_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_T_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.getType(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_T_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_T_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_L.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalCompanyId(),
					layoutVersionModelImpl.getOriginalLayoutPrototypeUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_L, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_L, args);

				args = new Object[] {
					layoutVersionModelImpl.getCompanyId(),
					layoutVersionModelImpl.getLayoutPrototypeUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_L, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_L, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_L_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalCompanyId(),
					layoutVersionModelImpl.getOriginalLayoutPrototypeUuid(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByC_L_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_L_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getCompanyId(),
					layoutVersionModelImpl.getLayoutPrototypeUuid(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByC_L_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_L_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByP_I.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.getOriginalIconImageId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByP_I, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByP_I, args);

				args = new Object[] {
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getIconImageId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByP_I, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByP_I, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByP_I_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.getOriginalIconImageId(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByP_I_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByP_I_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getIconImageId(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByP_I_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByP_I_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalClassNameId(),
					layoutVersionModelImpl.getOriginalClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);

				args = new Object[] {
					layoutVersionModelImpl.getClassNameId(),
					layoutVersionModelImpl.getClassPK()
				};

				FinderCacheUtil.removeResult(_finderPathCountByC_C, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByC_C_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalClassNameId(),
					layoutVersionModelImpl.getOriginalClassPK(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByC_C_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getClassNameId(),
					layoutVersionModelImpl.getClassPK(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByC_C_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByC_C_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_L.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.getOriginalLayoutId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_L, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_L, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getLayoutId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_L, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_L, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.getOriginalParentLayoutId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_P, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getParentLayoutId()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_P, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_P_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.getOriginalParentLayoutId(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_P_P_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_P_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getParentLayoutId(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_P_P_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_P_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_T.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.getOriginalType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_T, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getType()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_T, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_T, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_T_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.getOriginalType(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_P_T_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_T_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getType(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_P_T_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_T_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_F.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.getOriginalFriendlyURL()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_F, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_F, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getFriendlyURL()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_F, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_F, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_SPLU.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.
						getOriginalSourcePrototypeLayoutUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_SPLU, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_SPLU, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getSourcePrototypeLayoutUuid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_SPLU, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_SPLU, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_SPLU_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.
						getOriginalSourcePrototypeLayoutUuid(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_P_SPLU_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_SPLU_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getSourcePrototypeLayoutUuid(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_P_SPLU_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_SPLU_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_P_H.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.getOriginalParentLayoutId(),
					layoutVersionModelImpl.getOriginalHidden()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_P_H, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_P_H, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getParentLayoutId(),
					layoutVersionModelImpl.isHidden()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_P_H, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_P_H, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_P_H_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.getOriginalParentLayoutId(),
					layoutVersionModelImpl.getOriginalHidden(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_P_P_H_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_P_H_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getParentLayoutId(),
					layoutVersionModelImpl.isHidden(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_P_P_H_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_P_H_Version, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_P_LtP.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.getOriginalParentLayoutId(),
					layoutVersionModelImpl.getOriginalPriority()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_P_LtP, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_P_LtP, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getParentLayoutId(),
					layoutVersionModelImpl.getPriority()
				};

				FinderCacheUtil.removeResult(_finderPathCountByG_P_P_LtP, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_P_LtP, args);
			}

			if ((layoutVersionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByG_P_P_LtP_Version.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutVersionModelImpl.getOriginalGroupId(),
					layoutVersionModelImpl.getOriginalPrivateLayout(),
					layoutVersionModelImpl.getOriginalParentLayoutId(),
					layoutVersionModelImpl.getOriginalPriority(),
					layoutVersionModelImpl.getOriginalVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_P_P_LtP_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_P_LtP_Version, args);

				args = new Object[] {
					layoutVersionModelImpl.getGroupId(),
					layoutVersionModelImpl.isPrivateLayout(),
					layoutVersionModelImpl.getParentLayoutId(),
					layoutVersionModelImpl.getPriority(),
					layoutVersionModelImpl.getVersion()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByG_P_P_LtP_Version, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByG_P_P_LtP_Version, args);
			}
		}

		EntityCacheUtil.putResult(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionImpl.class, layoutVersion.getPrimaryKey(),
			layoutVersion, false);

		clearUniqueFindersCache(layoutVersionModelImpl, false);
		cacheUniqueFindersCache(layoutVersionModelImpl);

		layoutVersion.resetOriginalValues();

		return layoutVersion;
	}

	/**
	 * Returns the layout version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout version
	 * @return the layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLayoutVersionException {

		LayoutVersion layoutVersion = fetchByPrimaryKey(primaryKey);

		if (layoutVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLayoutVersionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutVersion;
	}

	/**
	 * Returns the layout version with the primary key or throws a <code>NoSuchLayoutVersionException</code> if it could not be found.
	 *
	 * @param layoutVersionId the primary key of the layout version
	 * @return the layout version
	 * @throws NoSuchLayoutVersionException if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion findByPrimaryKey(long layoutVersionId)
		throws NoSuchLayoutVersionException {

		return findByPrimaryKey((Serializable)layoutVersionId);
	}

	/**
	 * Returns the layout version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutVersionId the primary key of the layout version
	 * @return the layout version, or <code>null</code> if a layout version with the primary key could not be found
	 */
	@Override
	public LayoutVersion fetchByPrimaryKey(long layoutVersionId) {
		return fetchByPrimaryKey((Serializable)layoutVersionId);
	}

	/**
	 * Returns all the layout versions.
	 *
	 * @return the layout versions
	 */
	@Override
	public List<LayoutVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @return the range of layout versions
	 */
	@Override
	public List<LayoutVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout versions
	 */
	@Override
	public List<LayoutVersion> findAll(
		int start, int end,
		OrderByComparator<LayoutVersion> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout versions
	 * @param end the upper bound of the range of layout versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout versions
	 */
	@Override
	public List<LayoutVersion> findAll(
		int start, int end, OrderByComparator<LayoutVersion> orderByComparator,
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
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<LayoutVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutVersion>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTVERSION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTVERSION;

				if (pagination) {
					sql = sql.concat(LayoutVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutVersion>)QueryUtil.list(
						q, getDialect(), start, end);
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
	 * Removes all the layout versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutVersion layoutVersion : findAll()) {
			remove(layoutVersion);
		}
	}

	/**
	 * Returns the number of layout versions.
	 *
	 * @return the number of layout versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTVERSION);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(
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
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "layoutVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout version persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByPlid = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPlid",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByPlid = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPlid", new String[] {Long.class.getName()},
			LayoutVersionModelImpl.PLID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByPlid = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] {Long.class.getName()});

		_finderPathFetchByPlid_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByPlid_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			LayoutVersionModelImpl.PLID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByPlid_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid_Version",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByUuid = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid", new String[] {String.class.getName()},
			LayoutVersionModelImpl.UUID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByUuid = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByUuid_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_Version",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()},
			LayoutVersionModelImpl.UUID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByUuid_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByUUID_G_P = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUUID_G_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUUID_G_P = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUUID_G_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			LayoutVersionModelImpl.UUID_COLUMN_BITMASK |
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByUUID_G_P = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUUID_G_P",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

		_finderPathFetchByUUID_G_P_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByUUID_G_P_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			},
			LayoutVersionModelImpl.UUID_COLUMN_BITMASK |
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByUUID_G_P_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByUUID_G_P_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			LayoutVersionModelImpl.UUID_COLUMN_BITMASK |
			LayoutVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByUuid_C = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByUuid_C_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByUuid_C_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			LayoutVersionModelImpl.UUID_COLUMN_BITMASK |
			LayoutVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByUuid_C_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C_Version",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByGroupId = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId", new String[] {Long.class.getName()},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByGroupId_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByGroupId_Version",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByGroupId_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByGroupId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByGroupId_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId", new String[] {Long.class.getName()},
			LayoutVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByCompanyId = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByCompanyId_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCompanyId_Version",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCompanyId_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCompanyId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			LayoutVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByCompanyId_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCompanyId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByParentPlid = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByParentPlid",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByParentPlid = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByParentPlid", new String[] {Long.class.getName()},
			LayoutVersionModelImpl.PARENTPLID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByParentPlid = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByParentPlid",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByParentPlid_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByParentPlid_Version",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByParentPlid_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByParentPlid_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			LayoutVersionModelImpl.PARENTPLID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByParentPlid_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByParentPlid_Version",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByIconImageId = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByIconImageId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByIconImageId = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByIconImageId", new String[] {Long.class.getName()},
			LayoutVersionModelImpl.ICONIMAGEID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByIconImageId = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByIconImageId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByIconImageId_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByIconImageId_Version",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByIconImageId_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByIconImageId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()},
			LayoutVersionModelImpl.ICONIMAGEID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByIconImageId_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByIconImageId_Version",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByLayoutPrototypeUuid = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLayoutPrototypeUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByLayoutPrototypeUuid = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutPrototypeUuid", new String[] {String.class.getName()},
			LayoutVersionModelImpl.LAYOUTPROTOTYPEUUID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLayoutPrototypeUuid = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutPrototypeUuid",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindByLayoutPrototypeUuid_Version =
			new FinderPath(
				LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByLayoutPrototypeUuid_Version",
				new String[] {
					String.class.getName(), Integer.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByLayoutPrototypeUuid_Version =
			new FinderPath(
				LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByLayoutPrototypeUuid_Version",
				new String[] {String.class.getName(), Integer.class.getName()},
				LayoutVersionModelImpl.LAYOUTPROTOTYPEUUID_COLUMN_BITMASK |
				LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLayoutPrototypeUuid_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutPrototypeUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindBySourcePrototypeLayoutUuid =
			new FinderPath(
				LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findBySourcePrototypeLayoutUuid",
				new String[] {
					String.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid =
			new FinderPath(
				LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findBySourcePrototypeLayoutUuid",
				new String[] {String.class.getName()},
				LayoutVersionModelImpl.
					SOURCEPROTOTYPELAYOUTUUID_COLUMN_BITMASK |
				LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountBySourcePrototypeLayoutUuid = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySourcePrototypeLayoutUuid",
			new String[] {String.class.getName()});

		_finderPathWithPaginationFindBySourcePrototypeLayoutUuid_Version =
			new FinderPath(
				LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findBySourcePrototypeLayoutUuid_Version",
				new String[] {
					String.class.getName(), Integer.class.getName(),
					Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindBySourcePrototypeLayoutUuid_Version =
			new FinderPath(
				LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findBySourcePrototypeLayoutUuid_Version",
				new String[] {String.class.getName(), Integer.class.getName()},
				LayoutVersionModelImpl.
					SOURCEPROTOTYPELAYOUTUUID_COLUMN_BITMASK |
				LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountBySourcePrototypeLayoutUuid_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySourcePrototypeLayoutUuid_Version",
			new String[] {String.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByG_P = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByG_P_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_T = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_T",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_T = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_T",
			new String[] {Long.class.getName(), String.class.getName()},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.TYPE_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_T = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByG_T_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_T_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_T_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_T_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.TYPE_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_T_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_T_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByC_L = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_L",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_L = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_L",
			new String[] {Long.class.getName(), String.class.getName()},
			LayoutVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			LayoutVersionModelImpl.LAYOUTPROTOTYPEUUID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByC_L = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_L",
			new String[] {Long.class.getName(), String.class.getName()});

		_finderPathWithPaginationFindByC_L_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_L_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_L_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_L_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			},
			LayoutVersionModelImpl.COMPANYID_COLUMN_BITMASK |
			LayoutVersionModelImpl.LAYOUTPROTOTYPEUUID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByC_L_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_L_Version",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByP_I = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByP_I",
			new String[] {
				Boolean.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByP_I = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByP_I",
			new String[] {Boolean.class.getName(), Long.class.getName()},
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.ICONIMAGEID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByP_I = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_I",
			new String[] {Boolean.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByP_I_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByP_I_Version",
			new String[] {
				Boolean.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByP_I_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByP_I_Version",
			new String[] {
				Boolean.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.ICONIMAGEID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByP_I_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_I_Version",
			new String[] {
				Boolean.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByC_C = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			LayoutVersionModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			LayoutVersionModelImpl.CLASSPK_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByC_C_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByC_C_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByC_C_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByC_C_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			LayoutVersionModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			LayoutVersionModelImpl.CLASSPK_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByC_C_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_Version",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_P_L = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_L = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.LAYOUTID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_L = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_L",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});

		_finderPathFetchByG_P_L_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P_L_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.LAYOUTID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_L_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_L_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_P_P = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_P = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_P = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});

		_finderPathWithPaginationFindByG_P_P_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_P_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_P_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_P_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_P_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_P_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_P_T = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_T = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.TYPE_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_T = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_T",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByG_P_T_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_T_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_T_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_T_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.TYPE_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_T_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_T_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_P_F = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_F",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_F = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_F",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.FRIENDLYURL_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_F = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_F",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});

		_finderPathFetchByG_P_F_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_P_F_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.FRIENDLYURL_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_F_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_F_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_P_SPLU = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_SPLU",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_SPLU = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_SPLU",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.SOURCEPROTOTYPELAYOUTUUID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_SPLU = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_SPLU",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			});

		_finderPathWithPaginationFindByG_P_SPLU_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_SPLU_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_SPLU_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_SPLU_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.SOURCEPROTOTYPELAYOUTUUID_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_SPLU_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_P_SPLU_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_P_P_H = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_P_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_P_H = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_P_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutVersionModelImpl.HIDDEN_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_P_H = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_P_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName()
			});

		_finderPathWithPaginationFindByG_P_P_H_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_P_H_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_P_H_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_P_H_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutVersionModelImpl.HIDDEN_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_P_H_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_P_H_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_P_P_LtP = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_P_LtP",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_P_LtP = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_P_LtP",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIORITY_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_P_LtP = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P_P_LtP",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName()
			});

		_finderPathWithPaginationFindByG_P_P_LtP_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_P_P_LtP_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByG_P_P_LtP_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED,
			LayoutVersionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByG_P_P_LtP_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			},
			LayoutVersionModelImpl.GROUPID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
			LayoutVersionModelImpl.PARENTLAYOUTID_COLUMN_BITMASK |
			LayoutVersionModelImpl.PRIORITY_COLUMN_BITMASK |
			LayoutVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_P_LtP_Version = new FinderPath(
			LayoutVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByG_P_P_LtP_Version",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(LayoutVersionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_LAYOUTVERSION =
		"SELECT layoutVersion FROM LayoutVersion layoutVersion";

	private static final String _SQL_SELECT_LAYOUTVERSION_WHERE =
		"SELECT layoutVersion FROM LayoutVersion layoutVersion WHERE ";

	private static final String _SQL_COUNT_LAYOUTVERSION =
		"SELECT COUNT(layoutVersion) FROM LayoutVersion layoutVersion";

	private static final String _SQL_COUNT_LAYOUTVERSION_WHERE =
		"SELECT COUNT(layoutVersion) FROM LayoutVersion layoutVersion WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutVersion.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutVersion exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutVersion exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutVersionPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "type", "hidden", "system"});

}