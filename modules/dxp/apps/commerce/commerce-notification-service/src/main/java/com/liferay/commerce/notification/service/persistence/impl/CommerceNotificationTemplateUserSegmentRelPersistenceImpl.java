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

package com.liferay.commerce.notification.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.notification.exception.NoSuchNotificationTemplateUserSegmentRelException;
import com.liferay.commerce.notification.model.CommerceNotificationTemplateUserSegmentRel;
import com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateUserSegmentRelImpl;
import com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateUserSegmentRelModelImpl;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationTemplateUserSegmentRelPersistence;

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

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce notification template user segment rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceNotificationTemplateUserSegmentRelPersistence
 * @see com.liferay.commerce.notification.service.persistence.CommerceNotificationTemplateUserSegmentRelUtil
 * @generated
 */
@ProviderType
public class CommerceNotificationTemplateUserSegmentRelPersistenceImpl
	extends BasePersistenceImpl<CommerceNotificationTemplateUserSegmentRel>
	implements CommerceNotificationTemplateUserSegmentRelPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceNotificationTemplateUserSegmentRelUtil} to access the commerce notification template user segment rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceNotificationTemplateUserSegmentRelImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID =
		new FinderPath(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceNotificationTemplateId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID =
		new FinderPath(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceNotificationTemplateId",
			new String[] { Long.class.getName() },
			CommerceNotificationTemplateUserSegmentRelModelImpl.COMMERCENOTIFICATIONTEMPLATEID_COLUMN_BITMASK |
			CommerceNotificationTemplateUserSegmentRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONTEMPLATEID =
		new FinderPath(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceNotificationTemplateId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the matching commerce notification template user segment rels
	 */
	@Override
	public List<CommerceNotificationTemplateUserSegmentRel> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		return findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification template user segment rels
	 * @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	 * @return the range of matching commerce notification template user segment rels
	 */
	@Override
	public List<CommerceNotificationTemplateUserSegmentRel> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end) {
		return findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification template user segment rels
	 * @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification template user segment rels
	 */
	@Override
	public List<CommerceNotificationTemplateUserSegmentRel> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		return findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification template user segment rels
	 * @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce notification template user segment rels
	 */
	@Override
	public List<CommerceNotificationTemplateUserSegmentRel> findByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId, int start, int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator,
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

		List<CommerceNotificationTemplateUserSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationTemplateUserSegmentRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel : list) {
					if ((commerceNotificationTemplateId != commerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateId())) {
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

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCENOTIFICATIONTEMPLATEID_COMMERCENOTIFICATIONTEMPLATEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceNotificationTemplateUserSegmentRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceNotificationTemplateId);

				if (!pagination) {
					list = (List<CommerceNotificationTemplateUserSegmentRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationTemplateUserSegmentRel>)QueryUtil.list(q,
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
	 * Returns the first commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template user segment rel
	 * @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel findByCommerceNotificationTemplateId_First(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws NoSuchNotificationTemplateUserSegmentRelException {
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
			fetchByCommerceNotificationTemplateId_First(commerceNotificationTemplateId,
				orderByComparator);

		if (commerceNotificationTemplateUserSegmentRel != null) {
			return commerceNotificationTemplateUserSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceNotificationTemplateId=");
		msg.append(commerceNotificationTemplateId);

		msg.append("}");

		throw new NoSuchNotificationTemplateUserSegmentRelException(msg.toString());
	}

	/**
	 * Returns the first commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel fetchByCommerceNotificationTemplateId_First(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		List<CommerceNotificationTemplateUserSegmentRel> list = findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template user segment rel
	 * @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel findByCommerceNotificationTemplateId_Last(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws NoSuchNotificationTemplateUserSegmentRelException {
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
			fetchByCommerceNotificationTemplateId_Last(commerceNotificationTemplateId,
				orderByComparator);

		if (commerceNotificationTemplateUserSegmentRel != null) {
			return commerceNotificationTemplateUserSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceNotificationTemplateId=");
		msg.append(commerceNotificationTemplateId);

		msg.append("}");

		throw new NoSuchNotificationTemplateUserSegmentRelException(msg.toString());
	}

	/**
	 * Returns the last commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel fetchByCommerceNotificationTemplateId_Last(
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		int count = countByCommerceNotificationTemplateId(commerceNotificationTemplateId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationTemplateUserSegmentRel> list = findByCommerceNotificationTemplateId(commerceNotificationTemplateId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification template user segment rels before and after the current commerce notification template user segment rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateUserSegmentRelId the primary key of the current commerce notification template user segment rel
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template user segment rel
	 * @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel[] findByCommerceNotificationTemplateId_PrevAndNext(
		long commerceNotificationTemplateUserSegmentRelId,
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws NoSuchNotificationTemplateUserSegmentRelException {
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
			findByPrimaryKey(commerceNotificationTemplateUserSegmentRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationTemplateUserSegmentRel[] array = new CommerceNotificationTemplateUserSegmentRelImpl[3];

			array[0] = getByCommerceNotificationTemplateId_PrevAndNext(session,
					commerceNotificationTemplateUserSegmentRel,
					commerceNotificationTemplateId, orderByComparator, true);

			array[1] = commerceNotificationTemplateUserSegmentRel;

			array[2] = getByCommerceNotificationTemplateId_PrevAndNext(session,
					commerceNotificationTemplateUserSegmentRel,
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

	protected CommerceNotificationTemplateUserSegmentRel getByCommerceNotificationTemplateId_PrevAndNext(
		Session session,
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel,
		long commerceNotificationTemplateId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL_WHERE);

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
			query.append(CommerceNotificationTemplateUserSegmentRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceNotificationTemplateId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceNotificationTemplateUserSegmentRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceNotificationTemplateUserSegmentRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification template user segment rels where commerceNotificationTemplateId = &#63; from the database.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 */
	@Override
	public void removeByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		for (CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel : findByCommerceNotificationTemplateId(
				commerceNotificationTemplateId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceNotificationTemplateUserSegmentRel);
		}
	}

	/**
	 * Returns the number of commerce notification template user segment rels where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the number of matching commerce notification template user segment rels
	 */
	@Override
	public int countByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONTEMPLATEID;

		Object[] finderArgs = new Object[] { commerceNotificationTemplateId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL_WHERE);

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
		"commerceNotificationTemplateUserSegmentRel.commerceNotificationTemplateId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID =
		new FinderPath(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceUserSegmentEntryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID =
		new FinderPath(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceUserSegmentEntryId",
			new String[] { Long.class.getName() },
			CommerceNotificationTemplateUserSegmentRelModelImpl.COMMERCEUSERSEGMENTENTRYID_COLUMN_BITMASK |
			CommerceNotificationTemplateUserSegmentRelModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID =
		new FinderPath(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceUserSegmentEntryId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the matching commerce notification template user segment rels
	 */
	@Override
	public List<CommerceNotificationTemplateUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		return findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param start the lower bound of the range of commerce notification template user segment rels
	 * @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	 * @return the range of matching commerce notification template user segment rels
	 */
	@Override
	public List<CommerceNotificationTemplateUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end) {
		return findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param start the lower bound of the range of commerce notification template user segment rels
	 * @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification template user segment rels
	 */
	@Override
	public List<CommerceNotificationTemplateUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		return findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
			start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param start the lower bound of the range of commerce notification template user segment rels
	 * @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce notification template user segment rels
	 */
	@Override
	public List<CommerceNotificationTemplateUserSegmentRel> findByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId, int start, int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID;
			finderArgs = new Object[] { commerceUserSegmentEntryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID;
			finderArgs = new Object[] {
					commerceUserSegmentEntryId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceNotificationTemplateUserSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationTemplateUserSegmentRel>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel : list) {
					if ((commerceUserSegmentEntryId != commerceNotificationTemplateUserSegmentRel.getCommerceUserSegmentEntryId())) {
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

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEUSERSEGMENTENTRYID_COMMERCEUSERSEGMENTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceNotificationTemplateUserSegmentRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceUserSegmentEntryId);

				if (!pagination) {
					list = (List<CommerceNotificationTemplateUserSegmentRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationTemplateUserSegmentRel>)QueryUtil.list(q,
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
	 * Returns the first commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template user segment rel
	 * @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel findByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws NoSuchNotificationTemplateUserSegmentRelException {
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
			fetchByCommerceUserSegmentEntryId_First(commerceUserSegmentEntryId,
				orderByComparator);

		if (commerceNotificationTemplateUserSegmentRel != null) {
			return commerceNotificationTemplateUserSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceUserSegmentEntryId=");
		msg.append(commerceUserSegmentEntryId);

		msg.append("}");

		throw new NoSuchNotificationTemplateUserSegmentRelException(msg.toString());
	}

	/**
	 * Returns the first commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel fetchByCommerceUserSegmentEntryId_First(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		List<CommerceNotificationTemplateUserSegmentRel> list = findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template user segment rel
	 * @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel findByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws NoSuchNotificationTemplateUserSegmentRelException {
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
			fetchByCommerceUserSegmentEntryId_Last(commerceUserSegmentEntryId,
				orderByComparator);

		if (commerceNotificationTemplateUserSegmentRel != null) {
			return commerceNotificationTemplateUserSegmentRel;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceUserSegmentEntryId=");
		msg.append(commerceUserSegmentEntryId);

		msg.append("}");

		throw new NoSuchNotificationTemplateUserSegmentRelException(msg.toString());
	}

	/**
	 * Returns the last commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel fetchByCommerceUserSegmentEntryId_Last(
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		int count = countByCommerceUserSegmentEntryId(commerceUserSegmentEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationTemplateUserSegmentRel> list = findByCommerceUserSegmentEntryId(commerceUserSegmentEntryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification template user segment rels before and after the current commerce notification template user segment rel in the ordered set where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceNotificationTemplateUserSegmentRelId the primary key of the current commerce notification template user segment rel
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template user segment rel
	 * @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel[] findByCommerceUserSegmentEntryId_PrevAndNext(
		long commerceNotificationTemplateUserSegmentRelId,
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator)
		throws NoSuchNotificationTemplateUserSegmentRelException {
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
			findByPrimaryKey(commerceNotificationTemplateUserSegmentRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationTemplateUserSegmentRel[] array = new CommerceNotificationTemplateUserSegmentRelImpl[3];

			array[0] = getByCommerceUserSegmentEntryId_PrevAndNext(session,
					commerceNotificationTemplateUserSegmentRel,
					commerceUserSegmentEntryId, orderByComparator, true);

			array[1] = commerceNotificationTemplateUserSegmentRel;

			array[2] = getByCommerceUserSegmentEntryId_PrevAndNext(session,
					commerceNotificationTemplateUserSegmentRel,
					commerceUserSegmentEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceNotificationTemplateUserSegmentRel getByCommerceUserSegmentEntryId_PrevAndNext(
		Session session,
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel,
		long commerceUserSegmentEntryId,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator,
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

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEUSERSEGMENTENTRYID_COMMERCEUSERSEGMENTENTRYID_2);

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
			query.append(CommerceNotificationTemplateUserSegmentRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceUserSegmentEntryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceNotificationTemplateUserSegmentRel);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceNotificationTemplateUserSegmentRel> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification template user segment rels where commerceUserSegmentEntryId = &#63; from the database.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 */
	@Override
	public void removeByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		for (CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel : findByCommerceUserSegmentEntryId(
				commerceUserSegmentEntryId, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceNotificationTemplateUserSegmentRel);
		}
	}

	/**
	 * Returns the number of commerce notification template user segment rels where commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the number of matching commerce notification template user segment rels
	 */
	@Override
	public int countByCommerceUserSegmentEntryId(
		long commerceUserSegmentEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID;

		Object[] finderArgs = new Object[] { commerceUserSegmentEntryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEUSERSEGMENTENTRYID_COMMERCEUSERSEGMENTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceUserSegmentEntryId);

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

	private static final String _FINDER_COLUMN_COMMERCEUSERSEGMENTENTRYID_COMMERCEUSERSEGMENTENTRYID_2 =
		"commerceNotificationTemplateUserSegmentRel.commerceUserSegmentEntryId = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_C_C = new FinderPath(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			CommerceNotificationTemplateUserSegmentRelModelImpl.COMMERCENOTIFICATIONTEMPLATEID_COLUMN_BITMASK |
			CommerceNotificationTemplateUserSegmentRelModelImpl.COMMERCEUSERSEGMENTENTRYID_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelModelImpl.FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns the commerce notification template user segment rel where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63; or throws a {@link NoSuchNotificationTemplateUserSegmentRelException} if it could not be found.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the matching commerce notification template user segment rel
	 * @throws NoSuchNotificationTemplateUserSegmentRelException if a matching commerce notification template user segment rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel findByC_C(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId)
		throws NoSuchNotificationTemplateUserSegmentRelException {
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
			fetchByC_C(commerceNotificationTemplateId,
				commerceUserSegmentEntryId);

		if (commerceNotificationTemplateUserSegmentRel == null) {
			StringBundler msg = new StringBundler(6);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("commerceNotificationTemplateId=");
			msg.append(commerceNotificationTemplateId);

			msg.append(", commerceUserSegmentEntryId=");
			msg.append(commerceUserSegmentEntryId);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchNotificationTemplateUserSegmentRelException(msg.toString());
		}

		return commerceNotificationTemplateUserSegmentRel;
	}

	/**
	 * Returns the commerce notification template user segment rel where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel fetchByC_C(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId) {
		return fetchByC_C(commerceNotificationTemplateId,
			commerceUserSegmentEntryId, true);
	}

	/**
	 * Returns the commerce notification template user segment rel where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce notification template user segment rel, or <code>null</code> if a matching commerce notification template user segment rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel fetchByC_C(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId,
		boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] {
				commerceNotificationTemplateId, commerceUserSegmentEntryId
			};

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_C_C,
					finderArgs, this);
		}

		if (result instanceof CommerceNotificationTemplateUserSegmentRel) {
			CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
				(CommerceNotificationTemplateUserSegmentRel)result;

			if ((commerceNotificationTemplateId != commerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateId()) ||
					(commerceUserSegmentEntryId != commerceNotificationTemplateUserSegmentRel.getCommerceUserSegmentEntryId())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMMERCENOTIFICATIONTEMPLATEID_2);

			query.append(_FINDER_COLUMN_C_C_COMMERCEUSERSEGMENTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceNotificationTemplateId);

				qPos.add(commerceUserSegmentEntryId);

				List<CommerceNotificationTemplateUserSegmentRel> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, finderArgs,
						list);
				}
				else {
					CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
						list.get(0);

					result = commerceNotificationTemplateUserSegmentRel;

					cacheResult(commerceNotificationTemplateUserSegmentRel);

					if ((commerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateId() != commerceNotificationTemplateId) ||
							(commerceNotificationTemplateUserSegmentRel.getCommerceUserSegmentEntryId() != commerceUserSegmentEntryId)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
							finderArgs,
							commerceNotificationTemplateUserSegmentRel);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, finderArgs);

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
			return (CommerceNotificationTemplateUserSegmentRel)result;
		}
	}

	/**
	 * Removes the commerce notification template user segment rel where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63; from the database.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the commerce notification template user segment rel that was removed
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel removeByC_C(
		long commerceNotificationTemplateId, long commerceUserSegmentEntryId)
		throws NoSuchNotificationTemplateUserSegmentRelException {
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
			findByC_C(commerceNotificationTemplateId, commerceUserSegmentEntryId);

		return remove(commerceNotificationTemplateUserSegmentRel);
	}

	/**
	 * Returns the number of commerce notification template user segment rels where commerceNotificationTemplateId = &#63; and commerceUserSegmentEntryId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceUserSegmentEntryId the commerce user segment entry ID
	 * @return the number of matching commerce notification template user segment rels
	 */
	@Override
	public int countByC_C(long commerceNotificationTemplateId,
		long commerceUserSegmentEntryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] {
				commerceNotificationTemplateId, commerceUserSegmentEntryId
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL_WHERE);

			query.append(_FINDER_COLUMN_C_C_COMMERCENOTIFICATIONTEMPLATEID_2);

			query.append(_FINDER_COLUMN_C_C_COMMERCEUSERSEGMENTENTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceNotificationTemplateId);

				qPos.add(commerceUserSegmentEntryId);

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

	private static final String _FINDER_COLUMN_C_C_COMMERCENOTIFICATIONTEMPLATEID_2 =
		"commerceNotificationTemplateUserSegmentRel.commerceNotificationTemplateId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_COMMERCEUSERSEGMENTENTRYID_2 = "commerceNotificationTemplateUserSegmentRel.commerceUserSegmentEntryId = ?";

	public CommerceNotificationTemplateUserSegmentRelPersistenceImpl() {
		setModelClass(CommerceNotificationTemplateUserSegmentRel.class);

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
					"_dbColumnNames");

			field.setAccessible(true);

			Map<String, String> dbColumnNames = new HashMap<String, String>();

			dbColumnNames.put("commerceNotificationTemplateUserSegmentRelId",
				"CNTemplateUserSegmentRelId");

			field.set(this, dbColumnNames);
		}
		catch (Exception e) {
			if (_log.isDebugEnabled()) {
				_log.debug(e, e);
			}
		}
	}

	/**
	 * Caches the commerce notification template user segment rel in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationTemplateUserSegmentRel the commerce notification template user segment rel
	 */
	@Override
	public void cacheResult(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
		entityCache.putResult(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelImpl.class,
			commerceNotificationTemplateUserSegmentRel.getPrimaryKey(),
			commerceNotificationTemplateUserSegmentRel);

		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C,
			new Object[] {
				commerceNotificationTemplateUserSegmentRel.getCommerceNotificationTemplateId(),
				commerceNotificationTemplateUserSegmentRel.getCommerceUserSegmentEntryId()
			}, commerceNotificationTemplateUserSegmentRel);

		commerceNotificationTemplateUserSegmentRel.resetOriginalValues();
	}

	/**
	 * Caches the commerce notification template user segment rels in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationTemplateUserSegmentRels the commerce notification template user segment rels
	 */
	@Override
	public void cacheResult(
		List<CommerceNotificationTemplateUserSegmentRel> commerceNotificationTemplateUserSegmentRels) {
		for (CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel : commerceNotificationTemplateUserSegmentRels) {
			if (entityCache.getResult(
						CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceNotificationTemplateUserSegmentRelImpl.class,
						commerceNotificationTemplateUserSegmentRel.getPrimaryKey()) == null) {
				cacheResult(commerceNotificationTemplateUserSegmentRel);
			}
			else {
				commerceNotificationTemplateUserSegmentRel.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce notification template user segment rels.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceNotificationTemplateUserSegmentRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce notification template user segment rel.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
		entityCache.removeResult(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelImpl.class,
			commerceNotificationTemplateUserSegmentRel.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceNotificationTemplateUserSegmentRelModelImpl)commerceNotificationTemplateUserSegmentRel,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceNotificationTemplateUserSegmentRel> commerceNotificationTemplateUserSegmentRels) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel : commerceNotificationTemplateUserSegmentRels) {
			entityCache.removeResult(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceNotificationTemplateUserSegmentRelImpl.class,
				commerceNotificationTemplateUserSegmentRel.getPrimaryKey());

			clearUniqueFindersCache((CommerceNotificationTemplateUserSegmentRelModelImpl)commerceNotificationTemplateUserSegmentRel,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceNotificationTemplateUserSegmentRelModelImpl commerceNotificationTemplateUserSegmentRelModelImpl) {
		Object[] args = new Object[] {
				commerceNotificationTemplateUserSegmentRelModelImpl.getCommerceNotificationTemplateId(),
				commerceNotificationTemplateUserSegmentRelModelImpl.getCommerceUserSegmentEntryId()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_C_C, args, Long.valueOf(1),
			false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_C_C, args,
			commerceNotificationTemplateUserSegmentRelModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceNotificationTemplateUserSegmentRelModelImpl commerceNotificationTemplateUserSegmentRelModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceNotificationTemplateUserSegmentRelModelImpl.getCommerceNotificationTemplateId(),
					commerceNotificationTemplateUserSegmentRelModelImpl.getCommerceUserSegmentEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}

		if ((commerceNotificationTemplateUserSegmentRelModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceNotificationTemplateUserSegmentRelModelImpl.getOriginalCommerceNotificationTemplateId(),
					commerceNotificationTemplateUserSegmentRelModelImpl.getOriginalCommerceUserSegmentEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_C_C, args);
		}
	}

	/**
	 * Creates a new commerce notification template user segment rel with the primary key. Does not add the commerce notification template user segment rel to the database.
	 *
	 * @param commerceNotificationTemplateUserSegmentRelId the primary key for the new commerce notification template user segment rel
	 * @return the new commerce notification template user segment rel
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel create(
		long commerceNotificationTemplateUserSegmentRelId) {
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
			new CommerceNotificationTemplateUserSegmentRelImpl();

		commerceNotificationTemplateUserSegmentRel.setNew(true);
		commerceNotificationTemplateUserSegmentRel.setPrimaryKey(commerceNotificationTemplateUserSegmentRelId);

		commerceNotificationTemplateUserSegmentRel.setCompanyId(companyProvider.getCompanyId());

		return commerceNotificationTemplateUserSegmentRel;
	}

	/**
	 * Removes the commerce notification template user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceNotificationTemplateUserSegmentRelId the primary key of the commerce notification template user segment rel
	 * @return the commerce notification template user segment rel that was removed
	 * @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel remove(
		long commerceNotificationTemplateUserSegmentRelId)
		throws NoSuchNotificationTemplateUserSegmentRelException {
		return remove((Serializable)commerceNotificationTemplateUserSegmentRelId);
	}

	/**
	 * Removes the commerce notification template user segment rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce notification template user segment rel
	 * @return the commerce notification template user segment rel that was removed
	 * @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel remove(
		Serializable primaryKey)
		throws NoSuchNotificationTemplateUserSegmentRelException {
		Session session = null;

		try {
			session = openSession();

			CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
				(CommerceNotificationTemplateUserSegmentRel)session.get(CommerceNotificationTemplateUserSegmentRelImpl.class,
					primaryKey);

			if (commerceNotificationTemplateUserSegmentRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationTemplateUserSegmentRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceNotificationTemplateUserSegmentRel);
		}
		catch (NoSuchNotificationTemplateUserSegmentRelException nsee) {
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
	protected CommerceNotificationTemplateUserSegmentRel removeImpl(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceNotificationTemplateUserSegmentRel)) {
				commerceNotificationTemplateUserSegmentRel = (CommerceNotificationTemplateUserSegmentRel)session.get(CommerceNotificationTemplateUserSegmentRelImpl.class,
						commerceNotificationTemplateUserSegmentRel.getPrimaryKeyObj());
			}

			if (commerceNotificationTemplateUserSegmentRel != null) {
				session.delete(commerceNotificationTemplateUserSegmentRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceNotificationTemplateUserSegmentRel != null) {
			clearCache(commerceNotificationTemplateUserSegmentRel);
		}

		return commerceNotificationTemplateUserSegmentRel;
	}

	@Override
	public CommerceNotificationTemplateUserSegmentRel updateImpl(
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel) {
		boolean isNew = commerceNotificationTemplateUserSegmentRel.isNew();

		if (!(commerceNotificationTemplateUserSegmentRel instanceof CommerceNotificationTemplateUserSegmentRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
						commerceNotificationTemplateUserSegmentRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(commerceNotificationTemplateUserSegmentRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceNotificationTemplateUserSegmentRel proxy " +
					invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceNotificationTemplateUserSegmentRel implementation " +
				commerceNotificationTemplateUserSegmentRel.getClass());
		}

		CommerceNotificationTemplateUserSegmentRelModelImpl commerceNotificationTemplateUserSegmentRelModelImpl =
			(CommerceNotificationTemplateUserSegmentRelModelImpl)commerceNotificationTemplateUserSegmentRel;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew &&
				(commerceNotificationTemplateUserSegmentRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceNotificationTemplateUserSegmentRel.setCreateDate(now);
			}
			else {
				commerceNotificationTemplateUserSegmentRel.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceNotificationTemplateUserSegmentRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceNotificationTemplateUserSegmentRel.setModifiedDate(now);
			}
			else {
				commerceNotificationTemplateUserSegmentRel.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceNotificationTemplateUserSegmentRel.isNew()) {
				session.save(commerceNotificationTemplateUserSegmentRel);

				commerceNotificationTemplateUserSegmentRel.setNew(false);
			}
			else {
				commerceNotificationTemplateUserSegmentRel = (CommerceNotificationTemplateUserSegmentRel)session.merge(commerceNotificationTemplateUserSegmentRel);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceNotificationTemplateUserSegmentRelModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceNotificationTemplateUserSegmentRelModelImpl.getCommerceNotificationTemplateId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONTEMPLATEID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID,
				args);

			args = new Object[] {
					commerceNotificationTemplateUserSegmentRelModelImpl.getCommerceUserSegmentEntryId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceNotificationTemplateUserSegmentRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceNotificationTemplateUserSegmentRelModelImpl.getOriginalCommerceNotificationTemplateId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONTEMPLATEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID,
					args);

				args = new Object[] {
						commerceNotificationTemplateUserSegmentRelModelImpl.getCommerceNotificationTemplateId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCENOTIFICATIONTEMPLATEID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCENOTIFICATIONTEMPLATEID,
					args);
			}

			if ((commerceNotificationTemplateUserSegmentRelModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceNotificationTemplateUserSegmentRelModelImpl.getOriginalCommerceUserSegmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID,
					args);

				args = new Object[] {
						commerceNotificationTemplateUserSegmentRelModelImpl.getCommerceUserSegmentEntryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEUSERSEGMENTENTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEUSERSEGMENTENTRYID,
					args);
			}
		}

		entityCache.putResult(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateUserSegmentRelImpl.class,
			commerceNotificationTemplateUserSegmentRel.getPrimaryKey(),
			commerceNotificationTemplateUserSegmentRel, false);

		clearUniqueFindersCache(commerceNotificationTemplateUserSegmentRelModelImpl,
			false);
		cacheUniqueFindersCache(commerceNotificationTemplateUserSegmentRelModelImpl);

		commerceNotificationTemplateUserSegmentRel.resetOriginalValues();

		return commerceNotificationTemplateUserSegmentRel;
	}

	/**
	 * Returns the commerce notification template user segment rel with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce notification template user segment rel
	 * @return the commerce notification template user segment rel
	 * @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel findByPrimaryKey(
		Serializable primaryKey)
		throws NoSuchNotificationTemplateUserSegmentRelException {
		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
			fetchByPrimaryKey(primaryKey);

		if (commerceNotificationTemplateUserSegmentRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationTemplateUserSegmentRelException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceNotificationTemplateUserSegmentRel;
	}

	/**
	 * Returns the commerce notification template user segment rel with the primary key or throws a {@link NoSuchNotificationTemplateUserSegmentRelException} if it could not be found.
	 *
	 * @param commerceNotificationTemplateUserSegmentRelId the primary key of the commerce notification template user segment rel
	 * @return the commerce notification template user segment rel
	 * @throws NoSuchNotificationTemplateUserSegmentRelException if a commerce notification template user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel findByPrimaryKey(
		long commerceNotificationTemplateUserSegmentRelId)
		throws NoSuchNotificationTemplateUserSegmentRelException {
		return findByPrimaryKey((Serializable)commerceNotificationTemplateUserSegmentRelId);
	}

	/**
	 * Returns the commerce notification template user segment rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce notification template user segment rel
	 * @return the commerce notification template user segment rel, or <code>null</code> if a commerce notification template user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel fetchByPrimaryKey(
		Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
				CommerceNotificationTemplateUserSegmentRelImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
			(CommerceNotificationTemplateUserSegmentRel)serializable;

		if (commerceNotificationTemplateUserSegmentRel == null) {
			Session session = null;

			try {
				session = openSession();

				commerceNotificationTemplateUserSegmentRel = (CommerceNotificationTemplateUserSegmentRel)session.get(CommerceNotificationTemplateUserSegmentRelImpl.class,
						primaryKey);

				if (commerceNotificationTemplateUserSegmentRel != null) {
					cacheResult(commerceNotificationTemplateUserSegmentRel);
				}
				else {
					entityCache.putResult(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceNotificationTemplateUserSegmentRelImpl.class,
						primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationTemplateUserSegmentRelImpl.class,
					primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceNotificationTemplateUserSegmentRel;
	}

	/**
	 * Returns the commerce notification template user segment rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceNotificationTemplateUserSegmentRelId the primary key of the commerce notification template user segment rel
	 * @return the commerce notification template user segment rel, or <code>null</code> if a commerce notification template user segment rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateUserSegmentRel fetchByPrimaryKey(
		long commerceNotificationTemplateUserSegmentRelId) {
		return fetchByPrimaryKey((Serializable)commerceNotificationTemplateUserSegmentRelId);
	}

	@Override
	public Map<Serializable, CommerceNotificationTemplateUserSegmentRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceNotificationTemplateUserSegmentRel> map = new HashMap<Serializable, CommerceNotificationTemplateUserSegmentRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel =
				fetchByPrimaryKey(primaryKey);

			if (commerceNotificationTemplateUserSegmentRel != null) {
				map.put(primaryKey, commerceNotificationTemplateUserSegmentRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationTemplateUserSegmentRelImpl.class,
					primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey,
						(CommerceNotificationTemplateUserSegmentRel)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL_WHERE_PKS_IN);

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

			for (CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel : (List<CommerceNotificationTemplateUserSegmentRel>)q.list()) {
				map.put(commerceNotificationTemplateUserSegmentRel.getPrimaryKeyObj(),
					commerceNotificationTemplateUserSegmentRel);

				cacheResult(commerceNotificationTemplateUserSegmentRel);

				uncachedPrimaryKeys.remove(commerceNotificationTemplateUserSegmentRel.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceNotificationTemplateUserSegmentRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationTemplateUserSegmentRelImpl.class,
					primaryKey, nullModel);
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
	 * Returns all the commerce notification template user segment rels.
	 *
	 * @return the commerce notification template user segment rels
	 */
	@Override
	public List<CommerceNotificationTemplateUserSegmentRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification template user segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification template user segment rels
	 * @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	 * @return the range of commerce notification template user segment rels
	 */
	@Override
	public List<CommerceNotificationTemplateUserSegmentRel> findAll(int start,
		int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification template user segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification template user segment rels
	 * @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification template user segment rels
	 */
	@Override
	public List<CommerceNotificationTemplateUserSegmentRel> findAll(int start,
		int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification template user segment rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceNotificationTemplateUserSegmentRelModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification template user segment rels
	 * @param end the upper bound of the range of commerce notification template user segment rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce notification template user segment rels
	 */
	@Override
	public List<CommerceNotificationTemplateUserSegmentRel> findAll(int start,
		int end,
		OrderByComparator<CommerceNotificationTemplateUserSegmentRel> orderByComparator,
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

		List<CommerceNotificationTemplateUserSegmentRel> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceNotificationTemplateUserSegmentRel>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL;

				if (pagination) {
					sql = sql.concat(CommerceNotificationTemplateUserSegmentRelModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceNotificationTemplateUserSegmentRel>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceNotificationTemplateUserSegmentRel>)QueryUtil.list(q,
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
	 * Removes all the commerce notification template user segment rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel : findAll()) {
			remove(commerceNotificationTemplateUserSegmentRel);
		}
	}

	/**
	 * Returns the number of commerce notification template user segment rels.
	 *
	 * @return the number of commerce notification template user segment rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL);

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
		return CommerceNotificationTemplateUserSegmentRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce notification template user segment rel persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceNotificationTemplateUserSegmentRelImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL =
		"SELECT commerceNotificationTemplateUserSegmentRel FROM CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel";
	private static final String _SQL_SELECT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL_WHERE_PKS_IN =
		"SELECT commerceNotificationTemplateUserSegmentRel FROM CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel WHERE CNTemplateUserSegmentRelId IN (";
	private static final String _SQL_SELECT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL_WHERE =
		"SELECT commerceNotificationTemplateUserSegmentRel FROM CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel WHERE ";
	private static final String _SQL_COUNT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL =
		"SELECT COUNT(commerceNotificationTemplateUserSegmentRel) FROM CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel";
	private static final String _SQL_COUNT_COMMERCENOTIFICATIONTEMPLATEUSERSEGMENTREL_WHERE =
		"SELECT COUNT(commerceNotificationTemplateUserSegmentRel) FROM CommerceNotificationTemplateUserSegmentRel commerceNotificationTemplateUserSegmentRel WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceNotificationTemplateUserSegmentRel.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceNotificationTemplateUserSegmentRel exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceNotificationTemplateUserSegmentRel exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceNotificationTemplateUserSegmentRelPersistenceImpl.class);
	private static final Set<String> _badColumnNames = SetUtil.fromArray(new String[] {
				"commerceNotificationTemplateUserSegmentRelId"
			});
}