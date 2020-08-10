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

package com.liferay.commerce.notification.service.persistence.impl;

import com.liferay.commerce.notification.exception.NoSuchNotificationTemplateCommerceAccountGroupRelException;
import com.liferay.commerce.notification.model.CommerceNotificationTemplateCommerceAccountGroupRel;
import com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateCommerceAccountGroupRelImpl;
import com.liferay.commerce.notification.model.impl.CommerceNotificationTemplateCommerceAccountGroupRelModelImpl;
import com.liferay.commerce.notification.service.persistence.CommerceNotificationTemplateCommerceAccountGroupRelPersistence;
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
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.kernel.util.SetUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The persistence implementation for the commerce notification template commerce account group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Alessio Antonio Rendina
 * @generated
 */
public class CommerceNotificationTemplateCommerceAccountGroupRelPersistenceImpl
	extends BasePersistenceImpl
		<CommerceNotificationTemplateCommerceAccountGroupRel>
	implements CommerceNotificationTemplateCommerceAccountGroupRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceNotificationTemplateCommerceAccountGroupRelUtil</code> to access the commerce notification template commerce account group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceNotificationTemplateCommerceAccountGroupRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath
		_finderPathWithPaginationFindByCommerceNotificationTemplateId;
	private FinderPath
		_finderPathWithoutPaginationFindByCommerceNotificationTemplateId;
	private FinderPath _finderPathCountByCommerceNotificationTemplateId;

	/**
	 * Returns all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the matching commerce notification template commerce account group rels
	 */
	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId) {

		return findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @return the range of matching commerce notification template commerce account group rels
	 */
	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end) {

		return findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification template commerce account group rels
	 */
	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		return findByCommerceNotificationTemplateId(
			commerceNotificationTemplateId, start, end, orderByComparator,
			true);
	}

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification template commerce account group rels
	 */
	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceNotificationTemplateId(
			long commerceNotificationTemplateId, int start, int end,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceNotificationTemplateId;
				finderArgs = new Object[] {commerceNotificationTemplateId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByCommerceNotificationTemplateId;
			finderArgs = new Object[] {
				commerceNotificationTemplateId, start, end, orderByComparator
			};
		}

		List<CommerceNotificationTemplateCommerceAccountGroupRel> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceNotificationTemplateCommerceAccountGroupRel>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationTemplateCommerceAccountGroupRel
						commerceNotificationTemplateCommerceAccountGroupRel :
							list) {

					if (commerceNotificationTemplateId !=
							commerceNotificationTemplateCommerceAccountGroupRel.
								getCommerceNotificationTemplateId()) {

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
				_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCENOTIFICATIONTEMPLATEID_COMMERCENOTIFICATIONTEMPLATEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceNotificationTemplateId);

				list =
					(List<CommerceNotificationTemplateCommerceAccountGroupRel>)
						QueryUtil.list(query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
			findByCommerceNotificationTemplateId_First(
				long commerceNotificationTemplateId,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException {

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel =
				fetchByCommerceNotificationTemplateId_First(
					commerceNotificationTemplateId, orderByComparator);

		if (commerceNotificationTemplateCommerceAccountGroupRel != null) {
			return commerceNotificationTemplateCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceNotificationTemplateId=");
		sb.append(commerceNotificationTemplateId);

		sb.append("}");

		throw new NoSuchNotificationTemplateCommerceAccountGroupRelException(
			sb.toString());
	}

	/**
	 * Returns the first commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByCommerceNotificationTemplateId_First(
			long commerceNotificationTemplateId,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		List<CommerceNotificationTemplateCommerceAccountGroupRel> list =
			findByCommerceNotificationTemplateId(
				commerceNotificationTemplateId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
			findByCommerceNotificationTemplateId_Last(
				long commerceNotificationTemplateId,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException {

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel =
				fetchByCommerceNotificationTemplateId_Last(
					commerceNotificationTemplateId, orderByComparator);

		if (commerceNotificationTemplateCommerceAccountGroupRel != null) {
			return commerceNotificationTemplateCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceNotificationTemplateId=");
		sb.append(commerceNotificationTemplateId);

		sb.append("}");

		throw new NoSuchNotificationTemplateCommerceAccountGroupRelException(
			sb.toString());
	}

	/**
	 * Returns the last commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByCommerceNotificationTemplateId_Last(
			long commerceNotificationTemplateId,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		int count = countByCommerceNotificationTemplateId(
			commerceNotificationTemplateId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationTemplateCommerceAccountGroupRel> list =
			findByCommerceNotificationTemplateId(
				commerceNotificationTemplateId, count - 1, count,
				orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification template commerce account group rels before and after the current commerce notification template commerce account group rel in the ordered set where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the current commerce notification template commerce account group rel
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel[]
			findByCommerceNotificationTemplateId_PrevAndNext(
				long commerceNotificationTemplateCommerceAccountGroupRelId,
				long commerceNotificationTemplateId,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException {

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel =
				findByPrimaryKey(
					commerceNotificationTemplateCommerceAccountGroupRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationTemplateCommerceAccountGroupRel[] array =
				new CommerceNotificationTemplateCommerceAccountGroupRelImpl[3];

			array[0] = getByCommerceNotificationTemplateId_PrevAndNext(
				session, commerceNotificationTemplateCommerceAccountGroupRel,
				commerceNotificationTemplateId, orderByComparator, true);

			array[1] = commerceNotificationTemplateCommerceAccountGroupRel;

			array[2] = getByCommerceNotificationTemplateId_PrevAndNext(
				session, commerceNotificationTemplateCommerceAccountGroupRel,
				commerceNotificationTemplateId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceNotificationTemplateCommerceAccountGroupRel
		getByCommerceNotificationTemplateId_PrevAndNext(
			Session session,
			CommerceNotificationTemplateCommerceAccountGroupRel
				commerceNotificationTemplateCommerceAccountGroupRel,
			long commerceNotificationTemplateId,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
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

		sb.append(
			_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCENOTIFICATIONTEMPLATEID_COMMERCENOTIFICATIONTEMPLATEID_2);

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
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceNotificationTemplateId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceNotificationTemplateCommerceAccountGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceNotificationTemplateCommerceAccountGroupRel> list =
			query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63; from the database.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 */
	@Override
	public void removeByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		for (CommerceNotificationTemplateCommerceAccountGroupRel
				commerceNotificationTemplateCommerceAccountGroupRel :
					findByCommerceNotificationTemplateId(
						commerceNotificationTemplateId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commerceNotificationTemplateCommerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @return the number of matching commerce notification template commerce account group rels
	 */
	@Override
	public int countByCommerceNotificationTemplateId(
		long commerceNotificationTemplateId) {

		FinderPath finderPath =
			_finderPathCountByCommerceNotificationTemplateId;

		Object[] finderArgs = new Object[] {commerceNotificationTemplateId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(
				_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCENOTIFICATIONTEMPLATEID_COMMERCENOTIFICATIONTEMPLATEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceNotificationTemplateId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_COMMERCENOTIFICATIONTEMPLATEID_COMMERCENOTIFICATIONTEMPLATEID_2 =
			"commerceNotificationTemplateCommerceAccountGroupRel.commerceNotificationTemplateId = ?";

	private FinderPath _finderPathWithPaginationFindByCommerceAccountGroupId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceAccountGroupId;
	private FinderPath _finderPathCountByCommerceAccountGroupId;

	/**
	 * Returns all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce notification template commerce account group rels
	 */
	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceAccountGroupId(long commerceAccountGroupId) {

		return findByCommerceAccountGroupId(
			commerceAccountGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @return the range of matching commerce notification template commerce account group rels
	 */
	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end) {

		return findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce notification template commerce account group rels
	 */
	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		return findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce notification template commerce account group rels
	 */
	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel>
		findByCommerceAccountGroupId(
			long commerceAccountGroupId, int start, int end,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator,
			boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByCommerceAccountGroupId;
				finderArgs = new Object[] {commerceAccountGroupId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCommerceAccountGroupId;
			finderArgs = new Object[] {
				commerceAccountGroupId, start, end, orderByComparator
			};
		}

		List<CommerceNotificationTemplateCommerceAccountGroupRel> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceNotificationTemplateCommerceAccountGroupRel>)
					finderCache.getResult(finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceNotificationTemplateCommerceAccountGroupRel
						commerceNotificationTemplateCommerceAccountGroupRel :
							list) {

					if (commerceAccountGroupId !=
							commerceNotificationTemplateCommerceAccountGroupRel.
								getCommerceAccountGroupId()) {

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
				_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEACCOUNTGROUPID_COMMERCEACCOUNTGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(
					CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountGroupId);

				list =
					(List<CommerceNotificationTemplateCommerceAccountGroupRel>)
						QueryUtil.list(query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Returns the first commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
			findByCommerceAccountGroupId_First(
				long commerceAccountGroupId,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException {

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel =
				fetchByCommerceAccountGroupId_First(
					commerceAccountGroupId, orderByComparator);

		if (commerceNotificationTemplateCommerceAccountGroupRel != null) {
			return commerceNotificationTemplateCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountGroupId=");
		sb.append(commerceAccountGroupId);

		sb.append("}");

		throw new NoSuchNotificationTemplateCommerceAccountGroupRelException(
			sb.toString());
	}

	/**
	 * Returns the first commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByCommerceAccountGroupId_First(
			long commerceAccountGroupId,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		List<CommerceNotificationTemplateCommerceAccountGroupRel> list =
			findByCommerceAccountGroupId(
				commerceAccountGroupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
			findByCommerceAccountGroupId_Last(
				long commerceAccountGroupId,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException {

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel =
				fetchByCommerceAccountGroupId_Last(
					commerceAccountGroupId, orderByComparator);

		if (commerceNotificationTemplateCommerceAccountGroupRel != null) {
			return commerceNotificationTemplateCommerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountGroupId=");
		sb.append(commerceAccountGroupId);

		sb.append("}");

		throw new NoSuchNotificationTemplateCommerceAccountGroupRelException(
			sb.toString());
	}

	/**
	 * Returns the last commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByCommerceAccountGroupId_Last(
			long commerceAccountGroupId,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
					orderByComparator) {

		int count = countByCommerceAccountGroupId(commerceAccountGroupId);

		if (count == 0) {
			return null;
		}

		List<CommerceNotificationTemplateCommerceAccountGroupRel> list =
			findByCommerceAccountGroupId(
				commerceAccountGroupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce notification template commerce account group rels before and after the current commerce notification template commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the current commerce notification template commerce account group rel
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel[]
			findByCommerceAccountGroupId_PrevAndNext(
				long commerceNotificationTemplateCommerceAccountGroupRelId,
				long commerceAccountGroupId,
				OrderByComparator
					<CommerceNotificationTemplateCommerceAccountGroupRel>
						orderByComparator)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException {

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel =
				findByPrimaryKey(
					commerceNotificationTemplateCommerceAccountGroupRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationTemplateCommerceAccountGroupRel[] array =
				new CommerceNotificationTemplateCommerceAccountGroupRelImpl[3];

			array[0] = getByCommerceAccountGroupId_PrevAndNext(
				session, commerceNotificationTemplateCommerceAccountGroupRel,
				commerceAccountGroupId, orderByComparator, true);

			array[1] = commerceNotificationTemplateCommerceAccountGroupRel;

			array[2] = getByCommerceAccountGroupId_PrevAndNext(
				session, commerceNotificationTemplateCommerceAccountGroupRel,
				commerceAccountGroupId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected CommerceNotificationTemplateCommerceAccountGroupRel
		getByCommerceAccountGroupId_PrevAndNext(
			Session session,
			CommerceNotificationTemplateCommerceAccountGroupRel
				commerceNotificationTemplateCommerceAccountGroupRel,
			long commerceAccountGroupId,
			OrderByComparator
				<CommerceNotificationTemplateCommerceAccountGroupRel>
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

		sb.append(
			_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL_WHERE);

		sb.append(
			_FINDER_COLUMN_COMMERCEACCOUNTGROUPID_COMMERCEACCOUNTGROUPID_2);

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
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(commerceAccountGroupId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceNotificationTemplateCommerceAccountGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceNotificationTemplateCommerceAccountGroupRel> list =
			query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce notification template commerce account group rels where commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 */
	@Override
	public void removeByCommerceAccountGroupId(long commerceAccountGroupId) {
		for (CommerceNotificationTemplateCommerceAccountGroupRel
				commerceNotificationTemplateCommerceAccountGroupRel :
					findByCommerceAccountGroupId(
						commerceAccountGroupId, QueryUtil.ALL_POS,
						QueryUtil.ALL_POS, null)) {

			remove(commerceNotificationTemplateCommerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce notification template commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce notification template commerce account group rels
	 */
	@Override
	public int countByCommerceAccountGroupId(long commerceAccountGroupId) {
		FinderPath finderPath = _finderPathCountByCommerceAccountGroupId;

		Object[] finderArgs = new Object[] {commerceAccountGroupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(
				_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEACCOUNTGROUPID_COMMERCEACCOUNTGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountGroupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_COMMERCEACCOUNTGROUPID_COMMERCEACCOUNTGROUPID_2 =
			"commerceNotificationTemplateCommerceAccountGroupRel.commerceAccountGroupId = ?";

	private FinderPath _finderPathFetchByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns the commerce notification template commerce account group rel where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63; or throws a <code>NoSuchNotificationTemplateCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a matching commerce notification template commerce account group rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel findByC_C(
			long commerceNotificationTemplateId, long commerceAccountGroupId)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException {

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel = fetchByC_C(
				commerceNotificationTemplateId, commerceAccountGroupId);

		if (commerceNotificationTemplateCommerceAccountGroupRel == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("commerceNotificationTemplateId=");
			sb.append(commerceNotificationTemplateId);

			sb.append(", commerceAccountGroupId=");
			sb.append(commerceAccountGroupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchNotificationTemplateCommerceAccountGroupRelException(
				sb.toString());
		}

		return commerceNotificationTemplateCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce notification template commerce account group rel where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel fetchByC_C(
		long commerceNotificationTemplateId, long commerceAccountGroupId) {

		return fetchByC_C(
			commerceNotificationTemplateId, commerceAccountGroupId, true);
	}

	/**
	 * Returns the commerce notification template commerce account group rel where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce notification template commerce account group rel, or <code>null</code> if a matching commerce notification template commerce account group rel could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel fetchByC_C(
		long commerceNotificationTemplateId, long commerceAccountGroupId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				commerceNotificationTemplateId, commerceAccountGroupId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C, finderArgs, this);
		}

		if (result instanceof
				CommerceNotificationTemplateCommerceAccountGroupRel) {

			CommerceNotificationTemplateCommerceAccountGroupRel
				commerceNotificationTemplateCommerceAccountGroupRel =
					(CommerceNotificationTemplateCommerceAccountGroupRel)result;

			if ((commerceNotificationTemplateId !=
					commerceNotificationTemplateCommerceAccountGroupRel.
						getCommerceNotificationTemplateId()) ||
				(commerceAccountGroupId !=
					commerceNotificationTemplateCommerceAccountGroupRel.
						getCommerceAccountGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(
				_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMMERCENOTIFICATIONTEMPLATEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEACCOUNTGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceNotificationTemplateId);

				queryPos.add(commerceAccountGroupId);

				List<CommerceNotificationTemplateCommerceAccountGroupRel> list =
					query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C, finderArgs, list);
					}
				}
				else {
					CommerceNotificationTemplateCommerceAccountGroupRel
						commerceNotificationTemplateCommerceAccountGroupRel =
							list.get(0);

					result =
						commerceNotificationTemplateCommerceAccountGroupRel;

					cacheResult(
						commerceNotificationTemplateCommerceAccountGroupRel);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(_finderPathFetchByC_C, finderArgs);
				}

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
			return (CommerceNotificationTemplateCommerceAccountGroupRel)result;
		}
	}

	/**
	 * Removes the commerce notification template commerce account group rel where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the commerce notification template commerce account group rel that was removed
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel removeByC_C(
			long commerceNotificationTemplateId, long commerceAccountGroupId)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException {

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel = findByC_C(
				commerceNotificationTemplateId, commerceAccountGroupId);

		return remove(commerceNotificationTemplateCommerceAccountGroupRel);
	}

	/**
	 * Returns the number of commerce notification template commerce account group rels where commerceNotificationTemplateId = &#63; and commerceAccountGroupId = &#63;.
	 *
	 * @param commerceNotificationTemplateId the commerce notification template ID
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce notification template commerce account group rels
	 */
	@Override
	public int countByC_C(
		long commerceNotificationTemplateId, long commerceAccountGroupId) {

		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {
			commerceNotificationTemplateId, commerceAccountGroupId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(
				_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_COMMERCENOTIFICATIONTEMPLATEID_2);

			sb.append(_FINDER_COLUMN_C_C_COMMERCEACCOUNTGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceNotificationTemplateId);

				queryPos.add(commerceAccountGroupId);

				count = (Long)query.uniqueResult();

				finderCache.putResult(finderPath, finderArgs, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(finderPath, finderArgs);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return count.intValue();
	}

	private static final String
		_FINDER_COLUMN_C_C_COMMERCENOTIFICATIONTEMPLATEID_2 =
			"commerceNotificationTemplateCommerceAccountGroupRel.commerceNotificationTemplateId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_COMMERCEACCOUNTGROUPID_2 =
		"commerceNotificationTemplateCommerceAccountGroupRel.commerceAccountGroupId = ?";

	public CommerceNotificationTemplateCommerceAccountGroupRelPersistenceImpl() {
		Map<String, String> dbColumnNames = new HashMap<String, String>();

		dbColumnNames.put(
			"commerceNotificationTemplateCommerceAccountGroupRelId",
			"CNTemplateCAccountGroupRelId");

		try {
			Field field = BasePersistenceImpl.class.getDeclaredField(
				"_dbColumnNames");

			field.setAccessible(true);

			field.set(this, dbColumnNames);
		}
		catch (Exception exception) {
			if (_log.isDebugEnabled()) {
				_log.debug(exception, exception);
			}
		}

		setModelClass(
			CommerceNotificationTemplateCommerceAccountGroupRel.class);
	}

	/**
	 * Caches the commerce notification template commerce account group rel in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRel the commerce notification template commerce account group rel
	 */
	@Override
	public void cacheResult(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel) {

		entityCache.putResult(
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
			commerceNotificationTemplateCommerceAccountGroupRel.getPrimaryKey(),
			commerceNotificationTemplateCommerceAccountGroupRel);

		finderCache.putResult(
			_finderPathFetchByC_C,
			new Object[] {
				commerceNotificationTemplateCommerceAccountGroupRel.
					getCommerceNotificationTemplateId(),
				commerceNotificationTemplateCommerceAccountGroupRel.
					getCommerceAccountGroupId()
			},
			commerceNotificationTemplateCommerceAccountGroupRel);

		commerceNotificationTemplateCommerceAccountGroupRel.
			resetOriginalValues();
	}

	/**
	 * Caches the commerce notification template commerce account group rels in the entity cache if it is enabled.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRels the commerce notification template commerce account group rels
	 */
	@Override
	public void cacheResult(
		List<CommerceNotificationTemplateCommerceAccountGroupRel>
			commerceNotificationTemplateCommerceAccountGroupRels) {

		for (CommerceNotificationTemplateCommerceAccountGroupRel
				commerceNotificationTemplateCommerceAccountGroupRel :
					commerceNotificationTemplateCommerceAccountGroupRels) {

			if (entityCache.getResult(
					CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationTemplateCommerceAccountGroupRelImpl.
						class,
					commerceNotificationTemplateCommerceAccountGroupRel.
						getPrimaryKey()) == null) {

				cacheResult(
					commerceNotificationTemplateCommerceAccountGroupRel);
			}
			else {
				commerceNotificationTemplateCommerceAccountGroupRel.
					resetOriginalValues();
			}
		}
	}

	/**
	 * Clears the cache for all commerce notification template commerce account group rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(
			CommerceNotificationTemplateCommerceAccountGroupRelImpl.class);

		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	/**
	 * Clears the cache for the commerce notification template commerce account group rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel) {

		entityCache.removeResult(
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
			commerceNotificationTemplateCommerceAccountGroupRel.
				getPrimaryKey());

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		clearUniqueFindersCache(
			(CommerceNotificationTemplateCommerceAccountGroupRelModelImpl)
				commerceNotificationTemplateCommerceAccountGroupRel,
			true);
	}

	@Override
	public void clearCache(
		List<CommerceNotificationTemplateCommerceAccountGroupRel>
			commerceNotificationTemplateCommerceAccountGroupRels) {

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (CommerceNotificationTemplateCommerceAccountGroupRel
				commerceNotificationTemplateCommerceAccountGroupRel :
					commerceNotificationTemplateCommerceAccountGroupRels) {

			entityCache.removeResult(
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					ENTITY_CACHE_ENABLED,
				CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
				commerceNotificationTemplateCommerceAccountGroupRel.
					getPrimaryKey());

			clearUniqueFindersCache(
				(CommerceNotificationTemplateCommerceAccountGroupRelModelImpl)
					commerceNotificationTemplateCommerceAccountGroupRel,
				true);
		}
	}

	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					ENTITY_CACHE_ENABLED,
				CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
				primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceNotificationTemplateCommerceAccountGroupRelModelImpl
			commerceNotificationTemplateCommerceAccountGroupRelModelImpl) {

		Object[] args = new Object[] {
			commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				getCommerceNotificationTemplateId(),
			commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				getCommerceAccountGroupId()
		};

		finderCache.putResult(
			_finderPathCountByC_C, args, Long.valueOf(1), false);
		finderCache.putResult(
			_finderPathFetchByC_C, args,
			commerceNotificationTemplateCommerceAccountGroupRelModelImpl,
			false);
	}

	protected void clearUniqueFindersCache(
		CommerceNotificationTemplateCommerceAccountGroupRelModelImpl
			commerceNotificationTemplateCommerceAccountGroupRelModelImpl,
		boolean clearCurrent) {

		if (clearCurrent) {
			Object[] args = new Object[] {
				commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					getCommerceNotificationTemplateId(),
				commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					getCommerceAccountGroupId()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}

		if ((commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				getColumnBitmask() &
			 _finderPathFetchByC_C.getColumnBitmask()) != 0) {

			Object[] args = new Object[] {
				commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					getOriginalCommerceNotificationTemplateId(),
				commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					getOriginalCommerceAccountGroupId()
			};

			finderCache.removeResult(_finderPathCountByC_C, args);
			finderCache.removeResult(_finderPathFetchByC_C, args);
		}
	}

	/**
	 * Creates a new commerce notification template commerce account group rel with the primary key. Does not add the commerce notification template commerce account group rel to the database.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key for the new commerce notification template commerce account group rel
	 * @return the new commerce notification template commerce account group rel
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel create(
		long commerceNotificationTemplateCommerceAccountGroupRelId) {

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel =
				new CommerceNotificationTemplateCommerceAccountGroupRelImpl();

		commerceNotificationTemplateCommerceAccountGroupRel.setNew(true);
		commerceNotificationTemplateCommerceAccountGroupRel.setPrimaryKey(
			commerceNotificationTemplateCommerceAccountGroupRelId);

		commerceNotificationTemplateCommerceAccountGroupRel.setCompanyId(
			CompanyThreadLocal.getCompanyId());

		return commerceNotificationTemplateCommerceAccountGroupRel;
	}

	/**
	 * Removes the commerce notification template commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the commerce notification template commerce account group rel
	 * @return the commerce notification template commerce account group rel that was removed
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel remove(
			long commerceNotificationTemplateCommerceAccountGroupRelId)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException {

		return remove(
			(Serializable)
				commerceNotificationTemplateCommerceAccountGroupRelId);
	}

	/**
	 * Removes the commerce notification template commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce notification template commerce account group rel
	 * @return the commerce notification template commerce account group rel that was removed
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel remove(
			Serializable primaryKey)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceNotificationTemplateCommerceAccountGroupRel
				commerceNotificationTemplateCommerceAccountGroupRel =
					(CommerceNotificationTemplateCommerceAccountGroupRel)
						session.get(
							CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
							primaryKey);

			if (commerceNotificationTemplateCommerceAccountGroupRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchNotificationTemplateCommerceAccountGroupRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceNotificationTemplateCommerceAccountGroupRel);
		}
		catch (NoSuchNotificationTemplateCommerceAccountGroupRelException
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
	protected CommerceNotificationTemplateCommerceAccountGroupRel removeImpl(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(
					commerceNotificationTemplateCommerceAccountGroupRel)) {

				commerceNotificationTemplateCommerceAccountGroupRel =
					(CommerceNotificationTemplateCommerceAccountGroupRel)
						session.get(
							CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
							commerceNotificationTemplateCommerceAccountGroupRel.
								getPrimaryKeyObj());
			}

			if (commerceNotificationTemplateCommerceAccountGroupRel != null) {
				session.delete(
					commerceNotificationTemplateCommerceAccountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceNotificationTemplateCommerceAccountGroupRel != null) {
			clearCache(commerceNotificationTemplateCommerceAccountGroupRel);
		}

		return commerceNotificationTemplateCommerceAccountGroupRel;
	}

	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel updateImpl(
		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel) {

		boolean isNew =
			commerceNotificationTemplateCommerceAccountGroupRel.isNew();

		if (!(commerceNotificationTemplateCommerceAccountGroupRel instanceof
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(
					commerceNotificationTemplateCommerceAccountGroupRel.
						getClass())) {

				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceNotificationTemplateCommerceAccountGroupRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceNotificationTemplateCommerceAccountGroupRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceNotificationTemplateCommerceAccountGroupRel implementation " +
					commerceNotificationTemplateCommerceAccountGroupRel.
						getClass());
		}

		CommerceNotificationTemplateCommerceAccountGroupRelModelImpl
			commerceNotificationTemplateCommerceAccountGroupRelModelImpl =
				(CommerceNotificationTemplateCommerceAccountGroupRelModelImpl)
					commerceNotificationTemplateCommerceAccountGroupRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew &&
			(commerceNotificationTemplateCommerceAccountGroupRel.
				getCreateDate() == null)) {

			if (serviceContext == null) {
				commerceNotificationTemplateCommerceAccountGroupRel.
					setCreateDate(now);
			}
			else {
				commerceNotificationTemplateCommerceAccountGroupRel.
					setCreateDate(serviceContext.getCreateDate(now));
			}
		}

		if (!commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				hasSetModifiedDate()) {

			if (serviceContext == null) {
				commerceNotificationTemplateCommerceAccountGroupRel.
					setModifiedDate(now);
			}
			else {
				commerceNotificationTemplateCommerceAccountGroupRel.
					setModifiedDate(serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (commerceNotificationTemplateCommerceAccountGroupRel.isNew()) {
				session.save(
					commerceNotificationTemplateCommerceAccountGroupRel);

				commerceNotificationTemplateCommerceAccountGroupRel.setNew(
					false);
			}
			else {
				commerceNotificationTemplateCommerceAccountGroupRel =
					(CommerceNotificationTemplateCommerceAccountGroupRel)
						session.merge(
							commerceNotificationTemplateCommerceAccountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);

		if (!CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				COLUMN_BITMASK_ENABLED) {

			finderCache.clearCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
		}
		else if (isNew) {
			Object[] args = new Object[] {
				commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					getCommerceNotificationTemplateId()
			};

			finderCache.removeResult(
				_finderPathCountByCommerceNotificationTemplateId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCommerceNotificationTemplateId,
				args);

			args = new Object[] {
				commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					getCommerceAccountGroupId()
			};

			finderCache.removeResult(
				_finderPathCountByCommerceAccountGroupId, args);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindByCommerceAccountGroupId, args);

			finderCache.removeResult(_finderPathCountAll, FINDER_ARGS_EMPTY);
			finderCache.removeResult(
				_finderPathWithoutPaginationFindAll, FINDER_ARGS_EMPTY);
		}
		else {
			if ((commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCommerceNotificationTemplateId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
						getOriginalCommerceNotificationTemplateId()
				};

				finderCache.removeResult(
					_finderPathCountByCommerceNotificationTemplateId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCommerceNotificationTemplateId,
					args);

				args = new Object[] {
					commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
						getCommerceNotificationTemplateId()
				};

				finderCache.removeResult(
					_finderPathCountByCommerceNotificationTemplateId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCommerceNotificationTemplateId,
					args);
			}

			if ((commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					getColumnBitmask() &
				 _finderPathWithoutPaginationFindByCommerceAccountGroupId.
					 getColumnBitmask()) != 0) {

				Object[] args = new Object[] {
					commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
						getOriginalCommerceAccountGroupId()
				};

				finderCache.removeResult(
					_finderPathCountByCommerceAccountGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCommerceAccountGroupId,
					args);

				args = new Object[] {
					commerceNotificationTemplateCommerceAccountGroupRelModelImpl.
						getCommerceAccountGroupId()
				};

				finderCache.removeResult(
					_finderPathCountByCommerceAccountGroupId, args);
				finderCache.removeResult(
					_finderPathWithoutPaginationFindByCommerceAccountGroupId,
					args);
			}
		}

		entityCache.putResult(
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
			commerceNotificationTemplateCommerceAccountGroupRel.getPrimaryKey(),
			commerceNotificationTemplateCommerceAccountGroupRel, false);

		clearUniqueFindersCache(
			commerceNotificationTemplateCommerceAccountGroupRelModelImpl,
			false);
		cacheUniqueFindersCache(
			commerceNotificationTemplateCommerceAccountGroupRelModelImpl);

		commerceNotificationTemplateCommerceAccountGroupRel.
			resetOriginalValues();

		return commerceNotificationTemplateCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce notification template commerce account group rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce notification template commerce account group rel
	 * @return the commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel findByPrimaryKey(
			Serializable primaryKey)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException {

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel =
				fetchByPrimaryKey(primaryKey);

		if (commerceNotificationTemplateCommerceAccountGroupRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchNotificationTemplateCommerceAccountGroupRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceNotificationTemplateCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce notification template commerce account group rel with the primary key or throws a <code>NoSuchNotificationTemplateCommerceAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the commerce notification template commerce account group rel
	 * @return the commerce notification template commerce account group rel
	 * @throws NoSuchNotificationTemplateCommerceAccountGroupRelException if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel findByPrimaryKey(
			long commerceNotificationTemplateCommerceAccountGroupRelId)
		throws NoSuchNotificationTemplateCommerceAccountGroupRelException {

		return findByPrimaryKey(
			(Serializable)
				commerceNotificationTemplateCommerceAccountGroupRelId);
	}

	/**
	 * Returns the commerce notification template commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce notification template commerce account group rel
	 * @return the commerce notification template commerce account group rel, or <code>null</code> if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByPrimaryKey(Serializable primaryKey) {

		Serializable serializable = entityCache.getResult(
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
			primaryKey);

		if (serializable == nullModel) {
			return null;
		}

		CommerceNotificationTemplateCommerceAccountGroupRel
			commerceNotificationTemplateCommerceAccountGroupRel =
				(CommerceNotificationTemplateCommerceAccountGroupRel)
					serializable;

		if (commerceNotificationTemplateCommerceAccountGroupRel == null) {
			Session session = null;

			try {
				session = openSession();

				commerceNotificationTemplateCommerceAccountGroupRel =
					(CommerceNotificationTemplateCommerceAccountGroupRel)
						session.get(
							CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
							primaryKey);

				if (commerceNotificationTemplateCommerceAccountGroupRel !=
						null) {

					cacheResult(
						commerceNotificationTemplateCommerceAccountGroupRel);
				}
				else {
					entityCache.putResult(
						CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.ENTITY_CACHE_ENABLED,
						CommerceNotificationTemplateCommerceAccountGroupRelImpl.
							class,
						primaryKey, nullModel);
				}
			}
			catch (Exception exception) {
				entityCache.removeResult(
					CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationTemplateCommerceAccountGroupRelImpl.
						class,
					primaryKey);

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return commerceNotificationTemplateCommerceAccountGroupRel;
	}

	/**
	 * Returns the commerce notification template commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceNotificationTemplateCommerceAccountGroupRelId the primary key of the commerce notification template commerce account group rel
	 * @return the commerce notification template commerce account group rel, or <code>null</code> if a commerce notification template commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceNotificationTemplateCommerceAccountGroupRel
		fetchByPrimaryKey(
			long commerceNotificationTemplateCommerceAccountGroupRelId) {

		return fetchByPrimaryKey(
			(Serializable)
				commerceNotificationTemplateCommerceAccountGroupRelId);
	}

	@Override
	public Map
		<Serializable, CommerceNotificationTemplateCommerceAccountGroupRel>
			fetchByPrimaryKeys(Set<Serializable> primaryKeys) {

		if (primaryKeys.isEmpty()) {
			return Collections.emptyMap();
		}

		Map<Serializable, CommerceNotificationTemplateCommerceAccountGroupRel>
			map =
				new HashMap
					<Serializable,
					 CommerceNotificationTemplateCommerceAccountGroupRel>();

		if (primaryKeys.size() == 1) {
			Iterator<Serializable> iterator = primaryKeys.iterator();

			Serializable primaryKey = iterator.next();

			CommerceNotificationTemplateCommerceAccountGroupRel
				commerceNotificationTemplateCommerceAccountGroupRel =
					fetchByPrimaryKey(primaryKey);

			if (commerceNotificationTemplateCommerceAccountGroupRel != null) {
				map.put(
					primaryKey,
					commerceNotificationTemplateCommerceAccountGroupRel);
			}

			return map;
		}

		Set<Serializable> uncachedPrimaryKeys = null;

		for (Serializable primaryKey : primaryKeys) {
			Serializable serializable = entityCache.getResult(
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					ENTITY_CACHE_ENABLED,
				CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
				primaryKey);

			if (serializable != nullModel) {
				if (serializable == null) {
					if (uncachedPrimaryKeys == null) {
						uncachedPrimaryKeys = new HashSet<Serializable>();
					}

					uncachedPrimaryKeys.add(primaryKey);
				}
				else {
					map.put(
						primaryKey,
						(CommerceNotificationTemplateCommerceAccountGroupRel)
							serializable);
				}
			}
		}

		if (uncachedPrimaryKeys == null) {
			return map;
		}

		StringBundler sb = new StringBundler(
			uncachedPrimaryKeys.size() * 2 + 1);

		sb.append(
			_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL_WHERE_PKS_IN);

		for (Serializable primaryKey : uncachedPrimaryKeys) {
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

			for (CommerceNotificationTemplateCommerceAccountGroupRel
					commerceNotificationTemplateCommerceAccountGroupRel :
						(List
							<CommerceNotificationTemplateCommerceAccountGroupRel>)
								query.list()) {

				map.put(
					commerceNotificationTemplateCommerceAccountGroupRel.
						getPrimaryKeyObj(),
					commerceNotificationTemplateCommerceAccountGroupRel);

				cacheResult(
					commerceNotificationTemplateCommerceAccountGroupRel);

				uncachedPrimaryKeys.remove(
					commerceNotificationTemplateCommerceAccountGroupRel.
						getPrimaryKeyObj());
			}

			for (Serializable primaryKey : uncachedPrimaryKeys) {
				entityCache.putResult(
					CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.ENTITY_CACHE_ENABLED,
					CommerceNotificationTemplateCommerceAccountGroupRelImpl.
						class,
					primaryKey, nullModel);
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
	 * Returns all the commerce notification template commerce account group rels.
	 *
	 * @return the commerce notification template commerce account group rels
	 */
	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce notification template commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @return the range of commerce notification template commerce account group rels
	 */
	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel> findAll(
		int start, int end) {

		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce notification template commerce account group rels
	 */
	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationTemplateCommerceAccountGroupRel>
			orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce notification template commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceNotificationTemplateCommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce notification template commerce account group rels
	 * @param end the upper bound of the range of commerce notification template commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce notification template commerce account group rels
	 */
	@Override
	public List<CommerceNotificationTemplateCommerceAccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommerceNotificationTemplateCommerceAccountGroupRel>
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

		List<CommerceNotificationTemplateCommerceAccountGroupRel> list = null;

		if (useFinderCache) {
			list =
				(List<CommerceNotificationTemplateCommerceAccountGroupRel>)
					finderCache.getResult(finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(
					_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql =
					_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL;

				sql = sql.concat(
					CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list =
					(List<CommerceNotificationTemplateCommerceAccountGroupRel>)
						QueryUtil.list(query, getDialect(), start, end);

				cacheResult(list);

				if (useFinderCache) {
					finderCache.putResult(finderPath, finderArgs, list);
				}
			}
			catch (Exception exception) {
				if (useFinderCache) {
					finderCache.removeResult(finderPath, finderArgs);
				}

				throw processException(exception);
			}
			finally {
				closeSession(session);
			}
		}

		return list;
	}

	/**
	 * Removes all the commerce notification template commerce account group rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceNotificationTemplateCommerceAccountGroupRel
				commerceNotificationTemplateCommerceAccountGroupRel :
					findAll()) {

			remove(commerceNotificationTemplateCommerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce notification template commerce account group rels.
	 *
	 * @return the number of commerce notification template commerce account group rels
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
					_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL);

				count = (Long)query.uniqueResult();

				finderCache.putResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY, count);
			}
			catch (Exception exception) {
				finderCache.removeResult(
					_finderPathCountAll, FINDER_ARGS_EMPTY);

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
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
			TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce notification template commerce account group rel persistence.
	 */
	public void afterPropertiesSet() {
		_finderPathWithPaginationFindAll = new FinderPath(
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0]);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll",
			new String[0]);

		_finderPathCountAll = new FinderPath(
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0]);

		_finderPathWithPaginationFindByCommerceNotificationTemplateId =
			new FinderPath(
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					ENTITY_CACHE_ENABLED,
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					FINDER_CACHE_ENABLED,
				CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByCommerceNotificationTemplateId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				});

		_finderPathWithoutPaginationFindByCommerceNotificationTemplateId =
			new FinderPath(
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					ENTITY_CACHE_ENABLED,
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					FINDER_CACHE_ENABLED,
				CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceNotificationTemplateId",
				new String[] {Long.class.getName()},
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					COMMERCENOTIFICATIONTEMPLATEID_COLUMN_BITMASK |
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByCommerceNotificationTemplateId = new FinderPath(
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceNotificationTemplateId",
			new String[] {Long.class.getName()});

		_finderPathWithPaginationFindByCommerceAccountGroupId = new FinderPath(
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceAccountGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			});

		_finderPathWithoutPaginationFindByCommerceAccountGroupId =
			new FinderPath(
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					ENTITY_CACHE_ENABLED,
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					FINDER_CACHE_ENABLED,
				CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceAccountGroupId",
				new String[] {Long.class.getName()},
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					COMMERCEACCOUNTGROUPID_COLUMN_BITMASK |
				CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
					CREATEDATE_COLUMN_BITMASK);

		_finderPathCountByCommerceAccountGroupId = new FinderPath(
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceAccountGroupId",
			new String[] {Long.class.getName()});

		_finderPathFetchByC_C = new FinderPath(
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				FINDER_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelImpl.class,
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				COMMERCENOTIFICATIONTEMPLATEID_COLUMN_BITMASK |
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				COMMERCEACCOUNTGROUPID_COLUMN_BITMASK);

		_finderPathCountByC_C = new FinderPath(
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				ENTITY_CACHE_ENABLED,
			CommerceNotificationTemplateCommerceAccountGroupRelModelImpl.
				FINDER_CACHE_ENABLED,
			Long.class, FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()});
	}

	public void destroy() {
		entityCache.removeCache(
			CommerceNotificationTemplateCommerceAccountGroupRelImpl.class.
				getName());
		finderCache.removeCache(FINDER_CLASS_NAME_ENTITY);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITH_PAGINATION);
		finderCache.removeCache(FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION);
	}

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String
		_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL =
			"SELECT commerceNotificationTemplateCommerceAccountGroupRel FROM CommerceNotificationTemplateCommerceAccountGroupRel commerceNotificationTemplateCommerceAccountGroupRel";

	private static final String
		_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL_WHERE_PKS_IN =
			"SELECT commerceNotificationTemplateCommerceAccountGroupRel FROM CommerceNotificationTemplateCommerceAccountGroupRel commerceNotificationTemplateCommerceAccountGroupRel WHERE CNTemplateCAccountGroupRelId IN (";

	private static final String
		_SQL_SELECT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL_WHERE =
			"SELECT commerceNotificationTemplateCommerceAccountGroupRel FROM CommerceNotificationTemplateCommerceAccountGroupRel commerceNotificationTemplateCommerceAccountGroupRel WHERE ";

	private static final String
		_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL =
			"SELECT COUNT(commerceNotificationTemplateCommerceAccountGroupRel) FROM CommerceNotificationTemplateCommerceAccountGroupRel commerceNotificationTemplateCommerceAccountGroupRel";

	private static final String
		_SQL_COUNT_COMMERCENOTIFICATIONTEMPLATECOMMERCEACCOUNTGROUPREL_WHERE =
			"SELECT COUNT(commerceNotificationTemplateCommerceAccountGroupRel) FROM CommerceNotificationTemplateCommerceAccountGroupRel commerceNotificationTemplateCommerceAccountGroupRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceNotificationTemplateCommerceAccountGroupRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceNotificationTemplateCommerceAccountGroupRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceNotificationTemplateCommerceAccountGroupRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceNotificationTemplateCommerceAccountGroupRelPersistenceImpl.
			class);

	private static final Set<String> _badColumnNames = SetUtil.fromArray(
		new String[] {"commerceNotificationTemplateCommerceAccountGroupRelId"});

}