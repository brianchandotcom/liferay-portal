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

package com.liferay.depot.service.persistence.impl;

import com.liferay.depot.exception.NoSuchEntryGroupRelException;
import com.liferay.depot.model.DepotEntryGroupRel;
import com.liferay.depot.model.impl.DepotEntryGroupRelImpl;
import com.liferay.depot.model.impl.DepotEntryGroupRelModelImpl;
import com.liferay.depot.service.persistence.DepotEntryGroupRelPersistence;
import com.liferay.depot.service.persistence.impl.constants.DepotPersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the depot entry group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(service = DepotEntryGroupRelPersistence.class)
public class DepotEntryGroupRelPersistenceImpl
	extends BasePersistenceImpl<DepotEntryGroupRel>
	implements DepotEntryGroupRelPersistence {

	/**
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>DepotEntryGroupRelUtil</code> to access the depot entry group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		DepotEntryGroupRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByDepotEntryId;
	private FinderPath _finderPathWithoutPaginationFindByDepotEntryId;
	private FinderPath _finderPathCountByDepotEntryId;

	/**
	 * Returns all the depot entry group rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the matching depot entry group rels
	 */
	@Override
	public List<DepotEntryGroupRel> findByDepotEntryId(long depotEntryId) {
		return findByDepotEntryId(
			depotEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the depot entry group rels where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @return the range of matching depot entry group rels
	 */
	@Override
	public List<DepotEntryGroupRel> findByDepotEntryId(
		long depotEntryId, int start, int end) {

		return findByDepotEntryId(depotEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the depot entry group rels where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry group rels
	 */
	@Override
	public List<DepotEntryGroupRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<DepotEntryGroupRel> orderByComparator) {

		return findByDepotEntryId(
			depotEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the depot entry group rels where depotEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param depotEntryId the depot entry ID
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry group rels
	 */
	@Override
	public List<DepotEntryGroupRel> findByDepotEntryId(
		long depotEntryId, int start, int end,
		OrderByComparator<DepotEntryGroupRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByDepotEntryId;
				finderArgs = new Object[] {depotEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByDepotEntryId;
			finderArgs = new Object[] {
				depotEntryId, start, end, orderByComparator
			};
		}

		List<DepotEntryGroupRel> list = null;

		if (useFinderCache) {
			list = (List<DepotEntryGroupRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DepotEntryGroupRel depotEntryGroupRel : list) {
					if (depotEntryId != depotEntryGroupRel.getDepotEntryId()) {
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

			query.append(_SQL_SELECT_DEPOTENTRYGROUPREL_WHERE);

			query.append(_FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DepotEntryGroupRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(depotEntryId);

				list = (List<DepotEntryGroupRel>)QueryUtil.list(
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
	 * Returns the first depot entry group rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a matching depot entry group rel could not be found
	 */
	@Override
	public DepotEntryGroupRel findByDepotEntryId_First(
			long depotEntryId,
			OrderByComparator<DepotEntryGroupRel> orderByComparator)
		throws NoSuchEntryGroupRelException {

		DepotEntryGroupRel depotEntryGroupRel = fetchByDepotEntryId_First(
			depotEntryId, orderByComparator);

		if (depotEntryGroupRel != null) {
			return depotEntryGroupRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("depotEntryId=");
		msg.append(depotEntryId);

		msg.append("}");

		throw new NoSuchEntryGroupRelException(msg.toString());
	}

	/**
	 * Returns the first depot entry group rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry group rel, or <code>null</code> if a matching depot entry group rel could not be found
	 */
	@Override
	public DepotEntryGroupRel fetchByDepotEntryId_First(
		long depotEntryId,
		OrderByComparator<DepotEntryGroupRel> orderByComparator) {

		List<DepotEntryGroupRel> list = findByDepotEntryId(
			depotEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last depot entry group rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a matching depot entry group rel could not be found
	 */
	@Override
	public DepotEntryGroupRel findByDepotEntryId_Last(
			long depotEntryId,
			OrderByComparator<DepotEntryGroupRel> orderByComparator)
		throws NoSuchEntryGroupRelException {

		DepotEntryGroupRel depotEntryGroupRel = fetchByDepotEntryId_Last(
			depotEntryId, orderByComparator);

		if (depotEntryGroupRel != null) {
			return depotEntryGroupRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("depotEntryId=");
		msg.append(depotEntryId);

		msg.append("}");

		throw new NoSuchEntryGroupRelException(msg.toString());
	}

	/**
	 * Returns the last depot entry group rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry group rel, or <code>null</code> if a matching depot entry group rel could not be found
	 */
	@Override
	public DepotEntryGroupRel fetchByDepotEntryId_Last(
		long depotEntryId,
		OrderByComparator<DepotEntryGroupRel> orderByComparator) {

		int count = countByDepotEntryId(depotEntryId);

		if (count == 0) {
			return null;
		}

		List<DepotEntryGroupRel> list = findByDepotEntryId(
			depotEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the depot entry group rels before and after the current depot entry group rel in the ordered set where depotEntryId = &#63;.
	 *
	 * @param depotEntryGroupRelId the primary key of the current depot entry group rel
	 * @param depotEntryId the depot entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a depot entry group rel with the primary key could not be found
	 */
	@Override
	public DepotEntryGroupRel[] findByDepotEntryId_PrevAndNext(
			long depotEntryGroupRelId, long depotEntryId,
			OrderByComparator<DepotEntryGroupRel> orderByComparator)
		throws NoSuchEntryGroupRelException {

		DepotEntryGroupRel depotEntryGroupRel = findByPrimaryKey(
			depotEntryGroupRelId);

		Session session = null;

		try {
			session = openSession();

			DepotEntryGroupRel[] array = new DepotEntryGroupRelImpl[3];

			array[0] = getByDepotEntryId_PrevAndNext(
				session, depotEntryGroupRel, depotEntryId, orderByComparator,
				true);

			array[1] = depotEntryGroupRel;

			array[2] = getByDepotEntryId_PrevAndNext(
				session, depotEntryGroupRel, depotEntryId, orderByComparator,
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

	protected DepotEntryGroupRel getByDepotEntryId_PrevAndNext(
		Session session, DepotEntryGroupRel depotEntryGroupRel,
		long depotEntryId,
		OrderByComparator<DepotEntryGroupRel> orderByComparator,
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

		query.append(_SQL_SELECT_DEPOTENTRYGROUPREL_WHERE);

		query.append(_FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2);

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
			query.append(DepotEntryGroupRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(depotEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						depotEntryGroupRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DepotEntryGroupRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the depot entry group rels where depotEntryId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 */
	@Override
	public void removeByDepotEntryId(long depotEntryId) {
		for (DepotEntryGroupRel depotEntryGroupRel :
				findByDepotEntryId(
					depotEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(depotEntryGroupRel);
		}
	}

	/**
	 * Returns the number of depot entry group rels where depotEntryId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @return the number of matching depot entry group rels
	 */
	@Override
	public int countByDepotEntryId(long depotEntryId) {
		FinderPath finderPath = _finderPathCountByDepotEntryId;

		Object[] finderArgs = new Object[] {depotEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DEPOTENTRYGROUPREL_WHERE);

			query.append(_FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(depotEntryId);

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

	private static final String _FINDER_COLUMN_DEPOTENTRYID_DEPOTENTRYID_2 =
		"depotEntryGroupRel.depotEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByToGroupId;
	private FinderPath _finderPathWithoutPaginationFindByToGroupId;
	private FinderPath _finderPathCountByToGroupId;

	/**
	 * Returns all the depot entry group rels where toGroupId = &#63;.
	 *
	 * @param toGroupId the to group ID
	 * @return the matching depot entry group rels
	 */
	@Override
	public List<DepotEntryGroupRel> findByToGroupId(long toGroupId) {
		return findByToGroupId(
			toGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the depot entry group rels where toGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param toGroupId the to group ID
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @return the range of matching depot entry group rels
	 */
	@Override
	public List<DepotEntryGroupRel> findByToGroupId(
		long toGroupId, int start, int end) {

		return findByToGroupId(toGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the depot entry group rels where toGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param toGroupId the to group ID
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching depot entry group rels
	 */
	@Override
	public List<DepotEntryGroupRel> findByToGroupId(
		long toGroupId, int start, int end,
		OrderByComparator<DepotEntryGroupRel> orderByComparator) {

		return findByToGroupId(toGroupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the depot entry group rels where toGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param toGroupId the to group ID
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching depot entry group rels
	 */
	@Override
	public List<DepotEntryGroupRel> findByToGroupId(
		long toGroupId, int start, int end,
		OrderByComparator<DepotEntryGroupRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByToGroupId;
				finderArgs = new Object[] {toGroupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByToGroupId;
			finderArgs = new Object[] {
				toGroupId, start, end, orderByComparator
			};
		}

		List<DepotEntryGroupRel> list = null;

		if (useFinderCache) {
			list = (List<DepotEntryGroupRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (DepotEntryGroupRel depotEntryGroupRel : list) {
					if (toGroupId != depotEntryGroupRel.getToGroupId()) {
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

			query.append(_SQL_SELECT_DEPOTENTRYGROUPREL_WHERE);

			query.append(_FINDER_COLUMN_TOGROUPID_TOGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				query.append(DepotEntryGroupRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(toGroupId);

				list = (List<DepotEntryGroupRel>)QueryUtil.list(
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
	 * Returns the first depot entry group rel in the ordered set where toGroupId = &#63;.
	 *
	 * @param toGroupId the to group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a matching depot entry group rel could not be found
	 */
	@Override
	public DepotEntryGroupRel findByToGroupId_First(
			long toGroupId,
			OrderByComparator<DepotEntryGroupRel> orderByComparator)
		throws NoSuchEntryGroupRelException {

		DepotEntryGroupRel depotEntryGroupRel = fetchByToGroupId_First(
			toGroupId, orderByComparator);

		if (depotEntryGroupRel != null) {
			return depotEntryGroupRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("toGroupId=");
		msg.append(toGroupId);

		msg.append("}");

		throw new NoSuchEntryGroupRelException(msg.toString());
	}

	/**
	 * Returns the first depot entry group rel in the ordered set where toGroupId = &#63;.
	 *
	 * @param toGroupId the to group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching depot entry group rel, or <code>null</code> if a matching depot entry group rel could not be found
	 */
	@Override
	public DepotEntryGroupRel fetchByToGroupId_First(
		long toGroupId,
		OrderByComparator<DepotEntryGroupRel> orderByComparator) {

		List<DepotEntryGroupRel> list = findByToGroupId(
			toGroupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last depot entry group rel in the ordered set where toGroupId = &#63;.
	 *
	 * @param toGroupId the to group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a matching depot entry group rel could not be found
	 */
	@Override
	public DepotEntryGroupRel findByToGroupId_Last(
			long toGroupId,
			OrderByComparator<DepotEntryGroupRel> orderByComparator)
		throws NoSuchEntryGroupRelException {

		DepotEntryGroupRel depotEntryGroupRel = fetchByToGroupId_Last(
			toGroupId, orderByComparator);

		if (depotEntryGroupRel != null) {
			return depotEntryGroupRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("toGroupId=");
		msg.append(toGroupId);

		msg.append("}");

		throw new NoSuchEntryGroupRelException(msg.toString());
	}

	/**
	 * Returns the last depot entry group rel in the ordered set where toGroupId = &#63;.
	 *
	 * @param toGroupId the to group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching depot entry group rel, or <code>null</code> if a matching depot entry group rel could not be found
	 */
	@Override
	public DepotEntryGroupRel fetchByToGroupId_Last(
		long toGroupId,
		OrderByComparator<DepotEntryGroupRel> orderByComparator) {

		int count = countByToGroupId(toGroupId);

		if (count == 0) {
			return null;
		}

		List<DepotEntryGroupRel> list = findByToGroupId(
			toGroupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the depot entry group rels before and after the current depot entry group rel in the ordered set where toGroupId = &#63;.
	 *
	 * @param depotEntryGroupRelId the primary key of the current depot entry group rel
	 * @param toGroupId the to group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a depot entry group rel with the primary key could not be found
	 */
	@Override
	public DepotEntryGroupRel[] findByToGroupId_PrevAndNext(
			long depotEntryGroupRelId, long toGroupId,
			OrderByComparator<DepotEntryGroupRel> orderByComparator)
		throws NoSuchEntryGroupRelException {

		DepotEntryGroupRel depotEntryGroupRel = findByPrimaryKey(
			depotEntryGroupRelId);

		Session session = null;

		try {
			session = openSession();

			DepotEntryGroupRel[] array = new DepotEntryGroupRelImpl[3];

			array[0] = getByToGroupId_PrevAndNext(
				session, depotEntryGroupRel, toGroupId, orderByComparator,
				true);

			array[1] = depotEntryGroupRel;

			array[2] = getByToGroupId_PrevAndNext(
				session, depotEntryGroupRel, toGroupId, orderByComparator,
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

	protected DepotEntryGroupRel getByToGroupId_PrevAndNext(
		Session session, DepotEntryGroupRel depotEntryGroupRel, long toGroupId,
		OrderByComparator<DepotEntryGroupRel> orderByComparator,
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

		query.append(_SQL_SELECT_DEPOTENTRYGROUPREL_WHERE);

		query.append(_FINDER_COLUMN_TOGROUPID_TOGROUPID_2);

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
			query.append(DepotEntryGroupRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(toGroupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						depotEntryGroupRel)) {

				qPos.add(orderByConditionValue);
			}
		}

		List<DepotEntryGroupRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the depot entry group rels where toGroupId = &#63; from the database.
	 *
	 * @param toGroupId the to group ID
	 */
	@Override
	public void removeByToGroupId(long toGroupId) {
		for (DepotEntryGroupRel depotEntryGroupRel :
				findByToGroupId(
					toGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(depotEntryGroupRel);
		}
	}

	/**
	 * Returns the number of depot entry group rels where toGroupId = &#63;.
	 *
	 * @param toGroupId the to group ID
	 * @return the number of matching depot entry group rels
	 */
	@Override
	public int countByToGroupId(long toGroupId) {
		FinderPath finderPath = _finderPathCountByToGroupId;

		Object[] finderArgs = new Object[] {toGroupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_DEPOTENTRYGROUPREL_WHERE);

			query.append(_FINDER_COLUMN_TOGROUPID_TOGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(toGroupId);

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

	private static final String _FINDER_COLUMN_TOGROUPID_TOGROUPID_2 =
		"depotEntryGroupRel.toGroupId = ?";

	private FinderPath _finderPathFetchByD_TGI;
	private FinderPath _finderPathCountByD_TGI;

	/**
	 * Returns the depot entry group rel where depotEntryId = &#63; and toGroupId = &#63; or throws a <code>NoSuchEntryGroupRelException</code> if it could not be found.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param toGroupId the to group ID
	 * @return the matching depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a matching depot entry group rel could not be found
	 */
	@Override
	public DepotEntryGroupRel findByD_TGI(long depotEntryId, long toGroupId)
		throws NoSuchEntryGroupRelException {

		DepotEntryGroupRel depotEntryGroupRel = fetchByD_TGI(
			depotEntryId, toGroupId);

		if (depotEntryGroupRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("depotEntryId=");
			msg.append(depotEntryId);

			msg.append(", toGroupId=");
			msg.append(toGroupId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchEntryGroupRelException(msg.toString());
		}

		return depotEntryGroupRel;
	}

	/**
	 * Returns the depot entry group rel where depotEntryId = &#63; and toGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param toGroupId the to group ID
	 * @return the matching depot entry group rel, or <code>null</code> if a matching depot entry group rel could not be found
	 */
	@Override
	public DepotEntryGroupRel fetchByD_TGI(long depotEntryId, long toGroupId) {
		return fetchByD_TGI(depotEntryId, toGroupId, true);
	}

	/**
	 * Returns the depot entry group rel where depotEntryId = &#63; and toGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param toGroupId the to group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching depot entry group rel, or <code>null</code> if a matching depot entry group rel could not be found
	 */
	@Override
	public DepotEntryGroupRel fetchByD_TGI(
		long depotEntryId, long toGroupId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {depotEntryId, toGroupId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByD_TGI, finderArgs, this);
		}

		if (result instanceof DepotEntryGroupRel) {
			DepotEntryGroupRel depotEntryGroupRel = (DepotEntryGroupRel)result;

			if ((depotEntryId != depotEntryGroupRel.getDepotEntryId()) ||
				(toGroupId != depotEntryGroupRel.getToGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_DEPOTENTRYGROUPREL_WHERE);

			query.append(_FINDER_COLUMN_D_TGI_DEPOTENTRYID_2);

			query.append(_FINDER_COLUMN_D_TGI_TOGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(depotEntryId);

				qPos.add(toGroupId);

				List<DepotEntryGroupRel> list = q.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByD_TGI, finderArgs, list);
					}
				}
				else {
					DepotEntryGroupRel depotEntryGroupRel = list.get(0);

					result = depotEntryGroupRel;

					cacheResult(depotEntryGroupRel);
				}
			}
			catch (Exception e) {
				if (useFinderCache) {
					finderCache.removeResult(
						_finderPathFetchByD_TGI, finderArgs);
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
			return (DepotEntryGroupRel)result;
		}
	}

	/**
	 * Removes the depot entry group rel where depotEntryId = &#63; and toGroupId = &#63; from the database.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param toGroupId the to group ID
	 * @return the depot entry group rel that was removed
	 */
	@Override
	public DepotEntryGroupRel removeByD_TGI(long depotEntryId, long toGroupId)
		throws NoSuchEntryGroupRelException {

		DepotEntryGroupRel depotEntryGroupRel = findByD_TGI(
			depotEntryId, toGroupId);

		return remove(depotEntryGroupRel);
	}

	/**
	 * Returns the number of depot entry group rels where depotEntryId = &#63; and toGroupId = &#63;.
	 *
	 * @param depotEntryId the depot entry ID
	 * @param toGroupId the to group ID
	 * @return the number of matching depot entry group rels
	 */
	@Override
	public int countByD_TGI(long depotEntryId, long toGroupId) {
		FinderPath finderPath = _finderPathCountByD_TGI;

		Object[] finderArgs = new Object[] {depotEntryId, toGroupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_DEPOTENTRYGROUPREL_WHERE);

			query.append(_FINDER_COLUMN_D_TGI_DEPOTENTRYID_2);

			query.append(_FINDER_COLUMN_D_TGI_TOGROUPID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(depotEntryId);

				qPos.add(toGroupId);

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

	private static final String _FINDER_COLUMN_D_TGI_DEPOTENTRYID_2 =
		"depotEntryGroupRel.depotEntryId = ? AND ";

	private static final String _FINDER_COLUMN_D_TGI_TOGROUPID_2 =
		"depotEntryGroupRel.toGroupId = ?";

	public DepotEntryGroupRelPersistenceImpl() {
		setModelClass(DepotEntryGroupRel.class);

		setModelImplClass(DepotEntryGroupRelImpl.class);
		setModelPKClass(long.class);
	}

	/**
	 * Caches the depot entry group rel in the entity cache if it is enabled.
	 *
	 * @param depotEntryGroupRel the depot entry group rel
	 */
	@Override
	public void cacheResult(DepotEntryGroupRel depotEntryGroupRel) {
		entityCache.putResult(
			entityCacheEnabled, DepotEntryGroupRelImpl.class,
			depotEntryGroupRel.getPrimaryKey(), depotEntryGroupRel);

		finderCache.putResult(
			_finderPathFetchByD_TGI,
			new Object[] {
				depotEntryGroupRel.getDepotEntryId(),
				depotEntryGroupRel.getToGroupId()
			},
			depotEntryGroupRel);

		depotEntryGroupRel.resetOriginalValues();
	}

	/**
	 * Caches the depot entry group rels in the entity cache if it is enabled.
	 *
	 * @param depotEntryGroupRels the depot entry group rels
	 */
	@Override
	public void cacheResult(List<DepotEntryGroupRel> depotEntryGroupRels) {
		for (DepotEntryGroupRel depotEntryGroupRel : depotEntryGroupRels) {
			if (entityCache.getResult(
					entityCacheEnabled, DepotEntryGroupRelImpl.class,
					depotEntryGroupRel.getPrimaryKey()) == null) {

				cacheResult(depotEntryGroupRel);
			}
			else {
				depotEntryGroupRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all depot entry group rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(DepotEntryGroupRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the depot entry group rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(DepotEntryGroupRel depotEntryGroupRel) {
		entityCache.removeResult(
			entityCacheEnabled, DepotEntryGroupRelImpl.class,
			depotEntryGroupRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(DepotEntryGroupRelModelImpl)depotEntryGroupRel, true);
	}

	@Override
	public void clearCache(List<DepotEntryGroupRel> depotEntryGroupRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (DepotEntryGroupRel depotEntryGroupRel : depotEntryGroupRels) {
			entityCache.removeResult(
				entityCacheEnabled, DepotEntryGroupRelImpl.class,
				depotEntryGroupRel.getPrimaryKey());

			clearUniqueFindersCache(
				(DepotEntryGroupRelModelImpl)depotEntryGroupRel, true);
		}
	}

	protected void cacheUniqueFindersCache(
		DepotEntryGroupRelModelImpl depotEntryGroupRelModelImpl) {

		Object[] args = new Object[] {
			depotEntryGroupRelModelImpl.getDepotEntryId(),
			depotEntryGroupRelModelImpl.getToGroupId()
		};

		finderCache.putResult(
			_finderPathCountByD_TGI, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByD_TGI, args, depotEntryGroupRelModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		DepotEntryGroupRelModelImpl depotEntryGroupRelModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				depotEntryGroupRelModelImpl.getDepotEntryId(),
				depotEntryGroupRelModelImpl.getToGroupId()
			};

			finderCache.removeResult(_finderPathCountByD_TGI, args);
			finderCache.removeResult(_finderPathFetchByD_TGI, args);
		}

		if ((depotEntryGroupRelModelImpl.getColumnBitmask() &
			 _finderPathFetchByD_TGI.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				depotEntryGroupRelModelImpl.getOriginalDepotEntryId(),
				depotEntryGroupRelModelImpl.getOriginalToGroupId()
			};

			finderCache.removeResult(_finderPathCountByD_TGI, args);
			finderCache.removeResult(_finderPathFetchByD_TGI, args);
		}
	}

	/**
	 * Creates a new depot entry group rel with the primary key. Does not add the depot entry group rel to the database.
	 *
	 * @param depotEntryGroupRelId the primary key for the new depot entry group rel
	 * @return the new depot entry group rel
	 */
	@Override
	public DepotEntryGroupRel create(long depotEntryGroupRelId) {
		DepotEntryGroupRel depotEntryGroupRel = new DepotEntryGroupRelImpl();

		depotEntryGroupRel.setNew(true);
		depotEntryGroupRel.setPrimaryKey(depotEntryGroupRelId);

		return depotEntryGroupRel;
	}

	/**
	 * Removes the depot entry group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param depotEntryGroupRelId the primary key of the depot entry group rel
	 * @return the depot entry group rel that was removed
	 * @throws NoSuchEntryGroupRelException if a depot entry group rel with the primary key could not be found
	 */
	@Override
	public DepotEntryGroupRel remove(long depotEntryGroupRelId)
		throws NoSuchEntryGroupRelException {

		return remove((Serializable)depotEntryGroupRelId);
	}

	/**
	 * Removes the depot entry group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the depot entry group rel
	 * @return the depot entry group rel that was removed
	 * @throws NoSuchEntryGroupRelException if a depot entry group rel with the primary key could not be found
	 */
	@Override
	public DepotEntryGroupRel remove(Serializable primaryKey)
		throws NoSuchEntryGroupRelException {

		Session session = null;

		try {
			session = openSession();

			DepotEntryGroupRel depotEntryGroupRel =
				(DepotEntryGroupRel)session.get(
					DepotEntryGroupRelImpl.class, primaryKey);

			if (depotEntryGroupRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchEntryGroupRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(depotEntryGroupRel);
		}
		catch (NoSuchEntryGroupRelException nsee) {
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
	protected DepotEntryGroupRel removeImpl(
		DepotEntryGroupRel depotEntryGroupRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(depotEntryGroupRel)) {
				depotEntryGroupRel = (DepotEntryGroupRel)session.get(
					DepotEntryGroupRelImpl.class,
					depotEntryGroupRel.getPrimaryKeyObj());
			}

			if (depotEntryGroupRel != null) {
				session.delete(depotEntryGroupRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (depotEntryGroupRel != null) {
			clearCache(depotEntryGroupRel);
		}

		return depotEntryGroupRel;
	}

	@Override
	public DepotEntryGroupRel updateImpl(
		DepotEntryGroupRel depotEntryGroupRel) {

		boolean isNew = depotEntryGroupRel.isNew();

		if (!(depotEntryGroupRel instanceof DepotEntryGroupRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(depotEntryGroupRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					depotEntryGroupRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in depotEntryGroupRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom DepotEntryGroupRel implementation " +
					depotEntryGroupRel.getClass());
		}

		DepotEntryGroupRelModelImpl depotEntryGroupRelModelImpl =
			(DepotEntryGroupRelModelImpl)depotEntryGroupRel;

		Session session = null;

		try {
			session = openSession();

			if (depotEntryGroupRel.isNew()) {
				session.save(depotEntryGroupRel);

				depotEntryGroupRel.setNew(false);
			}
			else {
				depotEntryGroupRel = (DepotEntryGroupRel)session.merge(
					depotEntryGroupRel);
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
				depotEntryGroupRelModelImpl.getDepotEntryId()
			};

			finderCache.removeResult(_finderPathCountByDepotEntryId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByDepotEntryId, args);

			args = new Object[] {depotEntryGroupRelModelImpl.getToGroupId()};

			finderCache.removeResult(_finderPathCountByToGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByToGroupId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((depotEntryGroupRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByDepotEntryId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					depotEntryGroupRelModelImpl.getOriginalDepotEntryId()
				};

				finderCache.removeResult(_finderPathCountByDepotEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDepotEntryId, args);

				args = new Object[] {
					depotEntryGroupRelModelImpl.getDepotEntryId()
				};

				finderCache.removeResult(_finderPathCountByDepotEntryId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByDepotEntryId, args);
			}

			if ((depotEntryGroupRelModelImpl.getColumnBitmask() &
				 _finderPathWithoutPaginationFindByToGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					depotEntryGroupRelModelImpl.getOriginalToGroupId()
				};

				finderCache.removeResult(_finderPathCountByToGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByToGroupId, args);

				args = new Object[] {
					depotEntryGroupRelModelImpl.getToGroupId()
				};

				finderCache.removeResult(_finderPathCountByToGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByToGroupId, args);
			}
		}

		entityCache.putResult(
			entityCacheEnabled, DepotEntryGroupRelImpl.class,
			depotEntryGroupRel.getPrimaryKey(), depotEntryGroupRel, false);

		clearUniqueFindersCache(depotEntryGroupRelModelImpl, false);
		cacheUniqueFindersCache(depotEntryGroupRelModelImpl);

		depotEntryGroupRel.resetOriginalValues();

		return depotEntryGroupRel;
	}

	/**
	 * Returns the depot entry group rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the depot entry group rel
	 * @return the depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a depot entry group rel with the primary key could not be found
	 */
	@Override
	public DepotEntryGroupRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchEntryGroupRelException {

		DepotEntryGroupRel depotEntryGroupRel = fetchByPrimaryKey(primaryKey);

		if (depotEntryGroupRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchEntryGroupRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return depotEntryGroupRel;
	}

	/**
	 * Returns the depot entry group rel with the primary key or throws a <code>NoSuchEntryGroupRelException</code> if it could not be found.
	 *
	 * @param depotEntryGroupRelId the primary key of the depot entry group rel
	 * @return the depot entry group rel
	 * @throws NoSuchEntryGroupRelException if a depot entry group rel with the primary key could not be found
	 */
	@Override
	public DepotEntryGroupRel findByPrimaryKey(long depotEntryGroupRelId)
		throws NoSuchEntryGroupRelException {

		return findByPrimaryKey((Serializable)depotEntryGroupRelId);
	}

	/**
	 * Returns the depot entry group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param depotEntryGroupRelId the primary key of the depot entry group rel
	 * @return the depot entry group rel, or <code>null</code> if a depot entry group rel with the primary key could not be found
	 */
	@Override
	public DepotEntryGroupRel fetchByPrimaryKey(long depotEntryGroupRelId) {
		return fetchByPrimaryKey((Serializable)depotEntryGroupRelId);
	}

	/**
	 * Returns all the depot entry group rels.
	 *
	 * @return the depot entry group rels
	 */
	@Override
	public List<DepotEntryGroupRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the depot entry group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @return the range of depot entry group rels
	 */
	@Override
	public List<DepotEntryGroupRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the depot entry group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of depot entry group rels
	 */
	@Override
	public List<DepotEntryGroupRel> findAll(
		int start, int end,
		OrderByComparator<DepotEntryGroupRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the depot entry group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>DepotEntryGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of depot entry group rels
	 * @param end the upper bound of the range of depot entry group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of depot entry group rels
	 */
	@Override
	public List<DepotEntryGroupRel> findAll(
		int start, int end,
		OrderByComparator<DepotEntryGroupRel> orderByComparator,
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

		List<DepotEntryGroupRel> list = null;

		if (useFinderCache) {
			list = (List<DepotEntryGroupRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_DEPOTENTRYGROUPREL);

				appendOrderByComparator(
					query, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_DEPOTENTRYGROUPREL;

				sql = sql.concat(DepotEntryGroupRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				list = (List<DepotEntryGroupRel>)QueryUtil.list(
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
	 * Removes all the depot entry group rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (DepotEntryGroupRel depotEntryGroupRel : findAll()) {
			remove(depotEntryGroupRel);
		}
	}

	/**
	 * Returns the number of depot entry group rels.
	 *
	 * @return the number of depot entry group rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_DEPOTENTRYGROUPREL);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "depotEntryGroupRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_DEPOTENTRYGROUPREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return DepotEntryGroupRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the depot entry group rel persistence.
	 */
	@Activate
	public void activate() {
		DepotEntryGroupRelModelImpl.setEntityCacheEnabled(entityCacheEnabled);
		DepotEntryGroupRelModelImpl.setFinderCacheEnabled(finderCacheEnabled);

		_finderPathWithPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DepotEntryGroupRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DepotEntryGroupRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByDepotEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DepotEntryGroupRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByDepotEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByDepotEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DepotEntryGroupRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByDepotEntryId",
			new String[] {Long.class.getName()},
			DepotEntryGroupRelModelImpl.DEPOTENTRYID_COLUMN_BITMASK);

		_finderPathCountByDepotEntryId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByDepotEntryId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByToGroupId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DepotEntryGroupRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByToGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByToGroupId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DepotEntryGroupRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByToGroupId",
			new String[] {Long.class.getName()},
			DepotEntryGroupRelModelImpl.TOGROUPID_COLUMN_BITMASK);

		_finderPathCountByToGroupId = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByToGroupId",
			new String[] {Long.class.getName()});

		_finderPathFetchByD_TGI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled,
			DepotEntryGroupRelImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByD_TGI",
			new String[] {Long.class.getName(), Long.class.getName()},
			DepotEntryGroupRelModelImpl.DEPOTENTRYID_COLUMN_BITMASK |
			DepotEntryGroupRelModelImpl.TOGROUPID_COLUMN_BITMASK);

		_finderPathCountByD_TGI = new FinderPath(
			entityCacheEnabled, finderCacheEnabled, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByD_TGI",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(DepotEntryGroupRelImpl.class.getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	@Reference(
		target = DepotPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
		super.setConfiguration(configuration);

		_columnBitmaskEnabled = GetterUtil.getBoolean(
			configuration.get(
				"value.object.column.bitmask.enabled.com.liferay.depot.model.DepotEntryGroupRel"),
			true);
	}

	@Override
	@Reference(
		target = DepotPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = DepotPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
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

	private static final String _SQL_SELECT_DEPOTENTRYGROUPREL =
		"SELECT depotEntryGroupRel FROM DepotEntryGroupRel depotEntryGroupRel";

	private static final String _SQL_SELECT_DEPOTENTRYGROUPREL_WHERE =
		"SELECT depotEntryGroupRel FROM DepotEntryGroupRel depotEntryGroupRel WHERE ";

	private static final String _SQL_COUNT_DEPOTENTRYGROUPREL =
		"SELECT COUNT(depotEntryGroupRel) FROM DepotEntryGroupRel depotEntryGroupRel";

	private static final String _SQL_COUNT_DEPOTENTRYGROUPREL_WHERE =
		"SELECT COUNT(depotEntryGroupRel) FROM DepotEntryGroupRel depotEntryGroupRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "depotEntryGroupRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No DepotEntryGroupRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No DepotEntryGroupRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		DepotEntryGroupRelPersistenceImpl.class);

	static {
		try {
			Class.forName(DepotPersistenceConstants.class.getName());
		}
		catch (ClassNotFoundException cnfe) {
			throw new ExceptionInInitializerError(cnfe);
		}
	}

}