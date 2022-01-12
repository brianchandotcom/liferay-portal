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

import com.liferay.commerce.price.list.exception.NoSuchPriceListAccountRelException;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRel;
import com.liferay.commerce.price.list.model.CommercePriceListAccountRelTable;
import com.liferay.commerce.price.list.model.impl.CommercePriceListAccountRelImpl;
import com.liferay.commerce.price.list.model.impl.CommercePriceListAccountRelModelImpl;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListAccountRelPersistence;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListAccountRelUtil;
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
 * The persistence implementation for the commerce price list account rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceListAccountRelPersistenceImpl
	extends BasePersistenceImpl<CommercePriceListAccountRel>
	implements CommercePriceListAccountRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommercePriceListAccountRelUtil</code> to access the commerce price list account rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommercePriceListAccountRelImpl.class.getName();

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
	 * Returns all the commerce price list account rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @return the range of matching commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListAccountRel.class);

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

		List<CommercePriceListAccountRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceListAccountRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListAccountRel commercePriceListAccountRel :
						list) {

					if (!uuid.equals(commercePriceListAccountRel.getUuid())) {
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

			sb.append(_SQL_SELECT_COMMERCEPRICELISTACCOUNTREL_WHERE);

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
				sb.append(CommercePriceListAccountRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommercePriceListAccountRel>)QueryUtil.list(
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
	 * Returns the first commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel findByUuid_First(
			String uuid,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException {

		CommercePriceListAccountRel commercePriceListAccountRel =
			fetchByUuid_First(uuid, orderByComparator);

		if (commercePriceListAccountRel != null) {
			return commercePriceListAccountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPriceListAccountRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		List<CommercePriceListAccountRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel findByUuid_Last(
			String uuid,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException {

		CommercePriceListAccountRel commercePriceListAccountRel =
			fetchByUuid_Last(uuid, orderByComparator);

		if (commercePriceListAccountRel != null) {
			return commercePriceListAccountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPriceListAccountRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListAccountRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list account rels before and after the current commerce price list account rel in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceListAccountRelId the primary key of the current commerce price list account rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListAccountRel[] findByUuid_PrevAndNext(
			long commercePriceListAccountRelId, String uuid,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException {

		uuid = Objects.toString(uuid, "");

		CommercePriceListAccountRel commercePriceListAccountRel =
			findByPrimaryKey(commercePriceListAccountRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListAccountRel[] array =
				new CommercePriceListAccountRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commercePriceListAccountRel, uuid, orderByComparator,
				true);

			array[1] = commercePriceListAccountRel;

			array[2] = getByUuid_PrevAndNext(
				session, commercePriceListAccountRel, uuid, orderByComparator,
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

	protected CommercePriceListAccountRel getByUuid_PrevAndNext(
		Session session,
		CommercePriceListAccountRel commercePriceListAccountRel, String uuid,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICELISTACCOUNTREL_WHERE);

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
			sb.append(CommercePriceListAccountRelModelImpl.ORDER_BY_JPQL);
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
						commercePriceListAccountRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListAccountRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list account rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommercePriceListAccountRel commercePriceListAccountRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commercePriceListAccountRel);
		}
	}

	/**
	 * Returns the number of commerce price list account rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list account rels
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListAccountRel.class);

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

			sb.append(_SQL_COUNT_COMMERCEPRICELISTACCOUNTREL_WHERE);

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
		"commercePriceListAccountRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commercePriceListAccountRel.uuid IS NULL OR commercePriceListAccountRel.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @return the range of matching commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListAccountRel.class);

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

		List<CommercePriceListAccountRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceListAccountRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListAccountRel commercePriceListAccountRel :
						list) {

					if (!uuid.equals(commercePriceListAccountRel.getUuid()) ||
						(companyId !=
							commercePriceListAccountRel.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCEPRICELISTACCOUNTREL_WHERE);

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
				sb.append(CommercePriceListAccountRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommercePriceListAccountRel>)QueryUtil.list(
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
	 * Returns the first commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException {

		CommercePriceListAccountRel commercePriceListAccountRel =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commercePriceListAccountRel != null) {
			return commercePriceListAccountRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceListAccountRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		List<CommercePriceListAccountRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException {

		CommercePriceListAccountRel commercePriceListAccountRel =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (commercePriceListAccountRel != null) {
			return commercePriceListAccountRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceListAccountRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListAccountRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list account rels before and after the current commerce price list account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListAccountRelId the primary key of the current commerce price list account rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListAccountRel[] findByUuid_C_PrevAndNext(
			long commercePriceListAccountRelId, String uuid, long companyId,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException {

		uuid = Objects.toString(uuid, "");

		CommercePriceListAccountRel commercePriceListAccountRel =
			findByPrimaryKey(commercePriceListAccountRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListAccountRel[] array =
				new CommercePriceListAccountRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commercePriceListAccountRel, uuid, companyId,
				orderByComparator, true);

			array[1] = commercePriceListAccountRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, commercePriceListAccountRel, uuid, companyId,
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

	protected CommercePriceListAccountRel getByUuid_C_PrevAndNext(
		Session session,
		CommercePriceListAccountRel commercePriceListAccountRel, String uuid,
		long companyId,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICELISTACCOUNTREL_WHERE);

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
			sb.append(CommercePriceListAccountRelModelImpl.ORDER_BY_JPQL);
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
						commercePriceListAccountRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListAccountRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list account rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommercePriceListAccountRel commercePriceListAccountRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commercePriceListAccountRel);
		}
	}

	/**
	 * Returns the number of commerce price list account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list account rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListAccountRel.class);

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

			sb.append(_SQL_COUNT_COMMERCEPRICELISTACCOUNTREL_WHERE);

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
		"commercePriceListAccountRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commercePriceListAccountRel.uuid IS NULL OR commercePriceListAccountRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commercePriceListAccountRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCommercePriceListId;
	private FinderPath _finderPathWithoutPaginationFindByCommercePriceListId;
	private FinderPath _finderPathCountByCommercePriceListId;

	/**
	 * Returns all the commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findByCommercePriceListId(
		long commercePriceListId) {

		return findByCommercePriceListId(
			commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @return the range of matching commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {

		return findByCommercePriceListId(commercePriceListId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListAccountRel.class);

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

		List<CommercePriceListAccountRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceListAccountRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListAccountRel commercePriceListAccountRel :
						list) {

					if (commercePriceListId !=
							commercePriceListAccountRel.
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

			sb.append(_SQL_SELECT_COMMERCEPRICELISTACCOUNTREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceListAccountRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				list = (List<CommercePriceListAccountRel>)QueryUtil.list(
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
	 * Returns the first commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel findByCommercePriceListId_First(
			long commercePriceListId,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException {

		CommercePriceListAccountRel commercePriceListAccountRel =
			fetchByCommercePriceListId_First(
				commercePriceListId, orderByComparator);

		if (commercePriceListAccountRel != null) {
			return commercePriceListAccountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append("}");

		throw new NoSuchPriceListAccountRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		List<CommercePriceListAccountRel> list = findByCommercePriceListId(
			commercePriceListId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel findByCommercePriceListId_Last(
			long commercePriceListId,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException {

		CommercePriceListAccountRel commercePriceListAccountRel =
			fetchByCommercePriceListId_Last(
				commercePriceListId, orderByComparator);

		if (commercePriceListAccountRel != null) {
			return commercePriceListAccountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append("}");

		throw new NoSuchPriceListAccountRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		int count = countByCommercePriceListId(commercePriceListId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListAccountRel> list = findByCommercePriceListId(
			commercePriceListId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list account rels before and after the current commerce price list account rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListAccountRelId the primary key of the current commerce price list account rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListAccountRel[] findByCommercePriceListId_PrevAndNext(
			long commercePriceListAccountRelId, long commercePriceListId,
			OrderByComparator<CommercePriceListAccountRel> orderByComparator)
		throws NoSuchPriceListAccountRelException {

		CommercePriceListAccountRel commercePriceListAccountRel =
			findByPrimaryKey(commercePriceListAccountRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListAccountRel[] array =
				new CommercePriceListAccountRelImpl[3];

			array[0] = getByCommercePriceListId_PrevAndNext(
				session, commercePriceListAccountRel, commercePriceListId,
				orderByComparator, true);

			array[1] = commercePriceListAccountRel;

			array[2] = getByCommercePriceListId_PrevAndNext(
				session, commercePriceListAccountRel, commercePriceListId,
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

	protected CommercePriceListAccountRel getByCommercePriceListId_PrevAndNext(
		Session session,
		CommercePriceListAccountRel commercePriceListAccountRel,
		long commercePriceListId,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICELISTACCOUNTREL_WHERE);

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
			sb.append(CommercePriceListAccountRelModelImpl.ORDER_BY_JPQL);
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
						commercePriceListAccountRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListAccountRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list account rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	@Override
	public void removeByCommercePriceListId(long commercePriceListId) {
		for (CommercePriceListAccountRel commercePriceListAccountRel :
				findByCommercePriceListId(
					commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commercePriceListAccountRel);
		}
	}

	/**
	 * Returns the number of commerce price list account rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list account rels
	 */
	@Override
	public int countByCommercePriceListId(long commercePriceListId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListAccountRel.class);

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

			sb.append(_SQL_COUNT_COMMERCEPRICELISTACCOUNTREL_WHERE);

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
			"commercePriceListAccountRel.commercePriceListId = ?";

	private FinderPath _finderPathFetchByCAI_CPI;
	private FinderPath _finderPathCountByCAI_CPI;

	/**
	 * Returns the commerce price list account rel where commerceAccountId = &#63; and commercePriceListId = &#63; or throws a <code>NoSuchPriceListAccountRelException</code> if it could not be found.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel findByCAI_CPI(
			long commerceAccountId, long commercePriceListId)
		throws NoSuchPriceListAccountRelException {

		CommercePriceListAccountRel commercePriceListAccountRel =
			fetchByCAI_CPI(commerceAccountId, commercePriceListId);

		if (commercePriceListAccountRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceAccountId=");
			sb.append(commerceAccountId);

			sb.append(", commercePriceListId=");
			sb.append(commercePriceListId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPriceListAccountRelException(sb.toString());
		}

		return commercePriceListAccountRel;
	}

	/**
	 * Returns the commerce price list account rel where commerceAccountId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel fetchByCAI_CPI(
		long commerceAccountId, long commercePriceListId) {

		return fetchByCAI_CPI(commerceAccountId, commercePriceListId, true);
	}

	/**
	 * Returns the commerce price list account rel where commerceAccountId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list account rel, or <code>null</code> if a matching commerce price list account rel could not be found
	 */
	@Override
	public CommercePriceListAccountRel fetchByCAI_CPI(
		long commerceAccountId, long commercePriceListId,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListAccountRel.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {commerceAccountId, commercePriceListId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByCAI_CPI, finderArgs);
		}

		if (result instanceof CommercePriceListAccountRel) {
			CommercePriceListAccountRel commercePriceListAccountRel =
				(CommercePriceListAccountRel)result;

			if ((commerceAccountId !=
					commercePriceListAccountRel.getCommerceAccountId()) ||
				(commercePriceListId !=
					commercePriceListAccountRel.getCommercePriceListId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEPRICELISTACCOUNTREL_WHERE);

			sb.append(_FINDER_COLUMN_CAI_CPI_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_CAI_CPI_COMMERCEPRICELISTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountId);

				queryPos.add(commercePriceListId);

				List<CommercePriceListAccountRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByCAI_CPI, finderArgs, list);
					}
				}
				else {
					CommercePriceListAccountRel commercePriceListAccountRel =
						list.get(0);

					result = commercePriceListAccountRel;

					cacheResult(commercePriceListAccountRel);
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
			return (CommercePriceListAccountRel)result;
		}
	}

	/**
	 * Removes the commerce price list account rel where commerceAccountId = &#63; and commercePriceListId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the commerce price list account rel that was removed
	 */
	@Override
	public CommercePriceListAccountRel removeByCAI_CPI(
			long commerceAccountId, long commercePriceListId)
		throws NoSuchPriceListAccountRelException {

		CommercePriceListAccountRel commercePriceListAccountRel = findByCAI_CPI(
			commerceAccountId, commercePriceListId);

		return remove(commercePriceListAccountRel);
	}

	/**
	 * Returns the number of commerce price list account rels where commerceAccountId = &#63; and commercePriceListId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list account rels
	 */
	@Override
	public int countByCAI_CPI(
		long commerceAccountId, long commercePriceListId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListAccountRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCAI_CPI;

			finderArgs = new Object[] {commerceAccountId, commercePriceListId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPRICELISTACCOUNTREL_WHERE);

			sb.append(_FINDER_COLUMN_CAI_CPI_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_CAI_CPI_COMMERCEPRICELISTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountId);

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

	private static final String _FINDER_COLUMN_CAI_CPI_COMMERCEACCOUNTID_2 =
		"commercePriceListAccountRel.commerceAccountId = ? AND ";

	private static final String _FINDER_COLUMN_CAI_CPI_COMMERCEPRICELISTID_2 =
		"commercePriceListAccountRel.commercePriceListId = ?";

	public CommercePriceListAccountRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("order", "order_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommercePriceListAccountRel.class);

		setModelImplClass(CommercePriceListAccountRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommercePriceListAccountRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce price list account rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListAccountRel the commerce price list account rel
	 */
	@Override
	public void cacheResult(
		CommercePriceListAccountRel commercePriceListAccountRel) {

		if (commercePriceListAccountRel.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CommercePriceListAccountRelImpl.class,
			commercePriceListAccountRel.getPrimaryKey(),
			commercePriceListAccountRel);

		finderCache.putResult(
			_finderPathFetchByCAI_CPI,
			new Object[] {
				commercePriceListAccountRel.getCommerceAccountId(),
				commercePriceListAccountRel.getCommercePriceListId()
			},
			commercePriceListAccountRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce price list account rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListAccountRels the commerce price list account rels
	 */
	@Override
	public void cacheResult(
		List<CommercePriceListAccountRel> commercePriceListAccountRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commercePriceListAccountRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommercePriceListAccountRel commercePriceListAccountRel :
				commercePriceListAccountRels) {

			if (commercePriceListAccountRel.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					CommercePriceListAccountRelImpl.class,
					commercePriceListAccountRel.getPrimaryKey()) == null) {

				cacheResult(commercePriceListAccountRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce price list account rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommercePriceListAccountRelImpl.class);

		finderCache.clearCache(CommercePriceListAccountRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce price list account rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommercePriceListAccountRel commercePriceListAccountRel) {

		entityCache.removeResult(
			CommercePriceListAccountRelImpl.class, commercePriceListAccountRel);
	}

	@Override
	public void clearCache(
		List<CommercePriceListAccountRel> commercePriceListAccountRels) {

		for (CommercePriceListAccountRel commercePriceListAccountRel :
				commercePriceListAccountRels) {

			entityCache.removeResult(
				CommercePriceListAccountRelImpl.class,
				commercePriceListAccountRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommercePriceListAccountRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommercePriceListAccountRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePriceListAccountRelModelImpl
			commercePriceListAccountRelModelImpl) {

		Object[] args = new Object[] {
			commercePriceListAccountRelModelImpl.getCommerceAccountId(),
			commercePriceListAccountRelModelImpl.getCommercePriceListId()
		};

		finderCache.putResult(_finderPathCountByCAI_CPI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCAI_CPI, args,
			commercePriceListAccountRelModelImpl);
	}

	/**
	 * Creates a new commerce price list account rel with the primary key. Does not add the commerce price list account rel to the database.
	 *
	 * @param commercePriceListAccountRelId the primary key for the new commerce price list account rel
	 * @return the new commerce price list account rel
	 */
	@Override
	public CommercePriceListAccountRel create(
		long commercePriceListAccountRelId) {

		CommercePriceListAccountRel commercePriceListAccountRel =
			new CommercePriceListAccountRelImpl();

		commercePriceListAccountRel.setNew(true);
		commercePriceListAccountRel.setPrimaryKey(
			commercePriceListAccountRelId);

		String uuid = PortalUUIDUtil.generate();

		commercePriceListAccountRel.setUuid(uuid);

		commercePriceListAccountRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commercePriceListAccountRel;
	}

	/**
	 * Removes the commerce price list account rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListAccountRelId the primary key of the commerce price list account rel
	 * @return the commerce price list account rel that was removed
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListAccountRel remove(
			long commercePriceListAccountRelId)
		throws NoSuchPriceListAccountRelException {

		return remove((Serializable)commercePriceListAccountRelId);
	}

	/**
	 * Removes the commerce price list account rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce price list account rel
	 * @return the commerce price list account rel that was removed
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListAccountRel remove(Serializable primaryKey)
		throws NoSuchPriceListAccountRelException {

		Session session = null;

		try {
			session = openSession();

			CommercePriceListAccountRel commercePriceListAccountRel =
				(CommercePriceListAccountRel)session.get(
					CommercePriceListAccountRelImpl.class, primaryKey);

			if (commercePriceListAccountRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPriceListAccountRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commercePriceListAccountRel);
		}
		catch (NoSuchPriceListAccountRelException noSuchEntityException) {
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
	protected CommercePriceListAccountRel removeImpl(
		CommercePriceListAccountRel commercePriceListAccountRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePriceListAccountRel)) {
				commercePriceListAccountRel =
					(CommercePriceListAccountRel)session.get(
						CommercePriceListAccountRelImpl.class,
						commercePriceListAccountRel.getPrimaryKeyObj());
			}

			if ((commercePriceListAccountRel != null) &&
				ctPersistenceHelper.isRemove(commercePriceListAccountRel)) {

				session.delete(commercePriceListAccountRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceListAccountRel != null) {
			clearCache(commercePriceListAccountRel);
		}

		return commercePriceListAccountRel;
	}

	@Override
	public CommercePriceListAccountRel updateImpl(
		CommercePriceListAccountRel commercePriceListAccountRel) {

		boolean isNew = commercePriceListAccountRel.isNew();

		if (!(commercePriceListAccountRel instanceof
				CommercePriceListAccountRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commercePriceListAccountRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commercePriceListAccountRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commercePriceListAccountRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommercePriceListAccountRel implementation " +
					commercePriceListAccountRel.getClass());
		}

		CommercePriceListAccountRelModelImpl
			commercePriceListAccountRelModelImpl =
				(CommercePriceListAccountRelModelImpl)
					commercePriceListAccountRel;

		if (Validator.isNull(commercePriceListAccountRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commercePriceListAccountRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commercePriceListAccountRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commercePriceListAccountRel.setCreateDate(date);
			}
			else {
				commercePriceListAccountRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commercePriceListAccountRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commercePriceListAccountRel.setModifiedDate(date);
			}
			else {
				commercePriceListAccountRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(commercePriceListAccountRel)) {
				if (!isNew) {
					session.evict(
						CommercePriceListAccountRelImpl.class,
						commercePriceListAccountRel.getPrimaryKeyObj());
				}

				session.save(commercePriceListAccountRel);
			}
			else {
				commercePriceListAccountRel =
					(CommercePriceListAccountRel)session.merge(
						commercePriceListAccountRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceListAccountRel.getCtCollectionId() != 0) {
			if (isNew) {
				commercePriceListAccountRel.setNew(false);
			}

			commercePriceListAccountRel.resetOriginalValues();

			return commercePriceListAccountRel;
		}

		entityCache.putResult(
			CommercePriceListAccountRelImpl.class,
			commercePriceListAccountRelModelImpl, false, true);

		cacheUniqueFindersCache(commercePriceListAccountRelModelImpl);

		if (isNew) {
			commercePriceListAccountRel.setNew(false);
		}

		commercePriceListAccountRel.resetOriginalValues();

		return commercePriceListAccountRel;
	}

	/**
	 * Returns the commerce price list account rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list account rel
	 * @return the commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListAccountRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPriceListAccountRelException {

		CommercePriceListAccountRel commercePriceListAccountRel =
			fetchByPrimaryKey(primaryKey);

		if (commercePriceListAccountRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPriceListAccountRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commercePriceListAccountRel;
	}

	/**
	 * Returns the commerce price list account rel with the primary key or throws a <code>NoSuchPriceListAccountRelException</code> if it could not be found.
	 *
	 * @param commercePriceListAccountRelId the primary key of the commerce price list account rel
	 * @return the commerce price list account rel
	 * @throws NoSuchPriceListAccountRelException if a commerce price list account rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListAccountRel findByPrimaryKey(
			long commercePriceListAccountRelId)
		throws NoSuchPriceListAccountRelException {

		return findByPrimaryKey((Serializable)commercePriceListAccountRelId);
	}

	/**
	 * Returns the commerce price list account rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list account rel
	 * @return the commerce price list account rel, or <code>null</code> if a commerce price list account rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListAccountRel fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				CommercePriceListAccountRel.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		CommercePriceListAccountRel commercePriceListAccountRel = null;

		Session session = null;

		try {
			session = openSession();

			commercePriceListAccountRel =
				(CommercePriceListAccountRel)session.get(
					CommercePriceListAccountRelImpl.class, primaryKey);

			if (commercePriceListAccountRel != null) {
				cacheResult(commercePriceListAccountRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return commercePriceListAccountRel;
	}

	/**
	 * Returns the commerce price list account rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListAccountRelId the primary key of the commerce price list account rel
	 * @return the commerce price list account rel, or <code>null</code> if a commerce price list account rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListAccountRel fetchByPrimaryKey(
		long commercePriceListAccountRelId) {

		return fetchByPrimaryKey((Serializable)commercePriceListAccountRelId);
	}

	@Override
	public Map<Serializable, CommercePriceListAccountRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CommercePriceListAccountRel.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommercePriceListAccountRel> map =
			new HashMap<Serializable, CommercePriceListAccountRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommercePriceListAccountRel commercePriceListAccountRel =
				fetchByPrimaryKey(primaryKey);

			if (commercePriceListAccountRel != null) {
				map.put(primaryKey, commercePriceListAccountRel);
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

			for (CommercePriceListAccountRel commercePriceListAccountRel :
					(List<CommercePriceListAccountRel>)query.list()) {

				map.put(
					commercePriceListAccountRel.getPrimaryKeyObj(),
					commercePriceListAccountRel);

				cacheResult(commercePriceListAccountRel);
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
	 * Returns all the commerce price list account rels.
	 *
	 * @return the commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @return the range of commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list account rels
	 * @param end the upper bound of the range of commerce price list account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list account rels
	 */
	@Override
	public List<CommercePriceListAccountRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListAccountRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListAccountRel.class);

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

		List<CommercePriceListAccountRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceListAccountRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEPRICELISTACCOUNTREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRICELISTACCOUNTREL;

				sql = sql.concat(
					CommercePriceListAccountRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommercePriceListAccountRel>)QueryUtil.list(
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
	 * Removes all the commerce price list account rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePriceListAccountRel commercePriceListAccountRel :
				findAll()) {

			remove(commercePriceListAccountRel);
		}
	}

	/**
	 * Returns the number of commerce price list account rels.
	 *
	 * @return the number of commerce price list account rels
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListAccountRel.class);

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
					_SQL_COUNT_COMMERCEPRICELISTACCOUNTREL);

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
		return "commercePriceListAccountRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEPRICELISTACCOUNTREL;
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
		return CommercePriceListAccountRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CommercePriceListAccountRel";
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
		ctStrictColumnNames.add("commerceAccountId");
		ctStrictColumnNames.add("commercePriceListId");
		ctStrictColumnNames.add("order_");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("commercePriceListAccountRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"commerceAccountId", "commercePriceListId"});
	}

	/**
	 * Initializes the commerce price list account rel persistence.
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

		_finderPathFetchByCAI_CPI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCAI_CPI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceAccountId", "commercePriceListId"}, true);

		_finderPathCountByCAI_CPI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCAI_CPI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceAccountId", "commercePriceListId"}, false);

		_setCommercePriceListAccountRelUtilPersistence(this);
	}

	public void destroy() {
		_setCommercePriceListAccountRelUtilPersistence(null);

		entityCache.removeCache(
			CommercePriceListAccountRelImpl.class.getName());
	}

	private void _setCommercePriceListAccountRelUtilPersistence(
		CommercePriceListAccountRelPersistence
			commercePriceListAccountRelPersistence) {

		try {
			Field field =
				CommercePriceListAccountRelUtil.class.getDeclaredField(
					"_persistence");

			field.setAccessible(true);

			field.set(null, commercePriceListAccountRelPersistence);
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

	private static final String _SQL_SELECT_COMMERCEPRICELISTACCOUNTREL =
		"SELECT commercePriceListAccountRel FROM CommercePriceListAccountRel commercePriceListAccountRel";

	private static final String _SQL_SELECT_COMMERCEPRICELISTACCOUNTREL_WHERE =
		"SELECT commercePriceListAccountRel FROM CommercePriceListAccountRel commercePriceListAccountRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEPRICELISTACCOUNTREL =
		"SELECT COUNT(commercePriceListAccountRel) FROM CommercePriceListAccountRel commercePriceListAccountRel";

	private static final String _SQL_COUNT_COMMERCEPRICELISTACCOUNTREL_WHERE =
		"SELECT COUNT(commercePriceListAccountRel) FROM CommercePriceListAccountRel commercePriceListAccountRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commercePriceListAccountRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommercePriceListAccountRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommercePriceListAccountRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListAccountRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "order"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}