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

import com.liferay.commerce.discount.exception.NoSuchDiscountAccountRelException;
import com.liferay.commerce.discount.model.CommerceDiscountAccountRel;
import com.liferay.commerce.discount.model.CommerceDiscountAccountRelTable;
import com.liferay.commerce.discount.model.impl.CommerceDiscountAccountRelImpl;
import com.liferay.commerce.discount.model.impl.CommerceDiscountAccountRelModelImpl;
import com.liferay.commerce.discount.service.persistence.CommerceDiscountAccountRelPersistence;
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
 * The persistence implementation for the commerce discount account rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceDiscountAccountRelPersistenceImpl
	extends BasePersistenceImpl<CommerceDiscountAccountRel>
	implements CommerceDiscountAccountRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceDiscountAccountRelUtil</code> to access the commerce discount account rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceDiscountAccountRelImpl.class.getName();

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
	 * Returns all the commerce discount account rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByUuid(String uuid) {
		return findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByUuid(
		String uuid, int start, int end) {

		return findByUuid(uuid, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return findByUuid(uuid, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where uuid = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByUuid(
		String uuid, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
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

		List<CommerceDiscountAccountRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountAccountRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountAccountRel commerceDiscountAccountRel :
						list) {

					if (!uuid.equals(commerceDiscountAccountRel.getUuid())) {
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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

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
				sb.append(CommerceDiscountAccountRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceDiscountAccountRel>)QueryUtil.list(
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
	 * Returns the first commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel findByUuid_First(
			String uuid,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			fetchByUuid_First(uuid, orderByComparator);

		if (commerceDiscountAccountRel != null) {
			return commerceDiscountAccountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchDiscountAccountRelException(sb.toString());
	}

	/**
	 * Returns the first commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel fetchByUuid_First(
		String uuid,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		List<CommerceDiscountAccountRel> list = findByUuid(
			uuid, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel findByUuid_Last(
			String uuid,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			fetchByUuid_Last(uuid, orderByComparator);

		if (commerceDiscountAccountRel != null) {
			return commerceDiscountAccountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append("}");

		throw new NoSuchDiscountAccountRelException(sb.toString());
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel fetchByUuid_Last(
		String uuid,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		int count = countByUuid(uuid);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountAccountRel> list = findByUuid(
			uuid, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount account rels before and after the current commerce discount account rel in the ordered set where uuid = &#63;.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the current commerce discount account rel
	 * @param uuid the uuid
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountAccountRel[] findByUuid_PrevAndNext(
			long commerceDiscountAccountRelId, String uuid,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException {

		uuid = Objects.toString(uuid, "");

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			findByPrimaryKey(commerceDiscountAccountRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountAccountRel[] array =
				new CommerceDiscountAccountRelImpl[3];

			array[0] = getByUuid_PrevAndNext(
				session, commerceDiscountAccountRel, uuid, orderByComparator,
				true);

			array[1] = commerceDiscountAccountRel;

			array[2] = getByUuid_PrevAndNext(
				session, commerceDiscountAccountRel, uuid, orderByComparator,
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

	protected CommerceDiscountAccountRel getByUuid_PrevAndNext(
		Session session, CommerceDiscountAccountRel commerceDiscountAccountRel,
		String uuid,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

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
			sb.append(CommerceDiscountAccountRelModelImpl.ORDER_BY_JPQL);
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
						commerceDiscountAccountRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountAccountRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount account rels where uuid = &#63; from the database.
	 *
	 * @param uuid the uuid
	 */
	@Override
	public void removeByUuid(String uuid) {
		for (CommerceDiscountAccountRel commerceDiscountAccountRel :
				findByUuid(uuid, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceDiscountAccountRel);
		}
	}

	/**
	 * Returns the number of commerce discount account rels where uuid = &#63;.
	 *
	 * @param uuid the uuid
	 * @return the number of matching commerce discount account rels
	 */
	@Override
	public int countByUuid(String uuid) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid;

		Object[] finderArgs = new Object[] {uuid};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

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
		"commerceDiscountAccountRel.uuid = ?";

	private static final String _FINDER_COLUMN_UUID_UUID_3 =
		"(commerceDiscountAccountRel.uuid IS NULL OR commerceDiscountAccountRel.uuid = '')";

	private FinderPath _finderPathWithPaginationFindByUuid_C;
	private FinderPath _finderPathWithoutPaginationFindByUuid_C;
	private FinderPath _finderPathCountByUuid_C;

	/**
	 * Returns all the commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByUuid_C(
		String uuid, long companyId) {

		return findByUuid_C(
			uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end) {

		return findByUuid_C(uuid, companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return findByUuid_C(
			uuid, companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByUuid_C(
		String uuid, long companyId, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
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

		List<CommerceDiscountAccountRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountAccountRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountAccountRel commerceDiscountAccountRel :
						list) {

					if (!uuid.equals(commerceDiscountAccountRel.getUuid()) ||
						(companyId !=
							commerceDiscountAccountRel.getCompanyId())) {

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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

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
				sb.append(CommerceDiscountAccountRelModelImpl.ORDER_BY_JPQL);
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

				list = (List<CommerceDiscountAccountRel>)QueryUtil.list(
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
	 * Returns the first commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel findByUuid_C_First(
			String uuid, long companyId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			fetchByUuid_C_First(uuid, companyId, orderByComparator);

		if (commerceDiscountAccountRel != null) {
			return commerceDiscountAccountRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDiscountAccountRelException(sb.toString());
	}

	/**
	 * Returns the first commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel fetchByUuid_C_First(
		String uuid, long companyId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		List<CommerceDiscountAccountRel> list = findByUuid_C(
			uuid, companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel findByUuid_C_Last(
			String uuid, long companyId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			fetchByUuid_C_Last(uuid, companyId, orderByComparator);

		if (commerceDiscountAccountRel != null) {
			return commerceDiscountAccountRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("uuid=");
		sb.append(uuid);

		sb.append(", companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchDiscountAccountRelException(sb.toString());
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel fetchByUuid_C_Last(
		String uuid, long companyId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		int count = countByUuid_C(uuid, companyId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountAccountRel> list = findByUuid_C(
			uuid, companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount account rels before and after the current commerce discount account rel in the ordered set where uuid = &#63; and companyId = &#63;.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the current commerce discount account rel
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountAccountRel[] findByUuid_C_PrevAndNext(
			long commerceDiscountAccountRelId, String uuid, long companyId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException {

		uuid = Objects.toString(uuid, "");

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			findByPrimaryKey(commerceDiscountAccountRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountAccountRel[] array =
				new CommerceDiscountAccountRelImpl[3];

			array[0] = getByUuid_C_PrevAndNext(
				session, commerceDiscountAccountRel, uuid, companyId,
				orderByComparator, true);

			array[1] = commerceDiscountAccountRel;

			array[2] = getByUuid_C_PrevAndNext(
				session, commerceDiscountAccountRel, uuid, companyId,
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

	protected CommerceDiscountAccountRel getByUuid_C_PrevAndNext(
		Session session, CommerceDiscountAccountRel commerceDiscountAccountRel,
		String uuid, long companyId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

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
			sb.append(CommerceDiscountAccountRelModelImpl.ORDER_BY_JPQL);
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
						commerceDiscountAccountRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountAccountRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount account rels where uuid = &#63; and companyId = &#63; from the database.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 */
	@Override
	public void removeByUuid_C(String uuid, long companyId) {
		for (CommerceDiscountAccountRel commerceDiscountAccountRel :
				findByUuid_C(
					uuid, companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceDiscountAccountRel);
		}
	}

	/**
	 * Returns the number of commerce discount account rels where uuid = &#63; and companyId = &#63;.
	 *
	 * @param uuid the uuid
	 * @param companyId the company ID
	 * @return the number of matching commerce discount account rels
	 */
	@Override
	public int countByUuid_C(String uuid, long companyId) {
		uuid = Objects.toString(uuid, "");

		FinderPath finderPath = _finderPathCountByUuid_C;

		Object[] finderArgs = new Object[] {uuid, companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

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
		"commerceDiscountAccountRel.uuid = ? AND ";

	private static final String _FINDER_COLUMN_UUID_C_UUID_3 =
		"(commerceDiscountAccountRel.uuid IS NULL OR commerceDiscountAccountRel.uuid = '') AND ";

	private static final String _FINDER_COLUMN_UUID_C_COMPANYID_2 =
		"commerceDiscountAccountRel.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByCommerceAccountId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceAccountId;
	private FinderPath _finderPathCountByCommerceAccountId;

	/**
	 * Returns all the commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByCommerceAccountId(
		long commerceAccountId) {

		return findByCommerceAccountId(
			commerceAccountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByCommerceAccountId(
		long commerceAccountId, int start, int end) {

		return findByCommerceAccountId(commerceAccountId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByCommerceAccountId(
		long commerceAccountId, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return findByCommerceAccountId(
			commerceAccountId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByCommerceAccountId(
		long commerceAccountId, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceAccountId;
				finderArgs = new Object[] {commerceAccountId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceAccountId;
			finderArgs = new Object[] {
				commerceAccountId, start, end, orderByComparator
			};
		}

		List<CommerceDiscountAccountRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountAccountRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountAccountRel commerceDiscountAccountRel :
						list) {

					if (commerceAccountId !=
							commerceDiscountAccountRel.getCommerceAccountId()) {

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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEACCOUNTID_COMMERCEACCOUNTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceDiscountAccountRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountId);

				list = (List<CommerceDiscountAccountRel>)QueryUtil.list(
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
	 * Returns the first commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel findByCommerceAccountId_First(
			long commerceAccountId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			fetchByCommerceAccountId_First(
				commerceAccountId, orderByComparator);

		if (commerceDiscountAccountRel != null) {
			return commerceDiscountAccountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append("}");

		throw new NoSuchDiscountAccountRelException(sb.toString());
	}

	/**
	 * Returns the first commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel fetchByCommerceAccountId_First(
		long commerceAccountId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		List<CommerceDiscountAccountRel> list = findByCommerceAccountId(
			commerceAccountId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel findByCommerceAccountId_Last(
			long commerceAccountId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			fetchByCommerceAccountId_Last(commerceAccountId, orderByComparator);

		if (commerceDiscountAccountRel != null) {
			return commerceDiscountAccountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountId=");
		sb.append(commerceAccountId);

		sb.append("}");

		throw new NoSuchDiscountAccountRelException(sb.toString());
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel fetchByCommerceAccountId_Last(
		long commerceAccountId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		int count = countByCommerceAccountId(commerceAccountId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountAccountRel> list = findByCommerceAccountId(
			commerceAccountId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount account rels before and after the current commerce discount account rel in the ordered set where commerceAccountId = &#63;.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the current commerce discount account rel
	 * @param commerceAccountId the commerce account ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountAccountRel[] findByCommerceAccountId_PrevAndNext(
			long commerceDiscountAccountRelId, long commerceAccountId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			findByPrimaryKey(commerceDiscountAccountRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountAccountRel[] array =
				new CommerceDiscountAccountRelImpl[3];

			array[0] = getByCommerceAccountId_PrevAndNext(
				session, commerceDiscountAccountRel, commerceAccountId,
				orderByComparator, true);

			array[1] = commerceDiscountAccountRel;

			array[2] = getByCommerceAccountId_PrevAndNext(
				session, commerceDiscountAccountRel, commerceAccountId,
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

	protected CommerceDiscountAccountRel getByCommerceAccountId_PrevAndNext(
		Session session, CommerceDiscountAccountRel commerceDiscountAccountRel,
		long commerceAccountId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCEACCOUNTID_COMMERCEACCOUNTID_2);

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
			sb.append(CommerceDiscountAccountRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceAccountId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceDiscountAccountRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountAccountRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount account rels where commerceAccountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 */
	@Override
	public void removeByCommerceAccountId(long commerceAccountId) {
		for (CommerceDiscountAccountRel commerceDiscountAccountRel :
				findByCommerceAccountId(
					commerceAccountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceDiscountAccountRel);
		}
	}

	/**
	 * Returns the number of commerce discount account rels where commerceAccountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @return the number of matching commerce discount account rels
	 */
	@Override
	public int countByCommerceAccountId(long commerceAccountId) {
		FinderPath finderPath = _finderPathCountByCommerceAccountId;

		Object[] finderArgs = new Object[] {commerceAccountId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEACCOUNTID_COMMERCEACCOUNTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountId);

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
		_FINDER_COLUMN_COMMERCEACCOUNTID_COMMERCEACCOUNTID_2 =
			"commerceDiscountAccountRel.commerceAccountId = ?";

	private FinderPath _finderPathWithPaginationFindByCommerceDiscountId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceDiscountId;
	private FinderPath _finderPathCountByCommerceDiscountId;

	/**
	 * Returns all the commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByCommerceDiscountId(
		long commerceDiscountId) {

		return findByCommerceDiscountId(
			commerceDiscountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end) {

		return findByCommerceDiscountId(commerceDiscountId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return findByCommerceDiscountId(
			commerceDiscountId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findByCommerceDiscountId(
		long commerceDiscountId, int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
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

		List<CommerceDiscountAccountRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountAccountRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceDiscountAccountRel commerceDiscountAccountRel :
						list) {

					if (commerceDiscountId !=
							commerceDiscountAccountRel.
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

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCEDISCOUNTID_COMMERCEDISCOUNTID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceDiscountAccountRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceDiscountId);

				list = (List<CommerceDiscountAccountRel>)QueryUtil.list(
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
	 * Returns the first commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel findByCommerceDiscountId_First(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			fetchByCommerceDiscountId_First(
				commerceDiscountId, orderByComparator);

		if (commerceDiscountAccountRel != null) {
			return commerceDiscountAccountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountAccountRelException(sb.toString());
	}

	/**
	 * Returns the first commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel fetchByCommerceDiscountId_First(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		List<CommerceDiscountAccountRel> list = findByCommerceDiscountId(
			commerceDiscountId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel findByCommerceDiscountId_Last(
			long commerceDiscountId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			fetchByCommerceDiscountId_Last(
				commerceDiscountId, orderByComparator);

		if (commerceDiscountAccountRel != null) {
			return commerceDiscountAccountRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceDiscountId=");
		sb.append(commerceDiscountId);

		sb.append("}");

		throw new NoSuchDiscountAccountRelException(sb.toString());
	}

	/**
	 * Returns the last commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel fetchByCommerceDiscountId_Last(
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		int count = countByCommerceDiscountId(commerceDiscountId);

		if (count == 0) {
			return null;
		}

		List<CommerceDiscountAccountRel> list = findByCommerceDiscountId(
			commerceDiscountId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce discount account rels before and after the current commerce discount account rel in the ordered set where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the current commerce discount account rel
	 * @param commerceDiscountId the commerce discount ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountAccountRel[] findByCommerceDiscountId_PrevAndNext(
			long commerceDiscountAccountRelId, long commerceDiscountId,
			OrderByComparator<CommerceDiscountAccountRel> orderByComparator)
		throws NoSuchDiscountAccountRelException {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			findByPrimaryKey(commerceDiscountAccountRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountAccountRel[] array =
				new CommerceDiscountAccountRelImpl[3];

			array[0] = getByCommerceDiscountId_PrevAndNext(
				session, commerceDiscountAccountRel, commerceDiscountId,
				orderByComparator, true);

			array[1] = commerceDiscountAccountRel;

			array[2] = getByCommerceDiscountId_PrevAndNext(
				session, commerceDiscountAccountRel, commerceDiscountId,
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

	protected CommerceDiscountAccountRel getByCommerceDiscountId_PrevAndNext(
		Session session, CommerceDiscountAccountRel commerceDiscountAccountRel,
		long commerceDiscountId,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

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
			sb.append(CommerceDiscountAccountRelModelImpl.ORDER_BY_JPQL);
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
						commerceDiscountAccountRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceDiscountAccountRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce discount account rels where commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 */
	@Override
	public void removeByCommerceDiscountId(long commerceDiscountId) {
		for (CommerceDiscountAccountRel commerceDiscountAccountRel :
				findByCommerceDiscountId(
					commerceDiscountId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceDiscountAccountRel);
		}
	}

	/**
	 * Returns the number of commerce discount account rels where commerceDiscountId = &#63;.
	 *
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount account rels
	 */
	@Override
	public int countByCommerceDiscountId(long commerceDiscountId) {
		FinderPath finderPath = _finderPathCountByCommerceDiscountId;

		Object[] finderArgs = new Object[] {commerceDiscountId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

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
			"commerceDiscountAccountRel.commerceDiscountId = ?";

	private FinderPath _finderPathFetchByCAI_CDI;
	private FinderPath _finderPathCountByCAI_CDI;

	/**
	 * Returns the commerce discount account rel where commerceAccountId = &#63; and commerceDiscountId = &#63; or throws a <code>NoSuchDiscountAccountRelException</code> if it could not be found.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel findByCAI_CDI(
			long commerceAccountId, long commerceDiscountId)
		throws NoSuchDiscountAccountRelException {

		CommerceDiscountAccountRel commerceDiscountAccountRel = fetchByCAI_CDI(
			commerceAccountId, commerceDiscountId);

		if (commerceDiscountAccountRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceAccountId=");
			sb.append(commerceAccountId);

			sb.append(", commerceDiscountId=");
			sb.append(commerceDiscountId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchDiscountAccountRelException(sb.toString());
		}

		return commerceDiscountAccountRel;
	}

	/**
	 * Returns the commerce discount account rel where commerceAccountId = &#63; and commerceDiscountId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel fetchByCAI_CDI(
		long commerceAccountId, long commerceDiscountId) {

		return fetchByCAI_CDI(commerceAccountId, commerceDiscountId, true);
	}

	/**
	 * Returns the commerce discount account rel where commerceAccountId = &#63; and commerceDiscountId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce discount account rel, or <code>null</code> if a matching commerce discount account rel could not be found
	 */
	@Override
	public CommerceDiscountAccountRel fetchByCAI_CDI(
		long commerceAccountId, long commerceDiscountId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {commerceAccountId, commerceDiscountId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByCAI_CDI, finderArgs);
		}

		if (result instanceof CommerceDiscountAccountRel) {
			CommerceDiscountAccountRel commerceDiscountAccountRel =
				(CommerceDiscountAccountRel)result;

			if ((commerceAccountId !=
					commerceDiscountAccountRel.getCommerceAccountId()) ||
				(commerceDiscountId !=
					commerceDiscountAccountRel.getCommerceDiscountId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

			sb.append(_FINDER_COLUMN_CAI_CDI_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_CAI_CDI_COMMERCEDISCOUNTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountId);

				queryPos.add(commerceDiscountId);

				List<CommerceDiscountAccountRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByCAI_CDI, finderArgs, list);
					}
				}
				else {
					CommerceDiscountAccountRel commerceDiscountAccountRel =
						list.get(0);

					result = commerceDiscountAccountRel;

					cacheResult(commerceDiscountAccountRel);
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
			return (CommerceDiscountAccountRel)result;
		}
	}

	/**
	 * Removes the commerce discount account rel where commerceAccountId = &#63; and commerceDiscountId = &#63; from the database.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the commerce discount account rel that was removed
	 */
	@Override
	public CommerceDiscountAccountRel removeByCAI_CDI(
			long commerceAccountId, long commerceDiscountId)
		throws NoSuchDiscountAccountRelException {

		CommerceDiscountAccountRel commerceDiscountAccountRel = findByCAI_CDI(
			commerceAccountId, commerceDiscountId);

		return remove(commerceDiscountAccountRel);
	}

	/**
	 * Returns the number of commerce discount account rels where commerceAccountId = &#63; and commerceDiscountId = &#63;.
	 *
	 * @param commerceAccountId the commerce account ID
	 * @param commerceDiscountId the commerce discount ID
	 * @return the number of matching commerce discount account rels
	 */
	@Override
	public int countByCAI_CDI(long commerceAccountId, long commerceDiscountId) {
		FinderPath finderPath = _finderPathCountByCAI_CDI;

		Object[] finderArgs = new Object[] {
			commerceAccountId, commerceDiscountId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEDISCOUNTACCOUNTREL_WHERE);

			sb.append(_FINDER_COLUMN_CAI_CDI_COMMERCEACCOUNTID_2);

			sb.append(_FINDER_COLUMN_CAI_CDI_COMMERCEDISCOUNTID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountId);

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

	private static final String _FINDER_COLUMN_CAI_CDI_COMMERCEACCOUNTID_2 =
		"commerceDiscountAccountRel.commerceAccountId = ? AND ";

	private static final String _FINDER_COLUMN_CAI_CDI_COMMERCEDISCOUNTID_2 =
		"commerceDiscountAccountRel.commerceDiscountId = ?";

	public CommerceDiscountAccountRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("uuid", "uuid_");
		dbColumnNames.put("order", "order_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceDiscountAccountRel.class);

		setModelImplClass(CommerceDiscountAccountRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceDiscountAccountRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce discount account rel in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountAccountRel the commerce discount account rel
	 */
	@Override
	public void cacheResult(
		CommerceDiscountAccountRel commerceDiscountAccountRel) {

		entityCache.putResult(
			CommerceDiscountAccountRelImpl.class,
			commerceDiscountAccountRel.getPrimaryKey(),
			commerceDiscountAccountRel);

		finderCache.putResult(
			_finderPathFetchByCAI_CDI,
			new Object[] {
				commerceDiscountAccountRel.getCommerceAccountId(),
				commerceDiscountAccountRel.getCommerceDiscountId()
			},
			commerceDiscountAccountRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce discount account rels in the entity cache if it is enabled.
	 *
	 * @param commerceDiscountAccountRels the commerce discount account rels
	 */
	@Override
	public void cacheResult(
		List<CommerceDiscountAccountRel> commerceDiscountAccountRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceDiscountAccountRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceDiscountAccountRel commerceDiscountAccountRel :
				commerceDiscountAccountRels) {

			if (entityCache.getResult(
					CommerceDiscountAccountRelImpl.class,
					commerceDiscountAccountRel.getPrimaryKey()) == null) {

				cacheResult(commerceDiscountAccountRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce discount account rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceDiscountAccountRelImpl.class);

		finderCache.clearCache(CommerceDiscountAccountRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce discount account rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceDiscountAccountRel commerceDiscountAccountRel) {

		entityCache.removeResult(
			CommerceDiscountAccountRelImpl.class, commerceDiscountAccountRel);
	}

	@Override
	public void clearCache(
		List<CommerceDiscountAccountRel> commerceDiscountAccountRels) {

		for (CommerceDiscountAccountRel commerceDiscountAccountRel :
				commerceDiscountAccountRels) {

			entityCache.removeResult(
				CommerceDiscountAccountRelImpl.class,
				commerceDiscountAccountRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceDiscountAccountRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceDiscountAccountRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceDiscountAccountRelModelImpl
			commerceDiscountAccountRelModelImpl) {

		Object[] args = new Object[] {
			commerceDiscountAccountRelModelImpl.getCommerceAccountId(),
			commerceDiscountAccountRelModelImpl.getCommerceDiscountId()
		};

		finderCache.putResult(_finderPathCountByCAI_CDI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByCAI_CDI, args,
			commerceDiscountAccountRelModelImpl);
	}

	/**
	 * Creates a new commerce discount account rel with the primary key. Does not add the commerce discount account rel to the database.
	 *
	 * @param commerceDiscountAccountRelId the primary key for the new commerce discount account rel
	 * @return the new commerce discount account rel
	 */
	@Override
	public CommerceDiscountAccountRel create(
		long commerceDiscountAccountRelId) {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			new CommerceDiscountAccountRelImpl();

		commerceDiscountAccountRel.setNew(true);
		commerceDiscountAccountRel.setPrimaryKey(commerceDiscountAccountRelId);

		String uuid = PortalUUIDUtil.generate();

		commerceDiscountAccountRel.setUuid(uuid);

		commerceDiscountAccountRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceDiscountAccountRel;
	}

	/**
	 * Removes the commerce discount account rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the commerce discount account rel
	 * @return the commerce discount account rel that was removed
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountAccountRel remove(long commerceDiscountAccountRelId)
		throws NoSuchDiscountAccountRelException {

		return remove((Serializable)commerceDiscountAccountRelId);
	}

	/**
	 * Removes the commerce discount account rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce discount account rel
	 * @return the commerce discount account rel that was removed
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountAccountRel remove(Serializable primaryKey)
		throws NoSuchDiscountAccountRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceDiscountAccountRel commerceDiscountAccountRel =
				(CommerceDiscountAccountRel)session.get(
					CommerceDiscountAccountRelImpl.class, primaryKey);

			if (commerceDiscountAccountRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchDiscountAccountRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceDiscountAccountRel);
		}
		catch (NoSuchDiscountAccountRelException noSuchEntityException) {
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
	protected CommerceDiscountAccountRel removeImpl(
		CommerceDiscountAccountRel commerceDiscountAccountRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceDiscountAccountRel)) {
				commerceDiscountAccountRel =
					(CommerceDiscountAccountRel)session.get(
						CommerceDiscountAccountRelImpl.class,
						commerceDiscountAccountRel.getPrimaryKeyObj());
			}

			if (commerceDiscountAccountRel != null) {
				session.delete(commerceDiscountAccountRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceDiscountAccountRel != null) {
			clearCache(commerceDiscountAccountRel);
		}

		return commerceDiscountAccountRel;
	}

	@Override
	public CommerceDiscountAccountRel updateImpl(
		CommerceDiscountAccountRel commerceDiscountAccountRel) {

		boolean isNew = commerceDiscountAccountRel.isNew();

		if (!(commerceDiscountAccountRel instanceof
				CommerceDiscountAccountRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceDiscountAccountRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceDiscountAccountRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceDiscountAccountRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceDiscountAccountRel implementation " +
					commerceDiscountAccountRel.getClass());
		}

		CommerceDiscountAccountRelModelImpl
			commerceDiscountAccountRelModelImpl =
				(CommerceDiscountAccountRelModelImpl)commerceDiscountAccountRel;

		if (Validator.isNull(commerceDiscountAccountRel.getUuid())) {
			String uuid = PortalUUIDUtil.generate();

			commerceDiscountAccountRel.setUuid(uuid);
		}

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceDiscountAccountRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceDiscountAccountRel.setCreateDate(date);
			}
			else {
				commerceDiscountAccountRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceDiscountAccountRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceDiscountAccountRel.setModifiedDate(date);
			}
			else {
				commerceDiscountAccountRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceDiscountAccountRel);
			}
			else {
				commerceDiscountAccountRel =
					(CommerceDiscountAccountRel)session.merge(
						commerceDiscountAccountRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceDiscountAccountRelImpl.class,
			commerceDiscountAccountRelModelImpl, false, true);

		cacheUniqueFindersCache(commerceDiscountAccountRelModelImpl);

		if (isNew) {
			commerceDiscountAccountRel.setNew(false);
		}

		commerceDiscountAccountRel.resetOriginalValues();

		return commerceDiscountAccountRel;
	}

	/**
	 * Returns the commerce discount account rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce discount account rel
	 * @return the commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountAccountRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchDiscountAccountRelException {

		CommerceDiscountAccountRel commerceDiscountAccountRel =
			fetchByPrimaryKey(primaryKey);

		if (commerceDiscountAccountRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchDiscountAccountRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceDiscountAccountRel;
	}

	/**
	 * Returns the commerce discount account rel with the primary key or throws a <code>NoSuchDiscountAccountRelException</code> if it could not be found.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the commerce discount account rel
	 * @return the commerce discount account rel
	 * @throws NoSuchDiscountAccountRelException if a commerce discount account rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountAccountRel findByPrimaryKey(
			long commerceDiscountAccountRelId)
		throws NoSuchDiscountAccountRelException {

		return findByPrimaryKey((Serializable)commerceDiscountAccountRelId);
	}

	/**
	 * Returns the commerce discount account rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceDiscountAccountRelId the primary key of the commerce discount account rel
	 * @return the commerce discount account rel, or <code>null</code> if a commerce discount account rel with the primary key could not be found
	 */
	@Override
	public CommerceDiscountAccountRel fetchByPrimaryKey(
		long commerceDiscountAccountRelId) {

		return fetchByPrimaryKey((Serializable)commerceDiscountAccountRelId);
	}

	/**
	 * Returns all the commerce discount account rels.
	 *
	 * @return the commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce discount account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @return the range of commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce discount account rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceDiscountAccountRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce discount account rels
	 * @param end the upper bound of the range of commerce discount account rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce discount account rels
	 */
	@Override
	public List<CommerceDiscountAccountRel> findAll(
		int start, int end,
		OrderByComparator<CommerceDiscountAccountRel> orderByComparator,
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

		List<CommerceDiscountAccountRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceDiscountAccountRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL;

				sql = sql.concat(
					CommerceDiscountAccountRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceDiscountAccountRel>)QueryUtil.list(
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
	 * Removes all the commerce discount account rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceDiscountAccountRel commerceDiscountAccountRel :
				findAll()) {

			remove(commerceDiscountAccountRel);
		}
	}

	/**
	 * Returns the number of commerce discount account rels.
	 *
	 * @return the number of commerce discount account rels
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
					_SQL_COUNT_COMMERCEDISCOUNTACCOUNTREL);

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
		return "commerceDiscountAccountRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceDiscountAccountRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce discount account rel persistence.
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

		_finderPathWithPaginationFindByCommerceAccountId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceAccountId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceAccountId"}, true);

		_finderPathWithoutPaginationFindByCommerceAccountId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceAccountId", new String[] {Long.class.getName()},
			new String[] {"commerceAccountId"}, true);

		_finderPathCountByCommerceAccountId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceAccountId", new String[] {Long.class.getName()},
			new String[] {"commerceAccountId"}, false);

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

		_finderPathFetchByCAI_CDI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByCAI_CDI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceAccountId", "commerceDiscountId"}, true);

		_finderPathCountByCAI_CDI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCAI_CDI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"commerceAccountId", "commerceDiscountId"}, false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceDiscountAccountRelImpl.class.getName());
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL =
		"SELECT commerceDiscountAccountRel FROM CommerceDiscountAccountRel commerceDiscountAccountRel";

	private static final String _SQL_SELECT_COMMERCEDISCOUNTACCOUNTREL_WHERE =
		"SELECT commerceDiscountAccountRel FROM CommerceDiscountAccountRel commerceDiscountAccountRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEDISCOUNTACCOUNTREL =
		"SELECT COUNT(commerceDiscountAccountRel) FROM CommerceDiscountAccountRel commerceDiscountAccountRel";

	private static final String _SQL_COUNT_COMMERCEDISCOUNTACCOUNTREL_WHERE =
		"SELECT COUNT(commerceDiscountAccountRel) FROM CommerceDiscountAccountRel commerceDiscountAccountRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceDiscountAccountRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceDiscountAccountRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceDiscountAccountRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceDiscountAccountRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"uuid", "order"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}