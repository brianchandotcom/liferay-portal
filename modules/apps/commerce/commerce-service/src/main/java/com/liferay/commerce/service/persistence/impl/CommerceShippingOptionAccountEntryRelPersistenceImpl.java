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

package com.liferay.commerce.service.persistence.impl;

import com.liferay.commerce.exception.NoSuchShippingOptionAccountEntryRelException;
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRel;
import com.liferay.commerce.model.CommerceShippingOptionAccountEntryRelTable;
import com.liferay.commerce.model.impl.CommerceShippingOptionAccountEntryRelImpl;
import com.liferay.commerce.model.impl.CommerceShippingOptionAccountEntryRelModelImpl;
import com.liferay.commerce.service.persistence.CommerceShippingOptionAccountEntryRelPersistence;
import com.liferay.commerce.service.persistence.CommerceShippingOptionAccountEntryRelUtil;
import com.liferay.commerce.service.persistence.impl.constants.CommercePersistenceConstants;
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
import java.util.Objects;
import java.util.Set;

import javax.sql.DataSource;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Deactivate;
import org.osgi.service.component.annotations.Reference;

/**
 * The persistence implementation for the commerce shipping option account entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
@Component(service = CommerceShippingOptionAccountEntryRelPersistence.class)
public class CommerceShippingOptionAccountEntryRelPersistenceImpl
	extends BasePersistenceImpl<CommerceShippingOptionAccountEntryRel>
	implements CommerceShippingOptionAccountEntryRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceShippingOptionAccountEntryRelUtil</code> to access the commerce shipping option account entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceShippingOptionAccountEntryRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByAccountEntryId;
	private FinderPath _finderPathWithoutPaginationFindByAccountEntryId;
	private FinderPath _finderPathCountByAccountEntryId;

	/**
	 * Returns all the commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the matching commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel> findByAccountEntryId(
		long accountEntryId) {

		return findByAccountEntryId(
			accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @return the range of matching commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel> findByAccountEntryId(
		long accountEntryId, int start, int end) {

		return findByAccountEntryId(accountEntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<CommerceShippingOptionAccountEntryRel>
			orderByComparator) {

		return findByAccountEntryId(
			accountEntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param accountEntryId the account entry ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel> findByAccountEntryId(
		long accountEntryId, int start, int end,
		OrderByComparator<CommerceShippingOptionAccountEntryRel>
			orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByAccountEntryId;
				finderArgs = new Object[] {accountEntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByAccountEntryId;
			finderArgs = new Object[] {
				accountEntryId, start, end, orderByComparator
			};
		}

		List<CommerceShippingOptionAccountEntryRel> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceShippingOptionAccountEntryRel>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingOptionAccountEntryRel
						commerceShippingOptionAccountEntryRel : list) {

					if (accountEntryId !=
							commerceShippingOptionAccountEntryRel.
								getAccountEntryId()) {

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

			sb.append(_SQL_SELECT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceShippingOptionAccountEntryRelModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				list =
					(List<CommerceShippingOptionAccountEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel findByAccountEntryId_First(
			long accountEntryId,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel = fetchByAccountEntryId_First(
				accountEntryId, orderByComparator);

		if (commerceShippingOptionAccountEntryRel != null) {
			return commerceShippingOptionAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchShippingOptionAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel fetchByAccountEntryId_First(
		long accountEntryId,
		OrderByComparator<CommerceShippingOptionAccountEntryRel>
			orderByComparator) {

		List<CommerceShippingOptionAccountEntryRel> list = findByAccountEntryId(
			accountEntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel findByAccountEntryId_Last(
			long accountEntryId,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel = fetchByAccountEntryId_Last(
				accountEntryId, orderByComparator);

		if (commerceShippingOptionAccountEntryRel != null) {
			return commerceShippingOptionAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("accountEntryId=");
		sb.append(accountEntryId);

		sb.append("}");

		throw new NoSuchShippingOptionAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel fetchByAccountEntryId_Last(
		long accountEntryId,
		OrderByComparator<CommerceShippingOptionAccountEntryRel>
			orderByComparator) {

		int count = countByAccountEntryId(accountEntryId);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingOptionAccountEntryRel> list = findByAccountEntryId(
			accountEntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipping option account entry rels before and after the current commerce shipping option account entry rel in the ordered set where accountEntryId = &#63;.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the current commerce shipping option account entry rel
	 * @param accountEntryId the account entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel[]
			findByAccountEntryId_PrevAndNext(
				long CommerceShippingOptionAccountEntryRelId,
				long accountEntryId,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel = findByPrimaryKey(
				CommerceShippingOptionAccountEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingOptionAccountEntryRel[] array =
				new CommerceShippingOptionAccountEntryRelImpl[3];

			array[0] = getByAccountEntryId_PrevAndNext(
				session, commerceShippingOptionAccountEntryRel, accountEntryId,
				orderByComparator, true);

			array[1] = commerceShippingOptionAccountEntryRel;

			array[2] = getByAccountEntryId_PrevAndNext(
				session, commerceShippingOptionAccountEntryRel, accountEntryId,
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

	protected CommerceShippingOptionAccountEntryRel
		getByAccountEntryId_PrevAndNext(
			Session session,
			CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel,
			long accountEntryId,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
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

		sb.append(_SQL_SELECT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

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
				CommerceShippingOptionAccountEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(accountEntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceShippingOptionAccountEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceShippingOptionAccountEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipping option account entry rels where accountEntryId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 */
	@Override
	public void removeByAccountEntryId(long accountEntryId) {
		for (CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel :
					findByAccountEntryId(
						accountEntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						null)) {

			remove(commerceShippingOptionAccountEntryRel);
		}
	}

	/**
	 * Returns the number of commerce shipping option account entry rels where accountEntryId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @return the number of matching commerce shipping option account entry rels
	 */
	@Override
	public int countByAccountEntryId(long accountEntryId) {
		FinderPath finderPath = _finderPathCountByAccountEntryId;

		Object[] finderArgs = new Object[] {accountEntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

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

	private static final String _FINDER_COLUMN_ACCOUNTENTRYID_ACCOUNTENTRYID_2 =
		"commerceShippingOptionAccountEntryRel.accountEntryId = ?";

	private FinderPath _finderPathWithPaginationFindByCommerceChannelId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceChannelId;
	private FinderPath _finderPathCountByCommerceChannelId;

	/**
	 * Returns all the commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @return the matching commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel> findByCommerceChannelId(
		long commerceChannelId) {

		return findByCommerceChannelId(
			commerceChannelId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @return the range of matching commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel> findByCommerceChannelId(
		long commerceChannelId, int start, int end) {

		return findByCommerceChannelId(commerceChannelId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel> findByCommerceChannelId(
		long commerceChannelId, int start, int end,
		OrderByComparator<CommerceShippingOptionAccountEntryRel>
			orderByComparator) {

		return findByCommerceChannelId(
			commerceChannelId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel> findByCommerceChannelId(
		long commerceChannelId, int start, int end,
		OrderByComparator<CommerceShippingOptionAccountEntryRel>
			orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceChannelId;
				finderArgs = new Object[] {commerceChannelId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceChannelId;
			finderArgs = new Object[] {
				commerceChannelId, start, end, orderByComparator
			};
		}

		List<CommerceShippingOptionAccountEntryRel> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceShippingOptionAccountEntryRel>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingOptionAccountEntryRel
						commerceShippingOptionAccountEntryRel : list) {

					if (commerceChannelId !=
							commerceShippingOptionAccountEntryRel.
								getCommerceChannelId()) {

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

			sb.append(_SQL_SELECT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCECHANNELID_COMMERCECHANNELID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceShippingOptionAccountEntryRelModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceChannelId);

				list =
					(List<CommerceShippingOptionAccountEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel findByCommerceChannelId_First(
			long commerceChannelId,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				fetchByCommerceChannelId_First(
					commerceChannelId, orderByComparator);

		if (commerceShippingOptionAccountEntryRel != null) {
			return commerceShippingOptionAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceChannelId=");
		sb.append(commerceChannelId);

		sb.append("}");

		throw new NoSuchShippingOptionAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel fetchByCommerceChannelId_First(
		long commerceChannelId,
		OrderByComparator<CommerceShippingOptionAccountEntryRel>
			orderByComparator) {

		List<CommerceShippingOptionAccountEntryRel> list =
			findByCommerceChannelId(commerceChannelId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel findByCommerceChannelId_Last(
			long commerceChannelId,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				fetchByCommerceChannelId_Last(
					commerceChannelId, orderByComparator);

		if (commerceShippingOptionAccountEntryRel != null) {
			return commerceShippingOptionAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceChannelId=");
		sb.append(commerceChannelId);

		sb.append("}");

		throw new NoSuchShippingOptionAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel fetchByCommerceChannelId_Last(
		long commerceChannelId,
		OrderByComparator<CommerceShippingOptionAccountEntryRel>
			orderByComparator) {

		int count = countByCommerceChannelId(commerceChannelId);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingOptionAccountEntryRel> list =
			findByCommerceChannelId(
				commerceChannelId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipping option account entry rels before and after the current commerce shipping option account entry rel in the ordered set where commerceChannelId = &#63;.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the current commerce shipping option account entry rel
	 * @param commerceChannelId the commerce channel ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel[]
			findByCommerceChannelId_PrevAndNext(
				long CommerceShippingOptionAccountEntryRelId,
				long commerceChannelId,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel = findByPrimaryKey(
				CommerceShippingOptionAccountEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingOptionAccountEntryRel[] array =
				new CommerceShippingOptionAccountEntryRelImpl[3];

			array[0] = getByCommerceChannelId_PrevAndNext(
				session, commerceShippingOptionAccountEntryRel,
				commerceChannelId, orderByComparator, true);

			array[1] = commerceShippingOptionAccountEntryRel;

			array[2] = getByCommerceChannelId_PrevAndNext(
				session, commerceShippingOptionAccountEntryRel,
				commerceChannelId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShippingOptionAccountEntryRel
		getByCommerceChannelId_PrevAndNext(
			Session session,
			CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel,
			long commerceChannelId,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
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

		sb.append(_SQL_SELECT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_COMMERCECHANNELID_COMMERCECHANNELID_2);

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
				CommerceShippingOptionAccountEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceChannelId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceShippingOptionAccountEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceShippingOptionAccountEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipping option account entry rels where commerceChannelId = &#63; from the database.
	 *
	 * @param commerceChannelId the commerce channel ID
	 */
	@Override
	public void removeByCommerceChannelId(long commerceChannelId) {
		for (CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel :
					findByCommerceChannelId(
						commerceChannelId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
						null)) {

			remove(commerceShippingOptionAccountEntryRel);
		}
	}

	/**
	 * Returns the number of commerce shipping option account entry rels where commerceChannelId = &#63;.
	 *
	 * @param commerceChannelId the commerce channel ID
	 * @return the number of matching commerce shipping option account entry rels
	 */
	@Override
	public int countByCommerceChannelId(long commerceChannelId) {
		FinderPath finderPath = _finderPathCountByCommerceChannelId;

		Object[] finderArgs = new Object[] {commerceChannelId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_COMMERCECHANNELID_COMMERCECHANNELID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceChannelId);

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
		_FINDER_COLUMN_COMMERCECHANNELID_COMMERCECHANNELID_2 =
			"commerceShippingOptionAccountEntryRel.commerceChannelId = ?";

	private FinderPath _finderPathWithPaginationFindByCommerceShippingOptionKey;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceShippingOptionKey;
	private FinderPath _finderPathCountByCommerceShippingOptionKey;

	/**
	 * Returns all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @return the matching commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel>
		findByCommerceShippingOptionKey(String commerceShippingOptionKey) {

		return findByCommerceShippingOptionKey(
			commerceShippingOptionKey, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @return the range of matching commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel>
		findByCommerceShippingOptionKey(
			String commerceShippingOptionKey, int start, int end) {

		return findByCommerceShippingOptionKey(
			commerceShippingOptionKey, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel>
		findByCommerceShippingOptionKey(
			String commerceShippingOptionKey, int start, int end,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator) {

		return findByCommerceShippingOptionKey(
			commerceShippingOptionKey, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel>
		findByCommerceShippingOptionKey(
			String commerceShippingOptionKey, int start, int end,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator,
			boolean useFinderCache) {

		commerceShippingOptionKey = Objects.toString(
			commerceShippingOptionKey, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceShippingOptionKey;
				finderArgs = new Object[] {commerceShippingOptionKey};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceShippingOptionKey;
			finderArgs = new Object[] {
				commerceShippingOptionKey, start, end, orderByComparator
			};
		}

		List<CommerceShippingOptionAccountEntryRel> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceShippingOptionAccountEntryRel>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceShippingOptionAccountEntryRel
						commerceShippingOptionAccountEntryRel : list) {

					if (!commerceShippingOptionKey.equals(
							commerceShippingOptionAccountEntryRel.
								getCommerceShippingOptionKey())) {

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

			sb.append(_SQL_SELECT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL_WHERE);

			boolean bindCommerceShippingOptionKey = false;

			if (commerceShippingOptionKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_COMMERCESHIPPINGOPTIONKEY_COMMERCESHIPPINGOPTIONKEY_3);
			}
			else {
				bindCommerceShippingOptionKey = true;

				sb.append(
					_FINDER_COLUMN_COMMERCESHIPPINGOPTIONKEY_COMMERCESHIPPINGOPTIONKEY_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceShippingOptionAccountEntryRelModelImpl.
						ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCommerceShippingOptionKey) {
					queryPos.add(commerceShippingOptionKey);
				}

				list =
					(List<CommerceShippingOptionAccountEntryRel>)QueryUtil.list(
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
	 * Returns the first commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel
			findByCommerceShippingOptionKey_First(
				String commerceShippingOptionKey,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				fetchByCommerceShippingOptionKey_First(
					commerceShippingOptionKey, orderByComparator);

		if (commerceShippingOptionAccountEntryRel != null) {
			return commerceShippingOptionAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceShippingOptionKey=");
		sb.append(commerceShippingOptionKey);

		sb.append("}");

		throw new NoSuchShippingOptionAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the first commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel
		fetchByCommerceShippingOptionKey_First(
			String commerceShippingOptionKey,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator) {

		List<CommerceShippingOptionAccountEntryRel> list =
			findByCommerceShippingOptionKey(
				commerceShippingOptionKey, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel
			findByCommerceShippingOptionKey_Last(
				String commerceShippingOptionKey,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				fetchByCommerceShippingOptionKey_Last(
					commerceShippingOptionKey, orderByComparator);

		if (commerceShippingOptionAccountEntryRel != null) {
			return commerceShippingOptionAccountEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceShippingOptionKey=");
		sb.append(commerceShippingOptionKey);

		sb.append("}");

		throw new NoSuchShippingOptionAccountEntryRelException(sb.toString());
	}

	/**
	 * Returns the last commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel
		fetchByCommerceShippingOptionKey_Last(
			String commerceShippingOptionKey,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
				orderByComparator) {

		int count = countByCommerceShippingOptionKey(commerceShippingOptionKey);

		if (count == 0) {
			return null;
		}

		List<CommerceShippingOptionAccountEntryRel> list =
			findByCommerceShippingOptionKey(
				commerceShippingOptionKey, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce shipping option account entry rels before and after the current commerce shipping option account entry rel in the ordered set where commerceShippingOptionKey = &#63;.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the current commerce shipping option account entry rel
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel[]
			findByCommerceShippingOptionKey_PrevAndNext(
				long CommerceShippingOptionAccountEntryRelId,
				String commerceShippingOptionKey,
				OrderByComparator<CommerceShippingOptionAccountEntryRel>
					orderByComparator)
		throws NoSuchShippingOptionAccountEntryRelException {

		commerceShippingOptionKey = Objects.toString(
			commerceShippingOptionKey, "");

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel = findByPrimaryKey(
				CommerceShippingOptionAccountEntryRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceShippingOptionAccountEntryRel[] array =
				new CommerceShippingOptionAccountEntryRelImpl[3];

			array[0] = getByCommerceShippingOptionKey_PrevAndNext(
				session, commerceShippingOptionAccountEntryRel,
				commerceShippingOptionKey, orderByComparator, true);

			array[1] = commerceShippingOptionAccountEntryRel;

			array[2] = getByCommerceShippingOptionKey_PrevAndNext(
				session, commerceShippingOptionAccountEntryRel,
				commerceShippingOptionKey, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceShippingOptionAccountEntryRel
		getByCommerceShippingOptionKey_PrevAndNext(
			Session session,
			CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel,
			String commerceShippingOptionKey,
			OrderByComparator<CommerceShippingOptionAccountEntryRel>
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

		sb.append(_SQL_SELECT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL_WHERE);

		boolean bindCommerceShippingOptionKey = false;

		if (commerceShippingOptionKey.isEmpty()) {
			sb.append(
				_FINDER_COLUMN_COMMERCESHIPPINGOPTIONKEY_COMMERCESHIPPINGOPTIONKEY_3);
		}
		else {
			bindCommerceShippingOptionKey = true;

			sb.append(
				_FINDER_COLUMN_COMMERCESHIPPINGOPTIONKEY_COMMERCESHIPPINGOPTIONKEY_2);
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
				CommerceShippingOptionAccountEntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindCommerceShippingOptionKey) {
			queryPos.add(commerceShippingOptionKey);
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceShippingOptionAccountEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceShippingOptionAccountEntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce shipping option account entry rels where commerceShippingOptionKey = &#63; from the database.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 */
	@Override
	public void removeByCommerceShippingOptionKey(
		String commerceShippingOptionKey) {

		for (CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel :
					findByCommerceShippingOptionKey(
						commerceShippingOptionKey, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commerceShippingOptionAccountEntryRel);
		}
	}

	/**
	 * Returns the number of commerce shipping option account entry rels where commerceShippingOptionKey = &#63;.
	 *
	 * @param commerceShippingOptionKey the commerce shipping option key
	 * @return the number of matching commerce shipping option account entry rels
	 */
	@Override
	public int countByCommerceShippingOptionKey(
		String commerceShippingOptionKey) {

		commerceShippingOptionKey = Objects.toString(
			commerceShippingOptionKey, "");

		FinderPath finderPath = _finderPathCountByCommerceShippingOptionKey;

		Object[] finderArgs = new Object[] {commerceShippingOptionKey};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL_WHERE);

			boolean bindCommerceShippingOptionKey = false;

			if (commerceShippingOptionKey.isEmpty()) {
				sb.append(
					_FINDER_COLUMN_COMMERCESHIPPINGOPTIONKEY_COMMERCESHIPPINGOPTIONKEY_3);
			}
			else {
				bindCommerceShippingOptionKey = true;

				sb.append(
					_FINDER_COLUMN_COMMERCESHIPPINGOPTIONKEY_COMMERCESHIPPINGOPTIONKEY_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindCommerceShippingOptionKey) {
					queryPos.add(commerceShippingOptionKey);
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

	private static final String
		_FINDER_COLUMN_COMMERCESHIPPINGOPTIONKEY_COMMERCESHIPPINGOPTIONKEY_2 =
			"commerceShippingOptionAccountEntryRel.commerceShippingOptionKey = ?";

	private static final String
		_FINDER_COLUMN_COMMERCESHIPPINGOPTIONKEY_COMMERCESHIPPINGOPTIONKEY_3 =
			"(commerceShippingOptionAccountEntryRel.commerceShippingOptionKey IS NULL OR commerceShippingOptionAccountEntryRel.commerceShippingOptionKey = '')";

	private FinderPath _finderPathFetchByA_C;
	private FinderPath _finderPathCountByA_C;

	/**
	 * Returns the commerce shipping option account entry rel where accountEntryId = &#63; and commerceChannelId = &#63; or throws a <code>NoSuchShippingOptionAccountEntryRelException</code> if it could not be found.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @return the matching commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel findByA_C(
			long accountEntryId, long commerceChannelId)
		throws NoSuchShippingOptionAccountEntryRelException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel = fetchByA_C(
				accountEntryId, commerceChannelId);

		if (commerceShippingOptionAccountEntryRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("accountEntryId=");
			sb.append(accountEntryId);

			sb.append(", commerceChannelId=");
			sb.append(commerceChannelId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchShippingOptionAccountEntryRelException(
				sb.toString());
		}

		return commerceShippingOptionAccountEntryRel;
	}

	/**
	 * Returns the commerce shipping option account entry rel where accountEntryId = &#63; and commerceChannelId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @return the matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel fetchByA_C(
		long accountEntryId, long commerceChannelId) {

		return fetchByA_C(accountEntryId, commerceChannelId, true);
	}

	/**
	 * Returns the commerce shipping option account entry rel where accountEntryId = &#63; and commerceChannelId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce shipping option account entry rel, or <code>null</code> if a matching commerce shipping option account entry rel could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel fetchByA_C(
		long accountEntryId, long commerceChannelId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {accountEntryId, commerceChannelId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByA_C, finderArgs, this);
		}

		if (result instanceof CommerceShippingOptionAccountEntryRel) {
			CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel =
					(CommerceShippingOptionAccountEntryRel)result;

			if ((accountEntryId !=
					commerceShippingOptionAccountEntryRel.
						getAccountEntryId()) ||
				(commerceChannelId !=
					commerceShippingOptionAccountEntryRel.
						getCommerceChannelId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_C_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_C_COMMERCECHANNELID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(commerceChannelId);

				List<CommerceShippingOptionAccountEntryRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByA_C, finderArgs, list);
					}
				}
				else {
					CommerceShippingOptionAccountEntryRel
						commerceShippingOptionAccountEntryRel = list.get(0);

					result = commerceShippingOptionAccountEntryRel;

					cacheResult(commerceShippingOptionAccountEntryRel);
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
			return (CommerceShippingOptionAccountEntryRel)result;
		}
	}

	/**
	 * Removes the commerce shipping option account entry rel where accountEntryId = &#63; and commerceChannelId = &#63; from the database.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @return the commerce shipping option account entry rel that was removed
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel removeByA_C(
			long accountEntryId, long commerceChannelId)
		throws NoSuchShippingOptionAccountEntryRelException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel = findByA_C(
				accountEntryId, commerceChannelId);

		return remove(commerceShippingOptionAccountEntryRel);
	}

	/**
	 * Returns the number of commerce shipping option account entry rels where accountEntryId = &#63; and commerceChannelId = &#63;.
	 *
	 * @param accountEntryId the account entry ID
	 * @param commerceChannelId the commerce channel ID
	 * @return the number of matching commerce shipping option account entry rels
	 */
	@Override
	public int countByA_C(long accountEntryId, long commerceChannelId) {
		FinderPath finderPath = _finderPathCountByA_C;

		Object[] finderArgs = new Object[] {accountEntryId, commerceChannelId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_A_C_ACCOUNTENTRYID_2);

			sb.append(_FINDER_COLUMN_A_C_COMMERCECHANNELID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(accountEntryId);

				queryPos.add(commerceChannelId);

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

	private static final String _FINDER_COLUMN_A_C_ACCOUNTENTRYID_2 =
		"commerceShippingOptionAccountEntryRel.accountEntryId = ? AND ";

	private static final String _FINDER_COLUMN_A_C_COMMERCECHANNELID_2 =
		"commerceShippingOptionAccountEntryRel.commerceChannelId = ?";

	public CommerceShippingOptionAccountEntryRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"CommerceShippingOptionAccountEntryRelId",
			"CSOptionAccountEntryRelId");

		setDBColumnNames(dbColumnNames);

		setModelClass(CommerceShippingOptionAccountEntryRel.class);

		setModelImplClass(CommerceShippingOptionAccountEntryRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceShippingOptionAccountEntryRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce shipping option account entry rel in the entity cache if it is enabled.
	 *
	 * @param commerceShippingOptionAccountEntryRel the commerce shipping option account entry rel
	 */
	@Override
	public void cacheResult(
		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel) {

		entityCache.putResult(
			CommerceShippingOptionAccountEntryRelImpl.class,
			commerceShippingOptionAccountEntryRel.getPrimaryKey(),
			commerceShippingOptionAccountEntryRel);

		finderCache.putResult(
			_finderPathFetchByA_C,
			new Object[] {
				commerceShippingOptionAccountEntryRel.getAccountEntryId(),
				commerceShippingOptionAccountEntryRel.getCommerceChannelId()
			},
			commerceShippingOptionAccountEntryRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the commerce shipping option account entry rels in the entity cache if it is enabled.
	 *
	 * @param commerceShippingOptionAccountEntryRels the commerce shipping option account entry rels
	 */
	@Override
	public void cacheResult(
		List<CommerceShippingOptionAccountEntryRel>
			commerceShippingOptionAccountEntryRels) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (commerceShippingOptionAccountEntryRels.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel :
					commerceShippingOptionAccountEntryRels) {

			if (entityCache.getResult(
					CommerceShippingOptionAccountEntryRelImpl.class,
					commerceShippingOptionAccountEntryRel.getPrimaryKey()) ==
						null) {

				cacheResult(commerceShippingOptionAccountEntryRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce shipping option account entry rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceShippingOptionAccountEntryRelImpl.class);

		finderCache.clearCache(CommerceShippingOptionAccountEntryRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce shipping option account entry rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel) {

		entityCache.removeResult(
			CommerceShippingOptionAccountEntryRelImpl.class,
			commerceShippingOptionAccountEntryRel);
	}

	@Override
	public void clearCache(
		List<CommerceShippingOptionAccountEntryRel>
			commerceShippingOptionAccountEntryRels) {

		for (CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel :
					commerceShippingOptionAccountEntryRels) {

			entityCache.removeResult(
				CommerceShippingOptionAccountEntryRelImpl.class,
				commerceShippingOptionAccountEntryRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceShippingOptionAccountEntryRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceShippingOptionAccountEntryRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceShippingOptionAccountEntryRelModelImpl
			commerceShippingOptionAccountEntryRelModelImpl) {

		Object[] args = new Object[] {
			commerceShippingOptionAccountEntryRelModelImpl.getAccountEntryId(),
			commerceShippingOptionAccountEntryRelModelImpl.
				getCommerceChannelId()
		};

		finderCache.putResult(_finderPathCountByA_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByA_C, args,
			commerceShippingOptionAccountEntryRelModelImpl);
	}

	/**
	 * Creates a new commerce shipping option account entry rel with the primary key. Does not add the commerce shipping option account entry rel to the database.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key for the new commerce shipping option account entry rel
	 * @return the new commerce shipping option account entry rel
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel create(
		long CommerceShippingOptionAccountEntryRelId) {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel =
				new CommerceShippingOptionAccountEntryRelImpl();

		commerceShippingOptionAccountEntryRel.setNew(true);
		commerceShippingOptionAccountEntryRel.setPrimaryKey(
			CommerceShippingOptionAccountEntryRelId);

		commerceShippingOptionAccountEntryRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceShippingOptionAccountEntryRel;
	}

	/**
	 * Removes the commerce shipping option account entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the commerce shipping option account entry rel
	 * @return the commerce shipping option account entry rel that was removed
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel remove(
			long CommerceShippingOptionAccountEntryRelId)
		throws NoSuchShippingOptionAccountEntryRelException {

		return remove((Serializable)CommerceShippingOptionAccountEntryRelId);
	}

	/**
	 * Removes the commerce shipping option account entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce shipping option account entry rel
	 * @return the commerce shipping option account entry rel that was removed
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel remove(Serializable primaryKey)
		throws NoSuchShippingOptionAccountEntryRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel =
					(CommerceShippingOptionAccountEntryRel)session.get(
						CommerceShippingOptionAccountEntryRelImpl.class,
						primaryKey);

			if (commerceShippingOptionAccountEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchShippingOptionAccountEntryRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceShippingOptionAccountEntryRel);
		}
		catch (NoSuchShippingOptionAccountEntryRelException
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
	protected CommerceShippingOptionAccountEntryRel removeImpl(
		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceShippingOptionAccountEntryRel)) {
				commerceShippingOptionAccountEntryRel =
					(CommerceShippingOptionAccountEntryRel)session.get(
						CommerceShippingOptionAccountEntryRelImpl.class,
						commerceShippingOptionAccountEntryRel.
							getPrimaryKeyObj());
			}

			if (commerceShippingOptionAccountEntryRel != null) {
				session.delete(commerceShippingOptionAccountEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceShippingOptionAccountEntryRel != null) {
			clearCache(commerceShippingOptionAccountEntryRel);
		}

		return commerceShippingOptionAccountEntryRel;
	}

	@Override
	public CommerceShippingOptionAccountEntryRel updateImpl(
		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel) {

		boolean isNew = commerceShippingOptionAccountEntryRel.isNew();

		if (!(commerceShippingOptionAccountEntryRel instanceof
				CommerceShippingOptionAccountEntryRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceShippingOptionAccountEntryRel.getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceShippingOptionAccountEntryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceShippingOptionAccountEntryRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceShippingOptionAccountEntryRel implementation " +
					commerceShippingOptionAccountEntryRel.getClass());
		}

		CommerceShippingOptionAccountEntryRelModelImpl
			commerceShippingOptionAccountEntryRelModelImpl =
				(CommerceShippingOptionAccountEntryRelModelImpl)
					commerceShippingOptionAccountEntryRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew &&
			(commerceShippingOptionAccountEntryRel.getCreateDate() == null)) {

			if (serviceContext == null) {
				commerceShippingOptionAccountEntryRel.setCreateDate(date);
			}
			else {
				commerceShippingOptionAccountEntryRel.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!commerceShippingOptionAccountEntryRelModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				commerceShippingOptionAccountEntryRel.setModifiedDate(date);
			}
			else {
				commerceShippingOptionAccountEntryRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceShippingOptionAccountEntryRel);
			}
			else {
				commerceShippingOptionAccountEntryRel =
					(CommerceShippingOptionAccountEntryRel)session.merge(
						commerceShippingOptionAccountEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceShippingOptionAccountEntryRelImpl.class,
			commerceShippingOptionAccountEntryRelModelImpl, false, true);

		cacheUniqueFindersCache(commerceShippingOptionAccountEntryRelModelImpl);

		if (isNew) {
			commerceShippingOptionAccountEntryRel.setNew(false);
		}

		commerceShippingOptionAccountEntryRel.resetOriginalValues();

		return commerceShippingOptionAccountEntryRel;
	}

	/**
	 * Returns the commerce shipping option account entry rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce shipping option account entry rel
	 * @return the commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchShippingOptionAccountEntryRelException {

		CommerceShippingOptionAccountEntryRel
			commerceShippingOptionAccountEntryRel = fetchByPrimaryKey(
				primaryKey);

		if (commerceShippingOptionAccountEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchShippingOptionAccountEntryRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceShippingOptionAccountEntryRel;
	}

	/**
	 * Returns the commerce shipping option account entry rel with the primary key or throws a <code>NoSuchShippingOptionAccountEntryRelException</code> if it could not be found.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the commerce shipping option account entry rel
	 * @return the commerce shipping option account entry rel
	 * @throws NoSuchShippingOptionAccountEntryRelException if a commerce shipping option account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel findByPrimaryKey(
			long CommerceShippingOptionAccountEntryRelId)
		throws NoSuchShippingOptionAccountEntryRelException {

		return findByPrimaryKey(
			(Serializable)CommerceShippingOptionAccountEntryRelId);
	}

	/**
	 * Returns the commerce shipping option account entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param CommerceShippingOptionAccountEntryRelId the primary key of the commerce shipping option account entry rel
	 * @return the commerce shipping option account entry rel, or <code>null</code> if a commerce shipping option account entry rel with the primary key could not be found
	 */
	@Override
	public CommerceShippingOptionAccountEntryRel fetchByPrimaryKey(
		long CommerceShippingOptionAccountEntryRelId) {

		return fetchByPrimaryKey(
			(Serializable)CommerceShippingOptionAccountEntryRelId);
	}

	/**
	 * Returns all the commerce shipping option account entry rels.
	 *
	 * @return the commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce shipping option account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @return the range of commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingOptionAccountEntryRel>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce shipping option account entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceShippingOptionAccountEntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce shipping option account entry rels
	 * @param end the upper bound of the range of commerce shipping option account entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce shipping option account entry rels
	 */
	@Override
	public List<CommerceShippingOptionAccountEntryRel> findAll(
		int start, int end,
		OrderByComparator<CommerceShippingOptionAccountEntryRel>
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

		List<CommerceShippingOptionAccountEntryRel> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceShippingOptionAccountEntryRel>)
					finderCache.getResult(finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL;

				sql = sql.concat(
					CommerceShippingOptionAccountEntryRelModelImpl.
						ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list =
					(List<CommerceShippingOptionAccountEntryRel>)QueryUtil.list(
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
	 * Removes all the commerce shipping option account entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceShippingOptionAccountEntryRel
				commerceShippingOptionAccountEntryRel : findAll()) {

			remove(commerceShippingOptionAccountEntryRel);
		}
	}

	/**
	 * Returns the number of commerce shipping option account entry rels.
	 *
	 * @return the number of commerce shipping option account entry rels
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
					_SQL_COUNT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL);

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
		return "CSOptionAccountEntryRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceShippingOptionAccountEntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce shipping option account entry rel persistence.
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

		_finderPathWithPaginationFindByAccountEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByAccountEntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"accountEntryId"}, true);

		_finderPathWithoutPaginationFindByAccountEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByAccountEntryId",
			new String[] {Long.class.getName()},
			new String[] {"accountEntryId"}, true);

		_finderPathCountByAccountEntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByAccountEntryId",
			new String[] {Long.class.getName()},
			new String[] {"accountEntryId"}, false);

		_finderPathWithPaginationFindByCommerceChannelId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCommerceChannelId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceChannelId"}, true);

		_finderPathWithoutPaginationFindByCommerceChannelId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"findByCommerceChannelId", new String[] {Long.class.getName()},
			new String[] {"commerceChannelId"}, true);

		_finderPathCountByCommerceChannelId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceChannelId", new String[] {Long.class.getName()},
			new String[] {"commerceChannelId"}, false);

		_finderPathWithPaginationFindByCommerceShippingOptionKey =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceShippingOptionKey",
				new String[] {
					String.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"commerceShippingOptionKey"}, true);

		_finderPathWithoutPaginationFindByCommerceShippingOptionKey =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceShippingOptionKey",
				new String[] {String.class.getName()},
				new String[] {"commerceShippingOptionKey"}, true);

		_finderPathCountByCommerceShippingOptionKey = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceShippingOptionKey",
			new String[] {String.class.getName()},
			new String[] {"commerceShippingOptionKey"}, false);

		_finderPathFetchByA_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByA_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"accountEntryId", "commerceChannelId"}, true);

		_finderPathCountByA_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByA_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"accountEntryId", "commerceChannelId"}, false);

		CommerceShippingOptionAccountEntryRelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		CommerceShippingOptionAccountEntryRelUtil.setPersistence(null);

		entityCache.removeCache(
			CommerceShippingOptionAccountEntryRelImpl.class.getName());
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
		_SQL_SELECT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL =
			"SELECT commerceShippingOptionAccountEntryRel FROM CommerceShippingOptionAccountEntryRel commerceShippingOptionAccountEntryRel";

	private static final String
		_SQL_SELECT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL_WHERE =
			"SELECT commerceShippingOptionAccountEntryRel FROM CommerceShippingOptionAccountEntryRel commerceShippingOptionAccountEntryRel WHERE ";

	private static final String
		_SQL_COUNT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL =
			"SELECT COUNT(commerceShippingOptionAccountEntryRel) FROM CommerceShippingOptionAccountEntryRel commerceShippingOptionAccountEntryRel";

	private static final String
		_SQL_COUNT_COMMERCESHIPPINGOPTIONACCOUNTENTRYREL_WHERE =
			"SELECT COUNT(commerceShippingOptionAccountEntryRel) FROM CommerceShippingOptionAccountEntryRel commerceShippingOptionAccountEntryRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceShippingOptionAccountEntryRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceShippingOptionAccountEntryRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceShippingOptionAccountEntryRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceShippingOptionAccountEntryRelPersistenceImpl.class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"CommerceShippingOptionAccountEntryRelId"});

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}