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

import aQute.bnd.annotation.ProviderType;

import com.liferay.petra.string.StringBundler;

import com.liferay.portal.kernel.bean.BeanReference;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.NoSuchLayoutSetVersionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutSetVersion;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.LayoutSetVersionPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.model.impl.LayoutSetVersionImpl;
import com.liferay.portal.model.impl.LayoutSetVersionModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the layout set version service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@ProviderType
public class LayoutSetVersionPersistenceImpl extends BasePersistenceImpl<LayoutSetVersion>
	implements LayoutSetVersionPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutSetVersionUtil</code> to access the layout set version persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = LayoutSetVersionImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByLayoutSetId;
	private FinderPath _finderPathWithoutPaginationFindByLayoutSetId;
	private FinderPath _finderPathCountByLayoutSetId;

	/**
	 * Returns all the layout set versions where layoutSetId = &#63;.
	 *
	 * @param layoutSetId the layout set ID
	 * @return the matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetId(long layoutSetId) {
		return findByLayoutSetId(layoutSetId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set versions where layoutSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetId the layout set ID
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @return the range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetId(long layoutSetId,
		int start, int end) {
		return findByLayoutSetId(layoutSetId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set versions where layoutSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetId the layout set ID
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetId(long layoutSetId,
		int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return findByLayoutSetId(layoutSetId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout set versions where layoutSetId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetId the layout set ID
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetId(long layoutSetId,
		int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByLayoutSetId;
			finderArgs = new Object[] { layoutSetId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByLayoutSetId;
			finderArgs = new Object[] { layoutSetId, start, end, orderByComparator };
		}

		List<LayoutSetVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetVersion layoutSetVersion : list) {
					if ((layoutSetId != layoutSetVersion.getLayoutSetId())) {
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

			query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETID_LAYOUTSETID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetId);

				if (!pagination) {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
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
	 * Returns the first layout set version in the ordered set where layoutSetId = &#63;.
	 *
	 * @param layoutSetId the layout set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByLayoutSetId_First(long layoutSetId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByLayoutSetId_First(layoutSetId,
				orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetId=");
		msg.append(layoutSetId);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the first layout set version in the ordered set where layoutSetId = &#63;.
	 *
	 * @param layoutSetId the layout set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByLayoutSetId_First(long layoutSetId,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		List<LayoutSetVersion> list = findByLayoutSetId(layoutSetId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set version in the ordered set where layoutSetId = &#63;.
	 *
	 * @param layoutSetId the layout set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByLayoutSetId_Last(long layoutSetId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByLayoutSetId_Last(layoutSetId,
				orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetId=");
		msg.append(layoutSetId);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the last layout set version in the ordered set where layoutSetId = &#63;.
	 *
	 * @param layoutSetId the layout set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByLayoutSetId_Last(long layoutSetId,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		int count = countByLayoutSetId(layoutSetId);

		if (count == 0) {
			return null;
		}

		List<LayoutSetVersion> list = findByLayoutSetId(layoutSetId, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set versions before and after the current layout set version in the ordered set where layoutSetId = &#63;.
	 *
	 * @param layoutSetVersionId the primary key of the current layout set version
	 * @param layoutSetId the layout set ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set version
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion[] findByLayoutSetId_PrevAndNext(
		long layoutSetVersionId, long layoutSetId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = findByPrimaryKey(layoutSetVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetVersion[] array = new LayoutSetVersionImpl[3];

			array[0] = getByLayoutSetId_PrevAndNext(session, layoutSetVersion,
					layoutSetId, orderByComparator, true);

			array[1] = layoutSetVersion;

			array[2] = getByLayoutSetId_PrevAndNext(session, layoutSetVersion,
					layoutSetId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetVersion getByLayoutSetId_PrevAndNext(Session session,
		LayoutSetVersion layoutSetVersion, long layoutSetId,
		OrderByComparator<LayoutSetVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

		query.append(_FINDER_COLUMN_LAYOUTSETID_LAYOUTSETID_2);

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
			query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set versions where layoutSetId = &#63; from the database.
	 *
	 * @param layoutSetId the layout set ID
	 */
	@Override
	public void removeByLayoutSetId(long layoutSetId) {
		for (LayoutSetVersion layoutSetVersion : findByLayoutSetId(
				layoutSetId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetVersion);
		}
	}

	/**
	 * Returns the number of layout set versions where layoutSetId = &#63;.
	 *
	 * @param layoutSetId the layout set ID
	 * @return the number of matching layout set versions
	 */
	@Override
	public int countByLayoutSetId(long layoutSetId) {
		FinderPath finderPath = _finderPathCountByLayoutSetId;

		Object[] finderArgs = new Object[] { layoutSetId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETID_LAYOUTSETID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetId);

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

	private static final String _FINDER_COLUMN_LAYOUTSETID_LAYOUTSETID_2 = "layoutSetVersion.layoutSetId = ?";
	private FinderPath _finderPathFetchByLayoutSetId_Version;
	private FinderPath _finderPathCountByLayoutSetId_Version;

	/**
	 * Returns the layout set version where layoutSetId = &#63; and version = &#63; or throws a <code>NoSuchLayoutSetVersionException</code> if it could not be found.
	 *
	 * @param layoutSetId the layout set ID
	 * @param version the version
	 * @return the matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByLayoutSetId_Version(long layoutSetId,
		int version) throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByLayoutSetId_Version(layoutSetId,
				version);

		if (layoutSetVersion == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("layoutSetId=");
			msg.append(layoutSetId);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLayoutSetVersionException(msg.toString());
		}

		return layoutSetVersion;
	}

	/**
	 * Returns the layout set version where layoutSetId = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param layoutSetId the layout set ID
	 * @param version the version
	 * @return the matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByLayoutSetId_Version(long layoutSetId,
		int version) {
		return fetchByLayoutSetId_Version(layoutSetId, version, true);
	}

	/**
	 * Returns the layout set version where layoutSetId = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param layoutSetId the layout set ID
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByLayoutSetId_Version(long layoutSetId,
		int version, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { layoutSetId, version };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByLayoutSetId_Version,
					finderArgs, this);
		}

		if (result instanceof LayoutSetVersion) {
			LayoutSetVersion layoutSetVersion = (LayoutSetVersion)result;

			if ((layoutSetId != layoutSetVersion.getLayoutSetId()) ||
					(version != layoutSetVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETID_VERSION_LAYOUTSETID_2);

			query.append(_FINDER_COLUMN_LAYOUTSETID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetId);

				qPos.add(version);

				List<LayoutSetVersion> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(_finderPathFetchByLayoutSetId_Version,
						finderArgs, list);
				}
				else {
					LayoutSetVersion layoutSetVersion = list.get(0);

					result = layoutSetVersion;

					cacheResult(layoutSetVersion);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByLayoutSetId_Version,
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
			return (LayoutSetVersion)result;
		}
	}

	/**
	 * Removes the layout set version where layoutSetId = &#63; and version = &#63; from the database.
	 *
	 * @param layoutSetId the layout set ID
	 * @param version the version
	 * @return the layout set version that was removed
	 */
	@Override
	public LayoutSetVersion removeByLayoutSetId_Version(long layoutSetId,
		int version) throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = findByLayoutSetId_Version(layoutSetId,
				version);

		return remove(layoutSetVersion);
	}

	/**
	 * Returns the number of layout set versions where layoutSetId = &#63; and version = &#63;.
	 *
	 * @param layoutSetId the layout set ID
	 * @param version the version
	 * @return the number of matching layout set versions
	 */
	@Override
	public int countByLayoutSetId_Version(long layoutSetId, int version) {
		FinderPath finderPath = _finderPathCountByLayoutSetId_Version;

		Object[] finderArgs = new Object[] { layoutSetId, version };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETID_VERSION_LAYOUTSETID_2);

			query.append(_FINDER_COLUMN_LAYOUTSETID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetId);

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

	private static final String _FINDER_COLUMN_LAYOUTSETID_VERSION_LAYOUTSETID_2 =
		"layoutSetVersion.layoutSetId = ? AND ";
	private static final String _FINDER_COLUMN_LAYOUTSETID_VERSION_VERSION_2 = "layoutSetVersion.version = ?";
	private FinderPath _finderPathWithPaginationFindByGroupId;
	private FinderPath _finderPathWithoutPaginationFindByGroupId;
	private FinderPath _finderPathCountByGroupId;

	/**
	 * Returns all the layout set versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @return the range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByGroupId(long groupId, int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LayoutSetVersion> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout set versions where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByGroupId(long groupId, int start,
		int end, OrderByComparator<LayoutSetVersion> orderByComparator,
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

		List<LayoutSetVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetVersion layoutSetVersion : list) {
					if ((groupId != layoutSetVersion.getGroupId())) {
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

			query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
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
	 * Returns the first layout set version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByGroupId_First(long groupId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByGroupId_First(groupId,
				orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the first layout set version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByGroupId_First(long groupId,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		List<LayoutSetVersion> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByGroupId_Last(long groupId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the last layout set version in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByGroupId_Last(long groupId,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<LayoutSetVersion> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set versions before and after the current layout set version in the ordered set where groupId = &#63;.
	 *
	 * @param layoutSetVersionId the primary key of the current layout set version
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set version
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion[] findByGroupId_PrevAndNext(
		long layoutSetVersionId, long groupId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = findByPrimaryKey(layoutSetVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetVersion[] array = new LayoutSetVersionImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, layoutSetVersion,
					groupId, orderByComparator, true);

			array[1] = layoutSetVersion;

			array[2] = getByGroupId_PrevAndNext(session, layoutSetVersion,
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

	protected LayoutSetVersion getByGroupId_PrevAndNext(Session session,
		LayoutSetVersion layoutSetVersion, long groupId,
		OrderByComparator<LayoutSetVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

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
			query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set versions where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (LayoutSetVersion layoutSetVersion : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetVersion);
		}
	}

	/**
	 * Returns the number of layout set versions where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching layout set versions
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = _finderPathCountByGroupId;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTSETVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "layoutSetVersion.groupId = ?";
	private FinderPath _finderPathWithPaginationFindByGroupId_Version;
	private FinderPath _finderPathWithoutPaginationFindByGroupId_Version;
	private FinderPath _finderPathCountByGroupId_Version;

	/**
	 * Returns all the layout set versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByGroupId_Version(long groupId,
		int version) {
		return findByGroupId_Version(groupId, version, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @return the range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByGroupId_Version(long groupId,
		int version, int start, int end) {
		return findByGroupId_Version(groupId, version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByGroupId_Version(long groupId,
		int version, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return findByGroupId_Version(groupId, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout set versions where groupId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByGroupId_Version(long groupId,
		int version, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
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

		List<LayoutSetVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetVersion layoutSetVersion : list) {
					if ((groupId != layoutSetVersion.getGroupId()) ||
							(version != layoutSetVersion.getVersion())) {
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

			query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_VERSION_GROUPID_2);

			query.append(_FINDER_COLUMN_GROUPID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
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
	 * Returns the first layout set version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByGroupId_Version_First(long groupId,
		int version, OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByGroupId_Version_First(groupId,
				version, orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the first layout set version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByGroupId_Version_First(long groupId,
		int version, OrderByComparator<LayoutSetVersion> orderByComparator) {
		List<LayoutSetVersion> list = findByGroupId_Version(groupId, version,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByGroupId_Version_Last(long groupId,
		int version, OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByGroupId_Version_Last(groupId,
				version, orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the last layout set version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByGroupId_Version_Last(long groupId,
		int version, OrderByComparator<LayoutSetVersion> orderByComparator) {
		int count = countByGroupId_Version(groupId, version);

		if (count == 0) {
			return null;
		}

		List<LayoutSetVersion> list = findByGroupId_Version(groupId, version,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set versions before and after the current layout set version in the ordered set where groupId = &#63; and version = &#63;.
	 *
	 * @param layoutSetVersionId the primary key of the current layout set version
	 * @param groupId the group ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set version
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion[] findByGroupId_Version_PrevAndNext(
		long layoutSetVersionId, long groupId, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = findByPrimaryKey(layoutSetVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetVersion[] array = new LayoutSetVersionImpl[3];

			array[0] = getByGroupId_Version_PrevAndNext(session,
					layoutSetVersion, groupId, version, orderByComparator, true);

			array[1] = layoutSetVersion;

			array[2] = getByGroupId_Version_PrevAndNext(session,
					layoutSetVersion, groupId, version, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetVersion getByGroupId_Version_PrevAndNext(
		Session session, LayoutSetVersion layoutSetVersion, long groupId,
		int version, OrderByComparator<LayoutSetVersion> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

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
			query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
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
					layoutSetVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set versions where groupId = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 */
	@Override
	public void removeByGroupId_Version(long groupId, int version) {
		for (LayoutSetVersion layoutSetVersion : findByGroupId_Version(
				groupId, version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetVersion);
		}
	}

	/**
	 * Returns the number of layout set versions where groupId = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param version the version
	 * @return the number of matching layout set versions
	 */
	@Override
	public int countByGroupId_Version(long groupId, int version) {
		FinderPath finderPath = _finderPathCountByGroupId_Version;

		Object[] finderArgs = new Object[] { groupId, version };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSETVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_VERSION_GROUPID_2 = "layoutSetVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_GROUPID_VERSION_VERSION_2 = "layoutSetVersion.version = ?";
	private FinderPath _finderPathWithPaginationFindByLayoutSetPrototypeUuid;
	private FinderPath _finderPathWithoutPaginationFindByLayoutSetPrototypeUuid;
	private FinderPath _finderPathCountByLayoutSetPrototypeUuid;

	/**
	 * Returns all the layout set versions where layoutSetPrototypeUuid = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @return the matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid) {
		return findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set versions where layoutSetPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @return the range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end) {
		return findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetPrototypeUuid(
		String layoutSetPrototypeUuid, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		layoutSetPrototypeUuid = Objects.toString(layoutSetPrototypeUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByLayoutSetPrototypeUuid;
			finderArgs = new Object[] { layoutSetPrototypeUuid };
		}
		else {
			finderPath = _finderPathWithPaginationFindByLayoutSetPrototypeUuid;
			finderArgs = new Object[] {
					layoutSetPrototypeUuid,
					
					start, end, orderByComparator
				};
		}

		List<LayoutSetVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetVersion layoutSetVersion : list) {
					if (!layoutSetPrototypeUuid.equals(
								layoutSetVersion.getLayoutSetPrototypeUuid())) {
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

			query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

			boolean bindLayoutSetPrototypeUuid = false;

			if (layoutSetPrototypeUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_3);
			}
			else {
				bindLayoutSetPrototypeUuid = true;

				query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutSetPrototypeUuid) {
					qPos.add(layoutSetPrototypeUuid);
				}

				if (!pagination) {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
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
	 * Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByLayoutSetPrototypeUuid_First(layoutSetPrototypeUuid,
				orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetPrototypeUuid=");
		msg.append(layoutSetPrototypeUuid);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByLayoutSetPrototypeUuid_First(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		List<LayoutSetVersion> list = findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByLayoutSetPrototypeUuid_Last(layoutSetPrototypeUuid,
				orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetPrototypeUuid=");
		msg.append(layoutSetPrototypeUuid);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByLayoutSetPrototypeUuid_Last(
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		int count = countByLayoutSetPrototypeUuid(layoutSetPrototypeUuid);

		if (count == 0) {
			return null;
		}

		List<LayoutSetVersion> list = findByLayoutSetPrototypeUuid(layoutSetPrototypeUuid,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set versions before and after the current layout set version in the ordered set where layoutSetPrototypeUuid = &#63;.
	 *
	 * @param layoutSetVersionId the primary key of the current layout set version
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set version
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion[] findByLayoutSetPrototypeUuid_PrevAndNext(
		long layoutSetVersionId, String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		layoutSetPrototypeUuid = Objects.toString(layoutSetPrototypeUuid, "");

		LayoutSetVersion layoutSetVersion = findByPrimaryKey(layoutSetVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetVersion[] array = new LayoutSetVersionImpl[3];

			array[0] = getByLayoutSetPrototypeUuid_PrevAndNext(session,
					layoutSetVersion, layoutSetPrototypeUuid,
					orderByComparator, true);

			array[1] = layoutSetVersion;

			array[2] = getByLayoutSetPrototypeUuid_PrevAndNext(session,
					layoutSetVersion, layoutSetPrototypeUuid,
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

	protected LayoutSetVersion getByLayoutSetPrototypeUuid_PrevAndNext(
		Session session, LayoutSetVersion layoutSetVersion,
		String layoutSetPrototypeUuid,
		OrderByComparator<LayoutSetVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

		boolean bindLayoutSetPrototypeUuid = false;

		if (layoutSetPrototypeUuid.isEmpty()) {
			query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_3);
		}
		else {
			bindLayoutSetPrototypeUuid = true;

			query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_2);
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
			query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindLayoutSetPrototypeUuid) {
			qPos.add(layoutSetPrototypeUuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set versions where layoutSetPrototypeUuid = &#63; from the database.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 */
	@Override
	public void removeByLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {
		for (LayoutSetVersion layoutSetVersion : findByLayoutSetPrototypeUuid(
				layoutSetPrototypeUuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
				null)) {
			remove(layoutSetVersion);
		}
	}

	/**
	 * Returns the number of layout set versions where layoutSetPrototypeUuid = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @return the number of matching layout set versions
	 */
	@Override
	public int countByLayoutSetPrototypeUuid(String layoutSetPrototypeUuid) {
		layoutSetPrototypeUuid = Objects.toString(layoutSetPrototypeUuid, "");

		FinderPath finderPath = _finderPathCountByLayoutSetPrototypeUuid;

		Object[] finderArgs = new Object[] { layoutSetPrototypeUuid };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTSETVERSION_WHERE);

			boolean bindLayoutSetPrototypeUuid = false;

			if (layoutSetPrototypeUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_3);
			}
			else {
				bindLayoutSetPrototypeUuid = true;

				query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutSetPrototypeUuid) {
					qPos.add(layoutSetPrototypeUuid);
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

	private static final String _FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_2 =
		"layoutSetVersion.layoutSetPrototypeUuid = ?";
	private static final String _FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_LAYOUTSETPROTOTYPEUUID_3 =
		"(layoutSetVersion.layoutSetPrototypeUuid IS NULL OR layoutSetVersion.layoutSetPrototypeUuid = '')";
	private FinderPath _finderPathWithPaginationFindByLayoutSetPrototypeUuid_Version;
	private FinderPath _finderPathWithoutPaginationFindByLayoutSetPrototypeUuid_Version;
	private FinderPath _finderPathCountByLayoutSetPrototypeUuid_Version;

	/**
	 * Returns all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param version the version
	 * @return the matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version) {
		return findByLayoutSetPrototypeUuid_Version(layoutSetPrototypeUuid,
			version, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @return the range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version, int start, int end) {
		return findByLayoutSetPrototypeUuid_Version(layoutSetPrototypeUuid,
			version, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return findByLayoutSetPrototypeUuid_Version(layoutSetPrototypeUuid,
			version, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param version the version
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		layoutSetPrototypeUuid = Objects.toString(layoutSetPrototypeUuid, "");

		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByLayoutSetPrototypeUuid_Version;
			finderArgs = new Object[] { layoutSetPrototypeUuid, version };
		}
		else {
			finderPath = _finderPathWithPaginationFindByLayoutSetPrototypeUuid_Version;
			finderArgs = new Object[] {
					layoutSetPrototypeUuid, version,
					
					start, end, orderByComparator
				};
		}

		List<LayoutSetVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetVersion layoutSetVersion : list) {
					if (!layoutSetPrototypeUuid.equals(
								layoutSetVersion.getLayoutSetPrototypeUuid()) ||
							(version != layoutSetVersion.getVersion())) {
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

			query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

			boolean bindLayoutSetPrototypeUuid = false;

			if (layoutSetPrototypeUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_VERSION_LAYOUTSETPROTOTYPEUUID_3);
			}
			else {
				bindLayoutSetPrototypeUuid = true;

				query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_VERSION_LAYOUTSETPROTOTYPEUUID_2);
			}

			query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutSetPrototypeUuid) {
					qPos.add(layoutSetPrototypeUuid);
				}

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
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
	 * Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByLayoutSetPrototypeUuid_Version_First(
		String layoutSetPrototypeUuid, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByLayoutSetPrototypeUuid_Version_First(layoutSetPrototypeUuid,
				version, orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetPrototypeUuid=");
		msg.append(layoutSetPrototypeUuid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the first layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByLayoutSetPrototypeUuid_Version_First(
		String layoutSetPrototypeUuid, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		List<LayoutSetVersion> list = findByLayoutSetPrototypeUuid_Version(layoutSetPrototypeUuid,
				version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByLayoutSetPrototypeUuid_Version_Last(
		String layoutSetPrototypeUuid, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByLayoutSetPrototypeUuid_Version_Last(layoutSetPrototypeUuid,
				version, orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetPrototypeUuid=");
		msg.append(layoutSetPrototypeUuid);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the last layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByLayoutSetPrototypeUuid_Version_Last(
		String layoutSetPrototypeUuid, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		int count = countByLayoutSetPrototypeUuid_Version(layoutSetPrototypeUuid,
				version);

		if (count == 0) {
			return null;
		}

		List<LayoutSetVersion> list = findByLayoutSetPrototypeUuid_Version(layoutSetPrototypeUuid,
				version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set versions before and after the current layout set version in the ordered set where layoutSetPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutSetVersionId the primary key of the current layout set version
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set version
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion[] findByLayoutSetPrototypeUuid_Version_PrevAndNext(
		long layoutSetVersionId, String layoutSetPrototypeUuid, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		layoutSetPrototypeUuid = Objects.toString(layoutSetPrototypeUuid, "");

		LayoutSetVersion layoutSetVersion = findByPrimaryKey(layoutSetVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetVersion[] array = new LayoutSetVersionImpl[3];

			array[0] = getByLayoutSetPrototypeUuid_Version_PrevAndNext(session,
					layoutSetVersion, layoutSetPrototypeUuid, version,
					orderByComparator, true);

			array[1] = layoutSetVersion;

			array[2] = getByLayoutSetPrototypeUuid_Version_PrevAndNext(session,
					layoutSetVersion, layoutSetPrototypeUuid, version,
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

	protected LayoutSetVersion getByLayoutSetPrototypeUuid_Version_PrevAndNext(
		Session session, LayoutSetVersion layoutSetVersion,
		String layoutSetPrototypeUuid, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

		boolean bindLayoutSetPrototypeUuid = false;

		if (layoutSetPrototypeUuid.isEmpty()) {
			query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_VERSION_LAYOUTSETPROTOTYPEUUID_3);
		}
		else {
			bindLayoutSetPrototypeUuid = true;

			query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_VERSION_LAYOUTSETPROTOTYPEUUID_2);
		}

		query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_VERSION_VERSION_2);

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
			query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindLayoutSetPrototypeUuid) {
			qPos.add(layoutSetPrototypeUuid);
		}

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63; from the database.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param version the version
	 */
	@Override
	public void removeByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version) {
		for (LayoutSetVersion layoutSetVersion : findByLayoutSetPrototypeUuid_Version(
				layoutSetPrototypeUuid, version, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layoutSetVersion);
		}
	}

	/**
	 * Returns the number of layout set versions where layoutSetPrototypeUuid = &#63; and version = &#63;.
	 *
	 * @param layoutSetPrototypeUuid the layout set prototype uuid
	 * @param version the version
	 * @return the number of matching layout set versions
	 */
	@Override
	public int countByLayoutSetPrototypeUuid_Version(
		String layoutSetPrototypeUuid, int version) {
		layoutSetPrototypeUuid = Objects.toString(layoutSetPrototypeUuid, "");

		FinderPath finderPath = _finderPathCountByLayoutSetPrototypeUuid_Version;

		Object[] finderArgs = new Object[] { layoutSetPrototypeUuid, version };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSETVERSION_WHERE);

			boolean bindLayoutSetPrototypeUuid = false;

			if (layoutSetPrototypeUuid.isEmpty()) {
				query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_VERSION_LAYOUTSETPROTOTYPEUUID_3);
			}
			else {
				bindLayoutSetPrototypeUuid = true;

				query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_VERSION_LAYOUTSETPROTOTYPEUUID_2);
			}

			query.append(_FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindLayoutSetPrototypeUuid) {
					qPos.add(layoutSetPrototypeUuid);
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

	private static final String _FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_VERSION_LAYOUTSETPROTOTYPEUUID_2 =
		"layoutSetVersion.layoutSetPrototypeUuid = ? AND ";
	private static final String _FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_VERSION_LAYOUTSETPROTOTYPEUUID_3 =
		"(layoutSetVersion.layoutSetPrototypeUuid IS NULL OR layoutSetVersion.layoutSetPrototypeUuid = '') AND ";
	private static final String _FINDER_COLUMN_LAYOUTSETPROTOTYPEUUID_VERSION_VERSION_2 =
		"layoutSetVersion.version = ?";
	private FinderPath _finderPathWithPaginationFindByG_P;
	private FinderPath _finderPathWithoutPaginationFindByG_P;
	private FinderPath _finderPathCountByG_P;

	/**
	 * Returns all the layout set versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByG_P(long groupId, boolean privateLayout) {
		return findByG_P(groupId, privateLayout, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @return the range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByG_P(long groupId,
		boolean privateLayout, int start, int end) {
		return findByG_P(groupId, privateLayout, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByG_P(long groupId,
		boolean privateLayout, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return findByG_P(groupId, privateLayout, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout set versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByG_P(long groupId,
		boolean privateLayout, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByG_P;
			finderArgs = new Object[] { groupId, privateLayout };
		}
		else {
			finderPath = _finderPathWithPaginationFindByG_P;
			finderArgs = new Object[] {
					groupId, privateLayout,
					
					start, end, orderByComparator
				};
		}

		List<LayoutSetVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetVersion layoutSetVersion : list) {
					if ((groupId != layoutSetVersion.getGroupId()) ||
							(privateLayout != layoutSetVersion.isPrivateLayout())) {
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

			query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_G_P_GROUPID_2);

			query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
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
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
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
	 * Returns the first layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByG_P_First(long groupId,
		boolean privateLayout,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByG_P_First(groupId,
				privateLayout, orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the first layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByG_P_First(long groupId,
		boolean privateLayout,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		List<LayoutSetVersion> list = findByG_P(groupId, privateLayout, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByG_P_Last(long groupId, boolean privateLayout,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByG_P_Last(groupId,
				privateLayout, orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", privateLayout=");
		msg.append(privateLayout);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the last layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByG_P_Last(long groupId,
		boolean privateLayout,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		int count = countByG_P(groupId, privateLayout);

		if (count == 0) {
			return null;
		}

		List<LayoutSetVersion> list = findByG_P(groupId, privateLayout,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set versions before and after the current layout set version in the ordered set where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param layoutSetVersionId the primary key of the current layout set version
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set version
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion[] findByG_P_PrevAndNext(long layoutSetVersionId,
		long groupId, boolean privateLayout,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = findByPrimaryKey(layoutSetVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetVersion[] array = new LayoutSetVersionImpl[3];

			array[0] = getByG_P_PrevAndNext(session, layoutSetVersion, groupId,
					privateLayout, orderByComparator, true);

			array[1] = layoutSetVersion;

			array[2] = getByG_P_PrevAndNext(session, layoutSetVersion, groupId,
					privateLayout, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetVersion getByG_P_PrevAndNext(Session session,
		LayoutSetVersion layoutSetVersion, long groupId, boolean privateLayout,
		OrderByComparator<LayoutSetVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

		query.append(_FINDER_COLUMN_G_P_GROUPID_2);

		query.append(_FINDER_COLUMN_G_P_PRIVATELAYOUT_2);

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
			query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(privateLayout);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set versions where groupId = &#63; and privateLayout = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 */
	@Override
	public void removeByG_P(long groupId, boolean privateLayout) {
		for (LayoutSetVersion layoutSetVersion : findByG_P(groupId,
				privateLayout, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetVersion);
		}
	}

	/**
	 * Returns the number of layout set versions where groupId = &#63; and privateLayout = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @return the number of matching layout set versions
	 */
	@Override
	public int countByG_P(long groupId, boolean privateLayout) {
		FinderPath finderPath = _finderPathCountByG_P;

		Object[] finderArgs = new Object[] { groupId, privateLayout };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSETVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_G_P_GROUPID_2 = "layoutSetVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_PRIVATELAYOUT_2 = "layoutSetVersion.privateLayout = ?";
	private FinderPath _finderPathFetchByG_P_Version;
	private FinderPath _finderPathCountByG_P_Version;

	/**
	 * Returns the layout set version where groupId = &#63; and privateLayout = &#63; and version = &#63; or throws a <code>NoSuchLayoutSetVersionException</code> if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByG_P_Version(long groupId,
		boolean privateLayout, int version)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByG_P_Version(groupId,
				privateLayout, version);

		if (layoutSetVersion == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", privateLayout=");
			msg.append(privateLayout);

			msg.append(", version=");
			msg.append(version);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLayoutSetVersionException(msg.toString());
		}

		return layoutSetVersion;
	}

	/**
	 * Returns the layout set version where groupId = &#63; and privateLayout = &#63; and version = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByG_P_Version(long groupId,
		boolean privateLayout, int version) {
		return fetchByG_P_Version(groupId, privateLayout, version, true);
	}

	/**
	 * Returns the layout set version where groupId = &#63; and privateLayout = &#63; and version = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByG_P_Version(long groupId,
		boolean privateLayout, int version, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, privateLayout, version };

		Object result = null;

		if (retrieveFromCache) {
			result = FinderCacheUtil.getResult(_finderPathFetchByG_P_Version,
					finderArgs, this);
		}

		if (result instanceof LayoutSetVersion) {
			LayoutSetVersion layoutSetVersion = (LayoutSetVersion)result;

			if ((groupId != layoutSetVersion.getGroupId()) ||
					(privateLayout != layoutSetVersion.isPrivateLayout()) ||
					(version != layoutSetVersion.getVersion())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

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

				List<LayoutSetVersion> list = q.list();

				if (list.isEmpty()) {
					FinderCacheUtil.putResult(_finderPathFetchByG_P_Version,
						finderArgs, list);
				}
				else {
					LayoutSetVersion layoutSetVersion = list.get(0);

					result = layoutSetVersion;

					cacheResult(layoutSetVersion);
				}
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathFetchByG_P_Version,
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
			return (LayoutSetVersion)result;
		}
	}

	/**
	 * Removes the layout set version where groupId = &#63; and privateLayout = &#63; and version = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the layout set version that was removed
	 */
	@Override
	public LayoutSetVersion removeByG_P_Version(long groupId,
		boolean privateLayout, int version)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = findByG_P_Version(groupId,
				privateLayout, version);

		return remove(layoutSetVersion);
	}

	/**
	 * Returns the number of layout set versions where groupId = &#63; and privateLayout = &#63; and version = &#63;.
	 *
	 * @param groupId the group ID
	 * @param privateLayout the private layout
	 * @param version the version
	 * @return the number of matching layout set versions
	 */
	@Override
	public int countByG_P_Version(long groupId, boolean privateLayout,
		int version) {
		FinderPath finderPath = _finderPathCountByG_P_Version;

		Object[] finderArgs = new Object[] { groupId, privateLayout, version };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTSETVERSION_WHERE);

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

	private static final String _FINDER_COLUMN_G_P_VERSION_GROUPID_2 = "layoutSetVersion.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_P_VERSION_PRIVATELAYOUT_2 = "layoutSetVersion.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_G_P_VERSION_VERSION_2 = "layoutSetVersion.version = ?";
	private FinderPath _finderPathWithPaginationFindByP_L;
	private FinderPath _finderPathWithoutPaginationFindByP_L;
	private FinderPath _finderPathCountByP_L;

	/**
	 * Returns all the layout set versions where privateLayout = &#63; and logoId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @return the matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByP_L(boolean privateLayout, long logoId) {
		return findByP_L(privateLayout, logoId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set versions where privateLayout = &#63; and logoId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @return the range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByP_L(boolean privateLayout, long logoId,
		int start, int end) {
		return findByP_L(privateLayout, logoId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set versions where privateLayout = &#63; and logoId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByP_L(boolean privateLayout, long logoId,
		int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return findByP_L(privateLayout, logoId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout set versions where privateLayout = &#63; and logoId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByP_L(boolean privateLayout, long logoId,
		int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByP_L;
			finderArgs = new Object[] { privateLayout, logoId };
		}
		else {
			finderPath = _finderPathWithPaginationFindByP_L;
			finderArgs = new Object[] {
					privateLayout, logoId,
					
					start, end, orderByComparator
				};
		}

		List<LayoutSetVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetVersion layoutSetVersion : list) {
					if ((privateLayout != layoutSetVersion.isPrivateLayout()) ||
							(logoId != layoutSetVersion.getLogoId())) {
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

			query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_P_L_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_P_L_LOGOID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(privateLayout);

				qPos.add(logoId);

				if (!pagination) {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
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
	 * Returns the first layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByP_L_First(boolean privateLayout, long logoId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByP_L_First(privateLayout,
				logoId, orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("privateLayout=");
		msg.append(privateLayout);

		msg.append(", logoId=");
		msg.append(logoId);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the first layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByP_L_First(boolean privateLayout,
		long logoId, OrderByComparator<LayoutSetVersion> orderByComparator) {
		List<LayoutSetVersion> list = findByP_L(privateLayout, logoId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByP_L_Last(boolean privateLayout, long logoId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByP_L_Last(privateLayout,
				logoId, orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("privateLayout=");
		msg.append(privateLayout);

		msg.append(", logoId=");
		msg.append(logoId);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the last layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByP_L_Last(boolean privateLayout, long logoId,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		int count = countByP_L(privateLayout, logoId);

		if (count == 0) {
			return null;
		}

		List<LayoutSetVersion> list = findByP_L(privateLayout, logoId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set versions before and after the current layout set version in the ordered set where privateLayout = &#63; and logoId = &#63;.
	 *
	 * @param layoutSetVersionId the primary key of the current layout set version
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set version
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion[] findByP_L_PrevAndNext(long layoutSetVersionId,
		boolean privateLayout, long logoId,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = findByPrimaryKey(layoutSetVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetVersion[] array = new LayoutSetVersionImpl[3];

			array[0] = getByP_L_PrevAndNext(session, layoutSetVersion,
					privateLayout, logoId, orderByComparator, true);

			array[1] = layoutSetVersion;

			array[2] = getByP_L_PrevAndNext(session, layoutSetVersion,
					privateLayout, logoId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetVersion getByP_L_PrevAndNext(Session session,
		LayoutSetVersion layoutSetVersion, boolean privateLayout, long logoId,
		OrderByComparator<LayoutSetVersion> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

		query.append(_FINDER_COLUMN_P_L_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_P_L_LOGOID_2);

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
			query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(privateLayout);

		qPos.add(logoId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set versions where privateLayout = &#63; and logoId = &#63; from the database.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 */
	@Override
	public void removeByP_L(boolean privateLayout, long logoId) {
		for (LayoutSetVersion layoutSetVersion : findByP_L(privateLayout,
				logoId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(layoutSetVersion);
		}
	}

	/**
	 * Returns the number of layout set versions where privateLayout = &#63; and logoId = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @return the number of matching layout set versions
	 */
	@Override
	public int countByP_L(boolean privateLayout, long logoId) {
		FinderPath finderPath = _finderPathCountByP_L;

		Object[] finderArgs = new Object[] { privateLayout, logoId };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_P_L_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_P_L_LOGOID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(privateLayout);

				qPos.add(logoId);

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

	private static final String _FINDER_COLUMN_P_L_PRIVATELAYOUT_2 = "layoutSetVersion.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_P_L_LOGOID_2 = "layoutSetVersion.logoId = ?";
	private FinderPath _finderPathWithPaginationFindByP_L_Version;
	private FinderPath _finderPathWithoutPaginationFindByP_L_Version;
	private FinderPath _finderPathCountByP_L_Version;

	/**
	 * Returns all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param version the version
	 * @return the matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByP_L_Version(boolean privateLayout,
		long logoId, int version) {
		return findByP_L_Version(privateLayout, logoId, version,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param version the version
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @return the range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByP_L_Version(boolean privateLayout,
		long logoId, int version, int start, int end) {
		return findByP_L_Version(privateLayout, logoId, version, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param version the version
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByP_L_Version(boolean privateLayout,
		long logoId, int version, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return findByP_L_Version(privateLayout, logoId, version, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param version the version
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findByP_L_Version(boolean privateLayout,
		long logoId, int version, int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = _finderPathWithoutPaginationFindByP_L_Version;
			finderArgs = new Object[] { privateLayout, logoId, version };
		}
		else {
			finderPath = _finderPathWithPaginationFindByP_L_Version;
			finderArgs = new Object[] {
					privateLayout, logoId, version,
					
					start, end, orderByComparator
				};
		}

		List<LayoutSetVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutSetVersion layoutSetVersion : list) {
					if ((privateLayout != layoutSetVersion.isPrivateLayout()) ||
							(logoId != layoutSetVersion.getLogoId()) ||
							(version != layoutSetVersion.getVersion())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler query = null;

			if (orderByComparator != null) {
				query = new StringBundler(5 +
						(orderByComparator.getOrderByFields().length * 2));
			}
			else {
				query = new StringBundler(5);
			}

			query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_P_L_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_P_L_VERSION_LOGOID_2);

			query.append(_FINDER_COLUMN_P_L_VERSION_VERSION_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(privateLayout);

				qPos.add(logoId);

				qPos.add(version);

				if (!pagination) {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
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
	 * Returns the first layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByP_L_Version_First(boolean privateLayout,
		long logoId, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByP_L_Version_First(privateLayout,
				logoId, version, orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("privateLayout=");
		msg.append(privateLayout);

		msg.append(", logoId=");
		msg.append(logoId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the first layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByP_L_Version_First(boolean privateLayout,
		long logoId, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		List<LayoutSetVersion> list = findByP_L_Version(privateLayout, logoId,
				version, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version
	 * @throws NoSuchLayoutSetVersionException if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion findByP_L_Version_Last(boolean privateLayout,
		long logoId, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByP_L_Version_Last(privateLayout,
				logoId, version, orderByComparator);

		if (layoutSetVersion != null) {
			return layoutSetVersion;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("privateLayout=");
		msg.append(privateLayout);

		msg.append(", logoId=");
		msg.append(logoId);

		msg.append(", version=");
		msg.append(version);

		msg.append("}");

		throw new NoSuchLayoutSetVersionException(msg.toString());
	}

	/**
	 * Returns the last layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout set version, or <code>null</code> if a matching layout set version could not be found
	 */
	@Override
	public LayoutSetVersion fetchByP_L_Version_Last(boolean privateLayout,
		long logoId, int version,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		int count = countByP_L_Version(privateLayout, logoId, version);

		if (count == 0) {
			return null;
		}

		List<LayoutSetVersion> list = findByP_L_Version(privateLayout, logoId,
				version, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout set versions before and after the current layout set version in the ordered set where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	 *
	 * @param layoutSetVersionId the primary key of the current layout set version
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param version the version
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout set version
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion[] findByP_L_Version_PrevAndNext(
		long layoutSetVersionId, boolean privateLayout, long logoId,
		int version, OrderByComparator<LayoutSetVersion> orderByComparator)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = findByPrimaryKey(layoutSetVersionId);

		Session session = null;

		try {
			session = openSession();

			LayoutSetVersion[] array = new LayoutSetVersionImpl[3];

			array[0] = getByP_L_Version_PrevAndNext(session, layoutSetVersion,
					privateLayout, logoId, version, orderByComparator, true);

			array[1] = layoutSetVersion;

			array[2] = getByP_L_Version_PrevAndNext(session, layoutSetVersion,
					privateLayout, logoId, version, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutSetVersion getByP_L_Version_PrevAndNext(Session session,
		LayoutSetVersion layoutSetVersion, boolean privateLayout, long logoId,
		int version, OrderByComparator<LayoutSetVersion> orderByComparator,
		boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTSETVERSION_WHERE);

		query.append(_FINDER_COLUMN_P_L_VERSION_PRIVATELAYOUT_2);

		query.append(_FINDER_COLUMN_P_L_VERSION_LOGOID_2);

		query.append(_FINDER_COLUMN_P_L_VERSION_VERSION_2);

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
			query.append(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(privateLayout);

		qPos.add(logoId);

		qPos.add(version);

		if (orderByComparator != null) {
			for (Object orderByConditionValue : orderByComparator.getOrderByConditionValues(
					layoutSetVersion)) {
				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutSetVersion> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63; from the database.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param version the version
	 */
	@Override
	public void removeByP_L_Version(boolean privateLayout, long logoId,
		int version) {
		for (LayoutSetVersion layoutSetVersion : findByP_L_Version(
				privateLayout, logoId, version, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(layoutSetVersion);
		}
	}

	/**
	 * Returns the number of layout set versions where privateLayout = &#63; and logoId = &#63; and version = &#63;.
	 *
	 * @param privateLayout the private layout
	 * @param logoId the logo ID
	 * @param version the version
	 * @return the number of matching layout set versions
	 */
	@Override
	public int countByP_L_Version(boolean privateLayout, long logoId,
		int version) {
		FinderPath finderPath = _finderPathCountByP_L_Version;

		Object[] finderArgs = new Object[] { privateLayout, logoId, version };

		Long count = (Long)FinderCacheUtil.getResult(finderPath, finderArgs,
				this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTSETVERSION_WHERE);

			query.append(_FINDER_COLUMN_P_L_VERSION_PRIVATELAYOUT_2);

			query.append(_FINDER_COLUMN_P_L_VERSION_LOGOID_2);

			query.append(_FINDER_COLUMN_P_L_VERSION_VERSION_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(privateLayout);

				qPos.add(logoId);

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

	private static final String _FINDER_COLUMN_P_L_VERSION_PRIVATELAYOUT_2 = "layoutSetVersion.privateLayout = ? AND ";
	private static final String _FINDER_COLUMN_P_L_VERSION_LOGOID_2 = "layoutSetVersion.logoId = ? AND ";
	private static final String _FINDER_COLUMN_P_L_VERSION_VERSION_2 = "layoutSetVersion.version = ?";

	public LayoutSetVersionPersistenceImpl() {
		setModelClass(LayoutSetVersion.class);

		setModelImplClass(LayoutSetVersionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the layout set version in the entity cache if it is enabled.
	 *
	 * @param layoutSetVersion the layout set version
	 */
	@Override
	public void cacheResult(LayoutSetVersion layoutSetVersion) {
		EntityCacheUtil.putResult(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionImpl.class, layoutSetVersion.getPrimaryKey(),
			layoutSetVersion);

		FinderCacheUtil.putResult(_finderPathFetchByLayoutSetId_Version,
			new Object[] {
				layoutSetVersion.getLayoutSetId(), layoutSetVersion.getVersion()
			}, layoutSetVersion);

		FinderCacheUtil.putResult(_finderPathFetchByG_P_Version,
			new Object[] {
				layoutSetVersion.getGroupId(),
				layoutSetVersion.isPrivateLayout(),
				layoutSetVersion.getVersion()
			}, layoutSetVersion);

		layoutSetVersion.resetOriginalValues();
	}

	/**
	 * Caches the layout set versions in the entity cache if it is enabled.
	 *
	 * @param layoutSetVersions the layout set versions
	 */
	@Override
	public void cacheResult(List<LayoutSetVersion> layoutSetVersions) {
		for (LayoutSetVersion layoutSetVersion : layoutSetVersions) {
			if (EntityCacheUtil.getResult(
						LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
						LayoutSetVersionImpl.class,
						layoutSetVersion.getPrimaryKey()) == null) {
				cacheResult(layoutSetVersion);
			}
			else {
				layoutSetVersion.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout set versions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(LayoutSetVersionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout set version.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutSetVersion layoutSetVersion) {
		EntityCacheUtil.removeResult(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionImpl.class, layoutSetVersion.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LayoutSetVersionModelImpl)layoutSetVersion,
			true);
	}

	@Override
	public void clearCache(List<LayoutSetVersion> layoutSetVersions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutSetVersion layoutSetVersion : layoutSetVersions) {
			EntityCacheUtil.removeResult(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionImpl.class, layoutSetVersion.getPrimaryKey());

			clearUniqueFindersCache((LayoutSetVersionModelImpl)layoutSetVersion,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutSetVersionModelImpl layoutSetVersionModelImpl) {
		Object[] args = new Object[] {
				layoutSetVersionModelImpl.getLayoutSetId(),
				layoutSetVersionModelImpl.getVersion()
			};

		FinderCacheUtil.putResult(_finderPathCountByLayoutSetId_Version, args,
			Long.valueOf(1), false);
		FinderCacheUtil.putResult(_finderPathFetchByLayoutSetId_Version, args,
			layoutSetVersionModelImpl, false);

		args = new Object[] {
				layoutSetVersionModelImpl.getGroupId(),
				layoutSetVersionModelImpl.isPrivateLayout(),
				layoutSetVersionModelImpl.getVersion()
			};

		FinderCacheUtil.putResult(_finderPathCountByG_P_Version, args,
			Long.valueOf(1), false);
		FinderCacheUtil.putResult(_finderPathFetchByG_P_Version, args,
			layoutSetVersionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LayoutSetVersionModelImpl layoutSetVersionModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutSetVersionModelImpl.getLayoutSetId(),
					layoutSetVersionModelImpl.getVersion()
				};

			FinderCacheUtil.removeResult(_finderPathCountByLayoutSetId_Version,
				args);
			FinderCacheUtil.removeResult(_finderPathFetchByLayoutSetId_Version,
				args);
		}

		if ((layoutSetVersionModelImpl.getColumnBitmask() &
				_finderPathFetchByLayoutSetId_Version.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutSetVersionModelImpl.getOriginalLayoutSetId(),
					layoutSetVersionModelImpl.getOriginalVersion()
				};

			FinderCacheUtil.removeResult(_finderPathCountByLayoutSetId_Version,
				args);
			FinderCacheUtil.removeResult(_finderPathFetchByLayoutSetId_Version,
				args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
					layoutSetVersionModelImpl.getGroupId(),
					layoutSetVersionModelImpl.isPrivateLayout(),
					layoutSetVersionModelImpl.getVersion()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_Version, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_Version, args);
		}

		if ((layoutSetVersionModelImpl.getColumnBitmask() &
				_finderPathFetchByG_P_Version.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					layoutSetVersionModelImpl.getOriginalGroupId(),
					layoutSetVersionModelImpl.getOriginalPrivateLayout(),
					layoutSetVersionModelImpl.getOriginalVersion()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P_Version, args);
			FinderCacheUtil.removeResult(_finderPathFetchByG_P_Version, args);
		}
	}

	/**
	 * Creates a new layout set version with the primary key. Does not add the layout set version to the database.
	 *
	 * @param layoutSetVersionId the primary key for the new layout set version
	 * @return the new layout set version
	 */
	@Override
	public LayoutSetVersion create(long layoutSetVersionId) {
		LayoutSetVersion layoutSetVersion = new LayoutSetVersionImpl();

		layoutSetVersion.setNew(true);
		layoutSetVersion.setPrimaryKey(layoutSetVersionId);

		layoutSetVersion.setCompanyId(companyProvider.getCompanyId());

		return layoutSetVersion;
	}

	/**
	 * Removes the layout set version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutSetVersionId the primary key of the layout set version
	 * @return the layout set version that was removed
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion remove(long layoutSetVersionId)
		throws NoSuchLayoutSetVersionException {
		return remove((Serializable)layoutSetVersionId);
	}

	/**
	 * Removes the layout set version with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout set version
	 * @return the layout set version that was removed
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion remove(Serializable primaryKey)
		throws NoSuchLayoutSetVersionException {
		Session session = null;

		try {
			session = openSession();

			LayoutSetVersion layoutSetVersion = (LayoutSetVersion)session.get(LayoutSetVersionImpl.class,
					primaryKey);

			if (layoutSetVersion == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutSetVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(layoutSetVersion);
		}
		catch (NoSuchLayoutSetVersionException nsee) {
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
	protected LayoutSetVersion removeImpl(LayoutSetVersion layoutSetVersion) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutSetVersion)) {
				layoutSetVersion = (LayoutSetVersion)session.get(LayoutSetVersionImpl.class,
						layoutSetVersion.getPrimaryKeyObj());
			}

			if (layoutSetVersion != null) {
				session.delete(layoutSetVersion);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutSetVersion != null) {
			clearCache(layoutSetVersion);
		}

		return layoutSetVersion;
	}

	@Override
	public LayoutSetVersion updateImpl(LayoutSetVersion layoutSetVersion) {
		boolean isNew = layoutSetVersion.isNew();

		if (!(layoutSetVersion instanceof LayoutSetVersionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutSetVersion.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(layoutSetVersion);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutSetVersion proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutSetVersion implementation " +
				layoutSetVersion.getClass());
		}

		LayoutSetVersionModelImpl layoutSetVersionModelImpl = (LayoutSetVersionModelImpl)layoutSetVersion;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutSetVersion.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutSetVersion.setCreateDate(now);
			}
			else {
				layoutSetVersion.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!layoutSetVersionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutSetVersion.setModifiedDate(now);
			}
			else {
				layoutSetVersion.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutSetVersion.isNew()) {
				session.save(layoutSetVersion);

				layoutSetVersion.setNew(false);
			}
			else {
				throw new IllegalArgumentException(
					"LayoutSetVersion is read only, create a new version instead");
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutSetVersionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					layoutSetVersionModelImpl.getLayoutSetId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByLayoutSetId, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByLayoutSetId,
				args);

			args = new Object[] { layoutSetVersionModelImpl.getGroupId() };

			FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByGroupId,
				args);

			args = new Object[] {
					layoutSetVersionModelImpl.getGroupId(),
					layoutSetVersionModelImpl.getVersion()
				};

			FinderCacheUtil.removeResult(_finderPathCountByGroupId_Version, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByGroupId_Version,
				args);

			args = new Object[] {
					layoutSetVersionModelImpl.getLayoutSetPrototypeUuid()
				};

			FinderCacheUtil.removeResult(_finderPathCountByLayoutSetPrototypeUuid,
				args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByLayoutSetPrototypeUuid,
				args);

			args = new Object[] {
					layoutSetVersionModelImpl.getLayoutSetPrototypeUuid(),
					layoutSetVersionModelImpl.getVersion()
				};

			FinderCacheUtil.removeResult(_finderPathCountByLayoutSetPrototypeUuid_Version,
				args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByLayoutSetPrototypeUuid_Version,
				args);

			args = new Object[] {
					layoutSetVersionModelImpl.getGroupId(),
					layoutSetVersionModelImpl.isPrivateLayout()
				};

			FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P,
				args);

			args = new Object[] {
					layoutSetVersionModelImpl.isPrivateLayout(),
					layoutSetVersionModelImpl.getLogoId()
				};

			FinderCacheUtil.removeResult(_finderPathCountByP_L, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByP_L,
				args);

			args = new Object[] {
					layoutSetVersionModelImpl.isPrivateLayout(),
					layoutSetVersionModelImpl.getLogoId(),
					layoutSetVersionModelImpl.getVersion()
				};

			FinderCacheUtil.removeResult(_finderPathCountByP_L_Version, args);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByP_L_Version,
				args);

			FinderCacheUtil.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindAll,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((layoutSetVersionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByLayoutSetId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetVersionModelImpl.getOriginalLayoutSetId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByLayoutSetId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByLayoutSetId,
					args);

				args = new Object[] { layoutSetVersionModelImpl.getLayoutSetId() };

				FinderCacheUtil.removeResult(_finderPathCountByLayoutSetId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByLayoutSetId,
					args);
			}

			if ((layoutSetVersionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByGroupId.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetVersionModelImpl.getOriginalGroupId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);

				args = new Object[] { layoutSetVersionModelImpl.getGroupId() };

				FinderCacheUtil.removeResult(_finderPathCountByGroupId, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByGroupId,
					args);
			}

			if ((layoutSetVersionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByGroupId_Version.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetVersionModelImpl.getOriginalGroupId(),
						layoutSetVersionModelImpl.getOriginalVersion()
					};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId_Version,
					args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByGroupId_Version,
					args);

				args = new Object[] {
						layoutSetVersionModelImpl.getGroupId(),
						layoutSetVersionModelImpl.getVersion()
					};

				FinderCacheUtil.removeResult(_finderPathCountByGroupId_Version,
					args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByGroupId_Version,
					args);
			}

			if ((layoutSetVersionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByLayoutSetPrototypeUuid.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetVersionModelImpl.getOriginalLayoutSetPrototypeUuid()
					};

				FinderCacheUtil.removeResult(_finderPathCountByLayoutSetPrototypeUuid,
					args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByLayoutSetPrototypeUuid,
					args);

				args = new Object[] {
						layoutSetVersionModelImpl.getLayoutSetPrototypeUuid()
					};

				FinderCacheUtil.removeResult(_finderPathCountByLayoutSetPrototypeUuid,
					args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByLayoutSetPrototypeUuid,
					args);
			}

			if ((layoutSetVersionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByLayoutSetPrototypeUuid_Version.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetVersionModelImpl.getOriginalLayoutSetPrototypeUuid(),
						layoutSetVersionModelImpl.getOriginalVersion()
					};

				FinderCacheUtil.removeResult(_finderPathCountByLayoutSetPrototypeUuid_Version,
					args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByLayoutSetPrototypeUuid_Version,
					args);

				args = new Object[] {
						layoutSetVersionModelImpl.getLayoutSetPrototypeUuid(),
						layoutSetVersionModelImpl.getVersion()
					};

				FinderCacheUtil.removeResult(_finderPathCountByLayoutSetPrototypeUuid_Version,
					args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByLayoutSetPrototypeUuid_Version,
					args);
			}

			if ((layoutSetVersionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByG_P.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetVersionModelImpl.getOriginalGroupId(),
						layoutSetVersionModelImpl.getOriginalPrivateLayout()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P,
					args);

				args = new Object[] {
						layoutSetVersionModelImpl.getGroupId(),
						layoutSetVersionModelImpl.isPrivateLayout()
					};

				FinderCacheUtil.removeResult(_finderPathCountByG_P, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByG_P,
					args);
			}

			if ((layoutSetVersionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByP_L.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetVersionModelImpl.getOriginalPrivateLayout(),
						layoutSetVersionModelImpl.getOriginalLogoId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByP_L, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByP_L,
					args);

				args = new Object[] {
						layoutSetVersionModelImpl.isPrivateLayout(),
						layoutSetVersionModelImpl.getLogoId()
					};

				FinderCacheUtil.removeResult(_finderPathCountByP_L, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByP_L,
					args);
			}

			if ((layoutSetVersionModelImpl.getColumnBitmask() &
					_finderPathWithoutPaginationFindByP_L_Version.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						layoutSetVersionModelImpl.getOriginalPrivateLayout(),
						layoutSetVersionModelImpl.getOriginalLogoId(),
						layoutSetVersionModelImpl.getOriginalVersion()
					};

				FinderCacheUtil.removeResult(_finderPathCountByP_L_Version, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByP_L_Version,
					args);

				args = new Object[] {
						layoutSetVersionModelImpl.isPrivateLayout(),
						layoutSetVersionModelImpl.getLogoId(),
						layoutSetVersionModelImpl.getVersion()
					};

				FinderCacheUtil.removeResult(_finderPathCountByP_L_Version, args);
				FinderCacheUtil.removeResult(_finderPathWithoutPaginationFindByP_L_Version,
					args);
			}
		}

		EntityCacheUtil.putResult(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutSetVersionImpl.class, layoutSetVersion.getPrimaryKey(),
			layoutSetVersion, false);

		clearUniqueFindersCache(layoutSetVersionModelImpl, false);
		cacheUniqueFindersCache(layoutSetVersionModelImpl);

		layoutSetVersion.resetOriginalValues();

		return layoutSetVersion;
	}

	/**
	 * Returns the layout set version with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout set version
	 * @return the layout set version
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLayoutSetVersionException {
		LayoutSetVersion layoutSetVersion = fetchByPrimaryKey(primaryKey);

		if (layoutSetVersion == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLayoutSetVersionException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return layoutSetVersion;
	}

	/**
	 * Returns the layout set version with the primary key or throws a <code>NoSuchLayoutSetVersionException</code> if it could not be found.
	 *
	 * @param layoutSetVersionId the primary key of the layout set version
	 * @return the layout set version
	 * @throws NoSuchLayoutSetVersionException if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion findByPrimaryKey(long layoutSetVersionId)
		throws NoSuchLayoutSetVersionException {
		return findByPrimaryKey((Serializable)layoutSetVersionId);
	}

	/**
	 * Returns the layout set version with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutSetVersionId the primary key of the layout set version
	 * @return the layout set version, or <code>null</code> if a layout set version with the primary key could not be found
	 */
	@Override
	public LayoutSetVersion fetchByPrimaryKey(long layoutSetVersionId) {
		return fetchByPrimaryKey((Serializable)layoutSetVersionId);
	}

	/**
	 * Returns all the layout set versions.
	 *
	 * @return the layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout set versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @return the range of layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout set versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findAll(int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout set versions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not <code>QueryUtil#ALL_POS</code>), then the query will include the default ORDER BY logic from <code>LayoutSetVersionModelImpl</code>. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout set versions
	 * @param end the upper bound of the range of layout set versions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of layout set versions
	 */
	@Override
	public List<LayoutSetVersion> findAll(int start, int end,
		OrderByComparator<LayoutSetVersion> orderByComparator,
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

		List<LayoutSetVersion> list = null;

		if (retrieveFromCache) {
			list = (List<LayoutSetVersion>)FinderCacheUtil.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTSETVERSION);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTSETVERSION;

				if (pagination) {
					sql = sql.concat(LayoutSetVersionModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<LayoutSetVersion>)QueryUtil.list(q,
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
	 * Removes all the layout set versions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutSetVersion layoutSetVersion : findAll()) {
			remove(layoutSetVersion);
		}
	}

	/**
	 * Returns the number of layout set versions.
	 *
	 * @return the number of layout set versions
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(_finderPathCountAll,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTSETVERSION);

				count = (Long)q.uniqueResult();

				FinderCacheUtil.putResult(_finderPathCountAll,
					FINDER_ARGS_EMPTY, count);
			}
			catch (Exception e) {
				FinderCacheUtil.removeResult(_finderPathCountAll,
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
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "layoutSetVersionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTSETVERSION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutSetVersionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout set version persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
				new String[0]);

		_finderPathCountAll = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
				new String[0]);

		_finderPathWithPaginationFindByLayoutSetId = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLayoutSetId",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByLayoutSetId = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByLayoutSetId",
				new String[] { Long.class.getName() },
				LayoutSetVersionModelImpl.LAYOUTSETID_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLayoutSetId = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByLayoutSetId", new String[] { Long.class.getName() });

		_finderPathFetchByLayoutSetId_Version = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
				"fetchByLayoutSetId_Version",
				new String[] { Long.class.getName(), Integer.class.getName() },
				LayoutSetVersionModelImpl.LAYOUTSETID_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLayoutSetId_Version = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByLayoutSetId_Version",
				new String[] { Long.class.getName(), Integer.class.getName() });

		_finderPathWithPaginationFindByGroupId = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
				new String[] {
					Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByGroupId = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
				new String[] { Long.class.getName() },
				LayoutSetVersionModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByGroupId = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
				new String[] { Long.class.getName() });

		_finderPathWithPaginationFindByGroupId_Version = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByGroupId_Version",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByGroupId_Version = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByGroupId_Version",
				new String[] { Long.class.getName(), Integer.class.getName() },
				LayoutSetVersionModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByGroupId_Version = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByGroupId_Version",
				new String[] { Long.class.getName(), Integer.class.getName() });

		_finderPathWithPaginationFindByLayoutSetPrototypeUuid = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByLayoutSetPrototypeUuid",
				new String[] {
					String.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByLayoutSetPrototypeUuid = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByLayoutSetPrototypeUuid",
				new String[] { String.class.getName() },
				LayoutSetVersionModelImpl.LAYOUTSETPROTOTYPEUUID_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLayoutSetPrototypeUuid = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByLayoutSetPrototypeUuid",
				new String[] { String.class.getName() });

		_finderPathWithPaginationFindByLayoutSetPrototypeUuid_Version = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByLayoutSetPrototypeUuid_Version",
				new String[] {
					String.class.getName(), Integer.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByLayoutSetPrototypeUuid_Version = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByLayoutSetPrototypeUuid_Version",
				new String[] { String.class.getName(), Integer.class.getName() },
				LayoutSetVersionModelImpl.LAYOUTSETPROTOTYPEUUID_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByLayoutSetPrototypeUuid_Version = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByLayoutSetPrototypeUuid_Version",
				new String[] { String.class.getName(), Integer.class.getName() });

		_finderPathWithPaginationFindByG_P = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByG_P",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByG_P = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_P",
				new String[] { Long.class.getName(), Boolean.class.getName() },
				LayoutSetVersionModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_P",
				new String[] { Long.class.getName(), Boolean.class.getName() });

		_finderPathFetchByG_P_Version = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class, FINDER_CLASS_NAME_ENTITY,
				"fetchByG_P_Version",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Integer.class.getName()
				},
				LayoutSetVersionModelImpl.GROUPID_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByG_P_Version = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByG_P_Version",
				new String[] {
					Long.class.getName(), Boolean.class.getName(),
					Integer.class.getName()
				});

		_finderPathWithPaginationFindByP_L = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_L",
				new String[] {
					Boolean.class.getName(), Long.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByP_L = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_L",
				new String[] { Boolean.class.getName(), Long.class.getName() },
				LayoutSetVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.LOGOID_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByP_L = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByP_L",
				new String[] { Boolean.class.getName(), Long.class.getName() });

		_finderPathWithPaginationFindByP_L_Version = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByP_L_Version",
				new String[] {
					Boolean.class.getName(), Long.class.getName(),
					Integer.class.getName(),
					
				Integer.class.getName(), Integer.class.getName(),
					OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByP_L_Version = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED,
				LayoutSetVersionImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByP_L_Version",
				new String[] {
					Boolean.class.getName(), Long.class.getName(),
					Integer.class.getName()
				},
				LayoutSetVersionModelImpl.PRIVATELAYOUT_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.LOGOID_COLUMN_BITMASK |
				LayoutSetVersionModelImpl.VERSION_COLUMN_BITMASK);

		_finderPathCountByP_L_Version = new FinderPath(LayoutSetVersionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutSetVersionModelImpl.FINDER_CACHE_ENABLED, Long.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"countByP_L_Version",
				new String[] {
					Boolean.class.getName(), Long.class.getName(),
					Integer.class.getName()
				});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(LayoutSetVersionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@BeanReference(type = CompanyProviderWrapper.class)
	protected CompanyProvider companyProvider;
	private static final String _SQL_SELECT_LAYOUTSETVERSION = "SELECT layoutSetVersion FROM LayoutSetVersion layoutSetVersion";
	private static final String _SQL_SELECT_LAYOUTSETVERSION_WHERE = "SELECT layoutSetVersion FROM LayoutSetVersion layoutSetVersion WHERE ";
	private static final String _SQL_COUNT_LAYOUTSETVERSION = "SELECT COUNT(layoutSetVersion) FROM LayoutSetVersion layoutSetVersion";
	private static final String _SQL_COUNT_LAYOUTSETVERSION_WHERE = "SELECT COUNT(layoutSetVersion) FROM LayoutSetVersion layoutSetVersion WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutSetVersion.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No LayoutSetVersion exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No LayoutSetVersion exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(LayoutSetVersionPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"settings"
			});
}