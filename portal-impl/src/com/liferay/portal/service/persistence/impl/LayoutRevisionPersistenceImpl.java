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
import com.liferay.portal.kernel.exception.NoSuchLayoutRevisionException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.LayoutRevision;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.LayoutRevisionPersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.impl.LayoutRevisionImpl;
import com.liferay.portal.model.impl.LayoutRevisionModelImpl;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the layout revision service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
public class LayoutRevisionPersistenceImpl
	extends BasePersistenceImpl<LayoutRevision>
	implements LayoutRevisionPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>LayoutRevisionUtil</code> to access the layout revision persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		LayoutRevisionImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByLayoutSetBranchId;
	private FinderPath _finderPathWithoutPaginationFindByLayoutSetBranchId;
	private FinderPath _finderPathCountByLayoutSetBranchId;

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @return the matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId) {

		return findByLayoutSetBranchId(
			layoutSetBranchId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end) {

		return findByLayoutSetBranchId(layoutSetBranchId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return findByLayoutSetBranchId(
			layoutSetBranchId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByLayoutSetBranchId(
		long layoutSetBranchId, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByLayoutSetBranchId;
				finderArgs = new Object[] {layoutSetBranchId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByLayoutSetBranchId;
			finderArgs = new Object[] {
				layoutSetBranchId, start, end, orderByComparator
			};
		}

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutRevision layoutRevision : list) {
					if (layoutSetBranchId !=
							layoutRevision.getLayoutSetBranchId()) {

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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByLayoutSetBranchId_First(
			long layoutSetBranchId,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByLayoutSetBranchId_First(
			layoutSetBranchId, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByLayoutSetBranchId_First(
		long layoutSetBranchId,
		OrderByComparator<LayoutRevision> orderByComparator) {

		List<LayoutRevision> list = findByLayoutSetBranchId(
			layoutSetBranchId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByLayoutSetBranchId_Last(
			long layoutSetBranchId,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByLayoutSetBranchId_Last(
			layoutSetBranchId, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByLayoutSetBranchId_Last(
		long layoutSetBranchId,
		OrderByComparator<LayoutRevision> orderByComparator) {

		int count = countByLayoutSetBranchId(layoutSetBranchId);

		if (count == 0) {
			return null;
		}

		List<LayoutRevision> list = findByLayoutSetBranchId(
			layoutSetBranchId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision[] findByLayoutSetBranchId_PrevAndNext(
			long layoutRevisionId, long layoutSetBranchId,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByLayoutSetBranchId_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, orderByComparator,
				true);

			array[1] = layoutRevision;

			array[2] = getByLayoutSetBranchId_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, orderByComparator,
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

	protected LayoutRevision getByLayoutSetBranchId_PrevAndNext(
		Session session, LayoutRevision layoutRevision, long layoutSetBranchId,
		OrderByComparator<LayoutRevision> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutRevision)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 */
	@Override
	public void removeByLayoutSetBranchId(long layoutSetBranchId) {
		for (LayoutRevision layoutRevision :
				findByLayoutSetBranchId(
					layoutSetBranchId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByLayoutSetBranchId(long layoutSetBranchId) {
		FinderPath finderPath = _finderPathCountByLayoutSetBranchId;

		Object[] finderArgs = new Object[] {layoutSetBranchId};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

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
		_FINDER_COLUMN_LAYOUTSETBRANCHID_LAYOUTSETBRANCHID_2 =
			"layoutRevision.layoutSetBranchId = ?";

	private FinderPath _finderPathWithPaginationFindByPlid;
	private FinderPath _finderPathWithoutPaginationFindByPlid;
	private FinderPath _finderPathCountByPlid;

	/**
	 * Returns all the layout revisions where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByPlid(long plid) {
		return findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByPlid(long plid, int start, int end) {
		return findByPlid(plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return findByPlid(plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout revisions where plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByPlid(
		long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByPlid;
				finderArgs = new Object[] {plid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByPlid;
			finderArgs = new Object[] {plid, start, end, orderByComparator};
		}

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutRevision layoutRevision : list) {
					if (plid != layoutRevision.getPlid()) {
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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_PLID_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first layout revision in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByPlid_First(
			long plid, OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByPlid_First(
			plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByPlid_First(
		long plid, OrderByComparator<LayoutRevision> orderByComparator) {

		List<LayoutRevision> list = findByPlid(plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByPlid_Last(
			long plid, OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByPlid_Last(
			plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where plid = &#63;.
	 *
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByPlid_Last(
		long plid, OrderByComparator<LayoutRevision> orderByComparator) {

		int count = countByPlid(plid);

		if (count == 0) {
			return null;
		}

		List<LayoutRevision> list = findByPlid(
			plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where plid = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision[] findByPlid_PrevAndNext(
			long layoutRevisionId, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByPlid_PrevAndNext(
				session, layoutRevision, plid, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByPlid_PrevAndNext(
				session, layoutRevision, plid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByPlid_PrevAndNext(
		Session session, LayoutRevision layoutRevision, long plid,
		OrderByComparator<LayoutRevision> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
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
						layoutRevision)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout revisions where plid = &#63; from the database.
	 *
	 * @param plid the plid
	 */
	@Override
	public void removeByPlid(long plid) {
		for (LayoutRevision layoutRevision :
				findByPlid(plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where plid = &#63;.
	 *
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByPlid(long plid) {
		FinderPath finderPath = _finderPathCountByPlid;

		Object[] finderArgs = new Object[] {plid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

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
		"layoutRevision.plid = ?";

	private FinderPath _finderPathWithPaginationFindByStatus;
	private FinderPath _finderPathWithoutPaginationFindByStatus;
	private FinderPath _finderPathCountByStatus;

	/**
	 * Returns all the layout revisions where status = &#63;.
	 *
	 * @param status the status
	 * @return the matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByStatus(int status) {
		return findByStatus(status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByStatus(int status, int start, int end) {
		return findByStatus(status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByStatus(
		int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return findByStatus(status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout revisions where status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByStatus(
		int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByStatus;
				finderArgs = new Object[] {status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByStatus;
			finderArgs = new Object[] {status, start, end, orderByComparator};
		}

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutRevision layoutRevision : list) {
					if (status != layoutRevision.getStatus()) {
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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_STATUS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(status);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first layout revision in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByStatus_First(
			int status, OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByStatus_First(
			status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByStatus_First(
		int status, OrderByComparator<LayoutRevision> orderByComparator) {

		List<LayoutRevision> list = findByStatus(
			status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByStatus_Last(
			int status, OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByStatus_Last(
			status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where status = &#63;.
	 *
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByStatus_Last(
		int status, OrderByComparator<LayoutRevision> orderByComparator) {

		int count = countByStatus(status);

		if (count == 0) {
			return null;
		}

		List<LayoutRevision> list = findByStatus(
			status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where status = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision[] findByStatus_PrevAndNext(
			long layoutRevisionId, int status,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByStatus_PrevAndNext(
				session, layoutRevision, status, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByStatus_PrevAndNext(
				session, layoutRevision, status, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByStatus_PrevAndNext(
		Session session, LayoutRevision layoutRevision, int status,
		OrderByComparator<LayoutRevision> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_STATUS_STATUS_2);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutRevision)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout revisions where status = &#63; from the database.
	 *
	 * @param status the status
	 */
	@Override
	public void removeByStatus(int status) {
		for (LayoutRevision layoutRevision :
				findByStatus(
					status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where status = &#63;.
	 *
	 * @param status the status
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByStatus(int status) {
		FinderPath finderPath = _finderPathCountByStatus;

		Object[] finderArgs = new Object[] {status};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_STATUS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_STATUS_STATUS_2 =
		"layoutRevision.status = ?";

	private FinderPath _finderPathWithPaginationFindByL_H;
	private FinderPath _finderPathWithoutPaginationFindByL_H;
	private FinderPath _finderPathCountByL_H;

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @return the matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_H(
		long layoutSetBranchId, boolean head) {

		return findByL_H(
			layoutSetBranchId, head, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_H(
		long layoutSetBranchId, boolean head, int start, int end) {

		return findByL_H(layoutSetBranchId, head, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_H(
		long layoutSetBranchId, boolean head, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return findByL_H(
			layoutSetBranchId, head, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_H(
		long layoutSetBranchId, boolean head, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByL_H;
				finderArgs = new Object[] {layoutSetBranchId, head};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByL_H;
			finderArgs = new Object[] {
				layoutSetBranchId, head, start, end, orderByComparator
			};
		}

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutRevision layoutRevision : list) {
					if ((layoutSetBranchId !=
							layoutRevision.getLayoutSetBranchId()) ||
						(head != layoutRevision.isHead())) {

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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_H_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_H_HEAD_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(head);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_H_First(
			long layoutSetBranchId, boolean head,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_H_First(
			layoutSetBranchId, head, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", head=");
		msg.append(head);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_H_First(
		long layoutSetBranchId, boolean head,
		OrderByComparator<LayoutRevision> orderByComparator) {

		List<LayoutRevision> list = findByL_H(
			layoutSetBranchId, head, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_H_Last(
			long layoutSetBranchId, boolean head,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_H_Last(
			layoutSetBranchId, head, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", head=");
		msg.append(head);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_H_Last(
		long layoutSetBranchId, boolean head,
		OrderByComparator<LayoutRevision> orderByComparator) {

		int count = countByL_H(layoutSetBranchId, head);

		if (count == 0) {
			return null;
		}

		List<LayoutRevision> list = findByL_H(
			layoutSetBranchId, head, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision[] findByL_H_PrevAndNext(
			long layoutRevisionId, long layoutSetBranchId, boolean head,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_H_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, head,
				orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByL_H_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, head,
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

	protected LayoutRevision getByL_H_PrevAndNext(
		Session session, LayoutRevision layoutRevision, long layoutSetBranchId,
		boolean head, OrderByComparator<LayoutRevision> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_H_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_H_HEAD_2);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(head);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutRevision)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and head = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 */
	@Override
	public void removeByL_H(long layoutSetBranchId, boolean head) {
		for (LayoutRevision layoutRevision :
				findByL_H(
					layoutSetBranchId, head, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByL_H(long layoutSetBranchId, boolean head) {
		FinderPath finderPath = _finderPathCountByL_H;

		Object[] finderArgs = new Object[] {layoutSetBranchId, head};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_H_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_H_HEAD_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(head);

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

	private static final String _FINDER_COLUMN_L_H_LAYOUTSETBRANCHID_2 =
		"layoutRevision.layoutSetBranchId = ? AND ";

	private static final String _FINDER_COLUMN_L_H_HEAD_2 =
		"layoutRevision.head = ?";

	private FinderPath _finderPathWithPaginationFindByL_P;
	private FinderPath _finderPathWithoutPaginationFindByL_P;
	private FinderPath _finderPathCountByL_P;

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_P(long layoutSetBranchId, long plid) {
		return findByL_P(
			layoutSetBranchId, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_P(
		long layoutSetBranchId, long plid, int start, int end) {

		return findByL_P(layoutSetBranchId, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_P(
		long layoutSetBranchId, long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return findByL_P(
			layoutSetBranchId, plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_P(
		long layoutSetBranchId, long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByL_P;
				finderArgs = new Object[] {layoutSetBranchId, plid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByL_P;
			finderArgs = new Object[] {
				layoutSetBranchId, plid, start, end, orderByComparator
			};
		}

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutRevision layoutRevision : list) {
					if ((layoutSetBranchId !=
							layoutRevision.getLayoutSetBranchId()) ||
						(plid != layoutRevision.getPlid())) {

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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_P_First(
			long layoutSetBranchId, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_P_First(
			layoutSetBranchId, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_P_First(
		long layoutSetBranchId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {

		List<LayoutRevision> list = findByL_P(
			layoutSetBranchId, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_P_Last(
			long layoutSetBranchId, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_P_Last(
			layoutSetBranchId, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_P_Last(
		long layoutSetBranchId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {

		int count = countByL_P(layoutSetBranchId, plid);

		if (count == 0) {
			return null;
		}

		List<LayoutRevision> list = findByL_P(
			layoutSetBranchId, plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision[] findByL_P_PrevAndNext(
			long layoutRevisionId, long layoutSetBranchId, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_P_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, plid,
				orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByL_P_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, plid,
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

	protected LayoutRevision getByL_P_PrevAndNext(
		Session session, LayoutRevision layoutRevision, long layoutSetBranchId,
		long plid, OrderByComparator<LayoutRevision> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_P_PLID_2);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutRevision)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 */
	@Override
	public void removeByL_P(long layoutSetBranchId, long plid) {
		for (LayoutRevision layoutRevision :
				findByL_P(
					layoutSetBranchId, plid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByL_P(long layoutSetBranchId, long plid) {
		FinderPath finderPath = _finderPathCountByL_P;

		Object[] finderArgs = new Object[] {layoutSetBranchId, plid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

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

	private static final String _FINDER_COLUMN_L_P_LAYOUTSETBRANCHID_2 =
		"layoutRevision.layoutSetBranchId = ? AND ";

	private static final String _FINDER_COLUMN_L_P_PLID_2 =
		"layoutRevision.plid = ?";

	private FinderPath _finderPathWithPaginationFindByL_S;
	private FinderPath _finderPathWithoutPaginationFindByL_S;
	private FinderPath _finderPathCountByL_S;

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @return the matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_S(long layoutSetBranchId, int status) {
		return findByL_S(
			layoutSetBranchId, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_S(
		long layoutSetBranchId, int status, int start, int end) {

		return findByL_S(layoutSetBranchId, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_S(
		long layoutSetBranchId, int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return findByL_S(
			layoutSetBranchId, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_S(
		long layoutSetBranchId, int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByL_S;
				finderArgs = new Object[] {layoutSetBranchId, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByL_S;
			finderArgs = new Object[] {
				layoutSetBranchId, status, start, end, orderByComparator
			};
		}

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutRevision layoutRevision : list) {
					if ((layoutSetBranchId !=
							layoutRevision.getLayoutSetBranchId()) ||
						(status != layoutRevision.getStatus())) {

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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_S_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(status);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_S_First(
			long layoutSetBranchId, int status,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_S_First(
			layoutSetBranchId, status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_S_First(
		long layoutSetBranchId, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {

		List<LayoutRevision> list = findByL_S(
			layoutSetBranchId, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_S_Last(
			long layoutSetBranchId, int status,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_S_Last(
			layoutSetBranchId, status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_S_Last(
		long layoutSetBranchId, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {

		int count = countByL_S(layoutSetBranchId, status);

		if (count == 0) {
			return null;
		}

		List<LayoutRevision> list = findByL_S(
			layoutSetBranchId, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision[] findByL_S_PrevAndNext(
			long layoutRevisionId, long layoutSetBranchId, int status,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_S_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, status,
				orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByL_S_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, status,
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

	protected LayoutRevision getByL_S_PrevAndNext(
		Session session, LayoutRevision layoutRevision, long layoutSetBranchId,
		int status, OrderByComparator<LayoutRevision> orderByComparator,
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

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_S_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_S_STATUS_2);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutRevision)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and status = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 */
	@Override
	public void removeByL_S(long layoutSetBranchId, int status) {
		for (LayoutRevision layoutRevision :
				findByL_S(
					layoutSetBranchId, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param status the status
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByL_S(long layoutSetBranchId, int status) {
		FinderPath finderPath = _finderPathCountByL_S;

		Object[] finderArgs = new Object[] {layoutSetBranchId, status};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_S_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_L_S_LAYOUTSETBRANCHID_2 =
		"layoutRevision.layoutSetBranchId = ? AND ";

	private static final String _FINDER_COLUMN_L_S_STATUS_2 =
		"layoutRevision.status = ?";

	private FinderPath _finderPathWithPaginationFindByH_P;
	private FinderPath _finderPathWithoutPaginationFindByH_P;
	private FinderPath _finderPathCountByH_P;

	/**
	 * Returns all the layout revisions where head = &#63; and plid = &#63;.
	 *
	 * @param head the head
	 * @param plid the plid
	 * @return the matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByH_P(boolean head, long plid) {
		return findByH_P(
			head, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where head = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param head the head
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByH_P(
		boolean head, long plid, int start, int end) {

		return findByH_P(head, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where head = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param head the head
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByH_P(
		boolean head, long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return findByH_P(head, plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout revisions where head = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param head the head
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByH_P(
		boolean head, long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByH_P;
				finderArgs = new Object[] {head, plid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByH_P;
			finderArgs = new Object[] {
				head, plid, start, end, orderByComparator
			};
		}

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutRevision layoutRevision : list) {
					if ((head != layoutRevision.isHead()) ||
						(plid != layoutRevision.getPlid())) {

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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_H_P_HEAD_2);

			query.append(_FINDER_COLUMN_H_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(head);

				qPos.add(plid);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first layout revision in the ordered set where head = &#63; and plid = &#63;.
	 *
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByH_P_First(
			boolean head, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByH_P_First(
			head, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("head=");
		msg.append(head);

		msg.append(", plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where head = &#63; and plid = &#63;.
	 *
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByH_P_First(
		boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {

		List<LayoutRevision> list = findByH_P(
			head, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where head = &#63; and plid = &#63;.
	 *
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByH_P_Last(
			boolean head, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByH_P_Last(
			head, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("head=");
		msg.append(head);

		msg.append(", plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where head = &#63; and plid = &#63;.
	 *
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByH_P_Last(
		boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {

		int count = countByH_P(head, plid);

		if (count == 0) {
			return null;
		}

		List<LayoutRevision> list = findByH_P(
			head, plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where head = &#63; and plid = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision[] findByH_P_PrevAndNext(
			long layoutRevisionId, boolean head, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByH_P_PrevAndNext(
				session, layoutRevision, head, plid, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByH_P_PrevAndNext(
				session, layoutRevision, head, plid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByH_P_PrevAndNext(
		Session session, LayoutRevision layoutRevision, boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_H_P_HEAD_2);

		query.append(_FINDER_COLUMN_H_P_PLID_2);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(head);

		qPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutRevision)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout revisions where head = &#63; and plid = &#63; from the database.
	 *
	 * @param head the head
	 * @param plid the plid
	 */
	@Override
	public void removeByH_P(boolean head, long plid) {
		for (LayoutRevision layoutRevision :
				findByH_P(
					head, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where head = &#63; and plid = &#63;.
	 *
	 * @param head the head
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByH_P(boolean head, long plid) {
		FinderPath finderPath = _finderPathCountByH_P;

		Object[] finderArgs = new Object[] {head, plid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_H_P_HEAD_2);

			query.append(_FINDER_COLUMN_H_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(head);

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

	private static final String _FINDER_COLUMN_H_P_HEAD_2 =
		"layoutRevision.head = ? AND ";

	private static final String _FINDER_COLUMN_H_P_PLID_2 =
		"layoutRevision.plid = ?";

	private FinderPath _finderPathWithPaginationFindByP_NotS;
	private FinderPath _finderPathWithPaginationCountByP_NotS;

	/**
	 * Returns all the layout revisions where plid = &#63; and status &ne; &#63;.
	 *
	 * @param plid the plid
	 * @param status the status
	 * @return the matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByP_NotS(long plid, int status) {
		return findByP_NotS(
			plid, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where plid = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByP_NotS(
		long plid, int status, int start, int end) {

		return findByP_NotS(plid, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where plid = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByP_NotS(
		long plid, int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return findByP_NotS(plid, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout revisions where plid = &#63; and status &ne; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param plid the plid
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByP_NotS(
		long plid, int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByP_NotS;
		finderArgs = new Object[] {plid, status, start, end, orderByComparator};

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutRevision layoutRevision : list) {
					if ((plid != layoutRevision.getPlid()) ||
						(status == layoutRevision.getStatus())) {

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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_P_NOTS_PLID_2);

			query.append(_FINDER_COLUMN_P_NOTS_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				qPos.add(status);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	 *
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByP_NotS_First(
			long plid, int status,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByP_NotS_First(
			plid, status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(", status!=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	 *
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByP_NotS_First(
		long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {

		List<LayoutRevision> list = findByP_NotS(
			plid, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	 *
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByP_NotS_Last(
			long plid, int status,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByP_NotS_Last(
			plid, status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("plid=");
		msg.append(plid);

		msg.append(", status!=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	 *
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByP_NotS_Last(
		long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {

		int count = countByP_NotS(plid, status);

		if (count == 0) {
			return null;
		}

		List<LayoutRevision> list = findByP_NotS(
			plid, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where plid = &#63; and status &ne; &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision[] findByP_NotS_PrevAndNext(
			long layoutRevisionId, long plid, int status,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByP_NotS_PrevAndNext(
				session, layoutRevision, plid, status, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByP_NotS_PrevAndNext(
				session, layoutRevision, plid, status, orderByComparator,
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

	protected LayoutRevision getByP_NotS_PrevAndNext(
		Session session, LayoutRevision layoutRevision, long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_P_NOTS_PLID_2);

		query.append(_FINDER_COLUMN_P_NOTS_STATUS_2);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(plid);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutRevision)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout revisions where plid = &#63; and status &ne; &#63; from the database.
	 *
	 * @param plid the plid
	 * @param status the status
	 */
	@Override
	public void removeByP_NotS(long plid, int status) {
		for (LayoutRevision layoutRevision :
				findByP_NotS(
					plid, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where plid = &#63; and status &ne; &#63;.
	 *
	 * @param plid the plid
	 * @param status the status
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByP_NotS(long plid, int status) {
		FinderPath finderPath = _finderPathWithPaginationCountByP_NotS;

		Object[] finderArgs = new Object[] {plid, status};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_P_NOTS_PLID_2);

			query.append(_FINDER_COLUMN_P_NOTS_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(plid);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_P_NOTS_PLID_2 =
		"layoutRevision.plid = ? AND ";

	private static final String _FINDER_COLUMN_P_NOTS_STATUS_2 =
		"layoutRevision.status != ?";

	private FinderPath _finderPathWithPaginationFindByL_L_P;
	private FinderPath _finderPathWithoutPaginationFindByL_L_P;
	private FinderPath _finderPathCountByL_L_P;

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @return the matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_L_P(
		long layoutSetBranchId, long layoutBranchId, long plid) {

		return findByL_L_P(
			layoutSetBranchId, layoutBranchId, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_L_P(
		long layoutSetBranchId, long layoutBranchId, long plid, int start,
		int end) {

		return findByL_L_P(
			layoutSetBranchId, layoutBranchId, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_L_P(
		long layoutSetBranchId, long layoutBranchId, long plid, int start,
		int end, OrderByComparator<LayoutRevision> orderByComparator) {

		return findByL_L_P(
			layoutSetBranchId, layoutBranchId, plid, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_L_P(
		long layoutSetBranchId, long layoutBranchId, long plid, int start,
		int end, OrderByComparator<LayoutRevision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByL_L_P;
				finderArgs = new Object[] {
					layoutSetBranchId, layoutBranchId, plid
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByL_L_P;
			finderArgs = new Object[] {
				layoutSetBranchId, layoutBranchId, plid, start, end,
				orderByComparator
			};
		}

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutRevision layoutRevision : list) {
					if ((layoutSetBranchId !=
							layoutRevision.getLayoutSetBranchId()) ||
						(layoutBranchId !=
							layoutRevision.getLayoutBranchId()) ||
						(plid != layoutRevision.getPlid())) {

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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_L_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_L_P_LAYOUTBRANCHID_2);

			query.append(_FINDER_COLUMN_L_L_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(layoutBranchId);

				qPos.add(plid);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_L_P_First(
			long layoutSetBranchId, long layoutBranchId, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_L_P_First(
			layoutSetBranchId, layoutBranchId, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", layoutBranchId=");
		msg.append(layoutBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_L_P_First(
		long layoutSetBranchId, long layoutBranchId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {

		List<LayoutRevision> list = findByL_L_P(
			layoutSetBranchId, layoutBranchId, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_L_P_Last(
			long layoutSetBranchId, long layoutBranchId, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_L_P_Last(
			layoutSetBranchId, layoutBranchId, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", layoutBranchId=");
		msg.append(layoutBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_L_P_Last(
		long layoutSetBranchId, long layoutBranchId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {

		int count = countByL_L_P(layoutSetBranchId, layoutBranchId, plid);

		if (count == 0) {
			return null;
		}

		List<LayoutRevision> list = findByL_L_P(
			layoutSetBranchId, layoutBranchId, plid, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision[] findByL_L_P_PrevAndNext(
			long layoutRevisionId, long layoutSetBranchId, long layoutBranchId,
			long plid, OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_L_P_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, layoutBranchId,
				plid, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByL_L_P_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, layoutBranchId,
				plid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByL_L_P_PrevAndNext(
		Session session, LayoutRevision layoutRevision, long layoutSetBranchId,
		long layoutBranchId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_L_P_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_L_P_LAYOUTBRANCHID_2);

		query.append(_FINDER_COLUMN_L_L_P_PLID_2);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(layoutBranchId);

		qPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutRevision)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 */
	@Override
	public void removeByL_L_P(
		long layoutSetBranchId, long layoutBranchId, long plid) {

		for (LayoutRevision layoutRevision :
				findByL_L_P(
					layoutSetBranchId, layoutBranchId, plid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByL_L_P(
		long layoutSetBranchId, long layoutBranchId, long plid) {

		FinderPath finderPath = _finderPathCountByL_L_P;

		Object[] finderArgs = new Object[] {
			layoutSetBranchId, layoutBranchId, plid
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_L_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_L_P_LAYOUTBRANCHID_2);

			query.append(_FINDER_COLUMN_L_L_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(layoutBranchId);

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

	private static final String _FINDER_COLUMN_L_L_P_LAYOUTSETBRANCHID_2 =
		"layoutRevision.layoutSetBranchId = ? AND ";

	private static final String _FINDER_COLUMN_L_L_P_LAYOUTBRANCHID_2 =
		"layoutRevision.layoutBranchId = ? AND ";

	private static final String _FINDER_COLUMN_L_L_P_PLID_2 =
		"layoutRevision.plid = ? AND layoutRevision.status != 5";

	private FinderPath _finderPathWithPaginationFindByL_P_P;
	private FinderPath _finderPathWithoutPaginationFindByL_P_P;
	private FinderPath _finderPathCountByL_P_P;

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @return the matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_P_P(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid) {

		return findByL_P_P(
			layoutSetBranchId, parentLayoutRevisionId, plid, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_P_P(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		int start, int end) {

		return findByL_P_P(
			layoutSetBranchId, parentLayoutRevisionId, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_P_P(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return findByL_P_P(
			layoutSetBranchId, parentLayoutRevisionId, plid, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_P_P(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		int start, int end, OrderByComparator<LayoutRevision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByL_P_P;
				finderArgs = new Object[] {
					layoutSetBranchId, parentLayoutRevisionId, plid
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByL_P_P;
			finderArgs = new Object[] {
				layoutSetBranchId, parentLayoutRevisionId, plid, start, end,
				orderByComparator
			};
		}

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutRevision layoutRevision : list) {
					if ((layoutSetBranchId !=
							layoutRevision.getLayoutSetBranchId()) ||
						(parentLayoutRevisionId !=
							layoutRevision.getParentLayoutRevisionId()) ||
						(plid != layoutRevision.getPlid())) {

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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_P_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_P_PARENTLAYOUTREVISIONID_2);

			query.append(_FINDER_COLUMN_L_P_P_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(parentLayoutRevisionId);

				qPos.add(plid);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_P_P_First(
			long layoutSetBranchId, long parentLayoutRevisionId, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_P_P_First(
			layoutSetBranchId, parentLayoutRevisionId, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", parentLayoutRevisionId=");
		msg.append(parentLayoutRevisionId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_P_P_First(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {

		List<LayoutRevision> list = findByL_P_P(
			layoutSetBranchId, parentLayoutRevisionId, plid, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_P_P_Last(
			long layoutSetBranchId, long parentLayoutRevisionId, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_P_P_Last(
			layoutSetBranchId, parentLayoutRevisionId, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", parentLayoutRevisionId=");
		msg.append(parentLayoutRevisionId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_P_P_Last(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {

		int count = countByL_P_P(
			layoutSetBranchId, parentLayoutRevisionId, plid);

		if (count == 0) {
			return null;
		}

		List<LayoutRevision> list = findByL_P_P(
			layoutSetBranchId, parentLayoutRevisionId, plid, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision[] findByL_P_P_PrevAndNext(
			long layoutRevisionId, long layoutSetBranchId,
			long parentLayoutRevisionId, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_P_P_PrevAndNext(
				session, layoutRevision, layoutSetBranchId,
				parentLayoutRevisionId, plid, orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByL_P_P_PrevAndNext(
				session, layoutRevision, layoutSetBranchId,
				parentLayoutRevisionId, plid, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected LayoutRevision getByL_P_P_PrevAndNext(
		Session session, LayoutRevision layoutRevision, long layoutSetBranchId,
		long parentLayoutRevisionId, long plid,
		OrderByComparator<LayoutRevision> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_P_P_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_P_P_PARENTLAYOUTREVISIONID_2);

		query.append(_FINDER_COLUMN_L_P_P_PLID_2);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(parentLayoutRevisionId);

		qPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutRevision)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 */
	@Override
	public void removeByL_P_P(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid) {

		for (LayoutRevision layoutRevision :
				findByL_P_P(
					layoutSetBranchId, parentLayoutRevisionId, plid,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and parentLayoutRevisionId = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param parentLayoutRevisionId the parent layout revision ID
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByL_P_P(
		long layoutSetBranchId, long parentLayoutRevisionId, long plid) {

		FinderPath finderPath = _finderPathCountByL_P_P;

		Object[] finderArgs = new Object[] {
			layoutSetBranchId, parentLayoutRevisionId, plid
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_P_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_P_PARENTLAYOUTREVISIONID_2);

			query.append(_FINDER_COLUMN_L_P_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(parentLayoutRevisionId);

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

	private static final String _FINDER_COLUMN_L_P_P_LAYOUTSETBRANCHID_2 =
		"layoutRevision.layoutSetBranchId = ? AND ";

	private static final String _FINDER_COLUMN_L_P_P_PARENTLAYOUTREVISIONID_2 =
		"layoutRevision.parentLayoutRevisionId = ? AND ";

	private static final String _FINDER_COLUMN_L_P_P_PLID_2 =
		"layoutRevision.plid = ?";

	private FinderPath _finderPathFetchByL_H_P;
	private FinderPath _finderPathCountByL_H_P;

	/**
	 * Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or throws a <code>NoSuchLayoutRevisionException</code> if it could not be found.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_H_P(
			long layoutSetBranchId, boolean head, long plid)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_H_P(
			layoutSetBranchId, head, plid);

		if (layoutRevision == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("layoutSetBranchId=");
			msg.append(layoutSetBranchId);

			msg.append(", head=");
			msg.append(head);

			msg.append(", plid=");
			msg.append(plid);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLayoutRevisionException(msg.toString());
		}

		return layoutRevision;
	}

	/**
	 * Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_H_P(
		long layoutSetBranchId, boolean head, long plid) {

		return fetchByL_H_P(layoutSetBranchId, head, plid, true);
	}

	/**
	 * Returns the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_H_P(
		long layoutSetBranchId, boolean head, long plid,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {layoutSetBranchId, head, plid};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByL_H_P, finderArgs, this);
		}

		if (result instanceof LayoutRevision) {
			LayoutRevision layoutRevision = (LayoutRevision)result;

			if ((layoutSetBranchId != layoutRevision.getLayoutSetBranchId()) ||
				(head != layoutRevision.isHead()) ||
				(plid != layoutRevision.getPlid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_H_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_H_P_HEAD_2);

			query.append(_FINDER_COLUMN_L_H_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(head);

				qPos.add(plid);

				List<LayoutRevision> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByL_H_P, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									layoutSetBranchId, head, plid
								};
							}

							_log.warn(
								"LayoutRevisionPersistenceImpl.fetchByL_H_P(long, boolean, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					LayoutRevision layoutRevision = list.get(0);

					result = layoutRevision;

					cacheResult(layoutRevision);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByL_H_P, finderArgs);
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
			return (LayoutRevision)result;
		}
	}

	/**
	 * Removes the layout revision where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the layout revision that was removed
	 */
	@Override
	public LayoutRevision removeByL_H_P(
			long layoutSetBranchId, boolean head, long plid)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByL_H_P(
			layoutSetBranchId, head, plid);

		return remove(layoutRevision);
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByL_H_P(long layoutSetBranchId, boolean head, long plid) {
		FinderPath finderPath = _finderPathCountByL_H_P;

		Object[] finderArgs = new Object[] {layoutSetBranchId, head, plid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_H_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_H_P_HEAD_2);

			query.append(_FINDER_COLUMN_L_H_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(head);

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

	private static final String _FINDER_COLUMN_L_H_P_LAYOUTSETBRANCHID_2 =
		"layoutRevision.layoutSetBranchId = ? AND ";

	private static final String _FINDER_COLUMN_L_H_P_HEAD_2 =
		"layoutRevision.head = ? AND ";

	private static final String _FINDER_COLUMN_L_H_P_PLID_2 =
		"layoutRevision.plid = ?";

	private FinderPath _finderPathWithPaginationFindByL_H_P_Collection;
	private FinderPath _finderPathWithoutPaginationFindByL_H_P_Collection;
	private FinderPath _finderPathCountByL_H_P_Collection;

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid) {

		return findByL_H_P_Collection(
			layoutSetBranchId, head, plid, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid, int start, int end) {

		return findByL_H_P_Collection(
			layoutSetBranchId, head, plid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return findByL_H_P_Collection(
			layoutSetBranchId, head, plid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByL_H_P_Collection;
				finderArgs = new Object[] {layoutSetBranchId, head, plid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByL_H_P_Collection;
			finderArgs = new Object[] {
				layoutSetBranchId, head, plid, start, end, orderByComparator
			};
		}

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutRevision layoutRevision : list) {
					if ((layoutSetBranchId !=
							layoutRevision.getLayoutSetBranchId()) ||
						(head != layoutRevision.isHead()) ||
						(plid != layoutRevision.getPlid())) {

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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_H_P_COLLECTION_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_H_P_COLLECTION_HEAD_2);

			query.append(_FINDER_COLUMN_L_H_P_COLLECTION_PLID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(head);

				qPos.add(plid);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_H_P_Collection_First(
			long layoutSetBranchId, boolean head, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_H_P_Collection_First(
			layoutSetBranchId, head, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_H_P_Collection_First(
		long layoutSetBranchId, boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {

		List<LayoutRevision> list = findByL_H_P_Collection(
			layoutSetBranchId, head, plid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_H_P_Collection_Last(
			long layoutSetBranchId, boolean head, long plid,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_H_P_Collection_Last(
			layoutSetBranchId, head, plid, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", plid=");
		msg.append(plid);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_H_P_Collection_Last(
		long layoutSetBranchId, boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator) {

		int count = countByL_H_P_Collection(layoutSetBranchId, head, plid);

		if (count == 0) {
			return null;
		}

		List<LayoutRevision> list = findByL_H_P_Collection(
			layoutSetBranchId, head, plid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision[] findByL_H_P_Collection_PrevAndNext(
			long layoutRevisionId, long layoutSetBranchId, boolean head,
			long plid, OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_H_P_Collection_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, head, plid,
				orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByL_H_P_Collection_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, head, plid,
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

	protected LayoutRevision getByL_H_P_Collection_PrevAndNext(
		Session session, LayoutRevision layoutRevision, long layoutSetBranchId,
		boolean head, long plid,
		OrderByComparator<LayoutRevision> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_H_P_COLLECTION_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_H_P_COLLECTION_HEAD_2);

		query.append(_FINDER_COLUMN_L_H_P_COLLECTION_PLID_2);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(head);

		qPos.add(plid);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutRevision)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 */
	@Override
	public void removeByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid) {

		for (LayoutRevision layoutRevision :
				findByL_H_P_Collection(
					layoutSetBranchId, head, plid, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByL_H_P_Collection(
		long layoutSetBranchId, boolean head, long plid) {

		FinderPath finderPath = _finderPathCountByL_H_P_Collection;

		Object[] finderArgs = new Object[] {layoutSetBranchId, head, plid};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_H_P_COLLECTION_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_H_P_COLLECTION_HEAD_2);

			query.append(_FINDER_COLUMN_L_H_P_COLLECTION_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(head);

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

	private static final String
		_FINDER_COLUMN_L_H_P_COLLECTION_LAYOUTSETBRANCHID_2 =
			"layoutRevision.layoutSetBranchId = ? AND ";

	private static final String _FINDER_COLUMN_L_H_P_COLLECTION_HEAD_2 =
		"layoutRevision.head = ? AND ";

	private static final String _FINDER_COLUMN_L_H_P_COLLECTION_PLID_2 =
		"layoutRevision.plid = ?";

	private FinderPath _finderPathWithPaginationFindByL_H_S;
	private FinderPath _finderPathWithoutPaginationFindByL_H_S;
	private FinderPath _finderPathCountByL_H_S;

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param status the status
	 * @return the matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_H_S(
		long layoutSetBranchId, boolean head, int status) {

		return findByL_H_S(
			layoutSetBranchId, head, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_H_S(
		long layoutSetBranchId, boolean head, int status, int start, int end) {

		return findByL_H_S(layoutSetBranchId, head, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_H_S(
		long layoutSetBranchId, boolean head, int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return findByL_H_S(
			layoutSetBranchId, head, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_H_S(
		long layoutSetBranchId, boolean head, int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByL_H_S;
				finderArgs = new Object[] {layoutSetBranchId, head, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByL_H_S;
			finderArgs = new Object[] {
				layoutSetBranchId, head, status, start, end, orderByComparator
			};
		}

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutRevision layoutRevision : list) {
					if ((layoutSetBranchId !=
							layoutRevision.getLayoutSetBranchId()) ||
						(head != layoutRevision.isHead()) ||
						(status != layoutRevision.getStatus())) {

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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_H_S_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_H_S_HEAD_2);

			query.append(_FINDER_COLUMN_L_H_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(head);

				qPos.add(status);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_H_S_First(
			long layoutSetBranchId, boolean head, int status,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_H_S_First(
			layoutSetBranchId, head, status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_H_S_First(
		long layoutSetBranchId, boolean head, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {

		List<LayoutRevision> list = findByL_H_S(
			layoutSetBranchId, head, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_H_S_Last(
			long layoutSetBranchId, boolean head, int status,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_H_S_Last(
			layoutSetBranchId, head, status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", head=");
		msg.append(head);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_H_S_Last(
		long layoutSetBranchId, boolean head, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {

		int count = countByL_H_S(layoutSetBranchId, head, status);

		if (count == 0) {
			return null;
		}

		List<LayoutRevision> list = findByL_H_S(
			layoutSetBranchId, head, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision[] findByL_H_S_PrevAndNext(
			long layoutRevisionId, long layoutSetBranchId, boolean head,
			int status, OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_H_S_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, head, status,
				orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByL_H_S_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, head, status,
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

	protected LayoutRevision getByL_H_S_PrevAndNext(
		Session session, LayoutRevision layoutRevision, long layoutSetBranchId,
		boolean head, int status,
		OrderByComparator<LayoutRevision> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_H_S_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_H_S_HEAD_2);

		query.append(_FINDER_COLUMN_L_H_S_STATUS_2);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(head);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutRevision)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param status the status
	 */
	@Override
	public void removeByL_H_S(
		long layoutSetBranchId, boolean head, int status) {

		for (LayoutRevision layoutRevision :
				findByL_H_S(
					layoutSetBranchId, head, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and head = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param head the head
	 * @param status the status
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByL_H_S(long layoutSetBranchId, boolean head, int status) {
		FinderPath finderPath = _finderPathCountByL_H_S;

		Object[] finderArgs = new Object[] {layoutSetBranchId, head, status};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_H_S_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_H_S_HEAD_2);

			query.append(_FINDER_COLUMN_L_H_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(head);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_L_H_S_LAYOUTSETBRANCHID_2 =
		"layoutRevision.layoutSetBranchId = ? AND ";

	private static final String _FINDER_COLUMN_L_H_S_HEAD_2 =
		"layoutRevision.head = ? AND ";

	private static final String _FINDER_COLUMN_L_H_S_STATUS_2 =
		"layoutRevision.status = ?";

	private FinderPath _finderPathWithPaginationFindByL_P_S;
	private FinderPath _finderPathWithoutPaginationFindByL_P_S;
	private FinderPath _finderPathCountByL_P_S;

	/**
	 * Returns all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @return the matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_P_S(
		long layoutSetBranchId, long plid, int status) {

		return findByL_P_S(
			layoutSetBranchId, plid, status, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_P_S(
		long layoutSetBranchId, long plid, int status, int start, int end) {

		return findByL_P_S(layoutSetBranchId, plid, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_P_S(
		long layoutSetBranchId, long plid, int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return findByL_P_S(
			layoutSetBranchId, plid, status, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching layout revisions
	 */
	@Override
	public List<LayoutRevision> findByL_P_S(
		long layoutSetBranchId, long plid, int status, int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByL_P_S;
				finderArgs = new Object[] {layoutSetBranchId, plid, status};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByL_P_S;
			finderArgs = new Object[] {
				layoutSetBranchId, plid, status, start, end, orderByComparator
			};
		}

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (LayoutRevision layoutRevision : list) {
					if ((layoutSetBranchId !=
							layoutRevision.getLayoutSetBranchId()) ||
						(plid != layoutRevision.getPlid()) ||
						(status != layoutRevision.getStatus())) {

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

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_P_S_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_S_PLID_2);

			query.append(_FINDER_COLUMN_L_P_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				qPos.add(status);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_P_S_First(
			long layoutSetBranchId, long plid, int status,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_P_S_First(
			layoutSetBranchId, plid, status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the first layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_P_S_First(
		long layoutSetBranchId, long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {

		List<LayoutRevision> list = findByL_P_S(
			layoutSetBranchId, plid, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_P_S_Last(
			long layoutSetBranchId, long plid, int status,
			OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_P_S_Last(
			layoutSetBranchId, plid, status, orderByComparator);

		if (layoutRevision != null) {
			return layoutRevision;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("layoutSetBranchId=");
		msg.append(layoutSetBranchId);

		msg.append(", plid=");
		msg.append(plid);

		msg.append(", status=");
		msg.append(status);

		msg.append("}");

		throw new NoSuchLayoutRevisionException(msg.toString());
	}

	/**
	 * Returns the last layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_P_S_Last(
		long layoutSetBranchId, long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator) {

		int count = countByL_P_S(layoutSetBranchId, plid, status);

		if (count == 0) {
			return null;
		}

		List<LayoutRevision> list = findByL_P_S(
			layoutSetBranchId, plid, status, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the layout revisions before and after the current layout revision in the ordered set where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutRevisionId the primary key of the current layout revision
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision[] findByL_P_S_PrevAndNext(
			long layoutRevisionId, long layoutSetBranchId, long plid,
			int status, OrderByComparator<LayoutRevision> orderByComparator)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByPrimaryKey(layoutRevisionId);

		Session session = null;

		try {
			session = openSession();

			LayoutRevision[] array = new LayoutRevisionImpl[3];

			array[0] = getByL_P_S_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, plid, status,
				orderByComparator, true);

			array[1] = layoutRevision;

			array[2] = getByL_P_S_PrevAndNext(
				session, layoutRevision, layoutSetBranchId, plid, status,
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

	protected LayoutRevision getByL_P_S_PrevAndNext(
		Session session, LayoutRevision layoutRevision, long layoutSetBranchId,
		long plid, int status,
		OrderByComparator<LayoutRevision> orderByComparator, boolean previous) {

		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

		query.append(_FINDER_COLUMN_L_P_S_LAYOUTSETBRANCHID_2);

		query.append(_FINDER_COLUMN_L_P_S_PLID_2);

		query.append(_FINDER_COLUMN_L_P_S_STATUS_2);

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
			query.append(LayoutRevisionModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(layoutSetBranchId);

		qPos.add(plid);

		qPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						layoutRevision)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<LayoutRevision> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 */
	@Override
	public void removeByL_P_S(long layoutSetBranchId, long plid, int status) {
		for (LayoutRevision layoutRevision :
				findByL_P_S(
					layoutSetBranchId, plid, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and plid = &#63; and status = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param plid the plid
	 * @param status the status
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByL_P_S(long layoutSetBranchId, long plid, int status) {
		FinderPath finderPath = _finderPathCountByL_P_S;

		Object[] finderArgs = new Object[] {layoutSetBranchId, plid, status};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_P_S_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_P_S_PLID_2);

			query.append(_FINDER_COLUMN_L_P_S_STATUS_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(plid);

				qPos.add(status);

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

	private static final String _FINDER_COLUMN_L_P_S_LAYOUTSETBRANCHID_2 =
		"layoutRevision.layoutSetBranchId = ? AND ";

	private static final String _FINDER_COLUMN_L_P_S_PLID_2 =
		"layoutRevision.plid = ? AND ";

	private static final String _FINDER_COLUMN_L_P_S_STATUS_2 =
		"layoutRevision.status = ?";

	private FinderPath _finderPathFetchByL_L_H_P;
	private FinderPath _finderPathCountByL_L_H_P;

	/**
	 * Returns the layout revision where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63; or throws a <code>NoSuchLayoutRevisionException</code> if it could not be found.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the matching layout revision
	 * @throws NoSuchLayoutRevisionException if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision findByL_L_H_P(
			long layoutSetBranchId, long layoutBranchId, boolean head,
			long plid)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByL_L_H_P(
			layoutSetBranchId, layoutBranchId, head, plid);

		if (layoutRevision == null) {
			StringBundler msg = new StringBundler(10);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("layoutSetBranchId=");
			msg.append(layoutSetBranchId);

			msg.append(", layoutBranchId=");
			msg.append(layoutBranchId);

			msg.append(", head=");
			msg.append(head);

			msg.append(", plid=");
			msg.append(plid);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchLayoutRevisionException(msg.toString());
		}

		return layoutRevision;
	}

	/**
	 * Returns the layout revision where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_L_H_P(
		long layoutSetBranchId, long layoutBranchId, boolean head, long plid) {

		return fetchByL_L_H_P(
			layoutSetBranchId, layoutBranchId, head, plid, true);
	}

	/**
	 * Returns the layout revision where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param head the head
	 * @param plid the plid
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching layout revision, or <code>null</code> if a matching layout revision could not be found
	 */
	@Override
	public LayoutRevision fetchByL_L_H_P(
		long layoutSetBranchId, long layoutBranchId, boolean head, long plid,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				layoutSetBranchId, layoutBranchId, head, plid
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = FinderCacheUtil.getResult(
				_finderPathFetchByL_L_H_P, finderArgs, this);
		}

		if (result instanceof LayoutRevision) {
			LayoutRevision layoutRevision = (LayoutRevision)result;

			if ((layoutSetBranchId != layoutRevision.getLayoutSetBranchId()) ||
				(layoutBranchId != layoutRevision.getLayoutBranchId()) ||
				(head != layoutRevision.isHead()) ||
				(plid != layoutRevision.getPlid())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(6);

			query.append(_SQL_SELECT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_L_H_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_L_H_P_LAYOUTBRANCHID_2);

			query.append(_FINDER_COLUMN_L_L_H_P_HEAD_2);

			query.append(_FINDER_COLUMN_L_L_H_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(layoutBranchId);

				qPos.add(head);

				qPos.add(plid);

				List<LayoutRevision> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						FinderCacheUtil.putResult(
							_finderPathFetchByL_L_H_P, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									layoutSetBranchId, layoutBranchId, head,
									plid
								};
							}

							_log.warn(
								"LayoutRevisionPersistenceImpl.fetchByL_L_H_P(long, long, boolean, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					LayoutRevision layoutRevision = list.get(0);

					result = layoutRevision;

					cacheResult(layoutRevision);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(
						_finderPathFetchByL_L_H_P, finderArgs);
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
			return (LayoutRevision)result;
		}
	}

	/**
	 * Removes the layout revision where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63; from the database.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the layout revision that was removed
	 */
	@Override
	public LayoutRevision removeByL_L_H_P(
			long layoutSetBranchId, long layoutBranchId, boolean head,
			long plid)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = findByL_L_H_P(
			layoutSetBranchId, layoutBranchId, head, plid);

		return remove(layoutRevision);
	}

	/**
	 * Returns the number of layout revisions where layoutSetBranchId = &#63; and layoutBranchId = &#63; and head = &#63; and plid = &#63;.
	 *
	 * @param layoutSetBranchId the layout set branch ID
	 * @param layoutBranchId the layout branch ID
	 * @param head the head
	 * @param plid the plid
	 * @return the number of matching layout revisions
	 */
	@Override
	public int countByL_L_H_P(
		long layoutSetBranchId, long layoutBranchId, boolean head, long plid) {

		FinderPath finderPath = _finderPathCountByL_L_H_P;

		Object[] finderArgs = new Object[] {
			layoutSetBranchId, layoutBranchId, head, plid
		};

		Long count = (Long)FinderCacheUtil.getResult(
			finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_COUNT_LAYOUTREVISION_WHERE);

			query.append(_FINDER_COLUMN_L_L_H_P_LAYOUTSETBRANCHID_2);

			query.append(_FINDER_COLUMN_L_L_H_P_LAYOUTBRANCHID_2);

			query.append(_FINDER_COLUMN_L_L_H_P_HEAD_2);

			query.append(_FINDER_COLUMN_L_L_H_P_PLID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(layoutSetBranchId);

				qPos.add(layoutBranchId);

				qPos.add(head);

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

	private static final String _FINDER_COLUMN_L_L_H_P_LAYOUTSETBRANCHID_2 =
		"layoutRevision.layoutSetBranchId = ? AND ";

	private static final String _FINDER_COLUMN_L_L_H_P_LAYOUTBRANCHID_2 =
		"layoutRevision.layoutBranchId = ? AND ";

	private static final String _FINDER_COLUMN_L_L_H_P_HEAD_2 =
		"layoutRevision.head = ? AND ";

	private static final String _FINDER_COLUMN_L_L_H_P_PLID_2 =
		"layoutRevision.plid = ?";

	public LayoutRevisionPersistenceImpl() {
		setModelClass(LayoutRevision.class);

		setModelImplClass(LayoutRevisionImpl.class);
		setModelPKClass(long.class);
		setEntityCacheEnabled(LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED);
	}

	/**
	 * Caches the layout revision in the entity cache if it is enabled.
	 *
	 * @param layoutRevision the layout revision
	 */
	@Override
	public void cacheResult(LayoutRevision layoutRevision) {
		EntityCacheUtil.putResult(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionImpl.class, layoutRevision.getPrimaryKey(),
			layoutRevision);

		FinderCacheUtil.putResult(
			_finderPathFetchByL_H_P,
			new Object[] {
				layoutRevision.getLayoutSetBranchId(), layoutRevision.isHead(),
				layoutRevision.getPlid()
			},
			layoutRevision);

		FinderCacheUtil.putResult(
			_finderPathFetchByL_L_H_P,
			new Object[] {
				layoutRevision.getLayoutSetBranchId(),
				layoutRevision.getLayoutBranchId(), layoutRevision.isHead(),
				layoutRevision.getPlid()
			},
			layoutRevision);

		layoutRevision.resetOriginalValues();
	}

	/**
	 * Caches the layout revisions in the entity cache if it is enabled.
	 *
	 * @param layoutRevisions the layout revisions
	 */
	@Override
	public void cacheResult(List<LayoutRevision> layoutRevisions) {
		for (LayoutRevision layoutRevision : layoutRevisions) {
			if (EntityCacheUtil.getResult(
					LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
					LayoutRevisionImpl.class, layoutRevision.getPrimaryKey()) ==
						null) {

				cacheResult(layoutRevision);
			}
			else {
				layoutRevision.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all layout revisions.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		EntityCacheUtil.clearCache(LayoutRevisionImpl.class);

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the layout revision.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>com.liferay.portal.kernel.dao.orm.FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(LayoutRevision layoutRevision) {
		EntityCacheUtil.removeResult(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionImpl.class, layoutRevision.getPrimaryKey());

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((LayoutRevisionModelImpl)layoutRevision, true);
	}

	@Override
	public void clearCache(List<LayoutRevision> layoutRevisions) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (LayoutRevision layoutRevision : layoutRevisions) {
			EntityCacheUtil.removeResult(
				LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutRevisionImpl.class, layoutRevision.getPrimaryKey());

			clearUniqueFindersCache(
				(LayoutRevisionModelImpl)layoutRevision, true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			EntityCacheUtil.removeResult(
				LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
				LayoutRevisionImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		LayoutRevisionModelImpl layoutRevisionModelImpl) {

		Object[] args = new Object[] {
			layoutRevisionModelImpl.getLayoutSetBranchId(),
			layoutRevisionModelImpl.isHead(), layoutRevisionModelImpl.getPlid()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByL_H_P, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByL_H_P, args, layoutRevisionModelImpl, false);

		args = new Object[] {
			layoutRevisionModelImpl.getLayoutSetBranchId(),
			layoutRevisionModelImpl.getLayoutBranchId(),
			layoutRevisionModelImpl.isHead(), layoutRevisionModelImpl.getPlid()
		};

		FinderCacheUtil.putResult(
			_finderPathCountByL_L_H_P, args, Long.valueOf(1), false);
		FinderCacheUtil.putResult(
			_finderPathFetchByL_L_H_P, args, layoutRevisionModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		LayoutRevisionModelImpl layoutRevisionModelImpl, boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutRevisionModelImpl.getLayoutSetBranchId(),
				layoutRevisionModelImpl.isHead(),
				layoutRevisionModelImpl.getPlid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByL_H_P, args);
			FinderCacheUtil.removeResult(_finderPathFetchByL_H_P, args);
		}

		if ((layoutRevisionModelImpl.getColumnBitmask() &
			 _finderPathFetchByL_H_P.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutRevisionModelImpl.getOriginalLayoutSetBranchId(),
				layoutRevisionModelImpl.getOriginalHead(),
				layoutRevisionModelImpl.getOriginalPlid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByL_H_P, args);
			FinderCacheUtil.removeResult(_finderPathFetchByL_H_P, args);
		}

		if (clearCurrent) {
			Object[] args = new Object[] {
				layoutRevisionModelImpl.getLayoutSetBranchId(),
				layoutRevisionModelImpl.getLayoutBranchId(),
				layoutRevisionModelImpl.isHead(),
				layoutRevisionModelImpl.getPlid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByL_L_H_P, args);
			FinderCacheUtil.removeResult(_finderPathFetchByL_L_H_P, args);
		}

		if ((layoutRevisionModelImpl.getColumnBitmask() &
			 _finderPathFetchByL_L_H_P.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				layoutRevisionModelImpl.getOriginalLayoutSetBranchId(),
				layoutRevisionModelImpl.getOriginalLayoutBranchId(),
				layoutRevisionModelImpl.getOriginalHead(),
				layoutRevisionModelImpl.getOriginalPlid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByL_L_H_P, args);
			FinderCacheUtil.removeResult(_finderPathFetchByL_L_H_P, args);
		}
	}

	/**
	 * Creates a new layout revision with the primary key. Does not add the layout revision to the database.
	 *
	 * @param layoutRevisionId the primary key for the new layout revision
	 * @return the new layout revision
	 */
	@Override
	public LayoutRevision create(long layoutRevisionId) {
		LayoutRevision layoutRevision = new LayoutRevisionImpl();

		layoutRevision.setNew(true);
		layoutRevision.setPrimaryKey(layoutRevisionId);

		layoutRevision.setCompanyId(CompanyThreadLocal.getCompanyId());

		return layoutRevision;
	}

	/**
	 * Removes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param layoutRevisionId the primary key of the layout revision
	 * @return the layout revision that was removed
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision remove(long layoutRevisionId)
		throws NoSuchLayoutRevisionException {

		return remove((Serializable)layoutRevisionId);
	}

	/**
	 * Removes the layout revision with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the layout revision
	 * @return the layout revision that was removed
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision remove(Serializable primaryKey)
		throws NoSuchLayoutRevisionException {

		Session session = null;

		try {
			session = openSession();

			LayoutRevision layoutRevision = (LayoutRevision)session.get(
				LayoutRevisionImpl.class, primaryKey);

			if (layoutRevision == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchLayoutRevisionException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(layoutRevision);
		}
		catch (NoSuchLayoutRevisionException nsee) {
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
	protected LayoutRevision removeImpl(LayoutRevision layoutRevision) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(layoutRevision)) {
				layoutRevision = (LayoutRevision)session.get(
					LayoutRevisionImpl.class,
					layoutRevision.getPrimaryKeyObj());
			}

			if (layoutRevision != null) {
				session.delete(layoutRevision);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (layoutRevision != null) {
			clearCache(layoutRevision);
		}

		return layoutRevision;
	}

	@Override
	public LayoutRevision updateImpl(LayoutRevision layoutRevision) {
		boolean isNew = layoutRevision.isNew();

		if (!(layoutRevision instanceof LayoutRevisionModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(layoutRevision.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					layoutRevision);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in layoutRevision proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom LayoutRevision implementation " +
					layoutRevision.getClass());
		}

		LayoutRevisionModelImpl layoutRevisionModelImpl =
			(LayoutRevisionModelImpl)layoutRevision;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (layoutRevision.getCreateDate() == null)) {
			if (serviceContext == null) {
				layoutRevision.setCreateDate(now);
			}
			else {
				layoutRevision.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!layoutRevisionModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				layoutRevision.setModifiedDate(now);
			}
			else {
				layoutRevision.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (layoutRevision.isNew()) {
				session.save(layoutRevision);

				layoutRevision.setNew(false);
			}
			else {
				layoutRevision = (LayoutRevision)session.merge(layoutRevision);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!LayoutRevisionModelImpl.COLUMN_BITMASK_ENABLED) {
			FinderCacheUtil.clearCache(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				layoutRevisionModelImpl.getLayoutSetBranchId()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByLayoutSetBranchId, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByLayoutSetBranchId, args);

			args = new Object[] {layoutRevisionModelImpl.getPlid()};

			FinderCacheUtil.removeResult(_finderPathCountByPlid, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByPlid, args);

			args = new Object[] {layoutRevisionModelImpl.getStatus()};

			FinderCacheUtil.removeResult(_finderPathCountByStatus, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByStatus, args);

			args = new Object[] {
				layoutRevisionModelImpl.getLayoutSetBranchId(),
				layoutRevisionModelImpl.isHead()
			};

			FinderCacheUtil.removeResult(_finderPathCountByL_H, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByL_H, args);

			args = new Object[] {
				layoutRevisionModelImpl.getLayoutSetBranchId(),
				layoutRevisionModelImpl.getPlid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByL_P, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByL_P, args);

			args = new Object[] {
				layoutRevisionModelImpl.getLayoutSetBranchId(),
				layoutRevisionModelImpl.getStatus()
			};

			FinderCacheUtil.removeResult(_finderPathCountByL_S, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByL_S, args);

			args = new Object[] {
				layoutRevisionModelImpl.isHead(),
				layoutRevisionModelImpl.getPlid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByH_P, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByH_P, args);

			args = new Object[] {
				layoutRevisionModelImpl.getLayoutSetBranchId(),
				layoutRevisionModelImpl.getLayoutBranchId(),
				layoutRevisionModelImpl.getPlid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByL_L_P, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByL_L_P, args);

			args = new Object[] {
				layoutRevisionModelImpl.getLayoutSetBranchId(),
				layoutRevisionModelImpl.getParentLayoutRevisionId(),
				layoutRevisionModelImpl.getPlid()
			};

			FinderCacheUtil.removeResult(_finderPathCountByL_P_P, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByL_P_P, args);

			args = new Object[] {
				layoutRevisionModelImpl.getLayoutSetBranchId(),
				layoutRevisionModelImpl.isHead(),
				layoutRevisionModelImpl.getPlid()
			};

			FinderCacheUtil.removeResult(
				_finderPathCountByL_H_P_Collection, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByL_H_P_Collection, args);

			args = new Object[] {
				layoutRevisionModelImpl.getLayoutSetBranchId(),
				layoutRevisionModelImpl.isHead(),
				layoutRevisionModelImpl.getStatus()
			};

			FinderCacheUtil.removeResult(_finderPathCountByL_H_S, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByL_H_S, args);

			args = new Object[] {
				layoutRevisionModelImpl.getLayoutSetBranchId(),
				layoutRevisionModelImpl.getPlid(),
				layoutRevisionModelImpl.getStatus()
			};

			FinderCacheUtil.removeResult(_finderPathCountByL_P_S, args);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindByL_P_S, args);

			FinderCacheUtil.removeResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
			FinderCacheUtil.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((layoutRevisionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByLayoutSetBranchId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutRevisionModelImpl.getOriginalLayoutSetBranchId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByLayoutSetBranchId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByLayoutSetBranchId, args);

				args = new Object[] {
					layoutRevisionModelImpl.getLayoutSetBranchId()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByLayoutSetBranchId, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByLayoutSetBranchId, args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByPlid.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutRevisionModelImpl.getOriginalPlid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByPlid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByPlid, args);

				args = new Object[] {layoutRevisionModelImpl.getPlid()};

				FinderCacheUtil.removeResult(_finderPathCountByPlid, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByPlid, args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByStatus.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutRevisionModelImpl.getOriginalStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByStatus, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByStatus, args);

				args = new Object[] {layoutRevisionModelImpl.getStatus()};

				FinderCacheUtil.removeResult(_finderPathCountByStatus, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByStatus, args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByL_H.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutRevisionModelImpl.getOriginalLayoutSetBranchId(),
					layoutRevisionModelImpl.getOriginalHead()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_H, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_H, args);

				args = new Object[] {
					layoutRevisionModelImpl.getLayoutSetBranchId(),
					layoutRevisionModelImpl.isHead()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_H, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_H, args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByL_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutRevisionModelImpl.getOriginalLayoutSetBranchId(),
					layoutRevisionModelImpl.getOriginalPlid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_P, args);

				args = new Object[] {
					layoutRevisionModelImpl.getLayoutSetBranchId(),
					layoutRevisionModelImpl.getPlid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_P, args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByL_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutRevisionModelImpl.getOriginalLayoutSetBranchId(),
					layoutRevisionModelImpl.getOriginalStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_S, args);

				args = new Object[] {
					layoutRevisionModelImpl.getLayoutSetBranchId(),
					layoutRevisionModelImpl.getStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_S, args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByH_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutRevisionModelImpl.getOriginalHead(),
					layoutRevisionModelImpl.getOriginalPlid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByH_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByH_P, args);

				args = new Object[] {
					layoutRevisionModelImpl.isHead(),
					layoutRevisionModelImpl.getPlid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByH_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByH_P, args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByL_L_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutRevisionModelImpl.getOriginalLayoutSetBranchId(),
					layoutRevisionModelImpl.getOriginalLayoutBranchId(),
					layoutRevisionModelImpl.getOriginalPlid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_L_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_L_P, args);

				args = new Object[] {
					layoutRevisionModelImpl.getLayoutSetBranchId(),
					layoutRevisionModelImpl.getLayoutBranchId(),
					layoutRevisionModelImpl.getPlid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_L_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_L_P, args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByL_P_P.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutRevisionModelImpl.getOriginalLayoutSetBranchId(),
					layoutRevisionModelImpl.getOriginalParentLayoutRevisionId(),
					layoutRevisionModelImpl.getOriginalPlid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_P_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_P_P, args);

				args = new Object[] {
					layoutRevisionModelImpl.getLayoutSetBranchId(),
					layoutRevisionModelImpl.getParentLayoutRevisionId(),
					layoutRevisionModelImpl.getPlid()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_P_P, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_P_P, args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByL_H_P_Collection.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					layoutRevisionModelImpl.getOriginalLayoutSetBranchId(),
					layoutRevisionModelImpl.getOriginalHead(),
					layoutRevisionModelImpl.getOriginalPlid()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByL_H_P_Collection, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_H_P_Collection, args);

				args = new Object[] {
					layoutRevisionModelImpl.getLayoutSetBranchId(),
					layoutRevisionModelImpl.isHead(),
					layoutRevisionModelImpl.getPlid()
				};

				FinderCacheUtil.removeResult(
					_finderPathCountByL_H_P_Collection, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_H_P_Collection, args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByL_H_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutRevisionModelImpl.getOriginalLayoutSetBranchId(),
					layoutRevisionModelImpl.getOriginalHead(),
					layoutRevisionModelImpl.getOriginalStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_H_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_H_S, args);

				args = new Object[] {
					layoutRevisionModelImpl.getLayoutSetBranchId(),
					layoutRevisionModelImpl.isHead(),
					layoutRevisionModelImpl.getStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_H_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_H_S, args);
			}

			if ((layoutRevisionModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByL_P_S.getColumnBitmask()) !=
					 0) {

				Object[] args = new Object[] {
					layoutRevisionModelImpl.getOriginalLayoutSetBranchId(),
					layoutRevisionModelImpl.getOriginalPlid(),
					layoutRevisionModelImpl.getOriginalStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_P_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_P_S, args);

				args = new Object[] {
					layoutRevisionModelImpl.getLayoutSetBranchId(),
					layoutRevisionModelImpl.getPlid(),
					layoutRevisionModelImpl.getStatus()
				};

				FinderCacheUtil.removeResult(_finderPathCountByL_P_S, args);
				FinderCacheUtil.removeResult(
					_finderPathWithoutPaginationFindByL_P_S, args);
			}
		}

		EntityCacheUtil.putResult(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionImpl.class, layoutRevision.getPrimaryKey(),
			layoutRevision, false);

		clearUniqueFindersCache(layoutRevisionModelImpl, false);
		cacheUniqueFindersCache(layoutRevisionModelImpl);

		layoutRevision.resetOriginalValues();

		return layoutRevision;
	}

	/**
	 * Returns the layout revision with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the layout revision
	 * @return the layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision findByPrimaryKey(Serializable primaryKey)
		throws NoSuchLayoutRevisionException {

		LayoutRevision layoutRevision = fetchByPrimaryKey(primaryKey);

		if (layoutRevision == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchLayoutRevisionException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return layoutRevision;
	}

	/**
	 * Returns the layout revision with the primary key or throws a <code>NoSuchLayoutRevisionException</code> if it could not be found.
	 *
	 * @param layoutRevisionId the primary key of the layout revision
	 * @return the layout revision
	 * @throws NoSuchLayoutRevisionException if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision findByPrimaryKey(long layoutRevisionId)
		throws NoSuchLayoutRevisionException {

		return findByPrimaryKey((Serializable)layoutRevisionId);
	}

	/**
	 * Returns the layout revision with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param layoutRevisionId the primary key of the layout revision
	 * @return the layout revision, or <code>null</code> if a layout revision with the primary key could not be found
	 */
	@Override
	public LayoutRevision fetchByPrimaryKey(long layoutRevisionId) {
		return fetchByPrimaryKey((Serializable)layoutRevisionId);
	}

	/**
	 * Returns all the layout revisions.
	 *
	 * @return the layout revisions
	 */
	@Override
	public List<LayoutRevision> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the layout revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @return the range of layout revisions
	 */
	@Override
	public List<LayoutRevision> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the layout revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of layout revisions
	 */
	@Override
	public List<LayoutRevision> findAll(
		int start, int end,
		OrderByComparator<LayoutRevision> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the layout revisions.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>LayoutRevisionModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of layout revisions
	 * @param end the upper bound of the range of layout revisions (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of layout revisions
	 */
	@Override
	public List<LayoutRevision> findAll(
		int start, int end, OrderByComparator<LayoutRevision> orderByComparator,
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

		List<LayoutRevision> list = null;

		if (useFinderCache) {
			list = (List<LayoutRevision>)FinderCacheUtil.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_LAYOUTREVISION);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_LAYOUTREVISION;

				sql = sql.concat(LayoutRevisionModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<LayoutRevision>)QueryUtil.list(
					q, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					FinderCacheUtil.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					FinderCacheUtil.removeResult(finderPath, finderArgs);
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
	 * Removes all the layout revisions from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (LayoutRevision layoutRevision : findAll()) {
			remove(layoutRevision);
		}
	}

	/**
	 * Returns the number of layout revisions.
	 *
	 * @return the number of layout revisions
	 */
	@Override
	public int countAll() {
		Long count = (Long)FinderCacheUtil.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_LAYOUTREVISION);

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
	protected EntityCache getEntityCache() {
		return EntityCacheUtil.getEntityCache();
	}

	@Override
	protected String getPKDBName() {
		return "layoutRevisionId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_LAYOUTREVISION;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return LayoutRevisionModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the layout revision persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findAll", new String[0]);

		_finderPathCountAll = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByLayoutSetBranchId = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByLayoutSetBranchId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByLayoutSetBranchId = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByLayoutSetBranchId", new String[] {Long.class.getName()},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByLayoutSetBranchId = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByLayoutSetBranchId", new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByPlid = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByPlid",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByPlid = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByPlid", new String[] {Long.class.getName()},
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByPlid = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByPlid",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByStatus = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByStatus",
			new String[] {
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByStatus = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByStatus", new String[] {Integer.class.getName()},
			LayoutRevisionModelImpl.STATUS_COLUMN_BITMASK |
			LayoutRevisionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByStatus = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByStatus",
			new String[] {Integer.class.getName()});

		_finderPathWithPaginationFindByL_H = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_H",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByL_H = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByL_H",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.HEAD_COLUMN_BITMASK |
			LayoutRevisionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByL_H = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_H",
			new String[] {Long.class.getName(), Boolean.class.getName()});

		_finderPathWithPaginationFindByL_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByL_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByL_P",
			new String[] {Long.class.getName(), Long.class.getName()},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByL_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P",
			new String[] {Long.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByL_S = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_S",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByL_S = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByL_S",
			new String[] {Long.class.getName(), Integer.class.getName()},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.STATUS_COLUMN_BITMASK |
			LayoutRevisionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByL_S = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_S",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByH_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByH_P",
			new String[] {
				Boolean.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByH_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByH_P",
			new String[] {Boolean.class.getName(), Long.class.getName()},
			LayoutRevisionModelImpl.HEAD_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByH_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByH_P",
			new String[] {Boolean.class.getName(), Long.class.getName()});

		_finderPathWithPaginationFindByP_NotS = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByP_NotS",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});

		_finderPathWithPaginationCountByP_NotS = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByP_NotS",
			new String[] {Long.class.getName(), Integer.class.getName()});

		_finderPathWithPaginationFindByL_L_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_L_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByL_L_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByL_L_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.LAYOUTBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByL_L_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_L_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathWithPaginationFindByL_P_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_P_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByL_P_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByL_P_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PARENTLAYOUTREVISIONID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByL_P_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P_P",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

		_finderPathFetchByL_H_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_ENTITY, "fetchByL_H_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.HEAD_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK);

		_finderPathCountByL_H_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_H_P",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});

		_finderPathWithPaginationFindByL_H_P_Collection = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_H_P_Collection",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByL_H_P_Collection = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByL_H_P_Collection",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.HEAD_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByL_H_P_Collection = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByL_H_P_Collection",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Long.class.getName()
			});

		_finderPathWithPaginationFindByL_H_S = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_H_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByL_H_S = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByL_H_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.HEAD_COLUMN_BITMASK |
			LayoutRevisionModelImpl.STATUS_COLUMN_BITMASK |
			LayoutRevisionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByL_H_S = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_H_S",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName()
			});

		_finderPathWithPaginationFindByL_P_S = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByL_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByL_P_S = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByL_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.STATUS_COLUMN_BITMASK |
			LayoutRevisionModelImpl.MODIFIEDDATE_COLUMN_BITMASK);

		_finderPathCountByL_P_S = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_P_S",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName()
			});

		_finderPathFetchByL_L_H_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED,
			LayoutRevisionImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByL_L_H_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Long.class.getName()
			},
			LayoutRevisionModelImpl.LAYOUTSETBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.LAYOUTBRANCHID_COLUMN_BITMASK |
			LayoutRevisionModelImpl.HEAD_COLUMN_BITMASK |
			LayoutRevisionModelImpl.PLID_COLUMN_BITMASK);

		_finderPathCountByL_L_H_P = new FinderPath(
			LayoutRevisionModelImpl.ENTITY_CACHE_ENABLED,
			LayoutRevisionModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByL_L_H_P",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Long.class.getName()
			});
	}

	public void destroy() {
		EntityCacheUtil.removeCache(LayoutRevisionImpl.class.getName());
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	private static final String _SQL_SELECT_LAYOUTREVISION =
		"SELECT layoutRevision FROM LayoutRevision layoutRevision";

	private static final String _SQL_SELECT_LAYOUTREVISION_WHERE =
		"SELECT layoutRevision FROM LayoutRevision layoutRevision WHERE ";

	private static final String _SQL_COUNT_LAYOUTREVISION =
		"SELECT COUNT(layoutRevision) FROM LayoutRevision layoutRevision";

	private static final String _SQL_COUNT_LAYOUTREVISION_WHERE =
		"SELECT COUNT(layoutRevision) FROM LayoutRevision layoutRevision WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "layoutRevision.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No LayoutRevision exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No LayoutRevision exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		LayoutRevisionPersistenceImpl.class);

}