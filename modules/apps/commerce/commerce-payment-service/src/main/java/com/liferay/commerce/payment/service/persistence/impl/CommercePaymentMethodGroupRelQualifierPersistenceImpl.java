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

package com.liferay.commerce.payment.service.persistence.impl;

import com.liferay.commerce.payment.exception.NoSuchPaymentMethodGroupRelQualifierException;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifier;
import com.liferay.commerce.payment.model.CommercePaymentMethodGroupRelQualifierTable;
import com.liferay.commerce.payment.model.impl.CommercePaymentMethodGroupRelQualifierImpl;
import com.liferay.commerce.payment.model.impl.CommercePaymentMethodGroupRelQualifierModelImpl;
import com.liferay.commerce.payment.service.persistence.CommercePaymentMethodGroupRelQualifierPersistence;
import com.liferay.commerce.payment.service.persistence.CommercePaymentMethodGroupRelQualifierUtil;
import com.liferay.commerce.payment.service.persistence.impl.constants.CommercePersistenceConstants;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.configuration.Configuration;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.dao.orm.SessionFactory;
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

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the commerce payment method group rel qualifier service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
@Component(service = CommercePaymentMethodGroupRelQualifierPersistence.class)
public class CommercePaymentMethodGroupRelQualifierPersistenceImpl
	extends BasePersistenceImpl<CommercePaymentMethodGroupRelQualifier>
	implements CommercePaymentMethodGroupRelQualifierPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommercePaymentMethodGroupRelQualifierUtil</code> to access the commerce payment method group rel qualifier persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommercePaymentMethodGroupRelQualifierImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath
		_finderPathWithPaginationFindByCommercePaymentMethodGroupRelId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommercePaymentMethodGroupRelId;
	private FinderPath _finderPathCountByCommercePaymentMethodGroupRelId;

	/**
	 * Returns all the commerce payment method group rel qualifiers where CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the matching commerce payment method group rel qualifiers
	 */
	@Override
	public List<CommercePaymentMethodGroupRelQualifier>
		findByCommercePaymentMethodGroupRelId(
			long CommercePaymentMethodGroupRelId) {

		return findByCommercePaymentMethodGroupRelId(
			CommercePaymentMethodGroupRelId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce payment method group rel qualifiers where CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @return the range of matching commerce payment method group rel qualifiers
	 */
	@Override
	public List<CommercePaymentMethodGroupRelQualifier>
		findByCommercePaymentMethodGroupRelId(
			long CommercePaymentMethodGroupRelId, int start, int end) {

		return findByCommercePaymentMethodGroupRelId(
			CommercePaymentMethodGroupRelId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers where CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment method group rel qualifiers
	 */
	@Override
	public List<CommercePaymentMethodGroupRelQualifier>
		findByCommercePaymentMethodGroupRelId(
			long CommercePaymentMethodGroupRelId, int start, int end,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator) {

		return findByCommercePaymentMethodGroupRelId(
			CommercePaymentMethodGroupRelId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers where CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce payment method group rel qualifiers
	 */
	@Override
	public List<CommercePaymentMethodGroupRelQualifier>
		findByCommercePaymentMethodGroupRelId(
			long CommercePaymentMethodGroupRelId, int start, int end,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommercePaymentMethodGroupRelId;
				finderArgs = new Object[] {CommercePaymentMethodGroupRelId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommercePaymentMethodGroupRelId;
			finderArgs = new Object[] {
				CommercePaymentMethodGroupRelId, start, end, orderByComparator
			};
		}

		List<CommercePaymentMethodGroupRelQualifier> list = null;

		if (useFinderCache) {
			list =
				(List<CommercePaymentMethodGroupRelQualifier>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePaymentMethodGroupRelQualifier
						commercePaymentMethodGroupRelQualifier : list) {

					if (CommercePaymentMethodGroupRelId !=
							commercePaymentMethodGroupRelQualifier.
								getCommercePaymentMethodGroupRelId()) {

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

			sb.append(_SQL_SELECT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEPAYMENTMETHODGROUPRELID_COMMERCEPAYMENTMETHODGROUPRELID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommercePaymentMethodGroupRelQualifierModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CommercePaymentMethodGroupRelId);

				list =
					(List<CommercePaymentMethodGroupRelQualifier>)
						QueryUtil.list(query, getDialect(), start, end);

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
	 * Returns the first commerce payment method group rel qualifier in the ordered set where CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier
			findByCommercePaymentMethodGroupRelId_First(
				long CommercePaymentMethodGroupRelId,
				OrderByComparator<CommercePaymentMethodGroupRelQualifier>
					orderByComparator)
		throws NoSuchPaymentMethodGroupRelQualifierException {

		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier =
				fetchByCommercePaymentMethodGroupRelId_First(
					CommercePaymentMethodGroupRelId, orderByComparator);

		if (commercePaymentMethodGroupRelQualifier != null) {
			return commercePaymentMethodGroupRelQualifier;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CommercePaymentMethodGroupRelId=");
		sb.append(CommercePaymentMethodGroupRelId);

		sb.append("}");

		throw new NoSuchPaymentMethodGroupRelQualifierException(sb.toString());
	}

	/**
	 * Returns the first commerce payment method group rel qualifier in the ordered set where CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier
		fetchByCommercePaymentMethodGroupRelId_First(
			long CommercePaymentMethodGroupRelId,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator) {

		List<CommercePaymentMethodGroupRelQualifier> list =
			findByCommercePaymentMethodGroupRelId(
				CommercePaymentMethodGroupRelId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce payment method group rel qualifier in the ordered set where CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier
			findByCommercePaymentMethodGroupRelId_Last(
				long CommercePaymentMethodGroupRelId,
				OrderByComparator<CommercePaymentMethodGroupRelQualifier>
					orderByComparator)
		throws NoSuchPaymentMethodGroupRelQualifierException {

		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier =
				fetchByCommercePaymentMethodGroupRelId_Last(
					CommercePaymentMethodGroupRelId, orderByComparator);

		if (commercePaymentMethodGroupRelQualifier != null) {
			return commercePaymentMethodGroupRelQualifier;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("CommercePaymentMethodGroupRelId=");
		sb.append(CommercePaymentMethodGroupRelId);

		sb.append("}");

		throw new NoSuchPaymentMethodGroupRelQualifierException(sb.toString());
	}

	/**
	 * Returns the last commerce payment method group rel qualifier in the ordered set where CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier
		fetchByCommercePaymentMethodGroupRelId_Last(
			long CommercePaymentMethodGroupRelId,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator) {

		int count = countByCommercePaymentMethodGroupRelId(
			CommercePaymentMethodGroupRelId);

		if (count == 0) {
			return null;
		}

		List<CommercePaymentMethodGroupRelQualifier> list =
			findByCommercePaymentMethodGroupRelId(
				CommercePaymentMethodGroupRelId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce payment method group rel qualifiers before and after the current commerce payment method group rel qualifier in the ordered set where CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the current commerce payment method group rel qualifier
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier[]
			findByCommercePaymentMethodGroupRelId_PrevAndNext(
				long commercePaymentMethodGroupRelQualifierId,
				long CommercePaymentMethodGroupRelId,
				OrderByComparator<CommercePaymentMethodGroupRelQualifier>
					orderByComparator)
		throws NoSuchPaymentMethodGroupRelQualifierException {

		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier = findByPrimaryKey(
				commercePaymentMethodGroupRelQualifierId);

		Session session = null;

		try {
			session = openSession();

			CommercePaymentMethodGroupRelQualifier[] array =
				new CommercePaymentMethodGroupRelQualifierImpl[3];

			array[0] = getByCommercePaymentMethodGroupRelId_PrevAndNext(
				session, commercePaymentMethodGroupRelQualifier,
				CommercePaymentMethodGroupRelId, orderByComparator, true);

			array[1] = commercePaymentMethodGroupRelQualifier;

			array[2] = getByCommercePaymentMethodGroupRelId_PrevAndNext(
				session, commercePaymentMethodGroupRelQualifier,
				CommercePaymentMethodGroupRelId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePaymentMethodGroupRelQualifier
		getByCommercePaymentMethodGroupRelId_PrevAndNext(
			Session session,
			CommercePaymentMethodGroupRelQualifier
				commercePaymentMethodGroupRelQualifier,
			long CommercePaymentMethodGroupRelId,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
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

		sb.append(_SQL_SELECT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEPAYMENTMETHODGROUPRELID_COMMERCEPAYMENTMETHODGROUPRELID_2);

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
				CommercePaymentMethodGroupRelQualifierModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(CommercePaymentMethodGroupRelId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePaymentMethodGroupRelQualifier)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePaymentMethodGroupRelQualifier> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce payment method group rel qualifiers where CommercePaymentMethodGroupRelId = &#63; from the database.
	 *
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 */
	@Override
	public void removeByCommercePaymentMethodGroupRelId(
		long CommercePaymentMethodGroupRelId) {

		for (CommercePaymentMethodGroupRelQualifier
				commercePaymentMethodGroupRelQualifier :
					findByCommercePaymentMethodGroupRelId(
						CommercePaymentMethodGroupRelId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commercePaymentMethodGroupRelQualifier);
		}
	}

	/**
	 * Returns the number of commerce payment method group rel qualifiers where CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the number of matching commerce payment method group rel qualifiers
	 */
	@Override
	public int countByCommercePaymentMethodGroupRelId(
		long CommercePaymentMethodGroupRelId) {

		FinderPath finderPath =
			_finderPathCountByCommercePaymentMethodGroupRelId;

		Object[] finderArgs = new Object[] {CommercePaymentMethodGroupRelId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEPAYMENTMETHODGROUPRELID_COMMERCEPAYMENTMETHODGROUPRELID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(CommercePaymentMethodGroupRelId);

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
		_FINDER_COLUMN_COMMERCEPAYMENTMETHODGROUPRELID_COMMERCEPAYMENTMETHODGROUPRELID_2 =
			"commercePaymentMethodGroupRelQualifier.CommercePaymentMethodGroupRelId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the commerce payment method group rel qualifiers where classNameId = &#63; and CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the matching commerce payment method group rel qualifiers
	 */
	@Override
	public List<CommercePaymentMethodGroupRelQualifier> findByC_C(
		long classNameId, long CommercePaymentMethodGroupRelId) {

		return findByC_C(
			classNameId, CommercePaymentMethodGroupRelId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce payment method group rel qualifiers where classNameId = &#63; and CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @return the range of matching commerce payment method group rel qualifiers
	 */
	@Override
	public List<CommercePaymentMethodGroupRelQualifier> findByC_C(
		long classNameId, long CommercePaymentMethodGroupRelId, int start,
		int end) {

		return findByC_C(
			classNameId, CommercePaymentMethodGroupRelId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers where classNameId = &#63; and CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce payment method group rel qualifiers
	 */
	@Override
	public List<CommercePaymentMethodGroupRelQualifier> findByC_C(
		long classNameId, long CommercePaymentMethodGroupRelId, int start,
		int end,
		OrderByComparator<CommercePaymentMethodGroupRelQualifier>
			orderByComparator) {

		return findByC_C(
			classNameId, CommercePaymentMethodGroupRelId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers where classNameId = &#63; and CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce payment method group rel qualifiers
	 */
	@Override
	public List<CommercePaymentMethodGroupRelQualifier> findByC_C(
		long classNameId, long CommercePaymentMethodGroupRelId, int start,
		int end,
		OrderByComparator<CommercePaymentMethodGroupRelQualifier>
			orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {
					classNameId, CommercePaymentMethodGroupRelId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, CommercePaymentMethodGroupRelId, start, end,
				orderByComparator
			};
		}

		List<CommercePaymentMethodGroupRelQualifier> list = null;

		if (useFinderCache) {
			list =
				(List<CommercePaymentMethodGroupRelQualifier>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommercePaymentMethodGroupRelQualifier
						commercePaymentMethodGroupRelQualifier : list) {

					if ((classNameId !=
							commercePaymentMethodGroupRelQualifier.
								getClassNameId()) ||
						(CommercePaymentMethodGroupRelId !=
							commercePaymentMethodGroupRelQualifier.
								getCommercePaymentMethodGroupRelId())) {

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

			sb.append(_SQL_SELECT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEPAYMENTMETHODGROUPRELID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommercePaymentMethodGroupRelQualifierModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(CommercePaymentMethodGroupRelId);

				list =
					(List<CommercePaymentMethodGroupRelQualifier>)
						QueryUtil.list(query, getDialect(), start, end);

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
	 * Returns the first commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier findByC_C_First(
			long classNameId, long CommercePaymentMethodGroupRelId,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator)
		throws NoSuchPaymentMethodGroupRelQualifierException {

		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier = fetchByC_C_First(
				classNameId, CommercePaymentMethodGroupRelId,
				orderByComparator);

		if (commercePaymentMethodGroupRelQualifier != null) {
			return commercePaymentMethodGroupRelQualifier;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", CommercePaymentMethodGroupRelId=");
		sb.append(CommercePaymentMethodGroupRelId);

		sb.append("}");

		throw new NoSuchPaymentMethodGroupRelQualifierException(sb.toString());
	}

	/**
	 * Returns the first commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier fetchByC_C_First(
		long classNameId, long CommercePaymentMethodGroupRelId,
		OrderByComparator<CommercePaymentMethodGroupRelQualifier>
			orderByComparator) {

		List<CommercePaymentMethodGroupRelQualifier> list = findByC_C(
			classNameId, CommercePaymentMethodGroupRelId, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier findByC_C_Last(
			long classNameId, long CommercePaymentMethodGroupRelId,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator)
		throws NoSuchPaymentMethodGroupRelQualifierException {

		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier = fetchByC_C_Last(
				classNameId, CommercePaymentMethodGroupRelId,
				orderByComparator);

		if (commercePaymentMethodGroupRelQualifier != null) {
			return commercePaymentMethodGroupRelQualifier;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", CommercePaymentMethodGroupRelId=");
		sb.append(CommercePaymentMethodGroupRelId);

		sb.append("}");

		throw new NoSuchPaymentMethodGroupRelQualifierException(sb.toString());
	}

	/**
	 * Returns the last commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier fetchByC_C_Last(
		long classNameId, long CommercePaymentMethodGroupRelId,
		OrderByComparator<CommercePaymentMethodGroupRelQualifier>
			orderByComparator) {

		int count = countByC_C(classNameId, CommercePaymentMethodGroupRelId);

		if (count == 0) {
			return null;
		}

		List<CommercePaymentMethodGroupRelQualifier> list = findByC_C(
			classNameId, CommercePaymentMethodGroupRelId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce payment method group rel qualifiers before and after the current commerce payment method group rel qualifier in the ordered set where classNameId = &#63; and CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the current commerce payment method group rel qualifier
	 * @param classNameId the class name ID
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier[] findByC_C_PrevAndNext(
			long commercePaymentMethodGroupRelQualifierId, long classNameId,
			long CommercePaymentMethodGroupRelId,
			OrderByComparator<CommercePaymentMethodGroupRelQualifier>
				orderByComparator)
		throws NoSuchPaymentMethodGroupRelQualifierException {

		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier = findByPrimaryKey(
				commercePaymentMethodGroupRelQualifierId);

		Session session = null;

		try {
			session = openSession();

			CommercePaymentMethodGroupRelQualifier[] array =
				new CommercePaymentMethodGroupRelQualifierImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, commercePaymentMethodGroupRelQualifier, classNameId,
				CommercePaymentMethodGroupRelId, orderByComparator, true);

			array[1] = commercePaymentMethodGroupRelQualifier;

			array[2] = getByC_C_PrevAndNext(
				session, commercePaymentMethodGroupRelQualifier, classNameId,
				CommercePaymentMethodGroupRelId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommercePaymentMethodGroupRelQualifier getByC_C_PrevAndNext(
		Session session,
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier,
		long classNameId, long CommercePaymentMethodGroupRelId,
		OrderByComparator<CommercePaymentMethodGroupRelQualifier>
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

		sb.append(_SQL_SELECT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_COMMERCEPAYMENTMETHODGROUPRELID_2);

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
				CommercePaymentMethodGroupRelQualifierModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(CommercePaymentMethodGroupRelId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commercePaymentMethodGroupRelQualifier)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommercePaymentMethodGroupRelQualifier> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce payment method group rel qualifiers where classNameId = &#63; and CommercePaymentMethodGroupRelId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 */
	@Override
	public void removeByC_C(
		long classNameId, long CommercePaymentMethodGroupRelId) {

		for (CommercePaymentMethodGroupRelQualifier
				commercePaymentMethodGroupRelQualifier :
					findByC_C(
						classNameId, CommercePaymentMethodGroupRelId,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commercePaymentMethodGroupRelQualifier);
		}
	}

	/**
	 * Returns the number of commerce payment method group rel qualifiers where classNameId = &#63; and CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the number of matching commerce payment method group rel qualifiers
	 */
	@Override
	public int countByC_C(
		long classNameId, long CommercePaymentMethodGroupRelId) {

		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {
			classNameId, CommercePaymentMethodGroupRelId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEPAYMENTMETHODGROUPRELID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(CommercePaymentMethodGroupRelId);

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

	private static final String _FINDER_COLUMN_C_C_CLASSNAMEID_2 =
		"commercePaymentMethodGroupRelQualifier.classNameId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_C_COMMERCEPAYMENTMETHODGROUPRELID_2 =
			"commercePaymentMethodGroupRelQualifier.CommercePaymentMethodGroupRelId = ?";

	private FinderPath _finderPathFetchByC_C_C;
	private FinderPath _finderPathCountByC_C_C;

	/**
	 * Returns the commerce payment method group rel qualifier where classNameId = &#63; and classPK = &#63; and CommercePaymentMethodGroupRelId = &#63; or throws a <code>NoSuchPaymentMethodGroupRelQualifierException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the matching commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a matching commerce payment method group rel qualifier could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier findByC_C_C(
			long classNameId, long classPK,
			long CommercePaymentMethodGroupRelId)
		throws NoSuchPaymentMethodGroupRelQualifierException {

		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier = fetchByC_C_C(
				classNameId, classPK, CommercePaymentMethodGroupRelId);

		if (commercePaymentMethodGroupRelQualifier == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", CommercePaymentMethodGroupRelId=");
			sb.append(CommercePaymentMethodGroupRelId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchPaymentMethodGroupRelQualifierException(
				sb.toString());
		}

		return commercePaymentMethodGroupRelQualifier;
	}

	/**
	 * Returns the commerce payment method group rel qualifier where classNameId = &#63; and classPK = &#63; and CommercePaymentMethodGroupRelId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier fetchByC_C_C(
		long classNameId, long classPK, long CommercePaymentMethodGroupRelId) {

		return fetchByC_C_C(
			classNameId, classPK, CommercePaymentMethodGroupRelId, true);
	}

	/**
	 * Returns the commerce payment method group rel qualifier where classNameId = &#63; and classPK = &#63; and CommercePaymentMethodGroupRelId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce payment method group rel qualifier, or <code>null</code> if a matching commerce payment method group rel qualifier could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier fetchByC_C_C(
		long classNameId, long classPK, long CommercePaymentMethodGroupRelId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				classNameId, classPK, CommercePaymentMethodGroupRelId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C_C, finderArgs, this);
		}

		if (result instanceof CommercePaymentMethodGroupRelQualifier) {
			CommercePaymentMethodGroupRelQualifier
				commercePaymentMethodGroupRelQualifier =
					(CommercePaymentMethodGroupRelQualifier)result;

			if ((classNameId !=
					commercePaymentMethodGroupRelQualifier.getClassNameId()) ||
				(classPK !=
					commercePaymentMethodGroupRelQualifier.getClassPK()) ||
				(CommercePaymentMethodGroupRelId !=
					commercePaymentMethodGroupRelQualifier.
						getCommercePaymentMethodGroupRelId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCEPAYMENTMETHODGROUPRELID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(CommercePaymentMethodGroupRelId);

				List<CommercePaymentMethodGroupRelQualifier> list =
					query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_C, finderArgs, list);
					}
				}
				else {
					CommercePaymentMethodGroupRelQualifier
						commercePaymentMethodGroupRelQualifier = list.get(0);

					result = commercePaymentMethodGroupRelQualifier;

					cacheResult(commercePaymentMethodGroupRelQualifier);
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
			return (CommercePaymentMethodGroupRelQualifier)result;
		}
	}

	/**
	 * Removes the commerce payment method group rel qualifier where classNameId = &#63; and classPK = &#63; and CommercePaymentMethodGroupRelId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the commerce payment method group rel qualifier that was removed
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier removeByC_C_C(
			long classNameId, long classPK,
			long CommercePaymentMethodGroupRelId)
		throws NoSuchPaymentMethodGroupRelQualifierException {

		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier = findByC_C_C(
				classNameId, classPK, CommercePaymentMethodGroupRelId);

		return remove(commercePaymentMethodGroupRelQualifier);
	}

	/**
	 * Returns the number of commerce payment method group rel qualifiers where classNameId = &#63; and classPK = &#63; and CommercePaymentMethodGroupRelId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param CommercePaymentMethodGroupRelId the commerce payment method group rel ID
	 * @return the number of matching commerce payment method group rel qualifiers
	 */
	@Override
	public int countByC_C_C(
		long classNameId, long classPK, long CommercePaymentMethodGroupRelId) {

		FinderPath finderPath = _finderPathCountByC_C_C;

		Object[] finderArgs = new Object[] {
			classNameId, classPK, CommercePaymentMethodGroupRelId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCEPAYMENTMETHODGROUPRELID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(CommercePaymentMethodGroupRelId);

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

	private static final String _FINDER_COLUMN_C_C_C_CLASSNAMEID_2 =
		"commercePaymentMethodGroupRelQualifier.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"commercePaymentMethodGroupRelQualifier.classPK = ? AND ";

	private static final String
		_FINDER_COLUMN_C_C_C_COMMERCEPAYMENTMETHODGROUPRELID_2 =
			"commercePaymentMethodGroupRelQualifier.CommercePaymentMethodGroupRelId = ?";

	public CommercePaymentMethodGroupRelQualifierPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commercePaymentMethodGroupRelQualifierId",
			"CPMethodGroupRelQualifierId");
		dbColumnNames.put(
			"CommercePaymentMethodGroupRelId", "CPaymentMethodGroupRelId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommercePaymentMethodGroupRelQualifier.class);

		setModelImplClass(CommercePaymentMethodGroupRelQualifierImpl.class);
		setModelPKClass(long.class);

		setTable(CommercePaymentMethodGroupRelQualifierTable.INSTANCE);
	}

	/**
	 * Caches the commerce payment method group rel qualifier in the entity cache if it is enabled.
	 *
	 * @param commercePaymentMethodGroupRelQualifier the commerce payment method group rel qualifier
	 */
	@Override
	public void cacheResult(
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier) {

		entityCache.putResult(
			CommercePaymentMethodGroupRelQualifierImpl.class,
			commercePaymentMethodGroupRelQualifier.getPrimaryKey(),
			commercePaymentMethodGroupRelQualifier);

		finderCache.putResult(
			_finderPathFetchByC_C_C,
			new Object[] {
				commercePaymentMethodGroupRelQualifier.getClassNameId(),
				commercePaymentMethodGroupRelQualifier.getClassPK(),
				commercePaymentMethodGroupRelQualifier.
					getCommercePaymentMethodGroupRelId()
			},
			commercePaymentMethodGroupRelQualifier);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce payment method group rel qualifiers in the entity cache if it is enabled.
	 *
	 * @param commercePaymentMethodGroupRelQualifiers the commerce payment method group rel qualifiers
	 */
	@Override
	public void cacheResult(
		List<CommercePaymentMethodGroupRelQualifier>
			commercePaymentMethodGroupRelQualifiers) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commercePaymentMethodGroupRelQualifiers.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommercePaymentMethodGroupRelQualifier
				commercePaymentMethodGroupRelQualifier :
					commercePaymentMethodGroupRelQualifiers) {

			if (entityCache.getResult(
					CommercePaymentMethodGroupRelQualifierImpl.class,
					commercePaymentMethodGroupRelQualifier.getPrimaryKey()) ==
						null) {

				cacheResult(commercePaymentMethodGroupRelQualifier);
			}
		}
	}

	/**
	 * Clears the cache for all commerce payment method group rel qualifiers.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(
			CommercePaymentMethodGroupRelQualifierImpl.class);

		finderCache.clearCache(
			CommercePaymentMethodGroupRelQualifierImpl.class);
	}

	/**
	 * Clears the cache for the commerce payment method group rel qualifier.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier) {

		entityCache.removeResult(
			CommercePaymentMethodGroupRelQualifierImpl.class,
			commercePaymentMethodGroupRelQualifier);
	}

	@Override
	public void clearCache(
		List<CommercePaymentMethodGroupRelQualifier>
			commercePaymentMethodGroupRelQualifiers) {

		for (CommercePaymentMethodGroupRelQualifier
				commercePaymentMethodGroupRelQualifier :
					commercePaymentMethodGroupRelQualifiers) {

			entityCache.removeResult(
				CommercePaymentMethodGroupRelQualifierImpl.class,
				commercePaymentMethodGroupRelQualifier);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(
			CommercePaymentMethodGroupRelQualifierImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommercePaymentMethodGroupRelQualifierImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommercePaymentMethodGroupRelQualifierModelImpl
			commercePaymentMethodGroupRelQualifierModelImpl) {

		Object[] args = new Object[] {
			commercePaymentMethodGroupRelQualifierModelImpl.getClassNameId(),
			commercePaymentMethodGroupRelQualifierModelImpl.getClassPK(),
			commercePaymentMethodGroupRelQualifierModelImpl.
				getCommercePaymentMethodGroupRelId()
		};

		finderCache.putResult(_finderPathCountByC_C_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C_C, args,
			commercePaymentMethodGroupRelQualifierModelImpl);
	}

	/**
	 * Creates a new commerce payment method group rel qualifier with the primary key. Does not add the commerce payment method group rel qualifier to the database.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key for the new commerce payment method group rel qualifier
	 * @return the new commerce payment method group rel qualifier
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier create(
		long commercePaymentMethodGroupRelQualifierId) {

		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier =
				new CommercePaymentMethodGroupRelQualifierImpl();

		commercePaymentMethodGroupRelQualifier.setNew(true);
		commercePaymentMethodGroupRelQualifier.setPrimaryKey(
			commercePaymentMethodGroupRelQualifierId);

		commercePaymentMethodGroupRelQualifier.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commercePaymentMethodGroupRelQualifier;
	}

	/**
	 * Removes the commerce payment method group rel qualifier with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the commerce payment method group rel qualifier
	 * @return the commerce payment method group rel qualifier that was removed
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier remove(
			long commercePaymentMethodGroupRelQualifierId)
		throws NoSuchPaymentMethodGroupRelQualifierException {

		return remove((Serializable)commercePaymentMethodGroupRelQualifierId);
	}

	/**
	 * Removes the commerce payment method group rel qualifier with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce payment method group rel qualifier
	 * @return the commerce payment method group rel qualifier that was removed
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier remove(
			Serializable primaryKey)
		throws NoSuchPaymentMethodGroupRelQualifierException {

		Session session = null;

		try {
			session = openSession();

			CommercePaymentMethodGroupRelQualifier
				commercePaymentMethodGroupRelQualifier =
					(CommercePaymentMethodGroupRelQualifier)session.get(
						CommercePaymentMethodGroupRelQualifierImpl.class,
						primaryKey);

			if (commercePaymentMethodGroupRelQualifier == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchPaymentMethodGroupRelQualifierException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commercePaymentMethodGroupRelQualifier);
		}
		catch (NoSuchPaymentMethodGroupRelQualifierException
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
	protected CommercePaymentMethodGroupRelQualifier removeImpl(
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commercePaymentMethodGroupRelQualifier)) {
				commercePaymentMethodGroupRelQualifier =
					(CommercePaymentMethodGroupRelQualifier)session.get(
						CommercePaymentMethodGroupRelQualifierImpl.class,
						commercePaymentMethodGroupRelQualifier.
							getPrimaryKeyObj());
			}

			if (commercePaymentMethodGroupRelQualifier != null) {
				session.delete(commercePaymentMethodGroupRelQualifier);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commercePaymentMethodGroupRelQualifier != null) {
			clearCache(commercePaymentMethodGroupRelQualifier);
		}

		return commercePaymentMethodGroupRelQualifier;
	}

	@Override
	public CommercePaymentMethodGroupRelQualifier updateImpl(
		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier) {

		boolean isNew = commercePaymentMethodGroupRelQualifier.isNew();

		if (!(commercePaymentMethodGroupRelQualifier instanceof
				CommercePaymentMethodGroupRelQualifierModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commercePaymentMethodGroupRelQualifier.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commercePaymentMethodGroupRelQualifier);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commercePaymentMethodGroupRelQualifier proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommercePaymentMethodGroupRelQualifier implementation " +
					commercePaymentMethodGroupRelQualifier.getClass());
		}

		CommercePaymentMethodGroupRelQualifierModelImpl
			commercePaymentMethodGroupRelQualifierModelImpl =
				(CommercePaymentMethodGroupRelQualifierModelImpl)
					commercePaymentMethodGroupRelQualifier;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(commercePaymentMethodGroupRelQualifier.getCreateDate() == null)) {

			if (serviceContext == null) {
				commercePaymentMethodGroupRelQualifier.setCreateDate(date);
			}
			else {
				commercePaymentMethodGroupRelQualifier.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commercePaymentMethodGroupRelQualifierModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				commercePaymentMethodGroupRelQualifier.setModifiedDate(date);
			}
			else {
				commercePaymentMethodGroupRelQualifier.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commercePaymentMethodGroupRelQualifier);
			}
			else {
				commercePaymentMethodGroupRelQualifier =
					(CommercePaymentMethodGroupRelQualifier)session.merge(
						commercePaymentMethodGroupRelQualifier);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommercePaymentMethodGroupRelQualifierImpl.class,
			commercePaymentMethodGroupRelQualifierModelImpl, false, true);

		cacheUniqueFindersCache(
			commercePaymentMethodGroupRelQualifierModelImpl);

		if (isNew) {
			commercePaymentMethodGroupRelQualifier.setNew(false);
		}

		commercePaymentMethodGroupRelQualifier.resetOriginalValues();

		return commercePaymentMethodGroupRelQualifier;
	}

	/**
	 * Returns the commerce payment method group rel qualifier with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce payment method group rel qualifier
	 * @return the commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchPaymentMethodGroupRelQualifierException {

		CommercePaymentMethodGroupRelQualifier
			commercePaymentMethodGroupRelQualifier = fetchByPrimaryKey(
				primaryKey);

		if (commercePaymentMethodGroupRelQualifier == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchPaymentMethodGroupRelQualifierException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commercePaymentMethodGroupRelQualifier;
	}

	/**
	 * Returns the commerce payment method group rel qualifier with the primary key or throws a <code>NoSuchPaymentMethodGroupRelQualifierException</code> if it could not be found.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the commerce payment method group rel qualifier
	 * @return the commerce payment method group rel qualifier
	 * @throws NoSuchPaymentMethodGroupRelQualifierException if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier findByPrimaryKey(
			long commercePaymentMethodGroupRelQualifierId)
		throws NoSuchPaymentMethodGroupRelQualifierException {

		return findByPrimaryKey(
			(Serializable)commercePaymentMethodGroupRelQualifierId);
	}

	/**
	 * Returns the commerce payment method group rel qualifier with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commercePaymentMethodGroupRelQualifierId the primary key of the commerce payment method group rel qualifier
	 * @return the commerce payment method group rel qualifier, or <code>null</code> if a commerce payment method group rel qualifier with the primary key could not be found
	 */
	@Override
	public CommercePaymentMethodGroupRelQualifier fetchByPrimaryKey(
		long commercePaymentMethodGroupRelQualifierId) {

		return fetchByPrimaryKey(
			(Serializable)commercePaymentMethodGroupRelQualifierId);
	}

	/**
	 * Returns all the commerce payment method group rel qualifiers.
	 *
	 * @return the commerce payment method group rel qualifiers
	 */
	@Override
	public List<CommercePaymentMethodGroupRelQualifier> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce payment method group rel qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @return the range of commerce payment method group rel qualifiers
	 */
	@Override
	public List<CommercePaymentMethodGroupRelQualifier> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce payment method group rel qualifiers
	 */
	@Override
	public List<CommercePaymentMethodGroupRelQualifier> findAll(
		int start, int end,
		OrderByComparator<CommercePaymentMethodGroupRelQualifier>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce payment method group rel qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommercePaymentMethodGroupRelQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce payment method group rel qualifiers
	 * @param end the upper bound of the range of commerce payment method group rel qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce payment method group rel qualifiers
	 */
	@Override
	public List<CommercePaymentMethodGroupRelQualifier> findAll(
		int start, int end,
		OrderByComparator<CommercePaymentMethodGroupRelQualifier>
			orderByComparator,
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

		List<CommercePaymentMethodGroupRelQualifier> list = null;

		if (useFinderCache) {
			list =
				(List<CommercePaymentMethodGroupRelQualifier>)
					finderCache.getResult(finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER;

				sql = sql.concat(
					CommercePaymentMethodGroupRelQualifierModelImpl.
						ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list =
					(List<CommercePaymentMethodGroupRelQualifier>)
						QueryUtil.list(query, getDialect(), start, end);

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
	 * Removes all the commerce payment method group rel qualifiers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommercePaymentMethodGroupRelQualifier
				commercePaymentMethodGroupRelQualifier : findAll()) {

			remove(commercePaymentMethodGroupRelQualifier);
		}
	}

	/**
	 * Returns the number of commerce payment method group rel qualifiers.
	 *
	 * @return the number of commerce payment method group rel qualifiers
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(
					_SQL_COUNT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER);

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
		return "CPMethodGroupRelQualifierId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommercePaymentMethodGroupRelQualifierModelImpl.
			TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce payment method group rel qualifier persistence.
	 */
	@Activate
	public void activate() {
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

		_finderPathWithPaginationFindByCommercePaymentMethodGroupRelId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommercePaymentMethodGroupRelId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"CPaymentMethodGroupRelId"}, true);

		_finderPathWithoutPaginationFindByCommercePaymentMethodGroupRelId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommercePaymentMethodGroupRelId",
				new String[] {Long.class.getName()},
				new String[] {"CPaymentMethodGroupRelId"}, true);

		_finderPathCountByCommercePaymentMethodGroupRelId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommercePaymentMethodGroupRelId",
			new String[] {Long.class.getName()},
			new String[] {"CPaymentMethodGroupRelId"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "CPaymentMethodGroupRelId"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "CPaymentMethodGroupRelId"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "CPaymentMethodGroupRelId"}, false);

		_finderPathFetchByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "CPaymentMethodGroupRelId"},
			true);

		_finderPathCountByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "CPaymentMethodGroupRelId"},
			false);

		CommercePaymentMethodGroupRelQualifierUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommercePaymentMethodGroupRelQualifierUtil.setPersistence(null);

		entityCache.removeCache(
			CommercePaymentMethodGroupRelQualifierImpl.class.getName());
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CommercePersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String
		_SQL_SELECT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER =
			"SELECT commercePaymentMethodGroupRelQualifier FROM CommercePaymentMethodGroupRelQualifier commercePaymentMethodGroupRelQualifier";

	private static final String
		_SQL_SELECT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER_WHERE =
			"SELECT commercePaymentMethodGroupRelQualifier FROM CommercePaymentMethodGroupRelQualifier commercePaymentMethodGroupRelQualifier WHERE ";

	private static final String
		_SQL_COUNT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER =
			"SELECT COUNT(commercePaymentMethodGroupRelQualifier) FROM CommercePaymentMethodGroupRelQualifier commercePaymentMethodGroupRelQualifier";

	private static final String
		_SQL_COUNT_COMMERCEPAYMENTMETHODGROUPRELQUALIFIER_WHERE =
			"SELECT COUNT(commercePaymentMethodGroupRelQualifier) FROM CommercePaymentMethodGroupRelQualifier commercePaymentMethodGroupRelQualifier WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commercePaymentMethodGroupRelQualifier.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommercePaymentMethodGroupRelQualifier exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommercePaymentMethodGroupRelQualifier exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommercePaymentMethodGroupRelQualifierPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {
			"commercePaymentMethodGroupRelQualifierId",
			"CommercePaymentMethodGroupRelId"
		});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}