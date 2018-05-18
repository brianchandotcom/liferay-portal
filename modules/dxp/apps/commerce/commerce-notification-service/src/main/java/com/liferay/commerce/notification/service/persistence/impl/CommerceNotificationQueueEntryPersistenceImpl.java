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

package com.liferay.commerce.notification.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.exception.NoSuchNotificationQueueEntryException;
import com.liferay.commerce.notification.model.CommerceNotificationQueueEntry;
import com.liferay.commerce.notification.model.impl.CommerceNotificationQueueEntryImpl;
import com.liferay.commerce.notification.model.impl.CommerceNotificationQueueEntryModelImpl;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationQueueEntryPersistence;

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
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce notification queue entry service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationQueueEntryPersistence
 * @see com.liferay.commerce.notification.service.persistence.CommerceNotificationQueueEntryUtil
 * @generated
 */
@ProviderType
public class CommerceNotificationQueueEntryPersistenceImpl
	extends BasePersistenceImpl<CommerceNotificationQueueEntry>
	implements CommerceNotificationQueueEntryPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceNotificationQueueEntryUtil} to access the commerce notification queue entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceNotificationQueueEntryImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationQueueEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationQueueEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationQueueEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationQueueEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceNotificationQueueEntryModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceNotificationQueueEntryModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByGroupId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce notification queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByGroupId(long groupId,
		int start, int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByGroupId(long groupId,
		int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		List<CommerceNotificationQueueEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationQueueEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationQueueEntry commerceNotificationQueueEntry : list) {
					if ((groupId != commerceNotificationQueueEntry.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByGroupId_First(long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchNotificationQueueEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		List<CommerceNotificationQueueEntry> list = findByGroupId(groupId, 0,
				1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByGroupId_Last(long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchNotificationQueueEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationQueueEntry> list = findByGroupId(groupId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where groupId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry[] findByGroupId_PrevAndNext(
		long commerceNotificationQueueEntryId, long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = findByPrimaryKey(commerceNotificationQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationQueueEntry[] array = new CommerceNotificationQueueEntryImpl[3];

			array[0] = getByGroupId_PrevAndNext(session,
					commerceNotificationQueueEntry, groupId, orderByComparator,
					true);

			array[1] = commerceNotificationQueueEntry;

			array[2] = getByGroupId_PrevAndNext(session,
					commerceNotificationQueueEntry, groupId, orderByComparator,
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

	protected CommerceNotificationQueueEntry getByGroupId_PrevAndNext(
		Session session,
		CommerceNotificationQueueEntry commerceNotificationQueueEntry,
		long groupId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

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
			query.append(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceNotificationQueueEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceNotificationQueueEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification queue entries where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry : findByGroupId(
				groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceNotificationQueueEntry);
		}
	}

	/**
	 * Returns the number of commerce notification queue entries where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce notification queue entries
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceNotificationQueueEntry.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID =
		new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationQueueEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceNotificationTemplateId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID =
		new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationQueueEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceNotificationTemplateId",
			new String[] { Long.class.getName() },
			CommerceNotificationQueueEntryModelImpl.COMMERCENOTIFICATIONTEMPLATEID_COLUMN_BITMASK |
			CommerceNotificationQueueEntryModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONTEMPLATEID =
		new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceNotificationTemplateId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		return findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end) {
		return findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID;
			finderArgs = new Object[] { commerceNotificationTemplateId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID;
			finderArgs = new Object[] {
					commerceNotificationTemplateId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceNotificationQueueEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationQueueEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationQueueEntry commerceNotificationQueueEntry : list) {
					if ((commerceNotificationTemplateId != commerceNotificationQueueEntry.getCommerceNotificationTemplateId())) {
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

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMMERCENOTIFICATIONTEMPLATEID_COMMERCENOTIFICATIONTEMPLATEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceNotificationTemplateId);

				if (!pagination) {
					list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByCommerceNotificationTemplateId_First(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = fetchByCommerceNotificationTemplateId_First(commerceNotificationTemplateId,
				orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceNotificationTemplateId=");
		msg.append(commerceNotificationTemplateId);

		msg.append("}");

		throw new NoSuchNotificationQueueEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByCommerceNotificationTemplateId_First(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		List<CommerceNotificationQueueEntry> list = findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByCommerceNotificationTemplateId_Last(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = fetchByCommerceNotificationTemplateId_Last(commerceNotificationTemplateId,
				orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceNotificationTemplateId=");
		msg.append(commerceNotificationTemplateId);

		msg.append("}");

		throw new NoSuchNotificationQueueEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByCommerceNotificationTemplateId_Last(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		int count = countByCommerceNotificationTemplateId(commerceNotificationTemplateId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationQueueEntry> list = findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry[] findByCommerceNotificationTemplateId_PrevAndNext(
		long commerceNotificationQueueEntryId,
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = findByPrimaryKey(commerceNotificationQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationQueueEntry[] array = new CommerceNotificationQueueEntryImpl[3];

			array[0] = getByCommerceNotificationTemplateId_PrevAndNext(session,
					commerceNotificationQueueEntry,
					commerceNotificationTemplateId, orderByComparator, true);

			array[1] = commerceNotificationQueueEntry;

			array[2] = getByCommerceNotificationTemplateId_PrevAndNext(session,
					commerceNotificationQueueEntry,
					commerceNotificationTemplateId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceNotificationQueueEntry getByCommerceNotificationTemplateId_PrevAndNext(
		Session session,
		CommerceNotificationQueueEntry commerceNotificationQueueEntry,
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

		query.append(_FINDER_COLUMN_COMMERCENOTIFICATIONTEMPLATEID_COMMERCENOTIFICATIONTEMPLATEID_2);

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
			query.append(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceNotificationTemplateId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceNotificationQueueEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceNotificationQueueEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification queue entries where commerceNotificationTemplateId = &#63; from the database.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 */
	@Override
	public void removeByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry : findByCommerceNotificationTemplateId(
				commerceNotificationTemplateId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceNotificationQueueEntry);
		}
	}

	/**
	 * Returns the number of commerce notification queue entries where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the number of matching commerce notification queue entries
	 */
	@Override
	public int countByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONTEMPLATEID;

		Object[] finderArgs = new Object[] { commerceNotificationTemplateId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

			query.append(_FINDER_COLUMN_COMMERCENOTIFICATIONTEMPLATEID_COMMERCENOTIFICATIONTEMPLATEID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceNotificationTemplateId);

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

	private static final String _FINDER_COLUMN_COMMERCENOTIFICATIONTEMPLATEID_COMMERCENOTIFICATIONTEMPLATEID_2 =
		"commerceNotificationQueueEntry.commerceNotificationTemplateId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_SENT = new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationQueueEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findBySent",
			new String[] {
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SENT = new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationQueueEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findBySent",
			new String[] { Boolean.class.getName() },
			CommerceNotificationQueueEntryModelImpl.SENT_COLUMN_BITMASK |
			CommerceNotificationQueueEntryModelImpl.PRIORITY_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_SENT = new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countBySent", new String[] { Boolean.class.getName() });

	/**
	 * Returns all the commerce notification queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findBySent(boolean sent) {
		return findBySent(sent, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findBySent(boolean sent,
		int start, int end) {
		return findBySent(sent, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findBySent(boolean sent,
		int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return findBySent(sent, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sent = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sent the sent
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findBySent(boolean sent,
		int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SENT;
			finderArgs = new Object[] { sent };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_SENT;
			finderArgs = new Object[] { sent, start, end, orderByComparator };
		}

		List<CommerceNotificationQueueEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationQueueEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationQueueEntry commerceNotificationQueueEntry : list) {
					if ((sent != commerceNotificationQueueEntry.isSent())) {
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

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

			query.append(_FINDER_COLUMN_SENT_SENT_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(sent);

				if (!pagination) {
					list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findBySent_First(boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = fetchBySent_First(sent,
				orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("sent=");
		msg.append(sent);

		msg.append("}");

		throw new NoSuchNotificationQueueEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchBySent_First(boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		List<CommerceNotificationQueueEntry> list = findBySent(sent, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findBySent_Last(boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = fetchBySent_Last(sent,
				orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("sent=");
		msg.append(sent);

		msg.append("}");

		throw new NoSuchNotificationQueueEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchBySent_Last(boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		int count = countBySent(sent);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationQueueEntry> list = findBySent(sent, count - 1,
				count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where sent = &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param sent the sent
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry[] findBySent_PrevAndNext(
		long commerceNotificationQueueEntryId, boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = findByPrimaryKey(commerceNotificationQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationQueueEntry[] array = new CommerceNotificationQueueEntryImpl[3];

			array[0] = getBySent_PrevAndNext(session,
					commerceNotificationQueueEntry, sent, orderByComparator,
					true);

			array[1] = commerceNotificationQueueEntry;

			array[2] = getBySent_PrevAndNext(session,
					commerceNotificationQueueEntry, sent, orderByComparator,
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

	protected CommerceNotificationQueueEntry getBySent_PrevAndNext(
		Session session,
		CommerceNotificationQueueEntry commerceNotificationQueueEntry,
		boolean sent,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

		query.append(_FINDER_COLUMN_SENT_SENT_2);

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
			query.append(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(sent);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceNotificationQueueEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceNotificationQueueEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification queue entries where sent = &#63; from the database.
	 *
	 * @param sent the sent
	 */
	@Override
	public void removeBySent(boolean sent) {
		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry : findBySent(
				sent, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceNotificationQueueEntry);
		}
	}

	/**
	 * Returns the number of commerce notification queue entries where sent = &#63;.
	 *
	 * @param sent the sent
	 * @return the number of matching commerce notification queue entries
	 */
	@Override
	public int countBySent(boolean sent) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_SENT;

		Object[] finderArgs = new Object[] { sent };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

			query.append(_FINDER_COLUMN_SENT_SENT_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(sent);

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

	private static final String _FINDER_COLUMN_SENT_SENT_2 = "commerceNotificationQueueEntry.sent = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_LTS = new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationQueueEntryImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtS",
			new String[] {
				Date.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_COUNT_BY_LTS = new FinderPath(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtS",
			new String[] { Date.class.getName() });

	/**
	 * Returns all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByLtS(Date sentDate) {
		return findByLtS(sentDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByLtS(Date sentDate,
		int start, int end) {
		return findByLtS(sentDate, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByLtS(Date sentDate,
		int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return findByLtS(sentDate, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param sentDate the sent date
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findByLtS(Date sentDate,
		int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_LTS;
		finderArgs = new Object[] {
				_getTime(sentDate),
				
				start, end, orderByComparator
			};

		List<CommerceNotificationQueueEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationQueueEntry>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationQueueEntry commerceNotificationQueueEntry : list) {
					if ((sentDate.getTime() <= commerceNotificationQueueEntry.getSentDate()
																				 .getTime())) {
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

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

			boolean bindSentDate = false;

			if (sentDate == null) {
				query.append(_FINDER_COLUMN_LTS_SENTDATE_1);
			}
			else {
				bindSentDate = true;

				query.append(_FINDER_COLUMN_LTS_SENTDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSentDate) {
					qPos.add(new Timestamp(sentDate.getTime()));
				}

				if (!pagination) {
					list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(q,
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
	 * Returns the first commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByLtS_First(Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = fetchByLtS_First(sentDate,
				orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("sentDate=");
		msg.append(sentDate);

		msg.append("}");

		throw new NoSuchNotificationQueueEntryException(msg.toString());
	}

	/**
	 * Returns the first commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByLtS_First(Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		List<CommerceNotificationQueueEntry> list = findByLtS(sentDate, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByLtS_Last(Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = fetchByLtS_Last(sentDate,
				orderByComparator);

		if (commerceNotificationQueueEntry != null) {
			return commerceNotificationQueueEntry;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("sentDate=");
		msg.append(sentDate);

		msg.append("}");

		throw new NoSuchNotificationQueueEntryException(msg.toString());
	}

	/**
	 * Returns the last commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification queue entry, or <code>null</code> if a matching commerce notification queue entry could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByLtS_Last(Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		int count = countByLtS(sentDate);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationQueueEntry> list = findByLtS(sentDate,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification queue entries before and after the current commerce notification queue entry in the ordered set where sentDate &lt; &#63;.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the current commerce notification queue entry
	 * @param sentDate the sent date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry[] findByLtS_PrevAndNext(
		long commerceNotificationQueueEntryId, Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator)
		throws NoSuchNotificationQueueEntryException {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = findByPrimaryKey(commerceNotificationQueueEntryId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationQueueEntry[] array = new CommerceNotificationQueueEntryImpl[3];

			array[0] = getByLtS_PrevAndNext(session,
					commerceNotificationQueueEntry, sentDate,
					orderByComparator, true);

			array[1] = commerceNotificationQueueEntry;

			array[2] = getByLtS_PrevAndNext(session,
					commerceNotificationQueueEntry, sentDate,
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

	protected CommerceNotificationQueueEntry getByLtS_PrevAndNext(
		Session session,
		CommerceNotificationQueueEntry commerceNotificationQueueEntry,
		Date sentDate,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

		boolean bindSentDate = false;

		if (sentDate == null) {
			query.append(_FINDER_COLUMN_LTS_SENTDATE_1);
		}
		else {
			bindSentDate = true;

			query.append(_FINDER_COLUMN_LTS_SENTDATE_2);
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
			query.append(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		if (bindSentDate) {
			qPos.add(new Timestamp(sentDate.getTime()));
		}

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceNotificationQueueEntry);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceNotificationQueueEntry> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification queue entries where sentDate &lt; &#63; from the database.
	 *
	 * @param sentDate the sent date
	 */
	@Override
	public void removeByLtS(Date sentDate) {
		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry : findByLtS(
				sentDate, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceNotificationQueueEntry);
		}
	}

	/**
	 * Returns the number of commerce notification queue entries where sentDate &lt; &#63;.
	 *
	 * @param sentDate the sent date
	 * @return the number of matching commerce notification queue entries
	 */
	@Override
	public int countByLtS(Date sentDate) {
		FinderPath finderPath = FINDER_PATH_WITH_PAGINATION_COUNT_BY_LTS;

		Object[] finderArgs = new Object[] { _getTime(sentDate) };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE);

			boolean bindSentDate = false;

			if (sentDate == null) {
				query.append(_FINDER_COLUMN_LTS_SENTDATE_1);
			}
			else {
				bindSentDate = true;

				query.append(_FINDER_COLUMN_LTS_SENTDATE_2);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				if (bindSentDate) {
					qPos.add(new Timestamp(sentDate.getTime()));
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

	private static final String _FINDER_COLUMN_LTS_SENTDATE_1 = "commerceNotificationQueueEntry.sentDate IS NULL";
	private static final String _FINDER_COLUMN_LTS_SENTDATE_2 = "commerceNotificationQueueEntry.sentDate < ?";

	public CommerceNotificationQueueEntryPersistenceImpl() {
		setModelClass(CommerceNotificationQueueEntry.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("commerceNotificationQueueEntryId",
				"CNotificationQueueEntryId");
			dbColumnNames.put("from", "from_");
			dbColumnNames.put("to", "to_");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce notification queue entry in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationQueueEntry the commerce notification queue entry
	 */
	@Override
	public void cacheResult(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {
		entityCache.putResult(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryImpl.class,
			commerceNotificationQueueEntry.getPrimaryKey(),
			commerceNotificationQueueEntry);

		commerceNotificationQueueEntry.resetOriginalValues();
	}

	/**
	 * Caches the commerce notification queue entries in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationQueueEntries the commerce notification queue entries
	 */
	@Override
	public void cacheResult(
		List<CommerceNotificationQueueEntry> commerceNotificationQueueEntries) {
		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry : commerceNotificationQueueEntries) {
			if (entityCache.getResult(
						CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceNotificationQueueEntryImpl.class,
						commerceNotificationQueueEntry.getPrimaryKey()) == null) {
				cacheResult(commerceNotificationQueueEntry);
			}
			else {
				commerceNotificationQueueEntry.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce notification queue entries.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceNotificationQueueEntryImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce notification queue entry.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {
		entityCache.removeResult(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryImpl.class,
			commerceNotificationQueueEntry.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(
		List<CommerceNotificationQueueEntry> commerceNotificationQueueEntries) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry : commerceNotificationQueueEntries) {
			entityCache.removeResult(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceNotificationQueueEntryImpl.class,
				commerceNotificationQueueEntry.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce notification queue entry with the primary key. Does not add the commerce notification queue entry to the database.
	 *
	 * @param commerceNotificationQueueEntryId the primary key for the new commerce notification queue entry
	 * @return the new commerce notification queue entry
	 */
	@Override
	public CommerceNotificationQueueEntry create(
		long commerceNotificationQueueEntryId) {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = new CommerceNotificationQueueEntryImpl();

		commerceNotificationQueueEntry.setNew(true);
		commerceNotificationQueueEntry.setPrimaryKey(commerceNotificationQueueEntryId);

		commerceNotificationQueueEntry.setCompanyId(companyProvider.getCompanyId());

		return commerceNotificationQueueEntry;
	}

	/**
	 * Removes the commerce notification queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry that was removed
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry remove(
		long commerceNotificationQueueEntryId)
		throws NoSuchNotificationQueueEntryException {
		return remove((Serializable)commerceNotificationQueueEntryId);
	}

	/**
	 * Removes the commerce notification queue entry with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry that was removed
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry remove(Serializable primaryKey)
		throws NoSuchNotificationQueueEntryException {
		Session session = null;

		try {
			session = openSession();

			CommerceNotificationQueueEntry commerceNotificationQueueEntry = (CommerceNotificationQueueEntry)session.get(CommerceNotificationQueueEntryImpl.class,
					primaryKey);

			if (commerceNotificationQueueEntry == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationQueueEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceNotificationQueueEntry);
		}
		catch (NoSuchNotificationQueueEntryException nsee) {
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
	protected CommerceNotificationQueueEntry removeImpl(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceNotificationQueueEntry)) {
				commerceNotificationQueueEntry = (CommerceNotificationQueueEntry)session.get(CommerceNotificationQueueEntryImpl.class,
						commerceNotificationQueueEntry.getPrimaryKeyObj());
			}

			if (commerceNotificationQueueEntry != null) {
				session.delete(commerceNotificationQueueEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceNotificationQueueEntry != null) {
			clearCache(commerceNotificationQueueEntry);
		}

		return commerceNotificationQueueEntry;
	}

	@Override
	public CommerceNotificationQueueEntry updateImpl(
		CommerceNotificationQueueEntry commerceNotificationQueueEntry) {
		boolean isNew = commerceNotificationQueueEntry.isNew();

		if (!(commerceNotificationQueueEntry instanceof CommerceNotificationQueueEntryModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
						commerceNotificationQueueEntry.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceNotificationQueueEntry);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceNotificationQueueEntry proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceNotificationQueueEntry implementation " +
				commerceNotificationQueueEntry.getClass());
		}

		CommerceNotificationQueueEntryModelImpl commerceNotificationQueueEntryModelImpl =
			(CommerceNotificationQueueEntryModelImpl)commerceNotificationQueueEntry;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceNotificationQueueEntry.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceNotificationQueueEntry.setCreateDate(now);
			}
			else {
				commerceNotificationQueueEntry.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceNotificationQueueEntryModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceNotificationQueueEntry.setModifiedDate(now);
			}
			else {
				commerceNotificationQueueEntry.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceNotificationQueueEntry.isNew()) {
				session.save(commerceNotificationQueueEntry);

				commerceNotificationQueueEntry.setNew(false);
			}
			else {
				commerceNotificationQueueEntry = (CommerceNotificationQueueEntry)session.merge(commerceNotificationQueueEntry);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceNotificationQueueEntryModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceNotificationQueueEntryModelImpl.getGroupId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commerceNotificationQueueEntryModelImpl.getCommerceNotificationTemplateId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONTEMPLATEID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID,
				args);

			args = new Object[] { commerceNotificationQueueEntryModelImpl.isSent() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_SENT, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SENT,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceNotificationQueueEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceNotificationQueueEntryModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] {
						commerceNotificationQueueEntryModelImpl.getGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceNotificationQueueEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceNotificationQueueEntryModelImpl.getOriginalCommerceNotificationTemplateId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONTEMPLATEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID,
					args);

				args = new Object[] {
						commerceNotificationQueueEntryModelImpl.getCommerceNotificationTemplateId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONTEMPLATEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID,
					args);
			}

			if ((commerceNotificationQueueEntryModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SENT.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceNotificationQueueEntryModelImpl.getOriginalSent()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SENT, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SENT,
					args);

				args = new Object[] {
						commerceNotificationQueueEntryModelImpl.isSent()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_SENT, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_SENT,
					args);
			}
		}

		entityCache.putResult(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationQueueEntryImpl.class,
			commerceNotificationQueueEntry.getPrimaryKey(),
			commerceNotificationQueueEntry, false);

		commerceNotificationQueueEntry.resetOriginalValues();

		return commerceNotificationQueueEntry;
	}

	/**
	 * Returns the commerce notification queue entry with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByPrimaryKey(
		Serializable primaryKey) throws NoSuchNotificationQueueEntryException {
		CommerceNotificationQueueEntry commerceNotificationQueueEntry = fetchByPrimaryKey(primaryKey);

		if (commerceNotificationQueueEntry == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationQueueEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceNotificationQueueEntry;
	}

	/**
	 * Returns the commerce notification queue entry with the primary key or throws a {@link NoSuchNotificationQueueEntryException} if it could not be found.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry
	 * @throws NoSuchNotificationQueueEntryException if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry findByPrimaryKey(
		long commerceNotificationQueueEntryId)
		throws NoSuchNotificationQueueEntryException {
		return findByPrimaryKey((Serializable)commerceNotificationQueueEntryId);
	}

	/**
	 * Returns the commerce notification queue entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry, or <code>null</code> if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
				CommerceNotificationQueueEntryImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceNotificationQueueEntry commerceNotificationQueueEntry = (CommerceNotificationQueueEntry)serializable;

		if (commerceNotificationQueueEntry == null) {
			Session session = null;

			try {
				session = openSession();

				commerceNotificationQueueEntry = (CommerceNotificationQueueEntry)session.get(CommerceNotificationQueueEntryImpl.class,
						primaryKey);

				if (commerceNotificationQueueEntry != null) {
					cacheResult(commerceNotificationQueueEntry);
				}
				else {
					entityCache.putResult(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
						CommerceNotificationQueueEntryImpl.class, primaryKey,
						nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationQueueEntryImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceNotificationQueueEntry;
	}

	/**
	 * Returns the commerce notification queue entry with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceNotificationQueueEntryId the primary key of the commerce notification queue entry
	 * @return the commerce notification queue entry, or <code>null</code> if a commerce notification queue entry with the primary key could not be found
	 */
	@Override
	public CommerceNotificationQueueEntry fetchByPrimaryKey(
		long commerceNotificationQueueEntryId) {
		return fetchByPrimaryKey((Serializable)commerceNotificationQueueEntryId);
	}

	@Override
	public Map<Serializable, CommerceNotificationQueueEntry> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceNotificationQueueEntry> map = new HashMap<Serializable, CommerceNotificationQueueEntry>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceNotificationQueueEntry commerceNotificationQueueEntry = fetchByPrimaryKey(primaryKey);

			if (commerceNotificationQueueEntry != null) {
				map.put(primaryKey, commerceNotificationQueueEntry);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationQueueEntryImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceNotificationQueueEntry)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE_PKS_IN);

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

			for (CommerceNotificationQueueEntry commerceNotificationQueueEntry : (List<CommerceNotificationQueueEntry>)q.list()) {
				map.put(commerceNotificationQueueEntry.getPrimaryKeyObj(),
					commerceNotificationQueueEntry);

				cacheResult(commerceNotificationQueueEntry);

				uncachedPrimaryKeys.remove(commerceNotificationQueueEntry.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceNotificationQueueEntryModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationQueueEntryImpl.class, primaryKey,
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
	 * Returns all the commerce notification queue entries.
	 *
	 * @return the commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @return the range of commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findAll(int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification queue entries.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationQueueEntryModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification queue entries
	 * @param end the upper bound of the range of commerce notification queue entries (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce notification queue entries
	 */
	@Override
	public List<CommerceNotificationQueueEntry> findAll(int start, int end,
		OrderByComparator<CommerceNotificationQueueEntry> orderByComparator,
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

		List<CommerceNotificationQueueEntry> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationQueueEntry>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY;

				if (pagination) {
					sql = sql.concat(CommerceNotificationQueueEntryModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationQueueEntry>)QueryUtil.list(q,
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
	 * Removes all the commerce notification queue entries from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceNotificationQueueEntry commerceNotificationQueueEntry : findAll()) {
			remove(commerceNotificationQueueEntry);
		}
	}

	/**
	 * Returns the number of commerce notification queue entries.
	 *
	 * @return the number of commerce notification queue entries
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY);

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
		return CommerceNotificationQueueEntryModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce notification queue entry persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceNotificationQueueEntryImpl.class.getName());
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

	private Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY = "SELECT commerceNotificationQueueEntry FROM CommerceNotificationQueueEntry commerceNotificationQueueEntry";
	private static final String _SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE_PKS_IN =
		"SELECT commerceNotificationQueueEntry FROM CommerceNotificationQueueEntry commerceNotificationQueueEntry WHERE CNotificationQueueEntryId IN (";
	private static final String _SQL_SELECT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE =
		"SELECT commerceNotificationQueueEntry FROM CommerceNotificationQueueEntry commerceNotificationQueueEntry WHERE ";
	private static final String _SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY = "SELECT COUNT(commerceNotificationQueueEntry) FROM CommerceNotificationQueueEntry commerceNotificationQueueEntry";
	private static final String _SQL_COUNT_COMMERCENOTIFICATIONQUEUEENTRY_WHERE = "SELECT COUNT(commerceNotificationQueueEntry) FROM CommerceNotificationQueueEntry commerceNotificationQueueEntry WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceNotificationQueueEntry.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceNotificationQueueEntry exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceNotificationQueueEntry exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceNotificationQueueEntryPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"commerceNotificationQueueEntryId", "from", "to"
			});
}