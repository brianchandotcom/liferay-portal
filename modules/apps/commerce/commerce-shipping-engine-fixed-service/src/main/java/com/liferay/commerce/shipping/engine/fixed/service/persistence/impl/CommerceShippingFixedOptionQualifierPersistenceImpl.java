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

package com.liferay.commerce.shipping.engine.fixed.service.persistence.impl;

import com.liferay.commerce.shipping.engine.fixed.exception.NoSuchShippingFixedOptionQualifierException;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifier;
import com.liferay.commerce.shipping.engine.fixed.model.CommerceShippingFixedOptionQualifierTable;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionQualifierImpl;
import com.liferay.commerce.shipping.engine.fixed.model.impl.CommerceShippingFixedOptionQualifierModelImpl;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionQualifierPersistence;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.CommerceShippingFixedOptionQualifierUtil;
import com.liferay.commerce.shipping.engine.fixed.service.persistence.impl.constants.CommercePersistenceConstants;
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
 * The persistence implementation for the commerce shipping fixed option qualifier service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = CommerceShippingFixedOptionQualifierPersistence.class)
public class CommerceShippingFixedOptionQualifierPersistenceImpl
	extends BasePersistenceImpl<CommerceShippingFixedOptionQualifier>
	implements CommerceShippingFixedOptionQualifierPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceShippingFixedOptionQualifierUtil</code> to access the commerce shipping fixed option qualifier persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceShippingFixedOptionQualifierImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath
		_finderPathWithPaginationFindByCommerceShippingFixedOptionId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceShippingFixedOptionId;
	private FinderPath _finderPathCountByCommerceShippingFixedOptionId;

	/**
	 * Returns all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option qualifiers
	 */
	@Override
	public List<CommerceShippingFixedOptionQualifier>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId) {

		return findByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @return the range of matching commerce shipping fixed option qualifiers
	 */
	@Override
	public List<CommerceShippingFixedOptionQualifier>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end) {

		return findByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed option qualifiers
	 */
	@Override
	public List<CommerceShippingFixedOptionQualifier>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator) {

		return findByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping fixed option qualifiers
	 */
	@Override
	public List<CommerceShippingFixedOptionQualifier>
		findByCommerceShippingFixedOptionId(
			long commerceShippingFixedOptionId, int start, int end,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceShippingFixedOptionId;
				finderArgs = new Object[] {commerceShippingFixedOptionId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceShippingFixedOptionId;
			finderArgs = new Object[] {
				commerceShippingFixedOptionId, start, end, orderByComparator
			};
		}

		List<CommerceShippingFixedOptionQualifier> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceShippingFixedOptionQualifier>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingFixedOptionQualifier
						commerceShippingFixedOptionQualifier : list) {

					if (commerceShippingFixedOptionId !=
							commerceShippingFixedOptionQualifier.
								getCommerceShippingFixedOptionId()) {

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

			sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCESHIPPINGFIXEDOPTIONID_COMMERCESHIPPINGFIXEDOPTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceShippingFixedOptionQualifierModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceShippingFixedOptionId);

				list =
					(List<CommerceShippingFixedOptionQualifier>)QueryUtil.list(
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
	 * Returns the first commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier
			findByCommerceShippingFixedOptionId_First(
				long commerceShippingFixedOptionId,
				OrderByComparator<CommerceShippingFixedOptionQualifier>
					orderByComparator)
		throws NoSuchShippingFixedOptionQualifierException {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier =
				fetchByCommerceShippingFixedOptionId_First(
					commerceShippingFixedOptionId, orderByComparator);

		if (commerceShippingFixedOptionQualifier != null) {
			return commerceShippingFixedOptionQualifier;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceShippingFixedOptionId=");
		sb.append(commerceShippingFixedOptionId);

		sb.append("}");

		throw new NoSuchShippingFixedOptionQualifierException(sb.toString());
	}

	/**
	 * Returns the first commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier
		fetchByCommerceShippingFixedOptionId_First(
			long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator) {

		List<CommerceShippingFixedOptionQualifier> list =
			findByCommerceShippingFixedOptionId(
				commerceShippingFixedOptionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier
			findByCommerceShippingFixedOptionId_Last(
				long commerceShippingFixedOptionId,
				OrderByComparator<CommerceShippingFixedOptionQualifier>
					orderByComparator)
		throws NoSuchShippingFixedOptionQualifierException {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier =
				fetchByCommerceShippingFixedOptionId_Last(
					commerceShippingFixedOptionId, orderByComparator);

		if (commerceShippingFixedOptionQualifier != null) {
			return commerceShippingFixedOptionQualifier;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceShippingFixedOptionId=");
		sb.append(commerceShippingFixedOptionId);

		sb.append("}");

		throw new NoSuchShippingFixedOptionQualifierException(sb.toString());
	}

	/**
	 * Returns the last commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier
		fetchByCommerceShippingFixedOptionId_Last(
			long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator) {

		int count = countByCommerceShippingFixedOptionId(
			commerceShippingFixedOptionId);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingFixedOptionQualifier> list =
			findByCommerceShippingFixedOptionId(
				commerceShippingFixedOptionId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipping fixed option qualifiers before and after the current commerce shipping fixed option qualifier in the ordered set where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the current commerce shipping fixed option qualifier
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier[]
			findByCommerceShippingFixedOptionId_PrevAndNext(
				long commerceShippingFixedOptionQualifierId,
				long commerceShippingFixedOptionId,
				OrderByComparator<CommerceShippingFixedOptionQualifier>
					orderByComparator)
		throws NoSuchShippingFixedOptionQualifierException {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier = findByPrimaryKey(
				commerceShippingFixedOptionQualifierId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingFixedOptionQualifier[] array =
				new CommerceShippingFixedOptionQualifierImpl[3];

			array[0] = getByCommerceShippingFixedOptionId_PrevAndNext(
				session, commerceShippingFixedOptionQualifier,
				commerceShippingFixedOptionId, orderByComparator, true);

			array[1] = commerceShippingFixedOptionQualifier;

			array[2] = getByCommerceShippingFixedOptionId_PrevAndNext(
				session, commerceShippingFixedOptionQualifier,
				commerceShippingFixedOptionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShippingFixedOptionQualifier
		getByCommerceShippingFixedOptionId_PrevAndNext(
			Session session,
			CommerceShippingFixedOptionQualifier
				commerceShippingFixedOptionQualifier,
			long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
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

		sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCESHIPPINGFIXEDOPTIONID_COMMERCESHIPPINGFIXEDOPTIONID_2);

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
				CommerceShippingFixedOptionQualifierModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceShippingFixedOptionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceShippingFixedOptionQualifier)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceShippingFixedOptionQualifier> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63; from the database.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 */
	@Override
	public void removeByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {

		for (CommerceShippingFixedOptionQualifier
				commerceShippingFixedOptionQualifier :
					findByCommerceShippingFixedOptionId(
						commerceShippingFixedOptionId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commerceShippingFixedOptionQualifier);
		}
	}

	/**
	 * Returns the number of commerce shipping fixed option qualifiers where commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the number of matching commerce shipping fixed option qualifiers
	 */
	@Override
	public int countByCommerceShippingFixedOptionId(
		long commerceShippingFixedOptionId) {

		FinderPath finderPath = _finderPathCountByCommerceShippingFixedOptionId;

		Object[] finderArgs = new Object[] {commerceShippingFixedOptionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCESHIPPINGFIXEDOPTIONID_COMMERCESHIPPINGFIXEDOPTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceShippingFixedOptionId);

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
		_FINDER_COLUMN_COMMERCESHIPPINGFIXEDOPTIONID_COMMERCESHIPPINGFIXEDOPTIONID_2 =
			"commerceShippingFixedOptionQualifier.commerceShippingFixedOptionId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option qualifiers
	 */
	@Override
	public List<CommerceShippingFixedOptionQualifier> findByC_C(
		long classNameId, long commerceShippingFixedOptionId) {

		return findByC_C(
			classNameId, commerceShippingFixedOptionId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @return the range of matching commerce shipping fixed option qualifiers
	 */
	@Override
	public List<CommerceShippingFixedOptionQualifier> findByC_C(
		long classNameId, long commerceShippingFixedOptionId, int start,
		int end) {

		return findByC_C(
			classNameId, commerceShippingFixedOptionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping fixed option qualifiers
	 */
	@Override
	public List<CommerceShippingFixedOptionQualifier> findByC_C(
		long classNameId, long commerceShippingFixedOptionId, int start,
		int end,
		OrderByComparator<CommerceShippingFixedOptionQualifier>
			orderByComparator) {

		return findByC_C(
			classNameId, commerceShippingFixedOptionId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping fixed option qualifiers
	 */
	@Override
	public List<CommerceShippingFixedOptionQualifier> findByC_C(
		long classNameId, long commerceShippingFixedOptionId, int start,
		int end,
		OrderByComparator<CommerceShippingFixedOptionQualifier>
			orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {
					classNameId, commerceShippingFixedOptionId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, commerceShippingFixedOptionId, start, end,
				orderByComparator
			};
		}

		List<CommerceShippingFixedOptionQualifier> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceShippingFixedOptionQualifier>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingFixedOptionQualifier
						commerceShippingFixedOptionQualifier : list) {

					if ((classNameId !=
							commerceShippingFixedOptionQualifier.
								getClassNameId()) ||
						(commerceShippingFixedOptionId !=
							commerceShippingFixedOptionQualifier.
								getCommerceShippingFixedOptionId())) {

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

			sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCESHIPPINGFIXEDOPTIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceShippingFixedOptionQualifierModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(commerceShippingFixedOptionId);

				list =
					(List<CommerceShippingFixedOptionQualifier>)QueryUtil.list(
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
	 * Returns the first commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier findByC_C_First(
			long classNameId, long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator)
		throws NoSuchShippingFixedOptionQualifierException {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier = fetchByC_C_First(
				classNameId, commerceShippingFixedOptionId, orderByComparator);

		if (commerceShippingFixedOptionQualifier != null) {
			return commerceShippingFixedOptionQualifier;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", commerceShippingFixedOptionId=");
		sb.append(commerceShippingFixedOptionId);

		sb.append("}");

		throw new NoSuchShippingFixedOptionQualifierException(sb.toString());
	}

	/**
	 * Returns the first commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier fetchByC_C_First(
		long classNameId, long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionQualifier>
			orderByComparator) {

		List<CommerceShippingFixedOptionQualifier> list = findByC_C(
			classNameId, commerceShippingFixedOptionId, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier findByC_C_Last(
			long classNameId, long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator)
		throws NoSuchShippingFixedOptionQualifierException {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier = fetchByC_C_Last(
				classNameId, commerceShippingFixedOptionId, orderByComparator);

		if (commerceShippingFixedOptionQualifier != null) {
			return commerceShippingFixedOptionQualifier;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", commerceShippingFixedOptionId=");
		sb.append(commerceShippingFixedOptionId);

		sb.append("}");

		throw new NoSuchShippingFixedOptionQualifierException(sb.toString());
	}

	/**
	 * Returns the last commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier fetchByC_C_Last(
		long classNameId, long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionQualifier>
			orderByComparator) {

		int count = countByC_C(classNameId, commerceShippingFixedOptionId);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingFixedOptionQualifier> list = findByC_C(
			classNameId, commerceShippingFixedOptionId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipping fixed option qualifiers before and after the current commerce shipping fixed option qualifier in the ordered set where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the current commerce shipping fixed option qualifier
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier[] findByC_C_PrevAndNext(
			long commerceShippingFixedOptionQualifierId, long classNameId,
			long commerceShippingFixedOptionId,
			OrderByComparator<CommerceShippingFixedOptionQualifier>
				orderByComparator)
		throws NoSuchShippingFixedOptionQualifierException {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier = findByPrimaryKey(
				commerceShippingFixedOptionQualifierId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingFixedOptionQualifier[] array =
				new CommerceShippingFixedOptionQualifierImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, commerceShippingFixedOptionQualifier, classNameId,
				commerceShippingFixedOptionId, orderByComparator, true);

			array[1] = commerceShippingFixedOptionQualifier;

			array[2] = getByC_C_PrevAndNext(
				session, commerceShippingFixedOptionQualifier, classNameId,
				commerceShippingFixedOptionId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShippingFixedOptionQualifier getByC_C_PrevAndNext(
		Session session,
		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier,
		long classNameId, long commerceShippingFixedOptionId,
		OrderByComparator<CommerceShippingFixedOptionQualifier>
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

		sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_COMMERCESHIPPINGFIXEDOPTIONID_2);

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
				CommerceShippingFixedOptionQualifierModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(commerceShippingFixedOptionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceShippingFixedOptionQualifier)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceShippingFixedOptionQualifier> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 */
	@Override
	public void removeByC_C(
		long classNameId, long commerceShippingFixedOptionId) {

		for (CommerceShippingFixedOptionQualifier
				commerceShippingFixedOptionQualifier :
					findByC_C(
						classNameId, commerceShippingFixedOptionId,
						QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(commerceShippingFixedOptionQualifier);
		}
	}

	/**
	 * Returns the number of commerce shipping fixed option qualifiers where classNameId = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the number of matching commerce shipping fixed option qualifiers
	 */
	@Override
	public int countByC_C(
		long classNameId, long commerceShippingFixedOptionId) {

		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {
			classNameId, commerceShippingFixedOptionId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCESHIPPINGFIXEDOPTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(commerceShippingFixedOptionId);

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
		"commerceShippingFixedOptionQualifier.classNameId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_C_COMMERCESHIPPINGFIXEDOPTIONID_2 =
			"commerceShippingFixedOptionQualifier.commerceShippingFixedOptionId = ?";

	private FinderPath _finderPathFetchByC_C_C;
	private FinderPath _finderPathCountByC_C_C;

	/**
	 * Returns the commerce shipping fixed option qualifier where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63; or throws a <code>NoSuchShippingFixedOptionQualifierException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a matching commerce shipping fixed option qualifier could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier findByC_C_C(
			long classNameId, long classPK, long commerceShippingFixedOptionId)
		throws NoSuchShippingFixedOptionQualifierException {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier = fetchByC_C_C(
				classNameId, classPK, commerceShippingFixedOptionId);

		if (commerceShippingFixedOptionQualifier == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", commerceShippingFixedOptionId=");
			sb.append(commerceShippingFixedOptionId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchShippingFixedOptionQualifierException(
				sb.toString());
		}

		return commerceShippingFixedOptionQualifier;
	}

	/**
	 * Returns the commerce shipping fixed option qualifier where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier fetchByC_C_C(
		long classNameId, long classPK, long commerceShippingFixedOptionId) {

		return fetchByC_C_C(
			classNameId, classPK, commerceShippingFixedOptionId, true);
	}

	/**
	 * Returns the commerce shipping fixed option qualifier where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce shipping fixed option qualifier, or <code>null</code> if a matching commerce shipping fixed option qualifier could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier fetchByC_C_C(
		long classNameId, long classPK, long commerceShippingFixedOptionId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				classNameId, classPK, commerceShippingFixedOptionId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C_C, finderArgs, this);
		}

		if (result instanceof CommerceShippingFixedOptionQualifier) {
			CommerceShippingFixedOptionQualifier
				commerceShippingFixedOptionQualifier =
					(CommerceShippingFixedOptionQualifier)result;

			if ((classNameId !=
					commerceShippingFixedOptionQualifier.getClassNameId()) ||
				(classPK !=
					commerceShippingFixedOptionQualifier.getClassPK()) ||
				(commerceShippingFixedOptionId !=
					commerceShippingFixedOptionQualifier.
						getCommerceShippingFixedOptionId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCESHIPPINGFIXEDOPTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceShippingFixedOptionId);

				List<CommerceShippingFixedOptionQualifier> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_C, finderArgs, list);
					}
				}
				else {
					CommerceShippingFixedOptionQualifier
						commerceShippingFixedOptionQualifier = list.get(0);

					result = commerceShippingFixedOptionQualifier;

					cacheResult(commerceShippingFixedOptionQualifier);
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
			return (CommerceShippingFixedOptionQualifier)result;
		}
	}

	/**
	 * Removes the commerce shipping fixed option qualifier where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the commerce shipping fixed option qualifier that was removed
	 */
	@Override
	public CommerceShippingFixedOptionQualifier removeByC_C_C(
			long classNameId, long classPK, long commerceShippingFixedOptionId)
		throws NoSuchShippingFixedOptionQualifierException {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier = findByC_C_C(
				classNameId, classPK, commerceShippingFixedOptionId);

		return remove(commerceShippingFixedOptionQualifier);
	}

	/**
	 * Returns the number of commerce shipping fixed option qualifiers where classNameId = &#63; and classPK = &#63; and commerceShippingFixedOptionId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceShippingFixedOptionId the commerce shipping fixed option ID
	 * @return the number of matching commerce shipping fixed option qualifiers
	 */
	@Override
	public int countByC_C_C(
		long classNameId, long classPK, long commerceShippingFixedOptionId) {

		FinderPath finderPath = _finderPathCountByC_C_C;

		Object[] finderArgs = new Object[] {
			classNameId, classPK, commerceShippingFixedOptionId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCESHIPPINGFIXEDOPTIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceShippingFixedOptionId);

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
		"commerceShippingFixedOptionQualifier.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"commerceShippingFixedOptionQualifier.classPK = ? AND ";

	private static final String
		_FINDER_COLUMN_C_C_C_COMMERCESHIPPINGFIXEDOPTIONID_2 =
			"commerceShippingFixedOptionQualifier.commerceShippingFixedOptionId = ?";

	public CommerceShippingFixedOptionQualifierPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceShippingFixedOptionQualifierId",
			"CSFixedOptionQualifierId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceShippingFixedOptionQualifier.class);

		setModelImplClass(CommerceShippingFixedOptionQualifierImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceShippingFixedOptionQualifierTable.INSTANCE);
	}

	/**
	 * Caches the commerce shipping fixed option qualifier in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptionQualifier the commerce shipping fixed option qualifier
	 */
	@Override
	public void cacheResult(
		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier) {

		entityCache.putResult(
			CommerceShippingFixedOptionQualifierImpl.class,
			commerceShippingFixedOptionQualifier.getPrimaryKey(),
			commerceShippingFixedOptionQualifier);

		finderCache.putResult(
			_finderPathFetchByC_C_C,
			new Object[] {
				commerceShippingFixedOptionQualifier.getClassNameId(),
				commerceShippingFixedOptionQualifier.getClassPK(),
				commerceShippingFixedOptionQualifier.
					getCommerceShippingFixedOptionId()
			},
			commerceShippingFixedOptionQualifier);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce shipping fixed option qualifiers in the entity cache if it is enabled.
	 *
	 * @param commerceShippingFixedOptionQualifiers the commerce shipping fixed option qualifiers
	 */
	@Override
	public void cacheResult(
		List<CommerceShippingFixedOptionQualifier>
			commerceShippingFixedOptionQualifiers) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceShippingFixedOptionQualifiers.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceShippingFixedOptionQualifier
				commerceShippingFixedOptionQualifier :
					commerceShippingFixedOptionQualifiers) {

			if (entityCache.getResult(
					CommerceShippingFixedOptionQualifierImpl.class,
					commerceShippingFixedOptionQualifier.getPrimaryKey()) ==
						null) {

				cacheResult(commerceShippingFixedOptionQualifier);
			}
		}
	}

	/**
	 * Clears the cache for all commerce shipping fixed option qualifiers.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceShippingFixedOptionQualifierImpl.class);

		finderCache.clearCache(CommerceShippingFixedOptionQualifierImpl.class);
	}

	/**
	 * Clears the cache for the commerce shipping fixed option qualifier.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier) {

		entityCache.removeResult(
			CommerceShippingFixedOptionQualifierImpl.class,
			commerceShippingFixedOptionQualifier);
	}

	@Override
	public void clearCache(
		List<CommerceShippingFixedOptionQualifier>
			commerceShippingFixedOptionQualifiers) {

		for (CommerceShippingFixedOptionQualifier
				commerceShippingFixedOptionQualifier :
					commerceShippingFixedOptionQualifiers) {

			entityCache.removeResult(
				CommerceShippingFixedOptionQualifierImpl.class,
				commerceShippingFixedOptionQualifier);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceShippingFixedOptionQualifierImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceShippingFixedOptionQualifierImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceShippingFixedOptionQualifierModelImpl
			commerceShippingFixedOptionQualifierModelImpl) {

		Object[] args = new Object[] {
			commerceShippingFixedOptionQualifierModelImpl.getClassNameId(),
			commerceShippingFixedOptionQualifierModelImpl.getClassPK(),
			commerceShippingFixedOptionQualifierModelImpl.
				getCommerceShippingFixedOptionId()
		};

		finderCache.putResult(_finderPathCountByC_C_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C_C, args,
			commerceShippingFixedOptionQualifierModelImpl);
	}

	/**
	 * Creates a new commerce shipping fixed option qualifier with the primary key. Does not add the commerce shipping fixed option qualifier to the database.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key for the new commerce shipping fixed option qualifier
	 * @return the new commerce shipping fixed option qualifier
	 */
	@Override
	public CommerceShippingFixedOptionQualifier create(
		long commerceShippingFixedOptionQualifierId) {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier =
				new CommerceShippingFixedOptionQualifierImpl();

		commerceShippingFixedOptionQualifier.setNew(true);
		commerceShippingFixedOptionQualifier.setPrimaryKey(
			commerceShippingFixedOptionQualifierId);

		commerceShippingFixedOptionQualifier.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceShippingFixedOptionQualifier;
	}

	/**
	 * Removes the commerce shipping fixed option qualifier with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the commerce shipping fixed option qualifier
	 * @return the commerce shipping fixed option qualifier that was removed
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier remove(
			long commerceShippingFixedOptionQualifierId)
		throws NoSuchShippingFixedOptionQualifierException {

		return remove((Serializable)commerceShippingFixedOptionQualifierId);
	}

	/**
	 * Removes the commerce shipping fixed option qualifier with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce shipping fixed option qualifier
	 * @return the commerce shipping fixed option qualifier that was removed
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier remove(Serializable primaryKey)
		throws NoSuchShippingFixedOptionQualifierException {

		Session session = null;

		try {
			session = openSession();

			CommerceShippingFixedOptionQualifier
				commerceShippingFixedOptionQualifier =
					(CommerceShippingFixedOptionQualifier)session.get(
						CommerceShippingFixedOptionQualifierImpl.class,
						primaryKey);

			if (commerceShippingFixedOptionQualifier == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchShippingFixedOptionQualifierException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceShippingFixedOptionQualifier);
		}
		catch (NoSuchShippingFixedOptionQualifierException
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
	protected CommerceShippingFixedOptionQualifier removeImpl(
		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceShippingFixedOptionQualifier)) {
				commerceShippingFixedOptionQualifier =
					(CommerceShippingFixedOptionQualifier)session.get(
						CommerceShippingFixedOptionQualifierImpl.class,
						commerceShippingFixedOptionQualifier.
							getPrimaryKeyObj());
			}

			if (commerceShippingFixedOptionQualifier != null) {
				session.delete(commerceShippingFixedOptionQualifier);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceShippingFixedOptionQualifier != null) {
			clearCache(commerceShippingFixedOptionQualifier);
		}

		return commerceShippingFixedOptionQualifier;
	}

	@Override
	public CommerceShippingFixedOptionQualifier updateImpl(
		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier) {

		boolean isNew = commerceShippingFixedOptionQualifier.isNew();

		if (!(commerceShippingFixedOptionQualifier instanceof
				CommerceShippingFixedOptionQualifierModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceShippingFixedOptionQualifier.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceShippingFixedOptionQualifier);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceShippingFixedOptionQualifier proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceShippingFixedOptionQualifier implementation " +
					commerceShippingFixedOptionQualifier.getClass());
		}

		CommerceShippingFixedOptionQualifierModelImpl
			commerceShippingFixedOptionQualifierModelImpl =
				(CommerceShippingFixedOptionQualifierModelImpl)
					commerceShippingFixedOptionQualifier;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(commerceShippingFixedOptionQualifier.getCreateDate() == null)) {

			if (serviceContext == null) {
				commerceShippingFixedOptionQualifier.setCreateDate(date);
			}
			else {
				commerceShippingFixedOptionQualifier.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceShippingFixedOptionQualifierModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				commerceShippingFixedOptionQualifier.setModifiedDate(date);
			}
			else {
				commerceShippingFixedOptionQualifier.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceShippingFixedOptionQualifier);
			}
			else {
				commerceShippingFixedOptionQualifier =
					(CommerceShippingFixedOptionQualifier)session.merge(
						commerceShippingFixedOptionQualifier);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceShippingFixedOptionQualifierImpl.class,
			commerceShippingFixedOptionQualifierModelImpl, false, true);

		cacheUniqueFindersCache(commerceShippingFixedOptionQualifierModelImpl);

		if (isNew) {
			commerceShippingFixedOptionQualifier.setNew(false);
		}

		commerceShippingFixedOptionQualifier.resetOriginalValues();

		return commerceShippingFixedOptionQualifier;
	}

	/**
	 * Returns the commerce shipping fixed option qualifier with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipping fixed option qualifier
	 * @return the commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchShippingFixedOptionQualifierException {

		CommerceShippingFixedOptionQualifier
			commerceShippingFixedOptionQualifier = fetchByPrimaryKey(
				primaryKey);

		if (commerceShippingFixedOptionQualifier == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchShippingFixedOptionQualifierException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceShippingFixedOptionQualifier;
	}

	/**
	 * Returns the commerce shipping fixed option qualifier with the primary key or throws a <code>NoSuchShippingFixedOptionQualifierException</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the commerce shipping fixed option qualifier
	 * @return the commerce shipping fixed option qualifier
	 * @throws NoSuchShippingFixedOptionQualifierException if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier findByPrimaryKey(
			long commerceShippingFixedOptionQualifierId)
		throws NoSuchShippingFixedOptionQualifierException {

		return findByPrimaryKey(
			(Serializable)commerceShippingFixedOptionQualifierId);
	}

	/**
	 * Returns the commerce shipping fixed option qualifier with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceShippingFixedOptionQualifierId the primary key of the commerce shipping fixed option qualifier
	 * @return the commerce shipping fixed option qualifier, or <code>null</code> if a commerce shipping fixed option qualifier with the primary key could not be found
	 */
	@Override
	public CommerceShippingFixedOptionQualifier fetchByPrimaryKey(
		long commerceShippingFixedOptionQualifierId) {

		return fetchByPrimaryKey(
			(Serializable)commerceShippingFixedOptionQualifierId);
	}

	/**
	 * Returns all the commerce shipping fixed option qualifiers.
	 *
	 * @return the commerce shipping fixed option qualifiers
	 */
	@Override
	public List<CommerceShippingFixedOptionQualifier> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping fixed option qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @return the range of commerce shipping fixed option qualifiers
	 */
	@Override
	public List<CommerceShippingFixedOptionQualifier> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping fixed option qualifiers
	 */
	@Override
	public List<CommerceShippingFixedOptionQualifier> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingFixedOptionQualifier>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping fixed option qualifiers.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingFixedOptionQualifierModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping fixed option qualifiers
	 * @param end the upper bound of the range of commerce shipping fixed option qualifiers (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce shipping fixed option qualifiers
	 */
	@Override
	public List<CommerceShippingFixedOptionQualifier> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingFixedOptionQualifier>
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

		List<CommerceShippingFixedOptionQualifier> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceShippingFixedOptionQualifier>)
					finderCache.getResult(finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER;

				sql = sql.concat(
					CommerceShippingFixedOptionQualifierModelImpl.
						ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list =
					(List<CommerceShippingFixedOptionQualifier>)QueryUtil.list(
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
	 * Removes all the commerce shipping fixed option qualifiers from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceShippingFixedOptionQualifier
				commerceShippingFixedOptionQualifier : findAll()) {

			remove(commerceShippingFixedOptionQualifier);
		}
	}

	/**
	 * Returns the number of commerce shipping fixed option qualifiers.
	 *
	 * @return the number of commerce shipping fixed option qualifiers
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
					_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER);

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
		return "CSFixedOptionQualifierId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceShippingFixedOptionQualifierModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce shipping fixed option qualifier persistence.
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

		_finderPathWithPaginationFindByCommerceShippingFixedOptionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceShippingFixedOptionId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceShippingFixedOptionId"}, true);

		_finderPathWithoutPaginationFindByCommerceShippingFixedOptionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceShippingFixedOptionId",
				new String[] {Long.class.getName()},
				new String[] {"commerceShippingFixedOptionId"}, true);

		_finderPathCountByCommerceShippingFixedOptionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceShippingFixedOptionId",
			new String[] {Long.class.getName()},
			new String[] {"commerceShippingFixedOptionId"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "commerceShippingFixedOptionId"},
			true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "commerceShippingFixedOptionId"},
			true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "commerceShippingFixedOptionId"},
			false);

		_finderPathFetchByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"classNameId", "classPK", "commerceShippingFixedOptionId"
			},
			true);

		_finderPathCountByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {
				"classNameId", "classPK", "commerceShippingFixedOptionId"
			},
			false);

		CommerceShippingFixedOptionQualifierUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceShippingFixedOptionQualifierUtil.setPersistence(null);

		entityCache.removeCache(
			CommerceShippingFixedOptionQualifierImpl.class.getName());
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
		_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER =
			"SELECT commerceShippingFixedOptionQualifier FROM CommerceShippingFixedOptionQualifier commerceShippingFixedOptionQualifier";

	private static final String
		_SQL_SELECT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER_WHERE =
			"SELECT commerceShippingFixedOptionQualifier FROM CommerceShippingFixedOptionQualifier commerceShippingFixedOptionQualifier WHERE ";

	private static final String
		_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER =
			"SELECT COUNT(commerceShippingFixedOptionQualifier) FROM CommerceShippingFixedOptionQualifier commerceShippingFixedOptionQualifier";

	private static final String
		_SQL_COUNT_COMMERCESHIPPINGFIXEDOPTIONQUALIFIER_WHERE =
			"SELECT COUNT(commerceShippingFixedOptionQualifier) FROM CommerceShippingFixedOptionQualifier commerceShippingFixedOptionQualifier WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceShippingFixedOptionQualifier.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceShippingFixedOptionQualifier exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceShippingFixedOptionQualifier exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShippingFixedOptionQualifierPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceShippingFixedOptionQualifierId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}