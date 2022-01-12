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

import com.liferay.commerce.price.list.exception.NoSuchPriceListChannelRelException;
import com.liferay.commerce.price.list.model.CommercePriceListChannelRel;
import com.liferay.commerce.price.list.model.CommercePriceListChannelRelTable;
import com.liferay.commerce.price.list.model.impl.CommercePriceListChannelRelImpl;
import com.liferay.commerce.price.list.model.impl.CommercePriceListChannelRelModelImpl;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListChannelRelPersistence;
import com.liferay.commerce.price.list.service.persistence.CommercePriceListChannelRelUtil;
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
 * The persistence implementation for the commerce price list channel rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommercePriceListChannelRelPersistenceImpl
	extends BasePersistenceImpl<CommercePriceListChannelRel>
	implements CommercePriceListChannelRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommercePriceListChannelRelUtil</code> to access the commerce price list channel rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommercePriceListChannelRelImpl.class.getName();

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
	 * Returns all the commerce price list channel rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list channel rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @return the range of matching commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListChannelRel.class);

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

		List<CommercePriceListChannelRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceListChannelRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListChannelRel commercePriceListChannelRel :
						list) {

					if (!uuid.equals(commercePriceListChannelRel.getUuid())) {
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

			sb.append(_SQL_SELECT_COMMERCEPRICELISTCHANNELREL_WHERE);

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
				sb.append(CommercePriceListChannelRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommercePriceListChannelRel>)QueryUtil.list(
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
	 * Returns the first commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel findByUuid_First(
			String uuid,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			fetchByUuid_First(uuid, orderByComparator);

		if (commercePriceListChannelRel != null) {
			return commercePriceListChannelRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPriceListChannelRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		List<CommercePriceListChannelRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel findByUuid_Last(
			String uuid,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			fetchByUuid_Last(uuid, orderByComparator);

		if (commercePriceListChannelRel != null) {
			return commercePriceListChannelRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchPriceListChannelRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListChannelRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list channel rels before and after the current commerce price list channel rel in the ordered set where uuid = &#63;.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the current commerce price list channel rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListChannelRel[] findByUuid_PrevAndNext(
			long CommercePriceListChannelRelId, String uuid,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException {

		uuid = Objects.toString(uuid, "");

		CommercePriceListChannelRel commercePriceListChannelRel =
			findByPrimaryKey(CommercePriceListChannelRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListChannelRel[] array =
				new CommercePriceListChannelRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commercePriceListChannelRel, uuid, orderByComparator,
				true);

			array[1] = commercePriceListChannelRel;

			array[2] = getByUuid_PrevAndNext(
				session, commercePriceListChannelRel, uuid, orderByComparator,
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

	protected CommercePriceListChannelRel getByUuid_PrevAndNext(
		Session session,
		CommercePriceListChannelRel commercePriceListChannelRel, String uuid,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICELISTCHANNELREL_WHERE);

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
			sb.append(CommercePriceListChannelRelModelImpl.ORDER_BY_JPQL);
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
						commercePriceListChannelRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListChannelRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list channel rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommercePriceListChannelRel commercePriceListChannelRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commercePriceListChannelRel);
		}
	}

	/**
	 * Returns the number of commerce price list channel rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce price list channel rels
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListChannelRel.class);

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

			sb.append(_SQL_COUNT_COMMERCEPRICELISTCHANNELREL_WHERE);

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
		"commercePriceListChannelRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commercePriceListChannelRel.uuid IS NULL OR commercePriceListChannelRel.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @return the range of matching commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListChannelRel.class);

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

		List<CommercePriceListChannelRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceListChannelRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListChannelRel commercePriceListChannelRel :
						list) {

					if (!uuid.equals(commercePriceListChannelRel.getUuid()) ||
						(companyId !=
							commercePriceListChannelRel.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCEPRICELISTCHANNELREL_WHERE);

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
				sb.append(CommercePriceListChannelRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommercePriceListChannelRel>)QueryUtil.list(
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
	 * Returns the first commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commercePriceListChannelRel != null) {
			return commercePriceListChannelRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceListChannelRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		List<CommercePriceListChannelRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (commercePriceListChannelRel != null) {
			return commercePriceListChannelRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchPriceListChannelRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListChannelRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list channel rels before and after the current commerce price list channel rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the current commerce price list channel rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListChannelRel[] findByUuid_C_PrevAndNext(
			long CommercePriceListChannelRelId, String uuid, long companyId,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException {

		uuid = Objects.toString(uuid, "");

		CommercePriceListChannelRel commercePriceListChannelRel =
			findByPrimaryKey(CommercePriceListChannelRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListChannelRel[] array =
				new CommercePriceListChannelRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commercePriceListChannelRel, uuid, companyId,
				orderByComparator, true);

			array[1] = commercePriceListChannelRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, commercePriceListChannelRel, uuid, companyId,
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

	protected CommercePriceListChannelRel getByUuid_C_PrevAndNext(
		Session session,
		CommercePriceListChannelRel commercePriceListChannelRel, String uuid,
		long companyId,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICELISTCHANNELREL_WHERE);

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
			sb.append(CommercePriceListChannelRelModelImpl.ORDER_BY_JPQL);
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
						commercePriceListChannelRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListChannelRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list channel rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommercePriceListChannelRel commercePriceListChannelRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commercePriceListChannelRel);
		}
	}

	/**
	 * Returns the number of commerce price list channel rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce price list channel rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListChannelRel.class);

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

			sb.append(_SQL_COUNT_COMMERCEPRICELISTCHANNELREL_WHERE);

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
		"commercePriceListChannelRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commercePriceListChannelRel.uuid IS NULL OR commercePriceListChannelRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commercePriceListChannelRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCommercePriceListId;
	private FinderPath _finderPathWithoutPaginationFindByCommercePriceListId;
	private FinderPath _finderPathCountByCommercePriceListId;

	/**
	 * Returns all the commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findByCommercePriceListId(
		long commercePriceListId) {

		return findByCommercePriceListId(
			commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @return the range of matching commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end) {

		return findByCommercePriceListId(commercePriceListId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return findByCommercePriceListId(
			commercePriceListId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findByCommercePriceListId(
		long commercePriceListId, int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListChannelRel.class);

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

		List<CommercePriceListChannelRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceListChannelRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePriceListChannelRel commercePriceListChannelRel :
						list) {

					if (commercePriceListId !=
							commercePriceListChannelRel.
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

			sb.append(_SQL_SELECT_COMMERCEPRICELISTCHANNELREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEPRICELISTID_COMMERCEPRICELISTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommercePriceListChannelRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commercePriceListId);

				list = (List<CommercePriceListChannelRel>)QueryUtil.list(
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
	 * Returns the first commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel findByCommercePriceListId_First(
			long commercePriceListId,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			fetchByCommercePriceListId_First(
				commercePriceListId, orderByComparator);

		if (commercePriceListChannelRel != null) {
			return commercePriceListChannelRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append("}");

		throw new NoSuchPriceListChannelRelException(sb.toString());
	}

	/**
	 * Returns the first commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel fetchByCommercePriceListId_First(
		long commercePriceListId,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		List<CommercePriceListChannelRel> list = findByCommercePriceListId(
			commercePriceListId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel findByCommercePriceListId_Last(
			long commercePriceListId,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			fetchByCommercePriceListId_Last(
				commercePriceListId, orderByComparator);

		if (commercePriceListChannelRel != null) {
			return commercePriceListChannelRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commercePriceListId=");
		sb.append(commercePriceListId);

		sb.append("}");

		throw new NoSuchPriceListChannelRelException(sb.toString());
	}

	/**
	 * Returns the last commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel fetchByCommercePriceListId_Last(
		long commercePriceListId,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		int count = countByCommercePriceListId(commercePriceListId);

		if (count == 0) {
			return null;
		}

		List<CommercePriceListChannelRel> list = findByCommercePriceListId(
			commercePriceListId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce price list channel rels before and after the current commerce price list channel rel in the ordered set where commercePriceListId = &#63;.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the current commerce price list channel rel
	 * @param commercePriceListId the commerce price list ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListChannelRel[] findByCommercePriceListId_PrevAndNext(
			long CommercePriceListChannelRelId, long commercePriceListId,
			OrderByComparator<CommercePriceListChannelRel> orderByComparator)
		throws NoSuchPriceListChannelRelException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			findByPrimaryKey(CommercePriceListChannelRelId);

		Session session = null;

		try {
			session = openSession();

			CommercePriceListChannelRel[] array =
				new CommercePriceListChannelRelImpl[3];

			array[0] = getByCommercePriceListId_PrevAndNext(
				session, commercePriceListChannelRel, commercePriceListId,
				orderByComparator, true);

			array[1] = commercePriceListChannelRel;

			array[2] = getByCommercePriceListId_PrevAndNext(
				session, commercePriceListChannelRel, commercePriceListId,
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

	protected CommercePriceListChannelRel getByCommercePriceListId_PrevAndNext(
		Session session,
		CommercePriceListChannelRel commercePriceListChannelRel,
		long commercePriceListId,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEPRICELISTCHANNELREL_WHERE);

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
			sb.append(CommercePriceListChannelRelModelImpl.ORDER_BY_JPQL);
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
						commercePriceListChannelRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePriceListChannelRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce price list channel rels where commercePriceListId = &#63; from the database.
	 *
	 * @param commercePriceListId the commerce price list ID
	 */
	@Override
	public void removeByCommercePriceListId(long commercePriceListId) {
		for (CommercePriceListChannelRel commercePriceListChannelRel :
				findByCommercePriceListId(
					commercePriceListId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commercePriceListChannelRel);
		}
	}

	/**
	 * Returns the number of commerce price list channel rels where commercePriceListId = &#63;.
	 *
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list channel rels
	 */
	@Override
	public int countByCommercePriceListId(long commercePriceListId) {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListChannelRel.class);

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

			sb.append(_SQL_COUNT_COMMERCEPRICELISTCHANNELREL_WHERE);

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
			"commercePriceListChannelRel.commercePriceListId = ?";

	private FinderPath _finderPathFetchByCCI_CPI;
	private FinderPath _finderPathCountByCCI_CPI;

	/**
	 * Returns the commerce price list channel rel where commerceChannelId = &#63; and commercePriceListId = &#63; or throws a <code>NoSuchPriceListChannelRelException</code> if it could not be found.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel findByCCI_CPI(
			long commerceChannelId, long commercePriceListId)
		throws NoSuchPriceListChannelRelException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			fetchByCCI_CPI(commerceChannelId, commercePriceListId);

		if (commercePriceListChannelRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceChannelId=");
			sb.append(commerceChannelId);

			sb.append(", commercePriceListId=");
			sb.append(commercePriceListId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPriceListChannelRelException(sb.toString());
		}

		return commercePriceListChannelRel;
	}

	/**
	 * Returns the commerce price list channel rel where commerceChannelId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel fetchByCCI_CPI(
		long commerceChannelId, long commercePriceListId) {

		return fetchByCCI_CPI(commerceChannelId, commercePriceListId, true);
	}

	/**
	 * Returns the commerce price list channel rel where commerceChannelId = &#63; and commercePriceListId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce price list channel rel, or <code>null</code> if a matching commerce price list channel rel could not be found
	 */
	@Override
	public CommercePriceListChannelRel fetchByCCI_CPI(
		long commerceChannelId, long commercePriceListId,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListChannelRel.class);

		Object[] finderArgs = null;

		if (useFinderCache && productionMode) {
			finderArgs = new Object[] {commerceChannelId, commercePriceListId};
		}

		Object result = null;

		if (useFinderCache && productionMode) {
			result = finderCache.getResult(
				_finderPathFetchByCCI_CPI, finderArgs);
		}

		if (result instanceof CommercePriceListChannelRel) {
			CommercePriceListChannelRel commercePriceListChannelRel =
				(CommercePriceListChannelRel)result;

			if ((commerceChannelId !=
					commercePriceListChannelRel.getCommerceChannelId()) ||
				(commercePriceListId !=
					commercePriceListChannelRel.getCommercePriceListId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEPRICELISTCHANNELREL_WHERE);

			sb.append(_FINDER_COLUMN_CCI_CPI_COMMERCECHANNELID_2);

			sb.append(_FINDER_COLUMN_CCI_CPI_COMMERCEPRICELISTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceChannelId);

				queryPos.add(commercePriceListId);

				List<CommercePriceListChannelRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache && productionMode) {
						finderCache.putResult(
							_finderPathFetchByCCI_CPI, finderArgs, list);
					}
				}
				else {
					CommercePriceListChannelRel commercePriceListChannelRel =
						list.get(0);

					result = commercePriceListChannelRel;

					cacheResult(commercePriceListChannelRel);
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
			return (CommercePriceListChannelRel)result;
		}
	}

	/**
	 * Removes the commerce price list channel rel where commerceChannelId = &#63; and commercePriceListId = &#63; from the database.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the commerce price list channel rel that was removed
	 */
	@Override
	public CommercePriceListChannelRel removeByCCI_CPI(
			long commerceChannelId, long commercePriceListId)
		throws NoSuchPriceListChannelRelException {

		CommercePriceListChannelRel commercePriceListChannelRel = findByCCI_CPI(
			commerceChannelId, commercePriceListId);

		return remove(commercePriceListChannelRel);
	}

	/**
	 * Returns the number of commerce price list channel rels where commerceChannelId = &#63; and commercePriceListId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param commercePriceListId the commerce price list ID
	 * @return the number of matching commerce price list channel rels
	 */
	@Override
	public int countByCCI_CPI(
		long commerceChannelId, long commercePriceListId) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListChannelRel.class);

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		Long count = null;

		if (productionMode) {
			finderPath = _finderPathCountByCCI_CPI;

			finderArgs = new Object[] {commerceChannelId, commercePriceListId};

			count = (Long)finderCache.getResult(finderPath, finderArgs);
		}

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPRICELISTCHANNELREL_WHERE);

			sb.append(_FINDER_COLUMN_CCI_CPI_COMMERCECHANNELID_2);

			sb.append(_FINDER_COLUMN_CCI_CPI_COMMERCEPRICELISTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceChannelId);

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

	private static final String _FINDER_COLUMN_CCI_CPI_COMMERCECHANNELID_2 =
		"commercePriceListChannelRel.commerceChannelId = ? AND ";

	private static final String _FINDER_COLUMN_CCI_CPI_COMMERCEPRICELISTID_2 =
		"commercePriceListChannelRel.commercePriceListId = ?";

	public CommercePriceListChannelRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("order", "order_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommercePriceListChannelRel.class);

		setModelImplClass(CommercePriceListChannelRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommercePriceListChannelRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce price list channel rel in the entity cache if it is enabled.
	 *
	 * @param commercePriceListChannelRel the commerce price list channel rel
	 */
	@Override
	public void cacheResult(
		CommercePriceListChannelRel commercePriceListChannelRel) {

		if (commercePriceListChannelRel.getCtCollectionId() != 0) {
			return;
		}

		entityCache.putResult(
			CommercePriceListChannelRelImpl.class,
			commercePriceListChannelRel.getPrimaryKey(),
			commercePriceListChannelRel);

		finderCache.putResult(
			_finderPathFetchByCCI_CPI,
			new Object[] {
				commercePriceListChannelRel.getCommerceChannelId(),
				commercePriceListChannelRel.getCommercePriceListId()
			},
			commercePriceListChannelRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce price list channel rels in the entity cache if it is enabled.
	 *
	 * @param commercePriceListChannelRels the commerce price list channel rels
	 */
	@Override
	public void cacheResult(
		List<CommercePriceListChannelRel> commercePriceListChannelRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commercePriceListChannelRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommercePriceListChannelRel commercePriceListChannelRel :
				commercePriceListChannelRels) {

			if (commercePriceListChannelRel.getCtCollectionId() != 0) {
				continue;
			}

			if (entityCache.getResult(
					CommercePriceListChannelRelImpl.class,
					commercePriceListChannelRel.getPrimaryKey()) == null) {

				cacheResult(commercePriceListChannelRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce price list channel rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommercePriceListChannelRelImpl.class);

		finderCache.clearCache(CommercePriceListChannelRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce price list channel rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommercePriceListChannelRel commercePriceListChannelRel) {

		entityCache.removeResult(
			CommercePriceListChannelRelImpl.class, commercePriceListChannelRel);
	}

	@Override
	public void clearCache(
		List<CommercePriceListChannelRel> commercePriceListChannelRels) {

		for (CommercePriceListChannelRel commercePriceListChannelRel :
				commercePriceListChannelRels) {

			entityCache.removeResult(
				CommercePriceListChannelRelImpl.class,
				commercePriceListChannelRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommercePriceListChannelRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommercePriceListChannelRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePriceListChannelRelModelImpl
			commercePriceListChannelRelModelImpl) {

		Object[] args = new Object[] {
			commercePriceListChannelRelModelImpl.getCommerceChannelId(),
			commercePriceListChannelRelModelImpl.getCommercePriceListId()
		};

		finderCache.putResult(_finderPathCountByCCI_CPI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCCI_CPI, args,
			commercePriceListChannelRelModelImpl);
	}

	/**
	 * Creates a new commerce price list channel rel with the primary key. Does not add the commerce price list channel rel to the database.
	 *
	 * @param CommercePriceListChannelRelId the primary key for the new commerce price list channel rel
	 * @return the new commerce price list channel rel
	 */
	@Override
	public CommercePriceListChannelRel create(
		long CommercePriceListChannelRelId) {

		CommercePriceListChannelRel commercePriceListChannelRel =
			new CommercePriceListChannelRelImpl();

		commercePriceListChannelRel.setNew(true);
		commercePriceListChannelRel.setPrimaryKey(
			CommercePriceListChannelRelId);

		String uuid = PortalUUIDUtil.generate();

		commercePriceListChannelRel.setUuid(uuid);

		commercePriceListChannelRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commercePriceListChannelRel;
	}

	/**
	 * Removes the commerce price list channel rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the commerce price list channel rel
	 * @return the commerce price list channel rel that was removed
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListChannelRel remove(
			long CommercePriceListChannelRelId)
		throws NoSuchPriceListChannelRelException {

		return remove((Serializable)CommercePriceListChannelRelId);
	}

	/**
	 * Removes the commerce price list channel rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce price list channel rel
	 * @return the commerce price list channel rel that was removed
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListChannelRel remove(Serializable primaryKey)
		throws NoSuchPriceListChannelRelException {

		Session session = null;

		try {
			session = openSession();

			CommercePriceListChannelRel commercePriceListChannelRel =
				(CommercePriceListChannelRel)session.get(
					CommercePriceListChannelRelImpl.class, primaryKey);

			if (commercePriceListChannelRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPriceListChannelRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commercePriceListChannelRel);
		}
		catch (NoSuchPriceListChannelRelException noSuchEntityException) {
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
	protected CommercePriceListChannelRel removeImpl(
		CommercePriceListChannelRel commercePriceListChannelRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePriceListChannelRel)) {
				commercePriceListChannelRel =
					(CommercePriceListChannelRel)session.get(
						CommercePriceListChannelRelImpl.class,
						commercePriceListChannelRel.getPrimaryKeyObj());
			}

			if ((commercePriceListChannelRel != null) &&
				ctPersistenceHelper.isRemove(commercePriceListChannelRel)) {

				session.delete(commercePriceListChannelRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceListChannelRel != null) {
			clearCache(commercePriceListChannelRel);
		}

		return commercePriceListChannelRel;
	}

	@Override
	public CommercePriceListChannelRel updateImpl(
		CommercePriceListChannelRel commercePriceListChannelRel) {

		boolean isNew = commercePriceListChannelRel.isNew();

		if (!(commercePriceListChannelRel instanceof
				CommercePriceListChannelRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commercePriceListChannelRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commercePriceListChannelRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commercePriceListChannelRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommercePriceListChannelRel implementation " +
					commercePriceListChannelRel.getClass());
		}

		CommercePriceListChannelRelModelImpl
			commercePriceListChannelRelModelImpl =
				(CommercePriceListChannelRelModelImpl)
					commercePriceListChannelRel;

		if (Validator.isNull(commercePriceListChannelRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commercePriceListChannelRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commercePriceListChannelRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commercePriceListChannelRel.setCreateDate(date);
			}
			else {
				commercePriceListChannelRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commercePriceListChannelRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commercePriceListChannelRel.setModifiedDate(date);
			}
			else {
				commercePriceListChannelRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (ctPersistenceHelper.isInsert(commercePriceListChannelRel)) {
				if (!isNew) {
					session.evict(
						CommercePriceListChannelRelImpl.class,
						commercePriceListChannelRel.getPrimaryKeyObj());
				}

				session.save(commercePriceListChannelRel);
			}
			else {
				commercePriceListChannelRel =
					(CommercePriceListChannelRel)session.merge(
						commercePriceListChannelRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePriceListChannelRel.getCtCollectionId() != 0) {
			if (isNew) {
				commercePriceListChannelRel.setNew(false);
			}

			commercePriceListChannelRel.resetOriginalValues();

			return commercePriceListChannelRel;
		}

		entityCache.putResult(
			CommercePriceListChannelRelImpl.class,
			commercePriceListChannelRelModelImpl, false, true);

		cacheUniqueFindersCache(commercePriceListChannelRelModelImpl);

		if (isNew) {
			commercePriceListChannelRel.setNew(false);
		}

		commercePriceListChannelRel.resetOriginalValues();

		return commercePriceListChannelRel;
	}

	/**
	 * Returns the commerce price list channel rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list channel rel
	 * @return the commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListChannelRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchPriceListChannelRelException {

		CommercePriceListChannelRel commercePriceListChannelRel =
			fetchByPrimaryKey(primaryKey);

		if (commercePriceListChannelRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPriceListChannelRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commercePriceListChannelRel;
	}

	/**
	 * Returns the commerce price list channel rel with the primary key or throws a <code>NoSuchPriceListChannelRelException</code> if it could not be found.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the commerce price list channel rel
	 * @return the commerce price list channel rel
	 * @throws NoSuchPriceListChannelRelException if a commerce price list channel rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListChannelRel findByPrimaryKey(
			long CommercePriceListChannelRelId)
		throws NoSuchPriceListChannelRelException {

		return findByPrimaryKey((Serializable)CommercePriceListChannelRelId);
	}

	/**
	 * Returns the commerce price list channel rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce price list channel rel
	 * @return the commerce price list channel rel, or <code>null</code> if a commerce price list channel rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListChannelRel fetchByPrimaryKey(
		Serializable primaryKey) {

		if (ctPersistenceHelper.isProductionMode(
				CommercePriceListChannelRel.class)) {

			return super.fetchByPrimaryKey(primaryKey);
		}

		CommercePriceListChannelRel commercePriceListChannelRel = null;

		Session session = null;

		try {
			session = openSession();

			commercePriceListChannelRel =
				(CommercePriceListChannelRel)session.get(
					CommercePriceListChannelRelImpl.class, primaryKey);

			if (commercePriceListChannelRel != null) {
				cacheResult(commercePriceListChannelRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		return commercePriceListChannelRel;
	}

	/**
	 * Returns the commerce price list channel rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CommercePriceListChannelRelId the primary key of the commerce price list channel rel
	 * @return the commerce price list channel rel, or <code>null</code> if a commerce price list channel rel with the primary key could not be found
	 */
	@Override
	public CommercePriceListChannelRel fetchByPrimaryKey(
		long CommercePriceListChannelRelId) {

		return fetchByPrimaryKey((Serializable)CommercePriceListChannelRelId);
	}

	@Override
	public Map<Serializable, CommercePriceListChannelRel> fetchByPrimaryKeys(
		Set<Serializable> primaryKeys) {

		if (ctPersistenceHelper.isProductionMode(
				CommercePriceListChannelRel.class)) {

			return super.fetchByPrimaryKeys(primaryKeys);
		}

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommercePriceListChannelRel> map =
			new HashMap<Serializable, CommercePriceListChannelRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommercePriceListChannelRel commercePriceListChannelRel =
				fetchByPrimaryKey(primaryKey);

			if (commercePriceListChannelRel != null) {
				map.put(primaryKey, commercePriceListChannelRel);
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

			for (CommercePriceListChannelRel commercePriceListChannelRel :
					(List<CommercePriceListChannelRel>)query.list()) {

				map.put(
					commercePriceListChannelRel.getPrimaryKeyObj(),
					commercePriceListChannelRel);

				cacheResult(commercePriceListChannelRel);
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
	 * Returns all the commerce price list channel rels.
	 *
	 * @return the commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce price list channel rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @return the range of commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce price list channel rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePriceListChannelRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce price list channel rels
	 * @param end the upper bound of the range of commerce price list channel rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce price list channel rels
	 */
	@Override
	public List<CommercePriceListChannelRel> findAll(
		int start, int end,
		OrderByComparator<CommercePriceListChannelRel> orderByComparator,
		boolean useFinderCache) {

		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListChannelRel.class);

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

		List<CommercePriceListChannelRel> list = null;

		if (useFinderCache && productionMode) {
			list = (List<CommercePriceListChannelRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEPRICELISTCHANNELREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPRICELISTCHANNELREL;

				sql = sql.concat(
					CommercePriceListChannelRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommercePriceListChannelRel>)QueryUtil.list(
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
	 * Removes all the commerce price list channel rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePriceListChannelRel commercePriceListChannelRel :
				findAll()) {

			remove(commercePriceListChannelRel);
		}
	}

	/**
	 * Returns the number of commerce price list channel rels.
	 *
	 * @return the number of commerce price list channel rels
	 */
	@Override
	public int countAll() {
		boolean productionMode = ctPersistenceHelper.isProductionMode(
			CommercePriceListChannelRel.class);

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
					_SQL_COUNT_COMMERCEPRICELISTCHANNELREL);

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
		return "CommercePriceListChannelRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEPRICELISTCHANNELREL;
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
		return CommercePriceListChannelRelModelImpl.TABLE_COLUMNS_MAP;
	}

	@Override
	public String getTableName() {
		return "CommercePriceListChannelRel";
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
		ctStrictColumnNames.add("commerceChannelId");
		ctStrictColumnNames.add("commercePriceListId");
		ctStrictColumnNames.add("order_");
		ctStrictColumnNames.add("lastPublishDate");

		_ctColumnNamesMap.put(
			CTColumnResolutionType.CONTROL, ctControlColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.IGNORE, ctIgnoreColumnNames);
		_ctColumnNamesMap.put(
			CTColumnResolutionType.PK,
			Collections.singleton("CommercePriceListChannelRelId"));
		_ctColumnNamesMap.put(
			CTColumnResolutionType.STRICT, ctStrictColumnNames);

		_uniqueIndexColumnNames.add(
			new String[] {"commerceChannelId", "commercePriceListId"});
	}

	/**
	 * Initializes the commerce price list channel rel persistence.
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

		_finderPathFetchByCCI_CPI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCCI_CPI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceChannelId", "commercePriceListId"}, true);

		_finderPathCountByCCI_CPI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCCI_CPI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceChannelId", "commercePriceListId"}, false);

		_setCommercePriceListChannelRelUtilPersistence(this);
	}

	public void destroy() {
		_setCommercePriceListChannelRelUtilPersistence(null);

		entityCache.removeCache(
			CommercePriceListChannelRelImpl.class.getName());
	}

	private void _setCommercePriceListChannelRelUtilPersistence(
		CommercePriceListChannelRelPersistence
			commercePriceListChannelRelPersistence) {

		try {
			Field field =
				CommercePriceListChannelRelUtil.class.getDeclaredField(
					"_persistence");

			field.setAccessible(true);

			field.set(null, commercePriceListChannelRelPersistence);
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

	private static final String _SQL_SELECT_COMMERCEPRICELISTCHANNELREL =
		"SELECT commercePriceListChannelRel FROM CommercePriceListChannelRel commercePriceListChannelRel";

	private static final String _SQL_SELECT_COMMERCEPRICELISTCHANNELREL_WHERE =
		"SELECT commercePriceListChannelRel FROM CommercePriceListChannelRel commercePriceListChannelRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEPRICELISTCHANNELREL =
		"SELECT COUNT(commercePriceListChannelRel) FROM CommercePriceListChannelRel commercePriceListChannelRel";

	private static final String _SQL_COUNT_COMMERCEPRICELISTCHANNELREL_WHERE =
		"SELECT COUNT(commercePriceListChannelRel) FROM CommercePriceListChannelRel commercePriceListChannelRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commercePriceListChannelRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommercePriceListChannelRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommercePriceListChannelRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePriceListChannelRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "order"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}