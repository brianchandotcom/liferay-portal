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

import com.liferay.commerce.order.rule.exception.NoSuchCOREntryRelException;
import com.liferay.commerce.order.rule.model.COREntryRel;
import com.liferay.commerce.order.rule.model.COREntryRelTable;
import com.liferay.commerce.order.rule.model.impl.COREntryRelImpl;
import com.liferay.commerce.order.rule.model.impl.COREntryRelModelImpl;
import com.liferay.commerce.order.rule.service.persistence.COREntryRelPersistence;
import com.liferay.commerce.order.rule.service.persistence.COREntryRelUtil;
import com.liferay.commerce.order.rule.service.persistence.impl.constants.CORPersistenceConstants;
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
 * The persistence implementation for the cor entry rel service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Luca Pellizzon
 * @generated
 */
@Component(service = COREntryRelPersistence.class)
public class COREntryRelPersistenceImpl
	extends BasePersistenceImpl<COREntryRel> implements COREntryRelPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>COREntryRelUtil</code> to access the cor entry rel persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		COREntryRelImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCOREntryId;
	private FinderPath _finderPathWithoutPaginationFindByCOREntryId;
	private FinderPath _finderPathCountByCOREntryId;

	/**
	 * Returns all the cor entry rels where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @return the matching cor entry rels
	 */
	@Override
	public List<COREntryRel> findByCOREntryId(long COREntryId) {
		return findByCOREntryId(
			COREntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cor entry rels where COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @return the range of matching cor entry rels
	 */
	@Override
	public List<COREntryRel> findByCOREntryId(
		long COREntryId, int start, int end) {

		return findByCOREntryId(COREntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cor entry rels where COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entry rels
	 */
	@Override
	public List<COREntryRel> findByCOREntryId(
		long COREntryId, int start, int end,
		OrderByComparator<COREntryRel> orderByComparator) {

		return findByCOREntryId(
			COREntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cor entry rels where COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cor entry rels
	 */
	@Override
	public List<COREntryRel> findByCOREntryId(
		long COREntryId, int start, int end,
		OrderByComparator<COREntryRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCOREntryId;
				finderArgs = new Object[] {COREntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCOREntryId;
			finderArgs = new Object[] {
				COREntryId, start, end, orderByComparator
			};
		}

		List<COREntryRel> list = null;

		if (useFinderCache) {
			list = (List<COREntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (COREntryRel corEntryRel : list) {
					if (COREntryId != corEntryRel.getCOREntryId()) {
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

			sb.append(_SQL_SELECT_CORENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_CORENTRYID_CORENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(COREntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(COREntryId);

				list = (List<COREntryRel>)QueryUtil.list(
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
	 * Returns the first cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	@Override
	public COREntryRel findByCOREntryId_First(
			long COREntryId, OrderByComparator<COREntryRel> orderByComparator)
		throws NoSuchCOREntryRelException {

		COREntryRel corEntryRel = fetchByCOREntryId_First(
			COREntryId, orderByComparator);

		if (corEntryRel != null) {
			return corEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("COREntryId=");
		sb.append(COREntryId);

		sb.append("}");

		throw new NoSuchCOREntryRelException(sb.toString());
	}

	/**
	 * Returns the first cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	@Override
	public COREntryRel fetchByCOREntryId_First(
		long COREntryId, OrderByComparator<COREntryRel> orderByComparator) {

		List<COREntryRel> list = findByCOREntryId(
			COREntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	@Override
	public COREntryRel findByCOREntryId_Last(
			long COREntryId, OrderByComparator<COREntryRel> orderByComparator)
		throws NoSuchCOREntryRelException {

		COREntryRel corEntryRel = fetchByCOREntryId_Last(
			COREntryId, orderByComparator);

		if (corEntryRel != null) {
			return corEntryRel;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("COREntryId=");
		sb.append(COREntryId);

		sb.append("}");

		throw new NoSuchCOREntryRelException(sb.toString());
	}

	/**
	 * Returns the last cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	@Override
	public COREntryRel fetchByCOREntryId_Last(
		long COREntryId, OrderByComparator<COREntryRel> orderByComparator) {

		int count = countByCOREntryId(COREntryId);

		if (count == 0) {
			return null;
		}

		List<COREntryRel> list = findByCOREntryId(
			COREntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cor entry rels before and after the current cor entry rel in the ordered set where COREntryId = &#63;.
	 *
	 * @param COREntryRelId the primary key of the current cor entry rel
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry rel
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	@Override
	public COREntryRel[] findByCOREntryId_PrevAndNext(
			long COREntryRelId, long COREntryId,
			OrderByComparator<COREntryRel> orderByComparator)
		throws NoSuchCOREntryRelException {

		COREntryRel corEntryRel = findByPrimaryKey(COREntryRelId);

		Session session = null;

		try {
			session = openSession();

			COREntryRel[] array = new COREntryRelImpl[3];

			array[0] = getByCOREntryId_PrevAndNext(
				session, corEntryRel, COREntryId, orderByComparator, true);

			array[1] = corEntryRel;

			array[2] = getByCOREntryId_PrevAndNext(
				session, corEntryRel, COREntryId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected COREntryRel getByCOREntryId_PrevAndNext(
		Session session, COREntryRel corEntryRel, long COREntryId,
		OrderByComparator<COREntryRel> orderByComparator, boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				4 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(3);
		}

		sb.append(_SQL_SELECT_CORENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_CORENTRYID_CORENTRYID_2);

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
			sb.append(COREntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(COREntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(corEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<COREntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cor entry rels where COREntryId = &#63; from the database.
	 *
	 * @param COREntryId the cor entry ID
	 */
	@Override
	public void removeByCOREntryId(long COREntryId) {
		for (COREntryRel corEntryRel :
				findByCOREntryId(
					COREntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(corEntryRel);
		}
	}

	/**
	 * Returns the number of cor entry rels where COREntryId = &#63;.
	 *
	 * @param COREntryId the cor entry ID
	 * @return the number of matching cor entry rels
	 */
	@Override
	public int countByCOREntryId(long COREntryId) {
		FinderPath finderPath = _finderPathCountByCOREntryId;

		Object[] finderArgs = new Object[] {COREntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_CORENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_CORENTRYID_CORENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(COREntryId);

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

	private static final String _FINDER_COLUMN_CORENTRYID_CORENTRYID_2 =
		"corEntryRel.COREntryId = ?";

	private FinderPath _finderPathWithPaginationFindByC_C;
	private FinderPath _finderPathWithoutPaginationFindByC_C;
	private FinderPath _finderPathCountByC_C;

	/**
	 * Returns all the cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @return the matching cor entry rels
	 */
	@Override
	public List<COREntryRel> findByC_C(long classNameId, long COREntryId) {
		return findByC_C(
			classNameId, COREntryId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @return the range of matching cor entry rels
	 */
	@Override
	public List<COREntryRel> findByC_C(
		long classNameId, long COREntryId, int start, int end) {

		return findByC_C(classNameId, COREntryId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching cor entry rels
	 */
	@Override
	public List<COREntryRel> findByC_C(
		long classNameId, long COREntryId, int start, int end,
		OrderByComparator<COREntryRel> orderByComparator) {

		return findByC_C(
			classNameId, COREntryId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching cor entry rels
	 */
	@Override
	public List<COREntryRel> findByC_C(
		long classNameId, long COREntryId, int start, int end,
		OrderByComparator<COREntryRel> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_C;
				finderArgs = new Object[] {classNameId, COREntryId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_C;
			finderArgs = new Object[] {
				classNameId, COREntryId, start, end, orderByComparator
			};
		}

		List<COREntryRel> list = null;

		if (useFinderCache) {
			list = (List<COREntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);

			if ((list != null) && !list.isEmpty()) {
				for (COREntryRel corEntryRel : list) {
					if ((classNameId != corEntryRel.getClassNameId()) ||
						(COREntryId != corEntryRel.getCOREntryId())) {

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

			sb.append(_SQL_SELECT_CORENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CORENTRYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(COREntryRelModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(COREntryId);

				list = (List<COREntryRel>)QueryUtil.list(
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
	 * Returns the first cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	@Override
	public COREntryRel findByC_C_First(
			long classNameId, long COREntryId,
			OrderByComparator<COREntryRel> orderByComparator)
		throws NoSuchCOREntryRelException {

		COREntryRel corEntryRel = fetchByC_C_First(
			classNameId, COREntryId, orderByComparator);

		if (corEntryRel != null) {
			return corEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", COREntryId=");
		sb.append(COREntryId);

		sb.append("}");

		throw new NoSuchCOREntryRelException(sb.toString());
	}

	/**
	 * Returns the first cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	@Override
	public COREntryRel fetchByC_C_First(
		long classNameId, long COREntryId,
		OrderByComparator<COREntryRel> orderByComparator) {

		List<COREntryRel> list = findByC_C(
			classNameId, COREntryId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	@Override
	public COREntryRel findByC_C_Last(
			long classNameId, long COREntryId,
			OrderByComparator<COREntryRel> orderByComparator)
		throws NoSuchCOREntryRelException {

		COREntryRel corEntryRel = fetchByC_C_Last(
			classNameId, COREntryId, orderByComparator);

		if (corEntryRel != null) {
			return corEntryRel;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("classNameId=");
		sb.append(classNameId);

		sb.append(", COREntryId=");
		sb.append(COREntryId);

		sb.append("}");

		throw new NoSuchCOREntryRelException(sb.toString());
	}

	/**
	 * Returns the last cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	@Override
	public COREntryRel fetchByC_C_Last(
		long classNameId, long COREntryId,
		OrderByComparator<COREntryRel> orderByComparator) {

		int count = countByC_C(classNameId, COREntryId);

		if (count == 0) {
			return null;
		}

		List<COREntryRel> list = findByC_C(
			classNameId, COREntryId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the cor entry rels before and after the current cor entry rel in the ordered set where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param COREntryRelId the primary key of the current cor entry rel
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next cor entry rel
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	@Override
	public COREntryRel[] findByC_C_PrevAndNext(
			long COREntryRelId, long classNameId, long COREntryId,
			OrderByComparator<COREntryRel> orderByComparator)
		throws NoSuchCOREntryRelException {

		COREntryRel corEntryRel = findByPrimaryKey(COREntryRelId);

		Session session = null;

		try {
			session = openSession();

			COREntryRel[] array = new COREntryRelImpl[3];

			array[0] = getByC_C_PrevAndNext(
				session, corEntryRel, classNameId, COREntryId,
				orderByComparator, true);

			array[1] = corEntryRel;

			array[2] = getByC_C_PrevAndNext(
				session, corEntryRel, classNameId, COREntryId,
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

	protected COREntryRel getByC_C_PrevAndNext(
		Session session, COREntryRel corEntryRel, long classNameId,
		long COREntryId, OrderByComparator<COREntryRel> orderByComparator,
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

		sb.append(_SQL_SELECT_CORENTRYREL_WHERE);

		sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

		sb.append(_FINDER_COLUMN_C_C_CORENTRYID_2);

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
			sb.append(COREntryRelModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(classNameId);

		queryPos.add(COREntryId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(corEntryRel)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<COREntryRel> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the cor entry rels where classNameId = &#63; and COREntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 */
	@Override
	public void removeByC_C(long classNameId, long COREntryId) {
		for (COREntryRel corEntryRel :
				findByC_C(
					classNameId, COREntryId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(corEntryRel);
		}
	}

	/**
	 * Returns the number of cor entry rels where classNameId = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param COREntryId the cor entry ID
	 * @return the number of matching cor entry rels
	 */
	@Override
	public int countByC_C(long classNameId, long COREntryId) {
		FinderPath finderPath = _finderPathCountByC_C;

		Object[] finderArgs = new Object[] {classNameId, COREntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_CORENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_CORENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(COREntryId);

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
		"corEntryRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_CORENTRYID_2 =
		"corEntryRel.COREntryId = ?";

	private FinderPath _finderPathFetchByC_C_C;
	private FinderPath _finderPathCountByC_C_C;

	/**
	 * Returns the cor entry rel where classNameId = &#63; and classPK = &#63; and COREntryId = &#63; or throws a <code>NoSuchCOREntryRelException</code> if it could not be found.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @return the matching cor entry rel
	 * @throws NoSuchCOREntryRelException if a matching cor entry rel could not be found
	 */
	@Override
	public COREntryRel findByC_C_C(
			long classNameId, long classPK, long COREntryId)
		throws NoSuchCOREntryRelException {

		COREntryRel corEntryRel = fetchByC_C_C(
			classNameId, classPK, COREntryId);

		if (corEntryRel == null) {
			StringBundler sb = new StringBundler(8);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("classNameId=");
			sb.append(classNameId);

			sb.append(", classPK=");
			sb.append(classPK);

			sb.append(", COREntryId=");
			sb.append(COREntryId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchCOREntryRelException(sb.toString());
		}

		return corEntryRel;
	}

	/**
	 * Returns the cor entry rel where classNameId = &#63; and classPK = &#63; and COREntryId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @return the matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	@Override
	public COREntryRel fetchByC_C_C(
		long classNameId, long classPK, long COREntryId) {

		return fetchByC_C_C(classNameId, classPK, COREntryId, true);
	}

	/**
	 * Returns the cor entry rel where classNameId = &#63; and classPK = &#63; and COREntryId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching cor entry rel, or <code>null</code> if a matching cor entry rel could not be found
	 */
	@Override
	public COREntryRel fetchByC_C_C(
		long classNameId, long classPK, long COREntryId,
		boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {classNameId, classPK, COREntryId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByC_C_C, finderArgs, this);
		}

		if (result instanceof COREntryRel) {
			COREntryRel corEntryRel = (COREntryRel)result;

			if ((classNameId != corEntryRel.getClassNameId()) ||
				(classPK != corEntryRel.getClassPK()) ||
				(COREntryId != corEntryRel.getCOREntryId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(5);

			sb.append(_SQL_SELECT_CORENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_CORENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(COREntryId);

				List<COREntryRel> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByC_C_C, finderArgs, list);
					}
				}
				else {
					COREntryRel corEntryRel = list.get(0);

					result = corEntryRel;

					cacheResult(corEntryRel);
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
			return (COREntryRel)result;
		}
	}

	/**
	 * Removes the cor entry rel where classNameId = &#63; and classPK = &#63; and COREntryId = &#63; from the database.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @return the cor entry rel that was removed
	 */
	@Override
	public COREntryRel removeByC_C_C(
			long classNameId, long classPK, long COREntryId)
		throws NoSuchCOREntryRelException {

		COREntryRel corEntryRel = findByC_C_C(classNameId, classPK, COREntryId);

		return remove(corEntryRel);
	}

	/**
	 * Returns the number of cor entry rels where classNameId = &#63; and classPK = &#63; and COREntryId = &#63;.
	 *
	 * @param classNameId the class name ID
	 * @param classPK the class pk
	 * @param COREntryId the cor entry ID
	 * @return the number of matching cor entry rels
	 */
	@Override
	public int countByC_C_C(long classNameId, long classPK, long COREntryId) {
		FinderPath finderPath = _finderPathCountByC_C_C;

		Object[] finderArgs = new Object[] {classNameId, classPK, COREntryId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs, this);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_CORENTRYREL_WHERE);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSNAMEID_2);

			sb.append(_FINDER_COLUMN_C_C_C_CLASSPK_2);

			sb.append(_FINDER_COLUMN_C_C_C_CORENTRYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(classNameId);

				queryPos.add(classPK);

				queryPos.add(COREntryId);

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
		"corEntryRel.classNameId = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CLASSPK_2 =
		"corEntryRel.classPK = ? AND ";

	private static final String _FINDER_COLUMN_C_C_C_CORENTRYID_2 =
		"corEntryRel.COREntryId = ?";

	public COREntryRelPersistenceImpl() {
		setModelClass(COREntryRel.class);

		setModelImplClass(COREntryRelImpl.class);
		setModelPKClass(long.class);

		setTable(COREntryRelTable.INSTANCE);
	}

	/**
	 * Caches the cor entry rel in the entity cache if it is enabled.
	 *
	 * @param corEntryRel the cor entry rel
	 */
	@Override
	public void cacheResult(COREntryRel corEntryRel) {
		entityCache.putResult(
			COREntryRelImpl.class, corEntryRel.getPrimaryKey(), corEntryRel);

		finderCache.putResult(
			_finderPathFetchByC_C_C,
			new Object[] {
				corEntryRel.getClassNameId(), corEntryRel.getClassPK(),
				corEntryRel.getCOREntryId()
			},
			corEntryRel);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the cor entry rels in the entity cache if it is enabled.
	 *
	 * @param corEntryRels the cor entry rels
	 */
	@Override
	public void cacheResult(List<COREntryRel> corEntryRels) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (corEntryRels.size() > _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (COREntryRel corEntryRel : corEntryRels) {
			if (entityCache.getResult(
					COREntryRelImpl.class, corEntryRel.getPrimaryKey()) ==
						null) {

				cacheResult(corEntryRel);
			}
		}
	}

	/**
	 * Clears the cache for all cor entry rels.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(COREntryRelImpl.class);

		finderCache.clearCache(COREntryRelImpl.class);
	}

	/**
	 * Clears the cache for the cor entry rel.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(COREntryRel corEntryRel) {
		entityCache.removeResult(COREntryRelImpl.class, corEntryRel);
	}

	@Override
	public void clearCache(List<COREntryRel> corEntryRels) {
		for (COREntryRel corEntryRel : corEntryRels) {
			entityCache.removeResult(COREntryRelImpl.class, corEntryRel);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(COREntryRelImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(COREntryRelImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		COREntryRelModelImpl corEntryRelModelImpl) {

		Object[] args = new Object[] {
			corEntryRelModelImpl.getClassNameId(),
			corEntryRelModelImpl.getClassPK(),
			corEntryRelModelImpl.getCOREntryId()
		};

		finderCache.putResult(_finderPathCountByC_C_C, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByC_C_C, args, corEntryRelModelImpl);
	}

	/**
	 * Creates a new cor entry rel with the primary key. Does not add the cor entry rel to the database.
	 *
	 * @param COREntryRelId the primary key for the new cor entry rel
	 * @return the new cor entry rel
	 */
	@Override
	public COREntryRel create(long COREntryRelId) {
		COREntryRel corEntryRel = new COREntryRelImpl();

		corEntryRel.setNew(true);
		corEntryRel.setPrimaryKey(COREntryRelId);

		corEntryRel.setCompanyId(CompanyThreadLocal.getCompanyId());

		return corEntryRel;
	}

	/**
	 * Removes the cor entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param COREntryRelId the primary key of the cor entry rel
	 * @return the cor entry rel that was removed
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	@Override
	public COREntryRel remove(long COREntryRelId)
		throws NoSuchCOREntryRelException {

		return remove((Serializable)COREntryRelId);
	}

	/**
	 * Removes the cor entry rel with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the cor entry rel
	 * @return the cor entry rel that was removed
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	@Override
	public COREntryRel remove(Serializable primaryKey)
		throws NoSuchCOREntryRelException {

		Session session = null;

		try {
			session = openSession();

			COREntryRel corEntryRel = (COREntryRel)session.get(
				COREntryRelImpl.class, primaryKey);

			if (corEntryRel == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchCOREntryRelException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(corEntryRel);
		}
		catch (NoSuchCOREntryRelException noSuchEntityException) {
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
	protected COREntryRel removeImpl(COREntryRel corEntryRel) {
		Session session = null;

		try {
			session = openSession();

			if (!session.contains(corEntryRel)) {
				corEntryRel = (COREntryRel)session.get(
					COREntryRelImpl.class, corEntryRel.getPrimaryKeyObj());
			}

			if (corEntryRel != null) {
				session.delete(corEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (corEntryRel != null) {
			clearCache(corEntryRel);
		}

		return corEntryRel;
	}

	@Override
	public COREntryRel updateImpl(COREntryRel corEntryRel) {
		boolean isNew = corEntryRel.isNew();

		if (!(corEntryRel instanceof COREntryRelModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(corEntryRel.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(corEntryRel);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in corEntryRel proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom COREntryRel implementation " +
					corEntryRel.getClass());
		}

		COREntryRelModelImpl corEntryRelModelImpl =
			(COREntryRelModelImpl)corEntryRel;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (corEntryRel.getCreateDate() == null)) {
			if (serviceContext == null) {
				corEntryRel.setCreateDate(date);
			}
			else {
				corEntryRel.setCreateDate(serviceContext.getCreateDate(date));
			}
		}

		if (!corEntryRelModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				corEntryRel.setModifiedDate(date);
			}
			else {
				corEntryRel.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(corEntryRel);
			}
			else {
				corEntryRel = (COREntryRel)session.merge(corEntryRel);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			COREntryRelImpl.class, corEntryRelModelImpl, false, true);

		cacheUniqueFindersCache(corEntryRelModelImpl);

		if (isNew) {
			corEntryRel.setNew(false);
		}

		corEntryRel.resetOriginalValues();

		return corEntryRel;
	}

	/**
	 * Returns the cor entry rel with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the cor entry rel
	 * @return the cor entry rel
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	@Override
	public COREntryRel findByPrimaryKey(Serializable primaryKey)
		throws NoSuchCOREntryRelException {

		COREntryRel corEntryRel = fetchByPrimaryKey(primaryKey);

		if (corEntryRel == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchCOREntryRelException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return corEntryRel;
	}

	/**
	 * Returns the cor entry rel with the primary key or throws a <code>NoSuchCOREntryRelException</code> if it could not be found.
	 *
	 * @param COREntryRelId the primary key of the cor entry rel
	 * @return the cor entry rel
	 * @throws NoSuchCOREntryRelException if a cor entry rel with the primary key could not be found
	 */
	@Override
	public COREntryRel findByPrimaryKey(long COREntryRelId)
		throws NoSuchCOREntryRelException {

		return findByPrimaryKey((Serializable)COREntryRelId);
	}

	/**
	 * Returns the cor entry rel with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param COREntryRelId the primary key of the cor entry rel
	 * @return the cor entry rel, or <code>null</code> if a cor entry rel with the primary key could not be found
	 */
	@Override
	public COREntryRel fetchByPrimaryKey(long COREntryRelId) {
		return fetchByPrimaryKey((Serializable)COREntryRelId);
	}

	/**
	 * Returns all the cor entry rels.
	 *
	 * @return the cor entry rels
	 */
	@Override
	public List<COREntryRel> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the cor entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @return the range of cor entry rels
	 */
	@Override
	public List<COREntryRel> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the cor entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of cor entry rels
	 */
	@Override
	public List<COREntryRel> findAll(
		int start, int end, OrderByComparator<COREntryRel> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the cor entry rels.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>COREntryRelModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of cor entry rels
	 * @param end the upper bound of the range of cor entry rels (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of cor entry rels
	 */
	@Override
	public List<COREntryRel> findAll(
		int start, int end, OrderByComparator<COREntryRel> orderByComparator,
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

		List<COREntryRel> list = null;

		if (useFinderCache) {
			list = (List<COREntryRel>)finderCache.getResult(
				finderPath, finderArgs, this);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_CORENTRYREL);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_CORENTRYREL;

				sql = sql.concat(COREntryRelModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<COREntryRel>)QueryUtil.list(
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
	 * Removes all the cor entry rels from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (COREntryRel corEntryRel : findAll()) {
			remove(corEntryRel);
		}
	}

	/**
	 * Returns the number of cor entry rels.
	 *
	 * @return the number of cor entry rels
	 */
	@Override
	public int countAll() {
		Long count = (Long)finderCache.getResult(
			_finderPathCountAll, FINDER_ARGS_EMPTY, this);

		if (count == null) {
			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(_SQL_COUNT_CORENTRYREL);

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
		return "COREntryRelId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_CORENTRYREL;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return COREntryRelModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the cor entry rel persistence.
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

		_finderPathWithPaginationFindByCOREntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCOREntryId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"COREntryId"}, true);

		_finderPathWithoutPaginationFindByCOREntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCOREntryId",
			new String[] {Long.class.getName()}, new String[] {"COREntryId"},
			true);

		_finderPathCountByCOREntryId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCOREntryId",
			new String[] {Long.class.getName()}, new String[] {"COREntryId"},
			false);

		_finderPathWithPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"classNameId", "COREntryId"}, true);

		_finderPathWithoutPaginationFindByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "COREntryId"}, true);

		_finderPathCountByC_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"classNameId", "COREntryId"}, false);

		_finderPathFetchByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "COREntryId"}, true);

		_finderPathCountByC_C_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_C_C",
			new String[] {
				Long.class.getName(), Long.class.getName(), Long.class.getName()
			},
			new String[] {"classNameId", "classPK", "COREntryId"}, false);

		COREntryRelUtil.setPersistence(this);
	}

	@Deactivate
	public void deactivate() {
		COREntryRelUtil.setPersistence(null);

		entityCache.removeCache(COREntryRelImpl.class.getName());
	}

	@Override
	@Reference(
		target = CORPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = CORPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = CORPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_CORENTRYREL =
		"SELECT corEntryRel FROM COREntryRel corEntryRel";

	private static final String _SQL_SELECT_CORENTRYREL_WHERE =
		"SELECT corEntryRel FROM COREntryRel corEntryRel WHERE ";

	private static final String _SQL_COUNT_CORENTRYREL =
		"SELECT COUNT(corEntryRel) FROM COREntryRel corEntryRel";

	private static final String _SQL_COUNT_CORENTRYREL_WHERE =
		"SELECT COUNT(corEntryRel) FROM COREntryRel corEntryRel WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "corEntryRel.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No COREntryRel exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No COREntryRel exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		COREntryRelPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

}