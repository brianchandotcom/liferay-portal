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

package com.liferay.commerce.vat.service.persistence.impl;

import aQute.bnd.annotation.ProviderType;

import com.liferay.commerce.vat.exception.NoSuchVatNumberException;
import com.liferay.commerce.vat.model.CommerceVatNumber;
import com.liferay.commerce.vat.model.impl.CommerceVatNumberImpl;
import com.liferay.commerce.vat.model.impl.CommerceVatNumberModelImpl;
import com.liferay.commerce.vat.service.persistence.CommerceVatNumberPersistence;

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
 * The persistence implementation for the commerce vat number service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @see CommerceVatNumberPersistence
 * @see com.liferay.commerce.vat.service.persistence.CommerceVatNumberUtil
 * @generated
 */
@ProviderType
public class CommerceVatNumberPersistenceImpl extends BasePersistenceImpl<CommerceVatNumber>
	implements CommerceVatNumberPersistence {
	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use {@link CommerceVatNumberUtil} to access the commerce vat number persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY = CommerceVatNumberImpl.class.getName();
	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List1";
	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION = FINDER_CLASS_NAME_ENTITY +
		".List2";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_ALL = new FinderPath(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberModelImpl.FINDER_CACHE_ENABLED,
			CommerceVatNumberImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL = new FinderPath(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberModelImpl.FINDER_CACHE_ENABLED,
			CommerceVatNumberImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0]);
	public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll", new String[0]);
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_GROUPID = new FinderPath(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberModelImpl.FINDER_CACHE_ENABLED,
			CommerceVatNumberImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByGroupId",
			new String[] {
				Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID =
		new FinderPath(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberModelImpl.FINDER_CACHE_ENABLED,
			CommerceVatNumberImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByGroupId",
			new String[] { Long.class.getName() },
			CommerceVatNumberModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceVatNumberModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_GROUPID = new FinderPath(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByGroupId",
			new String[] { Long.class.getName() });

	/**
	 * Returns all the commerce vat numbers where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the matching commerce vat numbers
	 */
	@Override
	public List<CommerceVatNumber> findByGroupId(long groupId) {
		return findByGroupId(groupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce vat numbers where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce vat numbers
	 * @param end the upper bound of the range of commerce vat numbers (not inclusive)
	 * @return the range of matching commerce vat numbers
	 */
	@Override
	public List<CommerceVatNumber> findByGroupId(long groupId, int start,
		int end) {
		return findByGroupId(groupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce vat numbers where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce vat numbers
	 * @param end the upper bound of the range of commerce vat numbers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce vat numbers
	 */
	@Override
	public List<CommerceVatNumber> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceVatNumber> orderByComparator) {
		return findByGroupId(groupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce vat numbers where groupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param groupId the group ID
	 * @param start the lower bound of the range of commerce vat numbers
	 * @param end the upper bound of the range of commerce vat numbers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce vat numbers
	 */
	@Override
	public List<CommerceVatNumber> findByGroupId(long groupId, int start,
		int end, OrderByComparator<CommerceVatNumber> orderByComparator,
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

		List<CommerceVatNumber> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceVatNumber>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceVatNumber commerceVatNumber : list) {
					if ((groupId != commerceVatNumber.getGroupId())) {
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

			query.append(_SQL_SELECT_COMMERCEVATNUMBER_WHERE);

			query.append(_FINDER_COLUMN_GROUPID_GROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceVatNumberModelImpl.ORDER_BY_JPQL);
			}

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				if (!pagination) {
					list = (List<CommerceVatNumber>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceVatNumber>)QueryUtil.list(q,
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
	 * Returns the first commerce vat number in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce vat number
	 * @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	 */
	@Override
	public CommerceVatNumber findByGroupId_First(long groupId,
		OrderByComparator<CommerceVatNumber> orderByComparator)
		throws NoSuchVatNumberException {
		CommerceVatNumber commerceVatNumber = fetchByGroupId_First(groupId,
				orderByComparator);

		if (commerceVatNumber != null) {
			return commerceVatNumber;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchVatNumberException(msg.toString());
	}

	/**
	 * Returns the first commerce vat number in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	 */
	@Override
	public CommerceVatNumber fetchByGroupId_First(long groupId,
		OrderByComparator<CommerceVatNumber> orderByComparator) {
		List<CommerceVatNumber> list = findByGroupId(groupId, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce vat number in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce vat number
	 * @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	 */
	@Override
	public CommerceVatNumber findByGroupId_Last(long groupId,
		OrderByComparator<CommerceVatNumber> orderByComparator)
		throws NoSuchVatNumberException {
		CommerceVatNumber commerceVatNumber = fetchByGroupId_Last(groupId,
				orderByComparator);

		if (commerceVatNumber != null) {
			return commerceVatNumber;
		}

		StringBundler msg = new StringBundler(4);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("groupId=");
		msg.append(groupId);

		msg.append("}");

		throw new NoSuchVatNumberException(msg.toString());
	}

	/**
	 * Returns the last commerce vat number in the ordered set where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	 */
	@Override
	public CommerceVatNumber fetchByGroupId_Last(long groupId,
		OrderByComparator<CommerceVatNumber> orderByComparator) {
		int count = countByGroupId(groupId);

		if (count == 0) {
			return null;
		}

		List<CommerceVatNumber> list = findByGroupId(groupId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce vat numbers before and after the current commerce vat number in the ordered set where groupId = &#63;.
	 *
	 * @param commerceVatNumberId the primary key of the current commerce vat number
	 * @param groupId the group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce vat number
	 * @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	 */
	@Override
	public CommerceVatNumber[] findByGroupId_PrevAndNext(
		long commerceVatNumberId, long groupId,
		OrderByComparator<CommerceVatNumber> orderByComparator)
		throws NoSuchVatNumberException {
		CommerceVatNumber commerceVatNumber = findByPrimaryKey(commerceVatNumberId);

		Session session = null;

		try {
			session = openSession();

			CommerceVatNumber[] array = new CommerceVatNumberImpl[3];

			array[0] = getByGroupId_PrevAndNext(session, commerceVatNumber,
					groupId, orderByComparator, true);

			array[1] = commerceVatNumber;

			array[2] = getByGroupId_PrevAndNext(session, commerceVatNumber,
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

	protected CommerceVatNumber getByGroupId_PrevAndNext(Session session,
		CommerceVatNumber commerceVatNumber, long groupId,
		OrderByComparator<CommerceVatNumber> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(4 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(3);
		}

		query.append(_SQL_SELECT_COMMERCEVATNUMBER_WHERE);

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
			query.append(CommerceVatNumberModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(groupId);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceVatNumber);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceVatNumber> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce vat numbers where groupId = &#63; from the database.
	 *
	 * @param groupId the group ID
	 */
	@Override
	public void removeByGroupId(long groupId) {
		for (CommerceVatNumber commerceVatNumber : findByGroupId(groupId,
				QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceVatNumber);
		}
	}

	/**
	 * Returns the number of commerce vat numbers where groupId = &#63;.
	 *
	 * @param groupId the group ID
	 * @return the number of matching commerce vat numbers
	 */
	@Override
	public int countByGroupId(long groupId) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_GROUPID;

		Object[] finderArgs = new Object[] { groupId };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(2);

			query.append(_SQL_COUNT_COMMERCEVATNUMBER_WHERE);

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

	private static final String _FINDER_COLUMN_GROUPID_GROUPID_2 = "commerceVatNumber.groupId = ?";
	public static final FinderPath FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C = new FinderPath(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberModelImpl.FINDER_CACHE_ENABLED,
			CommerceVatNumberImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				
			Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			});
	public static final FinderPath FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C = new FinderPath(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberModelImpl.FINDER_CACHE_ENABLED,
			CommerceVatNumberImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] { Long.class.getName(), Long.class.getName() },
			CommerceVatNumberModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CommerceVatNumberModelImpl.CLASSPK_COLUMN_BITMASK |
			CommerceVatNumberModelImpl.CREATEDATE_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_C_C = new FinderPath(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] { Long.class.getName(), Long.class.getName() });

	/**
	 * Returns all the commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce vat numbers
	 */
	@Override
	public List<CommerceVatNumber> findByC_C(long classNameId, long classPK) {
		return findByC_C(classNameId, classPK, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce vat numbers
	 * @param end the upper bound of the range of commerce vat numbers (not inclusive)
	 * @return the range of matching commerce vat numbers
	 */
	@Override
	public List<CommerceVatNumber> findByC_C(long classNameId, long classPK,
		int start, int end) {
		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce vat numbers
	 * @param end the upper bound of the range of commerce vat numbers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce vat numbers
	 */
	@Override
	public List<CommerceVatNumber> findByC_C(long classNameId, long classPK,
		int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator) {
		return findByC_C(classNameId, classPK, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce vat numbers
	 * @param end the upper bound of the range of commerce vat numbers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of matching commerce vat numbers
	 */
	@Override
	public List<CommerceVatNumber> findByC_C(long classNameId, long classPK,
		int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator,
		boolean retrieveFromCache) {
		boolean pagination = true;
		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
				(orderByComparator == null)) {
			pagination = false;
			finderPath = FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] { classNameId, classPK };
		}
		else {
			finderPath = FINDER_PATH_WITH_PAGINATION_FIND_BY_C_C;
			finderArgs = new Object[] {
					classNameId, classPK,
					
					start, end, orderByComparator
				};
		}

		List<CommerceVatNumber> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceVatNumber>)finderCache.getResult(finderPath,
					finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceVatNumber commerceVatNumber : list) {
					if ((classNameId != commerceVatNumber.getClassNameId()) ||
							(classPK != commerceVatNumber.getClassPK())) {
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

			query.append(_SQL_SELECT_COMMERCEVATNUMBER_WHERE);

			query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);
			}
			else
			 if (pagination) {
				query.append(CommerceVatNumberModelImpl.ORDER_BY_JPQL);
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
					list = (List<CommerceVatNumber>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceVatNumber>)QueryUtil.list(q,
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
	 * Returns the first commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce vat number
	 * @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	 */
	@Override
	public CommerceVatNumber findByC_C_First(long classNameId, long classPK,
		OrderByComparator<CommerceVatNumber> orderByComparator)
		throws NoSuchVatNumberException {
		CommerceVatNumber commerceVatNumber = fetchByC_C_First(classNameId,
				classPK, orderByComparator);

		if (commerceVatNumber != null) {
			return commerceVatNumber;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchVatNumberException(msg.toString());
	}

	/**
	 * Returns the first commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	 */
	@Override
	public CommerceVatNumber fetchByC_C_First(long classNameId, long classPK,
		OrderByComparator<CommerceVatNumber> orderByComparator) {
		List<CommerceVatNumber> list = findByC_C(classNameId, classPK, 0, 1,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce vat number
	 * @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	 */
	@Override
	public CommerceVatNumber findByC_C_Last(long classNameId, long classPK,
		OrderByComparator<CommerceVatNumber> orderByComparator)
		throws NoSuchVatNumberException {
		CommerceVatNumber commerceVatNumber = fetchByC_C_Last(classNameId,
				classPK, orderByComparator);

		if (commerceVatNumber != null) {
			return commerceVatNumber;
		}

		StringBundler msg = new StringBundler(6);

		msg.append(_NO_SUCH_ENTITY_WITH_KEY);

		msg.append("classNameId=");
		msg.append(classNameId);

		msg.append(", classPK=");
		msg.append(classPK);

		msg.append("}");

		throw new NoSuchVatNumberException(msg.toString());
	}

	/**
	 * Returns the last commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	 */
	@Override
	public CommerceVatNumber fetchByC_C_Last(long classNameId, long classPK,
		OrderByComparator<CommerceVatNumber> orderByComparator) {
		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CommerceVatNumber> list = findByC_C(classNameId, classPK,
				count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce vat numbers before and after the current commerce vat number in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commerceVatNumberId the primary key of the current commerce vat number
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce vat number
	 * @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	 */
	@Override
	public CommerceVatNumber[] findByC_C_PrevAndNext(long commerceVatNumberId,
		long classNameId, long classPK,
		OrderByComparator<CommerceVatNumber> orderByComparator)
		throws NoSuchVatNumberException {
		CommerceVatNumber commerceVatNumber = findByPrimaryKey(commerceVatNumberId);

		Session session = null;

		try {
			session = openSession();

			CommerceVatNumber[] array = new CommerceVatNumberImpl[3];

			array[0] = getByC_C_PrevAndNext(session, commerceVatNumber,
					classNameId, classPK, orderByComparator, true);

			array[1] = commerceVatNumber;

			array[2] = getByC_C_PrevAndNext(session, commerceVatNumber,
					classNameId, classPK, orderByComparator, false);

			return array;
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceVatNumber getByC_C_PrevAndNext(Session session,
		CommerceVatNumber commerceVatNumber, long classNameId, long classPK,
		OrderByComparator<CommerceVatNumber> orderByComparator, boolean previous) {
		StringBundler query = null;

		if (orderByComparator != null) {
			query = new StringBundler(5 +
					(orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			query = new StringBundler(4);
		}

		query.append(_SQL_SELECT_COMMERCEVATNUMBER_WHERE);

		query.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		query.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			query.append(CommerceVatNumberModelImpl.ORDER_BY_JPQL);
		}

		String sql = query.toString();

		Query q = session.createQuery(sql);

		q.setFirstResult(0);
		q.setMaxResults(2);

		QueryPos qPos = QueryPos.getInstance(q);

		qPos.add(classNameId);

		qPos.add(classPK);

		if (orderByComparator != null) {
			Object[] values = orderByComparator.getOrderByConditionValues(commerceVatNumber);

			for (Object value : values) {
				qPos.add(value);
			}
		}

		List<CommerceVatNumber> list = q.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce vat numbers where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (CommerceVatNumber commerceVatNumber : findByC_C(classNameId,
				classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {
			remove(commerceVatNumber);
		}
	}

	/**
	 * Returns the number of commerce vat numbers where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce vat numbers
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_C_C;

		Object[] finderArgs = new Object[] { classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(3);

			query.append(_SQL_COUNT_COMMERCEVATNUMBER_WHERE);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 = "commerceVatNumber.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 = "commerceVatNumber.classPK = ?";
	public static final FinderPath FINDER_PATH_FETCH_BY_G_C_C = new FinderPath(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberModelImpl.FINDER_CACHE_ENABLED,
			CommerceVatNumberImpl.class, FINDER_CLASS_NAME_ENTITY,
			"fetchByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			CommerceVatNumberModelImpl.GROUPID_COLUMN_BITMASK |
			CommerceVatNumberModelImpl.CLASSNAMEID_COLUMN_BITMASK |
			CommerceVatNumberModelImpl.CLASSPK_COLUMN_BITMASK);
	public static final FinderPath FINDER_PATH_COUNT_BY_G_C_C = new FinderPath(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberModelImpl.FINDER_CACHE_ENABLED, Long.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByG_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			});

	/**
	 * Returns the commerce vat number where groupId = &#63; and classNameId = &#63; and classPK = &#63; or throws a {@link NoSuchVatNumberException} if it could not be found.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce vat number
	 * @throws NoSuchVatNumberException if a matching commerce vat number could not be found
	 */
	@Override
	public CommerceVatNumber findByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchVatNumberException {
		CommerceVatNumber commerceVatNumber = fetchByG_C_C(groupId,
				classNameId, classPK);

		if (commerceVatNumber == null) {
			StringBundler msg = new StringBundler(8);

			msg.append(_NO_SUCH_ENTITY_WITH_KEY);

			msg.append("groupId=");
			msg.append(groupId);

			msg.append(", classNameId=");
			msg.append(classNameId);

			msg.append(", classPK=");
			msg.append(classPK);

			msg.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(msg.toString());
			}

			throw new NoSuchVatNumberException(msg.toString());
		}

		return commerceVatNumber;
	}

	/**
	 * Returns the commerce vat number where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	 */
	@Override
	public CommerceVatNumber fetchByG_C_C(long groupId, long classNameId,
		long classPK) {
		return fetchByG_C_C(groupId, classNameId, classPK, true);
	}

	/**
	 * Returns the commerce vat number where groupId = &#63; and classNameId = &#63; and classPK = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the matching commerce vat number, or <code>null</code> if a matching commerce vat number could not be found
	 */
	@Override
	public CommerceVatNumber fetchByG_C_C(long groupId, long classNameId,
		long classPK, boolean retrieveFromCache) {
		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Object result = null;

		if (retrieveFromCache) {
			result = finderCache.getResult(FINDER_PATH_FETCH_BY_G_C_C,
					finderArgs, this);
		}

		if (result instanceof CommerceVatNumber) {
			CommerceVatNumber commerceVatNumber = (CommerceVatNumber)result;

			if ((groupId != commerceVatNumber.getGroupId()) ||
					(classNameId != commerceVatNumber.getClassNameId()) ||
					(classPK != commerceVatNumber.getClassPK())) {
				result = null;
			}
		}

		if (result == null) {
			StringBundler query = new StringBundler(5);

			query.append(_SQL_SELECT_COMMERCEVATNUMBER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

				List<CommerceVatNumber> list = q.list();

				if (list.isEmpty()) {
					finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
						finderArgs, list);
				}
				else {
					CommerceVatNumber commerceVatNumber = list.get(0);

					result = commerceVatNumber;

					cacheResult(commerceVatNumber);

					if ((commerceVatNumber.getGroupId() != groupId) ||
							(commerceVatNumber.getClassNameId() != classNameId) ||
							(commerceVatNumber.getClassPK() != classPK)) {
						finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
							finderArgs, commerceVatNumber);
					}
				}
			}
			catch (Exception e) {
				finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, finderArgs);

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
			return (CommerceVatNumber)result;
		}
	}

	/**
	 * Removes the commerce vat number where groupId = &#63; and classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the commerce vat number that was removed
	 */
	@Override
	public CommerceVatNumber removeByG_C_C(long groupId, long classNameId,
		long classPK) throws NoSuchVatNumberException {
		CommerceVatNumber commerceVatNumber = findByG_C_C(groupId, classNameId,
				classPK);

		return remove(commerceVatNumber);
	}

	/**
	 * Returns the number of commerce vat numbers where groupId = &#63; and classNameId = &#63; and classPK = &#63;.
	 *
	 * @param groupId the group ID
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce vat numbers
	 */
	@Override
	public int countByG_C_C(long groupId, long classNameId, long classPK) {
		FinderPath finderPath = FINDER_PATH_COUNT_BY_G_C_C;

		Object[] finderArgs = new Object[] { groupId, classNameId, classPK };

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler query = new StringBundler(4);

			query.append(_SQL_COUNT_COMMERCEVATNUMBER_WHERE);

			query.append(_FINDER_COLUMN_G_C_C_GROUPID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSNAMEID_2);

			query.append(_FINDER_COLUMN_G_C_C_CLASSPK_2);

			String sql = query.toString();

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				QueryPos qPos = QueryPos.getInstance(q);

				qPos.add(groupId);

				qPos.add(classNameId);

				qPos.add(classPK);

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

	private static final String _FINDER_COLUMN_G_C_C_GROUPID_2 = "commerceVatNumber.groupId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSNAMEID_2 = "commerceVatNumber.classNameId = ? AND ";
	private static final String _FINDER_COLUMN_G_C_C_CLASSPK_2 = "commerceVatNumber.classPK = ?";

	public CommerceVatNumberPersistenceImpl() {
		setModelClass(CommerceVatNumber.class);
	}

	/**
	 * Caches the commerce vat number in the entity cache if it is enabled.
	 *
	 * @param commerceVatNumber the commerce vat number
	 */
	@Override
	public void cacheResult(CommerceVatNumber commerceVatNumber) {
		entityCache.putResult(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberImpl.class, commerceVatNumber.getPrimaryKey(),
			commerceVatNumber);

		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C,
			new Object[] {
				commerceVatNumber.getGroupId(),
				commerceVatNumber.getClassNameId(),
				commerceVatNumber.getClassPK()
			}, commerceVatNumber);

		commerceVatNumber.resetOriginalValues();
	}

	/**
	 * Caches the commerce vat numbers in the entity cache if it is enabled.
	 *
	 * @param commerceVatNumbers the commerce vat numbers
	 */
	@Override
	public void cacheResult(List<CommerceVatNumber> commerceVatNumbers) {
		for (CommerceVatNumber commerceVatNumber : commerceVatNumbers) {
			if (entityCache.getResult(
						CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
						CommerceVatNumberImpl.class,
						commerceVatNumber.getPrimaryKey()) == null) {
				cacheResult(commerceVatNumber);
			}
			else {
				commerceVatNumber.resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce vat numbers.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceVatNumberImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce vat number.
	 *
	 * <p>
	 * The {@link EntityCache} and {@link FinderCache} are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceVatNumber commerceVatNumber) {
		entityCache.removeResult(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberImpl.class, commerceVatNumber.getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache((CommerceVatNumberModelImpl)commerceVatNumber,
			true);
	}

	@Override
	public void clearCache(List<CommerceVatNumber> commerceVatNumbers) {
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceVatNumber commerceVatNumber : commerceVatNumbers) {
			entityCache.removeResult(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
				CommerceVatNumberImpl.class, commerceVatNumber.getPrimaryKey());

			clearUniqueFindersCache((CommerceVatNumberModelImpl)commerceVatNumber,
				true);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceVatNumberModelImpl commerceVatNumberModelImpl) {
		Object[] args = new Object[] {
				commerceVatNumberModelImpl.getGroupId(),
				commerceVatNumberModelImpl.getClassNameId(),
				commerceVatNumberModelImpl.getClassPK()
			};

		finderCache.putResult(FINDER_PATH_COUNT_BY_G_C_C, args,
			Long.valueOf(1), false);
		finderCache.putResult(FINDER_PATH_FETCH_BY_G_C_C, args,
			commerceVatNumberModelImpl, false);
	}

	protected void clearUniqueFindersCache(
		CommerceVatNumberModelImpl commerceVatNumberModelImpl,
		boolean clearCurrent) {
		if (clearCurrent) {
			Object[] args = new Object[] {
					commerceVatNumberModelImpl.getGroupId(),
					commerceVatNumberModelImpl.getClassNameId(),
					commerceVatNumberModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, args);
		}

		if ((commerceVatNumberModelImpl.getColumnBitmask() &
				FINDER_PATH_FETCH_BY_G_C_C.getColumnBitmask()) != 0) {
			Object[] args = new Object[] {
					commerceVatNumberModelImpl.getOriginalGroupId(),
					commerceVatNumberModelImpl.getOriginalClassNameId(),
					commerceVatNumberModelImpl.getOriginalClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_G_C_C, args);
			finderCache.removeResult(FINDER_PATH_FETCH_BY_G_C_C, args);
		}
	}

	/**
	 * Creates a new commerce vat number with the primary key. Does not add the commerce vat number to the database.
	 *
	 * @param commerceVatNumberId the primary key for the new commerce vat number
	 * @return the new commerce vat number
	 */
	@Override
	public CommerceVatNumber create(long commerceVatNumberId) {
		CommerceVatNumber commerceVatNumber = new CommerceVatNumberImpl();

		commerceVatNumber.setNew(true);
		commerceVatNumber.setPrimaryKey(commerceVatNumberId);

		commerceVatNumber.setCompanyId(companyProvider.getCompanyId());

		return commerceVatNumber;
	}

	/**
	 * Removes the commerce vat number with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceVatNumberId the primary key of the commerce vat number
	 * @return the commerce vat number that was removed
	 * @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	 */
	@Override
	public CommerceVatNumber remove(long commerceVatNumberId)
		throws NoSuchVatNumberException {
		return remove((Serializable)commerceVatNumberId);
	}

	/**
	 * Removes the commerce vat number with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce vat number
	 * @return the commerce vat number that was removed
	 * @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	 */
	@Override
	public CommerceVatNumber remove(Serializable primaryKey)
		throws NoSuchVatNumberException {
		Session session = null;

		try {
			session = openSession();

			CommerceVatNumber commerceVatNumber = (CommerceVatNumber)session.get(CommerceVatNumberImpl.class,
					primaryKey);

			if (commerceVatNumber == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchVatNumberException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
					primaryKey);
			}

			return remove(commerceVatNumber);
		}
		catch (NoSuchVatNumberException nsee) {
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
	protected CommerceVatNumber removeImpl(CommerceVatNumber commerceVatNumber) {
		commerceVatNumber = toUnwrappedModel(commerceVatNumber);

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceVatNumber)) {
				commerceVatNumber = (CommerceVatNumber)session.get(CommerceVatNumberImpl.class,
						commerceVatNumber.getPrimaryKeyObj());
			}

			if (commerceVatNumber != null) {
				session.delete(commerceVatNumber);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		if (commerceVatNumber != null) {
			clearCache(commerceVatNumber);
		}

		return commerceVatNumber;
	}

	@Override
	public CommerceVatNumber updateImpl(CommerceVatNumber commerceVatNumber) {
		commerceVatNumber = toUnwrappedModel(commerceVatNumber);

		boolean isNew = commerceVatNumber.isNew();

		CommerceVatNumberModelImpl commerceVatNumberModelImpl = (CommerceVatNumberModelImpl)commerceVatNumber;

		ServiceContext serviceContext = ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceVatNumber.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceVatNumber.setCreateDate(now);
			}
			else {
				commerceVatNumber.setCreateDate(serviceContext.getCreateDate(
						now));
			}
		}

		if (!commerceVatNumberModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceVatNumber.setModifiedDate(now);
			}
			else {
				commerceVatNumber.setModifiedDate(serviceContext.getModifiedDate(
						now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceVatNumber.isNew()) {
				session.save(commerceVatNumber);

				commerceVatNumber.setNew(false);
			}
			else {
				commerceVatNumber = (CommerceVatNumber)session.merge(commerceVatNumber);
			}
		}
		catch (Exception e) {
			throw processException(e);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceVatNumberModelImpl.COLUMN_BITMASK_ENABLED) {
			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else
		 if (isNew) {
			Object[] args = new Object[] { commerceVatNumberModelImpl.getGroupId() };

			finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
				args);

			args = new Object[] {
					commerceVatNumberModelImpl.getClassNameId(),
					commerceVatNumberModelImpl.getClassPK()
				};

			finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
				args);

			finderCache.removeResult(FINDER_PATH_COUNT_ALL, FINDER_ARGS_EMPTY);
			finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_ALL,
				FINDER_ARGS_EMPTY);
		}

		else {
			if ((commerceVatNumberModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceVatNumberModelImpl.getOriginalGroupId()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);

				args = new Object[] { commerceVatNumberModelImpl.getGroupId() };

				finderCache.removeResult(FINDER_PATH_COUNT_BY_GROUPID, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_GROUPID,
					args);
			}

			if ((commerceVatNumberModelImpl.getColumnBitmask() &
					FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C.getColumnBitmask()) != 0) {
				Object[] args = new Object[] {
						commerceVatNumberModelImpl.getOriginalClassNameId(),
						commerceVatNumberModelImpl.getOriginalClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);

				args = new Object[] {
						commerceVatNumberModelImpl.getClassNameId(),
						commerceVatNumberModelImpl.getClassPK()
					};

				finderCache.removeResult(FINDER_PATH_COUNT_BY_C_C, args);
				finderCache.removeResult(FINDER_PATH_WITHOUT_PAGINATION_FIND_BY_C_C,
					args);
			}
		}

		entityCache.putResult(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
			CommerceVatNumberImpl.class, commerceVatNumber.getPrimaryKey(),
			commerceVatNumber, false);

		clearUniqueFindersCache(commerceVatNumberModelImpl, false);
		cacheUniqueFindersCache(commerceVatNumberModelImpl);

		commerceVatNumber.resetOriginalValues();

		return commerceVatNumber;
	}

	protected CommerceVatNumber toUnwrappedModel(
		CommerceVatNumber commerceVatNumber) {
		if (commerceVatNumber instanceof CommerceVatNumberImpl) {
			return commerceVatNumber;
		}

		CommerceVatNumberImpl commerceVatNumberImpl = new CommerceVatNumberImpl();

		commerceVatNumberImpl.setNew(commerceVatNumber.isNew());
		commerceVatNumberImpl.setPrimaryKey(commerceVatNumber.getPrimaryKey());

		commerceVatNumberImpl.setCommerceVatNumberId(commerceVatNumber.getCommerceVatNumberId());
		commerceVatNumberImpl.setGroupId(commerceVatNumber.getGroupId());
		commerceVatNumberImpl.setCompanyId(commerceVatNumber.getCompanyId());
		commerceVatNumberImpl.setUserId(commerceVatNumber.getUserId());
		commerceVatNumberImpl.setUserName(commerceVatNumber.getUserName());
		commerceVatNumberImpl.setCreateDate(commerceVatNumber.getCreateDate());
		commerceVatNumberImpl.setModifiedDate(commerceVatNumber.getModifiedDate());
		commerceVatNumberImpl.setClassNameId(commerceVatNumber.getClassNameId());
		commerceVatNumberImpl.setClassPK(commerceVatNumber.getClassPK());
		commerceVatNumberImpl.setValue(commerceVatNumber.getValue());
		commerceVatNumberImpl.setValid(commerceVatNumber.isValid());

		return commerceVatNumberImpl;
	}

	/**
	 * Returns the commerce vat number with the primary key or throws a {@link com.liferay.portal.kernel.exception.NoSuchModelException} if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce vat number
	 * @return the commerce vat number
	 * @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	 */
	@Override
	public CommerceVatNumber findByPrimaryKey(Serializable primaryKey)
		throws NoSuchVatNumberException {
		CommerceVatNumber commerceVatNumber = fetchByPrimaryKey(primaryKey);

		if (commerceVatNumber == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchVatNumberException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
				primaryKey);
		}

		return commerceVatNumber;
	}

	/**
	 * Returns the commerce vat number with the primary key or throws a {@link NoSuchVatNumberException} if it could not be found.
	 *
	 * @param commerceVatNumberId the primary key of the commerce vat number
	 * @return the commerce vat number
	 * @throws NoSuchVatNumberException if a commerce vat number with the primary key could not be found
	 */
	@Override
	public CommerceVatNumber findByPrimaryKey(long commerceVatNumberId)
		throws NoSuchVatNumberException {
		return findByPrimaryKey((Serializable)commerceVatNumberId);
	}

	/**
	 * Returns the commerce vat number with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce vat number
	 * @return the commerce vat number, or <code>null</code> if a commerce vat number with the primary key could not be found
	 */
	@Override
	public CommerceVatNumber fetchByPrimaryKey(Serializable primaryKey) {
		Serializable serializable = entityCache.getResult(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
				CommerceVatNumberImpl.class, primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceVatNumber commerceVatNumber = (CommerceVatNumber)serializable;

		if (commerceVatNumber == null) {
			Session session = null;

			try {
				session = openSession();

				commerceVatNumber = (CommerceVatNumber)session.get(CommerceVatNumberImpl.class,
						primaryKey);

				if (commerceVatNumber != null) {
					cacheResult(commerceVatNumber);
				}
				else {
					entityCache.putResult(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
						CommerceVatNumberImpl.class, primaryKey, nullModel);
				}
			}
			catch (Exception e) {
				entityCache.removeResult(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
					CommerceVatNumberImpl.class, primaryKey);

				throw processException(e);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceVatNumber;
	}

	/**
	 * Returns the commerce vat number with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceVatNumberId the primary key of the commerce vat number
	 * @return the commerce vat number, or <code>null</code> if a commerce vat number with the primary key could not be found
	 */
	@Override
	public CommerceVatNumber fetchByPrimaryKey(long commerceVatNumberId) {
		return fetchByPrimaryKey((Serializable)commerceVatNumberId);
	}

	@Override
	public Map<Serializable, CommerceVatNumber> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {
		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceVatNumber> map = new HashMap<Serializable, CommerceVatNumber>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceVatNumber commerceVatNumber = fetchByPrimaryKey(primaryKey);

			if (commerceVatNumber != null) {
				map.put(primaryKey, commerceVatNumber);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
					CommerceVatNumberImpl.class, primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(primaryKey, (CommerceVatNumber)serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler query = new StringBundler((uncachedPrimaryKeys.size() * 2) +
				1);

		query.append(_SQL_SELECT_COMMERCEVATNUMBER_WHERE_PKS_IN);

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

			for (CommerceVatNumber commerceVatNumber : (List<CommerceVatNumber>)q.list()) {
				map.put(commerceVatNumber.getPrimaryKeyObj(), commerceVatNumber);

				cacheResult(commerceVatNumber);

				uncachedPrimaryKeys.remove(commerceVatNumber.getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(CommerceVatNumberModelImpl.ENTITY_CACHE_ENABLED,
					CommerceVatNumberImpl.class, primaryKey, nullModel);
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
	 * Returns all the commerce vat numbers.
	 *
	 * @return the commerce vat numbers
	 */
	@Override
	public List<CommerceVatNumber> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce vat numbers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce vat numbers
	 * @param end the upper bound of the range of commerce vat numbers (not inclusive)
	 * @return the range of commerce vat numbers
	 */
	@Override
	public List<CommerceVatNumber> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce vat numbers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce vat numbers
	 * @param end the upper bound of the range of commerce vat numbers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce vat numbers
	 */
	@Override
	public List<CommerceVatNumber> findAll(int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator) {
		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce vat numbers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link QueryUtil#ALL_POS} will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent and pagination is required (<code>start</code> and <code>end</code> are not {@link QueryUtil#ALL_POS}), then the query will include the default ORDER BY logic from {@link CommerceVatNumberModelImpl}. If both <code>orderByComparator</code> and pagination are absent, for performance reasons, the query will not have an ORDER BY clause and the returned result set will be sorted on by the primary key in an ascending order.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce vat numbers
	 * @param end the upper bound of the range of commerce vat numbers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param retrieveFromCache whether to retrieve from the finder cache
	 * @return the ordered range of commerce vat numbers
	 */
	@Override
	public List<CommerceVatNumber> findAll(int start, int end,
		OrderByComparator<CommerceVatNumber> orderByComparator,
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

		List<CommerceVatNumber> list = null;

		if (retrieveFromCache) {
			list = (List<CommerceVatNumber>)finderCache.getResult(finderPath,
					finderArgs, this);
		}

		if (list == null) {
			StringBundler query = null;
			String sql = null;

			if (orderByComparator != null) {
				query = new StringBundler(2 +
						(orderByComparator.getOrderByFields().length * 2));

				query.append(_SQL_SELECT_COMMERCEVATNUMBER);

				appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
					orderByComparator);

				sql = query.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEVATNUMBER;

				if (pagination) {
					sql = sql.concat(CommerceVatNumberModelImpl.ORDER_BY_JPQL);
				}
			}

			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(sql);

				if (!pagination) {
					list = (List<CommerceVatNumber>)QueryUtil.list(q,
							getDialect(), start, end, false);

					Collections.sort(list);

					list = Collections.unmodifiableList(list);
				}
				else {
					list = (List<CommerceVatNumber>)QueryUtil.list(q,
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
	 * Removes all the commerce vat numbers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceVatNumber commerceVatNumber : findAll()) {
			remove(commerceVatNumber);
		}
	}

	/**
	 * Returns the number of commerce vat numbers.
	 *
	 * @return the number of commerce vat numbers
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(FINDER_PATH_COUNT_ALL,
				FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query q = session.createQuery(_SQL_COUNT_COMMERCEVATNUMBER);

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
		return CommerceVatNumberModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce vat number persistence.
	 */
	public void afterPropertiesSet() {
	}

	public void destroy() {
		entityCache.removeCache(CommerceVatNumberImpl.class.getName());
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
	private static final String _SQL_SELECT_COMMERCEVATNUMBER = "SELECT commerceVatNumber FROM CommerceVatNumber commerceVatNumber";
	private static final String _SQL_SELECT_COMMERCEVATNUMBER_WHERE_PKS_IN = "SELECT commerceVatNumber FROM CommerceVatNumber commerceVatNumber WHERE commerceVatNumberId IN (";
	private static final String _SQL_SELECT_COMMERCEVATNUMBER_WHERE = "SELECT commerceVatNumber FROM CommerceVatNumber commerceVatNumber WHERE ";
	private static final String _SQL_COUNT_COMMERCEVATNUMBER = "SELECT COUNT(commerceVatNumber) FROM CommerceVatNumber commerceVatNumber";
	private static final String _SQL_COUNT_COMMERCEVATNUMBER_WHERE = "SELECT COUNT(commerceVatNumber) FROM CommerceVatNumber commerceVatNumber WHERE ";
	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceVatNumber.";
	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No CommerceVatNumber exists with the primary key ";
	private static final String _NO_SUCH_ENTITY_WITH_KEY = "No CommerceVatNumber exists with the key {";
	private static final Log _log = LogFactoryUtil.getLog(CommerceVatNumberPersistenceImpl.class);
}