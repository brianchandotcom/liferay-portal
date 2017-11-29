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

package com.liferay.commerce.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.exception.NoSuchAddressException;
import com.liferay.commerce.model.CommerceAddress;
import com.liferay.commerce.model.impl.CommerceAddressImpl;
import com.liferay.commerce.model.impl.CommerceAddressModelImpl;
import com.liferay.commerce.service.persistence.CommerceAddressPersistence;

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
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce address service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceAddressPersistence
 * @see com.liferay.commerce.service.persistence.CommerceAddressUtil
 * @generated
 */
@ProviderType
public class CommerceAddressPersistenceImpl extends BasePersistenceImpl<CommerceAddress>
	implements CommerceAddressPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceAddressUtil} to access the commerce address persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceAddressImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_ADDRESSUSERID =
		new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByAddressUserId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ADDRESSUSERID =
		new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAddressUserId",
			new String[] { Long.class.getName() },
			CommerceAddressModelImpl.ADDRESSUSERID_COLUMN_BITMASK |
			CommerceAddressModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_ADDRESSUSERID = new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAddressUserId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce addresses where addressUserId = &#63;.
	 *
	 * @param addressUserId the address user ID
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByAddressUserId(long addressUserId) {
		return findByAddressUserId(addressUserId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses where addressUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param addressUserId the address user ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByAddressUserId(long addressUserId,
		int start, int end) {
		return findByAddressUserId(addressUserId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where addressUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param addressUserId the address user ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByAddressUserId(long addressUserId,
		int start, int end, OrderByComparator<CommerceAddress> orderByComparator) {
		return findByAddressUserId(addressUserId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where addressUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param addressUserId the address user ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByAddressUserId(long addressUserId,
		int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ADDRESSUSERID;
			finderArgs = new Object[] { addressUserId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_ADDRESSUSERID;
			finderArgs = new Object[] {
					addressUserId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceAddress> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAddress>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if ((addressUserId != commerceAddress.getAddressUserId())) {
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

			query.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			query.append(_FINDER_COLUMN_ADDRESSUSERID_ADDRESSUSERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(addressUserId);

				if (!pagination) {
					list = (List<CommerceAddress>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAddress>)QueryUtil.list(q,
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
	 * Returns the first commerce address in the ordered set where addressUserId = &#63;.
	 *
	 * @param addressUserId the address user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByAddressUserId_First(long addressUserId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = fetchByAddressUserId_First(addressUserId,
				orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("addressUserId=");
		msg.append(addressUserId);

		msg.append("}");

		throw new NoSuchAddressException(msg.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where addressUserId = &#63;.
	 *
	 * @param addressUserId the address user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByAddressUserId_First(long addressUserId,
		OrderByComparator<CommerceAddress> orderByComparator) {
		List<CommerceAddress> list = findByAddressUserId(addressUserId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where addressUserId = &#63;.
	 *
	 * @param addressUserId the address user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByAddressUserId_Last(long addressUserId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = fetchByAddressUserId_Last(addressUserId,
				orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("addressUserId=");
		msg.append(addressUserId);

		msg.append("}");

		throw new NoSuchAddressException(msg.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where addressUserId = &#63;.
	 *
	 * @param addressUserId the address user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByAddressUserId_Last(long addressUserId,
		OrderByComparator<CommerceAddress> orderByComparator) {
		int count = countByAddressUserId(addressUserId);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByAddressUserId(addressUserId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where addressUserId = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param addressUserId the address user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByAddressUserId_PrevAndNext(
		long commerceAddressId, long addressUserId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByAddressUserId_PrevAndNext(session, commerceAddress,
					addressUserId, orderByComparator, true);

			array[1] = commerceAddress;

			array[2] = getByAddressUserId_PrevAndNext(session, commerceAddress,
					addressUserId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceAddress getByAddressUserId_PrevAndNext(Session session,
		CommerceAddress commerceAddress, long addressUserId,
		OrderByComparator<CommerceAddress> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		query.append(_FINDER_COLUMN_ADDRESSUSERID_ADDRESSUSERID_2);

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
			query.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(addressUserId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAddress);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAddress> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where addressUserId = &#63; from the database.
	 *
	 * @param addressUserId the address user ID
	 */
	@Override
	public void removeByAddressUserId(long addressUserId) {
		for (CommerceAddress commerceAddress : findByAddressUserId(
				addressUserId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where addressUserId = &#63;.
	 *
	 * @param addressUserId the address user ID
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByAddressUserId(long addressUserId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_ADDRESSUSERID;

		Object[] finderArgs = new Object[] { addressUserId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			query.append(_FINDER_COLUMN_ADDRESSUSERID_ADDRESSUSERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(addressUserId);

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

	private static final String _FINDER_COLUMN_ADDRESSUSERID_ADDRESSUSERID_2 = "commerceAddress.addressUserId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEREGIONID =
		new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceRegionId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEREGIONID =
		new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceRegionId", new String[] { Long.class.getName() },
			CommerceAddressModelImpl.COMMERCEREGIONID_COLUMN_BITMASK |
			CommerceAddressModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCEREGIONID = new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceRegionId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce addresses where commerceRegionId = &#63;.
	 *
	 * @param commerceRegionId the commerce region ID
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceRegionId(long commerceRegionId) {
		return findByCommerceRegionId(commerceRegionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses where commerceRegionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceRegionId(long commerceRegionId,
		int start, int end) {
		return findByCommerceRegionId(commerceRegionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where commerceRegionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceRegionId(long commerceRegionId,
		int start, int end, OrderByComparator<CommerceAddress> orderByComparator) {
		return findByCommerceRegionId(commerceRegionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where commerceRegionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceRegionId(long commerceRegionId,
		int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEREGIONID;
			finderArgs = new Object[] { commerceRegionId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCEREGIONID;
			finderArgs = new Object[] {
					commerceRegionId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceAddress> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAddress>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if ((commerceRegionId != commerceAddress.getCommerceRegionId())) {
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

			query.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEREGIONID_COMMERCEREGIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceRegionId);

				if (!pagination) {
					list = (List<CommerceAddress>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAddress>)QueryUtil.list(q,
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
	 * Returns the first commerce address in the ordered set where commerceRegionId = &#63;.
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByCommerceRegionId_First(long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = fetchByCommerceRegionId_First(commerceRegionId,
				orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceRegionId=");
		msg.append(commerceRegionId);

		msg.append("}");

		throw new NoSuchAddressException(msg.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where commerceRegionId = &#63;.
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByCommerceRegionId_First(
		long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator) {
		List<CommerceAddress> list = findByCommerceRegionId(commerceRegionId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where commerceRegionId = &#63;.
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByCommerceRegionId_Last(long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = fetchByCommerceRegionId_Last(commerceRegionId,
				orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceRegionId=");
		msg.append(commerceRegionId);

		msg.append("}");

		throw new NoSuchAddressException(msg.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where commerceRegionId = &#63;.
	 *
	 * @param commerceRegionId the commerce region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByCommerceRegionId_Last(long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator) {
		int count = countByCommerceRegionId(commerceRegionId);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByCommerceRegionId(commerceRegionId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where commerceRegionId = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param commerceRegionId the commerce region ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByCommerceRegionId_PrevAndNext(
		long commerceAddressId, long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByCommerceRegionId_PrevAndNext(session,
					commerceAddress, commerceRegionId, orderByComparator, true);

			array[1] = commerceAddress;

			array[2] = getByCommerceRegionId_PrevAndNext(session,
					commerceAddress, commerceRegionId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceAddress getByCommerceRegionId_PrevAndNext(
		Session session, CommerceAddress commerceAddress,
		long commerceRegionId,
		OrderByComparator<CommerceAddress> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		query.append(_FINDER_COLUMN_COMMERCEREGIONID_COMMERCEREGIONID_2);

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
			query.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceRegionId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAddress);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAddress> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where commerceRegionId = &#63; from the database.
	 *
	 * @param commerceRegionId the commerce region ID
	 */
	@Override
	public void removeByCommerceRegionId(long commerceRegionId) {
		for (CommerceAddress commerceAddress : findByCommerceRegionId(
				commerceRegionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where commerceRegionId = &#63;.
	 *
	 * @param commerceRegionId the commerce region ID
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByCommerceRegionId(long commerceRegionId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCEREGIONID;

		Object[] finderArgs = new Object[] { commerceRegionId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			query.append(_FINDER_COLUMN_COMMERCEREGIONID_COMMERCEREGIONID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceRegionId);

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

	private static final String _FINDER_COLUMN_COMMERCEREGIONID_COMMERCEREGIONID_2 =
		"commerceAddress.commerceRegionId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCECOUNTRYID =
		new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceCountryId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID =
		new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceCountryId", new String[] { Long.class.getName() },
			CommerceAddressModelImpl.COMMERCECOUNTRYID_COLUMN_BITMASK |
			CommerceAddressModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID = new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceCountryId", new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce addresses where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceCountryId(long commerceCountryId) {
		return findByCommerceCountryId(commerceCountryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId, int start, int end) {
		return findByCommerceCountryId(commerceCountryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return findByCommerceCountryId(commerceCountryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where commerceCountryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByCommerceCountryId(
		long commerceCountryId, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID;
			finderArgs = new Object[] { commerceCountryId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_COMMERCECOUNTRYID;
			finderArgs = new Object[] {
					commerceCountryId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceAddress> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAddress>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if ((commerceCountryId != commerceAddress.getCommerceCountryId())) {
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

			query.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			query.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceCountryId);

				if (!pagination) {
					list = (List<CommerceAddress>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAddress>)QueryUtil.list(q,
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
	 * Returns the first commerce address in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = fetchByCommerceCountryId_First(commerceCountryId,
				orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceCountryId=");
		msg.append(commerceCountryId);

		msg.append("}");

		throw new NoSuchAddressException(msg.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByCommerceCountryId_First(
		long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator) {
		List<CommerceAddress> list = findByCommerceCountryId(commerceCountryId,
				0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = fetchByCommerceCountryId_Last(commerceCountryId,
				orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("commerceCountryId=");
		msg.append(commerceCountryId);

		msg.append("}");

		throw new NoSuchAddressException(msg.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByCommerceCountryId_Last(
		long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator) {
		int count = countByCommerceCountryId(commerceCountryId);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByCommerceCountryId(commerceCountryId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where commerceCountryId = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param commerceCountryId the commerce country ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByCommerceCountryId_PrevAndNext(
		long commerceAddressId, long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByCommerceCountryId_PrevAndNext(session,
					commerceAddress, commerceCountryId, orderByComparator, true);

			array[1] = commerceAddress;

			array[2] = getByCommerceCountryId_PrevAndNext(session,
					commerceAddress, commerceCountryId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceAddress getByCommerceCountryId_PrevAndNext(
		Session session, CommerceAddress commerceAddress,
		long commerceCountryId,
		OrderByComparator<CommerceAddress> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		query.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

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
			query.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(commerceCountryId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAddress);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAddress> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where commerceCountryId = &#63; from the database.
	 *
	 * @param commerceCountryId the commerce country ID
	 */
	@Override
	public void removeByCommerceCountryId(long commerceCountryId) {
		for (CommerceAddress commerceAddress : findByCommerceCountryId(
				commerceCountryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where commerceCountryId = &#63;.
	 *
	 * @param commerceCountryId the commerce country ID
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByCommerceCountryId(long commerceCountryId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID;

		Object[] finderArgs = new Object[] { commerceCountryId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			query.append(_FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(commerceCountryId);

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

	private static final String _FINDER_COLUMN_COMMERCECOUNTRYID_COMMERCECOUNTRYID_2 =
		"commerceAddress.commerceCountryId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A = new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_A",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A = new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A",
			new String[] { Long.class.getName(), Long.class.getName() },
			CommerceAddressModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceAddressModelImpl.ADDRESSUSERID_COLUMN_BITMASK |
			CommerceAddressModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A = new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce addresses where groupId = &#63; and addressUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_A(long groupId, long addressUserId) {
		return findByG_A(groupId, addressUserId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses where groupId = &#63; and addressUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_A(long groupId, long addressUserId,
		int start, int end) {
		return findByG_A(groupId, addressUserId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where groupId = &#63; and addressUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_A(long groupId, long addressUserId,
		int start, int end, OrderByComparator<CommerceAddress> orderByComparator) {
		return findByG_A(groupId, addressUserId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where groupId = &#63; and addressUserId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_A(long groupId, long addressUserId,
		int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] { groupId, addressUserId };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A;
			finderArgs = new Object[] {
					groupId, addressUserId,
					
					start, end, orderByComparator
				};
		}

		List<CommerceAddress> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAddress>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if ((groupId != commerceAddress.getGroupId()) ||
							(addressUserId != commerceAddress.getAddressUserId())) {
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

			query.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ADDRESSUSERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(addressUserId);

				if (!pagination) {
					list = (List<CommerceAddress>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAddress>)QueryUtil.list(q,
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
	 * Returns the first commerce address in the ordered set where groupId = &#63; and addressUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByG_A_First(long groupId, long addressUserId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = fetchByG_A_First(groupId,
				addressUserId, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", addressUserId=");
		msg.append(addressUserId);

		msg.append("}");

		throw new NoSuchAddressException(msg.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where groupId = &#63; and addressUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByG_A_First(long groupId, long addressUserId,
		OrderByComparator<CommerceAddress> orderByComparator) {
		List<CommerceAddress> list = findByG_A(groupId, addressUserId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where groupId = &#63; and addressUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByG_A_Last(long groupId, long addressUserId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = fetchByG_A_Last(groupId,
				addressUserId, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", addressUserId=");
		msg.append(addressUserId);

		msg.append("}");

		throw new NoSuchAddressException(msg.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where groupId = &#63; and addressUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByG_A_Last(long groupId, long addressUserId,
		OrderByComparator<CommerceAddress> orderByComparator) {
		int count = countByG_A(groupId, addressUserId);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByG_A(groupId, addressUserId,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where groupId = &#63; and addressUserId = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByG_A_PrevAndNext(long commerceAddressId,
		long groupId, long addressUserId,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByG_A_PrevAndNext(session, commerceAddress, groupId,
					addressUserId, orderByComparator, true);

			array[1] = commerceAddress;

			array[2] = getByG_A_PrevAndNext(session, commerceAddress, groupId,
					addressUserId, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceAddress getByG_A_PrevAndNext(Session session,
		CommerceAddress commerceAddress, long groupId, long addressUserId,
		OrderByComparator<CommerceAddress> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		query.append(_FINDER_COLUMN_G_A_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_ADDRESSUSERID_2);

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
			query.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(addressUserId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAddress);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAddress> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where groupId = &#63; and addressUserId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 */
	@Override
	public void removeByG_A(long groupId, long addressUserId) {
		for (CommerceAddress commerceAddress : findByG_A(groupId,
				addressUserId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where groupId = &#63; and addressUserId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByG_A(long groupId, long addressUserId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A;

		Object[] finderArgs = new Object[] { groupId, addressUserId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			query.append(_FINDER_COLUMN_G_A_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_ADDRESSUSERID_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(addressUserId);

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

	private static final String _FINDER_COLUMN_G_A_GROUPID_2 = "commerceAddress.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_ADDRESSUSERID_2 = "commerceAddress.addressUserId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A_DB = new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_A_DB",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_DB =
		new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A_DB",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			CommerceAddressModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceAddressModelImpl.ADDRESSUSERID_COLUMN_BITMASK |
			CommerceAddressModelImpl.DEFAULTBILLING_COLUMN_BITMASK |
			CommerceAddressModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A_DB = new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A_DB",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the commerce addresses where groupId = &#63; and addressUserId = &#63; and defaultBilling = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultBilling the default billing
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_A_DB(long groupId, long addressUserId,
		boolean defaultBilling) {
		return findByG_A_DB(groupId, addressUserId, defaultBilling,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses where groupId = &#63; and addressUserId = &#63; and defaultBilling = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultBilling the default billing
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_A_DB(long groupId, long addressUserId,
		boolean defaultBilling, int start, int end) {
		return findByG_A_DB(groupId, addressUserId, defaultBilling, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where groupId = &#63; and addressUserId = &#63; and defaultBilling = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultBilling the default billing
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_A_DB(long groupId, long addressUserId,
		boolean defaultBilling, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return findByG_A_DB(groupId, addressUserId, defaultBilling, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where groupId = &#63; and addressUserId = &#63; and defaultBilling = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultBilling the default billing
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_A_DB(long groupId, long addressUserId,
		boolean defaultBilling, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_DB;
			finderArgs = new Object[] { groupId, addressUserId, defaultBilling };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A_DB;
			finderArgs = new Object[] {
					groupId, addressUserId, defaultBilling,
					
					start, end, orderByComparator
				};
		}

		List<CommerceAddress> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAddress>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if ((groupId != commerceAddress.getGroupId()) ||
							(addressUserId != commerceAddress.getAddressUserId()) ||
							(defaultBilling != commerceAddress.getDefaultBilling())) {
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

			query.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			query.append(_FINDER_COLUMN_G_A_DB_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_DB_ADDRESSUSERID_2);

			query.append(_FINDER_COLUMN_G_A_DB_DEFAULTBILLING_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(addressUserId);

				qPos.add(defaultBilling);

				if (!pagination) {
					list = (List<CommerceAddress>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAddress>)QueryUtil.list(q,
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
	 * Returns the first commerce address in the ordered set where groupId = &#63; and addressUserId = &#63; and defaultBilling = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultBilling the default billing
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByG_A_DB_First(long groupId, long addressUserId,
		boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = fetchByG_A_DB_First(groupId,
				addressUserId, defaultBilling, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", addressUserId=");
		msg.append(addressUserId);

		msg.append(", defaultBilling=");
		msg.append(defaultBilling);

		msg.append("}");

		throw new NoSuchAddressException(msg.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where groupId = &#63; and addressUserId = &#63; and defaultBilling = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultBilling the default billing
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByG_A_DB_First(long groupId,
		long addressUserId, boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator) {
		List<CommerceAddress> list = findByG_A_DB(groupId, addressUserId,
				defaultBilling, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where groupId = &#63; and addressUserId = &#63; and defaultBilling = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultBilling the default billing
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByG_A_DB_Last(long groupId, long addressUserId,
		boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = fetchByG_A_DB_Last(groupId,
				addressUserId, defaultBilling, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", addressUserId=");
		msg.append(addressUserId);

		msg.append(", defaultBilling=");
		msg.append(defaultBilling);

		msg.append("}");

		throw new NoSuchAddressException(msg.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where groupId = &#63; and addressUserId = &#63; and defaultBilling = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultBilling the default billing
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByG_A_DB_Last(long groupId, long addressUserId,
		boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator) {
		int count = countByG_A_DB(groupId, addressUserId, defaultBilling);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByG_A_DB(groupId, addressUserId,
				defaultBilling, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where groupId = &#63; and addressUserId = &#63; and defaultBilling = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultBilling the default billing
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByG_A_DB_PrevAndNext(long commerceAddressId,
		long groupId, long addressUserId, boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByG_A_DB_PrevAndNext(session, commerceAddress,
					groupId, addressUserId, defaultBilling, orderByComparator,
					true);

			array[1] = commerceAddress;

			array[2] = getByG_A_DB_PrevAndNext(session, commerceAddress,
					groupId, addressUserId, defaultBilling, orderByComparator,
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

	protected CommerceAddress getByG_A_DB_PrevAndNext(Session session,
		CommerceAddress commerceAddress, long groupId, long addressUserId,
		boolean defaultBilling,
		OrderByComparator<CommerceAddress> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		query.append(_FINDER_COLUMN_G_A_DB_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_DB_ADDRESSUSERID_2);

		query.append(_FINDER_COLUMN_G_A_DB_DEFAULTBILLING_2);

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
			query.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(addressUserId);

		qPos.add(defaultBilling);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAddress);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAddress> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where groupId = &#63; and addressUserId = &#63; and defaultBilling = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultBilling the default billing
	 */
	@Override
	public void removeByG_A_DB(long groupId, long addressUserId,
		boolean defaultBilling) {
		for (CommerceAddress commerceAddress : findByG_A_DB(groupId,
				addressUserId, defaultBilling, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where groupId = &#63; and addressUserId = &#63; and defaultBilling = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultBilling the default billing
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByG_A_DB(long groupId, long addressUserId,
		boolean defaultBilling) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A_DB;

		Object[] finderArgs = new Object[] {
				groupId, addressUserId, defaultBilling
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			query.append(_FINDER_COLUMN_G_A_DB_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_DB_ADDRESSUSERID_2);

			query.append(_FINDER_COLUMN_G_A_DB_DEFAULTBILLING_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(addressUserId);

				qPos.add(defaultBilling);

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

	private static final String _FINDER_COLUMN_G_A_DB_GROUPID_2 = "commerceAddress.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_DB_ADDRESSUSERID_2 = "commerceAddress.addressUserId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_DB_DEFAULTBILLING_2 = "commerceAddress.defaultBilling = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A_DS = new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class, FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByG_A_DS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_DS =
		new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED,
			CommerceAddressImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByG_A_DS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			CommerceAddressModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceAddressModelImpl.ADDRESSUSERID_COLUMN_BITMASK |
			CommerceAddressModelImpl.DEFAULTSHIPPING_COLUMN_BITMASK |
			CommerceAddressModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_A_DS = new FinderPath(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_A_DS",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			});

	/**
	 * Returns all the commerce addresses where groupId = &#63; and addressUserId = &#63; and defaultShipping = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultShipping the default shipping
	 * @return the matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_A_DS(long groupId, long addressUserId,
		boolean defaultShipping) {
		return findByG_A_DS(groupId, addressUserId, defaultShipping,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses where groupId = &#63; and addressUserId = &#63; and defaultShipping = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultShipping the default shipping
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_A_DS(long groupId, long addressUserId,
		boolean defaultShipping, int start, int end) {
		return findByG_A_DS(groupId, addressUserId, defaultShipping, start,
			end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where groupId = &#63; and addressUserId = &#63; and defaultShipping = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultShipping the default shipping
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_A_DS(long groupId, long addressUserId,
		boolean defaultShipping, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return findByG_A_DS(groupId, addressUserId, defaultShipping, start,
			end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses where groupId = &#63; and addressUserId = &#63; and defaultShipping = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultShipping the default shipping
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce addresses
	 */
	@Override
	public List<CommerceAddress> findByG_A_DS(long groupId, long addressUserId,
		boolean defaultShipping, int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_DS;
			finderArgs = new Object[] { groupId, addressUserId, defaultShipping };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_G_A_DS;
			finderArgs = new Object[] {
					groupId, addressUserId, defaultShipping,
					
					start, end, orderByComparator
				};
		}

		List<CommerceAddress> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAddress>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAddress commerceAddress : list) {
					if ((groupId != commerceAddress.getGroupId()) ||
							(addressUserId != commerceAddress.getAddressUserId()) ||
							(defaultShipping != commerceAddress.getDefaultShipping())) {
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

			query.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

			query.append(_FINDER_COLUMN_G_A_DS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_DS_ADDRESSUSERID_2);

			query.append(_FINDER_COLUMN_G_A_DS_DEFAULTSHIPPING_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(addressUserId);

				qPos.add(defaultShipping);

				if (!pagination) {
					list = (List<CommerceAddress>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAddress>)QueryUtil.list(q,
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
	 * Returns the first commerce address in the ordered set where groupId = &#63; and addressUserId = &#63; and defaultShipping = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultShipping the default shipping
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByG_A_DS_First(long groupId, long addressUserId,
		boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = fetchByG_A_DS_First(groupId,
				addressUserId, defaultShipping, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", addressUserId=");
		msg.append(addressUserId);

		msg.append(", defaultShipping=");
		msg.append(defaultShipping);

		msg.append("}");

		throw new NoSuchAddressException(msg.toString());
	}

	/**
	 * Returns the first commerce address in the ordered set where groupId = &#63; and addressUserId = &#63; and defaultShipping = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultShipping the default shipping
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByG_A_DS_First(long groupId,
		long addressUserId, boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator) {
		List<CommerceAddress> list = findByG_A_DS(groupId, addressUserId,
				defaultShipping, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce address in the ordered set where groupId = &#63; and addressUserId = &#63; and defaultShipping = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultShipping the default shipping
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address
	 * @throws NoSuchAddressException if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress findByG_A_DS_Last(long groupId, long addressUserId,
		boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = fetchByG_A_DS_Last(groupId,
				addressUserId, defaultShipping, orderByComparator);

		if (commerceAddress != null) {
			return commerceAddress;
		}

		StringBundler msg = new StringBundler(8);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append(", addressUserId=");
		msg.append(addressUserId);

		msg.append(", defaultShipping=");
		msg.append(defaultShipping);

		msg.append("}");

		throw new NoSuchAddressException(msg.toString());
	}

	/**
	 * Returns the last commerce address in the ordered set where groupId = &#63; and addressUserId = &#63; and defaultShipping = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultShipping the default shipping
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce address, or <code>null</code> if a matching commerce address could not be found
	 */
	@Override
	public CommerceAddress fetchByG_A_DS_Last(long groupId, long addressUserId,
		boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator) {
		int count = countByG_A_DS(groupId, addressUserId, defaultShipping);

		if (count == 0) {
			return null;
		}

		List<CommerceAddress> list = findByG_A_DS(groupId, addressUserId,
				defaultShipping, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce addresses before and after the current commerce address in the ordered set where groupId = &#63; and addressUserId = &#63; and defaultShipping = &#63;.
	 *
	 * @param commerceAddressId the primary key of the current commerce address
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultShipping the default shipping
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress[] findByG_A_DS_PrevAndNext(long commerceAddressId,
		long groupId, long addressUserId, boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = findByPrimaryKey(commerceAddressId);

		Session session = null;

		try {
			session = openSession();

			CommerceAddress[] array = new CommerceAddressImpl[3];

			array[0] = getByG_A_DS_PrevAndNext(session, commerceAddress,
					groupId, addressUserId, defaultShipping, orderByComparator,
					true);

			array[1] = commerceAddress;

			array[2] = getByG_A_DS_PrevAndNext(session, commerceAddress,
					groupId, addressUserId, defaultShipping, orderByComparator,
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

	protected CommerceAddress getByG_A_DS_PrevAndNext(Session session,
		CommerceAddress commerceAddress, long groupId, long addressUserId,
		boolean defaultShipping,
		OrderByComparator<CommerceAddress> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(6 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(5);
		}

		query.append(_SQL_SELECT_COMMERCEADDRESS_WHERE);

		query.append(_FINDER_COLUMN_G_A_DS_GROUPID_2);

		query.append(_FINDER_COLUMN_G_A_DS_ADDRESSUSERID_2);

		query.append(_FINDER_COLUMN_G_A_DS_DEFAULTSHIPPING_2);

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
			query.append(CommerceAddressModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		qPos.add(addressUserId);

		qPos.add(defaultShipping);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceAddress);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceAddress> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce addresses where groupId = &#63; and addressUserId = &#63; and defaultShipping = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultShipping the default shipping
	 */
	@Override
	public void removeByG_A_DS(long groupId, long addressUserId,
		boolean defaultShipping) {
		for (CommerceAddress commerceAddress : findByG_A_DS(groupId,
				addressUserId, defaultShipping, QueryUtil.ALL_POS,
				QueryUtil.ALL_POS, null)) {
			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses where groupId = &#63; and addressUserId = &#63; and defaultShipping = &#63;.
	 *
	 * @param groupId the group ID
	 * @param addressUserId the address user ID
	 * @param defaultShipping the default shipping
	 * @return the number of matching commerce addresses
	 */
	@Override
	public int countByG_A_DS(long groupId, long addressUserId,
		boolean defaultShipping) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_A_DS;

		Object[] finderArgs = new Object[] {
				groupId, addressUserId, defaultShipping
			};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_COMMERCEADDRESS_WHERE);

			query.append(_FINDER_COLUMN_G_A_DS_GROUPID_2);

			query.append(_FINDER_COLUMN_G_A_DS_ADDRESSUSERID_2);

			query.append(_FINDER_COLUMN_G_A_DS_DEFAULTSHIPPING_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(addressUserId);

				qPos.add(defaultShipping);

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

	private static final String _FINDER_COLUMN_G_A_DS_GROUPID_2 = "commerceAddress.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_DS_ADDRESSUSERID_2 = "commerceAddress.addressUserId = ? AND ";
	private static final String _FINDER_COLUMN_G_A_DS_DEFAULTSHIPPING_2 = "commerceAddress.defaultShipping = ?";

	public CommerceAddressPersistenceImpl() {
		setModelClass(CommerceAddress.class);
	}

	/**
	 * Caches the commerce address in the entity cache if it is enabled.
	 *
	 * @param commerceAddress the commerce address
	 */
	@Override
	public void cacheResult(CommerceAddress commerceAddress) {
		entityCache.putResult(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressImpl.class, commerceAddress.getPrimaryKey(),
			commerceAddress);

		commerceAddress.resetOriginalValues();
	}

	/**
	 * Caches the commerce addresses in the entity cache if it is enabled.
	 *
	 * @param commerceAddresses the commerce addresses
	 */
	@Override
	public void cacheResult(List<CommerceAddress> commerceAddresses) {
		for (CommerceAddress commerceAddress : commerceAddresses) {
			if (entityCache.getResult(
						CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
						CommerceAddressImpl.class,
						commerceAddress.getPrimaryKey()) == null) {
				cacheResult(commerceAddress);
			}
			else {
				commerceAddress.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce addresses.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceAddressImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce address.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceAddress commerceAddress) {
		entityCache.removeResult(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressImpl.class, commerceAddress.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@Override
	public void clearCache(List<CommerceAddress> commerceAddresses) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceAddress commerceAddress : commerceAddresses) {
			entityCache.removeResult(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
				CommerceAddressImpl.class, commerceAddress.getPrimaryKey());
		}
	}

	/**
	 * Creates a new commerce address with the primary key. Does not add the commerce address to the database.
	 *
	 * @param commerceAddressId the primary key for the new commerce address
	 * @return the new commerce address
	 */
	@Override
	public CommerceAddress create(long commerceAddressId) {
		CommerceAddress commerceAddress = new CommerceAddressImpl();

		commerceAddress.setNew(true);
		commerceAddress.setPrimaryKey(commerceAddressId);

		commerceAddress.setCompanyId(companyProvider.getCompanyId());

		return commerceAddress;
	}

	/**
	 * Removes the commerce address with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAddressId the primary key of the commerce address
	 * @return the commerce address that was removed
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress remove(long commerceAddressId)
		throws NoSuchAddressException {
		return remove((Serializable)commerceAddressId);
	}

	/**
	 * Removes the commerce address with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce address
	 * @return the commerce address that was removed
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress remove(Serializable primaryKey)
		throws NoSuchAddressException {
		Session session = null;

		try {
			session = openSession();

			CommerceAddress commerceAddress = (CommerceAddress)session.get(CommerceAddressImpl.class,
					primaryKey);

			if (commerceAddress == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAddressException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceAddress);
		}
		catch (NoSuchAddressException nsee) {
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
	protected CommerceAddress removeImpl(CommerceAddress commerceAddress) {
		commerceAddress = toUnwrappedModel(commerceAddress);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceAddress)) {
				commerceAddress = (CommerceAddress)session.get(CommerceAddressImpl.class,
						commerceAddress.getPrimaryKeyObj());
			}

			if (commerceAddress != null) {
				session.delete(commerceAddress);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceAddress != null) {
			clearCache(commerceAddress);
		}

		return commerceAddress;
	}

	@Override
	public CommerceAddress updateImpl(CommerceAddress commerceAddress) {
		commerceAddress = toUnwrappedModel(commerceAddress);

		boolean isNew = commerceAddress.isNew();

		CommerceAddressModelImpl commerceAddressModelImpl = (CommerceAddressModelImpl)commerceAddress;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceAddress.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceAddress.setCreateDate(now);
			}
			else {
				commerceAddress.setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!commerceAddressModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceAddress.setModifiedDate(now);
			}
			else {
				commerceAddress.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceAddress.isNew()) {
				session.save(commerceAddress);

				commerceAddress.setNew(false);
			}
			else {
				commerceAddress = (CommerceAddress)session.merge(commerceAddress);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceAddressModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] {
					commerceAddressModelImpl.getAddressUserId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_ADDRESSUSERID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ADDRESSUSERID,
				args);

			args = new Object[] { commerceAddressModelImpl.getCommerceRegionId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEREGIONID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEREGIONID,
				args);

			args = new Object[] { commerceAddressModelImpl.getCommerceCountryId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID,
				args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID,
				args);

			args = new Object[] {
					commerceAddressModelImpl.getGroupId(),
					commerceAddressModelImpl.getAddressUserId()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
				args);

			args = new Object[] {
					commerceAddressModelImpl.getGroupId(),
					commerceAddressModelImpl.getAddressUserId(),
					commerceAddressModelImpl.getDefaultBilling()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A_DB, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_DB,
				args);

			args = new Object[] {
					commerceAddressModelImpl.getGroupId(),
					commerceAddressModelImpl.getAddressUserId(),
					commerceAddressModelImpl.getDefaultShipping()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A_DS, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_DS,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceAddressModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ADDRESSUSERID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAddressModelImpl.getOriginalAddressUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ADDRESSUSERID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ADDRESSUSERID,
					args);

				args = new Object[] { commerceAddressModelImpl.getAddressUserId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_ADDRESSUSERID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_ADDRESSUSERID,
					args);
			}

			if ((commerceAddressModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEREGIONID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAddressModelImpl.getOriginalCommerceRegionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEREGIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEREGIONID,
					args);

				args = new Object[] {
						commerceAddressModelImpl.getCommerceRegionId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCEREGIONID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCEREGIONID,
					args);
			}

			if ((commerceAddressModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAddressModelImpl.getOriginalCommerceCountryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID,
					args);

				args = new Object[] {
						commerceAddressModelImpl.getCommerceCountryId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_COMMERCECOUNTRYID,
					args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_COMMERCECOUNTRYID,
					args);
			}

			if ((commerceAddressModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAddressModelImpl.getOriginalGroupId(),
						commerceAddressModelImpl.getOriginalAddressUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);

				args = new Object[] {
						commerceAddressModelImpl.getGroupId(),
						commerceAddressModelImpl.getAddressUserId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A,
					args);
			}

			if ((commerceAddressModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_DB.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAddressModelImpl.getOriginalGroupId(),
						commerceAddressModelImpl.getOriginalAddressUserId(),
						commerceAddressModelImpl.getOriginalDefaultBilling()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A_DB, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_DB,
					args);

				args = new Object[] {
						commerceAddressModelImpl.getGroupId(),
						commerceAddressModelImpl.getAddressUserId(),
						commerceAddressModelImpl.getDefaultBilling()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A_DB, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_DB,
					args);
			}

			if ((commerceAddressModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_DS.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceAddressModelImpl.getOriginalGroupId(),
						commerceAddressModelImpl.getOriginalAddressUserId(),
						commerceAddressModelImpl.getOriginalDefaultShipping()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A_DS, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_DS,
					args);

				args = new Object[] {
						commerceAddressModelImpl.getGroupId(),
						commerceAddressModelImpl.getAddressUserId(),
						commerceAddressModelImpl.getDefaultShipping()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_G_A_DS, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_G_A_DS,
					args);
			}
		}

		entityCache.putResult(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
			CommerceAddressImpl.class, commerceAddress.getPrimaryKey(),
			commerceAddress, false);

		commerceAddress.resetOriginalValues();

		return commerceAddress;
	}

	protected CommerceAddress toUnwrappedModel(CommerceAddress commerceAddress) {
		if (commerceAddress instanceof CommerceAddressImpl) {
			return commerceAddress;
		}

		CommerceAddressImpl commerceAddressImpl = new CommerceAddressImpl();

		commerceAddressImpl.setNew(commerceAddress.isNew());
		commerceAddressImpl.setPrimaryKey(commerceAddress.getPrimaryKey());

		commerceAddressImpl.setCommerceAddressId(commerceAddress.getCommerceAddressId());
		commerceAddressImpl.setGroupId(commerceAddress.getGroupId());
		commerceAddressImpl.setCompanyId(commerceAddress.getCompanyId());
		commerceAddressImpl.setUserId(commerceAddress.getUserId());
		commerceAddressImpl.setUserName(commerceAddress.getUserName());
		commerceAddressImpl.setCreateDate(commerceAddress.getCreateDate());
		commerceAddressImpl.setModifiedDate(commerceAddress.getModifiedDate());
		commerceAddressImpl.setAddressUserId(commerceAddress.getAddressUserId());
		commerceAddressImpl.setName(commerceAddress.getName());
		commerceAddressImpl.setDescription(commerceAddress.getDescription());
		commerceAddressImpl.setStreet1(commerceAddress.getStreet1());
		commerceAddressImpl.setStreet2(commerceAddress.getStreet2());
		commerceAddressImpl.setStreet3(commerceAddress.getStreet3());
		commerceAddressImpl.setCity(commerceAddress.getCity());
		commerceAddressImpl.setZip(commerceAddress.getZip());
		commerceAddressImpl.setCommerceRegionId(commerceAddress.getCommerceRegionId());
		commerceAddressImpl.setCommerceCountryId(commerceAddress.getCommerceCountryId());
		commerceAddressImpl.setLatitude(commerceAddress.getLatitude());
		commerceAddressImpl.setLongitude(commerceAddress.getLongitude());
		commerceAddressImpl.setPhoneNumber(commerceAddress.getPhoneNumber());
		commerceAddressImpl.setDefaultBilling(commerceAddress.isDefaultBilling());
		commerceAddressImpl.setDefaultShipping(commerceAddress.isDefaultShipping());

		return commerceAddressImpl;
	}

	/**
	 * Returns the commerce address with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce address
	 * @return the commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAddressException {
		CommerceAddress commerceAddress = fetchByPrimaryKey(primaryKey);

		if (commerceAddress == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAddressException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceAddress;
	}

	/**
	 * Returns the commerce address with the primary key or throws a {@link NoSuchAddressException} if it could not be found.
	 *
	 * @param commerceAddressId the primary key of the commerce address
	 * @return the commerce address
	 * @throws NoSuchAddressException if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress findByPrimaryKey(long commerceAddressId)
		throws NoSuchAddressException {
		return findByPrimaryKey((Serializable)commerceAddressId);
	}

	/**
	 * Returns the commerce address with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce address
	 * @return the commerce address, or <code>null</code> if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
				CommerceAddressImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceAddress commerceAddress = (CommerceAddress)serializable;

		if (commerceAddress == null) {
			Session session = null;

			try {
				session = openSession();

				commerceAddress = (CommerceAddress)session.get(CommerceAddressImpl.class,
						primaryKey);

				if (commerceAddress != null) {
					cacheResult(commerceAddress);
				}
				else {
					entityCache.putResult(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
						CommerceAddressImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
					CommerceAddressImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceAddress;
	}

	/**
	 * Returns the commerce address with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAddressId the primary key of the commerce address
	 * @return the commerce address, or <code>null</code> if a commerce address with the primary key could not be found
	 */
	@Override
	public CommerceAddress fetchByPrimaryKey(long commerceAddressId) {
		return fetchByPrimaryKey((Serializable)commerceAddressId);
	}

	@Override
	public Map<Serializable, CommerceAddress> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceAddress> map = new HashMap<Serializable, CommerceAddress>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceAddress commerceAddress = fetchByPrimaryKey(primaryKey);

			if (commerceAddress != null) {
				map.put(primaryKey, commerceAddress);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
					CommerceAddressImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceAddress)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEADDRESS_WHERE_PKS_IN);

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

			for (CommerceAddress commerceAddress : (List<CommerceAddress>)q.list()) {
				map.put(commerceAddress.getPrimaryKeyObj(), commerceAddress);

				cacheResult(commerceAddress);

				uncachedPrimaryKeys.remove(commerceAddress.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceAddressModelImpl.ENTITY_CACHE_ENABLED,
					CommerceAddressImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce addresses.
	 *
	 * @return the commerce addresses
	 */
	@Override
	public List<CommerceAddress> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce addresses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @return the range of commerce addresses
	 */
	@Override
	public List<CommerceAddress> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce addresses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce addresses
	 */
	@Override
	public List<CommerceAddress> findAll(int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce addresses.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceAddressModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce addresses
	 * @param end the upper bound of the range of commerce addresses (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce addresses
	 */
	@Override
	public List<CommerceAddress> findAll(int start, int end,
		OrderByComparator<CommerceAddress> orderByComparator,
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

		List<CommerceAddress> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceAddress>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEADDRESS);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEADDRESS;

				if (pagination) {
					sql = sql.concat(CommerceAddressModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceAddress>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceAddress>)QueryUtil.list(q,
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
	 * Removes all the commerce addresses from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceAddress commerceAddress : findAll()) {
			remove(commerceAddress);
		}
	}

	/**
	 * Returns the number of commerce addresses.
	 *
	 * @return the number of commerce addresses
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEADDRESS);

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
		return CommerceAddressModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce address persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceAddressImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEADDRESS = "SELECT commerceAddress FROM CommerceAddress commerceAddress";
	private static final String _SQL_SELECT_COMMERCEADDRESS_WHERE_PKS_IN = "SELECT commerceAddress FROM CommerceAddress commerceAddress WHERE commerceAddressId IN (";
	private static final String _SQL_SELECT_COMMERCEADDRESS_WHERE = "SELECT commerceAddress FROM CommerceAddress commerceAddress WHERE ";
	private static final String _SQL_COUNT_COMMERCEADDRESS = "SELECT COUNT(commerceAddress) FROM CommerceAddress commerceAddress";
	private static final String _SQL_COUNT_COMMERCEADDRESS_WHERE = "SELECT COUNT(commerceAddress) FROM CommerceAddress commerceAddress WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceAddress.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceAddress exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceAddress exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceAddressPersistenceImpl.class);
}