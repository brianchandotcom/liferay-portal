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

package com.liferay.commerce.account.service.persistence.impl;

import com.liferay.commerce.account.exception.NoSuchAccountGroupRelException;
import com.liferay.commerce.account.model.CommerceAccountGroupRel;
import com.liferay.commerce.account.model.CommerceAccountGroupRelTable;
import com.liferay.commerce.account.model.impl.CommerceAccountGroupRelImpl;
import com.liferay.commerce.account.model.impl.CommerceAccountGroupRelModelImpl;
import com.liferay.commerce.account.service.persistence.CommerceAccountGroupRelPersistence;
import com.liferay.petra.string.StringBundler;
import com.liferay.portal.kernel.dao.orm.ArgumentsResolver;
import com.liferay.portal.kernel.dao.orm.EntityCache;
import com.liferay.portal.kernel.dao.orm.FinderCache;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.model.BaseModel;
import com.liferay.portal.kernel.security.auth.CompanyThreadLocal;
import com.liferay.portal.kernel.service.ServiceContext;
import com.liferay.portal.kernel.service.ServiceContextThreadLocal;
import com.liferay.portal.kernel.service.persistence.impl.BasePersistenceImpl;
import com.liferay.portal.kernel.util.HashMapDictionary;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.ProxyUtil;
import com.liferay.portal.spring.extender.service.ServiceReference;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.osgi.framework.Bundle;
import org.osgi.framework.BundleContext;
import org.osgi.framework.FrameworkUtil;
import org.osgi.framework.ServiceRegistration;

/**
 * The persistence implementation for the commerce account group rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Marco Leo
 * @generated
 */
