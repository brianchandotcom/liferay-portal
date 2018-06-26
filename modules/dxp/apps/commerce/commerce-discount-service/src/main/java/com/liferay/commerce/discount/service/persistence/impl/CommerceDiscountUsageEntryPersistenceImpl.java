/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of the Liferay Enterprise
 * Subscription License ("License"). You may not use this file except in
 * compliance with the License. You can obtain a copy of the License by
 * contacting Liferay, Inc. See the License for the specific language governing
 * permissions and limitations under the License, including but not limited to
 * distribution rights of the Software.
 *
 *
 *
 */

package com.liferay.commerce.discount.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.discount.exception.NoSuchDiscountUsageEntryException;
import com.liferay.commerce.discount.model.CommerceDiscountUsageEntry;
import com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountUsageEntryModelImpl;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountUsageEntryPersistence;

import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.CompanyProvider;
import com.liferay.portal.kernel.service.persistence.CompanyProviderWrapper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce discount usage entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @see CommerceDiscountUsageEntryPersistence
 * @see com.liferay.commerce.discount.service.persistence.CommerceDiscountUsageEntryUtil
 * @generated
 */
@ProviderType
public class CommerceDiscountUsageEntryPersistenceImpl
	extends BasePersistenceImpl<CommerceDiscountUsageEntry>
	implements CommerceDiscountUsageEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceDiscountUsageEntryUtil} to access the commerce discount usage entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceDiscountUsageEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceDiscountUsageEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceDiscountUsageEntryModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce discount usage entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount usage entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @return the range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID;
			finderArgs = new Object[] { groupId, start, end, orderByComparator };
		}

		List<CommerceDiscountUsageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceDiscountUsageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountUsageEntry commerceDiscountUsageEntry : list) {
					if ((groupId != commerceDiscountUsageEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceDiscountUsageEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceDiscountUsageEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceDiscountUsageEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce discount usage entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByGroupId_First(long groupId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {
		CommerceDiscountUsageEntry commerceDiscountUsageEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceDiscountUsageEntry != null) {
			return commerceDiscountUsageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchDiscountUsageEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce discount usage entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {
		List<CommerceDiscountUsageEntry> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByGroupId_Last(long groupId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {
		CommerceDiscountUsageEntry commerceDiscountUsageEntry = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceDiscountUsageEntry != null) {
			return commerceDiscountUsageEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchDiscountUsageEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce discount usage entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount usage entry, or <code>null</code> if a matching commerce discount usage entry could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountUsageEntry> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount usage entries before and after the current commerce discount usage entry in the ordered set where groupId = &#63;.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the current commerce discount usage entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry[] findByGroupId_PrevAndNext(
		long commerceDiscountUsageEntryId, long groupId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator)
		throws NoSuchDiscountUsageEntryException {
		CommerceDiscountUsageEntry commerceDiscountUsageEntry = findByPrimaryKey(commerceDiscountUsageEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountUsageEntry[] array = new CommerceDiscountUsageEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceDiscountUsageEntry, groupId, orderByComparator, true);

			array[1] = commerceDiscountUsageEntry;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceDiscountUsageEntry, groupId, orderByComparator,
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

	protected CommerceDiscountUsageEntry getByGroupId_PrevAndNext(
		Session session, CommerceDiscountUsageEntry commerceDiscountUsageEntry,
		long groupId,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

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
			query.append(CommerceDiscountUsageEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceDiscountUsageEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceDiscountUsageEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount usage entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceDiscountUsageEntry);
		}
	}

	/**
	 * Returns the number of commerce discount usage entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce discount usage entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceDiscountUsageEntry.groupId = ?";

	public CommerceDiscountUsageEntryPersistenceImpl() {
		setModelClass(CommerceDiscountUsageEntry.class);
	}

	/**
	 * Caches the commerce discount usage entry in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountUsageEntry the commerce discount usage entry
	 */
	@Override
	public void cacheResult(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
		entityCache.putResult(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			commerceDiscountUsageEntry.getPrimaryKey(),
			commerceDiscountUsageEntry);

		commerceDiscountUsageEntry.resetOriginalValues();
	}

	/**
	 * Caches the commerce discount usage entries in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountUsageEntries the commerce discount usage entries
	 */
	@Override
	public void cacheResult(
		List<CommerceDiscountUsageEntry> commerceDiscountUsageEntries) {
		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry : commerceDiscountUsageEntries) {
			if (entityCache.getResult(
						CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceDiscountUsageEntryImpl.class,
						commerceDiscountUsageEntry.getPrimaryKey()) == null) {
				cacheResult(commerceDiscountUsageEntry);
			}
			else {
				commerceDiscountUsageEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce discount usage entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceDiscountUsageEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce discount usage entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
		entityCache.removeResult(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			commerceDiscountUsageEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceDiscountUsageEntry> commerceDiscountUsageEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry : commerceDiscountUsageEntries) {
			entityCache.removeResult(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDiscountUsageEntryImpl.class,
				commerceDiscountUsageEntry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce discount usage entry with the primary key. Does not add the commerce discount usage entry to the database.
	 *
	 * @param commerceDiscountUsageEntryId the primary key for the new commerce discount usage entry
	 * @return the new commerce discount usage entry
	 */
	@Override
	public CommerceDiscountUsageEntry create(long commerceDiscountUsageEntryId) {
		CommerceDiscountUsageEntry commerceDiscountUsageEntry = new CommerceDiscountUsageEntryImpl();

		commerceDiscountUsageEntry.setNew(true);
		commerceDiscountUsageEntry.setPrimaryKey(commerceDiscountUsageEntryId);

		commerceDiscountUsageEntry.setCompanyId(companyProvider.getCompanyId());

		return commerceDiscountUsageEntry;
	}

	/**
	 * Removes the commerce discount usage entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry that was removed
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry remove(long commerceDiscountUsageEntryId)
		throws NoSuchDiscountUsageEntryException {
		return remove((Serializable)commerceDiscountUsageEntryId);
	}

	/**
	 * Removes the commerce discount usage entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry that was removed
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry remove(Serializable primaryKey)
		throws NoSuchDiscountUsageEntryException {
		Session session = null;

		try {
			session = openSession();

			CommerceDiscountUsageEntry commerceDiscountUsageEntry = (CommerceDiscountUsageEntry)session.get(CommerceDiscountUsageEntryImpl.class,
					primaryKey);

			if (commerceDiscountUsageEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDiscountUsageEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceDiscountUsageEntry);
		}
		catch (NoSuchDiscountUsageEntryException nsee) {
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
	protected CommerceDiscountUsageEntry removeImpl(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceDiscountUsageEntry)) {
				commerceDiscountUsageEntry = (CommerceDiscountUsageEntry)session.get(CommerceDiscountUsageEntryImpl.class,
						commerceDiscountUsageEntry.getPrimaryKeyObj());
			}

			if (commerceDiscountUsageEntry != null) {
				session.delete(commerceDiscountUsageEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceDiscountUsageEntry != null) {
			clearCache(commerceDiscountUsageEntry);
		}

		return commerceDiscountUsageEntry;
	}

	@Override
	public CommerceDiscountUsageEntry updateImpl(
		CommerceDiscountUsageEntry commerceDiscountUsageEntry) {
		boolean isNew = commerceDiscountUsageEntry.isNew();

		if (!(commerceDiscountUsageEntry instanceof CommerceDiscountUsageEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceDiscountUsageEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceDiscountUsageEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceDiscountUsageEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceDiscountUsageEntry implementation " +
				commerceDiscountUsageEntry.getClass());
		}

		CommerceDiscountUsageEntryModelImpl commerceDiscountUsageEntryModelImpl = (CommerceDiscountUsageEntryModelImpl)commerceDiscountUsageEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceDiscountUsageEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceDiscountUsageEntry.setCreateDate(now);
			}
			else {
				commerceDiscountUsageEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceDiscountUsageEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceDiscountUsageEntry.setModifiedDate(now);
			}
			else {
				commerceDiscountUsageEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceDiscountUsageEntry.isNew()) {
				session.save(commerceDiscountUsageEntry);

				commerceDiscountUsageEntry.setNew(false);
			}
			else {
				commerceDiscountUsageEntry = (CommerceDiscountUsageEntry)session.merge(commerceDiscountUsageEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceDiscountUsageEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceDiscountUsageEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceDiscountUsageEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceDiscountUsageEntryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						commerceDiscountUsageEntryModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}
		}

		entityCache.putResult(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceDiscountUsageEntryImpl.class,
			commerceDiscountUsageEntry.getPrimaryKey(),
			commerceDiscountUsageEntry, false);

		commerceDiscountUsageEntry.resetOriginalValues();

		return commerceDiscountUsageEntry;
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDiscountUsageEntryException {
		CommerceDiscountUsageEntry commerceDiscountUsageEntry = fetchByPrimaryKey(primaryKey);

		if (commerceDiscountUsageEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDiscountUsageEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceDiscountUsageEntry;
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or throws a {@link NoSuchDiscountUsageEntryException} if it could not be found.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry
	 * @throws NoSuchDiscountUsageEntryException if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry findByPrimaryKey(
		long commerceDiscountUsageEntryId)
		throws NoSuchDiscountUsageEntryException {
		return findByPrimaryKey((Serializable)commerceDiscountUsageEntryId);
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry, or <code>null</code> if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceDiscountUsageEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceDiscountUsageEntry commerceDiscountUsageEntry = (CommerceDiscountUsageEntry)serializable;

		if (commerceDiscountUsageEntry == null) {
			Session session = null;

			try {
				session = openSession();

				commerceDiscountUsageEntry = (CommerceDiscountUsageEntry)session.get(CommerceDiscountUsageEntryImpl.class,
						primaryKey);

				if (commerceDiscountUsageEntry != null) {
					cacheResult(commerceDiscountUsageEntry);
				}
				else {
					entityCache.putResult(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceDiscountUsageEntryImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountUsageEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceDiscountUsageEntry;
	}

	/**
	 * Returns the commerce discount usage entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountUsageEntryId the primary key of the commerce discount usage entry
	 * @return the commerce discount usage entry, or <code>null</code> if a commerce discount usage entry with the primary key could not be found
	 */
	@Override
	public CommerceDiscountUsageEntry fetchByPrimaryKey(
		long commerceDiscountUsageEntryId) {
		return fetchByPrimaryKey((Serializable)commerceDiscountUsageEntryId);
	}

	@Override
	public Map<Serializable, CommerceDiscountUsageEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceDiscountUsageEntry> map = new HashMap<Serializable, CommerceDiscountUsageEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceDiscountUsageEntry commerceDiscountUsageEntry = fetchByPrimaryKey(primaryKey);

			if (commerceDiscountUsageEntry != null) {
				map.put(primaryKey, commerceDiscountUsageEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountUsageEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceDiscountUsageEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE_PKS_IN);

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

			for (CommerceDiscountUsageEntry commerceDiscountUsageEntry : (List<CommerceDiscountUsageEntry>)q.list()) {
				map.put(commerceDiscountUsageEntry.getPrimaryKeyObj(),
					commerceDiscountUsageEntry);

				cacheResult(commerceDiscountUsageEntry);

				uncachedPrimaryKeys.remove(commerceDiscountUsageEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceDiscountUsageEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceDiscountUsageEntryImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce discount usage entries.
	 *
	 * @return the commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount usage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @return the range of commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findAll(int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount usage entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceDiscountUsageEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount usage entries
	 * @param end the upper bound of the range of commerce discount usage entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce discount usage entries
	 */
	@Override
	public List<CommerceDiscountUsageEntry> findAll(int start, int end,
		OrderByComparator<CommerceDiscountUsageEntry> orderByComparator,
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

		List<CommerceDiscountUsageEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceDiscountUsageEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY;

				if (pagination) {
					sql = sql.concat(CommerceDiscountUsageEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceDiscountUsageEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceDiscountUsageEntry>)QueryUtil.list(q,
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
	 * Removes all the commerce discount usage entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceDiscountUsageEntry commerceDiscountUsageEntry : findAll()) {
			remove(commerceDiscountUsageEntry);
		}
	}

	/**
	 * Returns the number of commerce discount usage entries.
	 *
	 * @return the number of commerce discount usage entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY);

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
		return CommerceDiscountUsageEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce discount usage entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceDiscountUsageEntryImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY = "SELECT commerceDiscountUsageEntry FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry";
	private static final String _SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE_PKS_IN =
		"SELECT commerceDiscountUsageEntry FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry WHERE commerceDiscountUsageEntryId IN (";
	private static final String _SQL_SELECT_COMMERCEDISCOUNTUSAGEENTRY_WHERE = "SELECT commerceDiscountUsageEntry FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry WHERE ";
	private static final String _SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY = "SELECT COUNT(commerceDiscountUsageEntry) FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry";
	private static final String _SQL_COUNT_COMMERCEDISCOUNTUSAGEENTRY_WHERE = "SELECT COUNT(commerceDiscountUsageEntry) FROM CommerceDiscountUsageEntry commerceDiscountUsageEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceDiscountUsageEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceDiscountUsageEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceDiscountUsageEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceDiscountUsageEntryPersistenceImpl.class);
}