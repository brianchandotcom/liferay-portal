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

package com.liferay.commerce.discount.service.persistence.impl;

import com.liferay.commerce.discount.exception.NoSuchDiscountOrderTypeRelException;
import com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRel;
import com.liferay.commerce.discount.model.CommerceDiscountOrderTypeRelTable;
import com.liferay.commerce.discount.model.impl.CommerceDiscountOrderTypeRelImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountOrderTypeRelModelImpl;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountOrderTypeRelPersistence;
import com.liferay.petra.string.StringBundler;
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

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/**
 * The persistence implementation for the commerce discount order type rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountOrderTypeRelPersistenceImpl
	extends BasePersistenceImpl<CommerceDiscountOrderTypeRel>
	implements CommerceDiscountOrderTypeRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceDiscountOrderTypeRelUtil</code> to access the commerce discount order type rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceDiscountOrderTypeRelImpl.class.getName();

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
	 * Returns all the commerce discount order type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid;
				finderArgs = new Object[] {uuid};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid;
			finderArgs = new Object[] {uuid, start, end, orderByComparator};
		}

		List<CommerceDiscountOrderTypeRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountOrderTypeRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel :
						list) {

					if (!uuid.equals(commerceDiscountOrderTypeRel.getUuid())) {
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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

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
				sb.append(CommerceDiscountOrderTypeRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceDiscountOrderTypeRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel findByUuid_First(
			String uuid,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			fetchByUuid_First(uuid, orderByComparator);

		if (commerceDiscountOrderTypeRel != null) {
			return commerceDiscountOrderTypeRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchDiscountOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the first commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		List<CommerceDiscountOrderTypeRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			fetchByUuid_Last(uuid, orderByComparator);

		if (commerceDiscountOrderTypeRel != null) {
			return commerceDiscountOrderTypeRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchDiscountOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountOrderTypeRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount order type rels before and after the current commerce discount order type rel in the ordered set where uuid = &#63;.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the current commerce discount order type rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel[] findByUuid_PrevAndNext(
			long commerceDiscountOrderTypeRelId, String uuid,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException {

		uuid = Objects.toString(uuid, "");

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			findByPrimaryKey(commerceDiscountOrderTypeRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountOrderTypeRel[] array =
				new CommerceDiscountOrderTypeRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commerceDiscountOrderTypeRel, uuid, orderByComparator,
				true);

			array[1] = commerceDiscountOrderTypeRel;

			array[2] = getByUuid_PrevAndNext(
				session, commerceDiscountOrderTypeRel, uuid, orderByComparator,
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

	protected CommerceDiscountOrderTypeRel getByUuid_PrevAndNext(
		Session session,
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel, String uuid,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

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
			sb.append(CommerceDiscountOrderTypeRelModelImpl.ORDER_BY_JPQL);
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
						commerceDiscountOrderTypeRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountOrderTypeRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount order type rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceDiscountOrderTypeRel);
		}
	}

	/**
	 * Returns the number of commerce discount order type rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce discount order type rels
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

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

				finderCache.putResult(finderPath, finderArgs, count);
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
		"commerceDiscountOrderTypeRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commerceDiscountOrderTypeRel.uuid IS NULL OR commerceDiscountOrderTypeRel.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByUuid_C;
				finderArgs = new Object[] {uuid, companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByUuid_C;
			finderArgs = new Object[] {
				uuid, companyId, start, end, orderByComparator
			};
		}

		List<CommerceDiscountOrderTypeRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountOrderTypeRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel :
						list) {

					if (!uuid.equals(commerceDiscountOrderTypeRel.getUuid()) ||
						(companyId !=
							commerceDiscountOrderTypeRel.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

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
				sb.append(CommerceDiscountOrderTypeRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceDiscountOrderTypeRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commerceDiscountOrderTypeRel != null) {
			return commerceDiscountOrderTypeRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDiscountOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the first commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		List<CommerceDiscountOrderTypeRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (commerceDiscountOrderTypeRel != null) {
			return commerceDiscountOrderTypeRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDiscountOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountOrderTypeRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount order type rels before and after the current commerce discount order type rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the current commerce discount order type rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel[] findByUuid_C_PrevAndNext(
			long commerceDiscountOrderTypeRelId, String uuid, long companyId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException {

		uuid = Objects.toString(uuid, "");

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			findByPrimaryKey(commerceDiscountOrderTypeRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountOrderTypeRel[] array =
				new CommerceDiscountOrderTypeRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commerceDiscountOrderTypeRel, uuid, companyId,
				orderByComparator, true);

			array[1] = commerceDiscountOrderTypeRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, commerceDiscountOrderTypeRel, uuid, companyId,
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

	protected CommerceDiscountOrderTypeRel getByUuid_C_PrevAndNext(
		Session session,
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel, String uuid,
		long companyId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

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
			sb.append(CommerceDiscountOrderTypeRelModelImpl.ORDER_BY_JPQL);
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
						commerceDiscountOrderTypeRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountOrderTypeRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount order type rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceDiscountOrderTypeRel);
		}
	}

	/**
	 * Returns the number of commerce discount order type rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce discount order type rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

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

				finderCache.putResult(finderPath, finderArgs, count);
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
		"commerceDiscountOrderTypeRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commerceDiscountOrderTypeRel.uuid IS NULL OR commerceDiscountOrderTypeRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceDiscountOrderTypeRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCommerceDiscountId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceDiscountId;
	private FinderPath _finderPathCountByCommerceDiscountId;

	/**
	 * Returns all the commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByCommerceDiscountId(
		long commerceDiscountId) {

		return findByCommerceDiscountId(
			commerceDiscountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end) {

		return findByCommerceDiscountId(commerceDiscountId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return findByCommerceDiscountId(
			commerceDiscountId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceDiscountId;
				finderArgs = new Object[] {commerceDiscountId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceDiscountId;
			finderArgs = new Object[] {
				commerceDiscountId, start, end, orderByComparator
			};
		}

		List<CommerceDiscountOrderTypeRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountOrderTypeRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel :
						list) {

					if (commerceDiscountId !=
							commerceDiscountOrderTypeRel.
								getCommerceDiscountId()) {

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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceDiscountOrderTypeRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceDiscountId);

				list = (List<CommerceDiscountOrderTypeRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel findByCommerceDiscountId_First(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			fetchByCommerceDiscountId_First(
				commerceDiscountId, orderByComparator);

		if (commerceDiscountOrderTypeRel != null) {
			return commerceDiscountOrderTypeRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the first commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel fetchByCommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		List<CommerceDiscountOrderTypeRel> list = findByCommerceDiscountId(
			commerceDiscountId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel findByCommerceDiscountId_Last(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			fetchByCommerceDiscountId_Last(
				commerceDiscountId, orderByComparator);

		if (commerceDiscountOrderTypeRel != null) {
			return commerceDiscountOrderTypeRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel fetchByCommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		int count = countByCommerceDiscountId(commerceDiscountId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountOrderTypeRel> list = findByCommerceDiscountId(
			commerceDiscountId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount order type rels before and after the current commerce discount order type rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the current commerce discount order type rel
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel[] findByCommerceDiscountId_PrevAndNext(
			long commerceDiscountOrderTypeRelId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			findByPrimaryKey(commerceDiscountOrderTypeRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountOrderTypeRel[] array =
				new CommerceDiscountOrderTypeRelImpl[3];

			array[0] = getByCommerceDiscountId_PrevAndNext(
				session, commerceDiscountOrderTypeRel, commerceDiscountId,
				orderByComparator, true);

			array[1] = commerceDiscountOrderTypeRel;

			array[2] = getByCommerceDiscountId_PrevAndNext(
				session, commerceDiscountOrderTypeRel, commerceDiscountId,
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

	protected CommerceDiscountOrderTypeRel getByCommerceDiscountId_PrevAndNext(
		Session session,
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel,
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

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
			sb.append(CommerceDiscountOrderTypeRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceDiscountId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceDiscountOrderTypeRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountOrderTypeRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount order type rels where commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 */
	@Override
	public void removeByCommerceDiscountId(long commerceDiscountId) {
		for (CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel :
				findByCommerceDiscountId(
					commerceDiscountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceDiscountOrderTypeRel);
		}
	}

	/**
	 * Returns the number of commerce discount order type rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount order type rels
	 */
	@Override
	public int countByCommerceDiscountId(long commerceDiscountId) {
		FinderPath finderPath = _finderPathCountByCommerceDiscountId;

		Object[] finderArgs = new Object[] {commerceDiscountId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceDiscountId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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
		_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2 =
			"commerceDiscountOrderTypeRel.commerceDiscountId = ?";

	private FinderPath _finderPathWithPaginationFindByCommerceOrderTypeId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceOrderTypeId;
	private FinderPath _finderPathCountByCommerceOrderTypeId;

	/**
	 * Returns all the commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId) {

		return findByCommerceOrderTypeId(
			commerceOrderTypeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end) {

		return findByCommerceOrderTypeId(commerceOrderTypeId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return findByCommerceOrderTypeId(
			commerceOrderTypeId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findByCommerceOrderTypeId(
		long commerceOrderTypeId, int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceOrderTypeId;
				finderArgs = new Object[] {commerceOrderTypeId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceOrderTypeId;
			finderArgs = new Object[] {
				commerceOrderTypeId, start, end, orderByComparator
			};
		}

		List<CommerceDiscountOrderTypeRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountOrderTypeRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel :
						list) {

					if (commerceOrderTypeId !=
							commerceDiscountOrderTypeRel.
								getCommerceOrderTypeId()) {

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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEORDERTYPEID_COMMERCEORDERTYPEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceDiscountOrderTypeRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderTypeId);

				list = (List<CommerceDiscountOrderTypeRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Returns the first commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel findByCommerceOrderTypeId_First(
			long commerceOrderTypeId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			fetchByCommerceOrderTypeId_First(
				commerceOrderTypeId, orderByComparator);

		if (commerceDiscountOrderTypeRel != null) {
			return commerceDiscountOrderTypeRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderTypeId=");
		sb.append(commerceOrderTypeId);

		sb.append("}");

		throw new NoSuchDiscountOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the first commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel fetchByCommerceOrderTypeId_First(
		long commerceOrderTypeId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		List<CommerceDiscountOrderTypeRel> list = findByCommerceOrderTypeId(
			commerceOrderTypeId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel findByCommerceOrderTypeId_Last(
			long commerceOrderTypeId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			fetchByCommerceOrderTypeId_Last(
				commerceOrderTypeId, orderByComparator);

		if (commerceDiscountOrderTypeRel != null) {
			return commerceDiscountOrderTypeRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderTypeId=");
		sb.append(commerceOrderTypeId);

		sb.append("}");

		throw new NoSuchDiscountOrderTypeRelException(sb.toString());
	}

	/**
	 * Returns the last commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel fetchByCommerceOrderTypeId_Last(
		long commerceOrderTypeId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		int count = countByCommerceOrderTypeId(commerceOrderTypeId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountOrderTypeRel> list = findByCommerceOrderTypeId(
			commerceOrderTypeId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount order type rels before and after the current commerce discount order type rel in the ordered set where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the current commerce discount order type rel
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel[] findByCommerceOrderTypeId_PrevAndNext(
			long commerceDiscountOrderTypeRelId, long commerceOrderTypeId,
			OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator)
		throws NoSuchDiscountOrderTypeRelException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			findByPrimaryKey(commerceDiscountOrderTypeRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountOrderTypeRel[] array =
				new CommerceDiscountOrderTypeRelImpl[3];

			array[0] = getByCommerceOrderTypeId_PrevAndNext(
				session, commerceDiscountOrderTypeRel, commerceOrderTypeId,
				orderByComparator, true);

			array[1] = commerceDiscountOrderTypeRel;

			array[2] = getByCommerceOrderTypeId_PrevAndNext(
				session, commerceDiscountOrderTypeRel, commerceOrderTypeId,
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

	protected CommerceDiscountOrderTypeRel getByCommerceOrderTypeId_PrevAndNext(
		Session session,
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel,
		long commerceOrderTypeId,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEORDERTYPEID_COMMERCEORDERTYPEID_2);

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
			sb.append(CommerceDiscountOrderTypeRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceOrderTypeId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceDiscountOrderTypeRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountOrderTypeRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount order type rels where commerceOrderTypeId = &#63; from the database.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 */
	@Override
	public void removeByCommerceOrderTypeId(long commerceOrderTypeId) {
		for (CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel :
				findByCommerceOrderTypeId(
					commerceOrderTypeId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceDiscountOrderTypeRel);
		}
	}

	/**
	 * Returns the number of commerce discount order type rels where commerceOrderTypeId = &#63;.
	 *
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce discount order type rels
	 */
	@Override
	public int countByCommerceOrderTypeId(long commerceOrderTypeId) {
		FinderPath finderPath = _finderPathCountByCommerceOrderTypeId;

		Object[] finderArgs = new Object[] {commerceOrderTypeId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEORDERTYPEID_COMMERCEORDERTYPEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderTypeId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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
		_FINDER_COLUMN_COMMERCEORDERTYPEID_COMMERCEORDERTYPEID_2 =
			"commerceDiscountOrderTypeRel.commerceOrderTypeId = ?";

	private FinderPath _finderPathFetchByCDI_COTI;
	private FinderPath _finderPathCountByCDI_COTI;

	/**
	 * Returns the commerce discount order type rel where commerceDiscountId = &#63; and commerceOrderTypeId = &#63; or throws a <code>NoSuchDiscountOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel findByCDI_COTI(
			long commerceDiscountId, long commerceOrderTypeId)
		throws NoSuchDiscountOrderTypeRelException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			fetchByCDI_COTI(commerceDiscountId, commerceOrderTypeId);

		if (commerceDiscountOrderTypeRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceDiscountId=");
			sb.append(commerceDiscountId);

			sb.append(", commerceOrderTypeId=");
			sb.append(commerceOrderTypeId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDiscountOrderTypeRelException(sb.toString());
		}

		return commerceDiscountOrderTypeRel;
	}

	/**
	 * Returns the commerce discount order type rel where commerceDiscountId = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel fetchByCDI_COTI(
		long commerceDiscountId, long commerceOrderTypeId) {

		return fetchByCDI_COTI(commerceDiscountId, commerceOrderTypeId, true);
	}

	/**
	 * Returns the commerce discount order type rel where commerceDiscountId = &#63; and commerceOrderTypeId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce discount order type rel, or <code>null</code> if a matching commerce discount order type rel could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel fetchByCDI_COTI(
		long commerceDiscountId, long commerceOrderTypeId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {commerceDiscountId, commerceOrderTypeId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCDI_COTI, finderArgs);
		}

		if (result instanceof CommerceDiscountOrderTypeRel) {
			CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
				(CommerceDiscountOrderTypeRel)result;

			if ((commerceDiscountId !=
					commerceDiscountOrderTypeRel.getCommerceDiscountId()) ||
				(commerceOrderTypeId !=
					commerceDiscountOrderTypeRel.getCommerceOrderTypeId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_CDI_COTI_COMMERCEDISCOUNTID_2);

			sb.append(_FINDER_COLUMN_CDI_COTI_COMMERCEORDERTYPEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceDiscountId);

				queryPos.add(commerceOrderTypeId);

				List<CommerceDiscountOrderTypeRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCDI_COTI, finderArgs, list);
					}
				}
				else {
					CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
						list.get(0);

					result = commerceDiscountOrderTypeRel;

					cacheResult(commerceDiscountOrderTypeRel);
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
			return (CommerceDiscountOrderTypeRel)result;
		}
	}

	/**
	 * Removes the commerce discount order type rel where commerceDiscountId = &#63; and commerceOrderTypeId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the commerce discount order type rel that was removed
	 */
	@Override
	public CommerceDiscountOrderTypeRel removeByCDI_COTI(
			long commerceDiscountId, long commerceOrderTypeId)
		throws NoSuchDiscountOrderTypeRelException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			findByCDI_COTI(commerceDiscountId, commerceOrderTypeId);

		return remove(commerceDiscountOrderTypeRel);
	}

	/**
	 * Returns the number of commerce discount order type rels where commerceDiscountId = &#63; and commerceOrderTypeId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param commerceOrderTypeId the commerce order type ID
	 * @return the number of matching commerce discount order type rels
	 */
	@Override
	public int countByCDI_COTI(
		long commerceDiscountId, long commerceOrderTypeId) {

		FinderPath finderPath = _finderPathCountByCDI_COTI;

		Object[] finderArgs = new Object[] {
			commerceDiscountId, commerceOrderTypeId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTORDERTYPEREL_WHERE);

			sb.append(_FINDER_COLUMN_CDI_COTI_COMMERCEDISCOUNTID_2);

			sb.append(_FINDER_COLUMN_CDI_COTI_COMMERCEORDERTYPEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceDiscountId);

				queryPos.add(commerceOrderTypeId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
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

	private static final String _FINDER_COLUMN_CDI_COTI_COMMERCEDISCOUNTID_2 =
		"commerceDiscountOrderTypeRel.commerceDiscountId = ? AND ";

	private static final String _FINDER_COLUMN_CDI_COTI_COMMERCEORDERTYPEID_2 =
		"commerceDiscountOrderTypeRel.commerceOrderTypeId = ?";

	public CommerceDiscountOrderTypeRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceDiscountOrderTypeRel.class);

		setModelImplClass(CommerceDiscountOrderTypeRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceDiscountOrderTypeRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce discount order type rel in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountOrderTypeRel the commerce discount order type rel
	 */
	@Override
	public void cacheResult(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

		entityCache.putResult(
			CommerceDiscountOrderTypeRelImpl.class,
			commerceDiscountOrderTypeRel.getPrimaryKey(),
			commerceDiscountOrderTypeRel);

		finderCache.putResult(
			_finderPathFetchByCDI_COTI,
			new Object[] {
				commerceDiscountOrderTypeRel.getCommerceDiscountId(),
				commerceDiscountOrderTypeRel.getCommerceOrderTypeId()
			},
			commerceDiscountOrderTypeRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce discount order type rels in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountOrderTypeRels the commerce discount order type rels
	 */
	@Override
	public void cacheResult(
		List<CommerceDiscountOrderTypeRel> commerceDiscountOrderTypeRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceDiscountOrderTypeRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel :
				commerceDiscountOrderTypeRels) {

			if (entityCache.getResult(
					CommerceDiscountOrderTypeRelImpl.class,
					commerceDiscountOrderTypeRel.getPrimaryKey()) == null) {

				cacheResult(commerceDiscountOrderTypeRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce discount order type rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceDiscountOrderTypeRelImpl.class);

		finderCache.clearCache(CommerceDiscountOrderTypeRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce discount order type rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

		entityCache.removeResult(
			CommerceDiscountOrderTypeRelImpl.class,
			commerceDiscountOrderTypeRel);
	}

	@Override
	public void clearCache(
		List<CommerceDiscountOrderTypeRel> commerceDiscountOrderTypeRels) {

		for (CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel :
				commerceDiscountOrderTypeRels) {

			entityCache.removeResult(
				CommerceDiscountOrderTypeRelImpl.class,
				commerceDiscountOrderTypeRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceDiscountOrderTypeRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceDiscountOrderTypeRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceDiscountOrderTypeRelModelImpl
			commerceDiscountOrderTypeRelModelImpl) {

		Object[] args = new Object[] {
			commerceDiscountOrderTypeRelModelImpl.getCommerceDiscountId(),
			commerceDiscountOrderTypeRelModelImpl.getCommerceOrderTypeId()
		};

		finderCache.putResult(
			_finderPathCountByCDI_COTI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCDI_COTI, args,
			commerceDiscountOrderTypeRelModelImpl);
	}

	/**
	 * Creates a new commerce discount order type rel with the primary key. Does not add the commerce discount order type rel to the database.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key for the new commerce discount order type rel
	 * @return the new commerce discount order type rel
	 */
	@Override
	public CommerceDiscountOrderTypeRel create(
		long commerceDiscountOrderTypeRelId) {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			new CommerceDiscountOrderTypeRelImpl();

		commerceDiscountOrderTypeRel.setNew(true);
		commerceDiscountOrderTypeRel.setPrimaryKey(
			commerceDiscountOrderTypeRelId);

		String uuid = PortalUUIDUtil.generate();

		commerceDiscountOrderTypeRel.setUuid(uuid);

		commerceDiscountOrderTypeRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceDiscountOrderTypeRel;
	}

	/**
	 * Removes the commerce discount order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the commerce discount order type rel
	 * @return the commerce discount order type rel that was removed
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel remove(
			long commerceDiscountOrderTypeRelId)
		throws NoSuchDiscountOrderTypeRelException {

		return remove((Serializable)commerceDiscountOrderTypeRelId);
	}

	/**
	 * Removes the commerce discount order type rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce discount order type rel
	 * @return the commerce discount order type rel that was removed
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel remove(Serializable primaryKey)
		throws NoSuchDiscountOrderTypeRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
				(CommerceDiscountOrderTypeRel)session.get(
					CommerceDiscountOrderTypeRelImpl.class, primaryKey);

			if (commerceDiscountOrderTypeRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDiscountOrderTypeRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceDiscountOrderTypeRel);
		}
		catch (NoSuchDiscountOrderTypeRelException noSuchEntityException) {
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
	protected CommerceDiscountOrderTypeRel removeImpl(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceDiscountOrderTypeRel)) {
				commerceDiscountOrderTypeRel =
					(CommerceDiscountOrderTypeRel)session.get(
						CommerceDiscountOrderTypeRelImpl.class,
						commerceDiscountOrderTypeRel.getPrimaryKeyObj());
			}

			if (commerceDiscountOrderTypeRel != null) {
				session.delete(commerceDiscountOrderTypeRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceDiscountOrderTypeRel != null) {
			clearCache(commerceDiscountOrderTypeRel);
		}

		return commerceDiscountOrderTypeRel;
	}

	@Override
	public CommerceDiscountOrderTypeRel updateImpl(
		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel) {

		boolean isNew = commerceDiscountOrderTypeRel.isNew();

		if (!(commerceDiscountOrderTypeRel instanceof
				CommerceDiscountOrderTypeRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceDiscountOrderTypeRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceDiscountOrderTypeRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceDiscountOrderTypeRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceDiscountOrderTypeRel implementation " +
					commerceDiscountOrderTypeRel.getClass());
		}

		CommerceDiscountOrderTypeRelModelImpl
			commerceDiscountOrderTypeRelModelImpl =
				(CommerceDiscountOrderTypeRelModelImpl)
					commerceDiscountOrderTypeRel;

		if (Validator.isNull(commerceDiscountOrderTypeRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceDiscountOrderTypeRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceDiscountOrderTypeRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceDiscountOrderTypeRel.setCreateDate(date);
			}
			else {
				commerceDiscountOrderTypeRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceDiscountOrderTypeRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceDiscountOrderTypeRel.setModifiedDate(date);
			}
			else {
				commerceDiscountOrderTypeRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceDiscountOrderTypeRel);
			}
			else {
				commerceDiscountOrderTypeRel =
					(CommerceDiscountOrderTypeRel)session.merge(
						commerceDiscountOrderTypeRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceDiscountOrderTypeRelImpl.class,
			commerceDiscountOrderTypeRelModelImpl, false, true);

		cacheUniqueFindersCache(commerceDiscountOrderTypeRelModelImpl);

		if (isNew) {
			commerceDiscountOrderTypeRel.setNew(false);
		}

		commerceDiscountOrderTypeRel.resetOriginalValues();

		return commerceDiscountOrderTypeRel;
	}

	/**
	 * Returns the commerce discount order type rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount order type rel
	 * @return the commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchDiscountOrderTypeRelException {

		CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel =
			fetchByPrimaryKey(primaryKey);

		if (commerceDiscountOrderTypeRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDiscountOrderTypeRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceDiscountOrderTypeRel;
	}

	/**
	 * Returns the commerce discount order type rel with the primary key or throws a <code>NoSuchDiscountOrderTypeRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the commerce discount order type rel
	 * @return the commerce discount order type rel
	 * @throws NoSuchDiscountOrderTypeRelException if a commerce discount order type rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel findByPrimaryKey(
			long commerceDiscountOrderTypeRelId)
		throws NoSuchDiscountOrderTypeRelException {

		return findByPrimaryKey((Serializable)commerceDiscountOrderTypeRelId);
	}

	/**
	 * Returns the commerce discount order type rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountOrderTypeRelId the primary key of the commerce discount order type rel
	 * @return the commerce discount order type rel, or <code>null</code> if a commerce discount order type rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountOrderTypeRel fetchByPrimaryKey(
		long commerceDiscountOrderTypeRelId) {

		return fetchByPrimaryKey((Serializable)commerceDiscountOrderTypeRelId);
	}

	/**
	 * Returns all the commerce discount order type rels.
	 *
	 * @return the commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @return the range of commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount order type rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountOrderTypeRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount order type rels
	 * @param end the upper bound of the range of commerce discount order type rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce discount order type rels
	 */
	@Override
	public List<CommerceDiscountOrderTypeRel> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountOrderTypeRel> orderByComparator,
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

		List<CommerceDiscountOrderTypeRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountOrderTypeRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL;

				sql = sql.concat(
					CommerceDiscountOrderTypeRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceDiscountOrderTypeRel>)QueryUtil.list(
					query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
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
	 * Removes all the commerce discount order type rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel :
				findAll()) {

			remove(commerceDiscountOrderTypeRel);
		}
	}

	/**
	 * Returns the number of commerce discount order type rels.
	 *
	 * @return the number of commerce discount order type rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEDISCOUNTORDERTYPEREL);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
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
		return "commerceDiscountOrderTypeRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceDiscountOrderTypeRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce discount order type rel persistence.
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

		_finderPathWithPaginationFindByCommerceDiscountId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceDiscountId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceDiscountId"}, true);

		_finderPathWithoutPaginationFindByCommerceDiscountId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceDiscountId", new String[] {Long.class.getName()},
			new String[] {"commerceDiscountId"}, true);

		_finderPathCountByCommerceDiscountId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceDiscountId", new String[] {Long.class.getName()},
			new String[] {"commerceDiscountId"}, false);

		_finderPathWithPaginationFindByCommerceOrderTypeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceOrderTypeId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceOrderTypeId"}, true);

		_finderPathWithoutPaginationFindByCommerceOrderTypeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceOrderTypeId", new String[] {Long.class.getName()},
			new String[] {"commerceOrderTypeId"}, true);

		_finderPathCountByCommerceOrderTypeId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceOrderTypeId", new String[] {Long.class.getName()},
			new String[] {"commerceOrderTypeId"}, false);

		_finderPathFetchByCDI_COTI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCDI_COTI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceDiscountId", "commerceOrderTypeId"}, true);

		_finderPathCountByCDI_COTI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCDI_COTI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceDiscountId", "commerceOrderTypeId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceDiscountOrderTypeRelImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL =
		"SELECT commerceDiscountOrderTypeRel FROM CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel";

	private static final String _SQL_SELECT_COMMERCEDISCOUNTORDERTYPEREL_WHERE =
		"SELECT commerceDiscountOrderTypeRel FROM CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEDISCOUNTORDERTYPEREL =
		"SELECT COUNT(commerceDiscountOrderTypeRel) FROM CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel";

	private static final String _SQL_COUNT_COMMERCEDISCOUNTORDERTYPEREL_WHERE =
		"SELECT COUNT(commerceDiscountOrderTypeRel) FROM CommerceDiscountOrderTypeRel commerceDiscountOrderTypeRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceDiscountOrderTypeRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceDiscountOrderTypeRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceDiscountOrderTypeRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountOrderTypeRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}