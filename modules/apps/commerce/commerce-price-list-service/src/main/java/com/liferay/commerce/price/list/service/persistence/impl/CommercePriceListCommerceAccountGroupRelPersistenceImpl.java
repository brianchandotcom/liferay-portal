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

import com.liferay.commerce.price.list.exception.NoSuchPriceListCommerceAccountGroupRelException;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRel;
import com.liferay.commerce.price.list.model.CommercePriceListCommerceAccountGroupRelTable;
import com.liferay.commerce.price.list.model.impl.CommercePriceListCommerceAccountGroupRelImpl;
import com.liferay.commerce.price.list.model.impl.CommercePriceListCommerceAccountGroupRelModelImpl;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListCommerceAccountGroupRelPersistence;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListCommerceAccountGroupRelUtil;
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
 * The persistence implementation for the commerce price list commerce account group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceListCommerceAccountGroupRelPersistenceImpl
	extends BasePersistenceImpl<CommercePriceListCommerceAccountGroupRel>
	implements CommercePriceListCommerceAccountGroupRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommercePriceListCommerceAccountGroupRelUtil</code> to access the commerce price list commerce account group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommercePriceListCommerceAccountGroupRelImpl.class.getName();

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
	 * Returns all the commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid(
		String uuid) {

		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @return the range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListCommerceAccountGroupRel.class);

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

		List<CommercePriceListCommerceAccountGroupRel> list = null;

		if (useFinderCache && productionMode) {
			list =
				(List<CommercePriceListCommerceAccountGroupRel>)
					finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListCommerceAccountGroupRel
						commercePriceListCommerceAccountGroupRel : list) {

					if (!uuid.equals(
							commercePriceListCommerceAccountGroupRel.
								getUuid())) {

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

			sb.append(
				_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE);

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
				sb.append(
					CommercePriceListCommerceAccountGroupRelModelImpl.
						ORDER_BY_JPQL);
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

				list =
					(List<CommercePriceListCommerceAccountGroupRel>)
						QueryUtil.list(query, getDialect(), start, end);

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
	 * Returns the first commerce price list commerce account group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel findByUuid_First(
			String uuid,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = fetchByUuid_First(
				uuid, orderByComparator);

		if (commercePriceListCommerceAccountGroupRel != null) {
			return commercePriceListCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPriceListCommerceAccountGroupRelException(
			sb.toString());
	}

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator) {

		List<CommercePriceListCommerceAccountGroupRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list commerce account group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel findByUuid_Last(
			String uuid,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = fetchByUuid_Last(
				uuid, orderByComparator);

		if (commercePriceListCommerceAccountGroupRel != null) {
			return commercePriceListCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPriceListCommerceAccountGroupRelException(
			sb.toString());
	}

	/**
	 * Returns the last commerce price list commerce account group rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListCommerceAccountGroupRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list commerce account group rels before and after the current commerce price list commerce account group rel in the ordered set where uuid = &#63;.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the current commerce price list commerce account group rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel[] findByUuid_PrevAndNext(
			long commercePriceListCommerceAccountGroupRelId, String uuid,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		uuid = Objects.toString(uuid, "");

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = findByPrimaryKey(
				commercePriceListCommerceAccountGroupRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListCommerceAccountGroupRel[] array =
				new CommercePriceListCommerceAccountGroupRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commercePriceListCommerceAccountGroupRel, uuid,
				orderByComparator, true);

			array[1] = commercePriceListCommerceAccountGroupRel;

			array[2] = getByUuid_PrevAndNext(
				session, commercePriceListCommerceAccountGroupRel, uuid,
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

	protected CommercePriceListCommerceAccountGroupRel getByUuid_PrevAndNext(
		Session session,
		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel,
		String uuid,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE);

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
			sb.append(
				CommercePriceListCommerceAccountGroupRelModelImpl.
					ORDER_BY_JPQL);
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
						commercePriceListCommerceAccountGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListCommerceAccountGroupRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list commerce account group rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel :
					findByUuid(
						uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commercePriceListCommerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce price list commerce account group rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list commerce account group rels
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListCommerceAccountGroupRel.class);

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

			sb.append(
				_SQL_COUNT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE);

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
		"commercePriceListCommerceAccountGroupRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commercePriceListCommerceAccountGroupRel.uuid IS NULL OR commercePriceListCommerceAccountGroupRel.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @return the range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListCommerceAccountGroupRel.class);

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

		List<CommercePriceListCommerceAccountGroupRel> list = null;

		if (useFinderCache && productionMode) {
			list =
				(List<CommercePriceListCommerceAccountGroupRel>)
					finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListCommerceAccountGroupRel
						commercePriceListCommerceAccountGroupRel : list) {

					if (!uuid.equals(
							commercePriceListCommerceAccountGroupRel.
								getUuid()) ||
						(companyId !=
							commercePriceListCommerceAccountGroupRel.
								getCompanyId())) {

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

			sb.append(
				_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE);

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
				sb.append(
					CommercePriceListCommerceAccountGroupRelModelImpl.
						ORDER_BY_JPQL);
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

				list =
					(List<CommercePriceListCommerceAccountGroupRel>)
						QueryUtil.list(query, getDialect(), start, end);

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
	 * Returns the first commerce price list commerce account group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = fetchByUuid_C_First(
				uuid, companyId, orderByComparator);

		if (commercePriceListCommerceAccountGroupRel != null) {
			return commercePriceListCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceListCommerceAccountGroupRelException(
			sb.toString());
	}

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator) {

		List<CommercePriceListCommerceAccountGroupRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list commerce account group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = fetchByUuid_C_Last(
				uuid, companyId, orderByComparator);

		if (commercePriceListCommerceAccountGroupRel != null) {
			return commercePriceListCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceListCommerceAccountGroupRelException(
			sb.toString());
	}

	/**
	 * Returns the last commerce price list commerce account group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListCommerceAccountGroupRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list commerce account group rels before and after the current commerce price list commerce account group rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the current commerce price list commerce account group rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel[] findByUuid_C_PrevAndNext(
			long commercePriceListCommerceAccountGroupRelId, String uuid,
			long companyId,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		uuid = Objects.toString(uuid, "");

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = findByPrimaryKey(
				commercePriceListCommerceAccountGroupRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListCommerceAccountGroupRel[] array =
				new CommercePriceListCommerceAccountGroupRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commercePriceListCommerceAccountGroupRel, uuid,
				companyId, orderByComparator, true);

			array[1] = commercePriceListCommerceAccountGroupRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, commercePriceListCommerceAccountGroupRel, uuid,
				companyId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePriceListCommerceAccountGroupRel getByUuid_C_PrevAndNext(
		Session session,
		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel,
		String uuid, long companyId,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE);

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
			sb.append(
				CommercePriceListCommerceAccountGroupRelModelImpl.
					ORDER_BY_JPQL);
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
						commercePriceListCommerceAccountGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListCommerceAccountGroupRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list commerce account group rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel :
					findByUuid_C(
						uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						null)) {

			remove(commercePriceListCommerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce price list commerce account group rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list commerce account group rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListCommerceAccountGroupRel.class);

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

			sb.append(
				_SQL_COUNT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE);

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
		"commercePriceListCommerceAccountGroupRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commercePriceListCommerceAccountGroupRel.uuid IS NULL OR commercePriceListCommerceAccountGroupRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commercePriceListCommerceAccountGroupRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCommercePriceListId;
	private FinderPath _finderPathWithoutPaginationFindByCommercePriceListId;
	private FinderPath _finderPathCountByCommercePriceListId;

	/**
	 * Returns all the commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel>
		findByCommercePriceListId(long commercePriceListId) {

		return findByCommercePriceListId(
			commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @return the range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end) {

		return findByCommercePriceListId(commercePriceListId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator) {

		return findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel>
		findByCommercePriceListId(
			long commercePriceListId, int start, int end,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator,
			boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListCommerceAccountGroupRel.class);

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

		List<CommercePriceListCommerceAccountGroupRel> list = null;

		if (useFinderCache && productionMode) {
			list =
				(List<CommercePriceListCommerceAccountGroupRel>)
					finderCache.getResult(finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListCommerceAccountGroupRel
						commercePriceListCommerceAccountGroupRel : list) {

					if (commercePriceListId !=
							commercePriceListCommerceAccountGroupRel.
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

			sb.append(
				_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommercePriceListCommerceAccountGroupRelModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				list =
					(List<CommercePriceListCommerceAccountGroupRel>)
						QueryUtil.list(query, getDialect(), start, end);

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
	 * Returns the first commerce price list commerce account group rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel
			findByCommercePriceListId_First(
				long commercePriceListId,
				OrderByComparator<CommercePriceListCommerceAccountGroupRel>
					orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				fetchByCommercePriceListId_First(
					commercePriceListId, orderByComparator);

		if (commercePriceListCommerceAccountGroupRel != null) {
			return commercePriceListCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append("}");

		throw new NoSuchPriceListCommerceAccountGroupRelException(
			sb.toString());
	}

	/**
	 * Returns the first commerce price list commerce account group rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel
		fetchByCommercePriceListId_First(
			long commercePriceListId,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator) {

		List<CommercePriceListCommerceAccountGroupRel> list =
			findByCommercePriceListId(
				commercePriceListId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list commerce account group rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel
			findByCommercePriceListId_Last(
				long commercePriceListId,
				OrderByComparator<CommercePriceListCommerceAccountGroupRel>
					orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				fetchByCommercePriceListId_Last(
					commercePriceListId, orderByComparator);

		if (commercePriceListCommerceAccountGroupRel != null) {
			return commercePriceListCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append("}");

		throw new NoSuchPriceListCommerceAccountGroupRelException(
			sb.toString());
	}

	/**
	 * Returns the last commerce price list commerce account group rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel
		fetchByCommercePriceListId_Last(
			long commercePriceListId,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator) {

		int count = countByCommercePriceListId(commercePriceListId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListCommerceAccountGroupRel> list =
			findByCommercePriceListId(
				commercePriceListId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list commerce account group rels before and after the current commerce price list commerce account group rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the current commerce price list commerce account group rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel[]
			findByCommercePriceListId_PrevAndNext(
				long commercePriceListCommerceAccountGroupRelId,
				long commercePriceListId,
				OrderByComparator<CommercePriceListCommerceAccountGroupRel>
					orderByComparator)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = findByPrimaryKey(
				commercePriceListCommerceAccountGroupRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListCommerceAccountGroupRel[] array =
				new CommercePriceListCommerceAccountGroupRelImpl[3];

			array[0] = getByCommercePriceListId_PrevAndNext(
				session, commercePriceListCommerceAccountGroupRel,
				commercePriceListId, orderByComparator, true);

			array[1] = commercePriceListCommerceAccountGroupRel;

			array[2] = getByCommercePriceListId_PrevAndNext(
				session, commercePriceListCommerceAccountGroupRel,
				commercePriceListId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePriceListCommerceAccountGroupRel
		getByCommercePriceListId_PrevAndNext(
			Session session,
			CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel,
			long commercePriceListId,
			OrderByComparator<CommercePriceListCommerceAccountGroupRel>
				orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE);

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
			sb.append(
				CommercePriceListCommerceAccountGroupRelModelImpl.
					ORDER_BY_JPQL);
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
						commercePriceListCommerceAccountGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListCommerceAccountGroupRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list commerce account group rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	@Override
	public void removeByCommercePriceListId(long commercePriceListId) {
		for (CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel :
					findByCommercePriceListId(
						commercePriceListId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commercePriceListCommerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce price list commerce account group rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list commerce account group rels
	 */
	@Override
	public int countByCommercePriceListId(long commercePriceListId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListCommerceAccountGroupRel.class);

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

			sb.append(
				_SQL_COUNT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE);

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
			"commercePriceListCommerceAccountGroupRel.commercePriceListId = ?";

	private FinderPath _finderPathFetchByCAGI_CPI;
	private FinderPath _finderPathCountByCAGI_CPI;

	/**
	 * Returns the commerce price list commerce account group rel where commercePriceListId = &#63; and commerceAccountGroupId = &#63; or throws a <code>NoSuchPriceListCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel findByCAGI_CPI(
			long commercePriceListId, long commerceAccountGroupId)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = fetchByCAGI_CPI(
				commercePriceListId, commerceAccountGroupId);

		if (commercePriceListCommerceAccountGroupRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commercePriceListId=");
			sb.append(commercePriceListId);

			sb.append(", commerceAccountGroupId=");
			sb.append(commerceAccountGroupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPriceListCommerceAccountGroupRelException(
				sb.toString());
		}

		return commercePriceListCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce price list commerce account group rel where commercePriceListId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByCAGI_CPI(
		long commercePriceListId, long commerceAccountGroupId) {

		return fetchByCAGI_CPI(
			commercePriceListId, commerceAccountGroupId, true);
	}

	/**
	 * Returns the commerce price list commerce account group rel where commercePriceListId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list commerce account group rel, or <code>null</code> if a matching commerce price list commerce account group rel could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByCAGI_CPI(
		long commercePriceListId, long commerceAccountGroupId,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListCommerceAccountGroupRel.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {
				commercePriceListId, commerceAccountGroupId
			};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByCAGI_CPI, finderArgs);
		}

		if (result instanceof CommercePriceListCommerceAccountGroupRel) {
			CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel =
					(CommercePriceListCommerceAccountGroupRel)result;

			if ((commercePriceListId !=
					commercePriceListCommerceAccountGroupRel.
						getCommercePriceListId()) ||
				(commerceAccountGroupId !=
					commercePriceListCommerceAccountGroupRel.
						getCommerceAccountGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(
				_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_CAGI_CPI_COMMERCEPRICELISTID_2);

			sb.append(_FINDER_COLUMN_CAGI_CPI_COMMERCEACCOUNTGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				queryPos.add(commerceAccountGroupId);

				List<CommercePriceListCommerceAccountGroupRel> list =
					query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByCAGI_CPI, finderArgs, list);
					}
				}
				else {
					CommercePriceListCommerceAccountGroupRel
						commercePriceListCommerceAccountGroupRel = list.get(0);

					result = commercePriceListCommerceAccountGroupRel;

					cacheResult(commercePriceListCommerceAccountGroupRel);
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
			return (CommercePriceListCommerceAccountGroupRel)result;
		}
	}

	/**
	 * Removes the commerce price list commerce account group rel where commercePriceListId = &#63; and commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the commerce price list commerce account group rel that was removed
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel removeByCAGI_CPI(
			long commercePriceListId, long commerceAccountGroupId)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = findByCAGI_CPI(
				commercePriceListId, commerceAccountGroupId);

		return remove(commercePriceListCommerceAccountGroupRel);
	}

	/**
	 * Returns the number of commerce price list commerce account group rels where commercePriceListId = &#63; and commerceAccountGroupId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce price list commerce account group rels
	 */
	@Override
	public int countByCAGI_CPI(
		long commercePriceListId, long commerceAccountGroupId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListCommerceAccountGroupRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCAGI_CPI;

			finderArgs = new Object[] {
				commercePriceListId, commerceAccountGroupId
			};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(
				_SQL_COUNT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_CAGI_CPI_COMMERCEPRICELISTID_2);

			sb.append(_FINDER_COLUMN_CAGI_CPI_COMMERCEACCOUNTGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				queryPos.add(commerceAccountGroupId);

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

	private static final String _FINDER_COLUMN_CAGI_CPI_COMMERCEPRICELISTID_2 =
		"commercePriceListCommerceAccountGroupRel.commercePriceListId = ? AND ";

	private static final String
		_FINDER_COLUMN_CAGI_CPI_COMMERCEACCOUNTGROUPID_2 =
			"commercePriceListCommerceAccountGroupRel.commerceAccountGroupId = ?";

	public CommercePriceListCommerceAccountGroupRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put(
			"commercePriceListCommerceAccountGroupRelId",
			"CPLCommerceAccountGroupRelId");
		dbColumnNames.put("order", "order_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommercePriceListCommerceAccountGroupRel.class);

		setModelImplClass(CommercePriceListCommerceAccountGroupRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommercePriceListCommerceAccountGroupRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce price list commerce account group rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListCommerceAccountGroupRel the commerce price list commerce account group rel
	 */
	@Override
	public void cacheResult(
		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel) {

		if (commercePriceListCommerceAccountGroupRel.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CommercePriceListCommerceAccountGroupRelImpl.class,
			commercePriceListCommerceAccountGroupRel.getPrimaryKey(),
			commercePriceListCommerceAccountGroupRel);

		finderCache.putResult(
			_finderPathFetchByCAGI_CPI,
			new Object[] {
				commercePriceListCommerceAccountGroupRel.
					getCommercePriceListId(),
				commercePriceListCommerceAccountGroupRel.
					getCommerceAccountGroupId()
			},
			commercePriceListCommerceAccountGroupRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce price list commerce account group rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListCommerceAccountGroupRels the commerce price list commerce account group rels
	 */
	@Override
	public void cacheResult(
		List<CommercePriceListCommerceAccountGroupRel>
			commercePriceListCommerceAccountGroupRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commercePriceListCommerceAccountGroupRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel :
					commercePriceListCommerceAccountGroupRels) {

			if (commercePriceListCommerceAccountGroupRel.getCtCollectionId() !=
					0) {

				continue;
			}

			if (entityCache.getResult(
					CommercePriceListCommerceAccountGroupRelImpl.class,
					commercePriceListCommerceAccountGroupRel.getPrimaryKey()) ==
						null) {

				cacheResult(commercePriceListCommerceAccountGroupRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce price list commerce account group rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(
			CommercePriceListCommerceAccountGroupRelImpl.class);

		finderCache.clearCache(
			CommercePriceListCommerceAccountGroupRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce price list commerce account group rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel) {

		entityCache.removeResult(
			CommercePriceListCommerceAccountGroupRelImpl.class,
			commercePriceListCommerceAccountGroupRel);
	}

	@Override
	public void clearCache(
		List<CommercePriceListCommerceAccountGroupRel>
			commercePriceListCommerceAccountGroupRels) {

		for (CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel :
					commercePriceListCommerceAccountGroupRels) {

			entityCache.removeResult(
				CommercePriceListCommerceAccountGroupRelImpl.class,
				commercePriceListCommerceAccountGroupRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(
			CommercePriceListCommerceAccountGroupRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommercePriceListCommerceAccountGroupRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePriceListCommerceAccountGroupRelModelImpl
			commercePriceListCommerceAccountGroupRelModelImpl) {

		Object[] args = new Object[] {
			commercePriceListCommerceAccountGroupRelModelImpl.
				getCommercePriceListId(),
			commercePriceListCommerceAccountGroupRelModelImpl.
				getCommerceAccountGroupId()
		};

		finderCache.putResult(
			_finderPathCountByCAGI_CPI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCAGI_CPI, args,
			commercePriceListCommerceAccountGroupRelModelImpl);
	}

	/**
	 * Creates a new commerce price list commerce account group rel with the primary key. Does not add the commerce price list commerce account group rel to the database.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key for the new commerce price list commerce account group rel
	 * @return the new commerce price list commerce account group rel
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel create(
		long commercePriceListCommerceAccountGroupRelId) {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel =
				new CommercePriceListCommerceAccountGroupRelImpl();

		commercePriceListCommerceAccountGroupRel.setNew(true);
		commercePriceListCommerceAccountGroupRel.setPrimaryKey(
			commercePriceListCommerceAccountGroupRelId);

		String uuid = PortalUUIDUtil.generate();

		commercePriceListCommerceAccountGroupRel.setUuid(uuid);

		commercePriceListCommerceAccountGroupRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commercePriceListCommerceAccountGroupRel;
	}

	/**
	 * Removes the commerce price list commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the commerce price list commerce account group rel
	 * @return the commerce price list commerce account group rel that was removed
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel remove(
			long commercePriceListCommerceAccountGroupRelId)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		return remove((Serializable)commercePriceListCommerceAccountGroupRelId);
	}

	/**
	 * Removes the commerce price list commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce price list commerce account group rel
	 * @return the commerce price list commerce account group rel that was removed
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel remove(
			Serializable primaryKey)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		Session session = null;

		try {
			session = openSession();

			CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel =
					(CommercePriceListCommerceAccountGroupRel)session.get(
						CommercePriceListCommerceAccountGroupRelImpl.class,
						primaryKey);

			if (commercePriceListCommerceAccountGroupRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPriceListCommerceAccountGroupRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commercePriceListCommerceAccountGroupRel);
		}
		catch (NoSuchPriceListCommerceAccountGroupRelException
					noSuchEntityException) {

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
	protected CommercePriceListCommerceAccountGroupRel removeImpl(
		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePriceListCommerceAccountGroupRel)) {
				commercePriceListCommerceAccountGroupRel =
					(CommercePriceListCommerceAccountGroupRel)session.get(
						CommercePriceListCommerceAccountGroupRelImpl.class,
						commercePriceListCommerceAccountGroupRel.
							getPrimaryKeyObj());
			}

			if ((commercePriceListCommerceAccountGroupRel != null) &&
				ctPersistenceHelper.isRemove(
					commercePriceListCommerceAccountGroupRel)) {

				session.delete(commercePriceListCommerceAccountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceListCommerceAccountGroupRel != null) {
			clearCache(commercePriceListCommerceAccountGroupRel);
		}

		return commercePriceListCommerceAccountGroupRel;
	}

	@Override
	public CommercePriceListCommerceAccountGroupRel updateImpl(
		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel) {

		boolean isNew = commercePriceListCommerceAccountGroupRel.isNew();

		if (!(commercePriceListCommerceAccountGroupRel instanceof
				CommercePriceListCommerceAccountGroupRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commercePriceListCommerceAccountGroupRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commercePriceListCommerceAccountGroupRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commercePriceListCommerceAccountGroupRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommercePriceListCommerceAccountGroupRel implementation " +
					commercePriceListCommerceAccountGroupRel.getClass());
		}

		CommercePriceListCommerceAccountGroupRelModelImpl
			commercePriceListCommerceAccountGroupRelModelImpl =
				(CommercePriceListCommerceAccountGroupRelModelImpl)
					commercePriceListCommerceAccountGroupRel;

		if (Validator.isNull(
				commercePriceListCommerceAccountGroupRel.getUuid())) {

			String uuid = PortalUUIDUtil.generate();

			commercePriceListCommerceAccountGroupRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(commercePriceListCommerceAccountGroupRel.getCreateDate() ==
				null)) {

			if (serviceContext == null) {
				commercePriceListCommerceAccountGroupRel.setCreateDate(date);
			}
			else {
				commercePriceListCommerceAccountGroupRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commercePriceListCommerceAccountGroupRelModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				commercePriceListCommerceAccountGroupRel.setModifiedDate(date);
			}
			else {
				commercePriceListCommerceAccountGroupRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(
					commercePriceListCommerceAccountGroupRel)) {

				if (!isNew) {
					session.evict(
						CommercePriceListCommerceAccountGroupRelImpl.class,
						commercePriceListCommerceAccountGroupRel.
							getPrimaryKeyObj());
				}

				session.save(commercePriceListCommerceAccountGroupRel);
			}
			else {
				commercePriceListCommerceAccountGroupRel =
					(CommercePriceListCommerceAccountGroupRel)session.merge(
						commercePriceListCommerceAccountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceListCommerceAccountGroupRel.getCtCollectionId() != 0) {
			if (isNew) {
				commercePriceListCommerceAccountGroupRel.setNew(false);
			}

			commercePriceListCommerceAccountGroupRel.resetOriginalValues();

			return commercePriceListCommerceAccountGroupRel;
		}

		entityCache.putResult(
			CommercePriceListCommerceAccountGroupRelImpl.class,
			commercePriceListCommerceAccountGroupRelModelImpl, false, true);

		cacheUniqueFindersCache(
			commercePriceListCommerceAccountGroupRelModelImpl);

		if (isNew) {
			commercePriceListCommerceAccountGroupRel.setNew(false);
		}

		commercePriceListCommerceAccountGroupRel.resetOriginalValues();

		return commercePriceListCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce price list commerce account group rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list commerce account group rel
	 * @return the commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = fetchByPrimaryKey(
				primaryKey);

		if (commercePriceListCommerceAccountGroupRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPriceListCommerceAccountGroupRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commercePriceListCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce price list commerce account group rel with the primary key or throws a <code>NoSuchPriceListCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the commerce price list commerce account group rel
	 * @return the commerce price list commerce account group rel
	 * @throws NoSuchPriceListCommerceAccountGroupRelException if a commerce price list commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel findByPrimaryKey(
			long commercePriceListCommerceAccountGroupRelId)
		throws NoSuchPriceListCommerceAccountGroupRelException {

		return findByPrimaryKey(
			(Serializable)commercePriceListCommerceAccountGroupRelId);
	}

	/**
	 * Returns the commerce price list commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list commerce account group rel
	 * @return the commerce price list commerce account group rel, or <code>null</code> if a commerce price list commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				CommercePriceListCommerceAccountGroupRel.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		CommercePriceListCommerceAccountGroupRel
			commercePriceListCommerceAccountGroupRel = null;

		Session session = null;

		try {
			session = openSession();

			commercePriceListCommerceAccountGroupRel =
				(CommercePriceListCommerceAccountGroupRel)session.get(
					CommercePriceListCommerceAccountGroupRelImpl.class,
					primaryKey);

			if (commercePriceListCommerceAccountGroupRel != null) {
				cacheResult(commercePriceListCommerceAccountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return commercePriceListCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce price list commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePriceListCommerceAccountGroupRelId the primary key of the commerce price list commerce account group rel
	 * @return the commerce price list commerce account group rel, or <code>null</code> if a commerce price list commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListCommerceAccountGroupRel fetchByPrimaryKey(
		long commercePriceListCommerceAccountGroupRelId) {

		return fetchByPrimaryKey(
			(Serializable)commercePriceListCommerceAccountGroupRelId);
	}

	@Override
	public Map<Serializable, CommercePriceListCommerceAccountGroupRel>
		fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CommercePriceListCommerceAccountGroupRel.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommercePriceListCommerceAccountGroupRel> map =
			new HashMap
				<Serializable, CommercePriceListCommerceAccountGroupRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel = fetchByPrimaryKey(
					primaryKey);

			if (commercePriceListCommerceAccountGroupRel != null) {
				map.put(primaryKey, commercePriceListCommerceAccountGroupRel);
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

			for (CommercePriceListCommerceAccountGroupRel
					commercePriceListCommerceAccountGroupRel :
						(List<CommercePriceListCommerceAccountGroupRel>)
							query.list()) {

				map.put(
					commercePriceListCommerceAccountGroupRel.getPrimaryKeyObj(),
					commercePriceListCommerceAccountGroupRel);

				cacheResult(commercePriceListCommerceAccountGroupRel);
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
	 * Returns all the commerce price list commerce account group rels.
	 *
	 * @return the commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @return the range of commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list commerce account group rels
	 * @param end the upper bound of the range of commerce price list commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list commerce account group rels
	 */
	@Override
	public List<CommercePriceListCommerceAccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListCommerceAccountGroupRel>
			orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListCommerceAccountGroupRel.class);

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

		List<CommercePriceListCommerceAccountGroupRel> list = null;

		if (useFinderCache && productionMode) {
			list =
				(List<CommercePriceListCommerceAccountGroupRel>)
					finderCache.getResult(finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL;

				sql = sql.concat(
					CommercePriceListCommerceAccountGroupRelModelImpl.
						ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list =
					(List<CommercePriceListCommerceAccountGroupRel>)
						QueryUtil.list(query, getDialect(), start, end);

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
	 * Removes all the commerce price list commerce account group rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePriceListCommerceAccountGroupRel
				commercePriceListCommerceAccountGroupRel : findAll()) {

			remove(commercePriceListCommerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce price list commerce account group rels.
	 *
	 * @return the number of commerce price list commerce account group rels
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListCommerceAccountGroupRel.class);

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
					_SQL_COUNT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL);

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
		return "CPLCommerceAccountGroupRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL;
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
		return CommercePriceListCommerceAccountGroupRelModelImpl.
			TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CPLCommerceGroupAccountRel";
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
		ctStrictColumnNames.add("commerceAccountGroupId");
		ctStrictColumnNames.add("order_");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CPLCommerceAccountGroupRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"commercePriceListId", "commerceAccountGroupId"});
	}

	/**
	 * Initializes the commerce price list commerce account group rel persistence.
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

		_finderPathFetchByCAGI_CPI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCAGI_CPI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commercePriceListId", "commerceAccountGroupId"},
			true);

		_finderPathCountByCAGI_CPI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCAGI_CPI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commercePriceListId", "commerceAccountGroupId"},
			false);

		_setCommercePriceListCommerceAccountGroupRelUtilPersistence(this);
	}

	public void destroy() {
		_setCommercePriceListCommerceAccountGroupRelUtilPersistence(null);

		entityCache.removeCache(
			CommercePriceListCommerceAccountGroupRelImpl.class.getName());
	}

	private void _setCommercePriceListCommerceAccountGroupRelUtilPersistence(
		CommercePriceListCommerceAccountGroupRelPersistence
			commercePriceListCommerceAccountGroupRelPersistence) {

		try {
			Field field =
				CommercePriceListCommerceAccountGroupRelUtil.class.
					getDeclaredField("_persistence");

			field.setAccessible(true);

			field.set(
				null, commercePriceListCommerceAccountGroupRelPersistence);
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

	private static final String
		_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL =
			"SELECT commercePriceListCommerceAccountGroupRel FROM CommercePriceListCommerceAccountGroupRel commercePriceListCommerceAccountGroupRel";

	private static final String
		_SQL_SELECT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE =
			"SELECT commercePriceListCommerceAccountGroupRel FROM CommercePriceListCommerceAccountGroupRel commercePriceListCommerceAccountGroupRel WHERE ";

	private static final String
		_SQL_COUNT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL =
			"SELECT COUNT(commercePriceListCommerceAccountGroupRel) FROM CommercePriceListCommerceAccountGroupRel commercePriceListCommerceAccountGroupRel";

	private static final String
		_SQL_COUNT_COMMERCEPRICELISTCOMMERCEACCOUNTGROUPREL_WHERE =
			"SELECT COUNT(commercePriceListCommerceAccountGroupRel) FROM CommercePriceListCommerceAccountGroupRel commercePriceListCommerceAccountGroupRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commercePriceListCommerceAccountGroupRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommercePriceListCommerceAccountGroupRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommercePriceListCommerceAccountGroupRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListCommerceAccountGroupRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {
			"uuid", "commercePriceListCommerceAccountGroupRelId", "order"
		});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}