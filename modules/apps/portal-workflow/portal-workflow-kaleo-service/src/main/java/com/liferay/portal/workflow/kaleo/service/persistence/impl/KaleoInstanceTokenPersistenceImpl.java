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
import com.liferay.portal.workflow.kaleo.exception.NoSuchInstanceTokenException;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceToken;
import com.liferay.portal.workflow.kaleo.model.KaleoInstanceTokenTable;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceTokenImpl;
import com.liferay.portal.workflow.kaleo.model.impl.KaleoInstanceTokenModelImpl;
import com.liferay.portal.workflow.kaleo.service.persistence.KaleoInstanceTokenPersistence;
import com.liferay.portal.workflow.kaleo.service.persistence.impl.constants.KaleoPersistenceConstants;

import java.io.Serializable;

import java.lang.reflect.InvocationHandler;

import java.sql.Timestamp;

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
 * The persistence implementation for the kaleo instance token service.
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Brian Wing Shun Chan
 * @generated
 */
@Component(
	service = {KaleoInstanceTokenPersistence.class, BasePersistence.class}
)
public class KaleoInstanceTokenPersistenceImpl
	extends BasePersistenceImpl<KaleoInstanceToken>
	implements KaleoInstanceTokenPersistence {

	/*
	 * NOTE FOR DEVELOPERS:
	 *
	 * Never modify or reference this class directly. Always use <code>KaleoInstanceTokenUtil</code> to access the kaleo instance token persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
	 */
	public static final String FINDER_CLASS_NAME_ENTITY =
		KaleoInstanceTokenImpl.class.getName();

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
	 * Returns all the kaleo instance tokens where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByCompanyId(long companyId) {
		return findByCompanyId(
			companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo instance tokens where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @return the range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByCompanyId(
		long companyId, int start, int end) {

		return findByCompanyId(companyId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return findByCompanyId(companyId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where companyId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByCompanyId(
		long companyId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
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

		List<KaleoInstanceToken> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstanceToken>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoInstanceToken kaleoInstanceToken : list) {
					if (companyId != kaleoInstanceToken.getCompanyId()) {
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

			sb.append(_SQL_SELECT_KALEOINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_COMPANYID_COMPANYID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoInstanceTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				list = (List<KaleoInstanceToken>)QueryUtil.list(
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
	 * Returns the first kaleo instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken findByCompanyId_First(
			long companyId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = fetchByCompanyId_First(
			companyId, orderByComparator);

		if (kaleoInstanceToken != null) {
			return kaleoInstanceToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken fetchByCompanyId_First(
		long companyId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		List<KaleoInstanceToken> list = findByCompanyId(
			companyId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken findByCompanyId_Last(
			long companyId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = fetchByCompanyId_Last(
			companyId, orderByComparator);

		if (kaleoInstanceToken != null) {
			return kaleoInstanceToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append("}");

		throw new NoSuchInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken fetchByCompanyId_Last(
		long companyId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		int count = countByCompanyId(companyId);

		if (count == 0) {
			return null;
		}

		List<KaleoInstanceToken> list = findByCompanyId(
			companyId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo instance tokens before and after the current kaleo instance token in the ordered set where companyId = &#63;.
	 *
	 * @param kaleoInstanceTokenId the primary key of the current kaleo instance token
	 * @param companyId the company ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance token
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	@Override
	public KaleoInstanceToken[] findByCompanyId_PrevAndNext(
			long kaleoInstanceTokenId, long companyId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = findByPrimaryKey(
			kaleoInstanceTokenId);

		Session session = null;

		try {
			session = openSession();

			KaleoInstanceToken[] array = new KaleoInstanceTokenImpl[3];

			array[0] = getByCompanyId_PrevAndNext(
				session, kaleoInstanceToken, companyId, orderByComparator,
				true);

			array[1] = kaleoInstanceToken;

			array[2] = getByCompanyId_PrevAndNext(
				session, kaleoInstanceToken, companyId, orderByComparator,
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

	protected KaleoInstanceToken getByCompanyId_PrevAndNext(
		Session session, KaleoInstanceToken kaleoInstanceToken, long companyId,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOINSTANCETOKEN_WHERE);

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
			sb.append(KaleoInstanceTokenModelImpl.ORDER_BY_JPQL);
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
						kaleoInstanceToken)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoInstanceToken> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo instance tokens where companyId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 */
	@Override
	public void removeByCompanyId(long companyId) {
		for (KaleoInstanceToken kaleoInstanceToken :
				findByCompanyId(
					companyId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoInstanceToken);
		}
	}

	/**
	 * Returns the number of kaleo instance tokens where companyId = &#63;.
	 *
	 * @param companyId the company ID
	 * @return the number of matching kaleo instance tokens
	 */
	@Override
	public int countByCompanyId(long companyId) {
		FinderPath finderPath = _finderPathCountByCompanyId;

		Object[] finderArgs = new Object[] {companyId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOINSTANCETOKEN_WHERE);

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
		"kaleoInstanceToken.companyId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoDefinitionVersionId;
	private FinderPath
		_finderPathWithoutPaginationFindByKaleoDefinitionVersionId;
	private FinderPath _finderPathCountByKaleoDefinitionVersionId;

	/**
	 * Returns all the kaleo instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
			null);
	}

	/**
	 * Returns a range of all the kaleo instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @return the range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
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

		List<KaleoInstanceToken> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstanceToken>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoInstanceToken kaleoInstanceToken : list) {
					if (kaleoDefinitionVersionId !=
							kaleoInstanceToken.getKaleoDefinitionVersionId()) {

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

			sb.append(_SQL_SELECT_KALEOINSTANCETOKEN_WHERE);

			sb.append(
				_FINDER_COLUMN_KALEODEFINITIONVERSIONID_KALEODEFINITIONVERSIONID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoInstanceTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoDefinitionVersionId);

				list = (List<KaleoInstanceToken>)QueryUtil.list(
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
	 * Returns the first kaleo instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken findByKaleoDefinitionVersionId_First(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken =
			fetchByKaleoDefinitionVersionId_First(
				kaleoDefinitionVersionId, orderByComparator);

		if (kaleoInstanceToken != null) {
			return kaleoInstanceToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken fetchByKaleoDefinitionVersionId_First(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		List<KaleoInstanceToken> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken findByKaleoDefinitionVersionId_Last(
			long kaleoDefinitionVersionId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken =
			fetchByKaleoDefinitionVersionId_Last(
				kaleoDefinitionVersionId, orderByComparator);

		if (kaleoInstanceToken != null) {
			return kaleoInstanceToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoDefinitionVersionId=");
		sb.append(kaleoDefinitionVersionId);

		sb.append("}");

		throw new NoSuchInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken fetchByKaleoDefinitionVersionId_Last(
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		int count = countByKaleoDefinitionVersionId(kaleoDefinitionVersionId);

		if (count == 0) {
			return null;
		}

		List<KaleoInstanceToken> list = findByKaleoDefinitionVersionId(
			kaleoDefinitionVersionId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo instance tokens before and after the current kaleo instance token in the ordered set where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoInstanceTokenId the primary key of the current kaleo instance token
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance token
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	@Override
	public KaleoInstanceToken[] findByKaleoDefinitionVersionId_PrevAndNext(
			long kaleoInstanceTokenId, long kaleoDefinitionVersionId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = findByPrimaryKey(
			kaleoInstanceTokenId);

		Session session = null;

		try {
			session = openSession();

			KaleoInstanceToken[] array = new KaleoInstanceTokenImpl[3];

			array[0] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoInstanceToken, kaleoDefinitionVersionId,
				orderByComparator, true);

			array[1] = kaleoInstanceToken;

			array[2] = getByKaleoDefinitionVersionId_PrevAndNext(
				session, kaleoInstanceToken, kaleoDefinitionVersionId,
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

	protected KaleoInstanceToken getByKaleoDefinitionVersionId_PrevAndNext(
		Session session, KaleoInstanceToken kaleoInstanceToken,
		long kaleoDefinitionVersionId,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOINSTANCETOKEN_WHERE);

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
			sb.append(KaleoInstanceTokenModelImpl.ORDER_BY_JPQL);
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
						kaleoInstanceToken)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoInstanceToken> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo instance tokens where kaleoDefinitionVersionId = &#63; from the database.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 */
	@Override
	public void removeByKaleoDefinitionVersionId(
		long kaleoDefinitionVersionId) {

		for (KaleoInstanceToken kaleoInstanceToken :
				findByKaleoDefinitionVersionId(
					kaleoDefinitionVersionId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoInstanceToken);
		}
	}

	/**
	 * Returns the number of kaleo instance tokens where kaleoDefinitionVersionId = &#63;.
	 *
	 * @param kaleoDefinitionVersionId the kaleo definition version ID
	 * @return the number of matching kaleo instance tokens
	 */
	@Override
	public int countByKaleoDefinitionVersionId(long kaleoDefinitionVersionId) {
		FinderPath finderPath = _finderPathCountByKaleoDefinitionVersionId;

		Object[] finderArgs = new Object[] {kaleoDefinitionVersionId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOINSTANCETOKEN_WHERE);

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
			"kaleoInstanceToken.kaleoDefinitionVersionId = ?";

	private FinderPath _finderPathWithPaginationFindByKaleoInstanceId;
	private FinderPath _finderPathWithoutPaginationFindByKaleoInstanceId;
	private FinderPath _finderPathCountByKaleoInstanceId;

	/**
	 * Returns all the kaleo instance tokens where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId) {

		return findByKaleoInstanceId(
			kaleoInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo instance tokens where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @return the range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end) {

		return findByKaleoInstanceId(kaleoInstanceId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return findByKaleoInstanceId(
			kaleoInstanceId, start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where kaleoInstanceId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByKaleoInstanceId(
		long kaleoInstanceId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
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

		List<KaleoInstanceToken> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstanceToken>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoInstanceToken kaleoInstanceToken : list) {
					if (kaleoInstanceId !=
							kaleoInstanceToken.getKaleoInstanceId()) {

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

			sb.append(_SQL_SELECT_KALEOINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_KALEOINSTANCEID_KALEOINSTANCEID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoInstanceTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(kaleoInstanceId);

				list = (List<KaleoInstanceToken>)QueryUtil.list(
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
	 * Returns the first kaleo instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken findByKaleoInstanceId_First(
			long kaleoInstanceId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = fetchByKaleoInstanceId_First(
			kaleoInstanceId, orderByComparator);

		if (kaleoInstanceToken != null) {
			return kaleoInstanceToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoInstanceId=");
		sb.append(kaleoInstanceId);

		sb.append("}");

		throw new NoSuchInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken fetchByKaleoInstanceId_First(
		long kaleoInstanceId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		List<KaleoInstanceToken> list = findByKaleoInstanceId(
			kaleoInstanceId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken findByKaleoInstanceId_Last(
			long kaleoInstanceId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = fetchByKaleoInstanceId_Last(
			kaleoInstanceId, orderByComparator);

		if (kaleoInstanceToken != null) {
			return kaleoInstanceToken;
		}

		StringBundler sb = new StringBundler(4);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("kaleoInstanceId=");
		sb.append(kaleoInstanceId);

		sb.append("}");

		throw new NoSuchInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken fetchByKaleoInstanceId_Last(
		long kaleoInstanceId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		int count = countByKaleoInstanceId(kaleoInstanceId);

		if (count == 0) {
			return null;
		}

		List<KaleoInstanceToken> list = findByKaleoInstanceId(
			kaleoInstanceId, count - 1, count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo instance tokens before and after the current kaleo instance token in the ordered set where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceTokenId the primary key of the current kaleo instance token
	 * @param kaleoInstanceId the kaleo instance ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance token
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	@Override
	public KaleoInstanceToken[] findByKaleoInstanceId_PrevAndNext(
			long kaleoInstanceTokenId, long kaleoInstanceId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = findByPrimaryKey(
			kaleoInstanceTokenId);

		Session session = null;

		try {
			session = openSession();

			KaleoInstanceToken[] array = new KaleoInstanceTokenImpl[3];

			array[0] = getByKaleoInstanceId_PrevAndNext(
				session, kaleoInstanceToken, kaleoInstanceId, orderByComparator,
				true);

			array[1] = kaleoInstanceToken;

			array[2] = getByKaleoInstanceId_PrevAndNext(
				session, kaleoInstanceToken, kaleoInstanceId, orderByComparator,
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

	protected KaleoInstanceToken getByKaleoInstanceId_PrevAndNext(
		Session session, KaleoInstanceToken kaleoInstanceToken,
		long kaleoInstanceId,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOINSTANCETOKEN_WHERE);

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
			sb.append(KaleoInstanceTokenModelImpl.ORDER_BY_JPQL);
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
						kaleoInstanceToken)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoInstanceToken> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo instance tokens where kaleoInstanceId = &#63; from the database.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 */
	@Override
	public void removeByKaleoInstanceId(long kaleoInstanceId) {
		for (KaleoInstanceToken kaleoInstanceToken :
				findByKaleoInstanceId(
					kaleoInstanceId, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
					null)) {

			remove(kaleoInstanceToken);
		}
	}

	/**
	 * Returns the number of kaleo instance tokens where kaleoInstanceId = &#63;.
	 *
	 * @param kaleoInstanceId the kaleo instance ID
	 * @return the number of matching kaleo instance tokens
	 */
	@Override
	public int countByKaleoInstanceId(long kaleoInstanceId) {
		FinderPath finderPath = _finderPathCountByKaleoInstanceId;

		Object[] finderArgs = new Object[] {kaleoInstanceId};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(2);

			sb.append(_SQL_COUNT_KALEOINSTANCETOKEN_WHERE);

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
			"kaleoInstanceToken.kaleoInstanceId = ?";

	private FinderPath _finderPathWithPaginationFindByC_PKITI;
	private FinderPath _finderPathWithoutPaginationFindByC_PKITI;
	private FinderPath _finderPathCountByC_PKITI;

	/**
	 * Returns all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @return the matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByC_PKITI(
		long companyId, long parentKaleoInstanceTokenId) {

		return findByC_PKITI(
			companyId, parentKaleoInstanceTokenId, QueryUtil.ALL_POS,
			QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @return the range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByC_PKITI(
		long companyId, long parentKaleoInstanceTokenId, int start, int end) {

		return findByC_PKITI(
			companyId, parentKaleoInstanceTokenId, start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByC_PKITI(
		long companyId, long parentKaleoInstanceTokenId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return findByC_PKITI(
			companyId, parentKaleoInstanceTokenId, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByC_PKITI(
		long companyId, long parentKaleoInstanceTokenId, int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_PKITI;
				finderArgs = new Object[] {
					companyId, parentKaleoInstanceTokenId
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_PKITI;
			finderArgs = new Object[] {
				companyId, parentKaleoInstanceTokenId, start, end,
				orderByComparator
			};
		}

		List<KaleoInstanceToken> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstanceToken>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoInstanceToken kaleoInstanceToken : list) {
					if ((companyId != kaleoInstanceToken.getCompanyId()) ||
						(parentKaleoInstanceTokenId !=
							kaleoInstanceToken.
								getParentKaleoInstanceTokenId())) {

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

			sb.append(_SQL_SELECT_KALEOINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_C_PKITI_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_PKITI_PARENTKALEOINSTANCETOKENID_2);

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoInstanceTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(parentKaleoInstanceTokenId);

				list = (List<KaleoInstanceToken>)QueryUtil.list(
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
	 * Returns the first kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken findByC_PKITI_First(
			long companyId, long parentKaleoInstanceTokenId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = fetchByC_PKITI_First(
			companyId, parentKaleoInstanceTokenId, orderByComparator);

		if (kaleoInstanceToken != null) {
			return kaleoInstanceToken;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", parentKaleoInstanceTokenId=");
		sb.append(parentKaleoInstanceTokenId);

		sb.append("}");

		throw new NoSuchInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken fetchByC_PKITI_First(
		long companyId, long parentKaleoInstanceTokenId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		List<KaleoInstanceToken> list = findByC_PKITI(
			companyId, parentKaleoInstanceTokenId, 0, 1, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken findByC_PKITI_Last(
			long companyId, long parentKaleoInstanceTokenId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = fetchByC_PKITI_Last(
			companyId, parentKaleoInstanceTokenId, orderByComparator);

		if (kaleoInstanceToken != null) {
			return kaleoInstanceToken;
		}

		StringBundler sb = new StringBundler(6);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", parentKaleoInstanceTokenId=");
		sb.append(parentKaleoInstanceTokenId);

		sb.append("}");

		throw new NoSuchInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken fetchByC_PKITI_Last(
		long companyId, long parentKaleoInstanceTokenId,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		int count = countByC_PKITI(companyId, parentKaleoInstanceTokenId);

		if (count == 0) {
			return null;
		}

		List<KaleoInstanceToken> list = findByC_PKITI(
			companyId, parentKaleoInstanceTokenId, count - 1, count,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo instance tokens before and after the current kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param kaleoInstanceTokenId the primary key of the current kaleo instance token
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance token
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	@Override
	public KaleoInstanceToken[] findByC_PKITI_PrevAndNext(
			long kaleoInstanceTokenId, long companyId,
			long parentKaleoInstanceTokenId,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = findByPrimaryKey(
			kaleoInstanceTokenId);

		Session session = null;

		try {
			session = openSession();

			KaleoInstanceToken[] array = new KaleoInstanceTokenImpl[3];

			array[0] = getByC_PKITI_PrevAndNext(
				session, kaleoInstanceToken, companyId,
				parentKaleoInstanceTokenId, orderByComparator, true);

			array[1] = kaleoInstanceToken;

			array[2] = getByC_PKITI_PrevAndNext(
				session, kaleoInstanceToken, companyId,
				parentKaleoInstanceTokenId, orderByComparator, false);

			return array;
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}
	}

	protected KaleoInstanceToken getByC_PKITI_PrevAndNext(
		Session session, KaleoInstanceToken kaleoInstanceToken, long companyId,
		long parentKaleoInstanceTokenId,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOINSTANCETOKEN_WHERE);

		sb.append(_FINDER_COLUMN_C_PKITI_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_PKITI_PARENTKALEOINSTANCETOKENID_2);

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
			sb.append(KaleoInstanceTokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(parentKaleoInstanceTokenId);

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoInstanceToken)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoInstanceToken> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 */
	@Override
	public void removeByC_PKITI(
		long companyId, long parentKaleoInstanceTokenId) {

		for (KaleoInstanceToken kaleoInstanceToken :
				findByC_PKITI(
					companyId, parentKaleoInstanceTokenId, QueryUtil.ALL_POS,
					QueryUtil.ALL_POS, null)) {

			remove(kaleoInstanceToken);
		}
	}

	/**
	 * Returns the number of kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @return the number of matching kaleo instance tokens
	 */
	@Override
	public int countByC_PKITI(long companyId, long parentKaleoInstanceTokenId) {
		FinderPath finderPath = _finderPathCountByC_PKITI;

		Object[] finderArgs = new Object[] {
			companyId, parentKaleoInstanceTokenId
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(3);

			sb.append(_SQL_COUNT_KALEOINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_C_PKITI_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_PKITI_PARENTKALEOINSTANCETOKENID_2);

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(parentKaleoInstanceTokenId);

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

	private static final String _FINDER_COLUMN_C_PKITI_COMPANYID_2 =
		"kaleoInstanceToken.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_PKITI_PARENTKALEOINSTANCETOKENID_2 =
			"kaleoInstanceToken.parentKaleoInstanceTokenId = ?";

	private FinderPath _finderPathWithPaginationFindByC_PKITI_CD;
	private FinderPath _finderPathWithoutPaginationFindByC_PKITI_CD;
	private FinderPath _finderPathCountByC_PKITI_CD;

	/**
	 * Returns all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @return the matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByC_PKITI_CD(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate) {

		return findByC_PKITI_CD(
			companyId, parentKaleoInstanceTokenId, completionDate,
			QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @return the range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByC_PKITI_CD(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate,
		int start, int end) {

		return findByC_PKITI_CD(
			companyId, parentKaleoInstanceTokenId, completionDate, start, end,
			null);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByC_PKITI_CD(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate,
		int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return findByC_PKITI_CD(
			companyId, parentKaleoInstanceTokenId, completionDate, start, end,
			orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of matching kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findByC_PKITI_CD(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate,
		int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
		boolean useFinderCache) {

		FinderPath finderPath = null;
		Object[] finderArgs = null;

		if ((start == QueryUtil.ALL_POS) && (end == QueryUtil.ALL_POS) &&
			(orderByComparator == null)) {

			if (useFinderCache) {
				finderPath = _finderPathWithoutPaginationFindByC_PKITI_CD;
				finderArgs = new Object[] {
					companyId, parentKaleoInstanceTokenId,
					_getTime(completionDate)
				};
			}
		}
		else if (useFinderCache) {
			finderPath = _finderPathWithPaginationFindByC_PKITI_CD;
			finderArgs = new Object[] {
				companyId, parentKaleoInstanceTokenId, _getTime(completionDate),
				start, end, orderByComparator
			};
		}

		List<KaleoInstanceToken> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstanceToken>)finderCache.getResult(
				finderPath, finderArgs);

			if ((list != null) && !list.isEmpty()) {
				for (KaleoInstanceToken kaleoInstanceToken : list) {
					if ((companyId != kaleoInstanceToken.getCompanyId()) ||
						(parentKaleoInstanceTokenId !=
							kaleoInstanceToken.
								getParentKaleoInstanceTokenId()) ||
						!Objects.equals(
							completionDate,
							kaleoInstanceToken.getCompletionDate())) {

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

			sb.append(_SQL_SELECT_KALEOINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_C_PKITI_CD_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_PKITI_CD_PARENTKALEOINSTANCETOKENID_2);

			boolean bindCompletionDate = false;

			if (completionDate == null) {
				sb.append(_FINDER_COLUMN_C_PKITI_CD_COMPLETIONDATE_1);
			}
			else {
				bindCompletionDate = true;

				sb.append(_FINDER_COLUMN_C_PKITI_CD_COMPLETIONDATE_2);
			}

			if (orderByComparator != null) {
				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);
			}
			else {
				sb.append(KaleoInstanceTokenModelImpl.ORDER_BY_JPQL);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(parentKaleoInstanceTokenId);

				if (bindCompletionDate) {
					queryPos.add(new Timestamp(completionDate.getTime()));
				}

				list = (List<KaleoInstanceToken>)QueryUtil.list(
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
	 * Returns the first kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken findByC_PKITI_CD_First(
			long companyId, long parentKaleoInstanceTokenId,
			Date completionDate,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = fetchByC_PKITI_CD_First(
			companyId, parentKaleoInstanceTokenId, completionDate,
			orderByComparator);

		if (kaleoInstanceToken != null) {
			return kaleoInstanceToken;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", parentKaleoInstanceTokenId=");
		sb.append(parentKaleoInstanceTokenId);

		sb.append(", completionDate=");
		sb.append(completionDate);

		sb.append("}");

		throw new NoSuchInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the first kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the first matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken fetchByC_PKITI_CD_First(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		List<KaleoInstanceToken> list = findByC_PKITI_CD(
			companyId, parentKaleoInstanceTokenId, completionDate, 0, 1,
			orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token
	 * @throws NoSuchInstanceTokenException if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken findByC_PKITI_CD_Last(
			long companyId, long parentKaleoInstanceTokenId,
			Date completionDate,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = fetchByC_PKITI_CD_Last(
			companyId, parentKaleoInstanceTokenId, completionDate,
			orderByComparator);

		if (kaleoInstanceToken != null) {
			return kaleoInstanceToken;
		}

		StringBundler sb = new StringBundler(8);

		sb.append(_NO_SUCH_ENTITY_WITH_KEY);

		sb.append("companyId=");
		sb.append(companyId);

		sb.append(", parentKaleoInstanceTokenId=");
		sb.append(parentKaleoInstanceTokenId);

		sb.append(", completionDate=");
		sb.append(completionDate);

		sb.append("}");

		throw new NoSuchInstanceTokenException(sb.toString());
	}

	/**
	 * Returns the last kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the last matching kaleo instance token, or <code>null</code> if a matching kaleo instance token could not be found
	 */
	@Override
	public KaleoInstanceToken fetchByC_PKITI_CD_Last(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		int count = countByC_PKITI_CD(
			companyId, parentKaleoInstanceTokenId, completionDate);

		if (count == 0) {
			return null;
		}

		List<KaleoInstanceToken> list = findByC_PKITI_CD(
			companyId, parentKaleoInstanceTokenId, completionDate, count - 1,
			count, orderByComparator);

		if (!list.isEmpty()) {
			return list.get(0);
		}

		return null;
	}

	/**
	 * Returns the kaleo instance tokens before and after the current kaleo instance token in the ordered set where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param kaleoInstanceTokenId the primary key of the current kaleo instance token
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @param orderByComparator the comparator to order the set by (optionally <code>null</code>)
	 * @return the previous, current, and next kaleo instance token
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	@Override
	public KaleoInstanceToken[] findByC_PKITI_CD_PrevAndNext(
			long kaleoInstanceTokenId, long companyId,
			long parentKaleoInstanceTokenId, Date completionDate,
			OrderByComparator<KaleoInstanceToken> orderByComparator)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = findByPrimaryKey(
			kaleoInstanceTokenId);

		Session session = null;

		try {
			session = openSession();

			KaleoInstanceToken[] array = new KaleoInstanceTokenImpl[3];

			array[0] = getByC_PKITI_CD_PrevAndNext(
				session, kaleoInstanceToken, companyId,
				parentKaleoInstanceTokenId, completionDate, orderByComparator,
				true);

			array[1] = kaleoInstanceToken;

			array[2] = getByC_PKITI_CD_PrevAndNext(
				session, kaleoInstanceToken, companyId,
				parentKaleoInstanceTokenId, completionDate, orderByComparator,
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

	protected KaleoInstanceToken getByC_PKITI_CD_PrevAndNext(
		Session session, KaleoInstanceToken kaleoInstanceToken, long companyId,
		long parentKaleoInstanceTokenId, Date completionDate,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
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

		sb.append(_SQL_SELECT_KALEOINSTANCETOKEN_WHERE);

		sb.append(_FINDER_COLUMN_C_PKITI_CD_COMPANYID_2);

		sb.append(_FINDER_COLUMN_C_PKITI_CD_PARENTKALEOINSTANCETOKENID_2);

		boolean bindCompletionDate = false;

		if (completionDate == null) {
			sb.append(_FINDER_COLUMN_C_PKITI_CD_COMPLETIONDATE_1);
		}
		else {
			bindCompletionDate = true;

			sb.append(_FINDER_COLUMN_C_PKITI_CD_COMPLETIONDATE_2);
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
			sb.append(KaleoInstanceTokenModelImpl.ORDER_BY_JPQL);
		}

		String sql = sb.toString();

		Query query = session.createQuery(sql);

		query.setFirstResult(0);
		query.setMaxResults(2);

		QueryPos queryPos = QueryPos.getInstance(query);

		queryPos.add(companyId);

		queryPos.add(parentKaleoInstanceTokenId);

		if (bindCompletionDate) {
			queryPos.add(new Timestamp(completionDate.getTime()));
		}

		if (orderByComparator != null) {
			for (Object orderByConditionValue :
					orderByComparator.getOrderByConditionValues(
						kaleoInstanceToken)) {

				queryPos.add(orderByConditionValue);
			}
		}

		List<KaleoInstanceToken> list = query.list();

		if (list.size() == 2) {
			return list.get(1);
		}
		else {
			return null;
		}
	}

	/**
	 * Removes all the kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63; from the database.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 */
	@Override
	public void removeByC_PKITI_CD(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate) {

		for (KaleoInstanceToken kaleoInstanceToken :
				findByC_PKITI_CD(
					companyId, parentKaleoInstanceTokenId, completionDate,
					QueryUtil.ALL_POS, QueryUtil.ALL_POS, null)) {

			remove(kaleoInstanceToken);
		}
	}

	/**
	 * Returns the number of kaleo instance tokens where companyId = &#63; and parentKaleoInstanceTokenId = &#63; and completionDate = &#63;.
	 *
	 * @param companyId the company ID
	 * @param parentKaleoInstanceTokenId the parent kaleo instance token ID
	 * @param completionDate the completion date
	 * @return the number of matching kaleo instance tokens
	 */
	@Override
	public int countByC_PKITI_CD(
		long companyId, long parentKaleoInstanceTokenId, Date completionDate) {

		FinderPath finderPath = _finderPathCountByC_PKITI_CD;

		Object[] finderArgs = new Object[] {
			companyId, parentKaleoInstanceTokenId, _getTime(completionDate)
		};

		Long count = (Long)finderCache.getResult(finderPath, finderArgs);

		if (count == null) {
			StringBundler sb = new StringBundler(4);

			sb.append(_SQL_COUNT_KALEOINSTANCETOKEN_WHERE);

			sb.append(_FINDER_COLUMN_C_PKITI_CD_COMPANYID_2);

			sb.append(_FINDER_COLUMN_C_PKITI_CD_PARENTKALEOINSTANCETOKENID_2);

			boolean bindCompletionDate = false;

			if (completionDate == null) {
				sb.append(_FINDER_COLUMN_C_PKITI_CD_COMPLETIONDATE_1);
			}
			else {
				bindCompletionDate = true;

				sb.append(_FINDER_COLUMN_C_PKITI_CD_COMPLETIONDATE_2);
			}

			String sql = sb.toString();

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				QueryPos queryPos = QueryPos.getInstance(query);

				queryPos.add(companyId);

				queryPos.add(parentKaleoInstanceTokenId);

				if (bindCompletionDate) {
					queryPos.add(new Timestamp(completionDate.getTime()));
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

	private static final String _FINDER_COLUMN_C_PKITI_CD_COMPANYID_2 =
		"kaleoInstanceToken.companyId = ? AND ";

	private static final String
		_FINDER_COLUMN_C_PKITI_CD_PARENTKALEOINSTANCETOKENID_2 =
			"kaleoInstanceToken.parentKaleoInstanceTokenId = ? AND ";

	private static final String _FINDER_COLUMN_C_PKITI_CD_COMPLETIONDATE_1 =
		"kaleoInstanceToken.completionDate IS NULL";

	private static final String _FINDER_COLUMN_C_PKITI_CD_COMPLETIONDATE_2 =
		"kaleoInstanceToken.completionDate = ?";

	public KaleoInstanceTokenPersistenceImpl() {
		setModelClass(KaleoInstanceToken.class);

		setModelImplClass(KaleoInstanceTokenImpl.class);
		setModelPKClass(long.class);

		setTable(KaleoInstanceTokenTable.INSTANCE);
	}

	/**
	 * Caches the kaleo instance token in the entity cache if it is enabled.
	 *
	 * @param kaleoInstanceToken the kaleo instance token
	 */
	@Override
	public void cacheResult(KaleoInstanceToken kaleoInstanceToken) {
		entityCache.putResult(
			KaleoInstanceTokenImpl.class, kaleoInstanceToken.getPrimaryKey(),
			kaleoInstanceToken);
	}

	private int _valueObjectFinderCacheListThreshold;

	/**
	 * Caches the kaleo instance tokens in the entity cache if it is enabled.
	 *
	 * @param kaleoInstanceTokens the kaleo instance tokens
	 */
	@Override
	public void cacheResult(List<KaleoInstanceToken> kaleoInstanceTokens) {
		if ((_valueObjectFinderCacheListThreshold == 0) ||
			((_valueObjectFinderCacheListThreshold > 0) &&
			 (kaleoInstanceTokens.size() >
				 _valueObjectFinderCacheListThreshold))) {

			return;
		}

		for (KaleoInstanceToken kaleoInstanceToken : kaleoInstanceTokens) {
			if (entityCache.getResult(
					KaleoInstanceTokenImpl.class,
					kaleoInstanceToken.getPrimaryKey()) == null) {

				cacheResult(kaleoInstanceToken);
			}
		}
	}

	/**
	 * Clears the cache for all kaleo instance tokens.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache() {
		entityCache.clearCache(KaleoInstanceTokenImpl.class);

		finderCache.clearCache(KaleoInstanceTokenImpl.class);
	}

	/**
	 * Clears the cache for the kaleo instance token.
	 *
	 * <p>
	 * The <code>EntityCache</code> and <code>FinderCache</code> are both cleared by this method.
	 * </p>
	 */
	@Override
	public void clearCache(KaleoInstanceToken kaleoInstanceToken) {
		entityCache.removeResult(
			KaleoInstanceTokenImpl.class, kaleoInstanceToken);
	}

	@Override
	public void clearCache(List<KaleoInstanceToken> kaleoInstanceTokens) {
		for (KaleoInstanceToken kaleoInstanceToken : kaleoInstanceTokens) {
			entityCache.removeResult(
				KaleoInstanceTokenImpl.class, kaleoInstanceToken);
		}
	}

	@Override
	public void clearCache(Set<Serializable> primaryKeys) {
		finderCache.clearCache(KaleoInstanceTokenImpl.class);

		for (Serializable primaryKey : primaryKeys) {
			entityCache.removeResult(KaleoInstanceTokenImpl.class, primaryKey);
		}
	}

	/**
	 * Creates a new kaleo instance token with the primary key. Does not add the kaleo instance token to the database.
	 *
	 * @param kaleoInstanceTokenId the primary key for the new kaleo instance token
	 * @return the new kaleo instance token
	 */
	@Override
	public KaleoInstanceToken create(long kaleoInstanceTokenId) {
		KaleoInstanceToken kaleoInstanceToken = new KaleoInstanceTokenImpl();

		kaleoInstanceToken.setNew(true);
		kaleoInstanceToken.setPrimaryKey(kaleoInstanceTokenId);

		kaleoInstanceToken.setCompanyId(CompanyThreadLocal.getCompanyId());

		return kaleoInstanceToken;
	}

	/**
	 * Removes the kaleo instance token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param kaleoInstanceTokenId the primary key of the kaleo instance token
	 * @return the kaleo instance token that was removed
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	@Override
	public KaleoInstanceToken remove(long kaleoInstanceTokenId)
		throws NoSuchInstanceTokenException {

		return remove((Serializable)kaleoInstanceTokenId);
	}

	/**
	 * Removes the kaleo instance token with the primary key from the database. Also notifies the appropriate model listeners.
	 *
	 * @param primaryKey the primary key of the kaleo instance token
	 * @return the kaleo instance token that was removed
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	@Override
	public KaleoInstanceToken remove(Serializable primaryKey)
		throws NoSuchInstanceTokenException {

		Session session = null;

		try {
			session = openSession();

			KaleoInstanceToken kaleoInstanceToken =
				(KaleoInstanceToken)session.get(
					KaleoInstanceTokenImpl.class, primaryKey);

			if (kaleoInstanceToken == null) {
				if (_log.isDebugEnabled()) {
					_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
				}

				throw new NoSuchInstanceTokenException(
					_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			return remove(kaleoInstanceToken);
		}
		catch (NoSuchInstanceTokenException noSuchEntityException) {
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
	protected KaleoInstanceToken removeImpl(
		KaleoInstanceToken kaleoInstanceToken) {

		Session session = null;

		try {
			session = openSession();

			if (!session.contains(kaleoInstanceToken)) {
				kaleoInstanceToken = (KaleoInstanceToken)session.get(
					KaleoInstanceTokenImpl.class,
					kaleoInstanceToken.getPrimaryKeyObj());
			}

			if (kaleoInstanceToken != null) {
				session.delete(kaleoInstanceToken);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		if (kaleoInstanceToken != null) {
			clearCache(kaleoInstanceToken);
		}

		return kaleoInstanceToken;
	}

	@Override
	public KaleoInstanceToken updateImpl(
		KaleoInstanceToken kaleoInstanceToken) {

		boolean isNew = kaleoInstanceToken.isNew();

		if (!(kaleoInstanceToken instanceof KaleoInstanceTokenModelImpl)) {
			InvocationHandler invocationHandler = null;

			if (ProxyUtil.isProxyClass(kaleoInstanceToken.getClass())) {
				invocationHandler = ProxyUtil.getInvocationHandler(
					kaleoInstanceToken);

				throw new IllegalArgumentException(
					"Implement ModelWrapper in kaleoInstanceToken proxy " +
						invocationHandler.getClass());
			}

			throw new IllegalArgumentException(
				"Implement ModelWrapper in custom KaleoInstanceToken implementation " +
					kaleoInstanceToken.getClass());
		}

		KaleoInstanceTokenModelImpl kaleoInstanceTokenModelImpl =
			(KaleoInstanceTokenModelImpl)kaleoInstanceToken;

		ServiceContext serviceContext =
			ServiceContextThreadLocal.getServiceContext();

		Date date = new Date();

		if (isNew && (kaleoInstanceToken.getCreateDate() == null)) {
			if (serviceContext == null) {
				kaleoInstanceToken.setCreateDate(date);
			}
			else {
				kaleoInstanceToken.setCreateDate(
					serviceContext.getCreateDate(date));
			}
		}

		if (!kaleoInstanceTokenModelImpl.hasSetModifiedDate()) {
			if (serviceContext == null) {
				kaleoInstanceToken.setModifiedDate(date);
			}
			else {
				kaleoInstanceToken.setModifiedDate(
					serviceContext.getModifiedDate(date));
			}
		}

		Session session = null;

		try {
			session = openSession();

			if (isNew) {
				session.save(kaleoInstanceToken);
			}
			else {
				kaleoInstanceToken = (KaleoInstanceToken)session.merge(
					kaleoInstanceToken);
			}
		}
		catch (Exception exception) {
			throw processException(exception);
		}
		finally {
			closeSession(session);
		}

		entityCache.putResult(
			KaleoInstanceTokenImpl.class, kaleoInstanceTokenModelImpl, false,
			true);

		if (isNew) {
			kaleoInstanceToken.setNew(false);
		}

		kaleoInstanceToken.resetOriginalValues();

		return kaleoInstanceToken;
	}

	/**
	 * Returns the kaleo instance token with the primary key or throws a <code>com.liferay.portal.kernel.exception.NoSuchModelException</code> if it could not be found.
	 *
	 * @param primaryKey the primary key of the kaleo instance token
	 * @return the kaleo instance token
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	@Override
	public KaleoInstanceToken findByPrimaryKey(Serializable primaryKey)
		throws NoSuchInstanceTokenException {

		KaleoInstanceToken kaleoInstanceToken = fetchByPrimaryKey(primaryKey);

		if (kaleoInstanceToken == null) {
			if (_log.isDebugEnabled()) {
				_log.debug(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
			}

			throw new NoSuchInstanceTokenException(
				_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + primaryKey);
		}

		return kaleoInstanceToken;
	}

	/**
	 * Returns the kaleo instance token with the primary key or throws a <code>NoSuchInstanceTokenException</code> if it could not be found.
	 *
	 * @param kaleoInstanceTokenId the primary key of the kaleo instance token
	 * @return the kaleo instance token
	 * @throws NoSuchInstanceTokenException if a kaleo instance token with the primary key could not be found
	 */
	@Override
	public KaleoInstanceToken findByPrimaryKey(long kaleoInstanceTokenId)
		throws NoSuchInstanceTokenException {

		return findByPrimaryKey((Serializable)kaleoInstanceTokenId);
	}

	/**
	 * Returns the kaleo instance token with the primary key or returns <code>null</code> if it could not be found.
	 *
	 * @param kaleoInstanceTokenId the primary key of the kaleo instance token
	 * @return the kaleo instance token, or <code>null</code> if a kaleo instance token with the primary key could not be found
	 */
	@Override
	public KaleoInstanceToken fetchByPrimaryKey(long kaleoInstanceTokenId) {
		return fetchByPrimaryKey((Serializable)kaleoInstanceTokenId);
	}

	/**
	 * Returns all the kaleo instance tokens.
	 *
	 * @return the kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findAll() {
		return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
	}

	/**
	 * Returns a range of all the kaleo instance tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @return the range of kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findAll(int start, int end) {
		return findAll(start, end, null);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @return the ordered range of kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findAll(
		int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator) {

		return findAll(start, end, orderByComparator, true);
	}

	/**
	 * Returns an ordered range of all the kaleo instance tokens.
	 *
	 * <p>
	 * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to <code>QueryUtil#ALL_POS</code> will return the full result set. If <code>orderByComparator</code> is specified, then the query will include the given ORDER BY logic. If <code>orderByComparator</code> is absent, then the query will include the default ORDER BY logic from <code>KaleoInstanceTokenModelImpl</code>.
	 * </p>
	 *
	 * @param start the lower bound of the range of kaleo instance tokens
	 * @param end the upper bound of the range of kaleo instance tokens (not inclusive)
	 * @param orderByComparator the comparator to order the results by (optionally <code>null</code>)
	 * @param useFinderCache whether to use the finder cache
	 * @return the ordered range of kaleo instance tokens
	 */
	@Override
	public List<KaleoInstanceToken> findAll(
		int start, int end,
		OrderByComparator<KaleoInstanceToken> orderByComparator,
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

		List<KaleoInstanceToken> list = null;

		if (useFinderCache) {
			list = (List<KaleoInstanceToken>)finderCache.getResult(
				finderPath, finderArgs);
		}

		if (list == null) {
			StringBundler sb = null;
			String sql = null;

			if (orderByComparator != null) {
				sb = new StringBundler(
					2 + (orderByComparator.getOrderByFields().length * 2));

				sb.append(_SQL_SELECT_KALEOINSTANCETOKEN);

				appendOrderByComparator(
					sb, _ORDER_BY_ENTITY_ALIAS, orderByComparator);

				sql = sb.toString();
			}
			else {
				sql = _SQL_SELECT_KALEOINSTANCETOKEN;

				sql = sql.concat(KaleoInstanceTokenModelImpl.ORDER_BY_JPQL);
			}

			Session session = null;

			try {
				session = openSession();

				Query query = session.createQuery(sql);

				list = (List<KaleoInstanceToken>)QueryUtil.list(
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
	 * Removes all the kaleo instance tokens from the database.
	 *
	 */
	@Override
	public void removeAll() {
		for (KaleoInstanceToken kaleoInstanceToken : findAll()) {
			remove(kaleoInstanceToken);
		}
	}

	/**
	 * Returns the number of kaleo instance tokens.
	 *
	 * @return the number of kaleo instance tokens
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
					_SQL_COUNT_KALEOINSTANCETOKEN);

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
		return "kaleoInstanceTokenId";
	}

	@Override
	protected String getSelectSQL() {
		return _SQL_SELECT_KALEOINSTANCETOKEN;
	}

	@Override
	protected Map<String, Integer> getTableColumnsMap() {
		return KaleoInstanceTokenModelImpl.TABLE_COLUMNS_MAP;
	}

	/**
	 * Initializes the kaleo instance token persistence.
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

		_finderPathWithPaginationFindByC_PKITI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_PKITI",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Integer.class.getName(), Integer.class.getName(),
				OrderByComparator.class.getName()
			},
			new String[] {"companyId", "parentKaleoInstanceTokenId"}, true);

		_finderPathWithoutPaginationFindByC_PKITI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_PKITI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "parentKaleoInstanceTokenId"}, true);

		_finderPathCountByC_PKITI = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_PKITI",
			new String[] {Long.class.getName(), Long.class.getName()},
			new String[] {"companyId", "parentKaleoInstanceTokenId"}, false);

		_finderPathWithPaginationFindByC_PKITI_CD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITH_PAGINATION, "findByC_PKITI_CD",
			new String[] {
				Long.class.getName(), Long.class.getName(),
				Date.class.getName(), Integer.class.getName(),
				Integer.class.getName(), OrderByComparator.class.getName()
			},
			new String[] {
				"companyId", "parentKaleoInstanceTokenId", "completionDate"
			},
			true);

		_finderPathWithoutPaginationFindByC_PKITI_CD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "findByC_PKITI_CD",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			},
			new String[] {
				"companyId", "parentKaleoInstanceTokenId", "completionDate"
			},
			true);

		_finderPathCountByC_PKITI_CD = new FinderPath(
			FINDER_CLASS_NAME_LIST_WITHOUT_PAGINATION, "countByC_PKITI_CD",
			new String[] {
				Long.class.getName(), Long.class.getName(), Date.class.getName()
			},
			new String[] {
				"companyId", "parentKaleoInstanceTokenId", "completionDate"
			},
			false);
	}

	@Deactivate
	public void deactivate() {
		entityCache.removeCache(KaleoInstanceTokenImpl.class.getName());
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

	private static Long _getTime(Date date) {
		if (date == null) {
			return null;
		}

		return date.getTime();
	}

	private static final String _SQL_SELECT_KALEOINSTANCETOKEN =
		"SELECT kaleoInstanceToken FROM KaleoInstanceToken kaleoInstanceToken";

	private static final String _SQL_SELECT_KALEOINSTANCETOKEN_WHERE =
		"SELECT kaleoInstanceToken FROM KaleoInstanceToken kaleoInstanceToken WHERE ";

	private static final String _SQL_COUNT_KALEOINSTANCETOKEN =
		"SELECT COUNT(kaleoInstanceToken) FROM KaleoInstanceToken kaleoInstanceToken";

	private static final String _SQL_COUNT_KALEOINSTANCETOKEN_WHERE =
		"SELECT COUNT(kaleoInstanceToken) FROM KaleoInstanceToken kaleoInstanceToken WHERE ";

	private static final String _ORDER_BY_ENTITY_ALIAS = "kaleoInstanceToken.";

	private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY =
		"No KaleoInstanceToken exists with the primary key ";

	private static final String _NO_SUCH_ENTITY_WITH_KEY =
		"No KaleoInstanceToken exists with the key {";

	private static final Log _log = LogFactoryUtil.getLog(
		KaleoInstanceTokenPersistenceImpl.class);

	@Override
	protected FinderCache getFinderCache() {
		return finderCache;
	}

	@Reference
	private KaleoInstanceTokenModelArgumentsResolver
		_kaleoInstanceTokenModelArgumentsResolver;

}