public class CommerceAccountGroupRelPersistenceImpl
	extends BasePersistenceImpl<CommerceAccountGroupRel>
	implements CommerceAccountGroupRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>CommerceAccountGroupRelUtil</code> to access the commerce account group rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		CommerceAccountGroupRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCommerceAccountGroupId;
	private FinderPath _finderPathWithoutPaginationFindByCommerceAccountGroupId;
	private FinderPath _finderPathCountByCommerceAccountGroupId;

	/**
	 * Returns all the commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account group rels
	 */
	@Override
	public List<CommerceAccountGroupRel> findByCommerceAccountGroupId(
		long commerceAccountGroupId) {

		return findByCommerceAccountGroupId(
			commerceAccountGroupId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @return the range of matching commerce account group rels
	 */
	@Override
	public List<CommerceAccountGroupRel> findByCommerceAccountGroupId(
		long commerceAccountGroupId, int start, int end) {

		return findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account group rels
	 */
	@Override
	public List<CommerceAccountGroupRel> findByCommerceAccountGroupId(
		long commerceAccountGroupId, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return findByCommerceAccountGroupId(
			commerceAccountGroupId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account group rels
	 */
	@Override
	public List<CommerceAccountGroupRel> findByCommerceAccountGroupId(
		long commerceAccountGroupId, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator,
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

		List<CommerceAccountGroupRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceAccountGroupRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAccountGroupRel commerceAccountGroupRel : list) {
					if (commerceAccountGroupId !=
							commerceAccountGroupRel.
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

			sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(
				_FINDER_COLUMN_COMMERCEACCOUNTGROUPID_COMMERCEACCOUNTGROUPID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAccountGroupRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(commerceAccountGroupId);

				list = (List<CommerceAccountGroupRel>)QueryUtil.list(
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
	 * Returns the first commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	@Override
	public CommerceAccountGroupRel findByCommerceAccountGroupId_First(
			long commerceAccountGroupId,
			OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws NoSuchAccountGroupRelException {

		CommerceAccountGroupRel commerceAccountGroupRel =
			fetchByCommerceAccountGroupId_First(
				commerceAccountGroupId, orderByComparator);

		if (commerceAccountGroupRel != null) {
			return commerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountGroupId=");
		sb.append(commerceAccountGroupId);

		sb.append("}");

		throw new NoSuchAccountGroupRelException(sb.toString());
	}

	/**
	 * Returns the first commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	@Override
	public CommerceAccountGroupRel fetchByCommerceAccountGroupId_First(
		long commerceAccountGroupId,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		List<CommerceAccountGroupRel> list = findByCommerceAccountGroupId(
			commerceAccountGroupId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	@Override
	public CommerceAccountGroupRel findByCommerceAccountGroupId_Last(
			long commerceAccountGroupId,
			OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws NoSuchAccountGroupRelException {

		CommerceAccountGroupRel commerceAccountGroupRel =
			fetchByCommerceAccountGroupId_Last(
				commerceAccountGroupId, orderByComparator);

		if (commerceAccountGroupRel != null) {
			return commerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("commerceAccountGroupId=");
		sb.append(commerceAccountGroupId);

		sb.append("}");

		throw new NoSuchAccountGroupRelException(sb.toString());
	}

	/**
	 * Returns the last commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	@Override
	public CommerceAccountGroupRel fetchByCommerceAccountGroupId_Last(
		long commerceAccountGroupId,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		int count = countByCommerceAccountGroupId(commerceAccountGroupId);

		if (count == 0) {
			return null;
		}

		List<CommerceAccountGroupRel> list = findByCommerceAccountGroupId(
			commerceAccountGroupId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce account group rels before and after the current commerce account group rel in the ordered set where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupRelId the primary key of the current commerce account group rel
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroupRel[] findByCommerceAccountGroupId_PrevAndNext(
			long commerceAccountGroupRelId, long commerceAccountGroupId,
			OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws NoSuchAccountGroupRelException {

		CommerceAccountGroupRel commerceAccountGroupRel = findByPrimaryKey(
			commerceAccountGroupRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceAccountGroupRel[] array =
				new CommerceAccountGroupRelImpl[3];

			array[0] = getByCommerceAccountGroupId_PrevAndNext(
				session, commerceAccountGroupRel, commerceAccountGroupId,
				orderByComparator, true);

			array[1] = commerceAccountGroupRel;

			array[2] = getByCommerceAccountGroupId_PrevAndNext(
				session, commerceAccountGroupRel, commerceAccountGroupId,
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

	protected CommerceAccountGroupRel getByCommerceAccountGroupId_PrevAndNext(
		Session session, CommerceAccountGroupRel commerceAccountGroupRel,
		long commerceAccountGroupId,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUPREL_WHERE);

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
			sb.append(CommerceAccountGroupRelModelImpl.ORDER_BY_JPQL);
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
						commerceAccountGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAccountGroupRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce account group rels where commerceAccountGroupId = &#63; from the database.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 */
	@Override
	public void removeByCommerceAccountGroupId(long commerceAccountGroupId) {
		for (CommerceAccountGroupRel commerceAccountGroupRel :
				findByCommerceAccountGroupId(
					commerceAccountGroupId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(commerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce account group rels where commerceAccountGroupId = &#63;.
	 *
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account group rels
	 */
	@Override
	public int countByCommerceAccountGroupId(long commerceAccountGroupId) {
		FinderPath finderPath = _finderPathCountByCommerceAccountGroupId;

		Object[] finderArgs = new Object[] {commerceAccountGroupId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_COMMERCEACCOUNTGROUPREL_WHERE);

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
			"commerceAccountGroupRel.commerceAccountGroupId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the matching commerce account group rels
	 */
	@Override
	public List<CommerceAccountGroupRel> findByC_C(
		long classNameId, long classPK) {

		return findByC_C(
			classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @return the range of matching commerce account group rels
	 */
	@Override
	public List<CommerceAccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end) {

		return findByC_C(classNameId, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching commerce account group rels
	 */
	@Override
	public List<CommerceAccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return findByC_C(
			classNameId, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching commerce account group rels
	 */
	@Override
	public List<CommerceAccountGroupRel> findByC_C(
		long classNameId, long classPK, int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, classPK, start, end, orderByComparator
			};
		}

		List<CommerceAccountGroupRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceAccountGroupRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (CommerceAccountGroupRel commerceAccountGroupRel : list) {
					if ((classNameId !=
							commerceAccountGroupRel.getClassNameId()) ||
						(classPK != commerceAccountGroupRel.getClassPK())) {

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

			sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(CommerceAccountGroupRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				list = (List<CommerceAccountGroupRel>)QueryUtil.list(
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
	 * Returns the first commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	@Override
	public CommerceAccountGroupRel findByC_C_First(
			long classNameId, long classPK,
			OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws NoSuchAccountGroupRelException {

		CommerceAccountGroupRel commerceAccountGroupRel = fetchByC_C_First(
			classNameId, classPK, orderByComparator);

		if (commerceAccountGroupRel != null) {
			return commerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchAccountGroupRelException(sb.toString());
	}

	/**
	 * Returns the first commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	@Override
	public CommerceAccountGroupRel fetchByC_C_First(
		long classNameId, long classPK,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		List<CommerceAccountGroupRel> list = findByC_C(
			classNameId, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	@Override
	public CommerceAccountGroupRel findByC_C_Last(
			long classNameId, long classPK,
			OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws NoSuchAccountGroupRelException {

		CommerceAccountGroupRel commerceAccountGroupRel = fetchByC_C_Last(
			classNameId, classPK, orderByComparator);

		if (commerceAccountGroupRel != null) {
			return commerceAccountGroupRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchAccountGroupRelException(sb.toString());
	}

	/**
	 * Returns the last commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	@Override
	public CommerceAccountGroupRel fetchByC_C_Last(
		long classNameId, long classPK,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		int count = countByC_C(classNameId, classPK);

		if (count == 0) {
			return null;
		}

		List<CommerceAccountGroupRel> list = findByC_C(
			classNameId, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the commerce account group rels before and after the current commerce account group rel in the ordered set where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param commerceAccountGroupRelId the primary key of the current commerce account group rel
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroupRel[] findByC_C_PrevAndNext(
			long commerceAccountGroupRelId, long classNameId, long classPK,
			OrderByComparator<CommerceAccountGroupRel> orderByComparator)
		throws NoSuchAccountGroupRelException {

		CommerceAccountGroupRel commerceAccountGroupRel = findByPrimaryKey(
			commerceAccountGroupRelId);

		Session session = null;

		try {
			session = openSession();

			CommerceAccountGroupRel[] array =
				new CommerceAccountGroupRelImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, commerceAccountGroupRel, classNameId, classPK,
				orderByComparator, true);

			array[1] = commerceAccountGroupRel;

			array[2] = getByC_C_PrevAndNext(
				session, commerceAccountGroupRel, classNameId, classPK,
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

	protected CommerceAccountGroupRel getByC_C_PrevAndNext(
		Session session, CommerceAccountGroupRel commerceAccountGroupRel,
		long classNameId, long classPK,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator,
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

		sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUPREL_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

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
			sb.append(CommerceAccountGroupRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						commerceAccountGroupRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<CommerceAccountGroupRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the commerce account group rels where classNameId = &#63; and classPK = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 */
	@Override
	public void removeByC_C(long classNameId, long classPK) {
		for (CommerceAccountGroupRel commerceAccountGroupRel :
				findByC_C(
					classNameId, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(commerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce account group rels where classNameId = &#63; and classPK = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @return the number of matching commerce account group rels
	 */
	@Override
	public int countByC_C(long classNameId, long classPK) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_COMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

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
		"commerceAccountGroupRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CLASSPK_2 =
		"commerceAccountGroupRel.classPK = ?";

	private FinderPath _finderPathFetchByC_C_C;
	private FinderPath _finderPathCountByC_C_C;

	/**
	 * Returns the commerce account group rel where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63; or throws a <code>NoSuchAccountGroupRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a matching commerce account group rel could not be found
	 */
	@Override
	public CommerceAccountGroupRel findByC_C_C(
			long classNameId, long classPK, long commerceAccountGroupId)
		throws NoSuchAccountGroupRelException {

		CommerceAccountGroupRel commerceAccountGroupRel = fetchByC_C_C(
			classNameId, classPK, commerceAccountGroupId);

		if (commerceAccountGroupRel == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", commerceAccountGroupId=");
			sb.append(commerceAccountGroupId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchAccountGroupRelException(sb.toString());
		}

		return commerceAccountGroupRel;
	}

	/**
	 * Returns the commerce account group rel where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	@Override
	public CommerceAccountGroupRel fetchByC_C_C(
		long classNameId, long classPK, long commerceAccountGroupId) {

		return fetchByC_C_C(classNameId, classPK, commerceAccountGroupId, true);
	}

	/**
	 * Returns the commerce account group rel where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching commerce account group rel, or <code>null</code> if a matching commerce account group rel could not be found
	 */
	@Override
	public CommerceAccountGroupRel fetchByC_C_C(
		long classNameId, long classPK, long commerceAccountGroupId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {
				classNameId, classPK, commerceAccountGroupId
			};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C_C, finderArgs, this);
		}

		if (result instanceof CommerceAccountGroupRel) {
			CommerceAccountGroupRel commerceAccountGroupRel =
				(CommerceAccountGroupRel)result;

			if ((classNameId != commerceAccountGroupRel.getClassNameId()) ||
				(classPK != commerceAccountGroupRel.getClassPK()) ||
				(commerceAccountGroupId !=
					commerceAccountGroupRel.getCommerceAccountGroupId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCEACCOUNTGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceAccountGroupId);

				List<CommerceAccountGroupRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_C, finderArgs, list);
					}
				}
				else {
					CommerceAccountGroupRel commerceAccountGroupRel = list.get(
						0);

					result = commerceAccountGroupRel;

					cacheResult(commerceAccountGroupRel);
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
			return (CommerceAccountGroupRel)result;
		}
	}

	/**
	 * Removes the commerce account group rel where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the commerce account group rel that was removed
	 */
	@Override
	public CommerceAccountGroupRel removeByC_C_C(
			long classNameId, long classPK, long commerceAccountGroupId)
		throws NoSuchAccountGroupRelException {

		CommerceAccountGroupRel commerceAccountGroupRel = findByC_C_C(
			classNameId, classPK, commerceAccountGroupId);

		return remove(commerceAccountGroupRel);
	}

	/**
	 * Returns the number of commerce account group rels where classNameId = &#63; and classPK = &#63; and commerceAccountGroupId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param commerceAccountGroupId the commerce account group ID
	 * @return the number of matching commerce account group rels
	 */
	@Override
	public int countByC_C_C(
		long classNameId, long classPK, long commerceAccountGroupId) {

		FinderPath finderPath = _finderPathCountByC_C_C;

		Object[] finderArgs = new Object[] {
			classNameId, classPK, commerceAccountGroupId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_COMMERCEACCOUNTGROUPREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_COMMERCEACCOUNTGROUPID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(commerceAccountGroupId);

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
		"commerceAccountGroupRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"commerceAccountGroupRel.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_COMMERCEACCOUNTGROUPID_2 =
		"commerceAccountGroupRel.commerceAccountGroupId = ?";

	public CommerceAccountGroupRelPersistenceImpl() {
		setModelClass(CommerceAccountGroupRel.class);

		setModelImplClass(CommerceAccountGroupRelImpl.class);
		setModelPKClass(long.class);

		setTable(CommerceAccountGroupRelTable.INSTANCE);
	}

	/**
	 * Caches the commerce account group rel in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroupRel the commerce account group rel
	 */
	@Override
	public void cacheResult(CommerceAccountGroupRel commerceAccountGroupRel) {
		entityCache.putResult(
			CommerceAccountGroupRelImpl.class,
			commerceAccountGroupRel.getPrimaryKey(), commerceAccountGroupRel);

		finderCache.putResult(
			_finderPathFetchByC_C_C,
			new Object[] {
				commerceAccountGroupRel.getClassNameId(),
				commerceAccountGroupRel.getClassPK(),
				commerceAccountGroupRel.getCommerceAccountGroupId()
			},
			commerceAccountGroupRel);
	}

	/**
	 * Caches the commerce account group rels in the entity cache if it is enabled.
	 *
	 * @param commerceAccountGroupRels the commerce account group rels
	 */
	@Override
	public void cacheResult(
		List<CommerceAccountGroupRel> commerceAccountGroupRels) {

		for (CommerceAccountGroupRel commerceAccountGroupRel :
				commerceAccountGroupRels) {

			if (entityCache.getResult(
					CommerceAccountGroupRelImpl.class,
					commerceAccountGroupRel.getPrimaryKey()) == null) {

				cacheResult(commerceAccountGroupRel);
			}
		}
	}

	/**
	 * Clears the cache for all commerce account group rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(CommerceAccountGroupRelImpl.class);

		finderCache.clearCache(CommerceAccountGroupRelImpl.class);
	}

	/**
	 * Clears the cache for the commerce account group rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(CommerceAccountGroupRel commerceAccountGroupRel) {
		entityCache.removeResult(
			CommerceAccountGroupRelImpl.class, commerceAccountGroupRel);
	}

	@Override
	public void clearCache(
		List<CommerceAccountGroupRel> commerceAccountGroupRels) {

		for (CommerceAccountGroupRel commerceAccountGroupRel :
				commerceAccountGroupRels) {

			entityCache.removeResult(
				CommerceAccountGroupRelImpl.class, commerceAccountGroupRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(CommerceAccountGroupRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				CommerceAccountGroupRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		CommerceAccountGroupRelModelImpl commerceAccountGroupRelModelImpl) {

		Object[] args = new Object[] {
			commerceAccountGroupRelModelImpl.getClassNameId(),
			commerceAccountGroupRelModelImpl.getClassPK(),
			commerceAccountGroupRelModelImpl.getCommerceAccountGroupId()
		};

		finderCache.putResult(_finderPathCountByC_C_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C_C, args, commerceAccountGroupRelModelImpl);
	}

	/**
	 * Creates a new commerce account group rel with the primary key. Does not add the commerce account group rel to the database.
	 *
	 * @param commerceAccountGroupRelId the primary key for the new commerce account group rel
	 * @return the new commerce account group rel
	 */
	@Override
	public CommerceAccountGroupRel create(long commerceAccountGroupRelId) {
		CommerceAccountGroupRel commerceAccountGroupRel =
			new CommerceAccountGroupRelImpl();

		commerceAccountGroupRel.setNew(true);
		commerceAccountGroupRel.setPrimaryKey(commerceAccountGroupRelId);

		commerceAccountGroupRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return commerceAccountGroupRel;
	}

	/**
	 * Removes the commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param commerceAccountGroupRelId the primary key of the commerce account group rel
	 * @return the commerce account group rel that was removed
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroupRel remove(long commerceAccountGroupRelId)
		throws NoSuchAccountGroupRelException {

		return remove((Serializable)commerceAccountGroupRelId);
	}

	/**
	 * Removes the commerce account group rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the commerce account group rel
	 * @return the commerce account group rel that was removed
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroupRel remove(Serializable primaryKey)
		throws NoSuchAccountGroupRelException {

		Session session = null;

		try {
			session = openSession();

			CommerceAccountGroupRel commerceAccountGroupRel =
				(CommerceAccountGroupRel)session.get(
					CommerceAccountGroupRelImpl.class, primaryKey);

			if (commerceAccountGroupRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchAccountGroupRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(commerceAccountGroupRel);
		}
		catch (NoSuchAccountGroupRelException noSuchEntityException) {
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
	protected CommerceAccountGroupRel removeImpl(
		CommerceAccountGroupRel commerceAccountGroupRel) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(commerceAccountGroupRel)) {
				commerceAccountGroupRel = (CommerceAccountGroupRel)session.get(
					CommerceAccountGroupRelImpl.class,
					commerceAccountGroupRel.getPrimaryKeyObj());
			}

			if (commerceAccountGroupRel != null) {
				session.delete(commerceAccountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (commerceAccountGroupRel != null) {
			clearCache(commerceAccountGroupRel);
		}

		return commerceAccountGroupRel;
	}

	@Override
	public CommerceAccountGroupRel updateImpl(
		CommerceAccountGroupRel commerceAccountGroupRel) {

		boolean isNew = commerceAccountGroupRel.isNew();

		if (!(commerceAccountGroupRel instanceof
				CommerceAccountGroupRelModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(commerceAccountGroupRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					commerceAccountGroupRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in commerceAccountGroupRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom CommerceAccountGroupRel implementation " +
					commerceAccountGroupRel.getClass());
		}

		CommerceAccountGroupRelModelImpl commerceAccountGroupRelModelImpl =
			(CommerceAccountGroupRelModelImpl)commerceAccountGroupRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date now = new Date();

		if (isNew && (commerceAccountGroupRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				commerceAccountGroupRel.setCreateDate(now);
			}
			else {
				commerceAccountGroupRel.setCreateDate(
					serviceContext.getCreateDate(now));
			}
		}

		if (!commerceAccountGroupRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				commerceAccountGroupRel.setModifiedDate(now);
			}
			else {
				commerceAccountGroupRel.setModifiedDate(
					serviceContext.getModifiedDate(now));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(commerceAccountGroupRel);
			}
			else {
				commerceAccountGroupRel =
					(CommerceAccountGroupRel)session.merge(
						commerceAccountGroupRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			CommerceAccountGroupRelImpl.class, commerceAccountGroupRelModelImpl,
			false, true);

		cacheUniqueFindersCache(commerceAccountGroupRelModelImpl);

		if (isNew) {
			commerceAccountGroupRel.setNew(false);
		}

		commerceAccountGroupRel.resetOriginalValues();

		return commerceAccountGroupRel;
	}

	/**
	 * Returns the commerce account group rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the commerce account group rel
	 * @return the commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroupRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchAccountGroupRelException {

		CommerceAccountGroupRel commerceAccountGroupRel = fetchByPrimaryKey(
			primaryKey);

		if (commerceAccountGroupRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchAccountGroupRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return commerceAccountGroupRel;
	}

	/**
	 * Returns the commerce account group rel with the primary key or throws a <code>NoSuchAccountGroupRelException</code> if it could not be found.
	 *
	 * @param commerceAccountGroupRelId the primary key of the commerce account group rel
	 * @return the commerce account group rel
	 * @throws NoSuchAccountGroupRelException if a commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroupRel findByPrimaryKey(
			long commerceAccountGroupRelId)
		throws NoSuchAccountGroupRelException {

		return findByPrimaryKey((Serializable)commerceAccountGroupRelId);
	}

	/**
	 * Returns the commerce account group rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param commerceAccountGroupRelId the primary key of the commerce account group rel
	 * @return the commerce account group rel, or <code>null</code> if a commerce account group rel with the primary key could not be found
	 */
	@Override
	public CommerceAccountGroupRel fetchByPrimaryKey(
		long commerceAccountGroupRelId) {

		return fetchByPrimaryKey((Serializable)commerceAccountGroupRelId);
	}

	/**
	 * Returns all the commerce account group rels.
	 *
	 * @return the commerce account group rels
	 */
	@Override
	public List<CommerceAccountGroupRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @return the range of commerce account group rels
	 */
	@Override
	public List<CommerceAccountGroupRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of commerce account group rels
	 */
	@Override
	public List<CommerceAccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the commerce account group rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>CommerceAccountGroupRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of commerce account group rels
	 * @param end the upper bound of the range of commerce account group rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of commerce account group rels
	 */
	@Override
	public List<CommerceAccountGroupRel> findAll(
		int start, int end,
		OrderByComparator<CommerceAccountGroupRel> orderByComparator,
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

		List<CommerceAccountGroupRel> list = null;

		if (useFinderCache) {
			list = (List<CommerceAccountGroupRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_COMMERCEACCOUNTGROUPREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_COMMERCEACCOUNTGROUPREL;

				sql = sql.concat(
					CommerceAccountGroupRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<CommerceAccountGroupRel>)QueryUtil.list(
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
	 * Removes all the commerce account group rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (CommerceAccountGroupRel commerceAccountGroupRel : findAll()) {
			remove(commerceAccountGroupRel);
		}
	}

	/**
	 * Returns the number of commerce account group rels.
	 *
	 * @return the number of commerce account group rels
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
					_SQL_COUNT_COMMERCEACCOUNTGROUPREL);

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
		return "commerceAccountGroupRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_COMMERCEACCOUNTGROUPREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return CommerceAccountGroupRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the commerce account group rel persistence.
	 */
	public void afterPropertiesSet() {
		Bundle bundle = FrameworkUtil.getBundle(
			CommerceAccountGroupRelPersistenceImpl.class);

		_bundleContext = bundle.getBundleContext();

		_argumentsResolverServiceRegistration = _bundleContext.registerService(
			ArgumentsResolver.class,
			new CommerceAccountGroupRelModelArgumentsResolver(),
			new HashMapDictionary<>());

		_finderPathWithPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathWithoutPaginationFindAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findAll", new String[0],
			new String[0], true);

		_finderPathCountAll = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countAll",
			new String[0], new String[0], false);

		_finderPathWithPaginationFindByCommerceAccountGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
			"findByCommerceAccountGroupId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"commerceAccountGroupId"}, true);

		_finderPathWithoutPaginationFindByCommerceAccountGroupId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByCommerceAccountGroupId",
				new String[] {Long.class.getName()},
				new String[] {"commerceAccountGroupId"}, true);

		_finderPathCountByCommerceAccountGroupId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByCommerceAccountGroupId",
			new String[] {Long.class.getName()},
			new String[] {"commerceAccountGroupId"}, false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "classPK"}, false);

		_finderPathFetchByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "commerceAccountGroupId"},
			true);

		_finderPathCountByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "commerceAccountGroupId"},
			false);
	}

	public void destroy() {
		entityCache.removeCache(CommerceAccountGroupRelImpl.class.getName());

		_argumentsResolverServiceRegistration.unregister();
	}

	private BundleContext _bundleContext;

	@ServiceReference(type = EntityCache.class)
	protected EntityCache entityCache;

	@ServiceReference(type = FinderCache.class)
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_COMMERCEACCOUNTGROUPREL =
		"SELECT commerceAccountGroupRel FROM CommerceAccountGroupRel commerceAccountGroupRel";

	private static final String _SQL_SELECT_COMMERCEACCOUNTGROUPREL_WHERE =
		"SELECT commerceAccountGroupRel FROM CommerceAccountGroupRel commerceAccountGroupRel WHERE ";

	private static final String _SQL_COUNT_COMMERCEACCOUNTGROUPREL =
		"SELECT COUNT(commerceAccountGroupRel) FROM CommerceAccountGroupRel commerceAccountGroupRel";

	private static final String _SQL_COUNT_COMMERCEACCOUNTGROUPREL_WHERE =
		"SELECT COUNT(commerceAccountGroupRel) FROM CommerceAccountGroupRel commerceAccountGroupRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"commerceAccountGroupRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No CommerceAccountGroupRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No CommerceAccountGroupRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		CommerceAccountGroupRelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	private ServiceRegistration<ArgumentsResolver>
		_argumentsResolverServiceRegistration;

	private static class CommerceAccountGroupRelModelArgumentsResolver
		implements ArgumentsResolver {

		@Override
		public Object[] getArguments(
			FinderPath finderPath, BaseModel<?> baseModel, boolean checkColumn,
			boolean original) {

			String[] columnNames = finderPath.getColumnNames();

			if ((columnNames == null) || (columnNames.length == 0)) {
				if (baseModel.isNew()) {
					return FINDER_ARGS_EMPTY;
				}

				return null;
			}

			CommerceAccountGroupRelModelImpl commerceAccountGroupRelModelImpl =
				(CommerceAccountGroupRelModelImpl)baseModel;

			long columnBitmask =
				commerceAccountGroupRelModelImpl.getColumnBitmask();

			if (!checkColumn || (columnBitmask == 0)) {
				return _getValue(
					commerceAccountGroupRelModelImpl, columnNames, original);
			}

			Long finderPathColumnBitmask = _finderPathColumnBitmasksCache.get(
				finderPath);

			if (finderPathColumnBitmask == null) {
				finderPathColumnBitmask = 0L;

				for (String columnName : columnNames) {
					finderPathColumnBitmask |=
						commerceAccountGroupRelModelImpl.getColumnBitmask(
							columnName);
				}

				_finderPathColumnBitmasksCache.put(
					finderPath, finderPathColumnBitmask);
			}

			if ((columnBitmask & finderPathColumnBitmask) != 0) {
				return _getValue(
					commerceAccountGroupRelModelImpl, columnNames, original);
			}

			return null;
		}

		@Override
		public String getClassName() {
			return CommerceAccountGroupRelImpl.class.getName();
		}

		@Override
		public String getTableName() {
			return CommerceAccountGroupRelTable.INSTANCE.getTableName();
		}

		private Object[] _getValue(
			CommerceAccountGroupRelModelImpl commerceAccountGroupRelModelImpl,
			String[] columnNames, boolean original) {

			Object[] arguments = new Object[columnNames.length];

			for (int i = 0; i < arguments.length; i++) {
				String columnName = columnNames[i];

				if (original) {
					arguments[i] =
						commerceAccountGroupRelModelImpl.getColumnOriginalValue(
							columnName);
				}
				else {
					arguments[i] =
						commerceAccountGroupRelModelImpl.getColumnValue(
							columnName);
				}
			}

			return arguments;
		}

		private static Map<FinderPath, Long> _finderPathColumnBitmasksCache =
			new ConcurrentHashMap<>();

	}

}