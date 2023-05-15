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

import com.liferay.commerce.term.exception.NoSuchTermEntryRelException;
import com.liferay.commerce.term.model.CommerceTermEntryRel;
import com.liferay.commerce.term.model.CommerceTermEntryRelTable;
import com.liferay.commerce.term.model.impl.CommerceTermEntryRelImpl;
import com.liferay.commerce.term.model.impl.CommerceTermEntryRelModelImpl;
import com.liferay.commerce.term.service.persistence.CommerceTermEntryRelPersistence;
import com.liferay.commerce.term.service.persistence.CommerceTermEntryRelUtil;
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
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.PropsKeys;
import com.liferay.portal.kernel.util.PropsUtil;
import com.liferay.portal.kernel.util.ProxyUtil;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the commerce term entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
@Component(service = CommerceTermEntryRelPersistence.class)
public class CommerceTermEntryRelPersistenceImpl
	extends BasePersistenceImpl<CommerceTermEntryRel>
	implements CommerceTermEntryRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceTermEntryRelUtil</code> to access the commerce term entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceTermEntryRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceTermEntryId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceTermEntryId;
	private FinderPath _finderPathCountByCommerceTermEntryId;

	/**
	 * Returns all the commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching commerce term entry rels
	 */
	@Override
	public List<CommerceTermEntryRel> findByCommerceTermEntryId(
		long commerceTermEntryId) {

		return findByCommerceTermEntryId(
			commerceTermEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @return the range of matching commerce term entry rels
	 */
	@Override
	public List<CommerceTermEntryRel> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end) {

		return findByCommerceTermEntryId(commerceTermEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entry rels
	 */
	@Override
	public List<CommerceTermEntryRel> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		return findByCommerceTermEntryId(
			commerceTermEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entry rels
	 */
	@Override
	public List<CommerceTermEntryRel> findByCommerceTermEntryId(
		long commerceTermEntryId, int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceTermEntryId;
				finderArgs = new Object[] {commerceTermEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceTermEntryId;
			finderArgs = new Object[] {
				commerceTermEntryId, start, end, orderByComparator
			};
		}

		List<CommerceTermEntryRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceTermEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTermEntryRel commerceTermEntryRel : list) {
					if (commerceTermEntryId !=
							commerceTermEntryRel.getCommerceTermEntryId()) {

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

			sb.append(_SQL_SELECT_COMMERCETERMENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCETERMENTRYID_COMMERCETERMENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTermEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceTermEntryId);

				list = (List<CommerceTermEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	@Override
	public CommerceTermEntryRel findByCommerceTermEntryId_First(
			long commerceTermEntryId,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws NoSuchTermEntryRelException {

		CommerceTermEntryRel commerceTermEntryRel =
			fetchByCommerceTermEntryId_First(
				commerceTermEntryId, orderByComparator);

		if (commerceTermEntryRel != null) {
			return commerceTermEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceTermEntryId=");
		sb.append(commerceTermEntryId);

		sb.append("}");

		throw new NoSuchTermEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	@Override
	public CommerceTermEntryRel fetchByCommerceTermEntryId_First(
		long commerceTermEntryId,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		List<CommerceTermEntryRel> list = findByCommerceTermEntryId(
			commerceTermEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	@Override
	public CommerceTermEntryRel findByCommerceTermEntryId_Last(
			long commerceTermEntryId,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws NoSuchTermEntryRelException {

		CommerceTermEntryRel commerceTermEntryRel =
			fetchByCommerceTermEntryId_Last(
				commerceTermEntryId, orderByComparator);

		if (commerceTermEntryRel != null) {
			return commerceTermEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceTermEntryId=");
		sb.append(commerceTermEntryId);

		sb.append("}");

		throw new NoSuchTermEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	@Override
	public CommerceTermEntryRel fetchByCommerceTermEntryId_Last(
		long commerceTermEntryId,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		int count = countByCommerceTermEntryId(commerceTermEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceTermEntryRel> list = findByCommerceTermEntryId(
			commerceTermEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce term entry rels before and after the current commerce term entry rel in the ordered set where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryRelId the primary key of the current commerce term entry rel
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	@Override
	public CommerceTermEntryRel[] findByCommerceTermEntryId_PrevAndNext(
			long commerceTermEntryRelId, long commerceTermEntryId,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws NoSuchTermEntryRelException {

		CommerceTermEntryRel commerceTermEntryRel = findByPrimaryKey(
			commerceTermEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceTermEntryRel[] array = new CommerceTermEntryRelImpl[3];

			array[0] = getByCommerceTermEntryId_PrevAndNext(
				session, commerceTermEntryRel, commerceTermEntryId,
				orderByComparator, true);

			array[1] = commerceTermEntryRel;

			array[2] = getByCommerceTermEntryId_PrevAndNext(
				session, commerceTermEntryRel, commerceTermEntryId,
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

	protected CommerceTermEntryRel getByCommerceTermEntryId_PrevAndNext(
		Session session, CommerceTermEntryRel commerceTermEntryRel,
		long commerceTermEntryId,
		OrderByComparator<CommerceTermEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETERMENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCETERMENTRYID_COMMERCETERMENTRYID_2);

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
			sb.append(CommerceTermEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceTermEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceTermEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTermEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce term entry rels where commerceTermEntryId = &#63; from the database.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 */
	@Override
	public void removeByCommerceTermEntryId(long commerceTermEntryId) {
		for (CommerceTermEntryRel commerceTermEntryRel :
				findByCommerceTermEntryId(
					commerceTermEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceTermEntryRel);
		}
	}

	/**
	 * Returns the number of commerce term entry rels where commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the number of matching commerce term entry rels
	 */
	@Override
	public int countByCommerceTermEntryId(long commerceTermEntryId) {
		FinderPath finderPath = _finderPathCountByCommerceTermEntryId;

		Object[] finderArgs = new Object[] {commerceTermEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCETERMENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCETERMENTRYID_COMMERCETERMENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceTermEntryId);

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
		_FINDER_COLUMN_COMMERCETERMENTRYID_COMMERCETERMENTRYID_2 =
			"commerceTermEntryRel.commerceTermEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching commerce term entry rels
	 */
	@Override
	public List<CommerceTermEntryRel> findByC_C(
		long classNameId, long commerceTermEntryId) {

		return findByC_C(
			classNameId, commerceTermEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @return the range of matching commerce term entry rels
	 */
	@Override
	public List<CommerceTermEntryRel> findByC_C(
		long classNameId, long commerceTermEntryId, int start, int end) {

		return findByC_C(classNameId, commerceTermEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce term entry rels
	 */
	@Override
	public List<CommerceTermEntryRel> findByC_C(
		long classNameId, long commerceTermEntryId, int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		return findByC_C(
			classNameId, commerceTermEntryId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce term entry rels
	 */
	@Override
	public List<CommerceTermEntryRel> findByC_C(
		long classNameId, long commerceTermEntryId, int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, commerceTermEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, commerceTermEntryId, start, end, orderByComparator
			};
		}

		List<CommerceTermEntryRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceTermEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceTermEntryRel commerceTermEntryRel : list) {
					if ((classNameId !=
							commerceTermEntryRel.getClassNameId()) ||
						(commerceTermEntryId !=
							commerceTermEntryRel.getCommerceTermEntryId())) {

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

			sb.append(_SQL_SELECT_COMMERCETERMENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCETERMENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceTermEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(commerceTermEntryId);

				list = (List<CommerceTermEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	@Override
	public CommerceTermEntryRel findByC_C_First(
			long classNameId, long commerceTermEntryId,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws NoSuchTermEntryRelException {

		CommerceTermEntryRel commerceTermEntryRel = fetchByC_C_First(
			classNameId, commerceTermEntryId, orderByComparator);

		if (commerceTermEntryRel != null) {
			return commerceTermEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", commerceTermEntryId=");
		sb.append(commerceTermEntryId);

		sb.append("}");

		throw new NoSuchTermEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	@Override
	public CommerceTermEntryRel fetchByC_C_First(
		long classNameId, long commerceTermEntryId,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		List<CommerceTermEntryRel> list = findByC_C(
			classNameId, commerceTermEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	@Override
	public CommerceTermEntryRel findByC_C_Last(
			long classNameId, long commerceTermEntryId,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws NoSuchTermEntryRelException {

		CommerceTermEntryRel commerceTermEntryRel = fetchByC_C_Last(
			classNameId, commerceTermEntryId, orderByComparator);

		if (commerceTermEntryRel != null) {
			return commerceTermEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", commerceTermEntryId=");
		sb.append(commerceTermEntryId);

		sb.append("}");

		throw new NoSuchTermEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	@Override
	public CommerceTermEntryRel fetchByC_C_Last(
		long classNameId, long commerceTermEntryId,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		int count = countByC_C(classNameId, commerceTermEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceTermEntryRel> list = findByC_C(
			classNameId, commerceTermEntryId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce term entry rels before and after the current commerce term entry rel in the ordered set where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param commerceTermEntryRelId the primary key of the current commerce term entry rel
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	@Override
	public CommerceTermEntryRel[] findByC_C_PrevAndNext(
			long commerceTermEntryRelId, long classNameId,
			long commerceTermEntryId,
			OrderByComparator<CommerceTermEntryRel> orderByComparator)
		throws NoSuchTermEntryRelException {

		CommerceTermEntryRel commerceTermEntryRel = findByPrimaryKey(
			commerceTermEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceTermEntryRel[] array = new CommerceTermEntryRelImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, commerceTermEntryRel, classNameId, commerceTermEntryId,
				orderByComparator, true);

			array[1] = commerceTermEntryRel;

			array[2] = getByC_C_PrevAndNext(
				session, commerceTermEntryRel, classNameId, commerceTermEntryId,
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

	protected CommerceTermEntryRel getByC_C_PrevAndNext(
		Session session, CommerceTermEntryRel commerceTermEntryRel,
		long classNameId, long commerceTermEntryId,
		OrderByComparator<CommerceTermEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCETERMENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_COMMERCETERMENTRYID_2);

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
			sb.append(CommerceTermEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(commerceTermEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceTermEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceTermEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 */
	@Override
	public void removeByC_C(long classNameId, long commerceTermEntryId) {
		for (CommerceTermEntryRel commerceTermEntryRel :
				findByC_C(
					classNameId, commerceTermEntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceTermEntryRel);
		}
	}

	/**
	 * Returns the number of commerce term entry rels where classNameId = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the number of matching commerce term entry rels
	 */
	@Override
	public int countByC_C(long classNameId, long commerceTermEntryId) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, commerceTermEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCETERMENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCETERMENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(commerceTermEntryId);

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
		"commerceTermEntryRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_COMMERCETERMENTRYID_2 =
		"commerceTermEntryRel.commerceTermEntryId = ?";

	private FinderPath _finderPathFetchByC_C_C;
	private FinderPath _finderPathCountByC_C_C;

	/**
	 * Returns the commerce term entry rel where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63; or throws a <code>NoSuchTermEntryRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a matching commerce term entry rel could not be found
	 */
	@Override
	public CommerceTermEntryRel findByC_C_C(
			long classNameId, long classPK, long commerceTermEntryId)
		throws NoSuchTermEntryRelException {

		CommerceTermEntryRel commerceTermEntryRel = fetchByC_C_C(
			classNameId, classPK, commerceTermEntryId);

		if (commerceTermEntryRel == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", commerceTermEntryId=");
			sb.append(commerceTermEntryId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTermEntryRelException(sb.toString());
		}

		return commerceTermEntryRel;
	}

	/**
	 * Returns the commerce term entry rel where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	@Override
	public CommerceTermEntryRel fetchByC_C_C(
		long classNameId, long classPK, long commerceTermEntryId) {

		return fetchByC_C_C(classNameId, classPK, commerceTermEntryId, true);
	}

	/**
	 * Returns the commerce term entry rel where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce term entry rel, or <code>null</code> if a matching commerce term entry rel could not be found
	 */
	@Override
	public CommerceTermEntryRel fetchByC_C_C(
		long classNameId, long classPK, long commerceTermEntryId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				classNameId, classPK, commerceTermEntryId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C_C, finderArgs, this);
		}

		if (result instanceof CommerceTermEntryRel) {
			CommerceTermEntryRel commerceTermEntryRel =
				(CommerceTermEntryRel)result;

			if ((classNameId != commerceTermEntryRel.getClassNameId()) ||
				(classPK != commerceTermEntryRel.getClassPK()) ||
				(commerceTermEntryId !=
					commerceTermEntryRel.getCommerceTermEntryId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_COMMERCETERMENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCETERMENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceTermEntryId);

				List<CommerceTermEntryRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_C, finderArgs, list);
					}
				}
				else {
					CommerceTermEntryRel commerceTermEntryRel = list.get(0);

					result = commerceTermEntryRel;

					cacheResult(commerceTermEntryRel);
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
			return (CommerceTermEntryRel)result;
		}
	}

	/**
	 * Removes the commerce term entry rel where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the commerce term entry rel that was removed
	 */
	@Override
	public CommerceTermEntryRel removeByC_C_C(
			long classNameId, long classPK, long commerceTermEntryId)
		throws NoSuchTermEntryRelException {

		CommerceTermEntryRel commerceTermEntryRel = findByC_C_C(
			classNameId, classPK, commerceTermEntryId);

		return remove(commerceTermEntryRel);
	}

	/**
	 * Returns the number of commerce term entry rels where classNameId = &#63; and classPK = &#63; and commerceTermEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceTermEntryId the commerce term entry ID
	 * @return the number of matching commerce term entry rels
	 */
	@Override
	public int countByC_C_C(
		long classNameId, long classPK, long commerceTermEntryId) {

		FinderPath finderPath = _finderPathCountByC_C_C;

		Object[] finderArgs = new Object[] {
			classNameId, classPK, commerceTermEntryId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCETERMENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCETERMENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceTermEntryId);

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
		"commerceTermEntryRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"commerceTermEntryRel.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_COMMERCETERMENTRYID_2 =
		"commerceTermEntryRel.commerceTermEntryId = ?";

	public CommerceTermEntryRelPersistenceImpl() {
		setModelClass(CommerceTermEntryRel.class);

		setModelImplClass(CommerceTermEntryRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceTermEntryRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce term entry rel in the entity cache if it is enabled.
	 *
	 * @param commerceTermEntryRel the commerce term entry rel
	 */
	@Override
	public void cacheResult(CommerceTermEntryRel commerceTermEntryRel) {
		entityCache.putResult(
			CommerceTermEntryRelImpl.class,
			commerceTermEntryRel.getPrimaryKey(), commerceTermEntryRel);

		finderCache.putResult(
			_finderPathFetchByC_C_C,
			new Object[] {
				commerceTermEntryRel.getClassNameId(),
				commerceTermEntryRel.getClassPK(),
				commerceTermEntryRel.getCommerceTermEntryId()
			},
			commerceTermEntryRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce term entry rels in the entity cache if it is enabled.
	 *
	 * @param commerceTermEntryRels the commerce term entry rels
	 */
	@Override
	public void cacheResult(List<CommerceTermEntryRel> commerceTermEntryRels) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceTermEntryRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceTermEntryRel commerceTermEntryRel :
				commerceTermEntryRels) {

			if (entityCache.getResult(
					CommerceTermEntryRelImpl.class,
					commerceTermEntryRel.getPrimaryKey()) == null) {

				cacheResult(commerceTermEntryRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce term entry rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceTermEntryRelImpl.class);

		finderCache.clearCache(CommerceTermEntryRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce term entry rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceTermEntryRel commerceTermEntryRel) {
		entityCache.removeResult(
			CommerceTermEntryRelImpl.class, commerceTermEntryRel);
	}

	@Override
	public void clearCache(List<CommerceTermEntryRel> commerceTermEntryRels) {
		for (CommerceTermEntryRel commerceTermEntryRel :
				commerceTermEntryRels) {

			entityCache.removeResult(
				CommerceTermEntryRelImpl.class, commerceTermEntryRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceTermEntryRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceTermEntryRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceTermEntryRelModelImpl commerceTermEntryRelModelImpl) {

		Object[] args = new Object[] {
			commerceTermEntryRelModelImpl.getClassNameId(),
			commerceTermEntryRelModelImpl.getClassPK(),
			commerceTermEntryRelModelImpl.getCommerceTermEntryId()
		};

		finderCache.putResult(_finderPathCountByC_C_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C_C, args, commerceTermEntryRelModelImpl);
	}

	/**
	 * Creates a new commerce term entry rel with the primary key. Does not add the commerce term entry rel to the database.
	 *
	 * @param commerceTermEntryRelId the primary key for the new commerce term entry rel
	 * @return the new commerce term entry rel
	 */
	@Override
	public CommerceTermEntryRel create(long commerceTermEntryRelId) {
		CommerceTermEntryRel commerceTermEntryRel =
			new CommerceTermEntryRelImpl();

		commerceTermEntryRel.setNew(true);
		commerceTermEntryRel.setPrimaryKey(commerceTermEntryRelId);

		commerceTermEntryRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceTermEntryRel;
	}

	/**
	 * Removes the commerce term entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceTermEntryRelId the primary key of the commerce term entry rel
	 * @return the commerce term entry rel that was removed
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	@Override
	public CommerceTermEntryRel remove(long commerceTermEntryRelId)
		throws NoSuchTermEntryRelException {

		return remove((Serializable)commerceTermEntryRelId);
	}

	/**
	 * Removes the commerce term entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce term entry rel
	 * @return the commerce term entry rel that was removed
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	@Override
	public CommerceTermEntryRel remove(Serializable primaryKey)
		throws NoSuchTermEntryRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceTermEntryRel commerceTermEntryRel =
				(CommerceTermEntryRel)session.get(
					CommerceTermEntryRelImpl.class, primaryKey);

			if (commerceTermEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTermEntryRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceTermEntryRel);
		}
		catch (NoSuchTermEntryRelException noSuchEntityException) {
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
	protected CommerceTermEntryRel removeImpl(
		CommerceTermEntryRel commerceTermEntryRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceTermEntryRel)) {
				commerceTermEntryRel = (CommerceTermEntryRel)session.get(
					CommerceTermEntryRelImpl.class,
					commerceTermEntryRel.getPrimaryKeyObj());
			}

			if (commerceTermEntryRel != null) {
				session.delete(commerceTermEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceTermEntryRel != null) {
			clearCache(commerceTermEntryRel);
		}

		return commerceTermEntryRel;
	}

	@Override
	public CommerceTermEntryRel updateImpl(
		CommerceTermEntryRel commerceTermEntryRel) {

		boolean isNew = commerceTermEntryRel.isNew();

		if (!(commerceTermEntryRel instanceof CommerceTermEntryRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceTermEntryRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceTermEntryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceTermEntryRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceTermEntryRel implementation " +
					commerceTermEntryRel.getClass());
		}

		CommerceTermEntryRelModelImpl commerceTermEntryRelModelImpl =
			(CommerceTermEntryRelModelImpl)commerceTermEntryRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceTermEntryRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceTermEntryRel.setCreateDate(date);
			}
			else {
				commerceTermEntryRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceTermEntryRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceTermEntryRel.setModifiedDate(date);
			}
			else {
				commerceTermEntryRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceTermEntryRel);
			}
			else {
				commerceTermEntryRel = (CommerceTermEntryRel)session.merge(
					commerceTermEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceTermEntryRelImpl.class, commerceTermEntryRelModelImpl,
			false, true);

		cacheUniqueFindersCache(commerceTermEntryRelModelImpl);

		if (isNew) {
			commerceTermEntryRel.setNew(false);
		}

		commerceTermEntryRel.resetOriginalValues();

		return commerceTermEntryRel;
	}

	/**
	 * Returns the commerce term entry rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce term entry rel
	 * @return the commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	@Override
	public CommerceTermEntryRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTermEntryRelException {

		CommerceTermEntryRel commerceTermEntryRel = fetchByPrimaryKey(
			primaryKey);

		if (commerceTermEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTermEntryRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceTermEntryRel;
	}

	/**
	 * Returns the commerce term entry rel with the primary key or throws a <code>NoSuchTermEntryRelException</code> if it could not be found.
	 *
	 * @param commerceTermEntryRelId the primary key of the commerce term entry rel
	 * @return the commerce term entry rel
	 * @throws NoSuchTermEntryRelException if a commerce term entry rel with the primary key could not be found
	 */
	@Override
	public CommerceTermEntryRel findByPrimaryKey(long commerceTermEntryRelId)
		throws NoSuchTermEntryRelException {

		return findByPrimaryKey((Serializable)commerceTermEntryRelId);
	}

	/**
	 * Returns the commerce term entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceTermEntryRelId the primary key of the commerce term entry rel
	 * @return the commerce term entry rel, or <code>null</code> if a commerce term entry rel with the primary key could not be found
	 */
	@Override
	public CommerceTermEntryRel fetchByPrimaryKey(long commerceTermEntryRelId) {
		return fetchByPrimaryKey((Serializable)commerceTermEntryRelId);
	}

	/**
	 * Returns all the commerce term entry rels.
	 *
	 * @return the commerce term entry rels
	 */
	@Override
	public List<CommerceTermEntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce term entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @return the range of commerce term entry rels
	 */
	@Override
	public List<CommerceTermEntryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce term entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce term entry rels
	 */
	@Override
	public List<CommerceTermEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce term entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceTermEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce term entry rels
	 * @param end the upper bound of the range of commerce term entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce term entry rels
	 */
	@Override
	public List<CommerceTermEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceTermEntryRel> orderByComparator,
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

		List<CommerceTermEntryRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceTermEntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCETERMENTRYREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCETERMENTRYREL;

				sql = sql.concat(CommerceTermEntryRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceTermEntryRel>)QueryUtil.list(
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
	 * Removes all the commerce term entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceTermEntryRel commerceTermEntryRel : findAll()) {
			remove(commerceTermEntryRel);
		}
	}

	/**
	 * Returns the number of commerce term entry rels.
	 *
	 * @return the number of commerce term entry rels
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
					_SQL_COUNT_COMMERCETERMENTRYREL);

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
	protected EntityCache getEntityCache() {
		return entityCache;
	}

	@Override
	protected String getPKDBName() {
		return "commerceTermEntryRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCETERMENTRYREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceTermEntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce term entry rel persistence.
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

		_finderPathWithPaginationFindByCommerceTermEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceTermEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceTermEntryId"}, true);

		_finderPathWithoutPaginationFindByCommerceTermEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceTermEntryId", new String[] {Long.class.getName()},
			new String[] {"commerceTermEntryId"}, true);

		_finderPathCountByCommerceTermEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceTermEntryId", new String[] {Long.class.getName()},
			new String[] {"commerceTermEntryId"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "commerceTermEntryId"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "commerceTermEntryId"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "commerceTermEntryId"}, false);

		_finderPathFetchByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "commerceTermEntryId"},
			true);

		_finderPathCountByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "commerceTermEntryId"},
			false);

		CommerceTermEntryRelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceTermEntryRelUtil.setPersistence(null);

		entityCache.removeCache(CommerceTermEntryRelImpl.class.getName());
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

	private static final String _SQL_SELECT_COMMERCETERMENTRYREL =
		"SELECT commerceTermEntryRel FROM CommerceTermEntryRel commerceTermEntryRel";

	private static final String _SQL_SELECT_COMMERCETERMENTRYREL_WHERE =
		"SELECT commerceTermEntryRel FROM CommerceTermEntryRel commerceTermEntryRel WHERE ";

	private static final String _SQL_COUNT_COMMERCETERMENTRYREL =
		"SELECT COUNT(commerceTermEntryRel) FROM CommerceTermEntryRel commerceTermEntryRel";

	private static final String _SQL_COUNT_COMMERCETERMENTRYREL_WHERE =
		"SELECT COUNT(commerceTermEntryRel) FROM CommerceTermEntryRel commerceTermEntryRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceTermEntryRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceTermEntryRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceTermEntryRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceTermEntryRelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}