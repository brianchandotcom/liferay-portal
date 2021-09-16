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

package com.liferay.portal.workflow.kaleo.service.persistence.impl;

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
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.workflow.kaleo.exception.NoSuchTaskInstanceTokenException;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoTaskInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoTaskInstanceTokenModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoTaskInstanceTokenPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.util.Collections;
import java.util.Date;
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
 * The persistence implementation for the kaleo task instance token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {KaleoTaskInstanceTokenPersistence.class, BasePersistence.class}
)
public class KaleoTaskInstanceTokenPersistenceImpl
	extends BasePersistenceImpl<KaleoTaskInstanceToken>
	implements KaleoTaskInstanceTokenPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KaleoTaskInstanceTokenUtil</code> to access the kaleo task instance token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KaleoTaskInstanceTokenImpl.class.getName();

	public static final String FINDER_CLASS_NAME_LIST_WITH_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List1";

	public static final String FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION =
		FINDER_CLASS_NAME_ENTITY + ".List2";

	private FinderPath _finderPathWithPaginationFindAll;
	private FinderPath _finderPathWithoutPaginationFindAll;
	private FinderPath _finderPathCountAll;
	private FinderPath _finderPathWithPaginationFindByCompanyId;
	private FinderPath _finderPathWithoutPaginationFindByCompanyId;
	private FinderPath _finderPathCountByCompanyId;

	/**
	 * Returns all the kaleo task instance tokens where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCompanyId;
				finderArgs = new Object[] {companyId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCompanyId;
			finderArgs = new Object[] {
				companyId, start, end, orderByComparator
			};
		}

		List<KaleoTaskInstanceToken> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskInstanceToken>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskInstanceToken kaleoTaskInstanceToken : list) {
					if (companyId != kaleoTaskInstanceToken.getCompanyId()) {
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

			sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskInstanceTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<KaleoTaskInstanceToken>)QueryUtil.list(
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
	 * Returns the first kaleo task instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByCompanyId_First(
			long companyId,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (kaleoTaskInstanceToken != null) {
			return kaleoTaskInstanceToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTaskInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the first kaleo task instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByCompanyId_First(
		long companyId,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		List<KaleoTaskInstanceToken> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByCompanyId_Last(
			long companyId,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (kaleoTaskInstanceToken != null) {
			return kaleoTaskInstanceToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchTaskInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the last kaleo task instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskInstanceToken> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task instance tokens before and after the current kaleo task instance token in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the current kaleo task instance token
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a kaleo task instance token with the primary key could not be found
	 */
	@Override
	public KaleoTaskInstanceToken[] findByCompanyId_PrevAndNext(
			long kaleoTaskInstanceTokenId, long companyId,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = findByPrimaryKey(
			kaleoTaskInstanceTokenId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskInstanceToken[] array = new KaleoTaskInstanceTokenImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, kaleoTaskInstanceToken, companyId, orderByComparator,
				true);

			array[1] = kaleoTaskInstanceToken;

			array[2] = getByCompanyId_PrevAndNext(
				session, kaleoTaskInstanceToken, companyId, orderByComparator,
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

	protected KaleoTaskInstanceToken getByCompanyId_PrevAndNext(
		Session session, KaleoTaskInstanceToken kaleoTaskInstanceToken,
		long companyId,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE);

		sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

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
			sb.append(KaleoTaskInstanceTokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskInstanceToken)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskInstanceToken> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task instance tokens where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoTaskInstanceToken);
		}
	}

	/**
	 * Returns the number of kaleo task instance tokens where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo task instance tokens
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTASKINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

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

	private static final String _FINDER_COLUMN_COMPANYID_COMPANYID_2 =
		"kaleoTaskInstanceToken.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoDefinitionVersionId;
	private FinderPath
		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
	private FinderPath _finderPathCountByKaleoDefinitionVersionId;

	/**
	 * Returns all the kaleo task instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath =
					_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
				finderArgs = new Object[] {kaleoDefinitionVersionId};
			}
		}
		else if (useFinderCache) {
			finderPath =
				_finderPathWithPaginationFindByKaleoDefinitionVersionId;
			finderArgs = new Object[] {
				kaleoDefinitionVersionId, start, end, orderByComparator
			};
		}

		List<KaleoTaskInstanceToken> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskInstanceToken>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskInstanceToken kaleoTaskInstanceToken : list) {
					if (kaleoDefinitionVersionId !=
							kaleoTaskInstanceToken.
								getKaleoDefinitionVersionId()) {

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

			sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskInstanceTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

				list = (List<KaleoTaskInstanceToken>)QueryUtil.list(
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
	 * Returns the first kaleo task instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			fetchByKaleoDefinitionVersionId_First(
				kaleoDefinitionVersionId, orderByComparator);

		if (kaleoTaskInstanceToken != null) {
			return kaleoTaskInstanceToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchTaskInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the first kaleo task instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		List<KaleoTaskInstanceToken> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			fetchByKaleoDefinitionVersionId_Last(
				kaleoDefinitionVersionId, orderByComparator);

		if (kaleoTaskInstanceToken != null) {
			return kaleoTaskInstanceToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchTaskInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the last kaleo task instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		int count = countByKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskInstanceToken> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task instance tokens before and after the current kaleo task instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the current kaleo task instance token
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a kaleo task instance token with the primary key could not be found
	 */
	@Override
	public KaleoTaskInstanceToken[] findByKaleoDefinitionVersionId_PrevAndNext(
			long kaleoTaskInstanceTokenId, long kaleoDefinitionVersionId,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = findByPrimaryKey(
			kaleoTaskInstanceTokenId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskInstanceToken[] array = new KaleoTaskInstanceTokenImpl[3];

			array[0] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoTaskInstanceToken, kaleoDefinitionVersionId,
				orderByComparator, true);

			array[1] = kaleoTaskInstanceToken;

			array[2] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoTaskInstanceToken, kaleoDefinitionVersionId,
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

	protected KaleoTaskInstanceToken getByKaleoDefinitionVersionId_PrevAndNext(
		Session session, KaleoTaskInstanceToken kaleoTaskInstanceToken,
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE);

		sb.append(
			_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

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
			sb.append(KaleoTaskInstanceTokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoDefinitionVersionId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskInstanceToken)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskInstanceToken> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task instance tokens where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	@Override
	public void removeByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				findByKaleoDefinitionVersionId(
					kaleoDefinitionVersionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoTaskInstanceToken);
		}
	}

	/**
	 * Returns the number of kaleo task instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo task instance tokens
	 */
	@Override
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		FinderPath finderPath = _finderPathCountByKaleoDefinitionVersionId;

		Object[] finderArgs = new Object[] {kaleoDefinitionVersionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTASKINSTANCETOKEN_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

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
		_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2 =
			"kaleoTaskInstanceToken.kaleoDefinitionVersionId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoInstanceId;
	private FinderPath _finderPathWithoutPaginationFindByKaleoInstanceId;
	private FinderPath _finderPathCountByKaleoInstanceId;

	/**
	 * Returns all the kaleo task instance tokens where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId) {

		return findByKaleoInstanceId(
			kaleoInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end) {

		return findByKaleoInstanceId(kaleoInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		return findByKaleoInstanceId(
			kaleoInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByKaleoInstanceId;
				finderArgs = new Object[] {kaleoInstanceId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByKaleoInstanceId;
			finderArgs = new Object[] {
				kaleoInstanceId, start, end, orderByComparator
			};
		}

		List<KaleoTaskInstanceToken> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskInstanceToken>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskInstanceToken kaleoTaskInstanceToken : list) {
					if (kaleoInstanceId !=
							kaleoTaskInstanceToken.getKaleoInstanceId()) {

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

			sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskInstanceTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoInstanceId);

				list = (List<KaleoTaskInstanceToken>)QueryUtil.list(
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
	 * Returns the first kaleo task instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByKaleoInstanceId_First(
			long kaleoInstanceId,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			fetchByKaleoInstanceId_First(kaleoInstanceId, orderByComparator);

		if (kaleoTaskInstanceToken != null) {
			return kaleoTaskInstanceToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoInstanceId=");
		sb.append(kaleoInstanceId);

		sb.append("}");

		throw new NoSuchTaskInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the first kaleo task instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByKaleoInstanceId_First(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		List<KaleoTaskInstanceToken> list = findByKaleoInstanceId(
			kaleoInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByKaleoInstanceId_Last(
			long kaleoInstanceId,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			fetchByKaleoInstanceId_Last(kaleoInstanceId, orderByComparator);

		if (kaleoTaskInstanceToken != null) {
			return kaleoTaskInstanceToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoInstanceId=");
		sb.append(kaleoInstanceId);

		sb.append("}");

		throw new NoSuchTaskInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the last kaleo task instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByKaleoInstanceId_Last(
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		int count = countByKaleoInstanceId(kaleoInstanceId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskInstanceToken> list = findByKaleoInstanceId(
			kaleoInstanceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task instance tokens before and after the current kaleo task instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the current kaleo task instance token
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a kaleo task instance token with the primary key could not be found
	 */
	@Override
	public KaleoTaskInstanceToken[] findByKaleoInstanceId_PrevAndNext(
			long kaleoTaskInstanceTokenId, long kaleoInstanceId,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = findByPrimaryKey(
			kaleoTaskInstanceTokenId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskInstanceToken[] array = new KaleoTaskInstanceTokenImpl[3];

			array[0] = getByKaleoInstanceId_PrevAndNext(
				session, kaleoTaskInstanceToken, kaleoInstanceId,
				orderByComparator, true);

			array[1] = kaleoTaskInstanceToken;

			array[2] = getByKaleoInstanceId_PrevAndNext(
				session, kaleoTaskInstanceToken, kaleoInstanceId,
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

	protected KaleoTaskInstanceToken getByKaleoInstanceId_PrevAndNext(
		Session session, KaleoTaskInstanceToken kaleoTaskInstanceToken,
		long kaleoInstanceId,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE);

		sb.append(_FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2);

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
			sb.append(KaleoTaskInstanceTokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(kaleoInstanceId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskInstanceToken)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskInstanceToken> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task instance tokens where kaleoInstanceId = &#63; from the database.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 */
	@Override
	public void removeByKaleoInstanceId(long kaleoInstanceId) {
		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				findByKaleoInstanceId(
					kaleoInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoTaskInstanceToken);
		}
	}

	/**
	 * Returns the number of kaleo task instance tokens where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the number of matching kaleo task instance tokens
	 */
	@Override
	public int countByKaleoInstanceId(long kaleoInstanceId) {
		FinderPath finderPath = _finderPathCountByKaleoInstanceId;

		Object[] finderArgs = new Object[] {kaleoInstanceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOTASKINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoInstanceId);

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
		_FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2 =
			"kaleoTaskInstanceToken.kaleoInstanceId = ?";

	private FinderPath _finderPathWithPaginationFindByC_U;
	private FinderPath _finderPathWithoutPaginationFindByC_U;
	private FinderPath _finderPathCountByC_U;

	/**
	 * Returns all the kaleo task instance tokens where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByC_U(long companyId, long userId) {
		return findByC_U(
			companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByC_U(
		long companyId, long userId, int start, int end) {

		return findByC_U(companyId, userId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		return findByC_U(
			companyId, userId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where companyId = &#63; and userId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByC_U(
		long companyId, long userId, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_U;
				finderArgs = new Object[] {companyId, userId};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_U;
			finderArgs = new Object[] {
				companyId, userId, start, end, orderByComparator
			};
		}

		List<KaleoTaskInstanceToken> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskInstanceToken>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskInstanceToken kaleoTaskInstanceToken : list) {
					if ((companyId != kaleoTaskInstanceToken.getCompanyId()) ||
						(userId != kaleoTaskInstanceToken.getUserId())) {

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

			sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_USERID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskInstanceTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

				list = (List<KaleoTaskInstanceToken>)QueryUtil.list(
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
	 * Returns the first kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByC_U_First(
			long companyId, long userId,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = fetchByC_U_First(
			companyId, userId, orderByComparator);

		if (kaleoTaskInstanceToken != null) {
			return kaleoTaskInstanceToken;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchTaskInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the first kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByC_U_First(
		long companyId, long userId,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		List<KaleoTaskInstanceToken> list = findByC_U(
			companyId, userId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByC_U_Last(
			long companyId, long userId,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = fetchByC_U_Last(
			companyId, userId, orderByComparator);

		if (kaleoTaskInstanceToken != null) {
			return kaleoTaskInstanceToken;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append("}");

		throw new NoSuchTaskInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the last kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByC_U_Last(
		long companyId, long userId,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		int count = countByC_U(companyId, userId);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskInstanceToken> list = findByC_U(
			companyId, userId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task instance tokens before and after the current kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the current kaleo task instance token
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a kaleo task instance token with the primary key could not be found
	 */
	@Override
	public KaleoTaskInstanceToken[] findByC_U_PrevAndNext(
			long kaleoTaskInstanceTokenId, long companyId, long userId,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = findByPrimaryKey(
			kaleoTaskInstanceTokenId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskInstanceToken[] array = new KaleoTaskInstanceTokenImpl[3];

			array[0] = getByC_U_PrevAndNext(
				session, kaleoTaskInstanceToken, companyId, userId,
				orderByComparator, true);

			array[1] = kaleoTaskInstanceToken;

			array[2] = getByC_U_PrevAndNext(
				session, kaleoTaskInstanceToken, companyId, userId,
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

	protected KaleoTaskInstanceToken getByC_U_PrevAndNext(
		Session session, KaleoTaskInstanceToken kaleoTaskInstanceToken,
		long companyId, long userId,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE);

		sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_U_USERID_2);

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
			sb.append(KaleoTaskInstanceTokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(userId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskInstanceToken)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskInstanceToken> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task instance tokens where companyId = &#63; and userId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 */
	@Override
	public void removeByC_U(long companyId, long userId) {
		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				findByC_U(
					companyId, userId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoTaskInstanceToken);
		}
	}

	/**
	 * Returns the number of kaleo task instance tokens where companyId = &#63; and userId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @return the number of matching kaleo task instance tokens
	 */
	@Override
	public int countByC_U(long companyId, long userId) {
		FinderPath finderPath = _finderPathCountByC_U;

		Object[] finderArgs = new Object[] {companyId, userId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOTASKINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_C_U_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_USERID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

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

	private static final String _FINDER_COLUMN_C_U_COMPANYID_2 =
		"kaleoTaskInstanceToken.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_U_USERID_2 =
		"kaleoTaskInstanceToken.userId = ?";

	private FinderPath _finderPathFetchByKII_KTI;
	private FinderPath _finderPathCountByKII_KTI;

	/**
	 * Returns the kaleo task instance token where kaleoInstanceId = &#63; and kaleoTaskId = &#63; or throws a <code>NoSuchTaskInstanceTokenException</code> if it could not be found.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param kaleoTaskId the kaleo task ID
	 * @return the matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByKII_KTI(
			long kaleoInstanceId, long kaleoTaskId)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = fetchByKII_KTI(
			kaleoInstanceId, kaleoTaskId);

		if (kaleoTaskInstanceToken == null) {
			StringBundler sb = new StringBundler(6);

			sb.append(_NO_SUCH_ENTITY_WITH_KEY);

			sb.append("kaleoInstanceId=");
			sb.append(kaleoInstanceId);

			sb.append(", kaleoTaskId=");
			sb.append(kaleoTaskId);

			sb.append("}");

			if (_log.isDebugEnabled()) {
				_log.debug(sb.toString());
			}

			throw new NoSuchTaskInstanceTokenException(sb.toString());
		}

		return kaleoTaskInstanceToken;
	}

	/**
	 * Returns the kaleo task instance token where kaleoInstanceId = &#63; and kaleoTaskId = &#63; or returns <code>null</code> if it could not be found. Uses the finder cache.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param kaleoTaskId the kaleo task ID
	 * @return the matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByKII_KTI(
		long kaleoInstanceId, long kaleoTaskId) {

		return fetchByKII_KTI(kaleoInstanceId, kaleoTaskId, true);
	}

	/**
	 * Returns the kaleo task instance token where kaleoInstanceId = &#63; and kaleoTaskId = &#63; or returns <code>null</code> if it could not be found, optionally using the finder cache.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param kaleoTaskId the kaleo task ID
	 * @param useFinderCache whether to use the finder cache
	 * @return the matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByKII_KTI(
		long kaleoInstanceId, long kaleoTaskId, boolean useFinderCache) {

		Object[] finderArgs = null;

		if (useFinderCache) {
			finderArgs = new Object[] {kaleoInstanceId, kaleoTaskId};
		}

		Object result = null;

		if (useFinderCache) {
			result = finderCache.getResult(
				_finderPathFetchByKII_KTI, finderArgs);
		}

		if (result instanceof KaleoTaskInstanceToken) {
			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				(KaleoTaskInstanceToken)result;

			if ((kaleoInstanceId !=
					kaleoTaskInstanceToken.getKaleoInstanceId()) ||
				(kaleoTaskId != kaleoTaskInstanceToken.getKaleoTaskId())) {

				result = null;
			}
		}

		if (result == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_KII_KTI_KALEOINSTANCEID_2);

			sb.append(_FINDER_COLUMN_KII_KTI_KALEOTASKID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoInstanceId);

				queryPos.add(kaleoTaskId);

				List<KaleoTaskInstanceToken> list = query.list();

				if (list.isEmpty()) {
					if (useFinderCache) {
						finderCache.putResult(
							_finderPathFetchByKII_KTI, finderArgs, list);
					}
				}
				else {
					if (list.size() > 1) {
						Collections.sort(list, Collections.reverseOrder());

						if (_log.isWarnEnabled()) {
							if (!useFinderCache) {
								finderArgs = new Object[] {
									kaleoInstanceId, kaleoTaskId
								};
							}

							_log.warn(
								"KaleoTaskInstanceTokenPersistenceImpl.fetchByKII_KTI(long, long, boolean) with parameters (" +
									StringUtil.merge(finderArgs) +
										") yields a result set with more than 1 result. This violates the logical unique restriction. There is no order guarantee on which result is returned by this finder.");
						}
					}

					KaleoTaskInstanceToken kaleoTaskInstanceToken = list.get(0);

					result = kaleoTaskInstanceToken;

					cacheResult(kaleoTaskInstanceToken);
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
			return (KaleoTaskInstanceToken)result;
		}
	}

	/**
	 * Removes the kaleo task instance token where kaleoInstanceId = &#63; and kaleoTaskId = &#63; from the database.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param kaleoTaskId the kaleo task ID
	 * @return the kaleo task instance token that was removed
	 */
	@Override
	public KaleoTaskInstanceToken removeByKII_KTI(
			long kaleoInstanceId, long kaleoTaskId)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = findByKII_KTI(
			kaleoInstanceId, kaleoTaskId);

		return remove(kaleoTaskInstanceToken);
	}

	/**
	 * Returns the number of kaleo task instance tokens where kaleoInstanceId = &#63; and kaleoTaskId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param kaleoTaskId the kaleo task ID
	 * @return the number of matching kaleo task instance tokens
	 */
	@Override
	public int countByKII_KTI(long kaleoInstanceId, long kaleoTaskId) {
		FinderPath finderPath = _finderPathCountByKII_KTI;

		Object[] finderArgs = new Object[] {kaleoInstanceId, kaleoTaskId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOTASKINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_KII_KTI_KALEOINSTANCEID_2);

			sb.append(_FINDER_COLUMN_KII_KTI_KALEOTASKID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoInstanceId);

				queryPos.add(kaleoTaskId);

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

	private static final String _FINDER_COLUMN_KII_KTI_KALEOINSTANCEID_2 =
		"kaleoTaskInstanceToken.kaleoInstanceId = ? AND ";

	private static final String _FINDER_COLUMN_KII_KTI_KALEOTASKID_2 =
		"kaleoTaskInstanceToken.kaleoTaskId = ?";

	private FinderPath _finderPathWithPaginationFindByCN_CPK;
	private FinderPath _finderPathWithoutPaginationFindByCN_CPK;
	private FinderPath _finderPathCountByCN_CPK;

	/**
	 * Returns all the kaleo task instance tokens where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @return the matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByCN_CPK(
		String className, long classPK) {

		return findByCN_CPK(
			className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByCN_CPK(
		String className, long classPK, int start, int end) {

		return findByCN_CPK(className, classPK, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByCN_CPK(
		String className, long classPK, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		return findByCN_CPK(
			className, classPK, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where className = &#63; and classPK = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByCN_CPK(
		String className, long classPK, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		boolean useFinderCache) {

		className = Objects.toString(className, "");

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByCN_CPK;
				finderArgs = new Object[] {className, classPK};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByCN_CPK;
			finderArgs = new Object[] {
				className, classPK, start, end, orderByComparator
			};
		}

		List<KaleoTaskInstanceToken> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskInstanceToken>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskInstanceToken kaleoTaskInstanceToken : list) {
					if (!className.equals(
							kaleoTaskInstanceToken.getClassName()) ||
						(classPK != kaleoTaskInstanceToken.getClassPK())) {

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

			sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE);

			boolean bindClassName = false;

			if (className.isEmpty()) {
				sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAME_3);
			}
			else {
				bindClassName = true;

				sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskInstanceTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindClassName) {
					queryPos.add(className);
				}

				queryPos.add(classPK);

				list = (List<KaleoTaskInstanceToken>)QueryUtil.list(
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
	 * Returns the first kaleo task instance token in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByCN_CPK_First(
			String className, long classPK,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = fetchByCN_CPK_First(
			className, classPK, orderByComparator);

		if (kaleoTaskInstanceToken != null) {
			return kaleoTaskInstanceToken;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("className=");
		sb.append(className);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchTaskInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the first kaleo task instance token in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByCN_CPK_First(
		String className, long classPK,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		List<KaleoTaskInstanceToken> list = findByCN_CPK(
			className, classPK, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task instance token in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByCN_CPK_Last(
			String className, long classPK,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = fetchByCN_CPK_Last(
			className, classPK, orderByComparator);

		if (kaleoTaskInstanceToken != null) {
			return kaleoTaskInstanceToken;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("className=");
		sb.append(className);

		sb.append(", classPK=");
		sb.append(classPK);

		sb.append("}");

		throw new NoSuchTaskInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the last kaleo task instance token in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByCN_CPK_Last(
		String className, long classPK,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		int count = countByCN_CPK(className, classPK);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskInstanceToken> list = findByCN_CPK(
			className, classPK, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task instance tokens before and after the current kaleo task instance token in the ordered set where className = &#63; and classPK = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the current kaleo task instance token
	 * @param className the class name
	 * @param classPK the class pk
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a kaleo task instance token with the primary key could not be found
	 */
	@Override
	public KaleoTaskInstanceToken[] findByCN_CPK_PrevAndNext(
			long kaleoTaskInstanceTokenId, String className, long classPK,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		className = Objects.toString(className, "");

		KaleoTaskInstanceToken kaleoTaskInstanceToken = findByPrimaryKey(
			kaleoTaskInstanceTokenId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskInstanceToken[] array = new KaleoTaskInstanceTokenImpl[3];

			array[0] = getByCN_CPK_PrevAndNext(
				session, kaleoTaskInstanceToken, className, classPK,
				orderByComparator, true);

			array[1] = kaleoTaskInstanceToken;

			array[2] = getByCN_CPK_PrevAndNext(
				session, kaleoTaskInstanceToken, className, classPK,
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

	protected KaleoTaskInstanceToken getByCN_CPK_PrevAndNext(
		Session session, KaleoTaskInstanceToken kaleoTaskInstanceToken,
		String className, long classPK,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE);

		boolean bindClassName = false;

		if (className.isEmpty()) {
			sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAME_3);
		}
		else {
			bindClassName = true;

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAME_2);
		}

		sb.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

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
			sb.append(KaleoTaskInstanceTokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		if (bindClassName) {
			queryPos.add(className);
		}

		queryPos.add(classPK);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskInstanceToken)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskInstanceToken> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task instance tokens where className = &#63; and classPK = &#63; from the database.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 */
	@Override
	public void removeByCN_CPK(String className, long classPK) {
		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				findByCN_CPK(
					className, classPK, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoTaskInstanceToken);
		}
	}

	/**
	 * Returns the number of kaleo task instance tokens where className = &#63; and classPK = &#63;.
	 *
	 * @param className the class name
	 * @param classPK the class pk
	 * @return the number of matching kaleo task instance tokens
	 */
	@Override
	public int countByCN_CPK(String className, long classPK) {
		className = Objects.toString(className, "");

		FinderPath finderPath = _finderPathCountByCN_CPK;

		Object[] finderArgs = new Object[] {className, classPK};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOTASKINSTANCETOKEN_WHERE);

			boolean bindClassName = false;

			if (className.isEmpty()) {
				sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAME_3);
			}
			else {
				bindClassName = true;

				sb.append(_FINDER_COLUMN_CN_CPK_CLASSNAME_2);
			}

			sb.append(_FINDER_COLUMN_CN_CPK_CLASSPK_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				if (bindClassName) {
					queryPos.add(className);
				}

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

	private static final String _FINDER_COLUMN_CN_CPK_CLASSNAME_2 =
		"kaleoTaskInstanceToken.className = ? AND ";

	private static final String _FINDER_COLUMN_CN_CPK_CLASSNAME_3 =
		"(kaleoTaskInstanceToken.className IS NULL OR kaleoTaskInstanceToken.className = '') AND ";

	private static final String _FINDER_COLUMN_CN_CPK_CLASSPK_2 =
		"kaleoTaskInstanceToken.classPK = ?";

	private FinderPath _finderPathWithPaginationFindByC_U_C;
	private FinderPath _finderPathWithoutPaginationFindByC_U_C;
	private FinderPath _finderPathCountByC_U_C;

	/**
	 * Returns all the kaleo task instance tokens where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @return the matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByC_U_C(
		long companyId, long userId, boolean completed) {

		return findByC_U_C(
			companyId, userId, completed, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByC_U_C(
		long companyId, long userId, boolean completed, int start, int end) {

		return findByC_U_C(companyId, userId, completed, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByC_U_C(
		long companyId, long userId, boolean completed, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		return findByC_U_C(
			companyId, userId, completed, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findByC_U_C(
		long companyId, long userId, boolean completed, int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_U_C;
				finderArgs = new Object[] {companyId, userId, completed};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_U_C;
			finderArgs = new Object[] {
				companyId, userId, completed, start, end, orderByComparator
			};
		}

		List<KaleoTaskInstanceToken> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskInstanceToken>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoTaskInstanceToken kaleoTaskInstanceToken : list) {
					if ((companyId != kaleoTaskInstanceToken.getCompanyId()) ||
						(userId != kaleoTaskInstanceToken.getUserId()) ||
						(completed != kaleoTaskInstanceToken.isCompleted())) {

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

			sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_C_U_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_C_USERID_2);

			sb.append(_FINDER_COLUMN_C_U_C_COMPLETED_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoTaskInstanceTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

				queryPos.add(completed);

				list = (List<KaleoTaskInstanceToken>)QueryUtil.list(
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
	 * Returns the first kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByC_U_C_First(
			long companyId, long userId, boolean completed,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = fetchByC_U_C_First(
			companyId, userId, completed, orderByComparator);

		if (kaleoTaskInstanceToken != null) {
			return kaleoTaskInstanceToken;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchTaskInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the first kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByC_U_C_First(
		long companyId, long userId, boolean completed,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		List<KaleoTaskInstanceToken> list = findByC_U_C(
			companyId, userId, completed, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByC_U_C_Last(
			long companyId, long userId, boolean completed,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = fetchByC_U_C_Last(
			companyId, userId, completed, orderByComparator);

		if (kaleoTaskInstanceToken != null) {
			return kaleoTaskInstanceToken;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", userId=");
		sb.append(userId);

		sb.append(", completed=");
		sb.append(completed);

		sb.append("}");

		throw new NoSuchTaskInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the last kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo task instance token, or <code>null</code> if a matching kaleo task instance token could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByC_U_C_Last(
		long companyId, long userId, boolean completed,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		int count = countByC_U_C(companyId, userId, completed);

		if (count == 0) {
			return null;
		}

		List<KaleoTaskInstanceToken> list = findByC_U_C(
			companyId, userId, completed, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo task instance tokens before and after the current kaleo task instance token in the ordered set where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the current kaleo task instance token
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a kaleo task instance token with the primary key could not be found
	 */
	@Override
	public KaleoTaskInstanceToken[] findByC_U_C_PrevAndNext(
			long kaleoTaskInstanceTokenId, long companyId, long userId,
			boolean completed,
			OrderByComparator<KaleoTaskInstanceToken> orderByComparator)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = findByPrimaryKey(
			kaleoTaskInstanceTokenId);

		Session session = null;

		try {
			session = openSession();

			KaleoTaskInstanceToken[] array = new KaleoTaskInstanceTokenImpl[3];

			array[0] = getByC_U_C_PrevAndNext(
				session, kaleoTaskInstanceToken, companyId, userId, completed,
				orderByComparator, true);

			array[1] = kaleoTaskInstanceToken;

			array[2] = getByC_U_C_PrevAndNext(
				session, kaleoTaskInstanceToken, companyId, userId, completed,
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

	protected KaleoTaskInstanceToken getByC_U_C_PrevAndNext(
		Session session, KaleoTaskInstanceToken kaleoTaskInstanceToken,
		long companyId, long userId, boolean completed,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
		boolean previous) {

		StringBundler sb = null;

		if (orderByComparator != null) {
			sb = new StringBundler(
				6 + (orderByComparator.getOrderByConditionFields().length * 3) +
					(orderByComparator.getOrderByFields().length * 3));
		}
		else {
			sb = new StringBundler(5);
		}

		sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE);

		sb.append(_FINDER_COLUMN_C_U_C_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_U_C_USERID_2);

		sb.append(_FINDER_COLUMN_C_U_C_COMPLETED_2);

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
			sb.append(KaleoTaskInstanceTokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(userId);

		queryPos.add(completed);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoTaskInstanceToken)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoTaskInstanceToken> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo task instance tokens where companyId = &#63; and userId = &#63; and completed = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 */
	@Override
	public void removeByC_U_C(long companyId, long userId, boolean completed) {
		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				findByC_U_C(
					companyId, userId, completed, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoTaskInstanceToken);
		}
	}

	/**
	 * Returns the number of kaleo task instance tokens where companyId = &#63; and userId = &#63; and completed = &#63;.
	 *
	 * @param companyId the company ID
	 * @param userId the user ID
	 * @param completed the completed
	 * @return the number of matching kaleo task instance tokens
	 */
	@Override
	public int countByC_U_C(long companyId, long userId, boolean completed) {
		FinderPath finderPath = _finderPathCountByC_U_C;

		Object[] finderArgs = new Object[] {companyId, userId, completed};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_KALEOTASKINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_C_U_C_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_U_C_USERID_2);

			sb.append(_FINDER_COLUMN_C_U_C_COMPLETED_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(userId);

				queryPos.add(completed);

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

	private static final String _FINDER_COLUMN_C_U_C_COMPANYID_2 =
		"kaleoTaskInstanceToken.companyId = ? AND ";

	private static final String _FINDER_COLUMN_C_U_C_USERID_2 =
		"kaleoTaskInstanceToken.userId = ? AND ";

	private static final String _FINDER_COLUMN_C_U_C_COMPLETED_2 =
		"kaleoTaskInstanceToken.completed = ?";

	public KaleoTaskInstanceTokenPersistenceImpl() {
		setModelClass(KaleoTaskInstanceToken.class);

		setModelImplClass(KaleoTaskInstanceTokenImpl.class);
		setModelPKClass(long.class);

		setTable(KaleoTaskInstanceTokenTable.INSTANCE);
	}

	/**
	 * Caches the kaleo task instance token in the entity cache if it is enabled.
	 *
	 * @param kaleoTaskInstanceToken the kaleo task instance token
	 */
	@Override
	public void cacheResult(KaleoTaskInstanceToken kaleoTaskInstanceToken) {
		entityCache.putResult(
			KaleoTaskInstanceTokenImpl.class,
			kaleoTaskInstanceToken.getPrimaryKey(), kaleoTaskInstanceToken);

		finderCache.putResult(
			_finderPathFetchByKII_KTI,
			new Object[] {
				kaleoTaskInstanceToken.getKaleoInstanceId(),
				kaleoTaskInstanceToken.getKaleoTaskId()
			},
			kaleoTaskInstanceToken);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the kaleo task instance tokens in the entity cache if it is enabled.
	 *
	 * @param kaleoTaskInstanceTokens the kaleo task instance tokens
	 */
	@Override
	public void cacheResult(
		List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens) {

		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (kaleoTaskInstanceTokens.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				kaleoTaskInstanceTokens) {

			if (entityCache.getResult(
					KaleoTaskInstanceTokenImpl.class,
					kaleoTaskInstanceToken.getPrimaryKey()) == null) {

				cacheResult(kaleoTaskInstanceToken);
			}
		}
	}

	/**
	 * Clears the cache for all kaleo task instance tokens.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoTaskInstanceTokenImpl.class);

		finderCache.clearCache(KaleoTaskInstanceTokenImpl.class);
	}

	/**
	 * Clears the cache for the kaleo task instance token.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoTaskInstanceToken kaleoTaskInstanceToken) {
		entityCache.removeResult(
			KaleoTaskInstanceTokenImpl.class, kaleoTaskInstanceToken);
	}

	@Override
	public void clearCache(
		List<KaleoTaskInstanceToken> kaleoTaskInstanceTokens) {

		for (KaleoTaskInstanceToken kaleoTaskInstanceToken :
				kaleoTaskInstanceTokens) {

			entityCache.removeResult(
				KaleoTaskInstanceTokenImpl.class, kaleoTaskInstanceToken);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(KaleoTaskInstanceTokenImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(
				KaleoTaskInstanceTokenImpl.class, primaryKey);
		}
	}

	protected void cacheUniqueFindersCache(
		KaleoTaskInstanceTokenModelImpl kaleoTaskInstanceTokenModelImpl) {

		Object[] args = new Object[] {
			kaleoTaskInstanceTokenModelImpl.getKaleoInstanceId(),
			kaleoTaskInstanceTokenModelImpl.getKaleoTaskId()
		};

		finderCache.putResult(_finderPathCountByKII_KTI, args, Long.valueOf(1));
		finderCache.putResult(
			_finderPathFetchByKII_KTI, args, kaleoTaskInstanceTokenModelImpl);
	}

	/**
	 * Creates a new kaleo task instance token with the primary key. Does not add the kaleo task instance token to the database.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key for the new kaleo task instance token
	 * @return the new kaleo task instance token
	 */
	@Override
	public KaleoTaskInstanceToken create(long kaleoTaskInstanceTokenId) {
		KaleoTaskInstanceToken kaleoTaskInstanceToken =
			new KaleoTaskInstanceTokenImpl();

		kaleoTaskInstanceToken.setNew(true);
		kaleoTaskInstanceToken.setPrimaryKey(kaleoTaskInstanceTokenId);

		kaleoTaskInstanceToken.setCompanyId(CompanyThreadLocal.getCompanyId());

		return kaleoTaskInstanceToken;
	}

	/**
	 * Removes the kaleo task instance token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the kaleo task instance token
	 * @return the kaleo task instance token that was removed
	 * @throws NoSuchTaskInstanceTokenException if a kaleo task instance token with the primary key could not be found
	 */
	@Override
	public KaleoTaskInstanceToken remove(long kaleoTaskInstanceTokenId)
		throws NoSuchTaskInstanceTokenException {

		return remove((Serializable)kaleoTaskInstanceTokenId);
	}

	/**
	 * Removes the kaleo task instance token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo task instance token
	 * @return the kaleo task instance token that was removed
	 * @throws NoSuchTaskInstanceTokenException if a kaleo task instance token with the primary key could not be found
	 */
	@Override
	public KaleoTaskInstanceToken remove(Serializable primaryKey)
		throws NoSuchTaskInstanceTokenException {

		Session session = null;

		try {
			session = openSession();

			KaleoTaskInstanceToken kaleoTaskInstanceToken =
				(KaleoTaskInstanceToken)session.get(
					KaleoTaskInstanceTokenImpl.class, primaryKey);

			if (kaleoTaskInstanceToken == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchTaskInstanceTokenException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(kaleoTaskInstanceToken);
		}
		catch (NoSuchTaskInstanceTokenException noSuchEntityException) {
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
	protected KaleoTaskInstanceToken removeImpl(
		KaleoTaskInstanceToken kaleoTaskInstanceToken) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoTaskInstanceToken)) {
				kaleoTaskInstanceToken = (KaleoTaskInstanceToken)session.get(
					KaleoTaskInstanceTokenImpl.class,
					kaleoTaskInstanceToken.getPrimaryKeyObj());
			}

			if (kaleoTaskInstanceToken != null) {
				session.delete(kaleoTaskInstanceToken);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoTaskInstanceToken != null) {
			clearCache(kaleoTaskInstanceToken);
		}

		return kaleoTaskInstanceToken;
	}

	@Override
	public KaleoTaskInstanceToken updateImpl(
		KaleoTaskInstanceToken kaleoTaskInstanceToken) {

		boolean isNew = kaleoTaskInstanceToken.isNew();

		if (!(kaleoTaskInstanceToken instanceof
				KaleoTaskInstanceTokenModelImpl)) {

			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kaleoTaskInstanceToken.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					kaleoTaskInstanceToken);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoTaskInstanceToken proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoTaskInstanceToken implementation " +
					kaleoTaskInstanceToken.getClass());
		}

		KaleoTaskInstanceTokenModelImpl kaleoTaskInstanceTokenModelImpl =
			(KaleoTaskInstanceTokenModelImpl)kaleoTaskInstanceToken;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (kaleoTaskInstanceToken.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoTaskInstanceToken.setCreateDate(date);
			}
			else {
				kaleoTaskInstanceToken.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!kaleoTaskInstanceTokenModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoTaskInstanceToken.setModifiedDate(date);
			}
			else {
				kaleoTaskInstanceToken.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(kaleoTaskInstanceToken);
			}
			else {
				kaleoTaskInstanceToken = (KaleoTaskInstanceToken)session.merge(
					kaleoTaskInstanceToken);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			KaleoTaskInstanceTokenImpl.class, kaleoTaskInstanceTokenModelImpl,
			false, true);

		cacheUniqueFindersCache(kaleoTaskInstanceTokenModelImpl);

		if (isNew) {
			kaleoTaskInstanceToken.setNew(false);
		}

		kaleoTaskInstanceToken.resetOriginalValues();

		return kaleoTaskInstanceToken;
	}

	/**
	 * Returns the kaleo task instance token with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo task instance token
	 * @return the kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a kaleo task instance token with the primary key could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByPrimaryKey(Serializable primaryKey)
		throws NoSuchTaskInstanceTokenException {

		KaleoTaskInstanceToken kaleoTaskInstanceToken = fetchByPrimaryKey(
			primaryKey);

		if (kaleoTaskInstanceToken == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchTaskInstanceTokenException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return kaleoTaskInstanceToken;
	}

	/**
	 * Returns the kaleo task instance token with the primary key or throws a <code>NoSuchTaskInstanceTokenException</code> if it could not be found.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the kaleo task instance token
	 * @return the kaleo task instance token
	 * @throws NoSuchTaskInstanceTokenException if a kaleo task instance token with the primary key could not be found
	 */
	@Override
	public KaleoTaskInstanceToken findByPrimaryKey(
			long kaleoTaskInstanceTokenId)
		throws NoSuchTaskInstanceTokenException {

		return findByPrimaryKey((Serializable)kaleoTaskInstanceTokenId);
	}

	/**
	 * Returns the kaleo task instance token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoTaskInstanceTokenId the primary key of the kaleo task instance token
	 * @return the kaleo task instance token, or <code>null</code> if a kaleo task instance token with the primary key could not be found
	 */
	@Override
	public KaleoTaskInstanceToken fetchByPrimaryKey(
		long kaleoTaskInstanceTokenId) {

		return fetchByPrimaryKey((Serializable)kaleoTaskInstanceTokenId);
	}

	/**
	 * Returns all the kaleo task instance tokens.
	 *
	 * @return the kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo task instance tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @return the range of kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findAll(
		int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo task instance tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoTaskInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo task instance tokens
	 * @param end the upper bound of the range of kaleo task instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo task instance tokens
	 */
	@Override
	public List<KaleoTaskInstanceToken> findAll(
		int start, int end,
		OrderByComparator<KaleoTaskInstanceToken> orderByComparator,
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

		List<KaleoTaskInstanceToken> list = null;

		if (useFinderCache) {
			list = (List<KaleoTaskInstanceToken>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_KALEOTASKINSTANCETOKEN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_KALEOTASKINSTANCETOKEN;

				sql = sql.concat(KaleoTaskInstanceTokenModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<KaleoTaskInstanceToken>)QueryUtil.list(
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
	 * Removes all the kaleo task instance tokens from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoTaskInstanceToken kaleoTaskInstanceToken : findAll()) {
			remove(kaleoTaskInstanceToken);
		}
	}

	/**
	 * Returns the number of kaleo task instance tokens.
	 *
	 * @return the number of kaleo task instance tokens
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
					_SQL_COUNT_KALEOTASKINSTANCETOKEN);

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
		return "kaleoTaskInstanceTokenId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KALEOTASKINSTANCETOKEN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoTaskInstanceTokenModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo task instance token persistence.
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

		_finderPathWithPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCompanyId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId"}, true);

		_finderPathWithoutPaginationFindByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			true);

		_finderPathCountByCompanyId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCompanyId",
			new String[] {Long.class.getName()}, new String[] {"companyId"},
			false);

		_finderPathWithPaginationFindByKaleoDefinitionVersionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITH_PAGINATION,
				"findByKaleoDefinitionVersionId",
				new String[] {
					Long.class.getName(), Integer.class.getName(),
					Integer.class.getName(), OrderByComparator.class.getName()
				},
				new String[] {"kaleoDefinitionVersionId"}, true);

		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId =
			new FinderPath(
				FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
				"findByKaleoDefinitionVersionId",
				new String[] {Long.class.getName()},
				new String[] {"kaleoDefinitionVersionId"}, true);

		_finderPathCountByKaleoDefinitionVersionId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION,
			"countByKaleoDefinitionVersionId",
			new String[] {Long.class.getName()},
			new String[] {"kaleoDefinitionVersionId"}, false);

		_finderPathWithPaginationFindByKaleoInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByKaleoInstanceId",
			new String[] {
				Long.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"kaleoInstanceId"}, true);

		_finderPathWithoutPaginationFindByKaleoInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByKaleoInstanceId",
			new String[] {Long.class.getName()},
			new String[] {"kaleoInstanceId"}, true);

		_finderPathCountByKaleoInstanceId = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKaleoInstanceId",
			new String[] {Long.class.getName()},
			new String[] {"kaleoInstanceId"}, false);

		_finderPathWithPaginationFindByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_U",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "userId"}, true);

		_finderPathWithoutPaginationFindByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, true);

		_finderPathCountByC_U = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "userId"}, false);

		_finderPathFetchByKII_KTI = new FinderPath(
			FINDER_CLASS_NAME_ENTITY, "fetchByKII_KTI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"kaleoInstanceId", "kaleoTaskId"}, true);

		_finderPathCountByKII_KTI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByKII_KTI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"kaleoInstanceId", "kaleoTaskId"}, false);

		_finderPathWithPaginationFindByCN_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByCN_CPK",
			new String[] {
				String.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"className", "classPK"}, true);

		_finderPathWithoutPaginationFindByCN_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByCN_CPK",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"className", "classPK"}, true);

		_finderPathCountByCN_CPK = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByCN_CPK",
			new String[] {String.class.getName(), Long.class.getName()},
			new String[] {"className", "classPK"}, false);

		_finderPathWithPaginationFindByC_U_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_U_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {"companyId", "userId", "completed"}, true);

		_finderPathWithoutPaginationFindByC_U_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_U_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "userId", "completed"}, true);

		_finderPathCountByC_U_C = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_U_C",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Boolean.class.getName()
			},
			new String[] {"companyId", "userId", "completed"}, false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(KaleoTaskInstanceTokenImpl.class.getName());
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.SERVICE_CONFIGURATION_FILTER,
		unbind = "-"
	)
	public void setConfiguration(Configuration configuration) {
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	@Override
	@Reference(
		target = KaleoPersistenceConstants.ORIGIN_BUNDLE_SYMBOLIC_NAME_FILTER,
		unbind = "-"
	)
	public void setSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}

	@Reference
	protected EntityCache entityCache;

	@Reference
	protected FinderCache finderCache;

	private static final String _SQL_SELECT_KALEOTASKINSTANCETOKEN =
		"SELECT kaleoTaskInstanceToken FROM KaleoTaskInstanceToken kaleoTaskInstanceToken";

	private static final String _SQL_SELECT_KALEOTASKINSTANCETOKEN_WHERE =
		"SELECT kaleoTaskInstanceToken FROM KaleoTaskInstanceToken kaleoTaskInstanceToken WHERE ";

	private static final String _SQL_COUNT_KALEOTASKINSTANCETOKEN =
		"SELECT COUNT(kaleoTaskInstanceToken) FROM KaleoTaskInstanceToken kaleoTaskInstanceToken";

	private static final String _SQL_COUNT_KALEOTASKINSTANCETOKEN_WHERE =
		"SELECT COUNT(kaleoTaskInstanceToken) FROM KaleoTaskInstanceToken kaleoTaskInstanceToken WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS =
		"kaleoTaskInstanceToken.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KaleoTaskInstanceToken exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KaleoTaskInstanceToken exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoTaskInstanceTokenPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private KaleoTaskInstanceTokenModelArgumentsResolver
		_kaleoTaskInstanceTokenModelArgumentsResolver;

}