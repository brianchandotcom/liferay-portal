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

package com.liferay.commerce.order.rule.service.persistence.impl;

import com.liferay.commerce.order.rule.exception.NoSuchOrderRuleEntryRelException;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRel;
import com.liferay.commerce.order.rule.model.CommerceOrderRuleEntryRelTable;
import com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryRelImpl;
import com.liferay.commerce.order.rule.model.impl.CommerceOrderRuleEntryRelModelImpl;
import com.liferay.commerce.order.rule.service.persistence.CommerceOrderRuleEntryRelPersistence;
import com.liferay.commerce.order.rule.service.persistence.impl.constants.CommercePersistenceConstants;
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
 * The persistence implementation for the commerce order rule entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
@Component(
	service = {
		CommerceOrderRuleEntryRelPersistence.class, BasePersistence.class
	}
)
public class CommerceOrderRuleEntryRelPersistenceImpl
	extends BasePersistenceImpl<CommerceOrderRuleEntryRel>
	implements CommerceOrderRuleEntryRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceOrderRuleEntryRelUtil</code> to access the commerce order rule entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceOrderRuleEntryRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceOrderRuleEntryId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceOrderRuleEntryId;
	private FinderPath _finderPathCountByCommerceOrderRuleEntryId;

	/**
	 * Returns all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the matching commerce order rule entry rels
	 */
	@Override
	public List<CommerceOrderRuleEntryRel> findByCommerceOrderRuleEntryId(
		long commerceOrderRuleEntryId) {

		return findByCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @return the range of matching commerce order rule entry rels
	 */
	@Override
	public List<CommerceOrderRuleEntryRel> findByCommerceOrderRuleEntryId(
		long commerceOrderRuleEntryId, int start, int end) {

		return findByCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entry rels
	 */
	@Override
	public List<CommerceOrderRuleEntryRel> findByCommerceOrderRuleEntryId(
		long commerceOrderRuleEntryId, int start, int end,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		return findByCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entry rels
	 */
	@Override
	public List<CommerceOrderRuleEntryRel> findByCommerceOrderRuleEntryId(
		long commerceOrderRuleEntryId, int start, int end,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceOrderRuleEntryId;
				finderArgs = new Object[] {commerceOrderRuleEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceOrderRuleEntryId;
			finderArgs = new Object[] {
				commerceOrderRuleEntryId, start, end, orderByComparator
			};
		}

		List<CommerceOrderRuleEntryRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderRuleEntryRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderRuleEntryRel commerceOrderRuleEntryRel :
						list) {

					if (commerceOrderRuleEntryId !=
							commerceOrderRuleEntryRel.
								getCommerceOrderRuleEntryId()) {

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

			sb.append(_SQL_SELECT_COMMERCEORDERRULEENTRYREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEORDERRULEENTRYID_COMMERCEORDERRULEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceOrderRuleEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderRuleEntryId);

				list = (List<CommerceOrderRuleEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel findByCommerceOrderRuleEntryId_First(
			long commerceOrderRuleEntryId,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator)
		throws NoSuchOrderRuleEntryRelException {

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel =
			fetchByCommerceOrderRuleEntryId_First(
				commerceOrderRuleEntryId, orderByComparator);

		if (commerceOrderRuleEntryRel != null) {
			return commerceOrderRuleEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderRuleEntryId=");
		sb.append(commerceOrderRuleEntryId);

		sb.append("}");

		throw new NoSuchOrderRuleEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel fetchByCommerceOrderRuleEntryId_First(
		long commerceOrderRuleEntryId,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		List<CommerceOrderRuleEntryRel> list = findByCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel findByCommerceOrderRuleEntryId_Last(
			long commerceOrderRuleEntryId,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator)
		throws NoSuchOrderRuleEntryRelException {

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel =
			fetchByCommerceOrderRuleEntryId_Last(
				commerceOrderRuleEntryId, orderByComparator);

		if (commerceOrderRuleEntryRel != null) {
			return commerceOrderRuleEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceOrderRuleEntryId=");
		sb.append(commerceOrderRuleEntryId);

		sb.append("}");

		throw new NoSuchOrderRuleEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel fetchByCommerceOrderRuleEntryId_Last(
		long commerceOrderRuleEntryId,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		int count = countByCommerceOrderRuleEntryId(commerceOrderRuleEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderRuleEntryRel> list = findByCommerceOrderRuleEntryId(
			commerceOrderRuleEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce order rule entry rels before and after the current commerce order rule entry rel in the ordered set where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the current commerce order rule entry rel
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel[]
			findByCommerceOrderRuleEntryId_PrevAndNext(
				long commerceOrderRuleEntryRelId, long commerceOrderRuleEntryId,
				OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator)
		throws NoSuchOrderRuleEntryRelException {

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel = findByPrimaryKey(
			commerceOrderRuleEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderRuleEntryRel[] array =
				new CommerceOrderRuleEntryRelImpl[3];

			array[0] = getByCommerceOrderRuleEntryId_PrevAndNext(
				session, commerceOrderRuleEntryRel, commerceOrderRuleEntryId,
				orderByComparator, true);

			array[1] = commerceOrderRuleEntryRel;

			array[2] = getByCommerceOrderRuleEntryId_PrevAndNext(
				session, commerceOrderRuleEntryRel, commerceOrderRuleEntryId,
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

	protected CommerceOrderRuleEntryRel
		getByCommerceOrderRuleEntryId_PrevAndNext(
			Session session,
			CommerceOrderRuleEntryRel commerceOrderRuleEntryRel,
			long commerceOrderRuleEntryId,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEORDERRULEENTRYREL_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEORDERRULEENTRYID_COMMERCEORDERRULEENTRYID_2);

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
			sb.append(CommerceOrderRuleEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceOrderRuleEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceOrderRuleEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceOrderRuleEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce order rule entry rels where commerceOrderRuleEntryId = &#63; from the database.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 */
	@Override
	public void removeByCommerceOrderRuleEntryId(
		long commerceOrderRuleEntryId) {

		for (CommerceOrderRuleEntryRel commerceOrderRuleEntryRel :
				findByCommerceOrderRuleEntryId(
					commerceOrderRuleEntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceOrderRuleEntryRel);
		}
	}

	/**
	 * Returns the number of commerce order rule entry rels where commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the number of matching commerce order rule entry rels
	 */
	@Override
	public int countByCommerceOrderRuleEntryId(long commerceOrderRuleEntryId) {
		FinderPath finderPath = _finderPathCountByCommerceOrderRuleEntryId;

		Object[] finderArgs = new Object[] {commerceOrderRuleEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEORDERRULEENTRYREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEORDERRULEENTRYID_COMMERCEORDERRULEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceOrderRuleEntryId);

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
		_FINDER_COLUMN_COMMERCEORDERRULEENTRYID_COMMERCEORDERRULEENTRYID_2 =
			"commerceOrderRuleEntryRel.commerceOrderRuleEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the matching commerce order rule entry rels
	 */
	@Override
	public List<CommerceOrderRuleEntryRel> findByC_C(
		long classNameId, long commerceOrderRuleEntryId) {

		return findByC_C(
			classNameId, commerceOrderRuleEntryId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @return the range of matching commerce order rule entry rels
	 */
	@Override
	public List<CommerceOrderRuleEntryRel> findByC_C(
		long classNameId, long commerceOrderRuleEntryId, int start, int end) {

		return findByC_C(
			classNameId, commerceOrderRuleEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce order rule entry rels
	 */
	@Override
	public List<CommerceOrderRuleEntryRel> findByC_C(
		long classNameId, long commerceOrderRuleEntryId, int start, int end,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		return findByC_C(
			classNameId, commerceOrderRuleEntryId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce order rule entry rels
	 */
	@Override
	public List<CommerceOrderRuleEntryRel> findByC_C(
		long classNameId, long commerceOrderRuleEntryId, int start, int end,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {
					classNameId, commerceOrderRuleEntryId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, commerceOrderRuleEntryId, start, end,
				orderByComparator
			};
		}

		List<CommerceOrderRuleEntryRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderRuleEntryRel>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceOrderRuleEntryRel commerceOrderRuleEntryRel :
						list) {

					if ((classNameId !=
							commerceOrderRuleEntryRel.getClassNameId()) ||
						(commerceOrderRuleEntryId !=
							commerceOrderRuleEntryRel.
								getCommerceOrderRuleEntryId())) {

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

			sb.append(_SQL_SELECT_COMMERCEORDERRULEENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEORDERRULEENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceOrderRuleEntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(commerceOrderRuleEntryId);

				list = (List<CommerceOrderRuleEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel findByC_C_First(
			long classNameId, long commerceOrderRuleEntryId,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator)
		throws NoSuchOrderRuleEntryRelException {

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel = fetchByC_C_First(
			classNameId, commerceOrderRuleEntryId, orderByComparator);

		if (commerceOrderRuleEntryRel != null) {
			return commerceOrderRuleEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", commerceOrderRuleEntryId=");
		sb.append(commerceOrderRuleEntryId);

		sb.append("}");

		throw new NoSuchOrderRuleEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel fetchByC_C_First(
		long classNameId, long commerceOrderRuleEntryId,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		List<CommerceOrderRuleEntryRel> list = findByC_C(
			classNameId, commerceOrderRuleEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel findByC_C_Last(
			long classNameId, long commerceOrderRuleEntryId,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator)
		throws NoSuchOrderRuleEntryRelException {

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel = fetchByC_C_Last(
			classNameId, commerceOrderRuleEntryId, orderByComparator);

		if (commerceOrderRuleEntryRel != null) {
			return commerceOrderRuleEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", commerceOrderRuleEntryId=");
		sb.append(commerceOrderRuleEntryId);

		sb.append("}");

		throw new NoSuchOrderRuleEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel fetchByC_C_Last(
		long classNameId, long commerceOrderRuleEntryId,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		int count = countByC_C(classNameId, commerceOrderRuleEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceOrderRuleEntryRel> list = findByC_C(
			classNameId, commerceOrderRuleEntryId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce order rule entry rels before and after the current commerce order rule entry rel in the ordered set where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the current commerce order rule entry rel
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel[] findByC_C_PrevAndNext(
			long commerceOrderRuleEntryRelId, long classNameId,
			long commerceOrderRuleEntryId,
			OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator)
		throws NoSuchOrderRuleEntryRelException {

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel = findByPrimaryKey(
			commerceOrderRuleEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceOrderRuleEntryRel[] array =
				new CommerceOrderRuleEntryRelImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, commerceOrderRuleEntryRel, classNameId,
				commerceOrderRuleEntryId, orderByComparator, true);

			array[1] = commerceOrderRuleEntryRel;

			array[2] = getByC_C_PrevAndNext(
				session, commerceOrderRuleEntryRel, classNameId,
				commerceOrderRuleEntryId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceOrderRuleEntryRel getByC_C_PrevAndNext(
		Session session, CommerceOrderRuleEntryRel commerceOrderRuleEntryRel,
		long classNameId, long commerceOrderRuleEntryId,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEORDERRULEENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_COMMERCEORDERRULEENTRYID_2);

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
			sb.append(CommerceOrderRuleEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(commerceOrderRuleEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceOrderRuleEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceOrderRuleEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 */
	@Override
	public void removeByC_C(long classNameId, long commerceOrderRuleEntryId) {
		for (CommerceOrderRuleEntryRel commerceOrderRuleEntryRel :
				findByC_C(
					classNameId, commerceOrderRuleEntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceOrderRuleEntryRel);
		}
	}

	/**
	 * Returns the number of commerce order rule entry rels where classNameId = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the number of matching commerce order rule entry rels
	 */
	@Override
	public int countByC_C(long classNameId, long commerceOrderRuleEntryId) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {
			classNameId, commerceOrderRuleEntryId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEORDERRULEENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEORDERRULEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(commerceOrderRuleEntryId);

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
		"commerceOrderRuleEntryRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_COMMERCEORDERRULEENTRYID_2 =
		"commerceOrderRuleEntryRel.commerceOrderRuleEntryId = ?";

	private FinderPath _finderPathFetchByC_C_C;
	private FinderPath _finderPathCountByC_C_C;

	/**
	 * Returns the commerce order rule entry rel where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63; or throws a <code>NoSuchOrderRuleEntryRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the matching commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a matching commerce order rule entry rel could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel findByC_C_C(
			long classNameId, long classPK, long commerceOrderRuleEntryId)
		throws NoSuchOrderRuleEntryRelException {

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel = fetchByC_C_C(
			classNameId, classPK, commerceOrderRuleEntryId);

		if (commerceOrderRuleEntryRel == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", commerceOrderRuleEntryId=");
			sb.append(commerceOrderRuleEntryId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchOrderRuleEntryRelException(sb.toString());
		}

		return commerceOrderRuleEntryRel;
	}

	/**
	 * Returns the commerce order rule entry rel where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel fetchByC_C_C(
		long classNameId, long classPK, long commerceOrderRuleEntryId) {

		return fetchByC_C_C(
			classNameId, classPK, commerceOrderRuleEntryId, true);
	}

	/**
	 * Returns the commerce order rule entry rel where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce order rule entry rel, or <code>null</code> if a matching commerce order rule entry rel could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel fetchByC_C_C(
		long classNameId, long classPK, long commerceOrderRuleEntryId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				classNameId, classPK, commerceOrderRuleEntryId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(_finderPathFetchByC_C_C, finderArgs);
		}

		if (result instanceof CommerceOrderRuleEntryRel) {
			CommerceOrderRuleEntryRel commerceOrderRuleEntryRel =
				(CommerceOrderRuleEntryRel)result;

			if ((classNameId != commerceOrderRuleEntryRel.getClassNameId()) ||
				(classPK != commerceOrderRuleEntryRel.getClassPK()) ||
				(commerceOrderRuleEntryId !=
					commerceOrderRuleEntryRel.getCommerceOrderRuleEntryId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_COMMERCEORDERRULEENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCEORDERRULEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceOrderRuleEntryId);

				List<CommerceOrderRuleEntryRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_C, finderArgs, list);
					}
				}
				else {
					CommerceOrderRuleEntryRel commerceOrderRuleEntryRel =
						list.get(0);

					result = commerceOrderRuleEntryRel;

					cacheResult(commerceOrderRuleEntryRel);
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
			return (CommerceOrderRuleEntryRel)result;
		}
	}

	/**
	 * Removes the commerce order rule entry rel where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the commerce order rule entry rel that was removed
	 */
	@Override
	public CommerceOrderRuleEntryRel removeByC_C_C(
			long classNameId, long classPK, long commerceOrderRuleEntryId)
		throws NoSuchOrderRuleEntryRelException {

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel = findByC_C_C(
			classNameId, classPK, commerceOrderRuleEntryId);

		return remove(commerceOrderRuleEntryRel);
	}

	/**
	 * Returns the number of commerce order rule entry rels where classNameId = &#63; and classPK = &#63; and commerceOrderRuleEntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceOrderRuleEntryId the commerce order rule entry ID
	 * @return the number of matching commerce order rule entry rels
	 */
	@Override
	public int countByC_C_C(
		long classNameId, long classPK, long commerceOrderRuleEntryId) {

		FinderPath finderPath = _finderPathCountByC_C_C;

		Object[] finderArgs = new Object[] {
			classNameId, classPK, commerceOrderRuleEntryId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEORDERRULEENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCEORDERRULEENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceOrderRuleEntryId);

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
		"commerceOrderRuleEntryRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"commerceOrderRuleEntryRel.classPK = ? AND ";

	private static final String
		_FINDER_COLUMN_C_C_C_COMMERCEORDERRULEENTRYID_2 =
			"commerceOrderRuleEntryRel.commerceOrderRuleEntryId = ?";

	public CommerceOrderRuleEntryRelPersistenceImpl() {
		setModelClass(CommerceOrderRuleEntryRel.class);

		setModelImplClass(CommerceOrderRuleEntryRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceOrderRuleEntryRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce order rule entry rel in the entity cache if it is enabled.
	 *
	 * @param commerceOrderRuleEntryRel the commerce order rule entry rel
	 */
	@Override
	public void cacheResult(
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel) {

		entityCache.putResult(
			CommerceOrderRuleEntryRelImpl.class,
			commerceOrderRuleEntryRel.getPrimaryKey(),
			commerceOrderRuleEntryRel);

		finderCache.putResult(
			_finderPathFetchByC_C_C,
			new Object[] {
				commerceOrderRuleEntryRel.getClassNameId(),
				commerceOrderRuleEntryRel.getClassPK(),
				commerceOrderRuleEntryRel.getCommerceOrderRuleEntryId()
			},
			commerceOrderRuleEntryRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce order rule entry rels in the entity cache if it is enabled.
	 *
	 * @param commerceOrderRuleEntryRels the commerce order rule entry rels
	 */
	@Override
	public void cacheResult(
		List<CommerceOrderRuleEntryRel> commerceOrderRuleEntryRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceOrderRuleEntryRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceOrderRuleEntryRel commerceOrderRuleEntryRel :
				commerceOrderRuleEntryRels) {

			if (entityCache.getResult(
					CommerceOrderRuleEntryRelImpl.class,
					commerceOrderRuleEntryRel.getPrimaryKey()) == null) {

				cacheResult(commerceOrderRuleEntryRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce order rule entry rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceOrderRuleEntryRelImpl.class);

		finderCache.clearCache(CommerceOrderRuleEntryRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce order rule entry rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel) {

		entityCache.removeResult(
			CommerceOrderRuleEntryRelImpl.class, commerceOrderRuleEntryRel);
	}

	@Override
	public void clearCache(
		List<CommerceOrderRuleEntryRel> commerceOrderRuleEntryRels) {

		for (CommerceOrderRuleEntryRel commerceOrderRuleEntryRel :
				commerceOrderRuleEntryRels) {

			entityCache.removeResult(
				CommerceOrderRuleEntryRelImpl.class, commerceOrderRuleEntryRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceOrderRuleEntryRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceOrderRuleEntryRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceOrderRuleEntryRelModelImpl commerceOrderRuleEntryRelModelImpl) {

		Object[] args = new Object[] {
			commerceOrderRuleEntryRelModelImpl.getClassNameId(),
			commerceOrderRuleEntryRelModelImpl.getClassPK(),
			commerceOrderRuleEntryRelModelImpl.getCommerceOrderRuleEntryId()
		};

		finderCache.putResult(_finderPathCountByC_C_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C_C, args, commerceOrderRuleEntryRelModelImpl);
	}

	/**
	 * Creates a new commerce order rule entry rel with the primary key. Does not add the commerce order rule entry rel to the database.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key for the new commerce order rule entry rel
	 * @return the new commerce order rule entry rel
	 */
	@Override
	public CommerceOrderRuleEntryRel create(long commerceOrderRuleEntryRelId) {
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel =
			new CommerceOrderRuleEntryRelImpl();

		commerceOrderRuleEntryRel.setNew(true);
		commerceOrderRuleEntryRel.setPrimaryKey(commerceOrderRuleEntryRelId);

		commerceOrderRuleEntryRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceOrderRuleEntryRel;
	}

	/**
	 * Removes the commerce order rule entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel that was removed
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel remove(long commerceOrderRuleEntryRelId)
		throws NoSuchOrderRuleEntryRelException {

		return remove((Serializable)commerceOrderRuleEntryRelId);
	}

	/**
	 * Removes the commerce order rule entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel that was removed
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel remove(Serializable primaryKey)
		throws NoSuchOrderRuleEntryRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceOrderRuleEntryRel commerceOrderRuleEntryRel =
				(CommerceOrderRuleEntryRel)session.get(
					CommerceOrderRuleEntryRelImpl.class, primaryKey);

			if (commerceOrderRuleEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchOrderRuleEntryRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceOrderRuleEntryRel);
		}
		catch (NoSuchOrderRuleEntryRelException noSuchEntityException) {
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
	protected CommerceOrderRuleEntryRel removeImpl(
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceOrderRuleEntryRel)) {
				commerceOrderRuleEntryRel =
					(CommerceOrderRuleEntryRel)session.get(
						CommerceOrderRuleEntryRelImpl.class,
						commerceOrderRuleEntryRel.getPrimaryKeyObj());
			}

			if (commerceOrderRuleEntryRel != null) {
				session.delete(commerceOrderRuleEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceOrderRuleEntryRel != null) {
			clearCache(commerceOrderRuleEntryRel);
		}

		return commerceOrderRuleEntryRel;
	}

	@Override
	public CommerceOrderRuleEntryRel updateImpl(
		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel) {

		boolean isNew = commerceOrderRuleEntryRel.isNew();

		if (!(commerceOrderRuleEntryRel instanceof
				CommerceOrderRuleEntryRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceOrderRuleEntryRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceOrderRuleEntryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceOrderRuleEntryRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceOrderRuleEntryRel implementation " +
					commerceOrderRuleEntryRel.getClass());
		}

		CommerceOrderRuleEntryRelModelImpl commerceOrderRuleEntryRelModelImpl =
			(CommerceOrderRuleEntryRelModelImpl)commerceOrderRuleEntryRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (commerceOrderRuleEntryRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceOrderRuleEntryRel.setCreateDate(date);
			}
			else {
				commerceOrderRuleEntryRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceOrderRuleEntryRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceOrderRuleEntryRel.setModifiedDate(date);
			}
			else {
				commerceOrderRuleEntryRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceOrderRuleEntryRel);
			}
			else {
				commerceOrderRuleEntryRel =
					(CommerceOrderRuleEntryRel)session.merge(
						commerceOrderRuleEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceOrderRuleEntryRelImpl.class,
			commerceOrderRuleEntryRelModelImpl, false, true);

		cacheUniqueFindersCache(commerceOrderRuleEntryRelModelImpl);

		if (isNew) {
			commerceOrderRuleEntryRel.setNew(false);
		}

		commerceOrderRuleEntryRel.resetOriginalValues();

		return commerceOrderRuleEntryRel;
	}

	/**
	 * Returns the commerce order rule entry rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchOrderRuleEntryRelException {

		CommerceOrderRuleEntryRel commerceOrderRuleEntryRel = fetchByPrimaryKey(
			primaryKey);

		if (commerceOrderRuleEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchOrderRuleEntryRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceOrderRuleEntryRel;
	}

	/**
	 * Returns the commerce order rule entry rel with the primary key or throws a <code>NoSuchOrderRuleEntryRelException</code> if it could not be found.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel
	 * @throws NoSuchOrderRuleEntryRelException if a commerce order rule entry rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel findByPrimaryKey(
			long commerceOrderRuleEntryRelId)
		throws NoSuchOrderRuleEntryRelException {

		return findByPrimaryKey((Serializable)commerceOrderRuleEntryRelId);
	}

	/**
	 * Returns the commerce order rule entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceOrderRuleEntryRelId the primary key of the commerce order rule entry rel
	 * @return the commerce order rule entry rel, or <code>null</code> if a commerce order rule entry rel with the primary key could not be found
	 */
	@Override
	public CommerceOrderRuleEntryRel fetchByPrimaryKey(
		long commerceOrderRuleEntryRelId) {

		return fetchByPrimaryKey((Serializable)commerceOrderRuleEntryRelId);
	}

	/**
	 * Returns all the commerce order rule entry rels.
	 *
	 * @return the commerce order rule entry rels
	 */
	@Override
	public List<CommerceOrderRuleEntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce order rule entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @return the range of commerce order rule entry rels
	 */
	@Override
	public List<CommerceOrderRuleEntryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce order rule entry rels
	 */
	@Override
	public List<CommerceOrderRuleEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce order rule entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceOrderRuleEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce order rule entry rels
	 * @param end the upper bound of the range of commerce order rule entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce order rule entry rels
	 */
	@Override
	public List<CommerceOrderRuleEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceOrderRuleEntryRel> orderByComparator,
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

		List<CommerceOrderRuleEntryRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceOrderRuleEntryRel>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEORDERRULEENTRYREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEORDERRULEENTRYREL;

				sql = sql.concat(
					CommerceOrderRuleEntryRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceOrderRuleEntryRel>)QueryUtil.list(
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
	 * Removes all the commerce order rule entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceOrderRuleEntryRel commerceOrderRuleEntryRel : findAll()) {
			remove(commerceOrderRuleEntryRel);
		}
	}

	/**
	 * Returns the number of commerce order rule entry rels.
	 *
	 * @return the number of commerce order rule entry rels
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
					_SQL_COUNT_COMMERCEORDERRULEENTRYREL);

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
		return "commerceOrderRuleEntryRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEORDERRULEENTRYREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceOrderRuleEntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce order rule entry rel persistence.
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

		_finderPathWithPaginationFindByCommerceOrderRuleEntryId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceOrderRuleEntryId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceOrderRuleEntryId"}, true);

		_finderPathWithoutPaginationFindByCommerceOrderRuleEntryId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceOrderRuleEntryId",
				new String[] {Long.class.getName()},
				new String[] {"commerceOrderRuleEntryId"}, true);

		_finderPathCountByCommerceOrderRuleEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceOrderRuleEntryId",
			new String[] {Long.class.getName()},
			new String[] {"commerceOrderRuleEntryId"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "commerceOrderRuleEntryId"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "commerceOrderRuleEntryId"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "commerceOrderRuleEntryId"}, false);

		_finderPathFetchByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "commerceOrderRuleEntryId"},
			true);

		_finderPathCountByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "commerceOrderRuleEntryId"},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(CommerceOrderRuleEntryRelImpl.class.getName());
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

	private static final String _SQL_SELECT_COMMERCEORDERRULEENTRYREL =
		"SELECT commerceOrderRuleEntryRel FROM CommerceOrderRuleEntryRel commerceOrderRuleEntryRel";

	private static final String _SQL_SELECT_COMMERCEORDERRULEENTRYREL_WHERE =
		"SELECT commerceOrderRuleEntryRel FROM CommerceOrderRuleEntryRel commerceOrderRuleEntryRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEORDERRULEENTRYREL =
		"SELECT COUNT(commerceOrderRuleEntryRel) FROM CommerceOrderRuleEntryRel commerceOrderRuleEntryRel";

	private static final String _SQL_COUNT_COMMERCEORDERRULEENTRYREL_WHERE =
		"SELECT COUNT(commerceOrderRuleEntryRel) FROM CommerceOrderRuleEntryRel commerceOrderRuleEntryRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceOrderRuleEntryRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceOrderRuleEntryRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceOrderRuleEntryRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceOrderRuleEntryRelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private CommerceOrderRuleEntryRelModelArgumentsResolver
		_commerceOrderRuleEntryRelModelArgumentsResolver;

}