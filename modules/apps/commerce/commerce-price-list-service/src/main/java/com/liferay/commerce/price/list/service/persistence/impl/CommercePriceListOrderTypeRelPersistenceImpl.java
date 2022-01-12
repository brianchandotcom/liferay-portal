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

package com.liferay.commerce.price.list.service.persistence.impl;

import com.liferay.commerce.price.list.exception.NoSuchPriceListOrderTypeRelException;
import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRel;
import com.liferay.commerce.price.list.model.CommercePriceListOrderTypeRelTable;
import com.liferay.commerce.price.list.model.impl.CommercePriceListOrderTypeRelImpl;
import com.liferay.commerce.price.list.model.impl.CommercePriceListOrderTypeRelModelImpl;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListOrderTypeRelPersistence;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListOrderTypeRelUtil;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.change.tracking.CTColumnResolutionType;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.change.tracking.helper.CTPersistenceHelper;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.Validator;
import com.liferay.portal.kernel.uuid.PortalUUIDUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce price list order type rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceListOrderTypeRelPersistenceImpl
	extends BasePersistenceImpl<CommercePriceListOrderTypeRel>
	implements CommercePriceListOrderTypeRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommercePriceListOrderTypeRelUtil</code> to access the commerce price list order type rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommercePriceListOrderTypeRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByUuid;
	private FinderPath _finderPathWithoutPaginationFindByUuid;
	private FinderPath _finderPathCountByUuid;

	/**
	 * Returns all the commerce price list order type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @return the range of matching commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListOrderTypeRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<CommercePriceListOrderTypeRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceListOrderTypeRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListOrderTypeRel
						commercePriceListOrderTypeRel : list) {

					if (!uuid.equals(commercePriceListOrderTypeRel.getUuid())) {
						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_COMMERCEPRICELISTORDERTYPEREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceListOrderTypeRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				list = (List<CommercePriceListOrderTypeRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel findByUuid_First(
			String uuid,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			fetchByUuid_First(uuid, orderByComparator);

		if (commercePriceListOrderTypeRel != null) {
			return commercePriceListOrderTypeRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPriceListOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		List<CommercePriceListOrderTypeRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel findByUuid_Last(
			String uuid,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			fetchByUuid_Last(uuid, orderByComparator);

		if (commercePriceListOrderTypeRel != null) {
			return commercePriceListOrderTypeRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPriceListOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListOrderTypeRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list order type rels before and after the current commerce price list order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the current commerce price list order type rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel[] findByUuid_PrevAndNext(
			long commercePriceListOrderTypeRelId, String uuid,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException {

		uuid = Objects.toString(uuid, "");

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			findByPrimaryKey(commercePriceListOrderTypeRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListOrderTypeRel[] array =
				new CommercePriceListOrderTypeRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commercePriceListOrderTypeRel, uuid, orderByComparator,
				true);

			array[1] = commercePriceListOrderTypeRel;

			array[2] = getByUuid_PrevAndNext(
				session, commercePriceListOrderTypeRel, uuid, orderByComparator,
				false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePriceListOrderTypeRel getByUuid_PrevAndNext(
		Session session,
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel,
		String uuid,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_COMMERCEPRICELISTORDERTYPEREL_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_UUID_2);
		}

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CommercePriceListOrderTypeRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceListOrderTypeRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListOrderTypeRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list order type rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommercePriceListOrderTypeRel commercePriceListOrderTypeRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commercePriceListOrderTypeRel);
		}
	}

	/**
	 * Returns the number of commerce price list order type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list order type rels
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListOrderTypeRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid;

			finderArgs = new Object[] {uuid};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEPRICELISTORDERTYPEREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_UUID_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_UUID_2 =
		"commercePriceListOrderTypeRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commercePriceListOrderTypeRel.uuid IS NULL OR commercePriceListOrderTypeRel.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @return the range of matching commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListOrderTypeRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<CommercePriceListOrderTypeRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceListOrderTypeRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListOrderTypeRel
						commercePriceListOrderTypeRel : list) {

					if (!uuid.equals(commercePriceListOrderTypeRel.getUuid()) ||
						(companyId !=
							commercePriceListOrderTypeRel.getCompanyId())) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					4 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(4);
			}

			sb.append(_SQL_SELECT_COMMERCEPRICELISTORDERTYPEREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceListOrderTypeRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				list = (List<CommercePriceListOrderTypeRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commercePriceListOrderTypeRel != null) {
			return commercePriceListOrderTypeRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceListOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		List<CommercePriceListOrderTypeRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (commercePriceListOrderTypeRel != null) {
			return commercePriceListOrderTypeRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceListOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListOrderTypeRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list order type rels before and after the current commerce price list order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the current commerce price list order type rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel[] findByUuid_C_PrevAndNext(
			long commercePriceListOrderTypeRelId, String uuid, long companyId,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException {

		uuid = Objects.toString(uuid, "");

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			findByPrimaryKey(commercePriceListOrderTypeRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListOrderTypeRel[] array =
				new CommercePriceListOrderTypeRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commercePriceListOrderTypeRel, uuid, companyId,
				orderByComparator, true);

			array[1] = commercePriceListOrderTypeRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, commercePriceListOrderTypeRel, uuid, companyId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePriceListOrderTypeRel getByUuid_C_PrevAndNext(
		Session session,
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel,
		String uuid, long companyId,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_COMMERCEPRICELISTORDERTYPEREL_WHERE);

		boolean bindUuid = false;

		if (uuid.isEmpty()) {
			sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
		}
		else {
			bindUuid = true;

			sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
		}

		sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CommercePriceListOrderTypeRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindUuid) {
			queryPos.add(uuid);
		}

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceListOrderTypeRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListOrderTypeRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list order type rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommercePriceListOrderTypeRel commercePriceListOrderTypeRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commercePriceListOrderTypeRel);
		}
	}

	/**
	 * Returns the number of commerce price list order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list order type rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListOrderTypeRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByUuid_C;

			finderArgs = new Object[] {uuid, companyId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPRICELISTORDERTYPEREL_WHERE);

			boolean bindUuid = false;

			if (uuid.isEmpty()) {
				sb.append(_FINDER_COLUMN_UUID_C_UUID_3);
			}
			else {
				bindUuid = true;

				sb.append(_FINDER_COLUMN_UUID_C_UUID_2);
			}

			sb.append(_FINDER_COLUMN_UUID_C_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindUuid) {
					queryPos.add(uuid);
				}

				queryPos.add(companyId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_UUID_C_UUID_2 =
		"commercePriceListOrderTypeRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commercePriceListOrderTypeRel.uuid IS NULL OR commercePriceListOrderTypeRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commercePriceListOrderTypeRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCommercePriceListId;
	private FinderPath _finderPathWithoutPaginationFindByCommercePriceListId;
	private FinderPath _finderPathCountByCommercePriceListId;

	/**
	 * Returns all the commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findByCommercePriceListId(
		long commercePriceListId) {

		return findByCommercePriceListId(
			commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @return the range of matching commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {

		return findByCommercePriceListId(commercePriceListId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListOrderTypeRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath =
					_finderPathWithoutPaginationFindByCommercePriceListId;
				finderArgs = new Object[] {commercePriceListId};
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindByCommercePriceListId;
			finderArgs = new Object[] {
				commercePriceListId, start, end, orderByComparator
			};
		}

		List<CommercePriceListOrderTypeRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceListOrderTypeRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListOrderTypeRel
						commercePriceListOrderTypeRel : list) {

					if (commercePriceListId !=
							commercePriceListOrderTypeRel.
								getCommercePriceListId()) {

						list = null;

						break;
					}
				}
			}
		}

		if (list == null) {
			StringBundler sb = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					3 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(3);
			}

			sb.append(_SQL_SELECT_COMMERCEPRICELISTORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceListOrderTypeRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				list = (List<CommercePriceListOrderTypeRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel findByCommercePriceListId_First(
			long commercePriceListId,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			fetchByCommercePriceListId_First(
				commercePriceListId, orderByComparator);

		if (commercePriceListOrderTypeRel != null) {
			return commercePriceListOrderTypeRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append("}");

		throw new NoSuchPriceListOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		List<CommercePriceListOrderTypeRel> list = findByCommercePriceListId(
			commercePriceListId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel findByCommercePriceListId_Last(
			long commercePriceListId,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator)
		throws NoSuchPriceListOrderTypeRelException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			fetchByCommercePriceListId_Last(
				commercePriceListId, orderByComparator);

		if (commercePriceListOrderTypeRel != null) {
			return commercePriceListOrderTypeRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append("}");

		throw new NoSuchPriceListOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		int count = countByCommercePriceListId(commercePriceListId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListOrderTypeRel> list = findByCommercePriceListId(
			commercePriceListId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list order type rels before and after the current commerce price list order type rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the current commerce price list order type rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel[]
			findByCommercePriceListId_PrevAndNext(
				long commercePriceListOrderTypeRelId, long commercePriceListId,
				OrderByComparator<CommercePriceListOrderTypeRel>
					orderByComparator)
		throws NoSuchPriceListOrderTypeRelException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			findByPrimaryKey(commercePriceListOrderTypeRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListOrderTypeRel[] array =
				new CommercePriceListOrderTypeRelImpl[3];

			array[0] = getByCommercePriceListId_PrevAndNext(
				session, commercePriceListOrderTypeRel, commercePriceListId,
				orderByComparator, true);

			array[1] = commercePriceListOrderTypeRel;

			array[2] = getByCommercePriceListId_PrevAndNext(
				session, commercePriceListOrderTypeRel, commercePriceListId,
				orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePriceListOrderTypeRel
		getByCommercePriceListId_PrevAndNext(
			Session session,
			CommercePriceListOrderTypeRel commercePriceListOrderTypeRel,
			long commercePriceListId,
			OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator,
			boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_COMMERCEPRICELISTORDERTYPEREL_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

		if (orderByComparator != null) {
			String[] orderByConditionFields =
				orderByComparator.getOrderByConditionFields();

			if (orderByConditionFields.length > 0) {
				sb.append(WHERE_AND);
			}

			for (int i = 0; i < orderByConditionFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByConditionFields[i]);

				if ((i + 1) < orderByConditionFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN_HAS_NEXT);
					}
					else {
						sb.append(WHERE_LESSER_THAN_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(WHERE_GREATER_THAN);
					}
					else {
						sb.append(WHERE_LESSER_THAN);
					}
				}
			}

			sb.append(ORDER_BY_CLAUSE);

			String[] orderByFields = orderByComparator.getOrderByFields();

			for (int i = 0; i < orderByFields.length; i++) {
				sb.append(_ORDER_BY_ENTITY_ALIAS);
				sb.append(orderByFields[i]);

				if ((i + 1) < orderByFields.length) {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC_HAS_NEXT);
					}
					else {
						sb.append(ORDER_BY_DESC_HAS_NEXT);
					}
				}
				else {
					if (orderByComparator.isAscending() ^ previous) {
						sb.append(ORDER_BY_ASC);
					}
					else {
						sb.append(ORDER_BY_DESC);
					}
				}
			}
		}
		else {
			sb.append(CommercePriceListOrderTypeRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commercePriceListId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePriceListOrderTypeRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListOrderTypeRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list order type rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	@Override
	public void removeByCommercePriceListId(long commercePriceListId) {
		for (CommercePriceListOrderTypeRel commercePriceListOrderTypeRel :
				findByCommercePriceListId(
					commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commercePriceListOrderTypeRel);
		}
	}

	/**
	 * Returns the number of commerce price list order type rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list order type rels
	 */
	@Override
	public int countByCommercePriceListId(long commercePriceListId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListOrderTypeRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCommercePriceListId;

			finderArgs = new Object[] {commercePriceListId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEPRICELISTORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2 =
			"commercePriceListOrderTypeRel.commercePriceListId = ?";

	private FinderPath _finderPathFetchByCPI_COTI;
	private FinderPath _finderPathCountByCPI_COTI;

	/**
	 * Returns the commerce price list order type rel where commercePriceListId = &#63; and commerceOrderTypeId = &#63; or throws a <code>NoSuchPriceListOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel findByCPI_COTI(
			long commercePriceListId, long commerceOrderTypeId)
		throws NoSuchPriceListOrderTypeRelException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			fetchByCPI_COTI(commercePriceListId, commerceOrderTypeId);

		if (commercePriceListOrderTypeRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commercePriceListId=");
			sb.append(commercePriceListId);

			sb.append(", commerceOrderTypeId=");
			sb.append(commerceOrderTypeId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPriceListOrderTypeRelException(sb.toString());
		}

		return commercePriceListOrderTypeRel;
	}

	/**
	 * Returns the commerce price list order type rel where commercePriceListId = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel fetchByCPI_COTI(
		long commercePriceListId, long commerceOrderTypeId) {

		return fetchByCPI_COTI(commercePriceListId, commerceOrderTypeId, true);
	}

	/**
	 * Returns the commerce price list order type rel where commercePriceListId = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list order type rel, or <code>null</code> if a matching commerce price list order type rel could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel fetchByCPI_COTI(
		long commercePriceListId, long commerceOrderTypeId,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListOrderTypeRel.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				commercePriceListId, commerceOrderTypeId
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByCPI_COTI, finderArgs);
		}

		if (result instanceof CommercePriceListOrderTypeRel) {
			CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
				(CommercePriceListOrderTypeRel)result;

			if ((commercePriceListId !=
					commercePriceListOrderTypeRel.getCommercePriceListId()) ||
				(commerceOrderTypeId !=
					commercePriceListOrderTypeRel.getCommerceOrderTypeId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEPRICELISTORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_CPI_COTI_COMMERCEPRICELISTID_2);

			sb.append(_FINDER_COLUMN_CPI_COTI_COMMERCEORDERTYPEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				queryPos.add(commerceOrderTypeId);

				List<CommercePriceListOrderTypeRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByCPI_COTI, finderArgs, list);
					}
				}
				else {
					CommercePriceListOrderTypeRel
						commercePriceListOrderTypeRel = list.get(0);

					result = commercePriceListOrderTypeRel;

					cacheResult(commercePriceListOrderTypeRel);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		if (result instanceof List<?>) {
			return null;
		}
		else {
			return (CommercePriceListOrderTypeRel)result;
		}
	}

	/**
	 * Removes the commerce price list order type rel where commercePriceListId = &#63; and commerceOrderTypeId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the commerce price list order type rel that was removed
	 */
	@Override
	public CommercePriceListOrderTypeRel removeByCPI_COTI(
			long commercePriceListId, long commerceOrderTypeId)
		throws NoSuchPriceListOrderTypeRelException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			findByCPI_COTI(commercePriceListId, commerceOrderTypeId);

		return remove(commercePriceListOrderTypeRel);
	}

	/**
	 * Returns the number of commerce price list order type rels where commercePriceListId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce price list order type rels
	 */
	@Override
	public int countByCPI_COTI(
		long commercePriceListId, long commerceOrderTypeId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListOrderTypeRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCPI_COTI;

			finderArgs = new Object[] {
				commercePriceListId, commerceOrderTypeId
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPRICELISTORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_CPI_COTI_COMMERCEPRICELISTID_2);

			sb.append(_FINDER_COLUMN_CPI_COTI_COMMERCEORDERTYPEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				queryPos.add(commerceOrderTypeId);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(finderPath, finderArgs, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String _FINDER_COLUMN_CPI_COTI_COMMERCEPRICELISTID_2 =
		"commercePriceListOrderTypeRel.commercePriceListId = ? AND ";

	private static final String _FINDER_COLUMN_CPI_COTI_COMMERCEORDERTYPEID_2 =
		"commercePriceListOrderTypeRel.commerceOrderTypeId = ?";

	public CommercePriceListOrderTypeRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"commercePriceListOrderTypeRelId", "CPriceListOrderTypeRelId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommercePriceListOrderTypeRel.class);

		setModelImplClass(CommercePriceListOrderTypeRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommercePriceListOrderTypeRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce price list order type rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListOrderTypeRel the commerce price list order type rel
	 */
	@Override
	public void cacheResult(
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel) {

		if (commercePriceListOrderTypeRel.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CommercePriceListOrderTypeRelImpl.class,
			commercePriceListOrderTypeRel.getPrimaryKey(),
			commercePriceListOrderTypeRel);

		finderCache.putResult(
			_finderPathFetchByCPI_COTI,
			new Object[] {
				commercePriceListOrderTypeRel.getCommercePriceListId(),
				commercePriceListOrderTypeRel.getCommerceOrderTypeId()
			},
			commercePriceListOrderTypeRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce price list order type rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListOrderTypeRels the commerce price list order type rels
	 */
	@Override
	public void cacheResult(
		List<CommercePriceListOrderTypeRel> commercePriceListOrderTypeRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commercePriceListOrderTypeRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommercePriceListOrderTypeRel commercePriceListOrderTypeRel :
				commercePriceListOrderTypeRels) {

			if (commercePriceListOrderTypeRel.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					CommercePriceListOrderTypeRelImpl.class,
					commercePriceListOrderTypeRel.getPrimaryKey()) == null) {

				cacheResult(commercePriceListOrderTypeRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce price list order type rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommercePriceListOrderTypeRelImpl.class);

		finderCache.clearCache(CommercePriceListOrderTypeRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce price list order type rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel) {

		entityCache.removeResult(
			CommercePriceListOrderTypeRelImpl.class,
			commercePriceListOrderTypeRel);
	}

	@Override
	public void clearCache(
		List<CommercePriceListOrderTypeRel> commercePriceListOrderTypeRels) {

		for (CommercePriceListOrderTypeRel commercePriceListOrderTypeRel :
				commercePriceListOrderTypeRels) {

			entityCache.removeResult(
				CommercePriceListOrderTypeRelImpl.class,
				commercePriceListOrderTypeRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommercePriceListOrderTypeRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommercePriceListOrderTypeRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePriceListOrderTypeRelModelImpl
			commercePriceListOrderTypeRelModelImpl) {

		Object[] args = new Object[] {
			commercePriceListOrderTypeRelModelImpl.getCommercePriceListId(),
			commercePriceListOrderTypeRelModelImpl.getCommerceOrderTypeId()
		};

		finderCache.putResult(
			_finderPathCountByCPI_COTI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCPI_COTI, args,
			commercePriceListOrderTypeRelModelImpl);
	}

	/**
	 * Creates a new commerce price list order type rel with the primary key. Does not add the commerce price list order type rel to the database.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key for the new commerce price list order type rel
	 * @return the new commerce price list order type rel
	 */
	@Override
	public CommercePriceListOrderTypeRel create(
		long commercePriceListOrderTypeRelId) {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			new CommercePriceListOrderTypeRelImpl();

		commercePriceListOrderTypeRel.setNew(true);
		commercePriceListOrderTypeRel.setPrimaryKey(
			commercePriceListOrderTypeRelId);

		String uuid = PortalUUIDUtil.generate();

		commercePriceListOrderTypeRel.setUuid(uuid);

		commercePriceListOrderTypeRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commercePriceListOrderTypeRel;
	}

	/**
	 * Removes the commerce price list order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the commerce price list order type rel
	 * @return the commerce price list order type rel that was removed
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel remove(
			long commercePriceListOrderTypeRelId)
		throws NoSuchPriceListOrderTypeRelException {

		return remove((Serializable)commercePriceListOrderTypeRelId);
	}

	/**
	 * Removes the commerce price list order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce price list order type rel
	 * @return the commerce price list order type rel that was removed
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel remove(Serializable primaryKey)
		throws NoSuchPriceListOrderTypeRelException {

		Session session = null;

		try {
			session = openSession();

			CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
				(CommercePriceListOrderTypeRel)session.get(
					CommercePriceListOrderTypeRelImpl.class, primaryKey);

			if (commercePriceListOrderTypeRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPriceListOrderTypeRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commercePriceListOrderTypeRel);
		}
		catch (NoSuchPriceListOrderTypeRelException noSuchEntityException) {
			throw noSuchEntityException;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	@Override
	protected CommercePriceListOrderTypeRel removeImpl(
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePriceListOrderTypeRel)) {
				commercePriceListOrderTypeRel =
					(CommercePriceListOrderTypeRel)session.get(
						CommercePriceListOrderTypeRelImpl.class,
						commercePriceListOrderTypeRel.getPrimaryKeyObj());
			}

			if ((commercePriceListOrderTypeRel != null) &&
				ctPersistenceHelper.isRemove(commercePriceListOrderTypeRel)) {

				session.delete(commercePriceListOrderTypeRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceListOrderTypeRel != null) {
			clearCache(commercePriceListOrderTypeRel);
		}

		return commercePriceListOrderTypeRel;
	}

	@Override
	public CommercePriceListOrderTypeRel updateImpl(
		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel) {

		boolean isNew = commercePriceListOrderTypeRel.isNew();

		if (!(commercePriceListOrderTypeRel instanceof
				CommercePriceListOrderTypeRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commercePriceListOrderTypeRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commercePriceListOrderTypeRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commercePriceListOrderTypeRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommercePriceListOrderTypeRel implementation " +
					commercePriceListOrderTypeRel.getClass());
		}

		CommercePriceListOrderTypeRelModelImpl
			commercePriceListOrderTypeRelModelImpl =
				(CommercePriceListOrderTypeRelModelImpl)
					commercePriceListOrderTypeRel;

		if (Validator.isNull(commercePriceListOrderTypeRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commercePriceListOrderTypeRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commercePriceListOrderTypeRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commercePriceListOrderTypeRel.setCreateDate(date);
			}
			else {
				commercePriceListOrderTypeRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commercePriceListOrderTypeRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commercePriceListOrderTypeRel.setModifiedDate(date);
			}
			else {
				commercePriceListOrderTypeRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(commercePriceListOrderTypeRel)) {
				if (!isNew) {
					session.evict(
						CommercePriceListOrderTypeRelImpl.class,
						commercePriceListOrderTypeRel.getPrimaryKeyObj());
				}

				session.save(commercePriceListOrderTypeRel);
			}
			else {
				commercePriceListOrderTypeRel =
					(CommercePriceListOrderTypeRel)session.merge(
						commercePriceListOrderTypeRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceListOrderTypeRel.getCtCollectionId() != 0) {
			if (isNew) {
				commercePriceListOrderTypeRel.setNew(false);
			}

			commercePriceListOrderTypeRel.resetOriginalValues();

			return commercePriceListOrderTypeRel;
		}

		entityCache.putResult(
			CommercePriceListOrderTypeRelImpl.class,
			commercePriceListOrderTypeRelModelImpl, false, true);

		cacheUniqueFindersCache(commercePriceListOrderTypeRelModelImpl);

		if (isNew) {
			commercePriceListOrderTypeRel.setNew(false);
		}

		commercePriceListOrderTypeRel.resetOriginalValues();

		return commercePriceListOrderTypeRel;
	}

	/**
	 * Returns the commerce price list order type rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list order type rel
	 * @return the commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchPriceListOrderTypeRelException {

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
			fetchByPrimaryKey(primaryKey);

		if (commercePriceListOrderTypeRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPriceListOrderTypeRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commercePriceListOrderTypeRel;
	}

	/**
	 * Returns the commerce price list order type rel with the primary key or throws a <code>NoSuchPriceListOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the commerce price list order type rel
	 * @return the commerce price list order type rel
	 * @throws NoSuchPriceListOrderTypeRelException if a commerce price list order type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel findByPrimaryKey(
			long commercePriceListOrderTypeRelId)
		throws NoSuchPriceListOrderTypeRelException {

		return findByPrimaryKey((Serializable)commercePriceListOrderTypeRelId);
	}

	/**
	 * Returns the commerce price list order type rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list order type rel
	 * @return the commerce price list order type rel, or <code>null</code> if a commerce price list order type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				CommercePriceListOrderTypeRel.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		CommercePriceListOrderTypeRel commercePriceListOrderTypeRel = null;

		Session session = null;

		try {
			session = openSession();

			commercePriceListOrderTypeRel =
				(CommercePriceListOrderTypeRel)session.get(
					CommercePriceListOrderTypeRelImpl.class, primaryKey);

			if (commercePriceListOrderTypeRel != null) {
				cacheResult(commercePriceListOrderTypeRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return commercePriceListOrderTypeRel;
	}

	/**
	 * Returns the commerce price list order type rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListOrderTypeRelId the primary key of the commerce price list order type rel
	 * @return the commerce price list order type rel, or <code>null</code> if a commerce price list order type rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListOrderTypeRel fetchByPrimaryKey(
		long commercePriceListOrderTypeRelId) {

		return fetchByPrimaryKey((Serializable)commercePriceListOrderTypeRelId);
	}

	@Override
	public Map<Serializable, CommercePriceListOrderTypeRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CommercePriceListOrderTypeRel.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommercePriceListOrderTypeRel> map =
			new HashMap<Serializable, CommercePriceListOrderTypeRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommercePriceListOrderTypeRel commercePriceListOrderTypeRel =
				fetchByPrimaryKey(primaryKey);

			if (commercePriceListOrderTypeRel != null) {
				map.put(primaryKey, commercePriceListOrderTypeRel);
			}

			return map;
		}

		if ((databaseInMaxParameters > 0) &&
			(primaryKeys.size() > databaseInMaxParameters)) {

			Iterator<Serializable> iterator = primaryKeys.iterator();

			while (iterator.hasNext()) {
				Set<Serializable> page = new HashSet<>();

				for (int i = 0;
					 (i < databaseInMaxParameters) && iterator.hasNext(); i++) {

					page.add(iterator.next());
				}

				map.putAll(fetchByPrimaryKeys(page));
			}

			return map;
		}

		StringBundler sb = new StringBundler((primaryKeys.size() * 2) + 1);

		sb.append(getSelectSQL());
		sb.append(" WHERE ");
		sb.append(getPKDBName());
		sb.append(" IN (");

		for (Serializable primaryKey : primaryKeys) {
			sb.append((long)primaryKey);

			sb.append(",");
		}

		sb.setIndex(sb.index() - 1);

		sb.append(")");

		String sql = sb.toString();

		Session session = null;

		try {
			session = openSession();

			Query query = session.createQuery(sql);

			for (CommercePriceListOrderTypeRel commercePriceListOrderTypeRel :
					(List<CommercePriceListOrderTypeRel>)query.list()) {

				map.put(
					commercePriceListOrderTypeRel.getPrimaryKeyObj(),
					commercePriceListOrderTypeRel);

				cacheResult(commercePriceListOrderTypeRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return map;
	}

	/**
	 * Returns all the commerce price list order type rels.
	 *
	 * @return the commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @return the range of commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list order type rels
	 * @param end the upper bound of the range of commerce price list order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list order type rels
	 */
	@Override
	public List<CommercePriceListOrderTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListOrderTypeRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache && productionMode) {
				finderPath = _finderPathWithoutPaginationFindAll;
				finderArgs = FINDER_ARGS_EMPTY;
			}
		}
		else if (useFinderCache && productionMode) {
			finderPath = _finderPathWithPaginationFindAll;
			finderArgs = new Object[] {start, end, orderByComparator};
		}

		List<CommercePriceListOrderTypeRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceListOrderTypeRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEPRICELISTORDERTYPEREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRICELISTORDERTYPEREL;

				sql = sql.concat(
					CommercePriceListOrderTypeRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommercePriceListOrderTypeRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache && productionMode) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the commerce price list order type rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePriceListOrderTypeRel commercePriceListOrderTypeRel :
				findAll()) {

			remove(commercePriceListOrderTypeRel);
		}
	}

	/**
	 * Returns the number of commerce price list order type rels.
	 *
	 * @return the number of commerce price list order type rels
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListOrderTypeRel.class);

		Long count = null;

		if (productionMode) {
			count = (Long)finderCache.getResult(
				_finderPathCountAll, FINDER_ARGS_EMPTY);
		}

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEPRICELISTORDERTYPEREL);

				count = (Long)query.uniqueResult();

				if (productionMode) {
					finderCache.putResult(
						_finderPathCountAll, FINDER_ARGS_EMPTY, count);
				}
			}
			catch (Exception exception) {
				throw processException(exception);
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
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "CPriceListOrderTypeRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEPRICELISTORDERTYPEREL;
	}

	@Override
	public Set<String> getCTColumnNames(
		CTColumnResolutionType ctColumnResolutionType) {

		return _ctColumnNamesMap.getOrDefault(
			ctColumnResolutionType, Collections.emptySet());
	}

	@Override
	public List<String> getMappingTableNames() {
		return _mappingTableNames;
	}

	@Override
	public Map<String, Integer> getTableColumnsMap() {
		return CommercePriceListOrderTypeRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CommercePriceListOrderTypeRel";
	}

	@Override
	public List<String[]> getUniqueIndexColumnNames() {
		return _uniqueIndexColumnNames;
	}

	private static final Map<CTColumnResolutionType, Set<String>>
		_ctColumnNamesMap = new EnumMap<CTColumnResolutionType, Set<String>>(
			CTColumnResolutionType.class);
	private static final List<String> _mappingTableNames =
		new ArrayList<String>();
	private static final List<String[]> _uniqueIndexColumnNames =
		new ArrayList<String[]>();

	static {
		Set<String> ctControlColumnNames = new HashSet<String>();
		Set<String> ctIgnoreColumnNames = new HashSet<String>();
		Set<String> ctStrictColumnNames = new HashSet<String>();

		ctControlColumnNames.add("mvccVersion");
		ctControlColumnNames.add("ctCollectionId");
		ctStrictColumnNames.add("uuid_");
		ctStrictColumnNames.add("companyId");
		ctStrictColumnNames.add("userId");
		ctStrictColumnNames.add("userName");
		ctStrictColumnNames.add("createDate");
		ctIgnoreColumnNames.add("modifiedDate");
		ctStrictColumnNames.add("commercePriceListId");
		ctStrictColumnNames.add("commerceOrderTypeId");
		ctStrictColumnNames.add("priority");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPriceListOrderTypeRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"commercePriceListId", "commerceOrderTypeId"});
	}

	/**
	 * Initializes the commerce price list order type rel persistence.
	 */
	public void afterPropertiesSet() {
		_valueObjectFinderCacheListThreshold = GetterUtil.getInteger(
			PropsUtil.get(PropsKeys.VALUE_OBJECT_FINDER_CACHE_LIST_THRESHOLD));

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid",
			new String[] {
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"uuid_"}, true);

		_finderPathWithoutPaginationFindByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			true);

		_finderPathCountByUuid = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid",
			new String[] {String.class.getName()}, new String[] {"uuid_"},
			false);

		_finderPathWithPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByUuid_C",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathWithoutPaginationFindByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, true);

		_finderPathCountByUuid_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByUuid_C",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"uuid_", "companyId"}, false);

		_finderPathWithPaginationFindByCommercePriceListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommercePriceListId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commercePriceListId"}, true);

		_finderPathWithoutPaginationFindByCommercePriceListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommercePriceListId", new String[] {Long.class.getName()},
			new String[] {"commercePriceListId"}, true);

		_finderPathCountByCommercePriceListId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommercePriceListId", new String[] {Long.class.getName()},
			new String[] {"commercePriceListId"}, false);

		_finderPathFetchByCPI_COTI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCPI_COTI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commercePriceListId", "commerceOrderTypeId"}, true);

		_finderPathCountByCPI_COTI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCPI_COTI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commercePriceListId", "commerceOrderTypeId"}, false);

		_setCommercePriceListOrderTypeRelUtilPersistence(this);
	}

	public void destroy() {
		_setCommercePriceListOrderTypeRelUtilPersistence(null);

		entityCache.removeCache(
			CommercePriceListOrderTypeRelImpl.class.getName());
	}

	private void _setCommercePriceListOrderTypeRelUtilPersistence(
		CommercePriceListOrderTypeRelPersistence
			commercePriceListOrderTypeRelPersistence) {

		try {
			Field field =
				CommercePriceListOrderTypeRelUtil.class.getDeclaredField(
					"_persistence");

			field.setAccessible(true);

			field.set(null, commercePriceListOrderTypeRelPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
	}

	@ServiceReference(type = CTPersistenceHelper.class)
	protected CTPersistenceHelper ctPersistenceHelper;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEPRICELISTORDERTYPEREL =
		"SELECT commercePriceListOrderTypeRel FROM CommercePriceListOrderTypeRel commercePriceListOrderTypeRel";

	private static final String
		_SQL_SELECT_COMMERCEPRICELISTORDERTYPEREL_WHERE =
			"SELECT commercePriceListOrderTypeRel FROM CommercePriceListOrderTypeRel commercePriceListOrderTypeRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEPRICELISTORDERTYPEREL =
		"SELECT COUNT(commercePriceListOrderTypeRel) FROM CommercePriceListOrderTypeRel commercePriceListOrderTypeRel";

	private static final String _SQL_COUNT_COMMERCEPRICELISTORDERTYPEREL_WHERE =
		"SELECT COUNT(commercePriceListOrderTypeRel) FROM CommercePriceListOrderTypeRel commercePriceListOrderTypeRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commercePriceListOrderTypeRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommercePriceListOrderTypeRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommercePriceListOrderTypeRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListOrderTypeRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "commercePriceListOrderTypeRelId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}