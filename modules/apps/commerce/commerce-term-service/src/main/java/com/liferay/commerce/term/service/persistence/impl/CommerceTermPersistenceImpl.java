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

package com.liferay.commerce.term.service.persistence.impl;

import com.liferay.commerce.term.exception.NoSuchTermException;
import com.liferay.commerce.term.model.CommerceTerm;
import com.liferay.commerce.term.model.CommerceTermTable;
import com.liferay.commerce.term.model.impl.CommerceTermImpl;
import com.liferay.commerce.term.model.impl.CommerceTermModelImpl;
import com.liferay.commerce.term.service.persistence.CommerceTermPersistence;
import com.liferay.commerce.term.service.persistence.CommerceTermUtil;
import com.liferay.commerce.term.service.persistence.impl.constants.CommercePersistenceConstants;
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
import com.liferay.portal.kernel.service.persistence.BasePersistence;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringUtil;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the commerce term service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
@Component(service = {CommerceTermPersistence.class, BasePersistence.class})
public class CommerceTermPersistenceImpl
	extends BasePersistenceImpl<CommerceTerm>
	implements CommerceTermPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceTermUtil</code> to access the commerce term persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceTermImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByC_A;
	private FinderPath _finderPathWithoutPaginationFindByC_A;
	private FinderPath _finderPathCountByC_A;

	/**
	 * Returns all the commerce terms where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByC_A(long companyId, boolean active) {
		return findByC_A(
			companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce terms where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @return the range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByC_A(
		long companyId, boolean active, int start, int end) {

		return findByC_A(companyId, active, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce terms where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceTerm> orderByComparator) {

		return findByC_A(
			companyId, active, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce terms where companyId = &#63; and active = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByC_A(
		long companyId, boolean active, int start, int end,
		OrderByComparator<CommerceTerm> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_A;
				finderArgs = new Object[] {companyId, active};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_A;
			finderArgs = new Object[] {
				companyId, active, start, end, orderByComparator
			};
		}

		List<CommerceTerm> list = null;

		if (useFinderCache) {
			list = (List<CommerceTerm>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTerm commerceTerm : list) {
					if ((companyId != commerceTerm.getCompanyId()) ||
						(active != commerceTerm.isActive())) {

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

			sb.append(_SQL_SELECT_COMMERCETERM_WHERE);

			sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTermModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				list = (List<CommerceTerm>)QueryUtil.list(
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
	 * Returns the first commerce term in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm findByC_A_First(
			long companyId, boolean active,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = fetchByC_A_First(
			companyId, active, orderByComparator);

		if (commerceTerm != null) {
			return commerceTerm;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchTermException(sb.toString());
	}

	/**
	 * Returns the first commerce term in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm fetchByC_A_First(
		long companyId, boolean active,
		OrderByComparator<CommerceTerm> orderByComparator) {

		List<CommerceTerm> list = findByC_A(
			companyId, active, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce term in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm findByC_A_Last(
			long companyId, boolean active,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = fetchByC_A_Last(
			companyId, active, orderByComparator);

		if (commerceTerm != null) {
			return commerceTerm;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append("}");

		throw new NoSuchTermException(sb.toString());
	}

	/**
	 * Returns the last commerce term in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm fetchByC_A_Last(
		long companyId, boolean active,
		OrderByComparator<CommerceTerm> orderByComparator) {

		int count = countByC_A(companyId, active);

		if (count == 0) {
			return null;
		}

		List<CommerceTerm> list = findByC_A(
			companyId, active, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce terms before and after the current commerce term in the ordered set where companyId = &#63; and active = &#63;.
	 *
	 * @param commerceTermId the primary key of the current commerce term
	 * @param companyId the company ID
	 * @param active the active
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	@Override
	public CommerceTerm[] findByC_A_PrevAndNext(
			long commerceTermId, long companyId, boolean active,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = findByPrimaryKey(commerceTermId);

		Session session = null;

		try {
			session = openSession();

			CommerceTerm[] array = new CommerceTermImpl[3];

			array[0] = getByC_A_PrevAndNext(
				session, commerceTerm, companyId, active, orderByComparator,
				true);

			array[1] = commerceTerm;

			array[2] = getByC_A_PrevAndNext(
				session, commerceTerm, companyId, active, orderByComparator,
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

	protected CommerceTerm getByC_A_PrevAndNext(
		Session session, CommerceTerm commerceTerm, long companyId,
		boolean active, OrderByComparator<CommerceTerm> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETERM_WHERE);

		sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

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
			sb.append(CommerceTermModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(active);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(commerceTerm)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTerm> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce terms where companyId = &#63; and active = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 */
	@Override
	public void removeByC_A(long companyId, boolean active) {
		for (CommerceTerm commerceTerm :
				findByC_A(
					companyId, active, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceTerm);
		}
	}

	/**
	 * Returns the number of commerce terms where companyId = &#63; and active = &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @return the number of matching commerce terms
	 */
	@Override
	public int countByC_A(long companyId, boolean active) {
		FinderPath finderPath = _finderPathCountByC_A;

		Object[] finderArgs = new Object[] {companyId, active};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCETERM_WHERE);

			sb.append(_FINDER_COLUMN_C_A_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_ACTIVE_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

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

	private static final String _FINDER_COLUMN_C_A_COMPANYID_2 =
		"commerceTerm.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_ACTIVE_2 =
		"commerceTerm.active = ?";

	private FinderPath _finderPathWithPaginationFindByC_LikeType;
	private FinderPath _finderPathWithPaginationCountByC_LikeType;

	/**
	 * Returns all the commerce terms where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByC_LikeType(long companyId, String type) {
		return findByC_LikeType(
			companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce terms where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @return the range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByC_LikeType(
		long companyId, String type, int start, int end) {

		return findByC_LikeType(companyId, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce terms where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByC_LikeType(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceTerm> orderByComparator) {

		return findByC_LikeType(
			companyId, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce terms where companyId = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByC_LikeType(
		long companyId, String type, int start, int end,
		OrderByComparator<CommerceTerm> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_LikeType;
		finderArgs = new Object[] {
			companyId, type, start, end, orderByComparator
		};

		List<CommerceTerm> list = null;

		if (useFinderCache) {
			list = (List<CommerceTerm>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTerm commerceTerm : list) {
					if ((companyId != commerceTerm.getCompanyId()) ||
						!StringUtil.wildcardMatches(
							commerceTerm.getType(), type, '_', '%', '\\',
							true)) {

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

			sb.append(_SQL_SELECT_COMMERCETERM_WHERE);

			sb.append(_FINDER_COLUMN_C_LIKETYPE_COMPANYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_LIKETYPE_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_C_LIKETYPE_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTermModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<CommerceTerm>)QueryUtil.list(
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
	 * Returns the first commerce term in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm findByC_LikeType_First(
			long companyId, String type,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = fetchByC_LikeType_First(
			companyId, type, orderByComparator);

		if (commerceTerm != null) {
			return commerceTerm;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", typeLIKE");
		sb.append(type);

		sb.append("}");

		throw new NoSuchTermException(sb.toString());
	}

	/**
	 * Returns the first commerce term in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm fetchByC_LikeType_First(
		long companyId, String type,
		OrderByComparator<CommerceTerm> orderByComparator) {

		List<CommerceTerm> list = findByC_LikeType(
			companyId, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce term in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm findByC_LikeType_Last(
			long companyId, String type,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = fetchByC_LikeType_Last(
			companyId, type, orderByComparator);

		if (commerceTerm != null) {
			return commerceTerm;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", typeLIKE");
		sb.append(type);

		sb.append("}");

		throw new NoSuchTermException(sb.toString());
	}

	/**
	 * Returns the last commerce term in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm fetchByC_LikeType_Last(
		long companyId, String type,
		OrderByComparator<CommerceTerm> orderByComparator) {

		int count = countByC_LikeType(companyId, type);

		if (count == 0) {
			return null;
		}

		List<CommerceTerm> list = findByC_LikeType(
			companyId, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce terms before and after the current commerce term in the ordered set where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param commerceTermId the primary key of the current commerce term
	 * @param companyId the company ID
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	@Override
	public CommerceTerm[] findByC_LikeType_PrevAndNext(
			long commerceTermId, long companyId, String type,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		type = Objects.toString(type, "");

		CommerceTerm commerceTerm = findByPrimaryKey(commerceTermId);

		Session session = null;

		try {
			session = openSession();

			CommerceTerm[] array = new CommerceTermImpl[3];

			array[0] = getByC_LikeType_PrevAndNext(
				session, commerceTerm, companyId, type, orderByComparator,
				true);

			array[1] = commerceTerm;

			array[2] = getByC_LikeType_PrevAndNext(
				session, commerceTerm, companyId, type, orderByComparator,
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

	protected CommerceTerm getByC_LikeType_PrevAndNext(
		Session session, CommerceTerm commerceTerm, long companyId, String type,
		OrderByComparator<CommerceTerm> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				5 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(4);
		}

		sb.append(_SQL_SELECT_COMMERCETERM_WHERE);

		sb.append(_FINDER_COLUMN_C_LIKETYPE_COMPANYID_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_LIKETYPE_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_LIKETYPE_TYPE_2);
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
			sb.append(CommerceTermModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(commerceTerm)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTerm> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce terms where companyId = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 */
	@Override
	public void removeByC_LikeType(long companyId, String type) {
		for (CommerceTerm commerceTerm :
				findByC_LikeType(
					companyId, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceTerm);
		}
	}

	/**
	 * Returns the number of commerce terms where companyId = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param type the type
	 * @return the number of matching commerce terms
	 */
	@Override
	public int countByC_LikeType(long companyId, String type) {
		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathWithPaginationCountByC_LikeType;

		Object[] finderArgs = new Object[] {companyId, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCETERM_WHERE);

			sb.append(_FINDER_COLUMN_C_LIKETYPE_COMPANYID_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_LIKETYPE_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_C_LIKETYPE_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindType) {
					queryPos.add(type);
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

	private static final String _FINDER_COLUMN_C_LIKETYPE_COMPANYID_2 =
		"commerceTerm.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_LIKETYPE_TYPE_2 =
		"commerceTerm.type LIKE ?";

	private static final String _FINDER_COLUMN_C_LIKETYPE_TYPE_3 =
		"(commerceTerm.type IS NULL OR commerceTerm.type LIKE '')";

	private FinderPath _finderPathWithPaginationFindByLtD_S;
	private FinderPath _finderPathWithPaginationCountByLtD_S;

	/**
	 * Returns all the commerce terms where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByLtD_S(Date displayDate, int status) {
		return findByLtD_S(
			displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce terms where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @return the range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByLtD_S(
		Date displayDate, int status, int start, int end) {

		return findByLtD_S(displayDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce terms where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceTerm> orderByComparator) {

		return findByLtD_S(
			displayDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce terms where displayDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByLtD_S(
		Date displayDate, int status, int start, int end,
		OrderByComparator<CommerceTerm> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtD_S;
		finderArgs = new Object[] {
			_getTime(displayDate), status, start, end, orderByComparator
		};

		List<CommerceTerm> list = null;

		if (useFinderCache) {
			list = (List<CommerceTerm>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTerm commerceTerm : list) {
					if ((displayDate.getTime() <= commerceTerm.getDisplayDate(
						).getTime()) || (status != commerceTerm.getStatus())) {

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

			sb.append(_SQL_SELECT_COMMERCETERM_WHERE);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
			}

			sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTermModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDisplayDate) {
					queryPos.add(new Timestamp(displayDate.getTime()));
				}

				queryPos.add(status);

				list = (List<CommerceTerm>)QueryUtil.list(
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
	 * Returns the first commerce term in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm findByLtD_S_First(
			Date displayDate, int status,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = fetchByLtD_S_First(
			displayDate, status, orderByComparator);

		if (commerceTerm != null) {
			return commerceTerm;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchTermException(sb.toString());
	}

	/**
	 * Returns the first commerce term in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm fetchByLtD_S_First(
		Date displayDate, int status,
		OrderByComparator<CommerceTerm> orderByComparator) {

		List<CommerceTerm> list = findByLtD_S(
			displayDate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce term in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm findByLtD_S_Last(
			Date displayDate, int status,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = fetchByLtD_S_Last(
			displayDate, status, orderByComparator);

		if (commerceTerm != null) {
			return commerceTerm;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("displayDate<");
		sb.append(displayDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchTermException(sb.toString());
	}

	/**
	 * Returns the last commerce term in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm fetchByLtD_S_Last(
		Date displayDate, int status,
		OrderByComparator<CommerceTerm> orderByComparator) {

		int count = countByLtD_S(displayDate, status);

		if (count == 0) {
			return null;
		}

		List<CommerceTerm> list = findByLtD_S(
			displayDate, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce terms before and after the current commerce term in the ordered set where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceTermId the primary key of the current commerce term
	 * @param displayDate the display date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	@Override
	public CommerceTerm[] findByLtD_S_PrevAndNext(
			long commerceTermId, Date displayDate, int status,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = findByPrimaryKey(commerceTermId);

		Session session = null;

		try {
			session = openSession();

			CommerceTerm[] array = new CommerceTermImpl[3];

			array[0] = getByLtD_S_PrevAndNext(
				session, commerceTerm, displayDate, status, orderByComparator,
				true);

			array[1] = commerceTerm;

			array[2] = getByLtD_S_PrevAndNext(
				session, commerceTerm, displayDate, status, orderByComparator,
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

	protected CommerceTerm getByLtD_S_PrevAndNext(
		Session session, CommerceTerm commerceTerm, Date displayDate,
		int status, OrderByComparator<CommerceTerm> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETERM_WHERE);

		boolean bindDisplayDate = false;

		if (displayDate == null) {
			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
		}
		else {
			bindDisplayDate = true;

			sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

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
			sb.append(CommerceTermModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindDisplayDate) {
			queryPos.add(new Timestamp(displayDate.getTime()));
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(commerceTerm)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTerm> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce terms where displayDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 */
	@Override
	public void removeByLtD_S(Date displayDate, int status) {
		for (CommerceTerm commerceTerm :
				findByLtD_S(
					displayDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceTerm);
		}
	}

	/**
	 * Returns the number of commerce terms where displayDate &lt; &#63; and status = &#63;.
	 *
	 * @param displayDate the display date
	 * @param status the status
	 * @return the number of matching commerce terms
	 */
	@Override
	public int countByLtD_S(Date displayDate, int status) {
		FinderPath finderPath = _finderPathWithPaginationCountByLtD_S;

		Object[] finderArgs = new Object[] {_getTime(displayDate), status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCETERM_WHERE);

			boolean bindDisplayDate = false;

			if (displayDate == null) {
				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_1);
			}
			else {
				bindDisplayDate = true;

				sb.append(_FINDER_COLUMN_LTD_S_DISPLAYDATE_2);
			}

			sb.append(_FINDER_COLUMN_LTD_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindDisplayDate) {
					queryPos.add(new Timestamp(displayDate.getTime()));
				}

				queryPos.add(status);

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

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_1 =
		"commerceTerm.displayDate IS NULL AND ";

	private static final String _FINDER_COLUMN_LTD_S_DISPLAYDATE_2 =
		"commerceTerm.displayDate < ? AND ";

	private static final String _FINDER_COLUMN_LTD_S_STATUS_2 =
		"commerceTerm.status = ?";

	private FinderPath _finderPathWithPaginationFindByLtE_S;
	private FinderPath _finderPathWithPaginationCountByLtE_S;

	/**
	 * Returns all the commerce terms where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByLtE_S(Date expirationDate, int status) {
		return findByLtE_S(
			expirationDate, status, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce terms where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @return the range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByLtE_S(
		Date expirationDate, int status, int start, int end) {

		return findByLtE_S(expirationDate, status, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce terms where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceTerm> orderByComparator) {

		return findByLtE_S(
			expirationDate, status, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce terms where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByLtE_S(
		Date expirationDate, int status, int start, int end,
		OrderByComparator<CommerceTerm> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByLtE_S;
		finderArgs = new Object[] {
			_getTime(expirationDate), status, start, end, orderByComparator
		};

		List<CommerceTerm> list = null;

		if (useFinderCache) {
			list = (List<CommerceTerm>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTerm commerceTerm : list) {
					if ((expirationDate.getTime() <=
							commerceTerm.getExpirationDate(
							).getTime()) ||
						(status != commerceTerm.getStatus())) {

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

			sb.append(_SQL_SELECT_COMMERCETERM_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2);
			}

			sb.append(_FINDER_COLUMN_LTE_S_STATUS_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTermModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
				}

				queryPos.add(status);

				list = (List<CommerceTerm>)QueryUtil.list(
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
	 * Returns the first commerce term in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm findByLtE_S_First(
			Date expirationDate, int status,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = fetchByLtE_S_First(
			expirationDate, status, orderByComparator);

		if (commerceTerm != null) {
			return commerceTerm;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate<");
		sb.append(expirationDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchTermException(sb.toString());
	}

	/**
	 * Returns the first commerce term in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm fetchByLtE_S_First(
		Date expirationDate, int status,
		OrderByComparator<CommerceTerm> orderByComparator) {

		List<CommerceTerm> list = findByLtE_S(
			expirationDate, status, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce term in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm findByLtE_S_Last(
			Date expirationDate, int status,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = fetchByLtE_S_Last(
			expirationDate, status, orderByComparator);

		if (commerceTerm != null) {
			return commerceTerm;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("expirationDate<");
		sb.append(expirationDate);

		sb.append(", status=");
		sb.append(status);

		sb.append("}");

		throw new NoSuchTermException(sb.toString());
	}

	/**
	 * Returns the last commerce term in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm fetchByLtE_S_Last(
		Date expirationDate, int status,
		OrderByComparator<CommerceTerm> orderByComparator) {

		int count = countByLtE_S(expirationDate, status);

		if (count == 0) {
			return null;
		}

		List<CommerceTerm> list = findByLtE_S(
			expirationDate, status, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce terms before and after the current commerce term in the ordered set where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param commerceTermId the primary key of the current commerce term
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	@Override
	public CommerceTerm[] findByLtE_S_PrevAndNext(
			long commerceTermId, Date expirationDate, int status,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = findByPrimaryKey(commerceTermId);

		Session session = null;

		try {
			session = openSession();

			CommerceTerm[] array = new CommerceTermImpl[3];

			array[0] = getByLtE_S_PrevAndNext(
				session, commerceTerm, expirationDate, status,
				orderByComparator, true);

			array[1] = commerceTerm;

			array[2] = getByLtE_S_PrevAndNext(
				session, commerceTerm, expirationDate, status,
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

	protected CommerceTerm getByLtE_S_PrevAndNext(
		Session session, CommerceTerm commerceTerm, Date expirationDate,
		int status, OrderByComparator<CommerceTerm> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETERM_WHERE);

		boolean bindExpirationDate = false;

		if (expirationDate == null) {
			sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1);
		}
		else {
			bindExpirationDate = true;

			sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2);
		}

		sb.append(_FINDER_COLUMN_LTE_S_STATUS_2);

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
			sb.append(CommerceTermModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindExpirationDate) {
			queryPos.add(new Timestamp(expirationDate.getTime()));
		}

		queryPos.add(status);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(commerceTerm)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTerm> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce terms where expirationDate &lt; &#63; and status = &#63; from the database.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 */
	@Override
	public void removeByLtE_S(Date expirationDate, int status) {
		for (CommerceTerm commerceTerm :
				findByLtE_S(
					expirationDate, status, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceTerm);
		}
	}

	/**
	 * Returns the number of commerce terms where expirationDate &lt; &#63; and status = &#63;.
	 *
	 * @param expirationDate the expiration date
	 * @param status the status
	 * @return the number of matching commerce terms
	 */
	@Override
	public int countByLtE_S(Date expirationDate, int status) {
		FinderPath finderPath = _finderPathWithPaginationCountByLtE_S;

		Object[] finderArgs = new Object[] {_getTime(expirationDate), status};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCETERM_WHERE);

			boolean bindExpirationDate = false;

			if (expirationDate == null) {
				sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1);
			}
			else {
				bindExpirationDate = true;

				sb.append(_FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2);
			}

			sb.append(_FINDER_COLUMN_LTE_S_STATUS_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindExpirationDate) {
					queryPos.add(new Timestamp(expirationDate.getTime()));
				}

				queryPos.add(status);

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

	private static final String _FINDER_COLUMN_LTE_S_EXPIRATIONDATE_1 =
		"commerceTerm.expirationDate IS NULL AND ";

	private static final String _FINDER_COLUMN_LTE_S_EXPIRATIONDATE_2 =
		"commerceTerm.expirationDate < ? AND ";

	private static final String _FINDER_COLUMN_LTE_S_STATUS_2 =
		"commerceTerm.status = ?";

	private FinderPath _finderPathWithPaginationFindByC_A_LikeType;
	private FinderPath _finderPathWithPaginationCountByC_A_LikeType;

	/**
	 * Returns all the commerce terms where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByC_A_LikeType(
		long companyId, boolean active, String type) {

		return findByC_A_LikeType(
			companyId, active, type, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce terms where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @return the range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end) {

		return findByC_A_LikeType(companyId, active, type, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce terms where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		OrderByComparator<CommerceTerm> orderByComparator) {

		return findByC_A_LikeType(
			companyId, active, type, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce terms where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce terms
	 */
	@Override
	public List<CommerceTerm> findByC_A_LikeType(
		long companyId, boolean active, String type, int start, int end,
		OrderByComparator<CommerceTerm> orderByComparator,
		boolean useFinderCache) {

		type = Objects.toString(type, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		finderPath = _finderPathWithPaginationFindByC_A_LikeType;
		finderArgs = new Object[] {
			companyId, active, type, start, end, orderByComparator
		};

		List<CommerceTerm> list = null;

		if (useFinderCache) {
			list = (List<CommerceTerm>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTerm commerceTerm : list) {
					if ((companyId != commerceTerm.getCompanyId()) ||
						(active != commerceTerm.isActive()) ||
						!StringUtil.wildcardMatches(
							commerceTerm.getType(), type, '_', '%', '\\',
							true)) {

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
					5 + (orderByComparator.getOrderByFields().length * 2));
			}
			else {
				sb = new StringBundler(5);
			}

			sb.append(_SQL_SELECT_COMMERCETERM_WHERE);

			sb.append(_FINDER_COLUMN_C_A_LIKETYPE_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_LIKETYPE_ACTIVE_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_LIKETYPE_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_C_A_LIKETYPE_TYPE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTermModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				if (bindType) {
					queryPos.add(type);
				}

				list = (List<CommerceTerm>)QueryUtil.list(
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
	 * Returns the first commerce term in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm findByC_A_LikeType_First(
			long companyId, boolean active, String type,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = fetchByC_A_LikeType_First(
			companyId, active, type, orderByComparator);

		if (commerceTerm != null) {
			return commerceTerm;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", typeLIKE");
		sb.append(type);

		sb.append("}");

		throw new NoSuchTermException(sb.toString());
	}

	/**
	 * Returns the first commerce term in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm fetchByC_A_LikeType_First(
		long companyId, boolean active, String type,
		OrderByComparator<CommerceTerm> orderByComparator) {

		List<CommerceTerm> list = findByC_A_LikeType(
			companyId, active, type, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce term in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm findByC_A_LikeType_Last(
			long companyId, boolean active, String type,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = fetchByC_A_LikeType_Last(
			companyId, active, type, orderByComparator);

		if (commerceTerm != null) {
			return commerceTerm;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", active=");
		sb.append(active);

		sb.append(", typeLIKE");
		sb.append(type);

		sb.append("}");

		throw new NoSuchTermException(sb.toString());
	}

	/**
	 * Returns the last commerce term in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm fetchByC_A_LikeType_Last(
		long companyId, boolean active, String type,
		OrderByComparator<CommerceTerm> orderByComparator) {

		int count = countByC_A_LikeType(companyId, active, type);

		if (count == 0) {
			return null;
		}

		List<CommerceTerm> list = findByC_A_LikeType(
			companyId, active, type, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce terms before and after the current commerce term in the ordered set where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param commerceTermId the primary key of the current commerce term
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	@Override
	public CommerceTerm[] findByC_A_LikeType_PrevAndNext(
			long commerceTermId, long companyId, boolean active, String type,
			OrderByComparator<CommerceTerm> orderByComparator)
		throws NoSuchTermException {

		type = Objects.toString(type, "");

		CommerceTerm commerceTerm = findByPrimaryKey(commerceTermId);

		Session session = null;

		try {
			session = openSession();

			CommerceTerm[] array = new CommerceTermImpl[3];

			array[0] = getByC_A_LikeType_PrevAndNext(
				session, commerceTerm, companyId, active, type,
				orderByComparator, true);

			array[1] = commerceTerm;

			array[2] = getByC_A_LikeType_PrevAndNext(
				session, commerceTerm, companyId, active, type,
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

	protected CommerceTerm getByC_A_LikeType_PrevAndNext(
		Session session, CommerceTerm commerceTerm, long companyId,
		boolean active, String type,
		OrderByComparator<CommerceTerm> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_COMMERCETERM_WHERE);

		sb.append(_FINDER_COLUMN_C_A_LIKETYPE_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_A_LIKETYPE_ACTIVE_2);

		boolean bindType = false;

		if (type.isEmpty()) {
			sb.append(_FINDER_COLUMN_C_A_LIKETYPE_TYPE_3);
		}
		else {
			bindType = true;

			sb.append(_FINDER_COLUMN_C_A_LIKETYPE_TYPE_2);
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
			sb.append(CommerceTermModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(active);

		if (bindType) {
			queryPos.add(type);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(commerceTerm)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTerm> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce terms where companyId = &#63; and active = &#63; and type LIKE &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 */
	@Override
	public void removeByC_A_LikeType(
		long companyId, boolean active, String type) {

		for (CommerceTerm commerceTerm :
				findByC_A_LikeType(
					companyId, active, type, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceTerm);
		}
	}

	/**
	 * Returns the number of commerce terms where companyId = &#63; and active = &#63; and type LIKE &#63;.
	 *
	 * @param companyId the company ID
	 * @param active the active
	 * @param type the type
	 * @return the number of matching commerce terms
	 */
	@Override
	public int countByC_A_LikeType(
		long companyId, boolean active, String type) {

		type = Objects.toString(type, "");

		FinderPath finderPath = _finderPathWithPaginationCountByC_A_LikeType;

		Object[] finderArgs = new Object[] {companyId, active, type};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCETERM_WHERE);

			sb.append(_FINDER_COLUMN_C_A_LIKETYPE_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_A_LIKETYPE_ACTIVE_2);

			boolean bindType = false;

			if (type.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_A_LIKETYPE_TYPE_3);
			}
			else {
				bindType = true;

				sb.append(_FINDER_COLUMN_C_A_LIKETYPE_TYPE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(active);

				if (bindType) {
					queryPos.add(type);
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

	private static final String _FINDER_COLUMN_C_A_LIKETYPE_COMPANYID_2 =
		"commerceTerm.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_A_LIKETYPE_ACTIVE_2 =
		"commerceTerm.active = ? AND ";

	private static final String _FINDER_COLUMN_C_A_LIKETYPE_TYPE_2 =
		"commerceTerm.type LIKE ?";

	private static final String _FINDER_COLUMN_C_A_LIKETYPE_TYPE_3 =
		"(commerceTerm.type IS NULL OR commerceTerm.type LIKE '')";

	private FinderPath _finderPathFetchByC_ERC;
	private FinderPath _finderPathCountByC_ERC;

	/**
	 * Returns the commerce term where companyId = &#63; and externalReferenceCode = &#63; or throws a <code>NoSuchTermException</code> if it could not be found.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce term
	 * @throws NoSuchTermException if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm findByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = fetchByC_ERC(
			companyId, externalReferenceCode);

		if (commerceTerm == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("companyId=");
			sb.append(companyId);

			sb.append(", externalReferenceCode=");
			sb.append(externalReferenceCode);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTermException(sb.toString());
		}

		return commerceTerm;
	}

	/**
	 * Returns the commerce term where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm fetchByC_ERC(
		long companyId, String externalReferenceCode) {

		return fetchByC_ERC(companyId, externalReferenceCode, true);
	}

	/**
	 * Returns the commerce term where companyId = &#63; and externalReferenceCode = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term, or <code>null</code> if a matching commerce term could not be found
	 */
	@Override
	public CommerceTerm fetchByC_ERC(
		long companyId, String externalReferenceCode, boolean useFinderCache) {

		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {companyId, externalReferenceCode};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_ERC, finderArgs);
		}

		if (result instanceof CommerceTerm) {
			CommerceTerm commerceTerm = (CommerceTerm)result;

			if ((companyId != commerceTerm.getCompanyId()) ||
				!Objects.equals(
					externalReferenceCode,
					commerceTerm.getExternalReferenceCode())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCETERM_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
				}

				List<CommerceTerm> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_ERC, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									companyId, externalReferenceCode
								};
							}

							_log.warn(
								"CommerceTermPersistenceImpl.fetchByC_ERC(long, String, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					CommerceTerm commerceTerm = list.get(0);

					result = commerceTerm;

					cacheResult(commerceTerm);
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
			return (CommerceTerm)result;
		}
	}

	/**
	 * Removes the commerce term where companyId = &#63; and externalReferenceCode = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the commerce term that was removed
	 */
	@Override
	public CommerceTerm removeByC_ERC(
			long companyId, String externalReferenceCode)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = findByC_ERC(
			companyId, externalReferenceCode);

		return remove(commerceTerm);
	}

	/**
	 * Returns the number of commerce terms where companyId = &#63; and externalReferenceCode = &#63;.
	 *
	 * @param companyId the company ID
	 * @param externalReferenceCode the external reference code
	 * @return the number of matching commerce terms
	 */
	@Override
	public int countByC_ERC(long companyId, String externalReferenceCode) {
		externalReferenceCode = Objects.toString(externalReferenceCode, "");

		FinderPath finderPath = _finderPathCountByC_ERC;

		Object[] finderArgs = new Object[] {companyId, externalReferenceCode};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCETERM_WHERE);

			sb.append(_FINDER_COLUMN_C_ERC_COMPANYID_2);

			boolean bindExternalReferenceCode = false;

			if (externalReferenceCode.isEmpty()) {
				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3);
			}
			else {
				bindExternalReferenceCode = true;

				sb.append(_FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				if (bindExternalReferenceCode) {
					queryPos.add(externalReferenceCode);
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

	private static final String _FINDER_COLUMN_C_ERC_COMPANYID_2 =
		"commerceTerm.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_2 =
		"commerceTerm.externalReferenceCode = ?";

	private static final String _FINDER_COLUMN_C_ERC_EXTERNALREFERENCECODE_3 =
		"(commerceTerm.externalReferenceCode IS NULL OR commerceTerm.externalReferenceCode = '')";

	public CommerceTermPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put("active", "active_");
		dbColumnNames.put("type", "type_");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceTerm.class);

		setModelImplClass(CommerceTermImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceTermTable.INSTANCE);
	}

	/**
	 * Caches the commerce term in the entity cache if it is enabled.
	 *
	 * @param commerceTerm the commerce term
	 */
	@Override
	public void cacheResult(CommerceTerm commerceTerm) {
		entityCache.putResult(
			CommerceTermImpl.class, commerceTerm.getPrimaryKey(), commerceTerm);

		finderCache.putResult(
			_finderPathFetchByC_ERC,
			new Object[] {
				commerceTerm.getCompanyId(),
				commerceTerm.getExternalReferenceCode()
			},
			commerceTerm);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce terms in the entity cache if it is enabled.
	 *
	 * @param commerceTerms the commerce terms
	 */
	@Override
	public void cacheResult(List<CommerceTerm> commerceTerms) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceTerms.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceTerm commerceTerm : commerceTerms) {
			if (entityCache.getResult(
					CommerceTermImpl.class, commerceTerm.getPrimaryKey()) ==
						null) {

				cacheResult(commerceTerm);
			}
		}
	}

	/**
	 * Clears the cache for all commerce terms.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTermImpl.class);

		finderCache.clearCache(CommerceTermImpl.class);
	}

	/**
	 * Clears the cache for the commerce term.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceTerm commerceTerm) {
		entityCache.removeResult(CommerceTermImpl.class, commerceTerm);
	}

	@Override
	public void clearCache(List<CommerceTerm> commerceTerms) {
		for (CommerceTerm commerceTerm : commerceTerms) {
			entityCache.removeResult(CommerceTermImpl.class, commerceTerm);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceTermImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(CommerceTermImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceTermModelImpl commerceTermModelImpl) {

		Object[] args = new Object[] {
			commerceTermModelImpl.getCompanyId(),
			commerceTermModelImpl.getExternalReferenceCode()
		};

		finderCache.putResult(_finderPathCountByC_ERC, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_ERC, args, commerceTermModelImpl);
	}

	/**
	 * Creates a new commerce term with the primary key. Does not add the commerce term to the database.
	 *
	 * @param commerceTermId the primary key for the new commerce term
	 * @return the new commerce term
	 */
	@Override
	public CommerceTerm create(long commerceTermId) {
		CommerceTerm commerceTerm = new CommerceTermImpl();

		commerceTerm.setNew(true);
		commerceTerm.setPrimaryKey(commerceTermId);

		commerceTerm.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceTerm;
	}

	/**
	 * Removes the commerce term with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTermId the primary key of the commerce term
	 * @return the commerce term that was removed
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	@Override
	public CommerceTerm remove(long commerceTermId) throws NoSuchTermException {
		return remove((Serializable)commerceTermId);
	}

	/**
	 * Removes the commerce term with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce term
	 * @return the commerce term that was removed
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	@Override
	public CommerceTerm remove(Serializable primaryKey)
		throws NoSuchTermException {

		Session session = null;

		try {
			session = openSession();

			CommerceTerm commerceTerm = (CommerceTerm)session.get(
				CommerceTermImpl.class, primaryKey);

			if (commerceTerm == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTermException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceTerm);
		}
		catch (NoSuchTermException noSuchEntityException) {
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
	protected CommerceTerm removeImpl(CommerceTerm commerceTerm) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTerm)) {
				commerceTerm = (CommerceTerm)session.get(
					CommerceTermImpl.class, commerceTerm.getPrimaryKeyObj());
			}

			if (commerceTerm != null) {
				session.delete(commerceTerm);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceTerm != null) {
			clearCache(commerceTerm);
		}

		return commerceTerm;
	}

	@Override
	public CommerceTerm updateImpl(CommerceTerm commerceTerm) {
		boolean isNew = commerceTerm.isNew();

		if (!(commerceTerm instanceof CommerceTermModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceTerm.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceTerm);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceTerm proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceTerm implementation " +
					commerceTerm.getClass());
		}

		CommerceTermModelImpl commerceTermModelImpl =
			(CommerceTermModelImpl)commerceTerm;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceTerm.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTerm.setCreateDate(date);
			}
			else {
				commerceTerm.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!commerceTermModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTerm.setModifiedDate(date);
			}
			else {
				commerceTerm.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceTerm);
			}
			else {
				commerceTerm = (CommerceTerm)session.merge(commerceTerm);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceTermImpl.class, commerceTermModelImpl, false, true);

		cacheUniqueFindersCache(commerceTermModelImpl);

		if (isNew) {
			commerceTerm.setNew(false);
		}

		commerceTerm.resetOriginalValues();

		return commerceTerm;
	}

	/**
	 * Returns the commerce term with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce term
	 * @return the commerce term
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	@Override
	public CommerceTerm findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTermException {

		CommerceTerm commerceTerm = fetchByPrimaryKey(primaryKey);

		if (commerceTerm == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTermException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceTerm;
	}

	/**
	 * Returns the commerce term with the primary key or throws a <code>NoSuchTermException</code> if it could not be found.
	 *
	 * @param commerceTermId the primary key of the commerce term
	 * @return the commerce term
	 * @throws NoSuchTermException if a commerce term with the primary key could not be found
	 */
	@Override
	public CommerceTerm findByPrimaryKey(long commerceTermId)
		throws NoSuchTermException {

		return findByPrimaryKey((Serializable)commerceTermId);
	}

	/**
	 * Returns the commerce term with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTermId the primary key of the commerce term
	 * @return the commerce term, or <code>null</code> if a commerce term with the primary key could not be found
	 */
	@Override
	public CommerceTerm fetchByPrimaryKey(long commerceTermId) {
		return fetchByPrimaryKey((Serializable)commerceTermId);
	}

	/**
	 * Returns all the commerce terms.
	 *
	 * @return the commerce terms
	 */
	@Override
	public List<CommerceTerm> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce terms.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @return the range of commerce terms
	 */
	@Override
	public List<CommerceTerm> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce terms.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce terms
	 */
	@Override
	public List<CommerceTerm> findAll(
		int start, int end, OrderByComparator<CommerceTerm> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce terms.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce terms
	 * @param end the upper bound of the range of commerce terms (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce terms
	 */
	@Override
	public List<CommerceTerm> findAll(
		int start, int end, OrderByComparator<CommerceTerm> orderByComparator,
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

		List<CommerceTerm> list = null;

		if (useFinderCache) {
			list = (List<CommerceTerm>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCETERM);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETERM;

				sql = sql.concat(CommerceTermModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceTerm>)QueryUtil.list(
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
	 * Removes all the commerce terms from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceTerm commerceTerm : findAll()) {
			remove(commerceTerm);
		}
	}

	/**
	 * Returns the number of commerce terms.
	 *
	 * @return the number of commerce terms
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_COMMERCETERM);

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
		return "commerceTermId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCETERM;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceTermModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce term persistence.
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

		_finderPathWithPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_"}, true);

		_finderPathWithoutPaginationFindByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "active_"}, true);

		_finderPathCountByC_A = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_A",
			new String[] {Long.class.getName(), Boolean.class.getName()},
			new String[] {"companyId", "active_"}, false);

		_finderPathWithPaginationFindByC_LikeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_LikeType",
			new String[] {
				Long.class.getName(), String.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "type_"}, true);

		_finderPathWithPaginationCountByC_LikeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_LikeType",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "type_"}, false);

		_finderPathWithPaginationFindByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtD_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"displayDate", "status"}, true);

		_finderPathWithPaginationCountByLtD_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtD_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"displayDate", "status"}, false);

		_finderPathWithPaginationFindByLtE_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByLtE_S",
			new String[] {
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"expirationDate", "status"}, true);

		_finderPathWithPaginationCountByLtE_S = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByLtE_S",
			new String[] {Date.class.getName(), Integer.class.getName()},
			new String[] {"expirationDate", "status"}, false);

		_finderPathWithPaginationFindByC_A_LikeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_A_LikeType",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "active_", "type_"}, true);

		_finderPathWithPaginationCountByC_A_LikeType = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "countByC_A_LikeType",
			new String[] {
				Long.class.getName(), Boolean.class.getName(),
				String.class.getName()
			},
			new String[] {"companyId", "active_", "type_"}, false);

		_finderPathFetchByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, true);

		_finderPathCountByC_ERC = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_ERC",
			new String[] {Long.class.getName(), String.class.getName()},
			new String[] {"companyId", "externalReferenceCode"}, false);

		_setCommerceTermUtilPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		_setCommerceTermUtilPersistence(null);

		entityCache.removeCache(CommerceTermImpl.class.getName());
	}

	private void _setCommerceTermUtilPersistence(
		CommerceTermPersistence commerceTermPersistence) {

		try {
			Field field = CommerceTermUtil.class.getDeclaredField(
				"_persistence");

			field.setAccessible(true);

			field.set(null, commerceTermPersistence);
		}
		catch (ReflectiveOperationException reflectiveOperationException) {
			throw new RuntimeException(reflectiveOperationException);
		}
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

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_COMMERCETERM =
		"SELECT commerceTerm FROM CommerceTerm commerceTerm";

	private static final String _SQL_SELECT_COMMERCETERM_WHERE =
		"SELECT commerceTerm FROM CommerceTerm commerceTerm WHERE ";

	private static final String _SQL_COUNT_COMMERCETERM =
		"SELECT COUNT(commerceTerm) FROM CommerceTerm commerceTerm";

	private static final String _SQL_COUNT_COMMERCETERM_WHERE =
		"SELECT COUNT(commerceTerm) FROM CommerceTerm commerceTerm WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "commerceTerm.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceTerm exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceTerm exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTermPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"active", "type"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CommerceTermModelArgumentsResolver
		_commerceTermModelArgumentsResolver;

}